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
name|Date
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
name|CourseRequest
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
name|StudentClassEnrollment
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseStudentClassEnrollment
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
name|Date
name|iTimestamp
decl_stmt|;
specifier|private
name|Date
name|iApprovedDate
decl_stmt|;
specifier|private
name|String
name|iApprovedBy
decl_stmt|;
specifier|private
name|String
name|iChangedBy
decl_stmt|;
specifier|private
name|Student
name|iStudent
decl_stmt|;
specifier|private
name|CourseRequest
name|iCourseRequest
decl_stmt|;
specifier|private
name|CourseOffering
name|iCourseOffering
decl_stmt|;
specifier|private
name|Class_
name|iClazz
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
name|PROP_TIMESTAMP
init|=
literal|"timestamp"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_APPROVED_DATE
init|=
literal|"approvedDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_APPROVED_BY
init|=
literal|"approvedBy"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CHANGED_BY
init|=
literal|"changedBy"
decl_stmt|;
specifier|public
name|BaseStudentClassEnrollment
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseStudentClassEnrollment
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
name|Date
name|getTimestamp
parameter_list|()
block|{
return|return
name|iTimestamp
return|;
block|}
specifier|public
name|void
name|setTimestamp
parameter_list|(
name|Date
name|timestamp
parameter_list|)
block|{
name|iTimestamp
operator|=
name|timestamp
expr_stmt|;
block|}
specifier|public
name|Date
name|getApprovedDate
parameter_list|()
block|{
return|return
name|iApprovedDate
return|;
block|}
specifier|public
name|void
name|setApprovedDate
parameter_list|(
name|Date
name|approvedDate
parameter_list|)
block|{
name|iApprovedDate
operator|=
name|approvedDate
expr_stmt|;
block|}
specifier|public
name|String
name|getApprovedBy
parameter_list|()
block|{
return|return
name|iApprovedBy
return|;
block|}
specifier|public
name|void
name|setApprovedBy
parameter_list|(
name|String
name|approvedBy
parameter_list|)
block|{
name|iApprovedBy
operator|=
name|approvedBy
expr_stmt|;
block|}
specifier|public
name|String
name|getChangedBy
parameter_list|()
block|{
return|return
name|iChangedBy
return|;
block|}
specifier|public
name|void
name|setChangedBy
parameter_list|(
name|String
name|changedBy
parameter_list|)
block|{
name|iChangedBy
operator|=
name|changedBy
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
name|CourseRequest
name|getCourseRequest
parameter_list|()
block|{
return|return
name|iCourseRequest
return|;
block|}
specifier|public
name|void
name|setCourseRequest
parameter_list|(
name|CourseRequest
name|courseRequest
parameter_list|)
block|{
name|iCourseRequest
operator|=
name|courseRequest
expr_stmt|;
block|}
specifier|public
name|CourseOffering
name|getCourseOffering
parameter_list|()
block|{
return|return
name|iCourseOffering
return|;
block|}
specifier|public
name|void
name|setCourseOffering
parameter_list|(
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
name|iCourseOffering
operator|=
name|courseOffering
expr_stmt|;
block|}
specifier|public
name|Class_
name|getClazz
parameter_list|()
block|{
return|return
name|iClazz
return|;
block|}
specifier|public
name|void
name|setClazz
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
name|iClazz
operator|=
name|clazz
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
name|StudentClassEnrollment
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
name|StudentClassEnrollment
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
name|StudentClassEnrollment
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
literal|"StudentClassEnrollment["
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
literal|"StudentClassEnrollment["
operator|+
literal|"\n	ApprovedBy: "
operator|+
name|getApprovedBy
argument_list|()
operator|+
literal|"\n	ApprovedDate: "
operator|+
name|getApprovedDate
argument_list|()
operator|+
literal|"\n	ChangedBy: "
operator|+
name|getChangedBy
argument_list|()
operator|+
literal|"\n	Clazz: "
operator|+
name|getClazz
argument_list|()
operator|+
literal|"\n	CourseOffering: "
operator|+
name|getCourseOffering
argument_list|()
operator|+
literal|"\n	CourseRequest: "
operator|+
name|getCourseRequest
argument_list|()
operator|+
literal|"\n	Student: "
operator|+
name|getStudent
argument_list|()
operator|+
literal|"\n	Timestamp: "
operator|+
name|getTimestamp
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

