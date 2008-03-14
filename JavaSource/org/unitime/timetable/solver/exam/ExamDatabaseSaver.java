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
name|Enumeration
import|;
end_import

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
name|Hashtable
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|DepartmentalInstructor
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
name|ExamConflict
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
name|ExamPeriod
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
name|Location
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
name|dao
operator|.
name|DepartmentalInstructorDAO
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
name|ExamDAO
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
name|ExamPeriodDAO
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
name|LocationDAO
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|Exam
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
name|exam
operator|.
name|model
operator|.
name|ExamPlacement
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
name|exam
operator|.
name|model
operator|.
name|ExamRoom
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
name|solver
operator|.
name|Solver
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
name|Progress
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamDatabaseSaver
extends|extends
name|ExamSaver
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ExamDatabaseLoader
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|int
name|iExamType
decl_stmt|;
specifier|private
name|Progress
name|iProgress
init|=
literal|null
decl_stmt|;
specifier|public
name|ExamDatabaseSaver
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|super
argument_list|(
name|solver
argument_list|)
expr_stmt|;
name|iProgress
operator|=
name|Progress
operator|.
name|getInstance
argument_list|(
name|getModel
argument_list|()
argument_list|)
expr_stmt|;
name|iSessionId
operator|=
name|getModel
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SessionId"
argument_list|,
operator|(
name|Long
operator|)
literal|null
argument_list|)
expr_stmt|;
name|iExamType
operator|=
name|getModel
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyInt
argument_list|(
literal|"Exam.Type"
argument_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|.
name|sExamTypeFinal
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|()
block|{
name|iProgress
operator|.
name|setStatus
argument_list|(
literal|"Saving solution ..."
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|ExamDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|saveSolution
argument_list|(
name|hibSession
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
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
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
name|iProgress
operator|.
name|fatal
argument_list|(
literal|"Unable to save a solution, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|String
name|getExamLabel
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|exam
parameter_list|)
block|{
return|return
literal|"<A href='examDetail.do?examId="
operator|+
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
operator|+
literal|"</A>"
return|;
block|}
specifier|protected
name|void
name|saveSolution
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
comment|/*         hibSession.createQuery(                 "delete ExamConflict c where c.uniqueId in "+                 "(select c.uniqueId from Exam x inner join x.conflicts c where x.session.uniqueId=:sessionId)")             .setLong("sessionId", iSessionId)             .executeUpdate();             */
name|Collection
name|exams
init|=
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|.
name|findAll
argument_list|(
name|iSessionId
argument_list|,
name|iExamType
argument_list|)
decl_stmt|;
name|iProgress
operator|.
name|setPhase
argument_list|(
literal|"Saving assignments..."
argument_list|,
name|exams
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Hashtable
name|examTable
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|exams
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
name|iProgress
operator|.
name|incProgress
argument_list|()
expr_stmt|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|exam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|exam
operator|.
name|setAssignedPeriod
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|exam
operator|.
name|setAssignedPreference
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|exam
operator|.
name|getAssignedRooms
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|getConflicts
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
name|ExamConflict
name|conf
init|=
operator|(
name|ExamConflict
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|j
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
name|Exam
name|examVar
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|getModel
argument_list|()
operator|.
name|variables
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Exam
name|x
init|=
operator|(
name|Exam
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|x
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|examVar
operator|=
name|x
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|examVar
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|getExamLabel
argument_list|(
name|exam
argument_list|)
operator|+
literal|" was not loaded."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|examTable
operator|.
name|put
argument_list|(
name|examVar
operator|.
name|getId
argument_list|()
argument_list|,
name|exam
argument_list|)
expr_stmt|;
name|ExamPlacement
name|placement
init|=
operator|(
name|ExamPlacement
operator|)
name|examVar
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|placement
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|getExamLabel
argument_list|(
name|exam
argument_list|)
operator|+
literal|" has no assignment."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|ExamPeriod
name|period
init|=
operator|new
name|ExamPeriodDAO
argument_list|()
operator|.
name|get
argument_list|(
name|placement
operator|.
name|getPeriod
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|period
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Examination period "
operator|+
name|placement
operator|.
name|getPeriod
argument_list|()
operator|.
name|getDayStr
argument_list|()
operator|+
literal|" "
operator|+
name|placement
operator|.
name|getPeriod
argument_list|()
operator|.
name|getTimeStr
argument_list|()
operator|+
literal|" not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|exam
operator|.
name|setAssignedPeriod
argument_list|(
name|period
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|placement
operator|.
name|getRooms
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
name|ExamRoom
name|room
init|=
operator|(
name|ExamRoom
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|Location
name|location
init|=
operator|new
name|LocationDAO
argument_list|()
operator|.
name|get
argument_list|(
name|room
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Location "
operator|+
name|room
operator|.
name|getName
argument_list|()
operator|+
literal|" (id:"
operator|+
name|room
operator|.
name|getId
argument_list|()
operator|+
literal|") not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|exam
operator|.
name|getAssignedRooms
argument_list|()
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
name|exam
operator|.
name|setAssignedPreference
argument_list|(
operator|new
name|ExamAssignment
argument_list|(
name|placement
argument_list|)
operator|.
name|getAssignedPreferenceString
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|exam
argument_list|)
expr_stmt|;
block|}
name|iProgress
operator|.
name|setPhase
argument_list|(
literal|"Saving conflicts..."
argument_list|,
name|getModel
argument_list|()
operator|.
name|assignedVariables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|getModel
argument_list|()
operator|.
name|assignedVariables
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Exam
name|examVar
init|=
operator|(
name|Exam
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|iProgress
operator|.
name|incProgress
argument_list|()
expr_stmt|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|exam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|examTable
operator|.
name|get
argument_list|(
name|examVar
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|exam
operator|==
literal|null
condition|)
continue|continue;
name|ExamPlacement
name|placement
init|=
operator|(
name|ExamPlacement
operator|)
name|examVar
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
name|ExamAssignmentInfo
name|info
init|=
operator|new
name|ExamAssignmentInfo
argument_list|(
name|placement
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|info
operator|.
name|getDirectConflicts
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
name|ExamAssignmentInfo
operator|.
name|DirectConflict
name|dc
init|=
operator|(
name|ExamAssignmentInfo
operator|.
name|DirectConflict
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|examVar
operator|.
name|getId
argument_list|()
operator|<
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|.
name|longValue
argument_list|()
condition|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|otherExam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|examTable
operator|.
name|get
argument_list|(
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|otherExam
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamName
argument_list|()
operator|+
literal|" (id:"
operator|+
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|+
literal|") not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|ExamConflict
name|conf
init|=
operator|new
name|ExamConflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setConflictType
argument_list|(
name|ExamConflict
operator|.
name|sConflictTypeDirect
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setStudents
argument_list|(
name|getStudents
argument_list|(
name|hibSession
argument_list|,
name|dc
operator|.
name|getStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setNrStudents
argument_list|(
name|conf
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|exam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|otherExam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|iProgress
operator|.
name|debug
argument_list|(
literal|"Direct conflict of "
operator|+
name|dc
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" students between "
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
operator|+
literal|" and "
operator|+
name|otherExam
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|info
operator|.
name|getBackToBackConflicts
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
name|ExamAssignmentInfo
operator|.
name|BackToBackConflict
name|btb
init|=
operator|(
name|ExamAssignmentInfo
operator|.
name|BackToBackConflict
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|examVar
operator|.
name|getId
argument_list|()
operator|<
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|.
name|longValue
argument_list|()
condition|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|otherExam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|examTable
operator|.
name|get
argument_list|(
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|otherExam
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamName
argument_list|()
operator|+
literal|" (id:"
operator|+
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|+
literal|") not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|ExamConflict
name|conf
init|=
operator|new
name|ExamConflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setConflictType
argument_list|(
name|btb
operator|.
name|isDistance
argument_list|()
condition|?
name|ExamConflict
operator|.
name|sConflictTypeBackToBackDist
else|:
name|ExamConflict
operator|.
name|sConflictTypeBackToBack
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setDistance
argument_list|(
name|btb
operator|.
name|getDistance
argument_list|()
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setStudents
argument_list|(
name|getStudents
argument_list|(
name|hibSession
argument_list|,
name|btb
operator|.
name|getStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setNrStudents
argument_list|(
name|conf
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|exam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|otherExam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|iProgress
operator|.
name|debug
argument_list|(
literal|"Back-to-back conflict of "
operator|+
name|btb
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" students between "
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
operator|+
literal|" and "
operator|+
name|otherExam
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|m2d
label|:
for|for
control|(
name|Iterator
name|i
init|=
name|info
operator|.
name|getMoreThanTwoADaysConflicts
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
name|ExamAssignmentInfo
operator|.
name|MoreThanTwoADayConflict
name|m2d
init|=
operator|(
name|ExamAssignmentInfo
operator|.
name|MoreThanTwoADayConflict
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|HashSet
name|confExams
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|confExams
operator|.
name|add
argument_list|(
name|exam
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|m2d
operator|.
name|getOtherExams
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
name|ExamAssignment
name|otherExamAsg
init|=
operator|(
name|ExamAssignment
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|examVar
operator|.
name|getId
argument_list|()
operator|>=
name|otherExamAsg
operator|.
name|getExamId
argument_list|()
operator|.
name|longValue
argument_list|()
condition|)
continue|continue
name|m2d
continue|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|otherExam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|examTable
operator|.
name|get
argument_list|(
name|otherExamAsg
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|otherExam
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|otherExamAsg
operator|.
name|getExamName
argument_list|()
operator|+
literal|" (id:"
operator|+
name|otherExamAsg
operator|.
name|getExamId
argument_list|()
operator|+
literal|") not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|confExams
operator|.
name|add
argument_list|(
name|otherExam
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|confExams
operator|.
name|size
argument_list|()
operator|>=
literal|3
condition|)
block|{
name|ExamConflict
name|conf
init|=
operator|new
name|ExamConflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setConflictType
argument_list|(
name|ExamConflict
operator|.
name|sConflictTypeMoreThanTwoADay
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setStudents
argument_list|(
name|getStudents
argument_list|(
name|hibSession
argument_list|,
name|m2d
operator|.
name|getStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setNrStudents
argument_list|(
name|conf
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|conf
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|confExams
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
operator|(
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|j
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|iProgress
operator|.
name|debug
argument_list|(
literal|"More than 2 a day conflict of "
operator|+
name|m2d
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" students between "
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
operator|+
literal|" and "
operator|+
name|m2d
operator|.
name|getOtherExams
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|info
operator|.
name|getInstructorDirectConflicts
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
name|ExamAssignmentInfo
operator|.
name|DirectConflict
name|dc
init|=
operator|(
name|ExamAssignmentInfo
operator|.
name|DirectConflict
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|examVar
operator|.
name|getId
argument_list|()
operator|<
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|.
name|longValue
argument_list|()
condition|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|otherExam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|examTable
operator|.
name|get
argument_list|(
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|otherExam
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamName
argument_list|()
operator|+
literal|" (id:"
operator|+
name|dc
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|+
literal|") not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|ExamConflict
name|conf
init|=
operator|new
name|ExamConflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setConflictType
argument_list|(
name|ExamConflict
operator|.
name|sConflictTypeDirect
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setInstructors
argument_list|(
name|getInstructors
argument_list|(
name|hibSession
argument_list|,
name|dc
operator|.
name|getStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setNrInstructors
argument_list|(
name|conf
operator|.
name|getInstructors
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|exam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|otherExam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|iProgress
operator|.
name|debug
argument_list|(
literal|"Direct conflict of "
operator|+
name|dc
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" instructors between "
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
operator|+
literal|" and "
operator|+
name|otherExam
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|info
operator|.
name|getInstructorBackToBackConflicts
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
name|ExamAssignmentInfo
operator|.
name|BackToBackConflict
name|btb
init|=
operator|(
name|ExamAssignmentInfo
operator|.
name|BackToBackConflict
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|examVar
operator|.
name|getId
argument_list|()
operator|<
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|.
name|longValue
argument_list|()
condition|)
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|otherExam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|examTable
operator|.
name|get
argument_list|(
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|otherExam
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamName
argument_list|()
operator|+
literal|" (id:"
operator|+
name|btb
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getExamId
argument_list|()
operator|+
literal|") not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|ExamConflict
name|conf
init|=
operator|new
name|ExamConflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setConflictType
argument_list|(
name|btb
operator|.
name|isDistance
argument_list|()
condition|?
name|ExamConflict
operator|.
name|sConflictTypeBackToBackDist
else|:
name|ExamConflict
operator|.
name|sConflictTypeBackToBack
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setDistance
argument_list|(
name|btb
operator|.
name|getDistance
argument_list|()
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setInstructors
argument_list|(
name|getInstructors
argument_list|(
name|hibSession
argument_list|,
name|btb
operator|.
name|getStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setNrInstructors
argument_list|(
name|conf
operator|.
name|getInstructors
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|exam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|otherExam
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|iProgress
operator|.
name|debug
argument_list|(
literal|"Back-to-back conflict of "
operator|+
name|btb
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" instructors between "
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
operator|+
literal|" and "
operator|+
name|otherExam
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|m2d
label|:
for|for
control|(
name|Iterator
name|i
init|=
name|info
operator|.
name|getInstructorMoreThanTwoADaysConflicts
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
name|ExamAssignmentInfo
operator|.
name|MoreThanTwoADayConflict
name|m2d
init|=
operator|(
name|ExamAssignmentInfo
operator|.
name|MoreThanTwoADayConflict
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|HashSet
name|confExams
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|confExams
operator|.
name|add
argument_list|(
name|exam
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|m2d
operator|.
name|getOtherExams
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
name|ExamAssignment
name|otherExamAsg
init|=
operator|(
name|ExamAssignment
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|examVar
operator|.
name|getId
argument_list|()
operator|>=
name|otherExamAsg
operator|.
name|getExamId
argument_list|()
operator|.
name|longValue
argument_list|()
condition|)
continue|continue
name|m2d
continue|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|otherExam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|examTable
operator|.
name|get
argument_list|(
name|otherExamAsg
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|otherExam
operator|==
literal|null
condition|)
block|{
name|iProgress
operator|.
name|warn
argument_list|(
literal|"Exam "
operator|+
name|otherExamAsg
operator|.
name|getExamName
argument_list|()
operator|+
literal|" (id:"
operator|+
name|otherExamAsg
operator|.
name|getExamId
argument_list|()
operator|+
literal|") not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|confExams
operator|.
name|add
argument_list|(
name|otherExam
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|confExams
operator|.
name|size
argument_list|()
operator|>=
literal|3
condition|)
block|{
name|ExamConflict
name|conf
init|=
operator|new
name|ExamConflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setConflictType
argument_list|(
name|ExamConflict
operator|.
name|sConflictTypeMoreThanTwoADay
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setInstructors
argument_list|(
name|getInstructors
argument_list|(
name|hibSession
argument_list|,
name|m2d
operator|.
name|getStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setNrInstructors
argument_list|(
name|conf
operator|.
name|getInstructors
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|conf
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|confExams
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
operator|(
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|j
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getConflicts
argument_list|()
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
name|iProgress
operator|.
name|debug
argument_list|(
literal|"More than 2 a day conflict of "
operator|+
name|m2d
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" instructors between "
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
operator|+
literal|" and "
operator|+
name|m2d
operator|.
name|getOtherExams
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|exams
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
name|exam
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|exam
operator|.
name|getConflicts
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|exam
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|HashSet
name|getStudents
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Collection
name|studentIds
parameter_list|)
block|{
name|HashSet
name|students
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|studentIds
operator|==
literal|null
operator|||
name|studentIds
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|students
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|studentIds
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
name|Long
name|studentId
init|=
operator|(
name|Long
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Student
name|student
init|=
operator|new
name|StudentDAO
argument_list|()
operator|.
name|get
argument_list|(
name|studentId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
name|students
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
return|return
name|students
return|;
block|}
specifier|protected
name|HashSet
name|getInstructors
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Collection
name|instructorIds
parameter_list|)
block|{
name|HashSet
name|instructors
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|instructorIds
operator|==
literal|null
operator|||
name|instructorIds
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|instructors
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|instructorIds
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
name|Long
name|instructorId
init|=
operator|(
name|Long
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|DepartmentalInstructor
name|instructor
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
operator|.
name|get
argument_list|(
name|instructorId
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|instructor
operator|!=
literal|null
condition|)
name|instructors
operator|.
name|add
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
block|}
return|return
name|instructors
return|;
block|}
block|}
end_class

end_unit

