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
name|gwt
operator|.
name|client
operator|.
name|rooms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|gwt
operator|.
name|shared
operator|.
name|RoomInterface
operator|.
name|RoomDetailInterface
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
name|gwt
operator|.
name|shared
operator|.
name|RoomInterface
operator|.
name|RoomsColumn
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomsComparator
implements|implements
name|Comparator
argument_list|<
name|RoomDetailInterface
argument_list|>
block|{
specifier|private
name|RoomsColumn
name|iColumn
decl_stmt|;
specifier|private
name|boolean
name|iAsc
decl_stmt|;
specifier|public
name|int
name|compareById
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|r2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByName
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getLabel
argument_list|()
argument_list|,
name|r2
operator|.
name|getLabel
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByExternalId
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getExternalId
argument_list|()
argument_list|,
name|r2
operator|.
name|getExternalId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByType
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
if|if
condition|(
name|r1
operator|.
name|getRoomType
argument_list|()
operator|.
name|hasOrder
argument_list|()
condition|)
block|{
if|if
condition|(
name|r2
operator|.
name|getRoomType
argument_list|()
operator|.
name|hasOrder
argument_list|()
condition|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getRoomType
argument_list|()
operator|.
name|getOrder
argument_list|()
argument_list|,
name|r2
operator|.
name|getRoomType
argument_list|()
operator|.
name|getOrder
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|r2
operator|.
name|getRoomType
argument_list|()
operator|.
name|hasOrder
argument_list|()
condition|)
block|{
return|return
literal|1
return|;
block|}
else|else
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getRoomType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|,
name|r2
operator|.
name|getRoomType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
specifier|public
name|int
name|compareByCapacity
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
operator|-
name|compare
argument_list|(
name|r1
operator|.
name|getCapacity
argument_list|()
argument_list|,
name|r2
operator|.
name|getCapacity
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByExamCapacity
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
operator|-
name|compare
argument_list|(
name|r1
operator|.
name|getExamCapacity
argument_list|()
operator|==
literal|null
condition|?
name|r1
operator|.
name|getCapacity
argument_list|()
else|:
name|r1
operator|.
name|getExamCapacity
argument_list|()
argument_list|,
name|r2
operator|.
name|getExamCapacity
argument_list|()
operator|==
literal|null
condition|?
name|r2
operator|.
name|getCapacity
argument_list|()
else|:
name|r2
operator|.
name|getExamCapacity
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByDistance
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
operator|-
name|compare
argument_list|(
name|r1
operator|.
name|isIgnoreTooFar
argument_list|()
argument_list|,
name|r2
operator|.
name|isIgnoreTooFar
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByRoomCheck
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
operator|-
name|compare
argument_list|(
name|r1
operator|.
name|isIgnoreRoomCheck
argument_list|()
argument_list|,
name|r2
operator|.
name|isIgnoreRoomCheck
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByControl
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getControlDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r1
operator|.
name|getControlDepartment
argument_list|()
operator|.
name|getAbbreviationOrCode
argument_list|()
argument_list|,
name|r2
operator|.
name|getControlDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r2
operator|.
name|getControlDepartment
argument_list|()
operator|.
name|getAbbreviationOrCode
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByEventDepartment
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getEventDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r1
operator|.
name|getEventDepartment
argument_list|()
operator|.
name|getAbbreviationOrCode
argument_list|()
argument_list|,
name|r2
operator|.
name|getEventDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|r2
operator|.
name|getEventDepartment
argument_list|()
operator|.
name|getAbbreviationOrCode
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByEventStatus
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getEventStatus
argument_list|()
argument_list|,
name|r2
operator|.
name|getEventStatus
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByEventMessage
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getEventNote
argument_list|()
argument_list|,
name|r2
operator|.
name|getEventNote
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByBreakTime
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getBreakTime
argument_list|()
argument_list|,
name|r2
operator|.
name|getBreakTime
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByServices
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getServices
argument_list|(
literal|"|"
argument_list|)
argument_list|,
name|r2
operator|.
name|getServices
argument_list|(
literal|"|"
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|int
name|compareByColumn
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
switch|switch
condition|(
name|iColumn
condition|)
block|{
case|case
name|NAME
case|:
return|return
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EXTERNAL_ID
case|:
return|return
name|compareByExternalId
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|TYPE
case|:
return|return
name|compareByType
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|CAPACITY
case|:
return|return
name|compareByCapacity
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EXAM_CAPACITY
case|:
return|return
name|compareByExamCapacity
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|DISTANCE_CHECK
case|:
return|return
name|compareByDistance
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|ROOM_CHECK
case|:
return|return
name|compareByRoomCheck
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|CONTROL_DEPT
case|:
return|return
name|compareByControl
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EVENT_DEPARTMENT
case|:
return|return
name|compareByEventDepartment
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EVENT_STATUS
case|:
return|return
name|compareByEventStatus
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EVENT_MESSAGE
case|:
return|return
name|compareByEventMessage
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|BREAK_TIME
case|:
return|return
name|compareByBreakTime
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|SERVICES
case|:
return|return
name|compareByServices
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
default|default:
return|return
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
block|}
block|}
specifier|public
name|RoomsComparator
parameter_list|(
name|RoomsColumn
name|column
parameter_list|,
name|boolean
name|asc
parameter_list|)
block|{
name|iColumn
operator|=
name|column
expr_stmt|;
name|iAsc
operator|=
name|asc
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|RoomDetailInterface
name|r1
parameter_list|,
name|RoomDetailInterface
name|r2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compareByColumn
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareByType
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareById
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
return|return
operator|(
name|iAsc
condition|?
name|cmp
else|:
operator|-
name|cmp
operator|)
return|;
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
if|if
condition|(
name|s1
operator|==
literal|null
operator|||
name|s1
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
operator|-
literal|1
else|:
name|s1
operator|.
name|compareToIgnoreCase
argument_list|(
name|s2
argument_list|)
operator|)
return|;
block|}
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|Number
name|n1
parameter_list|,
name|Number
name|n2
parameter_list|)
block|{
return|return
operator|(
name|n1
operator|==
literal|null
condition|?
name|n2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|n2
operator|==
literal|null
condition|?
literal|1
else|:
name|Double
operator|.
name|compare
argument_list|(
name|n1
operator|.
name|doubleValue
argument_list|()
argument_list|,
name|n2
operator|.
name|doubleValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|Boolean
name|b1
parameter_list|,
name|Boolean
name|b2
parameter_list|)
block|{
return|return
operator|(
name|b1
operator|==
literal|null
condition|?
name|b2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|b2
operator|==
literal|null
condition|?
literal|1
else|:
operator|(
name|b1
operator|.
name|booleanValue
argument_list|()
operator|==
name|b2
operator|.
name|booleanValue
argument_list|()
operator|)
condition|?
literal|0
else|:
operator|(
name|b1
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
operator|-
literal|1
operator|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isApplicable
parameter_list|(
name|RoomsColumn
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|NAME
case|:
case|case
name|EXTERNAL_ID
case|:
case|case
name|TYPE
case|:
case|case
name|CAPACITY
case|:
case|case
name|EXAM_CAPACITY
case|:
case|case
name|DISTANCE_CHECK
case|:
case|case
name|ROOM_CHECK
case|:
case|case
name|CONTROL_DEPT
case|:
case|case
name|EVENT_DEPARTMENT
case|:
case|case
name|EVENT_STATUS
case|:
case|case
name|EVENT_MESSAGE
case|:
case|case
name|BREAK_TIME
case|:
case|case
name|SERVICES
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

