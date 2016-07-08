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
name|solver
operator|.
name|instructor
package|;
end_package

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
name|InstructorInterface
operator|.
name|AssignmentChangesRequest
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
name|InstructorInterface
operator|.
name|AssignmentChangesResponse
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
name|InstructorInterface
operator|.
name|AssignmentInfo
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
name|InstructorInterface
operator|.
name|ComputeSuggestionsRequest
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
name|InstructorInterface
operator|.
name|InstructorInfo
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
name|InstructorInterface
operator|.
name|SuggestionsResponse
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
name|InstructorInterface
operator|.
name|TeachingRequestInfo
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
name|solver
operator|.
name|CommonSolverInterface
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|InstructorSchedulingProxy
extends|extends
name|CommonSolverInterface
block|{
specifier|public
name|List
argument_list|<
name|TeachingRequestInfo
argument_list|>
name|getTeachingRequests
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|,
name|boolean
name|assigned
parameter_list|)
function_decl|;
specifier|public
name|List
argument_list|<
name|InstructorInfo
argument_list|>
name|getInstructors
parameter_list|(
name|Long
name|departmentId
parameter_list|)
function_decl|;
specifier|public
name|TeachingRequestInfo
name|getTeachingRequestInfo
parameter_list|(
name|Long
name|requestId
parameter_list|)
function_decl|;
specifier|public
name|InstructorInfo
name|getInstructorInfo
parameter_list|(
name|Long
name|instructorId
parameter_list|)
function_decl|;
specifier|public
name|void
name|assign
parameter_list|(
name|List
argument_list|<
name|AssignmentInfo
argument_list|>
name|assignments
parameter_list|)
function_decl|;
specifier|public
name|SuggestionsResponse
name|computeSuggestions
parameter_list|(
name|ComputeSuggestionsRequest
name|request
parameter_list|)
function_decl|;
specifier|public
name|AssignmentChangesResponse
name|getAssignmentChanges
parameter_list|(
name|AssignmentChangesRequest
name|request
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

