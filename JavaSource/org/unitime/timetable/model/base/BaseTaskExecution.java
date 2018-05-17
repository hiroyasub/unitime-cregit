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
name|model
operator|.
name|base
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseTaskExecution
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|Integer
name|iExecutionDate
decl_stmt|;
specifier|private
name|Integer
name|iExecutionPeriod
decl_stmt|;
specifier|private
name|Integer
name|iExecutionStatus
decl_stmt|;
specifier|private
name|Date
name|iCreatedDate
decl_stmt|;
specifier|private
name|Date
name|iScheduledDate
decl_stmt|;
specifier|private
name|Date
name|iQueuedDate
decl_stmt|;
specifier|private
name|Date
name|iStartedDate
decl_stmt|;
specifier|private
name|Date
name|iFinishedDate
decl_stmt|;
specifier|private
name|String
name|iLogFile
decl_stmt|;
specifier|private
name|byte
index|[]
name|iOutputFile
decl_stmt|;
specifier|private
name|String
name|iOutputName
decl_stmt|;
specifier|private
name|String
name|iOutputContentType
decl_stmt|;
specifier|private
name|String
name|iStatusMessage
decl_stmt|;
specifier|private
name|PeriodicTask
name|iTask
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXEC_DATE
init|=
literal|"executionDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXEC_PERIOD
init|=
literal|"executionPeriod"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STATUS
init|=
literal|"executionStatus"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CREATED_DATE
init|=
literal|"createdDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SCHEDULED_DATE
init|=
literal|"scheduledDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_QUEUED_DATE
init|=
literal|"queuedDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STARTED_DATE
init|=
literal|"startedDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FINISHED_DATE
init|=
literal|"finishedDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LOG_FILE
init|=
literal|"logFile"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_OUTPUT_FILE
init|=
literal|"outputFile"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_OUTPUT_NAME
init|=
literal|"outputName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_OUTPUT_CONTENT
init|=
literal|"outputContentType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STATUS_MESSAGE
init|=
literal|"statusMessage"
decl_stmt|;
specifier|public
name|BaseTaskExecution
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseTaskExecution
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getExecutionDate
parameter_list|()
block|{
return|return
name|iExecutionDate
return|;
block|}
specifier|public
name|void
name|setExecutionDate
parameter_list|(
name|Integer
name|executionDate
parameter_list|)
block|{
name|iExecutionDate
operator|=
name|executionDate
expr_stmt|;
block|}
specifier|public
name|Integer
name|getExecutionPeriod
parameter_list|()
block|{
return|return
name|iExecutionPeriod
return|;
block|}
specifier|public
name|void
name|setExecutionPeriod
parameter_list|(
name|Integer
name|executionPeriod
parameter_list|)
block|{
name|iExecutionPeriod
operator|=
name|executionPeriod
expr_stmt|;
block|}
specifier|public
name|Integer
name|getExecutionStatus
parameter_list|()
block|{
return|return
name|iExecutionStatus
return|;
block|}
specifier|public
name|void
name|setExecutionStatus
parameter_list|(
name|Integer
name|executionStatus
parameter_list|)
block|{
name|iExecutionStatus
operator|=
name|executionStatus
expr_stmt|;
block|}
specifier|public
name|Date
name|getCreatedDate
parameter_list|()
block|{
return|return
name|iCreatedDate
return|;
block|}
specifier|public
name|void
name|setCreatedDate
parameter_list|(
name|Date
name|createdDate
parameter_list|)
block|{
name|iCreatedDate
operator|=
name|createdDate
expr_stmt|;
block|}
specifier|public
name|Date
name|getScheduledDate
parameter_list|()
block|{
return|return
name|iScheduledDate
return|;
block|}
specifier|public
name|void
name|setScheduledDate
parameter_list|(
name|Date
name|scheduledDate
parameter_list|)
block|{
name|iScheduledDate
operator|=
name|scheduledDate
expr_stmt|;
block|}
specifier|public
name|Date
name|getQueuedDate
parameter_list|()
block|{
return|return
name|iQueuedDate
return|;
block|}
specifier|public
name|void
name|setQueuedDate
parameter_list|(
name|Date
name|queuedDate
parameter_list|)
block|{
name|iQueuedDate
operator|=
name|queuedDate
expr_stmt|;
block|}
specifier|public
name|Date
name|getStartedDate
parameter_list|()
block|{
return|return
name|iStartedDate
return|;
block|}
specifier|public
name|void
name|setStartedDate
parameter_list|(
name|Date
name|startedDate
parameter_list|)
block|{
name|iStartedDate
operator|=
name|startedDate
expr_stmt|;
block|}
specifier|public
name|Date
name|getFinishedDate
parameter_list|()
block|{
return|return
name|iFinishedDate
return|;
block|}
specifier|public
name|void
name|setFinishedDate
parameter_list|(
name|Date
name|finishedDate
parameter_list|)
block|{
name|iFinishedDate
operator|=
name|finishedDate
expr_stmt|;
block|}
specifier|public
name|String
name|getLogFile
parameter_list|()
block|{
return|return
name|iLogFile
return|;
block|}
specifier|public
name|void
name|setLogFile
parameter_list|(
name|String
name|logFile
parameter_list|)
block|{
name|iLogFile
operator|=
name|logFile
expr_stmt|;
block|}
specifier|public
name|byte
index|[]
name|getOutputFile
parameter_list|()
block|{
return|return
name|iOutputFile
return|;
block|}
specifier|public
name|void
name|setOutputFile
parameter_list|(
name|byte
index|[]
name|outputFile
parameter_list|)
block|{
name|iOutputFile
operator|=
name|outputFile
expr_stmt|;
block|}
specifier|public
name|String
name|getOutputName
parameter_list|()
block|{
return|return
name|iOutputName
return|;
block|}
specifier|public
name|void
name|setOutputName
parameter_list|(
name|String
name|outputName
parameter_list|)
block|{
name|iOutputName
operator|=
name|outputName
expr_stmt|;
block|}
specifier|public
name|String
name|getOutputContentType
parameter_list|()
block|{
return|return
name|iOutputContentType
return|;
block|}
specifier|public
name|void
name|setOutputContentType
parameter_list|(
name|String
name|outputContentType
parameter_list|)
block|{
name|iOutputContentType
operator|=
name|outputContentType
expr_stmt|;
block|}
specifier|public
name|String
name|getStatusMessage
parameter_list|()
block|{
return|return
name|iStatusMessage
return|;
block|}
specifier|public
name|void
name|setStatusMessage
parameter_list|(
name|String
name|statusMessage
parameter_list|)
block|{
name|iStatusMessage
operator|=
name|statusMessage
expr_stmt|;
block|}
specifier|public
name|PeriodicTask
name|getTask
parameter_list|()
block|{
return|return
name|iTask
return|;
block|}
specifier|public
name|void
name|setTask
parameter_list|(
name|PeriodicTask
name|task
parameter_list|)
block|{
name|iTask
operator|=
name|task
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|TaskExecution
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|TaskExecution
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|TaskExecution
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"TaskExecution["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"TaskExecution["
operator|+
literal|"\n	CreatedDate: "
operator|+
name|getCreatedDate
argument_list|()
operator|+
literal|"\n	ExecutionDate: "
operator|+
name|getExecutionDate
argument_list|()
operator|+
literal|"\n	ExecutionPeriod: "
operator|+
name|getExecutionPeriod
argument_list|()
operator|+
literal|"\n	ExecutionStatus: "
operator|+
name|getExecutionStatus
argument_list|()
operator|+
literal|"\n	FinishedDate: "
operator|+
name|getFinishedDate
argument_list|()
operator|+
literal|"\n	LogFile: "
operator|+
name|getLogFile
argument_list|()
operator|+
literal|"\n	OutputContentType: "
operator|+
name|getOutputContentType
argument_list|()
operator|+
literal|"\n	OutputFile: "
operator|+
name|getOutputFile
argument_list|()
operator|+
literal|"\n	OutputName: "
operator|+
name|getOutputName
argument_list|()
operator|+
literal|"\n	QueuedDate: "
operator|+
name|getQueuedDate
argument_list|()
operator|+
literal|"\n	ScheduledDate: "
operator|+
name|getScheduledDate
argument_list|()
operator|+
literal|"\n	StartedDate: "
operator|+
name|getStartedDate
argument_list|()
operator|+
literal|"\n	StatusMessage: "
operator|+
name|getStatusMessage
argument_list|()
operator|+
literal|"\n	Task: "
operator|+
name|getTask
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

