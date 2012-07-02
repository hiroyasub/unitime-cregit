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
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|gwt
operator|.
name|resources
operator|.
name|GwtMessages
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
name|EventType
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
name|PageAccessException
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
name|Roles
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
name|security
operator|.
name|UserContext
import|;
end_import

begin_class
specifier|public
class|class
name|SimpleEventRights
implements|implements
name|EventRights
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|UserContext
name|iUser
decl_stmt|;
specifier|private
name|boolean
name|iHttpSessionNew
init|=
literal|false
decl_stmt|;
specifier|private
name|Date
name|iToday
decl_stmt|,
name|iBegin
decl_stmt|,
name|iEnd
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|public
name|SimpleEventRights
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|boolean
name|isHttpSessionNew
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|iUser
operator|=
name|user
expr_stmt|;
name|iHttpSessionNew
operator|=
name|isHttpSessionNew
expr_stmt|;
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
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
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|iToday
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
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
name|iSessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|!=
literal|null
condition|)
block|{
name|iBegin
operator|=
name|session
operator|.
name|getEventBeginDate
argument_list|()
expr_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|session
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
expr_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|iEnd
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|SimpleEventRights
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|this
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|context
operator|.
name|isHttpSessionNew
argument_list|()
argument_list|,
operator|(
name|sessionId
operator|!=
literal|null
condition|?
name|sessionId
else|:
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|?
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
else|:
literal|null
operator|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|UserContext
name|getUser
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
specifier|protected
name|boolean
name|isHttpSessionNew
parameter_list|()
block|{
return|return
name|iHttpSessionNew
return|;
block|}
specifier|protected
name|boolean
name|isAdmin
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|getUser
argument_list|()
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|boolean
name|isAuthenticated
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|!=
literal|null
return|;
block|}
specifier|protected
name|boolean
name|hasRole
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|hasSession
argument_list|()
return|;
block|}
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iManagedSessions
init|=
literal|null
decl_stmt|;
specifier|protected
name|boolean
name|hasSession
parameter_list|()
block|{
if|if
condition|(
name|iManagedSessions
operator|==
literal|null
condition|)
name|iManagedSessions
operator|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct d.session.uniqueId from TimetableManager m inner join m.departments d where m.externalUniqueId = :userId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"userId"
argument_list|,
name|getUserId
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
argument_list|)
expr_stmt|;
return|return
name|iManagedSessions
operator|.
name|contains
argument_list|(
name|iSessionId
argument_list|)
return|;
block|}
specifier|protected
name|boolean
name|isEventManager
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|Roles
operator|.
name|EVENT_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getUser
argument_list|()
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|&&
name|hasSession
argument_list|()
return|;
block|}
specifier|protected
name|boolean
name|isStudentAdvisor
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|Roles
operator|.
name|STUDENT_ADVISOR
operator|.
name|equals
argument_list|(
name|getUser
argument_list|()
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|&&
name|hasSession
argument_list|()
return|;
block|}
specifier|protected
name|boolean
name|isScheduleManager
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|Roles
operator|.
name|DEPT_SCHED_MGR_ROLE
operator|.
name|equals
argument_list|(
name|getUser
argument_list|()
operator|.
name|getCurrentRole
argument_list|()
argument_list|)
operator|&&
name|hasSession
argument_list|()
return|;
block|}
specifier|protected
name|String
name|getUserId
parameter_list|()
block|{
return|return
name|getUser
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|PageAccessException
name|getException
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isAuthenticated
argument_list|()
condition|)
return|return
operator|new
name|PageAccessException
argument_list|(
name|isHttpSessionNew
argument_list|()
condition|?
name|MESSAGES
operator|.
name|authenticationExpired
argument_list|()
else|:
name|MESSAGES
operator|.
name|authenticationRequired
argument_list|()
argument_list|)
return|;
return|return
operator|new
name|PageAccessException
argument_list|(
name|MESSAGES
operator|.
name|authenticationInsufficient
argument_list|()
argument_list|)
return|;
block|}
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iManagedRooms
init|=
literal|null
decl_stmt|;
specifier|protected
name|boolean
name|isLocationManager
parameter_list|(
name|Long
name|locationId
parameter_list|)
block|{
if|if
condition|(
name|isAdmin
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
if|else if
condition|(
name|isEventManager
argument_list|()
condition|)
block|{
if|if
condition|(
name|iManagedRooms
operator|==
literal|null
condition|)
name|iManagedRooms
operator|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct l.uniqueId "
operator|+
literal|"from Location l inner join l.roomDepts rd inner join rd.department.timetableManagers m "
operator|+
literal|"where m.externalUniqueId = :userId and rd.control = true and l.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"userId"
argument_list|,
name|getUserId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|iSessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|locationId
operator|==
literal|null
condition|?
operator|!
name|iManagedRooms
operator|.
name|isEmpty
argument_list|()
else|:
name|iManagedRooms
operator|.
name|contains
argument_list|(
name|locationId
argument_list|)
operator|)
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|iEventRooms
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|isEventLocation
parameter_list|(
name|Long
name|locationId
parameter_list|)
block|{
if|if
condition|(
name|iEventRooms
operator|==
literal|null
condition|)
name|iEventRooms
operator|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId "
operator|+
literal|"from Location l inner join l.roomDepts rd inner join rd.department.timetableManagers m inner join m.managerRoles mr, RoomTypeOption o "
operator|+
literal|"where rd.control = true and mr.role.reference = :eventMgr and o.status = 1 and o.roomType = l.roomType and o.session = l.session and l.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"eventMgr"
argument_list|,
name|Roles
operator|.
name|EVENT_MGR_ROLE
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|iSessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|locationId
operator|==
literal|null
condition|?
operator|!
name|iEventRooms
operator|.
name|isEmpty
argument_list|()
else|:
name|iEventRooms
operator|.
name|contains
argument_list|(
name|locationId
argument_list|)
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canOverbook
parameter_list|(
name|Long
name|locationId
parameter_list|)
block|{
return|return
name|isLocationManager
argument_list|(
name|locationId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canCreate
parameter_list|(
name|Long
name|locationId
parameter_list|)
block|{
comment|// Admin can always create an event.
if|if
condition|(
name|isAdmin
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
name|isAuthenticated
argument_list|()
operator|&&
name|isEventLocation
argument_list|(
name|locationId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canApprove
parameter_list|(
name|Long
name|locationId
parameter_list|)
block|{
return|return
name|isLocationManager
argument_list|(
name|locationId
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkAccess
parameter_list|()
throws|throws
name|PageAccessException
block|{
if|if
condition|(
operator|!
name|isAuthenticated
argument_list|()
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.event_timetable.requires_authentication"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
throw|throw
name|getException
argument_list|()
throw|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canSee
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
comment|// Owner of the event can always see the details
if|if
condition|(
name|isAuthenticated
argument_list|()
operator|&&
name|event
operator|.
name|getMainContact
argument_list|()
operator|!=
literal|null
operator|&&
name|getUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|event
operator|.
name|getMainContact
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
comment|// Admin and event manager can see details of any event
if|if
condition|(
name|isAdmin
argument_list|()
operator|||
name|isEventManager
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isPastOrOutside
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
if|if
condition|(
name|date
operator|==
literal|null
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iBegin
operator|!=
literal|null
operator|&&
name|date
operator|.
name|before
argument_list|(
name|iBegin
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iEnd
operator|!=
literal|null
operator|&&
operator|!
name|date
operator|.
name|before
argument_list|(
name|iEnd
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|date
operator|==
literal|null
operator|||
name|date
operator|.
name|before
argument_list|(
name|iToday
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|canEdit
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
comment|// Examination and class events cannot be edited just yet
switch|switch
condition|(
name|event
operator|.
name|getEventType
argument_list|()
condition|)
block|{
case|case
name|Event
operator|.
name|sEventTypeClass
case|:
case|case
name|Event
operator|.
name|sEventTypeFinalExam
case|:
case|case
name|Event
operator|.
name|sEventTypeMidtermExam
case|:
return|return
literal|false
return|;
block|}
comment|// Otherwise, user can edit (e.g., add new meetings) if he/see can see the details
return|return
name|canSee
argument_list|(
name|event
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canEdit
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
comment|// Admin can always edit a meeting
if|if
condition|(
name|isAdmin
argument_list|()
condition|)
return|return
literal|true
return|;
comment|// Past meetings cannot be edited
if|if
condition|(
name|isPastOrOutside
argument_list|(
name|meeting
operator|.
name|getStartTime
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Examination and class events cannot be edited just yet
switch|switch
condition|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
condition|)
block|{
case|case
name|Event
operator|.
name|sEventTypeClass
case|:
case|case
name|Event
operator|.
name|sEventTypeFinalExam
case|:
case|case
name|Event
operator|.
name|sEventTypeMidtermExam
case|:
return|return
literal|false
return|;
block|}
comment|// Owner of the event can edit the meeting
if|if
condition|(
name|isAuthenticated
argument_list|()
operator|&&
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMainContact
argument_list|()
operator|!=
literal|null
operator|&&
name|getUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMainContact
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
comment|// Event manager can edit if no location, or if the location is managed by the user
if|if
condition|(
name|isEventManager
argument_list|()
condition|)
block|{
name|Location
name|location
init|=
name|meeting
operator|.
name|getLocation
argument_list|()
decl_stmt|;
return|return
name|location
operator|==
literal|null
operator|||
name|isEventLocation
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canApprove
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
comment|// No approval for examination and class events
switch|switch
condition|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
condition|)
block|{
case|case
name|Event
operator|.
name|sEventTypeClass
case|:
case|case
name|Event
operator|.
name|sEventTypeFinalExam
case|:
case|case
name|Event
operator|.
name|sEventTypeMidtermExam
case|:
return|return
literal|false
return|;
block|}
comment|// Admin can always approve a meeting
if|if
condition|(
name|isAdmin
argument_list|()
condition|)
return|return
literal|true
return|;
comment|// Past meetings cannot be edited
if|if
condition|(
name|isPastOrOutside
argument_list|(
name|meeting
operator|.
name|getStartTime
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Event manager can approve if no location, or if the location is managed by the user
if|if
condition|(
name|isEventManager
argument_list|()
condition|)
block|{
name|Location
name|location
init|=
name|meeting
operator|.
name|getLocation
argument_list|()
decl_stmt|;
return|return
name|location
operator|==
literal|null
operator|||
name|isEventLocation
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canAddEvent
parameter_list|(
name|EventType
name|type
parameter_list|,
name|String
name|userId
parameter_list|)
block|{
comment|// Not authenticated
if|if
condition|(
operator|!
name|isAuthenticated
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// All dates are in the past
if|if
condition|(
name|iEnd
operator|==
literal|null
operator|||
operator|!
name|iToday
operator|.
name|before
argument_list|(
name|iEnd
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// No event room
if|if
condition|(
operator|!
name|isAdmin
argument_list|()
operator|&&
operator|!
name|isEventLocation
argument_list|(
literal|null
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Only admins and event managers can create an event on behalf of someone else
if|if
condition|(
name|userId
operator|!=
literal|null
operator|&&
operator|!
name|isAdmin
argument_list|()
operator|&&
operator|!
name|isEventManager
argument_list|()
operator|&&
operator|!
name|userId
operator|.
name|equals
argument_list|(
name|getUserId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Default event type to Special Event
if|if
condition|(
name|type
operator|==
literal|null
condition|)
name|type
operator|=
name|EventType
operator|.
name|Special
expr_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|Special
case|:
return|return
literal|true
return|;
case|case
name|Course
case|:
return|return
name|isAdmin
argument_list|()
operator|||
name|isEventManager
argument_list|()
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canLookupContacts
parameter_list|()
block|{
return|return
name|isAdmin
argument_list|()
operator|||
name|isEventManager
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canSeeSchedule
parameter_list|(
name|String
name|userId
parameter_list|)
block|{
return|return
name|isAdmin
argument_list|()
operator|||
name|isScheduleManager
argument_list|()
operator|||
name|isStudentAdvisor
argument_list|()
operator|||
operator|(
name|userId
operator|!=
literal|null
operator|&&
name|userId
operator|.
name|equals
argument_list|(
name|getUserId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

