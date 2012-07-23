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
name|Vector
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
name|util
operator|.
name|LabelValueBean
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
name|RoomFeatureListForm
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
name|LookupTables
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 06-27-2006  *   * XDoclet definition:  * @struts.action path="/roomFeatureSearch" name="roomFeatureListForm" input="/admin/roomFeatureSearch.jsp" scope="request"  * @struts.action-forward name="roomFeatureList" path="/roomFeatureList.do"  * @struts.action-forward name="showRoomFeatureSearch" path="roomFeatureSearchTile"  * @struts.action-forward name="showRoomFeatureList" path="roomFeatureListTile"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/roomFeatureSearch"
argument_list|)
specifier|public
class|class
name|RoomFeatureSearchAction
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
name|RoomFeatureListForm
name|roomFeatureListForm
init|=
operator|(
name|RoomFeatureListForm
operator|)
name|form
decl_stmt|;
comment|//Check permissions
name|HttpSession
name|httpSession
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
name|httpSession
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
name|Object
name|dc
init|=
name|roomFeatureListForm
operator|.
name|getDeptCodeX
argument_list|()
decl_stmt|;
if|if
condition|(
name|dc
operator|==
literal|null
condition|)
name|dc
operator|=
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|dc
operator|==
literal|null
condition|)
block|{
name|dc
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"default"
argument_list|)
expr_stmt|;
if|if
condition|(
name|dc
operator|!=
literal|null
condition|)
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|,
name|dc
argument_list|)
expr_stmt|;
block|}
name|String
name|deptCode
init|=
literal|""
decl_stmt|;
comment|// Dept code is saved to the session - go to room feature list
if|if
condition|(
name|dc
operator|!=
literal|null
operator|&&
operator|!
name|Constants
operator|.
name|BLANK_OPTION_VALUE
operator|.
name|equals
argument_list|(
name|dc
argument_list|)
condition|)
block|{
name|deptCode
operator|=
name|dc
operator|.
name|toString
argument_list|()
expr_stmt|;
name|roomFeatureListForm
operator|.
name|setDeptCodeX
argument_list|(
name|deptCode
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
argument_list|)
condition|)
block|{
name|RoomFeatureListAction
operator|.
name|buildPdfFeatureTable
argument_list|(
name|request
argument_list|,
name|roomFeatureListForm
argument_list|)
expr_stmt|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"roomFeatureList"
argument_list|)
return|;
block|}
comment|// No session attribute found - Load dept code
else|else
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
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
operator|||
name|user
operator|.
name|getCurrentRole
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
comment|//set departments
name|LookupTables
operator|.
name|setupDepartments
argument_list|(
name|request
argument_list|,
name|sessionContext
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomFeatureSearch"
argument_list|)
return|;
block|}
else|else
block|{
comment|//get user info
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
comment|//get depts owned by user and forward to the appropriate page
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
literal|0
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"You do not have any department to manage. "
argument_list|)
throw|;
block|}
if|else if
condition|(
name|mgrDepts
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|Vector
name|labelValueDepts
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|mgrDepts
operator|.
name|iterator
argument_list|()
init|;
name|it
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
name|it
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
name|labelValueDepts
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
name|labelValueDepts
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
literal|"deptCode"
argument_list|,
name|code
argument_list|)
expr_stmt|;
name|roomFeatureListForm
operator|.
name|setDeptCodeX
argument_list|(
name|code
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
name|labelValueDepts
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
argument_list|)
condition|)
block|{
name|RoomFeatureListAction
operator|.
name|buildPdfFeatureTable
argument_list|(
name|request
argument_list|,
name|roomFeatureListForm
argument_list|)
expr_stmt|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"roomFeatureList"
argument_list|)
return|;
block|}
else|else
block|{
name|Vector
name|labelValueDepts
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|mgrDepts
operator|.
name|iterator
argument_list|()
init|;
name|it
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
name|it
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
name|labelValueDepts
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
name|labelValueDepts
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
name|labelValueDepts
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomFeatureSearch"
argument_list|)
return|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

