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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Iterator
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
name|base
operator|.
name|BaseCourseEvent
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseEvent
extends|extends
name|BaseCourseEvent
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|CourseEvent
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|CourseEvent
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|Set
argument_list|<
name|Student
argument_list|>
name|getStudents
parameter_list|()
block|{
name|HashSet
argument_list|<
name|Student
argument_list|>
name|students
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|isReqAttendance
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|isReqAttendance
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
return|return
name|students
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|getRelatedCourses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|students
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getStudents
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|students
return|;
block|}
specifier|public
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|getInstructors
parameter_list|()
block|{
name|HashSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|isReqAttendance
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|isReqAttendance
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
return|return
name|instructors
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|getRelatedCourses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|instructors
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getInstructors
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|instructors
return|;
block|}
specifier|public
name|int
name|getEventType
parameter_list|()
block|{
return|return
name|sEventTypeCourse
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getStudentIds
parameter_list|()
block|{
name|HashSet
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getRelatedCourses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|studentIds
operator|.
name|addAll
argument_list|(
operator|(
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getStudentIds
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|studentIds
return|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|getStudentClassEnrollments
parameter_list|()
block|{
name|HashSet
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrollments
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|RelatedCourseInfo
name|owner
range|:
name|getRelatedCourses
argument_list|()
control|)
name|enrollments
operator|.
name|addAll
argument_list|(
name|owner
operator|.
name|getStudentClassEnrollments
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|enrollments
return|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
for|for
control|(
name|RelatedCourseInfo
name|rc
range|:
name|getRelatedCourses
argument_list|()
control|)
return|return
name|rc
operator|.
name|getCourse
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSession
argument_list|()
return|;
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

