begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|timetable
operator|.
name|form
operator|.
name|EditRoomGroupForm
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
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-12-2006  *   * XDoclet definition:  * @struts.action path="/editRoomGroup" name="editRoomGroupForm" input="/admin/editRoomGroup.jsp" scope="request"  * @struts.action-forward name="showRoomDetail" path="/roomDetail.do"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/editRoomGroup"
argument_list|)
specifier|public
class|class
name|EditRoomGroupAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
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
name|EditRoomGroupForm
name|editRoomGroupForm
init|=
operator|(
name|EditRoomGroupForm
operator|)
name|form
decl_stmt|;
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
name|editRoomGroupForm
operator|.
name|getDoit
argument_list|()
decl_stmt|;
comment|//return to room list
if|if
condition|(
name|doit
operator|!=
literal|null
operator|&&
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.returnToRoomDetail"
argument_list|)
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"roomDetail.do?id="
operator|+
name|editRoomGroupForm
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
comment|//the following call cannot be used since doit is has the same value as for return to room list (Back)
comment|//return mapping.findForward("showRoomDetail");
block|}
comment|//update location
if|if
condition|(
name|doit
operator|!=
literal|null
operator|&&
name|doit
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.update"
argument_list|)
argument_list|)
condition|)
block|{
name|doUpdate
argument_list|(
name|editRoomGroupForm
argument_list|,
name|request
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
comment|//get location information
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
name|Room
name|r
init|=
operator|(
name|Room
operator|)
name|location
decl_stmt|;
name|editRoomGroupForm
operator|.
name|setName
argument_list|(
name|r
operator|.
name|getLabel
argument_list|()
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
name|editRoomGroupForm
operator|.
name|setName
argument_list|(
name|nonUnivLocation
operator|.
name|getName
argument_list|()
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
literal|"editRoomGroup"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.lookup.notFound"
argument_list|,
literal|"Room Group"
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|location
argument_list|,
name|Right
operator|.
name|RoomEditGroups
argument_list|)
expr_stmt|;
name|boolean
name|editGlobalGroups
init|=
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|location
argument_list|,
name|Right
operator|.
name|RoomEditGlobalGroups
argument_list|)
decl_stmt|;
for|for
control|(
name|RoomGroup
name|rg
range|:
name|RoomGroup
operator|.
name|getAllGlobalRoomGroups
argument_list|(
name|location
operator|.
name|getSession
argument_list|()
argument_list|)
control|)
block|{
name|editRoomGroupForm
operator|.
name|addToGlobalRoomGroups
argument_list|(
name|rg
argument_list|,
name|editGlobalGroups
argument_list|,
name|location
operator|.
name|hasGroup
argument_list|(
name|rg
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Set
argument_list|<
name|Department
argument_list|>
name|departments
init|=
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Department
name|department
range|:
name|departments
control|)
block|{
for|for
control|(
name|RoomGroup
name|rg
range|:
name|RoomGroup
operator|.
name|getAllDepartmentRoomGroups
argument_list|(
name|department
argument_list|)
control|)
block|{
name|editRoomGroupForm
operator|.
name|addToMangaerRoomGroups
argument_list|(
name|rg
argument_list|,
literal|true
argument_list|,
name|location
operator|.
name|hasGroup
argument_list|(
name|rg
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Department
name|department
range|:
name|Department
operator|.
name|findAllExternal
argument_list|(
name|location
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|departments
operator|.
name|contains
argument_list|(
name|department
argument_list|)
condition|)
continue|continue;
for|for
control|(
name|RoomGroup
name|rg
range|:
name|RoomGroup
operator|.
name|getAllDepartmentRoomGroups
argument_list|(
name|department
argument_list|)
control|)
block|{
name|editRoomGroupForm
operator|.
name|addToMangaerRoomGroups
argument_list|(
name|rg
argument_list|,
literal|false
argument_list|,
name|location
operator|.
name|hasGroup
argument_list|(
name|rg
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEditRoomGroup"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param editRoomGroupForm 	 * @param request 	 * @throws Exception 	 */
specifier|private
name|void
name|doUpdate
parameter_list|(
name|EditRoomGroupForm
name|editRoomGroupForm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
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
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
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
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|location
argument_list|,
name|Right
operator|.
name|RoomEditGroups
argument_list|)
expr_stmt|;
name|boolean
name|editGlobalGroups
init|=
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|location
argument_list|,
name|Right
operator|.
name|RoomEditGlobalGroups
argument_list|)
decl_stmt|;
if|if
condition|(
name|editGlobalGroups
operator|&&
name|editRoomGroupForm
operator|.
name|getGlobalRoomGroupsAssigned
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
name|globalSelected
init|=
name|editRoomGroupForm
operator|.
name|getGlobalRoomGroupsAssigned
argument_list|()
decl_stmt|;
name|List
name|globalRg
init|=
name|editRoomGroupForm
operator|.
name|getGlobalRoomGroupIds
argument_list|()
decl_stmt|;
if|if
condition|(
name|globalSelected
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|iter
init|=
name|globalRg
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
name|String
name|rgId
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|RoomGroup
name|rg
init|=
name|RoomGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rgId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|rg
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomGroups
argument_list|()
operator|.
name|remove
argument_list|(
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
block|}
block|}
else|else
block|{
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
name|globalRg
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
name|String
name|rgId
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|selected
init|=
operator|(
name|String
operator|)
name|globalSelected
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|RoomGroup
name|rg
init|=
name|RoomGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rgId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|selected
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|selected
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"on"
argument_list|)
operator|||
name|selected
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"true"
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|rg
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rg
operator|.
name|getRooms
argument_list|()
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomGroups
argument_list|()
operator|.
name|add
argument_list|(
name|rg
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|rg
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rg
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomGroups
argument_list|()
operator|.
name|remove
argument_list|(
name|rg
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rg
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
name|Set
argument_list|<
name|Department
argument_list|>
name|departments
init|=
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|departments
operator|.
name|isEmpty
argument_list|()
operator|&&
name|editRoomGroupForm
operator|.
name|getManagerRoomGroupsAssigned
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
name|managerSelected
init|=
name|editRoomGroupForm
operator|.
name|getManagerRoomGroupsAssigned
argument_list|()
decl_stmt|;
name|List
name|managerRg
init|=
name|editRoomGroupForm
operator|.
name|getManagerRoomGroupIds
argument_list|()
decl_stmt|;
if|if
condition|(
name|managerSelected
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|iter
init|=
name|managerRg
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
name|String
name|rgId
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|RoomGroup
name|rg
init|=
name|RoomGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rgId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|rg
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|departments
operator|.
name|contains
argument_list|(
name|rg
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
continue|continue;
name|rg
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomGroups
argument_list|()
operator|.
name|remove
argument_list|(
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
block|}
block|}
else|else
block|{
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
name|managerRg
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
name|String
name|rgId
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|selected
init|=
operator|(
name|String
operator|)
name|managerSelected
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|selected
operator|==
literal|null
condition|)
continue|continue;
name|RoomGroup
name|rg
init|=
name|RoomGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rgId
argument_list|)
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|rg
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|departments
operator|.
name|contains
argument_list|(
name|rg
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|selected
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"on"
argument_list|)
operator|||
name|selected
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"true"
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|rg
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rg
operator|.
name|getRooms
argument_list|()
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomGroups
argument_list|()
operator|.
name|add
argument_list|(
name|rg
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|rg
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rg
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|location
operator|.
name|getRoomGroups
argument_list|()
operator|.
name|remove
argument_list|(
name|rg
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rg
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
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
name|sessionContext
argument_list|,
name|location
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
name|hibSession
operator|.
name|refresh
argument_list|(
name|location
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
block|}
end_class

end_unit

