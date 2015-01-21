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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseCreditUnitConfig
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
name|VariableFixedCreditUnitConfig
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseVariableFixedCreditUnitConfig
extends|extends
name|CourseCreditUnitConfig
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
name|Float
name|iMinUnits
decl_stmt|;
specifier|private
name|Float
name|iMaxUnits
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MIN_UNITS
init|=
literal|"minUnits"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MAX_UNITS
init|=
literal|"maxUnits"
decl_stmt|;
specifier|public
name|BaseVariableFixedCreditUnitConfig
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseVariableFixedCreditUnitConfig
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
name|Float
name|getMinUnits
parameter_list|()
block|{
return|return
name|iMinUnits
return|;
block|}
specifier|public
name|void
name|setMinUnits
parameter_list|(
name|Float
name|minUnits
parameter_list|)
block|{
name|iMinUnits
operator|=
name|minUnits
expr_stmt|;
block|}
specifier|public
name|Float
name|getMaxUnits
parameter_list|()
block|{
return|return
name|iMaxUnits
return|;
block|}
specifier|public
name|void
name|setMaxUnits
parameter_list|(
name|Float
name|maxUnits
parameter_list|)
block|{
name|iMaxUnits
operator|=
name|maxUnits
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
name|VariableFixedCreditUnitConfig
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
name|VariableFixedCreditUnitConfig
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
name|VariableFixedCreditUnitConfig
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
literal|"VariableFixedCreditUnitConfig["
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
literal|"VariableFixedCreditUnitConfig["
operator|+
literal|"\n	CourseCreditFormat: "
operator|+
name|getCourseCreditFormat
argument_list|()
operator|+
literal|"\n	CourseOwner: "
operator|+
name|getCourseOwner
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
literal|"\n	DefinesCreditAtCourseLevel: "
operator|+
name|getDefinesCreditAtCourseLevel
argument_list|()
operator|+
literal|"\n	MaxUnits: "
operator|+
name|getMaxUnits
argument_list|()
operator|+
literal|"\n	MinUnits: "
operator|+
name|getMinUnits
argument_list|()
operator|+
literal|"\n	SubpartOwner: "
operator|+
name|getSubpartOwner
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

