begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|ServerCallback
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
name|XCourse
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
name|NotifyStudentAction
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
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|XOffering
name|iOldOffering
decl_stmt|;
specifier|private
name|XEnrollment
name|iOldEnrollment
decl_stmt|;
specifier|private
name|XStudent
name|iOldStudent
decl_stmt|;
specifier|public
name|NotifyStudentAction
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|XOffering
name|oldOffering
parameter_list|,
name|XEnrollment
name|oldEnrollment
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
name|iOldOffering
operator|=
name|oldOffering
expr_stmt|;
name|iOldEnrollment
operator|=
name|oldEnrollment
expr_stmt|;
block|}
specifier|public
name|NotifyStudentAction
parameter_list|(
name|Long
name|studentId
parameter_list|,
name|XStudent
name|oldStudent
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
name|iOldStudent
operator|=
name|oldStudent
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
annotation|@
name|Override
specifier|public
name|Boolean
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
specifier|final
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
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
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iOldOffering
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|iOldEnrollment
operator|!=
literal|null
operator|&&
operator|!
name|iOldOffering
operator|.
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
name|iOldEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
condition|)
name|iOldOffering
operator|=
name|server
operator|.
name|getOffering
argument_list|(
name|iOldEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|message
init|=
literal|"Student "
operator|+
name|student
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|student
operator|.
name|getStudentId
argument_list|()
operator|+
literal|") changed."
decl_stmt|;
name|String
name|courseName
init|=
operator|(
name|iOldEnrollment
operator|==
literal|null
condition|?
name|iOldOffering
operator|.
name|getName
argument_list|()
else|:
name|iOldOffering
operator|.
name|getCourse
argument_list|(
name|iOldEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|.
name|getCourseName
argument_list|()
operator|)
decl_stmt|;
name|XCourseRequest
name|request
init|=
literal|null
decl_stmt|;
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
block|{
if|if
condition|(
name|r
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|r
decl_stmt|;
name|XCourseId
name|id
init|=
name|cr
operator|.
name|getCourseIdByOfferingId
argument_list|(
name|iOldOffering
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
name|courseName
operator|=
name|id
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
name|request
operator|=
name|cr
expr_stmt|;
break|break;
block|}
block|}
block|}
name|message
operator|+=
literal|"\n  Previous assignment:"
expr_stmt|;
if|if
condition|(
name|iOldEnrollment
operator|!=
literal|null
condition|)
block|{
name|message
operator|+=
literal|"\n    "
operator|+
operator|(
name|request
operator|==
literal|null
condition|?
name|iOldOffering
operator|.
name|toString
argument_list|()
else|:
name|request
operator|.
name|toString
argument_list|()
operator|)
expr_stmt|;
if|if
condition|(
name|iOldEnrollment
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
name|message
operator|+=
literal|" (approved by "
operator|+
name|iOldEnrollment
operator|.
name|getApproval
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")"
expr_stmt|;
for|for
control|(
name|XSection
name|section
range|:
name|iOldOffering
operator|.
name|getSections
argument_list|(
name|iOldEnrollment
argument_list|)
control|)
name|message
operator|+=
literal|"\n      "
operator|+
name|courseName
operator|+
literal|" "
operator|+
name|section
operator|.
name|toString
argument_list|(
name|iOldEnrollment
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|message
operator|+=
literal|"\n    "
operator|+
operator|(
name|request
operator|==
literal|null
condition|?
name|iOldOffering
operator|.
name|toString
argument_list|()
else|:
name|request
operator|.
name|toString
argument_list|()
operator|)
operator|+
literal|" NOT ASSIGNED"
expr_stmt|;
block|}
name|message
operator|+=
literal|"\n  New assignment:"
expr_stmt|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|iOldOffering
operator|.
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
name|request
operator|==
literal|null
operator|||
name|request
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
condition|)
block|{
name|message
operator|+=
literal|"\n    "
operator|+
operator|(
name|request
operator|==
literal|null
condition|?
name|iOldOffering
operator|.
name|toString
argument_list|()
else|:
name|request
operator|.
name|toString
argument_list|()
operator|)
operator|+
literal|" NOT ASSIGNED"
expr_stmt|;
block|}
else|else
block|{
name|message
operator|+=
literal|"\n    "
operator|+
name|request
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
name|message
operator|+=
literal|" (approved by "
operator|+
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")"
expr_stmt|;
for|for
control|(
name|XSection
name|section
range|:
name|offering
operator|.
name|getSections
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
argument_list|)
control|)
block|{
name|message
operator|+=
literal|"\n      "
operator|+
name|courseName
operator|+
literal|" "
operator|+
name|section
operator|.
name|toString
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|helper
operator|.
name|info
argument_list|(
name|message
argument_list|)
expr_stmt|;
if|if
condition|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.email"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
block|{
name|server
operator|.
name|execute
argument_list|(
operator|new
name|StudentEmail
argument_list|(
name|getStudentId
argument_list|()
argument_list|,
name|iOldOffering
argument_list|,
name|iOldEnrollment
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|,
operator|new
name|ServerCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|exception
parameter_list|)
block|{
name|helper
operator|.
name|error
argument_list|(
literal|"Failed to notify student: "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
else|else
block|{
name|String
name|message
init|=
literal|"Student "
operator|+
name|student
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
name|student
operator|.
name|getStudentId
argument_list|()
operator|+
literal|") changed."
decl_stmt|;
if|if
condition|(
name|iOldStudent
operator|!=
literal|null
condition|)
block|{
name|message
operator|+=
literal|"\n  Previous schedule:"
expr_stmt|;
for|for
control|(
name|XRequest
name|r
range|:
name|iOldStudent
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|message
operator|+=
literal|"\n    "
operator|+
name|r
expr_stmt|;
if|if
condition|(
name|r
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|r
decl_stmt|;
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
condition|)
name|message
operator|+=
literal|" NOT ASSIGNED"
expr_stmt|;
else|else
block|{
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
name|message
operator|+=
literal|" (approved by "
operator|+
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")"
expr_stmt|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|!=
literal|null
condition|)
block|{
name|XCourse
name|course
init|=
name|offering
operator|.
name|getCourse
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XSection
name|section
range|:
name|offering
operator|.
name|getSections
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
argument_list|)
control|)
name|message
operator|+=
literal|"\n      "
operator|+
operator|(
name|course
operator|==
literal|null
condition|?
name|offering
operator|.
name|getName
argument_list|()
else|:
name|course
operator|.
name|getCourseName
argument_list|()
operator|)
operator|+
literal|" "
operator|+
name|section
operator|.
name|toString
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
name|message
operator|+=
literal|"\n  New schedule:"
expr_stmt|;
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
block|{
name|message
operator|+=
literal|"\n    "
operator|+
name|r
expr_stmt|;
if|if
condition|(
name|r
operator|instanceof
name|XCourseRequest
condition|)
block|{
name|XCourseRequest
name|cr
init|=
operator|(
name|XCourseRequest
operator|)
name|r
decl_stmt|;
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
condition|)
name|message
operator|+=
literal|" NOT ASSIGNED"
expr_stmt|;
else|else
block|{
if|if
condition|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|!=
literal|null
condition|)
name|message
operator|+=
literal|" (approved by "
operator|+
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getApproval
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")"
expr_stmt|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|!=
literal|null
condition|)
block|{
name|XCourse
name|course
init|=
name|offering
operator|.
name|getCourse
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|XSection
name|section
range|:
name|offering
operator|.
name|getSections
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
argument_list|)
control|)
name|message
operator|+=
literal|"\n      "
operator|+
operator|(
name|course
operator|==
literal|null
condition|?
name|offering
operator|.
name|getName
argument_list|()
else|:
name|course
operator|.
name|getCourseName
argument_list|()
operator|)
operator|+
literal|" "
operator|+
name|section
operator|.
name|toString
argument_list|(
name|cr
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|helper
operator|.
name|info
argument_list|(
name|message
argument_list|)
expr_stmt|;
if|if
condition|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
operator|&&
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.enrollment.email"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
block|{
name|server
operator|.
name|execute
argument_list|(
operator|new
name|StudentEmail
argument_list|(
name|getStudentId
argument_list|()
argument_list|,
name|iOldStudent
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|,
operator|new
name|ServerCallback
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|exception
parameter_list|)
block|{
name|helper
operator|.
name|error
argument_list|(
literal|"Failed to notify student: "
operator|+
name|exception
operator|.
name|getMessage
argument_list|()
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|Boolean
name|result
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
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
literal|"notify"
return|;
block|}
block|}
end_class

end_unit

