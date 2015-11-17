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
name|shared
operator|.
name|DegreePlanInterface
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
name|DegreePlansSelectionDialog
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
name|DegreePlanInterface
argument_list|>
name|iTable
decl_stmt|;
specifier|private
name|UniTimeHeaderPanel
name|iFooter
decl_stmt|;
specifier|private
name|String
name|iLastSubmit
init|=
literal|null
decl_stmt|;
specifier|public
name|DegreePlansSelectionDialog
parameter_list|()
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
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
name|onSubmit
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
name|dialogSelectDegreePlan
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
literal|"unitime-SelectDegreePlan"
argument_list|)
expr_stmt|;
name|iTable
operator|=
operator|new
name|UniTimeTable
argument_list|<
name|DegreePlanInterface
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
name|MESSAGES
operator|.
name|colDegreePlanName
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
name|colDegreePlanSchool
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
name|colDegreePlanDegree
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
name|colDegreePlanTrackStatus
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
name|colDegreePlanLastModified
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
name|iFooter
operator|.
name|addButton
argument_list|(
literal|"select"
argument_list|,
name|MESSAGES
operator|.
name|buttonDegreePlanSelect
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
name|onSubmit
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
name|buttonDegreePlanCancel
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
name|DegreePlanInterface
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
name|DegreePlanInterface
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
name|onSubmit
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
name|DegreePlanInterface
argument_list|>
name|plans
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
literal|1
decl_stmt|;
for|for
control|(
name|DegreePlanInterface
name|plan
range|:
name|plans
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
name|row
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|plan
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|plan
operator|.
name|getName
argument_list|()
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
name|plan
operator|.
name|getSchool
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|plan
operator|.
name|getSchool
argument_list|()
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
name|plan
operator|.
name|getDegree
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|plan
operator|.
name|getDegree
argument_list|()
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
name|plan
operator|.
name|getTrackingStatus
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|plan
operator|.
name|getTrackingStatus
argument_list|()
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
name|plan
operator|.
name|getLastModified
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
name|plan
operator|.
name|getLastModified
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|plan
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|iLastSubmit
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
name|plan
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
block|}
specifier|public
name|void
name|onSubmit
parameter_list|(
name|DegreePlanInterface
name|plan
parameter_list|)
block|{
name|iLastSubmit
operator|=
name|plan
operator|.
name|getId
argument_list|()
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
block|}
block|}
block|}
block|}
end_class

end_unit

