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
name|solver
operator|.
name|ui
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
name|BitSet
import|;
end_import

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
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|GroupConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|RoomConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Lecture
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Placement
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomLocation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimetableModel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|assignment
operator|.
name|Assignment
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|solver
operator|.
name|Solver
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|PreferenceLevel
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomReport
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|int
index|[]
name|sGroupSizes
init|=
operator|new
name|int
index|[]
block|{
literal|0
block|,
literal|10
block|,
literal|20
block|,
literal|40
block|,
literal|60
block|,
literal|80
block|,
literal|100
block|,
literal|150
block|,
literal|200
block|,
literal|400
block|,
name|Integer
operator|.
name|MAX_VALUE
block|}
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|HashSet
name|iGroups
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
specifier|private
name|Long
name|iRoomType
init|=
literal|null
decl_stmt|;
specifier|private
name|BitSet
name|iSessionDays
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iStartDayDayOfWeek
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|iNrWeeks
init|=
literal|0.0
decl_stmt|;
specifier|public
name|RoomReport
parameter_list|(
name|Solver
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|solver
parameter_list|,
name|BitSet
name|sessionDays
parameter_list|,
name|int
name|startDayDayOfWeek
parameter_list|,
name|Long
name|roomType
parameter_list|)
block|{
name|TimetableModel
name|model
init|=
operator|(
name|TimetableModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|Assignment
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|assignment
init|=
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
name|iSessionDays
operator|=
name|sessionDays
expr_stmt|;
name|iStartDayDayOfWeek
operator|=
name|startDayDayOfWeek
expr_stmt|;
name|iRoomType
operator|=
name|roomType
expr_stmt|;
comment|// count number of weeks as a number of working days / 5
comment|// (this is to avoid problems when the default date pattern does not contain Saturdays and/or Sundays)
name|int
name|dow
init|=
name|iStartDayDayOfWeek
decl_stmt|;
name|int
name|nrDays
index|[]
init|=
operator|new
name|int
index|[]
block|{
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|}
decl_stmt|;
for|for
control|(
name|int
name|day
init|=
name|iSessionDays
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
init|;
name|day
operator|<
name|iSessionDays
operator|.
name|length
argument_list|()
condition|;
name|day
operator|++
control|)
block|{
if|if
condition|(
name|iSessionDays
operator|.
name|get
argument_list|(
name|day
argument_list|)
condition|)
name|nrDays
index|[
name|dow
index|]
operator|++
expr_stmt|;
name|dow
operator|=
operator|(
name|dow
operator|+
literal|1
operator|)
operator|%
literal|7
expr_stmt|;
block|}
name|iNrWeeks
operator|=
literal|0.2
operator|*
operator|(
name|nrDays
index|[
name|Constants
operator|.
name|DAY_MON
index|]
operator|+
name|nrDays
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
operator|+
name|nrDays
index|[
name|Constants
operator|.
name|DAY_WED
index|]
operator|+
name|nrDays
index|[
name|Constants
operator|.
name|DAY_THU
index|]
operator|+
name|nrDays
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
operator|)
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
name|sGroupSizes
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|RoomAllocationGroup
argument_list|(
name|sGroupSizes
index|[
name|i
index|]
argument_list|,
name|sGroupSizes
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|RoomConstraint
name|rc
range|:
name|model
operator|.
name|getRoomConstraints
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|iRoomType
argument_list|,
name|rc
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
continue|continue;
for|for
control|(
name|Iterator
name|i
init|=
name|iGroups
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
block|{
name|RoomAllocationGroup
name|g
init|=
operator|(
name|RoomAllocationGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|g
operator|.
name|add
argument_list|(
name|rc
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Lecture
name|lecture
range|:
name|model
operator|.
name|variables
argument_list|()
control|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|iGroups
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
block|{
name|RoomAllocationGroup
name|g
init|=
operator|(
name|RoomAllocationGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|g
operator|.
name|add
argument_list|(
name|lecture
argument_list|,
name|assignment
operator|.
name|getValue
argument_list|(
name|lecture
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|Set
name|getGroups
parameter_list|()
block|{
return|return
name|iGroups
return|;
block|}
specifier|public
class|class
name|RoomAllocationGroup
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
name|int
name|iMinRoomSize
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iMaxRoomSize
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iNrRooms
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iNrRoomsThisSizeOrBigger
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|iSlotsUse
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|iSlotsMustUse
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|iSlotsMustUseThisSizeOrBigger
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|iSlotsCanUse
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iLecturesUse
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iLecturesMustUse
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iLecturesMustUseThisSizeOrBigger
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iLecturesCanUse
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iRealMinRoomSize
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iRealMaxRoomSize
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iLecturesShouldUse
init|=
literal|0
decl_stmt|;
specifier|private
name|double
name|iSlotsShouldUse
init|=
literal|0
decl_stmt|;
specifier|public
name|RoomAllocationGroup
parameter_list|(
name|int
name|minSize
parameter_list|,
name|int
name|maxSize
parameter_list|)
block|{
name|iMinRoomSize
operator|=
name|minSize
expr_stmt|;
name|iMaxRoomSize
operator|=
name|maxSize
expr_stmt|;
name|iRealMinRoomSize
operator|=
name|maxSize
expr_stmt|;
name|iRealMaxRoomSize
operator|=
name|minSize
expr_stmt|;
block|}
specifier|public
name|int
name|getMinRoomSize
parameter_list|()
block|{
return|return
name|iMinRoomSize
return|;
block|}
specifier|public
name|int
name|getMaxRoomSize
parameter_list|()
block|{
return|return
name|iMaxRoomSize
return|;
block|}
specifier|public
name|int
name|getActualMinRoomSize
parameter_list|()
block|{
return|return
name|iRealMinRoomSize
return|;
block|}
specifier|public
name|int
name|getActualMaxRoomSize
parameter_list|()
block|{
return|return
name|iRealMaxRoomSize
return|;
block|}
specifier|public
name|int
name|getNrRooms
parameter_list|()
block|{
return|return
name|iNrRooms
return|;
block|}
specifier|public
name|int
name|getNrRoomsThisSizeOrBigger
parameter_list|()
block|{
return|return
name|iNrRoomsThisSizeOrBigger
return|;
block|}
specifier|public
name|double
name|getSlotsUse
parameter_list|()
block|{
return|return
name|iSlotsUse
return|;
block|}
specifier|public
name|double
name|getSlotsCanUse
parameter_list|()
block|{
return|return
name|iSlotsCanUse
return|;
block|}
specifier|public
name|double
name|getSlotsMustUse
parameter_list|()
block|{
return|return
name|iSlotsMustUse
return|;
block|}
specifier|public
name|double
name|getSlotsMustUseThisSizeOrBigger
parameter_list|()
block|{
return|return
name|iSlotsMustUseThisSizeOrBigger
return|;
block|}
specifier|public
name|double
name|getSlotsShouldUse
parameter_list|()
block|{
return|return
name|iSlotsShouldUse
return|;
block|}
specifier|public
name|int
name|getLecturesUse
parameter_list|()
block|{
return|return
name|iLecturesUse
return|;
block|}
specifier|public
name|int
name|getLecturesCanUse
parameter_list|()
block|{
return|return
name|iLecturesCanUse
return|;
block|}
specifier|public
name|int
name|getLecturesMustUse
parameter_list|()
block|{
return|return
name|iLecturesMustUse
return|;
block|}
specifier|public
name|int
name|getLecturesMustUseThisSizeOrBigger
parameter_list|()
block|{
return|return
name|iLecturesMustUseThisSizeOrBigger
return|;
block|}
specifier|public
name|int
name|getLecturesShouldUse
parameter_list|()
block|{
return|return
name|iLecturesShouldUse
return|;
block|}
specifier|public
name|void
name|add
parameter_list|(
name|RoomConstraint
name|rc
parameter_list|)
block|{
if|if
condition|(
name|iMinRoomSize
operator|<=
name|rc
operator|.
name|getCapacity
argument_list|()
operator|&&
name|rc
operator|.
name|getCapacity
argument_list|()
operator|<
name|iMaxRoomSize
condition|)
block|{
name|iNrRooms
operator|++
expr_stmt|;
name|iRealMinRoomSize
operator|=
name|Math
operator|.
name|min
argument_list|(
name|iRealMinRoomSize
argument_list|,
name|rc
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|iRealMaxRoomSize
operator|=
name|Math
operator|.
name|max
argument_list|(
name|iRealMaxRoomSize
argument_list|,
name|rc
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iMinRoomSize
operator|<=
name|rc
operator|.
name|getCapacity
argument_list|()
condition|)
name|iNrRoomsThisSizeOrBigger
operator|++
expr_stmt|;
block|}
specifier|public
name|void
name|add
parameter_list|(
name|Lecture
name|lecture
parameter_list|,
name|Placement
name|placement
parameter_list|)
block|{
if|if
condition|(
name|lecture
operator|.
name|getNrRooms
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|boolean
name|skip
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|lecture
operator|.
name|canShareRoom
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|lecture
operator|.
name|canShareRoomConstraints
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
block|{
name|GroupConstraint
name|gc
init|=
operator|(
name|GroupConstraint
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Lecture
name|other
range|:
name|gc
operator|.
name|variables
argument_list|()
control|)
block|{
if|if
condition|(
name|other
operator|.
name|getClassId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|lecture
operator|.
name|getClassId
argument_list|()
argument_list|)
operator|<
literal|0
condition|)
name|skip
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|skip
condition|)
return|return;
name|skip
operator|=
literal|true
expr_stmt|;
name|boolean
name|canUse
init|=
literal|false
decl_stmt|,
name|mustUse
init|=
literal|true
decl_stmt|,
name|mustUseThisSizeOrBigger
init|=
literal|true
decl_stmt|;
for|for
control|(
name|RoomLocation
name|r
range|:
name|lecture
operator|.
name|roomLocations
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getRoomConstraint
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|PreferenceLevel
operator|.
name|sProhibited
operator|.
name|equals
argument_list|(
name|PreferenceLevel
operator|.
name|int2prolog
argument_list|(
name|r
operator|.
name|getPreference
argument_list|()
argument_list|)
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|iRoomType
argument_list|,
name|r
operator|.
name|getRoomConstraint
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|mustUse
operator|=
literal|false
expr_stmt|;
name|mustUseThisSizeOrBigger
operator|=
literal|false
expr_stmt|;
continue|continue;
block|}
name|skip
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|iMinRoomSize
operator|<=
name|r
operator|.
name|getRoomSize
argument_list|()
operator|&&
name|r
operator|.
name|getRoomSize
argument_list|()
operator|<
name|iMaxRoomSize
condition|)
name|canUse
operator|=
literal|true
expr_stmt|;
else|else
name|mustUse
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|getRoomSize
argument_list|()
operator|<
name|iMinRoomSize
condition|)
name|mustUseThisSizeOrBigger
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|skip
condition|)
return|return;
name|boolean
name|shouldUse
init|=
name|canUse
operator|&&
name|mustUseThisSizeOrBigger
decl_stmt|;
if|if
condition|(
name|canUse
condition|)
block|{
name|iSlotsCanUse
operator|+=
name|getSlotsAWeek
argument_list|(
name|lecture
operator|.
name|timeLocations
argument_list|()
argument_list|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
name|iLecturesCanUse
operator|+=
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|mustUse
condition|)
block|{
name|iSlotsMustUse
operator|+=
name|getSlotsAWeek
argument_list|(
name|lecture
operator|.
name|timeLocations
argument_list|()
argument_list|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
name|iLecturesMustUse
operator|+=
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|mustUseThisSizeOrBigger
condition|)
block|{
name|iSlotsMustUseThisSizeOrBigger
operator|+=
name|getSlotsAWeek
argument_list|(
name|lecture
operator|.
name|timeLocations
argument_list|()
argument_list|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
name|iLecturesMustUseThisSizeOrBigger
operator|+=
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|shouldUse
condition|)
block|{
name|iSlotsShouldUse
operator|+=
name|getSlotsAWeek
argument_list|(
name|lecture
operator|.
name|timeLocations
argument_list|()
argument_list|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
name|iLecturesShouldUse
operator|+=
name|lecture
operator|.
name|getNrRooms
argument_list|()
expr_stmt|;
block|}
name|int
name|use
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|placement
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|placement
operator|.
name|isMultiRoom
argument_list|()
condition|)
block|{
for|for
control|(
name|RoomLocation
name|r
range|:
name|placement
operator|.
name|getRoomLocations
argument_list|()
control|)
block|{
if|if
condition|(
name|r
operator|.
name|getRoomConstraint
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|iRoomType
argument_list|,
name|r
operator|.
name|getRoomConstraint
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|iMinRoomSize
operator|<=
name|r
operator|.
name|getRoomSize
argument_list|()
operator|&&
name|r
operator|.
name|getRoomSize
argument_list|()
operator|<
name|iMaxRoomSize
condition|)
name|use
operator|++
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|placement
operator|.
name|getRoomLocation
argument_list|()
operator|.
name|getRoomConstraint
argument_list|()
operator|!=
literal|null
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|iRoomType
argument_list|,
name|placement
operator|.
name|getRoomLocation
argument_list|()
operator|.
name|getRoomConstraint
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
operator|&&
name|iMinRoomSize
operator|<=
name|placement
operator|.
name|getRoomLocation
argument_list|()
operator|.
name|getRoomSize
argument_list|()
operator|&&
name|placement
operator|.
name|getRoomLocation
argument_list|()
operator|.
name|getRoomSize
argument_list|()
operator|<
name|iMaxRoomSize
condition|)
name|use
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|use
operator|>
literal|0
condition|)
block|{
name|TimeLocation
name|t
init|=
name|placement
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
name|iSlotsUse
operator|+=
name|getSlotsAWeek
argument_list|(
name|t
argument_list|)
operator|*
name|use
expr_stmt|;
name|iLecturesUse
operator|+=
name|use
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|double
name|getSlotsAWeek
parameter_list|(
name|Collection
argument_list|<
name|TimeLocation
argument_list|>
name|times
parameter_list|)
block|{
if|if
condition|(
name|times
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|0
return|;
name|double
name|totalHoursAWeek
init|=
literal|0
decl_stmt|;
for|for
control|(
name|TimeLocation
name|t
range|:
name|times
control|)
name|totalHoursAWeek
operator|+=
name|getSlotsAWeek
argument_list|(
name|t
argument_list|)
expr_stmt|;
return|return
operator|(
operator|(
name|double
operator|)
name|totalHoursAWeek
operator|)
operator|/
name|times
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|double
name|getSlotsAWeek
parameter_list|(
name|TimeLocation
name|t
parameter_list|)
block|{
return|return
name|getAverageDays
argument_list|(
name|t
argument_list|)
operator|*
name|t
operator|.
name|getNrSlotsPerMeeting
argument_list|()
return|;
block|}
specifier|public
name|double
name|getAverageDays
parameter_list|(
name|TimeLocation
name|t
parameter_list|)
block|{
name|int
name|nrDays
init|=
literal|0
decl_stmt|;
name|int
name|dow
init|=
name|iStartDayDayOfWeek
decl_stmt|;
for|for
control|(
name|int
name|day
init|=
name|iSessionDays
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
init|;
name|day
operator|<
name|iSessionDays
operator|.
name|length
argument_list|()
condition|;
name|day
operator|++
control|)
block|{
if|if
condition|(
name|iSessionDays
operator|.
name|get
argument_list|(
name|day
argument_list|)
operator|&&
name|t
operator|.
name|getWeekCode
argument_list|()
operator|.
name|get
argument_list|(
name|day
argument_list|)
operator|&&
operator|(
name|t
operator|.
name|getDayCode
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|dow
index|]
operator|)
operator|!=
literal|0
condition|)
name|nrDays
operator|++
expr_stmt|;
name|dow
operator|=
operator|(
name|dow
operator|+
literal|1
operator|)
operator|%
literal|7
expr_stmt|;
block|}
return|return
operator|(
operator|(
name|double
operator|)
name|nrDays
operator|)
operator|/
name|iNrWeeks
return|;
block|}
block|}
block|}
end_class

end_unit

