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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeUnit
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
name|infinispan
operator|.
name|configuration
operator|.
name|cache
operator|.
name|CacheMode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|configuration
operator|.
name|cache
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|configuration
operator|.
name|cache
operator|.
name|ConfigurationBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|configuration
operator|.
name|global
operator|.
name|GlobalConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|configuration
operator|.
name|global
operator|.
name|GlobalConfigurationBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|manager
operator|.
name|DefaultCacheManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|manager
operator|.
name|EmbeddedCacheManager
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
name|locking
operator|.
name|LockService
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
name|model
operator|.
name|Session
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
name|SessionDAO
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
name|OnlineSectioningServerContext
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineStudentSchedulingContainerRemote
extends|extends
name|OnlineStudentSchedulingContainer
implements|implements
name|ReplicatedSolverContainer
argument_list|<
name|OnlineSectioningServer
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
name|OnlineStudentSchedulingContainerRemote
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|RpcDispatcher
name|iDispatcher
decl_stmt|;
specifier|private
name|EmbeddedCacheManager
name|iCacheManager
init|=
literal|null
decl_stmt|;
specifier|private
name|LockService
name|iLockService
decl_stmt|;
specifier|public
name|OnlineStudentSchedulingContainerRemote
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
name|iLockService
operator|=
operator|new
name|LockService
argument_list|(
name|channel
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
specifier|public
name|LockService
name|getLockService
parameter_list|()
block|{
return|return
name|iLockService
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|()
block|{
name|super
operator|.
name|start
argument_list|()
expr_stmt|;
name|GlobalConfiguration
name|global
init|=
name|GlobalConfigurationBuilder
operator|.
name|defaultClusteredBuilder
argument_list|()
operator|.
name|transport
argument_list|()
operator|.
name|addProperty
argument_list|(
literal|"channelLookup"
argument_list|,
literal|"org.unitime.commons.jgroups.SectioningChannelLookup"
argument_list|)
operator|.
name|clusterName
argument_list|(
literal|"UniTime:sectioning"
argument_list|)
operator|.
name|globalJmxStatistics
argument_list|()
operator|.
name|cacheManagerName
argument_list|(
literal|"OnlineSchedulingCacheManager"
argument_list|)
operator|.
name|allowDuplicateDomains
argument_list|(
literal|true
argument_list|)
operator|.
name|disable
argument_list|()
operator|.
name|build
argument_list|()
decl_stmt|;
name|Configuration
name|config
init|=
operator|new
name|ConfigurationBuilder
argument_list|()
operator|.
name|clustering
argument_list|()
operator|.
name|cacheMode
argument_list|(
name|CacheMode
operator|.
name|REPL_ASYNC
argument_list|)
operator|.
name|async
argument_list|()
operator|.
name|useReplQueue
argument_list|(
literal|true
argument_list|)
operator|.
name|replQueueInterval
argument_list|(
literal|500
argument_list|,
name|TimeUnit
operator|.
name|MILLISECONDS
argument_list|)
operator|.
name|replQueueMaxElements
argument_list|(
literal|1000
argument_list|)
operator|.
name|invocationBatching
argument_list|()
operator|.
name|enable
argument_list|()
operator|.
name|storeAsBinary
argument_list|()
operator|.
name|enable
argument_list|()
operator|.
name|build
argument_list|()
decl_stmt|;
name|iCacheManager
operator|=
operator|new
name|DefaultCacheManager
argument_list|(
name|global
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|stop
parameter_list|()
block|{
name|super
operator|.
name|stop
argument_list|()
expr_stmt|;
name|iCacheManager
operator|.
name|stop
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasMaster
parameter_list|(
name|String
name|sessionId
parameter_list|)
block|{
name|OnlineSectioningServer
name|server
init|=
name|getInstance
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sessionId
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|server
operator|!=
literal|null
operator|&&
name|server
operator|.
name|isMaster
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|createRemoteSolver
parameter_list|(
name|String
name|sessionId
parameter_list|,
name|DataProperties
name|config
parameter_list|,
name|Address
name|caller
parameter_list|)
block|{
if|if
condition|(
operator|!
name|canCreateSolver
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sessionId
argument_list|)
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|super
operator|.
name|createSolver
argument_list|(
name|sessionId
argument_list|,
name|config
argument_list|)
operator|!=
literal|null
return|;
block|}
specifier|protected
name|boolean
name|canCreateSolver
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|String
name|year
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.year"
argument_list|)
decl_stmt|;
if|if
condition|(
name|year
operator|!=
literal|null
operator|&&
operator|!
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|.
name|matches
argument_list|(
name|year
argument_list|)
condition|)
return|return
literal|false
return|;
name|String
name|term
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.term"
argument_list|)
decl_stmt|;
if|if
condition|(
name|term
operator|!=
literal|null
operator|&&
operator|!
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|.
name|matches
argument_list|(
name|term
argument_list|)
condition|)
return|return
literal|false
return|;
name|String
name|campus
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.campus"
argument_list|)
decl_stmt|;
if|if
condition|(
name|campus
operator|!=
literal|null
operator|&&
operator|!
name|session
operator|.
name|getAcademicInitiative
argument_list|()
operator|.
name|matches
argument_list|(
name|campus
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
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
name|sessionId
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
name|OnlineSectioningServer
name|solver
init|=
name|iInstances
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|sessionId
argument_list|)
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
literal|"Server "
operator|+
name|sessionId
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
name|sessionId
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
name|debug
argument_list|(
literal|"Excution of "
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|" on server "
operator|+
name|sessionId
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
block|}
annotation|@
name|Override
specifier|public
name|OnlineSectioningServer
name|createProxy
parameter_list|(
name|Address
name|address
parameter_list|,
name|String
name|user
parameter_list|)
block|{
name|ServerInvocationHandler
name|handler
init|=
operator|new
name|ServerInvocationHandler
argument_list|(
name|address
argument_list|,
name|user
argument_list|)
decl_stmt|;
name|OnlineSectioningServer
name|px
init|=
operator|(
name|OnlineSectioningServer
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|SolverProxy
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
name|OnlineSectioningServer
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
decl_stmt|;
return|return
name|px
return|;
block|}
annotation|@
name|Override
specifier|public
name|OnlineSectioningServer
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
name|OnlineSectioningServer
name|px
init|=
operator|(
name|OnlineSectioningServer
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|SolverProxy
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
name|OnlineSectioningServer
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
decl_stmt|;
return|return
name|px
return|;
block|}
specifier|public
class|class
name|ServerInvocationHandler
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
name|ServerInvocationHandler
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
annotation|@
name|Override
specifier|public
name|OnlineSectioningServerContext
name|getServerContext
parameter_list|(
specifier|final
name|Long
name|academicSessionId
parameter_list|)
block|{
return|return
operator|new
name|OnlineSectioningServerContext
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
block|{
return|return
name|academicSessionId
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isWaitTillStarted
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|EmbeddedCacheManager
name|getCacheManager
parameter_list|()
block|{
return|return
name|OnlineStudentSchedulingContainerRemote
operator|.
name|this
operator|.
name|getCacheManager
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|LockService
name|getLockService
parameter_list|()
block|{
return|return
name|iLockService
return|;
block|}
block|}
return|;
block|}
specifier|public
name|EmbeddedCacheManager
name|getCacheManager
parameter_list|()
block|{
return|return
name|iCacheManager
return|;
block|}
block|}
end_class

end_unit

