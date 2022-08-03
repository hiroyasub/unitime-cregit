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
operator|.
name|purdue
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
name|ApplicationProperties
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
name|OnlineSectioningInterface
operator|.
name|AdvisingStudentDetails
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
name|dao
operator|.
name|SessionDAO
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
operator|.
name|Action
operator|.
name|Builder
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
name|AdvisorCourseRequestsValidationProvider
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
name|CourseRequestsValidationProvider
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
name|DatabaseServer
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TwoPhaseCourseRequestValidationProvider
implements|implements
name|CourseRequestsValidationProvider
implements|,
name|AdvisorCourseRequestsValidationProvider
block|{
name|SimplifiedCourseRequestsValidationProvider
name|iSimplifiedValidation
decl_stmt|;
name|PurdueCourseRequestsValidationProvider
name|iFullValidation
decl_stmt|;
specifier|public
name|TwoPhaseCourseRequestValidationProvider
parameter_list|()
block|{
name|iSimplifiedValidation
operator|=
operator|new
name|SimplifiedCourseRequestsValidationProvider
argument_list|()
expr_stmt|;
name|iFullValidation
operator|=
operator|new
name|PurdueCourseRequestsValidationProvider
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isFullValidation
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|String
name|mode
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"purdue.specreg.courseReqValMode"
argument_list|,
literal|"assistant"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"simplified"
operator|.
name|equals
argument_list|(
name|mode
argument_list|)
operator|||
literal|"simple"
operator|.
name|equals
argument_list|(
name|mode
argument_list|)
condition|)
block|{
comment|// always simplified
return|return
literal|false
return|;
block|}
if|else if
condition|(
literal|"full"
operator|.
name|equals
argument_list|(
name|mode
argument_list|)
condition|)
block|{
comment|// always full
return|return
literal|true
return|;
block|}
if|else if
condition|(
literal|"assistant"
operator|.
name|equals
argument_list|(
name|mode
argument_list|)
condition|)
block|{
comment|// when online scheduling server is running
return|return
name|server
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|server
operator|instanceof
name|DatabaseServer
operator|)
return|;
block|}
if|else if
condition|(
literal|"online"
operator|.
name|equals
argument_list|(
name|mode
argument_list|)
condition|)
block|{
comment|// when online student scheduling is enabled
return|return
name|server
operator|!=
literal|null
operator|&&
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
return|;
block|}
if|else if
condition|(
literal|"published"
operator|.
name|equals
argument_list|(
name|mode
argument_list|)
condition|)
block|{
if|if
condition|(
name|server
operator|==
literal|null
operator|||
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|session
operator|!=
literal|null
operator|&&
name|session
operator|.
name|canNoRoleReportClass
argument_list|()
return|;
block|}
comment|// fallback: when online scheduling server is running
return|return
name|server
operator|!=
literal|null
operator|&&
operator|!
operator|(
name|server
operator|instanceof
name|DatabaseServer
operator|)
return|;
block|}
specifier|public
name|CourseRequestsValidationProvider
name|getCRVP
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
name|isFullValidation
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
condition|)
return|return
name|iFullValidation
return|;
else|else
return|return
name|iSimplifiedValidation
return|;
block|}
specifier|public
name|AdvisorCourseRequestsValidationProvider
name|getACRVP
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
name|isFullValidation
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
condition|)
return|return
name|iFullValidation
return|;
else|else
return|return
name|iSimplifiedValidation
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|checkEligibility
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|EligibilityCheck
name|check
parameter_list|,
name|Student
name|student
parameter_list|)
throws|throws
name|SectioningException
block|{
name|getCRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|.
name|checkEligibility
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|check
argument_list|,
name|student
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|check
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|)
throws|throws
name|SectioningException
block|{
name|getCRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
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
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|updateStudent
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Student
name|student
parameter_list|,
name|Builder
name|action
parameter_list|)
throws|throws
name|SectioningException
block|{
return|return
name|getCRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
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
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|revalidateStudent
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|Student
name|student
parameter_list|,
name|Builder
name|action
parameter_list|)
throws|throws
name|SectioningException
block|{
return|return
name|getCRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|.
name|revalidateStudent
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|action
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|validate
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|,
name|CheckCoursesResponse
name|response
parameter_list|)
throws|throws
name|SectioningException
block|{
name|getCRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|.
name|validate
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|submit
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|CourseRequestInterface
name|request
parameter_list|)
throws|throws
name|SectioningException
block|{
name|getCRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|.
name|submit
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|updateStudents
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|List
argument_list|<
name|Student
argument_list|>
name|students
parameter_list|)
throws|throws
name|SectioningException
block|{
return|return
name|getCRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|.
name|updateStudents
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|students
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|validateAdvisorRecommendations
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|AdvisingStudentDetails
name|request
parameter_list|,
name|CheckCoursesResponse
name|response
parameter_list|)
throws|throws
name|SectioningException
block|{
name|getACRVP
argument_list|(
name|server
argument_list|,
name|helper
argument_list|)
operator|.
name|validateAdvisorRecommendations
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|dispose
parameter_list|()
block|{
name|iFullValidation
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|iSimplifiedValidation
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit
