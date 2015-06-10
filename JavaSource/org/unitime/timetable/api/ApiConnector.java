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
name|api
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
name|java
operator|.
name|util
operator|.
name|Collection
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
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|GrantedAuthority
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
name|SessionContext
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
name|context
operator|.
name|AnonymousUserContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|ApiConnector
block|{
annotation|@
name|Autowired
specifier|protected
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
specifier|protected
name|ApiToken
name|apiToken
decl_stmt|;
specifier|public
name|void
name|doGet
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NOT_IMPLEMENTED
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|doPut
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NOT_IMPLEMENTED
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|doPost
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NOT_IMPLEMENTED
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|doDelete
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_NOT_IMPLEMENTED
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|ApiHelper
name|createHelper
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
block|{
return|return
operator|new
name|JsonApiHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|sessionContext
argument_list|)
return|;
block|}
specifier|protected
name|void
name|authenticateWithTokenIfNeeded
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
block|{
if|if
condition|(
operator|(
operator|!
name|sessionContext
operator|.
name|isAuthenticated
argument_list|()
operator|||
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|instanceof
name|AnonymousUserContext
operator|)
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"token"
argument_list|)
operator|!=
literal|null
operator|&&
name|ApplicationProperty
operator|.
name|ApiCanUseAPIToken
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|UserContext
name|context
init|=
name|apiToken
operator|.
name|getContext
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"token"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|context
operator|!=
literal|null
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Pretending to be "
operator|+
name|context
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|SecurityContextHolder
operator|.
name|getContext
argument_list|()
operator|.
name|setAuthentication
argument_list|(
operator|new
name|TokenAuthentication
argument_list|(
name|context
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|void
name|revokeTokenAuthenticationIfNeeded
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
block|{
block|}
specifier|public
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|IOException
block|{
name|authenticateWithTokenIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
try|try
block|{
name|doGet
argument_list|(
name|createHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|revokeTokenAuthenticationIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|doPut
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|IOException
block|{
name|authenticateWithTokenIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
try|try
block|{
name|doPut
argument_list|(
name|createHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|revokeTokenAuthenticationIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|doPost
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|IOException
block|{
name|authenticateWithTokenIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
try|try
block|{
name|doPost
argument_list|(
name|createHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|revokeTokenAuthenticationIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|doDelete
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|IOException
block|{
name|authenticateWithTokenIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
try|try
block|{
name|doDelete
argument_list|(
name|createHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|revokeTokenAuthenticationIfNeeded
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
specifier|static
class|class
name|TokenAuthentication
implements|implements
name|Authentication
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|UserContext
name|iContext
decl_stmt|;
specifier|public
name|TokenAuthentication
parameter_list|(
name|UserContext
name|context
parameter_list|)
block|{
name|iContext
operator|=
name|context
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iContext
operator|.
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|GrantedAuthority
argument_list|>
name|getAuthorities
parameter_list|()
block|{
return|return
name|iContext
operator|.
name|getAuthorities
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getCredentials
parameter_list|()
block|{
return|return
name|iContext
operator|.
name|getPassword
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getDetails
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getPrincipal
parameter_list|()
block|{
return|return
name|iContext
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAuthenticated
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAuthenticated
parameter_list|(
name|boolean
name|isAuthenticated
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Operation not supported."
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

