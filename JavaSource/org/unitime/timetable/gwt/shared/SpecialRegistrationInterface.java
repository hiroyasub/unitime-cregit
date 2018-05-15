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
name|ArrayList
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
name|Date
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
name|ErrorMessage
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
name|SpecialRegistrationInterface
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
name|SpecialRegistrationContext
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
name|boolean
name|iSpecReg
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iSpecRegRequestId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSpecRegRequestKey
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iSpecRegRequestKeyValid
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iSpecRegDisclaimerAccepted
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iSpecRegTimeConfs
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iSpecRegSpaceConfs
init|=
literal|false
decl_stmt|;
specifier|private
name|SpecialRegistrationStatus
name|iSpecRegStatus
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iNote
decl_stmt|;
specifier|public
name|SpecialRegistrationContext
parameter_list|()
block|{
block|}
specifier|public
name|SpecialRegistrationContext
parameter_list|(
name|SpecialRegistrationContext
name|cx
parameter_list|)
block|{
name|copy
argument_list|(
name|cx
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|copy
parameter_list|(
name|SpecialRegistrationContext
name|cx
parameter_list|)
block|{
name|iSpecReg
operator|=
name|cx
operator|.
name|iSpecReg
expr_stmt|;
name|iSpecRegRequestId
operator|=
name|cx
operator|.
name|iSpecRegRequestId
expr_stmt|;
name|iSpecRegRequestKey
operator|=
name|cx
operator|.
name|iSpecRegRequestKey
expr_stmt|;
name|iSpecRegDisclaimerAccepted
operator|=
name|cx
operator|.
name|iSpecRegDisclaimerAccepted
expr_stmt|;
name|iSpecRegTimeConfs
operator|=
name|cx
operator|.
name|iSpecRegTimeConfs
expr_stmt|;
name|iSpecRegSpaceConfs
operator|=
name|cx
operator|.
name|iSpecRegSpaceConfs
expr_stmt|;
name|iSpecRegStatus
operator|=
name|cx
operator|.
name|iSpecRegStatus
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|iSpecReg
return|;
block|}
specifier|public
name|void
name|setEnabled
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
name|String
name|getRequestKey
parameter_list|()
block|{
return|return
name|iSpecRegRequestKey
return|;
block|}
specifier|public
name|void
name|setRequestKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|iSpecRegRequestKey
operator|=
name|key
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasRequestKey
parameter_list|()
block|{
return|return
name|iSpecRegRequestKey
operator|!=
literal|null
operator|&&
operator|!
name|iSpecRegRequestKey
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isSpecRegRequestKeyValid
parameter_list|()
block|{
return|return
name|iSpecRegRequestKeyValid
return|;
block|}
specifier|public
name|void
name|setSpecRegRequestKeyValid
parameter_list|(
name|boolean
name|valid
parameter_list|)
block|{
name|iSpecRegRequestKeyValid
operator|=
name|valid
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasRequestId
parameter_list|()
block|{
return|return
name|iSpecRegRequestId
operator|!=
literal|null
return|;
block|}
specifier|public
name|String
name|getRequestId
parameter_list|()
block|{
return|return
name|iSpecRegRequestId
return|;
block|}
specifier|public
name|void
name|setRequestId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iSpecRegRequestId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanSubmit
parameter_list|()
block|{
return|return
name|iSpecRegStatus
operator|==
literal|null
operator|||
name|iSpecRegStatus
operator|==
name|SpecialRegistrationStatus
operator|.
name|Draft
return|;
block|}
specifier|public
name|boolean
name|isDisclaimerAccepted
parameter_list|()
block|{
return|return
name|iSpecRegDisclaimerAccepted
return|;
block|}
specifier|public
name|void
name|setDisclaimerAccepted
parameter_list|(
name|boolean
name|accepted
parameter_list|)
block|{
name|iSpecRegDisclaimerAccepted
operator|=
name|accepted
expr_stmt|;
block|}
specifier|public
name|boolean
name|areTimeConflictsAllowed
parameter_list|()
block|{
return|return
name|iSpecRegTimeConfs
return|;
block|}
specifier|public
name|void
name|setTimeConflictsAllowed
parameter_list|(
name|boolean
name|allow
parameter_list|)
block|{
name|iSpecRegTimeConfs
operator|=
name|allow
expr_stmt|;
block|}
specifier|public
name|boolean
name|areSpaceConflictsAllowed
parameter_list|()
block|{
return|return
name|iSpecRegSpaceConfs
return|;
block|}
specifier|public
name|void
name|setSpaceConflictsAllowed
parameter_list|(
name|boolean
name|allow
parameter_list|)
block|{
name|iSpecRegSpaceConfs
operator|=
name|allow
expr_stmt|;
block|}
specifier|public
name|SpecialRegistrationStatus
name|getStatus
parameter_list|()
block|{
return|return
name|iSpecRegStatus
return|;
block|}
specifier|public
name|void
name|setStatus
parameter_list|(
name|SpecialRegistrationStatus
name|status
parameter_list|)
block|{
name|iSpecRegStatus
operator|=
name|status
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|void
name|update
parameter_list|(
name|EligibilityCheck
name|check
parameter_list|)
block|{
name|iSpecRegTimeConfs
operator|=
name|check
operator|!=
literal|null
operator|&&
name|check
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|SR_TIME_CONF
argument_list|)
expr_stmt|;
name|iSpecRegSpaceConfs
operator|=
name|check
operator|!=
literal|null
operator|&&
name|check
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|SR_LIMIT_CONF
argument_list|)
expr_stmt|;
name|iSpecReg
operator|=
name|check
operator|!=
literal|null
operator|&&
name|check
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_SPECREG
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|reset
parameter_list|()
block|{
name|iNote
operator|=
literal|null
expr_stmt|;
name|iSpecReg
operator|=
literal|false
expr_stmt|;
name|iSpecRegRequestId
operator|=
literal|null
expr_stmt|;
name|iSpecRegRequestKeyValid
operator|=
literal|false
expr_stmt|;
name|iSpecRegDisclaimerAccepted
operator|=
literal|false
expr_stmt|;
name|iSpecRegTimeConfs
operator|=
literal|false
expr_stmt|;
name|iSpecRegSpaceConfs
operator|=
literal|false
expr_stmt|;
name|iSpecRegStatus
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|EligibilityCheck
name|check
parameter_list|)
block|{
name|reset
argument_list|()
expr_stmt|;
if|if
condition|(
name|check
operator|!=
literal|null
condition|)
name|update
argument_list|(
name|check
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationEligibilityRequest
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
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|iClassAssignments
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|iErrors
init|=
literal|null
decl_stmt|;
specifier|public
name|SpecialRegistrationEligibilityRequest
parameter_list|()
block|{
block|}
specifier|public
name|SpecialRegistrationEligibilityRequest
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|studentId
parameter_list|,
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|assignments
parameter_list|,
name|Collection
argument_list|<
name|ErrorMessage
argument_list|>
name|errors
parameter_list|)
block|{
name|iClassAssignments
operator|=
name|assignments
expr_stmt|;
name|iStudentId
operator|=
name|studentId
expr_stmt|;
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
if|if
condition|(
name|errors
operator|!=
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|(
name|errors
argument_list|)
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
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
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
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|getClassAssignments
parameter_list|()
block|{
return|return
name|iClassAssignments
return|;
block|}
specifier|public
name|void
name|setClassAssignments
parameter_list|(
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|assignments
parameter_list|)
block|{
name|iClassAssignments
operator|=
name|assignments
expr_stmt|;
block|}
specifier|public
name|void
name|addError
parameter_list|(
name|ErrorMessage
name|error
parameter_list|)
block|{
if|if
condition|(
name|iErrors
operator|==
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|()
expr_stmt|;
name|iErrors
operator|.
name|add
argument_list|(
name|error
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasErrors
parameter_list|()
block|{
return|return
name|iErrors
operator|!=
literal|null
operator|&&
operator|!
name|iErrors
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|getErrors
parameter_list|()
block|{
return|return
name|iErrors
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SpecialRegistrationEligibilityResponse
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
name|String
name|iMessage
decl_stmt|;
specifier|private
name|boolean
name|iCanSubmit
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|iErrors
init|=
literal|null
decl_stmt|;
specifier|public
name|SpecialRegistrationEligibilityResponse
parameter_list|()
block|{
block|}
specifier|public
name|SpecialRegistrationEligibilityResponse
parameter_list|(
name|boolean
name|canSubmit
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|iCanSubmit
operator|=
name|canSubmit
expr_stmt|;
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|boolean
name|isCanSubmit
parameter_list|()
block|{
return|return
name|iCanSubmit
return|;
block|}
specifier|public
name|void
name|setCanSubmit
parameter_list|(
name|boolean
name|canSubmit
parameter_list|)
block|{
name|iCanSubmit
operator|=
name|canSubmit
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
name|void
name|addError
parameter_list|(
name|ErrorMessage
name|error
parameter_list|)
block|{
if|if
condition|(
name|iErrors
operator|==
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|()
expr_stmt|;
name|iErrors
operator|.
name|add
argument_list|(
name|error
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasErrors
parameter_list|()
block|{
return|return
name|iErrors
operator|!=
literal|null
operator|&&
operator|!
name|iErrors
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|getErrors
parameter_list|()
block|{
return|return
name|iErrors
return|;
block|}
specifier|public
name|void
name|setErrors
parameter_list|(
name|Collection
argument_list|<
name|ErrorMessage
argument_list|>
name|messages
parameter_list|)
block|{
if|if
condition|(
name|messages
operator|==
literal|null
condition|)
name|iErrors
operator|=
literal|null
expr_stmt|;
else|else
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|(
name|messages
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|RetrieveSpecialRegistrationRequest
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
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|String
name|iRequestKey
decl_stmt|;
specifier|public
name|RetrieveSpecialRegistrationRequest
parameter_list|()
block|{
block|}
specifier|public
name|RetrieveSpecialRegistrationRequest
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|studentId
parameter_list|,
name|String
name|requestKey
parameter_list|)
block|{
name|iRequestKey
operator|=
name|requestKey
expr_stmt|;
name|iStudentId
operator|=
name|studentId
expr_stmt|;
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
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
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
name|String
name|getRequestKey
parameter_list|()
block|{
return|return
name|iRequestKey
return|;
block|}
specifier|public
name|void
name|setRequestKey
parameter_list|(
name|String
name|requestKey
parameter_list|)
block|{
name|iRequestKey
operator|=
name|requestKey
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
enum|enum
name|SpecialRegistrationStatus
implements|implements
name|IsSerializable
implements|,
name|Serializable
block|{
name|Draft
block|,
name|Pending
block|,
name|Approved
block|,
name|Rejected
block|,
name|Cancelled
block|, 		; 	}
specifier|public
specifier|static
class|class
name|RetrieveSpecialRegistrationResponse
implements|implements
name|IsSerializable
implements|,
name|Serializable
implements|,
name|Comparable
argument_list|<
name|RetrieveSpecialRegistrationResponse
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
name|ClassAssignmentInterface
name|iClassAssignment
decl_stmt|;
specifier|private
name|SpecialRegistrationStatus
name|iStatus
decl_stmt|;
specifier|private
name|Date
name|iSubmitDate
decl_stmt|;
specifier|private
name|String
name|iRequestId
decl_stmt|;
specifier|private
name|String
name|iDescription
decl_stmt|;
specifier|private
name|String
name|iNote
decl_stmt|;
specifier|private
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|iChanges
decl_stmt|;
specifier|public
name|RetrieveSpecialRegistrationResponse
parameter_list|()
block|{
block|}
specifier|public
name|boolean
name|hasClassAssignments
parameter_list|()
block|{
return|return
name|iClassAssignment
operator|!=
literal|null
return|;
block|}
specifier|public
name|ClassAssignmentInterface
name|getClassAssignments
parameter_list|()
block|{
return|return
name|iClassAssignment
return|;
block|}
specifier|public
name|void
name|setClassAssignments
parameter_list|(
name|ClassAssignmentInterface
name|assignments
parameter_list|)
block|{
name|iClassAssignment
operator|=
name|assignments
expr_stmt|;
block|}
specifier|public
name|Date
name|getSubmitDate
parameter_list|()
block|{
return|return
name|iSubmitDate
return|;
block|}
specifier|public
name|void
name|setSubmitDate
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
name|iSubmitDate
operator|=
name|date
expr_stmt|;
block|}
specifier|public
name|String
name|getRequestId
parameter_list|()
block|{
return|return
name|iRequestId
return|;
block|}
specifier|public
name|void
name|setRequestId
parameter_list|(
name|String
name|requestId
parameter_list|)
block|{
name|iRequestId
operator|=
name|requestId
expr_stmt|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|iDescription
return|;
block|}
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|iDescription
operator|=
name|description
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|SpecialRegistrationStatus
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|void
name|setStatus
parameter_list|(
name|SpecialRegistrationStatus
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasChanges
parameter_list|()
block|{
return|return
name|iChanges
operator|!=
literal|null
operator|&&
operator|!
name|iChanges
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|getChanges
parameter_list|()
block|{
return|return
name|iChanges
return|;
block|}
specifier|public
name|void
name|addChange
parameter_list|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|ca
parameter_list|)
block|{
if|if
condition|(
name|iChanges
operator|==
literal|null
condition|)
name|iChanges
operator|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|()
expr_stmt|;
name|iChanges
operator|.
name|add
argument_list|(
name|ca
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|RetrieveSpecialRegistrationResponse
name|o
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getSubmitDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getSubmitDate
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
operator|-
name|cmp
return|;
return|return
name|getRequestId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getRequestId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getRequestId
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
name|RetrieveSpecialRegistrationResponse
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getRequestId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|RetrieveSpecialRegistrationResponse
operator|)
name|o
operator|)
operator|.
name|getRequestId
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SubmitSpecialRegistrationRequest
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
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|String
name|iRequestKey
decl_stmt|;
specifier|private
name|String
name|iRequestId
decl_stmt|;
specifier|private
name|CourseRequestInterface
name|iCourses
decl_stmt|;
specifier|private
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|iClassAssignments
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|iErrors
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iNote
decl_stmt|;
specifier|public
name|SubmitSpecialRegistrationRequest
parameter_list|()
block|{
block|}
specifier|public
name|SubmitSpecialRegistrationRequest
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|studentId
parameter_list|,
name|String
name|requestKey
parameter_list|,
name|String
name|requestId
parameter_list|,
name|CourseRequestInterface
name|courses
parameter_list|,
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|assignments
parameter_list|,
name|Collection
argument_list|<
name|ErrorMessage
argument_list|>
name|errors
parameter_list|,
name|String
name|note
parameter_list|)
block|{
name|iRequestKey
operator|=
name|requestKey
expr_stmt|;
name|iRequestId
operator|=
name|requestId
expr_stmt|;
name|iStudentId
operator|=
name|studentId
expr_stmt|;
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
name|iCourses
operator|=
name|courses
expr_stmt|;
name|iClassAssignments
operator|=
name|assignments
expr_stmt|;
if|if
condition|(
name|errors
operator|!=
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|(
name|errors
argument_list|)
expr_stmt|;
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|getClassAssignments
parameter_list|()
block|{
return|return
name|iClassAssignments
return|;
block|}
specifier|public
name|void
name|setClassAssignments
parameter_list|(
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|assignments
parameter_list|)
block|{
name|iClassAssignments
operator|=
name|assignments
expr_stmt|;
block|}
specifier|public
name|CourseRequestInterface
name|getCourses
parameter_list|()
block|{
return|return
name|iCourses
return|;
block|}
specifier|public
name|void
name|setCourses
parameter_list|(
name|CourseRequestInterface
name|courses
parameter_list|)
block|{
name|iCourses
operator|=
name|courses
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
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
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
name|String
name|getRequestId
parameter_list|()
block|{
return|return
name|iRequestId
return|;
block|}
specifier|public
name|void
name|setRequestId
parameter_list|(
name|String
name|requestId
parameter_list|)
block|{
name|iRequestId
operator|=
name|requestId
expr_stmt|;
block|}
specifier|public
name|String
name|getRequestKey
parameter_list|()
block|{
return|return
name|iRequestKey
return|;
block|}
specifier|public
name|void
name|setRequestKey
parameter_list|(
name|String
name|requestKey
parameter_list|)
block|{
name|iRequestKey
operator|=
name|requestKey
expr_stmt|;
block|}
specifier|public
name|void
name|addError
parameter_list|(
name|ErrorMessage
name|error
parameter_list|)
block|{
if|if
condition|(
name|iErrors
operator|==
literal|null
condition|)
name|iErrors
operator|=
operator|new
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
argument_list|()
expr_stmt|;
name|iErrors
operator|.
name|add
argument_list|(
name|error
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasErrors
parameter_list|()
block|{
return|return
name|iErrors
operator|!=
literal|null
operator|&&
operator|!
name|iErrors
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|ErrorMessage
argument_list|>
name|getErrors
parameter_list|()
block|{
return|return
name|iErrors
return|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SubmitSpecialRegistrationResponse
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
name|String
name|iRequestId
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|boolean
name|iSuccess
decl_stmt|;
specifier|private
name|SpecialRegistrationStatus
name|iStatus
init|=
literal|null
decl_stmt|;
specifier|public
name|SubmitSpecialRegistrationResponse
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getRequestId
parameter_list|()
block|{
return|return
name|iRequestId
return|;
block|}
specifier|public
name|void
name|setRequestId
parameter_list|(
name|String
name|requestId
parameter_list|)
block|{
name|iRequestId
operator|=
name|requestId
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
name|isSuccess
parameter_list|()
block|{
return|return
name|iSuccess
return|;
block|}
specifier|public
name|boolean
name|isFailure
parameter_list|()
block|{
return|return
operator|!
name|iSuccess
return|;
block|}
specifier|public
name|void
name|setSuccess
parameter_list|(
name|boolean
name|success
parameter_list|)
block|{
name|iSuccess
operator|=
name|success
expr_stmt|;
block|}
specifier|public
name|SpecialRegistrationStatus
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|void
name|setStatus
parameter_list|(
name|SpecialRegistrationStatus
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|RetrieveAllSpecialRegistrationsRequest
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
decl_stmt|;
specifier|private
name|Long
name|iStudentId
decl_stmt|;
specifier|public
name|RetrieveAllSpecialRegistrationsRequest
parameter_list|()
block|{
block|}
specifier|public
name|RetrieveAllSpecialRegistrationsRequest
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
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
name|getStudentId
parameter_list|()
block|{
return|return
name|iStudentId
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
block|}
block|}
end_class

end_unit

