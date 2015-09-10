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
name|lang
operator|.
name|reflect
operator|.
name|Type
import|;
end_import

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
name|List
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

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gson
operator|.
name|reflect
operator|.
name|TypeToken
import|;
end_import

begin_class
specifier|public
class|class
name|XEInterface
block|{
specifier|public
specifier|static
class|class
name|Registration
block|{
specifier|public
name|String
name|subject
decl_stmt|;
specifier|public
name|String
name|subjectDescription
decl_stmt|;
specifier|public
name|String
name|courseNumber
decl_stmt|;
specifier|public
name|String
name|courseReferenceNumber
decl_stmt|;
specifier|public
name|String
name|courseTitle
decl_stmt|;
comment|/** 		 * 40	CEC 40% refund 		 * 60	CEC 60% refund 		 * 80	CEC 80% refund 		 * AA	Auditor Access 		 * AU	Audit 		 * CA	Cancel Administratively 		 * DB	Boiler Gold Rush Drop Course 		 * DC	Drop Course 		 * DD	Drop/Delete 		 * DT	Drop Course-TSW 		 * DW	Drop (Web) 		 * RC	**ReAdd Course** 		 * RE	**Registered** 		 * RT	**Web Registered** 		 * RW	**Web Registered** 		 * W	Withdrawn-W 		 * W1	Withdrawn 		 * W2	Withdrawn 		 * W3	Withdrawn 		 * W4	Withdrawn 		 * W5	Withdrawn 		 * WF	Withdrawn-WF 		 * WG	Withdrawn-pending grade 		 * WN	Withdrawn-WN 		 * WT	Withdrawn-W 		 * WU	Withdrawn-WU 		 * WL	Waitlist 		 */
specifier|public
name|String
name|courseRegistrationStatus
decl_stmt|;
specifier|public
name|String
name|courseRegistrationStatusDescription
decl_stmt|;
specifier|public
name|Double
name|creditHour
decl_stmt|;
specifier|public
name|String
name|gradingMode
decl_stmt|;
specifier|public
name|String
name|gradingModeDescription
decl_stmt|;
specifier|public
name|String
name|level
decl_stmt|;
specifier|public
name|String
name|levelDescription
decl_stmt|;
specifier|public
name|DateTime
name|registrationStatusDate
decl_stmt|;
specifier|public
name|String
name|scheduleDescription
decl_stmt|;
specifier|public
name|String
name|scheduleType
decl_stmt|;
specifier|public
name|String
name|sequenceNumber
decl_stmt|;
specifier|public
name|String
name|statusDescription
decl_stmt|;
comment|/** 		 * P = pending 		 * R = registered 		 * D = dropped 		 * L = waitlisted 		 * F = fatal error prevented registration 		 * W = withdrawn  		 */
specifier|public
name|String
name|statusIndicator
decl_stmt|;
specifier|public
name|List
argument_list|<
name|CrnError
argument_list|>
name|crnErrors
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
name|List
argument_list|<
name|RegistrationAction
argument_list|>
name|registrationActions
decl_stmt|;
specifier|public
name|boolean
name|canDrop
parameter_list|()
block|{
if|if
condition|(
name|registrationActions
operator|!=
literal|null
condition|)
for|for
control|(
name|RegistrationAction
name|action
range|:
name|registrationActions
control|)
block|{
if|if
condition|(
literal|"DW"
operator|.
name|equals
argument_list|(
name|action
operator|.
name|courseRegistrationStatus
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|canAdd
parameter_list|()
block|{
comment|// return !"DD".equals(courseRegistrationStatus);
if|if
condition|(
name|registrationActions
operator|!=
literal|null
condition|)
for|for
control|(
name|RegistrationAction
name|action
range|:
name|registrationActions
control|)
block|{
if|if
condition|(
literal|"RW"
operator|.
name|equals
argument_list|(
name|action
operator|.
name|courseRegistrationStatus
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|isRegistered
parameter_list|()
block|{
return|return
literal|"R"
operator|.
name|equals
argument_list|(
name|statusIndicator
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|CrnError
block|{
specifier|public
name|String
name|errorFlag
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
specifier|public
name|String
name|messageType
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|RegistrationAction
block|{
specifier|public
name|String
name|courseRegistrationStatus
decl_stmt|;
specifier|public
name|String
name|description
decl_stmt|;
specifier|public
name|Boolean
name|remove
decl_stmt|;
specifier|public
name|String
name|voiceType
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|TimeTicket
block|{
specifier|public
name|DateTime
name|beginDate
decl_stmt|;
specifier|public
name|DateTime
name|endDate
decl_stmt|;
specifier|public
name|String
name|startTime
decl_stmt|;
specifier|public
name|String
name|endTime
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|FailedRegistration
block|{
specifier|public
name|String
name|failedCRN
decl_stmt|;
specifier|public
name|String
name|failure
decl_stmt|;
specifier|public
name|Registration
name|registration
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|RegisterResponse
block|{
specifier|public
specifier|static
specifier|final
name|Type
name|TYPE_LIST
init|=
operator|new
name|TypeToken
argument_list|<
name|ArrayList
argument_list|<
name|RegisterResponse
argument_list|>
argument_list|>
argument_list|()
block|{}
operator|.
name|getType
argument_list|()
decl_stmt|;
specifier|public
name|List
argument_list|<
name|FailedRegistration
argument_list|>
name|failedRegistrations
decl_stmt|;
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|failureReasons
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Registration
argument_list|>
name|registrations
decl_stmt|;
specifier|public
name|List
argument_list|<
name|TimeTicket
argument_list|>
name|timeTickets
decl_stmt|;
specifier|public
name|Boolean
name|validStudent
decl_stmt|;
specifier|public
name|String
name|registrationException
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CourseReferenceNumber
block|{
specifier|public
name|String
name|courseReferenceNumber
decl_stmt|;
specifier|public
name|String
name|courseRegistrationStatus
decl_stmt|;
specifier|public
name|CourseReferenceNumber
parameter_list|()
block|{
block|}
specifier|public
name|CourseReferenceNumber
parameter_list|(
name|String
name|crn
parameter_list|)
block|{
name|this
operator|.
name|courseReferenceNumber
operator|=
name|crn
expr_stmt|;
block|}
specifier|public
name|CourseReferenceNumber
parameter_list|(
name|String
name|crn
parameter_list|,
name|String
name|status
parameter_list|)
block|{
name|this
operator|.
name|courseReferenceNumber
operator|=
name|crn
expr_stmt|;
name|this
operator|.
name|courseRegistrationStatus
operator|=
name|status
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|RegisterAction
block|{
specifier|public
name|String
name|courseReferenceNumber
decl_stmt|;
specifier|public
name|String
name|selectedAction
decl_stmt|;
specifier|public
name|String
name|selectedLevel
decl_stmt|;
specifier|public
name|String
name|selectedGradingMode
decl_stmt|;
specifier|public
name|String
name|selectedStudyPath
decl_stmt|;
specifier|public
name|String
name|selectedCreditHour
decl_stmt|;
specifier|public
name|RegisterAction
parameter_list|(
name|String
name|action
parameter_list|,
name|String
name|crn
parameter_list|)
block|{
name|selectedAction
operator|=
name|action
expr_stmt|;
name|courseReferenceNumber
operator|=
name|crn
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|RegisterRequest
block|{
specifier|public
name|String
name|bannerId
decl_stmt|;
specifier|public
name|String
name|term
decl_stmt|;
specifier|public
name|String
name|altPin
decl_stmt|;
specifier|public
name|String
name|systemIn
decl_stmt|;
specifier|public
name|List
argument_list|<
name|CourseReferenceNumber
argument_list|>
name|courseReferenceNumbers
decl_stmt|;
specifier|public
name|List
argument_list|<
name|RegisterAction
argument_list|>
name|actionsAndOptions
decl_stmt|;
specifier|public
name|RegisterRequest
parameter_list|(
name|String
name|term
parameter_list|,
name|String
name|bannerId
parameter_list|,
name|String
name|pin
parameter_list|,
name|boolean
name|admin
parameter_list|)
block|{
name|this
operator|.
name|term
operator|=
name|term
expr_stmt|;
name|this
operator|.
name|bannerId
operator|=
name|bannerId
expr_stmt|;
name|this
operator|.
name|altPin
operator|=
name|pin
expr_stmt|;
name|this
operator|.
name|systemIn
operator|=
operator|(
name|admin
condition|?
literal|"SB"
else|:
literal|"WA"
operator|)
expr_stmt|;
block|}
specifier|public
name|RegisterRequest
name|drop
parameter_list|(
name|String
name|crn
parameter_list|)
block|{
if|if
condition|(
name|actionsAndOptions
operator|==
literal|null
condition|)
name|actionsAndOptions
operator|=
operator|new
name|ArrayList
argument_list|<
name|RegisterAction
argument_list|>
argument_list|()
expr_stmt|;
name|actionsAndOptions
operator|.
name|add
argument_list|(
operator|new
name|RegisterAction
argument_list|(
literal|"DW"
argument_list|,
name|crn
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|RegisterRequest
name|keep
parameter_list|(
name|String
name|crn
parameter_list|)
block|{
if|if
condition|(
name|courseReferenceNumbers
operator|==
literal|null
condition|)
name|courseReferenceNumbers
operator|=
operator|new
name|ArrayList
argument_list|<
name|XEInterface
operator|.
name|CourseReferenceNumber
argument_list|>
argument_list|()
expr_stmt|;
name|courseReferenceNumbers
operator|.
name|add
argument_list|(
operator|new
name|CourseReferenceNumber
argument_list|(
name|crn
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|RegisterRequest
name|add
parameter_list|(
name|String
name|crn
parameter_list|,
name|boolean
name|changeStatus
parameter_list|)
block|{
if|if
condition|(
name|changeStatus
condition|)
block|{
if|if
condition|(
name|actionsAndOptions
operator|==
literal|null
condition|)
name|actionsAndOptions
operator|=
operator|new
name|ArrayList
argument_list|<
name|RegisterAction
argument_list|>
argument_list|()
expr_stmt|;
name|actionsAndOptions
operator|.
name|add
argument_list|(
operator|new
name|RegisterAction
argument_list|(
literal|"RW"
argument_list|,
name|crn
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|courseReferenceNumbers
operator|==
literal|null
condition|)
name|courseReferenceNumbers
operator|=
operator|new
name|ArrayList
argument_list|<
name|XEInterface
operator|.
name|CourseReferenceNumber
argument_list|>
argument_list|()
expr_stmt|;
if|if
condition|(
literal|"SB"
operator|.
name|equals
argument_list|(
name|systemIn
argument_list|)
condition|)
name|courseReferenceNumbers
operator|.
name|add
argument_list|(
operator|new
name|CourseReferenceNumber
argument_list|(
name|crn
argument_list|,
literal|"RW"
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|courseReferenceNumbers
operator|.
name|add
argument_list|(
operator|new
name|CourseReferenceNumber
argument_list|(
name|crn
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|this
return|;
block|}
specifier|public
name|RegisterRequest
name|empty
parameter_list|()
block|{
if|if
condition|(
name|courseReferenceNumbers
operator|==
literal|null
condition|)
name|courseReferenceNumbers
operator|=
operator|new
name|ArrayList
argument_list|<
name|XEInterface
operator|.
name|CourseReferenceNumber
argument_list|>
argument_list|()
expr_stmt|;
name|courseReferenceNumbers
operator|.
name|add
argument_list|(
operator|new
name|CourseReferenceNumber
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
operator|(
name|actionsAndOptions
operator|==
literal|null
operator|||
name|actionsAndOptions
operator|.
name|isEmpty
argument_list|()
operator|)
operator|&&
operator|(
name|courseReferenceNumbers
operator|==
literal|null
operator|||
name|courseReferenceNumbers
operator|.
name|isEmpty
argument_list|()
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|DegreePlan
block|{
specifier|public
specifier|static
specifier|final
name|Type
name|TYPE_LIST
init|=
operator|new
name|TypeToken
argument_list|<
name|ArrayList
argument_list|<
name|DegreePlan
argument_list|>
argument_list|>
argument_list|()
block|{}
operator|.
name|getType
argument_list|()
decl_stmt|;
specifier|public
name|String
name|id
decl_stmt|;
specifier|public
name|String
name|description
decl_stmt|;
specifier|public
name|Student
name|student
decl_stmt|;
specifier|public
name|CodeDescription
name|degree
decl_stmt|;
specifier|public
name|CodeDescription
name|school
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Year
argument_list|>
name|years
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Student
block|{
specifier|public
name|String
name|id
decl_stmt|;
specifier|public
name|String
name|name
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|CodeDescription
block|{
specifier|public
name|String
name|code
decl_stmt|;
specifier|public
name|String
name|description
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Year
extends|extends
name|CodeDescription
block|{
specifier|public
name|List
argument_list|<
name|Term
argument_list|>
name|terms
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Term
block|{
specifier|public
name|String
name|id
decl_stmt|;
specifier|public
name|CodeDescription
name|term
decl_stmt|;
specifier|public
name|Group
name|group
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Group
block|{
specifier|public
name|CodeDescription
name|groupType
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Course
argument_list|>
name|plannedClasses
decl_stmt|;
specifier|public
name|List
argument_list|<
name|Group
argument_list|>
name|groups
decl_stmt|;
specifier|public
name|List
argument_list|<
name|PlaceHolder
argument_list|>
name|plannedPlaceholders
decl_stmt|;
specifier|public
name|String
name|summaryDescription
decl_stmt|;
specifier|public
name|boolean
name|isGroupSelection
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Course
block|{
specifier|public
name|String
name|id
decl_stmt|;
specifier|public
name|String
name|title
decl_stmt|;
specifier|public
name|String
name|courseNumber
decl_stmt|;
specifier|public
name|String
name|courseDiscipline
decl_stmt|;
specifier|public
name|boolean
name|isGroupSelection
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|PlaceHolder
block|{
specifier|public
name|String
name|id
decl_stmt|;
specifier|public
name|CodeDescription
name|placeholderType
decl_stmt|;
specifier|public
name|String
name|placeholderValue
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|ErrorResponse
block|{
specifier|public
name|List
argument_list|<
name|Error
argument_list|>
name|errors
decl_stmt|;
specifier|public
name|Error
name|getError
parameter_list|()
block|{
return|return
operator|(
name|errors
operator|==
literal|null
operator|||
name|errors
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|errors
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Error
block|{
specifier|public
name|String
name|code
decl_stmt|;
specifier|public
name|String
name|message
decl_stmt|;
specifier|public
name|String
name|description
decl_stmt|;
specifier|public
name|String
name|type
decl_stmt|;
specifier|public
name|String
name|errorMessage
decl_stmt|;
block|}
block|}
end_class

end_unit

