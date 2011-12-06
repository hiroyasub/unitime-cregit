begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Assignment
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Config
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Course
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|CourseRequest
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Enrollment
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Offering
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

begin_class
specifier|public
class|class
name|RejectEnrollmentsAction
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
name|String
name|iApproval
decl_stmt|;
specifier|public
name|RejectEnrollmentsAction
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
name|iApproval
operator|=
name|approval
expr_stmt|;
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
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
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
literal|false
argument_list|)
decl_stmt|;
try|try
block|{
name|Offering
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
if|if
condition|(
name|offering
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
name|Config
name|config
range|:
name|offering
operator|.
name|getConfigs
argument_list|()
control|)
for|for
control|(
name|Enrollment
name|enrollment
range|:
operator|new
name|ArrayList
argument_list|<
name|Enrollment
argument_list|>
argument_list|(
name|config
operator|.
name|getEnrollments
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
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
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalId
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
name|enrollment
operator|.
name|getRequest
argument_list|()
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
name|REJECTED
argument_list|)
expr_stmt|;
for|for
control|(
name|Assignment
name|assignment
range|:
name|enrollment
operator|.
name|getAssignments
argument_list|()
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
name|getId
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|CourseRequest
name|request
init|=
literal|null
decl_stmt|;
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
name|getStudent
argument_list|()
operator|.
name|getId
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
if|if
condition|(
name|request
operator|==
literal|null
condition|)
name|request
operator|=
name|e
operator|.
name|getCourseRequest
argument_list|()
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|!=
literal|null
condition|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|request
operator|.
name|getCourseDemand
argument_list|()
argument_list|)
expr_stmt|;
name|CourseRequest
name|cr
init|=
operator|(
name|CourseRequest
operator|)
name|enrollment
operator|.
name|getRequest
argument_list|()
decl_stmt|;
name|enrollment
operator|.
name|getRequest
argument_list|()
operator|.
name|unassign
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|Course
name|course
range|:
name|cr
operator|.
name|getCourses
argument_list|()
control|)
name|course
operator|.
name|getRequests
argument_list|()
operator|.
name|remove
argument_list|(
name|cr
argument_list|)
expr_stmt|;
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getRequests
argument_list|()
operator|.
name|remove
argument_list|(
name|cr
argument_list|)
expr_stmt|;
name|cr
operator|.
name|setInitialAssignment
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|server
operator|.
name|notifyStudentChanged
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|cr
argument_list|,
name|enrollment
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
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
comment|/* 			server.execute(new UpdateEnrollmentCountsAction(getOfferingId()), helper.getUser(), new OnlineSectioningServer.ServerCallback<Boolean>() { 				@Override 				public void onFailure(Throwable exception) { 				} 				@Override 				public void onSuccess(Boolean result) { 				} 			}); 			*/
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
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"reject-enrollments"
return|;
block|}
block|}
end_class

end_unit

