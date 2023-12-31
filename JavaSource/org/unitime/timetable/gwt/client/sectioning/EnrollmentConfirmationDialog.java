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
name|gwt
operator|.
name|client
operator|.
name|sectioning
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|client
operator|.
name|aria
operator|.
name|AriaButton
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
name|client
operator|.
name|aria
operator|.
name|AriaStatus
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
name|client
operator|.
name|widgets
operator|.
name|P
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeDialogBox
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
name|resources
operator|.
name|StudentSectioningResources
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
name|ErrorMessage
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
name|SpecialRegistrationInterface
operator|.
name|SpecialRegistrationEligibilityResponse
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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|GWT
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|Scheduler
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|core
operator|.
name|client
operator|.
name|Scheduler
operator|.
name|ScheduledCommand
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|ClickEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|ClickHandler
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|CloseEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|CloseHandler
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|AsyncCallback
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|Image
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|PopupPanel
import|;
end_import

begin_class
specifier|public
class|class
name|EnrollmentConfirmationDialog
extends|extends
name|UniTimeDialogBox
block|{
specifier|protected
specifier|static
name|StudentSectioningConstants
name|CONSTANTS
init|=
name|GWT
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
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|StudentSectioningResources
name|RESOURCES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningResources
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|AriaButton
name|iYes
decl_stmt|,
name|iNo
decl_stmt|;
specifier|private
name|AsyncCallback
argument_list|<
name|SpecialRegistrationEligibilityResponse
argument_list|>
name|iCommand
decl_stmt|;
specifier|private
name|boolean
name|iValue
init|=
literal|false
decl_stmt|;
specifier|private
name|P
name|iOverrideMessage
init|=
literal|null
decl_stmt|,
name|iWaiting
init|=
literal|null
decl_stmt|,
name|iMessagePannel
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAll
init|=
literal|true
decl_stmt|;
specifier|private
name|SpecialRegistrationEligibilityResponse
name|iResponse
decl_stmt|;
specifier|public
name|EnrollmentConfirmationDialog
parameter_list|(
name|Throwable
name|exception
parameter_list|,
name|ClassAssignmentInterface
name|result
parameter_list|,
name|AsyncCallback
argument_list|<
name|SpecialRegistrationEligibilityResponse
argument_list|>
name|callback
parameter_list|)
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-CourseRequestsConfirmationDialog"
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogEnrollmentConfirmation
argument_list|()
argument_list|)
expr_stmt|;
name|iCommand
operator|=
name|callback
expr_stmt|;
name|P
name|panel
init|=
operator|new
name|P
argument_list|(
literal|"unitime-ConfirmationPanel"
argument_list|)
decl_stmt|;
name|setEscapeToHide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|P
name|bd
init|=
operator|new
name|P
argument_list|(
literal|"body-panel"
argument_list|)
decl_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|bd
argument_list|)
expr_stmt|;
name|P
name|ic
init|=
operator|new
name|P
argument_list|(
literal|"icon-panel"
argument_list|)
decl_stmt|;
name|bd
operator|.
name|add
argument_list|(
name|ic
argument_list|)
expr_stmt|;
name|ic
operator|.
name|add
argument_list|(
operator|new
name|Image
argument_list|(
name|exception
operator|!=
literal|null
condition|?
name|RESOURCES
operator|.
name|statusError
argument_list|()
else|:
name|RESOURCES
operator|.
name|statusWarning
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|P
name|cp
init|=
operator|new
name|P
argument_list|(
literal|"content-panel"
argument_list|)
decl_stmt|;
name|bd
operator|.
name|add
argument_list|(
name|cp
argument_list|)
expr_stmt|;
name|iMessagePannel
operator|=
operator|new
name|P
argument_list|(
literal|"message-panel"
argument_list|)
expr_stmt|;
name|cp
operator|.
name|add
argument_list|(
name|iMessagePannel
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|!=
literal|null
condition|)
block|{
name|P
name|m1
init|=
operator|new
name|P
argument_list|(
literal|"message"
argument_list|)
decl_stmt|;
name|m1
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageEnrollmentFailedWithErrors
argument_list|()
argument_list|)
expr_stmt|;
name|iMessagePannel
operator|.
name|add
argument_list|(
name|m1
argument_list|)
expr_stmt|;
if|if
condition|(
name|exception
operator|instanceof
name|SectioningException
operator|&&
operator|(
operator|(
name|SectioningException
operator|)
name|exception
operator|)
operator|.
name|hasErrors
argument_list|()
condition|)
block|{
name|P
name|ctab
init|=
operator|new
name|P
argument_list|(
literal|"course-table"
argument_list|)
decl_stmt|;
name|String
name|last
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|msg
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ErrorMessage
name|cm
range|:
operator|new
name|TreeSet
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|(
operator|(
operator|(
name|SectioningException
operator|)
name|exception
operator|)
operator|.
name|getErrors
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|cm
operator|.
name|getCode
argument_list|()
operator|!=
literal|null
operator|&&
name|cm
operator|.
name|getCode
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"UT_"
argument_list|)
condition|)
name|iAll
operator|=
literal|false
expr_stmt|;
name|P
name|cn
init|=
operator|new
name|P
argument_list|(
literal|"course-name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
operator|!
name|last
operator|.
name|equals
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
block|{
name|msg
operator|.
name|clear
argument_list|()
expr_stmt|;
name|cn
operator|.
name|setText
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|msg
operator|.
name|add
argument_list|(
name|cm
operator|.
name|getMessage
argument_list|()
argument_list|)
condition|)
continue|continue;
name|P
name|m
init|=
operator|new
name|P
argument_list|(
literal|"course-message"
argument_list|)
decl_stmt|;
name|m
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|courseMessage
argument_list|(
name|cm
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|P
name|crow
init|=
operator|new
name|P
argument_list|(
literal|"course-row"
argument_list|)
decl_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
operator|!
name|last
operator|.
name|equals
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
name|crow
operator|.
name|addStyleName
argument_list|(
literal|"first-course-line"
argument_list|)
expr_stmt|;
name|crow
operator|.
name|add
argument_list|(
name|cn
argument_list|)
expr_stmt|;
name|crow
operator|.
name|add
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|ctab
operator|.
name|add
argument_list|(
name|crow
argument_list|)
expr_stmt|;
name|last
operator|=
name|cm
operator|.
name|getCourse
argument_list|()
expr_stmt|;
block|}
name|iMessagePannel
operator|.
name|add
argument_list|(
name|ctab
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|P
name|em
init|=
operator|new
name|P
argument_list|(
literal|"message"
argument_list|,
literal|"error-message"
argument_list|)
decl_stmt|;
name|em
operator|.
name|setHTML
argument_list|(
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iMessagePannel
operator|.
name|add
argument_list|(
name|em
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|P
name|m1
init|=
operator|new
name|P
argument_list|(
literal|"message"
argument_list|)
decl_stmt|;
name|m1
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageEnrollmentSucceededWithErrors
argument_list|()
argument_list|)
expr_stmt|;
name|iMessagePannel
operator|.
name|add
argument_list|(
name|m1
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|.
name|hasErrors
argument_list|()
condition|)
block|{
name|P
name|ctab
init|=
operator|new
name|P
argument_list|(
literal|"course-table"
argument_list|)
decl_stmt|;
name|String
name|last
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|msg
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ErrorMessage
name|cm
range|:
operator|new
name|TreeSet
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|(
name|result
operator|.
name|getErrors
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|cm
operator|.
name|getCode
argument_list|()
operator|!=
literal|null
operator|&&
name|cm
operator|.
name|getCode
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"UT_"
argument_list|)
condition|)
name|iAll
operator|=
literal|false
expr_stmt|;
name|P
name|cn
init|=
operator|new
name|P
argument_list|(
literal|"course-name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
operator|!
name|last
operator|.
name|equals
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
block|{
name|msg
operator|.
name|clear
argument_list|()
expr_stmt|;
name|cn
operator|.
name|setText
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|msg
operator|.
name|add
argument_list|(
name|cm
operator|.
name|getMessage
argument_list|()
argument_list|)
condition|)
continue|continue;
name|P
name|m
init|=
operator|new
name|P
argument_list|(
literal|"course-message"
argument_list|)
decl_stmt|;
name|m
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|courseMessage
argument_list|(
name|cm
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|P
name|crow
init|=
operator|new
name|P
argument_list|(
literal|"course-row"
argument_list|)
decl_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
operator|!
name|last
operator|.
name|equals
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
name|crow
operator|.
name|addStyleName
argument_list|(
literal|"first-course-line"
argument_list|)
expr_stmt|;
name|crow
operator|.
name|add
argument_list|(
name|cn
argument_list|)
expr_stmt|;
name|crow
operator|.
name|add
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|ctab
operator|.
name|add
argument_list|(
name|crow
argument_list|)
expr_stmt|;
name|last
operator|=
name|cm
operator|.
name|getCourse
argument_list|()
expr_stmt|;
block|}
name|iMessagePannel
operator|.
name|add
argument_list|(
name|ctab
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|P
name|em
init|=
operator|new
name|P
argument_list|(
literal|"message"
argument_list|,
literal|"error-message"
argument_list|)
decl_stmt|;
name|em
operator|.
name|setHTML
argument_list|(
name|result
operator|.
name|getMessages
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|iMessagePannel
operator|.
name|add
argument_list|(
name|em
argument_list|)
expr_stmt|;
block|}
block|}
name|iOverrideMessage
operator|=
operator|new
name|P
argument_list|(
literal|"message"
argument_list|,
literal|"override-message"
argument_list|)
expr_stmt|;
name|iOverrideMessage
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageCheckingOverrides
argument_list|()
argument_list|)
expr_stmt|;
name|iMessagePannel
operator|.
name|add
argument_list|(
name|iOverrideMessage
argument_list|)
expr_stmt|;
name|P
name|bp
init|=
operator|new
name|P
argument_list|(
literal|"buttons-panel"
argument_list|)
decl_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|bp
argument_list|)
expr_stmt|;
name|iWaiting
operator|=
operator|new
name|P
argument_list|(
literal|"unitime-Waiting"
argument_list|)
expr_stmt|;
name|iWaiting
operator|.
name|add
argument_list|(
operator|new
name|Image
argument_list|(
name|RESOURCES
operator|.
name|loading_small
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|P
name|wm
init|=
operator|new
name|P
argument_list|(
literal|"waiting-message"
argument_list|)
decl_stmt|;
name|iWaiting
operator|.
name|add
argument_list|(
name|wm
argument_list|)
expr_stmt|;
name|wm
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|waitOverridesCheck
argument_list|()
argument_list|)
expr_stmt|;
name|bp
operator|.
name|add
argument_list|(
name|iWaiting
argument_list|)
expr_stmt|;
name|iYes
operator|=
operator|new
name|AriaButton
argument_list|(
name|MESSAGES
operator|.
name|buttonEnrollmentRequestOverrides
argument_list|()
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|addStyleName
argument_list|(
literal|"yes"
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|setTitle
argument_list|(
name|MESSAGES
operator|.
name|titleEnrollmentRequestOverrides
argument_list|()
argument_list|)
expr_stmt|;
name|bp
operator|.
name|add
argument_list|(
name|iYes
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|addClickHandler
argument_list|(
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|submit
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iNo
operator|=
operator|new
name|AriaButton
argument_list|(
name|MESSAGES
operator|.
name|buttonEnrollmentHideConfirmation
argument_list|()
argument_list|)
expr_stmt|;
name|iNo
operator|.
name|addStyleName
argument_list|(
literal|"no"
argument_list|)
expr_stmt|;
name|iNo
operator|.
name|setTitle
argument_list|(
name|MESSAGES
operator|.
name|titleEnrollmentHideConfirmation
argument_list|()
argument_list|)
expr_stmt|;
name|bp
operator|.
name|add
argument_list|(
name|iNo
argument_list|)
expr_stmt|;
name|iNo
operator|.
name|addClickHandler
argument_list|(
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addCloseHandler
argument_list|(
operator|new
name|CloseHandler
argument_list|<
name|PopupPanel
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClose
parameter_list|(
name|CloseEvent
argument_list|<
name|PopupPanel
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|iCommand
operator|!=
literal|null
condition|)
name|Scheduler
operator|.
name|get
argument_list|()
operator|.
name|scheduleDeferred
argument_list|(
operator|new
name|ScheduledCommand
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|iCommand
operator|.
name|onSuccess
argument_list|(
name|iValue
condition|?
name|iResponse
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|showError
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iWaiting
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iOverrideMessage
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageCannotRequestOverrides
argument_list|(
name|message
argument_list|)
argument_list|)
expr_stmt|;
name|super
operator|.
name|center
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|showErrors
parameter_list|(
name|List
argument_list|<
name|ErrorMessage
argument_list|>
name|errors
parameter_list|)
block|{
name|iWaiting
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iOverrideMessage
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageCannotRequestOverridesErrors
argument_list|()
argument_list|)
expr_stmt|;
name|P
name|ctab
init|=
operator|new
name|P
argument_list|(
literal|"course-table"
argument_list|)
decl_stmt|;
name|String
name|last
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|msg
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ErrorMessage
name|cm
range|:
name|errors
control|)
block|{
name|P
name|cn
init|=
operator|new
name|P
argument_list|(
literal|"course-name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
operator|!
name|last
operator|.
name|equals
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
block|{
name|msg
operator|.
name|clear
argument_list|()
expr_stmt|;
name|cn
operator|.
name|setText
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|msg
operator|.
name|add
argument_list|(
name|cm
operator|.
name|getMessage
argument_list|()
argument_list|)
condition|)
continue|continue;
name|P
name|m
init|=
operator|new
name|P
argument_list|(
literal|"course-message"
argument_list|)
decl_stmt|;
name|m
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|courseMessage
argument_list|(
name|cm
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|P
name|crow
init|=
operator|new
name|P
argument_list|(
literal|"course-row"
argument_list|)
decl_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
operator|!
name|last
operator|.
name|equals
argument_list|(
name|cm
operator|.
name|getCourse
argument_list|()
argument_list|)
condition|)
name|crow
operator|.
name|addStyleName
argument_list|(
literal|"first-course-line"
argument_list|)
expr_stmt|;
name|crow
operator|.
name|add
argument_list|(
name|cn
argument_list|)
expr_stmt|;
name|crow
operator|.
name|add
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|ctab
operator|.
name|add
argument_list|(
name|crow
argument_list|)
expr_stmt|;
name|last
operator|=
name|cm
operator|.
name|getCourse
argument_list|()
expr_stmt|;
block|}
name|iMessagePannel
operator|.
name|add
argument_list|(
name|ctab
argument_list|)
expr_stmt|;
name|P
name|m1
init|=
operator|new
name|P
argument_list|(
literal|"message"
argument_list|)
decl_stmt|;
name|m1
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageCannotRequestOverridesErrorsBottom
argument_list|()
argument_list|)
expr_stmt|;
name|iMessagePannel
operator|.
name|add
argument_list|(
name|m1
argument_list|)
expr_stmt|;
name|super
operator|.
name|center
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|showRequestOverrides
parameter_list|()
block|{
name|iWaiting
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|iAll
condition|)
name|iOverrideMessage
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageCanRequestOverridesAll
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|iOverrideMessage
operator|.
name|setHTML
argument_list|(
name|MESSAGES
operator|.
name|messageCanRequestOverridesSome
argument_list|()
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iYes
operator|.
name|setFocus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|super
operator|.
name|center
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setResponse
parameter_list|(
name|SpecialRegistrationEligibilityResponse
name|eligibilityResponse
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isVisible
argument_list|()
condition|)
return|return;
name|iResponse
operator|=
name|eligibilityResponse
expr_stmt|;
specifier|final
name|Collection
argument_list|<
name|ErrorMessage
argument_list|>
name|errors
init|=
name|eligibilityResponse
operator|.
name|getErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|eligibilityResponse
operator|.
name|isCanSubmit
argument_list|()
operator|&&
name|errors
operator|!=
literal|null
operator|&&
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|showRequestOverrides
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|eligibilityResponse
operator|.
name|hasDeniedErrors
argument_list|()
condition|)
block|{
name|showErrors
argument_list|(
name|eligibilityResponse
operator|.
name|getDeniedErrors
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|eligibilityResponse
operator|.
name|hasMessage
argument_list|()
condition|)
block|{
name|showError
argument_list|(
name|eligibilityResponse
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|eligibilityResponse
operator|.
name|isCanSubmit
argument_list|()
condition|)
block|{
name|showError
argument_list|(
name|MESSAGES
operator|.
name|errorNoRegistrationErrorsDetected
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|showError
argument_list|(
name|MESSAGES
operator|.
name|errorRegistrationErrorsBadResponse
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|SpecialRegistrationEligibilityResponse
name|getResponse
parameter_list|()
block|{
return|return
name|iResponse
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|center
parameter_list|()
block|{
name|super
operator|.
name|center
argument_list|()
expr_stmt|;
name|AriaStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|setText
argument_list|(
name|ARIA
operator|.
name|dialogOpened
argument_list|(
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Scheduler
operator|.
name|get
argument_list|()
operator|.
name|scheduleDeferred
argument_list|(
operator|new
name|ScheduledCommand
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|iNo
operator|.
name|setFocus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|submit
parameter_list|()
block|{
name|iValue
operator|=
name|iYes
operator|.
name|isEnabled
argument_list|()
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

