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
name|ClassInstructor
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
name|Class_
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
name|DepartmentalInstructor
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseClassInstructor
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
name|iPercentShare
decl_stmt|;
specifier|private
name|Boolean
name|iLead
decl_stmt|;
specifier|private
name|Boolean
name|iTentative
decl_stmt|;
specifier|private
name|Class_
name|iClassInstructing
decl_stmt|;
specifier|private
name|DepartmentalInstructor
name|iInstructor
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
name|PROP_PERCENT_SHARE
init|=
literal|"percentShare"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_IS_LEAD
init|=
literal|"lead"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TENTATIVE
init|=
literal|"tentative"
decl_stmt|;
specifier|public
name|BaseClassInstructor
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseClassInstructor
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
name|getPercentShare
parameter_list|()
block|{
return|return
name|iPercentShare
return|;
block|}
specifier|public
name|void
name|setPercentShare
parameter_list|(
name|Integer
name|percentShare
parameter_list|)
block|{
name|iPercentShare
operator|=
name|percentShare
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isLead
parameter_list|()
block|{
return|return
name|iLead
return|;
block|}
specifier|public
name|Boolean
name|getLead
parameter_list|()
block|{
return|return
name|iLead
return|;
block|}
specifier|public
name|void
name|setLead
parameter_list|(
name|Boolean
name|lead
parameter_list|)
block|{
name|iLead
operator|=
name|lead
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isTentative
parameter_list|()
block|{
return|return
name|iTentative
return|;
block|}
specifier|public
name|Boolean
name|getTentative
parameter_list|()
block|{
return|return
name|iTentative
return|;
block|}
specifier|public
name|void
name|setTentative
parameter_list|(
name|Boolean
name|tentative
parameter_list|)
block|{
name|iTentative
operator|=
name|tentative
expr_stmt|;
block|}
specifier|public
name|Class_
name|getClassInstructing
parameter_list|()
block|{
return|return
name|iClassInstructing
return|;
block|}
specifier|public
name|void
name|setClassInstructing
parameter_list|(
name|Class_
name|classInstructing
parameter_list|)
block|{
name|iClassInstructing
operator|=
name|classInstructing
expr_stmt|;
block|}
specifier|public
name|DepartmentalInstructor
name|getInstructor
parameter_list|()
block|{
return|return
name|iInstructor
return|;
block|}
specifier|public
name|void
name|setInstructor
parameter_list|(
name|DepartmentalInstructor
name|instructor
parameter_list|)
block|{
name|iInstructor
operator|=
name|instructor
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
name|ClassInstructor
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
name|ClassInstructor
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
name|ClassInstructor
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
literal|"ClassInstructor["
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
literal|"ClassInstructor["
operator|+
literal|"\n	ClassInstructing: "
operator|+
name|getClassInstructing
argument_list|()
operator|+
literal|"\n	Instructor: "
operator|+
name|getInstructor
argument_list|()
operator|+
literal|"\n	Lead: "
operator|+
name|getLead
argument_list|()
operator|+
literal|"\n	PercentShare: "
operator|+
name|getPercentShare
argument_list|()
operator|+
literal|"\n	Tentative: "
operator|+
name|getTentative
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

