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
name|text
operator|.
name|SimpleDateFormat
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
name|ExamPeriodEditForm
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
name|ExamPeriod
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
name|PreferenceLevel
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
name|ExamPeriodDAO
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
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamPeriodEditAction
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
try|try
block|{
name|ExamPeriodEditForm
name|myForm
init|=
operator|(
name|ExamPeriodEditForm
operator|)
name|form
decl_stmt|;
comment|// Check Access
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
operator|||
operator|!
name|Web
operator|.
name|hasRole
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Roles
operator|.
name|getAdminRoles
argument_list|()
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
comment|// Read operation to be performed
name|String
name|op
init|=
operator|(
name|myForm
operator|.
name|getOp
argument_list|()
operator|!=
literal|null
condition|?
name|myForm
operator|.
name|getOp
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
name|myForm
operator|.
name|load
argument_list|(
literal|null
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOp
argument_list|(
literal|"List"
argument_list|)
expr_stmt|;
block|}
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
comment|// Reset Form
if|if
condition|(
literal|"Back"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getUniqueId
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
name|myForm
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|load
argument_list|(
literal|null
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOp
argument_list|(
literal|"List"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Add Period"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|load
argument_list|(
literal|null
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOp
argument_list|(
literal|"Save"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Evening Periods"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|&&
name|myForm
operator|.
name|getCanAutoSetup
argument_list|()
condition|)
block|{
name|myForm
operator|.
name|setAutoSetup
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setExamType
argument_list|(
name|Exam
operator|.
name|sExamTypes
index|[
name|Exam
operator|.
name|sExamTypeEvening
index|]
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOp
argument_list|(
literal|"Save"
argument_list|)
expr_stmt|;
block|}
comment|// Add / Update
if|if
condition|(
literal|"Update"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Save"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
comment|// Validate input
name|ActionMessages
name|errors
init|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
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
if|if
condition|(
name|myForm
operator|.
name|getAutoSetup
argument_list|()
condition|)
name|myForm
operator|.
name|setDays
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOp
argument_list|(
name|myForm
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|?
literal|"Save"
else|:
literal|"Update"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|(
operator|new
name|ExamPeriodDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|ExamPeriod
name|ep
init|=
name|myForm
operator|.
name|saveOrUpdate
argument_list|(
name|request
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|ep
operator|!=
literal|null
condition|)
block|{
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|ep
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|EXAM_PERIOD_EDIT
argument_list|,
operator|(
literal|"Save"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|?
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
else|:
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
operator|)
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
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
name|myForm
operator|.
name|setOp
argument_list|(
literal|"List"
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getUniqueId
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
name|myForm
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Edit
if|if
condition|(
literal|"Edit"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|String
name|id
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
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
name|id
operator|==
literal|null
operator|||
name|id
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
literal|"key"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
literal|"Unique Id : "
operator|+
name|id
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
name|mapping
operator|.
name|findForward
argument_list|(
literal|"list"
argument_list|)
return|;
block|}
else|else
block|{
name|ExamPeriod
name|ep
init|=
operator|(
operator|new
name|ExamPeriodDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|id
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|ep
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
literal|"Unique Id : "
operator|+
name|id
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
name|mapping
operator|.
name|findForward
argument_list|(
literal|"list"
argument_list|)
return|;
block|}
else|else
block|{
name|myForm
operator|.
name|load
argument_list|(
name|ep
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Delete
if|if
condition|(
literal|"Delete"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|(
operator|new
name|ExamPeriodDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hibSession
operator|.
name|getTransaction
argument_list|()
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|ExamPeriod
name|ep
init|=
operator|(
operator|new
name|ExamPeriodDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|ep
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|EXAM_PERIOD_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|delete
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
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
name|myForm
operator|.
name|load
argument_list|(
literal|null
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOp
argument_list|(
literal|"List"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"List"
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getOp
argument_list|()
argument_list|)
condition|)
block|{
comment|// Read all existing settings and store in request
name|getExamPeriods
argument_list|(
name|request
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"list"
argument_list|)
return|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
name|myForm
operator|.
name|getAutoSetup
argument_list|()
condition|?
literal|"evening"
else|:
name|myForm
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|?
literal|"add"
else|:
literal|"edit"
argument_list|)
return|;
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
throw|throw
name|e
throw|;
block|}
block|}
specifier|private
name|void
name|getExamPeriods
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"examPeriods.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
comment|// Create web table instance
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|4
argument_list|,
literal|null
argument_list|,
literal|"examPeriodEdit.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Type"
block|,
literal|"Date"
block|,
literal|"Start Time"
block|,
literal|"End Time"
block|,
literal|"Length"
block|,
literal|"Preference"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|TreeSet
name|periods
init|=
name|ExamPeriod
operator|.
name|findAll
argument_list|(
name|request
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|periods
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|webTable
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"No exan period defined for this session."
block|}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
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
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|sdf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"EEE MM/dd/yyyy"
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|stf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"hh:mm aa"
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|periods
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
name|ExamPeriod
name|ep
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|onClick
init|=
literal|"onClick=\"document.location='examPeriodEdit.do?op=Edit&id="
operator|+
name|ep
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
decl_stmt|;
name|String
name|deptStr
init|=
literal|""
decl_stmt|;
name|String
name|deptCmp
init|=
literal|""
decl_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
name|onClick
argument_list|,
operator|new
name|String
index|[]
block|{
name|Exam
operator|.
name|sExamTypes
index|[
name|ep
operator|.
name|getExamType
argument_list|()
index|]
block|,
literal|"<a name='"
operator|+
name|ep
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|sdf
operator|.
name|format
argument_list|(
name|ep
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|+
literal|"</a>"
block|,
name|stf
operator|.
name|format
argument_list|(
name|ep
operator|.
name|getStartTime
argument_list|()
argument_list|)
block|,
name|stf
operator|.
name|format
argument_list|(
name|ep
operator|.
name|getEndTime
argument_list|()
argument_list|)
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|ep
operator|.
name|getLength
argument_list|()
argument_list|)
block|,
operator|(
name|PreferenceLevel
operator|.
name|sNeutral
operator|.
name|equals
argument_list|(
name|ep
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|?
literal|""
else|:
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|ep
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|ep
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
operator|+
literal|"</font>"
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|ep
operator|.
name|getExamType
argument_list|()
block|,
name|ep
operator|.
name|getStartDate
argument_list|()
block|,
name|ep
operator|.
name|getStartSlot
argument_list|()
block|,
name|ep
operator|.
name|getStartSlot
argument_list|()
operator|+
name|ep
operator|.
name|getLength
argument_list|()
block|,
name|ep
operator|.
name|getLength
argument_list|()
block|,
name|ep
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefId
argument_list|()
block|}
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ExamPeriods.table"
argument_list|,
name|webTable
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"examPeriods.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

