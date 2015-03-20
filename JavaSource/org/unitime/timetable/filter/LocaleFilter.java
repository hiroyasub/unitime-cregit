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
name|filter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|Filter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterChain
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|FilterConfig
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|ServletResponse
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
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|Authentication
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|core
operator|.
name|context
operator|.
name|SecurityContextHolder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|defaults
operator|.
name|ApplicationProperty
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
name|security
operator|.
name|UserContext
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
name|Formats
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|LocaleFilter
implements|implements
name|Filter
block|{
specifier|private
name|boolean
name|iUseBrowserSettings
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|fc
parameter_list|)
throws|throws
name|ServletException
block|{
name|iUseBrowserSettings
operator|=
literal|"true"
operator|.
name|equals
argument_list|(
name|fc
operator|.
name|getInitParameter
argument_list|(
literal|"use-browser-settings"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|UserContext
name|getUser
parameter_list|()
block|{
name|Authentication
name|authentication
init|=
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|getAuthentication
argument_list|()
decl_stmt|;
if|if
condition|(
name|authentication
operator|!=
literal|null
operator|&&
name|authentication
operator|.
name|isAuthenticated
argument_list|()
operator|&&
name|authentication
operator|.
name|getPrincipal
argument_list|()
operator|instanceof
name|UserContext
condition|)
return|return
operator|(
name|UserContext
operator|)
name|authentication
operator|.
name|getPrincipal
argument_list|()
return|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|doFilter
parameter_list|(
name|ServletRequest
name|req
parameter_list|,
name|ServletResponse
name|resp
parameter_list|,
name|FilterChain
name|chain
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
block|{
try|try
block|{
if|if
condition|(
name|req
operator|instanceof
name|HttpServletRequest
condition|)
block|{
name|HttpServletRequest
name|request
init|=
operator|(
name|HttpServletRequest
operator|)
name|req
decl_stmt|;
name|String
name|locale
init|=
literal|null
decl_stmt|;
comment|// Try HTTP header, Accept-Language field
if|if
condition|(
name|iUseBrowserSettings
condition|)
name|locale
operator|=
name|request
operator|.
name|getHeader
argument_list|(
literal|"Accept-Language"
argument_list|)
expr_stmt|;
comment|// Try locale parameter (use http session to store)
if|if
condition|(
name|req
operator|.
name|getParameter
argument_list|(
literal|"locale"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|locale
operator|=
name|req
operator|.
name|getParameter
argument_list|(
literal|"locale"
argument_list|)
expr_stmt|;
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"unitime.locale"
argument_list|,
name|locale
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"unitime.locale"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|locale
operator|=
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"unitime.locale"
argument_list|)
expr_stmt|;
block|}
comment|// Fall back to unitime.locale
if|if
condition|(
name|locale
operator|==
literal|null
condition|)
block|{
name|locale
operator|=
name|ApplicationProperty
operator|.
name|Locale
operator|.
name|value
argument_list|()
expr_stmt|;
name|UserContext
name|user
init|=
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
name|locale
operator|=
name|user
operator|.
name|getProperty
argument_list|(
name|ApplicationProperty
operator|.
name|Locale
operator|.
name|key
argument_list|()
argument_list|,
name|locale
argument_list|)
expr_stmt|;
block|}
name|Localization
operator|.
name|setLocale
argument_list|(
name|locale
argument_list|)
expr_stmt|;
block|}
name|chain
operator|.
name|doFilter
argument_list|(
name|req
argument_list|,
name|resp
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|Localization
operator|.
name|removeLocale
argument_list|()
expr_stmt|;
name|Formats
operator|.
name|removeFormats
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|destroy
parameter_list|()
block|{
block|}
block|}
end_class

end_unit

