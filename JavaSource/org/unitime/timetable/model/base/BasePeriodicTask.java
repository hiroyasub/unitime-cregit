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
name|HashSet
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
name|Script
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

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePeriodicTask
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
name|String
name|iName
decl_stmt|;
specifier|private
name|String
name|iEmail
decl_stmt|;
specifier|private
name|byte
index|[]
name|iInputFile
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|Script
name|iScript
decl_stmt|;
specifier|private
name|TimetableManager
name|iOwner
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|TaskParameter
argument_list|>
name|iParameters
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|TaskExecution
argument_list|>
name|iSchedule
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
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EMAIL
init|=
literal|"email"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_INPUT_FILE
init|=
literal|"inputFile"
decl_stmt|;
specifier|public
name|BasePeriodicTask
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BasePeriodicTask
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
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|byte
index|[]
name|getInputFile
parameter_list|()
block|{
return|return
name|iInputFile
return|;
block|}
specifier|public
name|void
name|setInputFile
parameter_list|(
name|byte
index|[]
name|inputFile
parameter_list|)
block|{
name|iInputFile
operator|=
name|inputFile
expr_stmt|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|Script
name|getScript
parameter_list|()
block|{
return|return
name|iScript
return|;
block|}
specifier|public
name|void
name|setScript
parameter_list|(
name|Script
name|script
parameter_list|)
block|{
name|iScript
operator|=
name|script
expr_stmt|;
block|}
specifier|public
name|TimetableManager
name|getOwner
parameter_list|()
block|{
return|return
name|iOwner
return|;
block|}
specifier|public
name|void
name|setOwner
parameter_list|(
name|TimetableManager
name|owner
parameter_list|)
block|{
name|iOwner
operator|=
name|owner
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|TaskParameter
argument_list|>
name|getParameters
parameter_list|()
block|{
return|return
name|iParameters
return|;
block|}
specifier|public
name|void
name|setParameters
parameter_list|(
name|Set
argument_list|<
name|TaskParameter
argument_list|>
name|parameters
parameter_list|)
block|{
name|iParameters
operator|=
name|parameters
expr_stmt|;
block|}
specifier|public
name|void
name|addToparameters
parameter_list|(
name|TaskParameter
name|taskParameter
parameter_list|)
block|{
if|if
condition|(
name|iParameters
operator|==
literal|null
condition|)
name|iParameters
operator|=
operator|new
name|HashSet
argument_list|<
name|TaskParameter
argument_list|>
argument_list|()
expr_stmt|;
name|iParameters
operator|.
name|add
argument_list|(
name|taskParameter
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|TaskExecution
argument_list|>
name|getSchedule
parameter_list|()
block|{
return|return
name|iSchedule
return|;
block|}
specifier|public
name|void
name|setSchedule
parameter_list|(
name|Set
argument_list|<
name|TaskExecution
argument_list|>
name|schedule
parameter_list|)
block|{
name|iSchedule
operator|=
name|schedule
expr_stmt|;
block|}
specifier|public
name|void
name|addToschedule
parameter_list|(
name|TaskExecution
name|taskExecution
parameter_list|)
block|{
if|if
condition|(
name|iSchedule
operator|==
literal|null
condition|)
name|iSchedule
operator|=
operator|new
name|HashSet
argument_list|<
name|TaskExecution
argument_list|>
argument_list|()
expr_stmt|;
name|iSchedule
operator|.
name|add
argument_list|(
name|taskExecution
argument_list|)
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
name|PeriodicTask
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
name|PeriodicTask
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
name|PeriodicTask
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
literal|"PeriodicTask["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|getName
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
literal|"PeriodicTask["
operator|+
literal|"\n	Email: "
operator|+
name|getEmail
argument_list|()
operator|+
literal|"\n	InputFile: "
operator|+
name|getInputFile
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	Owner: "
operator|+
name|getOwner
argument_list|()
operator|+
literal|"\n	Script: "
operator|+
name|getScript
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
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

