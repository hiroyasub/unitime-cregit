begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|NonUnivLocationForm
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
name|NonUniversityLocationDAO
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
name|RoomTypeDAO
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
name|LocationPermIdGenerator
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-05-2006  *   * XDoclet definition:  * @struts.action path="/addNonUnivLocation" name="nonUnivLocationForm" input="/admin/addNonUnivLocation.jsp" scope="request" validate="true"  */
end_comment

begin_class
specifier|public
class|class
name|AddNonUnivLocationAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 * @throws Exception  	 */
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
name|NonUnivLocationForm
name|nonUnivLocationForm
init|=
operator|(
name|NonUnivLocationForm
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
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
if|if
condition|(
name|nonUnivLocationForm
operator|.
name|getDoit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|String
name|doit
init|=
name|nonUnivLocationForm
operator|.
name|getDoit
argument_list|()
decl_stmt|;
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomList"
argument_list|)
return|;
block|}
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
comment|// Validate input prefs
name|errors
operator|=
name|nonUnivLocationForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
comment|// No errors
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
name|request
argument_list|,
name|nonUnivLocationForm
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
else|else
block|{
name|setDepts
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showAdd"
argument_list|)
return|;
block|}
block|}
block|}
name|setDepts
argument_list|(
name|request
argument_list|)
expr_stmt|;
comment|//set default department based on user selection or department that user owns
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
name|manager
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|isAdmin
condition|)
name|departments
operator|=
name|Department
operator|.
name|findAllBeingUsed
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|nonUnivLocationForm
operator|.
name|setDeptSize
argument_list|(
name|departments
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
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
name|nonUnivLocationForm
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
name|httpSession
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
name|nonUnivLocationForm
operator|.
name|setDeptCode
argument_list|(
name|httpSession
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showAdd"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param request 	 * @param nonUnivLocationForm 	 * @throws Exception 	 */
specifier|private
name|void
name|update
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|NonUnivLocationForm
name|nonUnivLocationForm
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
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|(
operator|new
name|NonUniversityLocationDAO
argument_list|()
operator|)
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
name|NonUniversityLocation
name|nonUniv
init|=
operator|new
name|NonUniversityLocation
argument_list|()
decl_stmt|;
name|nonUniv
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
name|nonUniv
operator|.
name|setManagerIds
argument_list|(
name|mgrId
argument_list|)
expr_stmt|;
if|if
condition|(
name|nonUnivLocationForm
operator|.
name|getName
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|nonUnivLocationForm
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
name|nonUniv
operator|.
name|setName
argument_list|(
name|nonUnivLocationForm
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|nonUnivLocationForm
operator|.
name|getCapacity
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|nonUnivLocationForm
operator|.
name|getCapacity
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
name|nonUniv
operator|.
name|setCapacity
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|nonUnivLocationForm
operator|.
name|getCapacity
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|nonUniv
operator|.
name|setIgnoreTooFar
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|nonUnivLocationForm
operator|.
name|isIgnoreTooFar
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|nonUnivLocationForm
operator|.
name|isIgnoreRoomCheck
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setCoordinateX
argument_list|(
name|nonUnivLocationForm
operator|.
name|getCoordX
argument_list|()
operator|==
literal|null
operator|||
name|nonUnivLocationForm
operator|.
name|getCoordX
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|null
else|:
name|Double
operator|.
name|valueOf
argument_list|(
name|nonUnivLocationForm
operator|.
name|getCoordX
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setCoordinateY
argument_list|(
name|nonUnivLocationForm
operator|.
name|getCoordY
argument_list|()
operator|==
literal|null
operator|||
name|nonUnivLocationForm
operator|.
name|getCoordY
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|null
else|:
name|Double
operator|.
name|valueOf
argument_list|(
name|nonUnivLocationForm
operator|.
name|getCoordY
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setFeatures
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setAssignments
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setRoomGroups
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setRoomDepts
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setExamEnabled
argument_list|(
name|Exam
operator|.
name|sExamTypeFinal
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setExamEnabled
argument_list|(
name|Exam
operator|.
name|sExamTypeMidterm
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setExamCapacity
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|nonUniv
operator|.
name|setRoomType
argument_list|(
name|RoomTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|nonUnivLocationForm
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|LocationPermIdGenerator
operator|.
name|setPermanentId
argument_list|(
name|nonUniv
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|nonUniv
argument_list|)
expr_stmt|;
comment|//set room department for location
name|RoomDept
name|rd
init|=
operator|new
name|RoomDept
argument_list|()
decl_stmt|;
name|rd
operator|.
name|setRoom
argument_list|(
name|nonUniv
argument_list|)
expr_stmt|;
name|rd
operator|.
name|setControl
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|Department
name|d
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|nonUnivLocationForm
operator|.
name|getDeptCode
argument_list|()
operator|==
literal|null
condition|)
block|{
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
name|d
operator|=
operator|(
name|Department
operator|)
name|owner
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|String
name|deptCode
init|=
name|nonUnivLocationForm
operator|.
name|getDeptCode
argument_list|()
decl_stmt|;
name|d
operator|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|deptCode
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
block|}
name|rd
operator|.
name|setDepartment
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rd
argument_list|)
expr_stmt|;
name|nonUniv
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
name|nonUniv
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
name|Location
operator|)
name|nonUniv
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
name|CREATE
argument_list|,
literal|null
argument_list|,
name|d
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
name|d
argument_list|)
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
comment|/** 	 *  	 * @param request 	 * @param nonUnivLocationForm  	 * @throws Exception 	 */
specifier|private
name|void
name|setDepts
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
name|TreeSet
name|depts
init|=
operator|new
name|TreeSet
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
operator|new
name|TreeSet
argument_list|(
name|owner
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
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
if|if
condition|(
operator|!
name|d
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
condition|)
continue|continue;
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
block|}
block|}
end_class

end_unit

