begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|SolverGroupEditForm
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
name|SolverGroup
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
name|SessionDAO
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
name|SolverGroupDAO
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
name|util
operator|.
name|Formats
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
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/solverGroupEdit"
argument_list|)
specifier|public
class|class
name|SolverGroupEditAction
extends|extends
name|Action
block|{
specifier|private
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|sDF
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_TIME_STAMP
argument_list|)
decl_stmt|;
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
name|SolverGroupEditForm
name|myForm
init|=
operator|(
name|SolverGroupEditForm
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
name|SolverGroups
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
operator|||
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
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
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
name|SolverGroupDAO
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
argument_list|,
name|sessionContext
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
literal|"edit"
argument_list|)
return|;
block|}
else|else
block|{
name|SolverGroup
name|group
init|=
operator|(
operator|new
name|SolverGroupDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|group
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
literal|"edit"
argument_list|)
return|;
block|}
else|else
block|{
name|myForm
operator|.
name|load
argument_list|(
name|group
argument_list|,
name|session
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
name|SolverGroupDAO
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
argument_list|,
name|sessionContext
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
name|setOp
argument_list|(
literal|"List"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Add Solver Group"
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
name|session
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Delete All"
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
name|SolverGroupDAO
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
for|for
control|(
name|Iterator
name|i
init|=
name|SolverGroup
operator|.
name|findBySessionId
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
name|SolverGroup
name|group
init|=
operator|(
name|SolverGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|group
operator|.
name|getSolutions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
for|for
control|(
name|Iterator
name|j
init|=
name|group
operator|.
name|getDepartments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|dept
init|=
operator|(
name|Department
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|dept
operator|.
name|setSolverGroup
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|dept
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|group
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimetableManager
name|mgr
init|=
operator|(
name|TimetableManager
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|mgr
operator|.
name|getSolverGroups
argument_list|()
operator|.
name|remove
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|mgr
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|delete
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
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
block|}
if|if
condition|(
literal|"Auto Setup"
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
name|SolverGroupDAO
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
name|TreeSet
name|allDepts
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|Comparator
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|Department
name|d1
init|=
operator|(
name|Department
operator|)
name|o1
decl_stmt|;
name|Department
name|d2
init|=
operator|(
name|Department
operator|)
name|o2
decl_stmt|;
name|int
name|cmp
init|=
operator|-
name|Double
operator|.
name|compare
argument_list|(
name|d1
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
name|d2
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|d1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|allDepts
operator|.
name|addAll
argument_list|(
name|session
operator|.
name|getDepartments
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|allDepts
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
name|getSolverGroup
argument_list|()
operator|!=
literal|null
condition|)
continue|continue;
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
name|SolverGroup
name|sg
init|=
operator|new
name|SolverGroup
argument_list|()
decl_stmt|;
name|sg
operator|.
name|setAbbv
argument_list|(
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setName
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
name|getExternalMgrLabel
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|" Manager"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setTimetableManagers
argument_list|(
operator|new
name|HashSet
argument_list|<
name|TimetableManager
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|sg
argument_list|)
expr_stmt|;
name|d
operator|.
name|setSolverGroup
argument_list|(
name|sg
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|d
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimetableManager
name|mgr
init|=
operator|(
name|TimetableManager
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|mgr
operator|.
name|getSolverGroups
argument_list|()
operator|.
name|add
argument_list|(
name|sg
argument_list|)
expr_stmt|;
name|sg
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|add
argument_list|(
name|mgr
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|mgr
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
operator|!
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|d
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Set
name|depts
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|d
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimetableManager
name|mgr
init|=
operator|(
name|TimetableManager
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|Set
name|myDepts
init|=
name|mgr
operator|.
name|departmentsForSession
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|depts
operator|==
literal|null
condition|)
name|depts
operator|=
operator|new
name|HashSet
argument_list|(
name|myDepts
argument_list|)
expr_stmt|;
else|else
name|depts
operator|.
name|retainAll
argument_list|(
name|myDepts
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|depts
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|x
init|=
operator|(
name|Department
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|.
name|getSolverGroup
argument_list|()
operator|!=
literal|null
operator|||
name|x
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|j
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|depts
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|StringBuffer
name|abbv
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|name
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|HashSet
name|mgrs
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|depts
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|x
init|=
operator|(
name|Department
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|mgrs
operator|.
name|addAll
argument_list|(
name|x
operator|.
name|getTimetableManagers
argument_list|()
argument_list|)
expr_stmt|;
name|abbv
operator|.
name|append
argument_list|(
name|x
operator|.
name|getShortLabel
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|name
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|name
operator|.
name|append
argument_list|(
name|x
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|SolverGroup
name|sg
init|=
operator|new
name|SolverGroup
argument_list|()
decl_stmt|;
name|sg
operator|.
name|setAbbv
argument_list|(
name|abbv
operator|.
name|length
argument_list|()
operator|<=
literal|10
condition|?
name|abbv
operator|.
name|toString
argument_list|()
else|:
name|abbv
operator|.
name|toString
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setName
argument_list|(
name|name
operator|.
name|length
argument_list|()
operator|<=
literal|50
condition|?
name|name
operator|.
name|toString
argument_list|()
else|:
name|name
operator|.
name|toString
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|47
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setTimetableManagers
argument_list|(
operator|new
name|HashSet
argument_list|<
name|TimetableManager
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|sg
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|depts
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Department
name|x
init|=
operator|(
name|Department
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|x
operator|.
name|setSolverGroup
argument_list|(
name|sg
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|mgrs
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimetableManager
name|mgr
init|=
operator|(
name|TimetableManager
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|mgr
operator|.
name|getSolverGroups
argument_list|()
operator|.
name|add
argument_list|(
name|sg
argument_list|)
expr_stmt|;
name|sg
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|add
argument_list|(
name|mgr
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|mgr
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
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
block|}
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
name|ExportUtils
operator|.
name|exportPDF
argument_list|(
name|getSolverGroups
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
literal|false
argument_list|)
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"solverGroups.ord"
argument_list|)
argument_list|,
name|response
argument_list|,
literal|"solverGroups"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Read all existing settings and store in request
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
name|PdfWebTable
name|table
init|=
name|getSolverGroups
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"SolverGroups.table"
argument_list|,
name|table
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"solverGroups.ord"
argument_list|)
argument_list|)
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
name|PdfWebTable
name|getSolverGroups
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Session
name|session
parameter_list|,
name|boolean
name|html
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
literal|"solverGroups.ord"
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
name|PdfWebTable
name|webTable
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|5
argument_list|,
operator|(
name|html
condition|?
literal|null
else|:
literal|"Solver Groups - "
operator|+
name|session
operator|.
name|getLabel
argument_list|()
operator|)
argument_list|,
literal|"solverGroupEdit.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Abbv"
block|,
literal|"Name"
block|,
literal|"Departments"
block|,
literal|"Managers"
block|,
literal|"Committed"
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
name|Set
name|solverGroups
init|=
name|SolverGroup
operator|.
name|findBySessionId
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solverGroups
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
literal|"No time pattern defined for this academic initiative and term."
block|}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|(
operator|new
name|SolverGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|solverGroups
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
name|SolverGroup
name|group
init|=
operator|(
name|SolverGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|group
operator|.
name|getDepartments
argument_list|()
operator|==
literal|null
operator|||
name|group
operator|.
name|getTimetableManagers
argument_list|()
operator|==
literal|null
condition|)
name|hibSession
operator|.
name|refresh
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|String
name|onClick
init|=
literal|"onClick=\"document.location='solverGroupEdit.do?op=Edit&id="
operator|+
name|group
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
for|for
control|(
name|Iterator
name|j
init|=
operator|(
operator|new
name|TreeSet
argument_list|(
name|group
operator|.
name|getDepartments
argument_list|()
argument_list|)
operator|)
operator|.
name|iterator
argument_list|()
init|;
name|j
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
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|deptStr
operator|+=
operator|(
name|html
condition|?
literal|"<span title='"
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
literal|"'>"
operator|+
name|d
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|"</span>"
else|:
name|d
operator|.
name|getDeptCode
argument_list|()
operator|)
expr_stmt|;
name|deptCmp
operator|+=
name|d
operator|.
name|getDeptCode
argument_list|()
expr_stmt|;
if|if
condition|(
name|j
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|deptStr
operator|+=
literal|", "
expr_stmt|;
name|deptCmp
operator|+=
literal|","
expr_stmt|;
block|}
block|}
name|String
name|mgrStr
init|=
literal|""
decl_stmt|;
name|String
name|mgrCmp
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
operator|(
operator|new
name|TreeSet
argument_list|(
name|group
operator|.
name|getTimetableManagers
argument_list|()
argument_list|)
operator|)
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimetableManager
name|mgr
init|=
operator|(
name|TimetableManager
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|mgrStr
operator|+=
operator|(
name|html
condition|?
literal|"<span title='"
operator|+
name|mgr
operator|.
name|getName
argument_list|()
operator|+
literal|"'>"
operator|+
name|mgr
operator|.
name|getShortName
argument_list|()
operator|+
literal|"</span>"
else|:
name|mgr
operator|.
name|getShortName
argument_list|()
operator|)
expr_stmt|;
name|mgrCmp
operator|+=
name|mgr
operator|.
name|getLastName
argument_list|()
expr_stmt|;
if|if
condition|(
name|j
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|mgrStr
operator|+=
literal|", "
expr_stmt|;
name|mgrCmp
operator|+=
literal|","
expr_stmt|;
block|}
block|}
name|Date
name|commitDate
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|group
operator|.
name|getCommittedSolution
argument_list|()
operator|!=
literal|null
condition|)
name|commitDate
operator|=
name|group
operator|.
name|getCommittedSolution
argument_list|()
operator|.
name|getCommitDate
argument_list|()
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
operator|(
name|html
condition|?
literal|"<a name='"
operator|+
name|group
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
operator|(
name|html
condition|?
name|group
operator|.
name|getAbbv
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
else|:
name|group
operator|.
name|getAbbv
argument_list|()
operator|)
operator|+
literal|"</a>"
else|:
name|group
operator|.
name|getAbbv
argument_list|()
operator|)
block|,
operator|(
name|html
condition|?
name|group
operator|.
name|getName
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
else|:
name|group
operator|.
name|getName
argument_list|()
operator|)
block|,
name|deptStr
block|,
name|mgrStr
block|,
operator|(
name|commitDate
operator|==
literal|null
condition|?
literal|""
else|:
name|sDF
operator|.
name|format
argument_list|(
name|commitDate
argument_list|)
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|group
operator|.
name|getAbbv
argument_list|()
block|,
name|group
operator|.
name|getName
argument_list|()
block|,
name|deptCmp
block|,
name|mgrCmp
block|,
name|Long
operator|.
name|valueOf
argument_list|(
name|commitDate
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|commitDate
operator|.
name|getTime
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|webTable
return|;
block|}
block|}
end_class

end_unit

