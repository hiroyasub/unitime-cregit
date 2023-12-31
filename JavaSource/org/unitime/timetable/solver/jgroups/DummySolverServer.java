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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationHandler
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

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
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Proxy
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|Message
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|Message
operator|.
name|Flag
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|MessageListener
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
name|RequestOptions
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
name|ResponseMode
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
name|jgroups
operator|.
name|util
operator|.
name|Rsp
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|util
operator|.
name|RspList
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
name|SolverParameterGroup
operator|.
name|SolverType
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|server
operator|.
name|CheckMaster
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
name|onlinesectioning
operator|.
name|server
operator|.
name|CheckMaster
operator|.
name|Master
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
name|solver
operator|.
name|SolverProxy
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
name|solver
operator|.
name|exam
operator|.
name|ExamSolverProxy
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
name|solver
operator|.
name|instructor
operator|.
name|InstructorSchedulingProxy
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
name|solver
operator|.
name|studentsct
operator|.
name|StudentSolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DummySolverServer
extends|extends
name|AbstractSolverServer
implements|implements
name|MessageListener
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
name|DummySolverServer
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|RequestOptions
name|sFirstResponse
init|=
operator|new
name|RequestOptions
argument_list|(
name|ResponseMode
operator|.
name|GET_FIRST
argument_list|,
literal|0
argument_list|)
operator|.
name|setFlags
argument_list|(
name|Flag
operator|.
name|DONT_BUNDLE
argument_list|,
name|Flag
operator|.
name|OOB
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|RequestOptions
name|sAllResponses
init|=
operator|new
name|RequestOptions
argument_list|(
name|ResponseMode
operator|.
name|GET_ALL
argument_list|,
literal|0
argument_list|)
operator|.
name|setFlags
argument_list|(
name|Flag
operator|.
name|DONT_BUNDLE
argument_list|,
name|Flag
operator|.
name|OOB
argument_list|)
decl_stmt|;
specifier|private
name|JChannel
name|iChannel
init|=
literal|null
decl_stmt|;
specifier|private
name|RpcDispatcher
name|iDispatcher
decl_stmt|;
specifier|private
name|RpcDispatcher
name|iRoomAvailabilityDispatcher
decl_stmt|;
specifier|protected
name|Properties
name|iProperties
init|=
literal|null
decl_stmt|;
specifier|private
name|DummyContainer
argument_list|<
name|SolverProxy
argument_list|>
name|iCourseSolverContainer
decl_stmt|;
specifier|private
name|DummyContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
name|iExamSolverContainer
decl_stmt|;
specifier|private
name|DummyContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
name|iStudentSolverContainer
decl_stmt|;
specifier|private
name|DummyContainer
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
name|iInstructorSchedulingContainer
decl_stmt|;
specifier|private
name|DummyContainer
argument_list|<
name|OnlineSectioningServer
argument_list|>
name|iOnlineStudentSchedulingContainer
decl_stmt|;
specifier|private
name|SolverContainerWrapper
argument_list|<
name|SolverProxy
argument_list|>
name|iCourseSolverContainerWrapper
decl_stmt|;
specifier|private
name|SolverContainerWrapper
argument_list|<
name|ExamSolverProxy
argument_list|>
name|iExamSolverContainerWrapper
decl_stmt|;
specifier|private
name|SolverContainerWrapper
argument_list|<
name|StudentSolverProxy
argument_list|>
name|iStudentSolverContainerWrapper
decl_stmt|;
specifier|private
name|SolverContainerWrapper
argument_list|<
name|OnlineSectioningServer
argument_list|>
name|iOnlineStudentSchedulingContainerWrapper
decl_stmt|;
specifier|public
name|DummySolverServer
parameter_list|(
name|JChannel
name|channel
parameter_list|)
block|{
name|iChannel
operator|=
name|channel
expr_stmt|;
name|iDispatcher
operator|=
operator|new
name|MuxRpcDispatcher
argument_list|(
name|SCOPE_SERVER
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
name|iCourseSolverContainer
operator|=
operator|new
name|DummyContainer
argument_list|<
name|SolverProxy
argument_list|>
argument_list|(
name|channel
argument_list|,
name|SCOPE_COURSE
argument_list|,
name|SolverProxy
operator|.
name|class
argument_list|)
expr_stmt|;
name|iExamSolverContainer
operator|=
operator|new
name|DummyContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
argument_list|(
name|channel
argument_list|,
name|SCOPE_EXAM
argument_list|,
name|ExamSolverProxy
operator|.
name|class
argument_list|)
expr_stmt|;
name|iStudentSolverContainer
operator|=
operator|new
name|DummyContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
argument_list|(
name|channel
argument_list|,
name|SCOPE_STUDENT
argument_list|,
name|StudentSolverProxy
operator|.
name|class
argument_list|)
expr_stmt|;
name|iInstructorSchedulingContainer
operator|=
operator|new
name|DummyContainer
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
argument_list|(
name|channel
argument_list|,
name|SCOPE_INSTRUCTOR
argument_list|,
name|InstructorSchedulingProxy
operator|.
name|class
argument_list|)
expr_stmt|;
name|iOnlineStudentSchedulingContainer
operator|=
operator|new
name|ReplicatedDummyContainer
argument_list|<
name|OnlineSectioningServer
argument_list|>
argument_list|(
name|channel
argument_list|,
name|SCOPE_ONLINE
argument_list|,
name|OnlineSectioningServer
operator|.
name|class
argument_list|)
expr_stmt|;
name|iRoomAvailabilityDispatcher
operator|=
operator|new
name|MuxRpcDispatcher
argument_list|(
name|SCOPE_AVAILABILITY
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
name|iCourseSolverContainerWrapper
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|SolverProxy
argument_list|>
argument_list|(
name|iDispatcher
argument_list|,
name|iCourseSolverContainer
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iExamSolverContainerWrapper
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|ExamSolverProxy
argument_list|>
argument_list|(
name|iDispatcher
argument_list|,
name|iExamSolverContainer
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iStudentSolverContainerWrapper
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|StudentSolverProxy
argument_list|>
argument_list|(
name|iDispatcher
argument_list|,
name|iStudentSolverContainer
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iOnlineStudentSchedulingContainerWrapper
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|OnlineSectioningServer
argument_list|>
argument_list|(
name|iDispatcher
argument_list|,
name|iOnlineStudentSchedulingContainer
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Properties
name|getProperties
parameter_list|()
block|{
if|if
condition|(
name|iProperties
operator|==
literal|null
condition|)
name|iProperties
operator|=
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
expr_stmt|;
return|return
name|iProperties
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isLocal
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Address
name|getAddress
parameter_list|()
block|{
return|return
name|iChannel
operator|.
name|getAddress
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Address
name|getLocalAddress
parameter_list|()
block|{
try|try
block|{
name|RspList
argument_list|<
name|Boolean
argument_list|>
name|ret
init|=
name|iDispatcher
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"isLocal"
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|,
name|sAllResponses
argument_list|)
decl_stmt|;
for|for
control|(
name|Rsp
argument_list|<
name|Boolean
argument_list|>
name|local
range|:
name|ret
control|)
block|{
if|if
condition|(
name|Boolean
operator|.
name|TRUE
operator|.
name|equals
argument_list|(
name|local
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
return|return
name|local
operator|.
name|getSender
argument_list|()
return|;
block|}
return|return
literal|null
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
literal|"Failed to retrieve local address: "
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
annotation|@
name|Override
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iChannel
operator|.
name|getAddressAsString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getUsage
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getAvailableMemory
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getMemoryLimit
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isActive
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAvailable
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|SolverContainer
argument_list|<
name|SolverProxy
argument_list|>
name|getCourseSolverContainer
parameter_list|()
block|{
return|return
name|iCourseSolverContainerWrapper
return|;
block|}
annotation|@
name|Override
specifier|public
name|SolverContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
name|getExamSolverContainer
parameter_list|()
block|{
return|return
name|iExamSolverContainerWrapper
return|;
block|}
annotation|@
name|Override
specifier|public
name|SolverContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
name|getStudentSolverContainer
parameter_list|()
block|{
return|return
name|iStudentSolverContainerWrapper
return|;
block|}
annotation|@
name|Override
specifier|public
name|SolverContainer
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
name|getInstructorSchedulingContainer
parameter_list|()
block|{
return|return
name|iInstructorSchedulingContainer
return|;
block|}
annotation|@
name|Override
specifier|public
name|SolverContainer
argument_list|<
name|OnlineSectioningServer
argument_list|>
name|getOnlineStudentSchedulingContainer
parameter_list|()
block|{
return|return
name|iOnlineStudentSchedulingContainerWrapper
return|;
block|}
annotation|@
name|Override
specifier|public
name|RoomAvailabilityInterface
name|getRoomAvailability
parameter_list|()
block|{
name|Address
name|local
init|=
name|getLocalAddress
argument_list|()
decl_stmt|;
if|if
condition|(
name|local
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|RoomAvailabilityInterface
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|SolverServerImplementation
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
operator|new
name|Class
index|[]
block|{
name|RoomAvailabilityInterface
operator|.
name|class
block|}
argument_list|,
operator|new
name|RoomAvailabilityInvocationHandler
argument_list|(
name|local
argument_list|)
argument_list|)
return|;
block|}
specifier|public
class|class
name|RoomAvailabilityInvocationHandler
implements|implements
name|InvocationHandler
block|{
specifier|private
name|Address
name|iAddress
decl_stmt|;
specifier|private
name|RoomAvailabilityInvocationHandler
parameter_list|(
name|Address
name|address
parameter_list|)
block|{
name|iAddress
operator|=
name|address
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Throwable
block|{
try|try
block|{
return|return
name|iRoomAvailabilityDispatcher
operator|.
name|callRemoteMethod
argument_list|(
name|iAddress
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
specifier|public
class|class
name|DummyContainer
parameter_list|<
name|T
parameter_list|>
implements|implements
name|RemoteSolverContainer
argument_list|<
name|T
argument_list|>
block|{
specifier|protected
name|RpcDispatcher
name|iDispatcher
decl_stmt|;
specifier|protected
name|Class
argument_list|<
name|T
argument_list|>
name|iClazz
decl_stmt|;
specifier|public
name|DummyContainer
parameter_list|(
name|JChannel
name|channel
parameter_list|,
name|short
name|scope
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
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
name|iClazz
operator|=
name|clazz
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getSolvers
parameter_list|()
block|{
return|return
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|T
name|getSolver
parameter_list|(
name|String
name|user
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getMemUsage
parameter_list|(
name|String
name|user
parameter_list|)
block|{
return|return
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasSolver
parameter_list|(
name|String
name|user
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|T
name|createSolver
parameter_list|(
name|String
name|user
parameter_list|,
name|DataProperties
name|config
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|unloadSolver
parameter_list|(
name|String
name|user
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|int
name|getUsage
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|stop
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|createRemoteSolver
parameter_list|(
name|String
name|user
parameter_list|,
name|DataProperties
name|config
parameter_list|,
name|Address
name|caller
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|RpcDispatcher
name|getDispatcher
parameter_list|()
block|{
return|return
name|iDispatcher
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|dispatch
parameter_list|(
name|Address
name|address
parameter_list|,
name|String
name|user
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
name|user
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
name|debug
argument_list|(
literal|"Excution of "
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|" on solver "
operator|+
name|user
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
throw|throw
name|e
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|String
name|method
parameter_list|,
name|String
name|user
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
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Method "
operator|+
name|method
operator|+
literal|" not implemented."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|T
name|createProxy
parameter_list|(
name|Address
name|address
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|SolverInvocationHandler
name|handler
init|=
operator|new
name|SolverInvocationHandler
argument_list|(
name|address
argument_list|,
name|user
argument_list|)
decl_stmt|;
return|return
operator|(
name|T
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|iClazz
operator|.
name|getClassLoader
argument_list|()
argument_list|,
operator|new
name|Class
index|[]
block|{
name|iClazz
block|,
name|RemoteSolver
operator|.
name|class
block|, }
argument_list|,
name|handler
argument_list|)
return|;
block|}
specifier|public
class|class
name|SolverInvocationHandler
implements|implements
name|InvocationHandler
block|{
specifier|private
name|Address
name|iAddress
decl_stmt|;
specifier|private
name|String
name|iUser
decl_stmt|;
specifier|private
name|SolverInvocationHandler
parameter_list|(
name|Address
name|address
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|iAddress
operator|=
name|address
expr_stmt|;
name|iUser
operator|=
name|user
expr_stmt|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iAddress
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Throwable
block|{
try|try
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|,
name|method
operator|.
name|getParameterTypes
argument_list|()
argument_list|)
operator|.
name|invoke
argument_list|(
name|this
argument_list|,
name|args
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
block|}
return|return
name|dispatch
argument_list|(
name|iAddress
argument_list|,
name|iUser
argument_list|,
name|method
argument_list|,
name|args
argument_list|)
return|;
block|}
block|}
block|}
specifier|public
class|class
name|ReplicatedDummyContainer
parameter_list|<
name|T
parameter_list|>
extends|extends
name|DummyContainer
argument_list|<
name|T
argument_list|>
implements|implements
name|ReplicatedSolverContainer
argument_list|<
name|T
argument_list|>
block|{
specifier|public
name|ReplicatedDummyContainer
parameter_list|(
name|JChannel
name|channel
parameter_list|,
name|short
name|scope
parameter_list|,
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|)
block|{
name|super
argument_list|(
name|channel
argument_list|,
name|scope
argument_list|,
name|clazz
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|dispatch
parameter_list|(
name|Collection
argument_list|<
name|Address
argument_list|>
name|addresses
parameter_list|,
name|String
name|sessionId
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
if|if
condition|(
name|addresses
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
name|dispatch
argument_list|(
name|ToolBox
operator|.
name|random
argument_list|(
name|addresses
argument_list|)
argument_list|,
name|sessionId
argument_list|,
name|method
argument_list|,
name|args
argument_list|)
return|;
block|}
else|else
block|{
name|Address
name|address
init|=
name|ToolBox
operator|.
name|random
argument_list|(
name|addresses
argument_list|)
decl_stmt|;
name|CheckMaster
name|ch
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|CheckMaster
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|ch
operator|==
literal|null
operator|&&
literal|"execute"
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
name|ch
operator|=
name|args
index|[
literal|0
index|]
operator|.
name|getClass
argument_list|()
operator|.
name|getAnnotation
argument_list|(
name|CheckMaster
operator|.
name|class
argument_list|)
expr_stmt|;
name|RspList
argument_list|<
name|Boolean
argument_list|>
name|ret
init|=
name|iDispatcher
operator|.
name|callRemoteMethods
argument_list|(
name|addresses
argument_list|,
literal|"hasMaster"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|sessionId
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sAllResponses
argument_list|)
decl_stmt|;
if|if
condition|(
name|ch
operator|!=
literal|null
operator|&&
name|ch
operator|.
name|value
argument_list|()
operator|==
name|Master
operator|.
name|REQUIRED
condition|)
block|{
for|for
control|(
name|Rsp
argument_list|<
name|Boolean
argument_list|>
name|rsp
range|:
name|ret
control|)
block|{
if|if
condition|(
name|rsp
operator|!=
literal|null
operator|&&
name|rsp
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|address
operator|=
name|rsp
operator|.
name|getSender
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
else|else
block|{
name|List
argument_list|<
name|Address
argument_list|>
name|slaves
init|=
operator|new
name|ArrayList
argument_list|<
name|Address
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Rsp
argument_list|<
name|Boolean
argument_list|>
name|rsp
range|:
name|ret
control|)
block|{
if|if
condition|(
name|rsp
operator|!=
literal|null
operator|&&
operator|!
name|rsp
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|slaves
operator|.
name|add
argument_list|(
name|rsp
operator|.
name|getSender
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|slaves
operator|.
name|isEmpty
argument_list|()
condition|)
name|address
operator|=
name|ToolBox
operator|.
name|random
argument_list|(
name|slaves
argument_list|)
expr_stmt|;
block|}
return|return
name|dispatch
argument_list|(
name|address
argument_list|,
name|sessionId
argument_list|,
name|method
argument_list|,
name|args
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getTargetException
argument_list|()
operator|!=
literal|null
operator|&&
name|e
operator|.
name|getTargetException
argument_list|()
operator|instanceof
name|Exception
condition|)
throw|throw
operator|(
name|Exception
operator|)
name|e
operator|.
name|getTargetException
argument_list|()
throw|;
else|else
throw|throw
name|e
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|T
name|createProxy
parameter_list|(
name|Collection
argument_list|<
name|Address
argument_list|>
name|addresses
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|ReplicatedServerInvocationHandler
name|handler
init|=
operator|new
name|ReplicatedServerInvocationHandler
argument_list|(
name|addresses
argument_list|,
name|user
argument_list|)
decl_stmt|;
name|T
name|px
init|=
operator|(
name|T
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|iClazz
operator|.
name|getClassLoader
argument_list|()
argument_list|,
operator|new
name|Class
index|[]
block|{
name|iClazz
block|,
name|RemoteSolver
operator|.
name|class
block|, }
argument_list|,
name|handler
argument_list|)
decl_stmt|;
return|return
name|px
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasMaster
parameter_list|(
name|String
name|user
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
specifier|public
class|class
name|ReplicatedServerInvocationHandler
implements|implements
name|InvocationHandler
block|{
specifier|private
name|Collection
argument_list|<
name|Address
argument_list|>
name|iAddresses
decl_stmt|;
specifier|private
name|String
name|iUser
decl_stmt|;
specifier|private
name|ReplicatedServerInvocationHandler
parameter_list|(
name|Collection
argument_list|<
name|Address
argument_list|>
name|addresses
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|iAddresses
operator|=
name|addresses
expr_stmt|;
name|iUser
operator|=
name|user
expr_stmt|;
block|}
specifier|public
name|String
name|getHost
parameter_list|()
block|{
return|return
name|iAddresses
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|getUser
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Throwable
block|{
try|try
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|,
name|method
operator|.
name|getParameterTypes
argument_list|()
argument_list|)
operator|.
name|invoke
argument_list|(
name|this
argument_list|,
name|args
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
block|}
return|return
name|dispatch
argument_list|(
name|iAddresses
argument_list|,
name|iUser
argument_list|,
name|method
argument_list|,
name|args
argument_list|)
return|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|receive
parameter_list|(
name|Message
name|msg
parameter_list|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"receive("
operator|+
name|msg
operator|+
literal|", "
operator|+
name|msg
operator|.
name|getObject
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|getState
parameter_list|(
name|OutputStream
name|output
parameter_list|)
throws|throws
name|Exception
block|{
name|getProperties
argument_list|()
operator|.
name|store
argument_list|(
name|output
argument_list|,
literal|"UniTime Application Properties"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setState
parameter_list|(
name|InputStream
name|input
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|iProperties
operator|==
literal|null
condition|)
block|{
name|iProperties
operator|=
operator|new
name|Properties
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iProperties
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
name|iProperties
operator|.
name|load
argument_list|(
name|input
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isCoordinator
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|SolverServer
argument_list|>
name|getServers
parameter_list|(
name|boolean
name|onlyAvailable
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
annotation|@
name|Override
specifier|public
name|SolverServer
name|crateServerProxy
parameter_list|(
name|Address
name|address
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|shutdown
parameter_list|()
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|unloadSolver
parameter_list|(
name|SolverType
name|type
parameter_list|,
name|String
name|id
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|COURSE
case|:
name|getCourseSolverContainer
argument_list|()
operator|.
name|unloadSolver
argument_list|(
name|id
argument_list|)
expr_stmt|;
break|break;
case|case
name|EXAM
case|:
name|getExamSolverContainer
argument_list|()
operator|.
name|unloadSolver
argument_list|(
name|id
argument_list|)
expr_stmt|;
break|break;
case|case
name|INSTRUCTOR
case|:
name|getInstructorSchedulingContainer
argument_list|()
operator|.
name|unloadSolver
argument_list|(
name|id
argument_list|)
expr_stmt|;
break|break;
case|case
name|STUDENT
case|:
name|getStudentSolverContainer
argument_list|()
operator|.
name|unloadSolver
argument_list|(
name|id
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
end_class

end_unit

