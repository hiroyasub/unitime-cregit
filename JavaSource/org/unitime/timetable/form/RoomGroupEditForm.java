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
name|form
package|;
end_package

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
name|action
operator|.
name|ActionMessage
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
comment|/**   * MyEclipse Struts  * Creation date: 05-02-2006  *   * XDoclet definition:  * @struts.form name="roomGroupEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|RoomGroupEditForm
extends|extends
name|ActionForm
block|{
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|id
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|abbv
decl_stmt|;
specifier|private
name|boolean
name|global
decl_stmt|;
specifier|private
name|boolean
name|deft
decl_stmt|;
specifier|private
name|boolean
name|feature
decl_stmt|;
specifier|private
name|String
name|desc
decl_stmt|;
specifier|private
name|Collection
name|assignedRooms
decl_stmt|;
specifier|private
name|Collection
name|notAssignedRooms
decl_stmt|;
specifier|private
name|String
index|[]
name|assignedSelected
init|=
block|{}
decl_stmt|;
specifier|private
name|String
index|[]
name|notAssignedSelected
init|=
block|{}
decl_stmt|;
specifier|private
name|String
index|[]
name|heading
decl_stmt|;
specifier|private
name|Collection
name|roomFeatures
decl_stmt|;
specifier|private
name|boolean
name|showDeptSelection
decl_stmt|;
specifier|private
name|int
name|deptSize
decl_stmt|;
specifier|private
name|String
name|doit
decl_stmt|;
specifier|private
name|String
name|deptCode
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|82818547444631422L
decl_stmt|;
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
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"roomGroup"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|abbv
operator|==
literal|null
operator|||
name|abbv
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"roomGroup"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
block|}
try|try
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|RoomGroup
operator|.
name|getAllGlobalRoomGroups
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
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
operator|&&
operator|!
name|rg
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Department
name|dept
init|=
operator|(
name|deptCode
operator|==
literal|null
condition|?
literal|null
else|:
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|deptCode
argument_list|,
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
operator|.
name|getSessionId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|RoomGroup
operator|.
name|getAllDepartmentRoomGroups
argument_list|(
name|dept
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
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
operator|&&
operator|!
name|rg
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
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
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|deptSize
operator|!=
literal|1
condition|)
block|{
if|if
condition|(
name|deptCode
operator|==
literal|null
operator|||
name|deptCode
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"Department"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Department"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|errors
return|;
block|}
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 * @throws Exception  	 */
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
name|name
operator|=
literal|""
expr_stmt|;
name|setDeptSize
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|displayDeptSelection
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 * @param request 	 */
specifier|private
name|void
name|setDeptSize
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|HttpSession
name|httpSession
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
name|httpSession
argument_list|)
decl_stmt|;
name|Long
name|sessionId
decl_stmt|;
try|try
block|{
name|sessionId
operator|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getUniqueId
argument_list|()
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
name|manager
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
name|setDeptSize
argument_list|(
name|departments
operator|.
name|size
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
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** 	 *  	 * 	 */
specifier|public
name|void
name|setRooms
parameter_list|()
block|{
name|int
name|i
init|=
literal|0
decl_stmt|;
name|Iterator
name|iter
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|assignedRooms
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|assignedSelection
init|=
operator|new
name|String
index|[
name|assignedRooms
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|iter
operator|=
name|assignedRooms
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
name|Location
name|r
init|=
operator|(
name|Location
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|assignedSelection
index|[
name|i
index|]
operator|=
name|r
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|assignedSelected
operator|=
name|assignedSelection
expr_stmt|;
block|}
block|}
specifier|public
name|Collection
name|getAssignedRooms
parameter_list|()
block|{
return|return
name|assignedRooms
return|;
block|}
specifier|public
name|void
name|setAssignedRooms
parameter_list|(
name|Collection
name|assignedRooms
parameter_list|)
block|{
name|this
operator|.
name|assignedRooms
operator|=
name|assignedRooms
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDeft
parameter_list|()
block|{
return|return
name|deft
return|;
block|}
specifier|public
name|void
name|setDeft
parameter_list|(
name|boolean
name|deft
parameter_list|)
block|{
name|this
operator|.
name|deft
operator|=
name|deft
expr_stmt|;
block|}
specifier|public
name|String
name|getDesc
parameter_list|()
block|{
return|return
name|desc
return|;
block|}
specifier|public
name|void
name|setDesc
parameter_list|(
name|String
name|desc
parameter_list|)
block|{
name|this
operator|.
name|desc
operator|=
name|desc
expr_stmt|;
block|}
specifier|public
name|boolean
name|isGlobal
parameter_list|()
block|{
return|return
name|global
return|;
block|}
specifier|public
name|void
name|setGlobal
parameter_list|(
name|boolean
name|global
parameter_list|)
block|{
name|this
operator|.
name|global
operator|=
name|global
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|Collection
name|getNotAssignedRooms
parameter_list|()
block|{
return|return
name|notAssignedRooms
return|;
block|}
specifier|public
name|void
name|setNotAssignedRooms
parameter_list|(
name|Collection
name|notAssignedRooms
parameter_list|)
block|{
name|this
operator|.
name|notAssignedRooms
operator|=
name|notAssignedRooms
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getAssignedSelected
parameter_list|()
block|{
return|return
name|assignedSelected
return|;
block|}
specifier|public
name|void
name|setAssignedSelected
parameter_list|(
name|String
index|[]
name|assignedSelected
parameter_list|)
block|{
name|this
operator|.
name|assignedSelected
operator|=
name|assignedSelected
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getNotAssignedSelected
parameter_list|()
block|{
return|return
name|notAssignedSelected
return|;
block|}
specifier|public
name|void
name|setNotAssignedSelected
parameter_list|(
name|String
index|[]
name|notAssignedSelected
parameter_list|)
block|{
name|this
operator|.
name|notAssignedSelected
operator|=
name|notAssignedSelected
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getHeading
parameter_list|()
block|{
return|return
name|heading
return|;
block|}
specifier|public
name|void
name|setHeading
parameter_list|(
name|String
index|[]
name|heading
parameter_list|)
block|{
name|this
operator|.
name|heading
operator|=
name|heading
expr_stmt|;
block|}
specifier|public
name|Collection
name|getRoomFeatures
parameter_list|()
block|{
return|return
name|roomFeatures
return|;
block|}
specifier|public
name|void
name|setRoomFeatures
parameter_list|(
name|Collection
name|roomFeatures
parameter_list|)
block|{
name|this
operator|.
name|roomFeatures
operator|=
name|roomFeatures
expr_stmt|;
block|}
specifier|public
name|boolean
name|isFeature
parameter_list|()
block|{
return|return
name|feature
return|;
block|}
specifier|public
name|void
name|setFeature
parameter_list|(
name|boolean
name|feature
parameter_list|)
block|{
name|this
operator|.
name|feature
operator|=
name|feature
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowDeptSelection
parameter_list|()
block|{
return|return
name|showDeptSelection
return|;
block|}
specifier|public
name|void
name|setShowDeptSelection
parameter_list|(
name|boolean
name|showDeptSelection
parameter_list|)
block|{
name|this
operator|.
name|showDeptSelection
operator|=
name|showDeptSelection
expr_stmt|;
block|}
comment|/** 	 *  	 * @param request 	 * @throws Exception 	 */
specifier|public
name|void
name|displayDeptSelection
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|showDeptSelection
operator|=
literal|false
expr_stmt|;
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
condition|)
block|{
try|try
block|{
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
if|if
condition|(
name|owner
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
name|showDeptSelection
operator|=
literal|true
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|int
name|getDeptSize
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
name|int
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
specifier|public
name|String
name|getDoit
parameter_list|()
block|{
return|return
name|doit
return|;
block|}
specifier|public
name|void
name|setDoit
parameter_list|(
name|String
name|doit
parameter_list|)
block|{
name|this
operator|.
name|doit
operator|=
name|doit
expr_stmt|;
block|}
specifier|public
name|String
name|getDeptCode
parameter_list|()
block|{
return|return
name|deptCode
return|;
block|}
specifier|public
name|void
name|setDeptCode
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
name|String
name|getAbbv
parameter_list|()
block|{
return|return
name|abbv
return|;
block|}
specifier|public
name|void
name|setAbbv
parameter_list|(
name|String
name|abbv
parameter_list|)
block|{
name|this
operator|.
name|abbv
operator|=
name|abbv
expr_stmt|;
block|}
comment|/** 	 *  	 * @param deptCode 	 * @param request 	 * @return 	 * @throws Exception 	 */
specifier|public
name|String
name|getDeptName
parameter_list|(
name|String
name|deptCode
parameter_list|,
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
name|getUniqueId
argument_list|()
decl_stmt|;
name|Department
name|d
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|deptCode
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
return|return
name|d
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|d
operator|.
name|getName
argument_list|()
return|;
block|}
block|}
end_class

end_unit

