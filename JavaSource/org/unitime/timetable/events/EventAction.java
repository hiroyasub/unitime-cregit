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
name|defaults
operator|.
name|SessionAttribute
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
name|command
operator|.
name|client
operator|.
name|GwtRpcResponse
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|GwtConstants
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
name|EventRpcRequest
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
name|MeetingConflictInterface
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
name|Qualifiable
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
name|UserAuthority
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
name|evaluation
operator|.
name|UniTimePermissionCheck
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
name|qualifiers
operator|.
name|SimpleQualifier
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
name|rights
operator|.
name|Right
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
name|Formats
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|EventAction
parameter_list|<
name|T
extends|extends
name|EventRpcRequest
parameter_list|<
name|R
parameter_list|>
parameter_list|,
name|R
extends|extends
name|GwtRpcResponse
parameter_list|>
implements|implements
name|GwtRpcImplementation
argument_list|<
name|T
argument_list|,
name|R
argument_list|>
block|{
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
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|sDateFormat
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT_SHORT
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|R
name|execute
parameter_list|(
name|T
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
comment|// Check basic permissions
name|context
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|Right
operator|.
name|Events
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Execute action
return|return
name|execute
argument_list|(
name|request
argument_list|,
operator|new
name|EventContext
argument_list|(
name|context
argument_list|,
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|abstract
name|R
name|execute
parameter_list|(
name|T
name|request
parameter_list|,
name|EventContext
name|context
parameter_list|)
function_decl|;
specifier|protected
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|getDateFormat
parameter_list|()
block|{
return|return
name|sDateFormat
return|;
block|}
specifier|protected
specifier|static
name|String
name|toString
parameter_list|(
name|MeetingInterface
name|meeting
parameter_list|)
block|{
return|return
operator|(
name|meeting
operator|instanceof
name|MeetingConflictInterface
condition|?
operator|(
operator|(
name|MeetingConflictInterface
operator|)
name|meeting
operator|)
operator|.
name|getName
argument_list|()
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
operator|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getDateFormat
argument_list|()
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|)
operator|+
name|meeting
operator|.
name|getAllocatedTime
argument_list|(
name|CONSTANTS
argument_list|)
operator|+
operator|(
name|meeting
operator|.
name|hasLocation
argument_list|()
condition|?
literal|" "
operator|+
name|meeting
operator|.
name|getLocationName
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
specifier|protected
specifier|static
name|String
name|toString
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
return|return
operator|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getDateFormat
argument_list|()
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|)
operator|+
name|time2string
argument_list|(
name|meeting
operator|.
name|getStartPeriod
argument_list|()
argument_list|,
literal|0
argument_list|)
operator|+
literal|" - "
operator|+
name|time2string
argument_list|(
name|meeting
operator|.
name|getStopPeriod
argument_list|()
argument_list|,
literal|0
argument_list|)
operator|+
operator|(
name|meeting
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|" "
operator|+
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
specifier|protected
specifier|static
name|String
name|time2string
parameter_list|(
name|int
name|slot
parameter_list|,
name|int
name|offset
parameter_list|)
block|{
name|int
name|min
init|=
literal|5
operator|*
name|slot
operator|+
name|offset
decl_stmt|;
if|if
condition|(
name|min
operator|==
literal|0
operator|||
name|min
operator|==
literal|1440
condition|)
return|return
name|CONSTANTS
operator|.
name|timeMidnight
argument_list|()
return|;
if|if
condition|(
name|min
operator|==
literal|720
condition|)
return|return
name|CONSTANTS
operator|.
name|timeNoon
argument_list|()
return|;
name|int
name|h
init|=
name|min
operator|/
literal|60
decl_stmt|;
name|int
name|m
init|=
name|min
operator|%
literal|60
decl_stmt|;
if|if
condition|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
condition|)
block|{
return|return
operator|(
name|h
operator|>
literal|12
condition|?
name|h
operator|-
literal|12
else|:
name|h
operator|)
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
operator|+
operator|(
name|h
operator|==
literal|24
condition|?
literal|"a"
else|:
name|h
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
return|;
block|}
else|else
block|{
return|return
name|h
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|EventContext
implements|implements
name|SessionContext
block|{
specifier|private
name|SessionContext
name|iContext
decl_stmt|;
specifier|private
name|Qualifiable
index|[]
name|iFilter
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
name|UserContext
name|iUser
decl_stmt|;
specifier|private
name|boolean
name|iAllowEditPast
init|=
literal|false
decl_stmt|;
specifier|public
name|EventContext
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|UserContext
name|user
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|iContext
operator|=
name|context
expr_stmt|;
name|iUser
operator|=
name|user
expr_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
name|sessionId
operator|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
expr_stmt|;
name|iFilter
operator|=
operator|new
name|Qualifiable
index|[]
block|{
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|sessionId
argument_list|)
block|}
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
name|sessionId
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
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|String
name|role
init|=
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
operator|)
decl_stmt|;
for|for
control|(
name|UserAuthority
name|authority
range|:
name|user
operator|.
name|getAuthorities
argument_list|()
control|)
block|{
if|if
condition|(
name|authority
operator|.
name|getAcademicSession
argument_list|()
operator|!=
literal|null
operator|&&
name|authority
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getQualifierId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessionId
argument_list|)
operator|&&
operator|(
name|role
operator|==
literal|null
operator|||
name|role
operator|.
name|equals
argument_list|(
name|authority
operator|.
name|getRole
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|iUser
operator|=
operator|new
name|UniTimePermissionCheck
operator|.
name|UserContextWrapper
argument_list|(
name|user
argument_list|,
name|authority
argument_list|)
expr_stmt|;
if|if
condition|(
name|role
operator|!=
literal|null
condition|)
name|iFilter
operator|=
operator|new
name|Qualifiable
index|[]
block|{
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|sessionId
argument_list|)
block|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Role"
argument_list|,
name|role
argument_list|)
block|}
expr_stmt|;
break|break;
block|}
block|}
block|}
name|iAllowEditPast
operator|=
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventEditPast
argument_list|)
expr_stmt|;
block|}
specifier|public
name|EventContext
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
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isOutside
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
return|return
name|date
operator|==
literal|null
operator|||
operator|(
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
operator|)
operator|||
operator|(
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
operator|)
return|;
block|}
specifier|public
name|boolean
name|isPast
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
return|return
operator|!
name|iAllowEditPast
operator|&&
operator|(
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
operator|)
return|;
block|}
specifier|public
name|boolean
name|isPastOrOutside
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
return|return
name|isPast
argument_list|(
name|date
argument_list|)
operator|||
name|isOutside
argument_list|(
name|date
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isAuthenticated
parameter_list|()
block|{
return|return
name|iUser
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|UserContext
name|getUser
parameter_list|()
block|{
return|return
name|iUser
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isHttpSessionNew
parameter_list|()
block|{
return|return
name|iContext
operator|.
name|isHttpSessionNew
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getHttpSessionId
parameter_list|()
block|{
return|return
name|iContext
operator|.
name|getHttpSessionId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getAttribute
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|getAttribute
argument_list|(
name|name
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeAttribute
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iContext
operator|.
name|removeAttribute
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAttribute
parameter_list|(
name|String
name|name
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|iContext
operator|.
name|setAttribute
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeAttribute
parameter_list|(
name|SessionAttribute
name|attribute
parameter_list|)
block|{
name|iContext
operator|.
name|removeAttribute
argument_list|(
name|attribute
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAttribute
parameter_list|(
name|SessionAttribute
name|attribute
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|iContext
operator|.
name|setAttribute
argument_list|(
name|attribute
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getAttribute
parameter_list|(
name|SessionAttribute
name|attribute
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|getAttribute
argument_list|(
name|attribute
argument_list|)
return|;
block|}
specifier|public
name|PageAccessException
name|getException
parameter_list|()
block|{
if|if
condition|(
name|iContext
operator|.
name|isAuthenticated
argument_list|()
condition|)
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
return|return
operator|new
name|PageAccessException
argument_list|(
name|iContext
operator|.
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
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermission
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
if|if
condition|(
operator|!
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|right
argument_list|,
name|iFilter
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
name|void
name|checkPermission
parameter_list|(
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
if|if
condition|(
operator|!
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|,
name|iFilter
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
name|void
name|checkPermission
parameter_list|(
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
if|if
condition|(
operator|!
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|targetObject
argument_list|,
name|right
argument_list|,
name|iFilter
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
name|hasPermission
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|right
argument_list|,
name|iFilter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermission
parameter_list|(
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|,
name|iFilter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermission
parameter_list|(
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|targetObject
argument_list|,
name|right
argument_list|,
name|iFilter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|right
argument_list|,
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|length
operator|==
literal|0
condition|?
name|iFilter
else|:
name|filter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|,
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|length
operator|==
literal|0
condition|?
name|iFilter
else|:
name|filter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
return|return
name|iContext
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|targetObject
argument_list|,
name|right
argument_list|,
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|length
operator|==
literal|0
condition|?
name|iFilter
else|:
name|filter
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
name|iContext
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|right
argument_list|,
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|length
operator|==
literal|0
condition|?
name|iFilter
else|:
name|filter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
name|iContext
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|targetId
argument_list|,
name|targetType
argument_list|,
name|right
argument_list|,
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|length
operator|==
literal|0
condition|?
name|iFilter
else|:
name|filter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
name|iContext
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|targetObject
argument_list|,
name|right
argument_list|,
name|filter
operator|==
literal|null
operator|||
name|filter
operator|.
name|length
operator|==
literal|0
condition|?
name|iFilter
else|:
name|filter
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

