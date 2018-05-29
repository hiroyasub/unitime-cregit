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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|Locale
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
name|fileupload
operator|.
name|FileItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|GwtRpcException
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
name|server
operator|.
name|UploadServlet
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
name|SaveTaskDetailsRpcRequest
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
name|ScriptParameter
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
name|TimetableManager
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
name|model
operator|.
name|dao
operator|.
name|ScriptDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|SaveTaskDetailsRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SaveTaskBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SaveTaskDetailsRpcRequest
argument_list|,
name|TaskInterface
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|TaskInterface
name|execute
parameter_list|(
name|SaveTaskDetailsRpcRequest
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
name|TaskEdit
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|PeriodicTaskDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
name|TaskInterface
name|task
init|=
name|request
operator|.
name|getTask
argument_list|()
decl_stmt|;
name|PeriodicTask
name|t
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|task
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
name|t
operator|=
name|PeriodicTaskDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|task
operator|.
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
if|if
condition|(
name|t
operator|==
literal|null
condition|)
block|{
name|t
operator|=
operator|new
name|PeriodicTask
argument_list|()
expr_stmt|;
name|t
operator|.
name|setScript
argument_list|(
name|ScriptDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|task
operator|.
name|getScript
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|t
operator|.
name|setOwner
argument_list|(
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|t
operator|.
name|setParameters
argument_list|(
operator|new
name|HashSet
argument_list|<
name|TaskParameter
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setSchedule
argument_list|(
operator|new
name|HashSet
argument_list|<
name|TaskExecution
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Date
name|ts
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|t
operator|.
name|setEmail
argument_list|(
name|task
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setName
argument_list|(
name|task
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|TaskParameter
argument_list|>
name|parameters
init|=
operator|new
name|ArrayList
argument_list|<
name|TaskParameter
argument_list|>
argument_list|(
name|t
operator|.
name|getParameters
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|ScriptParameter
name|p
range|:
name|t
operator|.
name|getScript
argument_list|()
operator|.
name|getParameters
argument_list|()
control|)
block|{
name|TaskParameter
name|tp
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|TaskParameter
argument_list|>
name|i
init|=
name|parameters
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
name|TaskParameter
name|x
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|tp
operator|=
name|x
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
name|String
name|value
init|=
name|task
operator|.
name|getParameter
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"file"
operator|.
name|equals
argument_list|(
name|p
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|FileItem
name|file
init|=
operator|(
name|FileItem
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|UploadServlet
operator|.
name|SESSION_LAST_FILE
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|!=
literal|null
condition|)
block|{
name|t
operator|.
name|setInputFile
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|value
operator|=
name|file
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|tp
operator|!=
literal|null
operator|&&
name|t
operator|.
name|getInputFile
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|value
operator|=
name|tp
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|t
operator|.
name|setInputFile
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|tp
operator|==
literal|null
condition|)
block|{
name|tp
operator|=
operator|new
name|TaskParameter
argument_list|()
expr_stmt|;
name|tp
operator|.
name|setTask
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setName
argument_list|(
name|p
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|getParameters
argument_list|()
operator|.
name|add
argument_list|(
name|tp
argument_list|)
expr_stmt|;
block|}
name|tp
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|tp
operator|!=
literal|null
condition|)
block|{
name|tp
operator|.
name|setTask
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|tp
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|TaskParameter
name|tp
range|:
name|parameters
control|)
block|{
name|t
operator|.
name|getParameters
argument_list|()
operator|.
name|remove
argument_list|(
name|tp
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setTask
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|tp
argument_list|)
expr_stmt|;
block|}
name|int
name|base
init|=
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
name|t
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|TaskExecution
argument_list|>
name|executions
init|=
operator|new
name|ArrayList
argument_list|<
name|TaskExecution
argument_list|>
argument_list|(
name|t
operator|.
name|getSchedule
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|TaskExecutionInterface
name|exec
range|:
name|task
operator|.
name|getExecutions
argument_list|()
control|)
block|{
name|TaskExecution
name|execution
init|=
literal|null
decl_stmt|;
name|int
name|index
init|=
name|exec
operator|.
name|getDayOfYear
argument_list|()
operator|-
name|base
decl_stmt|;
name|exec
operator|.
name|setExecutionDate
argument_list|(
name|getScheduleDate
argument_list|(
name|t
operator|.
name|getSession
argument_list|()
argument_list|,
name|index
argument_list|,
name|exec
operator|.
name|getSlot
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//if (exec.getDayOfYear() == null) exec.setDayOfYear(CalendarUtils.date2dayOfYear(t.getSession().getSessionStartYear(), exec.getExecutionDate()));
comment|// int index = exec.getDayOfYear() - base;
for|for
control|(
name|Iterator
argument_list|<
name|TaskExecution
argument_list|>
name|i
init|=
name|executions
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
name|TaskExecution
name|e
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|e
operator|.
name|getExecutionPeriod
argument_list|()
operator|.
name|equals
argument_list|(
name|exec
operator|.
name|getSlot
argument_list|()
argument_list|)
operator|&&
name|e
operator|.
name|getExecutionDate
argument_list|()
operator|.
name|equals
argument_list|(
name|index
argument_list|)
condition|)
block|{
name|execution
operator|=
name|e
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|execution
operator|==
literal|null
condition|)
block|{
name|execution
operator|=
operator|new
name|TaskExecution
argument_list|()
expr_stmt|;
name|execution
operator|.
name|setCreatedDate
argument_list|(
name|ts
argument_list|)
expr_stmt|;
name|execution
operator|.
name|setExecutionDate
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|execution
operator|.
name|setExecutionPeriod
argument_list|(
name|exec
operator|.
name|getSlot
argument_list|()
argument_list|)
expr_stmt|;
name|execution
operator|.
name|setScheduledDate
argument_list|(
name|getScheduleDate
argument_list|(
name|t
operator|.
name|getSession
argument_list|()
argument_list|,
name|index
argument_list|,
name|exec
operator|.
name|getSlot
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|execution
operator|.
name|setExecutionStatus
argument_list|(
name|ExecutionStatus
operator|.
name|CREATED
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|execution
operator|.
name|setTask
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|t
operator|.
name|getSchedule
argument_list|()
operator|.
name|add
argument_list|(
name|execution
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|TaskExecution
name|e
range|:
name|executions
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getExecutionStatus
argument_list|()
operator|==
name|ExecutionStatus
operator|.
name|CREATED
operator|.
name|ordinal
argument_list|()
condition|)
block|{
name|t
operator|.
name|getSchedule
argument_list|()
operator|.
name|remove
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|e
operator|.
name|setTask
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|t
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
name|t
operator|.
name|setUniqueId
argument_list|(
operator|(
name|Long
operator|)
name|hibSession
operator|.
name|save
argument_list|(
name|t
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|hibSession
operator|.
name|update
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
return|return
name|GetTasksBackend
operator|.
name|getTask
argument_list|(
name|t
argument_list|,
name|context
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
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
specifier|public
specifier|static
name|Date
name|getScheduleDate
parameter_list|(
name|Session
name|session
parameter_list|,
name|int
name|date
parameter_list|,
name|int
name|slot
parameter_list|)
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|date
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
name|slot
operator|*
literal|5
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getTime
argument_list|()
return|;
block|}
block|}
end_class

end_unit
