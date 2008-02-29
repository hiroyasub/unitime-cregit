begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|interactive
operator|.
name|ClassAssignmentDetails
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
name|interactive
operator|.
name|Hint
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
name|interactive
operator|.
name|Suggestions
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
name|interactive
operator|.
name|SuggestionsModel
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
name|ui
operator|.
name|AssignmentPreferenceInfo
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
name|ui
operator|.
name|ConflictStatisticsInfo
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
name|ui
operator|.
name|DeptBalancingReport
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
name|ui
operator|.
name|DiscouragedInstructorBtbReport
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
name|ui
operator|.
name|PerturbationReport
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
name|ui
operator|.
name|PropertiesInfo
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
name|ui
operator|.
name|RoomReport
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
name|ui
operator|.
name|SameSubpartBalancingReport
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
name|ui
operator|.
name|SolverUnassignedClassesModel
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
name|ui
operator|.
name|StudentConflictsReport
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
name|ui
operator|.
name|ViolatedDistrPreferencesReport
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
name|CSVFile
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
name|SolverProxy
extends|extends
name|ClassAssignmentProxy
block|{
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
parameter_list|(
name|boolean
name|createNewSolution
parameter_list|,
name|boolean
name|commitSolution
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|finalSectioning
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
name|SolverUnassignedClassesModel
name|getUnassignedClassesModel
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Vector
name|getTimetableGridTables
parameter_list|(
name|String
name|findString
parameter_list|,
name|int
name|resourceType
parameter_list|,
name|int
name|startDay
parameter_list|,
name|int
name|bgMode
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|ClassAssignmentDetails
name|getClassAssignmentDetails
parameter_list|(
name|Long
name|classId
parameter_list|,
name|boolean
name|includeConstraints
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Suggestions
name|getSuggestions
parameter_list|(
name|SuggestionsModel
name|model
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|void
name|assign
parameter_list|(
name|Collection
name|hints
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Hashtable
name|conflictInfo
parameter_list|(
name|Collection
name|hints
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|PropertiesInfo
name|getGlobalInfo
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|ConflictStatisticsInfo
name|getCbsInfo
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|ConflictStatisticsInfo
name|getCbsInfo
parameter_list|(
name|Long
name|classId
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|AssignmentPreferenceInfo
name|getInfo
parameter_list|(
name|Hint
name|hint
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|String
name|getNotValidReason
parameter_list|(
name|Hint
name|hint
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Vector
name|getAssignmentRecords
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Vector
name|getChangesToInitial
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Vector
name|getChangesToBest
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Vector
name|getChangesToSolution
parameter_list|(
name|Long
name|solutionId
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Vector
name|getAssignedClasses
parameter_list|()
throws|throws
name|Exception
function_decl|;
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
name|RoomReport
name|getRoomReport
parameter_list|(
name|int
name|startDay
parameter_list|,
name|int
name|endDay
parameter_list|,
name|int
name|nrWeeks
parameter_list|,
name|Integer
name|roomType
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|DeptBalancingReport
name|getDeptBalancingReport
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|ViolatedDistrPreferencesReport
name|getViolatedDistrPreferencesReport
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|DiscouragedInstructorBtbReport
name|getDiscouragedInstructorBtbReport
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|StudentConflictsReport
name|getStudentConflictsReport
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|SameSubpartBalancingReport
name|getSameSubpartBalancingReport
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|PerturbationReport
name|getPerturbationReport
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|CSVFile
name|export
parameter_list|()
throws|throws
name|Exception
function_decl|;
specifier|public
name|Set
name|getDepartmentIds
parameter_list|()
throws|throws
name|Exception
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
name|Hashtable
name|getAssignmentTable2
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Hashtable
name|getAssignmentInfoTable2
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|byte
index|[]
name|exportXml
parameter_list|()
throws|throws
name|Exception
function_decl|;
block|}
end_interface

end_unit

