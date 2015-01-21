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
name|CourseOffering
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
name|Exam
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
name|ExamOwner
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseExamOwner
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
name|Long
name|iOwnerId
decl_stmt|;
specifier|private
name|Integer
name|iOwnerType
decl_stmt|;
specifier|private
name|Exam
name|iExam
decl_stmt|;
specifier|private
name|CourseOffering
name|iCourse
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
name|PROP_OWNER_ID
init|=
literal|"ownerId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_OWNER_TYPE
init|=
literal|"ownerType"
decl_stmt|;
specifier|public
name|BaseExamOwner
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseExamOwner
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
name|Long
name|getOwnerId
parameter_list|()
block|{
return|return
name|iOwnerId
return|;
block|}
specifier|public
name|void
name|setOwnerId
parameter_list|(
name|Long
name|ownerId
parameter_list|)
block|{
name|iOwnerId
operator|=
name|ownerId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getOwnerType
parameter_list|()
block|{
return|return
name|iOwnerType
return|;
block|}
specifier|public
name|void
name|setOwnerType
parameter_list|(
name|Integer
name|ownerType
parameter_list|)
block|{
name|iOwnerType
operator|=
name|ownerType
expr_stmt|;
block|}
specifier|public
name|Exam
name|getExam
parameter_list|()
block|{
return|return
name|iExam
return|;
block|}
specifier|public
name|void
name|setExam
parameter_list|(
name|Exam
name|exam
parameter_list|)
block|{
name|iExam
operator|=
name|exam
expr_stmt|;
block|}
specifier|public
name|CourseOffering
name|getCourse
parameter_list|()
block|{
return|return
name|iCourse
return|;
block|}
specifier|public
name|void
name|setCourse
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
block|{
name|iCourse
operator|=
name|course
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
name|ExamOwner
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
name|ExamOwner
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
name|ExamOwner
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
literal|"ExamOwner["
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
literal|"ExamOwner["
operator|+
literal|"\n	Course: "
operator|+
name|getCourse
argument_list|()
operator|+
literal|"\n	Exam: "
operator|+
name|getExam
argument_list|()
operator|+
literal|"\n	OwnerId: "
operator|+
name|getOwnerId
argument_list|()
operator|+
literal|"\n	OwnerType: "
operator|+
name|getOwnerType
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

