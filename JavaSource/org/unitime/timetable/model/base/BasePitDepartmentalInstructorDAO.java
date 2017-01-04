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
name|PitDepartmentalInstructor
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
name|PitDepartmentalInstructorDAO
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BasePitDepartmentalInstructorDAO
extends|extends
name|_RootDAO
argument_list|<
name|PitDepartmentalInstructor
argument_list|,
name|Long
argument_list|>
block|{
specifier|private
specifier|static
name|PitDepartmentalInstructorDAO
name|sInstance
decl_stmt|;
specifier|public
specifier|static
name|PitDepartmentalInstructorDAO
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
name|PitDepartmentalInstructorDAO
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|Class
argument_list|<
name|PitDepartmentalInstructor
argument_list|>
name|getReferenceClass
parameter_list|()
block|{
return|return
name|PitDepartmentalInstructor
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
name|PitDepartmentalInstructor
argument_list|>
name|findByPositionType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|positionTypeId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitDepartmentalInstructor x where x.positionType.uniqueId = :positionTypeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"positionTypeId"
argument_list|,
name|positionTypeId
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
name|PitDepartmentalInstructor
argument_list|>
name|findByDepartment
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|departmentId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitDepartmentalInstructor x where x.department.uniqueId = :departmentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|departmentId
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
name|PitDepartmentalInstructor
argument_list|>
name|findByPointInTimeData
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|pointInTimeDataId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitDepartmentalInstructor x where x.pointInTimeData.uniqueId = :pointInTimeDataId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"pointInTimeDataId"
argument_list|,
name|pointInTimeDataId
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
name|PitDepartmentalInstructor
argument_list|>
name|findByDepartmentalInstructor
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|departmentalInstructorId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PitDepartmentalInstructor x where x.departmentalInstructor.uniqueId = :departmentalInstructorId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentalInstructorId"
argument_list|,
name|departmentalInstructorId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
end_class

end_unit

