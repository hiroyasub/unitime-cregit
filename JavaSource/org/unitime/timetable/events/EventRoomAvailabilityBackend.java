begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|events
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Query
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|EventInterface
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
name|EventInterface
operator|.
name|MeetingConglictInterface
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
name|EventInterface
operator|.
name|MeetingInterface
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
name|EventInterface
operator|.
name|EventRoomAvailabilityRpcRequest
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
name|EventInterface
operator|.
name|EventRoomAvailabilityRpcResponse
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
name|Meeting
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
name|Session
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
name|EventDAO
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
name|SessionDAO
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
name|security
operator|.
name|SessionContext
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
name|CalendarUtils
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
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.gwt.shared.EventInterface$EventRoomAvailabilityRpcRequest"
argument_list|)
specifier|public
class|class
name|EventRoomAvailabilityBackend
extends|extends
name|EventAction
argument_list|<
name|EventRoomAvailabilityRpcRequest
argument_list|,
name|EventRoomAvailabilityRpcResponse
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|EventRoomAvailabilityRpcResponse
name|execute
parameter_list|(
name|EventRoomAvailabilityRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|EventRights
name|rights
parameter_list|)
block|{
name|EventRoomAvailabilityRpcResponse
name|response
init|=
operator|new
name|EventRoomAvailabilityRpcResponse
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|hasDates
argument_list|()
operator|&&
name|request
operator|.
name|hasLocations
argument_list|()
condition|)
block|{
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|request
operator|.
name|getLocations
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|idx
operator|+=
literal|1000
control|)
block|{
name|String
name|dates
init|=
literal|""
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
name|request
operator|.
name|getDates
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|dates
operator|+=
operator|(
name|dates
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
literal|":d"
operator|+
name|i
expr_stmt|;
name|String
name|locations
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|idx
init|;
name|i
operator|+
name|idx
operator|<
name|request
operator|.
name|getLocations
argument_list|()
operator|.
name|size
argument_list|()
operator|&&
name|i
operator|<
literal|1000
condition|;
name|i
operator|++
control|)
name|locations
operator|+=
operator|(
name|locations
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
literal|":l"
operator|+
name|i
expr_stmt|;
name|Query
name|query
init|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select m from Meeting m "
operator|+
literal|"where m.startPeriod<:stopTime and m.stopPeriod>:startTime and "
operator|+
literal|"m.locationPermanentId in ("
operator|+
name|locations
operator|+
literal|") and m.meetingDate in ("
operator|+
name|dates
operator|+
literal|")"
argument_list|)
decl_stmt|;
name|query
operator|.
name|setInteger
argument_list|(
literal|"startTime"
argument_list|,
name|request
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|query
operator|.
name|setInteger
argument_list|(
literal|"stopTime"
argument_list|,
name|request
operator|.
name|getEndSlot
argument_list|()
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
name|request
operator|.
name|getDates
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Date
name|date
init|=
name|CalendarUtils
operator|.
name|dateOfYear2date
argument_list|(
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|,
name|request
operator|.
name|getDates
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
decl_stmt|;
name|query
operator|.
name|setDate
argument_list|(
literal|"d"
operator|+
name|i
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
name|idx
init|;
name|i
operator|+
name|idx
operator|<
name|request
operator|.
name|getLocations
argument_list|()
operator|.
name|size
argument_list|()
operator|&&
name|i
operator|<
literal|1000
condition|;
name|i
operator|++
control|)
name|query
operator|.
name|setLong
argument_list|(
literal|"l"
operator|+
name|i
argument_list|,
name|request
operator|.
name|getLocations
argument_list|()
operator|.
name|get
argument_list|(
name|idx
operator|+
name|i
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Meeting
name|m
range|:
operator|(
name|List
argument_list|<
name|Meeting
argument_list|>
operator|)
name|query
operator|.
name|list
argument_list|()
control|)
block|{
name|MeetingConglictInterface
name|conflict
init|=
operator|new
name|MeetingConglictInterface
argument_list|()
decl_stmt|;
name|conflict
operator|.
name|setEventId
argument_list|(
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setName
argument_list|(
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventName
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setType
argument_list|(
name|EventInterface
operator|.
name|EventType
operator|.
name|values
argument_list|()
index|[
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setId
argument_list|(
name|m
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setMeetingDate
argument_list|(
name|m
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setDayOfYear
argument_list|(
name|CalendarUtils
operator|.
name|date2dayOfYear
argument_list|(
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|,
name|m
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartOffset
argument_list|(
name|m
operator|.
name|getStartOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|m
operator|.
name|getStartOffset
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndOffset
argument_list|(
name|m
operator|.
name|getStopOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|m
operator|.
name|getStopOffset
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartSlot
argument_list|(
name|m
operator|.
name|getStartPeriod
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndSlot
argument_list|(
name|m
operator|.
name|getStopPeriod
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|isApproved
argument_list|()
condition|)
name|conflict
operator|.
name|setApprovalDate
argument_list|(
name|m
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addOverlap
argument_list|(
name|CalendarUtils
operator|.
name|date2dayOfYear
argument_list|(
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|,
name|m
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
argument_list|,
name|m
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|,
name|conflict
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|request
operator|.
name|hasMeetings
argument_list|()
condition|)
block|{
name|response
operator|.
name|setMeetings
argument_list|(
name|request
operator|.
name|getMeetings
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|MeetingInterface
name|meeting
range|:
name|response
operator|.
name|getMeetings
argument_list|()
control|)
block|{
if|if
condition|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|)
block|{
name|meeting
operator|.
name|setMeetingDate
argument_list|(
name|CalendarUtils
operator|.
name|dateOfYear2date
argument_list|(
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|,
name|meeting
operator|.
name|getDayOfYear
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setDayOfWeek
argument_list|(
name|Constants
operator|.
name|getDayOfWeek
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|rights
operator|.
name|isPastOrOutside
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
condition|)
block|{
name|MeetingConglictInterface
name|conflict
init|=
operator|new
name|MeetingConglictInterface
argument_list|()
decl_stmt|;
name|conflict
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|conflictPastOrOutside
argument_list|(
name|session
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setType
argument_list|(
name|EventInterface
operator|.
name|EventType
operator|.
name|Unavailabile
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setMeetingDate
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setDayOfYear
argument_list|(
name|meeting
operator|.
name|getDayOfYear
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartOffset
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndOffset
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartSlot
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndSlot
argument_list|(
literal|288
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setPast
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|addConflict
argument_list|(
name|conflict
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|meeting
operator|.
name|hasLocation
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|!
name|rights
operator|.
name|canCreate
argument_list|(
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|MeetingConglictInterface
name|conflict
init|=
operator|new
name|MeetingConglictInterface
argument_list|()
decl_stmt|;
name|conflict
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|conflictNotEventRoom
argument_list|(
name|meeting
operator|.
name|getLocationName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setType
argument_list|(
name|EventInterface
operator|.
name|EventType
operator|.
name|Unavailabile
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setMeetingDate
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setDayOfYear
argument_list|(
name|meeting
operator|.
name|getDayOfYear
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartOffset
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndOffset
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartSlot
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndSlot
argument_list|(
literal|288
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|addConflict
argument_list|(
name|conflict
argument_list|)
expr_stmt|;
block|}
name|meeting
operator|.
name|setEndOffset
argument_list|(
operator|-
literal|10
argument_list|)
expr_stmt|;
for|for
control|(
name|Meeting
name|m
range|:
operator|(
name|List
argument_list|<
name|Meeting
argument_list|>
operator|)
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select m from Meeting m, Location l "
operator|+
literal|"where m.startPeriod< :stopTime and m.stopPeriod> :startTime and "
operator|+
literal|"m.locationPermanentId = l.permanentId and l.uniqueId = :locationdId and m.meetingDate = :meetingDate and m.uniqueId != :meetingId"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startTime"
argument_list|,
name|meeting
operator|.
name|getStartSlot
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"stopTime"
argument_list|,
name|meeting
operator|.
name|getEndSlot
argument_list|()
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"meetingDate"
argument_list|,
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"locationdId"
argument_list|,
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"meetingId"
argument_list|,
name|meeting
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|meeting
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|MeetingConglictInterface
name|conflict
init|=
operator|new
name|MeetingConglictInterface
argument_list|()
decl_stmt|;
name|conflict
operator|.
name|setEventId
argument_list|(
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setName
argument_list|(
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventName
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setType
argument_list|(
name|EventInterface
operator|.
name|EventType
operator|.
name|values
argument_list|()
index|[
name|m
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setId
argument_list|(
name|m
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setMeetingDate
argument_list|(
name|m
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setDayOfYear
argument_list|(
name|meeting
operator|.
name|getDayOfYear
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartSlot
argument_list|(
name|m
operator|.
name|getStartPeriod
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndSlot
argument_list|(
name|m
operator|.
name|getStopPeriod
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setStartOffset
argument_list|(
name|m
operator|.
name|getStartOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|m
operator|.
name|getStartOffset
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setEndOffset
argument_list|(
name|m
operator|.
name|getStopOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|m
operator|.
name|getStopOffset
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|isApproved
argument_list|()
condition|)
name|conflict
operator|.
name|setApprovalDate
argument_list|(
name|m
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|addConflict
argument_list|(
name|conflict
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

