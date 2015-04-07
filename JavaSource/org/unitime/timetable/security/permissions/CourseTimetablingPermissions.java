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
name|InstructionalOffering
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
name|Solution
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
name|SolverProxy
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
name|SolverServerService
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
specifier|public
class|class
name|CourseTimetablingPermissions
block|{
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|CourseTimetabling
argument_list|)
specifier|public
specifier|static
class|class
name|CourseTimetabling
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
name|Timetable
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
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
name|CourseTimetablingAudit
argument_list|)
specifier|public
specifier|static
class|class
name|CourseTimetablingAudit
extends|extends
name|CourseTimetabling
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
if|if
condition|(
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
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
name|Audit
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Timetable
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|CourseTimetablingOrAudit
extends|extends
name|CourseTimetabling
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
name|Audit
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Timetable
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Timetables
argument_list|)
specifier|public
specifier|static
class|class
name|Timetables
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
if|if
condition|(
name|source
operator|.
name|getCommittedSolution
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
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
name|Timetable
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
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
name|Solver
argument_list|)
specifier|public
specifier|static
class|class
name|Solver
extends|extends
name|CourseTimetablingOrAudit
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AssignedClasses
argument_list|)
specifier|public
specifier|static
class|class
name|AssignedClasses
extends|extends
name|CourseTimetabling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|AssignmentHistory
argument_list|)
specifier|public
specifier|static
class|class
name|AssignmentHistory
extends|extends
name|CourseTimetabling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Suggestions
argument_list|)
specifier|public
specifier|static
class|class
name|Suggestions
extends|extends
name|CourseTimetabling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SolutionChanges
argument_list|)
specifier|public
specifier|static
class|class
name|SolutionChanges
extends|extends
name|CourseTimetabling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ConflictStatistics
argument_list|)
specifier|public
specifier|static
class|class
name|ConflictStatistics
extends|extends
name|CourseTimetablingOrAudit
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SolverLog
argument_list|)
specifier|public
specifier|static
class|class
name|SolverLog
extends|extends
name|CourseTimetablingOrAudit
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|NotAssignedClasses
argument_list|)
specifier|public
specifier|static
class|class
name|NotAssignedClasses
extends|extends
name|CourseTimetabling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SolutionReports
argument_list|)
specifier|public
specifier|static
class|class
name|SolutionReports
extends|extends
name|CourseTimetabling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|TimetableGrid
argument_list|)
specifier|public
specifier|static
class|class
name|TimetableGrid
extends|extends
name|Timetables
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ClassAssignments
argument_list|)
specifier|public
specifier|static
class|class
name|ClassAssignments
implements|implements
name|Permission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionSession
name|permissionSession
decl_stmt|;
annotation|@
name|Autowired
name|PermissionDepartment
name|permissionDepartment
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
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
name|Session
name|source
parameter_list|)
block|{
if|if
condition|(
operator|!
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Has a solver running -> can see assignments
if|if
condition|(
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
comment|// Check for a department with a committed solution or for my department with a solution
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
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getCommittedSolution
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
if|if
condition|(
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
name|Timetable
argument_list|)
operator|&&
operator|!
name|department
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getSolutions
argument_list|()
operator|.
name|isEmpty
argument_list|()
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
name|Session
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Session
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
name|ClassAssignmentsExportCsv
argument_list|)
specifier|public
specifier|static
class|class
name|ClassAssignmentsExportCsv
extends|extends
name|ClassAssignments
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ClassAssignmentsExportPdf
argument_list|)
specifier|public
specifier|static
class|class
name|ClassAssignmentsExportPdf
extends|extends
name|ClassAssignments
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ClassAssignment
argument_list|)
specifier|public
specifier|static
class|class
name|ClassAssignment
implements|implements
name|Permission
argument_list|<
name|Class_
argument_list|>
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
decl_stmt|;
annotation|@
name|Autowired
name|PermissionDepartment
name|permissionDepartment
decl_stmt|;
annotation|@
name|Autowired
name|Permission
argument_list|<
name|InstructionalOffering
argument_list|>
name|permissionOfferingLockNeededLimitedEdit
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
name|Class_
name|source
parameter_list|)
block|{
comment|// cancelled classes cannot be assigned
if|if
condition|(
name|source
operator|.
name|isCancelled
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// Must have a committed solution (not the class per se, but the managing department)
if|if
condition|(
name|source
operator|.
name|getManagingDept
argument_list|()
operator|==
literal|null
operator|||
name|source
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getSolverGroup
argument_list|()
operator|==
literal|null
operator|||
name|source
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getSolverGroup
argument_list|()
operator|.
name|getCommittedSolution
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
comment|// No date or time pattern
if|if
condition|(
name|source
operator|.
name|effectiveDatePattern
argument_list|()
operator|==
literal|null
operator|||
name|source
operator|.
name|effectiveTimePatterns
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// Showing an in-memory solution
if|if
condition|(
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
comment|// Showing a selected solution
name|String
name|solutionIdsStr
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|SelectedSolution
argument_list|)
decl_stmt|;
if|if
condition|(
name|solutionIdsStr
operator|!=
literal|null
operator|&&
operator|!
name|solutionIdsStr
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// Need an offering lock
if|if
condition|(
name|permissionOfferingLockNeededLimitedEdit
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// Check departmental permissions
return|return
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getManagingDept
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Timetable
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Class_
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Class_
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
name|TimetablesSolutionCommit
argument_list|)
specifier|public
specifier|static
class|class
name|TimetablesSolutionCommit
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
name|Autowired
name|SolverServerService
name|solverServerService
decl_stmt|;
specifier|private
name|boolean
name|hasInstance
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|solverServerService
operator|.
name|getOnlineStudentSchedulingContainer
argument_list|()
operator|.
name|hasSolver
argument_list|(
name|sessionId
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
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
if|if
condition|(
name|hasInstance
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
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
name|Commit
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
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
name|TimetablesSolutionLoad
argument_list|)
specifier|public
specifier|static
class|class
name|TimetablesSolutionLoad
implements|implements
name|Permission
argument_list|<
name|Solution
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
name|Solution
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
name|getOwner
argument_list|()
operator|.
name|getDepartments
argument_list|()
control|)
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
name|Timetable
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Solution
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Solution
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
name|TimetablesSolutionDelete
argument_list|)
specifier|public
specifier|static
class|class
name|TimetablesSolutionDelete
extends|extends
name|TimetablesSolutionLoad
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|TimetablesSolutionLoadEmpty
argument_list|)
specifier|public
specifier|static
class|class
name|TimetablesSolutionLoadEmpty
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
name|Timetable
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Audit
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
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
name|TimetablesSolutionChangeNote
argument_list|)
specifier|public
specifier|static
class|class
name|TimetablesSolutionChangeNote
extends|extends
name|TimetablesSolutionLoad
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|TimetablesSolutionExportCsv
argument_list|)
specifier|public
specifier|static
class|class
name|TimetablesSolutionExportCsv
extends|extends
name|TimetablesSolutionLoad
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SolverSolutionSave
argument_list|)
specifier|public
specifier|static
class|class
name|SolverSolutionSave
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
name|Timetable
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
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
name|SolverSolutionExportCsv
argument_list|)
specifier|public
specifier|static
class|class
name|SolverSolutionExportCsv
extends|extends
name|SolverSolutionSave
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SolverSolutionExportXml
argument_list|)
specifier|public
specifier|static
class|class
name|SolverSolutionExportXml
extends|extends
name|SolverSolutionSave
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ManageSolvers
argument_list|)
specifier|public
specifier|static
class|class
name|ManageSolvers
implements|implements
name|Permission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionSession
name|permissionSession
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
name|Session
name|source
parameter_list|)
block|{
return|return
name|permissionSession
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Session
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Session
operator|.
name|class
return|;
block|}
block|}
block|}
end_class

end_unit

