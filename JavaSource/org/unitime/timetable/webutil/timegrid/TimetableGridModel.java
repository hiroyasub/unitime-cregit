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
name|webutil
operator|.
name|timegrid
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
specifier|abstract
class|class
name|TimetableGridModel
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
name|TimetableGridCell
index|[]
index|[]
index|[]
name|iData
decl_stmt|;
specifier|private
name|boolean
index|[]
index|[]
name|iAvailable
decl_stmt|;
specifier|private
name|String
index|[]
index|[]
name|iBackground
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|int
name|iSize
init|=
literal|0
decl_stmt|;
specifier|private
name|Long
name|iType
init|=
literal|null
decl_stmt|;
specifier|private
specifier|transient
name|boolean
index|[]
index|[]
index|[]
name|iRendered
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sResourceTypeRoom
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sResourceTypeInstructor
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sResourceTypeDepartment
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeNotAvailable
init|=
operator|-
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeNone
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeTimePref
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeRoomPref
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeStudentConf
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeInstructorBtbPref
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeDistributionConstPref
init|=
literal|5
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModePerturbations
init|=
literal|6
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModePerturbationPenalty
init|=
literal|7
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeHardConflicts
init|=
literal|8
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeDepartmentalBalancing
init|=
literal|9
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sBgModeTooBigRooms
init|=
literal|10
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sBgModes
init|=
operator|new
name|String
index|[]
block|{
literal|"None"
block|,
literal|"Time Preferences"
block|,
literal|"Room Preferences"
block|,
literal|"Student Conflicts"
block|,
literal|"Instructor Back-to-Back Preferences"
block|,
literal|"Distribution Preferences"
block|,
literal|"Perturbations"
block|,
literal|"Perturbation Penalty"
block|,
literal|"Hard Conflicts"
block|,
literal|"Departmental Balancing Penalty"
block|,
literal|"Too Big Rooms"
block|}
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sResourceTypes
init|=
operator|new
name|String
index|[]
block|{
literal|"Room"
block|,
literal|"Instructor"
block|,
literal|"Department"
block|}
decl_stmt|;
specifier|private
name|int
name|iResourceType
decl_stmt|;
specifier|private
name|int
name|iBgMode
decl_stmt|;
specifier|private
name|long
name|iResourceId
decl_stmt|;
specifier|public
name|TimetableGridModel
parameter_list|()
block|{
name|iData
operator|=
operator|new
name|TimetableGridCell
index|[
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
index|]
index|[
name|Constants
operator|.
name|SLOTS_PER_DAY
index|]
index|[]
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
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|;
name|j
operator|++
control|)
name|iData
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
literal|null
expr_stmt|;
name|iAvailable
operator|=
operator|new
name|boolean
index|[
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
index|]
index|[
name|Constants
operator|.
name|SLOTS_PER_DAY
index|]
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
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|;
name|j
operator|++
control|)
name|iAvailable
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
literal|true
expr_stmt|;
block|}
specifier|public
name|TimetableGridModel
parameter_list|(
name|int
name|resourceType
parameter_list|,
name|long
name|resourceId
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|iResourceType
operator|=
name|resourceType
expr_stmt|;
name|iResourceId
operator|=
name|resourceId
expr_stmt|;
block|}
specifier|public
name|int
name|getResourceType
parameter_list|()
block|{
return|return
name|iResourceType
return|;
block|}
specifier|public
name|long
name|getResourceId
parameter_list|()
block|{
return|return
name|iResourceId
return|;
block|}
specifier|public
specifier|static
name|String
index|[]
name|getBackgroundModes
parameter_list|()
block|{
return|return
name|sBgModes
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|void
name|setSize
parameter_list|(
name|int
name|size
parameter_list|)
block|{
name|iSize
operator|=
name|size
expr_stmt|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|Long
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|void
name|addCell
parameter_list|(
name|int
name|slot
parameter_list|,
name|TimetableGridCell
name|cell
parameter_list|)
block|{
name|addCell
argument_list|(
name|slot
operator|/
name|Constants
operator|.
name|SLOTS_PER_DAY
argument_list|,
name|slot
operator|%
name|Constants
operator|.
name|SLOTS_PER_DAY
argument_list|,
name|cell
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addCell
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|TimetableGridCell
name|cell
parameter_list|)
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|cell
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
name|idx
operator|=
name|Math
operator|.
name|max
argument_list|(
name|getIndex
argument_list|(
name|day
argument_list|,
name|slot
operator|+
name|i
argument_list|,
name|cell
argument_list|)
argument_list|,
name|idx
argument_list|)
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
name|cell
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|shift
argument_list|(
name|day
argument_list|,
name|slot
operator|+
name|i
argument_list|,
name|idx
argument_list|)
expr_stmt|;
name|setCell
argument_list|(
name|day
argument_list|,
name|slot
operator|+
name|i
argument_list|,
name|idx
argument_list|,
name|cell
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setAvailable
parameter_list|(
name|int
name|slot
parameter_list|,
name|boolean
name|available
parameter_list|)
block|{
name|setAvailable
argument_list|(
name|slot
operator|/
name|Constants
operator|.
name|SLOTS_PER_DAY
argument_list|,
name|slot
operator|%
name|Constants
operator|.
name|SLOTS_PER_DAY
argument_list|,
name|available
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setAvailable
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|boolean
name|available
parameter_list|)
block|{
name|iAvailable
index|[
name|day
index|]
index|[
name|slot
index|]
operator|=
name|available
expr_stmt|;
block|}
specifier|public
name|void
name|setBackground
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|String
name|background
parameter_list|)
block|{
if|if
condition|(
name|iBackground
operator|==
literal|null
condition|)
block|{
name|iBackground
operator|=
operator|new
name|String
index|[
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
index|]
index|[
name|Constants
operator|.
name|SLOTS_PER_DAY
index|]
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
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|;
name|j
operator|++
control|)
name|iBackground
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
literal|null
expr_stmt|;
block|}
name|iBackground
index|[
name|day
index|]
index|[
name|slot
index|]
operator|=
name|background
expr_stmt|;
block|}
specifier|public
name|String
name|getBackground
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|)
block|{
if|if
condition|(
name|iBackground
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|iBackground
index|[
name|day
index|]
index|[
name|slot
index|]
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|int
name|getSize
parameter_list|()
block|{
return|return
name|iSize
return|;
block|}
specifier|public
name|Long
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|shift
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
name|TimetableGridCell
name|cell
init|=
name|getCell
argument_list|(
name|day
argument_list|,
name|slot
argument_list|,
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|cell
operator|==
literal|null
condition|)
return|return;
for|for
control|(
name|int
name|s
init|=
name|cell
operator|.
name|getSlot
argument_list|()
init|;
name|s
operator|<
name|cell
operator|.
name|getSlot
argument_list|()
operator|+
name|cell
operator|.
name|getLength
argument_list|()
condition|;
name|s
operator|++
control|)
block|{
name|shift
argument_list|(
name|day
argument_list|,
name|s
argument_list|,
name|idx
operator|+
literal|1
argument_list|)
expr_stmt|;
name|setCell
argument_list|(
name|day
argument_list|,
name|s
argument_list|,
name|idx
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|setCell
argument_list|(
name|day
argument_list|,
name|s
argument_list|,
name|idx
operator|+
literal|1
argument_list|,
name|cell
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setCell
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|int
name|idx
parameter_list|,
name|TimetableGridCell
name|cell
parameter_list|)
block|{
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|==
literal|null
condition|)
block|{
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|=
operator|new
name|TimetableGridCell
index|[
name|idx
operator|+
literal|1
index|]
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
name|idx
condition|;
name|i
operator|++
control|)
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|i
index|]
operator|=
literal|null
expr_stmt|;
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|idx
index|]
operator|=
name|cell
expr_stmt|;
block|}
if|else if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|.
name|length
operator|<=
name|idx
condition|)
block|{
name|TimetableGridCell
index|[]
name|old
init|=
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
decl_stmt|;
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|=
operator|new
name|TimetableGridCell
index|[
name|idx
operator|+
literal|1
index|]
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
name|idx
condition|;
name|i
operator|++
control|)
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|i
index|]
operator|=
operator|(
name|i
operator|<
name|old
operator|.
name|length
condition|?
name|old
index|[
name|i
index|]
else|:
literal|null
operator|)
expr_stmt|;
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|idx
index|]
operator|=
name|cell
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|idx
index|]
operator|!=
literal|null
operator|&&
name|cell
operator|!=
literal|null
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"WARN: ("
operator|+
name|day
operator|+
literal|","
operator|+
name|slot
operator|+
literal|","
operator|+
name|idx
operator|+
literal|") already full with "
operator|+
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|idx
index|]
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|idx
index|]
operator|=
name|cell
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|getIndex
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|TimetableGridCell
name|cell
parameter_list|)
block|{
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|==
literal|null
condition|)
block|{
return|return
literal|0
return|;
block|}
else|else
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|i
index|]
operator|!=
literal|null
operator|&&
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|i
index|]
operator|.
name|compareTo
argument_list|(
name|cell
argument_list|)
operator|<=
literal|0
condition|)
name|idx
operator|=
name|i
operator|+
literal|1
expr_stmt|;
block|}
return|return
name|idx
return|;
block|}
block|}
specifier|public
name|int
name|nrCells
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|)
block|{
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|==
literal|null
condition|)
return|return
literal|0
return|;
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|i
index|]
operator|!=
literal|null
condition|)
name|ret
operator|++
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|TimetableGridCell
name|getCell
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|.
name|length
operator|<=
name|idx
condition|)
return|return
literal|null
return|;
return|return
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|idx
index|]
return|;
block|}
specifier|public
name|int
name|getMaxIdx
parameter_list|(
name|int
name|startDay
parameter_list|,
name|int
name|endDay
parameter_list|,
name|int
name|firstSlot
parameter_list|,
name|int
name|lastSlot
parameter_list|)
block|{
name|int
name|max
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|day
init|=
name|startDay
init|;
name|day
operator|<=
name|endDay
condition|;
name|day
operator|++
control|)
for|for
control|(
name|int
name|slot
init|=
name|firstSlot
init|;
name|slot
operator|<=
name|lastSlot
condition|;
name|slot
operator|++
control|)
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|!=
literal|null
condition|)
name|max
operator|=
name|Math
operator|.
name|max
argument_list|(
name|max
argument_list|,
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|.
name|length
operator|-
literal|1
argument_list|)
expr_stmt|;
return|return
name|max
return|;
block|}
specifier|public
name|int
name|getMaxIdxForDay
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|firstSlot
parameter_list|,
name|int
name|lastSlot
parameter_list|)
block|{
name|int
name|max
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|slot
init|=
name|firstSlot
init|;
name|slot
operator|<=
name|lastSlot
condition|;
name|slot
operator|++
control|)
if|if
condition|(
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|!=
literal|null
condition|)
name|max
operator|=
name|Math
operator|.
name|max
argument_list|(
name|max
argument_list|,
name|iData
index|[
name|day
index|]
index|[
name|slot
index|]
operator|.
name|length
operator|-
literal|1
argument_list|)
expr_stmt|;
return|return
name|max
return|;
block|}
specifier|public
name|boolean
name|isAvailable
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|)
block|{
return|return
name|iAvailable
index|[
name|day
index|]
index|[
name|slot
index|]
return|;
block|}
specifier|public
name|void
name|clearRendered
parameter_list|()
block|{
name|iRendered
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRendered
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
if|if
condition|(
name|iRendered
operator|==
literal|null
operator|||
name|iRendered
index|[
name|day
index|]
index|[
name|slot
index|]
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|iRendered
index|[
name|day
index|]
index|[
name|slot
index|]
operator|.
name|length
operator|<=
name|idx
condition|)
return|return
literal|false
return|;
return|return
name|iRendered
index|[
name|day
index|]
index|[
name|slot
index|]
index|[
name|idx
index|]
return|;
block|}
specifier|public
name|void
name|setRendered
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|int
name|idx
parameter_list|,
name|int
name|rowSpan
parameter_list|,
name|int
name|colSpan
parameter_list|)
block|{
if|if
condition|(
name|iRendered
operator|==
literal|null
condition|)
block|{
name|iRendered
operator|=
operator|new
name|boolean
index|[
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
index|]
index|[
name|Constants
operator|.
name|SLOTS_PER_DAY
index|]
index|[]
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
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|;
name|j
operator|++
control|)
name|iRendered
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
literal|null
expr_stmt|;
block|}
for|for
control|(
name|int
name|row
init|=
literal|0
init|;
name|row
operator|<
name|rowSpan
condition|;
name|row
operator|++
control|)
block|{
for|for
control|(
name|int
name|col
init|=
literal|0
init|;
name|col
operator|<
name|colSpan
condition|;
name|col
operator|++
control|)
block|{
if|if
condition|(
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
operator|==
literal|null
condition|)
block|{
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
operator|=
operator|new
name|boolean
index|[
name|idx
operator|+
name|rowSpan
index|]
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
name|idx
operator|+
name|rowSpan
condition|;
name|i
operator|++
control|)
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
index|[
name|i
index|]
operator|=
literal|false
expr_stmt|;
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
index|[
name|idx
operator|+
name|row
index|]
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
operator|.
name|length
operator|<=
name|idx
operator|+
name|row
condition|)
block|{
name|boolean
index|[]
name|old
init|=
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
decl_stmt|;
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
operator|=
operator|new
name|boolean
index|[
name|idx
operator|+
name|rowSpan
index|]
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
name|idx
operator|+
name|rowSpan
condition|;
name|i
operator|++
control|)
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
index|[
name|i
index|]
operator|=
operator|(
name|i
operator|<
name|old
operator|.
name|length
condition|?
name|old
index|[
name|i
index|]
else|:
literal|false
operator|)
expr_stmt|;
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
index|[
name|idx
operator|+
name|row
index|]
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
index|[
name|idx
operator|+
name|row
index|]
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"WARN: ("
operator|+
name|day
operator|+
literal|","
operator|+
operator|(
name|slot
operator|+
name|col
operator|)
operator|+
literal|","
operator|+
operator|(
name|idx
operator|+
name|row
operator|)
operator|+
literal|") already rendered"
argument_list|)
expr_stmt|;
name|iRendered
index|[
name|day
index|]
index|[
name|slot
operator|+
name|col
index|]
index|[
name|idx
operator|+
name|row
index|]
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|int
name|getDepth
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|int
name|idx
parameter_list|,
name|int
name|maxIdx
parameter_list|)
block|{
name|int
name|depth
init|=
literal|1
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|idx
operator|+
literal|1
init|;
name|i
operator|<=
name|maxIdx
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|getCell
argument_list|(
name|day
argument_list|,
name|slot
argument_list|,
name|i
argument_list|)
operator|!=
literal|null
operator|||
name|isRendered
argument_list|(
name|day
argument_list|,
name|slot
argument_list|,
name|i
argument_list|)
condition|)
break|break;
name|depth
operator|++
expr_stmt|;
block|}
return|return
name|depth
return|;
block|}
specifier|public
name|int
name|getDepth
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|int
name|idx
parameter_list|,
name|int
name|maxIdx
parameter_list|,
name|int
name|colSpan
parameter_list|)
block|{
name|int
name|depth
init|=
name|Integer
operator|.
name|MAX_VALUE
decl_stmt|;
for|for
control|(
name|int
name|col
init|=
literal|0
init|;
name|col
operator|<
name|colSpan
condition|;
name|col
operator|++
control|)
name|depth
operator|=
name|Math
operator|.
name|min
argument_list|(
name|depth
argument_list|,
name|getDepth
argument_list|(
name|day
argument_list|,
name|slot
operator|+
name|col
argument_list|,
name|idx
argument_list|,
name|maxIdx
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|depth
return|;
block|}
specifier|protected
name|boolean
name|isUselessFirst
parameter_list|(
name|int
name|d
parameter_list|,
name|int
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|-
literal|1
operator|<
literal|0
operator|||
name|s
operator|+
literal|6
operator|>=
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|)
return|return
literal|false
return|;
return|return
operator|(
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|-
literal|1
argument_list|)
operator|!=
literal|0
operator|&&
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|+
literal|0
argument_list|)
operator|==
literal|0
operator|&&
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|+
literal|1
argument_list|)
operator|==
literal|0
operator|&&
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|+
literal|2
argument_list|)
operator|==
literal|0
operator|&&
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|+
literal|3
argument_list|)
operator|==
literal|0
operator|&&
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|+
literal|4
argument_list|)
operator|==
literal|0
operator|&&
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|+
literal|5
argument_list|)
operator|==
literal|0
operator|&&
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
operator|+
literal|6
argument_list|)
operator|!=
literal|0
operator|)
return|;
block|}
specifier|protected
name|boolean
name|isUseless
parameter_list|(
name|int
name|d
parameter_list|,
name|int
name|s
parameter_list|)
block|{
return|return
name|isUselessFirst
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
operator|||
name|isUselessFirst
argument_list|(
name|d
argument_list|,
name|s
operator|-
literal|1
argument_list|)
operator|||
name|isUselessFirst
argument_list|(
name|d
argument_list|,
name|s
operator|-
literal|2
argument_list|)
operator|||
name|isUselessFirst
argument_list|(
name|d
argument_list|,
name|s
operator|-
literal|3
argument_list|)
operator|||
name|isUselessFirst
argument_list|(
name|d
argument_list|,
name|s
operator|-
literal|4
argument_list|)
operator|||
name|isUselessFirst
argument_list|(
name|d
argument_list|,
name|s
operator|-
literal|5
argument_list|)
return|;
block|}
specifier|protected
name|void
name|initBgModeUselessSlots
parameter_list|()
block|{
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|d
operator|++
control|)
block|{
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|;
name|s
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|isAvailable
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
condition|)
continue|continue;
name|int
name|pref
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|nrCells
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
operator|==
literal|0
condition|)
block|{
if|if
condition|(
name|isUseless
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
condition|)
name|pref
operator|=
literal|4
expr_stmt|;
switch|switch
condition|(
name|d
condition|)
block|{
case|case
literal|0
case|:
if|if
condition|(
name|nrCells
argument_list|(
literal|2
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
operator|&&
name|nrCells
argument_list|(
literal|4
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
condition|)
name|pref
operator|++
expr_stmt|;
break|break;
case|case
literal|1
case|:
if|if
condition|(
name|nrCells
argument_list|(
literal|3
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
condition|)
name|pref
operator|++
expr_stmt|;
break|break;
case|case
literal|2
case|:
if|if
condition|(
name|nrCells
argument_list|(
literal|0
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
operator|&&
name|nrCells
argument_list|(
literal|4
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
condition|)
name|pref
operator|++
expr_stmt|;
break|break;
case|case
literal|3
case|:
if|if
condition|(
name|nrCells
argument_list|(
literal|1
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
condition|)
name|pref
operator|++
expr_stmt|;
break|break;
case|case
literal|4
case|:
if|if
condition|(
name|nrCells
argument_list|(
literal|0
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
operator|&&
name|nrCells
argument_list|(
literal|2
argument_list|,
name|s
argument_list|)
operator|!=
literal|0
condition|)
name|pref
operator|++
expr_stmt|;
break|break;
block|}
block|}
name|String
name|background
init|=
name|TimetableGridCell
operator|.
name|pref2color
argument_list|(
name|PreferenceLevel
operator|.
name|sNeutral
argument_list|)
decl_stmt|;
if|if
condition|(
name|pref
operator|>
literal|4
condition|)
name|background
operator|=
name|TimetableGridCell
operator|.
name|pref2color
argument_list|(
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
expr_stmt|;
if|else if
condition|(
name|pref
operator|==
literal|4
condition|)
name|background
operator|=
name|TimetableGridCell
operator|.
name|pref2color
argument_list|(
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
argument_list|)
expr_stmt|;
if|else if
condition|(
name|pref
operator|>
literal|0
condition|)
name|background
operator|=
name|TimetableGridCell
operator|.
name|pref2color
argument_list|(
name|PreferenceLevel
operator|.
name|sDiscouraged
argument_list|)
expr_stmt|;
name|setBackground
argument_list|(
name|d
argument_list|,
name|s
argument_list|,
name|background
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

