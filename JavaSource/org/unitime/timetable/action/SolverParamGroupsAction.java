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
name|hibernate
operator|.
name|criterion
operator|.
name|Order
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|SolverParamGroupsForm
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
name|SolverParameterGroup
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
name|SolverParameterGroupDAO
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
literal|"/solverParamGroups"
argument_list|)
specifier|public
class|class
name|SolverParamGroupsAction
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
name|SolverParamGroupsForm
name|myForm
init|=
operator|(
name|SolverParamGroupsForm
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
name|SolverParameterGroups
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
name|setOp
argument_list|(
literal|"List"
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
literal|"Add Solver Parameter Group"
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
block|}
else|else
block|{
name|SolverParameterGroupDAO
name|dao
init|=
operator|new
name|SolverParameterGroupDAO
argument_list|()
decl_stmt|;
name|SolverParameterGroup
name|group
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
literal|"Save"
argument_list|)
condition|)
name|group
operator|=
operator|new
name|SolverParameterGroup
argument_list|()
expr_stmt|;
else|else
name|group
operator|=
name|dao
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|group
operator|.
name|setName
argument_list|(
name|myForm
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|group
operator|.
name|setDescription
argument_list|(
name|myForm
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|group
operator|.
name|setType
argument_list|(
name|myForm
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getOrder
argument_list|()
operator|<
literal|0
condition|)
block|{
name|group
operator|.
name|setOrder
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|dao
operator|.
name|findAll
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|dao
operator|.
name|saveOrUpdate
argument_list|(
name|group
argument_list|)
expr_stmt|;
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
block|}
else|else
block|{
name|SolverParameterGroupDAO
name|dao
init|=
operator|new
name|SolverParameterGroupDAO
argument_list|()
decl_stmt|;
name|SolverParameterGroup
name|group
init|=
name|dao
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
block|}
else|else
block|{
name|myForm
operator|.
name|setUniqueId
argument_list|(
name|group
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setName
argument_list|(
name|group
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOrder
argument_list|(
name|group
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setType
argument_list|(
name|group
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setDescription
argument_list|(
name|group
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setOp
argument_list|(
literal|"Update"
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
name|SolverParameterGroupDAO
name|dao
init|=
operator|new
name|SolverParameterGroupDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|dao
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
name|SolverParameterGroup
name|group
init|=
name|dao
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
name|List
name|list
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|SolverParameterGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|gt
argument_list|(
literal|"order"
argument_list|,
name|group
operator|.
name|getOrder
argument_list|()
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|list
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
name|SolverParameterGroup
name|g
init|=
operator|(
name|SolverParameterGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|g
operator|.
name|setOrder
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|g
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|dao
operator|.
name|save
argument_list|(
name|g
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
name|dao
operator|.
name|delete
argument_list|(
name|group
argument_list|,
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
name|SolverParameterGroupDAO
name|dao
init|=
operator|new
name|SolverParameterGroupDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|dao
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
name|SolverParameterGroup
name|group
init|=
name|dao
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
name|List
name|list
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|SolverParameterGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"order"
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|group
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|SolverParameterGroup
name|prior
init|=
operator|(
name|SolverParameterGroup
operator|)
name|list
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|prior
operator|.
name|setOrder
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|prior
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|dao
operator|.
name|save
argument_list|(
name|prior
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|group
operator|.
name|setOrder
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|group
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|dao
operator|.
name|save
argument_list|(
name|group
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|List
name|list
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|SolverParameterGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"order"
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|group
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
operator|+
literal|1
argument_list|)
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|SolverParameterGroup
name|next
init|=
operator|(
name|SolverParameterGroup
operator|)
name|list
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|next
operator|.
name|setOrder
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|next
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|dao
operator|.
name|save
argument_list|(
name|next
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|group
operator|.
name|setOrder
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|group
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|dao
operator|.
name|save
argument_list|(
name|group
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
name|myForm
operator|.
name|setOrder
argument_list|(
name|group
operator|.
name|getOrder
argument_list|()
operator|.
name|intValue
argument_list|()
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
comment|//Read all existing settings and store in request
name|getSolverParameterGroups
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
specifier|private
name|void
name|getSolverParameterGroups
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"solverParamGroups.ord"
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
literal|3
argument_list|,
literal|null
argument_list|,
literal|"solverParamGroups.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Order"
block|,
literal|"Name"
block|,
literal|"Type"
block|,
literal|"Description"
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
block|}
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
try|try
block|{
name|SolverParameterGroupDAO
name|dao
init|=
operator|new
name|SolverParameterGroupDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|dao
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
name|List
name|list
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|SolverParameterGroup
operator|.
name|class
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"order"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|size
operator|=
name|list
operator|.
name|size
argument_list|()
expr_stmt|;
if|if
condition|(
name|list
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
literal|"No solver parameter group defined."
block|}
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|list
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
name|SolverParameterGroup
name|group
init|=
operator|(
name|SolverParameterGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|onClick
init|=
literal|"onClick=\"document.location='solverParamGroups.do?op=Edit&id="
operator|+
name|group
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
decl_stmt|;
name|String
name|ops
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|group
operator|.
name|getOrder
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
literal|"<img src='images/arrow_up.png' border='0' align='absmiddle' title='Move Up' "
operator|+
literal|"onclick=\"solverParamGroupsForm.op2.value='Move Up';solverParamGroupsForm.uniqueId.value='"
operator|+
name|group
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';solverParamGroupsForm.submit(); event.cancelBubble=true;\">"
expr_stmt|;
block|}
else|else
name|ops
operator|+=
literal|"<img src='images/blank.png' border='0' align='absmiddle'>"
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
literal|"<img src='images/arrow_down.png' border='0' align='absmiddle' title='Move Down' "
operator|+
literal|"onclick=\"solverParamGroupsForm.op2.value='Move Down';solverParamGroupsForm.uniqueId.value='"
operator|+
name|group
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';solverParamGroupsForm.submit(); event.cancelBubble=true;\">"
expr_stmt|;
block|}
else|else
name|ops
operator|+=
literal|"<img src='images/blank.png' border='0' align='absmiddle'>"
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
name|group
operator|.
name|getName
argument_list|()
block|,
name|group
operator|.
name|getSolverType
argument_list|()
operator|.
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
block|,
name|group
operator|.
name|getDescription
argument_list|()
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|group
operator|.
name|getOrder
argument_list|()
block|,
name|group
operator|.
name|getName
argument_list|()
block|,
name|group
operator|.
name|getType
argument_list|()
block|,
name|group
operator|.
name|getDescription
argument_list|()
block|}
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
throw|throw
name|e
throw|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"SolverParameterGroup.table"
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
literal|"solverParamGroups.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"SolverParameterGroup.last"
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|size
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

