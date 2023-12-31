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
name|GroupOverrideReservation
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
name|StudentGroupReservation
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
name|XGroupReservation
operator|.
name|XGroupReservationSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XGroupReservation
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
name|XGroup
name|iGroup
decl_stmt|;
specifier|private
name|Boolean
name|iExpired
decl_stmt|;
specifier|private
name|boolean
name|iOverride
init|=
literal|false
decl_stmt|;
specifier|public
name|XGroupReservation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XGroupReservation
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
name|XGroupReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|StudentGroupReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|Group
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
name|iOverride
operator|=
literal|false
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
name|XGroupReservation
parameter_list|(
name|XOffering
name|offering
parameter_list|,
name|GroupOverrideReservation
name|reservation
parameter_list|)
block|{
name|super
argument_list|(
name|XReservationType
operator|.
name|GroupOverride
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
name|iOverride
operator|=
name|reservation
operator|.
name|isAlwaysExpired
argument_list|()
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
name|setMustBeUsed
argument_list|(
name|reservation
operator|.
name|isMustBeUsed
argument_list|()
argument_list|)
expr_stmt|;
name|setAllowOverlap
argument_list|(
name|reservation
operator|.
name|isAllowOverlap
argument_list|()
argument_list|)
expr_stmt|;
name|setCanAssignOverLimit
argument_list|(
name|reservation
operator|.
name|isCanAssignOverLimit
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|reservation
operator|.
name|isAlwaysExpired
argument_list|()
condition|)
name|iExpired
operator|=
literal|true
expr_stmt|;
else|else
name|iType
operator|=
name|XReservationType
operator|.
name|Group
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
annotation|@
name|Override
specifier|public
name|boolean
name|isOverride
parameter_list|()
block|{
return|return
name|iOverride
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
name|GroupOverride
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
name|boolean
name|isAlwaysExpired
parameter_list|()
block|{
return|return
name|getType
argument_list|()
operator|==
name|XReservationType
operator|.
name|GroupOverride
operator|&&
name|iExpired
operator|!=
literal|null
operator|&&
name|iExpired
operator|.
name|booleanValue
argument_list|()
return|;
block|}
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
return|return
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|contains
argument_list|(
name|iGroup
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
name|super
operator|.
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|iGroup
operator|=
operator|new
name|XGroup
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
if|if
condition|(
name|getType
argument_list|()
operator|==
name|XReservationType
operator|.
name|GroupOverride
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
name|iOverride
operator|=
name|in
operator|.
name|readBoolean
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iExpired
operator|=
literal|null
expr_stmt|;
name|iOverride
operator|=
literal|false
expr_stmt|;
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
name|iGroup
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
if|if
condition|(
name|getType
argument_list|()
operator|==
name|XReservationType
operator|.
name|GroupOverride
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
name|out
operator|.
name|writeBoolean
argument_list|(
name|iOverride
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|XGroupReservationSerializer
implements|implements
name|Externalizer
argument_list|<
name|XGroupReservation
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
name|XGroupReservation
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
name|XGroupReservation
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
name|XGroupReservation
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

