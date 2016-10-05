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
name|widgets
package|;
end_package

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
name|aria
operator|.
name|AriaTextBox
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
name|shared
operator|.
name|CourseRequestInterface
operator|.
name|RequestedCourse
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
name|dom
operator|.
name|client
operator|.
name|Style
operator|.
name|Overflow
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
name|BlurEvent
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
name|BlurHandler
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|KeyUpEvent
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
name|KeyUpHandler
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|SelectionEvent
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
name|SelectionHandler
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
name|ValueChangeEvent
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
name|ValueChangeHandler
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
name|shared
operator|.
name|HandlerRegistration
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
name|Timer
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
name|HasHorizontalAlignment
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
name|RootPanel
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
name|VerticalPanel
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
name|CourseFinderDialog
extends|extends
name|UniTimeDialogBox
implements|implements
name|CourseFinder
block|{
specifier|protected
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
specifier|protected
specifier|static
specifier|final
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
name|AriaTextBox
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|AriaButton
name|iFilterSelect
decl_stmt|;
specifier|private
name|CourseFinderTab
index|[]
name|iTabs
init|=
literal|null
decl_stmt|;
specifier|private
name|UniTimeTabPanel
name|iTabPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|VerticalPanel
name|iDialogPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Character
argument_list|,
name|Integer
argument_list|>
name|iTabAccessKeys
init|=
operator|new
name|HashMap
argument_list|<
name|Character
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|CourseFinderDialog
parameter_list|()
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-CourseFinderDialog"
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|courseSelectionDialog
argument_list|()
argument_list|)
expr_stmt|;
name|iFilter
operator|=
operator|new
name|AriaTextBox
argument_list|()
expr_stmt|;
name|iFilter
operator|.
name|setStyleName
argument_list|(
literal|"gwt-SuggestBox"
argument_list|)
expr_stmt|;
name|iFilterSelect
operator|=
operator|new
name|AriaButton
argument_list|(
name|MESSAGES
operator|.
name|buttonSelect
argument_list|()
argument_list|)
expr_stmt|;
name|iFilterSelect
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
name|CourseFinderTab
name|tab
init|=
name|getSelectedTab
argument_list|()
decl_stmt|;
name|RequestedCourse
name|rc
init|=
operator|(
name|RequestedCourse
operator|)
operator|(
name|tab
operator|==
literal|null
condition|?
literal|null
else|:
name|tab
operator|.
name|getValue
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|rc
operator|!=
literal|null
condition|)
name|iFilter
operator|.
name|setValue
argument_list|(
name|rc
operator|.
name|toString
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
name|SelectionEvent
operator|.
name|fire
argument_list|(
name|CourseFinderDialog
operator|.
name|this
argument_list|,
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|P
name|filterPanel
init|=
operator|new
name|P
argument_list|(
literal|"filter"
argument_list|)
decl_stmt|;
name|P
name|filterText
init|=
operator|new
name|P
argument_list|(
literal|"text"
argument_list|)
decl_stmt|;
name|P
name|filterButton
init|=
operator|new
name|P
argument_list|(
literal|"button"
argument_list|)
decl_stmt|;
name|filterPanel
operator|.
name|add
argument_list|(
name|filterButton
argument_list|)
expr_stmt|;
name|filterPanel
operator|.
name|add
argument_list|(
name|filterText
argument_list|)
expr_stmt|;
name|filterText
operator|.
name|add
argument_list|(
name|iFilter
argument_list|)
expr_stmt|;
name|filterButton
operator|.
name|add
argument_list|(
name|iFilterSelect
argument_list|)
expr_stmt|;
name|iDialogPanel
operator|=
operator|new
name|VerticalPanel
argument_list|()
expr_stmt|;
name|iDialogPanel
operator|.
name|setSpacing
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|iDialogPanel
operator|.
name|add
argument_list|(
name|filterPanel
argument_list|)
expr_stmt|;
name|iDialogPanel
operator|.
name|setCellHorizontalAlignment
argument_list|(
name|filterPanel
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_CENTER
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
name|RootPanel
operator|.
name|getBodyElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setOverflow
argument_list|(
name|Overflow
operator|.
name|AUTO
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
specifier|final
name|Timer
name|finderTimer
init|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|iTabs
operator|!=
literal|null
condition|)
block|{
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|rc
operator|.
name|setCourseName
argument_list|(
name|iFilter
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
name|tab
operator|.
name|setValue
argument_list|(
name|rc
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|iFilter
operator|.
name|addKeyUpHandler
argument_list|(
operator|new
name|KeyUpHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onKeyUp
parameter_list|(
name|KeyUpEvent
name|event
parameter_list|)
block|{
name|finderTimer
operator|.
name|schedule
argument_list|(
literal|250
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_ENTER
condition|)
block|{
name|CourseFinderTab
name|tab
init|=
name|getSelectedTab
argument_list|()
decl_stmt|;
name|RequestedCourse
name|rc
init|=
operator|(
name|RequestedCourse
operator|)
operator|(
name|tab
operator|==
literal|null
condition|?
literal|null
else|:
name|tab
operator|.
name|getValue
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|rc
operator|!=
literal|null
condition|)
name|iFilter
operator|.
name|setValue
argument_list|(
name|rc
operator|.
name|toString
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
name|SelectionEvent
operator|.
name|fire
argument_list|(
name|CourseFinderDialog
operator|.
name|this
argument_list|,
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|else if
condition|(
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_ESCAPE
condition|)
block|{
name|hide
argument_list|()
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|event
operator|.
name|isControlKeyDown
argument_list|()
operator|||
name|event
operator|.
name|isAltKeyDown
argument_list|()
condition|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Character
argument_list|,
name|Integer
argument_list|>
name|entry
range|:
name|iTabAccessKeys
operator|.
name|entrySet
argument_list|()
control|)
if|if
condition|(
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|Character
operator|.
name|toLowerCase
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
operator|||
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|Character
operator|.
name|toUpperCase
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
block|{
name|iTabPanel
operator|.
name|selectTab
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
name|event
operator|.
name|stopPropagation
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iTabs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
name|tab
operator|.
name|onKeyUp
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|String
argument_list|>
name|event
parameter_list|)
block|{
name|RequestedCourse
name|value
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|value
operator|.
name|setCourseName
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTabs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
name|tab
operator|.
name|setValue
argument_list|(
name|value
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|RequestedCourse
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|RequestedCourse
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|iTabs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
name|tab
operator|.
name|setValue
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|addBlurHandler
argument_list|(
operator|new
name|BlurHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onBlur
parameter_list|(
name|BlurEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isShowing
argument_list|()
condition|)
block|{
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
name|iFilter
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
block|}
block|}
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iDialogPanel
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|RequestedCourse
name|value
parameter_list|)
block|{
name|setValue
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|RequestedCourse
name|getValue
parameter_list|()
block|{
name|RequestedCourse
name|ret
init|=
operator|(
name|RequestedCourse
operator|)
name|getSelectedTab
argument_list|()
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|ret
operator|!=
literal|null
condition|)
return|return
name|ret
return|;
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|()
decl_stmt|;
name|rc
operator|.
name|setCourseName
argument_list|(
name|iFilter
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|rc
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|findCourse
parameter_list|()
block|{
name|iFilter
operator|.
name|setAriaLabel
argument_list|(
name|isAllowFreeTime
argument_list|()
condition|?
name|ARIA
operator|.
name|courseFinderFilterAllowsFreeTime
argument_list|()
else|:
name|ARIA
operator|.
name|courseFinderFilter
argument_list|()
argument_list|)
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
name|courseFinderDialogOpened
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|iTabs
operator|!=
literal|null
condition|)
for|for
control|(
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
name|tab
operator|.
name|changeTip
argument_list|()
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
name|RootPanel
operator|.
name|getBodyElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setOverflow
argument_list|(
name|Overflow
operator|.
name|HIDDEN
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
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|iFilter
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
name|boolean
name|isAllowFreeTime
parameter_list|()
block|{
if|if
condition|(
name|iTabs
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
if|if
condition|(
operator|!
name|tab
operator|.
name|isCourseSelection
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setTabs
parameter_list|(
name|CourseFinderTab
modifier|...
name|tabs
parameter_list|)
block|{
name|iTabs
operator|=
name|tabs
expr_stmt|;
if|if
condition|(
name|iTabs
operator|.
name|length
operator|==
literal|1
condition|)
block|{
if|if
condition|(
name|iTabs
index|[
literal|0
index|]
operator|.
name|asWidget
argument_list|()
operator|instanceof
name|VerticalPanel
condition|)
block|{
name|VerticalPanel
name|vp
init|=
operator|(
name|VerticalPanel
operator|)
name|iTabs
index|[
literal|0
index|]
operator|.
name|asWidget
argument_list|()
decl_stmt|;
while|while
condition|(
name|vp
operator|.
name|getWidgetCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|Widget
name|w
init|=
name|vp
operator|.
name|getWidget
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|vp
operator|.
name|remove
argument_list|(
name|w
argument_list|)
expr_stmt|;
name|iDialogPanel
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|iDialogPanel
operator|.
name|add
argument_list|(
name|iTabs
index|[
literal|0
index|]
operator|.
name|asWidget
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|iTabPanel
operator|=
operator|new
name|UniTimeTabPanel
argument_list|()
expr_stmt|;
name|int
name|tabIndex
init|=
literal|0
decl_stmt|;
for|for
control|(
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
block|{
name|iTabPanel
operator|.
name|add
argument_list|(
name|tab
operator|.
name|asWidget
argument_list|()
argument_list|,
name|tab
operator|.
name|getName
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|Character
name|ch
init|=
name|UniTimeHeaderPanel
operator|.
name|guessAccessKey
argument_list|(
name|tab
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ch
operator|!=
literal|null
condition|)
name|iTabAccessKeys
operator|.
name|put
argument_list|(
name|ch
argument_list|,
name|tabIndex
argument_list|)
expr_stmt|;
name|tabIndex
operator|++
expr_stmt|;
block|}
name|iTabPanel
operator|.
name|selectTab
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|iDialogPanel
operator|.
name|add
argument_list|(
name|iTabPanel
argument_list|)
expr_stmt|;
block|}
for|for
control|(
specifier|final
name|CourseFinderTab
name|tab
range|:
name|iTabs
control|)
block|{
name|tab
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|RequestedCourse
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|RequestedCourse
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getSource
argument_list|()
operator|.
name|equals
argument_list|(
name|tab
argument_list|)
condition|)
name|selectTab
argument_list|(
name|tab
argument_list|)
expr_stmt|;
else|else
name|tab
operator|.
name|setValue
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|iFilter
operator|.
name|setValue
argument_list|(
name|event
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|event
operator|.
name|getValue
argument_list|()
operator|.
name|toString
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|tab
operator|.
name|addSelectionHandler
argument_list|(
operator|new
name|SelectionHandler
argument_list|<
name|RequestedCourse
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSelection
parameter_list|(
name|SelectionEvent
argument_list|<
name|RequestedCourse
argument_list|>
name|event
parameter_list|)
block|{
name|iFilter
operator|.
name|setValue
argument_list|(
name|event
operator|.
name|getSelectedItem
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|event
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|)
expr_stmt|;
name|hide
argument_list|()
expr_stmt|;
name|SelectionEvent
operator|.
name|fire
argument_list|(
name|CourseFinderDialog
operator|.
name|this
argument_list|,
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|tab
operator|.
name|addResponseHandler
argument_list|(
operator|new
name|ResponseHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onResponse
parameter_list|(
name|ResponseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|CourseFinderTab
name|selected
init|=
name|getSelectedTab
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|!=
literal|null
operator|&&
name|selected
operator|.
name|isCourseSelection
argument_list|()
operator|&&
name|selected
operator|.
name|getValue
argument_list|()
operator|!=
literal|null
condition|)
return|return;
name|selectTab
argument_list|(
name|tab
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|selectTab
parameter_list|(
name|CourseFinderTab
name|tab
parameter_list|)
block|{
if|if
condition|(
name|iTabs
operator|!=
literal|null
operator|&&
name|iTabs
operator|.
name|length
operator|>
literal|1
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iTabs
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|tab
operator|.
name|equals
argument_list|(
name|iTabs
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|iTabPanel
operator|.
name|selectTab
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addSelectionHandler
parameter_list|(
name|SelectionHandler
argument_list|<
name|RequestedCourse
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|addHandler
argument_list|(
name|handler
argument_list|,
name|SelectionEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|CourseFinderTab
name|getSelectedTab
parameter_list|()
block|{
if|if
condition|(
name|iTabs
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|iTabs
operator|.
name|length
operator|==
literal|1
condition|)
return|return
name|iTabs
index|[
literal|0
index|]
return|;
else|else
return|return
name|iTabs
index|[
name|iTabPanel
operator|.
name|getSelectedTab
argument_list|()
index|]
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
specifier|final
name|RequestedCourse
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
name|iFilter
operator|.
name|setValue
argument_list|(
name|value
operator|==
literal|null
condition|?
literal|""
else|:
name|value
operator|.
name|toString
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|fireEvents
condition|)
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|this
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addValueChangeHandler
parameter_list|(
name|ValueChangeHandler
argument_list|<
name|RequestedCourse
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|addHandler
argument_list|(
name|handler
argument_list|,
name|ValueChangeEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

