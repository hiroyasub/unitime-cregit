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
name|security
operator|.
name|permissions
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
name|ArrayList
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
name|HashSet
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
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|model
operator|.
name|DepartmentStatusType
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
name|Exam
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
name|ExamType
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
name|RoomTypeOption
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
name|Solution
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
name|UserQualifier
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

begin_class
specifier|public
class|class
name|EventPermissions
block|{
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|PersonalSchedule
argument_list|)
specifier|public
specifier|static
class|class
name|PersonalSchedule
implements|implements
name|Permission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionSession
name|permissionSession
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
operator|(
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportClasses
argument_list|)
operator|&&
name|Solution
operator|.
name|hasTimetable
argument_list|(
name|source
operator|.
name|getSessionId
argument_list|()
argument_list|)
operator|)
operator|||
operator|(
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsFinal
argument_list|)
operator|&&
name|Exam
operator|.
name|hasTimetable
argument_list|(
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|ExamType
operator|.
name|sExamTypeFinal
argument_list|)
operator|)
operator|||
operator|(
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsMidterm
argument_list|)
operator|&&
name|Exam
operator|.
name|hasTimetable
argument_list|(
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|ExamType
operator|.
name|sExamTypeMidterm
argument_list|)
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Session
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Session
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|PersonalScheduleLookup
argument_list|)
specifier|public
specifier|static
class|class
name|PersonalScheduleLookup
extends|extends
name|PersonalSchedule
block|{}
specifier|protected
specifier|static
specifier|abstract
class|class
name|EventPermission
parameter_list|<
name|T
parameter_list|>
implements|implements
name|Permission
argument_list|<
name|T
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionSession
name|permissionSession
decl_stmt|;
specifier|protected
name|Date
name|today
parameter_list|()
block|{
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
return|return
name|cal
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|protected
name|Date
name|begin
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
return|return
name|session
operator|.
name|getEventBeginDate
argument_list|()
return|;
block|}
specifier|protected
name|Date
name|end
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
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
return|return
name|cal
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|protected
name|boolean
name|isOutside
parameter_list|(
name|Date
name|date
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
return|return
name|date
operator|==
literal|null
operator|||
name|date
operator|.
name|before
argument_list|(
name|begin
argument_list|(
name|session
argument_list|)
argument_list|)
operator|||
operator|!
name|date
operator|.
name|before
argument_list|(
name|end
argument_list|(
name|session
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|boolean
name|isPast
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
name|date
operator|.
name|before
argument_list|(
name|today
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|List
argument_list|<
name|Long
argument_list|>
name|locations
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|UserContext
name|user
parameter_list|)
block|{
name|String
name|anyRequest
init|=
literal|""
decl_stmt|;
name|String
name|deptRequest
init|=
literal|""
decl_stmt|;
name|String
name|mgrRequest
init|=
literal|""
decl_stmt|;
for|for
control|(
name|RoomTypeOption
operator|.
name|Status
name|state
range|:
name|RoomTypeOption
operator|.
name|Status
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|state
operator|.
name|isAuthenticatedUsersCanRequestEvents
argument_list|()
condition|)
name|anyRequest
operator|+=
operator|(
name|anyRequest
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|)
operator|+
name|state
operator|.
name|ordinal
argument_list|()
expr_stmt|;
else|else
block|{
if|if
condition|(
name|state
operator|.
name|isDepartmentalUsersCanRequestEvents
argument_list|()
condition|)
name|deptRequest
operator|+=
operator|(
name|deptRequest
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|)
operator|+
name|state
operator|.
name|ordinal
argument_list|()
expr_stmt|;
if|if
condition|(
name|state
operator|.
name|isEventManagersCanRequestEvents
argument_list|()
condition|)
name|mgrRequest
operator|+=
operator|(
name|mgrRequest
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|)
operator|+
name|state
operator|.
name|ordinal
argument_list|()
expr_stmt|;
block|}
block|}
name|Set
argument_list|<
name|Serializable
argument_list|>
name|roleDeptIds
init|=
operator|new
name|HashSet
argument_list|<
name|Serializable
argument_list|>
argument_list|()
decl_stmt|,
name|mgrDeptIds
init|=
operator|new
name|HashSet
argument_list|<
name|Serializable
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
condition|)
block|{
for|for
control|(
name|UserAuthority
name|a
range|:
name|user
operator|.
name|getAuthorities
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|sessionId
operator|.
name|equals
argument_list|(
name|a
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getQualifierId
argument_list|()
argument_list|)
condition|)
continue|continue;
for|for
control|(
name|UserQualifier
name|q
range|:
name|a
operator|.
name|getQualifiers
argument_list|(
literal|"Department"
argument_list|)
control|)
block|{
name|roleDeptIds
operator|.
name|add
argument_list|(
name|q
operator|.
name|getQualifierId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|a
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventMeetingApprove
argument_list|)
condition|)
name|mgrDeptIds
operator|.
name|add
argument_list|(
name|q
operator|.
name|getQualifierId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|String
name|roleDept
init|=
literal|null
decl_stmt|,
name|mgrDept
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Serializable
name|id
range|:
name|roleDeptIds
control|)
name|roleDept
operator|=
operator|(
name|roleDept
operator|==
literal|null
condition|?
literal|""
else|:
name|roleDept
operator|+
literal|","
operator|)
operator|+
name|id
expr_stmt|;
for|for
control|(
name|Serializable
name|id
range|:
name|mgrDeptIds
control|)
name|mgrDept
operator|=
operator|(
name|mgrDept
operator|==
literal|null
condition|?
literal|""
else|:
name|mgrDept
operator|+
literal|","
operator|)
operator|+
name|id
expr_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
return|return
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
return|;
return|return
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
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
literal|"from Location l, RoomTypeOption o "
operator|+
literal|"where l.eventDepartment.allowEvents = true and o.roomType = l.roomType and o.department = l.eventDepartment and l.session.uniqueId = :sessionId and ("
operator|+
literal|"o.status in ("
operator|+
name|anyRequest
operator|+
literal|")"
operator|+
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
condition|?
literal|" or o.status in ("
operator|+
name|deptRequest
operator|+
literal|")"
else|:
name|roleDept
operator|==
literal|null
condition|?
literal|""
else|:
literal|" or (o.status in ("
operator|+
name|deptRequest
operator|+
literal|") and o.department.uniqueId in ("
operator|+
name|roleDept
operator|+
literal|"))"
operator|)
operator|+
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
operator|&&
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventMeetingApprove
argument_list|)
condition|?
literal|" or o.status in ("
operator|+
name|mgrRequest
operator|+
literal|")"
else|:
name|mgrDept
operator|==
literal|null
condition|?
literal|""
else|:
literal|" or (o.status in ("
operator|+
name|mgrRequest
operator|+
literal|") and o.department.uniqueId in ("
operator|+
name|mgrDept
operator|+
literal|"))"
operator|)
operator|+
literal|")"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
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
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Events
argument_list|)
specifier|public
specifier|static
class|class
name|Events
extends|extends
name|EventPermission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|canNoRoleReport
argument_list|()
operator|||
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventAnyLocation
argument_list|)
operator|||
operator|!
name|locations
argument_list|(
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|user
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Session
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Session
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventAddSpecial
argument_list|)
specifier|public
specifier|static
class|class
name|EventAddSpecial
extends|extends
name|EventPermission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Session
name|source
parameter_list|)
block|{
return|return
operator|(
operator|!
name|isPast
argument_list|(
name|end
argument_list|(
name|source
argument_list|)
argument_list|)
operator|||
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventEditPast
argument_list|)
operator|)
operator|&&
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventAnyLocation
argument_list|)
operator|||
operator|!
name|locations
argument_list|(
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|user
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Session
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Session
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventAddCourseRelated
argument_list|)
specifier|public
specifier|static
class|class
name|EventAddCourseRelated
extends|extends
name|EventAddSpecial
block|{ }
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventDetail
argument_list|)
specifier|public
specifier|static
class|class
name|EventDetail
implements|implements
name|Permission
argument_list|<
name|Event
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Event
name|source
parameter_list|)
block|{
return|return
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventLookupContact
argument_list|)
operator|||
name|user
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|source
operator|.
name|getMainContact
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Event
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Event
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventEdit
argument_list|)
specifier|public
specifier|static
class|class
name|EventEdit
extends|extends
name|EventPermission
argument_list|<
name|Event
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionSession
name|permissionSession
decl_stmt|;
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Date
argument_list|>
name|permissionEventDate
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Event
name|source
parameter_list|)
block|{
comment|// Examination and class events cannot be edited just yet
switch|switch
condition|(
name|source
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
comment|// Must be the owner or an event admin
if|if
condition|(
operator|!
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventLookupContact
argument_list|)
operator|&&
operator|!
name|user
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|source
operator|.
name|getMainContact
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Check academic session
name|Session
name|session
init|=
name|source
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
return|return
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|session
argument_list|)
return|;
block|}
else|else
block|{
name|boolean
name|noLocation
init|=
literal|true
decl_stmt|,
name|hasDate
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Meeting
name|meeting
range|:
name|source
operator|.
name|getMeetings
argument_list|()
control|)
block|{
name|Location
name|location
init|=
name|meeting
operator|.
name|getLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|location
operator|!=
literal|null
condition|)
block|{
name|noLocation
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|location
operator|.
name|getSession
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|hasDate
operator|&&
name|permissionEventDate
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
condition|)
name|hasDate
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|noLocation
operator|&&
name|hasDate
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Event
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Event
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventDate
argument_list|)
specifier|public
specifier|static
class|class
name|EventDate
extends|extends
name|EventPermission
argument_list|<
name|Date
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Date
name|source
parameter_list|)
block|{
return|return
operator|(
operator|!
name|isPast
argument_list|(
name|source
argument_list|)
operator|||
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventEditPast
argument_list|)
operator|)
operator|&&
operator|!
name|isOutside
argument_list|(
name|source
argument_list|,
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Date
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Date
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventLocation
argument_list|)
specifier|public
specifier|static
class|class
name|EventLocation
extends|extends
name|EventPermission
argument_list|<
name|Location
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Location
name|source
parameter_list|)
block|{
return|return
name|source
operator|==
literal|null
operator|||
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventAnyLocation
argument_list|)
operator|||
name|locations
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|user
argument_list|)
operator|.
name|contains
argument_list|(
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Location
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Location
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventLocationApprove
argument_list|)
specifier|public
specifier|static
class|class
name|EventLocationApprove
extends|extends
name|EventPermission
argument_list|<
name|Location
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Location
argument_list|>
name|permissionEventLocation
decl_stmt|;
annotation|@
name|Autowired
name|PermissionDepartment
name|permissionDepartment
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Location
name|source
parameter_list|)
block|{
if|if
condition|(
name|source
operator|==
literal|null
condition|)
return|return
literal|true
return|;
if|if
condition|(
operator|!
name|permissionEventLocation
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventAnyLocation
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|source
operator|.
name|getEventDepartment
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|source
operator|.
name|getRoomType
argument_list|()
operator|.
name|getOption
argument_list|(
name|source
operator|.
name|getEventDepartment
argument_list|()
argument_list|)
operator|.
name|getEventStatus
argument_list|()
operator|.
name|isEventsManagersCanApprove
argument_list|()
condition|)
return|return
literal|false
return|;
return|return
name|source
operator|.
name|getEventDepartment
argument_list|()
operator|!=
literal|null
operator|&&
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getEventDepartment
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Location
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Location
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventLocationOverbook
argument_list|)
specifier|public
specifier|static
class|class
name|EventLocationOverbook
extends|extends
name|EventLocationApprove
block|{ }
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventMeetingEdit
argument_list|)
specifier|public
specifier|static
class|class
name|EventMeetingEdit
extends|extends
name|EventPermission
argument_list|<
name|Meeting
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Event
argument_list|>
name|permissionEventEdit
decl_stmt|;
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Date
argument_list|>
name|permissionEventDate
decl_stmt|;
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Location
argument_list|>
name|permissionEventLocation
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Meeting
name|source
parameter_list|)
block|{
return|return
name|permissionEventEdit
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getEvent
argument_list|()
argument_list|)
operator|&&
name|permissionEventDate
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|&&
name|permissionEventLocation
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getLocation
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Meeting
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Meeting
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EventMeetingApprove
argument_list|)
specifier|public
specifier|static
class|class
name|EventMeetingApprove
extends|extends
name|EventPermission
argument_list|<
name|Meeting
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Event
argument_list|>
name|permissionEventEdit
decl_stmt|;
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Location
argument_list|>
name|permissionEventLocationApprove
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Meeting
name|source
parameter_list|)
block|{
return|return
name|permissionEventEdit
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getEvent
argument_list|()
argument_list|)
operator|&&
operator|!
name|isOutside
argument_list|(
name|source
operator|.
name|getMeetingDate
argument_list|()
argument_list|,
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
operator|&&
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventApprovePast
argument_list|)
operator|||
operator|!
name|isPast
argument_list|(
name|source
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|)
operator|&&
name|permissionEventLocationApprove
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getLocation
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Meeting
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Meeting
operator|.
name|class
return|;
block|}
block|}
block|}
end_class

end_unit

