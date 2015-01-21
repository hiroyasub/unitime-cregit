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
name|spring
operator|.
name|gwt
package|;
end_package

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
name|IncompatibleRemoteServiceException
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
name|SerializationException
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
name|server
operator|.
name|rpc
operator|.
name|RPC
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
name|server
operator|.
name|rpc
operator|.
name|RPCRequest
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
name|server
operator|.
name|rpc
operator|.
name|RemoteServiceServlet
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
name|server
operator|.
name|rpc
operator|.
name|UnexpectedException
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GwtDispatcherServlet
extends|extends
name|RemoteServiceServlet
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|processCall
parameter_list|(
name|String
name|payload
parameter_list|)
throws|throws
name|SerializationException
block|{
try|try
block|{
name|Object
name|handler
init|=
name|getBean
argument_list|(
name|getThreadLocalRequest
argument_list|()
argument_list|)
decl_stmt|;
name|RPCRequest
name|rpcRequest
init|=
name|RPC
operator|.
name|decodeRequest
argument_list|(
name|payload
argument_list|,
name|handler
operator|.
name|getClass
argument_list|()
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|onAfterRequestDeserialized
argument_list|(
name|rpcRequest
argument_list|)
expr_stmt|;
try|try
block|{
return|return
name|RPC
operator|.
name|invokeAndEncodeResponse
argument_list|(
name|handler
argument_list|,
name|rpcRequest
operator|.
name|getMethod
argument_list|()
argument_list|,
name|rpcRequest
operator|.
name|getParameters
argument_list|()
argument_list|,
name|rpcRequest
operator|.
name|getSerializationPolicy
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|UnexpectedException
name|ex
parameter_list|)
block|{
if|if
condition|(
name|ex
operator|.
name|getCause
argument_list|()
operator|instanceof
name|AccessDeniedException
condition|)
return|return
name|RPC
operator|.
name|encodeResponseForFailure
argument_list|(
name|rpcRequest
operator|.
name|getMethod
argument_list|()
argument_list|,
operator|new
name|PageAccessException
argument_list|(
name|ex
operator|.
name|getCause
argument_list|()
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|rpcRequest
operator|.
name|getSerializationPolicy
argument_list|()
argument_list|)
return|;
throw|throw
name|ex
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|IncompatibleRemoteServiceException
name|ex
parameter_list|)
block|{
return|return
name|RPC
operator|.
name|encodeResponseForFailure
argument_list|(
literal|null
argument_list|,
name|ex
argument_list|)
return|;
block|}
block|}
specifier|protected
name|String
name|getService
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|String
name|url
init|=
name|request
operator|.
name|getRequestURI
argument_list|()
decl_stmt|;
name|String
name|service
init|=
name|url
operator|.
name|substring
argument_list|(
name|url
operator|.
name|lastIndexOf
argument_list|(
literal|"/"
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
return|return
name|service
return|;
block|}
specifier|protected
name|Object
name|getBean
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|String
name|service
init|=
name|getService
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|Object
name|bean
init|=
name|getBean
argument_list|(
name|service
argument_list|)
decl_stmt|;
return|return
name|bean
return|;
block|}
specifier|protected
name|Object
name|getBean
parameter_list|(
name|String
name|name
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
name|applicationContext
operator|.
name|getBean
argument_list|(
name|name
argument_list|)
return|;
block|}
block|}
end_class

end_unit

