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
name|iRequestId
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
name|requestId
parameter_list|)
block|{
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
block|}
specifier|public
specifier|static
class|class
name|RetrieveSpecialRegistrationResponse
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
name|ClassAssignmentInterface
name|iClassAssignment
decl_stmt|;
specifier|private
name|boolean
name|iCanSubmit
decl_stmt|;
specifier|private
name|boolean
name|iCanEnroll
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
name|isCanEnroll
parameter_list|()
block|{
return|return
name|iCanEnroll
return|;
block|}
specifier|public
name|void
name|setCanEnroll
parameter_list|(
name|boolean
name|canEnroll
parameter_list|)
block|{
name|iCanEnroll
operator|=
name|canEnroll
expr_stmt|;
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
parameter_list|)
block|{
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
name|iCanSubmit
decl_stmt|;
specifier|private
name|boolean
name|iCanEnroll
decl_stmt|;
specifier|private
name|boolean
name|iSuccess
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
name|isCanEnroll
parameter_list|()
block|{
return|return
name|iCanEnroll
return|;
block|}
specifier|public
name|void
name|setCanEnroll
parameter_list|(
name|boolean
name|canEnroll
parameter_list|)
block|{
name|iCanEnroll
operator|=
name|canEnroll
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

