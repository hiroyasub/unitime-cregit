begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|defaults
operator|.
name|ApplicationProperty
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
name|StudentSectioningConstants
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
name|CourseRequestInterface
operator|.
name|RequestedCourse
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
operator|.
name|RequestedCourseStatus
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
name|model
operator|.
name|CourseRequest
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
name|custom
operator|.
name|CustomCourseRequestsHolder
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
name|custom
operator|.
name|CustomCourseRequestsValidationHolder
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
name|solver
operator|.
name|studentsct
operator|.
name|StudentSolver
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GetRequest
implements|implements
name|OnlineSectioningAction
argument_list|<
name|CourseRequestInterface
argument_list|>
block|{
specifier|protected
specifier|static
name|StudentSectioningConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
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
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|boolean
name|iSectioning
decl_stmt|;
specifier|private
name|boolean
name|iCustomValidation
init|=
literal|false
decl_stmt|;
specifier|public
name|GetRequest
name|forStudent
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|boolean
name|sectioning
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
name|iSectioning
operator|=
name|sectioning
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|GetRequest
name|forStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
return|return
name|forStudent
argument_list|(
name|studentId
argument_list|,
literal|true
argument_list|)
return|;
block|}
specifier|public
name|GetRequest
name|withCustomValidation
parameter_list|(
name|boolean
name|validation
parameter_list|)
block|{
name|iCustomValidation
operator|=
name|validation
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|CourseRequestInterface
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
name|iStudentId
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|CustomCourseRequestsHolder
operator|.
name|hasProvider
argument_list|()
condition|)
block|{
name|CourseRequestInterface
name|request
init|=
name|CustomCourseRequestsHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|getCourseRequests
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
operator|new
name|XStudent
argument_list|(
literal|null
argument_list|,
name|helper
operator|.
name|getStudentExternalId
argument_list|()
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|!=
literal|null
condition|)
return|return
name|request
return|;
block|}
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNoStudent
argument_list|()
argument_list|)
throw|;
block|}
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
name|iStudentId
argument_list|)
argument_list|)
expr_stmt|;
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
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
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
operator|&&
name|CustomCourseRequestsHolder
operator|.
name|hasProvider
argument_list|()
condition|)
block|{
name|CourseRequestInterface
name|request
init|=
name|CustomCourseRequestsHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|getCourseRequests
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|!=
literal|null
condition|)
return|return
name|request
return|;
block|}
name|CourseRequestInterface
name|request
init|=
operator|new
name|CourseRequestInterface
argument_list|()
decl_stmt|;
name|request
operator|.
name|setStudentId
argument_list|(
name|iStudentId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setSaved
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAcademicSessionId
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setMaxCredit
argument_list|(
name|student
operator|.
name|getMaxCredit
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getMaxCreditOverride
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|request
operator|.
name|setMaxCreditOverride
argument_list|(
name|student
operator|.
name|getMaxCreditOverride
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setMaxCreditOverrideExternalId
argument_list|(
name|student
operator|.
name|getMaxCreditOverride
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setMaxCreditOverrideTimeStamp
argument_list|(
name|student
operator|.
name|getMaxCreditOverride
argument_list|()
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
name|Integer
name|status
init|=
name|student
operator|.
name|getMaxCreditOverride
argument_list|()
operator|.
name|getStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
condition|)
name|request
operator|.
name|setMaxCreditOverrideStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_PENDING
argument_list|)
expr_stmt|;
if|else if
condition|(
name|status
operator|==
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
operator|.
name|CourseRequestOverrideStatus
operator|.
name|APPROVED
operator|.
name|ordinal
argument_list|()
condition|)
name|request
operator|.
name|setMaxCreditOverrideStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_APPROVED
argument_list|)
expr_stmt|;
if|else if
condition|(
name|status
operator|==
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
operator|.
name|CourseRequestOverrideStatus
operator|.
name|REJECTED
operator|.
name|ordinal
argument_list|()
condition|)
name|request
operator|.
name|setMaxCreditOverrideStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_REJECTED
argument_list|)
expr_stmt|;
if|else if
condition|(
name|status
operator|==
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
operator|.
name|CourseRequestOverrideStatus
operator|.
name|CANCELLED
operator|.
name|ordinal
argument_list|()
condition|)
name|request
operator|.
name|setMaxCreditOverrideStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_CANCELLED
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|setMaxCreditOverrideStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_PENDING
argument_list|)
expr_stmt|;
block|}
name|CourseRequestInterface
operator|.
name|Request
name|lastRequest
init|=
literal|null
decl_stmt|;
name|int
name|lastRequestPriority
init|=
operator|-
literal|1
decl_stmt|;
name|boolean
name|setReadOnly
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingMakeAssignedRequestReadOnly
operator|.
name|isTrue
argument_list|()
decl_stmt|;
name|boolean
name|setReadOnlyWhenReserved
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingMakeReservedRequestReadOnly
operator|.
name|isTrue
argument_list|()
decl_stmt|;
name|boolean
name|setInactive
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingMakeUnassignedRequestsInactive
operator|.
name|isTrue
argument_list|()
decl_stmt|;
if|if
condition|(
name|helper
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getType
argument_list|()
operator|==
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|MANAGER
condition|)
block|{
name|setReadOnly
operator|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingMakeAssignedRequestReadOnlyIfAdmin
operator|.
name|isTrue
argument_list|()
expr_stmt|;
name|setReadOnlyWhenReserved
operator|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingMakeReservedRequestReadOnlyIfAdmin
operator|.
name|isTrue
argument_list|()
expr_stmt|;
name|setInactive
operator|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingMakeUnassignedRequestsInactiveIfAdmin
operator|.
name|isTrue
argument_list|()
expr_stmt|;
block|}
name|boolean
name|reservedNoPriority
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingReservedRequestNoPriorityChanges
operator|.
name|isTrue
argument_list|()
decl_stmt|;
name|boolean
name|reservedNoAlternatives
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingReservedRequestNoAlternativeChanges
operator|.
name|isTrue
argument_list|()
decl_stmt|;
name|boolean
name|enrolledNoPriority
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingAssignedRequestNoPriorityChanges
operator|.
name|isTrue
argument_list|()
decl_stmt|;
name|boolean
name|enrolledNoAlternatives
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingAssignedRequestNoAlternativeChanges
operator|.
name|isTrue
argument_list|()
decl_stmt|;
if|if
condition|(
name|setInactive
condition|)
block|{
name|boolean
name|hasEnrollments
init|=
literal|false
decl_stmt|;
for|for
control|(
name|XRequest
name|cd
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|cd
operator|instanceof
name|XCourseRequest
operator|&&
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|hasEnrollments
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|hasEnrollments
condition|)
name|setInactive
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|setInactive
operator|&&
name|server
operator|instanceof
name|StudentSolver
condition|)
name|setInactive
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|XRequest
name|cd
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|CourseRequestInterface
operator|.
name|Request
name|r
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|cd
operator|instanceof
name|XFreeTimeRequest
condition|)
block|{
name|XFreeTimeRequest
name|ftr
init|=
operator|(
name|XFreeTimeRequest
operator|)
name|cd
decl_stmt|;
name|CourseRequestInterface
operator|.
name|FreeTime
name|ft
init|=
operator|new
name|CourseRequestInterface
operator|.
name|FreeTime
argument_list|()
decl_stmt|;
name|ft
operator|.
name|setStart
argument_list|(
name|ftr
operator|.
name|getTime
argument_list|()
operator|.
name|getSlot
argument_list|()
argument_list|)
expr_stmt|;
name|ft
operator|.
name|setLength
argument_list|(
name|ftr
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|DayCode
name|day
range|:
name|DayCode
operator|.
name|toDayCodes
argument_list|(
name|ftr
operator|.
name|getTime
argument_list|()
operator|.
name|getDays
argument_list|()
argument_list|)
control|)
name|ft
operator|.
name|addDay
argument_list|(
name|day
operator|.
name|getIndex
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|lastRequest
operator|!=
literal|null
operator|&&
name|lastRequestPriority
operator|==
name|cd
operator|.
name|getPriority
argument_list|()
operator|&&
name|lastRequest
operator|.
name|hasRequestedCourse
argument_list|()
operator|&&
name|lastRequest
operator|.
name|getRequestedCourse
argument_list|(
literal|0
argument_list|)
operator|.
name|isFreeTime
argument_list|()
condition|)
block|{
name|lastRequest
operator|.
name|getRequestedCourse
argument_list|(
literal|0
argument_list|)
operator|.
name|addFreeTime
argument_list|(
name|ft
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|r
operator|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
expr_stmt|;
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|r
operator|.
name|addRequestedCourse
argument_list|(
name|rc
argument_list|)
expr_stmt|;
name|rc
operator|.
name|addFreeTime
argument_list|(
name|ft
argument_list|)
expr_stmt|;
if|if
condition|(
name|cd
operator|.
name|isAlternative
argument_list|()
condition|)
name|request
operator|.
name|getAlternatives
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|lastRequest
operator|=
name|r
expr_stmt|;
name|lastRequestPriority
operator|=
name|cd
operator|.
name|getPriority
argument_list|()
expr_stmt|;
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|SAVED
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|cd
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|r
operator|=
operator|new
name|CourseRequestInterface
operator|.
name|Request
argument_list|()
expr_stmt|;
for|for
control|(
name|XCourseId
name|courseId
range|:
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|getCourseIds
argument_list|()
control|)
block|{
name|XCourse
name|c
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|courseId
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|null
condition|)
continue|continue;
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|rc
operator|.
name|setCourseId
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCourseName
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
operator|+
literal|" "
operator|+
name|c
operator|.
name|getCourseNumber
argument_list|()
operator|+
operator|(
name|c
operator|.
name|hasUniqueName
argument_list|()
operator|&&
operator|!
name|CONSTANTS
operator|.
name|showCourseTitle
argument_list|()
condition|?
literal|""
else|:
literal|" - "
operator|+
name|c
operator|.
name|getTitle
argument_list|()
operator|)
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCourseTitle
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCredit
argument_list|(
name|c
operator|.
name|getMinCredit
argument_list|()
argument_list|,
name|c
operator|.
name|getMaxCredit
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|isEnrolled
init|=
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
operator|&&
name|c
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
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
name|setReadOnly
operator|&&
name|isEnrolled
condition|)
name|rc
operator|.
name|setReadOnly
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSectioning
operator|&&
name|setInactive
operator|&&
operator|!
name|isEnrolled
condition|)
name|rc
operator|.
name|setInactive
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|iSectioning
operator|&&
name|isEnrolled
condition|)
block|{
name|rc
operator|.
name|setReadOnly
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCanDelete
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|enrolledNoAlternatives
condition|)
name|rc
operator|.
name|setCanChangeAlternatives
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|enrolledNoPriority
condition|)
name|rc
operator|.
name|setCanChangePriority
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|iSectioning
operator|&&
name|setReadOnlyWhenReserved
condition|)
block|{
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|!=
literal|null
operator|&&
operator|(
name|offering
operator|.
name|hasIndividualReservation
argument_list|(
name|student
argument_list|,
name|c
argument_list|)
operator|||
name|offering
operator|.
name|hasGroupReservation
argument_list|(
name|student
argument_list|,
name|c
argument_list|)
operator|)
condition|)
block|{
name|rc
operator|.
name|setReadOnly
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setCanDelete
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservedNoAlternatives
condition|)
name|rc
operator|.
name|setCanChangeAlternatives
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservedNoPriority
condition|)
name|rc
operator|.
name|setCanChangePriority
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|isEnrolled
condition|)
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|ENROLLED
argument_list|)
expr_stmt|;
else|else
block|{
name|Integer
name|status
init|=
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|getOverrideStatus
argument_list|(
name|courseId
argument_list|)
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
condition|)
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|SAVED
argument_list|)
expr_stmt|;
if|else if
condition|(
name|status
operator|==
name|CourseRequest
operator|.
name|CourseRequestOverrideStatus
operator|.
name|APPROVED
operator|.
name|ordinal
argument_list|()
condition|)
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_APPROVED
argument_list|)
expr_stmt|;
if|else if
condition|(
name|status
operator|==
name|CourseRequest
operator|.
name|CourseRequestOverrideStatus
operator|.
name|REJECTED
operator|.
name|ordinal
argument_list|()
condition|)
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_REJECTED
argument_list|)
expr_stmt|;
if|else if
condition|(
name|status
operator|==
name|CourseRequest
operator|.
name|CourseRequestOverrideStatus
operator|.
name|CANCELLED
operator|.
name|ordinal
argument_list|()
condition|)
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_CANCELLED
argument_list|)
expr_stmt|;
else|else
name|rc
operator|.
name|setStatus
argument_list|(
name|RequestedCourseStatus
operator|.
name|OVERRIDE_PENDING
argument_list|)
expr_stmt|;
block|}
name|rc
operator|.
name|setOverrideExternalId
argument_list|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|getOverrideExternalId
argument_list|(
name|courseId
argument_list|)
argument_list|)
expr_stmt|;
name|rc
operator|.
name|setOverrideTimeStamp
argument_list|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|getOverrideTimeStamp
argument_list|(
name|courseId
argument_list|)
argument_list|)
expr_stmt|;
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|fillPreferencesIn
argument_list|(
name|rc
argument_list|,
name|courseId
argument_list|)
expr_stmt|;
name|r
operator|.
name|addRequestedCourse
argument_list|(
name|rc
argument_list|)
expr_stmt|;
block|}
name|r
operator|.
name|setWaitList
argument_list|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|isWaitlist
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCritical
argument_list|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|isCritical
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setTimeStamp
argument_list|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|cd
operator|)
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
if|if
condition|(
name|cd
operator|.
name|isAlternative
argument_list|()
condition|)
name|request
operator|.
name|getAlternatives
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
name|lastRequest
operator|=
name|r
expr_stmt|;
name|lastRequestPriority
operator|=
name|cd
operator|.
name|getPriority
argument_list|()
expr_stmt|;
block|}
name|action
operator|.
name|addRequest
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|cd
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iCustomValidation
operator|&&
name|CustomCourseRequestsValidationHolder
operator|.
name|hasProvider
argument_list|()
condition|)
name|CustomCourseRequestsValidationHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|check
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|)
expr_stmt|;
return|return
name|request
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
literal|"get-request"
return|;
block|}
block|}
end_class

end_unit

