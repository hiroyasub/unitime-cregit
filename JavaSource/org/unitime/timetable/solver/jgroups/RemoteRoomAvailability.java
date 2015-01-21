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
name|solver
operator|.
name|jgroups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
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
name|jgroups
operator|.
name|Address
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|JChannel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|blocks
operator|.
name|RpcDispatcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|blocks
operator|.
name|mux
operator|.
name|MuxRpcDispatcher
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
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
name|dao
operator|.
name|_RootDAO
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
name|RoomAvailability
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RemoteRoomAvailability
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
name|RemoteRoomAvailability
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|RpcDispatcher
name|iDispatcher
decl_stmt|;
specifier|public
name|RemoteRoomAvailability
parameter_list|(
name|JChannel
name|channel
parameter_list|,
name|short
name|scope
parameter_list|)
block|{
name|iDispatcher
operator|=
operator|new
name|MuxRpcDispatcher
argument_list|(
name|scope
argument_list|,
name|channel
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RpcDispatcher
name|getDispatcher
parameter_list|()
block|{
return|return
name|iDispatcher
return|;
block|}
specifier|public
name|Object
name|invoke
parameter_list|(
name|String
name|method
parameter_list|,
name|Class
index|[]
name|types
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|RoomAvailabilityInterface
name|availability
init|=
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
decl_stmt|;
if|if
condition|(
name|availability
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"There is no room availability."
argument_list|)
throw|;
return|return
name|availability
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
name|method
argument_list|,
name|types
argument_list|)
operator|.
name|invoke
argument_list|(
name|availability
argument_list|,
name|args
argument_list|)
return|;
block|}
finally|finally
block|{
name|_RootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|Object
name|dispatch
parameter_list|(
name|Address
name|address
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
return|return
name|iDispatcher
operator|.
name|callRemoteMethod
argument_list|(
name|address
argument_list|,
literal|"invoke"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|method
operator|.
name|getName
argument_list|()
block|,
name|method
operator|.
name|getParameterTypes
argument_list|()
block|,
name|args
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|,
name|Class
index|[]
operator|.
expr|class
block|,
name|Object
index|[]
operator|.
expr|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sFirstResponse
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Excution of room availability method "
operator|+
name|method
operator|+
literal|" failed: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

