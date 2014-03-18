begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
operator|.
name|rooms
package|;
end_package

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
name|HashMap
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
name|Map
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
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|defaults
operator|.
name|CommonValues
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
name|UserProperty
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
name|GwtRpcException
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|RoomInterface
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
name|RoomInterface
operator|.
name|RoomSharingModel
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
name|RoomInterface
operator|.
name|RoomSharingOption
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
name|RoomInterface
operator|.
name|RoomSharingRequest
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
name|ChangeLog
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
name|Department
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
name|RoomDept
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
name|DepartmentDAO
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
name|RequiredTimeTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|RoomSharingRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RoomSharingBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|RoomSharingRequest
argument_list|,
name|RoomSharingModel
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
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
specifier|final
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
annotation|@
name|Override
specifier|public
name|RoomSharingModel
name|execute
parameter_list|(
name|RoomSharingRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
switch|switch
condition|(
name|request
operator|.
name|getOperation
argument_list|()
condition|)
block|{
case|case
name|LOAD
case|:
return|return
operator|(
name|request
operator|.
name|isEventAvailability
argument_list|()
condition|?
name|loadEventAvailability
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
else|:
name|loadRoomSharing
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
operator|)
return|;
case|case
name|SAVE
case|:
return|return
operator|(
name|request
operator|.
name|isEventAvailability
argument_list|()
condition|?
name|saveEventAvailability
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
else|:
name|saveRoomSharing
argument_list|(
name|request
argument_list|,
name|context
argument_list|)
operator|)
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|RoomSharingModel
name|loadRoomSharing
parameter_list|(
name|RoomSharingRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|,
literal|"Location"
argument_list|,
name|Right
operator|.
name|RoomDetailAvailability
argument_list|)
expr_stmt|;
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
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|)
decl_stmt|;
name|RoomSharingModel
name|model
init|=
operator|new
name|RoomSharingModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|setId
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setName
argument_list|(
name|location
operator|.
name|getLabel
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
literal|true
condition|;
name|i
operator|++
control|)
block|{
name|String
name|mode
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.room.sharingMode"
operator|+
operator|(
literal|1
operator|+
name|i
operator|)
argument_list|,
name|i
operator|<
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
operator|.
name|length
condition|?
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
index|[
name|i
index|]
else|:
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|mode
operator|==
literal|null
operator|||
name|mode
operator|.
name|isEmpty
argument_list|()
condition|)
break|break;
name|model
operator|.
name|addMode
argument_list|(
operator|new
name|RoomInterface
operator|.
name|RoomSharingDisplayMode
argument_list|(
name|mode
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|boolean
name|editable
init|=
name|context
operator|.
name|hasPermission
argument_list|(
name|location
argument_list|,
name|Right
operator|.
name|RoomEditAvailability
argument_list|)
decl_stmt|;
name|model
operator|.
name|setDefaultEditable
argument_list|(
name|editable
argument_list|)
expr_stmt|;
name|model
operator|.
name|addOption
argument_list|(
operator|new
name|RoomSharingOption
argument_list|(
operator|-
literal|1l
argument_list|,
literal|"#FFFFFF"
argument_list|,
name|MESSAGES
operator|.
name|codeFreeForAll
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|legendFreeForAll
argument_list|()
argument_list|,
name|editable
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|addOption
argument_list|(
operator|new
name|RoomSharingOption
argument_list|(
operator|-
literal|2l
argument_list|,
literal|"#696969"
argument_list|,
name|MESSAGES
operator|.
name|codeNotAvailable
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|legendNotAvailable
argument_list|()
argument_list|,
name|editable
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|defaultGridSize
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridSize
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultGridSize
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|model
operator|.
name|getModes
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|model
operator|.
name|getModes
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|defaultGridSize
argument_list|)
condition|)
block|{
name|model
operator|.
name|setDefaultMode
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|model
operator|.
name|setDefaultHorizontal
argument_list|(
name|CommonValues
operator|.
name|HorizontalGrid
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|setDefaultOption
argument_list|(
name|model
operator|.
name|getOptions
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|setNote
argument_list|(
name|location
operator|.
name|getShareNote
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setNoteEditable
argument_list|(
name|editable
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Department
argument_list|>
name|current
init|=
operator|new
name|TreeSet
argument_list|<
name|Department
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RoomDept
name|rd
range|:
name|location
operator|.
name|getRoomDepts
argument_list|()
control|)
name|current
operator|.
name|add
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|current
control|)
name|model
operator|.
name|addOption
argument_list|(
operator|new
name|RoomSharingOption
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"#"
operator|+
name|d
operator|.
name|getRoomSharingColor
argument_list|(
name|current
argument_list|)
argument_list|,
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|d
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|d
operator|.
name|isExternalManager
argument_list|()
condition|?
literal|" (EXT: "
operator|+
name|d
operator|.
name|getExternalMgrLabel
argument_list|()
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|,
name|editable
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|Department
operator|.
name|findAllBeingUsed
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
name|model
operator|.
name|addOther
argument_list|(
operator|new
name|RoomSharingOption
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"#"
operator|+
name|d
operator|.
name|getRoomSharingColor
argument_list|(
name|current
argument_list|)
argument_list|,
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|d
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|d
operator|.
name|isExternalManager
argument_list|()
condition|?
literal|" (EXT: "
operator|+
name|d
operator|.
name|getExternalMgrLabel
argument_list|()
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|,
name|editable
argument_list|)
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Character
argument_list|,
name|Long
argument_list|>
name|char2dept
init|=
operator|new
name|HashMap
argument_list|<
name|Character
argument_list|,
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|char
name|pref
init|=
literal|'0'
decl_stmt|;
if|if
condition|(
name|location
operator|.
name|getManagerIds
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|location
operator|.
name|getManagerIds
argument_list|()
argument_list|,
literal|","
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|Long
name|id
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
decl_stmt|;
name|char2dept
operator|.
name|put
argument_list|(
operator|new
name|Character
argument_list|(
name|pref
operator|++
argument_list|)
argument_list|,
name|id
argument_list|)
expr_stmt|;
block|}
block|}
try|try
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
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
name|NR_DAYS
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|;
name|t
operator|++
control|)
block|{
name|pref
operator|=
operator|(
name|location
operator|.
name|getPattern
argument_list|()
operator|!=
literal|null
operator|&&
name|idx
operator|<
name|location
operator|.
name|getPattern
argument_list|()
operator|.
name|length
argument_list|()
condition|?
name|location
operator|.
name|getPattern
argument_list|()
operator|.
name|charAt
argument_list|(
name|idx
argument_list|)
else|:
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomSharingModel
operator|.
name|sFreeForAllPrefChar
operator|)
expr_stmt|;
name|idx
operator|++
expr_stmt|;
if|if
condition|(
name|pref
operator|==
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomSharingModel
operator|.
name|sNotAvailablePrefChar
condition|)
block|{
name|model
operator|.
name|setOption
argument_list|(
name|d
argument_list|,
name|t
argument_list|,
operator|-
literal|2l
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|pref
operator|==
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomSharingModel
operator|.
name|sFreeForAllPrefChar
condition|)
block|{
name|model
operator|.
name|setOption
argument_list|(
name|d
argument_list|,
name|t
argument_list|,
operator|-
literal|1l
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Long
name|deptId
init|=
operator|(
name|char2dept
operator|==
literal|null
condition|?
literal|null
else|:
name|char2dept
operator|.
name|get
argument_list|(
name|pref
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|deptId
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|deptId
operator|=
operator|new
name|ArrayList
argument_list|<
name|Department
argument_list|>
argument_list|(
name|current
argument_list|)
operator|.
name|get
argument_list|(
name|pref
operator|-
literal|'0'
argument_list|)
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IndexOutOfBoundsException
name|e
parameter_list|)
block|{
block|}
block|}
name|model
operator|.
name|setOption
argument_list|(
name|d
argument_list|,
name|t
argument_list|,
name|deptId
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|e
parameter_list|)
block|{
block|}
catch|catch
parameter_list|(
name|IndexOutOfBoundsException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|editable
operator|&&
operator|!
name|context
operator|.
name|getUser
argument_list|()
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
name|boolean
name|control
init|=
literal|false
decl_stmt|,
name|allDept
init|=
literal|true
decl_stmt|;
for|for
control|(
name|RoomDept
name|rd
range|:
name|location
operator|.
name|getRoomDepts
argument_list|()
control|)
block|{
if|if
condition|(
name|rd
operator|.
name|isControl
argument_list|()
condition|)
name|control
operator|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasQualifier
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|allDept
operator|&&
operator|!
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasQualifier
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
name|allDept
operator|=
literal|false
expr_stmt|;
block|}
name|model
operator|.
name|setDefaultEditable
argument_list|(
name|control
operator|||
name|allDept
argument_list|)
expr_stmt|;
name|model
operator|.
name|setNoteEditable
argument_list|(
name|control
operator|||
name|allDept
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|control
operator|&&
operator|!
name|allDept
condition|)
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
literal|7
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
literal|288
condition|;
name|s
operator|++
control|)
block|{
name|RoomSharingOption
name|option
init|=
name|model
operator|.
name|getOption
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|model
operator|.
name|setEditable
argument_list|(
name|d
argument_list|,
name|s
argument_list|,
name|option
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasQualifier
argument_list|(
operator|new
name|SimpleQualifier
argument_list|(
literal|"Department"
argument_list|,
name|option
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|model
return|;
block|}
specifier|public
name|RoomSharingModel
name|saveRoomSharing
parameter_list|(
name|RoomSharingRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|,
literal|"Location"
argument_list|,
name|Right
operator|.
name|RoomEditAvailability
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|Character
argument_list|>
name|dept2char
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Character
argument_list|>
argument_list|()
decl_stmt|;
name|dept2char
operator|.
name|put
argument_list|(
operator|-
literal|1l
argument_list|,
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomSharingModel
operator|.
name|sFreeForAllPrefChar
argument_list|)
expr_stmt|;
name|dept2char
operator|.
name|put
argument_list|(
operator|-
literal|2l
argument_list|,
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomSharingModel
operator|.
name|sNotAvailablePrefChar
argument_list|)
expr_stmt|;
name|String
name|managerIds
init|=
literal|""
decl_stmt|;
name|char
name|pref
init|=
literal|'0'
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|add
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RoomSharingOption
name|option
range|:
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|getOptions
argument_list|()
control|)
block|{
if|if
condition|(
name|option
operator|.
name|getId
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|managerIds
operator|+=
operator|(
name|managerIds
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|","
operator|)
operator|+
name|option
operator|.
name|getId
argument_list|()
expr_stmt|;
name|dept2char
operator|.
name|put
argument_list|(
name|option
operator|.
name|getId
argument_list|()
argument_list|,
operator|new
name|Character
argument_list|(
name|pref
operator|++
argument_list|)
argument_list|)
expr_stmt|;
name|add
operator|.
name|add
argument_list|(
name|option
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|pattern
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
literal|7
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
literal|288
condition|;
name|s
operator|++
control|)
block|{
name|RoomSharingOption
name|option
init|=
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|getOption
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|pattern
operator|+=
name|dept2char
operator|.
name|get
argument_list|(
name|option
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
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
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|location
operator|.
name|setManagerIds
argument_list|(
name|managerIds
argument_list|)
expr_stmt|;
name|location
operator|.
name|setPattern
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|RoomDept
argument_list|>
name|i
init|=
name|location
operator|.
name|getRoomDepts
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
name|RoomDept
name|rd
init|=
operator|(
name|RoomDept
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|add
operator|.
name|remove
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|remove
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|rd
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Long
name|id
range|:
name|add
control|)
block|{
name|RoomDept
name|rd
init|=
operator|new
name|RoomDept
argument_list|()
decl_stmt|;
name|rd
operator|.
name|setControl
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|rd
operator|.
name|setDepartment
argument_list|(
name|DepartmentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|id
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|add
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|rd
operator|.
name|setRoom
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|add
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rd
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|isNoteEditable
argument_list|()
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|hasNote
argument_list|()
condition|)
name|location
operator|.
name|setShareNote
argument_list|(
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|getNote
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|2048
condition|?
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|getNote
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2048
argument_list|)
else|:
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|location
operator|.
name|setShareNote
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|location
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_DEPT_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
name|location
operator|.
name|getControllingDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
if|if
condition|(
name|ex
operator|instanceof
name|GwtRpcException
condition|)
throw|throw
operator|(
name|GwtRpcException
operator|)
name|ex
throw|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
argument_list|)
throw|;
block|}
block|}
specifier|public
name|RoomSharingModel
name|loadEventAvailability
parameter_list|(
name|RoomSharingRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|,
literal|"Location"
argument_list|,
name|Right
operator|.
name|RoomDetailEventAvailability
argument_list|)
expr_stmt|;
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
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|)
decl_stmt|;
name|RoomSharingModel
name|model
init|=
operator|new
name|RoomSharingModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|setId
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|model
operator|.
name|setName
argument_list|(
name|location
operator|.
name|getLabel
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
literal|true
condition|;
name|i
operator|++
control|)
block|{
name|String
name|mode
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.room.sharingMode"
operator|+
operator|(
literal|1
operator|+
name|i
operator|)
argument_list|,
name|i
operator|<
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
operator|.
name|length
condition|?
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
index|[
name|i
index|]
else|:
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|mode
operator|==
literal|null
operator|||
name|mode
operator|.
name|isEmpty
argument_list|()
condition|)
break|break;
name|model
operator|.
name|addMode
argument_list|(
operator|new
name|RoomInterface
operator|.
name|RoomSharingDisplayMode
argument_list|(
name|mode
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|boolean
name|editable
init|=
name|context
operator|.
name|hasPermission
argument_list|(
name|location
argument_list|,
name|Right
operator|.
name|RoomEditEventAvailability
argument_list|)
decl_stmt|;
name|model
operator|.
name|setDefaultEditable
argument_list|(
name|editable
argument_list|)
expr_stmt|;
name|model
operator|.
name|addOption
argument_list|(
operator|new
name|RoomSharingOption
argument_list|(
operator|-
literal|1l
argument_list|,
literal|"#FFFFFF"
argument_list|,
name|MESSAGES
operator|.
name|codeAvailable
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|legendAvailable
argument_list|()
argument_list|,
name|editable
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|addOption
argument_list|(
operator|new
name|RoomSharingOption
argument_list|(
operator|-
literal|2l
argument_list|,
literal|"#696969"
argument_list|,
name|MESSAGES
operator|.
name|codeNotAvailable
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|legendNotAvailable
argument_list|()
argument_list|,
name|editable
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|defaultGridSize
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridSize
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultGridSize
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|model
operator|.
name|getModes
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|model
operator|.
name|getModes
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|defaultGridSize
argument_list|)
condition|)
block|{
name|model
operator|.
name|setDefaultMode
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|model
operator|.
name|setDefaultHorizontal
argument_list|(
name|CommonValues
operator|.
name|HorizontalGrid
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|setDefaultOption
argument_list|(
name|model
operator|.
name|getOptions
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
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
name|NR_DAYS
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|;
name|t
operator|++
control|)
block|{
name|char
name|pref
init|=
operator|(
name|location
operator|.
name|getEventAvailability
argument_list|()
operator|!=
literal|null
operator|&&
name|idx
operator|<
name|location
operator|.
name|getEventAvailability
argument_list|()
operator|.
name|length
argument_list|()
condition|?
name|location
operator|.
name|getEventAvailability
argument_list|()
operator|.
name|charAt
argument_list|(
name|idx
argument_list|)
else|:
literal|'0'
operator|)
decl_stmt|;
name|idx
operator|++
expr_stmt|;
name|model
operator|.
name|setOption
argument_list|(
name|d
argument_list|,
name|t
argument_list|,
name|pref
operator|==
literal|'0'
condition|?
operator|-
literal|1l
else|:
operator|-
literal|2l
argument_list|)
expr_stmt|;
block|}
return|return
name|model
return|;
block|}
specifier|public
name|RoomSharingModel
name|saveEventAvailability
parameter_list|(
name|RoomSharingRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|,
literal|"Location"
argument_list|,
name|Right
operator|.
name|RoomEditEventAvailability
argument_list|)
expr_stmt|;
name|String
name|availability
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
literal|7
condition|;
name|d
operator|++
control|)
for|for
control|(
name|int
name|s
init|=
literal|0
init|;
name|s
operator|<
literal|288
condition|;
name|s
operator|++
control|)
block|{
name|RoomSharingOption
name|option
init|=
name|request
operator|.
name|getModel
argument_list|()
operator|.
name|getOption
argument_list|(
name|d
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|availability
operator|+=
operator|(
name|option
operator|.
name|getId
argument_list|()
operator|==
operator|-
literal|1l
condition|?
literal|'0'
else|:
literal|'1'
operator|)
expr_stmt|;
block|}
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
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
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|location
operator|.
name|setEventAvailability
argument_list|(
name|availability
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|location
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_DEPT_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
name|location
operator|.
name|getControllingDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
if|if
condition|(
name|ex
operator|instanceof
name|GwtRpcException
condition|)
throw|throw
operator|(
name|GwtRpcException
operator|)
name|ex
throw|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

