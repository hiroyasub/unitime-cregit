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
name|Collection
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
name|timetable
operator|.
name|form
operator|.
name|SpecialUseRoomForm
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
name|Building
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
name|ExternalBuilding
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
name|ExternalRoom
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
name|ExternalRoomFeature
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
name|dao
operator|.
name|BuildingDAO
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
name|RoomDAO
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
name|SessionDAO
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
comment|/**   * MyEclipse Struts  * Creation date: 05-05-2006  *   * XDoclet definition:  * @struts.action path="/addSpecialUseRoom" name="specialUseRoomForm" input="/admin/addSpecialUseRoom.jsp" scope="request" validate="true"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/addSpecialUseRoom"
argument_list|)
specifier|public
class|class
name|AddSpecialUseRoomAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
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
name|SpecialUseRoomForm
name|specialUseRoomForm
init|=
operator|(
name|SpecialUseRoomForm
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|AddSpecialUseRoom
argument_list|)
expr_stmt|;
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
name|List
argument_list|<
name|Building
argument_list|>
name|buildings
init|=
name|Building
operator|.
name|findAll
argument_list|(
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
name|specialUseRoomForm
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
name|specialUseRoomForm
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
name|specialUseRoomForm
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
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|forward
init|=
name|update
argument_list|(
name|request
argument_list|,
name|specialUseRoomForm
argument_list|)
decl_stmt|;
if|if
condition|(
name|forward
operator|!=
literal|null
condition|)
return|return
name|mapping
operator|.
name|findForward
argument_list|(
name|forward
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
block|}
name|setup
argument_list|(
name|request
argument_list|,
name|departments
argument_list|,
name|buildings
argument_list|)
expr_stmt|;
comment|//set default department
name|specialUseRoomForm
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
name|departments
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|Department
name|d
init|=
name|departments
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|specialUseRoomForm
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
name|sessionContext
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
name|specialUseRoomForm
operator|.
name|setDeptCode
argument_list|(
name|sessionContext
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
comment|/** 	 *  	 * @param request 	 * @throws Exception 	 */
specifier|private
name|void
name|setup
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Set
argument_list|<
name|Department
argument_list|>
name|departments
parameter_list|,
name|List
argument_list|<
name|Building
argument_list|>
name|buildings
parameter_list|)
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|LabelValueBean
argument_list|>
name|deptList
init|=
operator|new
name|ArrayList
argument_list|<
name|LabelValueBean
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|departments
control|)
block|{
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
name|deptList
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
name|deptList
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|LabelValueBean
argument_list|>
name|bldgList
init|=
operator|new
name|ArrayList
argument_list|<
name|LabelValueBean
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Building
name|b
range|:
name|buildings
control|)
block|{
name|bldgList
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|b
operator|.
name|getAbbreviation
argument_list|()
operator|+
literal|"-"
operator|+
name|b
operator|.
name|getName
argument_list|()
argument_list|,
name|b
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"-"
operator|+
name|b
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
name|Building
operator|.
name|BLDG_LIST_ATTR_NAME
argument_list|,
name|bldgList
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 * @param request 	 * @param specialUseRoomForm 	 * @param mapping 	 * @return 	 * @throws Exception 	 */
specifier|private
name|String
name|update
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SpecialUseRoomForm
name|specialUseRoomForm
parameter_list|)
throws|throws
name|Exception
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|Long
name|sessionId
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
name|Long
name|bldgUniqueId
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|specialUseRoomForm
operator|.
name|getBldgId
argument_list|()
operator|.
name|split
argument_list|(
literal|"-"
argument_list|)
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|String
name|bldgAbbv
init|=
name|specialUseRoomForm
operator|.
name|getBldgId
argument_list|()
operator|.
name|split
argument_list|(
literal|"-"
argument_list|)
index|[
literal|1
index|]
decl_stmt|;
name|String
name|roomNum
init|=
name|specialUseRoomForm
operator|.
name|getRoomNum
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
comment|//check if room already exists
name|Room
name|existingRoom
init|=
name|Room
operator|.
name|findByBldgIdRoomNbr
argument_list|(
name|bldgUniqueId
argument_list|,
name|roomNum
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|existingRoom
operator|!=
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"specialUseRoom"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
literal|"Room "
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
return|return
literal|null
return|;
block|}
comment|//get room
name|ExternalBuilding
name|extBldg
init|=
name|ExternalBuilding
operator|.
name|findByAbbv
argument_list|(
name|sessionId
argument_list|,
name|bldgAbbv
argument_list|)
decl_stmt|;
name|ExternalRoom
name|extRoom
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|extBldg
operator|!=
literal|null
condition|)
name|extRoom
operator|=
name|extBldg
operator|.
name|findRoom
argument_list|(
name|roomNum
argument_list|)
expr_stmt|;
if|if
condition|(
name|extRoom
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"specialUseRoom"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
literal|"Room number "
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
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|!
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|extRoom
argument_list|,
name|Right
operator|.
name|AddSpecialUseRoomExternalRoom
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"specialUseRoom"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.room.ownership"
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
return|return
literal|null
return|;
block|}
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|(
operator|new
name|RoomDAO
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
name|Room
name|room
init|=
operator|new
name|Room
argument_list|()
decl_stmt|;
name|room
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|room
operator|.
name|setIgnoreTooFar
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|room
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|room
operator|.
name|setCoordinateX
argument_list|(
name|extRoom
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setCoordinateY
argument_list|(
name|extRoom
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setCapacity
argument_list|(
name|extRoom
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setExamCapacity
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|room
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
name|room
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
name|room
operator|.
name|setRoomNumber
argument_list|(
name|roomNum
argument_list|)
expr_stmt|;
name|room
operator|.
name|setRoomType
argument_list|(
name|extRoom
operator|.
name|getRoomType
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setExternalUniqueId
argument_list|(
name|extRoom
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setClassification
argument_list|(
name|extRoom
operator|.
name|getClassification
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setDisplayName
argument_list|(
name|extRoom
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|BuildingDAO
name|bldgDAO
init|=
operator|new
name|BuildingDAO
argument_list|()
decl_stmt|;
name|Building
name|bldg
init|=
name|bldgDAO
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|bldgUniqueId
argument_list|)
argument_list|)
decl_stmt|;
name|room
operator|.
name|setBuildingAbbv
argument_list|(
name|bldgAbbv
argument_list|)
expr_stmt|;
name|room
operator|.
name|setBuilding
argument_list|(
name|bldg
argument_list|)
expr_stmt|;
name|room
operator|.
name|setFeatures
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setAssignments
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setRoomGroups
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|room
operator|.
name|setRoomDepts
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|LocationPermIdGenerator
operator|.
name|setPermanentId
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|Set
name|extRoomFeatures
init|=
name|extRoom
operator|.
name|getRoomFeatures
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|extRoomFeatures
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|addRoomFeatures
argument_list|(
name|extRoomFeatures
argument_list|,
name|room
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|room
argument_list|)
expr_stmt|;
block|}
name|Department
name|dept
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|specialUseRoomForm
operator|.
name|getDeptCode
argument_list|()
operator|!=
literal|null
operator|&&
name|specialUseRoomForm
operator|.
name|getDeptCode
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
name|deptSelected
init|=
name|specialUseRoomForm
operator|.
name|getDeptCode
argument_list|()
decl_stmt|;
name|RoomDept
name|roomdept
init|=
operator|new
name|RoomDept
argument_list|()
decl_stmt|;
name|roomdept
operator|.
name|setRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|roomdept
operator|.
name|setControl
argument_list|(
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
name|dept
operator|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|deptSelected
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
name|roomdept
operator|.
name|setDepartment
argument_list|(
name|dept
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|roomdept
argument_list|)
expr_stmt|;
block|}
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
operator|(
name|Location
operator|)
name|room
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
name|dept
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
block|{
name|hibSession
operator|.
name|refresh
argument_list|(
name|dept
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|refresh
argument_list|(
name|room
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
return|return
operator|(
literal|"showRoomList"
operator|)
return|;
block|}
comment|/** 	 * Add room features 	 * @param extRoomFeatures 	 * @param room 	 */
specifier|private
name|void
name|addRoomFeatures
parameter_list|(
name|Set
name|extRoomFeatures
parameter_list|,
name|Room
name|room
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|Set
name|roomFeatures
init|=
name|room
operator|.
name|getFeatures
argument_list|()
decl_stmt|;
name|Iterator
name|f
init|=
name|extRoomFeatures
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Collection
name|globalRoomFeatures
init|=
name|RoomFeature
operator|.
name|getAllGlobalRoomFeatures
argument_list|(
name|room
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
while|while
condition|(
name|f
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ExternalRoomFeature
name|extRoomFeature
init|=
operator|(
name|ExternalRoomFeature
operator|)
name|f
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|featureValue
init|=
name|extRoomFeature
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|Iterator
name|g
init|=
name|globalRoomFeatures
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|g
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|RoomFeature
name|globalFeature
init|=
operator|(
name|RoomFeature
operator|)
name|g
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|globalFeature
operator|.
name|getLabel
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|featureValue
argument_list|)
condition|)
block|{
name|globalFeature
operator|.
name|getRooms
argument_list|()
operator|.
name|add
argument_list|(
operator|(
name|Location
operator|)
name|room
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|globalFeature
argument_list|)
expr_stmt|;
name|roomFeatures
operator|.
name|add
argument_list|(
name|globalFeature
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
name|room
operator|.
name|setFeatures
argument_list|(
name|roomFeatures
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
end_class

end_unit

