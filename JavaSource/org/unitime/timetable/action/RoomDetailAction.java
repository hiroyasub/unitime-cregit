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
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|Set
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
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|MessageResources
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
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|form
operator|.
name|RoomDetailForm
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
name|Assignment
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
name|DepartmentRoomFeature
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
name|NonUniversityLocation
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
name|model
operator|.
name|Room
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
name|RoomGroup
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
name|RoomPref
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
name|TimetableManagerDAO
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
name|util
operator|.
name|LookupTables
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
comment|/**   * MyEclipse Struts  * Creation date: 05-12-2006  *   * XDoclet definition:  * @struts.action path="/roomDetail" name="roomDetailForm" input="/admin/roomDetail.jsp" scope="request"  * @struts.action-forward name="showEditRoomFeaure" path="/editRoomFeature.do"  * @struts.action-forward name="showRoomList" path="/roomList.do"  * @struts.action-forward name="showEditRoomGroup" path="/editRoomGroup.do"  * @struts.action-forward name="showEditRoomPref" path="/editRoomPref.do"  * @struts.action-forward name="showEditRoomDept" path="/editRoomDept.do"  */
end_comment

begin_class
specifier|public
class|class
name|RoomDetailAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
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
name|RoomDetailForm
name|roomDetailForm
init|=
operator|(
name|RoomDetailForm
operator|)
name|form
decl_stmt|;
name|HttpSession
name|webSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|webSession
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
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|String
name|doit
init|=
name|roomDetailForm
operator|.
name|getDoit
argument_list|()
decl_stmt|;
if|if
condition|(
name|doit
operator|!=
literal|null
condition|)
block|{
comment|//delete location
if|if
condition|(
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.delete"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"y"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"confirm"
argument_list|)
argument_list|)
condition|)
block|{
name|doDelete
argument_list|(
name|roomDetailForm
argument_list|,
name|request
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomList"
argument_list|)
return|;
block|}
block|}
comment|//return to room list
if|if
condition|(
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.returnToRoomList"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|roomDetailForm
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
literal|"A"
operator|+
name|roomDetailForm
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomList"
argument_list|)
return|;
block|}
comment|//modify room
if|if
condition|(
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.modifyRoom"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEditRoom"
argument_list|)
return|;
block|}
comment|//modify room departments
if|if
condition|(
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.modifyRoomDepts"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEditRoomDept"
argument_list|)
return|;
block|}
comment|//modify room groups
if|if
condition|(
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.modifyRoomGroups"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEditRoomGroup"
argument_list|)
return|;
block|}
comment|//modify room features
if|if
condition|(
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.modifyRoomFeatures"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEditRoomFeature"
argument_list|)
return|;
block|}
comment|//modify room preferences
if|if
condition|(
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.modifyRoomPreference"
argument_list|)
argument_list|)
operator|||
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addRoomPreference"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEditRoomPref"
argument_list|)
return|;
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|==
literal|null
operator|&&
name|roomDetailForm
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomList"
argument_list|)
return|;
comment|//get location
name|Long
name|id
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|!=
literal|null
condition|?
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
else|:
name|roomDetailForm
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
name|LocationDAO
name|ldao
init|=
operator|new
name|LocationDAO
argument_list|()
decl_stmt|;
name|Location
name|location
init|=
name|ldao
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
block|{
name|roomDetailForm
operator|.
name|setNonUniv
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|roomDetailForm
operator|.
name|setNonUniv
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|//set roomSharingTable and user preference on location in form
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|webSession
argument_list|)
decl_stmt|;
name|Session
name|s
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|String
name|mgrId
init|=
operator|(
name|String
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|TMTBL_MGR_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|TimetableManagerDAO
name|tdao
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
decl_stmt|;
name|TimetableManager
name|owner
init|=
name|tdao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|mgrId
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|timeVertical
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridVertical
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|RequiredTimeTable
name|rtt
init|=
name|location
operator|.
name|getRoomSharingTable
argument_list|(
name|s
argument_list|,
name|user
argument_list|)
decl_stmt|;
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|setDefaultSelection
argument_list|(
name|RequiredTimeTable
operator|.
name|getTimeGridSize
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setSharingTable
argument_list|(
name|rtt
operator|.
name|print
argument_list|(
literal|false
argument_list|,
name|timeVertical
argument_list|)
argument_list|)
expr_stmt|;
comment|//get department
name|Long
name|sessionId
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getUniqueId
argument_list|()
decl_stmt|;
name|String
name|deptCode
init|=
operator|(
name|String
operator|)
name|webSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|)
decl_stmt|;
comment|//get room preferences
name|Vector
name|depts
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|roomDetailForm
operator|.
name|setEditable
argument_list|(
name|user
operator|.
name|isAdmin
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
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
name|depts
operator|.
name|add
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
condition|)
name|roomDetailForm
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|depts
argument_list|)
expr_stmt|;
name|Vector
name|prefs
init|=
operator|new
name|Vector
argument_list|(
name|depts
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|depts
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|PreferenceLevel
name|pref
init|=
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sNeutral
argument_list|)
decl_stmt|;
name|Set
name|roomPrefs
init|=
name|d
operator|.
name|getEffectiveRoomPreferences
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|roomPrefs
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomPref
name|rp
init|=
operator|(
name|RoomPref
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rp
operator|.
name|getRoom
argument_list|()
operator|.
name|equals
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|pref
operator|=
name|rp
operator|.
name|getPrefLevel
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
operator|.
name|equals
argument_list|(
name|PreferenceLevel
operator|.
name|sNeutral
argument_list|)
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
else|else
name|prefs
operator|.
name|addElement
argument_list|(
name|pref
argument_list|)
expr_stmt|;
block|}
name|roomDetailForm
operator|.
name|setDepts
argument_list|(
name|depts
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setRoomPrefs
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupPrefLevels
argument_list|(
name|request
argument_list|)
expr_stmt|;
comment|//set location information in form
name|roomDetailForm
operator|.
name|setCapacity
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setCoordinateX
argument_list|(
name|location
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setCoordinateY
argument_list|(
name|location
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setIgnoreTooFar
argument_list|(
name|location
operator|.
name|isIgnoreTooFar
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
name|location
operator|.
name|isIgnoreTooFar
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|location
operator|.
name|isIgnoreRoomCheck
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setPatterns
argument_list|(
name|location
operator|.
name|getPattern
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setGlobalFeatures
argument_list|(
operator|new
name|TreeSet
argument_list|(
name|location
operator|.
name|getGlobalRoomFeatures
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setDepartmentFeatures
argument_list|(
operator|new
name|TreeSet
argument_list|(
name|location
operator|.
name|getDepartmentRoomFeatures
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|roomDetailForm
operator|.
name|getDepartmentFeatures
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
name|DepartmentRoomFeature
name|drf
init|=
operator|(
name|DepartmentRoomFeature
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|boolean
name|skip
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|location
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
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
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|drf
operator|.
name|getDepartment
argument_list|()
operator|.
name|equals
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
block|{
name|skip
operator|=
literal|false
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|skip
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|roomDetailForm
operator|.
name|setGroups
argument_list|(
operator|new
name|TreeSet
argument_list|(
name|location
operator|.
name|getRoomGroups
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|roomDetailForm
operator|.
name|getGroups
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
name|RoomGroup
name|rg
init|=
operator|(
name|RoomGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rg
operator|.
name|isGlobal
argument_list|()
condition|)
continue|continue;
name|boolean
name|skip
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|location
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
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
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rg
operator|.
name|getDepartment
argument_list|()
operator|.
name|equals
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
block|{
name|skip
operator|=
literal|false
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|skip
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
block|{
name|Room
name|r
init|=
operator|(
name|Room
operator|)
name|location
decl_stmt|;
name|roomDetailForm
operator|.
name|setName
argument_list|(
name|r
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setType
argument_list|(
name|r
operator|.
name|getScheduledRoomType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"genClassroom"
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getScheduledRoomType
argument_list|()
argument_list|)
condition|)
block|{
name|roomDetailForm
operator|.
name|setTypeName
argument_list|(
literal|"Classroom"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"computingLab"
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getScheduledRoomType
argument_list|()
argument_list|)
condition|)
block|{
name|roomDetailForm
operator|.
name|setTypeName
argument_list|(
literal|"Computing Laboratory"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"departmental"
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getScheduledRoomType
argument_list|()
argument_list|)
condition|)
block|{
name|roomDetailForm
operator|.
name|setTypeName
argument_list|(
literal|"Additional Instructional Room"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"specialUse"
operator|.
name|equals
argument_list|(
name|r
operator|.
name|getScheduledRoomType
argument_list|()
argument_list|)
condition|)
block|{
name|roomDetailForm
operator|.
name|setTypeName
argument_list|(
literal|"Special Use Room"
argument_list|)
expr_stmt|;
block|}
else|else
name|roomDetailForm
operator|.
name|setTypeName
argument_list|(
name|r
operator|.
name|getScheduledRoomType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|r
operator|.
name|getScheduledRoomType
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"specialUse"
argument_list|)
condition|)
block|{
name|roomDetailForm
operator|.
name|setDeleteFlag
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|roomDetailForm
operator|.
name|setDeleteFlag
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|roomDetailForm
operator|.
name|setExternalId
argument_list|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|?
name|r
operator|.
name|getExternalUniqueId
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|location
operator|instanceof
name|NonUniversityLocation
condition|)
block|{
name|NonUniversityLocation
name|nonUnivLocation
init|=
operator|(
name|NonUniversityLocation
operator|)
name|location
decl_stmt|;
name|roomDetailForm
operator|.
name|setName
argument_list|(
name|nonUnivLocation
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setType
argument_list|(
literal|"Non Univeristy Location"
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setDeleteFlag
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setExternalId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setTypeName
argument_list|(
literal|"Non-University Location"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"roomDetail"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.lookup.notFound"
argument_list|,
literal|"Room"
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
name|roomDetailForm
operator|.
name|setOwner
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|roomDetailForm
operator|.
name|setControl
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Set
name|ownedDepts
init|=
name|owner
operator|.
name|departmentsForSession
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|boolean
name|controls
init|=
literal|false
decl_stmt|;
name|boolean
name|allDepts
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Iterator
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
name|rd
operator|.
name|isControl
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
name|roomDetailForm
operator|.
name|setControl
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|rd
operator|.
name|isControl
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|ownedDepts
operator|!=
literal|null
operator|&&
name|ownedDepts
operator|.
name|contains
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
name|controls
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|ownedDepts
operator|==
literal|null
operator|||
operator|!
name|ownedDepts
operator|.
name|contains
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
block|{
name|allDepts
operator|=
literal|false
expr_stmt|;
block|}
block|}
name|roomDetailForm
operator|.
name|setOwner
argument_list|(
name|controls
operator|||
name|allDepts
argument_list|)
expr_stmt|;
name|EditRoomAction
operator|.
name|setupDepartments
argument_list|(
name|request
argument_list|,
name|location
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomDetail"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param roomDetailForm 	 * @param request 	 */
specifier|private
name|void
name|doDelete
parameter_list|(
name|RoomDetailForm
name|roomDetailForm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
comment|//get location
name|Long
name|id
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
decl_stmt|;
name|LocationDAO
name|ldao
init|=
operator|new
name|LocationDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|ldao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|Location
name|location
init|=
name|ldao
operator|.
name|get
argument_list|(
name|id
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|!=
literal|null
condition|)
block|{
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|location
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
name|location
operator|.
name|getControllingDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|List
name|roomPrefs
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"room.uniqueId"
argument_list|,
name|id
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
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
name|Department
name|d
init|=
name|rd
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|remove
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|roomPrefs
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
name|RoomPref
name|rp
init|=
operator|(
name|RoomPref
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|rp
operator|.
name|getOwner
argument_list|()
operator|.
name|getPreferences
argument_list|()
operator|.
name|remove
argument_list|(
name|rp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|rp
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rp
operator|.
name|getOwner
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|location
operator|.
name|getAssignments
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
name|Assignment
name|a
init|=
operator|(
name|Assignment
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|a
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|a
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|hibSession
operator|.
name|delete
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
block|}
end_class

end_unit

