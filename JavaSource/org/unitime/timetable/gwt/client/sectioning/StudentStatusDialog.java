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
name|Set
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
name|SimpleForm
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
name|client
operator|.
name|widgets
operator|.
name|UniTimeHeaderPanel
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
name|UniTimeTextBox
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
name|WebTable
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
name|GwtMessages
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
name|OnlineSectioningInterface
operator|.
name|StudentStatusInfo
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|ChangeEvent
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
name|ChangeHandler
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
name|user
operator|.
name|client
operator|.
name|Command
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
name|Label
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
name|ListBox
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
name|TextArea
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentStatusDialog
extends|extends
name|UniTimeDialogBox
block|{
specifier|public
specifier|static
specifier|final
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
specifier|public
specifier|static
specifier|final
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
specifier|public
specifier|static
specifier|final
name|GwtMessages
name|GWT_MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|UniTimeTextBox
name|iSubject
decl_stmt|,
name|iCC
decl_stmt|;
specifier|private
name|TextArea
name|iMessage
decl_stmt|,
name|iNote
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentStatusInfo
argument_list|>
name|iStates
decl_stmt|;
specifier|private
name|ListBox
name|iStatus
decl_stmt|;
specifier|private
name|int
name|iStatusRow
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iButtons
decl_stmt|;
specifier|private
name|SimpleForm
name|iForm
decl_stmt|;
specifier|private
name|Command
name|iCommand
decl_stmt|;
specifier|public
name|StudentStatusDialog
parameter_list|(
name|Set
argument_list|<
name|StudentStatusInfo
argument_list|>
name|states
parameter_list|)
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iStates
operator|=
name|states
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-StudentStatusDialog"
argument_list|)
expr_stmt|;
name|setEscapeToHide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSubject
operator|=
operator|new
name|UniTimeTextBox
argument_list|(
literal|512
argument_list|,
literal|473
argument_list|)
expr_stmt|;
name|iSubject
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|defaulSubject
argument_list|()
argument_list|)
expr_stmt|;
name|iCC
operator|=
operator|new
name|UniTimeTextBox
argument_list|(
literal|512
argument_list|,
literal|473
argument_list|)
expr_stmt|;
name|iMessage
operator|=
operator|new
name|TextArea
argument_list|()
expr_stmt|;
name|iMessage
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextArea"
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setVisibleLines
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setCharacterWidth
argument_list|(
literal|80
argument_list|)
expr_stmt|;
name|iNote
operator|=
operator|new
name|TextArea
argument_list|()
expr_stmt|;
name|iNote
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextArea"
argument_list|)
expr_stmt|;
name|iNote
operator|.
name|setVisibleLines
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|iNote
operator|.
name|setCharacterWidth
argument_list|(
literal|80
argument_list|)
expr_stmt|;
name|iStatus
operator|=
operator|new
name|ListBox
argument_list|()
expr_stmt|;
name|iStatus
operator|.
name|addItem
argument_list|(
name|MESSAGES
operator|.
name|statusNoChange
argument_list|()
argument_list|,
literal|"-"
argument_list|)
expr_stmt|;
name|iStatus
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentStatusInfo
name|s
range|:
name|iStates
control|)
name|iStatus
operator|.
name|addItem
argument_list|(
name|s
operator|.
name|getLabel
argument_list|()
argument_list|,
name|s
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|iStatus
operator|.
name|addChangeHandler
argument_list|(
operator|new
name|ChangeHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onChange
parameter_list|(
name|ChangeEvent
name|event
parameter_list|)
block|{
name|statusChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iButtons
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|()
expr_stmt|;
name|iButtons
operator|.
name|addButton
argument_list|(
literal|"set-note"
argument_list|,
name|MESSAGES
operator|.
name|buttonSetNote
argument_list|()
argument_list|,
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
name|iCommand
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|addButton
argument_list|(
literal|"send-email"
argument_list|,
name|MESSAGES
operator|.
name|emailSend
argument_list|()
argument_list|,
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
name|iCommand
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|addButton
argument_list|(
literal|"mass-cancel"
argument_list|,
name|MESSAGES
operator|.
name|buttonMassCancel
argument_list|()
argument_list|,
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
name|iCommand
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|addButton
argument_list|(
literal|"set-status"
argument_list|,
name|MESSAGES
operator|.
name|buttonSetStatus
argument_list|()
argument_list|,
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
name|iCommand
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|addButton
argument_list|(
literal|"close"
argument_list|,
name|MESSAGES
operator|.
name|buttonClose
argument_list|()
argument_list|,
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
name|iForm
operator|=
operator|new
name|SimpleForm
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iForm
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|statusChanged
parameter_list|()
block|{
while|while
condition|(
name|iForm
operator|.
name|getRowCount
argument_list|()
operator|>
name|iStatusRow
operator|+
literal|1
condition|)
name|iForm
operator|.
name|removeRow
argument_list|(
name|iStatusRow
operator|+
literal|1
argument_list|)
expr_stmt|;
name|String
name|statusRef
init|=
name|iStatus
operator|.
name|getValue
argument_list|(
name|iStatus
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
decl_stmt|;
name|StudentStatusInfo
name|status
init|=
literal|null
decl_stmt|;
for|for
control|(
name|StudentStatusInfo
name|s
range|:
name|iStates
control|)
if|if
condition|(
name|statusRef
operator|.
name|equals
argument_list|(
name|s
operator|.
name|getReference
argument_list|()
argument_list|)
condition|)
block|{
name|status
operator|=
name|s
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
block|{
name|WebTable
name|table
init|=
operator|new
name|WebTable
argument_list|()
decl_stmt|;
name|table
operator|.
name|addStyleName
argument_list|(
literal|"unitime-StatusAccess"
argument_list|)
expr_stmt|;
name|table
operator|.
name|setHeader
argument_list|(
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colPage
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"75px"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colCanOpen
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"50px"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colCanStudentChange
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"50px"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colCanAdvisorChange
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"50px"
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|MESSAGES
operator|.
name|colCanAdminChange
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|"50px"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|table
operator|.
name|setData
argument_list|(
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|GWT_MESSAGES
operator|.
name|pageStudentCourseRequests
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanAccessRequestsPage
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanAccessRequestsPage
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusCanAccessCourseRequests
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusCanNotAccessCourseRequests
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanStudentRegister
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanStudentRegister
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusStudentsCanRegister
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusStudentsCanNotRegister
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanAdvisorRegister
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanAdvisorRegister
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusAdvisorsCanRegister
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusAdvisorsCanNotRegister
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanAdminRegister
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanAdminRegister
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusAdminsCanRegister
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusAdminsCanNotRegister
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|Row
argument_list|(
operator|new
name|WebTable
operator|.
name|Cell
argument_list|(
name|GWT_MESSAGES
operator|.
name|pageStudentSchedulingAssistant
argument_list|()
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanAccessAssistantPage
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanAccessAssistantPage
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusCanAccessSchedulingAssistant
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusCanNotAccessSchedulingAssistant
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanStudentEnroll
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanStudentEnroll
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusStudentsCanEnroll
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusStudentsCanNotEnroll
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanAdvisorEnroll
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanAdvisorEnroll
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusAdvisorsCanEnroll
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusAdvisorsCanNotEnroll
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanAdminEnroll
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanAdminEnroll
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusAdminsCanEnroll
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusAdminsCanNotEnroll
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propPermissions
argument_list|()
argument_list|,
name|table
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propWaitLists
argument_list|()
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isWaitList
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isWaitList
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusCanWaitList
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusCanNotWaitList
argument_list|()
argument_list|,
literal|null
argument_list|)
operator|.
name|getWidget
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propEmailNotification
argument_list|()
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isEmail
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isEmail
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusCanEmail
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusCanNotEmail
argument_list|()
argument_list|,
literal|null
argument_list|)
operator|.
name|getWidget
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propCourseRequestValidation
argument_list|()
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isRequestValiadtion
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isRequestValiadtion
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusCanRequestValidation
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusCanNotRequestValidation
argument_list|()
argument_list|,
literal|null
argument_list|)
operator|.
name|getWidget
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propSpecialRegistration
argument_list|()
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isSpecialRegistration
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isSpecialRegistration
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusCanSpecialRegistration
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusCanNotSpecialRegistration
argument_list|()
argument_list|,
literal|null
argument_list|)
operator|.
name|getWidget
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propCanRequire
argument_list|()
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isCanRequire
argument_list|()
condition|?
name|RESOURCES
operator|.
name|on
argument_list|()
else|:
name|RESOURCES
operator|.
name|off
argument_list|()
argument_list|,
name|status
operator|.
name|isCanRequire
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusCanRequire
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusCanNotRequire
argument_list|()
argument_list|,
literal|null
argument_list|)
operator|.
name|getWidget
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propStatusSchedule
argument_list|()
argument_list|,
operator|new
name|WebTable
operator|.
name|IconCell
argument_list|(
name|status
operator|.
name|isNoSchedule
argument_list|()
condition|?
name|RESOURCES
operator|.
name|off
argument_list|()
else|:
name|RESOURCES
operator|.
name|on
argument_list|()
argument_list|,
name|status
operator|.
name|isNoSchedule
argument_list|()
condition|?
name|MESSAGES
operator|.
name|messageStatusNoSchedule
argument_list|()
else|:
name|MESSAGES
operator|.
name|messageStatusSchedule
argument_list|()
argument_list|,
literal|null
argument_list|)
operator|.
name|getWidget
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|status
operator|.
name|hasMessage
argument_list|()
condition|)
block|{
name|P
name|m
init|=
operator|new
name|P
argument_list|(
literal|"status-message"
argument_list|)
decl_stmt|;
name|m
operator|.
name|setText
argument_list|(
name|status
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propStatusMessage
argument_list|()
argument_list|,
name|m
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|status
operator|.
name|hasEffectiveStart
argument_list|()
operator|||
name|status
operator|.
name|hasEffectiveStop
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|status
operator|.
name|hasEffectiveStart
argument_list|()
condition|)
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propEffectivePeriod
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|messageEffectivePeriodBefore
argument_list|(
name|status
operator|.
name|getEffectiveStop
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|else if
condition|(
operator|!
name|status
operator|.
name|hasEffectiveStop
argument_list|()
condition|)
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propEffectivePeriod
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|messageEffectivePeriodAfter
argument_list|(
name|status
operator|.
name|getEffectiveStart
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propEffectivePeriod
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|messageEffectivePeriodBetween
argument_list|(
name|status
operator|.
name|getEffectiveStart
argument_list|()
argument_list|,
name|status
operator|.
name|getEffectiveStop
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|status
operator|.
name|hasFallback
argument_list|()
condition|)
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propFallbackStatus
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|status
operator|.
name|getFallback
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|status
operator|.
name|hasCourseTypes
argument_list|()
condition|)
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propCourseTypes
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|status
operator|.
name|getCourseTypes
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iButtons
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentStatusInfo
argument_list|>
name|getStatuses
parameter_list|()
block|{
return|return
name|iStates
return|;
block|}
specifier|public
name|void
name|setStudentNote
parameter_list|(
name|Command
name|command
parameter_list|)
block|{
name|iCommand
operator|=
name|command
expr_stmt|;
name|iForm
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propNote
argument_list|()
argument_list|,
name|iNote
argument_list|)
expr_stmt|;
name|iStatusRow
operator|=
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|newStatus
argument_list|()
argument_list|,
name|iStatus
argument_list|)
expr_stmt|;
name|iStatus
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iButtons
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-note"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"send-email"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"mass-cancel"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-status"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|setStudentNote
argument_list|()
argument_list|)
expr_stmt|;
name|statusChanged
argument_list|()
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|sendStudentEmail
parameter_list|(
name|Command
name|command
parameter_list|)
block|{
name|iCommand
operator|=
name|command
expr_stmt|;
name|iForm
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|emailSubject
argument_list|()
argument_list|,
name|iSubject
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSubject
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|iSubject
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|MESSAGES
operator|.
name|defaulSubjectMassCancel
argument_list|()
argument_list|)
condition|)
name|iSubject
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|defaulSubject
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|emailCC
argument_list|()
argument_list|,
name|iCC
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|emailBody
argument_list|()
argument_list|,
name|iMessage
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iButtons
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-note"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"send-email"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"mass-cancel"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-status"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|sendStudentEmail
argument_list|()
argument_list|)
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|massCancel
parameter_list|(
name|Command
name|command
parameter_list|)
block|{
name|iCommand
operator|=
name|command
expr_stmt|;
name|iForm
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|iSubject
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|iSubject
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|MESSAGES
operator|.
name|defaulSubject
argument_list|()
argument_list|)
condition|)
name|iSubject
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|defaulSubjectMassCancel
argument_list|()
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|emailSubject
argument_list|()
argument_list|,
name|iSubject
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|emailCC
argument_list|()
argument_list|,
name|iCC
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|emailBody
argument_list|()
argument_list|,
name|iMessage
argument_list|)
expr_stmt|;
name|iStatus
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iStatus
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
if|if
condition|(
literal|"Cancelled"
operator|.
name|equalsIgnoreCase
argument_list|(
name|iStatus
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
name|iStatus
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
name|iStatusRow
operator|=
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|newStatus
argument_list|()
argument_list|,
name|iStatus
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iButtons
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-note"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"send-email"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"mass-cancel"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-status"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|massCancel
argument_list|()
argument_list|)
expr_stmt|;
name|statusChanged
argument_list|()
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setStatus
parameter_list|(
name|Command
name|command
parameter_list|)
block|{
name|iCommand
operator|=
name|command
expr_stmt|;
name|iForm
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iStatusRow
operator|=
name|iForm
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|newStatus
argument_list|()
argument_list|,
name|iStatus
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iButtons
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-note"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"send-email"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"mass-cancel"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|setEnabled
argument_list|(
literal|"set-status"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|setStudentStatus
argument_list|()
argument_list|)
expr_stmt|;
name|statusChanged
argument_list|()
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
operator|.
name|getValue
argument_list|(
name|iStatus
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
operator|.
name|getText
argument_list|()
return|;
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
operator|.
name|getText
argument_list|()
return|;
block|}
specifier|public
name|String
name|getCC
parameter_list|()
block|{
return|return
name|iCC
operator|.
name|getText
argument_list|()
return|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
operator|.
name|getText
argument_list|()
return|;
block|}
block|}
end_class

end_unit

