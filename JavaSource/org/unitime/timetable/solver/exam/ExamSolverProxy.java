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
name|exam
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DataProperties
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
name|ui
operator|.
name|ExamAssignment
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
name|ui
operator|.
name|ExamAssignmentInfo
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
name|ui
operator|.
name|ExamConflictStatisticsInfo
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
name|ui
operator|.
name|ExamInfo
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
name|ui
operator|.
name|ExamProposedChange
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
name|ui
operator|.
name|ExamRoomInfo
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
name|ui
operator|.
name|ExamSuggestionsInfo
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|ExamSolverProxy
extends|extends
name|ExamAssignmentProxy
extends|,
name|CommonSolverInterface
block|{
specifier|public
name|String
name|getHost
parameter_list|()
function_decl|;
specifier|public
name|String
name|getUser
parameter_list|()
function_decl|;
specifier|public
name|void
name|dispose
parameter_list|()
function_decl|;
specifier|public
name|void
name|load
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
function_decl|;
specifier|public
name|void
name|reload
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
function_decl|;
specifier|public
name|Date
name|getLoadedDate
parameter_list|()
function_decl|;
specifier|public
name|void
name|save
parameter_list|()
function_decl|;
specifier|public
name|int
name|getDebugLevel
parameter_list|()
function_decl|;
specifier|public
name|void
name|setDebugLevel
parameter_list|(
name|int
name|level
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|backup
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|ownerId
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|restore
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|ownerId
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|restore
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|ownerId
parameter_list|,
name|boolean
name|removeFiles
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|getAssignedExams
parameter_list|()
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamInfo
argument_list|>
name|getUnassignedExams
parameter_list|()
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|getAssignedExams
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamInfo
argument_list|>
name|getUnassignedExams
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|getAssignedExamsOfRoom
parameter_list|(
name|Long
name|roomId
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|getAssignedExamsOfInstructor
parameter_list|(
name|Long
name|instructorId
parameter_list|)
function_decl|;
specifier|public
name|ExamAssignmentInfo
name|getAssignment
parameter_list|(
name|Long
name|examId
parameter_list|,
name|Long
name|periodId
parameter_list|,
name|Collection
argument_list|<
name|Long
argument_list|>
name|roomIds
parameter_list|)
function_decl|;
specifier|public
name|String
name|assign
parameter_list|(
name|ExamAssignment
name|assignment
parameter_list|)
function_decl|;
specifier|public
name|String
name|unassign
parameter_list|(
name|ExamInfo
name|exam
parameter_list|)
function_decl|;
specifier|public
name|ExamProposedChange
name|update
parameter_list|(
name|ExamProposedChange
name|change
parameter_list|)
function_decl|;
specifier|public
name|Vector
argument_list|<
name|ExamRoomInfo
argument_list|>
name|getRooms
parameter_list|(
name|long
name|examId
parameter_list|,
name|long
name|periodId
parameter_list|,
name|ExamProposedChange
name|change
parameter_list|,
name|int
name|minRoomSize
parameter_list|,
name|int
name|maxRoomSize
parameter_list|,
name|String
name|filter
parameter_list|,
name|boolean
name|allowConflicts
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|getPeriods
parameter_list|(
name|long
name|examId
parameter_list|,
name|ExamProposedChange
name|change
parameter_list|)
function_decl|;
specifier|public
name|ExamSuggestionsInfo
name|getSuggestions
parameter_list|(
name|long
name|examId
parameter_list|,
name|ExamProposedChange
name|change
parameter_list|,
name|String
name|filter
parameter_list|,
name|int
name|depth
parameter_list|,
name|int
name|limit
parameter_list|,
name|long
name|timeOut
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
index|[]
argument_list|>
name|getChangesToInitial
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|)
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
index|[]
argument_list|>
name|getChangesToBest
parameter_list|(
name|Long
name|subjectAreaId
parameter_list|)
function_decl|;
specifier|public
name|ExamConflictStatisticsInfo
name|getCbsInfo
parameter_list|()
function_decl|;
specifier|public
name|ExamConflictStatisticsInfo
name|getCbsInfo
parameter_list|(
name|Long
name|examId
parameter_list|)
function_decl|;
specifier|public
name|long
name|timeFromLastUsed
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isPassivated
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|activateIfNeeded
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|passivate
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|puid
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|passivateIfNeeded
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|puid
parameter_list|)
function_decl|;
specifier|public
name|Date
name|getLastUsed
parameter_list|()
function_decl|;
specifier|public
name|void
name|interrupt
parameter_list|()
function_decl|;
specifier|public
name|byte
index|[]
name|exportXml
parameter_list|()
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

