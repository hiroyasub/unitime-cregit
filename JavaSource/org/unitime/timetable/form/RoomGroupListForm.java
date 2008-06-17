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
name|form
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
name|Collection
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
name|ActionErrors
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
comment|/**   * MyEclipse Struts  * Creation date: 05-02-2006  *   * XDoclet definition:  * @struts.form name="roomGroupListForm"  */
end_comment

begin_class
specifier|public
class|class
name|RoomGroupListForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|4644102545584357862L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|Collection
name|roomGroups
decl_stmt|;
specifier|private
name|String
name|deptCode
decl_stmt|;
specifier|private
name|boolean
name|deptSize
decl_stmt|;
specifier|private
name|boolean
name|canAdd
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/** 	 *  	 */
specifier|public
name|String
name|getDeptCodeX
parameter_list|()
block|{
return|return
name|deptCode
return|;
block|}
specifier|public
name|void
name|setDeptCodeX
parameter_list|(
name|String
name|deptCode
parameter_list|)
block|{
name|this
operator|.
name|deptCode
operator|=
name|deptCode
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDeptSize
parameter_list|()
block|{
return|return
name|deptSize
return|;
block|}
specifier|public
name|void
name|setDeptSize
parameter_list|(
name|boolean
name|deptSize
parameter_list|)
block|{
name|this
operator|.
name|deptSize
operator|=
name|deptSize
expr_stmt|;
block|}
comment|/**  	 * Method validate 	 * @param mapping 	 * @param request 	 * @return ActionErrors 	 */
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
comment|/*         if(deptCode==null || deptCode.equalsIgnoreCase("")) {         	errors.add("deptCode",                      new ActionMessage("errors.required", "Department") );         }        */
return|return
name|errors
return|;
block|}
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 */
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
name|roomGroups
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
name|deptSize
operator|=
name|displayDeptList
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|canAdd
operator|=
literal|false
expr_stmt|;
try|try
block|{
name|setDeptAttr
argument_list|(
name|request
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
specifier|public
name|void
name|setRoomGroups
parameter_list|(
name|Collection
name|roomGroups
parameter_list|)
block|{
name|this
operator|.
name|roomGroups
operator|=
name|roomGroups
expr_stmt|;
block|}
specifier|public
name|Collection
name|getRoomGroups
parameter_list|()
block|{
return|return
name|roomGroups
return|;
block|}
comment|/** 	 *  	 * @param request 	 * @return 	 */
specifier|private
name|boolean
name|displayDeptList
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|deptSize
operator|=
literal|true
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
name|Long
name|sessionId
init|=
operator|(
name|Long
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SESSION_ID_ATTR_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
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
operator|&&
operator|!
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
name|TimetableManager
name|mgr
init|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|Set
name|mgrDepts
init|=
name|Department
operator|.
name|findAllOwned
argument_list|(
name|sessionId
argument_list|,
name|mgr
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|mgrDepts
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|deptSize
operator|=
literal|false
expr_stmt|;
block|}
block|}
return|return
name|deptSize
return|;
block|}
comment|/** 	 *  	 * @param request 	 * @throws Exception 	 */
specifier|private
name|void
name|setDeptAttr
parameter_list|(
name|HttpServletRequest
name|request
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
name|ArrayList
name|departments
init|=
operator|new
name|ArrayList
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
name|TreeSet
name|depts
init|=
literal|null
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
name|depts
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
name|depts
operator|=
name|Department
operator|.
name|findAllOwned
argument_list|(
name|sessionId
argument_list|,
name|owner
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
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
name|String
name|code
init|=
name|d
operator|.
name|getDeptCode
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|abbv
init|=
name|d
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|departments
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|code
operator|+
literal|" - "
operator|+
name|abbv
operator|+
literal|" ("
operator|+
name|d
operator|.
name|getExternalMgrLabel
argument_list|()
operator|+
literal|")"
argument_list|,
name|code
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|departments
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|code
operator|+
literal|" - "
operator|+
name|abbv
argument_list|,
name|code
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|departments
argument_list|)
expr_stmt|;
if|if
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
condition|)
block|{
name|deptCode
operator|=
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
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setCanAdd
parameter_list|(
name|boolean
name|canAdd
parameter_list|)
block|{
name|this
operator|.
name|canAdd
operator|=
name|canAdd
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanAdd
parameter_list|()
block|{
return|return
name|canAdd
return|;
block|}
block|}
end_class

end_unit

