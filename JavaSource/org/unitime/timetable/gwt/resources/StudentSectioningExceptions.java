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
name|resources
package|;
end_package

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|Messages
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|StudentSectioningExceptions
extends|extends
name|Messages
block|{
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course {0} does not exist."
argument_list|)
name|String
name|courseDoesNotExist
parameter_list|(
name|String
name|course
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session {0} does not exist."
argument_list|)
name|String
name|sessionDoesNotExist
parameter_list|(
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session not selected."
argument_list|)
name|String
name|noAcademicSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No suitable academic sessions found."
argument_list|)
name|String
name|noSuitableAcademicSessions
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No classes found for {0}."
argument_list|)
name|String
name|noClassesForCourse
parameter_list|(
name|String
name|course
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unable to compute a schedule ({0})."
argument_list|)
name|String
name|sectioningFailed
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Too many bad attempts, login disabled."
argument_list|)
name|String
name|tooManyLoginAttempts
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"User name not provided."
argument_list|)
name|String
name|loginNoUsername
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Wrong username and/or password."
argument_list|)
name|String
name|loginFailed
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Login failed ({0})."
argument_list|)
name|String
name|loginFailedUnknown
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"User is not logged in."
argument_list|)
name|String
name|userNotLoggedIn
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unable to load section information ({0})."
argument_list|)
name|String
name|customSectionNamesFailed
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unable to retrive course details ({0})."
argument_list|)
name|String
name|customCourseDetailsFailed
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unable to retrive class details ({0})."
argument_list|)
name|String
name|customSectionLimitsFailed
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course {0} is undergoing maintenance / changes."
argument_list|)
name|String
name|courseLocked
parameter_list|(
name|String
name|course
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course detail interface not provided."
argument_list|)
name|String
name|noCustomCourseDetails
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Last academic session failed ({0})."
argument_list|)
name|String
name|lastAcademicSessionFailed
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not a student."
argument_list|)
name|String
name|noStudent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Wrong student id."
argument_list|)
name|String
name|badStudentId
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No requests stored for the student."
argument_list|)
name|String
name|noRequests
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Wrong academic session."
argument_list|)
name|String
name|badSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Your are not authenticated, please log in first."
argument_list|)
name|String
name|enrollNotAuthenticated
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Your are not registered as a student in {0}."
argument_list|)
name|String
name|enrollNotStudent
parameter_list|(
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unable to enroll into {0}, the class is no longer available."
argument_list|)
name|String
name|enrollNotAvailable
parameter_list|(
name|String
name|clazz
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"This feature is not supported in the current environment."
argument_list|)
name|String
name|notSupportedFeature
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No schedule stored for the student."
argument_list|)
name|String
name|noSchedule
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No courses provided."
argument_list|)
name|String
name|noCourse
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unable to compute a schedule (no solution found)."
argument_list|)
name|String
name|noSolution
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0}"
argument_list|)
name|String
name|unknown
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session is not available for student scheduling."
argument_list|)
name|String
name|noServerForSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Wrong class or instructional offering."
argument_list|)
name|String
name|badClassOrOffering
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Wrong instructional offering."
argument_list|)
name|String
name|badOffering
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

