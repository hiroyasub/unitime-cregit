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
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|online
operator|.
name|expectations
operator|.
name|OverExpectedCriterion
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
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
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
name|FixedCreditUnitConfig
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
name|XConfig
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
name|XReservation
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
name|ListClasses
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
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
name|String
name|iCourse
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|public
name|ListClasses
name|forCourseAndStudent
parameter_list|(
name|String
name|course
parameter_list|,
name|Long
name|studentId
parameter_list|)
block|{
name|iCourse
operator|=
name|course
expr_stmt|;
name|iStudentId
operator|=
name|studentId
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|String
name|getCourse
parameter_list|()
block|{
return|return
name|iCourse
return|;
block|}
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
specifier|protected
name|boolean
name|isAllowDisabled
parameter_list|(
name|XEnrollments
name|enrollments
parameter_list|,
name|XStudent
name|student
parameter_list|,
name|XOffering
name|offering
parameter_list|,
name|XCourseId
name|course
parameter_list|,
name|XConfig
name|config
parameter_list|,
name|XSection
name|section
parameter_list|)
block|{
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|student
operator|.
name|isAllowDisabled
argument_list|()
condition|)
return|return
literal|true
return|;
for|for
control|(
name|XReservation
name|reservation
range|:
name|offering
operator|.
name|getReservations
argument_list|()
control|)
if|if
condition|(
name|reservation
operator|.
name|isAllowDisabled
argument_list|()
operator|&&
name|reservation
operator|.
name|isApplicable
argument_list|(
name|student
argument_list|,
name|course
argument_list|)
operator|&&
name|reservation
operator|.
name|isIncluded
argument_list|(
name|offering
argument_list|,
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|,
name|section
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
for|for
control|(
name|XEnrollment
name|enrollment
range|:
name|enrollments
operator|.
name|getEnrollmentsForSection
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
control|)
if|if
condition|(
name|enrollment
operator|.
name|getStudentId
argument_list|()
operator|.
name|equals
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|protected
name|boolean
name|isAvailable
parameter_list|(
name|XEnrollments
name|enrollments
parameter_list|,
name|XStudent
name|student
parameter_list|,
name|XOffering
name|offering
parameter_list|,
name|XCourse
name|course
parameter_list|,
name|XConfig
name|config
parameter_list|,
name|XSection
name|section
parameter_list|)
block|{
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|true
return|;
name|boolean
name|hasMustBeUsed
init|=
literal|false
decl_stmt|;
name|boolean
name|hasReservation
init|=
literal|false
decl_stmt|;
name|boolean
name|canOverLimit
init|=
literal|false
decl_stmt|;
for|for
control|(
name|XReservation
name|r
range|:
name|offering
operator|.
name|getReservations
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|r
operator|.
name|isApplicable
argument_list|(
name|student
argument_list|,
name|course
argument_list|)
condition|)
continue|continue;
comment|// reservation does not apply to this student
name|boolean
name|mustBeUsed
init|=
operator|(
name|r
operator|.
name|mustBeUsed
argument_list|()
operator|&&
operator|(
name|r
operator|.
name|isAlwaysExpired
argument_list|()
operator|||
operator|!
name|r
operator|.
name|isExpired
argument_list|()
operator|)
operator|)
decl_stmt|;
if|if
condition|(
name|mustBeUsed
operator|&&
operator|!
name|hasMustBeUsed
condition|)
block|{
name|hasReservation
operator|=
literal|false
expr_stmt|;
name|hasMustBeUsed
operator|=
literal|true
expr_stmt|;
name|canOverLimit
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|hasMustBeUsed
operator|&&
operator|!
name|mustBeUsed
condition|)
continue|continue;
comment|// student must use a reservation, but this one is not it
if|if
condition|(
name|r
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
operator|&&
name|r
operator|.
name|getLimit
argument_list|()
operator|<=
name|enrollments
operator|.
name|countEnrollmentsForReservation
argument_list|(
name|r
operator|.
name|getReservationId
argument_list|()
argument_list|)
condition|)
continue|continue;
comment|// reservation is full
if|if
condition|(
name|r
operator|.
name|isIncluded
argument_list|(
name|offering
argument_list|,
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|,
name|section
argument_list|)
condition|)
block|{
name|hasReservation
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|canAssignOverLimit
argument_list|()
condition|)
name|canOverLimit
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|canOverLimit
condition|)
block|{
if|if
condition|(
name|section
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
operator|&&
name|enrollments
operator|.
name|countEnrollmentsForSection
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
operator|>=
name|section
operator|.
name|getLimit
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|config
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
operator|&&
name|enrollments
operator|.
name|countEnrollmentsForConfig
argument_list|(
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|)
operator|>=
name|config
operator|.
name|getLimit
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|course
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
operator|&&
name|enrollments
operator|.
name|countEnrollmentsForCourse
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|>=
name|course
operator|.
name|getLimit
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
if|if
condition|(
name|hasReservation
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|hasMustBeUsed
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|offering
operator|.
name|getUnreservedSpace
argument_list|(
name|enrollments
argument_list|)
operator|<=
literal|0
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|offering
operator|.
name|getUnreservedConfigSpace
argument_list|(
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|,
name|enrollments
argument_list|)
operator|<=
literal|0
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|offering
operator|.
name|getUnreservedSectionSpace
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|,
name|enrollments
argument_list|)
operator|<=
literal|0
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|ClassAssignment
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
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|()
decl_stmt|;
name|Lock
name|lock
init|=
name|server
operator|.
name|readLock
argument_list|()
decl_stmt|;
name|boolean
name|checkAvailability
init|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"ListClasses.CheckClasAvailability"
argument_list|,
literal|true
argument_list|)
decl_stmt|;
try|try
block|{
name|XCourseId
name|id
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|getCourse
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionCourseDoesNotExist
argument_list|(
name|getCourse
argument_list|()
argument_list|)
argument_list|)
throw|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|id
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|XCourse
name|c
init|=
name|offering
operator|.
name|getCourse
argument_list|(
name|id
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
name|c
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
name|c
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|OverExpectedCriterion
name|overExp
init|=
name|server
operator|.
name|getOverExpectedCriterion
argument_list|()
decl_stmt|;
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
name|courseAssign
init|=
operator|new
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|()
decl_stmt|;
name|courseAssign
operator|.
name|setCourseId
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
name|courseAssign
operator|.
name|setCourseNbr
argument_list|(
name|c
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
expr_stmt|;
name|courseAssign
operator|.
name|setTitle
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|courseAssign
operator|.
name|setSubject
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|courseAssign
operator|.
name|setHasCrossList
argument_list|(
name|offering
operator|.
name|hasCrossList
argument_list|()
argument_list|)
expr_stmt|;
name|courseAssign
operator|.
name|setCanWaitList
argument_list|(
name|offering
operator|.
name|isWaitList
argument_list|()
argument_list|)
expr_stmt|;
name|XStudent
name|student
init|=
operator|(
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
name|getStudentId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|String
name|filter
init|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Filter.OnlineOnlyStudentFilter"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|String
name|imFilter
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|filter
operator|!=
literal|null
operator|&&
operator|!
name|filter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|new
name|Query
argument_list|(
name|filter
argument_list|)
operator|.
name|match
argument_list|(
operator|new
name|StudentMatcher
argument_list|(
name|student
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
argument_list|,
name|server
argument_list|,
literal|false
argument_list|)
argument_list|)
condition|)
block|{
name|imFilter
operator|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Filter.OnlineOnlyInstructionalModeRegExp"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Filter.OnlineOnlyExclusiveCourses"
argument_list|,
literal|false
argument_list|)
condition|)
block|{
name|imFilter
operator|=
name|server
operator|.
name|getConfig
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Filter.ResidentialInstructionalModeRegExp"
argument_list|)
expr_stmt|;
block|}
block|}
name|XEnrollment
name|enrollment
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|XCourseRequest
name|r
init|=
name|student
operator|.
name|getRequestForCourse
argument_list|(
name|id
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
name|enrollment
operator|=
operator|(
name|r
operator|==
literal|null
condition|?
literal|null
else|:
name|r
operator|.
name|getEnrollment
argument_list|()
operator|)
expr_stmt|;
block|}
for|for
control|(
name|XConfig
name|config
range|:
name|offering
operator|.
name|getConfigs
argument_list|()
control|)
block|{
if|if
condition|(
name|imFilter
operator|!=
literal|null
operator|&&
operator|(
name|enrollment
operator|==
literal|null
operator|||
operator|!
name|config
operator|.
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
name|enrollment
operator|.
name|getConfigId
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|String
name|imRef
init|=
operator|(
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getReference
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|imFilter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|imRef
operator|!=
literal|null
operator|&&
operator|!
name|imRef
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
block|}
else|else
block|{
if|if
condition|(
name|imRef
operator|==
literal|null
operator|||
operator|!
name|imRef
operator|.
name|matches
argument_list|(
name|imFilter
argument_list|)
condition|)
continue|continue;
block|}
block|}
for|for
control|(
name|XSubpart
name|subpart
range|:
name|config
operator|.
name|getSubparts
argument_list|()
control|)
for|for
control|(
name|XSection
name|section
range|:
name|subpart
operator|.
name|getSections
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|section
operator|.
name|isEnabledForScheduling
argument_list|()
operator|&&
operator|!
name|isAllowDisabled
argument_list|(
name|enrollments
argument_list|,
name|student
argument_list|,
name|offering
argument_list|,
name|id
argument_list|,
name|config
argument_list|,
name|section
argument_list|)
condition|)
continue|continue;
name|String
name|room
init|=
literal|null
decl_stmt|;
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
name|rm
range|:
name|section
operator|.
name|getRooms
argument_list|()
control|)
block|{
if|if
condition|(
name|room
operator|==
literal|null
condition|)
name|room
operator|=
literal|""
expr_stmt|;
else|else
name|room
operator|+=
literal|", "
expr_stmt|;
name|room
operator|+=
name|rm
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
block|}
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|a
init|=
name|courseAssign
operator|.
name|addClassAssignment
argument_list|()
decl_stmt|;
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
name|setSection
argument_list|(
name|section
operator|.
name|getName
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setExternalId
argument_list|(
name|section
operator|.
name|getExternalId
argument_list|(
name|c
operator|.
name|getCourseId
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
name|setCancelled
argument_list|(
name|section
operator|.
name|isCancelled
argument_list|()
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
name|a
operator|.
name|setSaved
argument_list|(
name|enrollment
operator|!=
literal|null
operator|&&
name|enrollment
operator|.
name|getSectionIds
argument_list|()
operator|.
name|contains
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|a
operator|.
name|isSaved
argument_list|()
operator|&&
name|checkAvailability
condition|)
name|a
operator|.
name|setAvailable
argument_list|(
name|isAvailable
argument_list|(
name|enrollments
argument_list|,
name|student
argument_list|,
name|offering
argument_list|,
name|c
argument_list|,
name|config
argument_list|,
name|section
argument_list|)
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
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|a
operator|.
name|setCreditRange
argument_list|(
name|subpart
operator|.
name|getCreditMin
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|,
name|subpart
operator|.
name|getCreditMax
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Float
name|creditOverride
init|=
name|section
operator|.
name|getCreditOverride
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|creditOverride
operator|!=
literal|null
condition|)
name|a
operator|.
name|setCredit
argument_list|(
name|FixedCreditUnitConfig
operator|.
name|formatCredit
argument_list|(
name|creditOverride
argument_list|)
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
name|rm
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
name|rm
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|rm
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
name|c
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
name|subpart
operator|.
name|getSubpartId
argument_list|()
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
name|c
operator|.
name|getConsentLabel
argument_list|()
argument_list|)
expr_stmt|;
name|a
operator|.
name|setExpected
argument_list|(
name|overExp
operator|.
name|getExpected
argument_list|(
name|section
operator|.
name|getLimit
argument_list|()
argument_list|,
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
name|ret
operator|.
name|add
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNoClassesForCourse
argument_list|(
name|getCourse
argument_list|()
argument_list|)
argument_list|)
throw|;
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
literal|"list-classes"
return|;
block|}
block|}
end_class

end_unit

