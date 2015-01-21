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
name|IndividualReservation
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
name|OverrideReservation
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XIndividualReservation
operator|.
name|XIndividualReservationSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XIndividualReservation
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
name|Integer
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|private
name|Boolean
name|iExpired
init|=
literal|null
decl_stmt|;
specifier|public
name|XIndividualReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XIndividualReservation
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
name|XIndividualReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|IndividualReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Individual
argument_list|,
name|offering
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
name|reservation
operator|.
name|getStudents
argument_list|()
control|)
name|iStudentIds
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XIndividualReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|OverrideReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Override
argument_list|,
name|offering
argument_list|,
name|reservation
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
name|reservation
operator|.
name|getStudents
argument_list|()
control|)
name|iStudentIds
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setMustBeUsed
argument_list|(
name|reservation
operator|.
name|getOverrideType
argument_list|()
operator|.
name|isMustBeUsed
argument_list|()
argument_list|)
expr_stmt|;
name|setAllowOverlap
argument_list|(
name|reservation
operator|.
name|getOverrideType
argument_list|()
operator|.
name|isAllowTimeConflict
argument_list|()
argument_list|)
expr_stmt|;
name|setCanAssignOverLimit
argument_list|(
name|reservation
operator|.
name|getOverrideType
argument_list|()
operator|.
name|isAllowOverLimit
argument_list|()
argument_list|)
expr_stmt|;
name|iExpired
operator|=
name|reservation
operator|.
name|getOverrideType
argument_list|()
operator|.
name|isExpired
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XIndividualReservation
parameter_list|(
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|IndividualReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Individual
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
block|}
specifier|public
name|XIndividualReservation
parameter_list|(
name|ReservationOverride
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Override
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
block|}
specifier|public
name|XIndividualReservation
parameter_list|(
name|GroupReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Group
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
name|getReservationLimit
argument_list|()
argument_list|)
expr_stmt|;
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
parameter_list|)
block|{
return|return
name|iStudentIds
operator|.
name|contains
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
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
comment|/**      * Reservation limit == number of students in the reservation      */
annotation|@
name|Override
specifier|public
name|int
name|getReservationLimit
parameter_list|()
block|{
return|return
operator|(
name|iLimit
operator|==
literal|null
condition|?
name|iStudentIds
operator|.
name|size
argument_list|()
else|:
name|iLimit
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isExpired
parameter_list|()
block|{
return|return
operator|(
name|getType
argument_list|()
operator|==
name|XReservationType
operator|.
name|Override
operator|&&
name|iExpired
operator|!=
literal|null
condition|?
name|iExpired
operator|.
name|booleanValue
argument_list|()
else|:
name|super
operator|.
name|isExpired
argument_list|()
operator|)
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
name|iLimit
operator|=
name|in
operator|.
name|readInt
argument_list|()
expr_stmt|;
if|if
condition|(
name|iLimit
operator|==
operator|-
literal|2
condition|)
name|iLimit
operator|=
literal|null
expr_stmt|;
if|if
condition|(
name|getType
argument_list|()
operator|==
name|XReservationType
operator|.
name|Override
condition|)
block|{
switch|switch
condition|(
name|in
operator|.
name|readByte
argument_list|()
condition|)
block|{
case|case
literal|0
case|:
name|iExpired
operator|=
literal|false
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|iExpired
operator|=
literal|true
expr_stmt|;
break|break;
default|default:
name|iExpired
operator|=
literal|null
expr_stmt|;
break|break;
block|}
block|}
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
name|out
operator|.
name|writeInt
argument_list|(
name|iLimit
operator|==
literal|null
condition|?
operator|-
literal|2
else|:
name|iLimit
argument_list|)
expr_stmt|;
if|if
condition|(
name|getType
argument_list|()
operator|==
name|XReservationType
operator|.
name|Override
condition|)
block|{
name|out
operator|.
name|writeByte
argument_list|(
name|iExpired
operator|==
literal|null
condition|?
literal|2
else|:
name|iExpired
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|XIndividualReservationSerializer
implements|implements
name|Externalizer
argument_list|<
name|XIndividualReservation
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
name|XIndividualReservation
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
name|XIndividualReservation
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
name|XIndividualReservation
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

