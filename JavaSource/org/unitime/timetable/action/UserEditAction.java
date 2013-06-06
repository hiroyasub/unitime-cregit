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
name|List
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
name|UserEditForm
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
name|UserDAO
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
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/userEdit"
argument_list|)
specifier|public
class|class
name|UserEditAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
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
name|UserEditForm
name|myForm
init|=
operator|(
name|UserEditForm
operator|)
name|form
decl_stmt|;
comment|// Check Access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Users
argument_list|)
expr_stmt|;
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
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
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
literal|"Add User"
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
if|if
condition|(
literal|"Request Password Change"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
literal|"gwt.jsp?page=password&reset=1"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
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
name|UserDAO
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
literal|"externalId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|User
name|u
init|=
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|User
operator|.
name|findByExternalId
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"externalId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalid"
argument_list|,
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
name|u
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
name|UserDAO
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
name|getUserList
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
name|getUserList
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
name|sessionContext
argument_list|,
literal|"users.ord"
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
literal|"userEdit.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"External ID"
block|,
literal|"User Name"
block|,
literal|"Manager"
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
block|}
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|List
name|users
init|=
operator|new
name|UserDAO
argument_list|()
operator|.
name|findAll
argument_list|()
decl_stmt|;
if|if
condition|(
name|users
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
literal|"No users defined."
block|}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|users
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|User
name|user
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|User
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|onClick
init|=
literal|"onClick=\"document.location='userEdit.do?op=Edit&id="
operator|+
name|user
operator|.
name|getExternalUniqueId
argument_list|()
operator|+
literal|"';\""
decl_stmt|;
name|TimetableManager
name|mgr
init|=
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|user
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
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
name|user
operator|.
name|getExternalUniqueId
argument_list|()
block|,
name|user
operator|.
name|getUsername
argument_list|()
block|,
operator|(
name|mgr
operator|==
literal|null
condition|?
literal|""
else|:
name|mgr
operator|.
name|getName
argument_list|()
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|user
operator|.
name|getExternalUniqueId
argument_list|()
block|,
name|user
operator|.
name|getUsername
argument_list|()
block|,
operator|(
name|mgr
operator|==
literal|null
condition|?
literal|""
else|:
name|mgr
operator|.
name|getName
argument_list|()
operator|)
block|}
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"Users.table"
argument_list|,
name|webTable
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"users.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

