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
name|commons
operator|.
name|web
operator|.
name|WebTable
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
name|model
operator|.
name|dao
operator|.
name|DepartmentDAO
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
name|webutil
operator|.
name|BackTracker
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
name|webutil
operator|.
name|InstructorListBuilder
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts Creation date: 10-14-2005  *   * XDoclet definition:  *   * @struts:action path="/instructorList" name="instructorSearchForm"  *                input="/user/instructorList.jsp" parameter="op"  *                scope="request" validate="true"  * @struts:action-forward name="showInstructorList" path="instructorListTile"  */
end_comment

begin_class
specifier|public
class|class
name|InstructorListAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance
comment|// Variables
comment|// --------------------------------------------------------- Methods
comment|/** 	 * Method execute 	 *  	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
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
comment|// Check permissions
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
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
comment|// Check if to return to search page
name|String
name|op
init|=
name|instructorSearchForm
operator|.
name|getOp
argument_list|()
decl_stmt|;
if|if
condition|(
name|op
operator|!=
literal|null
operator|&&
name|op
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Back to Search"
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showInstructorSearch"
argument_list|)
return|;
block|}
comment|//get deptCode from request - for user with only one department
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"deptId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|deptId
operator|!=
literal|null
condition|)
block|{
name|instructorSearchForm
operator|.
name|setDeptUniqueId
argument_list|(
name|deptId
argument_list|)
expr_stmt|;
block|}
comment|// Set Form Variable
if|if
condition|(
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
operator|!=
literal|null
operator|&&
operator|(
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
operator|.
name|equals
argument_list|(
name|instructorSearchForm
operator|.
name|getDeptUniqueId
argument_list|()
argument_list|)
operator|||
name|instructorSearchForm
operator|.
name|getDeptUniqueId
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|instructorSearchForm
operator|.
name|setDeptUniqueId
argument_list|(
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Set Session Variable
if|if
condition|(
operator|!
name|instructorSearchForm
operator|.
name|getDeptUniqueId
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|,
name|instructorSearchForm
operator|.
name|getDeptUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|request
operator|.
name|getAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setupManagerDepartments
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
comment|// Validate input
name|errors
operator|=
name|instructorSearchForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
comment|// Validation fails
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
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
literal|"showInstructorSearch"
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
if|if
condition|(
operator|!
name|user
operator|.
name|isAdmin
argument_list|()
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
name|mgr
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
block|}
if|if
condition|(
name|Web
operator|.
name|hasRole
argument_list|(
name|httpSession
argument_list|,
name|Roles
operator|.
name|getAdminRoles
argument_list|()
argument_list|)
condition|)
block|{
name|instructorSearchForm
operator|.
name|setAdmin
argument_list|(
literal|"Y"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|instructorSearchForm
operator|.
name|setAdmin
argument_list|(
literal|"N"
argument_list|)
expr_stmt|;
block|}
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"instructorList.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|InstructorListBuilder
name|ilb
init|=
operator|new
name|InstructorListBuilder
argument_list|()
decl_stmt|;
name|String
name|backId
init|=
operator|(
literal|"PreferenceGroup"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backType"
argument_list|)
argument_list|)
condition|?
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
name|String
name|tblData
init|=
name|ilb
operator|.
name|htmlTableForInstructor
argument_list|(
name|request
argument_list|,
name|instructorSearchForm
operator|.
name|getDeptUniqueId
argument_list|()
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"instructorList.ord"
argument_list|)
argument_list|,
name|backId
argument_list|)
decl_stmt|;
if|if
condition|(
name|tblData
operator|==
literal|null
operator|||
name|tblData
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
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
literal|"No instructors were found. Use the option 'Manage Instructor List' to add instructors to your list."
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
else|else
block|{
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|ilb
operator|.
name|pdfTableForInstructor
argument_list|(
name|request
argument_list|,
name|instructorSearchForm
operator|.
name|getDeptUniqueId
argument_list|()
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"instructorList.ord"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|deptId
operator|!=
literal|null
condition|)
block|{
name|Department
name|d
init|=
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|deptId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
block|{
name|instructorSearchForm
operator|.
name|setEditable
argument_list|(
name|d
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
operator|||
name|d
operator|.
name|isLimitedEditableBy
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"instructorList.do?deptId="
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"Instructors ("
operator|+
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
operator|+
literal|")"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Department
name|d
init|=
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|!=
literal|null
condition|)
block|{
name|instructorSearchForm
operator|.
name|setEditable
argument_list|(
name|d
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
operator|||
name|d
operator|.
name|isLimitedEditableBy
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"instructorList.do?deptId="
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|"Instructors ("
operator|+
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
operator|+
literal|")"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"instructorList.do"
argument_list|,
literal|"Instructors"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"instructorList"
argument_list|,
name|tblData
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
literal|"showInstructorList"
argument_list|)
return|;
block|}
comment|/**      * @return      */
specifier|private
name|void
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
block|}
block|}
end_class

end_unit

