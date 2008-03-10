begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|ExamRoomInfo
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
name|ifs
operator|.
name|util
operator|.
name|DataProperties
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
block|{
specifier|public
name|String
name|getHost
parameter_list|()
function_decl|;
specifier|public
name|String
name|getHostLabel
parameter_list|()
function_decl|;
specifier|public
name|void
name|dispose
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|load
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|reload
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Date
name|getLoadedDate
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|save
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|start
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|boolean
name|isRunning
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|stopSolver
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|restoreBest
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|saveBest
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|clear
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Hashtable
name|currentSolutionInfo
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Hashtable
name|bestSolutionInfo
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|boolean
name|isWorking
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|DataProperties
name|getProperties
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|setProperties
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|String
name|getNote
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|int
name|getDebugLevel
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|setDebugLevel
parameter_list|(
name|int
name|level
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Map
name|getProgress
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|,
name|String
name|fromStage
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|boolean
name|backup
parameter_list|(
name|File
name|folder
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|boolean
name|restore
parameter_list|(
name|File
name|folder
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|boolean
name|restore
parameter_list|(
name|File
name|folder
parameter_list|,
name|boolean
name|removeFiles
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|getAssignedExams
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Collection
argument_list|<
name|ExamInfo
argument_list|>
name|getUnassignedExams
parameter_list|()
throws|throws
name|Exception
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
throws|throws
name|Exception
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
throws|throws
name|Exception
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
throws|throws
name|Exception
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
throws|throws
name|Exception
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
parameter_list|)
function_decl|;
specifier|public
name|Collection
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
name|ExamAssignmentInfo
name|assignment
parameter_list|)
function_decl|;
specifier|public
name|int
name|getExamType
parameter_list|()
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
throws|throws
name|Exception
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
throws|throws
name|Exception
function_decl|;
block|}
end_interface

end_unit

