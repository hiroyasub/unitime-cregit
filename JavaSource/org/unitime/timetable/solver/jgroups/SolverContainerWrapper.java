begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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

begin_class
specifier|public
class|class
name|SolverContainerWrapper
parameter_list|<
name|T
parameter_list|>
implements|implements
name|SolverContainer
argument_list|<
name|T
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
name|SolverContainerWrapper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|SolverServerImplementation
name|iServer
decl_stmt|;
specifier|private
name|RemoteSolverContainer
argument_list|<
name|T
argument_list|>
name|iContainer
decl_stmt|;
specifier|private
name|boolean
name|iCheckLocal
init|=
literal|true
decl_stmt|;
specifier|public
name|SolverContainerWrapper
parameter_list|(
name|SolverServerImplementation
name|server
parameter_list|,
name|RemoteSolverContainer
argument_list|<
name|T
argument_list|>
name|container
parameter_list|,
name|boolean
name|checkLocal
parameter_list|)
block|{
name|iServer
operator|=
name|server
expr_stmt|;
name|iContainer
operator|=
name|container
expr_stmt|;
name|iCheckLocal
operator|=
name|checkLocal
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
name|Set
argument_list|<
name|String
argument_list|>
name|solvers
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|iContainer
operator|.
name|getSolvers
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|RspList
argument_list|<
name|Set
argument_list|<
name|String
argument_list|>
argument_list|>
name|ret
init|=
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"getSolvers"
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
name|SolverServerImplementation
operator|.
name|sAllResponses
argument_list|)
decl_stmt|;
for|for
control|(
name|Rsp
argument_list|<
name|Set
argument_list|<
name|String
argument_list|>
argument_list|>
name|rsp
range|:
name|ret
control|)
block|{
name|solvers
operator|.
name|addAll
argument_list|(
name|rsp
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
literal|"Failed to retrieve solvers: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|solvers
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
try|try
block|{
if|if
condition|(
name|iCheckLocal
condition|)
block|{
name|T
name|solver
init|=
name|iContainer
operator|.
name|getSolver
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
return|return
name|solver
return|;
block|}
name|RspList
argument_list|<
name|Boolean
argument_list|>
name|ret
init|=
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"hasSolver"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|user
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
name|List
argument_list|<
name|Address
argument_list|>
name|senders
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
name|rsp
operator|.
name|getValue
argument_list|()
condition|)
name|senders
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
if|if
condition|(
name|senders
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
if|else if
condition|(
name|senders
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
return|return
name|iContainer
operator|.
name|createProxy
argument_list|(
name|senders
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|user
argument_list|)
return|;
if|else if
condition|(
name|iContainer
operator|instanceof
name|ReplicatedSolverContainer
condition|)
return|return
operator|(
operator|(
name|ReplicatedSolverContainer
argument_list|<
name|T
argument_list|>
operator|)
name|iContainer
operator|)
operator|.
name|createProxy
argument_list|(
name|senders
argument_list|,
name|user
argument_list|)
return|;
else|else
return|return
name|iContainer
operator|.
name|createProxy
argument_list|(
name|ToolBox
operator|.
name|random
argument_list|(
name|senders
argument_list|)
argument_list|,
name|user
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
literal|"Failed to retrieve solver "
operator|+
name|user
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
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
try|try
block|{
if|if
condition|(
name|iContainer
operator|.
name|hasSolver
argument_list|(
name|user
argument_list|)
condition|)
return|return
literal|true
return|;
name|RspList
argument_list|<
name|Boolean
argument_list|>
name|ret
init|=
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"hasSolver"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|user
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
if|if
condition|(
name|rsp
operator|.
name|getValue
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
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
literal|"Failed to check solver "
operator|+
name|user
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
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
try|try
block|{
name|SolverServer
name|bestServer
init|=
literal|null
decl_stmt|;
name|int
name|bestUsage
init|=
literal|0
decl_stmt|;
for|for
control|(
name|SolverServer
name|server
range|:
name|iServer
operator|.
name|getServers
argument_list|(
literal|true
argument_list|)
control|)
block|{
name|int
name|usage
init|=
name|server
operator|.
name|getUsage
argument_list|()
decl_stmt|;
if|if
condition|(
name|server
operator|.
name|isLocal
argument_list|()
condition|)
name|usage
operator|+=
literal|500
expr_stmt|;
if|if
condition|(
name|bestServer
operator|==
literal|null
operator|||
name|bestUsage
operator|>
name|usage
condition|)
block|{
name|bestServer
operator|=
name|server
expr_stmt|;
name|bestUsage
operator|=
name|usage
expr_stmt|;
block|}
block|}
if|if
condition|(
name|bestServer
operator|==
literal|null
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Not enough resources to create a solver instance, please try again later."
argument_list|)
throw|;
if|if
condition|(
name|bestServer
operator|.
name|getAddress
argument_list|()
operator|.
name|equals
argument_list|(
name|iServer
operator|.
name|getAddress
argument_list|()
argument_list|)
condition|)
return|return
name|iContainer
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|config
argument_list|)
return|;
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethod
argument_list|(
name|bestServer
operator|.
name|getAddress
argument_list|()
argument_list|,
literal|"createRemoteSolver"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|user
block|,
name|config
block|,
name|iServer
operator|.
name|getAddress
argument_list|()
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
name|DataProperties
operator|.
name|class
block|,
name|Address
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sFirstResponse
argument_list|)
expr_stmt|;
return|return
name|iContainer
operator|.
name|createProxy
argument_list|(
name|bestServer
operator|.
name|getAddress
argument_list|()
argument_list|,
name|user
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Failed to start the solver: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
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
try|try
block|{
if|if
condition|(
name|iContainer
operator|.
name|hasSolver
argument_list|(
name|user
argument_list|)
condition|)
name|iContainer
operator|.
name|unloadSolver
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|RspList
argument_list|<
name|Boolean
argument_list|>
name|ret
init|=
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"hasSolver"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|user
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
operator|.
name|getValue
argument_list|()
condition|)
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethod
argument_list|(
name|rsp
operator|.
name|getSender
argument_list|()
argument_list|,
literal|"unloadSolver"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|user
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
name|sFirstResponse
argument_list|)
expr_stmt|;
block|}
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
literal|"Failed to unload solver "
operator|+
name|user
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|getUsage
parameter_list|()
block|{
name|int
name|usage
init|=
literal|0
decl_stmt|;
try|try
block|{
name|RspList
argument_list|<
name|Integer
argument_list|>
name|ret
init|=
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"getUsage"
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
name|SolverServerImplementation
operator|.
name|sAllResponses
argument_list|)
decl_stmt|;
for|for
control|(
name|Rsp
argument_list|<
name|Integer
argument_list|>
name|rsp
range|:
name|ret
control|)
name|usage
operator|+=
name|rsp
operator|.
name|getValue
argument_list|()
expr_stmt|;
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
literal|"Failed to check solver server usage: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|usage
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|()
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Method start is not supported on the solver container wrapper."
argument_list|)
throw|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|stop
parameter_list|()
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Method stop is not supported on the solver container wrapper."
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

