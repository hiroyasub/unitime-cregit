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
name|Session
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
name|Order
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
name|EditRoomFeatureForm
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
name|RoomFeatureDAO
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
comment|/**   * MyEclipse Struts  * Creation date: 05-12-2006  *   * XDoclet definition:  * @struts.action path="/editRoomFeature" name="editRoomFeatureForm" input="/admin/editRoomFeature.jsp" scope="request"  * @struts.action-forward name="showRoomDetail" path="/roomDetail.do"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/editRoomFeature"
argument_list|)
specifier|public
class|class
name|EditRoomFeatureAction
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
name|EditRoomFeatureForm
name|editRoomFeatureForm
init|=
operator|(
name|EditRoomFeatureForm
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
name|editRoomFeatureForm
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
name|editRoomFeatureForm
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
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
name|editRoomFeatureForm
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
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
name|editRoomFeatureForm
operator|.
name|setRoomLabel
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
name|editRoomFeatureForm
operator|.
name|setRoomLabel
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
comment|//get features
name|ArrayList
name|globalRoomFeatures
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|ArrayList
name|deptRoomFeatures
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
try|try
block|{
name|RoomFeatureDAO
name|d
init|=
operator|new
name|RoomFeatureDAO
argument_list|()
decl_stmt|;
name|hibSession
operator|=
name|d
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|List
name|list
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|GlobalRoomFeature
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
literal|"session.uniqueId"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"label"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|list
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
name|GlobalRoomFeature
name|rf
init|=
operator|(
name|GlobalRoomFeature
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|globalRoomFeatures
operator|.
name|add
argument_list|(
name|rf
argument_list|)
expr_stmt|;
block|}
name|list
operator|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct f from DepartmentRoomFeature f where f.department.session=:sessionId order by label"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|i1
init|=
name|list
operator|.
name|iterator
argument_list|()
init|;
name|i1
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DepartmentRoomFeature
name|rf
init|=
operator|(
name|DepartmentRoomFeature
operator|)
name|i1
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rf
operator|.
name|getDeptCode
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|Department
name|dept
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|rf
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|sessionId
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|location
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i2
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
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|dept
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|rd
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|deptRoomFeatures
operator|.
name|add
argument_list|(
name|rf
argument_list|)
expr_stmt|;
break|break;
block|}
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
block|}
for|for
control|(
name|Iterator
name|iter
init|=
name|globalRoomFeatures
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
name|GlobalRoomFeature
name|grf
init|=
operator|(
name|GlobalRoomFeature
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|editRoomFeatureForm
operator|.
name|getGlobalRoomFeatureIds
argument_list|()
operator|.
name|contains
argument_list|(
name|grf
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
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
name|editRoomFeatureForm
operator|.
name|addToGlobalRoomFeatures
argument_list|(
name|grf
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|,
name|Boolean
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|hasFeature
argument_list|(
name|grf
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|editRoomFeatureForm
operator|.
name|addToGlobalRoomFeatures
argument_list|(
name|grf
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|,
name|Boolean
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|hasFeature
argument_list|(
name|grf
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|Iterator
name|iter
init|=
name|deptRoomFeatures
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
name|DepartmentRoomFeature
name|drf
init|=
operator|(
name|DepartmentRoomFeature
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|editRoomFeatureForm
operator|.
name|getDepartmentRoomFeatureIds
argument_list|()
operator|.
name|contains
argument_list|(
name|drf
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
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
operator|(
name|drf
operator|.
name|getDeptCode
argument_list|()
operator|!=
literal|null
operator|&&
name|owner
operator|.
name|getDepartments
argument_list|()
operator|.
name|contains
argument_list|(
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|drf
operator|.
name|getDeptCode
argument_list|()
argument_list|,
name|sessionId
argument_list|)
argument_list|)
operator|)
condition|)
block|{
name|editRoomFeatureForm
operator|.
name|addToDepartmentRoomFeatures
argument_list|(
name|drf
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|,
name|Boolean
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|hasFeature
argument_list|(
name|drf
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|editRoomFeatureForm
operator|.
name|addToDepartmentRoomFeatures
argument_list|(
name|drf
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|,
name|Boolean
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|hasFeature
argument_list|(
name|drf
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEditRoomFeature"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param editRoomFeatureForm 	 * @param request 	 * @throws Exception 	 */
specifier|private
name|void
name|doUpdate
parameter_list|(
name|EditRoomFeatureForm
name|editRoomFeatureForm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
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
name|RoomFeatureDAO
name|rfdao
init|=
operator|new
name|RoomFeatureDAO
argument_list|()
decl_stmt|;
name|Set
name|rfs
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
comment|//update room features
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
if|if
condition|(
name|editRoomFeatureForm
operator|.
name|getGlobalRoomFeaturesAssigned
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
name|globalSelected
init|=
name|editRoomFeatureForm
operator|.
name|getGlobalRoomFeaturesAssigned
argument_list|()
decl_stmt|;
name|List
name|globalRf
init|=
name|editRoomFeatureForm
operator|.
name|getGlobalRoomFeatureIds
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
name|globalRf
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
name|rfId
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|RoomFeature
name|rf
init|=
name|rfdao
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rfId
argument_list|)
argument_list|)
decl_stmt|;
name|rf
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
name|rf
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
name|globalRf
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
name|rfId
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
name|RoomFeature
name|rf
init|=
name|rfdao
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rfId
argument_list|)
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
name|rfs
operator|.
name|add
argument_list|(
name|rf
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|rf
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rf
operator|.
name|getRooms
argument_list|()
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|rf
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rf
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rf
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|editRoomFeatureForm
operator|.
name|getDepartmentRoomFeaturesAssigned
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|List
name|managerSelected
init|=
name|editRoomFeatureForm
operator|.
name|getDepartmentRoomFeaturesAssigned
argument_list|()
decl_stmt|;
name|List
name|managerRf
init|=
name|editRoomFeatureForm
operator|.
name|getDepartmentRoomFeatureIds
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
name|managerRf
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
name|rfId
init|=
operator|(
name|String
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|RoomFeature
name|rf
init|=
name|rfdao
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rfId
argument_list|)
argument_list|)
decl_stmt|;
name|rf
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
name|rf
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
name|managerRf
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
name|rfId
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
name|RoomFeature
name|rf
init|=
name|rfdao
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|rfId
argument_list|)
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
name|rfs
operator|.
name|add
argument_list|(
name|rf
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|rf
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rf
operator|.
name|getRooms
argument_list|()
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|rf
operator|.
name|hasLocation
argument_list|(
name|location
argument_list|)
condition|)
block|{
name|rf
operator|.
name|getRooms
argument_list|()
operator|.
name|remove
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|rf
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
name|location
operator|.
name|setFeatures
argument_list|(
name|rfs
argument_list|)
expr_stmt|;
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
name|request
argument_list|,
name|location
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

