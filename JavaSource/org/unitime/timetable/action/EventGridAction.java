begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|HttpServletResponse
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
name|Action
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
name|ActionForward
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|ActionMessages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|Web
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
name|ApplicationProperties
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
name|form
operator|.
name|EventGridForm
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
name|form
operator|.
name|EventRoomAvailabilityForm
operator|.
name|DateLocation
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
name|Building
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
name|Event
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
name|RoomType
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
name|TimetableManager
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
name|BuildingDAO
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
name|util
operator|.
name|Constants
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
name|timegrid
operator|.
name|PdfEventGridTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|EventGridAction
extends|extends
name|Action
block|{
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|EventGridForm
name|myForm
init|=
operator|(
name|EventGridForm
operator|)
name|form
decl_stmt|;
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
name|String
name|op
init|=
name|myForm
operator|.
name|getOp
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|op
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
operator|||
name|op
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|TimetableManager
name|mgr
init|=
operator|(
name|user
operator|==
literal|null
condition|?
literal|null
else|:
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|mgr
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|Collection
argument_list|<
name|RoomType
argument_list|>
name|allRoomTypes
init|=
name|myForm
operator|.
name|getAllRoomTypes
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|RoomType
argument_list|>
name|defaultRoomTypes
init|=
name|mgr
operator|.
name|findDefaultEventManagerRoomTimesFor
argument_list|(
name|user
operator|.
name|getRole
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|Long
argument_list|>
name|orderedTypeList
init|=
operator|new
name|Vector
argument_list|(
name|allRoomTypes
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|RoomType
name|displayedRoomType
range|:
name|allRoomTypes
control|)
block|{
for|for
control|(
name|RoomType
name|rt
range|:
name|defaultRoomTypes
control|)
block|{
if|if
condition|(
name|displayedRoomType
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|orderedTypeList
operator|.
name|add
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|myForm
operator|.
name|setRoomTypes
argument_list|(
operator|new
name|Long
index|[
name|orderedTypeList
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Long
name|l
range|:
name|orderedTypeList
control|)
block|{
name|myForm
operator|.
name|getRoomTypes
argument_list|()
index|[
name|i
index|]
operator|=
name|l
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
literal|"Show Availability"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|loadDates
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Clear"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|Long
name|sessionId
init|=
name|myForm
operator|.
name|getSessionId
argument_list|()
decl_stmt|;
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setSessionId
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|TimetableManager
name|mgr
init|=
operator|(
name|user
operator|==
literal|null
condition|?
literal|null
else|:
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|mgr
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|Collection
argument_list|<
name|RoomType
argument_list|>
name|allRoomTypes
init|=
name|myForm
operator|.
name|getAllRoomTypes
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|RoomType
argument_list|>
name|defaultRoomTypes
init|=
name|mgr
operator|.
name|findDefaultEventManagerRoomTimesFor
argument_list|(
name|user
operator|.
name|getRole
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|Long
argument_list|>
name|orderedTypeList
init|=
operator|new
name|Vector
argument_list|(
name|allRoomTypes
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|RoomType
name|displayedRoomType
range|:
name|allRoomTypes
control|)
block|{
for|for
control|(
name|RoomType
name|rt
range|:
name|defaultRoomTypes
control|)
block|{
if|if
condition|(
name|displayedRoomType
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|orderedTypeList
operator|.
name|add
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|myForm
operator|.
name|setRoomTypes
argument_list|(
operator|new
name|Long
index|[
name|orderedTypeList
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Long
name|l
range|:
name|orderedTypeList
control|)
block|{
name|myForm
operator|.
name|getRoomTypes
argument_list|()
index|[
name|i
index|]
operator|=
name|l
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"SessionChanged"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getBuildingId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Building
name|b
init|=
name|BuildingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getBuildingId
argument_list|()
argument_list|)
decl_stmt|;
name|Building
name|nb
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|b
operator|!=
literal|null
operator|&&
operator|!
name|b
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|b
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|nb
operator|=
name|Building
operator|.
name|findByExternalIdAndSession
argument_list|(
name|b
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|nb
operator|=
name|Building
operator|.
name|findByBldgAbbv
argument_list|(
name|b
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|myForm
operator|.
name|setBuildingId
argument_list|(
name|nb
operator|==
literal|null
condition|?
literal|null
else|:
name|nb
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|TimetableManager
name|mgr
init|=
operator|(
name|user
operator|==
literal|null
condition|?
literal|null
else|:
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|mgr
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getRoomTypes
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|Collection
argument_list|<
name|RoomType
argument_list|>
name|allRoomTypes
init|=
name|myForm
operator|.
name|getAllRoomTypes
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|RoomType
argument_list|>
name|defaultRoomTypes
init|=
name|mgr
operator|.
name|findDefaultEventManagerRoomTimesFor
argument_list|(
name|user
operator|.
name|getRole
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|Long
argument_list|>
name|orderedTypeList
init|=
operator|new
name|Vector
argument_list|(
name|allRoomTypes
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|RoomType
name|displayedRoomType
range|:
name|allRoomTypes
control|)
block|{
for|for
control|(
name|RoomType
name|rt
range|:
name|defaultRoomTypes
control|)
block|{
if|if
condition|(
name|displayedRoomType
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|orderedTypeList
operator|.
name|add
argument_list|(
name|rt
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|myForm
operator|.
name|setRoomTypes
argument_list|(
operator|new
name|Long
index|[
name|orderedTypeList
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Long
name|l
range|:
name|orderedTypeList
control|)
block|{
name|myForm
operator|.
name|getRoomTypes
argument_list|()
index|[
name|i
index|]
operator|=
name|l
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"EventGrid.StartTime"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|myForm
operator|.
name|load
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ActionMessages
name|errors
init|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
if|if
condition|(
literal|"Add Event"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"select"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|select
init|=
operator|(
name|String
index|[]
operator|)
name|request
operator|.
name|getParameterValues
argument_list|(
literal|"select"
argument_list|)
decl_stmt|;
name|TreeSet
argument_list|<
name|DateLocation
argument_list|>
name|locations
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|select
label|:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|select
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|select
index|[
name|i
index|]
argument_list|,
literal|":"
argument_list|)
decl_stmt|;
name|Location
name|location
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|Date
name|date
init|=
operator|new
name|Date
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|startTime
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|stopTime
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|DateLocation
name|last
range|:
name|locations
control|)
block|{
if|if
condition|(
name|last
operator|.
name|getDate
argument_list|()
operator|.
name|equals
argument_list|(
name|date
argument_list|)
operator|&&
name|last
operator|.
name|getLocation
argument_list|()
operator|.
name|equals
argument_list|(
name|location
operator|.
name|getPermanentId
argument_list|()
argument_list|)
operator|&&
name|startTime
operator|==
name|last
operator|.
name|getStopTime
argument_list|()
condition|)
block|{
name|last
operator|.
name|setStopTime
argument_list|(
name|stopTime
argument_list|)
expr_stmt|;
continue|continue
name|select
continue|;
block|}
block|}
name|locations
operator|.
name|add
argument_list|(
operator|new
name|DateLocation
argument_list|(
name|date
argument_list|,
name|location
argument_list|,
name|startTime
argument_list|,
name|stopTime
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|HttpSession
name|session
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Event.DateLocations"
argument_list|,
name|locations
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Event.EventType"
argument_list|,
name|Event
operator|.
name|sEventTypes
index|[
name|Event
operator|.
name|sEventTypeSpecial
index|]
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Event.SessionId"
argument_list|,
name|myForm
operator|.
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Event.StartTime"
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Event.StopTime"
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"Event.IsAddMeetings"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"add"
argument_list|)
return|;
block|}
else|else
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"select"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No available time/room selected."
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|File
name|f
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"events"
argument_list|,
literal|".pdf"
argument_list|)
decl_stmt|;
try|try
block|{
operator|new
name|PdfEventGridTable
argument_list|(
name|myForm
argument_list|)
operator|.
name|export
argument_list|(
name|f
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|.
name|exists
argument_list|()
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_OPEN_URL
argument_list|,
literal|"temp/"
operator|+
name|f
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

