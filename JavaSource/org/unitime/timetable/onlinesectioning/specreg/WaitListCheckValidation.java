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
name|specreg
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
name|Customization
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
name|WaitListValidationProvider
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
name|WaitListCheckValidation
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
specifier|private
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
specifier|private
name|CourseRequestInterface
name|iRequest
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iSubmitIfNoConfims
init|=
literal|false
decl_stmt|;
specifier|public
name|WaitListCheckValidation
name|withRequest
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
name|WaitListCheckValidation
name|withSubmitIfNoConfirms
parameter_list|(
name|boolean
name|submitIfNoConfims
parameter_list|)
block|{
name|iSubmitIfNoConfims
operator|=
name|submitIfNoConfims
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iRequest
operator|.
name|getStudentId
argument_list|()
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
if|if
condition|(
operator|!
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
operator|||
operator|!
name|Customization
operator|.
name|WaitListValidationProvider
operator|.
name|hasProvider
argument_list|()
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNotSupportedFeature
argument_list|()
argument_list|)
throw|;
name|CheckCoursesResponse
name|response
init|=
operator|new
name|CheckCoursesResponse
argument_list|()
decl_stmt|;
name|Lock
name|lock
init|=
name|server
operator|.
name|lockStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|,
literal|null
argument_list|,
name|name
argument_list|()
argument_list|)
decl_stmt|;
try|try
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
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|WaitListValidationProvider
name|provider
init|=
name|Customization
operator|.
name|WaitListValidationProvider
operator|.
name|getProvider
argument_list|()
decl_stmt|;
name|provider
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
name|hasCreditNote
argument_list|()
condition|)
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|response
operator|.
name|getCreditNote
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
name|INFO
argument_list|)
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|hasCreditWarning
argument_list|()
condition|)
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|response
operator|.
name|getCreditWarning
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
name|response
operator|.
name|hasErrorMessage
argument_list|()
condition|)
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|response
operator|.
name|getErrorMessage
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
name|ERROR
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
name|FAILURE
argument_list|)
expr_stmt|;
if|else if
condition|(
name|response
operator|.
name|isConfirm
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
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|iSubmitIfNoConfims
operator|&&
operator|!
name|response
operator|.
name|isConfirm
argument_list|()
condition|)
block|{
name|iRequest
operator|.
name|addConfirmations
argument_list|(
name|response
operator|.
name|getMessages
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|WaitListSubmitOverrides
argument_list|()
operator|.
name|withRequest
argument_list|(
name|iRequest
argument_list|)
operator|.
name|execute
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
expr_stmt|;
block|}
return|return
name|response
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
literal|"wait-validate"
return|;
block|}
block|}
end_class

end_unit

