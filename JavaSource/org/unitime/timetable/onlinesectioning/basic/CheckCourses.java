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
name|CourseRequestInterface
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
name|CourseRequestInterface
operator|.
name|CheckCoursesResponse
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
name|CourseRequestInterface
operator|.
name|CourseMessage
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
name|CourseRequestInterface
operator|.
name|RequestedCourse
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
name|custom
operator|.
name|CustomCourseRequestsValidationHolder
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
name|match
operator|.
name|CourseMatcher
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
name|XCourseId
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
name|XCourseRequest
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
name|XRequest
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CheckCourses
implements|implements
name|OnlineSectioningAction
argument_list|<
name|CheckCoursesResponse
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
specifier|final
name|StudentSectioningMessages
name|MESSAGES
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
specifier|private
name|CourseRequestInterface
name|iRequest
decl_stmt|;
specifier|private
name|CourseMatcher
name|iMatcher
decl_stmt|;
specifier|private
name|boolean
name|iCustomValidation
init|=
literal|false
decl_stmt|;
specifier|public
name|CheckCourses
name|forRequest
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|)
block|{
name|iRequest
operator|=
name|request
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|CheckCourses
name|withMatcher
parameter_list|(
name|CourseMatcher
name|matcher
parameter_list|)
block|{
name|iMatcher
operator|=
name|matcher
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|CheckCourses
name|withCustomValidation
parameter_list|(
name|boolean
name|validation
parameter_list|)
block|{
name|iCustomValidation
operator|=
name|validation
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|CheckCoursesResponse
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
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
if|if
condition|(
name|iMatcher
operator|!=
literal|null
condition|)
name|iMatcher
operator|.
name|setServer
argument_list|(
name|server
argument_list|)
expr_stmt|;
name|CheckCoursesResponse
name|response
init|=
operator|new
name|CheckCoursesResponse
argument_list|()
decl_stmt|;
if|if
condition|(
name|iRequest
operator|.
name|getStudentId
argument_list|()
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
name|iRequest
operator|.
name|getStudentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|XStudent
name|student
init|=
operator|(
name|iRequest
operator|.
name|getStudentId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|iRequest
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
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
name|student
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|OnlineSectioningLog
operator|.
name|Request
name|r
range|:
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|iRequest
argument_list|)
control|)
name|action
operator|.
name|addRequest
argument_list|(
name|r
argument_list|)
expr_stmt|;
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|cr
range|:
name|iRequest
operator|.
name|getCourses
argument_list|()
control|)
block|{
if|if
condition|(
name|cr
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
for|for
control|(
name|RequestedCourse
name|rc
range|:
name|cr
operator|.
name|getRequestedCourse
argument_list|()
control|)
if|if
condition|(
name|rc
operator|.
name|isCourse
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|rc
argument_list|)
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|addError
argument_list|(
name|rc
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|,
literal|"NOT_FOUND"
argument_list|,
name|MESSAGES
operator|.
name|validationCourseNotExists
argument_list|(
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|validationCourseNotExists
argument_list|(
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|CourseRequestInterface
operator|.
name|Request
name|cr
range|:
name|iRequest
operator|.
name|getAlternatives
argument_list|()
control|)
block|{
if|if
condition|(
name|cr
operator|.
name|hasRequestedCourse
argument_list|()
condition|)
block|{
for|for
control|(
name|RequestedCourse
name|rc
range|:
name|cr
operator|.
name|getRequestedCourse
argument_list|()
control|)
if|if
condition|(
name|rc
operator|.
name|isCourse
argument_list|()
operator|&&
name|lookup
argument_list|(
name|server
argument_list|,
name|student
argument_list|,
name|rc
argument_list|)
operator|==
literal|null
condition|)
block|{
name|response
operator|.
name|addError
argument_list|(
name|rc
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|,
literal|"NOT_FOUND"
argument_list|,
name|MESSAGES
operator|.
name|validationCourseNotExists
argument_list|(
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setErrorMessage
argument_list|(
name|MESSAGES
operator|.
name|validationCourseNotExists
argument_list|(
name|rc
operator|.
name|getCourseName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|iCustomValidation
operator|&&
name|CustomCourseRequestsValidationHolder
operator|.
name|hasProvider
argument_list|()
condition|)
name|CustomCourseRequestsValidationHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|validate
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|iRequest
argument_list|,
name|response
argument_list|)
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|hasMessages
argument_list|()
condition|)
for|for
control|(
name|CourseMessage
name|m
range|:
name|response
operator|.
name|getMessages
argument_list|()
control|)
if|if
condition|(
name|m
operator|.
name|hasCourse
argument_list|()
condition|)
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|m
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|m
operator|.
name|isError
argument_list|()
condition|?
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|ERROR
else|:
name|m
operator|.
name|isConfirm
argument_list|()
condition|?
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|WARN
else|:
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|isError
argument_list|()
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
name|FALSE
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
name|TRUE
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
specifier|public
name|XCourseId
name|lookup
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|XStudent
name|student
parameter_list|,
name|RequestedCourse
name|course
parameter_list|)
block|{
name|XCourseId
name|c
init|=
name|server
operator|.
name|getCourse
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
operator|&&
name|iMatcher
operator|!=
literal|null
operator|&&
operator|!
name|iMatcher
operator|.
name|match
argument_list|(
name|c
argument_list|)
condition|)
block|{
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XRequest
name|r
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
if|if
condition|(
name|r
operator|instanceof
name|XCourseRequest
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|hasCourse
argument_list|(
name|c
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
return|return
name|c
return|;
comment|// already requested
block|}
block|}
return|return
literal|null
return|;
block|}
return|return
name|c
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"check-courses"
return|;
block|}
block|}
end_class

end_unit

