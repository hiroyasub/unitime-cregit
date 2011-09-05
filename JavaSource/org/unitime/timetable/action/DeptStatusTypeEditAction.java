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
name|DeptStatusTypeEditForm
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
name|DepartmentStatusType
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
name|DepartmentStatusTypeDAO
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DeptStatusTypeEditAction
extends|extends
name|Action
block|{
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
name|DeptStatusTypeEditForm
name|myForm
init|=
operator|(
name|DeptStatusTypeEditForm
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
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|op
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
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
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Add Status Type"
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
name|DepartmentStatusTypeDAO
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
name|myForm
operator|.
name|saveOrUpdate
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
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
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
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
literal|"reference"
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
block|}
else|else
block|{
name|DepartmentStatusType
name|s
init|=
operator|(
operator|new
name|DepartmentStatusTypeDAO
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
name|s
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"reference"
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
block|}
else|else
block|{
name|myForm
operator|.
name|load
argument_list|(
name|s
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
name|DepartmentStatusTypeDAO
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
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
comment|// Move Up or Down
if|if
condition|(
literal|"Move Up"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Move Down"
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
name|DepartmentStatusTypeDAO
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
name|DepartmentStatusType
name|curStatus
init|=
operator|(
operator|new
name|DepartmentStatusTypeDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"Move Up"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|DepartmentStatusType
operator|.
name|findAll
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
name|DepartmentStatusType
name|s
init|=
operator|(
name|DepartmentStatusType
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|getOrd
argument_list|()
operator|+
literal|1
operator|==
name|curStatus
operator|.
name|getOrd
argument_list|()
condition|)
block|{
name|s
operator|.
name|setOrd
argument_list|(
name|s
operator|.
name|getOrd
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|found
condition|)
block|{
name|curStatus
operator|.
name|setOrd
argument_list|(
name|curStatus
operator|.
name|getOrd
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOrder
argument_list|(
name|curStatus
operator|.
name|getOrd
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|curStatus
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|DepartmentStatusType
operator|.
name|findAll
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
name|DepartmentStatusType
name|s
init|=
operator|(
name|DepartmentStatusType
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|getOrd
argument_list|()
operator|-
literal|1
operator|==
name|curStatus
operator|.
name|getOrd
argument_list|()
condition|)
block|{
name|s
operator|.
name|setOrd
argument_list|(
name|s
operator|.
name|getOrd
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|found
condition|)
block|{
name|curStatus
operator|.
name|setOrd
argument_list|(
name|curStatus
operator|.
name|getOrd
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOrder
argument_list|(
name|curStatus
operator|.
name|getOrd
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|curStatus
argument_list|)
expr_stmt|;
block|}
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
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
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
name|getDeptStatusList
argument_list|(
name|request
argument_list|,
name|sessionId
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
literal|"Save"
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getOp
argument_list|()
argument_list|)
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
name|getDeptStatusList
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Long
name|sessionId
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
literal|"deptStatusTypes.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
literal|2
argument_list|)
expr_stmt|;
comment|// Create web table instance
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|5
argument_list|,
literal|null
argument_list|,
literal|"deptStatusTypeEdit.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|""
block|,
literal|"Reference"
block|,
literal|"Label"
block|,
literal|"Apply"
block|,
literal|"Rights"
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
block|}
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|TreeSet
name|statuses
init|=
name|DepartmentStatusType
operator|.
name|findAll
argument_list|()
decl_stmt|;
if|if
condition|(
name|statuses
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
literal|"No status defined."
block|}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|int
name|ord
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|statuses
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
name|DepartmentStatusType
name|s
init|=
operator|(
name|DepartmentStatusType
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|ord
operator|!=
name|s
operator|.
name|getOrd
argument_list|()
condition|)
block|{
name|s
operator|.
name|setOrd
argument_list|(
name|ord
argument_list|)
expr_stmt|;
name|DepartmentStatusTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
name|ord
operator|++
expr_stmt|;
name|String
name|onClick
init|=
literal|"onClick=\"document.location='deptStatusTypeEdit.do?op=Edit&id="
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
decl_stmt|;
name|String
name|rights
init|=
literal|""
decl_stmt|;
name|String
name|apply
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|applyDepartment
argument_list|()
condition|)
block|{
if|if
condition|(
name|s
operator|.
name|applySession
argument_list|()
condition|)
name|apply
operator|=
literal|"Both"
expr_stmt|;
else|else
name|apply
operator|=
literal|"Department"
expr_stmt|;
block|}
if|else if
condition|(
name|s
operator|.
name|applySession
argument_list|()
condition|)
name|apply
operator|=
literal|"Session"
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canOwnerView
argument_list|()
operator|||
name|s
operator|.
name|canOwnerLimitedEdit
argument_list|()
operator|||
name|s
operator|.
name|canOwnerEdit
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"owner can "
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canOwnerView
argument_list|()
operator|&&
name|s
operator|.
name|canOwnerEdit
argument_list|()
condition|)
name|rights
operator|+=
literal|"do all"
expr_stmt|;
else|else
block|{
if|if
condition|(
name|s
operator|.
name|canOwnerView
argument_list|()
condition|)
name|rights
operator|+=
literal|"view"
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canOwnerEdit
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|rights
operator|.
name|endsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|rights
operator|+=
literal|" and "
expr_stmt|;
name|rights
operator|+=
literal|"edit"
expr_stmt|;
block|}
if|else if
condition|(
name|s
operator|.
name|canOwnerLimitedEdit
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|rights
operator|.
name|endsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|rights
operator|+=
literal|" and "
expr_stmt|;
name|rights
operator|+=
literal|"limited edit"
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|s
operator|.
name|canManagerView
argument_list|()
operator|||
name|s
operator|.
name|canManagerLimitedEdit
argument_list|()
operator|||
name|s
operator|.
name|canManagerEdit
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"manager can "
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canManagerView
argument_list|()
operator|&&
name|s
operator|.
name|canManagerEdit
argument_list|()
condition|)
name|rights
operator|+=
literal|"do all"
expr_stmt|;
else|else
block|{
if|if
condition|(
name|s
operator|.
name|canManagerView
argument_list|()
condition|)
name|rights
operator|+=
literal|"view"
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canManagerEdit
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|rights
operator|.
name|endsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|rights
operator|+=
literal|" and "
expr_stmt|;
name|rights
operator|+=
literal|"edit"
expr_stmt|;
block|}
if|else if
condition|(
name|s
operator|.
name|canManagerLimitedEdit
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|rights
operator|.
name|endsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|rights
operator|+=
literal|" and "
expr_stmt|;
name|rights
operator|+=
literal|"limited edit"
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|s
operator|.
name|canAudit
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"audit"
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|.
name|canTimetable
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"timetable"
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|.
name|canCommit
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"commit"
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|.
name|canExamView
argument_list|()
operator|||
name|s
operator|.
name|canExamEdit
argument_list|()
operator|||
name|s
operator|.
name|canExamTimetable
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"exam "
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canExamEdit
argument_list|()
operator|&&
name|s
operator|.
name|canExamTimetable
argument_list|()
condition|)
name|rights
operator|+=
literal|"do all"
expr_stmt|;
else|else
block|{
if|if
condition|(
name|s
operator|.
name|canExamView
argument_list|()
condition|)
name|rights
operator|+=
literal|"view"
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canExamEdit
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|rights
operator|.
name|endsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|rights
operator|+=
literal|" and "
expr_stmt|;
name|rights
operator|+=
literal|"edit"
expr_stmt|;
block|}
if|else if
condition|(
name|s
operator|.
name|canExamTimetable
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|rights
operator|.
name|endsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|rights
operator|+=
literal|" and "
expr_stmt|;
name|rights
operator|+=
literal|"timetable"
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|s
operator|.
name|canOnlineSectionStudents
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"sectioning"
expr_stmt|;
block|}
if|else if
condition|(
name|s
operator|.
name|canSectionAssistStudents
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"assistant"
expr_stmt|;
block|}
if|else if
condition|(
name|s
operator|.
name|canPreRegisterStudents
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"registration"
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|.
name|canNoRoleReportExamFinal
argument_list|()
operator|||
name|s
operator|.
name|canNoRoleReportExamMidterm
argument_list|()
operator|||
name|s
operator|.
name|canNoRoleReportClass
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"no-role"
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canNoRoleReportExamFinal
argument_list|()
operator|&&
name|s
operator|.
name|canNoRoleReportExamMidterm
argument_list|()
operator|&&
name|s
operator|.
name|canNoRoleReportClass
argument_list|()
condition|)
name|rights
operator|+=
literal|" all"
expr_stmt|;
else|else
block|{
if|if
condition|(
name|s
operator|.
name|canNoRoleReportClass
argument_list|()
condition|)
name|rights
operator|+=
literal|" classes"
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canNoRoleReportExamFinal
argument_list|()
operator|&&
name|s
operator|.
name|canNoRoleReportExamMidterm
argument_list|()
condition|)
name|rights
operator|+=
literal|" exams"
expr_stmt|;
else|else
block|{
if|if
condition|(
name|s
operator|.
name|canNoRoleReportExamFinal
argument_list|()
condition|)
name|rights
operator|+=
literal|" final exams"
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|canNoRoleReportExamMidterm
argument_list|()
condition|)
name|rights
operator|+=
literal|" midterm exams"
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|s
operator|.
name|isTestSession
argument_list|()
condition|)
block|{
if|if
condition|(
name|rights
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|rights
operator|+=
literal|"; "
expr_stmt|;
name|rights
operator|+=
literal|"test session"
expr_stmt|;
block|}
name|String
name|ops
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|getOrd
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>
literal|0
condition|)
block|{
name|ops
operator|+=
literal|"<img src='images/arrow_u.gif' border='0' align='absmiddle' title='Move Up' "
operator|+
literal|"onclick=\"deptStatusTypeEditForm.op2.value='Move Up';deptStatusTypeEditForm.uniqueId.value='"
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';deptStatusTypeEditForm.submit(); event.cancelBubble=true;\">"
expr_stmt|;
block|}
else|else
name|ops
operator|+=
literal|"<img src='images/blank.gif' border='0' align='absmiddle'>"
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ops
operator|+=
literal|"<img src='images/arrow_d.gif' border='0' align='absmiddle' title='Move Down' "
operator|+
literal|"onclick=\"deptStatusTypeEditForm.op2.value='Move Down';deptStatusTypeEditForm.uniqueId.value='"
operator|+
name|s
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';deptStatusTypeEditForm.submit(); event.cancelBubble=true;\">"
expr_stmt|;
block|}
else|else
name|ops
operator|+=
literal|"<img src='images/blank.gif' border='0' align='absmiddle'>"
expr_stmt|;
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
name|ops
block|,
name|s
operator|.
name|getReference
argument_list|()
block|,
name|s
operator|.
name|getLabel
argument_list|()
block|,
name|apply
block|,
name|rights
block|,         		}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|s
operator|.
name|getOrd
argument_list|()
block|,
name|s
operator|.
name|getOrd
argument_list|()
block|,
name|s
operator|.
name|getLabel
argument_list|()
block|,
name|s
operator|.
name|getApply
argument_list|()
block|,
name|s
operator|.
name|getStatus
argument_list|()
block|,                              		}
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"DeptStatusType.last"
argument_list|,
operator|new
name|Integer
argument_list|(
name|statuses
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"DeptStatusType.table"
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
literal|"deptStatusTypes.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

