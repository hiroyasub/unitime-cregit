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
name|Hashtable
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
name|ClassAssignmentInterface
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
name|SpecialRegistrationInterface
operator|.
name|SpecialRegistrationEligibilityRequest
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
name|SpecialRegistrationInterface
operator|.
name|SpecialRegistrationEligibilityResponse
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
name|SpecialRegistrationEligibility
implements|implements
name|OnlineSectioningAction
argument_list|<
name|SpecialRegistrationEligibilityResponse
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
name|SpecialRegistrationEligibilityRequest
name|iRequest
decl_stmt|;
specifier|public
name|SpecialRegistrationEligibility
name|withRequest
parameter_list|(
name|SpecialRegistrationEligibilityRequest
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
name|SpecialRegistrationEligibilityRequest
name|getRequest
parameter_list|()
block|{
return|return
name|iRequest
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|getAssignment
parameter_list|()
block|{
return|return
name|iRequest
operator|.
name|getClassAssignments
argument_list|()
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
name|SpecialRegistrationEligibilityResponse
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
name|CustomSpecialRegistrationHolder
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
if|if
condition|(
name|getRequest
argument_list|()
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
name|getStudentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|requested
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|requested
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|REQUESTED
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|Builder
argument_list|>
name|options
init|=
operator|new
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|Builder
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
name|assignment
range|:
name|getAssignment
argument_list|()
control|)
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|Section
name|s
init|=
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
name|requested
operator|.
name|addSection
argument_list|(
name|s
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|assignment
operator|.
name|isFreeTime
argument_list|()
operator|&&
operator|!
name|assignment
operator|.
name|isDummy
argument_list|()
operator|&&
operator|!
name|assignment
operator|.
name|isTeachingAssignment
argument_list|()
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|Builder
name|option
init|=
name|options
operator|.
name|get
argument_list|(
name|assignment
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|option
operator|==
literal|null
condition|)
block|{
name|option
operator|=
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|newBuilder
argument_list|()
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|CourseRequestOption
operator|.
name|OptionType
operator|.
name|ORIGINAL_ENROLLMENT
argument_list|)
expr_stmt|;
name|options
operator|.
name|put
argument_list|(
name|assignment
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|option
argument_list|)
expr_stmt|;
block|}
name|option
operator|.
name|addSection
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
name|action
operator|.
name|addEnrollment
argument_list|(
name|requested
argument_list|)
expr_stmt|;
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
name|SpecialRegistrationEligibilityResponse
name|response
init|=
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
name|student
argument_list|,
name|getRequest
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|response
return|;
block|}
finally|finally
block|{
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
literal|"specreg-eligibility"
return|;
block|}
block|}
end_class

end_unit

