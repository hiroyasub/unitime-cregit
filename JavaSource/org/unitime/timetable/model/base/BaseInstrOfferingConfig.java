begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|BaseInstrOfferingConfig
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
name|iLimit
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
name|InstructionalOffering
name|iInstructionalOffering
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|SchedulingSubpart
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
name|PROP_CONFIG_LIMIT
init|=
literal|"limit"
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
name|BaseInstrOfferingConfig
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseInstrOfferingConfig
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
name|SchedulingSubpart
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
name|SchedulingSubpart
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
name|SchedulingSubpart
name|schedulingSubpart
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
name|SchedulingSubpart
argument_list|>
argument_list|()
expr_stmt|;
name|iSchedulingSubparts
operator|.
name|add
argument_list|(
name|schedulingSubpart
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
name|InstrOfferingConfig
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
name|InstrOfferingConfig
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
name|InstrOfferingConfig
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
literal|"InstrOfferingConfig["
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
literal|"InstrOfferingConfig["
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
literal|"\n	Name: "
operator|+
name|getName
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

