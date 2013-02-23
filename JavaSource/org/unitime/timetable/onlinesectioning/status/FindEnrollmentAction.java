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
name|status
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
name|Date
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
name|coursett
operator|.
name|model
operator|.
name|RoomLocation
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
name|AcademicAreaCode
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
name|reservation
operator|.
name|CourseReservation
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
name|reservation
operator|.
name|CurriculumReservation
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
name|reservation
operator|.
name|GroupReservation
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
name|reservation
operator|.
name|IndividualReservation
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
name|reservation
operator|.
name|Reservation
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
name|server
operator|.
name|Query
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
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
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
name|CourseInfo
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

begin_class
specifier|public
class|class
name|FindEnrollmentAction
implements|implements
name|OnlineSectioningAction
argument_list|<
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
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
name|Query
name|iQuery
decl_stmt|;
specifier|private
name|Long
name|iCourseId
decl_stmt|,
name|iClassId
decl_stmt|;
specifier|private
name|boolean
name|iConsentToDoCourse
decl_stmt|;
specifier|public
name|FindEnrollmentAction
parameter_list|(
name|String
name|query
parameter_list|,
name|Long
name|courseId
parameter_list|,
name|Long
name|classId
parameter_list|,
name|boolean
name|isConsentToDoCourse
parameter_list|)
block|{
name|iQuery
operator|=
operator|new
name|Query
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|iCourseId
operator|=
name|courseId
expr_stmt|;
name|iClassId
operator|=
name|classId
expr_stmt|;
name|iConsentToDoCourse
operator|=
name|isConsentToDoCourse
expr_stmt|;
block|}
specifier|public
name|Query
name|query
parameter_list|()
block|{
return|return
name|iQuery
return|;
block|}
specifier|public
name|Long
name|courseId
parameter_list|()
block|{
return|return
name|iCourseId
return|;
block|}
specifier|public
name|Long
name|classId
parameter_list|()
block|{
return|return
name|iClassId
return|;
block|}
specifier|public
name|boolean
name|isConsentToDoCourse
parameter_list|()
block|{
return|return
name|iConsentToDoCourse
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
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
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
argument_list|()
decl_stmt|;
name|CourseInfo
name|info
init|=
name|server
operator|.
name|getCourseInfo
argument_list|(
name|courseId
argument_list|()
argument_list|)
decl_stmt|;
name|Course
name|course
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|courseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
return|return
name|ret
return|;
name|Section
name|filterSection
init|=
operator|(
name|classId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getSection
argument_list|(
name|classId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
for|for
control|(
name|CourseRequest
name|request
range|:
name|course
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getId
argument_list|()
operator|!=
name|course
operator|.
name|getId
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|filterSection
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getSections
argument_list|()
operator|.
name|contains
argument_list|(
name|filterSection
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|==
literal|null
operator|&&
operator|!
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|canAssign
argument_list|(
name|request
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|query
argument_list|()
operator|.
name|match
argument_list|(
operator|new
name|StatusPageSuggestionsAction
operator|.
name|CourseRequestMatcher
argument_list|(
name|helper
argument_list|,
name|server
argument_list|,
name|info
argument_list|,
name|request
argument_list|,
name|isConsentToDoCourse
argument_list|()
argument_list|)
argument_list|)
condition|)
continue|continue;
name|ClassAssignmentInterface
operator|.
name|Student
name|st
init|=
operator|new
name|ClassAssignmentInterface
operator|.
name|Student
argument_list|()
decl_stmt|;
name|st
operator|.
name|setId
argument_list|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setExternalId
argument_list|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setName
argument_list|(
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|AcademicAreaCode
name|ac
range|:
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getAcademicAreaClasiffications
argument_list|()
control|)
block|{
name|st
operator|.
name|addArea
argument_list|(
name|ac
operator|.
name|getArea
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|addClassification
argument_list|(
name|ac
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|AcademicAreaCode
name|ac
range|:
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getMajors
argument_list|()
control|)
block|{
name|st
operator|.
name|addMajor
argument_list|(
name|ac
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|AcademicAreaCode
name|ac
range|:
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getMinors
argument_list|()
control|)
block|{
name|st
operator|.
name|addGroup
argument_list|(
name|ac
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|e
init|=
operator|new
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|()
decl_stmt|;
name|e
operator|.
name|setStudent
argument_list|(
name|st
argument_list|)
expr_stmt|;
name|e
operator|.
name|setPriority
argument_list|(
literal|1
operator|+
name|request
operator|.
name|getPriority
argument_list|()
argument_list|)
expr_stmt|;
name|CourseAssignment
name|c
init|=
operator|new
name|CourseAssignment
argument_list|()
decl_stmt|;
name|c
operator|.
name|setCourseId
argument_list|(
name|course
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setSubject
argument_list|(
name|course
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setCourseNbr
argument_list|(
name|course
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
expr_stmt|;
name|e
operator|.
name|setCourse
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|e
operator|.
name|setWaitList
argument_list|(
name|request
operator|.
name|isWaitlist
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
name|course
argument_list|)
condition|)
name|e
operator|.
name|setAlternative
argument_list|(
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|isAlternative
argument_list|()
condition|)
block|{
for|for
control|(
name|Request
name|r
range|:
name|request
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
name|r
operator|instanceof
name|CourseRequest
operator|&&
operator|!
name|r
operator|.
name|isAlternative
argument_list|()
operator|&&
name|r
operator|.
name|getAssignment
argument_list|()
operator|==
literal|null
condition|)
block|{
name|e
operator|.
name|setAlternative
argument_list|(
operator|(
operator|(
name|CourseRequest
operator|)
name|r
operator|)
operator|.
name|getCourses
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
name|e
operator|.
name|setRequestedDate
argument_list|(
operator|new
name|Date
argument_list|(
name|request
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getReservation
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Reservation
name|r
init|=
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getReservation
argument_list|()
decl_stmt|;
if|if
condition|(
name|r
operator|instanceof
name|GroupReservation
condition|)
block|{
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationGroup
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|r
operator|instanceof
name|IndividualReservation
condition|)
block|{
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationIndividual
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|r
operator|instanceof
name|CourseReservation
condition|)
block|{
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationCourse
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|r
operator|instanceof
name|CurriculumReservation
condition|)
block|{
name|e
operator|.
name|setReservation
argument_list|(
name|MSG
operator|.
name|reservationCurriculum
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
name|e
operator|.
name|setEnrolledDate
argument_list|(
operator|new
name|Date
argument_list|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|approval
init|=
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
name|e
operator|.
name|setApprovedDate
argument_list|(
operator|new
name|Date
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|approval
index|[
literal|0
index|]
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|e
operator|.
name|setApprovedBy
argument_list|(
name|approval
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Section
name|section
range|:
name|request
operator|.
name|getAssignment
argument_list|()
operator|.
name|getSections
argument_list|()
control|)
block|{
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
init|=
name|e
operator|.
name|getCourse
argument_list|()
operator|.
name|addClassAssignment
argument_list|()
decl_stmt|;
name|a
operator|.
name|setAlternative
argument_list|(
name|request
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
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setSubpart
argument_list|(
name|section
operator|.
name|getSubpart
argument_list|()
operator|.
name|getName
argument_list|()
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
name|getId
argument_list|()
argument_list|)
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
name|setLimit
argument_list|(
operator|new
name|int
index|[]
block|{
name|section
operator|.
name|getEnrollments
argument_list|()
operator|.
name|size
argument_list|()
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
name|getDayCode
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
name|getStartSlot
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
name|Iterator
argument_list|<
name|RoomLocation
argument_list|>
name|i
init|=
name|section
operator|.
name|getRooms
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
name|RoomLocation
name|rm
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|a
operator|.
name|addRoom
argument_list|(
name|rm
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|section
operator|.
name|getChoice
argument_list|()
operator|.
name|getInstructorNames
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|section
operator|.
name|getChoice
argument_list|()
operator|.
name|getInstructorNames
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
index|[]
name|instructors
init|=
name|section
operator|.
name|getChoice
argument_list|()
operator|.
name|getInstructorNames
argument_list|()
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|instructor
range|:
name|instructors
control|)
block|{
name|String
index|[]
name|nameEmail
init|=
name|instructor
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
decl_stmt|;
name|a
operator|.
name|addInstructor
argument_list|(
name|nameEmail
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|a
operator|.
name|addInstructoEmailr
argument_list|(
name|nameEmail
operator|.
name|length
operator|<
literal|2
condition|?
literal|""
else|:
name|nameEmail
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|section
operator|.
name|getParent
argument_list|()
operator|!=
literal|null
condition|)
name|a
operator|.
name|setParentSection
argument_list|(
name|section
operator|.
name|getParent
argument_list|()
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getId
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
name|getSubpart
argument_list|()
operator|.
name|getId
argument_list|()
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
name|section
operator|.
name|getSubpart
argument_list|()
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
name|Request
name|q
range|:
name|request
operator|.
name|getStudent
argument_list|()
operator|.
name|getRequests
argument_list|()
control|)
block|{
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
operator|!
name|x
operator|.
name|isCourseRequest
argument_list|()
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
name|Section
argument_list|>
name|j
init|=
name|x
operator|.
name|getSections
argument_list|()
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
name|Section
name|s
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|==
name|section
operator|||
name|s
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
name|server
operator|.
name|distance
argument_list|(
name|s
argument_list|,
name|section
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
name|RoomLocation
argument_list|>
name|k
init|=
name|s
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
name|s
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
name|s
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
name|s
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|overlap
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|clazz
argument_list|(
name|x
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|x
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseNumber
argument_list|()
argument_list|,
name|s
operator|.
name|getSubpart
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|s
operator|.
name|getName
argument_list|(
name|x
operator|.
name|getCourse
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
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
name|a
operator|.
name|setParentSection
argument_list|(
name|info
operator|.
name|getConsent
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setExpected
argument_list|(
name|Math
operator|.
name|round
argument_list|(
name|section
operator|.
name|getSpaceExpected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|ret
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
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
literal|"find-enrollments"
return|;
block|}
block|}
end_class

end_unit

