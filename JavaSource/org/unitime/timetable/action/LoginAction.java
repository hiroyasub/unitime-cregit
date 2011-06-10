begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|Subject
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|login
operator|.
name|LoginContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|login
operator|.
name|LoginException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|Web
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
name|ApplicationProperties
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
name|authenticate
operator|.
name|jaas
operator|.
name|LoginConfiguration
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
name|authenticate
operator|.
name|jaas
operator|.
name|UserPasswordHandler
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
name|model
operator|.
name|ApplicationConfig
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
name|util
operator|.
name|Constants
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
name|util
operator|.
name|LoginManager
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts Creation date: 01-16-2007  *   * XDoclet definition:  *   * @struts.action  */
end_comment

begin_class
specifier|public
class|class
name|LoginAction
extends|extends
name|Action
block|{
comment|/* 	 * Generated Methods 	 */
comment|/** 	 * Method execute 	 *  	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|cs
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"cs"
argument_list|)
decl_stmt|;
name|String
name|username
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"username"
argument_list|)
decl_stmt|;
name|String
name|password
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"password"
argument_list|)
decl_stmt|;
name|String
name|menu
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"menu"
argument_list|)
decl_stmt|;
comment|// Check form is submitted
if|if
condition|(
name|cs
operator|==
literal|null
operator|||
operator|!
name|cs
operator|.
name|equals
argument_list|(
literal|"login"
argument_list|)
condition|)
block|{
name|String
name|m
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"message"
argument_list|)
decl_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.login_url"
argument_list|)
operator|+
operator|(
name|m
operator|==
literal|null
condition|?
literal|""
else|:
literal|"?m="
operator|+
name|m
operator|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|username
operator|==
literal|null
operator|||
name|username
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
name|password
operator|==
literal|null
operator|||
name|password
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.login_url"
argument_list|)
operator|+
literal|"?e=1"
operator|+
literal|"&menu="
operator|+
name|menu
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|Date
name|attemptDateTime
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
if|if
condition|(
name|LoginManager
operator|.
name|isUserLockedOut
argument_list|(
name|username
argument_list|,
name|attemptDateTime
argument_list|)
condition|)
block|{
comment|// count this attempt, allows for slowing down of responses if the user is flooding the system with failed requests
name|LoginManager
operator|.
name|addFailedLoginAttempt
argument_list|(
name|username
argument_list|,
name|attemptDateTime
argument_list|)
expr_stmt|;
comment|//TODO figure out what the appropriate message is
name|response
operator|.
name|sendRedirect
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.login_url"
argument_list|)
operator|+
literal|"?e=4"
operator|+
literal|"&menu="
operator|+
name|menu
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
try|try
block|{
name|UserPasswordHandler
name|handler
init|=
operator|new
name|UserPasswordHandler
argument_list|(
name|username
argument_list|,
name|password
argument_list|)
decl_stmt|;
name|LoginContext
name|lc
init|=
operator|new
name|LoginContext
argument_list|(
literal|"Timetabling"
argument_list|,
operator|new
name|Subject
argument_list|()
argument_list|,
name|handler
argument_list|,
operator|new
name|LoginConfiguration
argument_list|()
argument_list|)
decl_stmt|;
name|lc
operator|.
name|login
argument_list|()
expr_stmt|;
name|Set
name|creds
init|=
name|lc
operator|.
name|getSubject
argument_list|()
operator|.
name|getPublicCredentials
argument_list|()
decl_stmt|;
if|if
condition|(
name|creds
operator|==
literal|null
operator|||
name|creds
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|LoginManager
operator|.
name|addFailedLoginAttempt
argument_list|(
name|username
argument_list|,
name|attemptDateTime
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.login_url"
argument_list|)
operator|+
literal|"?e=2"
operator|+
literal|"&menu="
operator|+
name|menu
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|creds
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
name|o
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|User
condition|)
block|{
name|User
name|user
init|=
operator|(
name|User
operator|)
name|o
decl_stmt|;
comment|// Set Session Variables
name|HttpSession
name|session
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"loggedOn"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"hdnCallingScreen"
argument_list|,
literal|"main.jsp"
argument_list|)
expr_stmt|;
name|Web
operator|.
name|setUser
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
expr_stmt|;
comment|// Check App Status
name|String
name|appStatus
init|=
name|ApplicationConfig
operator|.
name|getConfigValue
argument_list|(
name|Constants
operator|.
name|CFG_APP_ACCESS_LEVEL
argument_list|,
name|Constants
operator|.
name|APP_ACL_ALL
argument_list|)
decl_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CFG_APP_ACCESS_LEVEL
argument_list|,
name|appStatus
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"authUserExtId"
argument_list|,
name|user
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setMaxInactiveInterval
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.maxInactiveInterval"
argument_list|,
literal|"1800"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"selectPrimaryRole.do"
argument_list|)
expr_stmt|;
name|LoginManager
operator|.
name|loginSuceeded
argument_list|(
name|username
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|LoginException
name|le
parameter_list|)
block|{
name|LoginManager
operator|.
name|addFailedLoginAttempt
argument_list|(
name|username
argument_list|,
name|attemptDateTime
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|error
argument_list|(
name|le
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.login_url"
argument_list|)
operator|+
literal|"?e=3"
operator|+
literal|"&menu="
operator|+
name|menu
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

