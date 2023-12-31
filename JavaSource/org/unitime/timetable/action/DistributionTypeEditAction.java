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
name|Enumeration
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
name|List
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
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
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
name|timetable
operator|.
name|form
operator|.
name|DistributionTypeEditForm
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
name|DistributionType
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
name|model
operator|.
name|dao
operator|.
name|DistributionTypeDAO
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
name|ActionErrors
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:action path="/distributionTypeEdit" name="distributionTypeEditForm" parameter="do" scope="request" validate="true"  * @struts:action-forward name="showEdit" path="/admin/distributionTypeEdit.jsp"  * @struts:action-forward name="showDistributionTypeList" path="/distributionTypeList.do" redirect="true"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/distributionTypeEdit"
argument_list|)
specifier|public
class|class
name|DistributionTypeEditAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 * @throws HibernateException 	 */
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
name|DistributionTypeEditForm
name|myForm
init|=
operator|(
name|DistributionTypeEditForm
operator|)
name|form
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|DistributionTypeEdit
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
name|Long
name|sessionId
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
name|Long
name|id
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|setRefTableEntry
argument_list|(
operator|(
operator|new
name|DistributionTypeDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|id
argument_list|)
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getParameterValues
argument_list|(
literal|"depts"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|depts
init|=
name|request
operator|.
name|getParameterValues
argument_list|(
literal|"depts"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|depts
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|myForm
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|add
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|depts
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|List
name|list
init|=
operator|(
operator|new
name|DepartmentDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|Department
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
literal|"session.uniqueId"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"deptCode"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|Vector
name|availableDepts
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|list
operator|.
name|iterator
argument_list|()
init|;
name|iter
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
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|availableDepts
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
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|availableDepts
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Save"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|DistributionTypeDAO
name|dao
init|=
operator|new
name|DistributionTypeDAO
argument_list|()
decl_stmt|;
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
name|DistributionType
name|distType
init|=
name|dao
operator|.
name|get
argument_list|(
name|myForm
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|DistributionType
name|x
init|=
operator|(
name|DistributionType
operator|)
name|myForm
operator|.
name|getRefTableEntry
argument_list|()
decl_stmt|;
name|distType
operator|.
name|setAbbreviation
argument_list|(
name|x
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|distType
operator|.
name|setAllowedPref
argument_list|(
name|x
operator|.
name|getAllowedPref
argument_list|()
argument_list|)
expr_stmt|;
name|distType
operator|.
name|setDescr
argument_list|(
name|x
operator|.
name|getDescr
argument_list|()
argument_list|)
expr_stmt|;
name|distType
operator|.
name|setInstructorPref
argument_list|(
name|x
operator|.
name|isInstructorPref
argument_list|()
operator|==
literal|null
condition|?
name|Boolean
operator|.
name|FALSE
else|:
name|x
operator|.
name|isInstructorPref
argument_list|()
argument_list|)
expr_stmt|;
name|distType
operator|.
name|setLabel
argument_list|(
name|x
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|distType
operator|.
name|setVisible
argument_list|(
name|x
operator|.
name|isVisible
argument_list|()
operator|==
literal|null
condition|?
name|Boolean
operator|.
name|FALSE
else|:
name|x
operator|.
name|isVisible
argument_list|()
argument_list|)
expr_stmt|;
name|HashSet
name|oldDepts
init|=
operator|new
name|HashSet
argument_list|(
name|distType
operator|.
name|getDepartments
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|myForm
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Long
name|departmentId
init|=
operator|(
name|Long
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
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
name|departmentId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|oldDepts
operator|.
name|remove
argument_list|(
name|d
argument_list|)
condition|)
block|{
comment|//not changed -> do nothing
block|}
else|else
block|{
name|distType
operator|.
name|getDepartments
argument_list|()
operator|.
name|add
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|oldDepts
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
operator|!
name|d
operator|.
name|getSessionId
argument_list|()
operator|.
name|equals
argument_list|(
name|sessionId
argument_list|)
condition|)
continue|continue;
name|distType
operator|.
name|getDepartments
argument_list|()
operator|.
name|remove
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|distType
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|distType
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DIST_TYPE_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
literal|null
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDistributionTypeList"
argument_list|)
return|;
block|}
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDistributionTypeList"
argument_list|)
return|;
block|}
if|if
condition|(
literal|"Add Department"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getDepartmentId
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getDepartmentId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"department"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No department selected."
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|boolean
name|contains
init|=
name|myForm
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|myForm
operator|.
name|getDepartmentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|contains
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"department"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Department already present in the list of departments."
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|myForm
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|add
argument_list|(
name|myForm
operator|.
name|getDepartmentId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"Remove Department"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getDepartmentId
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getDepartmentId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"department"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No department selected."
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|boolean
name|contains
init|=
name|myForm
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|myForm
operator|.
name|getDepartmentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|contains
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"department"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Department not present in the list of departments."
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|myForm
operator|.
name|getDepartmentIds
argument_list|()
operator|.
name|remove
argument_list|(
name|myForm
operator|.
name|getDepartmentId
argument_list|()
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
block|}
end_class

end_unit

