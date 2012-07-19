begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Assignment
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
name|ClassAssignmentProxy
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
name|AssignmentService
import|;
end_import

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
name|Department
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
name|Department
name|source
parameter_list|)
block|{
return|return
name|source
operator|!=
literal|null
operator|&&
name|source
operator|.
name|getSolverGroup
argument_list|()
operator|!=
literal|null
operator|&&
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Timetable
argument_list|)
operator|&&
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
operator|||
operator|!
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"SolverGroup"
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Department
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Department
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
implements|implements
name|Permission
argument_list|<
name|Department
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
name|Department
name|source
parameter_list|)
block|{
return|return
name|source
operator|!=
literal|null
operator|&&
name|source
operator|.
name|getSolverGroup
argument_list|()
operator|!=
literal|null
operator|&&
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Audit
argument_list|)
operator|&&
operator|!
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|Timetable
argument_list|)
operator|&&
operator|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
operator|||
operator|!
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"SolverGroup"
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Department
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Department
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
name|CourseTimetabling
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
name|ClassAssignmentsExportCSV
argument_list|)
specifier|public
specifier|static
class|class
name|ClassAssignmentsExportCSV
extends|extends
name|ClassAssignments
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ClassAssignmentsExportPDF
argument_list|)
specifier|public
specifier|static
class|class
name|ClassAssignmentsExportPDF
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
name|AssignmentService
argument_list|<
name|ClassAssignmentProxy
argument_list|>
name|classAssignmentService
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
comment|// Showing an in-memory or uncommitted solution
try|try
block|{
name|Assignment
name|assignment
init|=
operator|(
name|classAssignmentService
operator|.
name|getAssignment
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|classAssignmentService
operator|.
name|getAssignment
argument_list|()
operator|.
name|getAssignment
argument_list|(
name|source
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|assignment
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
name|assignment
operator|.
name|getSolution
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|assignment
operator|.
name|getSolution
argument_list|()
operator|.
name|isCommited
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
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
block|}
end_class

end_unit

