begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|basic
package|;
end_package

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
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|ifs
operator|.
name|util
operator|.
name|DistanceMetric
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
name|Course
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
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|resources
operator|.
name|StudentSectioningMessages
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
name|onlinesectioning
operator|.
name|OnlineSectioningAction
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
name|model
operator|.
name|XCourse
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
name|model
operator|.
name|XCourseId
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
name|model
operator|.
name|XCourseRequest
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
name|model
operator|.
name|XDistribution
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
name|model
operator|.
name|XEnrollment
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
name|model
operator|.
name|XEnrollments
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
name|model
operator|.
name|XExpectations
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
name|model
operator|.
name|XFreeTimeRequest
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
name|model
operator|.
name|XInstructor
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
name|model
operator|.
name|XOffering
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
name|model
operator|.
name|XRequest
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
name|model
operator|.
name|XRoom
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
name|model
operator|.
name|XSection
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
name|model
operator|.
name|XStudent
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
name|model
operator|.
name|XSubpart
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
name|solver
operator|.
name|SectioningRequest
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
name|util
operator|.
name|Formats
import|;
end_import

begin_class
specifier|public
class|class
name|GetAssignment
implements|implements
name|OnlineSectioningAction
argument_list|<
name|ClassAssignmentInterface
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|public
name|GetAssignment
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|ClassAssignmentInterface
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|server
operator|.
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|df
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_REQUEST
argument_list|)
decl_stmt|;
name|DistanceMetric
name|m
init|=
name|server
operator|.
name|getDistanceMetric
argument_list|()
decl_stmt|;
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|iStudentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|ClassAssignmentInterface
name|ret
init|=
operator|new
name|ClassAssignmentInterface
argument_list|()
decl_stmt|;
name|int
name|nrUnassignedCourses
init|=
literal|0
decl_stmt|;
for|for
control|(
name|XRequest
name|request
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
name|ca
init|=
operator|new
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XCourseRequest
name|r
init|=
operator|(
name|XCourseRequest
operator|)
name|request
decl_stmt|;
name|XEnrollment
name|enrollment
init|=
name|r
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
name|XCourseId
name|courseId
init|=
operator|(
name|enrollment
operator|==
literal|null
condition|?
name|r
operator|.
name|getCourseIds
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
else|:
name|enrollment
operator|)
decl_stmt|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|courseId
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|XDistribution
argument_list|>
name|distributions
init|=
name|server
operator|.
name|getDistributions
argument_list|(
name|courseId
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|XExpectations
name|expectations
init|=
name|server
operator|.
name|getExpectations
argument_list|(
name|courseId
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|XCourse
name|course
init|=
name|offering
operator|.
name|getCourse
argument_list|(
name|courseId
argument_list|)
decl_stmt|;
if|if
condition|(
name|server
operator|.
name|isOfferingLocked
argument_list|(
name|course
operator|.
name|getOfferingId
argument_list|()
argument_list|)
condition|)
name|ca
operator|.
name|setLocked
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|ca
operator|.
name|setAssigned
argument_list|(
name|enrollment
operator|!=
literal|null
argument_list|)
expr_stmt|;
name|ca
operator|.
name|setCourseId
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|ca
operator|.
name|setSubject
argument_list|(
name|course
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|ca
operator|.
name|setWaitListed
argument_list|(
name|r
operator|.
name|isWaitlist
argument_list|()
argument_list|)
expr_stmt|;
name|ca
operator|.
name|setCourseNbr
argument_list|(
name|course
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|enrollment
operator|==
literal|null
condition|)
block|{
name|TreeSet
argument_list|<
name|Enrollment
argument_list|>
name|overlap
init|=
operator|new
name|TreeSet
argument_list|<
name|Enrollment
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|Enrollment
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Enrollment
name|o1
parameter_list|,
name|Enrollment
name|o2
parameter_list|)
block|{
return|return
name|o1
operator|.
name|getRequest
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o2
operator|.
name|getRequest
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|TreeSet
argument_list|<
name|Section
argument_list|>
argument_list|>
name|overlapingSections
init|=
operator|new
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|TreeSet
argument_list|<
name|Section
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Collection
argument_list|<
name|Enrollment
argument_list|>
name|avEnrls
init|=
name|SectioningRequest
operator|.
name|convert
argument_list|(
name|r
argument_list|,
name|server
argument_list|)
operator|.
name|getEnrollmentsSkipSameTime
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Enrollment
argument_list|>
name|e
init|=
name|avEnrls
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
name|Enrollment
name|enrl
init|=
name|e
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Request
name|q
range|:
name|enrl
operator|.
name|getStudent
argument_list|()
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|q
operator|.
name|equals
argument_list|(
name|request
argument_list|)
condition|)
continue|continue;
name|Enrollment
name|x
init|=
name|q
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|==
literal|null
operator|||
name|x
operator|.
name|getAssignments
argument_list|()
operator|==
literal|null
operator|||
name|x
operator|.
name|getAssignments
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
for|for
control|(
name|Iterator
argument_list|<
name|Assignment
argument_list|>
name|i
init|=
name|x
operator|.
name|getAssignments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Assignment
name|a
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|isOverlapping
argument_list|(
name|enrl
operator|.
name|getAssignments
argument_list|()
argument_list|)
condition|)
block|{
name|overlap
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
if|if
condition|(
name|x
operator|.
name|getRequest
argument_list|()
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
name|x
operator|.
name|getRequest
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|Section
argument_list|>
name|ss
init|=
name|overlapingSections
operator|.
name|get
argument_list|(
name|cr
argument_list|)
decl_stmt|;
if|if
condition|(
name|ss
operator|==
literal|null
condition|)
block|{
name|ss
operator|=
operator|new
name|TreeSet
argument_list|<
name|Section
argument_list|>
argument_list|()
expr_stmt|;
name|overlapingSections
operator|.
name|put
argument_list|(
name|cr
argument_list|,
name|ss
argument_list|)
expr_stmt|;
block|}
name|ss
operator|.
name|add
argument_list|(
operator|(
name|Section
operator|)
name|a
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
for|for
control|(
name|Enrollment
name|q
range|:
name|overlap
control|)
block|{
if|if
condition|(
name|q
operator|.
name|getRequest
argument_list|()
operator|instanceof
name|FreeTimeRequest
condition|)
block|{
name|ca
operator|.
name|addOverlap
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toString
argument_list|(
operator|(
name|FreeTimeRequest
operator|)
name|q
operator|.
name|getRequest
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|q
operator|.
name|getRequest
argument_list|()
decl_stmt|;
name|Course
name|o
init|=
name|q
operator|.
name|getCourse
argument_list|()
decl_stmt|;
name|String
name|ov
init|=
name|MSG
operator|.
name|course
argument_list|(
name|o
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|o
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|overlapingSections
operator|.
name|get
argument_list|(
name|cr
argument_list|)
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
for|for
control|(
name|Iterator
argument_list|<
name|Section
argument_list|>
name|i
init|=
name|overlapingSections
operator|.
name|get
argument_list|(
name|cr
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Section
name|s
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ov
operator|+=
literal|" "
operator|+
name|s
operator|.
name|getSubpart
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|ov
operator|+=
literal|","
expr_stmt|;
block|}
name|ca
operator|.
name|addOverlap
argument_list|(
name|ov
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|avEnrls
operator|.
name|isEmpty
argument_list|()
condition|)
name|ca
operator|.
name|setNotAvailable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|nrUnassignedCourses
operator|++
expr_stmt|;
name|int
name|alt
init|=
name|nrUnassignedCourses
decl_stmt|;
for|for
control|(
name|XRequest
name|q
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|q
operator|instanceof
name|XCourseRequest
operator|&&
operator|!
name|q
operator|.
name|equals
argument_list|(
name|request
argument_list|)
condition|)
block|{
name|XEnrollment
name|otherEnrollment
init|=
operator|(
operator|(
name|XCourseRequest
operator|)
name|q
operator|)
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
name|otherEnrollment
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|q
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
if|if
condition|(
operator|--
name|alt
operator|==
literal|0
condition|)
block|{
name|XOffering
name|otherOffering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|otherEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|XCourse
name|otherCourse
init|=
name|otherOffering
operator|.
name|getCourse
argument_list|(
name|otherEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|ca
operator|.
name|setInstead
argument_list|(
name|MSG
operator|.
name|course
argument_list|(
name|otherCourse
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|otherCourse
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
block|}
else|else
block|{
name|List
argument_list|<
name|XSection
argument_list|>
name|sections
init|=
name|offering
operator|.
name|getSections
argument_list|(
name|enrollment
argument_list|)
decl_stmt|;
name|boolean
name|hasAlt
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|getCourseIds
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|hasAlt
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|offering
operator|.
name|getConfigs
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|hasAlt
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|XSubpart
name|subpart
range|:
name|offering
operator|.
name|getConfigs
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getSubparts
argument_list|()
control|)
block|{
if|if
condition|(
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|hasAlt
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
name|XEnrollments
name|enrollments
init|=
name|server
operator|.
name|getEnrollments
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XSection
name|section
range|:
name|sections
control|)
block|{
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
init|=
name|ca
operator|.
name|addClassAssignment
argument_list|()
decl_stmt|;
name|a
operator|.
name|setAlternative
argument_list|(
name|r
operator|.
name|isAlternative
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setClassId
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
expr_stmt|;
name|XSubpart
name|subpart
init|=
name|offering
operator|.
name|getSubpart
argument_list|(
name|section
operator|.
name|getSubpartId
argument_list|()
argument_list|)
decl_stmt|;
name|a
operator|.
name|setSubpart
argument_list|(
name|subpart
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setClassNumber
argument_list|(
name|section
operator|.
name|getName
argument_list|(
operator|-
literal|1l
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSection
argument_list|(
name|section
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setLimit
argument_list|(
operator|new
name|int
index|[]
block|{
name|enrollments
operator|.
name|countEnrollmentsForSection
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
block|,
name|section
operator|.
name|getLimit
argument_list|()
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|DayCode
name|d
range|:
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getDays
argument_list|()
argument_list|)
control|)
name|a
operator|.
name|addDay
argument_list|(
name|d
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setStart
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getSlot
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setLength
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setBreakTime
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getBreakTime
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setDatePattern
argument_list|(
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getDatePatternName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|section
operator|.
name|getRooms
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XRoom
name|room
range|:
name|section
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|a
operator|.
name|addRoom
argument_list|(
name|room
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|XInstructor
name|instructor
range|:
name|section
operator|.
name|getInstructors
argument_list|()
control|)
block|{
name|a
operator|.
name|addInstructor
argument_list|(
name|instructor
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|addInstructoEmail
argument_list|(
name|instructor
operator|.
name|getEmail
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|instructor
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|section
operator|.
name|getParentId
argument_list|()
operator|!=
literal|null
condition|)
name|a
operator|.
name|setParentSection
argument_list|(
name|offering
operator|.
name|getSection
argument_list|(
name|section
operator|.
name|getParentId
argument_list|()
argument_list|)
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSubpartId
argument_list|(
name|section
operator|.
name|getSubpartId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setHasAlternatives
argument_list|(
name|hasAlt
argument_list|)
expr_stmt|;
name|a
operator|.
name|addNote
argument_list|(
name|course
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|addNote
argument_list|(
name|section
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCredit
argument_list|(
name|subpart
operator|.
name|getCredit
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|dist
init|=
literal|0
decl_stmt|;
name|String
name|from
init|=
literal|null
decl_stmt|;
name|TreeSet
argument_list|<
name|String
argument_list|>
name|overlap
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XRequest
name|q
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|q
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XEnrollment
name|otherEnrollment
init|=
operator|(
operator|(
name|XCourseRequest
operator|)
name|q
operator|)
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
name|otherEnrollment
operator|==
literal|null
condition|)
continue|continue;
name|XOffering
name|otherOffering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|otherEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XSection
name|otherSection
range|:
name|otherOffering
operator|.
name|getSections
argument_list|(
name|otherEnrollment
argument_list|)
control|)
block|{
if|if
condition|(
name|otherSection
operator|.
name|equals
argument_list|(
name|section
argument_list|)
operator|||
name|otherSection
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|int
name|d
init|=
name|otherSection
operator|.
name|getDistanceInMinutes
argument_list|(
name|section
argument_list|,
name|m
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|>
name|dist
condition|)
block|{
name|dist
operator|=
name|d
expr_stmt|;
name|from
operator|=
literal|""
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|XRoom
argument_list|>
name|k
init|=
name|otherSection
operator|.
name|getRooms
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|from
operator|+=
name|k
operator|.
name|next
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|k
operator|.
name|hasNext
argument_list|()
condition|?
literal|", "
else|:
literal|""
operator|)
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|>
name|otherSection
operator|.
name|getTime
argument_list|()
operator|.
name|getBreakTime
argument_list|()
condition|)
block|{
name|a
operator|.
name|setDistanceConflict
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|section
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
operator|&&
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|hasIntersection
argument_list|(
name|otherSection
operator|.
name|getTime
argument_list|()
argument_list|)
operator|&&
operator|!
name|section
operator|.
name|isToIgnoreStudentConflictsWith
argument_list|(
name|distributions
argument_list|,
name|otherSection
operator|.
name|getSectionId
argument_list|()
argument_list|)
condition|)
block|{
name|XCourse
name|otherCourse
init|=
name|otherOffering
operator|.
name|getCourse
argument_list|(
name|otherEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|XSubpart
name|otherSubpart
init|=
name|otherOffering
operator|.
name|getSubpart
argument_list|(
name|otherSection
operator|.
name|getSubpartId
argument_list|()
argument_list|)
decl_stmt|;
name|overlap
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|clazz
argument_list|(
name|otherCourse
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|otherCourse
operator|.
name|getCourseNumber
argument_list|()
argument_list|,
name|otherSubpart
operator|.
name|getName
argument_list|()
argument_list|,
name|otherSection
operator|.
name|getName
argument_list|(
name|otherCourse
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
operator|!
name|overlap
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|j
init|=
name|overlap
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|n
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|note
operator|==
literal|null
condition|)
name|note
operator|=
name|MSG
operator|.
name|noteAllowedOverlapFirst
argument_list|(
name|n
argument_list|)
expr_stmt|;
if|else if
condition|(
name|j
operator|.
name|hasNext
argument_list|()
condition|)
name|note
operator|+=
name|MSG
operator|.
name|noteAllowedOverlapMiddle
argument_list|(
name|n
argument_list|)
expr_stmt|;
else|else
name|note
operator|+=
name|MSG
operator|.
name|noteAllowedOverlapLast
argument_list|(
name|n
argument_list|)
expr_stmt|;
block|}
name|a
operator|.
name|addNote
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
name|a
operator|.
name|setBackToBackDistance
argument_list|(
name|dist
argument_list|)
expr_stmt|;
name|a
operator|.
name|setBackToBackRooms
argument_list|(
name|from
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSaved
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|a
operator|.
name|getParentSection
argument_list|()
operator|==
literal|null
condition|)
block|{
name|String
name|consent
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|.
name|getConsentLabel
argument_list|()
decl_stmt|;
if|if
condition|(
name|consent
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|a
operator|.
name|setParentSection
argument_list|(
name|MSG
operator|.
name|consentApproved
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|enrollment
operator|.
name|getApproval
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
name|a
operator|.
name|setParentSection
argument_list|(
name|MSG
operator|.
name|consentWaiting
argument_list|(
name|consent
operator|.
name|toLowerCase
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|a
operator|.
name|setExpected
argument_list|(
name|Math
operator|.
name|round
argument_list|(
name|expectations
operator|.
name|getExpectedSpace
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|else if
condition|(
name|request
operator|instanceof
name|XFreeTimeRequest
condition|)
block|{
name|XFreeTimeRequest
name|r
init|=
operator|(
name|XFreeTimeRequest
operator|)
name|request
decl_stmt|;
name|ca
operator|.
name|setCourseId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
for|for
control|(
name|XRequest
name|q
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|q
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XEnrollment
name|otherEnrollment
init|=
operator|(
operator|(
name|XCourseRequest
operator|)
name|q
operator|)
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
name|otherEnrollment
operator|==
literal|null
condition|)
continue|continue;
name|XOffering
name|otherOffering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|otherEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XSection
name|otherSection
range|:
name|otherOffering
operator|.
name|getSections
argument_list|(
name|otherEnrollment
argument_list|)
control|)
block|{
if|if
condition|(
name|otherSection
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
operator|&&
name|otherSection
operator|.
name|getTime
argument_list|()
operator|.
name|hasIntersection
argument_list|(
name|r
operator|.
name|getTime
argument_list|()
argument_list|)
condition|)
block|{
name|XCourse
name|otherCourse
init|=
name|otherOffering
operator|.
name|getCourse
argument_list|(
name|otherEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|XSubpart
name|otherSubpart
init|=
name|otherOffering
operator|.
name|getSubpart
argument_list|(
name|otherSection
operator|.
name|getSubpartId
argument_list|()
argument_list|)
decl_stmt|;
name|ca
operator|.
name|addOverlap
argument_list|(
name|MSG
operator|.
name|clazz
argument_list|(
name|otherCourse
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|otherCourse
operator|.
name|getCourseNumber
argument_list|()
argument_list|,
name|otherSubpart
operator|.
name|getName
argument_list|()
argument_list|,
name|otherSection
operator|.
name|getName
argument_list|(
name|otherCourse
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|ca
operator|.
name|setAssigned
argument_list|(
name|ca
operator|.
name|getOverlaps
argument_list|()
operator|==
literal|null
argument_list|)
expr_stmt|;
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
init|=
name|ca
operator|.
name|addClassAssignment
argument_list|()
decl_stmt|;
name|a
operator|.
name|setAlternative
argument_list|(
name|r
operator|.
name|isAlternative
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|DayCode
name|d
range|:
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|r
operator|.
name|getTime
argument_list|()
operator|.
name|getDays
argument_list|()
argument_list|)
control|)
name|a
operator|.
name|addDay
argument_list|(
name|d
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setStart
argument_list|(
name|r
operator|.
name|getTime
argument_list|()
operator|.
name|getSlot
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setLength
argument_list|(
name|r
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ret
operator|.
name|add
argument_list|(
name|ca
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"get-assignment"
return|;
block|}
block|}
end_class

end_unit

