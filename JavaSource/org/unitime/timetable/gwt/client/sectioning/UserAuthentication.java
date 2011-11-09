begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Lookup
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
name|UserAuthenticationProvider
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
name|JavaScriptObject
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
name|Button
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
name|Composite
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
name|DialogBox
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
name|FlexTable
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
name|PasswordTextBox
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
name|TextBox
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UserAuthentication
extends|extends
name|Composite
implements|implements
name|UserAuthenticationProvider
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
specifier|private
name|Label
name|iUserLabel
decl_stmt|;
specifier|private
name|Label
name|iHint
decl_stmt|;
specifier|private
name|Button
name|iLogIn
decl_stmt|,
name|iSkip
decl_stmt|,
name|iLookup
decl_stmt|;
specifier|private
name|TextBox
name|iUserName
decl_stmt|;
specifier|private
name|PasswordTextBox
name|iUserPassword
decl_stmt|;
specifier|private
name|DialogBox
name|iDialog
decl_stmt|;
specifier|private
name|Label
name|iError
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|UserAuthenticatedHandler
argument_list|>
name|iUserAuthenticatedHandlers
init|=
operator|new
name|ArrayList
argument_list|<
name|UserAuthenticatedHandler
argument_list|>
argument_list|()
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
specifier|static
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|sAuthenticateCallback
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iLoggedIn
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iGuest
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iLastUser
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAllowGuest
init|=
literal|false
decl_stmt|;
specifier|private
name|Command
name|iOnLoginCommand
init|=
literal|null
decl_stmt|;
specifier|public
name|UserAuthentication
parameter_list|(
name|boolean
name|allowGuest
parameter_list|)
block|{
name|iAllowGuest
operator|=
name|allowGuest
expr_stmt|;
name|iUserLabel
operator|=
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|userNotAuthenticated
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iUserLabel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-SessionSelector"
argument_list|)
expr_stmt|;
name|VerticalPanel
name|vertical
init|=
operator|new
name|VerticalPanel
argument_list|()
decl_stmt|;
name|vertical
operator|.
name|add
argument_list|(
name|iUserLabel
argument_list|)
expr_stmt|;
name|iHint
operator|=
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|userHint
argument_list|()
argument_list|)
expr_stmt|;
name|iHint
operator|.
name|setStyleName
argument_list|(
literal|"unitime-Hint"
argument_list|)
expr_stmt|;
name|vertical
operator|.
name|add
argument_list|(
name|iHint
argument_list|)
expr_stmt|;
name|iDialog
operator|=
operator|new
name|DialogBox
argument_list|()
expr_stmt|;
name|iDialog
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|dialogAuthenticate
argument_list|()
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setAnimationEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setAutoHideEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setGlassEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|FlexTable
name|grid
init|=
operator|new
name|FlexTable
argument_list|()
decl_stmt|;
name|grid
operator|.
name|setCellPadding
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|grid
operator|.
name|setCellSpacing
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|grid
operator|.
name|setText
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
name|MESSAGES
operator|.
name|username
argument_list|()
argument_list|)
expr_stmt|;
name|iUserName
operator|=
operator|new
name|TextBox
argument_list|()
expr_stmt|;
name|iUserName
operator|.
name|setStyleName
argument_list|(
literal|"gwt-SuggestBox"
argument_list|)
expr_stmt|;
name|grid
operator|.
name|setWidget
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|,
name|iUserName
argument_list|)
expr_stmt|;
name|grid
operator|.
name|setText
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|,
name|MESSAGES
operator|.
name|password
argument_list|()
argument_list|)
expr_stmt|;
name|iUserPassword
operator|=
operator|new
name|PasswordTextBox
argument_list|()
expr_stmt|;
name|iUserPassword
operator|.
name|setStyleName
argument_list|(
literal|"gwt-SuggestBox"
argument_list|)
expr_stmt|;
name|grid
operator|.
name|setWidget
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
name|iUserPassword
argument_list|)
expr_stmt|;
name|iError
operator|=
operator|new
name|Label
argument_list|()
expr_stmt|;
name|iError
operator|.
name|setStyleName
argument_list|(
literal|"unitime-ErrorMessage"
argument_list|)
expr_stmt|;
name|iError
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|grid
operator|.
name|getFlexCellFormatter
argument_list|()
operator|.
name|setColSpan
argument_list|(
literal|2
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|grid
operator|.
name|setWidget
argument_list|(
literal|2
argument_list|,
literal|0
argument_list|,
name|iError
argument_list|)
expr_stmt|;
name|HorizontalPanel
name|buttonPanelWithPad
init|=
operator|new
name|HorizontalPanel
argument_list|()
decl_stmt|;
name|buttonPanelWithPad
operator|.
name|setWidth
argument_list|(
literal|"100%"
argument_list|)
expr_stmt|;
name|HorizontalPanel
name|buttonPanel
init|=
operator|new
name|HorizontalPanel
argument_list|()
decl_stmt|;
name|buttonPanel
operator|.
name|setSpacing
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|buttonPanelWithPad
operator|.
name|add
argument_list|(
name|buttonPanel
argument_list|)
expr_stmt|;
name|buttonPanelWithPad
operator|.
name|setCellHorizontalAlignment
argument_list|(
name|buttonPanel
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_RIGHT
argument_list|)
expr_stmt|;
name|grid
operator|.
name|getFlexCellFormatter
argument_list|()
operator|.
name|setColSpan
argument_list|(
literal|3
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|grid
operator|.
name|setWidget
argument_list|(
literal|3
argument_list|,
literal|0
argument_list|,
name|buttonPanelWithPad
argument_list|)
expr_stmt|;
name|iLogIn
operator|=
operator|new
name|Button
argument_list|(
name|MESSAGES
operator|.
name|buttonUserLogin
argument_list|()
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|iLogIn
argument_list|)
expr_stmt|;
name|iSkip
operator|=
operator|new
name|Button
argument_list|(
name|MESSAGES
operator|.
name|buttonUserSkip
argument_list|()
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|iSkip
argument_list|)
expr_stmt|;
name|iSkip
operator|.
name|setVisible
argument_list|(
name|iAllowGuest
argument_list|)
expr_stmt|;
name|Lookup
operator|.
name|getInstance
argument_list|()
operator|.
name|setCallback
argument_list|(
name|createLookupCallback
argument_list|()
argument_list|)
expr_stmt|;
name|iLookup
operator|=
operator|new
name|Button
argument_list|(
name|MESSAGES
operator|.
name|buttonUserLookup
argument_list|()
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|iLookup
argument_list|)
expr_stmt|;
name|iLookup
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iSkip
operator|.
name|addClickHandler
argument_list|(
operator|new
name|ClickHandler
argument_list|()
block|{
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|logIn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iLogIn
operator|.
name|addClickHandler
argument_list|(
operator|new
name|ClickHandler
argument_list|()
block|{
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|logIn
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iLookup
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
name|iDialog
operator|.
name|hide
argument_list|()
expr_stmt|;
name|Lookup
operator|.
name|getInstance
argument_list|()
operator|.
name|center
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iUserName
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
name|iUserPassword
operator|.
name|selectAll
argument_list|()
expr_stmt|;
name|iUserPassword
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
if|if
condition|(
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_ESCAPE
operator|&&
name|iAllowGuest
condition|)
block|{
name|logIn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iUserPassword
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
name|logIn
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_ESCAPE
operator|&&
name|iAllowGuest
condition|)
block|{
name|logIn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|add
argument_list|(
name|grid
argument_list|)
expr_stmt|;
name|ClickHandler
name|ch
init|=
operator|new
name|ClickHandler
argument_list|()
block|{
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
name|iLoggedIn
condition|)
name|logOut
argument_list|()
expr_stmt|;
else|else
name|authenticate
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|iUserLabel
operator|.
name|addClickHandler
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|iHint
operator|.
name|addClickHandler
argument_list|(
name|ch
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|vertical
argument_list|)
expr_stmt|;
name|sAuthenticateCallback
operator|=
operator|new
name|AsyncCallback
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
name|iError
operator|.
name|setText
argument_list|(
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|iError
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iUserName
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iUserPassword
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iLogIn
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSkip
operator|.
name|setEnabled
argument_list|(
literal|true
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
name|iUserName
operator|.
name|selectAll
argument_list|()
expr_stmt|;
name|iUserName
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
specifier|public
name|void
name|onSuccess
parameter_list|(
name|String
name|result
parameter_list|)
block|{
name|iUserName
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iUserPassword
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iLogIn
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSkip
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iError
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iDialog
operator|.
name|hide
argument_list|()
expr_stmt|;
name|iUserLabel
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|userLabel
argument_list|(
name|result
argument_list|)
argument_list|)
expr_stmt|;
name|iLastUser
operator|=
name|result
expr_stmt|;
name|iHint
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|userHintLogout
argument_list|()
argument_list|)
expr_stmt|;
name|iLoggedIn
operator|=
literal|true
expr_stmt|;
name|iGuest
operator|=
literal|false
expr_stmt|;
name|UserAuthenticatedEvent
name|e
init|=
operator|new
name|UserAuthenticatedEvent
argument_list|(
name|iGuest
argument_list|)
decl_stmt|;
for|for
control|(
name|UserAuthenticatedHandler
name|h
range|:
name|iUserAuthenticatedHandlers
control|)
name|h
operator|.
name|onLogIn
argument_list|(
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|iOnLoginCommand
operator|!=
literal|null
condition|)
name|iOnLoginCommand
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
block|}
specifier|public
name|void
name|setAllowLookup
parameter_list|(
name|boolean
name|allow
parameter_list|)
block|{
name|iLookup
operator|.
name|setVisible
argument_list|(
name|allow
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowing
parameter_list|()
block|{
return|return
name|iDialog
operator|.
name|isShowing
argument_list|()
return|;
block|}
specifier|public
name|void
name|authenticate
parameter_list|()
block|{
name|iError
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iDialog
operator|.
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
name|iUserName
operator|.
name|selectAll
argument_list|()
expr_stmt|;
name|iUserName
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
specifier|public
name|void
name|authenticated
parameter_list|(
name|String
name|user
parameter_list|)
block|{
if|if
condition|(
name|iDialog
operator|.
name|isShowing
argument_list|()
condition|)
name|iDialog
operator|.
name|hide
argument_list|()
expr_stmt|;
name|iLoggedIn
operator|=
literal|true
expr_stmt|;
name|iGuest
operator|=
name|MESSAGES
operator|.
name|userGuest
argument_list|()
operator|.
name|equals
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|iUserLabel
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|userLabel
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|iLastUser
operator|=
name|user
expr_stmt|;
name|iHint
operator|.
name|setText
argument_list|(
name|iGuest
condition|?
name|MESSAGES
operator|.
name|userHintLogin
argument_list|()
else|:
name|MESSAGES
operator|.
name|userHintLogout
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|logIn
parameter_list|(
name|boolean
name|guest
parameter_list|)
block|{
name|iError
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|guest
condition|)
block|{
name|sSectioningService
operator|.
name|logOut
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
block|}
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
name|iLoggedIn
operator|=
literal|true
expr_stmt|;
name|iGuest
operator|=
literal|true
expr_stmt|;
name|iDialog
operator|.
name|hide
argument_list|()
expr_stmt|;
name|iUserLabel
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|userLabel
argument_list|(
name|MESSAGES
operator|.
name|userGuest
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iHint
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|userHintLogin
argument_list|()
argument_list|)
expr_stmt|;
name|iLastUser
operator|=
name|MESSAGES
operator|.
name|userGuest
argument_list|()
expr_stmt|;
name|UserAuthenticatedEvent
name|e
init|=
operator|new
name|UserAuthenticatedEvent
argument_list|(
name|iGuest
argument_list|)
decl_stmt|;
for|for
control|(
name|UserAuthenticatedHandler
name|h
range|:
name|iUserAuthenticatedHandlers
control|)
name|h
operator|.
name|onLogIn
argument_list|(
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|iOnLoginCommand
operator|!=
literal|null
condition|)
name|iOnLoginCommand
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return;
block|}
name|iUserName
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iUserPassword
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iLogIn
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iSkip
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sSectioningService
operator|.
name|logIn
argument_list|(
name|iUserName
operator|.
name|getText
argument_list|()
argument_list|,
name|iUserPassword
operator|.
name|getText
argument_list|()
argument_list|,
name|sAuthenticateCallback
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|logOut
parameter_list|()
block|{
name|sSectioningService
operator|.
name|logOut
argument_list|(
operator|new
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
block|}
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
name|UserAuthenticatedEvent
name|e
init|=
operator|new
name|UserAuthenticatedEvent
argument_list|(
name|iGuest
argument_list|)
decl_stmt|;
name|iHint
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|userHintClose
argument_list|()
argument_list|)
expr_stmt|;
name|iUserLabel
operator|.
name|setText
argument_list|(
name|MESSAGES
operator|.
name|userNotAuthenticated
argument_list|()
argument_list|)
expr_stmt|;
name|iLastUser
operator|=
literal|null
expr_stmt|;
for|for
control|(
name|UserAuthenticatedHandler
name|h
range|:
name|iUserAuthenticatedHandlers
control|)
name|h
operator|.
name|onLogOut
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|iLoggedIn
operator|=
literal|false
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|iLastUser
return|;
block|}
specifier|public
name|void
name|setUser
parameter_list|(
specifier|final
name|String
name|user
parameter_list|,
specifier|final
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
block|{
name|iOnLoginCommand
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|authenticate
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|user
operator|.
name|equals
argument_list|(
name|iLastUser
argument_list|)
condition|)
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|user
operator|.
name|equals
argument_list|(
name|MESSAGES
operator|.
name|userGuest
argument_list|()
argument_list|)
condition|)
block|{
name|logIn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|callback
operator|.
name|onSuccess
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iOnLoginCommand
operator|=
operator|new
name|Command
argument_list|()
block|{
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
name|user
operator|.
name|equals
argument_list|(
name|getUser
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iUserLabel
operator|.
name|setText
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|authenticate
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|UserAuthenticatedEvent
block|{
specifier|private
name|boolean
name|iGuest
init|=
literal|false
decl_stmt|;
specifier|private
name|UserAuthenticatedEvent
parameter_list|(
name|boolean
name|guest
parameter_list|)
block|{
name|iGuest
operator|=
name|guest
expr_stmt|;
block|}
specifier|public
name|boolean
name|isGuest
parameter_list|()
block|{
return|return
name|iGuest
return|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|UserAuthenticatedHandler
block|{
specifier|public
name|void
name|onLogIn
parameter_list|(
name|UserAuthenticatedEvent
name|event
parameter_list|)
function_decl|;
specifier|public
name|void
name|onLogOut
parameter_list|(
name|UserAuthenticatedEvent
name|event
parameter_list|)
function_decl|;
block|}
specifier|public
name|void
name|addUserAuthenticatedHandler
parameter_list|(
name|UserAuthenticatedHandler
name|h
parameter_list|)
block|{
name|iUserAuthenticatedHandlers
operator|.
name|add
argument_list|(
name|h
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|personFound
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|sSectioningService
operator|.
name|logIn
argument_list|(
literal|"LOOKUP"
argument_list|,
name|externalUniqueId
argument_list|,
name|sAuthenticateCallback
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|native
name|JavaScriptObject
name|createLookupCallback
parameter_list|()
comment|/*-{ 		return function(person) { 			@org.unitime.timetable.gwt.client.sectioning.UserAuthentication::personFound(Ljava/lang/String;)(person[0]); 	    }; 	 }-*/
function_decl|;
block|}
end_class

end_unit

