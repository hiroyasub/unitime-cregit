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
name|model
operator|.
name|CourseOffering
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
name|Roles
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
name|model
operator|.
name|DepartmentStatusType
operator|.
name|Status
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
name|custom
operator|.
name|CustomStudentEnrollmentHolder
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
name|service
operator|.
name|SolverServerService
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

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
name|StudentSectioningSolutionExportXml
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSectioningSolutionExportXml
extends|extends
name|StudentSectioningSolver
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
name|hasInstance
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
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
name|CourseOffering
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
name|CourseOffering
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
name|Roles
operator|.
name|ROLE_INSTRUCTOR
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
name|getInstructionalOffering
argument_list|()
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
name|getInstructionalOffering
argument_list|()
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
name|CourseOffering
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|CourseOffering
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
name|Roles
operator|.
name|ROLE_INSTRUCTOR
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
name|Roles
operator|.
name|ROLE_STUDENT
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
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingMassCancel
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingMassCancel
extends|extends
name|SimpleSessionPermission
block|{
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
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsOnline
argument_list|)
operator|&&
name|CustomStudentEnrollmentHolder
operator|.
name|isAllowWaitListing
argument_list|()
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingEmailStudent
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingEmailStudent
extends|extends
name|SimpleSessionPermission
block|{
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
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsOnline
argument_list|)
operator|&&
name|ApplicationProperty
operator|.
name|OnlineSchedulingEmailConfirmation
operator|.
name|isTrue
argument_list|()
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingChangeStudentStatus
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingChangeStudentStatus
extends|extends
name|SimpleSessionPermission
block|{
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
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsOnline
argument_list|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingRequestStudentUpdate
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingRequestStudentUpdate
extends|extends
name|SimpleSessionPermission
block|{
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
name|super
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|)
operator|&&
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsOnline
argument_list|)
operator|&&
name|ApplicationProperty
operator|.
name|CustomizationStudentEnrollments
operator|.
name|value
argument_list|()
operator|!=
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

