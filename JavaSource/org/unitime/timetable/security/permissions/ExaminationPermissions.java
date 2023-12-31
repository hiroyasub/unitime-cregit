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
name|DistributionObject
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
name|DistributionPref
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
name|Exam
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
name|ExamOwner
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
name|ExamStatus
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
name|ExamType
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
name|SubjectArea
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
name|ExaminationPermissions
block|{
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|Examinations
argument_list|)
specifier|public
specifier|static
class|class
name|Examinations
implements|implements
name|Permission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionExamination
name|permissionExaminationStatus
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
name|SubjectArea
operator|.
name|getUserSubjectAreas
argument_list|(
name|user
argument_list|,
literal|false
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|ExamType
operator|.
name|findAllUsed
argument_list|(
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
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
name|DepartmentIndependent
argument_list|)
condition|)
return|return
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
literal|null
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
argument_list|)
return|;
else|else
return|return
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
literal|null
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
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
name|ExaminationView
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationView
implements|implements
name|Permission
argument_list|<
name|Exam
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionExamination
name|permissionExaminationStatus
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
name|Exam
name|source
parameter_list|)
block|{
name|SimpleExaminationPermission
name|p
init|=
operator|new
name|SimpleExaminationPermission
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|checkManager
parameter_list|(
name|UserAuthority
name|authority
parameter_list|,
name|ExamStatus
name|examStatus
parameter_list|,
name|DepartmentStatusType
operator|.
name|Status
modifier|...
name|status
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
decl_stmt|;
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
name|DepartmentIndependent
argument_list|)
condition|)
return|return
name|p
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getSession
argument_list|()
argument_list|,
name|source
operator|.
name|getExamType
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
argument_list|)
return|;
else|else
block|{
for|for
control|(
name|ExamOwner
name|owner
range|:
name|source
operator|.
name|getOwners
argument_list|()
control|)
block|{
if|if
condition|(
name|p
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|owner
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|source
operator|.
name|getExamType
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
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
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Exam
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Exam
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
name|ExaminationDetail
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationDetail
implements|implements
name|Permission
argument_list|<
name|Exam
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionExamination
name|permissionExaminationStatus
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
name|Exam
name|source
parameter_list|)
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
name|DepartmentIndependent
argument_list|)
condition|)
return|return
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getSession
argument_list|()
argument_list|,
name|source
operator|.
name|getExamType
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
argument_list|)
return|;
else|else
block|{
for|for
control|(
name|ExamOwner
name|owner
range|:
name|source
operator|.
name|getOwners
argument_list|()
control|)
block|{
if|if
condition|(
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|owner
operator|.
name|getCourse
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|source
operator|.
name|getExamType
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamView
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
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Exam
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Exam
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
name|ExaminationAdd
argument_list|)
specifier|public
specifier|static
class|class
name|AddExamination
implements|implements
name|Permission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionExamination
name|permissionExaminationStatus
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
name|ExamType
operator|.
name|findAllUsed
argument_list|(
name|source
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
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
name|DepartmentIndependent
argument_list|)
condition|)
return|return
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
literal|null
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
argument_list|)
return|;
else|else
return|return
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
literal|null
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamEdit
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
name|ExaminationEdit
argument_list|)
specifier|public
specifier|static
class|class
name|EditExamination
implements|implements
name|Permission
argument_list|<
name|Exam
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionExamination
name|permissionExaminationStatus
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
name|Exam
name|source
parameter_list|)
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
name|DepartmentIndependent
argument_list|)
condition|)
return|return
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getSession
argument_list|()
argument_list|,
name|source
operator|.
name|getExamType
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
argument_list|)
return|;
else|else
block|{
for|for
control|(
name|ExamOwner
name|owner
range|:
name|source
operator|.
name|getOwners
argument_list|()
control|)
block|{
if|if
condition|(
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|owner
operator|.
name|getCourse
argument_list|()
operator|.
name|getDepartment
argument_list|()
argument_list|,
name|source
operator|.
name|getExamType
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamEdit
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
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Exam
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Exam
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
name|ExaminationDelete
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationDelete
extends|extends
name|EditExamination
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ExaminationClone
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationClone
extends|extends
name|EditExamination
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|DistributionPreferenceExam
argument_list|)
specifier|public
specifier|static
class|class
name|DistributionPreferenceExam
extends|extends
name|EditExamination
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ExaminationEditClearPreferences
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationEditClearPreferences
extends|extends
name|EditExamination
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ExaminationAssignment
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationAssignment
implements|implements
name|Permission
argument_list|<
name|Exam
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionExamination
name|permissionExaminationStatus
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
name|Exam
name|source
parameter_list|)
block|{
return|return
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
operator|&&
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
operator|.
name|getSession
argument_list|()
argument_list|,
name|source
operator|.
name|getExamType
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|Exam
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|Exam
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
name|ExaminationSchedule
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationSchedule
implements|implements
name|Permission
argument_list|<
name|Session
argument_list|>
block|{
annotation|@
name|Autowired
name|PermissionExamination
name|permissionExaminationStatus
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
name|permissionExaminationStatus
operator|.
name|check
argument_list|(
name|user
argument_list|,
name|source
argument_list|,
literal|null
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsFinal
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ReportExamsMidterm
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
name|ExaminationDistributionPreferences
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationDistributionPreferences
extends|extends
name|Examinations
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ExaminationDistributionPreferenceAdd
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationDistributionPreferenceAdd
extends|extends
name|AddExamination
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ExaminationDistributionPreferenceEdit
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationDistributionPreferenceEdit
implements|implements
name|Permission
argument_list|<
name|DistributionPref
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Exam
argument_list|>
name|permissionExaminationEdit
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
name|DistributionPref
name|source
parameter_list|)
block|{
for|for
control|(
name|DistributionObject
name|distrObj
range|:
name|source
operator|.
name|getDistributionObjects
argument_list|()
control|)
block|{
if|if
condition|(
name|distrObj
operator|.
name|getPrefGroup
argument_list|()
operator|instanceof
name|Exam
condition|)
block|{
if|if
condition|(
operator|!
name|permissionExaminationEdit
operator|.
name|check
argument_list|(
name|user
argument_list|,
operator|(
name|Exam
operator|)
name|distrObj
operator|.
name|getPrefGroup
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
else|else
block|{
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
name|DistributionPref
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|DistributionPref
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
name|ExaminationDistributionPreferenceDelete
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationDistributionPreferenceDelete
extends|extends
name|ExaminationDistributionPreferenceEdit
block|{}
annotation|@
name|PermissionForRight
argument_list|(
name|Right
operator|.
name|ExaminationDistributionPreferenceDetail
argument_list|)
specifier|public
specifier|static
class|class
name|ExaminationDistributionPreferenceDetail
implements|implements
name|Permission
argument_list|<
name|DistributionPref
argument_list|>
block|{
annotation|@
name|Autowired
name|Permission
argument_list|<
name|Exam
argument_list|>
name|permissionExaminationDetail
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
name|DistributionPref
name|source
parameter_list|)
block|{
for|for
control|(
name|DistributionObject
name|distrObj
range|:
name|source
operator|.
name|getDistributionObjects
argument_list|()
control|)
block|{
if|if
condition|(
name|distrObj
operator|.
name|getPrefGroup
argument_list|()
operator|instanceof
name|Exam
condition|)
block|{
if|if
condition|(
name|permissionExaminationDetail
operator|.
name|check
argument_list|(
name|user
argument_list|,
operator|(
name|Exam
operator|)
name|distrObj
operator|.
name|getPrefGroup
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
name|DistributionPref
argument_list|>
name|type
parameter_list|()
block|{
return|return
name|DistributionPref
operator|.
name|class
return|;
block|}
block|}
block|}
end_class

end_unit

