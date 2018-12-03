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
name|custom
operator|.
name|purdue
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
name|HashMap
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
name|org
operator|.
name|joda
operator|.
name|time
operator|.
name|DateTime
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SpecialRegistrationInterface
block|{
specifier|public
specifier|static
class|class
name|SpecialRegistrationRequest
block|{
specifier|public
name|String
name|requestId
decl_stmt|;
specifier|public
name|String
name|studentId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|campus
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|mode
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Change
argument_list|>
name|changes
decl_stmt|;
specifier|public
name|DateTime
name|dateCreated
decl_stmt|;
specifier|public
name|Float
name|maxCredit
decl_stmt|;
specifier|public
name|String
name|requestorId
decl_stmt|;
specifier|public
name|String
name|requestorRole
decl_stmt|;
specifier|public
name|List
argument_list|<
name|CourseCredit
argument_list|>
name|courseCreditHrs
decl_stmt|;
specifier|public
name|List
argument_list|<
name|CourseCredit
argument_list|>
name|alternateCourseCreditHrs
decl_stmt|;
specifier|public
name|String
name|notes
decl_stmt|;
specifier|public
name|String
name|requestorNotes
decl_stmt|;
specifier|public
name|String
name|completionStatus
decl_stmt|;
specifier|public
name|List
argument_list|<
name|CancelledRequest
argument_list|>
name|cancelledRequests
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationEligibility
block|{
specifier|public
name|String
name|studentId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|campus
decl_stmt|;
specifier|public
name|Boolean
name|eligible
decl_stmt|;
specifier|public
name|List
argument_list|<
name|EligibilityProblem
argument_list|>
name|eligibilityProblems
decl_stmt|;
block|}
specifier|public
specifier|static
enum|enum
name|RequestStatus
block|{
name|mayEdit
block|,
name|mayNotEdit
block|,
name|maySubmit
block|,
name|newRequest
block|,
name|draft
block|,
name|inProgress
block|,
name|approved
block|,
name|denied
block|,
name|cancelled
block|,
name|completed
block|,
name|deferred
block|,
name|escalated
block|, 		 ; 	}
specifier|public
specifier|static
class|class
name|SpecialRegistrationResponse
block|{
specifier|public
name|SpecialRegistrationRequest
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationResponseList
block|{
specifier|public
name|List
argument_list|<
name|SpecialRegistrationRequest
argument_list|>
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationStatusResponse
block|{
specifier|public
name|SpecialRegistrationStatus
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationEligibilityResponse
block|{
specifier|public
name|SpecialRegistrationEligibility
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CheckEligibilityResponse
extends|extends
name|SpecialRegistrationEligibilityResponse
block|{
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|overrides
decl_stmt|;
specifier|public
name|Float
name|maxCredit
decl_stmt|;
specifier|public
name|Boolean
name|hasNonCanceledRequest
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationMultipleStatusResponse
block|{
specifier|public
name|SpecialRegistrationMultipleStatus
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationMultipleStatus
block|{
comment|// public Set<String> overrides;
specifier|public
name|List
argument_list|<
name|SpecialRegistrationStatus
argument_list|>
name|students
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationStatus
block|{
comment|// public Set<String> overrides;
specifier|public
name|List
argument_list|<
name|SpecialRegistrationRequest
argument_list|>
name|requests
decl_stmt|;
specifier|public
name|Float
name|maxCredit
decl_stmt|;
specifier|public
name|String
name|studentId
decl_stmt|;
block|}
specifier|public
specifier|static
enum|enum
name|ResponseStatus
block|{
name|success
block|,
name|failure
block|; 	}
specifier|public
specifier|static
enum|enum
name|ChangeOperation
block|{
name|ADD
block|,
name|DROP
block|,
name|KEEP
block|, 	}
specifier|public
specifier|static
class|class
name|Change
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|courseNbr
decl_stmt|;
specifier|public
name|String
name|crn
decl_stmt|;
specifier|public
name|String
name|operation
decl_stmt|;
specifier|public
name|List
argument_list|<
name|ChangeError
argument_list|>
name|errors
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Override
argument_list|>
name|overrides
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|List
argument_list|<
name|ChangeNote
argument_list|>
name|notes
decl_stmt|;
specifier|public
name|String
name|credit
decl_stmt|;
specifier|public
name|boolean
name|hasLastNote
parameter_list|()
block|{
if|if
condition|(
name|notes
operator|==
literal|null
operator|||
name|notes
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|ChangeNote
name|n
range|:
name|notes
control|)
if|if
condition|(
name|n
operator|.
name|notes
operator|!=
literal|null
operator|&&
operator|!
name|n
operator|.
name|notes
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|String
name|getLastNote
parameter_list|()
block|{
if|if
condition|(
name|notes
operator|==
literal|null
operator|||
name|notes
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|ChangeNote
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|ChangeNote
name|n
range|:
name|notes
control|)
if|if
condition|(
name|n
operator|.
name|notes
operator|!=
literal|null
operator|&&
operator|!
name|n
operator|.
name|notes
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
name|note
operator|==
literal|null
operator|||
name|note
operator|.
name|dateCreated
operator|.
name|isBefore
argument_list|(
name|n
operator|.
name|dateCreated
argument_list|)
operator|)
condition|)
name|note
operator|=
name|n
expr_stmt|;
return|return
operator|(
name|note
operator|==
literal|null
condition|?
literal|null
else|:
name|note
operator|.
name|notes
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|ChangeError
block|{
name|String
name|code
decl_stmt|;
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ChangeNote
block|{
specifier|public
name|DateTime
name|dateCreated
decl_stmt|;
specifier|public
name|String
name|fullName
decl_stmt|;
specifier|public
name|String
name|notes
decl_stmt|;
specifier|public
name|String
name|purpose
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|EligibilityProblem
block|{
name|String
name|code
decl_stmt|;
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Override
block|{
name|String
name|code
decl_stmt|;
name|String
name|message
decl_stmt|;
name|String
name|needsAction
decl_stmt|;
name|String
name|needsOverride
decl_stmt|;
name|String
name|overrideApplied
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ValidationCheckRequest
block|{
specifier|public
name|String
name|studentId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|campus
decl_stmt|;
specifier|public
name|String
name|includeReg
decl_stmt|;
specifier|public
name|String
name|mode
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Schedule
argument_list|>
name|schedule
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Schedule
argument_list|>
name|alternatives
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Schedule
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|courseNbr
decl_stmt|;
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|crns
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ValidationCheckResponse
block|{
specifier|public
name|ScheduleRestrictions
name|scheduleRestrictions
decl_stmt|;
specifier|public
name|ScheduleRestrictions
name|alternativesRestrictions
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ScheduleRestrictions
block|{
specifier|public
name|List
argument_list|<
name|Problem
argument_list|>
name|problems
decl_stmt|;
specifier|public
name|String
name|sisId
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Problem
block|{
name|String
name|code
decl_stmt|;
name|String
name|crn
decl_stmt|;
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CourseCredit
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|courseNbr
decl_stmt|;
specifier|public
name|String
name|title
decl_stmt|;
specifier|public
name|Float
name|creditHrs
decl_stmt|;
specifier|public
name|List
argument_list|<
name|CourseCredit
argument_list|>
name|alternatives
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|RestrictionsCheckRequest
block|{
specifier|public
name|String
name|sisId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|campus
decl_stmt|;
specifier|public
name|String
name|includeReg
decl_stmt|;
specifier|public
name|String
name|mode
decl_stmt|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Crn
argument_list|>
argument_list|>
name|actions
decl_stmt|;
specifier|public
name|void
name|addOperation
parameter_list|(
name|String
name|op
parameter_list|,
name|String
name|crn
parameter_list|)
block|{
if|if
condition|(
name|actions
operator|==
literal|null
condition|)
name|actions
operator|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|Crn
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Crn
argument_list|>
name|crns
init|=
name|actions
operator|.
name|get
argument_list|(
name|op
argument_list|)
decl_stmt|;
if|if
condition|(
name|crns
operator|==
literal|null
condition|)
block|{
name|crns
operator|=
operator|new
name|ArrayList
argument_list|<
name|Crn
argument_list|>
argument_list|()
expr_stmt|;
name|actions
operator|.
name|put
argument_list|(
name|op
argument_list|,
name|crns
argument_list|)
expr_stmt|;
block|}
name|Crn
name|c
init|=
operator|new
name|Crn
argument_list|()
decl_stmt|;
name|c
operator|.
name|crn
operator|=
name|crn
expr_stmt|;
name|crns
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|add
parameter_list|(
name|String
name|crn
parameter_list|)
block|{
name|addOperation
argument_list|(
literal|"ADD"
argument_list|,
name|crn
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|drop
parameter_list|(
name|String
name|crn
parameter_list|)
block|{
name|addOperation
argument_list|(
literal|"DROP"
argument_list|,
name|crn
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Crn
block|{
name|String
name|crn
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|RestrictionsCheckResponse
extends|extends
name|ScheduleRestrictions
block|{ 	}
specifier|public
specifier|static
class|class
name|SpecialRegistrationCancelResponse
block|{
specifier|public
name|String
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CheckRestrictionsRequest
block|{
specifier|public
name|String
name|studentId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|campus
decl_stmt|;
specifier|public
name|String
name|mode
decl_stmt|;
specifier|public
name|RestrictionsCheckRequest
name|changes
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CheckRestrictionsResponse
block|{
specifier|public
name|List
argument_list|<
name|SpecialRegistrationRequest
argument_list|>
name|cancelRegistrationRequests
decl_stmt|;
specifier|public
name|List
argument_list|<
name|DeniedRequest
argument_list|>
name|deniedRequests
decl_stmt|;
specifier|public
name|SpecialRegistrationEligibilityResponse
name|eligible
decl_stmt|;
specifier|public
name|Float
name|maxCredit
decl_stmt|;
specifier|public
name|RestrictionsCheckResponse
name|outJson
decl_stmt|;
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|overrides
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|DeniedRequest
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|courseNbr
decl_stmt|;
specifier|public
name|String
name|crn
decl_stmt|;
specifier|public
name|String
name|code
decl_stmt|;
specifier|public
name|String
name|errorMessage
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CancelledRequest
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|courseNbr
decl_stmt|;
specifier|public
name|String
name|crn
decl_stmt|;
specifier|public
name|String
name|requestId
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ErrorResponse
block|{
specifier|public
name|String
name|data
decl_stmt|;
specifier|public
name|String
name|status
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
block|}
block|}
end_class

end_unit

