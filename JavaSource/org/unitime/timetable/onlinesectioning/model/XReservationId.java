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
name|Externalizable
import|;
end_import

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
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|online
operator|.
name|OnlineReservation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|CourseReservation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|CurriculumReservation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|GroupReservation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|IndividualReservation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|LearningCommunityReservation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|Reservation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|ReservationOverride
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XReservationId
operator|.
name|XReservationIdSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XReservationId
implements|implements
name|Serializable
implements|,
name|Externalizable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
name|XReservationType
name|iType
decl_stmt|;
specifier|private
name|Long
name|iOfferingId
decl_stmt|;
specifier|private
name|Long
name|iReservationId
decl_stmt|;
specifier|public
name|XReservationId
parameter_list|()
block|{
block|}
specifier|public
name|XReservationId
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XReservationId
parameter_list|(
name|XReservationType
name|type
parameter_list|,
name|Long
name|offeringId
parameter_list|,
name|Long
name|reservationId
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iOfferingId
operator|=
name|offeringId
expr_stmt|;
name|iReservationId
operator|=
name|reservationId
expr_stmt|;
block|}
specifier|public
name|XReservationId
parameter_list|(
name|XReservationId
name|reservation
parameter_list|)
block|{
name|iType
operator|=
name|reservation
operator|.
name|getType
argument_list|()
expr_stmt|;
name|iOfferingId
operator|=
name|reservation
operator|.
name|getOfferingId
argument_list|()
expr_stmt|;
name|iReservationId
operator|=
name|reservation
operator|.
name|getReservationId
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XReservationId
parameter_list|(
name|Reservation
name|reservation
parameter_list|)
block|{
name|iOfferingId
operator|=
name|reservation
operator|.
name|getOffering
argument_list|()
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iReservationId
operator|=
name|reservation
operator|.
name|getId
argument_list|()
expr_stmt|;
if|if
condition|(
name|reservation
operator|instanceof
name|OnlineReservation
condition|)
name|iType
operator|=
name|XReservationType
operator|.
name|values
argument_list|()
index|[
operator|(
operator|(
name|OnlineReservation
operator|)
name|reservation
operator|)
operator|.
name|getType
argument_list|()
index|]
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|ReservationOverride
condition|)
name|iType
operator|=
name|XReservationType
operator|.
name|IndividualOverride
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|LearningCommunityReservation
condition|)
name|iType
operator|=
name|XReservationType
operator|.
name|LearningCommunity
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|GroupReservation
condition|)
name|iType
operator|=
name|XReservationType
operator|.
name|Group
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|IndividualReservation
condition|)
name|iType
operator|=
name|XReservationType
operator|.
name|Individual
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|CurriculumReservation
condition|)
name|iType
operator|=
name|XReservationType
operator|.
name|Curriculum
expr_stmt|;
if|else if
condition|(
name|reservation
operator|instanceof
name|CourseReservation
condition|)
name|iType
operator|=
name|XReservationType
operator|.
name|Course
expr_stmt|;
else|else
name|iType
operator|=
name|XReservationType
operator|.
name|Dummy
expr_stmt|;
block|}
specifier|public
name|XReservationType
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|Long
name|getOfferingId
parameter_list|()
block|{
return|return
name|iOfferingId
return|;
block|}
specifier|public
name|Long
name|getReservationId
parameter_list|()
block|{
return|return
name|iReservationId
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
operator|(
name|int
operator|)
operator|(
name|getReservationId
argument_list|()
operator|^
operator|(
name|getReservationId
argument_list|()
operator|>>>
literal|32
operator|)
operator|)
return|;
block|}
annotation|@
name|Override
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
name|XReservationId
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getReservationId
argument_list|()
operator|==
operator|(
operator|(
name|XReservationId
operator|)
name|o
operator|)
operator|.
name|getReservationId
argument_list|()
operator|&&
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|XReservationId
operator|)
name|o
operator|)
operator|.
name|getOfferingId
argument_list|()
argument_list|)
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
name|iType
operator|=
name|XReservationType
operator|.
name|values
argument_list|()
index|[
name|in
operator|.
name|readInt
argument_list|()
index|]
expr_stmt|;
name|iOfferingId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iReservationId
operator|=
name|in
operator|.
name|readLong
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
name|out
operator|.
name|writeInt
argument_list|(
name|iType
operator|.
name|ordinal
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeLong
argument_list|(
name|iOfferingId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeLong
argument_list|(
name|iReservationId
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|XReservationIdSerializer
implements|implements
name|Externalizer
argument_list|<
name|XReservationId
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
name|XReservationId
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
name|XReservationId
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
name|XReservationId
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

