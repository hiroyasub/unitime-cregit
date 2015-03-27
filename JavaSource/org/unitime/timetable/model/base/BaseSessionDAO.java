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
name|Session
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
name|SessionDAO
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSessionDAO
extends|extends
name|_RootDAO
argument_list|<
name|Session
argument_list|,
name|Long
argument_list|>
block|{
specifier|private
specifier|static
name|SessionDAO
name|sInstance
decl_stmt|;
specifier|public
specifier|static
name|SessionDAO
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
name|SessionDAO
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|Class
argument_list|<
name|Session
argument_list|>
name|getReferenceClass
parameter_list|()
block|{
return|return
name|Session
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
name|Session
argument_list|>
name|findByStatusType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|statusTypeId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Session x where x.statusType.uniqueId = :statusTypeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"statusTypeId"
argument_list|,
name|statusTypeId
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
name|Session
argument_list|>
name|findByDefaultDatePattern
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|defaultDatePatternId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Session x where x.defaultDatePattern.uniqueId = :defaultDatePatternId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"defaultDatePatternId"
argument_list|,
name|defaultDatePatternId
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
name|Session
argument_list|>
name|findByDefaultSectioningStatus
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|defaultSectioningStatusId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Session x where x.defaultSectioningStatus.uniqueId = :defaultSectioningStatusId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"defaultSectioningStatusId"
argument_list|,
name|defaultSectioningStatusId
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
name|Session
argument_list|>
name|findByDefaultClassDurationType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|defaultClassDurationTypeId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Session x where x.defaultClassDurationType.uniqueId = :defaultClassDurationTypeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"defaultClassDurationTypeId"
argument_list|,
name|defaultClassDurationTypeId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
end_class

end_unit

