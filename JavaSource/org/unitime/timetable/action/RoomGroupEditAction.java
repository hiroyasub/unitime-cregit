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
name|Collections
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
name|List
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
name|hibernate
operator|.
name|HibernateException
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
name|RoomGroupPref
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
name|ExamTypeDAO
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|spring
operator|.
name|struts
operator|.
name|SpringAwareLookupDispatchAction
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
comment|/**   * MyEclipse Struts  * Creation date: 05-02-2006  *   * XDoclet definition:  * @struts.action path="/roomGroupEdit" name="roomGroupEditForm" input="/admin/roomGroupEdit.jsp" parameter="doit" scope="request" validate="true"  * @struts.action-forward name="showRoomGroupList" path="/roomGroupList.do"  * @struts.action-forward name="showEdit" path="roomGroupEditTile"  * @struts.action-forward name="showAdd" path="roomGroupEditTile"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/roomGroupEdit"
argument_list|)
specifier|public
class|class
name|RoomGroupEditAction
extends|extends
name|SpringAwareLookupDispatchAction
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/** 	 *  	 * @return 	 */
specifier|protected
name|Map
name|getKeyMethodMap
parameter_list|()
block|{
name|Map
name|map
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"editRoomGroup"
argument_list|,
literal|"editRoomGroup"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.delete"
argument_list|,
literal|"deleteRoomGroup"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.update"
argument_list|,
literal|"saveRoomGroup"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.addNew"
argument_list|,
literal|"saveRoomGroup"
argument_list|)
expr_stmt|;
name|map
operator|.
name|put
argument_list|(
literal|"button.returnToRoomGroupList"
argument_list|,
literal|"cancelRoomGroup"
argument_list|)
expr_stmt|;
return|return
name|map
return|;
block|}
comment|/** 	 *  	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return 	 * @throws HibernateException 	 * @throws Exception 	 */
specifier|public
name|ActionForward
name|editRoomGroup
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
name|HibernateException
throws|,
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
comment|//get roomGroup from request
name|Long
name|id
init|=
operator|new
name|Long
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|roomGroupEditForm
operator|.
name|setId
argument_list|(
name|id
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|RoomGroupDAO
name|rdao
init|=
operator|new
name|RoomGroupDAO
argument_list|()
decl_stmt|;
name|RoomGroup
name|rg
init|=
name|rdao
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|rg
argument_list|,
name|rg
operator|.
name|isGlobal
argument_list|()
condition|?
name|Right
operator|.
name|GlobalRoomGroupEdit
else|:
name|Right
operator|.
name|DepartmenalRoomGroupEdit
argument_list|)
expr_stmt|;
name|roomGroupEditForm
operator|.
name|setSessionId
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
comment|//get depts owned by user
if|if
condition|(
name|roomGroupEditForm
operator|.
name|getName
argument_list|()
operator|==
literal|null
operator|||
name|roomGroupEditForm
operator|.
name|getName
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|roomGroupEditForm
operator|.
name|setName
argument_list|(
name|rg
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomGroupEditForm
operator|.
name|getAbbv
argument_list|()
operator|==
literal|null
operator|||
name|roomGroupEditForm
operator|.
name|getAbbv
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|roomGroupEditForm
operator|.
name|setAbbv
argument_list|(
name|rg
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|roomGroupEditForm
operator|.
name|setGlobal
argument_list|(
name|rg
operator|.
name|isGlobal
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|rg
operator|.
name|isGlobal
argument_list|()
condition|)
block|{
name|roomGroupEditForm
operator|.
name|setDeptCode
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|roomGroupEditForm
operator|.
name|setDeptName
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|String
name|dept
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentCodeRoom
argument_list|)
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|matches
argument_list|(
literal|"Exam[0-9]*"
argument_list|)
condition|)
block|{
name|roomGroupEditForm
operator|.
name|setDeptName
argument_list|(
name|ExamTypeDAO
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
name|dept
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
argument_list|)
argument_list|)
operator|.
name|getLabel
argument_list|()
operator|+
literal|" Examination Rooms"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|dept
operator|!=
literal|null
operator|&&
operator|!
name|dept
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
literal|"All"
operator|.
name|equals
argument_list|(
name|dept
argument_list|)
condition|)
block|{
name|Department
name|department
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|dept
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|!=
literal|null
condition|)
name|roomGroupEditForm
operator|.
name|setDeptName
argument_list|(
name|department
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|department
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|roomGroupEditForm
operator|.
name|setDeptCode
argument_list|(
name|rg
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|roomGroupEditForm
operator|.
name|setDeptName
argument_list|(
name|rg
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|rg
operator|.
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|roomGroupEditForm
operator|.
name|setDeft
argument_list|(
name|rg
operator|.
name|isDefaultGroup
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomGroupEditForm
operator|.
name|getDesc
argument_list|()
operator|==
literal|null
operator|||
name|roomGroupEditForm
operator|.
name|getDesc
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|roomGroupEditForm
operator|.
name|setDesc
argument_list|(
name|rg
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
comment|//get rooms owned by user
name|Collection
name|assigned
init|=
name|getAssignedRooms
argument_list|(
name|rg
argument_list|)
decl_stmt|;
name|Collection
name|available
init|=
name|getAvailableRooms
argument_list|(
name|rg
argument_list|)
decl_stmt|;
name|TreeSet
name|sortedAssignedRooms
init|=
operator|new
name|TreeSet
argument_list|(
name|assigned
argument_list|)
decl_stmt|;
name|roomGroupEditForm
operator|.
name|setAssignedRooms
argument_list|(
name|sortedAssignedRooms
argument_list|)
expr_stmt|;
name|TreeSet
name|sortedAvailableRooms
init|=
operator|new
name|TreeSet
argument_list|(
name|available
argument_list|)
decl_stmt|;
name|roomGroupEditForm
operator|.
name|setNotAssignedRooms
argument_list|(
name|sortedAvailableRooms
argument_list|)
expr_stmt|;
name|roomGroupEditForm
operator|.
name|setRooms
argument_list|()
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEdit"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return 	 */
specifier|public
name|ActionForward
name|cancelRoomGroup
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
block|{
name|RoomGroupEditForm
name|roomGroupEditForm
init|=
operator|(
name|RoomGroupEditForm
operator|)
name|form
decl_stmt|;
if|if
condition|(
name|roomGroupEditForm
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
name|roomGroupEditForm
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
literal|"showRoomGroupList"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return 	 * @throws Exception  	 */
specifier|public
name|ActionForward
name|deleteRoomGroup
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
name|Long
name|id
init|=
operator|new
name|Long
argument_list|(
name|roomGroupEditForm
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
name|RoomGroupDAO
name|rgdao
init|=
operator|new
name|RoomGroupDAO
argument_list|()
decl_stmt|;
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
name|RoomGroup
name|rg
init|=
name|rgdao
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
name|rg
operator|!=
literal|null
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|rg
argument_list|,
name|rg
operator|.
name|isGlobal
argument_list|()
condition|?
name|Right
operator|.
name|GlobalRoomGroupDelete
else|:
name|Right
operator|.
name|DepartmenalRoomGroupDelete
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
name|DELETE
argument_list|,
literal|null
argument_list|,
name|rg
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|Collection
name|rooms
init|=
name|rg
operator|.
name|getRooms
argument_list|()
decl_stmt|;
comment|//remove roomGroup from room
for|for
control|(
name|Iterator
name|iter
init|=
name|rooms
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
name|Collection
name|roomGroups
init|=
name|r
operator|.
name|getRoomGroups
argument_list|()
decl_stmt|;
name|roomGroups
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
name|r
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|RoomGroupPref
name|p
range|:
operator|(
name|List
argument_list|<
name|RoomGroupPref
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from RoomGroupPref p where p.roomGroup.uniqueId = :id"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"id"
argument_list|,
name|id
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|p
operator|.
name|getOwner
argument_list|()
operator|.
name|getPreferences
argument_list|()
operator|.
name|remove
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|p
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|p
operator|.
name|getOwner
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|delete
argument_list|(
name|rg
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomGroupList"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return 	 * @throws Exception 	 */
specifier|public
name|ActionForward
name|saveRoomGroup
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
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
comment|//Validate input prefs
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
name|update
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
name|editRoomGroup
argument_list|(
name|mapping
argument_list|,
name|form
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
literal|"showEdit"
argument_list|)
return|;
block|}
if|if
condition|(
name|roomGroupEditForm
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
name|roomGroupEditForm
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
literal|"showRoomGroupList"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param mapping 	 * @param roomGroupEditForm 	 * @param request 	 * @param response 	 */
specifier|private
name|void
name|update
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
name|Long
name|id
init|=
operator|new
name|Long
argument_list|(
name|roomGroupEditForm
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
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
name|rgdao
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|rg
argument_list|,
name|rg
operator|.
name|isGlobal
argument_list|()
condition|?
name|Right
operator|.
name|GlobalRoomGroupEdit
else|:
name|Right
operator|.
name|DepartmenalRoomGroupEdit
argument_list|)
expr_stmt|;
comment|//update name, defaultGroup, and desc
if|if
condition|(
name|roomGroupEditForm
operator|.
name|getName
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|roomGroupEditForm
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
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
block|}
if|if
condition|(
name|roomGroupEditForm
operator|.
name|getAbbv
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|roomGroupEditForm
operator|.
name|getAbbv
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
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
block|}
if|if
condition|(
name|roomGroupEditForm
operator|.
name|getDesc
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|rg
operator|.
name|setDescription
argument_list|(
name|roomGroupEditForm
operator|.
name|getDesc
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|200
condition|?
name|roomGroupEditForm
operator|.
name|getDesc
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|200
argument_list|)
else|:
name|roomGroupEditForm
operator|.
name|getDesc
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|rg
argument_list|,
name|Right
operator|.
name|GlobalRoomGroupEditSetDefault
argument_list|)
condition|)
block|{
if|if
condition|(
name|roomGroupEditForm
operator|.
name|isDeft
argument_list|()
condition|)
block|{
name|rg
operator|.
name|setDefaultGroup
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rg
operator|.
name|setDefaultGroup
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
block|}
comment|//update rooms
name|String
index|[]
name|selectedAssigned
init|=
name|roomGroupEditForm
operator|.
name|getAssignedSelected
argument_list|()
decl_stmt|;
name|String
index|[]
name|selectedNotAssigned
init|=
name|roomGroupEditForm
operator|.
name|getNotAssignedSelected
argument_list|()
decl_stmt|;
name|Collection
name|assignedRooms
init|=
name|getAssignedRooms
argument_list|(
name|rg
argument_list|)
decl_stmt|;
name|String
name|s1
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|selectedAssigned
operator|.
name|length
operator|!=
literal|0
condition|)
name|s1
operator|=
name|Constants
operator|.
name|arrayToStr
argument_list|(
name|selectedAssigned
argument_list|,
literal|""
argument_list|,
literal|","
argument_list|)
expr_stmt|;
else|else
name|s1
operator|=
operator|new
name|String
argument_list|()
expr_stmt|;
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
comment|//move room from assignedRooms to notAssignedRooms
if|if
condition|(
name|selectedAssigned
operator|.
name|length
operator|!=
name|assignedRooms
operator|.
name|size
argument_list|()
condition|)
block|{
name|Collection
name|rooms
init|=
name|rg
operator|.
name|getRooms
argument_list|()
decl_stmt|;
name|Collection
name|m
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
comment|//remove roomGroup from room
for|for
control|(
name|Iterator
name|iter
init|=
name|rooms
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
if|if
condition|(
name|assignedRooms
operator|.
name|contains
argument_list|(
name|r
argument_list|)
operator|&&
name|s1
operator|.
name|indexOf
argument_list|(
name|r
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|==
operator|-
literal|1
condition|)
block|{
name|Collection
name|roomGroups
init|=
name|r
operator|.
name|getRoomGroups
argument_list|()
decl_stmt|;
name|roomGroups
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
name|r
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
block|}
comment|//remove room from roomGroup
name|rooms
operator|.
name|removeAll
argument_list|(
name|m
argument_list|)
expr_stmt|;
block|}
comment|//move room from notAssignedRooms to assignedRooms
if|if
condition|(
name|selectedNotAssigned
operator|.
name|length
operator|!=
literal|0
condition|)
block|{
name|Set
name|m
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
comment|//add roomGroup to room
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selectedNotAssigned
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|Location
name|r
init|=
name|rdao
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|selectedNotAssigned
index|[
name|i
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|Set
name|groups
init|=
name|r
operator|.
name|getRoomGroups
argument_list|()
decl_stmt|;
name|groups
operator|.
name|add
argument_list|(
name|rg
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|m
operator|.
name|add
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
comment|//add room to roomGroup
name|rg
operator|.
name|setRooms
argument_list|(
name|m
argument_list|)
expr_stmt|;
block|}
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
name|sessionContext
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
name|UPDATE
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
name|RoomGroup
name|x
range|:
name|RoomGroup
operator|.
name|getAllRoomGroupsForSession
argument_list|(
name|rg
operator|.
name|getSession
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
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
operator|&&
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
comment|/** 	 *  	 * @param user  	 * @param rooms 	 * @param rg  	 * @return 	 * @throws Exception  	 */
specifier|private
name|Collection
name|getAssignedRooms
parameter_list|(
name|RoomGroup
name|rg
parameter_list|)
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|Location
argument_list|>
name|rooms
init|=
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|(
name|rg
operator|.
name|getRooms
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|dept
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentCodeRoom
argument_list|)
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|matches
argument_list|(
literal|"Exam[0-9]*"
argument_list|)
condition|)
block|{
name|Long
name|examType
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|dept
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Location
argument_list|>
name|i
init|=
name|rooms
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
if|if
condition|(
operator|!
name|i
operator|.
name|next
argument_list|()
operator|.
name|isExamEnabled
argument_list|(
name|examType
argument_list|)
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|dept
operator|!=
literal|null
operator|&&
operator|!
name|dept
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
literal|"All"
operator|.
name|equals
argument_list|(
name|dept
argument_list|)
condition|)
block|{
name|Department
name|department
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|dept
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|!=
literal|null
condition|)
block|{
name|rooms
label|:
for|for
control|(
name|Iterator
argument_list|<
name|Location
argument_list|>
name|i
init|=
name|rooms
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
name|Location
name|location
init|=
name|i
operator|.
name|next
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
if|if
condition|(
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|equals
argument_list|(
name|department
argument_list|)
condition|)
continue|continue
name|rooms
continue|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|rooms
return|;
block|}
comment|/** 	 *  	 * @param user 	 * @param d 	 * @return 	 * @throws Exception  	 */
specifier|private
name|Collection
name|getAvailableRooms
parameter_list|(
name|RoomGroup
name|rg
parameter_list|)
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|Location
argument_list|>
name|rooms
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|rg
operator|.
name|isGlobal
argument_list|()
operator|&&
name|rg
operator|.
name|getDepartment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Department
name|dept
init|=
name|rg
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
name|rooms
operator|=
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|RoomDept
name|rd
range|:
name|dept
operator|.
name|getRoomDepts
argument_list|()
control|)
name|rooms
operator|.
name|add
argument_list|(
name|rd
operator|.
name|getRoom
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Session
name|session
init|=
name|rg
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|String
name|dept
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentCodeRoom
argument_list|)
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
operator|&&
name|dept
operator|.
name|matches
argument_list|(
literal|"Exam[0-9]*"
argument_list|)
condition|)
block|{
name|rooms
operator|=
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|(
name|Location
operator|.
name|findAllExamLocations
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|Long
operator|.
name|valueOf
argument_list|(
name|dept
operator|.
name|substring
argument_list|(
literal|4
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|dept
operator|!=
literal|null
operator|&&
operator|!
name|dept
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
literal|"All"
operator|.
name|equals
argument_list|(
name|dept
argument_list|)
condition|)
block|{
name|Department
name|department
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|dept
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|!=
literal|null
condition|)
block|{
name|rooms
operator|=
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|RoomDept
name|rd
range|:
name|department
operator|.
name|getRoomDepts
argument_list|()
control|)
name|rooms
operator|.
name|add
argument_list|(
name|rd
operator|.
name|getRoom
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rooms
operator|=
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|(
name|Location
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|rooms
operator|=
operator|new
name|ArrayList
argument_list|<
name|Location
argument_list|>
argument_list|(
name|Location
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|rooms
argument_list|)
expr_stmt|;
name|rooms
operator|.
name|removeAll
argument_list|(
name|rg
operator|.
name|getRooms
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|rooms
return|;
block|}
block|}
end_class

end_unit

