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
name|service
package|;
end_package

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
name|blocks
operator|.
name|RpcDispatcher
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
name|DisposableBean
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
name|InitializingBean
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|jgroups
operator|.
name|UniTimeChannelLookup
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
name|jgroups
operator|.
name|LocalSolverServer
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
name|jgroups
operator|.
name|RemoteSolverContainer
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
name|jgroups
operator|.
name|SolverContainer
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
name|jgroups
operator|.
name|SolverContainerWrapper
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
name|jgroups
operator|.
name|SolverServer
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
name|jgroups
operator|.
name|SolverServerImplementation
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
annotation|@
name|Service
argument_list|(
literal|"solverServerService"
argument_list|)
specifier|public
class|class
name|SolverServerService
implements|implements
name|InitializingBean
implements|,
name|DisposableBean
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
name|SolverServerService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|JChannel
name|iChannel
init|=
literal|null
decl_stmt|;
specifier|private
name|SolverServer
name|iServer
init|=
literal|null
decl_stmt|;
specifier|private
name|SolverContainer
argument_list|<
name|SolverProxy
argument_list|>
name|iCourseSolverContainer
decl_stmt|;
specifier|private
name|SolverContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
name|iExamSolverContainer
decl_stmt|;
specifier|private
name|SolverContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
name|iStudentSolverContainer
decl_stmt|;
specifier|private
name|SolverContainer
argument_list|<
name|OnlineSectioningServer
argument_list|>
name|iOnlineStudentSchedulingContainer
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|afterPropertiesSet
parameter_list|()
throws|throws
name|Exception
block|{
try|try
block|{
if|if
condition|(
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.solver.cluster"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
block|{
name|iServer
operator|=
operator|new
name|LocalSolverServer
argument_list|()
expr_stmt|;
name|iServer
operator|.
name|start
argument_list|()
expr_stmt|;
name|iCourseSolverContainer
operator|=
name|iServer
operator|.
name|getCourseSolverContainer
argument_list|()
expr_stmt|;
name|iExamSolverContainer
operator|=
name|iServer
operator|.
name|getExamSolverContainer
argument_list|()
expr_stmt|;
name|iStudentSolverContainer
operator|=
name|iServer
operator|.
name|getStudentSolverContainer
argument_list|()
expr_stmt|;
name|iOnlineStudentSchedulingContainer
operator|=
name|iServer
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iChannel
operator|=
operator|(
name|JChannel
operator|)
operator|new
name|UniTimeChannelLookup
argument_list|()
operator|.
name|getJGroupsChannel
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|iServer
operator|=
operator|new
name|SolverServerImplementation
argument_list|(
literal|true
argument_list|,
name|iChannel
argument_list|)
expr_stmt|;
name|iChannel
operator|.
name|connect
argument_list|(
literal|"UniTime:rpc"
argument_list|)
expr_stmt|;
name|iServer
operator|.
name|start
argument_list|()
expr_stmt|;
name|iCourseSolverContainer
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|SolverProxy
argument_list|>
argument_list|(
operator|(
operator|(
name|SolverServerImplementation
operator|)
name|iServer
operator|)
operator|.
name|getDispatcher
argument_list|()
argument_list|,
operator|(
name|RemoteSolverContainer
argument_list|<
name|SolverProxy
argument_list|>
operator|)
name|iServer
operator|.
name|getCourseSolverContainer
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iExamSolverContainer
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|ExamSolverProxy
argument_list|>
argument_list|(
operator|(
operator|(
name|SolverServerImplementation
operator|)
name|iServer
operator|)
operator|.
name|getDispatcher
argument_list|()
argument_list|,
operator|(
name|RemoteSolverContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
operator|)
name|iServer
operator|.
name|getExamSolverContainer
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iStudentSolverContainer
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|StudentSolverProxy
argument_list|>
argument_list|(
operator|(
operator|(
name|SolverServerImplementation
operator|)
name|iServer
operator|)
operator|.
name|getDispatcher
argument_list|()
argument_list|,
operator|(
name|RemoteSolverContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
operator|)
name|iServer
operator|.
name|getStudentSolverContainer
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iOnlineStudentSchedulingContainer
operator|=
operator|new
name|SolverContainerWrapper
argument_list|<
name|OnlineSectioningServer
argument_list|>
argument_list|(
operator|(
operator|(
name|SolverServerImplementation
operator|)
name|iServer
operator|)
operator|.
name|getDispatcher
argument_list|()
argument_list|,
operator|(
name|RemoteSolverContainer
argument_list|<
name|OnlineSectioningServer
argument_list|>
operator|)
name|iServer
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
argument_list|,
literal|false
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
name|fatal
argument_list|(
literal|"Failed to start solver server: "
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
specifier|private
name|RpcDispatcher
name|getDispatcher
parameter_list|()
block|{
if|if
condition|(
name|iServer
operator|instanceof
name|SolverServerImplementation
condition|)
return|return
operator|(
operator|(
name|SolverServerImplementation
operator|)
name|iServer
operator|)
operator|.
name|getDispatcher
argument_list|()
return|;
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|destroy
parameter_list|()
throws|throws
name|Exception
block|{
try|try
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Server is going down..."
argument_list|)
expr_stmt|;
name|iServer
operator|.
name|stop
argument_list|()
expr_stmt|;
if|if
condition|(
name|iChannel
operator|!=
literal|null
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Disconnecting from the channel..."
argument_list|)
expr_stmt|;
name|iChannel
operator|.
name|disconnect
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Closing the channel..."
argument_list|)
expr_stmt|;
name|iChannel
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|iServer
operator|=
literal|null
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
name|fatal
argument_list|(
literal|"Failed to stop solver server: "
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
return|return
name|iServer
operator|.
name|getServers
argument_list|(
name|onlyAvailable
argument_list|)
return|;
block|}
specifier|public
name|SolverServer
name|getLocalServer
parameter_list|()
block|{
return|return
name|iServer
return|;
block|}
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
specifier|public
name|SolverProxy
name|createCourseSolver
parameter_list|(
name|String
name|host
parameter_list|,
name|String
name|user
parameter_list|,
name|DataProperties
name|properties
parameter_list|)
block|{
if|if
condition|(
name|host
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
literal|"local"
operator|.
name|equals
argument_list|(
name|host
argument_list|)
condition|)
block|{
name|SolverProxy
name|solver
init|=
name|iServer
operator|.
name|getCourseSolverContainer
argument_list|()
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
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
if|if
condition|(
name|server
operator|.
name|getHost
argument_list|()
operator|.
name|equals
argument_list|(
name|host
argument_list|)
condition|)
block|{
name|SolverProxy
name|solver
init|=
name|server
operator|.
name|getCourseSolverContainer
argument_list|()
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
block|}
block|}
name|SolverProxy
name|solver
init|=
name|iCourseSolverContainer
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
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
specifier|public
name|ExamSolverProxy
name|createExamSolver
parameter_list|(
name|String
name|host
parameter_list|,
name|String
name|user
parameter_list|,
name|DataProperties
name|properties
parameter_list|)
block|{
if|if
condition|(
name|host
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
literal|"local"
operator|.
name|equals
argument_list|(
name|host
argument_list|)
condition|)
block|{
name|ExamSolverProxy
name|solver
init|=
name|iServer
operator|.
name|getExamSolverContainer
argument_list|()
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
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
if|if
condition|(
name|server
operator|.
name|getHost
argument_list|()
operator|.
name|equals
argument_list|(
name|host
argument_list|)
condition|)
block|{
name|ExamSolverProxy
name|solver
init|=
name|server
operator|.
name|getExamSolverContainer
argument_list|()
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
block|}
block|}
name|ExamSolverProxy
name|solver
init|=
name|iExamSolverContainer
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
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
specifier|public
name|StudentSolverProxy
name|createStudentSolver
parameter_list|(
name|String
name|host
parameter_list|,
name|String
name|user
parameter_list|,
name|DataProperties
name|properties
parameter_list|)
block|{
if|if
condition|(
name|host
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
literal|"local"
operator|.
name|equals
argument_list|(
name|host
argument_list|)
condition|)
block|{
name|StudentSolverProxy
name|solver
init|=
name|iServer
operator|.
name|getStudentSolverContainer
argument_list|()
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
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
if|if
condition|(
name|server
operator|.
name|getHost
argument_list|()
operator|.
name|equals
argument_list|(
name|host
argument_list|)
condition|)
block|{
name|StudentSolverProxy
name|solver
init|=
name|server
operator|.
name|getStudentSolverContainer
argument_list|()
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
block|}
block|}
name|StudentSolverProxy
name|solver
init|=
name|iStudentSolverContainer
operator|.
name|createSolver
argument_list|(
name|user
argument_list|,
name|properties
argument_list|)
decl_stmt|;
return|return
name|solver
return|;
block|}
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
name|SolverServer
name|getServer
parameter_list|(
name|String
name|host
parameter_list|)
block|{
if|if
condition|(
literal|"local"
operator|.
name|equals
argument_list|(
name|host
argument_list|)
operator|||
name|host
operator|==
literal|null
condition|)
return|return
name|iServer
return|;
if|if
condition|(
name|iChannel
operator|!=
literal|null
condition|)
for|for
control|(
name|Address
name|address
range|:
name|iChannel
operator|.
name|getView
argument_list|()
operator|.
name|getMembers
argument_list|()
control|)
block|{
if|if
condition|(
name|host
operator|.
name|equals
argument_list|(
name|address
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
return|return
name|iServer
operator|.
name|crateServerProxy
argument_list|(
name|address
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|boolean
name|isOnlineStudentSchedulingEnabled
parameter_list|()
block|{
return|return
operator|!
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|getSolvers
argument_list|()
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isStudentRegistrationEnabled
parameter_list|()
block|{
for|for
control|(
name|Session
name|session
range|:
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
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
name|canOnlineSectionStudents
argument_list|()
operator|&&
operator|!
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canSectionAssistStudents
argument_list|()
operator|&&
name|session
operator|.
name|getStatusType
argument_list|()
operator|.
name|canPreRegisterStudents
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|setApplicationProperty
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|RpcDispatcher
name|dispatcher
init|=
name|getDispatcher
argument_list|()
decl_stmt|;
if|if
condition|(
name|dispatcher
operator|!=
literal|null
condition|)
name|dispatcher
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"setApplicationProperty"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|sessionId
block|,
name|key
block|,
name|value
block|}
argument_list|,
operator|new
name|Class
index|[]
block|{
name|Long
operator|.
name|class
block|,
name|String
operator|.
name|class
block|,
name|String
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sAllResponses
argument_list|)
expr_stmt|;
else|else
name|iServer
operator|.
name|setApplicationProperty
argument_list|(
name|sessionId
argument_list|,
name|key
argument_list|,
name|value
argument_list|)
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
literal|"Failed to update the application property "
operator|+
name|key
operator|+
literal|" along the cluster: "
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
name|void
name|setLoggingLevel
parameter_list|(
name|String
name|name
parameter_list|,
name|Integer
name|level
parameter_list|)
block|{
try|try
block|{
name|RpcDispatcher
name|dispatcher
init|=
name|getDispatcher
argument_list|()
decl_stmt|;
if|if
condition|(
name|dispatcher
operator|!=
literal|null
condition|)
name|dispatcher
operator|.
name|callRemoteMethods
argument_list|(
literal|null
argument_list|,
literal|"setLoggingLevel"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|name
block|,
name|level
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
name|Integer
operator|.
name|class
block|}
argument_list|,
name|SolverServerImplementation
operator|.
name|sAllResponses
argument_list|)
expr_stmt|;
else|else
name|iServer
operator|.
name|setLoggingLevel
argument_list|(
name|name
argument_list|,
name|level
argument_list|)
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
literal|"Failed to update the logging level for "
operator|+
name|name
operator|+
literal|" along the cluster: "
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
end_class

end_unit

