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
name|RoomFeatureEditForm
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
name|GlobalRoomFeature
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
name|RoomFeature
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
name|DepartmentRoomFeatureDAO
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
name|GlobalRoomFeatureDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 06-27-2006  *   * XDoclet definition:  * @struts.action path="/roomFeatureAdd" name="roomFeatureEditForm" input="/admin/roomFeatureAdd.jsp" scope="request"  * @struts.action-forward name="showAdd" path="roomFeatureAddTile"  * @struts.action-forward name="showRoomFeatureList" path="/roomFeatureList.do"  */
end_comment

begin_class
specifier|public
class|class
name|RoomFeatureAddAction
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
name|RoomFeatureEditForm
name|roomFeatureEditForm
init|=
operator|(
name|RoomFeatureEditForm
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
name|roomFeatureEditForm
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
name|roomFeatureEditForm
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
name|roomFeatureEditForm
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
literal|"showRoomFeatureList"
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
literal|"button.returnToRoomFeatureList"
argument_list|)
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomFeatureList"
argument_list|)
return|;
block|}
block|}
comment|//get depts owned by user
name|setDeptList
argument_list|(
name|request
argument_list|,
name|roomFeatureEditForm
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
operator|||
name|user
operator|.
name|getRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
argument_list|)
condition|)
block|{
name|roomFeatureEditForm
operator|.
name|setGlobal
argument_list|(
name|roomFeatureEditForm
operator|.
name|getDeptCode
argument_list|()
operator|==
literal|null
operator|||
name|roomFeatureEditForm
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
operator|||
name|roomFeatureEditForm
operator|.
name|getDeptCode
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"exam"
argument_list|)
operator|||
name|roomFeatureEditForm
operator|.
name|getDeptCode
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"eexam"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|roomFeatureEditForm
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
comment|/** 	 *  	 * @param mapping 	 * @param roomFeatureEditForm 	 * @param request 	 * @param response 	 */
specifier|private
name|void
name|save
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|RoomFeatureEditForm
name|roomFeatureEditForm
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
comment|//if roomFeature is global
if|if
condition|(
name|roomFeatureEditForm
operator|.
name|isGlobal
argument_list|()
condition|)
block|{
name|GlobalRoomFeatureDAO
name|gdao
init|=
operator|new
name|GlobalRoomFeatureDAO
argument_list|()
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
name|gdao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
name|GlobalRoomFeature
name|rf
init|=
operator|new
name|GlobalRoomFeature
argument_list|()
decl_stmt|;
name|rf
operator|.
name|setLabel
argument_list|(
name|roomFeatureEditForm
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|rf
operator|.
name|setAbbv
argument_list|(
name|roomFeatureEditForm
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rf
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
name|rf
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_FEATURE_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
literal|null
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
name|rf
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
name|rf
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
else|else
block|{
name|DepartmentRoomFeatureDAO
name|ddao
init|=
operator|new
name|DepartmentRoomFeatureDAO
argument_list|()
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
name|ddao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
name|DepartmentRoomFeature
name|rf
init|=
operator|new
name|DepartmentRoomFeature
argument_list|()
decl_stmt|;
name|rf
operator|.
name|setLabel
argument_list|(
name|roomFeatureEditForm
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|rf
operator|.
name|setAbbv
argument_list|(
name|roomFeatureEditForm
operator|.
name|getAbbv
argument_list|()
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
name|rf
operator|.
name|setDepartment
argument_list|(
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|roomFeatureEditForm
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rf
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
operator|(
name|RoomFeature
operator|)
name|rf
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|ROOM_FEATURE_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
name|rf
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
name|rf
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
name|rf
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
comment|/** 	 *  	 * @param request 	 * @param roomFeatureEditForm 	 * @throws Exception  	 */
specifier|private
name|void
name|setDeptList
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|RoomFeatureEditForm
name|roomFeatureEditForm
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
operator|||
name|user
operator|.
name|getRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
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
name|roomFeatureEditForm
operator|.
name|setDeptSize
argument_list|(
name|departments
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|roomFeatureEditForm
operator|.
name|setDeptCode
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|ArrayList
name|list
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
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
name|roomFeatureEditForm
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
name|roomFeatureEditForm
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
block|}
end_class

end_unit

