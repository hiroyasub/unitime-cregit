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
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|ExportUtils
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
name|PdfWebTable
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts Creation date: 10-14-2005  *   * XDoclet definition:  *   * @struts:action path="/instructorList" name="instructorSearchForm"  *                input="/user/instructorList.jsp" parameter="op"  *                scope="request" validate="true"  * @struts:action-forward name="showInstructorList" path="instructorListTile"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/instructorList"
argument_list|)
specifier|public
class|class
name|InstructorListAction
extends|extends
name|Action
block|{
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Instructors
argument_list|)
expr_stmt|;
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
comment|/* Suspected unused code 		if (op != null&& op.equalsIgnoreCase("Back to Search")) { 			return mapping.findForward("showInstructorSearch"); 		} */
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
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|)
operator|!=
literal|null
operator|&&
operator|(
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
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
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
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
name|sessionContext
operator|.
name|setAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
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
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
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
name|sessionContext
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
name|sessionContext
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
name|isEmpty
argument_list|()
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
name|MSG
operator|.
name|errorNoInstructorsFoundInSearch
argument_list|()
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
name|MSG
operator|.
name|actionExportPdf
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|PdfWebTable
name|table
init|=
name|ilb
operator|.
name|pdfTableForInstructor
argument_list|(
name|sessionContext
argument_list|,
name|instructorSearchForm
operator|.
name|getDeptUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|table
operator|!=
literal|null
condition|)
block|{
name|ExportUtils
operator|.
name|exportPDF
argument_list|(
name|table
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"instructorList.ord"
argument_list|)
argument_list|,
name|response
argument_list|,
literal|"instructors"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
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
name|MSG
operator|.
name|backInstructors
argument_list|(
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
argument_list|)
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
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
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
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
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
name|MSG
operator|.
name|backInstructors
argument_list|(
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
argument_list|)
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
name|MSG
operator|.
name|backInstructors2
argument_list|()
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
name|Vector
argument_list|<
name|LabelValueBean
argument_list|>
name|labelValueDepts
init|=
operator|new
name|Vector
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
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
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
block|}
if|if
condition|(
name|labelValueDepts
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"deptId"
argument_list|,
name|labelValueDepts
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
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

