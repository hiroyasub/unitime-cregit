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
name|DepartmentalInstructor
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
name|OfferingCoordinator
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
name|BaseOfferingCoordinator
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
name|DepartmentalInstructor
name|iInstructor
decl_stmt|;
specifier|private
name|InstructionalOffering
name|iOffering
decl_stmt|;
specifier|private
name|TeachingResponsibility
name|iResponsibility
decl_stmt|;
specifier|public
name|BaseOfferingCoordinator
parameter_list|()
block|{
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
name|InstructionalOffering
name|getOffering
parameter_list|()
block|{
return|return
name|iOffering
return|;
block|}
specifier|public
name|void
name|setOffering
parameter_list|(
name|InstructionalOffering
name|offering
parameter_list|)
block|{
name|iOffering
operator|=
name|offering
expr_stmt|;
block|}
specifier|public
name|TeachingResponsibility
name|getResponsibility
parameter_list|()
block|{
return|return
name|iResponsibility
return|;
block|}
specifier|public
name|void
name|setResponsibility
parameter_list|(
name|TeachingResponsibility
name|responsibility
parameter_list|)
block|{
name|iResponsibility
operator|=
name|responsibility
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
name|OfferingCoordinator
operator|)
condition|)
return|return
literal|false
return|;
name|OfferingCoordinator
name|offeringCoordinator
init|=
operator|(
name|OfferingCoordinator
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getInstructor
argument_list|()
operator|==
literal|null
operator|||
name|offeringCoordinator
operator|.
name|getInstructor
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|getInstructor
argument_list|()
operator|.
name|equals
argument_list|(
name|offeringCoordinator
operator|.
name|getInstructor
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getOffering
argument_list|()
operator|==
literal|null
operator|||
name|offeringCoordinator
operator|.
name|getOffering
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|getOffering
argument_list|()
operator|.
name|equals
argument_list|(
name|offeringCoordinator
operator|.
name|getOffering
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getInstructor
argument_list|()
operator|==
literal|null
operator|||
name|getOffering
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
name|getInstructor
argument_list|()
operator|.
name|hashCode
argument_list|()
operator|^
name|getOffering
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
literal|"OfferingCoordinator["
operator|+
name|getInstructor
argument_list|()
operator|+
literal|", "
operator|+
name|getOffering
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
literal|"OfferingCoordinator["
operator|+
literal|"\n	Instructor: "
operator|+
name|getInstructor
argument_list|()
operator|+
literal|"\n	Offering: "
operator|+
name|getOffering
argument_list|()
operator|+
literal|"\n	Responsibility: "
operator|+
name|getResponsibility
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

