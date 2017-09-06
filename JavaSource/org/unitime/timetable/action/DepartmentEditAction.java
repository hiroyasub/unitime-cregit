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
name|MessageResources
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
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|DepartmentEditForm
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
name|TimePref
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
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/departmentEdit"
argument_list|)
specifier|public
class|class
name|DepartmentEditAction
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
name|DepartmentEditForm
name|myForm
init|=
operator|(
name|DepartmentEditForm
operator|)
name|form
decl_stmt|;
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
comment|// Check Access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Departments
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
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|DEPT_ATTR_NAME
argument_list|,
name|Department
operator|.
name|findAllNonExternal
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Edit
if|if
condition|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"op.edit"
argument_list|)
operator|.
name|equalsIgnoreCase
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
name|Department
name|department
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
name|id
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|!=
literal|null
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|department
argument_list|,
name|Right
operator|.
name|DepartmentEdit
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|load
argument_list|(
name|department
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
block|}
comment|// Add
if|if
condition|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addDepartment"
argument_list|)
operator|.
name|equalsIgnoreCase
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
name|setSessionId
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setInheritInstructorPreferences
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setAllowStudentScheduling
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|DepartmentAdd
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"add"
argument_list|)
return|;
block|}
comment|// Update/Save
if|if
condition|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.updateDepartment"
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|op
argument_list|)
operator|||
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.saveDepartment"
argument_list|)
operator|.
name|equalsIgnoreCase
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
name|getId
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"edit"
argument_list|)
return|;
else|else
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"add"
argument_list|)
return|;
block|}
else|else
block|{
if|if
condition|(
name|myForm
operator|.
name|getId
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
literal|0l
argument_list|)
condition|)
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|DepartmentAdd
argument_list|)
expr_stmt|;
else|else
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|myForm
operator|.
name|getId
argument_list|()
argument_list|,
literal|"Department"
argument_list|,
name|Right
operator|.
name|DepartmentEdit
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|save
argument_list|(
name|sessionContext
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"Add Status"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|addBlankDependentDepartment
argument_list|()
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
name|myForm
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
literal|"add"
else|:
literal|"edit"
argument_list|)
return|;
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
name|myForm
operator|.
name|deleteAllDependentDepartments
argument_list|()
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
name|myForm
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
literal|"add"
else|:
literal|"edit"
argument_list|)
return|;
block|}
if|if
condition|(
literal|"Delete"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"deleteId"
argument_list|)
operator|!=
literal|null
operator|&&
operator|!
name|request
operator|.
name|getParameter
argument_list|(
literal|"deleteId"
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|myForm
operator|.
name|deleteDependentDepartment
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"deleteId"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
name|myForm
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
literal|"add"
else|:
literal|"edit"
argument_list|)
return|;
block|}
comment|// Delete
if|if
condition|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.deleteDepartment"
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|doDelete
argument_list|(
name|myForm
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|myForm
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|JUMP_TO_ATTR_NAME
argument_list|,
name|myForm
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"back"
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
comment|/** 	 * Delete a department 	 * @param request 	 * @param myForm 	 */
specifier|private
name|void
name|doDelete
parameter_list|(
name|DepartmentEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|frm
operator|.
name|getId
argument_list|()
argument_list|,
literal|"Department"
argument_list|,
name|Right
operator|.
name|DepartmentDelete
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|DepartmentDAO
argument_list|()
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
name|Department
name|department
init|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|department
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select c from Class_ c where c.managingDept.uniqueId=:deptId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|iterate
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getManagingDept
argument_list|()
operator|.
name|equals
argument_list|(
name|department
argument_list|)
condition|)
block|{
comment|// Clear all room preferences from the subpart
for|for
control|(
name|Iterator
name|j
init|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getPreferences
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
name|Object
name|pref
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|pref
operator|instanceof
name|TimePref
operator|)
condition|)
name|j
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|deleteAllDistributionPreferences
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|clazz
operator|.
name|setManagingDept
argument_list|(
name|clazz
operator|.
name|getControllingDept
argument_list|()
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
comment|// Clear all room preferences from the class
for|for
control|(
name|Iterator
name|j
init|=
name|clazz
operator|.
name|getPreferences
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
name|Object
name|pref
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|pref
operator|instanceof
name|TimePref
operator|)
condition|)
name|j
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|clazz
operator|.
name|deleteAllDistributionPreferences
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"delete StudentClassEnrollment e where e.clazz.uniqueId in "
operator|+
literal|"(select c.uniqueId from Class_ c, CourseOffering co where "
operator|+
literal|"co.isControl=1 and "
operator|+
literal|"c.schedulingSubpart.instrOfferingConfig.instructionalOffering=co.instructionalOffering and "
operator|+
literal|"co.subjectArea.department.uniqueId=:deptId)"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|department
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
block|}
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|department
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DEPARTMENT_EDIT
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
name|hibSession
operator|.
name|delete
argument_list|(
name|department
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|HibernateUtil
operator|.
name|clearCache
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|HibernateException
name|e
parameter_list|)
block|{
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
block|}
end_class

end_unit

