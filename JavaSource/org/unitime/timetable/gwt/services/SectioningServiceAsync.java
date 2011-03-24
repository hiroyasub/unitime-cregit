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
name|AsyncCallback
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|SectioningServiceAsync
block|{
name|void
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
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|CourseAssignment
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|listAcademicSessions
parameter_list|(
name|boolean
name|sectioning
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|String
index|[]
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|retrieveCourseDetails
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|course
parameter_list|,
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|listClasses
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|course
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
name|void
name|retrieveCourseOfferingId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|course
parameter_list|,
name|AsyncCallback
argument_list|<
name|Long
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
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
parameter_list|,
name|AsyncCallback
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|checkCourses
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|String
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
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
name|AsyncCallback
argument_list|<
name|Collection
argument_list|<
name|ClassAssignmentInterface
argument_list|>
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|logIn
parameter_list|(
name|String
name|userName
parameter_list|,
name|String
name|password
parameter_list|,
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|logOut
parameter_list|(
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|whoAmI
parameter_list|(
name|AsyncCallback
argument_list|<
name|String
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|lastAcademicSession
parameter_list|(
name|boolean
name|sectioning
parameter_list|,
name|AsyncCallback
argument_list|<
name|String
index|[]
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|lastRequest
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|AsyncCallback
argument_list|<
name|CourseRequestInterface
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|lastResult
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|AsyncCallback
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|saveRequest
parameter_list|(
name|CourseRequestInterface
name|request
parameter_list|,
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
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
parameter_list|,
name|AsyncCallback
argument_list|<
name|ClassAssignmentInterface
argument_list|>
name|callback
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
name|void
name|isAdmin
parameter_list|(
name|AsyncCallback
argument_list|<
name|Boolean
argument_list|>
name|isAdmin
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
block|}
end_interface

end_unit

