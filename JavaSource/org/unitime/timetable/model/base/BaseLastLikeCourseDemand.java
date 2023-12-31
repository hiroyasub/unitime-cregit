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
name|LastLikeCourseDemand
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
name|Student
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
name|SubjectArea
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseLastLikeCourseDemand
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
name|String
name|iCourseNbr
decl_stmt|;
specifier|private
name|Integer
name|iPriority
decl_stmt|;
specifier|private
name|String
name|iCoursePermId
decl_stmt|;
specifier|private
name|Student
name|iStudent
decl_stmt|;
specifier|private
name|SubjectArea
name|iSubjectArea
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
name|PROP_COURSE_NBR
init|=
literal|"courseNbr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PRIORITY
init|=
literal|"priority"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COURSE_PERM_ID
init|=
literal|"coursePermId"
decl_stmt|;
specifier|public
name|BaseLastLikeCourseDemand
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseLastLikeCourseDemand
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
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|iCourseNbr
return|;
block|}
specifier|public
name|void
name|setCourseNbr
parameter_list|(
name|String
name|courseNbr
parameter_list|)
block|{
name|iCourseNbr
operator|=
name|courseNbr
expr_stmt|;
block|}
specifier|public
name|Integer
name|getPriority
parameter_list|()
block|{
return|return
name|iPriority
return|;
block|}
specifier|public
name|void
name|setPriority
parameter_list|(
name|Integer
name|priority
parameter_list|)
block|{
name|iPriority
operator|=
name|priority
expr_stmt|;
block|}
specifier|public
name|String
name|getCoursePermId
parameter_list|()
block|{
return|return
name|iCoursePermId
return|;
block|}
specifier|public
name|void
name|setCoursePermId
parameter_list|(
name|String
name|coursePermId
parameter_list|)
block|{
name|iCoursePermId
operator|=
name|coursePermId
expr_stmt|;
block|}
specifier|public
name|Student
name|getStudent
parameter_list|()
block|{
return|return
name|iStudent
return|;
block|}
specifier|public
name|void
name|setStudent
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|iStudent
operator|=
name|student
expr_stmt|;
block|}
specifier|public
name|SubjectArea
name|getSubjectArea
parameter_list|()
block|{
return|return
name|iSubjectArea
return|;
block|}
specifier|public
name|void
name|setSubjectArea
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
name|iSubjectArea
operator|=
name|subjectArea
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
name|LastLikeCourseDemand
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
name|LastLikeCourseDemand
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
name|LastLikeCourseDemand
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
literal|"LastLikeCourseDemand["
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
literal|"LastLikeCourseDemand["
operator|+
literal|"\n	CourseNbr: "
operator|+
name|getCourseNbr
argument_list|()
operator|+
literal|"\n	CoursePermId: "
operator|+
name|getCoursePermId
argument_list|()
operator|+
literal|"\n	Priority: "
operator|+
name|getPriority
argument_list|()
operator|+
literal|"\n	Student: "
operator|+
name|getStudent
argument_list|()
operator|+
literal|"\n	SubjectArea: "
operator|+
name|getSubjectArea
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

