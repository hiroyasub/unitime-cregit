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
name|ArrayList
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
name|LabelValueBean
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
name|form
operator|.
name|RoomGroupEditForm
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
name|RoomGroupDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 06-28-2006  *   * XDoclet definition:  * @struts.action path="/roomGroupAdd" name="roomGroupEditForm" input="/admin/roomGroupAdd.jsp" scope="request"  * @struts.action-forward name="showRoomGroupList" path="/roomGroupList.do"  * @struts.action-forward name="showAdd" path="roomGroupAddTile"  */
end_comment

begin_class
specifier|public
class|class
name|RoomGroupAddAction
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
name|RoomGroupEditForm
name|roomGroupEditForm
init|=
operator|(
name|RoomGroupEditForm
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
name|roomGroupEditForm
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
comment|//add new
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
literal|"button.addNew"
argument_list|)
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|errors
operator|=
name|roomGroupEditForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|save
argument_list|(
name|mapping
argument_list|,
name|roomGroupEditForm
argument_list|,
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomGroupList"
argument_list|)
return|;
block|}
else|else
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
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
literal|"button.returnToRoomGroupList"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomGroupList"
argument_list|)
return|;
block|}
block|}
comment|//get depts owned by user
name|setDeptList
argument_list|(
name|request
argument_list|,
name|roomGroupEditForm
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|user
operator|.
name|getRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|ADMIN_ROLE
argument_list|)
condition|)
block|{
name|roomGroupEditForm
operator|.
name|setGlobal
argument_list|(
name|roomGroupEditForm
operator|.
name|getDeptCode
argument_list|()
operator|==
literal|null
operator|||
name|roomGroupEditForm
operator|.
name|getDeptCode
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|roomGroupEditForm
operator|.
name|setGlobal
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showAdd"
argument_list|)
return|;
block|}
specifier|private
name|void
name|save
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|RoomGroupEditForm
name|roomGroupEditForm
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
name|HttpSession
name|webSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
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
name|getSessionId
argument_list|()
decl_stmt|;
comment|//create new roomGroup
name|LocationDAO
name|rdao
init|=
operator|new
name|LocationDAO
argument_list|()
decl_stmt|;
name|RoomGroupDAO
name|rgdao
init|=
operator|new
name|RoomGroupDAO
argument_list|()
decl_stmt|;
name|RoomGroup
name|rg
init|=
operator|new
name|RoomGroup
argument_list|()
decl_stmt|;
name|rg
operator|.
name|setName
argument_list|(
name|roomGroupEditForm
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|rg
operator|.
name|setAbbv
argument_list|(
name|roomGroupEditForm
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|rg
operator|.
name|setSession
argument_list|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|rg
operator|.
name|setDescription
argument_list|(
name|roomGroupEditForm
operator|.
name|getDesc
argument_list|()
argument_list|)
expr_stmt|;
name|rg
operator|.
name|setGlobal
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|roomGroupEditForm
operator|.
name|isGlobal
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|rg
operator|.
name|setDefaultGroup
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|roomGroupEditForm
operator|.
name|isDeft
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
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
name|Set
name|depts
init|=
name|owner
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|roomGroupEditForm
operator|.
name|isGlobal
argument_list|()
condition|)
block|{
name|Department
name|d
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|roomGroupEditForm
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
name|rg
operator|.
name|setDepartment
argument_list|(
name|d
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
name|rgdao
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
name|checkDefault
argument_list|(
name|hibSession
argument_list|,
name|rg
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rg
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|rg
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_GROUP_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
name|rg
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|refresh
argument_list|(
name|rg
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
literal|"A"
operator|+
name|rg
operator|.
name|getUniqueId
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
try|try
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
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
block|}
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	 *  	 * @param request 	 * @param roomFeatureEditForm 	 * @throws Exception  	 */
specifier|private
name|void
name|setDeptList
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|RoomGroupEditForm
name|roomGroupEditForm
parameter_list|)
throws|throws
name|Exception
block|{
name|HttpSession
name|webSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
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
name|boolean
name|isAdmin
init|=
name|user
operator|.
name|getRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|ADMIN_ROLE
argument_list|)
decl_stmt|;
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
name|manager
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
name|Set
name|departments
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|.
name|getRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|ADMIN_ROLE
argument_list|)
condition|)
block|{
name|departments
operator|=
name|Department
operator|.
name|findAllBeingUsed
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|departments
operator|=
operator|new
name|TreeSet
argument_list|(
name|manager
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//Set departments = new TreeSet(manager.departmentsForSession(sessionId));
name|roomGroupEditForm
operator|.
name|setDeptSize
argument_list|(
name|departments
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|ArrayList
name|list
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|roomGroupEditForm
operator|.
name|setDeptCode
argument_list|(
literal|null
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|departments
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|dept
init|=
operator|(
name|Department
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|dept
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
condition|)
continue|continue;
name|list
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|dept
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|dept
operator|.
name|getName
argument_list|()
argument_list|,
name|dept
operator|.
name|getDeptCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|list
argument_list|)
expr_stmt|;
comment|//set default department
if|if
condition|(
operator|!
name|isAdmin
operator|&&
operator|(
name|departments
operator|.
name|size
argument_list|()
operator|==
literal|1
operator|)
condition|)
block|{
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|departments
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|roomGroupEditForm
operator|.
name|setDeptCode
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|webSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|)
operator|!=
literal|null
operator|&&
operator|!
literal|"All"
operator|.
name|equalsIgnoreCase
argument_list|(
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
argument_list|)
operator|&&
operator|!
literal|"Exam"
operator|.
name|equalsIgnoreCase
argument_list|(
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
argument_list|)
condition|)
block|{
name|roomGroupEditForm
operator|.
name|setDeptCode
argument_list|(
name|webSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 *  	 * @param hibSession 	 * @param rg 	 */
specifier|public
name|void
name|checkDefault
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|RoomGroup
name|rg
parameter_list|)
block|{
if|if
condition|(
operator|!
name|rg
operator|.
name|isDefaultGroup
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
return|return;
for|for
control|(
name|Iterator
name|i
init|=
operator|(
operator|new
name|RoomGroupDAO
argument_list|()
operator|)
operator|.
name|findAll
argument_list|(
name|hibSession
argument_list|)
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
name|x
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
name|x
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rg
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|x
operator|.
name|isDefaultGroup
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|x
operator|.
name|setDefaultGroup
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

