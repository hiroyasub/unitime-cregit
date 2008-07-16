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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-05-2006  *   * XDoclet definition:  * @struts.form name="nonUnivLocationForm"  */
end_comment

begin_class
specifier|public
class|class
name|NonUnivLocationForm
extends|extends
name|ActionForm
block|{
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|doit
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|capacity
decl_stmt|;
specifier|private
name|boolean
name|ignoreTooFar
decl_stmt|;
specifier|private
name|boolean
name|ignoreRoomCheck
decl_stmt|;
specifier|private
name|String
name|deptCode
decl_stmt|;
specifier|private
name|int
name|deptSize
decl_stmt|;
specifier|private
name|Long
name|type
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|683878933677076553L
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
literal|"Name"
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
name|capacity
operator|==
literal|null
operator|||
name|capacity
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
literal|"Capacity"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Capacity"
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
name|name
operator|=
literal|""
expr_stmt|;
name|ignoreTooFar
operator|=
literal|false
expr_stmt|;
name|ignoreRoomCheck
operator|=
literal|false
expr_stmt|;
try|try
block|{
name|setDeptSize
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
block|}
block|}
comment|/** 	 *  	 * @param request 	 */
specifier|private
name|void
name|setDeptSize
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|deptSize
operator|=
literal|0
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
name|deptSize
operator|=
name|mgrDepts
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Set
name|mgrDepts
init|=
name|Department
operator|.
name|findAllBeingUsed
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
name|deptSize
operator|=
name|mgrDepts
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|getCapacity
parameter_list|()
block|{
return|return
name|capacity
return|;
block|}
specifier|public
name|void
name|setCapacity
parameter_list|(
name|String
name|capacity
parameter_list|)
block|{
name|this
operator|.
name|capacity
operator|=
name|capacity
expr_stmt|;
block|}
specifier|public
name|boolean
name|isIgnoreTooFar
parameter_list|()
block|{
return|return
name|ignoreTooFar
return|;
block|}
specifier|public
name|void
name|setIgnoreTooFar
parameter_list|(
name|boolean
name|ignoreTooFar
parameter_list|)
block|{
name|this
operator|.
name|ignoreTooFar
operator|=
name|ignoreTooFar
expr_stmt|;
block|}
specifier|public
name|boolean
name|isIgnoreRoomCheck
parameter_list|()
block|{
return|return
name|ignoreRoomCheck
return|;
block|}
specifier|public
name|void
name|setIgnoreRoomCheck
parameter_list|(
name|boolean
name|ignoreRoomCheck
parameter_list|)
block|{
name|this
operator|.
name|ignoreRoomCheck
operator|=
name|ignoreRoomCheck
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
name|dept
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
if|if
condition|(
name|dept
operator|==
literal|null
condition|)
block|{
return|return
name|deptCode
operator|+
literal|" - Unknown"
return|;
block|}
return|return
name|dept
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|public
name|Long
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|Long
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|RoomType
argument_list|>
name|getRoomTypes
parameter_list|()
block|{
return|return
name|RoomType
operator|.
name|findAll
argument_list|(
literal|false
argument_list|)
return|;
block|}
block|}
end_class

end_unit

