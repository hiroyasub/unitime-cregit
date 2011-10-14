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
name|services
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
name|PageAccessException
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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|RemoteService
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
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|RemoteServiceRelativePath
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
annotation|@
name|RemoteServiceRelativePath
argument_list|(
literal|"sectioning.gwt"
argument_list|)
specifier|public
interface|interface
name|SectioningService
extends|extends
name|RemoteService
block|{
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
name|listCourseOfferings
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|query
parameter_list|,
name|Integer
name|limit
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|String
index|[]
argument_list|>
name|listAcademicSessions
parameter_list|(
name|boolean
name|sectioning
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|retrieveCourseDetails
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|course
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|listClasses
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|course
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Long
name|retrieveCourseOfferingId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|course
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|String
argument_list|>
name|checkCourses
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|section
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Collection
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|computeSuggestions
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|,
name|int
name|selectedAssignment
parameter_list|,
name|String
name|filter
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|logIn
parameter_list|(
name|String
name|userName
parameter_list|,
name|String
name|password
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|logOut
parameter_list|()
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|whoAmI
parameter_list|()
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
index|[]
name|lastAcademicSession
parameter_list|(
name|boolean
name|sectioning
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|CourseRequestInterface
name|lastRequest
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|lastResult
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|saveRequest
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|enroll
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
name|currentAssignment
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|isAdmin
parameter_list|()
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|List
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
name|listEnrollments
parameter_list|(
name|Long
name|offeringId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|ClassAssignmentInterface
name|getEnrollment
parameter_list|(
name|Long
name|studentId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|canApprove
parameter_list|(
name|Long
name|classOrOfferingId
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|String
name|approveEnrollments
parameter_list|(
name|Long
name|classOrOfferingId
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
name|Boolean
name|rejectEnrollments
parameter_list|(
name|Long
name|classOrOfferingId
parameter_list|,
name|List
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
throws|throws
name|SectioningException
throws|,
name|PageAccessException
function_decl|;
block|}
end_interface

end_unit

