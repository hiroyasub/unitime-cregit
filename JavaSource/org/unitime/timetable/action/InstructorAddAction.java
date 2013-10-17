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
name|InstructorEditForm
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-20-2006  *   * XDoclet definition:  * @struts.action path="/addNewInstructor" name="instructorEditForm" input="/user/addNewInstructor.jsp" scope="request"  *  * @author Tomas Muller, Zuzana Mullerova  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/instructorAdd"
argument_list|)
specifier|public
class|class
name|InstructorAddAction
extends|extends
name|InstructorAction
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
name|super
operator|.
name|execute
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
name|InstructorEditForm
name|frm
init|=
operator|(
name|InstructorEditForm
operator|)
name|form
decl_stmt|;
name|frm
operator|.
name|setMatchFound
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|String
name|op
init|=
name|frm
operator|.
name|getOp
argument_list|()
decl_stmt|;
comment|// Cancel adding an instructor - Go back to Instructors screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionBackToInstructors
argument_list|()
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructorList.do"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|//get department
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
condition|)
block|{
name|String
name|deptId
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
name|DepartmentId
argument_list|)
decl_stmt|;
name|Department
name|d
init|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|deptId
argument_list|)
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setDeptName
argument_list|(
name|d
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|frm
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|frm
operator|.
name|getDeptCode
argument_list|()
argument_list|,
literal|"Department"
argument_list|,
name|Right
operator|.
name|InstructorAdd
argument_list|)
expr_stmt|;
comment|//update - Update the instructor and go back to Instructor List Screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionSaveInstructor
argument_list|()
argument_list|)
condition|)
block|{
name|errors
operator|=
name|frm
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
operator|&&
name|isDeptInstructorUnique
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
condition|)
block|{
name|doUpdate
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructorList.do"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
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
name|errors
operator|.
name|add
argument_list|(
literal|"uniqueId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorInstructorIdAlreadyExistsInList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
comment|// lookup
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionLookupInstructor
argument_list|()
argument_list|)
condition|)
block|{
name|errors
operator|=
name|frm
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
name|findMatchingInstructor
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getMatchFound
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|frm
operator|.
name|getMatchFound
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"lookup"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorNoMatchingRecordsFound
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
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
comment|// search select
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionSelectInstructor
argument_list|()
argument_list|)
condition|)
block|{
name|String
name|select
init|=
name|frm
operator|.
name|getSearchSelect
argument_list|()
decl_stmt|;
if|if
condition|(
name|select
operator|!=
literal|null
operator|&&
name|select
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|select
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"i2a2"
argument_list|)
condition|)
block|{
name|fillI2A2Info
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fillStaffInfo
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"lookup"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|MSG
operator|.
name|errorNoInstructorSelectedFromList
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showAdd"
argument_list|)
return|;
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
block|}
end_class

end_unit

