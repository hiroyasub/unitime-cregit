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
name|RefTableEntry
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
name|TeachingResponsibility
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseTeachingResponsibility
extends|extends
name|RefTableEntry
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
name|Boolean
name|iCoordinator
decl_stmt|;
specifier|private
name|Boolean
name|iInstructor
decl_stmt|;
specifier|private
name|String
name|iAbbreviation
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COORDINATOR
init|=
literal|"coordinator"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_INSTRUCTOR
init|=
literal|"instructor"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ABBREVIATION
init|=
literal|"abbreviation"
decl_stmt|;
specifier|public
name|BaseTeachingResponsibility
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseTeachingResponsibility
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
name|Boolean
name|isCoordinator
parameter_list|()
block|{
return|return
name|iCoordinator
return|;
block|}
specifier|public
name|Boolean
name|getCoordinator
parameter_list|()
block|{
return|return
name|iCoordinator
return|;
block|}
specifier|public
name|void
name|setCoordinator
parameter_list|(
name|Boolean
name|coordinator
parameter_list|)
block|{
name|iCoordinator
operator|=
name|coordinator
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isInstructor
parameter_list|()
block|{
return|return
name|iInstructor
return|;
block|}
specifier|public
name|Boolean
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
name|Boolean
name|instructor
parameter_list|)
block|{
name|iInstructor
operator|=
name|instructor
expr_stmt|;
block|}
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|iAbbreviation
return|;
block|}
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|String
name|abbreviation
parameter_list|)
block|{
name|iAbbreviation
operator|=
name|abbreviation
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
name|TeachingResponsibility
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
name|TeachingResponsibility
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
name|TeachingResponsibility
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
literal|"TeachingResponsibility["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|getLabel
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
literal|"TeachingResponsibility["
operator|+
literal|"\n	Abbreviation: "
operator|+
name|getAbbreviation
argument_list|()
operator|+
literal|"\n	Coordinator: "
operator|+
name|getCoordinator
argument_list|()
operator|+
literal|"\n	Instructor: "
operator|+
name|getInstructor
argument_list|()
operator|+
literal|"\n	Label: "
operator|+
name|getLabel
argument_list|()
operator|+
literal|"\n	Reference: "
operator|+
name|getReference
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
