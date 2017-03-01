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
name|InstructionalOffering
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
name|PitCourseOffering
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
name|PointInTimeData
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePitInstructionalOffering
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
name|iInstrOfferingPermId
decl_stmt|;
specifier|private
name|Integer
name|iDemand
decl_stmt|;
specifier|private
name|Integer
name|iLimit
decl_stmt|;
specifier|private
name|Long
name|iUniqueIdRolledForwardFrom
decl_stmt|;
specifier|private
name|String
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|Integer
name|iEnrollment
decl_stmt|;
specifier|private
name|PointInTimeData
name|iPointInTimeData
decl_stmt|;
specifier|private
name|InstructionalOffering
name|iInstructionalOffering
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|PitCourseOffering
argument_list|>
name|iPitCourseOfferings
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|PitInstrOfferingConfig
argument_list|>
name|iPitInstrOfferingConfigs
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
name|PROP_INSTR_OFFERING_PERM_ID
init|=
literal|"instrOfferingPermId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DEMAND
init|=
literal|"demand"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_OFFR_LIMIT
init|=
literal|"limit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UID_ROLLED_FWD_FROM
init|=
literal|"uniqueIdRolledForwardFrom"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
name|BasePitInstructionalOffering
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BasePitInstructionalOffering
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
name|getInstrOfferingPermId
parameter_list|()
block|{
return|return
name|iInstrOfferingPermId
return|;
block|}
specifier|public
name|void
name|setInstrOfferingPermId
parameter_list|(
name|Integer
name|instrOfferingPermId
parameter_list|)
block|{
name|iInstrOfferingPermId
operator|=
name|instrOfferingPermId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getDemand
parameter_list|()
block|{
return|return
name|iDemand
return|;
block|}
specifier|public
name|void
name|setDemand
parameter_list|(
name|Integer
name|demand
parameter_list|)
block|{
name|iDemand
operator|=
name|demand
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|Integer
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
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
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getEnrollment
parameter_list|()
block|{
return|return
name|iEnrollment
return|;
block|}
specifier|public
name|void
name|setEnrollment
parameter_list|(
name|Integer
name|enrollment
parameter_list|)
block|{
name|iEnrollment
operator|=
name|enrollment
expr_stmt|;
block|}
specifier|public
name|PointInTimeData
name|getPointInTimeData
parameter_list|()
block|{
return|return
name|iPointInTimeData
return|;
block|}
specifier|public
name|void
name|setPointInTimeData
parameter_list|(
name|PointInTimeData
name|pointInTimeData
parameter_list|)
block|{
name|iPointInTimeData
operator|=
name|pointInTimeData
expr_stmt|;
block|}
specifier|public
name|InstructionalOffering
name|getInstructionalOffering
parameter_list|()
block|{
return|return
name|iInstructionalOffering
return|;
block|}
specifier|public
name|void
name|setInstructionalOffering
parameter_list|(
name|InstructionalOffering
name|instructionalOffering
parameter_list|)
block|{
name|iInstructionalOffering
operator|=
name|instructionalOffering
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|PitCourseOffering
argument_list|>
name|getPitCourseOfferings
parameter_list|()
block|{
return|return
name|iPitCourseOfferings
return|;
block|}
specifier|public
name|void
name|setPitCourseOfferings
parameter_list|(
name|Set
argument_list|<
name|PitCourseOffering
argument_list|>
name|pitCourseOfferings
parameter_list|)
block|{
name|iPitCourseOfferings
operator|=
name|pitCourseOfferings
expr_stmt|;
block|}
specifier|public
name|void
name|addTopitCourseOfferings
parameter_list|(
name|PitCourseOffering
name|pitCourseOffering
parameter_list|)
block|{
if|if
condition|(
name|iPitCourseOfferings
operator|==
literal|null
condition|)
name|iPitCourseOfferings
operator|=
operator|new
name|HashSet
argument_list|<
name|PitCourseOffering
argument_list|>
argument_list|()
expr_stmt|;
name|iPitCourseOfferings
operator|.
name|add
argument_list|(
name|pitCourseOffering
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|PitInstrOfferingConfig
argument_list|>
name|getPitInstrOfferingConfigs
parameter_list|()
block|{
return|return
name|iPitInstrOfferingConfigs
return|;
block|}
specifier|public
name|void
name|setPitInstrOfferingConfigs
parameter_list|(
name|Set
argument_list|<
name|PitInstrOfferingConfig
argument_list|>
name|pitInstrOfferingConfigs
parameter_list|)
block|{
name|iPitInstrOfferingConfigs
operator|=
name|pitInstrOfferingConfigs
expr_stmt|;
block|}
specifier|public
name|void
name|addTopitInstrOfferingConfigs
parameter_list|(
name|PitInstrOfferingConfig
name|pitInstrOfferingConfig
parameter_list|)
block|{
if|if
condition|(
name|iPitInstrOfferingConfigs
operator|==
literal|null
condition|)
name|iPitInstrOfferingConfigs
operator|=
operator|new
name|HashSet
argument_list|<
name|PitInstrOfferingConfig
argument_list|>
argument_list|()
expr_stmt|;
name|iPitInstrOfferingConfigs
operator|.
name|add
argument_list|(
name|pitInstrOfferingConfig
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
name|PitInstructionalOffering
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
name|PitInstructionalOffering
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
name|PitInstructionalOffering
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
literal|"PitInstructionalOffering["
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
literal|"PitInstructionalOffering["
operator|+
literal|"\n	Demand: "
operator|+
name|getDemand
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
argument_list|()
operator|+
literal|"\n	InstrOfferingPermId: "
operator|+
name|getInstrOfferingPermId
argument_list|()
operator|+
literal|"\n	InstructionalOffering: "
operator|+
name|getInstructionalOffering
argument_list|()
operator|+
literal|"\n	Limit: "
operator|+
name|getLimit
argument_list|()
operator|+
literal|"\n	PointInTimeData: "
operator|+
name|getPointInTimeData
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
literal|"]"
return|;
block|}
block|}
end_class

end_unit

