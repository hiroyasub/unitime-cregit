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
name|ClassDurationType
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
name|InstrOfferingConfig
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
name|InstructionalMethod
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
name|PitInstrOfferingConfig
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
name|PitInstructionalOffering
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
name|PitSchedulingSubpart
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePitInstrOfferingConfig
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
name|Boolean
name|iUnlimitedEnrollment
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|Long
name|iUniqueIdRolledForwardFrom
decl_stmt|;
specifier|private
name|InstrOfferingConfig
name|iInstrOfferingConfig
decl_stmt|;
specifier|private
name|PitInstructionalOffering
name|iPitInstructionalOffering
decl_stmt|;
specifier|private
name|ClassDurationType
name|iClassDurationType
decl_stmt|;
specifier|private
name|InstructionalMethod
name|iInstructionalMethod
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|PitSchedulingSubpart
argument_list|>
name|iSchedulingSubparts
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
name|PROP_UNLIMITED_ENROLLMENT
init|=
literal|"unlimitedEnrollment"
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
name|PROP_UID_ROLLED_FWD_FROM
init|=
literal|"uniqueIdRolledForwardFrom"
decl_stmt|;
specifier|public
name|BasePitInstrOfferingConfig
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BasePitInstrOfferingConfig
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
name|Boolean
name|isUnlimitedEnrollment
parameter_list|()
block|{
return|return
name|iUnlimitedEnrollment
return|;
block|}
specifier|public
name|Boolean
name|getUnlimitedEnrollment
parameter_list|()
block|{
return|return
name|iUnlimitedEnrollment
return|;
block|}
specifier|public
name|void
name|setUnlimitedEnrollment
parameter_list|(
name|Boolean
name|unlimitedEnrollment
parameter_list|)
block|{
name|iUnlimitedEnrollment
operator|=
name|unlimitedEnrollment
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
name|Long
name|getUniqueIdRolledForwardFrom
parameter_list|()
block|{
return|return
name|iUniqueIdRolledForwardFrom
return|;
block|}
specifier|public
name|void
name|setUniqueIdRolledForwardFrom
parameter_list|(
name|Long
name|uniqueIdRolledForwardFrom
parameter_list|)
block|{
name|iUniqueIdRolledForwardFrom
operator|=
name|uniqueIdRolledForwardFrom
expr_stmt|;
block|}
specifier|public
name|InstrOfferingConfig
name|getInstrOfferingConfig
parameter_list|()
block|{
return|return
name|iInstrOfferingConfig
return|;
block|}
specifier|public
name|void
name|setInstrOfferingConfig
parameter_list|(
name|InstrOfferingConfig
name|instrOfferingConfig
parameter_list|)
block|{
name|iInstrOfferingConfig
operator|=
name|instrOfferingConfig
expr_stmt|;
block|}
specifier|public
name|PitInstructionalOffering
name|getPitInstructionalOffering
parameter_list|()
block|{
return|return
name|iPitInstructionalOffering
return|;
block|}
specifier|public
name|void
name|setPitInstructionalOffering
parameter_list|(
name|PitInstructionalOffering
name|pitInstructionalOffering
parameter_list|)
block|{
name|iPitInstructionalOffering
operator|=
name|pitInstructionalOffering
expr_stmt|;
block|}
specifier|public
name|ClassDurationType
name|getClassDurationType
parameter_list|()
block|{
return|return
name|iClassDurationType
return|;
block|}
specifier|public
name|void
name|setClassDurationType
parameter_list|(
name|ClassDurationType
name|classDurationType
parameter_list|)
block|{
name|iClassDurationType
operator|=
name|classDurationType
expr_stmt|;
block|}
specifier|public
name|InstructionalMethod
name|getInstructionalMethod
parameter_list|()
block|{
return|return
name|iInstructionalMethod
return|;
block|}
specifier|public
name|void
name|setInstructionalMethod
parameter_list|(
name|InstructionalMethod
name|instructionalMethod
parameter_list|)
block|{
name|iInstructionalMethod
operator|=
name|instructionalMethod
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|PitSchedulingSubpart
argument_list|>
name|getSchedulingSubparts
parameter_list|()
block|{
return|return
name|iSchedulingSubparts
return|;
block|}
specifier|public
name|void
name|setSchedulingSubparts
parameter_list|(
name|Set
argument_list|<
name|PitSchedulingSubpart
argument_list|>
name|schedulingSubparts
parameter_list|)
block|{
name|iSchedulingSubparts
operator|=
name|schedulingSubparts
expr_stmt|;
block|}
specifier|public
name|void
name|addToschedulingSubparts
parameter_list|(
name|PitSchedulingSubpart
name|pitSchedulingSubpart
parameter_list|)
block|{
if|if
condition|(
name|iSchedulingSubparts
operator|==
literal|null
condition|)
name|iSchedulingSubparts
operator|=
operator|new
name|HashSet
argument_list|<
name|PitSchedulingSubpart
argument_list|>
argument_list|()
expr_stmt|;
name|iSchedulingSubparts
operator|.
name|add
argument_list|(
name|pitSchedulingSubpart
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
name|PitInstrOfferingConfig
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
name|PitInstrOfferingConfig
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
name|PitInstrOfferingConfig
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
literal|"PitInstrOfferingConfig["
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
literal|"PitInstrOfferingConfig["
operator|+
literal|"\n	ClassDurationType: "
operator|+
name|getClassDurationType
argument_list|()
operator|+
literal|"\n	InstrOfferingConfig: "
operator|+
name|getInstrOfferingConfig
argument_list|()
operator|+
literal|"\n	InstructionalMethod: "
operator|+
name|getInstructionalMethod
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	PitInstructionalOffering: "
operator|+
name|getPitInstructionalOffering
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"\n	UniqueIdRolledForwardFrom: "
operator|+
name|getUniqueIdRolledForwardFrom
argument_list|()
operator|+
literal|"\n	UnlimitedEnrollment: "
operator|+
name|getUnlimitedEnrollment
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

