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
name|updates
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
name|StudentClassEnrollment
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
name|model
operator|.
name|XApproval
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
name|XEnrollment
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
name|XEnrollments
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
name|XOffering
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
name|XSection
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
name|ApproveEnrollmentsAction
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
name|Long
name|iOfferingId
decl_stmt|;
specifier|private
name|Collection
argument_list|<
name|Long
argument_list|>
name|iStudentIds
decl_stmt|;
specifier|private
name|Collection
argument_list|<
name|Long
argument_list|>
name|iCourseIdsCanApprove
decl_stmt|;
specifier|private
name|String
name|iApproval
decl_stmt|;
specifier|public
name|ApproveEnrollmentsAction
name|withParams
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|courseIdsCanApprove
parameter_list|,
name|String
name|approval
parameter_list|)
block|{
name|iOfferingId
operator|=
name|offeringId
expr_stmt|;
name|iStudentIds
operator|=
name|studentIds
expr_stmt|;
name|iCourseIdsCanApprove
operator|=
name|courseIdsCanApprove
expr_stmt|;
name|iApproval
operator|=
name|approval
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Long
name|getOfferingId
parameter_list|()
block|{
return|return
name|iOfferingId
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
specifier|public
name|String
name|getApproval
parameter_list|()
block|{
return|return
name|iApproval
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
name|Lock
name|lock
init|=
name|server
operator|.
name|lockOffering
argument_list|(
name|getOfferingId
argument_list|()
argument_list|,
name|getStudentIds
argument_list|()
argument_list|,
name|name
argument_list|()
argument_list|)
decl_stmt|;
try|try
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
name|getOfferingId
argument_list|()
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
name|OFFERING
argument_list|)
argument_list|)
expr_stmt|;
name|String
index|[]
name|approval
init|=
name|getApproval
argument_list|()
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
name|Date
name|approvedDate
init|=
operator|new
name|Date
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|approval
index|[
literal|0
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|XEnrollments
name|enrollments
init|=
name|server
operator|.
name|getEnrollments
argument_list|(
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
operator|||
name|enrollments
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionBadOffering
argument_list|()
argument_list|)
throw|;
for|for
control|(
name|XCourseRequest
name|request
range|:
name|enrollments
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|XEnrollment
name|enrollment
init|=
name|request
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
if|if
condition|(
name|enrollment
operator|==
literal|null
operator|||
operator|!
name|enrollment
operator|.
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
name|getOfferingId
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|enrollment
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|&&
name|iCourseIdsCanApprove
operator|.
name|contains
argument_list|(
name|enrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
block|{
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|enrollment
operator|.
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
continue|continue;
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
argument_list|)
expr_stmt|;
name|action
operator|.
name|addRequest
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|request
argument_list|)
argument_list|)
expr_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|enrl
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|enrl
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|APPROVED
argument_list|)
expr_stmt|;
for|for
control|(
name|XSection
name|assignment
range|:
name|offering
operator|.
name|getSections
argument_list|(
name|enrollment
argument_list|)
control|)
name|enrl
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|assignment
argument_list|,
name|enrollment
argument_list|)
argument_list|)
expr_stmt|;
name|action
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
name|offering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|offering
operator|.
name|getName
argument_list|()
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
name|OFFERING
argument_list|)
argument_list|)
expr_stmt|;
name|action
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
name|setName
argument_list|(
name|approval
index|[
literal|2
index|]
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|approval
index|[
literal|1
index|]
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
name|MANAGER
argument_list|)
argument_list|)
expr_stmt|;
name|action
operator|.
name|addEnrollment
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|XEnrollment
name|oldEnrollment
init|=
operator|new
name|XEnrollment
argument_list|(
name|enrollment
argument_list|)
decl_stmt|;
name|enrollment
operator|.
name|setApproval
argument_list|(
operator|new
name|XApproval
argument_list|(
name|approval
index|[
literal|1
index|]
argument_list|,
name|approvedDate
argument_list|,
name|approval
index|[
literal|2
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|XCourseRequest
name|r
init|=
name|server
operator|.
name|assign
argument_list|(
name|request
argument_list|,
name|enrollment
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|!=
literal|null
operator|&&
name|r
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
operator|(
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentClassEnrollment e where e.student.uniqueId = :studentId and e.courseOffering.instructionalOffering = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|enrollment
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|getOfferingId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|e
operator|.
name|setApprovedBy
argument_list|(
name|approval
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|e
operator|.
name|setApprovedDate
argument_list|(
name|approvedDate
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|NotifyStudentAction
operator|.
name|class
argument_list|)
operator|.
name|forStudent
argument_list|(
name|enrollment
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|oldEnrollment
argument_list|(
name|offering
argument_list|,
name|offering
operator|.
name|getCourse
argument_list|(
name|oldEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|,
name|oldEnrollment
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
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
return|return
literal|true
return|;
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
literal|"approve-enrollments"
return|;
block|}
block|}
end_class

end_unit

