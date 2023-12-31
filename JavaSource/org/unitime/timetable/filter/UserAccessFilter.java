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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|UserAccessFilter
implements|implements
name|Filter
block|{
specifier|public
specifier|static
name|String
name|sAllowNone
init|=
literal|"none"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sAllowAdmin
init|=
literal|"admin"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sAllowLoggedIn
init|=
literal|"logged-in"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sAllowAll
init|=
literal|"all"
decl_stmt|;
specifier|private
name|String
name|iAllow
init|=
literal|null
decl_stmt|;
specifier|public
name|void
name|init
parameter_list|(
name|FilterConfig
name|cfg
parameter_list|)
throws|throws
name|ServletException
block|{
name|iAllow
operator|=
name|cfg
operator|.
name|getInitParameter
argument_list|(
literal|"allow"
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
specifier|public
name|void
name|doFilter
parameter_list|(
name|ServletRequest
name|request
parameter_list|,
name|ServletResponse
name|response
parameter_list|,
name|FilterChain
name|chain
parameter_list|)
throws|throws
name|IOException
throws|,
name|ServletException
block|{
if|if
condition|(
operator|!
name|sAllowAll
operator|.
name|equals
argument_list|(
name|iAllow
argument_list|)
condition|)
block|{
if|if
condition|(
name|request
operator|instanceof
name|HttpServletRequest
condition|)
block|{
name|HttpServletRequest
name|httpRequest
init|=
operator|(
name|HttpServletRequest
operator|)
name|request
decl_stmt|;
name|HttpServletResponse
name|httpResponse
init|=
operator|(
name|HttpServletResponse
operator|)
name|response
decl_stmt|;
name|HttpSession
name|session
init|=
name|httpRequest
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|UserContext
name|user
init|=
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|sAllowLoggedIn
operator|.
name|equals
argument_list|(
name|iAllow
argument_list|)
operator|&&
name|user
operator|!=
literal|null
condition|)
block|{
name|session
operator|.
name|setAttribute
argument_list|(
literal|"exception"
argument_list|,
operator|new
name|ServletException
argument_list|(
literal|"Access Denied."
argument_list|)
argument_list|)
expr_stmt|;
name|httpResponse
operator|.
name|sendRedirect
argument_list|(
name|httpRequest
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/error.jsp"
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|sAllowAdmin
operator|.
name|equals
argument_list|(
name|iAllow
argument_list|)
operator|&&
operator|(
name|user
operator|==
literal|null
operator|||
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|IsAdmin
argument_list|)
operator|)
condition|)
block|{
name|session
operator|.
name|setAttribute
argument_list|(
literal|"exception"
argument_list|,
operator|new
name|ServletException
argument_list|(
literal|"Access Denied."
argument_list|)
argument_list|)
expr_stmt|;
name|httpResponse
operator|.
name|sendRedirect
argument_list|(
name|httpRequest
operator|.
name|getContextPath
argument_list|()
operator|+
literal|"/error.jsp"
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|ServletException
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
block|}
comment|// Process request
name|chain
operator|.
name|doFilter
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|destroy
parameter_list|()
block|{
block|}
block|}
end_class

end_unit

