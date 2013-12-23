begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Collections
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
name|HashMap
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|StudentInfo
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
name|match
operator|.
name|AbstractStudentMatcher
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
name|XAcademicAreaCode
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
name|XStudentId
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
name|status
operator|.
name|FindEnrollmentInfoAction
operator|.
name|FindEnrollmentInfoCourseMatcher
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
name|status
operator|.
name|StatusPageSuggestionsAction
operator|.
name|CourseRequestMatcher
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
name|status
operator|.
name|StatusPageSuggestionsAction
operator|.
name|StudentMatcher
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|FindStudentInfoAction
implements|implements
name|OnlineSectioningAction
argument_list|<
name|List
argument_list|<
name|StudentInfo
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
name|Query
name|iQuery
decl_stmt|;
specifier|private
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iCoursesIcoordinate
decl_stmt|,
name|iCoursesIcanApprove
decl_stmt|;
specifier|public
name|FindStudentInfoAction
parameter_list|(
name|String
name|query
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|coursesIcoordinage
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|coursesIcanApprove
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
name|iCoursesIcanApprove
operator|=
name|coursesIcanApprove
expr_stmt|;
name|iCoursesIcoordinate
operator|=
name|coursesIcoordinage
expr_stmt|;
name|Matcher
name|m
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"limit:[ ]?([0-9]*)"
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
operator|.
name|matcher
argument_list|(
name|query
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|iLimit
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|Integer
name|limit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|boolean
name|isConsentToDoCourse
parameter_list|(
name|XCourse
name|course
parameter_list|)
block|{
return|return
name|iCoursesIcanApprove
operator|!=
literal|null
operator|&&
name|course
operator|.
name|getConsentLabel
argument_list|()
operator|!=
literal|null
operator|&&
name|iCoursesIcanApprove
operator|.
name|contains
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isCourseVisible
parameter_list|(
name|Long
name|courseId
parameter_list|)
block|{
return|return
name|iCoursesIcoordinate
operator|==
literal|null
operator|||
name|iCoursesIcoordinate
operator|.
name|contains
argument_list|(
name|courseId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|StudentInfo
argument_list|>
name|execute
parameter_list|(
specifier|final
name|OnlineSectioningServer
name|server
parameter_list|,
specifier|final
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|StudentInfo
argument_list|>
name|students
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|StudentInfo
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|gEnrl
init|=
literal|0
decl_stmt|,
name|gWait
init|=
literal|0
decl_stmt|,
name|gRes
init|=
literal|0
decl_stmt|,
name|gUnasg
init|=
literal|0
decl_stmt|;
name|int
name|gtEnrl
init|=
literal|0
decl_stmt|,
name|gtWait
init|=
literal|0
decl_stmt|,
name|gtRes
init|=
literal|0
decl_stmt|,
name|gtUnasg
init|=
literal|0
decl_stmt|;
name|int
name|gConNeed
init|=
literal|0
decl_stmt|,
name|gtConNeed
init|=
literal|0
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|unassigned
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|XCourseId
name|info
range|:
name|server
operator|.
name|findCourses
argument_list|(
operator|new
name|FindEnrollmentInfoCourseMatcher
argument_list|(
name|iCoursesIcoordinate
argument_list|,
name|iCoursesIcanApprove
argument_list|,
name|iQuery
argument_list|)
argument_list|)
control|)
block|{
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|info
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
condition|)
continue|continue;
name|XCourse
name|course
init|=
name|offering
operator|.
name|getCourse
argument_list|(
name|info
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|XEnrollments
name|enrollments
init|=
name|server
operator|.
name|getEnrollments
argument_list|(
name|info
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|enrollments
operator|==
literal|null
condition|)
continue|continue;
name|boolean
name|isConsentToDoCourse
init|=
name|isConsentToDoCourse
argument_list|(
name|course
argument_list|)
decl_stmt|;
for|for
control|(
name|XCourseRequest
name|request
range|:
name|enrollments
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|request
operator|.
name|hasCourse
argument_list|(
name|info
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|info
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|CourseRequestMatcher
name|m
init|=
operator|new
name|CourseRequestMatcher
argument_list|(
name|server
argument_list|,
name|course
argument_list|,
name|student
argument_list|,
name|offering
argument_list|,
name|request
argument_list|,
name|isConsentToDoCourse
argument_list|)
decl_stmt|;
if|if
condition|(
name|query
argument_list|()
operator|.
name|match
argument_list|(
name|m
argument_list|)
condition|)
block|{
name|StudentInfo
name|s
init|=
name|students
operator|.
name|get
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
name|s
operator|=
operator|new
name|StudentInfo
argument_list|()
expr_stmt|;
name|students
operator|.
name|put
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|,
name|s
argument_list|)
expr_stmt|;
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
name|s
operator|.
name|setStudent
argument_list|(
name|st
argument_list|)
expr_stmt|;
name|st
operator|.
name|setId
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XAcademicAreaCode
name|ac
range|:
name|student
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
name|XAcademicAreaCode
name|ac
range|:
name|student
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
name|String
name|acc
range|:
name|student
operator|.
name|getAccomodations
argument_list|()
control|)
block|{
name|st
operator|.
name|addAccommodation
argument_list|(
name|acc
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|gr
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
block|{
name|st
operator|.
name|addGroup
argument_list|(
name|gr
argument_list|)
expr_stmt|;
block|}
name|int
name|tEnrl
init|=
literal|0
decl_stmt|,
name|tWait
init|=
literal|0
decl_stmt|,
name|tRes
init|=
literal|0
decl_stmt|,
name|tConNeed
init|=
literal|0
decl_stmt|,
name|tReq
init|=
literal|0
decl_stmt|,
name|tUnasg
init|=
literal|0
decl_stmt|;
for|for
control|(
name|XRequest
name|r
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|r
decl_stmt|;
if|if
condition|(
operator|!
name|r
operator|.
name|isAlternative
argument_list|()
condition|)
name|tReq
operator|++
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|student
operator|.
name|canAssign
argument_list|(
name|cr
argument_list|)
condition|)
block|{
name|tUnasg
operator|++
expr_stmt|;
name|gtUnasg
operator|++
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|isWaitlist
argument_list|()
condition|)
block|{
name|tWait
operator|++
expr_stmt|;
name|gtWait
operator|++
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|tEnrl
operator|++
expr_stmt|;
name|gtEnrl
operator|++
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getReservation
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|tRes
operator|++
expr_stmt|;
name|gtRes
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|==
literal|null
condition|)
block|{
name|XCourse
name|i
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|!=
literal|null
operator|&&
name|i
operator|.
name|getConsentLabel
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|tConNeed
operator|++
expr_stmt|;
name|gtConNeed
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
name|s
operator|.
name|setTotalEnrollment
argument_list|(
name|tEnrl
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalReservation
argument_list|(
name|tRes
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalWaitlist
argument_list|(
name|tWait
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalUnassigned
argument_list|(
name|tUnasg
argument_list|)
expr_stmt|;
name|s
operator|.
name|setTotalConsentNeeded
argument_list|(
name|tConNeed
argument_list|)
expr_stmt|;
name|s
operator|.
name|setEnrollment
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setReservation
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setWaitlist
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setUnassigned
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setConsentNeeded
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|s
operator|.
name|setRequested
argument_list|(
name|tReq
argument_list|)
expr_stmt|;
name|s
operator|.
name|setStatus
argument_list|(
name|student
operator|.
name|getStatus
argument_list|()
operator|==
literal|null
condition|?
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
else|:
name|student
operator|.
name|getStatus
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setEmailDate
argument_list|(
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|m
operator|.
name|enrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|s
operator|.
name|setEnrollment
argument_list|(
name|s
operator|.
name|getEnrollment
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gEnrl
operator|++
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getReservation
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|s
operator|.
name|setReservation
argument_list|(
name|s
operator|.
name|getReservation
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gRes
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|course
operator|.
name|getConsentLabel
argument_list|()
operator|!=
literal|null
operator|&&
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|==
literal|null
condition|)
block|{
name|s
operator|.
name|setConsentNeeded
argument_list|(
name|s
operator|.
name|getConsentNeeded
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gConNeed
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|getEnrolledDate
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setEnrolledDate
argument_list|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|after
argument_list|(
name|s
operator|.
name|getEnrolledDate
argument_list|()
argument_list|)
condition|)
name|s
operator|.
name|setEnrolledDate
argument_list|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setApprovedDate
argument_list|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|after
argument_list|(
name|s
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
condition|)
name|s
operator|.
name|setApprovedDate
argument_list|(
name|m
operator|.
name|enrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|m
operator|.
name|student
argument_list|()
operator|.
name|canAssign
argument_list|(
name|m
operator|.
name|request
argument_list|()
argument_list|)
operator|&&
name|unassigned
operator|.
name|add
argument_list|(
name|m
operator|.
name|request
argument_list|()
operator|.
name|getRequestId
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|m
operator|.
name|request
argument_list|()
operator|.
name|isWaitlist
argument_list|()
condition|)
block|{
name|s
operator|.
name|setWaitlist
argument_list|(
name|s
operator|.
name|getWaitlist
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gWait
operator|++
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|getTopWaitingPriority
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setTopWaitingPriority
argument_list|(
literal|1
operator|+
name|m
operator|.
name|request
argument_list|()
operator|.
name|getPriority
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|s
operator|.
name|setTopWaitingPriority
argument_list|(
name|Math
operator|.
name|min
argument_list|(
literal|1
operator|+
name|m
operator|.
name|request
argument_list|()
operator|.
name|getPriority
argument_list|()
argument_list|,
name|s
operator|.
name|getTopWaitingPriority
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|s
operator|.
name|setUnassigned
argument_list|(
name|s
operator|.
name|getUnassigned
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|gUnasg
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|m
operator|.
name|request
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|getRequestedDate
argument_list|()
operator|==
literal|null
condition|)
name|s
operator|.
name|setRequestedDate
argument_list|(
name|m
operator|.
name|request
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|m
operator|.
name|request
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|after
argument_list|(
name|s
operator|.
name|getRequestedDate
argument_list|()
argument_list|)
condition|)
name|s
operator|.
name|setRequestedDate
argument_list|(
name|m
operator|.
name|request
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|List
argument_list|<
name|StudentInfo
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|StudentInfo
argument_list|>
argument_list|(
name|students
operator|.
name|values
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XStudentId
name|id
range|:
name|server
operator|.
name|findStudents
argument_list|(
operator|new
name|FindStudentInfoMatcher
argument_list|(
name|server
argument_list|,
name|query
argument_list|()
argument_list|)
argument_list|)
control|)
block|{
name|XStudent
name|student
init|=
operator|(
name|id
operator|instanceof
name|XStudent
condition|?
operator|(
name|XStudent
operator|)
name|id
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|id
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|StudentInfo
name|s
init|=
operator|new
name|StudentInfo
argument_list|()
decl_stmt|;
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
name|s
operator|.
name|setStudent
argument_list|(
name|st
argument_list|)
expr_stmt|;
name|st
operator|.
name|setId
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|st
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XAcademicAreaCode
name|ac
range|:
name|student
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
name|XAcademicAreaCode
name|ac
range|:
name|student
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
name|s
operator|.
name|setStatus
argument_list|(
name|student
operator|.
name|getStatus
argument_list|()
operator|==
literal|null
condition|?
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
else|:
name|student
operator|.
name|getStatus
argument_list|()
argument_list|)
expr_stmt|;
name|s
operator|.
name|setEmailDate
argument_list|(
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|student
operator|.
name|getEmailTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|ret
argument_list|,
operator|new
name|Comparator
argument_list|<
name|StudentInfo
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|StudentInfo
name|s1
parameter_list|,
name|StudentInfo
name|s2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|s1
operator|.
name|getStudent
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getStudent
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|new
name|Long
argument_list|(
name|s1
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|limit
argument_list|()
operator|!=
literal|null
operator|&&
name|ret
operator|.
name|size
argument_list|()
operator|>=
name|limit
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|StudentInfo
argument_list|>
name|r
init|=
operator|new
name|ArrayList
argument_list|<
name|StudentInfo
argument_list|>
argument_list|(
name|limit
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|StudentInfo
name|i
range|:
name|ret
control|)
block|{
name|r
operator|.
name|add
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|size
argument_list|()
operator|==
name|limit
argument_list|()
condition|)
break|break;
block|}
name|ret
operator|=
name|r
expr_stmt|;
block|}
comment|// if (students.size()> 0) {
name|StudentInfo
name|t
init|=
operator|new
name|StudentInfo
argument_list|()
decl_stmt|;
name|t
operator|.
name|setEnrollment
argument_list|(
name|gEnrl
argument_list|)
expr_stmt|;
name|t
operator|.
name|setReservation
argument_list|(
name|gRes
argument_list|)
expr_stmt|;
name|t
operator|.
name|setWaitlist
argument_list|(
name|gWait
argument_list|)
expr_stmt|;
name|t
operator|.
name|setUnassigned
argument_list|(
name|gUnasg
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalEnrollment
argument_list|(
name|gtEnrl
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalReservation
argument_list|(
name|gtRes
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalWaitlist
argument_list|(
name|gtWait
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalUnassigned
argument_list|(
name|gtUnasg
argument_list|)
expr_stmt|;
name|t
operator|.
name|setConsentNeeded
argument_list|(
name|gConNeed
argument_list|)
expr_stmt|;
name|t
operator|.
name|setTotalConsentNeeded
argument_list|(
name|gtConNeed
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|t
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
literal|"find-student-infos"
return|;
block|}
specifier|public
specifier|static
class|class
name|FindStudentInfoMatcher
extends|extends
name|AbstractStudentMatcher
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
name|Query
name|iQuery
decl_stmt|;
specifier|private
name|String
name|iDefaultSectioningStatus
decl_stmt|;
specifier|public
name|FindStudentInfoMatcher
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|Query
name|query
parameter_list|)
block|{
name|iQuery
operator|=
name|query
expr_stmt|;
name|iDefaultSectioningStatus
operator|=
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|match
parameter_list|(
name|XStudentId
name|id
parameter_list|)
block|{
name|XStudent
name|student
init|=
operator|(
name|id
operator|instanceof
name|XStudent
condition|?
operator|(
name|XStudent
operator|)
name|id
else|:
name|getServer
argument_list|()
operator|.
name|getStudent
argument_list|(
name|id
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
return|return
name|student
operator|!=
literal|null
operator|&&
name|student
operator|.
name|getRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
name|iQuery
operator|.
name|match
argument_list|(
operator|new
name|StudentMatcher
argument_list|(
name|student
argument_list|,
name|iDefaultSectioningStatus
argument_list|)
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

