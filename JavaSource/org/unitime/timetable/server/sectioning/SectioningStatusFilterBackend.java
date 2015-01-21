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
name|server
operator|.
name|sectioning
package|;
end_package

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
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|client
operator|.
name|sectioning
operator|.
name|SectioningStatusFilterBox
operator|.
name|SectioningStatusFilterRpcRequest
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|resources
operator|.
name|StudentSectioningMessages
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
name|server
operator|.
name|UniTimePrincipal
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
name|SectioningException
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
name|EventInterface
operator|.
name|FilterRpcResponse
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
name|OnlineSectioningLog
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
name|status
operator|.
name|SectioningStatusFilterAction
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|UserContext
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|service
operator|.
name|SolverServerService
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
name|service
operator|.
name|SolverService
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
name|GwtRpcImplements
argument_list|(
name|SectioningStatusFilterRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SectioningStatusFilterBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SectioningStatusFilterRpcRequest
argument_list|,
name|FilterRpcResponse
argument_list|>
block|{
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|SectioningStatusFilterBackend
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
name|studentSectioningSolverService
decl_stmt|;
specifier|private
annotation|@
name|Autowired
name|SolverServerService
name|solverServerService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|FilterRpcResponse
name|execute
parameter_list|(
name|SectioningStatusFilterRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
try|try
block|{
name|boolean
name|online
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getOption
argument_list|(
literal|"online"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|)
block|{
name|request
operator|.
name|setOption
argument_list|(
literal|"user"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|ConsentApproval
argument_list|)
condition|)
name|request
operator|.
name|setOption
argument_list|(
literal|"approval"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|online
condition|)
block|{
name|Long
name|sessionId
init|=
name|getStatusPageSessionId
argument_list|(
name|context
argument_list|)
decl_stmt|;
name|OnlineSectioningServer
name|server
init|=
name|solverServerService
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|getSolver
argument_list|(
name|sessionId
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|server
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionBadSession
argument_list|()
argument_list|)
throw|;
name|context
operator|.
name|checkPermission
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"Session"
argument_list|,
name|Right
operator|.
name|SchedulingDashboard
argument_list|)
expr_stmt|;
name|request
operator|.
name|setSessionId
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|SectioningStatusFilterAction
operator|.
name|class
argument_list|)
operator|.
name|forRequest
argument_list|(
name|request
argument_list|)
argument_list|,
name|currentUser
argument_list|(
name|context
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
name|OnlineSectioningServer
name|server
init|=
name|studentSectioningSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|server
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNoSolver
argument_list|()
argument_list|)
throw|;
name|context
operator|.
name|checkPermission
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"Session"
argument_list|,
name|Right
operator|.
name|StudentSectioningSolverDashboard
argument_list|)
expr_stmt|;
name|request
operator|.
name|setSessionId
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|SectioningStatusFilterAction
operator|.
name|class
argument_list|)
operator|.
name|forRequest
argument_list|(
name|request
argument_list|)
argument_list|,
name|currentUser
argument_list|(
name|context
argument_list|)
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|PageAccessException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|SectioningException
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
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|private
name|Long
name|getStatusPageSessionId
parameter_list|(
name|SessionContext
name|context
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
block|{
name|UserContext
name|user
init|=
name|context
operator|.
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
throw|throw
operator|new
name|PageAccessException
argument_list|(
name|context
operator|.
name|isHttpSessionNew
argument_list|()
condition|?
name|MSG
operator|.
name|exceptionHttpSessionExpired
argument_list|()
else|:
name|MSG
operator|.
name|exceptionLoginRequired
argument_list|()
argument_list|)
throw|;
if|if
condition|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|==
literal|null
condition|)
block|{
name|Long
name|sessionId
init|=
name|getLastSessionId
argument_list|(
name|context
argument_list|)
decl_stmt|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
return|return
name|sessionId
return|;
block|}
else|else
block|{
return|return
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
return|;
block|}
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNoAcademicSession
argument_list|()
argument_list|)
throw|;
block|}
specifier|public
name|Long
name|getLastSessionId
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
name|Long
name|lastSessionId
init|=
operator|(
name|Long
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"sessionId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastSessionId
operator|==
literal|null
condition|)
block|{
name|UserContext
name|user
init|=
name|context
operator|.
name|getUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|Long
name|sessionId
init|=
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
name|lastSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
block|}
return|return
name|lastSessionId
return|;
block|}
specifier|private
name|OnlineSectioningLog
operator|.
name|Entity
name|currentUser
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
name|UserContext
name|user
init|=
name|context
operator|.
name|getUser
argument_list|()
decl_stmt|;
name|UniTimePrincipal
name|principal
init|=
operator|(
name|UniTimePrincipal
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"user"
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
return|return
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|user
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|user
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
name|user
operator|.
name|getUsername
argument_list|()
else|:
name|user
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
condition|?
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|MANAGER
else|:
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|STUDENT
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
if|else if
condition|(
name|principal
operator|!=
literal|null
condition|)
block|{
return|return
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|principal
operator|.
name|getExternalId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|principal
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|STUDENT
argument_list|)
operator|.
name|build
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

