begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Enumeration
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
name|PreferenceLevel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|int
name|iStartDay
decl_stmt|,
name|iEndDay
decl_stmt|,
name|iNrWeeks
decl_stmt|;
specifier|private
name|Integer
name|iRoomType
init|=
literal|null
decl_stmt|;
specifier|public
name|RoomReport
parameter_list|(
name|TimetableModel
name|model
parameter_list|,
name|int
name|startDay
parameter_list|,
name|int
name|endDay
parameter_list|,
name|int
name|nrWeeks
parameter_list|,
name|Integer
name|roomType
parameter_list|)
block|{
name|iStartDay
operator|=
name|startDay
expr_stmt|;
name|iEndDay
operator|=
name|endDay
expr_stmt|;
name|iNrWeeks
operator|=
name|nrWeeks
expr_stmt|;
name|iRoomType
operator|=
name|roomType
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
name|Enumeration
name|e
init|=
name|model
operator|.
name|getRoomConstraints
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|RoomConstraint
name|rc
init|=
operator|(
name|RoomConstraint
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|iRoomType
operator|!=
literal|null
operator|&&
operator|!
name|iRoomType
operator|.
name|equals
argument_list|(
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
name|Enumeration
name|e
init|=
name|model
operator|.
name|variables
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Lecture
name|lecture
init|=
operator|(
name|Lecture
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
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
name|Enumeration
name|e
init|=
name|gc
operator|.
name|variables
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Lecture
name|other
init|=
operator|(
name|Lecture
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
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
name|Enumeration
name|e
init|=
name|lecture
operator|.
name|roomLocations
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|RoomLocation
name|r
init|=
operator|(
name|RoomLocation
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
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
name|iRoomType
operator|!=
literal|null
operator|&&
operator|!
name|iRoomType
operator|.
name|equals
argument_list|(
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
name|TimeLocation
name|t
init|=
operator|(
name|TimeLocation
operator|)
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|.
name|firstElement
argument_list|()
decl_stmt|;
name|iSlotsCanUse
operator|+=
operator|(
operator|(
operator|(
name|double
operator|)
name|t
operator|.
name|getNrWeeks
argument_list|(
name|iStartDay
argument_list|,
name|iEndDay
argument_list|)
operator|)
operator|/
name|iNrWeeks
operator|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
operator|*
name|t
operator|.
name|getNrMeetings
argument_list|()
operator|*
name|t
operator|.
name|getNrSlotsPerMeeting
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
name|TimeLocation
name|t
init|=
operator|(
name|TimeLocation
operator|)
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|.
name|firstElement
argument_list|()
decl_stmt|;
name|iSlotsMustUse
operator|+=
operator|(
operator|(
operator|(
name|double
operator|)
name|t
operator|.
name|getNrWeeks
argument_list|(
name|iStartDay
argument_list|,
name|iEndDay
argument_list|)
operator|)
operator|/
name|iNrWeeks
operator|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
operator|*
name|t
operator|.
name|getNrMeetings
argument_list|()
operator|*
name|t
operator|.
name|getNrSlotsPerMeeting
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
name|TimeLocation
name|t
init|=
operator|(
name|TimeLocation
operator|)
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|.
name|firstElement
argument_list|()
decl_stmt|;
name|iSlotsMustUseThisSizeOrBigger
operator|+=
operator|(
operator|(
operator|(
name|double
operator|)
name|t
operator|.
name|getNrWeeks
argument_list|(
name|iStartDay
argument_list|,
name|iEndDay
argument_list|)
operator|)
operator|/
name|iNrWeeks
operator|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
operator|*
name|t
operator|.
name|getNrMeetings
argument_list|()
operator|*
name|t
operator|.
name|getNrSlotsPerMeeting
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
name|TimeLocation
name|t
init|=
operator|(
name|TimeLocation
operator|)
name|lecture
operator|.
name|timeLocations
argument_list|()
operator|.
name|firstElement
argument_list|()
decl_stmt|;
name|iSlotsShouldUse
operator|+=
operator|(
operator|(
operator|(
name|double
operator|)
name|t
operator|.
name|getNrWeeks
argument_list|(
name|iStartDay
argument_list|,
name|iEndDay
argument_list|)
operator|)
operator|/
name|iNrWeeks
operator|)
operator|*
name|lecture
operator|.
name|getNrRooms
argument_list|()
operator|*
name|t
operator|.
name|getNrMeetings
argument_list|()
operator|*
name|t
operator|.
name|getNrSlotsPerMeeting
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
name|lecture
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Placement
name|placement
init|=
operator|(
name|Placement
operator|)
name|lecture
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
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
name|Enumeration
name|e
init|=
name|placement
operator|.
name|getRoomLocations
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|RoomLocation
name|r
init|=
operator|(
name|RoomLocation
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
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
name|iRoomType
operator|!=
literal|null
operator|&&
operator|!
name|iRoomType
operator|.
name|equals
argument_list|(
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
operator|(
name|iRoomType
operator|==
literal|null
operator|||
name|iRoomType
operator|.
name|equals
argument_list|(
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
operator|)
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
operator|(
operator|(
operator|(
name|double
operator|)
name|t
operator|.
name|getNrWeeks
argument_list|(
name|iStartDay
argument_list|,
name|iEndDay
argument_list|)
operator|)
operator|/
name|iNrWeeks
operator|)
operator|*
name|use
operator|*
name|t
operator|.
name|getNrMeetings
argument_list|()
operator|*
name|t
operator|.
name|getNrSlotsPerMeeting
argument_list|()
expr_stmt|;
name|iLecturesUse
operator|+=
name|use
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

