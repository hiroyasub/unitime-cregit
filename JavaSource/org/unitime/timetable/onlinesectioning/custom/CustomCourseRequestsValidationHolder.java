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
name|custom
package|;
end_package

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
name|List
import|;
end_import

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
name|updates
operator|.
name|ReloadStudent
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CustomCourseRequestsValidationHolder
block|{
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
specifier|static
name|CourseRequestsValidationProvider
name|sProvider
init|=
literal|null
decl_stmt|;
specifier|public
specifier|synchronized
specifier|static
name|CourseRequestsValidationProvider
name|getProvider
parameter_list|()
block|{
if|if
condition|(
name|sProvider
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|sProvider
operator|=
operator|(
operator|(
name|CourseRequestsValidationProvider
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|ApplicationProperty
operator|.
name|CustomizationCourseRequestsValidation
operator|.
name|value
argument_list|()
argument_list|)
operator|.
name|newInstance
argument_list|()
operator|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionCourseRequestValidationProvider
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
name|sProvider
return|;
block|}
specifier|public
specifier|synchronized
specifier|static
name|void
name|release
parameter_list|()
block|{
if|if
condition|(
name|sProvider
operator|!=
literal|null
condition|)
block|{
name|sProvider
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|sProvider
operator|=
literal|null
expr_stmt|;
block|}
block|}
specifier|public
specifier|synchronized
specifier|static
name|boolean
name|hasProvider
parameter_list|()
block|{
return|return
name|sProvider
operator|!=
literal|null
operator|||
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
specifier|public
specifier|static
class|class
name|Check
implements|implements
name|OnlineSectioningAction
argument_list|<
name|CourseRequestInterface
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
name|CourseRequestInterface
name|iRequest
decl_stmt|;
specifier|public
name|Check
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
name|CourseRequestInterface
name|getRequest
parameter_list|()
block|{
return|return
name|iRequest
return|;
block|}
annotation|@
name|Override
specifier|public
name|CourseRequestInterface
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|CourseRequestInterface
name|request
init|=
name|getRequest
argument_list|()
decl_stmt|;
if|if
condition|(
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
name|check
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|)
expr_stmt|;
return|return
name|request
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
literal|"check-overrides"
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Update
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Boolean
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
name|Collection
argument_list|<
name|Long
argument_list|>
name|iStudentIds
init|=
literal|null
decl_stmt|;
specifier|public
name|Update
name|forStudents
parameter_list|(
name|Long
modifier|...
name|studentIds
parameter_list|)
block|{
name|iStudentIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|studentIds
control|)
name|iStudentIds
operator|.
name|add
argument_list|(
name|studentId
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Update
name|forStudents
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
name|iStudentIds
operator|=
name|studentIds
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getStudentIds
parameter_list|()
block|{
return|return
name|iStudentIds
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
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
name|CustomCourseRequestsValidationHolder
operator|.
name|hasProvider
argument_list|()
condition|)
return|return
literal|false
return|;
name|List
argument_list|<
name|Long
argument_list|>
name|reloadIds
init|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
for|for
control|(
name|Long
name|studentId
range|:
name|getStudentIds
argument_list|()
control|)
block|{
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOther
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
name|studentId
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|STUDENT
argument_list|)
argument_list|)
expr_stmt|;
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
name|studentId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
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
name|addAction
argument_list|(
name|this
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
decl_stmt|;
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
name|studentId
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
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
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|STUDENT
argument_list|)
argument_list|)
expr_stmt|;
name|long
name|c0
init|=
name|OnlineSectioningHelper
operator|.
name|getCpuTime
argument_list|()
decl_stmt|;
try|try
block|{
if|if
condition|(
name|CustomCourseRequestsValidationHolder
operator|.
name|getProvider
argument_list|()
operator|.
name|updateStudent
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|action
argument_list|)
condition|)
block|{
name|reloadIds
operator|.
name|add
argument_list|(
name|studentId
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
name|TRUE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
block|}
catch|catch
parameter_list|(
name|SectioningException
name|e
parameter_list|)
block|{
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
if|if
condition|(
name|e
operator|.
name|getCause
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|action
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|FATAL
argument_list|)
operator|.
name|setText
argument_list|(
name|e
operator|.
name|getCause
argument_list|()
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|": "
operator|+
name|e
operator|.
name|getCause
argument_list|()
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|action
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|FATAL
argument_list|)
operator|.
name|setText
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
operator|==
literal|null
condition|?
literal|"null"
else|:
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|action
operator|.
name|setCpuTime
argument_list|(
name|OnlineSectioningHelper
operator|.
name|getCpuTime
argument_list|()
operator|-
name|c0
argument_list|)
expr_stmt|;
name|action
operator|.
name|setEndTime
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|rollbackTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|SectioningException
condition|)
throw|throw
operator|(
name|SectioningException
operator|)
name|e
throw|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
if|if
condition|(
operator|!
name|reloadIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|server
operator|.
name|execute
argument_list|(
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
name|reloadIds
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|!
name|reloadIds
operator|.
name|isEmpty
argument_list|()
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
literal|"update-overrides"
return|;
block|}
block|}
block|}
end_class

end_unit

