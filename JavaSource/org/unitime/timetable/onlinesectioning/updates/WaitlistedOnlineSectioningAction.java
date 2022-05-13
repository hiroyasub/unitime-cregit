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
name|updates
package|;
end_package

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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
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
name|model
operator|.
name|Student
operator|.
name|StudentPriority
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
name|OnlineSectioningInterface
operator|.
name|WaitListMode
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
name|StudentSectioningStatus
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
name|custom
operator|.
name|Customization
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
name|WaitListComparatorProvider
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
name|WaitListValidationProvider
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
name|XOverride
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
name|XStudent
operator|.
name|XGroup
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
name|onlinesectioning
operator|.
name|solver
operator|.
name|SectioningRequestComparator
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
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|WaitlistedOnlineSectioningAction
parameter_list|<
name|T
parameter_list|>
implements|implements
name|OnlineSectioningAction
argument_list|<
name|T
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
name|Set
argument_list|<
name|String
argument_list|>
name|iWaitlistStatuses
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|StudentPriority
argument_list|,
name|String
argument_list|>
name|iPriorityStudentGroupReference
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|StudentPriority
argument_list|,
name|Query
argument_list|>
name|iPriorityStudentQuery
init|=
literal|null
decl_stmt|;
specifier|public
name|boolean
name|hasWaitListingStatus
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|)
block|{
comment|// Check student status
name|String
name|status
init|=
name|student
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
name|status
operator|=
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
expr_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iWaitlistStatuses
operator|==
literal|null
condition|)
name|iWaitlistStatuses
operator|=
name|StudentSectioningStatus
operator|.
name|getMatchingStatuses
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|waitlist
argument_list|,
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|enrollment
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|iWaitlistStatuses
operator|.
name|contains
argument_list|(
name|status
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|isWaitListed
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|XCourseRequest
name|request
parameter_list|,
name|XOffering
name|offering
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
comment|// Check wait-list toggle first
if|if
condition|(
name|student
operator|==
literal|null
operator|||
name|request
operator|==
literal|null
operator|||
operator|!
name|request
operator|.
name|isWaitlist
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// If already enrolled
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
comment|// Check if a section swap
if|if
condition|(
operator|!
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getWaitListSwapWithCourseOffering
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Lower choice than the enrolled course
if|if
condition|(
name|request
operator|.
name|getIndex
argument_list|(
name|offering
argument_list|)
operator|>
name|request
operator|.
name|getEnrolledCourseIndex
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// Requirements are already met
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
operator|&&
name|request
operator|.
name|isRequired
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
argument_list|,
name|offering
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
comment|// Check if student can assign
if|if
condition|(
operator|!
name|student
operator|.
name|canAssign
argument_list|(
name|request
argument_list|,
name|WaitListMode
operator|.
name|WaitList
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Check student status
if|if
condition|(
operator|!
name|hasWaitListingStatus
argument_list|(
name|student
argument_list|,
name|server
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Check recent failed wait-lists
if|if
condition|(
name|student
operator|.
name|isFailedWaitlist
argument_list|(
name|request
operator|.
name|getCourseIdByOfferingId
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// 5 minutes
comment|// Check wait-list overrides, when configured
if|if
condition|(
name|Customization
operator|.
name|WaitListValidationProvider
operator|.
name|hasProvider
argument_list|()
condition|)
block|{
comment|// Student has a max credit override>> check credit
name|Float
name|credit
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|student
operator|.
name|getMaxCreditOverride
argument_list|()
operator|!=
literal|null
operator|&&
name|student
operator|.
name|getMaxCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|credit
operator|=
literal|0f
expr_stmt|;
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
operator|&&
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
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
comment|// skip drop/swap course
if|if
condition|(
name|request
operator|.
name|getWaitListSwapWithCourseOffering
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|request
operator|.
name|getWaitListSwapWithCourseOffering
argument_list|()
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
name|credit
operator|+=
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCredit
argument_list|(
name|server
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|XCourse
name|course
range|:
name|offering
operator|.
name|getCourses
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
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|XOverride
name|override
init|=
name|request
operator|.
name|getOverride
argument_list|(
name|course
argument_list|)
decl_stmt|;
if|if
condition|(
name|override
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
literal|"TBD"
operator|.
name|equals
argument_list|(
name|override
operator|.
name|getExternalId
argument_list|()
argument_list|)
condition|)
continue|continue;
comment|// override not requested --> ignore
name|WaitListValidationProvider
name|wp
init|=
name|Customization
operator|.
name|WaitListValidationProvider
operator|.
name|getProvider
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|wp
operator|.
name|updateStudent
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|helper
operator|.
name|getAction
argument_list|()
argument_list|)
condition|)
name|override
operator|=
name|request
operator|.
name|getOverride
argument_list|(
name|course
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Failed to check wait-list status for student "
operator|+
name|student
operator|.
name|getExternalId
argument_list|()
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|credit
operator|!=
literal|null
operator|&&
name|course
operator|.
name|hasCredit
argument_list|()
operator|&&
name|credit
operator|+
name|course
operator|.
name|getMinCredit
argument_list|()
operator|>
name|student
operator|.
name|getMaxCredit
argument_list|()
condition|)
block|{
name|WaitListValidationProvider
name|wp
init|=
name|Customization
operator|.
name|WaitListValidationProvider
operator|.
name|getProvider
argument_list|()
decl_stmt|;
try|try
block|{
name|wp
operator|.
name|updateStudent
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|helper
operator|.
name|getAction
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Failed to check wait-list status for student "
operator|+
name|student
operator|.
name|getExternalId
argument_list|()
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// override for this course is not approved
if|if
condition|(
name|override
operator|!=
literal|null
operator|&&
operator|!
name|override
operator|.
name|isApproved
argument_list|()
condition|)
continue|continue;
if|if
condition|(
name|credit
operator|!=
literal|null
operator|&&
name|course
operator|.
name|hasCredit
argument_list|()
operator|&&
name|credit
operator|+
name|course
operator|.
name|getMinCredit
argument_list|()
operator|>
name|student
operator|.
name|getMaxCredit
argument_list|()
condition|)
continue|continue;
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|isWaitListedAssumeApproved
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|XCourseRequest
name|request
parameter_list|,
name|XOffering
name|offering
parameter_list|,
name|XCourseId
name|courseId
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
comment|// No student
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|false
return|;
comment|// Check wait-list toggle first
if|if
condition|(
name|request
operator|==
literal|null
operator|||
operator|!
name|request
operator|.
name|isWaitlist
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// Check that the offering can be wait-listed
if|if
condition|(
operator|!
name|offering
operator|.
name|isWaitList
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// No matching course
if|if
condition|(
name|courseId
operator|==
literal|null
condition|)
return|return
literal|false
return|;
comment|// If already enrolled
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
comment|// Check if a section swap
if|if
condition|(
operator|!
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getWaitListSwapWithCourseOffering
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Lower choice than the enrolled course
if|if
condition|(
name|request
operator|.
name|getIndex
argument_list|(
name|offering
argument_list|)
operator|>
name|request
operator|.
name|getEnrolledCourseIndex
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// Requirements are already met
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
operator|&&
name|request
operator|.
name|isRequired
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
argument_list|,
name|offering
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
comment|// Check student status
if|if
condition|(
operator|!
name|hasWaitListingStatus
argument_list|(
name|student
argument_list|,
name|server
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|public
name|StudentPriority
name|getStudentPriority
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
name|iPriorityStudentGroupReference
operator|==
literal|null
condition|)
block|{
name|iPriorityStudentGroupReference
operator|=
operator|new
name|HashMap
argument_list|<
name|StudentPriority
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iPriorityStudentQuery
operator|=
operator|new
name|HashMap
argument_list|<
name|StudentPriority
argument_list|,
name|Query
argument_list|>
argument_list|()
expr_stmt|;
name|DataProperties
name|config
init|=
name|server
operator|.
name|getConfig
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentPriority
name|priority
range|:
name|StudentPriority
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|priority
operator|==
name|StudentPriority
operator|.
name|Normal
condition|)
break|break;
name|String
name|priorityStudentFilter
init|=
name|config
operator|.
name|getProperty
argument_list|(
literal|"Load."
operator|+
name|priority
operator|.
name|name
argument_list|()
operator|+
literal|"StudentFilter"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|priorityStudentFilter
operator|!=
literal|null
operator|&&
operator|!
name|priorityStudentFilter
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Query
name|q
init|=
operator|new
name|Query
argument_list|(
name|priorityStudentFilter
argument_list|)
decl_stmt|;
name|iPriorityStudentQuery
operator|.
name|put
argument_list|(
name|priority
argument_list|,
name|q
argument_list|)
expr_stmt|;
block|}
name|String
name|groupRef
init|=
name|config
operator|.
name|getProperty
argument_list|(
literal|"Load."
operator|+
name|priority
operator|.
name|name
argument_list|()
operator|+
literal|"StudentGroupReference"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|groupRef
operator|!=
literal|null
operator|&&
operator|!
name|groupRef
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iPriorityStudentGroupReference
operator|.
name|put
argument_list|(
name|priority
argument_list|,
name|groupRef
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|StudentPriority
name|priority
range|:
name|StudentPriority
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|priority
operator|==
name|StudentPriority
operator|.
name|Normal
condition|)
break|break;
name|Query
name|query
init|=
name|iPriorityStudentQuery
operator|.
name|get
argument_list|(
name|priority
argument_list|)
decl_stmt|;
name|String
name|groupRef
init|=
name|iPriorityStudentGroupReference
operator|.
name|get
argument_list|(
name|priority
argument_list|)
decl_stmt|;
if|if
condition|(
name|query
operator|!=
literal|null
operator|&&
name|query
operator|.
name|match
argument_list|(
operator|new
name|StatusPageSuggestionsAction
operator|.
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
return|return
name|priority
return|;
block|}
if|else if
condition|(
name|groupRef
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XGroup
name|g
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
block|{
if|if
condition|(
name|groupRef
operator|.
name|equals
argument_list|(
name|g
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|priority
return|;
block|}
block|}
block|}
block|}
return|return
name|StudentPriority
operator|.
name|Normal
return|;
block|}
specifier|public
name|String
name|getWaitListPosition
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|XStudent
name|student
parameter_list|,
name|XCourseRequest
name|request
parameter_list|,
name|XCourseId
name|courseId
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isWaitListedAssumeApproved
argument_list|(
name|student
argument_list|,
name|request
argument_list|,
name|offering
argument_list|,
name|courseId
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
condition|)
return|return
literal|null
return|;
if|if
condition|(
operator|!
name|courseId
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getCourseIdByOfferingId
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
argument_list|)
condition|)
return|return
literal|null
return|;
name|XEnrollments
name|enrl
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
if|if
condition|(
name|enrl
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|WaitListComparatorProvider
name|cmpProvider
init|=
name|Customization
operator|.
name|WaitListComparatorProvider
operator|.
name|getProvider
argument_list|()
decl_stmt|;
name|Comparator
argument_list|<
name|SectioningRequest
argument_list|>
name|cmp
init|=
operator|(
name|cmpProvider
operator|==
literal|null
condition|?
operator|new
name|SectioningRequestComparator
argument_list|()
else|:
name|cmpProvider
operator|.
name|getComparator
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|)
decl_stmt|;
name|SectioningRequest
name|sr
init|=
operator|new
name|SectioningRequest
argument_list|(
name|offering
argument_list|,
name|request
argument_list|,
name|courseId
argument_list|,
name|student
argument_list|,
literal|false
argument_list|,
name|getStudentPriority
argument_list|(
name|student
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|int
name|before
init|=
literal|0
decl_stmt|,
name|total
init|=
literal|0
decl_stmt|;
for|for
control|(
name|XCourseRequest
name|cr
range|:
name|enrl
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|cr
operator|.
name|isWaitlist
argument_list|()
condition|)
continue|continue;
comment|// skip not wait-listed
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
comment|// skip enrolled
comment|// Check if a section swap
if|if
condition|(
operator|!
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|equals
argument_list|(
name|cr
operator|.
name|getWaitListSwapWithCourseOffering
argument_list|()
argument_list|)
condition|)
continue|continue;
comment|// Lower choice than the enrolled course
if|if
condition|(
name|cr
operator|.
name|getIndex
argument_list|(
name|offering
argument_list|)
operator|>
name|cr
operator|.
name|getEnrolledCourseIndex
argument_list|()
condition|)
continue|continue;
comment|// Requirements are already met
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
operator|&&
name|cr
operator|.
name|isRequired
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
argument_list|,
name|offering
argument_list|)
condition|)
continue|continue;
block|}
name|XStudent
name|s
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|cr
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
name|XCourseId
name|c
init|=
name|cr
operator|.
name|getCourseIdByOfferingId
argument_list|(
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|isWaitListedAssumeApproved
argument_list|(
name|s
argument_list|,
name|cr
argument_list|,
name|offering
argument_list|,
name|c
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
condition|)
continue|continue;
comment|// skip not wait-listed
name|total
operator|++
expr_stmt|;
if|if
condition|(
operator|!
name|cr
operator|.
name|equals
argument_list|(
name|request
argument_list|)
condition|)
block|{
name|SectioningRequest
name|other
init|=
operator|new
name|SectioningRequest
argument_list|(
name|offering
argument_list|,
name|cr
argument_list|,
name|c
argument_list|,
name|s
argument_list|,
literal|false
argument_list|,
name|getStudentPriority
argument_list|(
name|s
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|.
name|compare
argument_list|(
name|other
argument_list|,
name|sr
argument_list|)
operator|<
literal|0
condition|)
name|before
operator|++
expr_stmt|;
block|}
block|}
return|return
name|MSG
operator|.
name|waitListPosition
argument_list|(
name|before
operator|+
literal|1
argument_list|,
name|total
argument_list|)
return|;
block|}
block|}
end_class

end_unit

