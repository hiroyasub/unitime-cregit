begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|StudentSectioningExceptions
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
name|core
operator|.
name|client
operator|.
name|GWT
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_enum
specifier|public
enum|enum
name|SectioningExceptionType
block|{
name|COURSE_NOT_EXIST
block|,
name|SESSION_NOT_EXIST
block|,
name|NO_ACADEMIC_SESSION
block|,
name|NO_SUITABLE_ACADEMIC_SESSIONS
block|,
name|NO_CLASSES_FOR_COURSE
block|,
name|SECTIONING_FAILED
block|,
name|LOGIN_TOO_MANY_ATTEMPTS
block|,
name|LOGIN_NO_USERNAME
block|,
name|LOGIN_FAILED
block|,
name|LOGIN_FAILED_UNKNOWN
block|,
name|USER_NOT_LOGGED_IN
block|,
name|LAST_ACADEMIC_SESSION_FAILED
block|,
name|NO_STUDENT
block|,
name|BAD_STUDENT_ID
block|,
name|NO_STUDENT_REQUESTS
block|,
name|NO_STUDENT_SCHEDULE
block|,
name|BAD_SESSION
block|,
name|ENROLL_NOT_AUTHENTICATED
block|,
name|ENROLL_NOT_STUDENT
block|,
name|ENROLL_NOT_AVAILABLE
block|,
name|FEATURE_NOT_SUPPORTED
block|,
name|EMPTY_COURSE_REQUEST
block|,
name|NO_SOLUTION
block|,
name|UNKNOWN
block|,
name|CUSTOM_SECTION_NAMES_FAILURE
block|,
name|CUSTOM_COURSE_DETAILS_FAILURE
block|,
name|CUSTOM_SECTION_LIMITS_FAILURE
block|,
name|NO_CUSTOM_COURSE_DETAILS
block|,
name|COURSE_LOCKED
block|;
specifier|private
specifier|static
name|StudentSectioningExceptions
name|sMessages
init|=
literal|null
decl_stmt|;
static|static
block|{
try|try
block|{
name|sMessages
operator|=
name|GWT
operator|.
name|create
argument_list|(
name|StudentSectioningExceptions
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedOperationException
name|e
parameter_list|)
block|{
block|}
block|}
specifier|public
name|String
name|message
parameter_list|(
name|String
name|problem
parameter_list|)
block|{
if|if
condition|(
name|sMessages
operator|==
literal|null
condition|)
return|return
name|name
argument_list|()
operator|+
operator|(
name|problem
operator|==
literal|null
condition|?
literal|""
else|:
literal|": "
operator|+
name|problem
operator|)
return|;
switch|switch
condition|(
name|this
condition|)
block|{
case|case
name|COURSE_NOT_EXIST
case|:
return|return
name|sMessages
operator|.
name|courseDoesNotExist
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|SESSION_NOT_EXIST
case|:
return|return
name|sMessages
operator|.
name|sessionDoesNotExist
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|NO_ACADEMIC_SESSION
case|:
return|return
name|sMessages
operator|.
name|noAcademicSession
argument_list|()
return|;
case|case
name|NO_SUITABLE_ACADEMIC_SESSIONS
case|:
return|return
name|sMessages
operator|.
name|noSuitableAcademicSessions
argument_list|()
return|;
case|case
name|NO_CLASSES_FOR_COURSE
case|:
return|return
name|sMessages
operator|.
name|noClassesForCourse
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|SECTIONING_FAILED
case|:
return|return
name|sMessages
operator|.
name|sectioningFailed
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|CUSTOM_SECTION_NAMES_FAILURE
case|:
return|return
name|sMessages
operator|.
name|customSectionNamesFailed
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|LOGIN_TOO_MANY_ATTEMPTS
case|:
return|return
name|sMessages
operator|.
name|tooManyLoginAttempts
argument_list|()
return|;
case|case
name|LOGIN_NO_USERNAME
case|:
return|return
name|sMessages
operator|.
name|loginNoUsername
argument_list|()
return|;
case|case
name|LOGIN_FAILED
case|:
return|return
name|sMessages
operator|.
name|loginFailed
argument_list|()
return|;
case|case
name|LOGIN_FAILED_UNKNOWN
case|:
return|return
name|sMessages
operator|.
name|loginFailedUnknown
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|USER_NOT_LOGGED_IN
case|:
return|return
name|sMessages
operator|.
name|userNotLoggedIn
argument_list|()
return|;
case|case
name|LAST_ACADEMIC_SESSION_FAILED
case|:
return|return
name|sMessages
operator|.
name|lastAcademicSessionFailed
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|NO_STUDENT
case|:
return|return
name|sMessages
operator|.
name|noStudent
argument_list|()
return|;
case|case
name|BAD_STUDENT_ID
case|:
return|return
name|sMessages
operator|.
name|badStudentId
argument_list|()
return|;
case|case
name|NO_STUDENT_REQUESTS
case|:
return|return
name|sMessages
operator|.
name|noRequests
argument_list|()
return|;
case|case
name|BAD_SESSION
case|:
return|return
name|sMessages
operator|.
name|badSession
argument_list|()
return|;
case|case
name|NO_STUDENT_SCHEDULE
case|:
return|return
name|sMessages
operator|.
name|noSchedule
argument_list|()
return|;
case|case
name|ENROLL_NOT_AUTHENTICATED
case|:
return|return
name|sMessages
operator|.
name|enrollNotAuthenticated
argument_list|()
return|;
case|case
name|ENROLL_NOT_STUDENT
case|:
return|return
name|sMessages
operator|.
name|enrollNotStudent
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|ENROLL_NOT_AVAILABLE
case|:
return|return
name|sMessages
operator|.
name|enrollNotAvailable
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|FEATURE_NOT_SUPPORTED
case|:
return|return
name|sMessages
operator|.
name|notSupportedFeature
argument_list|()
return|;
case|case
name|EMPTY_COURSE_REQUEST
case|:
return|return
name|sMessages
operator|.
name|noCourse
argument_list|()
return|;
case|case
name|NO_SOLUTION
case|:
return|return
name|sMessages
operator|.
name|noSolution
argument_list|()
return|;
case|case
name|CUSTOM_COURSE_DETAILS_FAILURE
case|:
return|return
name|sMessages
operator|.
name|customCourseDetailsFailed
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|NO_CUSTOM_COURSE_DETAILS
case|:
return|return
name|sMessages
operator|.
name|noCustomCourseDetails
argument_list|()
return|;
case|case
name|CUSTOM_SECTION_LIMITS_FAILURE
case|:
return|return
name|sMessages
operator|.
name|customSectionLimitsFailed
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|COURSE_LOCKED
case|:
return|return
name|sMessages
operator|.
name|courseLocked
argument_list|(
name|problem
argument_list|)
return|;
case|case
name|UNKNOWN
case|:
return|return
name|sMessages
operator|.
name|unknown
argument_list|(
name|problem
argument_list|)
return|;
default|default:
return|return
name|name
argument_list|()
operator|+
operator|(
name|problem
operator|==
literal|null
condition|?
literal|""
else|:
literal|": "
operator|+
name|problem
operator|)
return|;
block|}
block|}
block|}
end_enum

end_unit

