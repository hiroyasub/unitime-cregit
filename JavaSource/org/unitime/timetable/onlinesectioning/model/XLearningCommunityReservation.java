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
name|LearningCommunityReservation
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
name|StudentGroupType
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XStudent
operator|.
name|XGroup
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XLearningCommunityReservation
operator|.
name|XLearningCommunityReservationSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XLearningCommunityReservation
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
name|int
name|iLimit
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iStudentIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|XCourseId
name|iCourseId
decl_stmt|;
specifier|private
name|XGroup
name|iGroup
decl_stmt|;
specifier|public
name|XLearningCommunityReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XLearningCommunityReservation
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
name|XLearningCommunityReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|LearningCommunityReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|LearningCommunity
argument_list|,
name|offering
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
name|iLimit
operator|=
operator|(
name|reservation
operator|.
name|getLimit
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|reservation
operator|.
name|getLimit
argument_list|()
operator|)
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
name|iGroup
operator|=
operator|new
name|XGroup
argument_list|(
name|reservation
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservation
operator|.
name|getGroup
argument_list|()
operator|.
name|getType
argument_list|()
operator|!=
literal|null
operator|&&
name|reservation
operator|.
name|getGroup
argument_list|()
operator|.
name|getType
argument_list|()
operator|.
name|getAllowDisabledSection
argument_list|()
operator|==
name|StudentGroupType
operator|.
name|AllowDisabledSection
operator|.
name|WithGroupReservation
condition|)
name|setAllowDisabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XLearningCommunityReservation
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|LearningCommunityReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|LearningCommunity
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
name|iStudentIds
operator|.
name|addAll
argument_list|(
name|reservation
operator|.
name|getStudentIds
argument_list|()
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
operator|(
name|int
operator|)
name|Math
operator|.
name|round
argument_list|(
name|reservation
operator|.
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|setAllowDisabled
argument_list|(
name|reservation
operator|.
name|isAllowDisabled
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XGroup
name|getGroup
parameter_list|()
block|{
return|return
name|iGroup
return|;
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
comment|/**      * Reservation limit      */
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
comment|/**      * Reservation is applicable for all students in the reservation      */
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
name|iGroup
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|iStudentIds
operator|.
name|contains
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|contains
argument_list|(
name|iGroup
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
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
comment|/**      * Students in the reservation      */
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getStudentIds
parameter_list|()
block|{
return|return
name|iStudentIds
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
if|if
condition|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|)
block|{
name|iGroup
operator|=
operator|new
name|XGroup
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iGroup
operator|=
literal|null
expr_stmt|;
block|}
name|int
name|nrStudents
init|=
name|in
operator|.
name|readInt
argument_list|()
decl_stmt|;
name|iStudentIds
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|nrStudents
condition|;
name|i
operator|++
control|)
name|iStudentIds
operator|.
name|add
argument_list|(
name|in
operator|.
name|readLong
argument_list|()
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
if|if
condition|(
name|iGroup
operator|!=
literal|null
condition|)
block|{
name|out
operator|.
name|writeBoolean
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iGroup
operator|.
name|writeExternal
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|writeInt
argument_list|(
name|iStudentIds
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|iStudentIds
control|)
name|out
operator|.
name|writeLong
argument_list|(
name|studentId
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
name|XLearningCommunityReservationSerializer
implements|implements
name|Externalizer
argument_list|<
name|XLearningCommunityReservation
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
name|XLearningCommunityReservation
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
name|XLearningCommunityReservation
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
name|XLearningCommunityReservation
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit
