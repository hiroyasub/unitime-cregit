begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|sectioning
operator|.
name|UserAuthentication
operator|.
name|UserAuthenticatedEvent
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
name|AcademicSessionProvider
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
name|dom
operator|.
name|client
operator|.
name|Style
operator|.
name|Unit
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
name|Window
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
name|Grid
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
name|RootPanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentSectioningPage
extends|extends
name|Composite
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
specifier|final
name|SectioningServiceAsync
name|iSectioningService
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
specifier|public
specifier|static
enum|enum
name|Mode
block|{
name|SECTIONING
argument_list|(
literal|true
argument_list|)
block|,
name|REQUESTS
argument_list|(
literal|false
argument_list|)
block|;
name|boolean
name|iSectioning
decl_stmt|;
specifier|private
name|Mode
parameter_list|(
name|boolean
name|isSectioning
parameter_list|)
block|{
name|iSectioning
operator|=
name|isSectioning
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSectioning
parameter_list|()
block|{
return|return
name|iSectioning
return|;
block|}
block|}
empty_stmt|;
specifier|public
name|StudentSectioningPage
parameter_list|(
specifier|final
name|Mode
name|mode
parameter_list|)
block|{
name|Grid
name|titlePanel
init|=
operator|new
name|Grid
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
decl_stmt|;
name|titlePanel
operator|.
name|getCellFormatter
argument_list|()
operator|.
name|setWidth
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|"33%"
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|getCellFormatter
argument_list|()
operator|.
name|setWidth
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|,
literal|"34%"
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|getCellFormatter
argument_list|()
operator|.
name|setWidth
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|,
literal|"33%"
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|getCellFormatter
argument_list|()
operator|.
name|setHorizontalAlignment
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_CENTER
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|getCellFormatter
argument_list|()
operator|.
name|setHorizontalAlignment
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|,
name|HasHorizontalAlignment
operator|.
name|ALIGN_RIGHT
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|getCellFormatter
argument_list|()
operator|.
name|getElement
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
operator|.
name|getStyle
argument_list|()
operator|.
name|setPaddingLeft
argument_list|(
literal|10
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|titlePanel
operator|.
name|setHTML
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|"&nbsp;"
argument_list|)
expr_stmt|;
specifier|final
name|UserAuthentication
name|userAuthentication
init|=
operator|new
name|UserAuthentication
argument_list|(
name|mode
operator|.
name|isSectioning
argument_list|()
condition|?
operator|!
name|CONSTANTS
operator|.
name|isAuthenticationRequired
argument_list|()
else|:
literal|false
argument_list|)
decl_stmt|;
name|titlePanel
operator|.
name|setWidget
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|,
name|userAuthentication
argument_list|)
expr_stmt|;
if|if
condition|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"student"
argument_list|)
operator|==
literal|null
condition|)
name|iSectioningService
operator|.
name|whoAmI
argument_list|(
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
name|userAuthentication
operator|.
name|authenticate
argument_list|()
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
if|if
condition|(
name|MESSAGES
operator|.
name|userGuest
argument_list|()
operator|.
name|equals
argument_list|(
name|result
argument_list|)
condition|)
block|{
comment|// user is guest (i.e., not truly authenticated)
if|if
condition|(
operator|!
name|mode
operator|.
name|isSectioning
argument_list|()
operator|||
name|CONSTANTS
operator|.
name|isAuthenticationRequired
argument_list|()
operator|||
name|CONSTANTS
operator|.
name|tryAuthenticationWhenGuest
argument_list|()
condition|)
name|userAuthentication
operator|.
name|authenticate
argument_list|()
expr_stmt|;
else|else
name|userAuthentication
operator|.
name|authenticated
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|userAuthentication
operator|.
name|authenticated
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
specifier|final
name|AcademicSessionSelector
name|sessionSelector
init|=
operator|new
name|AcademicSessionSelector
argument_list|(
name|mode
argument_list|)
decl_stmt|;
name|titlePanel
operator|.
name|setWidget
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|,
name|sessionSelector
argument_list|)
expr_stmt|;
name|iSectioningService
operator|.
name|isAdminOrAdvisor
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
if|if
condition|(
name|result
condition|)
block|{
name|userAuthentication
operator|.
name|setAllowLookup
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"session"
argument_list|)
operator|!=
literal|null
condition|)
name|sessionSelector
operator|.
name|selectSession
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"session"
argument_list|)
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
if|if
condition|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"student"
argument_list|)
operator|!=
literal|null
condition|)
name|UserAuthentication
operator|.
name|personFound
argument_list|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"student"
argument_list|)
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
name|RootPanel
operator|.
name|get
argument_list|(
literal|"UniTimeGWT:Header"
argument_list|)
operator|.
name|clear
argument_list|()
expr_stmt|;
name|RootPanel
operator|.
name|get
argument_list|(
literal|"UniTimeGWT:Header"
argument_list|)
operator|.
name|add
argument_list|(
name|titlePanel
argument_list|)
expr_stmt|;
specifier|final
name|StudentSectioningWidget
name|widget
init|=
operator|new
name|StudentSectioningWidget
argument_list|(
literal|true
argument_list|,
name|sessionSelector
argument_list|,
name|userAuthentication
argument_list|,
name|mode
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|initWidget
argument_list|(
name|widget
argument_list|)
expr_stmt|;
name|userAuthentication
operator|.
name|addUserAuthenticatedHandler
argument_list|(
operator|new
name|UserAuthentication
operator|.
name|UserAuthenticatedHandler
argument_list|()
block|{
specifier|public
name|void
name|onLogIn
parameter_list|(
name|UserAuthenticatedEvent
name|event
parameter_list|)
block|{
if|if
condition|(
operator|!
name|mode
operator|.
name|isSectioning
argument_list|()
condition|)
name|sessionSelector
operator|.
name|selectSession
argument_list|(
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|sessionSelector
operator|.
name|selectSession
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|onLogOut
parameter_list|(
name|UserAuthenticatedEvent
name|event
parameter_list|)
block|{
if|if
condition|(
operator|!
name|event
operator|.
name|isGuest
argument_list|()
condition|)
block|{
name|widget
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// sessionSelector.selectSession(null);
block|}
name|userAuthentication
operator|.
name|authenticate
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|sessionSelector
operator|.
name|addAcademicSessionChangeHandler
argument_list|(
operator|new
name|AcademicSessionProvider
operator|.
name|AcademicSessionChangeHandler
argument_list|()
block|{
specifier|public
name|void
name|onAcademicSessionChange
parameter_list|(
name|AcademicSessionProvider
operator|.
name|AcademicSessionChangeEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isChanged
argument_list|()
condition|)
name|widget
operator|.
name|clear
argument_list|()
expr_stmt|;
name|widget
operator|.
name|checkEligibility
argument_list|(
name|event
operator|.
name|getNewAcademicSessionId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|userAuthentication
operator|.
name|setLookupOptions
argument_list|(
literal|"mustHaveExternalId,source=students,session="
operator|+
name|event
operator|.
name|getNewAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|Window
operator|.
name|Location
operator|.
name|getParameter
argument_list|(
literal|"session"
argument_list|)
operator|==
literal|null
condition|)
name|iSectioningService
operator|.
name|lastAcademicSession
argument_list|(
name|mode
operator|.
name|isSectioning
argument_list|()
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|AcademicSessionProvider
operator|.
name|AcademicSessionInfo
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
if|if
condition|(
operator|!
name|userAuthentication
operator|.
name|isShowing
argument_list|()
condition|)
name|sessionSelector
operator|.
name|selectSession
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|onSuccess
parameter_list|(
name|AcademicSessionProvider
operator|.
name|AcademicSessionInfo
name|result
parameter_list|)
block|{
name|sessionSelector
operator|.
name|selectSession
argument_list|(
name|result
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|widget
operator|.
name|checkEligibility
argument_list|(
name|sessionSelector
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|userAuthentication
operator|.
name|setLookupOptions
argument_list|(
literal|"mustHaveExternalId,source=students,session="
operator|+
name|sessionSelector
operator|.
name|getAcademicSessionId
argument_list|()
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

