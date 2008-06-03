begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Location
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
name|webutil
operator|.
name|EventModel
import|;
end_import

begin_class
specifier|public
class|class
name|EventRoomAvailabilityForm
extends|extends
name|ActionForm
block|{
specifier|private
name|EventModel
name|iModel
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|String
name|iLocationType
decl_stmt|;
specifier|private
name|List
name|iLocations
decl_stmt|;
specifier|private
name|int
name|iStartTime
decl_stmt|;
specifier|private
name|int
name|iStopTime
decl_stmt|;
specifier|private
name|TreeSet
argument_list|<
name|Date
argument_list|>
name|iMeetingDates
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
empty_stmt|;
specifier|private
name|Date
name|iSomeDate
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
specifier|private
name|Long
name|iBuildingId
decl_stmt|;
specifier|private
name|String
name|iRoomNumber
decl_stmt|;
specifier|private
name|String
name|iMinCapacity
decl_stmt|;
specifier|private
name|String
name|iMaxCapacity
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iSessionId
operator|=
literal|null
expr_stmt|;
name|iLocations
operator|=
literal|null
expr_stmt|;
name|iSomeDate
operator|=
literal|null
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
name|iStartTime
operator|=
literal|30
expr_stmt|;
name|iStopTime
operator|=
literal|70
expr_stmt|;
name|iLocationType
operator|=
literal|null
expr_stmt|;
name|iMeetingDates
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iMinCapacity
operator|=
literal|null
expr_stmt|;
name|iMaxCapacity
operator|=
literal|null
expr_stmt|;
name|iLocations
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|iMeetingDates
operator|=
operator|(
name|TreeSet
argument_list|<
name|Date
argument_list|>
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.MeetingDates"
argument_list|)
expr_stmt|;
name|iSessionId
operator|=
operator|(
name|Long
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.SessionId"
argument_list|)
expr_stmt|;
name|iSomeDate
operator|=
name|iMeetingDates
operator|.
name|first
argument_list|()
expr_stmt|;
name|iStartTime
operator|=
operator|(
name|Integer
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.StartTime"
argument_list|)
expr_stmt|;
name|iStopTime
operator|=
operator|(
name|Integer
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.StopTime"
argument_list|)
expr_stmt|;
name|iLocationType
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.LocationType"
argument_list|)
expr_stmt|;
name|iMeetingDates
operator|=
operator|(
name|TreeSet
argument_list|<
name|Date
argument_list|>
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.MeetingDates"
argument_list|)
expr_stmt|;
name|iMinCapacity
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.MinCapacity"
argument_list|)
expr_stmt|;
name|iMaxCapacity
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.MaxCapacity"
argument_list|)
expr_stmt|;
name|iBuildingId
operator|=
operator|(
name|Long
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.BuildingId"
argument_list|)
expr_stmt|;
name|iRoomNumber
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"Event.RoomNumber"
argument_list|)
expr_stmt|;
name|iLocations
operator|=
name|getPossibleLocations
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
block|}
specifier|public
name|List
name|getPossibleLocations
parameter_list|()
block|{
name|Query
name|hibQuery
init|=
operator|new
name|LocationDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from Room r where r.building.uniqueId = :buildingId"
argument_list|)
decl_stmt|;
name|hibQuery
operator|.
name|setLong
argument_list|(
literal|"buildingId"
argument_list|,
name|iBuildingId
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|iBuildingId
argument_list|)
expr_stmt|;
return|return
name|hibQuery
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|TreeSet
name|getOverlappingMeetings
parameter_list|(
name|Date
name|meetingDate
parameter_list|,
name|int
name|startTime
parameter_list|,
name|int
name|stopTime
parameter_list|,
name|Location
name|location
parameter_list|)
block|{
name|Calendar
name|start
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|start
operator|.
name|setTime
argument_list|(
name|meetingDate
argument_list|)
expr_stmt|;
name|start
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
name|startTime
operator|/
literal|4
argument_list|)
expr_stmt|;
name|start
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
operator|(
name|startTime
operator|%
literal|4
operator|)
operator|*
literal|15
argument_list|)
expr_stmt|;
name|start
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|Calendar
name|stop
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|stop
operator|.
name|setTime
argument_list|(
name|meetingDate
argument_list|)
expr_stmt|;
name|stop
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
name|stopTime
operator|/
literal|4
argument_list|)
expr_stmt|;
name|stop
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
operator|(
name|stopTime
operator|%
literal|4
operator|)
operator|*
literal|15
argument_list|)
expr_stmt|;
name|stop
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|Query
name|hibQuery
init|=
operator|new
name|MeetingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"Select m from Meeting m where m.startTime<=:stopTime and m.stopTime>=:startTime and m.location.uniqueId=:locId"
argument_list|)
decl_stmt|;
name|hibQuery
operator|.
name|setDate
argument_list|(
literal|"startTime"
argument_list|,
name|start
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
name|hibQuery
operator|.
name|setDate
argument_list|(
literal|"stopTime"
argument_list|,
name|stop
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
name|hibQuery
operator|.
name|setLong
argument_list|(
literal|"locId"
argument_list|,
literal|223206l
argument_list|)
expr_stmt|;
comment|//		System.out.print(hibQuery.setCacheable(true).list());
return|return
operator|(
name|TreeSet
operator|)
name|hibQuery
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|List
name|getMeetings
parameter_list|()
block|{
comment|/*		List overlappingMeetings = null; 		for (Iterator i=iLocations.iterator(); i.hasNext();){ 			Room location = (Room) i.next();  			Query q = new MeetingDAO().getSession().createQuery("Select m from Meeting m where m.location.uniqueId = :locationId"); 			q.setLong("locationId", location.getUniqueId()); 			overlappingMeetings.addAll(q.setCacheable(true).list()); 		} 		return (TreeSet) overlappingMeetings; 	//	getOverlappingMeetings(iSomeDate, iStartTime, iStopTime); */
name|Meeting
name|m
decl_stmt|;
comment|//m.g
name|Query
name|q
init|=
operator|new
name|MeetingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"Select m from Meeting m where m.startPeriod<=:stopTime and m.stopPeriod>=:startTime"
argument_list|)
decl_stmt|;
name|q
operator|.
name|setInteger
argument_list|(
literal|"startTime"
argument_list|,
name|iStartTime
argument_list|)
expr_stmt|;
name|q
operator|.
name|setInteger
argument_list|(
literal|"stopTime"
argument_list|,
name|iStopTime
argument_list|)
expr_stmt|;
return|return
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|List
name|getLocations
parameter_list|()
block|{
return|return
name|iLocations
return|;
block|}
specifier|public
name|void
name|setLocations
parameter_list|(
name|List
name|locations
parameter_list|)
block|{
name|iLocations
operator|=
name|locations
expr_stmt|;
block|}
specifier|public
name|int
name|getStartTime
parameter_list|()
block|{
return|return
name|iStartTime
return|;
block|}
specifier|public
name|void
name|setStartTime
parameter_list|(
name|int
name|startTime
parameter_list|)
block|{
name|iStartTime
operator|=
name|startTime
expr_stmt|;
block|}
specifier|public
name|int
name|getStopTime
parameter_list|()
block|{
return|return
name|iStopTime
return|;
block|}
specifier|public
name|void
name|setStopTime
parameter_list|(
name|int
name|stopTime
parameter_list|)
block|{
name|iStopTime
operator|=
name|stopTime
expr_stmt|;
block|}
comment|// Query hibQuery = new EventDAO().getSession().createQuery(query);
comment|// List events = hibQuery.setCacheable(true).list();
block|}
end_class

end_unit

