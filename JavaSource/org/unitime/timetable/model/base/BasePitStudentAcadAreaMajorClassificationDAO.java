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
name|util
operator|.
name|List
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
name|PitStudentAcadAreaMajorClassification
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
name|dao
operator|.
name|_RootDAO
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
name|dao
operator|.
name|PitStudentAcadAreaMajorClassificationDAO
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePitStudentAcadAreaMajorClassificationDAO
extends|extends
name|_RootDAO
argument_list|<
name|PitStudentAcadAreaMajorClassification
argument_list|,
name|Long
argument_list|>
block|{
specifier|private
specifier|static
name|PitStudentAcadAreaMajorClassificationDAO
name|sInstance
decl_stmt|;
specifier|public
specifier|static
name|PitStudentAcadAreaMajorClassificationDAO
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|PitStudentAcadAreaMajorClassificationDAO
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|Class
argument_list|<
name|PitStudentAcadAreaMajorClassification
argument_list|>
name|getReferenceClass
parameter_list|()
block|{
return|return
name|PitStudentAcadAreaMajorClassification
operator|.
name|class
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|PitStudentAcadAreaMajorClassification
argument_list|>
name|findByPitStudent
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|pitStudentId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitStudentAcadAreaMajorClassification x where x.pitStudent.uniqueId = :pitStudentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"pitStudentId"
argument_list|,
name|pitStudentId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|PitStudentAcadAreaMajorClassification
argument_list|>
name|findByAcademicArea
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|academicAreaId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitStudentAcadAreaMajorClassification x where x.academicArea.uniqueId = :academicAreaId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"academicAreaId"
argument_list|,
name|academicAreaId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|PitStudentAcadAreaMajorClassification
argument_list|>
name|findByAcademicClassification
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|academicClassificationId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitStudentAcadAreaMajorClassification x where x.academicClassification.uniqueId = :academicClassificationId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"academicClassificationId"
argument_list|,
name|academicClassificationId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|PitStudentAcadAreaMajorClassification
argument_list|>
name|findByMajor
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|majorId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitStudentAcadAreaMajorClassification x where x.major.uniqueId = :majorId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"majorId"
argument_list|,
name|majorId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
end_class

end_unit
