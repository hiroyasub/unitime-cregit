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
name|server
operator|.
name|instructor
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|Set
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
name|defaults
operator|.
name|UserProperty
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcResponseList
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|gwt
operator|.
name|resources
operator|.
name|GwtConstants
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
name|dao
operator|.
name|Class_DAO
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
name|gwt
operator|.
name|shared
operator|.
name|InstructorInterface
operator|.
name|InstructorInfo
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
name|gwt
operator|.
name|shared
operator|.
name|InstructorInterface
operator|.
name|TeachingAssignmentsPageRequest
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
name|solver
operator|.
name|instructor
operator|.
name|InstructorSchedulingProxy
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
name|solver
operator|.
name|service
operator|.
name|SolverService
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|TeachingAssignmentsPageRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|TeachingAssignmentsBackend
extends|extends
name|InstructorSchedulingBackendHelper
implements|implements
name|GwtRpcImplementation
argument_list|<
name|TeachingAssignmentsPageRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|InstructorInfo
argument_list|>
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
name|instructorSchedulingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|InstructorInfo
argument_list|>
name|execute
parameter_list|(
name|TeachingAssignmentsPageRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructorSchedulingSolver
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|,
name|request
operator|.
name|getDepartmentId
argument_list|()
operator|==
literal|null
condition|?
literal|"-1"
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getDepartmentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|InstructorSchedulingProxy
name|solver
init|=
name|instructorSchedulingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
return|return
operator|new
name|GwtRpcResponseList
argument_list|<
name|InstructorInfo
argument_list|>
argument_list|(
name|solver
operator|.
name|getInstructors
argument_list|(
name|request
operator|.
name|getDepartmentId
argument_list|()
argument_list|)
argument_list|)
return|;
else|else
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|commonItypes
init|=
name|getCommonItypes
argument_list|()
decl_stmt|;
name|String
name|nameFormat
init|=
name|UserProperty
operator|.
name|NameFormat
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
name|GwtRpcResponseList
argument_list|<
name|InstructorInfo
argument_list|>
name|ret
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|InstructorInfo
argument_list|>
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getDepartmentId
argument_list|()
operator|==
literal|null
condition|)
block|{
name|List
argument_list|<
name|Long
argument_list|>
name|departmentIds
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
for|for
control|(
name|DepartmentalInstructor
name|di
range|:
name|d
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
block|{
name|departmentIds
operator|.
name|add
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|departmentIds
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|ret
return|;
name|instructors
operator|=
operator|(
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct i from DepartmentalInstructor i where "
operator|+
literal|"i.department.uniqueId in :departmentIds and i.teachingPreference.prefProlog != :prohibited and i.maxLoad> 0.0"
argument_list|)
operator|.
name|setParameterList
argument_list|(
literal|"departmentIds"
argument_list|,
name|departmentIds
argument_list|)
operator|.
name|setString
argument_list|(
literal|"prohibited"
argument_list|,
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|instructors
operator|=
operator|(
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct i from DepartmentalInstructor i where "
operator|+
literal|"i.department.uniqueId = :departmentId and i.teachingPreference.prefProlog != :prohibited and i.maxLoad> 0.0"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|request
operator|.
name|getDepartmentId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"prohibited"
argument_list|,
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|DepartmentalInstructor
name|instructor
range|:
name|instructors
control|)
block|{
name|ret
operator|.
name|add
argument_list|(
name|getInstructorInfo
argument_list|(
name|instructor
argument_list|,
name|nameFormat
argument_list|,
name|commonItypes
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|ret
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
block|}
end_class

end_unit

