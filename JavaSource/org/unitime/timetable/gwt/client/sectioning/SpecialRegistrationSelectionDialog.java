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
name|List
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
name|ServerDateTimeFormat
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
name|UniTimeTable
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
name|UniTimeTableHeader
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
name|GwtAriaMessages
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
name|SpecialRegistrationInterface
operator|.
name|RetrieveSpecialRegistrationResponse
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
name|dom
operator|.
name|client
operator|.
name|KeyCodes
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
name|i18n
operator|.
name|client
operator|.
name|DateTimeFormat
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
name|Event
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
name|Event
operator|.
name|NativePreviewEvent
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
name|Widget
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SpecialRegistrationSelectionDialog
extends|extends
name|UniTimeDialogBox
block|{
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
specifier|protected
specifier|static
specifier|final
name|GwtAriaMessages
name|ARIA
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtAriaMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|DateTimeFormat
name|sModifiedDateFormat
init|=
name|ServerDateTimeFormat
operator|.
name|getFormat
argument_list|(
name|CONSTANTS
operator|.
name|timeStampFormat
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
name|SimpleForm
name|iForm
decl_stmt|;
specifier|private
name|UniTimeTable
argument_list|<
name|RetrieveSpecialRegistrationResponse
argument_list|>
name|iTable
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iFooter
decl_stmt|;
specifier|private
name|boolean
name|iSpecReg
init|=
literal|false
decl_stmt|;
specifier|public
name|SpecialRegistrationSelectionDialog
parameter_list|(
name|boolean
name|specReg
parameter_list|)
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iSpecReg
operator|=
name|specReg
expr_stmt|;
name|setEscapeToHide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setEnterToSubmit
argument_list|(
operator|new
name|Command
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
if|if
condition|(
name|iTable
operator|.
name|getSelectedRow
argument_list|()
operator|>
literal|0
condition|)
name|doSubmit
argument_list|(
name|iTable
operator|.
name|getData
argument_list|(
name|iTable
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogSpecialRegistrations
argument_list|()
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
name|addStyleName
argument_list|(
literal|"unitime-SpecialRegistrations"
argument_list|)
expr_stmt|;
name|iTable
operator|=
operator|new
name|UniTimeTable
argument_list|<
name|RetrieveSpecialRegistrationResponse
argument_list|>
argument_list|()
expr_stmt|;
name|iTable
operator|.
name|addStyleName
argument_list|(
literal|"plans-table"
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|setAllowSelection
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|setAllowMultiSelect
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iForm
operator|.
name|addRow
argument_list|(
name|iTable
argument_list|)
expr_stmt|;
name|iFooter
operator|=
operator|new
name|UniTimeHeaderPanel
argument_list|()
expr_stmt|;
name|iForm
operator|.
name|addBottomRow
argument_list|(
name|iFooter
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iForm
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|UniTimeTableHeader
argument_list|>
name|header
init|=
operator|new
name|ArrayList
argument_list|<
name|UniTimeTableHeader
argument_list|>
argument_list|()
decl_stmt|;
name|header
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colSpecRegSubmitted
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|header
operator|.
name|add
argument_list|(
operator|new
name|UniTimeTableHeader
argument_list|(
name|MESSAGES
operator|.
name|colSpecRegName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iTable
operator|.
name|addRow
argument_list|(
literal|null
argument_list|,
name|header
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSpecReg
condition|)
block|{
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"create"
argument_list|,
name|MESSAGES
operator|.
name|buttonSpecRegCreateNew
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
name|doSubmit
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"select"
argument_list|,
name|MESSAGES
operator|.
name|buttonSpecRegSelect
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
if|if
condition|(
name|iTable
operator|.
name|getSelectedRow
argument_list|()
operator|>
literal|0
condition|)
name|doSubmit
argument_list|(
name|iTable
operator|.
name|getData
argument_list|(
name|iTable
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"cancel"
argument_list|,
name|MESSAGES
operator|.
name|buttonSpecRegCancel
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
name|iTable
operator|.
name|addMouseClickListener
argument_list|(
operator|new
name|UniTimeTable
operator|.
name|MouseClickListener
argument_list|<
name|RetrieveSpecialRegistrationResponse
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseClick
parameter_list|(
name|UniTimeTable
operator|.
name|TableEvent
argument_list|<
name|RetrieveSpecialRegistrationResponse
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getData
argument_list|()
operator|!=
literal|null
condition|)
name|doSubmit
argument_list|(
name|event
operator|.
name|getData
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|open
parameter_list|(
name|List
argument_list|<
name|RetrieveSpecialRegistrationResponse
argument_list|>
name|registrations
parameter_list|,
name|String
name|selectRequestId
parameter_list|)
block|{
name|iTable
operator|.
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|int
name|select
init|=
operator|-
literal|1
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|registrations
argument_list|)
expr_stmt|;
for|for
control|(
name|RetrieveSpecialRegistrationResponse
name|reg
range|:
name|registrations
control|)
block|{
name|List
argument_list|<
name|Widget
argument_list|>
name|row
init|=
operator|new
name|ArrayList
argument_list|<
name|Widget
argument_list|>
argument_list|()
decl_stmt|;
name|P
name|p
init|=
operator|new
name|P
argument_list|(
literal|"icons"
argument_list|)
decl_stmt|;
if|if
condition|(
name|reg
operator|.
name|isCanEnroll
argument_list|()
condition|)
block|{
name|Image
name|canEnroll
init|=
operator|new
name|Image
argument_list|(
name|RESOURCES
operator|.
name|specRegCanEnroll
argument_list|()
argument_list|)
decl_stmt|;
name|canEnroll
operator|.
name|setTitle
argument_list|(
name|MESSAGES
operator|.
name|hintSpecRegCanEnroll
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|canEnroll
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|reg
operator|.
name|isCanSubmit
argument_list|()
condition|)
block|{
name|Image
name|canNotSubmit
init|=
operator|new
name|Image
argument_list|(
name|RESOURCES
operator|.
name|specRegCanNotSubmit
argument_list|()
argument_list|)
decl_stmt|;
name|canNotSubmit
operator|.
name|setTitle
argument_list|(
name|MESSAGES
operator|.
name|hintSpecRegCanNotSubmit
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|canNotSubmit
argument_list|)
expr_stmt|;
block|}
name|row
operator|.
name|add
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|reg
operator|.
name|getSubmitDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sModifiedDateFormat
operator|.
name|format
argument_list|(
name|reg
operator|.
name|getSubmitDate
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|reg
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|reg
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|reg
operator|.
name|getRequestId
argument_list|()
operator|.
name|equals
argument_list|(
name|selectRequestId
argument_list|)
condition|)
name|select
operator|=
name|iTable
operator|.
name|getRowCount
argument_list|()
expr_stmt|;
name|iTable
operator|.
name|addRow
argument_list|(
name|reg
argument_list|,
name|row
argument_list|)
expr_stmt|;
block|}
name|iTable
operator|.
name|setSelected
argument_list|(
name|select
operator|<
literal|0
condition|?
literal|1
else|:
name|select
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|center
argument_list|()
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
name|iFooter
operator|.
name|setFocus
argument_list|(
literal|"select"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|updateAriaStatus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|show
parameter_list|()
block|{
name|super
operator|.
name|show
argument_list|()
expr_stmt|;
name|updateAriaStatus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|doSubmit
parameter_list|(
name|RetrieveSpecialRegistrationResponse
name|reg
parameter_list|)
block|{
if|if
condition|(
name|reg
operator|!=
literal|null
condition|)
name|AriaStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|setText
argument_list|(
name|ARIA
operator|.
name|selectedSpecReg
argument_list|(
name|reg
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|onPreviewNativeEvent
parameter_list|(
name|NativePreviewEvent
name|event
parameter_list|)
block|{
name|super
operator|.
name|onPreviewNativeEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getTypeInt
argument_list|()
operator|==
name|Event
operator|.
name|ONKEYDOWN
condition|)
block|{
if|if
condition|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_UP
condition|)
block|{
name|int
name|row
init|=
name|iTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
name|iTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|row
operator|--
expr_stmt|;
if|if
condition|(
name|row
operator|<=
literal|0
condition|)
name|row
operator|=
name|iTable
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
expr_stmt|;
name|iTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|updateAriaStatus
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_DOWN
condition|)
block|{
name|int
name|row
init|=
name|iTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
name|iTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|row
operator|++
expr_stmt|;
if|if
condition|(
name|row
operator|>=
name|iTable
operator|.
name|getRowCount
argument_list|()
condition|)
name|row
operator|=
literal|1
expr_stmt|;
name|iTable
operator|.
name|setSelected
argument_list|(
name|row
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|updateAriaStatus
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|void
name|updateAriaStatus
parameter_list|(
name|boolean
name|justOpened
parameter_list|)
block|{
name|String
name|text
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|justOpened
condition|)
name|text
operator|=
name|ARIA
operator|.
name|showingSpecRegs
argument_list|(
name|iTable
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|int
name|row
init|=
name|iTable
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
name|RetrieveSpecialRegistrationResponse
name|reg
init|=
name|iTable
operator|.
name|getData
argument_list|(
name|row
argument_list|)
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
operator|&&
name|reg
operator|!=
literal|null
condition|)
block|{
name|text
operator|+=
operator|(
name|text
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" "
operator|)
operator|+
name|ARIA
operator|.
name|showingSpecReg
argument_list|(
name|row
argument_list|,
name|iTable
operator|.
name|getRowCount
argument_list|()
operator|-
literal|1
argument_list|,
name|reg
operator|.
name|getDescription
argument_list|()
argument_list|,
name|reg
operator|.
name|getSubmitDate
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|AriaStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

