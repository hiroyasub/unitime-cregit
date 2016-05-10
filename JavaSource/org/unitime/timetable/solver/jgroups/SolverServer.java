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
name|jgroups
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
name|jgroups
operator|.
name|Address
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
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
name|solver
operator|.
name|SolverProxy
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
name|exam
operator|.
name|ExamSolverProxy
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
name|instructor
operator|.
name|InstructorSchedulingProxy
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
name|studentsct
operator|.
name|StudentSolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|SolverServer
block|{
specifier|public
specifier|static
specifier|final
name|short
name|SCOPE_SERVER
init|=
literal|0
decl_stmt|,
name|SCOPE_COURSE
init|=
literal|1
decl_stmt|,
name|SCOPE_EXAM
init|=
literal|2
decl_stmt|,
name|SCOPE_STUDENT
init|=
literal|3
decl_stmt|,
name|SCOPE_AVAILABILITY
init|=
literal|4
decl_stmt|,
name|SCOPE_ONLINE
init|=
literal|5
decl_stmt|,
name|SCOPE_INSTRUCTOR
init|=
literal|6
decl_stmt|;
specifier|public
name|void
name|start
parameter_list|()
function_decl|;
specifier|public
name|void
name|stop
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isLocal
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isCoordinator
parameter_list|()
function_decl|;
specifier|public
name|Address
name|getAddress
parameter_list|()
function_decl|;
specifier|public
name|Address
name|getLocalAddress
parameter_list|()
function_decl|;
specifier|public
name|String
name|getHost
parameter_list|()
function_decl|;
specifier|public
name|Date
name|getStartTime
parameter_list|()
function_decl|;
specifier|public
name|int
name|getUsage
parameter_list|()
function_decl|;
specifier|public
name|String
name|getVersion
parameter_list|()
function_decl|;
specifier|public
name|void
name|setUsageBase
parameter_list|(
name|int
name|usage
parameter_list|)
function_decl|;
specifier|public
name|long
name|getAvailableMemory
parameter_list|()
function_decl|;
specifier|public
name|int
name|getAvailableProcessors
parameter_list|()
function_decl|;
specifier|public
name|long
name|getMemoryLimit
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isActive
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isAvailable
parameter_list|()
function_decl|;
specifier|public
name|void
name|shutdown
parameter_list|()
function_decl|;
specifier|public
name|SolverContainer
argument_list|<
name|SolverProxy
argument_list|>
name|getCourseSolverContainer
parameter_list|()
function_decl|;
specifier|public
name|SolverContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
name|getExamSolverContainer
parameter_list|()
function_decl|;
specifier|public
name|SolverContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
name|getStudentSolverContainer
parameter_list|()
function_decl|;
specifier|public
name|SolverContainer
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
name|getInstructorSchedulingContainer
parameter_list|()
function_decl|;
specifier|public
name|SolverContainer
argument_list|<
name|OnlineSectioningServer
argument_list|>
name|getOnlineStudentSchedulingContainer
parameter_list|()
function_decl|;
specifier|public
name|RoomAvailabilityInterface
name|getRoomAvailability
parameter_list|()
function_decl|;
specifier|public
name|void
name|refreshCourseSolution
parameter_list|(
name|Long
modifier|...
name|solutionId
parameter_list|)
function_decl|;
specifier|public
name|void
name|refreshExamSolution
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|examTypeId
parameter_list|)
function_decl|;
specifier|public
name|void
name|refreshInstructorSolution
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|solverGroupIds
parameter_list|)
function_decl|;
specifier|public
name|void
name|setApplicationProperty
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
function_decl|;
specifier|public
name|void
name|setLoggingLevel
parameter_list|(
name|String
name|name
parameter_list|,
name|Integer
name|level
parameter_list|)
function_decl|;
specifier|public
name|void
name|reset
parameter_list|()
function_decl|;
specifier|public
name|List
argument_list|<
name|SolverServer
argument_list|>
name|getServers
parameter_list|(
name|boolean
name|onlyAvailable
parameter_list|)
function_decl|;
specifier|public
name|SolverServer
name|crateServerProxy
parameter_list|(
name|Address
name|address
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

