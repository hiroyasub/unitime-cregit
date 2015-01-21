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
name|SuspectedException
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
name|StudentSolverContainerRemote
extends|extends
name|StudentSolverContainer
implements|implements
name|RemoteSolverContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
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
name|StudentSolverContainerRemote
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|RpcDispatcher
name|iDispatcher
decl_stmt|;
specifier|public
name|StudentSolverContainerRemote
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
name|super
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|config
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
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
try|try
block|{
name|StudentSolverProxy
name|solver
init|=
name|iStudentSolvers
operator|.
name|get
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"exists"
operator|.
name|equals
argument_list|(
name|method
argument_list|)
operator|&&
name|types
operator|.
name|length
operator|==
literal|0
condition|)
return|return
name|solver
operator|!=
literal|null
return|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver "
operator|+
name|user
operator|+
literal|" does not exist."
argument_list|)
throw|;
return|return
name|solver
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
name|solver
argument_list|,
name|args
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
throw|throw
operator|(
name|Exception
operator|)
name|e
operator|.
name|getTargetException
argument_list|()
throw|;
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
name|InvocationTargetException
name|e
parameter_list|)
block|{
throw|throw
operator|(
name|Exception
operator|)
name|e
operator|.
name|getTargetException
argument_list|()
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
literal|"exists"
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|e
operator|instanceof
name|SuspectedException
condition|)
return|return
literal|false
return|;
name|sLog
operator|.
name|error
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
name|StudentSolverProxy
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
name|StudentSolverProxy
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|StudentSolverProxy
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
name|StudentSolverProxy
operator|.
name|class
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
end_class

end_unit

