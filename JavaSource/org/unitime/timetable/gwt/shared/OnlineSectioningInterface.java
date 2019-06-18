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
name|gwt
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

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
name|Map
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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|IsSerializable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineSectioningInterface
implements|implements
name|IsSerializable
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
class|class
name|EligibilityCheck
implements|implements
name|IsSerializable
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|int
name|iFlags
init|=
literal|0
decl_stmt|;
specifier|private
name|String
name|iMessage
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iCheckboxMessage
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iSessionId
init|=
literal|null
decl_stmt|,
name|iStudentId
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|String
argument_list|>
name|iOverrides
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iOverrideRequestDisclaimer
init|=
literal|null
decl_stmt|;
specifier|private
name|GradeModes
name|iGradeModes
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|EligibilityFlag
implements|implements
name|IsSerializable
block|{
name|IS_ADMIN
block|,
name|IS_ADVISOR
block|,
name|IS_GUEST
block|,
name|CAN_USE_ASSISTANT
block|,
name|CAN_ENROLL
block|,
name|PIN_REQUIRED
block|,
name|CAN_WAITLIST
block|,
name|RECHECK_AFTER_ENROLLMENT
block|,
name|RECHECK_BEFORE_ENROLLMENT
block|,
name|CAN_RESET
block|,
name|CONFIRM_DROP
block|,
name|QUICK_ADD_DROP
block|,
name|ALTERNATIVES_DROP
block|,
name|GWT_CONFIRMATIONS
block|,
name|DEGREE_PLANS
block|,
name|CAN_REGISTER
block|,
name|NO_REQUEST_ARROWS
block|,
name|CAN_SPECREG
block|,
name|HAS_SPECREG
block|,
name|SR_TIME_CONF
block|,
name|SR_LIMIT_CONF
block|,
name|CAN_REQUIRE
block|,
name|CAN_CHANGE_GRADE_MODE
block|, 			;
specifier|public
name|int
name|flag
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
block|}
specifier|public
name|EligibilityCheck
parameter_list|()
block|{
block|}
specifier|public
name|EligibilityCheck
parameter_list|(
name|String
name|message
parameter_list|,
name|EligibilityFlag
modifier|...
name|flags
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
for|for
control|(
name|EligibilityFlag
name|flag
range|:
name|flags
control|)
name|setFlag
argument_list|(
name|flag
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|EligibilityCheck
parameter_list|(
name|EligibilityFlag
modifier|...
name|flags
parameter_list|)
block|{
for|for
control|(
name|EligibilityFlag
name|flag
range|:
name|flags
control|)
name|setFlag
argument_list|(
name|flag
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setFlag
parameter_list|(
name|EligibilityFlag
name|flag
parameter_list|,
name|boolean
name|set
parameter_list|)
block|{
if|if
condition|(
name|set
operator|&&
operator|!
name|hasFlag
argument_list|(
name|flag
argument_list|)
condition|)
name|iFlags
operator|+=
name|flag
operator|.
name|flag
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|set
operator|&&
name|hasFlag
argument_list|(
name|flag
argument_list|)
condition|)
name|iFlags
operator|-=
name|flag
operator|.
name|flag
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasFlag
parameter_list|(
name|EligibilityFlag
name|flag
parameter_list|)
block|{
return|return
operator|(
name|iFlags
operator|&
name|flag
operator|.
name|flag
argument_list|()
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|boolean
name|hasFlag
parameter_list|(
name|EligibilityFlag
modifier|...
name|flags
parameter_list|)
block|{
for|for
control|(
name|EligibilityFlag
name|flag
range|:
name|flags
control|)
if|if
condition|(
operator|(
name|iFlags
operator|&
name|flag
operator|.
name|flag
argument_list|()
operator|)
operator|!=
literal|0
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasMessage
parameter_list|()
block|{
return|return
name|iMessage
operator|!=
literal|null
operator|&&
operator|!
name|iMessage
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setCheckboxMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iCheckboxMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasCheckboxMessage
parameter_list|()
block|{
return|return
name|iCheckboxMessage
operator|!=
literal|null
operator|&&
operator|!
name|iCheckboxMessage
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getCheckboxMessage
parameter_list|()
block|{
return|return
name|iCheckboxMessage
return|;
block|}
specifier|public
name|void
name|setOverrideRequestDisclaimer
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iOverrideRequestDisclaimer
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|String
name|getOverrideRequestDisclaimer
parameter_list|()
block|{
return|return
name|iOverrideRequestDisclaimer
return|;
block|}
specifier|public
name|boolean
name|hasOverrideRequestDisclaimer
parameter_list|()
block|{
return|return
name|iOverrideRequestDisclaimer
operator|!=
literal|null
operator|&&
operator|!
name|iOverrideRequestDisclaimer
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setStudentId
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
block|}
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
return|;
block|}
specifier|public
name|boolean
name|hasOverride
parameter_list|(
name|String
name|errorCode
parameter_list|)
block|{
if|if
condition|(
name|errorCode
operator|==
literal|null
operator|||
name|errorCode
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
name|iOverrides
operator|!=
literal|null
operator|&&
name|iOverrides
operator|.
name|contains
argument_list|(
name|errorCode
argument_list|)
return|;
block|}
specifier|public
name|void
name|setOverrides
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|overrides
parameter_list|)
block|{
name|iOverrides
operator|=
operator|(
name|overrides
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|overrides
argument_list|)
operator|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasGradeModes
parameter_list|()
block|{
return|return
name|iGradeModes
operator|!=
literal|null
operator|&&
name|iGradeModes
operator|.
name|hasGradeModes
argument_list|()
return|;
block|}
specifier|public
name|void
name|addGradeMode
parameter_list|(
name|String
name|sectionId
parameter_list|,
name|String
name|code
parameter_list|,
name|String
name|label
parameter_list|)
block|{
if|if
condition|(
name|iGradeModes
operator|==
literal|null
condition|)
name|iGradeModes
operator|=
operator|new
name|GradeModes
argument_list|()
expr_stmt|;
name|iGradeModes
operator|.
name|add
argument_list|(
name|sectionId
argument_list|,
operator|new
name|GradeMode
argument_list|(
name|code
argument_list|,
name|label
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|GradeMode
name|getGradeMode
parameter_list|(
name|ClassAssignment
name|section
parameter_list|)
block|{
if|if
condition|(
name|iGradeModes
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|iGradeModes
operator|.
name|get
argument_list|(
name|section
argument_list|)
return|;
block|}
specifier|public
name|GradeModes
name|getGradeModes
parameter_list|()
block|{
return|return
name|iGradeModes
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SectioningProperties
implements|implements
name|IsSerializable
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iSessionId
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAdmin
init|=
literal|false
decl_stmt|,
name|iAdvisor
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iEmail
init|=
literal|false
decl_stmt|,
name|iMassCancel
init|=
literal|false
decl_stmt|,
name|iChangeStatus
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iRequestUpdate
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iChangeLog
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCheckStudentOverrides
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iValidateStudentOverrides
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iRecheckCriticalCourses
init|=
literal|false
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentGroupInfo
argument_list|>
name|iEditableGroups
init|=
literal|null
decl_stmt|;
specifier|public
name|SectioningProperties
parameter_list|()
block|{
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|boolean
name|isAdmin
parameter_list|()
block|{
return|return
name|iAdmin
return|;
block|}
specifier|public
name|boolean
name|isAdvisor
parameter_list|()
block|{
return|return
name|iAdvisor
return|;
block|}
specifier|public
name|boolean
name|isAdminOrAdvisor
parameter_list|()
block|{
return|return
name|iAdmin
operator|||
name|iAdvisor
return|;
block|}
specifier|public
name|void
name|setAdmin
parameter_list|(
name|boolean
name|admin
parameter_list|)
block|{
name|iAdmin
operator|=
name|admin
expr_stmt|;
block|}
specifier|public
name|void
name|setAdvisor
parameter_list|(
name|boolean
name|advisor
parameter_list|)
block|{
name|iAdvisor
operator|=
name|advisor
expr_stmt|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|boolean
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setMassCancel
parameter_list|(
name|boolean
name|massCancel
parameter_list|)
block|{
name|iMassCancel
operator|=
name|massCancel
expr_stmt|;
block|}
specifier|public
name|boolean
name|isMassCancel
parameter_list|()
block|{
return|return
name|iMassCancel
return|;
block|}
specifier|public
name|void
name|setChangeStatus
parameter_list|(
name|boolean
name|changeStatus
parameter_list|)
block|{
name|iChangeStatus
operator|=
name|changeStatus
expr_stmt|;
block|}
specifier|public
name|boolean
name|isChangeStatus
parameter_list|()
block|{
return|return
name|iChangeStatus
return|;
block|}
specifier|public
name|void
name|setRequestUpdate
parameter_list|(
name|boolean
name|requestUpdate
parameter_list|)
block|{
name|iRequestUpdate
operator|=
name|requestUpdate
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRequestUpdate
parameter_list|()
block|{
return|return
name|iRequestUpdate
return|;
block|}
specifier|public
name|void
name|setCheckStudentOverrides
parameter_list|(
name|boolean
name|checkOverrides
parameter_list|)
block|{
name|iCheckStudentOverrides
operator|=
name|checkOverrides
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCheckStudentOverrides
parameter_list|()
block|{
return|return
name|iCheckStudentOverrides
return|;
block|}
specifier|public
name|void
name|setValidateStudentOverrides
parameter_list|(
name|boolean
name|validateOverrides
parameter_list|)
block|{
name|iValidateStudentOverrides
operator|=
name|validateOverrides
expr_stmt|;
block|}
specifier|public
name|boolean
name|isValidateStudentOverrides
parameter_list|()
block|{
return|return
name|iValidateStudentOverrides
return|;
block|}
specifier|public
name|void
name|setRecheckCriticalCourses
parameter_list|(
name|boolean
name|recheckCriticalCourses
parameter_list|)
block|{
name|iRecheckCriticalCourses
operator|=
name|recheckCriticalCourses
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRecheckCriticalCourses
parameter_list|()
block|{
return|return
name|iRecheckCriticalCourses
return|;
block|}
specifier|public
name|void
name|setChangeLog
parameter_list|(
name|boolean
name|changeLog
parameter_list|)
block|{
name|iChangeLog
operator|=
name|changeLog
expr_stmt|;
block|}
specifier|public
name|boolean
name|isChangeLog
parameter_list|()
block|{
return|return
name|iChangeLog
return|;
block|}
specifier|public
name|boolean
name|isCanSelectStudent
parameter_list|()
block|{
return|return
name|iEmail
operator|||
name|iMassCancel
operator|||
name|iChangeStatus
operator|||
name|iRequestUpdate
operator|||
name|iCheckStudentOverrides
operator|||
name|iValidateStudentOverrides
return|;
block|}
specifier|public
name|boolean
name|hasEditableGroups
parameter_list|()
block|{
return|return
name|iEditableGroups
operator|!=
literal|null
operator|&&
operator|!
name|iEditableGroups
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentGroupInfo
argument_list|>
name|getEditableGroups
parameter_list|()
block|{
return|return
name|iEditableGroups
return|;
block|}
specifier|public
name|void
name|setEditableGroups
parameter_list|(
name|Set
argument_list|<
name|StudentGroupInfo
argument_list|>
name|groups
parameter_list|)
block|{
name|iEditableGroups
operator|=
name|groups
expr_stmt|;
block|}
specifier|public
name|void
name|addEditableGroup
parameter_list|(
name|StudentGroupInfo
name|group
parameter_list|)
block|{
if|if
condition|(
name|iEditableGroups
operator|==
literal|null
condition|)
name|iEditableGroups
operator|=
operator|new
name|TreeSet
argument_list|<
name|StudentGroupInfo
argument_list|>
argument_list|()
expr_stmt|;
name|iEditableGroups
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|StudentGroupInfo
implements|implements
name|IsSerializable
implements|,
name|Serializable
implements|,
name|Comparable
argument_list|<
name|StudentGroupInfo
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
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iReference
decl_stmt|,
name|iLabel
decl_stmt|;
specifier|private
name|String
name|iType
decl_stmt|;
specifier|public
name|StudentGroupInfo
parameter_list|()
block|{
block|}
specifier|public
name|StudentGroupInfo
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|reference
parameter_list|,
name|String
name|label
parameter_list|,
name|String
name|type
parameter_list|)
block|{
name|iUniqueId
operator|=
name|id
expr_stmt|;
name|iReference
operator|=
name|reference
expr_stmt|;
name|iLabel
operator|=
name|label
expr_stmt|;
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iUniqueId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|iReference
operator|=
name|reference
expr_stmt|;
block|}
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|iReference
return|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|iLabel
operator|=
name|label
expr_stmt|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasType
parameter_list|()
block|{
return|return
name|iType
operator|!=
literal|null
operator|&&
operator|!
name|iType
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getReference
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getReference
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|StudentGroupInfo
name|status
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|(
name|hasType
argument_list|()
condition|?
name|getType
argument_list|()
else|:
literal|""
operator|)
operator|.
name|compareTo
argument_list|(
name|status
operator|.
name|hasType
argument_list|()
condition|?
name|status
operator|.
name|getType
argument_list|()
else|:
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getReference
argument_list|()
operator|.
name|compareTo
argument_list|(
name|status
operator|.
name|getReference
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|StudentGroupInfo
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|StudentGroupInfo
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|StudentStatusInfo
implements|implements
name|IsSerializable
implements|,
name|Serializable
implements|,
name|Comparable
argument_list|<
name|StudentStatusInfo
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
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iReference
decl_stmt|,
name|iLabel
decl_stmt|;
specifier|private
name|boolean
name|iAssistantPage
init|=
literal|false
decl_stmt|,
name|iRequestsPage
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iRegStudent
init|=
literal|false
decl_stmt|,
name|iRegAdvisor
init|=
literal|false
decl_stmt|,
name|iRegAdmin
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iEnrlStudent
init|=
literal|false
decl_stmt|,
name|iEnrlAdvisor
init|=
literal|false
decl_stmt|,
name|iEnrlAdmin
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iWaitList
init|=
literal|false
decl_stmt|,
name|iEmail
init|=
literal|false
decl_stmt|,
name|iCanRequire
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iSpecReg
init|=
literal|false
decl_stmt|,
name|iReqValidation
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iCourseTypes
decl_stmt|;
specifier|private
name|String
name|iEffectiveStart
decl_stmt|,
name|iEffectiveStop
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|String
name|iFallback
decl_stmt|;
specifier|public
name|StudentStatusInfo
parameter_list|()
block|{
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iUniqueId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|iReference
operator|=
name|reference
expr_stmt|;
block|}
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|iReference
return|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|iLabel
operator|=
name|label
expr_stmt|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setCanAccessAssistantPage
parameter_list|(
name|boolean
name|assistantPage
parameter_list|)
block|{
name|iAssistantPage
operator|=
name|assistantPage
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanAccessAssistantPage
parameter_list|()
block|{
return|return
name|iAssistantPage
return|;
block|}
specifier|public
name|void
name|setCanAccessRequestsPage
parameter_list|(
name|boolean
name|requestPage
parameter_list|)
block|{
name|iRequestsPage
operator|=
name|requestPage
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanAccessRequestsPage
parameter_list|()
block|{
return|return
name|iRequestsPage
return|;
block|}
specifier|public
name|void
name|setCanStudentRegister
parameter_list|(
name|boolean
name|regStudent
parameter_list|)
block|{
name|iRegStudent
operator|=
name|regStudent
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanStudentRegister
parameter_list|()
block|{
return|return
name|iRegStudent
return|;
block|}
specifier|public
name|void
name|setCanAdvisorRegister
parameter_list|(
name|boolean
name|regAdvisor
parameter_list|)
block|{
name|iRegAdvisor
operator|=
name|regAdvisor
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanAdvisorRegister
parameter_list|()
block|{
return|return
name|iRegAdvisor
return|;
block|}
specifier|public
name|void
name|setCanAdminRegister
parameter_list|(
name|boolean
name|regAdmin
parameter_list|)
block|{
name|iRegAdmin
operator|=
name|regAdmin
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanAdminRegister
parameter_list|()
block|{
return|return
name|iRegAdmin
return|;
block|}
specifier|public
name|void
name|setCanStudentEnroll
parameter_list|(
name|boolean
name|enrlStudent
parameter_list|)
block|{
name|iEnrlStudent
operator|=
name|enrlStudent
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanStudentEnroll
parameter_list|()
block|{
return|return
name|iEnrlStudent
return|;
block|}
specifier|public
name|void
name|setCanAdvisorEnroll
parameter_list|(
name|boolean
name|enrlAdvisor
parameter_list|)
block|{
name|iEnrlAdvisor
operator|=
name|enrlAdvisor
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanAdvisorEnroll
parameter_list|()
block|{
return|return
name|iEnrlAdvisor
return|;
block|}
specifier|public
name|void
name|setCanAdminEnroll
parameter_list|(
name|boolean
name|enrlAdmin
parameter_list|)
block|{
name|iEnrlAdmin
operator|=
name|enrlAdmin
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanAdminEnroll
parameter_list|()
block|{
return|return
name|iEnrlAdmin
return|;
block|}
specifier|public
name|void
name|setWaitList
parameter_list|(
name|boolean
name|waitlist
parameter_list|)
block|{
name|iWaitList
operator|=
name|waitlist
expr_stmt|;
block|}
specifier|public
name|boolean
name|isWaitList
parameter_list|()
block|{
return|return
name|iWaitList
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|boolean
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setCanRequire
parameter_list|(
name|boolean
name|canRequire
parameter_list|)
block|{
name|iCanRequire
operator|=
name|canRequire
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanRequire
parameter_list|()
block|{
return|return
name|iCanRequire
return|;
block|}
specifier|public
name|void
name|setSpecialRegistration
parameter_list|(
name|boolean
name|specReg
parameter_list|)
block|{
name|iSpecReg
operator|=
name|specReg
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSpecialRegistration
parameter_list|()
block|{
return|return
name|iSpecReg
return|;
block|}
specifier|public
name|void
name|setRequestValiadtion
parameter_list|(
name|boolean
name|reqVal
parameter_list|)
block|{
name|iReqValidation
operator|=
name|reqVal
expr_stmt|;
block|}
specifier|public
name|boolean
name|isRequestValiadtion
parameter_list|()
block|{
return|return
name|iReqValidation
return|;
block|}
specifier|public
name|void
name|setAllEnabled
parameter_list|()
block|{
name|iAssistantPage
operator|=
literal|true
expr_stmt|;
name|iRequestsPage
operator|=
literal|true
expr_stmt|;
name|iRegStudent
operator|=
literal|true
expr_stmt|;
name|iRegAdvisor
operator|=
literal|true
expr_stmt|;
name|iRegAdmin
operator|=
literal|true
expr_stmt|;
name|iEnrlStudent
operator|=
literal|true
expr_stmt|;
name|iEnrlAdvisor
operator|=
literal|true
expr_stmt|;
name|iEnrlAdmin
operator|=
literal|true
expr_stmt|;
name|iWaitList
operator|=
literal|true
expr_stmt|;
name|iEmail
operator|=
literal|true
expr_stmt|;
name|iCanRequire
operator|=
literal|true
expr_stmt|;
block|}
specifier|public
name|void
name|setCourseTypes
parameter_list|(
name|String
name|courseTypes
parameter_list|)
block|{
name|iCourseTypes
operator|=
name|courseTypes
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasCourseTypes
parameter_list|()
block|{
return|return
name|iCourseTypes
operator|!=
literal|null
operator|&&
operator|!
name|iCourseTypes
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getCourseTypes
parameter_list|()
block|{
return|return
name|iCourseTypes
return|;
block|}
specifier|public
name|void
name|setEffectiveStart
parameter_list|(
name|String
name|start
parameter_list|)
block|{
name|iEffectiveStart
operator|=
name|start
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasEffectiveStart
parameter_list|()
block|{
return|return
name|iEffectiveStart
operator|!=
literal|null
operator|&&
operator|!
name|iEffectiveStart
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getEffectiveStart
parameter_list|()
block|{
return|return
name|iEffectiveStart
return|;
block|}
specifier|public
name|void
name|setEffectiveStop
parameter_list|(
name|String
name|stop
parameter_list|)
block|{
name|iEffectiveStop
operator|=
name|stop
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasEffectiveStop
parameter_list|()
block|{
return|return
name|iEffectiveStop
operator|!=
literal|null
operator|&&
operator|!
name|iEffectiveStop
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getEffectiveStop
parameter_list|()
block|{
return|return
name|iEffectiveStop
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasMessage
parameter_list|()
block|{
return|return
name|iMessage
operator|!=
literal|null
operator|&&
operator|!
name|iMessage
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setFallback
parameter_list|(
name|String
name|fallback
parameter_list|)
block|{
name|iFallback
operator|=
name|fallback
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasFallback
parameter_list|()
block|{
return|return
name|iFallback
operator|!=
literal|null
operator|&&
operator|!
name|iFallback
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getFallback
parameter_list|()
block|{
return|return
name|iFallback
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getReference
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getReference
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|StudentStatusInfo
name|status
parameter_list|)
block|{
return|return
name|getReference
argument_list|()
operator|.
name|compareTo
argument_list|(
name|status
operator|.
name|getReference
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|StudentStatusInfo
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|StudentStatusInfo
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|GradeModes
implements|implements
name|IsSerializable
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|GradeMode
argument_list|>
name|iModes
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|GradeMode
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|GradeModes
parameter_list|()
block|{
block|}
specifier|public
name|boolean
name|hasGradeModes
parameter_list|()
block|{
return|return
operator|!
name|iModes
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|add
parameter_list|(
name|String
name|sectionId
parameter_list|,
name|GradeMode
name|mode
parameter_list|)
block|{
name|iModes
operator|.
name|put
argument_list|(
name|sectionId
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
specifier|public
name|GradeMode
name|get
parameter_list|(
name|ClassAssignment
name|a
parameter_list|)
block|{
if|if
condition|(
name|a
operator|.
name|getExternalId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|a
operator|.
name|getParentSection
argument_list|()
operator|!=
literal|null
operator|&&
name|a
operator|.
name|getParentSection
argument_list|()
operator|.
name|equals
argument_list|(
name|a
operator|.
name|getSection
argument_list|()
argument_list|)
condition|)
return|return
literal|null
return|;
return|return
name|iModes
operator|.
name|get
argument_list|(
name|a
operator|.
name|getExternalId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|GradeMode
argument_list|>
name|toMap
parameter_list|()
block|{
return|return
name|iModes
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|GradeMode
implements|implements
name|IsSerializable
implements|,
name|Serializable
implements|,
name|Comparable
argument_list|<
name|GradeMode
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
specifier|private
name|String
name|iCode
decl_stmt|;
specifier|private
name|String
name|iLabel
decl_stmt|;
specifier|public
name|GradeMode
parameter_list|()
block|{
block|}
specifier|public
name|GradeMode
parameter_list|(
name|String
name|code
parameter_list|,
name|String
name|label
parameter_list|)
block|{
name|iCode
operator|=
name|code
expr_stmt|;
name|iLabel
operator|=
name|label
expr_stmt|;
block|}
specifier|public
name|void
name|setCode
parameter_list|(
name|String
name|code
parameter_list|)
block|{
name|iCode
operator|=
name|code
expr_stmt|;
block|}
specifier|public
name|String
name|getCode
parameter_list|()
block|{
return|return
name|iCode
return|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|iLabel
operator|=
name|label
expr_stmt|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getCode
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|GradeMode
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getCode
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|GradeMode
operator|)
name|o
operator|)
operator|.
name|getCode
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|GradeMode
name|m
parameter_list|)
block|{
return|return
name|getLabel
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|m
operator|.
name|getLabel
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

