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
name|AcademicArea
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
name|AcademicClassification
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
name|CurriculumReservation
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
name|PosMajor
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
name|Reservation
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCurriculumReservation
extends|extends
name|Reservation
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
name|AcademicArea
name|iArea
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|PosMajor
argument_list|>
name|iMajors
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|AcademicClassification
argument_list|>
name|iClassifications
decl_stmt|;
specifier|public
name|BaseCurriculumReservation
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseCurriculumReservation
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
name|AcademicArea
name|getArea
parameter_list|()
block|{
return|return
name|iArea
return|;
block|}
specifier|public
name|void
name|setArea
parameter_list|(
name|AcademicArea
name|area
parameter_list|)
block|{
name|iArea
operator|=
name|area
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|PosMajor
argument_list|>
name|getMajors
parameter_list|()
block|{
return|return
name|iMajors
return|;
block|}
specifier|public
name|void
name|setMajors
parameter_list|(
name|Set
argument_list|<
name|PosMajor
argument_list|>
name|majors
parameter_list|)
block|{
name|iMajors
operator|=
name|majors
expr_stmt|;
block|}
specifier|public
name|void
name|addTomajors
parameter_list|(
name|PosMajor
name|posMajor
parameter_list|)
block|{
if|if
condition|(
name|iMajors
operator|==
literal|null
condition|)
name|iMajors
operator|=
operator|new
name|HashSet
argument_list|<
name|PosMajor
argument_list|>
argument_list|()
expr_stmt|;
name|iMajors
operator|.
name|add
argument_list|(
name|posMajor
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|AcademicClassification
argument_list|>
name|getClassifications
parameter_list|()
block|{
return|return
name|iClassifications
return|;
block|}
specifier|public
name|void
name|setClassifications
parameter_list|(
name|Set
argument_list|<
name|AcademicClassification
argument_list|>
name|classifications
parameter_list|)
block|{
name|iClassifications
operator|=
name|classifications
expr_stmt|;
block|}
specifier|public
name|void
name|addToclassifications
parameter_list|(
name|AcademicClassification
name|academicClassification
parameter_list|)
block|{
if|if
condition|(
name|iClassifications
operator|==
literal|null
condition|)
name|iClassifications
operator|=
operator|new
name|HashSet
argument_list|<
name|AcademicClassification
argument_list|>
argument_list|()
expr_stmt|;
name|iClassifications
operator|.
name|add
argument_list|(
name|academicClassification
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
name|CurriculumReservation
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
name|CurriculumReservation
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
name|CurriculumReservation
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
literal|"CurriculumReservation["
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
literal|"CurriculumReservation["
operator|+
literal|"\n	Area: "
operator|+
name|getArea
argument_list|()
operator|+
literal|"\n	ExpirationDate: "
operator|+
name|getExpirationDate
argument_list|()
operator|+
literal|"\n	Inclusive: "
operator|+
name|getInclusive
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
literal|"\n	StartDate: "
operator|+
name|getStartDate
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

