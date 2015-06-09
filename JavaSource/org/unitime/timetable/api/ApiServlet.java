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
name|http
operator|.
name|HttpServlet
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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|NoSuchBeanDefinitionException
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
name|access
operator|.
name|AccessDeniedException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|context
operator|.
name|WebApplicationContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|context
operator|.
name|support
operator|.
name|WebApplicationContextUtils
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
name|PageAccessException
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
name|context
operator|.
name|AnonymousUserContext
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
name|HttpSessionContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ApiServlet
extends|extends
name|HttpServlet
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ApiServlet
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|getServletContext
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getReference
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
return|return
name|request
operator|.
name|getServletPath
argument_list|()
operator|+
name|request
operator|.
name|getPathInfo
argument_list|()
return|;
block|}
specifier|protected
name|ApiConnector
name|getConnector
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|WebApplicationContext
name|applicationContext
init|=
name|WebApplicationContextUtils
operator|.
name|getWebApplicationContext
argument_list|(
name|getServletContext
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|ApiConnector
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
name|getReference
argument_list|(
name|request
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|ApiHelper
name|getHelper
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
name|getSessionContext
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|void
name|checkError
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|,
name|Throwable
name|t
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|t
operator|instanceof
name|NoSuchBeanDefinitionException
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Service "
operator|+
name|getReference
argument_list|(
name|request
argument_list|)
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Service "
operator|+
name|getReference
argument_list|(
name|request
argument_list|)
operator|+
literal|" not known, please check the request path."
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|t
operator|instanceof
name|IllegalArgumentException
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|t
operator|instanceof
name|PageAccessException
operator|||
name|t
operator|instanceof
name|AccessDeniedException
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|isAuthenticated
argument_list|()
operator|||
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|instanceof
name|AnonymousUserContext
condition|)
block|{
name|response
operator|.
name|setHeader
argument_list|(
literal|"WWW-Authenticate"
argument_list|,
literal|"Basic"
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_UNAUTHORIZED
argument_list|,
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_FORBIDDEN
argument_list|,
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|sLog
operator|.
name|warn
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_INTERNAL_SERVER_ERROR
argument_list|,
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
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
name|ServletException
throws|,
name|IOException
block|{
try|try
block|{
name|getConnector
argument_list|(
name|request
argument_list|)
operator|.
name|doGet
argument_list|(
name|getHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|checkError
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
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
name|ServletException
throws|,
name|IOException
block|{
try|try
block|{
name|getConnector
argument_list|(
name|request
argument_list|)
operator|.
name|doPost
argument_list|(
name|getHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|checkError
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
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
name|ServletException
throws|,
name|IOException
block|{
try|try
block|{
name|getConnector
argument_list|(
name|request
argument_list|)
operator|.
name|doPut
argument_list|(
name|getHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|checkError
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
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
name|ServletException
throws|,
name|IOException
block|{
try|try
block|{
name|getConnector
argument_list|(
name|request
argument_list|)
operator|.
name|doDelete
argument_list|(
name|getHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|checkError
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

