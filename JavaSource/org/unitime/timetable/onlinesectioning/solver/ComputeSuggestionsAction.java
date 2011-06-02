begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|solver
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|StudentSectioningModel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|extension
operator|.
name|DistanceConflict
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|extension
operator|.
name|TimeOverlapsCounter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Assignment
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|CourseRequest
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Enrollment
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|FreeTimeRequest
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Request
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Section
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Student
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|server
operator|.
name|DayCode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|shared
operator|.
name|ClassAssignmentInterface
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|shared
operator|.
name|CourseRequestInterface
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|shared
operator|.
name|SectioningException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|shared
operator|.
name|SectioningExceptionType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|OnlineSectioningLog
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|OnlineSectioningServer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|OnlineSectioningServer
operator|.
name|Lock
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ComputeSuggestionsAction
extends|extends
name|FindAssignmentAction
block|{
specifier|private
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|iSelection
decl_stmt|;
specifier|private
name|double
name|iValue
init|=
literal|0.0
decl_stmt|;
specifier|public
name|ComputeSuggestionsAction
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|,
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|selectedAssignment
parameter_list|)
throws|throws
name|SectioningException
block|{
name|super
argument_list|(
name|request
argument_list|,
name|currentAssignment
argument_list|)
expr_stmt|;
name|iSelection
operator|=
name|selectedAssignment
expr_stmt|;
block|}
specifier|public
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|getSelection
parameter_list|()
block|{
return|return
name|iSelection
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|long
name|t0
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|StudentSectioningModel
name|model
init|=
operator|new
name|StudentSectioningModel
argument_list|(
name|server
operator|.
name|getConfig
argument_list|()
argument_list|)
decl_stmt|;
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|getAction
argument_list|()
decl_stmt|;
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
operator|!=
literal|null
condition|)
name|action
operator|.
name|setStudent
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Student
name|student
init|=
operator|new
name|Student
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1l
else|:
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|enrolled
init|=
literal|null
decl_stmt|;
name|Lock
name|readLock
init|=
name|server
operator|.
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|Student
name|original
init|=
operator|(
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|original
operator|!=
literal|null
condition|)
block|{
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|original
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|original
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|enrolled
operator|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Request
name|r
range|:
name|original
operator|.
name|getRequests
argument_list|()
control|)
if|if
condition|(
name|r
operator|.
name|getInitialAssignment
argument_list|()
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|isCourseRequest
argument_list|()
condition|)
for|for
control|(
name|Section
name|s
range|:
name|r
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|getSections
argument_list|()
control|)
name|enrolled
operator|.
name|add
argument_list|(
name|s
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|enrollment
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|enrollment
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|STORED
argument_list|)
expr_stmt|;
for|for
control|(
name|Request
name|oldRequest
range|:
name|original
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|oldRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|!=
literal|null
operator|&&
name|oldRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|isCourseRequest
argument_list|()
condition|)
for|for
control|(
name|Section
name|section
range|:
name|oldRequest
operator|.
name|getInitialAssignment
argument_list|()
operator|.
name|getSections
argument_list|()
control|)
name|enrollment
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|section
argument_list|,
name|oldRequest
operator|.
name|getInitialAssignment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|addEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|c
range|:
name|getRequest
argument_list|()
operator|.
name|getCourses
argument_list|()
control|)
name|addRequest
argument_list|(
name|server
argument_list|,
name|model
argument_list|,
name|student
argument_list|,
name|original
argument_list|,
name|c
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|SectioningExceptionType
operator|.
name|EMPTY_COURSE_REQUEST
argument_list|)
throw|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|c
range|:
name|getRequest
argument_list|()
operator|.
name|getAlternatives
argument_list|()
control|)
name|addRequest
argument_list|(
name|server
argument_list|,
name|model
argument_list|,
name|student
argument_list|,
name|original
argument_list|,
name|c
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|model
operator|.
name|addStudent
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|model
operator|.
name|setDistanceConflict
argument_list|(
operator|new
name|DistanceConflict
argument_list|(
literal|null
argument_list|,
name|model
operator|.
name|getProperties
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|setTimeOverlaps
argument_list|(
operator|new
name|TimeOverlapsCounter
argument_list|(
literal|null
argument_list|,
name|model
operator|.
name|getProperties
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|readLock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
name|long
name|t1
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
name|preferredSectionsForCourse
init|=
operator|new
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
name|requiredSectionsForCourse
init|=
operator|new
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|HashSet
argument_list|<
name|FreeTimeRequest
argument_list|>
name|requiredFreeTimes
init|=
operator|new
name|HashSet
argument_list|<
name|FreeTimeRequest
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
argument_list|>
argument_list|()
decl_stmt|;
name|ClassAssignmentInterface
name|messages
init|=
operator|new
name|ClassAssignmentInterface
argument_list|()
decl_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|messages
argument_list|)
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|requested
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|requested
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|PREVIOUS
argument_list|)
expr_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|assignment
range|:
name|getAssignment
argument_list|()
control|)
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
name|requested
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
name|action
operator|.
name|addEnrollment
argument_list|(
name|requested
argument_list|)
expr_stmt|;
name|Request
name|selectedRequest
init|=
literal|null
decl_stmt|;
name|Section
name|selectedSection
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Request
argument_list|>
name|e
init|=
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|e
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Request
name|r
init|=
operator|(
name|Request
operator|)
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
name|OnlineSectioningLog
operator|.
name|Request
operator|.
name|Builder
name|rq
init|=
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|r
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|instanceof
name|CourseRequest
condition|)
block|{
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|r
decl_stmt|;
if|if
condition|(
operator|!
name|getSelection
argument_list|()
operator|.
name|isFreeTime
argument_list|()
operator|&&
name|cr
operator|.
name|getCourse
argument_list|(
name|getSelection
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|selectedRequest
operator|=
name|r
expr_stmt|;
if|if
condition|(
name|getSelection
argument_list|()
operator|.
name|getClassId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Section
name|section
init|=
name|cr
operator|.
name|getSection
argument_list|(
name|getSelection
argument_list|()
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|!=
literal|null
condition|)
name|selectedSection
operator|=
name|section
expr_stmt|;
block|}
block|}
name|HashSet
argument_list|<
name|Section
argument_list|>
name|preferredSections
init|=
operator|new
name|HashSet
argument_list|<
name|Section
argument_list|>
argument_list|()
decl_stmt|;
name|HashSet
argument_list|<
name|Section
argument_list|>
name|requiredSections
init|=
operator|new
name|HashSet
argument_list|<
name|Section
argument_list|>
argument_list|()
decl_stmt|;
name|a
label|:
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
range|:
name|getAssignment
argument_list|()
control|)
block|{
if|if
condition|(
name|a
operator|!=
literal|null
operator|&&
operator|!
name|a
operator|.
name|isFreeTime
argument_list|()
operator|&&
name|cr
operator|.
name|getCourse
argument_list|(
name|a
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|!=
literal|null
operator|&&
name|a
operator|.
name|getClassId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Section
name|section
init|=
name|cr
operator|.
name|getSection
argument_list|(
name|a
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|==
literal|null
operator|||
name|section
operator|.
name|getLimit
argument_list|()
operator|==
literal|0
condition|)
block|{
name|messages
operator|.
name|addMessage
argument_list|(
operator|(
name|a
operator|.
name|isSaved
argument_list|()
condition|?
literal|"Enrolled class"
else|:
name|a
operator|.
name|isPinned
argument_list|()
condition|?
literal|"Required class"
else|:
literal|"Previously selected class"
operator|)
operator|+
name|a
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|+
name|a
operator|.
name|getCourseNbr
argument_list|()
operator|+
literal|" "
operator|+
name|a
operator|.
name|getSubpart
argument_list|()
operator|+
literal|" "
operator|+
name|a
operator|.
name|getSection
argument_list|()
operator|+
literal|" is no longer available."
argument_list|)
expr_stmt|;
continue|continue
name|a
continue|;
block|}
if|if
condition|(
name|a
operator|.
name|isPinned
argument_list|()
operator|&&
operator|!
name|getSelection
argument_list|()
operator|.
name|equals
argument_list|(
name|a
argument_list|)
condition|)
name|requiredSections
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
name|preferredSections
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
name|cr
operator|.
name|getSelectedChoices
argument_list|()
operator|.
name|add
argument_list|(
name|section
operator|.
name|getChoice
argument_list|()
argument_list|)
expr_stmt|;
name|rq
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|section
argument_list|,
name|cr
operator|.
name|getCourse
argument_list|(
name|a
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|setPreference
argument_list|(
name|getSelection
argument_list|()
operator|.
name|equals
argument_list|(
name|a
argument_list|)
condition|?
name|OnlineSectioningLog
operator|.
name|Section
operator|.
name|Preference
operator|.
name|SELECTED
else|:
name|a
operator|.
name|isPinned
argument_list|()
condition|?
name|OnlineSectioningLog
operator|.
name|Section
operator|.
name|Preference
operator|.
name|REQUIRED
else|:
name|OnlineSectioningLog
operator|.
name|Section
operator|.
name|Preference
operator|.
name|PREFERRED
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|preferredSectionsForCourse
operator|.
name|put
argument_list|(
name|cr
argument_list|,
name|preferredSections
argument_list|)
expr_stmt|;
name|requiredSectionsForCourse
operator|.
name|put
argument_list|(
name|cr
argument_list|,
name|requiredSections
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|FreeTimeRequest
name|ft
init|=
operator|(
name|FreeTimeRequest
operator|)
name|r
decl_stmt|;
if|if
condition|(
name|getSelection
argument_list|()
operator|.
name|isFreeTime
argument_list|()
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|==
name|getSelection
argument_list|()
operator|.
name|getStart
argument_list|()
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
operator|==
name|getSelection
argument_list|()
operator|.
name|getLength
argument_list|()
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
operator|==
name|DayCode
operator|.
name|toInt
argument_list|(
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|getSelection
argument_list|()
operator|.
name|getDays
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
name|selectedRequest
operator|=
name|r
expr_stmt|;
for|for
control|(
name|OnlineSectioningLog
operator|.
name|Time
operator|.
name|Builder
name|ftb
range|:
name|rq
operator|.
name|getFreeTimeBuilderList
argument_list|()
control|)
name|ftb
operator|.
name|setPreference
argument_list|(
name|OnlineSectioningLog
operator|.
name|Section
operator|.
name|Preference
operator|.
name|SELECTED
argument_list|)
expr_stmt|;
block|}
else|else
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
range|:
name|getAssignment
argument_list|()
control|)
block|{
if|if
condition|(
name|a
operator|!=
literal|null
operator|&&
name|a
operator|.
name|isFreeTime
argument_list|()
operator|&&
name|a
operator|.
name|isPinned
argument_list|()
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|==
name|a
operator|.
name|getStart
argument_list|()
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
operator|==
name|a
operator|.
name|getLength
argument_list|()
operator|&&
name|ft
operator|.
name|getTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
operator|==
name|DayCode
operator|.
name|toInt
argument_list|(
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|a
operator|.
name|getDays
argument_list|()
argument_list|)
argument_list|)
condition|)
block|{
name|requiredFreeTimes
operator|.
name|add
argument_list|(
name|ft
argument_list|)
expr_stmt|;
for|for
control|(
name|OnlineSectioningLog
operator|.
name|Time
operator|.
name|Builder
name|ftb
range|:
name|rq
operator|.
name|getFreeTimeBuilderList
argument_list|()
control|)
name|ftb
operator|.
name|setPreference
argument_list|(
name|OnlineSectioningLog
operator|.
name|Section
operator|.
name|Preference
operator|.
name|REQUIRED
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|action
operator|.
name|addRequest
argument_list|(
name|rq
argument_list|)
expr_stmt|;
block|}
name|long
name|t2
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|SuggestionsBranchAndBound
name|suggestionBaB
init|=
operator|new
name|SuggestionsBranchAndBound
argument_list|(
name|model
operator|.
name|getProperties
argument_list|()
argument_list|,
name|student
argument_list|,
name|requiredSectionsForCourse
argument_list|,
name|requiredFreeTimes
argument_list|,
name|preferredSectionsForCourse
argument_list|,
name|selectedRequest
argument_list|,
name|selectedSection
argument_list|)
decl_stmt|;
name|TreeSet
argument_list|<
name|SuggestionsBranchAndBound
operator|.
name|Suggestion
argument_list|>
name|suggestions
init|=
name|suggestionBaB
operator|.
name|computeSuggestions
argument_list|()
decl_stmt|;
name|iValue
operator|=
operator|(
name|suggestions
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0.0
else|:
operator|-
name|suggestions
operator|.
name|first
argument_list|()
operator|.
name|getValue
argument_list|()
operator|)
expr_stmt|;
name|long
name|t3
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|helper
operator|.
name|debug
argument_list|(
literal|"  -- suggestion B&B took "
operator|+
name|suggestionBaB
operator|.
name|getTime
argument_list|()
operator|+
literal|"ms"
operator|+
operator|(
name|suggestionBaB
operator|.
name|isTimeoutReached
argument_list|()
condition|?
literal|", timeout reached"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
for|for
control|(
name|SuggestionsBranchAndBound
operator|.
name|Suggestion
name|suggestion
range|:
name|suggestions
control|)
block|{
name|ret
operator|.
name|add
argument_list|(
name|convert
argument_list|(
name|server
argument_list|,
name|suggestion
operator|.
name|getEnrollments
argument_list|()
argument_list|,
name|requiredSectionsForCourse
argument_list|,
name|requiredFreeTimes
argument_list|,
literal|true
argument_list|,
name|model
operator|.
name|getDistanceConflict
argument_list|()
argument_list|,
name|enrolled
argument_list|)
argument_list|)
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|solution
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|solution
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|COMPUTED
argument_list|)
expr_stmt|;
name|solution
operator|.
name|setValue
argument_list|(
operator|-
name|suggestion
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Enrollment
name|e
range|:
name|suggestion
operator|.
name|getEnrollments
argument_list|()
control|)
block|{
if|if
condition|(
name|e
operator|!=
literal|null
operator|&&
name|e
operator|.
name|getAssignments
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|Assignment
name|section
range|:
name|e
operator|.
name|getAssignments
argument_list|()
control|)
name|solution
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|section
argument_list|,
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|addEnrollment
argument_list|(
name|solution
argument_list|)
expr_stmt|;
block|}
name|long
name|t4
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"Sectioning took "
operator|+
operator|(
name|t4
operator|-
name|t0
operator|)
operator|+
literal|"ms (model "
operator|+
operator|(
name|t1
operator|-
name|t0
operator|)
operator|+
literal|"ms, solver init "
operator|+
operator|(
name|t2
operator|-
name|t1
operator|)
operator|+
literal|"ms, sectioning "
operator|+
operator|(
name|t3
operator|-
name|t2
operator|)
operator|+
literal|"ms, conversion "
operator|+
operator|(
name|t4
operator|-
name|t3
operator|)
operator|+
literal|"ms)"
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"suggestions"
return|;
block|}
specifier|public
name|double
name|value
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
block|}
end_class

end_unit

