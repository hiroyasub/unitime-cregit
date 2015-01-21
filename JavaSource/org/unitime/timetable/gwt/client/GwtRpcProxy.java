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
name|page
operator|.
name|UniTimeNotifications
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
name|http
operator|.
name|client
operator|.
name|Request
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
name|rpc
operator|.
name|impl
operator|.
name|RemoteServiceProxy
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
name|impl
operator|.
name|RpcStatsContext
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
name|impl
operator|.
name|Serializer
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
name|impl
operator|.
name|RequestCallbackAdapter
operator|.
name|ResponseReader
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GwtRpcProxy
extends|extends
name|RemoteServiceProxy
block|{
specifier|public
name|GwtRpcProxy
parameter_list|(
name|String
name|moduleBaseURL
parameter_list|,
name|String
name|remoteServiceRelativePath
parameter_list|,
name|String
name|serializationPolicyName
parameter_list|,
name|Serializer
name|serializer
parameter_list|)
block|{
name|super
argument_list|(
name|moduleBaseURL
argument_list|,
name|remoteServiceRelativePath
argument_list|,
name|serializationPolicyName
argument_list|,
name|serializer
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
parameter_list|<
name|T
parameter_list|>
name|Request
name|doInvoke
parameter_list|(
name|ResponseReader
name|responseReader
parameter_list|,
specifier|final
name|String
name|methodName
parameter_list|,
name|RpcStatsContext
name|statsContext
parameter_list|,
name|String
name|requestData
parameter_list|,
specifier|final
name|AsyncCallback
argument_list|<
name|T
argument_list|>
name|callback
parameter_list|)
block|{
return|return
name|super
operator|.
name|doInvoke
argument_list|(
name|responseReader
argument_list|,
name|methodName
argument_list|,
name|statsContext
argument_list|,
name|requestData
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|T
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
name|UniTimeNotifications
operator|.
name|error
argument_list|(
literal|"Request "
operator|+
name|methodName
operator|.
name|replace
argument_list|(
literal|"_Proxy"
argument_list|,
literal|""
argument_list|)
operator|+
literal|" failed: "
operator|+
name|caught
operator|.
name|getMessage
argument_list|()
argument_list|,
name|caught
argument_list|)
expr_stmt|;
name|callback
operator|.
name|onFailure
argument_list|(
name|caught
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|T
name|result
parameter_list|)
block|{
name|callback
operator|.
name|onSuccess
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
return|;
block|}
block|}
end_class

end_unit

