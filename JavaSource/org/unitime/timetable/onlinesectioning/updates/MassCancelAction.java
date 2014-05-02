begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|ClassWaitList
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
name|CourseDemand
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
name|CourseRequest
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
name|model
operator|.
name|StudentSectioningStatus
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
name|solver
operator|.
name|SectioningRequest
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
name|MassCancelAction
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
name|List
argument_list|<
name|Long
argument_list|>
name|iStudentIds
decl_stmt|;
specifier|private
name|StudentSectioningStatus
name|iStatus
decl_stmt|;
specifier|private
name|boolean
name|iEmail
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iSubject
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|String
name|iCC
decl_stmt|;
specifier|public
name|MassCancelAction
name|forStudents
parameter_list|(
name|List
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
name|MassCancelAction
name|withStatus
parameter_list|(
name|StudentSectioningStatus
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|MassCancelAction
name|withEmail
parameter_list|(
name|String
name|subject
parameter_list|,
name|String
name|message
parameter_list|,
name|String
name|cc
parameter_list|)
block|{
name|iEmail
operator|=
literal|true
expr_stmt|;
name|iSubject
operator|=
name|subject
expr_stmt|;
name|iMessage
operator|=
name|message
expr_stmt|;
name|iCC
operator|=
name|cc
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|List
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
name|StudentSectioningStatus
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
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
name|String
name|getCC
parameter_list|()
block|{
return|return
name|iCC
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
name|Exception
name|caughtException
init|=
literal|null
decl_stmt|;
name|Set
argument_list|<
name|Long
argument_list|>
name|offeringsToCheck
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|OnlineSectioningServer
operator|.
name|ServerCallback
argument_list|<
name|Boolean
argument_list|>
name|emailSent
init|=
operator|new
name|OnlineSectioningServer
operator|.
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
literal|"Student email failed: "
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
decl_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|getStudentIds
argument_list|()
control|)
block|{
name|Lock
name|lock
init|=
name|server
operator|.
name|lockStudent
argument_list|(
name|studentId
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
decl_stmt|;
try|try
block|{
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
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
name|student
operator|.
name|getUniqueId
argument_list|()
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
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|i
init|=
name|student
operator|.
name|getClassEnrollments
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|StudentClassEnrollment
name|enrl
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|enrl
operator|.
name|getClazz
argument_list|()
operator|.
name|getStudentEnrollments
argument_list|()
operator|.
name|remove
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
comment|/* 							if (enrl.getCourseRequest() != null) 								enrl.getCourseRequest().getClassEnrollments().remove(enrl); 								*/
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|enrl
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
argument_list|<
name|CourseDemand
argument_list|>
name|i
init|=
name|student
operator|.
name|getCourseDemands
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|CourseDemand
name|cd
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|cd
operator|.
name|getFreeTime
argument_list|()
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
name|cd
operator|.
name|getFreeTime
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|CourseRequest
argument_list|>
name|j
init|=
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|CourseRequest
name|cr
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|ClassWaitList
argument_list|>
name|k
init|=
name|cr
operator|.
name|getClassWaitLists
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|k
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|k
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|cr
argument_list|)
expr_stmt|;
name|j
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|cd
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|student
operator|.
name|setSectioningStatus
argument_list|(
name|getStatus
argument_list|()
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
name|XStudent
name|oldStudent
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
name|XStudent
name|newStudent
init|=
literal|null
decl_stmt|;
try|try
block|{
name|newStudent
operator|=
name|ReloadAllData
operator|.
name|loadStudent
argument_list|(
name|student
argument_list|,
literal|null
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
expr_stmt|;
name|server
operator|.
name|update
argument_list|(
name|newStudent
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|instanceof
name|RuntimeException
condition|)
throw|throw
operator|(
name|RuntimeException
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
name|oldStudent
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|XRequest
name|oldRequest
range|:
name|oldStudent
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|XEnrollment
name|oldEnrollment
init|=
operator|(
name|oldRequest
operator|instanceof
name|XCourseRequest
condition|?
operator|(
operator|(
name|XCourseRequest
operator|)
name|oldRequest
operator|)
operator|.
name|getEnrollment
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|oldEnrollment
operator|==
literal|null
condition|)
continue|continue;
comment|// free time or not assigned
name|offeringsToCheck
operator|.
name|add
argument_list|(
name|oldEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
expr_stmt|;
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|oldEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|EnrollStudent
operator|.
name|updateSpace
argument_list|(
name|server
argument_list|,
literal|null
argument_list|,
name|oldEnrollment
operator|==
literal|null
condition|?
literal|null
else|:
name|SectioningRequest
operator|.
name|convert
argument_list|(
name|oldStudent
argument_list|,
operator|(
name|XCourseRequest
operator|)
name|oldRequest
argument_list|,
name|server
argument_list|,
name|offering
argument_list|,
name|oldEnrollment
argument_list|)
argument_list|,
name|offering
argument_list|)
expr_stmt|;
block|}
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|enrollment
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
decl_stmt|;
name|enrollment
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|STORED
argument_list|)
expr_stmt|;
for|for
control|(
name|XRequest
name|oldRequest
range|:
name|oldStudent
operator|.
name|getRequests
argument_list|()
control|)
block|{
name|XEnrollment
name|oldEnrollment
init|=
operator|(
name|oldRequest
operator|instanceof
name|XCourseRequest
condition|?
operator|(
operator|(
name|XCourseRequest
operator|)
name|oldRequest
operator|)
operator|.
name|getEnrollment
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|oldEnrollment
operator|!=
literal|null
condition|)
for|for
control|(
name|XSection
name|section
range|:
name|server
operator|.
name|getOffering
argument_list|(
name|oldEnrollment
operator|.
name|getOfferingId
argument_list|()
argument_list|)
operator|.
name|getSections
argument_list|(
name|oldEnrollment
argument_list|)
control|)
name|enrollment
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|section
argument_list|,
name|oldEnrollment
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|addEnrollment
argument_list|(
name|enrollment
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iEmail
operator|&&
name|ApplicationProperty
operator|.
name|OnlineSchedulingEmailConfirmation
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|StudentEmail
name|email
init|=
name|server
operator|.
name|createAction
argument_list|(
name|StudentEmail
operator|.
name|class
argument_list|)
operator|.
name|forStudent
argument_list|(
name|studentId
argument_list|)
operator|.
name|oldStudent
argument_list|(
name|oldStudent
argument_list|)
decl_stmt|;
name|email
operator|.
name|setCC
argument_list|(
name|getCC
argument_list|()
argument_list|)
expr_stmt|;
name|email
operator|.
name|setEmailSubject
argument_list|(
name|getSubject
argument_list|()
operator|==
literal|null
operator|||
name|getSubject
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|MSG
operator|.
name|defaulSubjectMassCancel
argument_list|()
else|:
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|email
operator|.
name|setMessage
argument_list|(
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|execute
argument_list|(
name|email
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|,
name|emailSent
argument_list|)
expr_stmt|;
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
name|caughtException
operator|=
name|e
expr_stmt|;
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
name|OnlineSectioningServer
operator|.
name|ServerCallback
argument_list|<
name|Boolean
argument_list|>
name|offeringChecked
init|=
operator|new
name|OnlineSectioningServer
operator|.
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
literal|"Offering check failed: "
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
decl_stmt|;
for|for
control|(
name|Long
name|offeringId
range|:
name|offeringsToCheck
control|)
block|{
name|server
operator|.
name|persistExpectedSpaces
argument_list|(
name|offeringId
argument_list|)
expr_stmt|;
name|server
operator|.
name|execute
argument_list|(
name|server
operator|.
name|createAction
argument_list|(
name|CheckOfferingAction
operator|.
name|class
argument_list|)
operator|.
name|forOfferings
argument_list|(
name|offeringId
argument_list|)
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|,
name|offeringChecked
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|caughtException
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|caughtException
operator|instanceof
name|SectioningException
condition|)
throw|throw
operator|(
name|SectioningException
operator|)
name|caughtException
throw|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|caughtException
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|caughtException
argument_list|)
throw|;
block|}
return|return
literal|true
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
literal|"mass-cancel"
return|;
block|}
block|}
end_class

end_unit

