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
package|;
end_package

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
name|base
operator|.
name|BaseCurriculumOverrideReservation
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
name|model
operator|.
name|XReservation
operator|.
name|Flags
import|;
end_import

begin_class
specifier|public
class|class
name|CurriculumOverrideReservation
extends|extends
name|BaseCurriculumOverrideReservation
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|CurriculumOverrideReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getFlagsNotNull
parameter_list|()
block|{
if|if
condition|(
name|getFlags
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|0
return|;
return|return
name|getFlags
argument_list|()
return|;
block|}
comment|/**      * True if holding this reservation allows a student to have attend overlapping class.       */
annotation|@
name|Override
specifier|public
name|boolean
name|isAllowOverlap
parameter_list|()
block|{
return|return
name|Flags
operator|.
name|AllowOverlap
operator|.
name|in
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setAllowOverlap
parameter_list|(
name|boolean
name|allowOverlap
parameter_list|)
block|{
name|setFlags
argument_list|(
name|Flags
operator|.
name|AllowOverlap
operator|.
name|set
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|,
name|allowOverlap
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * True if holding this reservation allows a student to have attend a class that is disabled for student scheduling.       */
specifier|public
name|boolean
name|isAllowDisabled
parameter_list|()
block|{
return|return
name|Flags
operator|.
name|AllowDiabled
operator|.
name|in
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setAllowDisabled
parameter_list|(
name|boolean
name|allowDisabled
parameter_list|)
block|{
name|setFlags
argument_list|(
name|Flags
operator|.
name|AllowDiabled
operator|.
name|set
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|,
name|allowDisabled
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * True if can go over the course / config / section limit. Only to be used in the online sectioning.        */
annotation|@
name|Override
specifier|public
name|boolean
name|isCanAssignOverLimit
parameter_list|()
block|{
return|return
name|Flags
operator|.
name|CanAssignOverLimit
operator|.
name|in
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setCanAssignOverLimit
parameter_list|(
name|boolean
name|canAssignOverLimit
parameter_list|)
block|{
name|setFlags
argument_list|(
name|Flags
operator|.
name|CanAssignOverLimit
operator|.
name|set
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|,
name|canAssignOverLimit
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * If true, student must use the reservation (if applicable)      */
annotation|@
name|Override
specifier|public
name|boolean
name|isMustBeUsed
parameter_list|()
block|{
return|return
name|Flags
operator|.
name|MustBeUsed
operator|.
name|in
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setMustBeUsed
parameter_list|(
name|boolean
name|mustBeUsed
parameter_list|)
block|{
name|setFlags
argument_list|(
name|Flags
operator|.
name|MustBeUsed
operator|.
name|set
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|,
name|mustBeUsed
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * If true, the reservation is override (it is always expired)      */
annotation|@
name|Override
specifier|public
name|boolean
name|isAlwaysExpired
parameter_list|()
block|{
return|return
name|Flags
operator|.
name|AlwaysExpired
operator|.
name|in
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setAlwaysExpired
parameter_list|(
name|boolean
name|override
parameter_list|)
block|{
name|setFlags
argument_list|(
name|Flags
operator|.
name|AlwaysExpired
operator|.
name|set
argument_list|(
name|getFlagsNotNull
argument_list|()
argument_list|,
name|override
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isExpired
parameter_list|()
block|{
return|return
operator|(
name|isAlwaysExpired
argument_list|()
condition|?
literal|true
else|:
name|super
operator|.
name|isExpired
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|getStartDate
parameter_list|()
block|{
return|return
operator|(
name|isAlwaysExpired
argument_list|()
condition|?
literal|null
else|:
name|super
operator|.
name|getStartDate
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|getExpirationDate
parameter_list|()
block|{
return|return
operator|(
name|isAlwaysExpired
argument_list|()
condition|?
literal|null
else|:
name|super
operator|.
name|getExpirationDate
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getPriority
parameter_list|()
block|{
return|return
operator|(
name|isAlwaysExpired
argument_list|()
condition|?
name|ApplicationProperty
operator|.
name|ReservationPriorityOverride
operator|.
name|intValue
argument_list|()
else|:
name|ApplicationProperty
operator|.
name|ReservationPriorityCurriculum
operator|.
name|intValue
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit
