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
name|onlinesectioning
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
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
name|CourseReservation
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XCourseReservation
operator|.
name|XCourseReservationSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XCourseReservation
extends|extends
name|XReservation
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
name|XCourseId
name|iCourseId
decl_stmt|;
specifier|private
name|int
name|iLimit
init|=
operator|-
literal|1
decl_stmt|;
specifier|public
name|XCourseReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XCourseReservation
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
argument_list|()
expr_stmt|;
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XCourseReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|CourseReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Course
argument_list|,
name|offering
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
name|iCourseId
operator|=
operator|new
name|XCourseId
argument_list|(
name|reservation
operator|.
name|getCourse
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XCourse
name|course
range|:
name|offering
operator|.
name|getCourses
argument_list|()
control|)
if|if
condition|(
name|course
operator|.
name|getCourseId
argument_list|()
operator|.
name|equals
argument_list|(
name|iCourseId
argument_list|)
condition|)
name|iLimit
operator|=
name|course
operator|.
name|getLimit
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XCourseReservation
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|CourseReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Course
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
name|iCourseId
operator|=
operator|new
name|XCourseId
argument_list|(
name|reservation
operator|.
name|getCourse
argument_list|()
argument_list|)
expr_stmt|;
name|iLimit
operator|=
name|reservation
operator|.
name|getCourse
argument_list|()
operator|.
name|getLimit
argument_list|()
expr_stmt|;
block|}
comment|/** Course offering id */
specifier|public
name|Long
name|getCourseId
parameter_list|()
block|{
return|return
name|iCourseId
operator|.
name|getCourseId
argument_list|()
return|;
block|}
comment|/** Instructional offering id */
specifier|public
name|Long
name|getOfferingId
parameter_list|()
block|{
return|return
name|iCourseId
operator|.
name|getOfferingId
argument_list|()
return|;
block|}
comment|/**      * Reservation limit (-1 for unlimited)      */
annotation|@
name|Override
specifier|public
name|int
name|getReservationLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
comment|/**      * Check the courses      */
annotation|@
name|Override
specifier|public
name|boolean
name|isApplicable
parameter_list|(
name|XStudent
name|student
parameter_list|,
name|XCourseId
name|course
parameter_list|)
block|{
if|if
condition|(
name|course
operator|!=
literal|null
condition|)
return|return
name|iCourseId
operator|.
name|equals
argument_list|(
name|course
argument_list|)
return|;
for|for
control|(
name|XRequest
name|request
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|request
operator|instanceof
name|XCourseRequest
operator|&&
operator|(
operator|(
name|XCourseRequest
operator|)
name|request
operator|)
operator|.
name|getCourseIds
argument_list|()
operator|.
name|contains
argument_list|(
name|iCourseId
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|super
operator|.
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|iCourseId
operator|=
operator|new
name|XCourseId
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|iLimit
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|super
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|iCourseId
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeInt
argument_list|(
name|iLimit
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XCourseReservationSerializer
implements|implements
name|Externalizer
argument_list|<
name|XCourseReservation
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XCourseReservation
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XCourseReservation
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XCourseReservation
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

