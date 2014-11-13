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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionRedirect
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
name|ApplicationProperty
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
name|interfaces
operator|.
name|ExternalClassEditAction
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
name|interfaces
operator|.
name|ExternalUidLookup
operator|.
name|UserInfo
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
name|Assignment
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
name|ClassInstructor
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
name|Class_
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
name|dao
operator|.
name|DepartmentalInstructorDAO
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
name|webutil
operator|.
name|BackTracker
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-20-2006  *   * XDoclet definition:  * @struts.action path="/instructorInfoEdit" name="instructorEditForm" input="/user/instructorInfoEdit.jsp" scope="request"  *  * @author Tomas Muller, Stephanie Schluttenhofer, Zuzana Mullerova  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/instructorInfoEdit"
argument_list|)
specifier|public
class|class
name|InstructorInfoEditAction
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
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
comment|//Read parameters
name|String
name|instructorId
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"instructorId"
argument_list|)
decl_stmt|;
name|String
name|op
init|=
name|frm
operator|.
name|getOp
argument_list|()
decl_stmt|;
comment|//Check instructor exists
if|if
condition|(
name|instructorId
operator|==
literal|null
operator|||
name|instructorId
operator|.
name|trim
argument_list|()
operator|==
literal|""
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|exceptionInstructorInfoNotSupplied
argument_list|()
argument_list|)
throw|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|instructorId
argument_list|,
literal|"DepartmentalInstructor"
argument_list|,
name|Right
operator|.
name|InstructorEdit
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setInstructorId
argument_list|(
name|instructorId
argument_list|)
expr_stmt|;
comment|// Cancel - Go back to Instructors Detail Screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionBackToDetail
argument_list|()
argument_list|)
operator|&&
name|instructorId
operator|!=
literal|null
operator|&&
name|instructorId
operator|.
name|trim
argument_list|()
operator|!=
literal|""
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
literal|"instructorDetail.do?instructorId="
operator|+
name|instructorId
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Check ID
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
literal|"showEdit"
argument_list|)
return|;
block|}
comment|//update - Update the instructor and go back to Instructor Detail Screen
if|if
condition|(
operator|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionUpdateInstructor
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionNextInstructor
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionPreviousInstructor
argument_list|()
argument_list|)
operator|)
operator|&&
name|instructorId
operator|!=
literal|null
operator|&&
name|instructorId
operator|.
name|trim
argument_list|()
operator|!=
literal|""
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
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionNextInstructor
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
literal|"instructorInfoEdit.do?instructorId="
operator|+
name|frm
operator|.
name|getNextId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionPreviousInstructor
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
literal|"instructorInfoEdit.do?instructorId="
operator|+
name|frm
operator|.
name|getPreviousId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|ActionRedirect
name|redirect
init|=
operator|new
name|ActionRedirect
argument_list|(
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDetail"
argument_list|)
argument_list|)
decl_stmt|;
name|redirect
operator|.
name|addParameter
argument_list|(
literal|"instructorId"
argument_list|,
name|frm
operator|.
name|getInstructorId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|redirect
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
block|}
block|}
comment|// Delete Instructor
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionDeleteInstructor
argument_list|()
argument_list|)
condition|)
block|{
name|doDelete
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showList"
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEdit"
argument_list|)
return|;
block|}
comment|//Load form
name|doLoad
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"instructorDetail.do?instructorId="
operator|+
name|frm
operator|.
name|getInstructorId
argument_list|()
argument_list|,
name|MSG
operator|.
name|backInstructor
argument_list|(
name|frm
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
literal|"null"
else|:
name|frm
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|,
literal|false
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
comment|/** 	 * Deletes instructor 	 * @param request 	 * @param frm 	 */
specifier|private
name|void
name|doDelete
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|InstructorEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|instructorId
init|=
name|frm
operator|.
name|getInstructorId
argument_list|()
decl_stmt|;
name|DepartmentalInstructorDAO
name|idao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|instructorId
argument_list|,
literal|"DepartmentalInstructor"
argument_list|,
name|Right
operator|.
name|InstructorDelete
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|idao
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
name|DepartmentalInstructor
name|inst
init|=
name|idao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|instructorId
argument_list|)
argument_list|)
decl_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|inst
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|INSTRUCTOR_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
name|inst
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|HashSet
argument_list|<
name|Class_
argument_list|>
name|updatedClasses
init|=
operator|new
name|HashSet
argument_list|<
name|Class_
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|inst
operator|.
name|getClasses
argument_list|()
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
name|ClassInstructor
name|ci
init|=
operator|(
name|ClassInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|c
init|=
name|ci
operator|.
name|getClassInstructing
argument_list|()
decl_stmt|;
name|updatedClasses
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|c
operator|.
name|getClassInstructors
argument_list|()
operator|.
name|remove
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|inst
operator|.
name|getExams
argument_list|()
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
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|exam
operator|.
name|getInstructors
argument_list|()
operator|.
name|remove
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|exam
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|inst
operator|.
name|getAssignments
argument_list|()
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
name|Assignment
name|a
init|=
operator|(
name|Assignment
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|a
operator|.
name|getInstructors
argument_list|()
operator|.
name|remove
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
name|Department
name|d
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|inst
operator|.
name|getDepartment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|d
operator|=
name|inst
operator|.
name|getDepartment
argument_list|()
expr_stmt|;
block|}
name|d
operator|.
name|getInstructors
argument_list|()
operator|.
name|remove
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|String
name|className
init|=
name|ApplicationProperty
operator|.
name|ExternalActionClassEdit
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|className
operator|!=
literal|null
operator|&&
name|className
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
name|ExternalClassEditAction
name|editAction
init|=
operator|(
name|ExternalClassEditAction
operator|)
operator|(
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
operator|)
decl_stmt|;
for|for
control|(
name|Class_
name|c
range|:
name|updatedClasses
control|)
block|{
name|editAction
operator|.
name|performExternalClassEditAction
argument_list|(
name|c
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|clear
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
block|}
comment|/** 	 * Loads the non-editable instructor info into the form 	 * @param request 	 * @param frm 	 */
specifier|private
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|InstructorEditForm
name|frm
parameter_list|)
block|{
name|String
name|instructorId
init|=
name|frm
operator|.
name|getInstructorId
argument_list|()
decl_stmt|;
name|DepartmentalInstructorDAO
name|idao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|DepartmentalInstructor
name|inst
init|=
name|idao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|instructorId
argument_list|)
argument_list|)
decl_stmt|;
comment|// populate form
name|frm
operator|.
name|setInstructorId
argument_list|(
name|instructorId
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setName
argument_list|(
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|inst
operator|.
name|getFirstName
argument_list|()
argument_list|,
literal|"-"
operator|.
name|toCharArray
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
operator|(
operator|(
name|inst
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
operator|)
condition|?
literal|""
else|:
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|inst
operator|.
name|getMiddleName
argument_list|()
argument_list|,
literal|"-"
operator|.
name|toCharArray
argument_list|()
argument_list|)
operator|)
operator|+
literal|" "
operator|+
name|Constants
operator|.
name|toInitialCase
argument_list|(
name|inst
operator|.
name|getLastName
argument_list|()
argument_list|,
literal|"-"
operator|.
name|toCharArray
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|inst
operator|.
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setFname
argument_list|(
name|inst
operator|.
name|getFirstName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|inst
operator|.
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setMname
argument_list|(
name|inst
operator|.
name|getMiddleName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|frm
operator|.
name|setLname
argument_list|(
name|inst
operator|.
name|getLastName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setTitle
argument_list|(
name|inst
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|puid
init|=
name|inst
operator|.
name|getExternalUniqueId
argument_list|()
decl_stmt|;
if|if
condition|(
name|puid
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setPuId
argument_list|(
name|puid
argument_list|)
expr_stmt|;
block|}
name|frm
operator|.
name|setEmail
argument_list|(
name|inst
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDeptName
argument_list|(
name|inst
operator|.
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|inst
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setPosType
argument_list|(
name|inst
operator|.
name|getPositionType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|inst
operator|.
name|getCareerAcct
argument_list|()
operator|!=
literal|null
operator|&&
name|inst
operator|.
name|getCareerAcct
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|frm
operator|.
name|setCareerAcct
argument_list|(
name|inst
operator|.
name|getCareerAcct
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|puid
operator|!=
literal|null
operator|&&
operator|!
name|puid
operator|.
name|isEmpty
argument_list|()
operator|&&
name|DepartmentalInstructor
operator|.
name|canLookupInstructor
argument_list|()
condition|)
block|{
try|try
block|{
name|UserInfo
name|user
init|=
name|DepartmentalInstructor
operator|.
name|lookupInstructor
argument_list|(
name|puid
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|!=
literal|null
operator|&&
name|user
operator|.
name|getUserName
argument_list|()
operator|!=
literal|null
condition|)
name|frm
operator|.
name|setCareerAcct
argument_list|(
name|user
operator|.
name|getUserName
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|frm
operator|.
name|setCareerAcct
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
if|if
condition|(
name|inst
operator|.
name|getNote
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setNote
argument_list|(
name|inst
operator|.
name|getNote
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|frm
operator|.
name|setIgnoreDist
argument_list|(
name|inst
operator|.
name|isIgnoreToFar
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
name|inst
operator|.
name|isIgnoreToFar
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|DepartmentalInstructor
name|previous
init|=
name|inst
operator|.
name|getPreviousDepartmentalInstructor
argument_list|(
name|sessionContext
argument_list|,
name|Right
operator|.
name|InstructorEdit
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setPreviousId
argument_list|(
name|previous
operator|==
literal|null
condition|?
literal|null
else|:
name|previous
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|DepartmentalInstructor
name|next
init|=
name|inst
operator|.
name|getNextDepartmentalInstructor
argument_list|(
name|sessionContext
argument_list|,
name|Right
operator|.
name|InstructorEdit
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setNextId
argument_list|(
name|next
operator|==
literal|null
condition|?
literal|null
else|:
name|next
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
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
block|}
block|}
block|}
end_class

end_unit

