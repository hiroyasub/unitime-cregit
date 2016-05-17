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
name|security
operator|.
name|permissions
package|;
end_package

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
name|security
operator|.
name|UserContext
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
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructorSchedulingPermissions
block|{
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|InstructorScheduling
argument_list|)
specifier|public
specifier|static
class|class
name|InstructorScheduling
implements|implements
name|Permission
argument_list|<
name|SolverGroup
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionDepartment
name|permissionDepartment
decl_stmt|;
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|SolverGroup
name|source
parameter_list|)
block|{
for|for
control|(
name|Department
name|department
range|:
name|source
operator|.
name|getDepartments
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|department
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|InstructorScheduling
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|OwnerLimitedEdit
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
for|for
control|(
name|Department
name|department
range|:
name|source
operator|.
name|getDepartments
argument_list|()
control|)
block|{
for|for
control|(
name|DepartmentalInstructor
name|di
range|:
name|department
operator|.
name|getInstructors
argument_list|()
control|)
if|if
condition|(
name|di
operator|.
name|getTeachingPreference
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|PreferenceLevel
operator|.
name|sProhibited
operator|.
name|equals
argument_list|(
name|di
operator|.
name|getTeachingPreference
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|SolverGroup
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|SolverGroup
operator|.
name|class
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|InstructorSchedulingSolver
argument_list|)
specifier|public
specifier|static
class|class
name|InstructorSchedulingSolver
extends|extends
name|InstructorScheduling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|InstructorSchedulingSolverLog
argument_list|)
specifier|public
specifier|static
class|class
name|InstructorSchedulingSolverLog
extends|extends
name|InstructorSchedulingSolver
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|InstructorSchedulingSolutionExportXml
argument_list|)
specifier|public
specifier|static
class|class
name|InstructorSchedulingSolutionExportXml
extends|extends
name|InstructorSchedulingSolver
block|{}
block|}
end_class

end_unit
