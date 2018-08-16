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
name|DistributionObject
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
name|DistributionPref
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
name|DistributionType
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
name|Preference
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseDistributionPref
extends|extends
name|Preference
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
name|Integer
name|iGrouping
decl_stmt|;
specifier|private
name|Long
name|iUniqueIdRolledForwardFrom
decl_stmt|;
specifier|private
name|DistributionType
name|iDistributionType
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|DistributionObject
argument_list|>
name|iDistributionObjects
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DIST_GROUPING
init|=
literal|"grouping"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UID_ROLLED_FWD_FROM
init|=
literal|"uniqueIdRolledForwardFrom"
decl_stmt|;
specifier|public
name|BaseDistributionPref
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseDistributionPref
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
name|Integer
name|getGrouping
parameter_list|()
block|{
return|return
name|iGrouping
return|;
block|}
specifier|public
name|void
name|setGrouping
parameter_list|(
name|Integer
name|grouping
parameter_list|)
block|{
name|iGrouping
operator|=
name|grouping
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
name|DistributionType
name|getDistributionType
parameter_list|()
block|{
return|return
name|iDistributionType
return|;
block|}
specifier|public
name|void
name|setDistributionType
parameter_list|(
name|DistributionType
name|distributionType
parameter_list|)
block|{
name|iDistributionType
operator|=
name|distributionType
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|DistributionObject
argument_list|>
name|getDistributionObjects
parameter_list|()
block|{
return|return
name|iDistributionObjects
return|;
block|}
specifier|public
name|void
name|setDistributionObjects
parameter_list|(
name|Set
argument_list|<
name|DistributionObject
argument_list|>
name|distributionObjects
parameter_list|)
block|{
name|iDistributionObjects
operator|=
name|distributionObjects
expr_stmt|;
block|}
specifier|public
name|void
name|addTodistributionObjects
parameter_list|(
name|DistributionObject
name|distributionObject
parameter_list|)
block|{
if|if
condition|(
name|iDistributionObjects
operator|==
literal|null
condition|)
name|iDistributionObjects
operator|=
operator|new
name|HashSet
argument_list|<
name|DistributionObject
argument_list|>
argument_list|()
expr_stmt|;
name|iDistributionObjects
operator|.
name|add
argument_list|(
name|distributionObject
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
name|DistributionPref
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
name|DistributionPref
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
name|DistributionPref
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
literal|"DistributionPref["
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
literal|"DistributionPref["
operator|+
literal|"\n	DistributionType: "
operator|+
name|getDistributionType
argument_list|()
operator|+
literal|"\n	Grouping: "
operator|+
name|getGrouping
argument_list|()
operator|+
literal|"\n	Owner: "
operator|+
name|getOwner
argument_list|()
operator|+
literal|"\n	PrefLevel: "
operator|+
name|getPrefLevel
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

