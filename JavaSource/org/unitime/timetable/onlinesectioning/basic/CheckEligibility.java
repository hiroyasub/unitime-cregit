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
name|onlinesectioning
operator|.
name|basic
package|;
end_package

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
name|resources
operator|.
name|StudentSectioningMessages
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
name|OnlineSectioningInterface
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
name|SectioningException
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
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
operator|.
name|EligibilityFlag
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
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
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
name|OnlineSectioningAction
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
name|OnlineSectioningHelper
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
name|OnlineSectioningLog
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
name|OnlineSectioningServer
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
name|OnlineSectioningServer
operator|.
name|Lock
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
name|CustomSpecialRegistrationHolder
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XStudent
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
name|server
operator|.
name|CheckMaster
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
name|server
operator|.
name|CheckMaster
operator|.
name|Master
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
name|updates
operator|.
name|ReloadStudent
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
class|class
name|CheckEligibility
implements|implements
name|OnlineSectioningAction
argument_list|<
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
name|Long
name|iStudentId
decl_stmt|;
specifier|protected
name|EligibilityCheck
name|iCheck
decl_stmt|;
specifier|protected
name|boolean
name|iCustomCheck
init|=
literal|true
decl_stmt|;
specifier|protected
name|Boolean
name|iPermissionCanEnroll
decl_stmt|;
specifier|protected
name|Boolean
name|iPermissionCanRequirePreferences
decl_stmt|;
specifier|public
name|CheckEligibility
name|forStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|CheckEligibility
name|withCheck
parameter_list|(
name|EligibilityCheck
name|check
parameter_list|)
block|{
name|iCheck
operator|=
name|check
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|CheckEligibility
name|includeCustomCheck
parameter_list|(
name|boolean
name|customCheck
parameter_list|)
block|{
name|iCustomCheck
operator|=
name|customCheck
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|CheckEligibility
name|withPermission
parameter_list|(
name|boolean
name|canEnroll
parameter_list|,
name|boolean
name|canRequire
parameter_list|)
block|{
name|iPermissionCanEnroll
operator|=
name|canEnroll
expr_stmt|;
name|iPermissionCanRequirePreferences
operator|=
name|canRequire
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|protected
name|void
name|logCheck
parameter_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
parameter_list|,
name|EligibilityCheck
name|check
parameter_list|)
block|{
for|for
control|(
name|EligibilityCheck
operator|.
name|EligibilityFlag
name|f
range|:
name|EligibilityCheck
operator|.
name|EligibilityFlag
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|check
operator|.
name|hasFlag
argument_list|(
name|f
argument_list|)
condition|)
name|action
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
name|f
operator|.
name|name
argument_list|()
operator|.
name|replace
argument_list|(
literal|'_'
argument_list|,
literal|' '
argument_list|)
argument_list|)
operator|.
name|setValue
argument_list|(
literal|"true"
argument_list|)
expr_stmt|;
if|if
condition|(
name|check
operator|.
name|hasMessage
argument_list|()
condition|)
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|check
operator|.
name|getMessage
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|WARN
argument_list|)
expr_stmt|;
if|if
condition|(
name|check
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_ENROLL
argument_list|)
condition|)
name|action
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|TRUE
argument_list|)
expr_stmt|;
else|else
name|action
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|EligibilityCheck
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
name|iCheck
operator|==
literal|null
condition|)
name|iCheck
operator|=
operator|new
name|EligibilityCheck
argument_list|()
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|getAction
argument_list|()
decl_stmt|;
name|Lock
name|lock
init|=
operator|(
name|iStudentId
operator|==
literal|null
operator|||
name|server
operator|.
name|getStudent
argument_list|(
name|iStudentId
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|lockStudent
argument_list|(
name|iStudentId
argument_list|,
literal|null
argument_list|,
name|name
argument_list|()
argument_list|)
operator|)
decl_stmt|;
try|try
block|{
comment|// Always allow for the assistant mode
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_USE_ASSISTANT
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|iStudentId
operator|!=
literal|null
condition|)
name|action
operator|.
name|setStudent
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|iStudentId
argument_list|)
argument_list|)
expr_stmt|;
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_WAITLIST
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
operator|&&
name|CustomStudentEnrollmentHolder
operator|.
name|isAllowWaitListing
argument_list|()
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
name|XStudent
name|xstudent
init|=
literal|null
decl_stmt|;
try|try
block|{
name|Student
name|student
init|=
operator|(
name|iStudentId
operator|==
literal|null
condition|?
literal|null
else|:
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iStudentId
argument_list|,
name|hibSession
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADMIN
argument_list|)
operator|&&
operator|!
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADVISOR
argument_list|)
operator|&&
operator|!
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_GUEST
argument_list|)
operator|&&
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
condition|)
block|{
name|iCheck
operator|.
name|setMessage
argument_list|(
name|MSG
operator|.
name|exceptionEnrollNotStudent
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|CustomStudentEnrollmentHolder
operator|.
name|hasProvider
argument_list|()
operator|&&
name|CustomStudentEnrollmentHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|isCanRequestUpdates
argument_list|()
condition|)
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
name|hibSession
operator|=
literal|null
expr_stmt|;
comment|// UniTime does not know about the student, but there is an enrollment provider capable of requesting updates -> use check eligibility to request an update
name|CustomStudentEnrollmentHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|checkEligibility
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|iCheck
argument_list|,
operator|new
name|XStudent
argument_list|(
literal|null
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|logCheck
argument_list|(
name|action
argument_list|,
name|iCheck
argument_list|)
expr_stmt|;
name|action
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|NULL
argument_list|)
expr_stmt|;
return|return
name|iCheck
return|;
block|}
if|if
condition|(
name|iStudentId
operator|!=
literal|null
condition|)
block|{
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|HAS_ADVISOR_REQUESTS
argument_list|,
name|student
operator|.
name|getAdvisorCourseRequests
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|student
operator|.
name|getAdvisorCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setName
argument_list|(
name|helper
operator|.
name|getStudentNameFormat
argument_list|()
operator|.
name|format
argument_list|(
name|student
argument_list|)
argument_list|)
expr_stmt|;
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getEffectiveStatus
argument_list|()
decl_stmt|;
name|boolean
name|disabled
init|=
operator|(
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
operator|)
decl_stmt|;
if|if
condition|(
name|disabled
operator|&&
name|status
operator|!=
literal|null
operator|&&
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
operator|&&
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADMIN
argument_list|)
condition|)
name|disabled
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|disabled
operator|&&
name|status
operator|!=
literal|null
operator|&&
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
operator|&&
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADVISOR
argument_list|)
condition|)
name|disabled
operator|=
literal|false
expr_stmt|;
name|boolean
name|noenrl
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|iPermissionCanEnroll
operator|!=
literal|null
condition|)
block|{
name|noenrl
operator|=
operator|!
name|iPermissionCanEnroll
expr_stmt|;
block|}
else|else
block|{
name|noenrl
operator|=
operator|(
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
operator|)
expr_stmt|;
if|if
condition|(
name|noenrl
operator|&&
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
operator|&&
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADMIN
argument_list|)
condition|)
name|noenrl
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|noenrl
operator|&&
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
operator|&&
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADVISOR
argument_list|)
condition|)
name|noenrl
operator|=
literal|false
expr_stmt|;
block|}
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
name|waitlist
argument_list|)
condition|)
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_WAITLIST
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|iPermissionCanRequirePreferences
operator|!=
literal|null
condition|)
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_REQUIRE
argument_list|,
name|iPermissionCanRequirePreferences
argument_list|)
expr_stmt|;
else|else
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_REQUIRE
argument_list|,
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADMIN
argument_list|)
operator|||
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADVISOR
argument_list|)
operator|||
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
name|canreq
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|disabled
condition|)
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_USE_ASSISTANT
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|noenrl
condition|)
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_ENROLL
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_ENROLL
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|StudentSectioningStatus
name|s
init|=
name|student
operator|.
name|getSectioningStatus
argument_list|()
decl_stmt|;
while|while
condition|(
name|s
operator|!=
literal|null
operator|&&
name|s
operator|.
name|isPast
argument_list|()
operator|&&
name|s
operator|.
name|getFallBackStatus
argument_list|()
operator|!=
literal|null
condition|)
name|s
operator|=
name|s
operator|.
name|getFallBackStatus
argument_list|()
expr_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
operator|&&
name|s
operator|.
name|getMessage
argument_list|()
operator|!=
literal|null
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
name|s
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|status
operator|!=
literal|null
operator|&&
name|status
operator|.
name|getMessage
argument_list|()
operator|!=
literal|null
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
name|status
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|disabled
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
name|MSG
operator|.
name|exceptionAccessDisabled
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|noenrl
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
name|MSG
operator|.
name|exceptionEnrollmentDisabled
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|StudentStatusEffectivePeriodMessage
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|String
name|effectivePeriod
init|=
operator|(
name|s
operator|!=
literal|null
condition|?
name|s
operator|.
name|getEffectivePeriod
argument_list|()
else|:
name|status
operator|!=
literal|null
condition|?
name|status
operator|.
name|getEffectivePeriod
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|effectivePeriod
operator|!=
literal|null
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
operator|(
name|iCheck
operator|.
name|hasMessage
argument_list|()
condition|?
name|iCheck
operator|.
name|getMessage
argument_list|()
operator|+
literal|"\n"
else|:
literal|""
operator|)
operator|+
name|MSG
operator|.
name|messageTimeWindow
argument_list|(
name|effectivePeriod
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|xstudent
operator|=
name|server
operator|.
name|getStudent
argument_list|(
name|iStudentId
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|hibSession
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|xstudent
operator|==
literal|null
operator|&&
name|iStudentId
operator|!=
literal|null
condition|)
block|{
comment|// Server does not know about the student, but he/she is in the database --> try to reload it
name|server
operator|.
name|createAction
argument_list|(
name|ReloadStudent
operator|.
name|class
argument_list|)
operator|.
name|forStudents
argument_list|(
name|iStudentId
argument_list|)
operator|.
name|execute
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
expr_stmt|;
name|xstudent
operator|=
name|server
operator|.
name|getStudent
argument_list|(
name|iStudentId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|xstudent
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|iCheck
operator|.
name|hasMessage
argument_list|()
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
name|MSG
operator|.
name|exceptionEnrollNotStudent
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_ENROLL
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|iCustomCheck
condition|)
block|{
if|if
condition|(
name|CustomStudentEnrollmentHolder
operator|.
name|hasProvider
argument_list|()
condition|)
name|CustomStudentEnrollmentHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|checkEligibility
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|iCheck
argument_list|,
name|xstudent
argument_list|)
expr_stmt|;
if|if
condition|(
name|CustomSpecialRegistrationHolder
operator|.
name|hasProvider
argument_list|()
condition|)
name|CustomSpecialRegistrationHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|checkEligibility
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|iCheck
argument_list|,
name|xstudent
argument_list|)
expr_stmt|;
block|}
name|logCheck
argument_list|(
name|action
argument_list|,
name|iCheck
argument_list|)
expr_stmt|;
return|return
name|iCheck
return|;
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_ENROLL
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iCheck
operator|.
name|setMessage
argument_list|(
name|MSG
operator|.
name|exceptionFailedEligibilityCheck
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
name|MSG
operator|.
name|exceptionFailedEligibilityCheck
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|logCheck
argument_list|(
name|action
argument_list|,
name|iCheck
argument_list|)
expr_stmt|;
name|action
operator|.
name|setResult
argument_list|(
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|ResultType
operator|.
name|FAILURE
argument_list|)
expr_stmt|;
return|return
name|iCheck
return|;
block|}
finally|finally
block|{
if|if
condition|(
name|lock
operator|!=
literal|null
condition|)
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"eligibility"
return|;
block|}
block|}
end_class

end_unit

