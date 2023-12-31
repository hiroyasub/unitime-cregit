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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|defaults
operator|.
name|ApplicationProperty
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
name|BaseCourseReservation
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseReservation
extends|extends
name|BaseCourseReservation
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|1024840740051802430L
decl_stmt|;
specifier|public
name|CourseReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isApplicable
parameter_list|(
name|Student
name|student
parameter_list|,
name|CourseRequest
name|request
parameter_list|)
block|{
return|return
name|request
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|equals
argument_list|(
name|getCourse
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getPriority
parameter_list|()
block|{
return|return
name|ApplicationProperty
operator|.
name|ReservationPriorityCourse
operator|.
name|intValue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isCanAssignOverLimit
parameter_list|()
block|{
return|return
name|ApplicationProperty
operator|.
name|ReservationCanOverLimitCourse
operator|.
name|isTrue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isMustBeUsed
parameter_list|()
block|{
return|return
name|ApplicationProperty
operator|.
name|ReservationMustBeUsedCourse
operator|.
name|isTrue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAllowOverlap
parameter_list|()
block|{
return|return
name|ApplicationProperty
operator|.
name|ReservationAllowOverlapCourse
operator|.
name|isTrue
argument_list|()
return|;
block|}
block|}
end_class

end_unit

