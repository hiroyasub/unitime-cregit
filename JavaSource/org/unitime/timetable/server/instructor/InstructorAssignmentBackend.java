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
name|HashSet
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcException
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
name|GwtRpcResponseNull
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
name|GwtMessages
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
name|AssignmentInfo
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
name|InstructorAssignmentRequest
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
name|ExternalInstrOfferingConfigAssignInstructorsAction
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
name|InstrOfferingConfig
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
name|InstructorAssignmentRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|InstructorAssignmentBackend
extends|extends
name|InstructorSchedulingBackendHelper
implements|implements
name|GwtRpcImplementation
argument_list|<
name|InstructorAssignmentRequest
argument_list|,
name|GwtRpcResponseNull
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
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
name|GwtRpcResponseNull
name|execute
parameter_list|(
name|InstructorAssignmentRequest
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
block|{
name|solver
operator|.
name|assign
argument_list|(
name|request
operator|.
name|getAssignments
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|GwtRpcResponseNull
argument_list|()
return|;
block|}
name|boolean
name|commit
init|=
literal|true
decl_stmt|;
name|Set
argument_list|<
name|InstrOfferingConfig
argument_list|>
name|updateConfigs
init|=
operator|new
name|HashSet
argument_list|<
name|InstrOfferingConfig
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
name|DepartmentalInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
name|Context
name|cx
init|=
operator|new
name|Context
argument_list|(
name|context
argument_list|)
decl_stmt|;
name|Suggestion
name|s
init|=
operator|new
name|Suggestion
argument_list|()
decl_stmt|;
for|for
control|(
name|AssignmentInfo
name|ai
range|:
name|request
operator|.
name|getAssignments
argument_list|()
control|)
block|{
name|Class_
name|clazz
init|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|ai
operator|.
name|getRequest
argument_list|()
operator|.
name|getRequestId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
continue|continue;
name|DepartmentalInstructor
name|instructor
init|=
operator|(
name|ai
operator|.
name|getInstructor
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|DepartmentalInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|ai
operator|.
name|getInstructor
argument_list|()
operator|.
name|getInstructorId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|instructor
operator|!=
literal|null
condition|)
name|s
operator|.
name|set
argument_list|(
name|clazz
argument_list|,
name|ai
operator|.
name|getIndex
argument_list|()
argument_list|,
name|instructor
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|isIgnoreConflicts
argument_list|()
condition|)
block|{
for|for
control|(
name|InstructorAssignment
name|a
range|:
name|s
operator|.
name|getAssignments
argument_list|()
control|)
block|{
name|assign
argument_list|(
name|hibSession
argument_list|,
name|a
argument_list|,
name|cx
argument_list|,
name|commit
argument_list|)
expr_stmt|;
if|if
condition|(
name|commit
condition|)
name|updateConfigs
operator|.
name|add
argument_list|(
name|a
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|Set
argument_list|<
name|InstructorAssignment
argument_list|>
name|conflicts
init|=
operator|new
name|HashSet
argument_list|<
name|InstructorAssignment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|InstructorAssignment
name|a
range|:
name|s
operator|.
name|getAssignments
argument_list|()
control|)
block|{
name|s
operator|.
name|computeConflicts
argument_list|(
name|a
argument_list|,
name|conflicts
argument_list|,
name|cx
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|InstructorAssignment
name|a
range|:
name|s
operator|.
name|getAssignments
argument_list|()
control|)
if|if
condition|(
operator|!
name|conflicts
operator|.
name|remove
argument_list|(
name|a
argument_list|)
condition|)
block|{
name|assign
argument_list|(
name|hibSession
argument_list|,
name|a
argument_list|,
name|cx
argument_list|,
name|commit
argument_list|)
expr_stmt|;
if|if
condition|(
name|commit
condition|)
name|updateConfigs
operator|.
name|add
argument_list|(
name|a
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|InstructorAssignment
name|c
range|:
name|conflicts
control|)
block|{
name|unassign
argument_list|(
name|hibSession
argument_list|,
name|c
argument_list|,
name|cx
argument_list|,
name|commit
argument_list|)
expr_stmt|;
if|if
condition|(
name|commit
condition|)
name|updateConfigs
operator|.
name|add
argument_list|(
name|c
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
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
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
block|{
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
if|if
condition|(
name|updateConfigs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|String
name|className
init|=
name|ApplicationProperty
operator|.
name|ExternalActionInstrOfferingConfigAssignInstructors
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
name|ExternalInstrOfferingConfigAssignInstructorsAction
name|assignAction
init|=
operator|(
name|ExternalInstrOfferingConfigAssignInstructorsAction
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
name|InstrOfferingConfig
name|ioc
range|:
name|updateConfigs
control|)
name|assignAction
operator|.
name|performExternalInstrOfferingConfigAssignInstructorsAction
argument_list|(
name|ioc
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
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
literal|"Failed to call external action: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|GwtRpcResponseNull
argument_list|()
return|;
block|}
specifier|public
name|void
name|assign
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|InstructorAssignment
name|assignment
parameter_list|,
name|Context
name|context
parameter_list|,
name|boolean
name|commit
parameter_list|)
block|{
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|current
init|=
name|getInstructors
argument_list|(
name|assignment
operator|.
name|getClazz
argument_list|()
argument_list|)
decl_stmt|;
name|DepartmentalInstructor
name|previous
init|=
operator|(
name|assignment
operator|.
name|getIndex
argument_list|()
operator|<
name|current
operator|.
name|size
argument_list|()
condition|?
name|current
operator|.
name|get
argument_list|(
name|assignment
operator|.
name|getIndex
argument_list|()
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
name|replaceInstructor
argument_list|(
name|hibSession
argument_list|,
name|assignment
operator|.
name|getClazz
argument_list|()
argument_list|,
name|previous
argument_list|,
name|assignment
operator|.
name|getAssigment
argument_list|()
argument_list|,
name|assignment
operator|.
name|getIndex
argument_list|()
argument_list|,
name|context
argument_list|,
name|commit
argument_list|)
expr_stmt|;
for|for
control|(
name|Class_
name|parent
init|=
name|assignment
operator|.
name|getClazz
argument_list|()
operator|.
name|getParentClass
argument_list|()
init|;
name|parent
operator|!=
literal|null
condition|;
name|parent
operator|=
name|parent
operator|.
name|getParentClass
argument_list|()
control|)
block|{
if|if
condition|(
name|isToBeIncluded
argument_list|(
name|parent
argument_list|,
literal|null
argument_list|)
condition|)
name|replaceInstructor
argument_list|(
name|hibSession
argument_list|,
name|parent
argument_list|,
name|previous
argument_list|,
name|assignment
operator|.
name|getAssigment
argument_list|()
argument_list|,
name|assignment
operator|.
name|getIndex
argument_list|()
argument_list|,
name|context
argument_list|,
name|commit
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|previous
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|previous
argument_list|)
expr_stmt|;
if|if
condition|(
name|assignment
operator|.
name|getAssigment
argument_list|()
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|assignment
operator|.
name|getAssigment
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|unassign
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|InstructorAssignment
name|assignment
parameter_list|,
name|Context
name|context
parameter_list|,
name|boolean
name|commit
parameter_list|)
block|{
name|replaceInstructor
argument_list|(
name|hibSession
argument_list|,
name|assignment
operator|.
name|getClazz
argument_list|()
argument_list|,
name|assignment
operator|.
name|getAssigment
argument_list|()
argument_list|,
literal|null
argument_list|,
name|assignment
operator|.
name|getIndex
argument_list|()
argument_list|,
name|context
argument_list|,
name|commit
argument_list|)
expr_stmt|;
for|for
control|(
name|Class_
name|parent
init|=
name|assignment
operator|.
name|getClazz
argument_list|()
operator|.
name|getParentClass
argument_list|()
init|;
name|parent
operator|!=
literal|null
condition|;
name|parent
operator|=
name|parent
operator|.
name|getParentClass
argument_list|()
control|)
block|{
if|if
condition|(
name|isToBeIncluded
argument_list|(
name|parent
argument_list|,
literal|null
argument_list|)
condition|)
name|replaceInstructor
argument_list|(
name|hibSession
argument_list|,
name|parent
argument_list|,
name|assignment
operator|.
name|getAssigment
argument_list|()
argument_list|,
literal|null
argument_list|,
name|assignment
operator|.
name|getIndex
argument_list|()
argument_list|,
name|context
argument_list|,
name|commit
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|.
name|getAssigment
argument_list|()
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|assignment
operator|.
name|getAssigment
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|replaceInstructor
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Class_
name|clazz
parameter_list|,
name|DepartmentalInstructor
name|oldInstructor
parameter_list|,
name|DepartmentalInstructor
name|newInstructor
parameter_list|,
name|int
name|index
parameter_list|,
name|Context
name|context
parameter_list|,
name|boolean
name|commit
parameter_list|)
block|{
if|if
condition|(
name|oldInstructor
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ClassInstructor
name|ci
range|:
name|clazz
operator|.
name|getClassInstructors
argument_list|()
control|)
block|{
if|if
condition|(
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|equals
argument_list|(
name|oldInstructor
argument_list|)
condition|)
block|{
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|getClasses
argument_list|()
operator|.
name|remove
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|ci
operator|.
name|getClassInstructing
argument_list|()
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
name|delete
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|newInstructor
operator|!=
literal|null
condition|)
block|{
name|ClassInstructor
name|ci
init|=
operator|new
name|ClassInstructor
argument_list|()
decl_stmt|;
name|ci
operator|.
name|setClassInstructing
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setInstructor
argument_list|(
name|newInstructor
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setTentative
argument_list|(
operator|!
name|commit
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setLead
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setPercentShare
argument_list|(
literal|100
operator|/
name|clazz
operator|.
name|effectiveNbrInstructors
argument_list|()
argument_list|)
expr_stmt|;
name|ci
operator|.
name|setAssignmentIndex
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|clazz
operator|.
name|getClassInstructors
argument_list|()
operator|.
name|add
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|newInstructor
operator|.
name|getClasses
argument_list|()
operator|.
name|add
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|oldInstructor
operator|!=
literal|null
operator|||
name|newInstructor
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
name|context
operator|.
name|getSessionContext
argument_list|()
argument_list|,
name|clazz
argument_list|,
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|hibSession
argument_list|)
operator|+
literal|": "
operator|+
operator|(
name|oldInstructor
operator|==
literal|null
condition|?
literal|"<i>Not Assigned</i>"
else|:
name|oldInstructor
operator|.
name|getName
argument_list|(
name|context
operator|.
name|getNameFormat
argument_list|()
argument_list|)
operator|)
operator|+
literal|"&rarr; "
operator|+
operator|(
name|newInstructor
operator|==
literal|null
condition|?
literal|"<i>Not Assigned</i>"
else|:
name|newInstructor
operator|.
name|getName
argument_list|(
name|context
operator|.
name|getNameFormat
argument_list|()
argument_list|)
operator|)
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|INSTRUCTOR_ASSIGNMENT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|ASSIGN
argument_list|,
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|clazz
operator|.
name|getControllingDept
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
