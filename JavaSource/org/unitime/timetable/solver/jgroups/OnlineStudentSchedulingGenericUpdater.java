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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Iterator
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
name|Map
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|locks
operator|.
name|Lock
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
name|ClusterDiscoveryDAO
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineStudentSchedulingGenericUpdater
extends|extends
name|Thread
block|{
specifier|private
name|Log
name|iLog
decl_stmt|;
specifier|private
name|long
name|iSleepTimeInSeconds
init|=
literal|5
decl_stmt|;
specifier|private
name|boolean
name|iRun
init|=
literal|true
decl_stmt|,
name|iPause
init|=
literal|false
decl_stmt|;
specifier|private
name|RpcDispatcher
name|iDispatcher
decl_stmt|;
specifier|private
name|OnlineStudentSchedulingContainerRemote
name|iContainer
decl_stmt|;
specifier|public
name|OnlineStudentSchedulingGenericUpdater
parameter_list|(
name|RpcDispatcher
name|dispatcher
parameter_list|,
name|OnlineStudentSchedulingContainerRemote
name|container
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|iDispatcher
operator|=
name|dispatcher
expr_stmt|;
name|iContainer
operator|=
name|container
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setName
argument_list|(
literal|"Updater[generic]"
argument_list|)
expr_stmt|;
name|iSleepTimeInSeconds
operator|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingQueueLoadInterval
operator|.
name|intValue
argument_list|()
expr_stmt|;
name|iLog
operator|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|OnlineStudentSchedulingGenericUpdater
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|+
literal|".updater[generic]"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|iLog
operator|.
name|info
argument_list|(
literal|"Generic updater started."
argument_list|)
expr_stmt|;
while|while
condition|(
name|iRun
condition|)
block|{
try|try
block|{
name|sleep
argument_list|(
name|iSleepTimeInSeconds
operator|*
literal|1000
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|iRun
operator|&&
operator|!
name|iPause
condition|)
name|checkForNewServers
argument_list|()
expr_stmt|;
block|}
name|iLog
operator|.
name|info
argument_list|(
literal|"Generic updater stopped."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Generic updater failed, "
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
specifier|public
specifier|synchronized
name|void
name|pauseUpading
parameter_list|()
block|{
name|iPause
operator|=
literal|true
expr_stmt|;
name|iLog
operator|.
name|info
argument_list|(
literal|"Generic updater paused."
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|void
name|resumeUpading
parameter_list|()
block|{
name|interrupt
argument_list|()
expr_stmt|;
name|iPause
operator|=
literal|false
expr_stmt|;
name|iLog
operator|.
name|info
argument_list|(
literal|"Generic updater resumed."
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|stopUpdating
parameter_list|()
block|{
name|iRun
operator|=
literal|false
expr_stmt|;
name|interrupt
argument_list|()
expr_stmt|;
try|try
block|{
name|this
operator|.
name|join
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
block|}
specifier|public
specifier|synchronized
name|void
name|checkForNewServers
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isCoordinator
argument_list|()
condition|)
return|return;
if|if
condition|(
operator|!
name|ClusterDiscoveryDAO
operator|.
name|isConfigured
argument_list|()
condition|)
block|{
name|iLog
operator|.
name|info
argument_list|(
literal|"Hibernate is not configured yet, will check for new servers later..."
argument_list|)
expr_stmt|;
return|return;
block|}
name|Lock
name|lock
init|=
name|iContainer
operator|.
name|getLockService
argument_list|()
operator|.
name|getLock
argument_list|(
literal|"updater[generic].check"
argument_list|)
decl_stmt|;
name|lock
operator|.
name|lock
argument_list|()
expr_stmt|;
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
name|getSession
argument_list|()
decl_stmt|;
try|try
block|{
name|boolean
name|replicate
init|=
name|ApplicationProperty
operator|.
name|OnlineSchedulingServerReplicated
operator|.
name|isTrue
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|Address
argument_list|>
argument_list|>
name|solvers
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Set
argument_list|<
name|Address
argument_list|>
argument_list|>
argument_list|()
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
if|if
condition|(
name|rsp
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
for|for
control|(
name|String
name|solver
range|:
name|rsp
operator|.
name|getValue
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|Address
argument_list|>
name|members
init|=
name|solvers
operator|.
name|get
argument_list|(
name|solver
argument_list|)
decl_stmt|;
if|if
condition|(
name|members
operator|==
literal|null
condition|)
block|{
name|members
operator|=
operator|new
name|HashSet
argument_list|<
name|Address
argument_list|>
argument_list|()
expr_stmt|;
name|solvers
operator|.
name|put
argument_list|(
name|solver
argument_list|,
name|members
argument_list|)
expr_stmt|;
block|}
name|members
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
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Failed to retrieve servers: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
for|for
control|(
name|Iterator
argument_list|<
name|Session
argument_list|>
name|i
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|hibSession
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Session
name|session
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|solvers
operator|.
name|containsKey
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
try|try
block|{
name|Set
argument_list|<
name|Address
argument_list|>
name|members
init|=
name|solvers
operator|.
name|get
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|members
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|List
argument_list|<
name|Address
argument_list|>
name|masters
init|=
operator|new
name|ArrayList
argument_list|<
name|Address
argument_list|>
argument_list|()
decl_stmt|;
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
name|members
argument_list|,
literal|"hasMaster"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
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
name|Boolean
operator|.
name|TRUE
operator|.
name|equals
argument_list|(
name|rsp
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
name|masters
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
name|masters
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|iLog
operator|.
name|warn
argument_list|(
name|masters
operator|.
name|size
argument_list|()
operator|+
literal|" masters for "
operator|+
name|session
operator|.
name|getLabel
argument_list|()
operator|+
literal|" detected."
argument_list|)
expr_stmt|;
name|iLog
operator|.
name|info
argument_list|(
name|iContainer
operator|.
name|getLockService
argument_list|()
operator|.
name|printLocks
argument_list|()
argument_list|)
expr_stmt|;
name|iLog
operator|.
name|info
argument_list|(
literal|"Releasing master locks for "
operator|+
name|session
operator|.
name|getLabel
argument_list|()
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
name|masters
argument_list|,
literal|"invoke"
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"setProperty"
block|,
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|,
name|Object
operator|.
name|class
block|}
block|,
operator|new
name|Object
index|[]
block|{
literal|"ReadyToServe"
block|,
name|Boolean
operator|.
name|FALSE
block|}
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
name|sAllResponses
argument_list|)
expr_stmt|;
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
name|masters
argument_list|,
literal|"invoke"
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"setProperty"
block|,
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
operator|new
name|Class
index|[]
block|{
name|String
operator|.
name|class
block|,
name|Object
operator|.
name|class
block|}
block|,
operator|new
name|Object
index|[]
block|{
literal|"ReloadIsNeeded"
block|,
name|Boolean
operator|.
name|TRUE
block|}
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
name|sAllResponses
argument_list|)
expr_stmt|;
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethods
argument_list|(
name|masters
argument_list|,
literal|"invoke"
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"releaseMasterLockIfHeld"
block|,
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
operator|new
name|Class
index|[]
block|{}
block|,
operator|new
name|Object
index|[]
block|{}
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
name|sAllResponses
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Failed to release master locks for "
operator|+
name|session
operator|.
name|getLabel
argument_list|()
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
continue|continue;
block|}
if|if
condition|(
operator|!
name|replicate
condition|)
continue|continue;
block|}
if|if
condition|(
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|isTestSession
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canSectionAssistStudents
argument_list|()
operator|&&
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canOnlineSectionStudents
argument_list|()
condition|)
continue|continue;
name|int
name|nrSolutions
init|=
operator|(
operator|(
name|Number
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select count(s) from Solution s where s.owner.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|nrSolutions
operator|==
literal|0
condition|)
continue|continue;
name|List
argument_list|<
name|Address
argument_list|>
name|available
init|=
operator|new
name|ArrayList
argument_list|<
name|Address
argument_list|>
argument_list|()
decl_stmt|;
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
literal|"isAvailable"
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
name|Boolean
argument_list|>
name|rsp
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
name|rsp
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
name|available
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
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|fatal
argument_list|(
literal|"Unable to update session "
operator|+
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
literal|" "
operator|+
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|+
literal|" ("
operator|+
name|session
operator|.
name|getAcademicInitiative
argument_list|()
operator|+
literal|"), reason: "
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
if|if
condition|(
name|available
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|iLog
operator|.
name|fatal
argument_list|(
literal|"Unable to update session "
operator|+
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
literal|" "
operator|+
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|+
literal|" ("
operator|+
name|session
operator|.
name|getAcademicInitiative
argument_list|()
operator|+
literal|"), reason: no server available."
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|replicate
condition|)
block|{
name|Collections
operator|.
name|shuffle
argument_list|(
name|available
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Address
argument_list|>
name|members
init|=
name|solvers
operator|.
name|get
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|members
operator|!=
literal|null
condition|)
block|{
name|boolean
name|ready
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Address
name|address
range|:
name|members
control|)
block|{
name|OnlineSectioningServer
name|server
init|=
name|iContainer
operator|.
name|createProxy
argument_list|(
name|address
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|server
operator|.
name|isReady
argument_list|()
condition|)
block|{
name|ready
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|ready
condition|)
continue|continue;
block|}
try|try
block|{
for|for
control|(
name|Address
name|address
range|:
name|available
control|)
block|{
if|if
condition|(
name|members
operator|!=
literal|null
operator|&&
name|members
operator|.
name|contains
argument_list|(
name|address
argument_list|)
condition|)
continue|continue;
name|Boolean
name|created
init|=
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethod
argument_list|(
name|address
argument_list|,
literal|"createRemoteSolver"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
literal|null
block|,
name|iDispatcher
operator|.
name|getChannel
argument_list|()
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
decl_stmt|;
comment|// startup only one server first
if|if
condition|(
name|members
operator|==
literal|null
operator|&&
name|created
condition|)
break|break;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|fatal
argument_list|(
literal|"Unable to update session "
operator|+
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
literal|" "
operator|+
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|+
literal|" ("
operator|+
name|session
operator|.
name|getAcademicInitiative
argument_list|()
operator|+
literal|"), reason: "
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
else|else
block|{
try|try
block|{
comment|// retrieve usage of the available serves
name|Map
argument_list|<
name|Address
argument_list|,
name|Integer
argument_list|>
name|usages
init|=
operator|new
name|HashMap
argument_list|<
name|Address
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Address
name|address
range|:
name|available
control|)
block|{
name|Integer
name|usage
init|=
name|iDispatcher
operator|.
name|callRemoteMethod
argument_list|(
name|address
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
name|sFirstResponse
argument_list|)
decl_stmt|;
name|usages
operator|.
name|put
argument_list|(
name|address
argument_list|,
name|usage
argument_list|)
expr_stmt|;
block|}
comment|// while there is a server available, pick one with the lowest usage and try to create the solver there
while|while
condition|(
operator|!
name|usages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Address
name|bestAddress
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
name|Map
operator|.
name|Entry
argument_list|<
name|Address
argument_list|,
name|Integer
argument_list|>
name|entry
range|:
name|usages
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|bestAddress
operator|==
literal|null
operator|||
name|bestUsage
operator|>
name|entry
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|bestAddress
operator|=
name|entry
operator|.
name|getKey
argument_list|()
expr_stmt|;
name|bestUsage
operator|=
name|entry
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
block|}
name|usages
operator|.
name|remove
argument_list|(
name|bestAddress
argument_list|)
expr_stmt|;
name|Boolean
name|created
init|=
name|iContainer
operator|.
name|getDispatcher
argument_list|()
operator|.
name|callRemoteMethod
argument_list|(
name|bestAddress
argument_list|,
literal|"createRemoteSolver"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
literal|null
block|,
name|iDispatcher
operator|.
name|getChannel
argument_list|()
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
decl_stmt|;
if|if
condition|(
name|created
condition|)
break|break;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|fatal
argument_list|(
literal|"Unable to update session "
operator|+
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
literal|" "
operator|+
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|+
literal|" ("
operator|+
name|session
operator|.
name|getAcademicInitiative
argument_list|()
operator|+
literal|"), reason: "
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
block|}
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
name|lock
operator|.
name|unlock
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|isCoordinator
parameter_list|()
block|{
return|return
name|iDispatcher
operator|.
name|getChannel
argument_list|()
operator|.
name|getView
argument_list|()
operator|.
name|getMembers
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|equals
argument_list|(
name|iDispatcher
operator|.
name|getChannel
argument_list|()
operator|.
name|getAddress
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

