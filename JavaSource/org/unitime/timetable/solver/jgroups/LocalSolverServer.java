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
name|log4j
operator|.
name|Logger
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
name|InMemoryServer
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
name|LocalSolverServer
extends|extends
name|AbstractSolverServer
block|{
specifier|private
name|CourseSolverContainer
name|iCourseSolverContainer
decl_stmt|;
specifier|private
name|ExaminationSolverContainer
name|iExamSolverContainer
decl_stmt|;
specifier|private
name|StudentSolverContainer
name|iStudentSolverContainer
decl_stmt|;
specifier|private
name|InstructorSchedulingContainer
name|iInstructorSchedulingContainer
decl_stmt|;
specifier|private
name|OnlineStudentSchedulingContainer
name|iOnlineStudentSchedulingContainer
decl_stmt|;
specifier|private
name|Updater
name|iUpdater
decl_stmt|;
specifier|public
name|LocalSolverServer
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|iCourseSolverContainer
operator|=
operator|new
name|CourseSolverContainer
argument_list|()
expr_stmt|;
name|iExamSolverContainer
operator|=
operator|new
name|ExaminationSolverContainer
argument_list|()
expr_stmt|;
name|iStudentSolverContainer
operator|=
operator|new
name|StudentSolverContainer
argument_list|()
expr_stmt|;
name|iInstructorSchedulingContainer
operator|=
operator|new
name|InstructorSchedulingContainer
argument_list|()
expr_stmt|;
name|iOnlineStudentSchedulingContainer
operator|=
operator|new
name|OnlineStudentSchedulingContainer
argument_list|()
expr_stmt|;
name|iUpdater
operator|=
operator|new
name|Updater
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|()
block|{
name|iCourseSolverContainer
operator|.
name|start
argument_list|()
expr_stmt|;
name|iExamSolverContainer
operator|.
name|start
argument_list|()
expr_stmt|;
name|iStudentSolverContainer
operator|.
name|start
argument_list|()
expr_stmt|;
name|iInstructorSchedulingContainer
operator|.
name|start
argument_list|()
expr_stmt|;
name|iOnlineStudentSchedulingContainer
operator|.
name|start
argument_list|()
expr_stmt|;
name|iUpdater
operator|.
name|start
argument_list|()
expr_stmt|;
name|super
operator|.
name|start
argument_list|()
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
name|iCourseSolverContainer
operator|.
name|stop
argument_list|()
expr_stmt|;
name|iExamSolverContainer
operator|.
name|stop
argument_list|()
expr_stmt|;
name|iStudentSolverContainer
operator|.
name|stop
argument_list|()
expr_stmt|;
name|iInstructorSchedulingContainer
operator|.
name|stop
argument_list|()
expr_stmt|;
name|iOnlineStudentSchedulingContainer
operator|.
name|stop
argument_list|()
expr_stmt|;
name|iUpdater
operator|.
name|stopUpdating
argument_list|()
expr_stmt|;
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
name|int
name|getUsage
parameter_list|()
block|{
name|int
name|ret
init|=
name|super
operator|.
name|getUsage
argument_list|()
decl_stmt|;
name|ret
operator|+=
name|iCourseSolverContainer
operator|.
name|getUsage
argument_list|()
expr_stmt|;
name|ret
operator|+=
name|iExamSolverContainer
operator|.
name|getUsage
argument_list|()
expr_stmt|;
name|ret
operator|+=
name|iStudentSolverContainer
operator|.
name|getUsage
argument_list|()
expr_stmt|;
name|ret
operator|+=
name|iInstructorSchedulingContainer
operator|.
name|getUsage
argument_list|()
expr_stmt|;
name|ret
operator|+=
name|iOnlineStudentSchedulingContainer
operator|.
name|getUsage
argument_list|()
expr_stmt|;
return|return
name|ret
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
name|List
argument_list|<
name|SolverServer
argument_list|>
name|servers
init|=
operator|new
name|ArrayList
argument_list|<
name|SolverServer
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|onlyAvailable
operator|||
name|isActive
argument_list|()
condition|)
name|servers
operator|.
name|add
argument_list|(
name|this
argument_list|)
expr_stmt|;
return|return
name|servers
return|;
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
name|SolverContainer
argument_list|<
name|SolverProxy
argument_list|>
name|getCourseSolverContainer
parameter_list|()
block|{
return|return
name|iCourseSolverContainer
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
name|iExamSolverContainer
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
name|iStudentSolverContainer
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
name|iOnlineStudentSchedulingContainer
return|;
block|}
specifier|public
class|class
name|Updater
extends|extends
name|Thread
block|{
specifier|private
name|Logger
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
specifier|public
name|Updater
parameter_list|()
block|{
name|super
argument_list|()
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
name|Logger
operator|.
name|getLogger
argument_list|(
name|OnlineStudentSchedulingGenericUpdater
operator|.
name|class
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
name|SessionDAO
operator|.
name|isConfigured
argument_list|()
condition|)
block|{
name|iLog
operator|.
name|info
argument_list|(
literal|"Hibernate is not yet configured, waiting..."
argument_list|)
expr_stmt|;
return|return;
block|}
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
name|iOnlineStudentSchedulingContainer
operator|.
name|getSolvers
argument_list|()
argument_list|)
decl_stmt|;
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
name|contains
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
continue|continue;
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
name|Properties
name|properties
init|=
name|ApplicationProperties
operator|.
name|getConfigProperties
argument_list|()
decl_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|OnlineSchedulingServerClass
operator|.
name|value
argument_list|()
operator|==
literal|null
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
name|ApplicationProperty
operator|.
name|OnlineSchedulingServerClass
operator|.
name|key
argument_list|()
argument_list|,
name|InMemoryServer
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|iOnlineStudentSchedulingContainer
operator|.
name|createSolver
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
literal|null
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
name|fatal
argument_list|(
literal|"Unable to upadte session "
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
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
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

