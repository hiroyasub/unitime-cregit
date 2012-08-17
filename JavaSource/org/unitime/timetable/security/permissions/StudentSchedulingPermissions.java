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
name|Student
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
name|onlinesectioning
operator|.
name|OnlineSectioningService
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

begin_class
specifier|public
class|class
name|StudentSchedulingPermissions
block|{
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentScheduling
argument_list|)
specifier|public
specifier|static
class|class
name|StudentScheduling
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSectioningSolver
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSectioningSolver
extends|extends
name|StudentScheduling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSectioningSolverLog
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSectioningSolverLog
extends|extends
name|StudentScheduling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSectioningSolverDashboard
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSectioningSolverDashboard
extends|extends
name|StudentScheduling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|EnrollmentAuditPDFReports
argument_list|)
specifier|public
specifier|static
class|class
name|EnrollmentAuditPDFReports
extends|extends
name|SimpleSessionPermission
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SchedulingAssistant
argument_list|)
specifier|public
specifier|static
class|class
name|SchedulingAssistant
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
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsAssistant
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsOnline
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|OnlineSectioningService
operator|.
name|getInstance
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
operator|!=
literal|null
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
name|SchedulingDashboard
argument_list|)
specifier|public
specifier|static
class|class
name|SchedulingDashboard
extends|extends
name|SchedulingAssistant
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|CourseRequests
argument_list|)
specifier|public
specifier|static
class|class
name|CourseRequests
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
name|DepartmentStatusType
name|status
init|=
name|source
operator|.
name|getStatusType
argument_list|()
decl_stmt|;
return|return
name|status
operator|!=
literal|null
operator|&&
name|status
operator|.
name|can
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsPreRegister
argument_list|)
operator|&&
operator|!
name|status
operator|.
name|can
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsAssistant
argument_list|)
operator|&&
operator|!
name|status
operator|.
name|can
argument_list|(
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|StudentsOnline
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
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ConsentApproval
argument_list|)
specifier|public
specifier|static
class|class
name|ConsentApproval
implements|implements
name|Permission
argument_list|<
name|InstructionalOffering
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
name|InstructionalOffering
name|source
parameter_list|)
block|{
if|if
condition|(
name|source
operator|.
name|getConsentType
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
literal|"Instructor"
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
literal|"IN"
operator|.
name|equals
argument_list|(
name|source
operator|.
name|getConsentType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
for|for
control|(
name|DepartmentalInstructor
name|instructor
range|:
name|source
operator|.
name|getCoordinators
argument_list|()
control|)
block|{
if|if
condition|(
name|user
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|instructor
operator|.
name|getExternalUniqueId
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
else|else
block|{
return|return
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|InstructionalOffering
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|InstructionalOffering
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
name|OfferingEnrollments
argument_list|)
specifier|public
specifier|static
class|class
name|OfferingEnrollments
implements|implements
name|Permission
argument_list|<
name|InstructionalOffering
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
name|InstructionalOffering
name|source
parameter_list|)
block|{
if|if
condition|(
literal|"Instructor"
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
for|for
control|(
name|DepartmentalInstructor
name|instructor
range|:
name|source
operator|.
name|getCoordinators
argument_list|()
control|)
block|{
if|if
condition|(
name|user
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|instructor
operator|.
name|getExternalUniqueId
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
else|else
block|{
return|return
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|InstructionalOffering
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|InstructionalOffering
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
name|StudentEnrollments
argument_list|)
specifier|public
specifier|static
class|class
name|StudentEnrollments
implements|implements
name|Permission
argument_list|<
name|Student
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
name|Student
name|source
parameter_list|)
block|{
if|if
condition|(
literal|"Student"
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
return|return
name|source
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
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
name|Student
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Student
operator|.
name|class
return|;
block|}
block|}
block|}
end_class

end_unit

