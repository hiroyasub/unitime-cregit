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
name|script
package|;
end_package

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
name|List
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
name|UserProperty
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
name|client
operator|.
name|GwtRpcResponseList
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
name|shared
operator|.
name|TaskInterface
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
name|ContactInterface
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
name|TaskInterface
operator|.
name|ExecutionStatus
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
name|TaskInterface
operator|.
name|GetTasksRpcRequest
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
name|TaskInterface
operator|.
name|TaskExecutionInterface
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
name|PeriodicTask
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
name|TaskExecution
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
name|TaskParameter
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
name|PeriodicTaskDAO
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
name|util
operator|.
name|CalendarUtils
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
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|GetTasksRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|GetTasksBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|GetTasksRpcRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|TaskInterface
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|TaskInterface
argument_list|>
name|execute
parameter_list|(
name|GetTasksRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Tasks
argument_list|)
expr_stmt|;
name|GwtRpcResponseList
argument_list|<
name|TaskInterface
argument_list|>
name|tasks
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|TaskInterface
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|PeriodicTask
name|t
range|:
operator|(
name|List
argument_list|<
name|PeriodicTask
argument_list|>
operator|)
name|PeriodicTaskDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from PeriodicTask where session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|tasks
operator|.
name|add
argument_list|(
name|getTask
argument_list|(
name|t
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|tasks
argument_list|)
expr_stmt|;
return|return
name|tasks
return|;
block|}
specifier|public
specifier|static
name|TaskInterface
name|getTask
parameter_list|(
name|PeriodicTask
name|t
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|TaskInterface
name|task
init|=
operator|new
name|TaskInterface
argument_list|()
decl_stmt|;
name|task
operator|.
name|setId
argument_list|(
name|t
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|task
operator|.
name|setName
argument_list|(
name|t
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|task
operator|.
name|setEmail
argument_list|(
name|t
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|nameFormat
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|NameFormat
argument_list|)
decl_stmt|;
name|ContactInterface
name|owner
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|owner
operator|.
name|setAcademicTitle
argument_list|(
name|t
operator|.
name|getOwner
argument_list|()
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|owner
operator|.
name|setEmail
argument_list|(
name|t
operator|.
name|getOwner
argument_list|()
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
expr_stmt|;
name|owner
operator|.
name|setExternalId
argument_list|(
name|t
operator|.
name|getOwner
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|owner
operator|.
name|setFirstName
argument_list|(
name|t
operator|.
name|getOwner
argument_list|()
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|owner
operator|.
name|setMiddleName
argument_list|(
name|t
operator|.
name|getOwner
argument_list|()
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|owner
operator|.
name|setLastName
argument_list|(
name|t
operator|.
name|getOwner
argument_list|()
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|owner
operator|.
name|setFormattedName
argument_list|(
name|t
operator|.
name|getOwner
argument_list|()
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
name|task
operator|.
name|setOwner
argument_list|(
name|owner
argument_list|)
expr_stmt|;
name|task
operator|.
name|setScript
argument_list|(
name|LoadAllScriptsBackend
operator|.
name|load
argument_list|(
name|t
operator|.
name|getScript
argument_list|()
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
name|task
operator|.
name|setCanEdit
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|TaskEdit
argument_list|)
operator|&&
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|canEdit
argument_list|()
argument_list|)
expr_stmt|;
name|task
operator|.
name|setCanView
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|TaskDetail
argument_list|)
operator|&&
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|canExecute
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|TaskParameter
name|p
range|:
name|t
operator|.
name|getParameters
argument_list|()
control|)
block|{
name|task
operator|.
name|setParameter
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|,
name|p
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|TaskExecution
name|e
range|:
name|t
operator|.
name|getSchedule
argument_list|()
control|)
block|{
name|TaskExecutionInterface
name|exec
init|=
operator|new
name|TaskExecutionInterface
argument_list|()
decl_stmt|;
name|exec
operator|.
name|setId
argument_list|(
name|e
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setCreated
argument_list|(
name|e
operator|.
name|getCreatedDate
argument_list|()
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setStatus
argument_list|(
name|ExecutionStatus
operator|.
name|values
argument_list|()
index|[
name|e
operator|.
name|getExecutionStatus
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setExecutionDate
argument_list|(
name|t
operator|.
name|getSession
argument_list|()
operator|.
name|getDate
argument_list|(
name|e
operator|.
name|getExecutionDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setSlot
argument_list|(
name|e
operator|.
name|getExecutionPeriod
argument_list|()
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setDayOfWeek
argument_list|(
name|Constants
operator|.
name|getDayOfWeek
argument_list|(
name|exec
operator|.
name|getExecutionDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setDayOfYear
argument_list|(
name|CalendarUtils
operator|.
name|date2dayOfYear
argument_list|(
name|t
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionStartYear
argument_list|()
argument_list|,
name|exec
operator|.
name|getExecutionDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setFinished
argument_list|(
name|e
operator|.
name|getFinishedDate
argument_list|()
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setQueued
argument_list|(
name|e
operator|.
name|getQueuedDate
argument_list|()
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setStarted
argument_list|(
name|e
operator|.
name|getStartedDate
argument_list|()
argument_list|)
expr_stmt|;
name|exec
operator|.
name|setStatusMessage
argument_list|(
name|e
operator|.
name|getStatusMessage
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getStartedDate
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|task
operator|.
name|getLastExecuted
argument_list|()
operator|==
literal|null
operator|||
name|task
operator|.
name|getLastExecuted
argument_list|()
operator|.
name|before
argument_list|(
name|e
operator|.
name|getStartedDate
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|task
operator|.
name|setLastExecuted
argument_list|(
name|e
operator|.
name|getStartedDate
argument_list|()
argument_list|)
expr_stmt|;
name|task
operator|.
name|setLastStatus
argument_list|(
name|ExecutionStatus
operator|.
name|values
argument_list|()
index|[
name|e
operator|.
name|getExecutionStatus
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
name|exec
operator|.
name|setOutput
argument_list|(
name|e
operator|.
name|getOutputName
argument_list|()
argument_list|)
expr_stmt|;
name|task
operator|.
name|addExecution
argument_list|(
name|exec
argument_list|)
expr_stmt|;
block|}
return|return
name|task
return|;
block|}
block|}
end_class

end_unit

