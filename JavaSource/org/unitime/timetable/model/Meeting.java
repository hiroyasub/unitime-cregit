begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2008, UniTime LLC  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|java
operator|.
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|BaseMeeting
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
name|dao
operator|.
name|LocationDAO
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
name|dao
operator|.
name|MeetingDAO
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
name|dao
operator|.
name|RoomDAO
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

begin_class
specifier|public
class|class
name|Meeting
extends|extends
name|BaseMeeting
implements|implements
name|Comparable
argument_list|<
name|Meeting
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
specifier|private
name|Location
name|location
init|=
literal|null
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Meeting
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Meeting
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|Meeting
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Event
name|event
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Date
name|meetingDate
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|startPeriod
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|stopPeriod
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|classCanOverride
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|event
argument_list|,
name|meetingDate
argument_list|,
name|startPeriod
argument_list|,
name|stopPeriod
argument_list|,
name|classCanOverride
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
annotation|@
name|Override
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|Meeting
name|newMeeting
init|=
operator|new
name|Meeting
argument_list|()
decl_stmt|;
name|newMeeting
operator|.
name|setClassCanOverride
argument_list|(
name|isClassCanOverride
argument_list|()
argument_list|)
expr_stmt|;
name|newMeeting
operator|.
name|setLocationPermanentId
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|)
expr_stmt|;
name|newMeeting
operator|.
name|setMeetingDate
argument_list|(
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|newMeeting
operator|.
name|setStartOffset
argument_list|(
name|getStartOffset
argument_list|()
argument_list|)
expr_stmt|;
name|newMeeting
operator|.
name|setStartPeriod
argument_list|(
name|getStartPeriod
argument_list|()
argument_list|)
expr_stmt|;
name|newMeeting
operator|.
name|setStopOffset
argument_list|(
name|getStopOffset
argument_list|()
argument_list|)
expr_stmt|;
name|newMeeting
operator|.
name|setStopPeriod
argument_list|(
name|getStopPeriod
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|newMeeting
operator|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Meeting
name|other
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getMeetingDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getStartPeriod
argument_list|()
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getStartPeriod
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getRoomLabel
argument_list|()
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getRoomLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Location
name|getLocation
parameter_list|()
block|{
if|if
condition|(
name|location
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|location
operator|)
return|;
block|}
if|if
condition|(
name|getLocationPermanentId
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
if|if
condition|(
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|Session
name|session
init|=
name|getEvent
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
block|{
name|location
operator|=
operator|(
name|Location
operator|)
name|RoomDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from Room r where r.permanentId = :permId and r.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"permId"
argument_list|,
name|getLocationPermanentId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
expr_stmt|;
if|if
condition|(
name|location
operator|==
literal|null
condition|)
name|location
operator|=
operator|(
name|Location
operator|)
name|RoomDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from NonUniversityLocation r where r.permanentId = :permId and r.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"permId"
argument_list|,
name|getLocationPermanentId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
expr_stmt|;
return|return
name|location
return|;
block|}
name|long
name|distance
init|=
operator|-
literal|1
decl_stmt|;
name|List
name|locations
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from Location where permanentId = :permId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"permId"
argument_list|,
name|getLocationPermanentId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|locations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|it
init|=
name|locations
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|loc
init|=
operator|(
name|Location
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|long
name|dist
init|=
name|loc
operator|.
name|getSession
argument_list|()
operator|.
name|getDistance
argument_list|(
name|getMeetingDate
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|==
literal|null
operator|||
name|distance
operator|>
name|dist
condition|)
block|{
name|location
operator|=
name|loc
expr_stmt|;
name|distance
operator|=
name|dist
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|location
operator|)
return|;
block|}
specifier|public
name|List
argument_list|<
name|?
argument_list|>
name|getTimeRoomOverlaps
parameter_list|()
block|{
return|return
operator|(
name|MeetingDAO
operator|.
name|getInstance
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from Meeting m where m.meetingDate=:meetingDate and m.startPeriod< :stopPeriod and m.stopPeriod> :startPeriod and m.locationPermanentId = :locPermId and m.uniqueId != :uniqueId"
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"meetingDate"
argument_list|,
name|getMeetingDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"stopPeriod"
argument_list|,
name|getStopPeriod
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startPeriod"
argument_list|,
name|getStartPeriod
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"locPermId"
argument_list|,
name|getLocationPermanentId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|this
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|hasTimeRoomOverlaps
parameter_list|()
block|{
name|Long
name|count
init|=
operator|(
name|Long
operator|)
name|MeetingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(m) from Meeting m where m.meetingDate=:meetingDate and m.startPeriod< :stopPeriod and m.stopPeriod> :startPeriod and m.locationPermanentId = :locPermId and m.uniqueId != :uniqueId"
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"meetingDate"
argument_list|,
name|getMeetingDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"stopPeriod"
argument_list|,
name|getStopPeriod
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startPeriod"
argument_list|,
name|getStartPeriod
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"locPermId"
argument_list|,
name|getLocationPermanentId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|this
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
return|return
operator|(
name|count
operator|>
literal|0
operator|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|DateFormat
operator|.
name|getDateInstance
argument_list|(
name|DateFormat
operator|.
name|SHORT
argument_list|)
operator|.
name|format
argument_list|(
name|getMeetingDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|startTime
argument_list|()
operator|+
literal|" - "
operator|+
name|stopTime
argument_list|()
operator|+
operator|(
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|", "
operator|+
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
operator|)
return|;
block|}
specifier|public
name|String
name|getTimeLabel
parameter_list|()
block|{
return|return
name|dateStr
argument_list|()
operator|+
literal|" "
operator|+
name|startTime
argument_list|()
operator|+
literal|" - "
operator|+
name|stopTime
argument_list|()
return|;
block|}
specifier|public
name|String
name|getRoomLabel
parameter_list|()
block|{
return|return
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
return|;
block|}
specifier|private
name|String
name|periodToTime
parameter_list|(
name|Integer
name|slot
parameter_list|,
name|Integer
name|offset
parameter_list|)
block|{
if|if
condition|(
name|slot
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|""
operator|)
return|;
block|}
name|int
name|min
init|=
name|slot
operator|.
name|intValue
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
if|if
condition|(
name|offset
operator|!=
literal|null
condition|)
name|min
operator|+=
name|offset
expr_stmt|;
name|int
name|hours
init|=
operator|(
name|min
operator|/
literal|60
operator|)
decl_stmt|;
name|int
name|minutes
init|=
name|min
operator|%
literal|60
decl_stmt|;
name|String
name|amPm
init|=
literal|"a"
decl_stmt|;
if|if
condition|(
name|hours
operator|>=
literal|12
condition|)
block|{
name|amPm
operator|=
literal|"p"
expr_stmt|;
name|hours
operator|-=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|hours
operator|==
literal|0
condition|)
block|{
name|hours
operator|=
literal|12
expr_stmt|;
block|}
return|return
operator|(
name|hours
operator|+
literal|":"
operator|+
operator|(
name|minutes
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|minutes
operator|+
name|amPm
operator|)
return|;
block|}
specifier|public
name|String
name|startTime
parameter_list|()
block|{
return|return
operator|(
name|periodToTime
argument_list|(
name|getStartPeriod
argument_list|()
argument_list|,
name|getStartOffset
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|stopTime
parameter_list|()
block|{
return|return
operator|(
name|periodToTime
argument_list|(
name|getStopPeriod
argument_list|()
argument_list|,
name|getStopOffset
argument_list|()
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|dateStr
parameter_list|()
block|{
return|return
operator|new
name|SimpleDateFormat
argument_list|(
literal|"EEE MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|getMeetingDate
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Date
name|getStartTime
parameter_list|()
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|min
init|=
operator|(
name|getStartPeriod
argument_list|()
operator|.
name|intValue
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|+
operator|(
name|getStartOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getStartOffset
argument_list|()
operator|)
decl_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
name|min
operator|/
literal|60
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
name|min
operator|%
literal|60
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|public
name|Date
name|getStopTime
parameter_list|()
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|min
init|=
operator|(
name|getStopPeriod
argument_list|()
operator|.
name|intValue
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|+
operator|(
name|getStopOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getStopOffset
argument_list|()
operator|)
decl_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
name|min
operator|/
literal|60
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
name|min
operator|%
literal|60
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|public
name|int
name|getDayOfWeek
parameter_list|()
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isApproved
parameter_list|()
block|{
return|return
name|getApprovedDate
argument_list|()
operator|!=
literal|null
return|;
block|}
specifier|public
name|boolean
name|overlaps
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
if|if
condition|(
operator|!
name|getMeetingDate
argument_list|()
operator|.
name|equals
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|getStartPeriod
argument_list|()
operator|<
name|meeting
operator|.
name|getStopPeriod
argument_list|()
operator|&&
name|meeting
operator|.
name|getStartPeriod
argument_list|()
operator|<
name|getStopPeriod
argument_list|()
return|;
block|}
block|}
end_class

end_unit

