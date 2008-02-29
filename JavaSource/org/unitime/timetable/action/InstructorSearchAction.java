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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|InstructorSearchForm
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
name|DepartmentalInstructor
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 10-14-2005  *   * XDoclet definition:  * @struts:action path="/instructorSearch" name="instructorSearchForm" input="/user/instructorSearch.jsp" scope="request" validate="true"  * @struts:action-forward name="showInstructorSearch" path="instructorSearchTile"  */
end_comment

begin_class
specifier|public
class|class
name|InstructorSearchAction
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
name|InstructorSearchForm
name|instructorSearchForm
init|=
operator|(
name|InstructorSearchForm
operator|)
name|form
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
name|Set
name|mgrDepts
init|=
name|setupManagerDepartments
argument_list|(
name|request
argument_list|)
decl_stmt|;
comment|// Dept code is saved to the session - go to instructor list
name|Object
name|dc
init|=
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|String
name|deptId
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|dc
operator|!=
literal|null
operator|&&
operator|!
name|dc
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|BLANK_OPTION_VALUE
argument_list|)
condition|)
block|{
name|boolean
name|allowed
init|=
literal|false
decl_stmt|;
name|deptId
operator|=
name|dc
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|mgrDepts
operator|!=
literal|null
operator|&&
name|mgrDepts
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|&&
operator|!
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|mgrDepts
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
name|d
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|deptId
argument_list|)
condition|)
block|{
name|allowed
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
operator|||
name|allowed
condition|)
block|{
name|getInstructorList
argument_list|(
name|instructorSearchForm
argument_list|,
name|request
argument_list|,
name|deptId
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"instructorList"
argument_list|)
return|;
block|}
block|}
comment|// No session attribute found - Load dept code
else|else
block|{
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|mgrDepts
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|deptId
operator|=
name|d
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|,
name|deptId
argument_list|)
expr_stmt|;
name|getInstructorList
argument_list|(
name|instructorSearchForm
argument_list|,
name|request
argument_list|,
name|deptId
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"instructorList"
argument_list|)
return|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showInstructorSearch"
argument_list|)
return|;
block|}
specifier|private
name|void
name|getInstructorList
parameter_list|(
name|InstructorSearchForm
name|instructorSearchForm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|String
name|deptId
parameter_list|,
name|Long
name|sessionId
parameter_list|)
throws|throws
name|Exception
block|{
name|instructorSearchForm
operator|.
name|setDeptUniqueId
argument_list|(
name|deptId
argument_list|)
expr_stmt|;
name|List
name|v
init|=
name|DepartmentalInstructor
operator|.
name|getInstructorByDept
argument_list|(
name|sessionId
argument_list|,
operator|new
name|Long
argument_list|(
name|deptId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|v
operator|==
literal|null
operator|||
name|v
operator|.
name|size
argument_list|()
operator|==
literal|0
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
operator|.
name|add
argument_list|(
literal|"searchResult"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No instructors for the selected department were found."
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
block|}
comment|/**      * @return      */
specifier|private
name|Set
name|setupManagerDepartments
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|Set
name|mgrDepts
init|=
literal|null
decl_stmt|;
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
name|user
operator|.
name|isAdmin
argument_list|()
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
name|mgrDepts
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
comment|// Get manager info
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
name|mgrDepts
operator|=
operator|new
name|TreeSet
argument_list|(
name|mgr
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//get depts owned by user and forward to the appropriate page
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
name|labelValueDepts
operator|.
name|add
argument_list|(
operator|new
name|LabelValueBean
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|"-"
operator|+
name|d
operator|.
name|getName
argument_list|()
argument_list|,
name|d
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
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
name|request
operator|.
name|setAttribute
argument_list|(
literal|"deptId"
argument_list|,
name|d
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
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
name|mgrDepts
return|;
block|}
block|}
end_class

end_unit

