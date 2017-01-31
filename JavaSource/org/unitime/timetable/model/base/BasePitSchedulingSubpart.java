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
name|CourseCreditType
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
name|CourseCreditUnitType
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
name|ItypeDesc
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
name|PitClass
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
name|PitSchedulingSubpart
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
name|SchedulingSubpart
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePitSchedulingSubpart
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
name|iMinutesPerWk
decl_stmt|;
specifier|private
name|Boolean
name|iStudentAllowOverlap
decl_stmt|;
specifier|private
name|String
name|iSchedulingSubpartSuffixCache
decl_stmt|;
specifier|private
name|Float
name|iCredit
decl_stmt|;
specifier|private
name|String
name|iCourseName
decl_stmt|;
specifier|private
name|Long
name|iUniqueIdRolledForwardFrom
decl_stmt|;
specifier|private
name|CourseCreditType
name|iCreditType
decl_stmt|;
specifier|private
name|CourseCreditUnitType
name|iCreditUnitType
decl_stmt|;
specifier|private
name|ItypeDesc
name|iItype
decl_stmt|;
specifier|private
name|SchedulingSubpart
name|iSchedulingSubpart
decl_stmt|;
specifier|private
name|PitSchedulingSubpart
name|iPitParentSubpart
decl_stmt|;
specifier|private
name|PitInstrOfferingConfig
name|iPitInstrOfferingConfig
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|PitSchedulingSubpart
argument_list|>
name|iPitChildSubparts
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|PitClass
argument_list|>
name|iPitClasses
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
name|PROP_MIN_PER_WK
init|=
literal|"minutesPerWk"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STUDENT_ALLOW_OVERLAP
init|=
literal|"studentAllowOverlap"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SUBPART_SUFFIX
init|=
literal|"schedulingSubpartSuffixCache"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CREDIT
init|=
literal|"credit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UID_ROLLED_FWD_FROM
init|=
literal|"uniqueIdRolledForwardFrom"
decl_stmt|;
specifier|public
name|BasePitSchedulingSubpart
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BasePitSchedulingSubpart
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
name|getMinutesPerWk
parameter_list|()
block|{
return|return
name|iMinutesPerWk
return|;
block|}
specifier|public
name|void
name|setMinutesPerWk
parameter_list|(
name|Integer
name|minutesPerWk
parameter_list|)
block|{
name|iMinutesPerWk
operator|=
name|minutesPerWk
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isStudentAllowOverlap
parameter_list|()
block|{
return|return
name|iStudentAllowOverlap
return|;
block|}
specifier|public
name|Boolean
name|getStudentAllowOverlap
parameter_list|()
block|{
return|return
name|iStudentAllowOverlap
return|;
block|}
specifier|public
name|void
name|setStudentAllowOverlap
parameter_list|(
name|Boolean
name|studentAllowOverlap
parameter_list|)
block|{
name|iStudentAllowOverlap
operator|=
name|studentAllowOverlap
expr_stmt|;
block|}
specifier|public
name|String
name|getSchedulingSubpartSuffixCache
parameter_list|()
block|{
return|return
name|iSchedulingSubpartSuffixCache
return|;
block|}
specifier|public
name|void
name|setSchedulingSubpartSuffixCache
parameter_list|(
name|String
name|schedulingSubpartSuffixCache
parameter_list|)
block|{
name|iSchedulingSubpartSuffixCache
operator|=
name|schedulingSubpartSuffixCache
expr_stmt|;
block|}
specifier|public
name|Float
name|getCredit
parameter_list|()
block|{
return|return
name|iCredit
return|;
block|}
specifier|public
name|void
name|setCredit
parameter_list|(
name|Float
name|credit
parameter_list|)
block|{
name|iCredit
operator|=
name|credit
expr_stmt|;
block|}
specifier|public
name|String
name|getCourseName
parameter_list|()
block|{
return|return
name|iCourseName
return|;
block|}
specifier|public
name|void
name|setCourseName
parameter_list|(
name|String
name|courseName
parameter_list|)
block|{
name|iCourseName
operator|=
name|courseName
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
name|CourseCreditType
name|getCreditType
parameter_list|()
block|{
return|return
name|iCreditType
return|;
block|}
specifier|public
name|void
name|setCreditType
parameter_list|(
name|CourseCreditType
name|creditType
parameter_list|)
block|{
name|iCreditType
operator|=
name|creditType
expr_stmt|;
block|}
specifier|public
name|CourseCreditUnitType
name|getCreditUnitType
parameter_list|()
block|{
return|return
name|iCreditUnitType
return|;
block|}
specifier|public
name|void
name|setCreditUnitType
parameter_list|(
name|CourseCreditUnitType
name|creditUnitType
parameter_list|)
block|{
name|iCreditUnitType
operator|=
name|creditUnitType
expr_stmt|;
block|}
specifier|public
name|ItypeDesc
name|getItype
parameter_list|()
block|{
return|return
name|iItype
return|;
block|}
specifier|public
name|void
name|setItype
parameter_list|(
name|ItypeDesc
name|itype
parameter_list|)
block|{
name|iItype
operator|=
name|itype
expr_stmt|;
block|}
specifier|public
name|SchedulingSubpart
name|getSchedulingSubpart
parameter_list|()
block|{
return|return
name|iSchedulingSubpart
return|;
block|}
specifier|public
name|void
name|setSchedulingSubpart
parameter_list|(
name|SchedulingSubpart
name|schedulingSubpart
parameter_list|)
block|{
name|iSchedulingSubpart
operator|=
name|schedulingSubpart
expr_stmt|;
block|}
specifier|public
name|PitSchedulingSubpart
name|getPitParentSubpart
parameter_list|()
block|{
return|return
name|iPitParentSubpart
return|;
block|}
specifier|public
name|void
name|setPitParentSubpart
parameter_list|(
name|PitSchedulingSubpart
name|pitParentSubpart
parameter_list|)
block|{
name|iPitParentSubpart
operator|=
name|pitParentSubpart
expr_stmt|;
block|}
specifier|public
name|PitInstrOfferingConfig
name|getPitInstrOfferingConfig
parameter_list|()
block|{
return|return
name|iPitInstrOfferingConfig
return|;
block|}
specifier|public
name|void
name|setPitInstrOfferingConfig
parameter_list|(
name|PitInstrOfferingConfig
name|pitInstrOfferingConfig
parameter_list|)
block|{
name|iPitInstrOfferingConfig
operator|=
name|pitInstrOfferingConfig
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|PitSchedulingSubpart
argument_list|>
name|getPitChildSubparts
parameter_list|()
block|{
return|return
name|iPitChildSubparts
return|;
block|}
specifier|public
name|void
name|setPitChildSubparts
parameter_list|(
name|Set
argument_list|<
name|PitSchedulingSubpart
argument_list|>
name|pitChildSubparts
parameter_list|)
block|{
name|iPitChildSubparts
operator|=
name|pitChildSubparts
expr_stmt|;
block|}
specifier|public
name|void
name|addTopitChildSubparts
parameter_list|(
name|PitSchedulingSubpart
name|pitSchedulingSubpart
parameter_list|)
block|{
if|if
condition|(
name|iPitChildSubparts
operator|==
literal|null
condition|)
name|iPitChildSubparts
operator|=
operator|new
name|HashSet
argument_list|<
name|PitSchedulingSubpart
argument_list|>
argument_list|()
expr_stmt|;
name|iPitChildSubparts
operator|.
name|add
argument_list|(
name|pitSchedulingSubpart
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|PitClass
argument_list|>
name|getPitClasses
parameter_list|()
block|{
return|return
name|iPitClasses
return|;
block|}
specifier|public
name|void
name|setPitClasses
parameter_list|(
name|Set
argument_list|<
name|PitClass
argument_list|>
name|pitClasses
parameter_list|)
block|{
name|iPitClasses
operator|=
name|pitClasses
expr_stmt|;
block|}
specifier|public
name|void
name|addTopitClasses
parameter_list|(
name|PitClass
name|pitClass
parameter_list|)
block|{
if|if
condition|(
name|iPitClasses
operator|==
literal|null
condition|)
name|iPitClasses
operator|=
operator|new
name|HashSet
argument_list|<
name|PitClass
argument_list|>
argument_list|()
expr_stmt|;
name|iPitClasses
operator|.
name|add
argument_list|(
name|pitClass
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
name|PitSchedulingSubpart
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
name|PitSchedulingSubpart
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
name|PitSchedulingSubpart
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
literal|"PitSchedulingSubpart["
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
literal|"PitSchedulingSubpart["
operator|+
literal|"\n	Credit: "
operator|+
name|getCredit
argument_list|()
operator|+
literal|"\n	CreditType: "
operator|+
name|getCreditType
argument_list|()
operator|+
literal|"\n	CreditUnitType: "
operator|+
name|getCreditUnitType
argument_list|()
operator|+
literal|"\n	Itype: "
operator|+
name|getItype
argument_list|()
operator|+
literal|"\n	MinutesPerWk: "
operator|+
name|getMinutesPerWk
argument_list|()
operator|+
literal|"\n	PitInstrOfferingConfig: "
operator|+
name|getPitInstrOfferingConfig
argument_list|()
operator|+
literal|"\n	PitParentSubpart: "
operator|+
name|getPitParentSubpart
argument_list|()
operator|+
literal|"\n	SchedulingSubpart: "
operator|+
name|getSchedulingSubpart
argument_list|()
operator|+
literal|"\n	SchedulingSubpartSuffixCache: "
operator|+
name|getSchedulingSubpartSuffixCache
argument_list|()
operator|+
literal|"\n	StudentAllowOverlap: "
operator|+
name|getStudentAllowOverlap
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
