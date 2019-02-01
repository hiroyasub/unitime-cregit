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
name|java
operator|.
name|util
operator|.
name|List
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
name|Advisor
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
name|OfferingCoordinator
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
name|StudentSectioningStatus
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
name|model
operator|.
name|dao
operator|.
name|StudentDAO
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
name|UserAuthority
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
name|UserQualifier
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
name|qualifiers
operator|.
name|SimpleQualifier
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
name|studentsct
operator|.
name|StudentSolverProxy
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
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
name|studentSectioningSolverService
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
return|return
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
name|StudentSectioningSolver
argument_list|)
operator|||
name|studentSectioningSolverService
operator|.
name|getSolver
argument_list|()
operator|!=
literal|null
operator|)
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
name|StudentSectioningSolverReports
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSectioningSolverReports
extends|extends
name|StudentSectioningSolverDashboard
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSectioningSolverSave
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSectioningSolverSave
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
name|StudentSectioningSolverPublish
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSectioningSolverPublish
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
specifier|protected
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
if|if
condition|(
operator|!
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
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserQualifier
argument_list|>
name|q
init|=
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Student"
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
operator|||
name|q
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
name|Student
name|student
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|q
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
return|return
operator|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|enabled
argument_list|)
operator|)
return|;
block|}
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
name|UserAuthority
name|authority
range|:
name|user
operator|.
name|getAuthorities
argument_list|(
name|Roles
operator|.
name|ROLE_STUDENT
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
control|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserQualifier
argument_list|>
name|q
init|=
name|authority
operator|.
name|getQualifiers
argument_list|(
literal|"Student"
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
operator|||
name|q
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|Student
name|student
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|q
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
operator|&&
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|enabled
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
block|}
return|return
literal|true
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
operator|||
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
name|StudentsPreRegister
argument_list|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|SchedulingReports
argument_list|)
specifier|public
specifier|static
class|class
name|SchedulingReports
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
name|StudentsPreRegister
argument_list|)
condition|)
return|return
literal|false
return|;
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
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserQualifier
argument_list|>
name|q
init|=
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Student"
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
operator|||
name|q
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
name|Student
name|student
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|q
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
return|return
operator|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|regenabled
argument_list|)
operator|)
return|;
block|}
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
name|UserAuthority
name|authority
range|:
name|user
operator|.
name|getAuthorities
argument_list|(
name|Roles
operator|.
name|ROLE_STUDENT
argument_list|,
operator|new
name|SimpleQualifier
argument_list|(
literal|"Session"
argument_list|,
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
control|)
block|{
name|List
argument_list|<
name|?
extends|extends
name|UserQualifier
argument_list|>
name|q
init|=
name|authority
operator|.
name|getQualifiers
argument_list|(
literal|"Student"
argument_list|)
decl_stmt|;
if|if
condition|(
name|q
operator|==
literal|null
operator|||
name|q
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|Student
name|student
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|q
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|regenabled
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
return|return
literal|true
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
name|OfferingCoordinator
name|coordinator
range|:
name|source
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getOfferingCoordinators
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
name|coordinator
operator|.
name|getInstructor
argument_list|()
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
name|OfferingCoordinator
name|coordinator
range|:
name|source
operator|.
name|getOfferingCoordinators
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
name|coordinator
operator|.
name|getInstructor
argument_list|()
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
for|for
control|(
name|CourseOffering
name|course
range|:
name|source
operator|.
name|getCourseOfferings
argument_list|()
control|)
if|if
condition|(
name|permissionDepartment
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
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
operator|(
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
operator|||
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsAssistant
argument_list|)
operator|||
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsPreRegister
argument_list|)
operator|)
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingChangeStudentGroup
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingChangeStudentGroup
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
operator|(
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
operator|||
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsAssistant
argument_list|)
operator|||
name|source
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsPreRegister
argument_list|)
operator|)
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
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingCheckStudentOverrides
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingCheckStudentOverrides
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
name|StudentsPreRegister
argument_list|)
operator|&&
name|ApplicationProperty
operator|.
name|CustomizationCourseRequestsValidation
operator|.
name|value
argument_list|()
operator|!=
literal|null
return|;
block|}
block|}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingValidateStudentOverrides
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingValidateStudentOverrides
extends|extends
name|StudentSchedulingCheckStudentOverrides
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingAdvisor
extends|extends
name|StudentScheduling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingAdmin
extends|extends
name|StudentScheduling
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingCanEnroll
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingCanEnroll
implements|implements
name|Permission
argument_list|<
name|Student
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
name|Student
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
operator|.
name|getSession
argument_list|()
argument_list|)
operator|&&
operator|!
name|source
operator|.
name|getSession
argument_list|()
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
condition|)
return|return
literal|false
return|;
name|StudentSectioningStatus
name|status
init|=
name|source
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
comment|// Student check
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
block|{
if|if
condition|(
name|status
operator|!=
literal|null
operator|&&
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|enrollment
argument_list|)
condition|)
return|return
literal|false
return|;
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
block|}
comment|// Admin check
if|if
condition|(
operator|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|admin
argument_list|)
operator|)
operator|&&
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
comment|// Advisor check
if|if
condition|(
operator|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|advisor
argument_list|)
operator|)
operator|&&
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
condition|)
block|{
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisorCanModifyAllStudents
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisorCanModifyMyStudents
argument_list|)
condition|)
block|{
for|for
control|(
name|Advisor
name|advisor
range|:
name|source
operator|.
name|getAdvisors
argument_list|()
control|)
if|if
condition|(
name|advisor
operator|.
name|getRole
argument_list|()
operator|.
name|getReference
argument_list|()
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
operator|&&
name|advisor
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
condition|)
return|return
literal|true
return|;
block|}
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
name|StudentSchedulingCanRegister
argument_list|)
specifier|public
specifier|static
class|class
name|StudentSchedulingCanRegister
implements|implements
name|Permission
argument_list|<
name|Student
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
name|Student
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
operator|.
name|getSession
argument_list|()
argument_list|)
operator|&&
operator|!
name|source
operator|.
name|getSession
argument_list|()
operator|.
name|getStatusType
argument_list|()
operator|.
name|can
argument_list|(
name|Status
operator|.
name|StudentsPreRegister
argument_list|)
condition|)
return|return
literal|false
return|;
name|StudentSectioningStatus
name|status
init|=
name|source
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
comment|// Student check
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
block|{
if|if
condition|(
name|status
operator|!=
literal|null
operator|&&
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|registration
argument_list|)
condition|)
return|return
literal|false
return|;
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
block|}
comment|// Admin check
if|if
condition|(
operator|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|regadmin
argument_list|)
operator|)
operator|&&
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdmin
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
comment|// Advisor check
if|if
condition|(
operator|(
name|status
operator|==
literal|null
operator|||
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|regadvisor
argument_list|)
operator|)
operator|&&
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisor
argument_list|)
condition|)
block|{
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisorCanModifyAllStudents
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|StudentSchedulingAdvisorCanModifyMyStudents
argument_list|)
condition|)
block|{
for|for
control|(
name|Advisor
name|advisor
range|:
name|source
operator|.
name|getAdvisors
argument_list|()
control|)
if|if
condition|(
name|advisor
operator|.
name|getRole
argument_list|()
operator|.
name|getReference
argument_list|()
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
operator|&&
name|advisor
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
condition|)
return|return
literal|true
return|;
block|}
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

