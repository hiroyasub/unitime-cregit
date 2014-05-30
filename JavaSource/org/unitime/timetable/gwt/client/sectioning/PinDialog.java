begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|AriaDialogBox
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
name|client
operator|.
name|page
operator|.
name|UniTimeNotifications
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
name|LoadingWidget
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
name|services
operator|.
name|SectioningService
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
name|services
operator|.
name|SectioningServiceAsync
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
name|EligibilityCheck
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
name|HorizontalPanel
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
name|PopupPanel
import|;
end_import

begin_class
specifier|public
class|class
name|PinDialog
extends|extends
name|AriaDialogBox
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
specifier|final
name|SectioningServiceAsync
name|sSectioningService
init|=
name|GWT
operator|.
name|create
argument_list|(
name|SectioningService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|AriaTextBox
name|iPin
init|=
literal|null
decl_stmt|;
specifier|private
name|AriaButton
name|iButton
init|=
literal|null
decl_stmt|;
specifier|private
name|AsyncCallback
argument_list|<
name|EligibilityCheck
argument_list|>
name|iCallback
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iOnline
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|,
name|iStudentId
decl_stmt|;
specifier|public
name|PinDialog
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogPin
argument_list|()
argument_list|)
expr_stmt|;
name|setAnimationEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setAutoHideEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setGlassEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setModal
argument_list|(
literal|true
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
name|iPin
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|HorizontalPanel
name|panel
init|=
operator|new
name|HorizontalPanel
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setSpacing
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|pin
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iPin
operator|=
operator|new
name|AriaTextBox
argument_list|()
expr_stmt|;
name|iPin
operator|.
name|setStyleName
argument_list|(
literal|"gwt-SuggestBox"
argument_list|)
expr_stmt|;
name|iPin
operator|.
name|setAriaLabel
argument_list|(
name|ARIA
operator|.
name|propPinNumber
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|iPin
argument_list|)
expr_stmt|;
name|iButton
operator|=
operator|new
name|AriaButton
argument_list|(
name|MESSAGES
operator|.
name|buttonSetPin
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|iButton
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|iButton
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
name|sendPin
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iPin
operator|.
name|addKeyUpHandler
argument_list|(
operator|new
name|KeyUpHandler
argument_list|()
block|{
specifier|public
name|void
name|onKeyUp
parameter_list|(
name|KeyUpEvent
name|event
parameter_list|)
block|{
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
name|sendPin
argument_list|()
expr_stmt|;
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
name|iCallback
operator|.
name|onFailure
argument_list|(
operator|new
name|SectioningException
argument_list|(
name|MESSAGES
operator|.
name|exceptionAuthenticationPinNotProvided
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|sendPin
parameter_list|()
block|{
specifier|final
name|String
name|pin
init|=
name|iPin
operator|.
name|getText
argument_list|()
decl_stmt|;
name|hide
argument_list|()
expr_stmt|;
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|show
argument_list|(
name|MESSAGES
operator|.
name|waitEligibilityCheck
argument_list|()
argument_list|)
expr_stmt|;
name|sSectioningService
operator|.
name|checkEligibility
argument_list|(
name|iOnline
argument_list|,
name|iSessionId
argument_list|,
name|iStudentId
argument_list|,
name|pin
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|EligibilityCheck
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|EligibilityCheck
name|result
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
operator|.
name|hasFlag
argument_list|(
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
operator|.
name|EligibilityFlag
operator|.
name|PIN_REQUIRED
argument_list|)
condition|)
block|{
if|if
condition|(
name|result
operator|.
name|hasMessage
argument_list|()
condition|)
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|result
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|MESSAGES
operator|.
name|exceptionAuthenticationPinRequired
argument_list|()
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
name|iPin
operator|.
name|selectAll
argument_list|()
expr_stmt|;
name|iPin
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
else|else
block|{
if|if
condition|(
name|result
operator|.
name|hasMessage
argument_list|()
condition|)
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|result
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iCallback
operator|.
name|onSuccess
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
name|iCallback
operator|.
name|onFailure
argument_list|(
name|caught
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|checkEligibility
parameter_list|(
name|boolean
name|online
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Long
name|studentId
parameter_list|,
name|AsyncCallback
argument_list|<
name|EligibilityCheck
argument_list|>
name|callback
parameter_list|)
block|{
name|iOnline
operator|=
name|online
expr_stmt|;
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
name|iStudentId
operator|=
name|studentId
expr_stmt|;
name|iCallback
operator|=
name|callback
expr_stmt|;
name|iPin
operator|.
name|setText
argument_list|(
literal|""
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
name|iPin
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
end_class

end_unit
