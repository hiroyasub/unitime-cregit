begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|PatternSyntaxException
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
name|ExamModel
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
name|ExamPeriodPlacement
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
name|ExamRoomPlacement
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
name|ExamRoomSharing
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
name|ExamStudent
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
name|ExamProposedChange
import|;
end_import

begin_class
specifier|public
class|class
name|ExamSuggestions
block|{
specifier|private
name|ExamSolver
name|iSolver
decl_stmt|;
specifier|private
name|ExamModel
name|iModel
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|iInitialAssignment
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Exam
argument_list|,
name|ExamAssignment
argument_list|>
name|iInitialInfo
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|Exam
argument_list|>
name|iInitialUnassignment
decl_stmt|;
specifier|private
name|TreeSet
argument_list|<
name|ExamProposedChange
argument_list|>
name|iSuggestions
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|Exam
argument_list|>
name|iResolvedExams
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|iConflictsToResolve
decl_stmt|;
specifier|private
name|Exam
name|iExam
decl_stmt|;
specifier|private
name|int
name|iDepth
init|=
literal|2
decl_stmt|;
specifier|private
name|int
name|iLimit
init|=
literal|20
decl_stmt|;
specifier|private
name|int
name|iNrSolutions
init|=
literal|0
decl_stmt|,
name|iNrCombinationsConsidered
init|=
literal|0
decl_stmt|;
specifier|private
name|long
name|iTimeOut
init|=
literal|5000
decl_stmt|;
specifier|private
name|long
name|iStartTime
init|=
literal|0
decl_stmt|;
specifier|private
name|boolean
name|iTimeoutReached
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|public
name|ExamSuggestions
parameter_list|(
name|ExamSolver
name|solver
parameter_list|)
block|{
name|iSolver
operator|=
name|solver
expr_stmt|;
name|iModel
operator|=
operator|(
name|ExamModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
expr_stmt|;
name|iInitialAssignment
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|iInitialUnassignment
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|iInitialInfo
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
for|for
control|(
name|Exam
name|exam
range|:
name|iModel
operator|.
name|variables
argument_list|()
control|)
block|{
name|ExamPlacement
name|placement
init|=
name|exam
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
name|iInitialUnassignment
operator|.
name|add
argument_list|(
name|exam
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iInitialAssignment
operator|.
name|put
argument_list|(
name|exam
argument_list|,
name|placement
argument_list|)
expr_stmt|;
name|iInitialInfo
operator|.
name|put
argument_list|(
name|exam
argument_list|,
operator|new
name|ExamAssignment
argument_list|(
name|exam
argument_list|,
name|placement
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|int
name|getDepth
parameter_list|()
block|{
return|return
name|iDepth
return|;
block|}
specifier|public
name|void
name|setDepth
parameter_list|(
name|int
name|depth
parameter_list|)
block|{
name|iDepth
operator|=
name|depth
expr_stmt|;
block|}
specifier|public
name|int
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|int
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|long
name|getTimeOut
parameter_list|()
block|{
return|return
name|iTimeOut
return|;
block|}
specifier|public
name|void
name|setTimeOut
parameter_list|(
name|long
name|timeOut
parameter_list|)
block|{
name|iTimeOut
operator|=
name|timeOut
expr_stmt|;
block|}
specifier|public
name|String
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
specifier|public
name|int
name|getNrSolutions
parameter_list|()
block|{
return|return
name|iNrSolutions
return|;
block|}
specifier|public
name|int
name|getNrCombinationsConsidered
parameter_list|()
block|{
return|return
name|iNrCombinationsConsidered
return|;
block|}
specifier|public
name|boolean
name|wasTimeoutReached
parameter_list|()
block|{
return|return
name|iTimeoutReached
return|;
block|}
specifier|public
name|boolean
name|match
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|iFilter
operator|==
literal|null
operator|||
name|iFilter
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|true
return|;
name|String
name|n
init|=
name|name
operator|.
name|toUpperCase
argument_list|()
decl_stmt|;
name|StringTokenizer
name|stk1
init|=
operator|new
name|StringTokenizer
argument_list|(
name|iFilter
operator|.
name|toUpperCase
argument_list|()
argument_list|,
literal|";"
argument_list|)
decl_stmt|;
while|while
condition|(
name|stk1
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|StringTokenizer
name|stk2
init|=
operator|new
name|StringTokenizer
argument_list|(
name|stk1
operator|.
name|nextToken
argument_list|()
argument_list|,
literal|" ,"
argument_list|)
decl_stmt|;
name|boolean
name|match
init|=
literal|true
decl_stmt|;
while|while
condition|(
name|match
operator|&&
name|stk2
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|token
init|=
name|stk2
operator|.
name|nextToken
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|token
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
continue|continue;
if|if
condition|(
name|token
operator|.
name|indexOf
argument_list|(
literal|'*'
argument_list|)
operator|>=
literal|0
operator|||
name|token
operator|.
name|indexOf
argument_list|(
literal|'?'
argument_list|)
operator|>=
literal|0
condition|)
block|{
try|try
block|{
name|String
name|tokenRegExp
init|=
literal|"\\s+"
operator|+
name|token
operator|.
name|replaceAll
argument_list|(
literal|"\\."
argument_list|,
literal|"\\."
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\?"
argument_list|,
literal|".+"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\*"
argument_list|,
literal|".*"
argument_list|)
operator|+
literal|"\\s"
decl_stmt|;
if|if
condition|(
operator|!
name|Pattern
operator|.
name|compile
argument_list|(
name|tokenRegExp
argument_list|)
operator|.
name|matcher
argument_list|(
literal|" "
operator|+
name|n
operator|+
literal|" "
argument_list|)
operator|.
name|find
argument_list|()
condition|)
name|match
operator|=
literal|false
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PatternSyntaxException
name|e
parameter_list|)
block|{
name|match
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|n
operator|.
name|indexOf
argument_list|(
name|token
argument_list|)
operator|<
literal|0
condition|)
name|match
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|match
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
specifier|synchronized
name|TreeSet
argument_list|<
name|ExamProposedChange
argument_list|>
name|computeSuggestions
parameter_list|(
name|Exam
name|exam
parameter_list|,
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|givenAssignments
parameter_list|)
block|{
name|iSuggestions
operator|=
operator|new
name|TreeSet
argument_list|<
name|ExamProposedChange
argument_list|>
argument_list|()
expr_stmt|;
name|iResolvedExams
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|iConflictsToResolve
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|iNrSolutions
operator|=
literal|0
expr_stmt|;
name|iNrCombinationsConsidered
operator|=
literal|0
expr_stmt|;
name|iTimeoutReached
operator|=
literal|false
expr_stmt|;
name|iExam
operator|=
name|exam
expr_stmt|;
if|if
condition|(
name|givenAssignments
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ExamAssignment
name|assignment
range|:
name|givenAssignments
control|)
block|{
name|ExamPlacement
name|placement
init|=
name|iSolver
operator|.
name|getPlacement
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|placement
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|placement
operator|.
name|variable
argument_list|()
operator|.
name|equals
argument_list|(
name|exam
argument_list|)
condition|)
continue|continue;
name|Set
name|conflicts
init|=
name|iModel
operator|.
name|conflictValues
argument_list|(
name|placement
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|conflicts
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
name|ExamPlacement
name|conflictPlacement
init|=
operator|(
name|ExamPlacement
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iConflictsToResolve
operator|.
name|put
argument_list|(
operator|(
name|Exam
operator|)
name|conflictPlacement
operator|.
name|variable
argument_list|()
argument_list|,
name|conflictPlacement
argument_list|)
expr_stmt|;
name|conflictPlacement
operator|.
name|variable
argument_list|()
operator|.
name|unassign
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
name|iResolvedExams
operator|.
name|add
argument_list|(
operator|(
name|Exam
operator|)
name|placement
operator|.
name|variable
argument_list|()
argument_list|)
expr_stmt|;
name|iConflictsToResolve
operator|.
name|remove
argument_list|(
operator|(
name|Exam
operator|)
name|placement
operator|.
name|variable
argument_list|()
argument_list|)
expr_stmt|;
name|placement
operator|.
name|variable
argument_list|()
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|placement
argument_list|)
expr_stmt|;
block|}
block|}
name|iStartTime
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
name|backtrack
argument_list|(
name|iDepth
argument_list|)
expr_stmt|;
for|for
control|(
name|Exam
name|x
range|:
name|iInitialUnassignment
control|)
if|if
condition|(
name|x
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
condition|)
name|x
operator|.
name|unassign
argument_list|(
literal|0
argument_list|)
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|x
range|:
name|iInitialAssignment
operator|.
name|entrySet
argument_list|()
control|)
if|if
condition|(
operator|!
name|x
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|x
operator|.
name|getKey
argument_list|()
operator|.
name|getAssignment
argument_list|()
argument_list|)
condition|)
name|x
operator|.
name|getKey
argument_list|()
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|x
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iSuggestions
return|;
block|}
specifier|public
name|Set
name|findBestAvailableRooms
parameter_list|(
name|Exam
name|exam
parameter_list|,
name|ExamPeriodPlacement
name|period
parameter_list|,
name|boolean
name|checkConstraints
parameter_list|)
block|{
if|if
condition|(
name|exam
operator|.
name|getMaxRooms
argument_list|()
operator|==
literal|0
condition|)
return|return
operator|new
name|HashSet
argument_list|()
return|;
name|ExamRoomSharing
name|sharing
init|=
name|iModel
operator|.
name|getRoomSharing
argument_list|()
decl_stmt|;
name|loop
label|:
for|for
control|(
name|int
name|nrRooms
init|=
literal|1
init|;
name|nrRooms
operator|<=
name|exam
operator|.
name|getMaxRooms
argument_list|()
condition|;
name|nrRooms
operator|++
control|)
block|{
name|HashSet
name|rooms
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|rooms
operator|.
name|size
argument_list|()
operator|<
name|nrRooms
operator|&&
name|size
operator|<
name|exam
operator|.
name|getSize
argument_list|()
condition|)
block|{
name|int
name|minSize
init|=
operator|(
name|exam
operator|.
name|getSize
argument_list|()
operator|-
name|size
operator|)
operator|/
operator|(
name|nrRooms
operator|-
name|rooms
operator|.
name|size
argument_list|()
operator|)
decl_stmt|;
name|ExamRoomPlacement
name|best
init|=
literal|null
decl_stmt|;
name|int
name|bestSize
init|=
literal|0
decl_stmt|,
name|bestPenalty
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ExamRoomPlacement
name|room
range|:
name|exam
operator|.
name|getRoomPlacements
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|room
operator|.
name|isAvailable
argument_list|(
name|period
operator|.
name|getPeriod
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|checkConstraints
condition|)
block|{
if|if
condition|(
name|nrRooms
operator|==
literal|1
operator|&&
name|sharing
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|sharing
operator|.
name|inConflict
argument_list|(
name|exam
argument_list|,
name|room
operator|.
name|getRoom
argument_list|()
operator|.
name|getPlacements
argument_list|(
name|period
operator|.
name|getPeriod
argument_list|()
argument_list|)
argument_list|,
name|room
operator|.
name|getRoom
argument_list|()
argument_list|)
condition|)
continue|continue;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|room
operator|.
name|getRoom
argument_list|()
operator|.
name|getPlacements
argument_list|(
name|period
operator|.
name|getPeriod
argument_list|()
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
block|}
block|}
if|if
condition|(
name|rooms
operator|.
name|contains
argument_list|(
name|room
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|checkConstraints
operator|&&
operator|!
name|exam
operator|.
name|checkDistributionConstraints
argument_list|(
name|room
argument_list|)
condition|)
continue|continue;
name|int
name|s
init|=
name|room
operator|.
name|getSize
argument_list|(
name|exam
operator|.
name|hasAltSeating
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|s
operator|<
name|minSize
condition|)
break|break;
name|int
name|p
init|=
name|room
operator|.
name|getPenalty
argument_list|(
name|period
operator|.
name|getPeriod
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|best
operator|==
literal|null
operator|||
name|bestPenalty
operator|>
name|p
operator|||
operator|(
name|bestPenalty
operator|==
name|p
operator|&&
name|bestSize
operator|>
name|s
operator|)
condition|)
block|{
name|best
operator|=
name|room
expr_stmt|;
name|bestSize
operator|=
name|s
expr_stmt|;
name|bestPenalty
operator|=
name|p
expr_stmt|;
block|}
block|}
if|if
condition|(
name|best
operator|==
literal|null
condition|)
continue|continue
name|loop
continue|;
name|rooms
operator|.
name|add
argument_list|(
name|best
argument_list|)
expr_stmt|;
name|size
operator|+=
name|bestSize
expr_stmt|;
block|}
if|if
condition|(
name|size
operator|>=
name|exam
operator|.
name|getSize
argument_list|()
condition|)
return|return
name|rooms
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|private
name|void
name|tryPlacement
parameter_list|(
name|ExamPlacement
name|placement
parameter_list|,
name|int
name|depth
parameter_list|)
block|{
if|if
condition|(
name|placement
operator|.
name|equals
argument_list|(
name|placement
operator|.
name|variable
argument_list|()
operator|.
name|getAssignment
argument_list|()
argument_list|)
condition|)
return|return;
if|if
condition|(
name|placement
operator|.
name|variable
argument_list|()
operator|.
name|equals
argument_list|(
name|iExam
argument_list|)
operator|&&
operator|!
name|match
argument_list|(
name|placement
operator|.
name|getPeriod
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|" "
operator|+
name|placement
operator|.
name|getRoomName
argument_list|(
literal|", "
argument_list|)
argument_list|)
condition|)
return|return;
name|Set
name|conflicts
init|=
name|iModel
operator|.
name|conflictValues
argument_list|(
name|placement
argument_list|)
decl_stmt|;
name|tryPlacement
argument_list|(
name|placement
argument_list|,
name|depth
argument_list|,
name|conflicts
argument_list|)
expr_stmt|;
if|if
condition|(
name|iConflictsToResolve
operator|.
name|size
argument_list|()
operator|+
name|conflicts
operator|.
name|size
argument_list|()
operator|<
name|depth
condition|)
block|{
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|placement
operator|.
name|variable
argument_list|()
decl_stmt|;
name|HashSet
name|adepts
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|ExamStudent
name|s
range|:
name|exam
operator|.
name|getStudents
argument_list|()
control|)
block|{
name|Set
name|exams
init|=
name|s
operator|.
name|getExams
argument_list|(
name|placement
operator|.
name|getPeriod
argument_list|()
argument_list|)
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
name|ExamPlacement
name|conf
init|=
operator|(
name|ExamPlacement
operator|)
operator|(
operator|(
name|Exam
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|conf
operator|==
literal|null
operator|||
name|conflicts
operator|.
name|contains
argument_list|(
name|conf
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|iResolvedExams
operator|.
name|contains
argument_list|(
operator|(
name|Exam
operator|)
name|conf
operator|.
name|variable
argument_list|()
argument_list|)
condition|)
continue|continue;
name|adepts
operator|.
name|add
argument_list|(
name|conf
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|adepts
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
name|ExamPlacement
name|adept
init|=
operator|(
name|ExamPlacement
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|conflicts
operator|.
name|add
argument_list|(
name|adept
argument_list|)
expr_stmt|;
name|tryPlacement
argument_list|(
name|placement
argument_list|,
name|depth
argument_list|,
name|conflicts
argument_list|)
expr_stmt|;
name|conflicts
operator|.
name|remove
argument_list|(
name|adept
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iConflictsToResolve
operator|.
name|size
argument_list|()
operator|+
name|conflicts
operator|.
name|size
argument_list|()
operator|+
literal|1
operator|<
name|depth
condition|)
block|{
for|for
control|(
name|Iterator
name|i1
init|=
name|adepts
operator|.
name|iterator
argument_list|()
init|;
name|i1
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPlacement
name|a1
init|=
operator|(
name|ExamPlacement
operator|)
name|i1
operator|.
name|next
argument_list|()
decl_stmt|;
name|conflicts
operator|.
name|add
argument_list|(
name|a1
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i2
init|=
name|adepts
operator|.
name|iterator
argument_list|()
init|;
name|i2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPlacement
name|a2
init|=
operator|(
name|ExamPlacement
operator|)
name|i2
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|a2
operator|.
name|variable
argument_list|()
operator|.
name|getId
argument_list|()
operator|>=
name|a1
operator|.
name|variable
argument_list|()
operator|.
name|getId
argument_list|()
condition|)
continue|continue;
name|conflicts
operator|.
name|add
argument_list|(
name|a2
argument_list|)
expr_stmt|;
name|tryPlacement
argument_list|(
name|placement
argument_list|,
name|depth
argument_list|,
name|conflicts
argument_list|)
expr_stmt|;
name|conflicts
operator|.
name|remove
argument_list|(
name|a2
argument_list|)
expr_stmt|;
block|}
name|conflicts
operator|.
name|remove
argument_list|(
name|a1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|private
name|void
name|tryPlacement
parameter_list|(
name|ExamPlacement
name|placement
parameter_list|,
name|int
name|depth
parameter_list|,
name|Set
name|conflicts
parameter_list|)
block|{
name|iNrCombinationsConsidered
operator|++
expr_stmt|;
if|if
condition|(
name|iConflictsToResolve
operator|.
name|size
argument_list|()
operator|+
name|conflicts
operator|.
name|size
argument_list|()
operator|>
name|depth
condition|)
return|return;
for|for
control|(
name|Iterator
name|i
init|=
name|conflicts
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
name|ExamPlacement
name|c
init|=
operator|(
name|ExamPlacement
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|iResolvedExams
operator|.
name|contains
argument_list|(
operator|(
name|Exam
operator|)
name|c
operator|.
name|variable
argument_list|()
argument_list|)
condition|)
return|return;
block|}
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|placement
operator|.
name|variable
argument_list|()
decl_stmt|;
name|ExamPlacement
name|cur
init|=
operator|(
name|ExamPlacement
operator|)
name|exam
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|conflicts
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|conflicts
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
name|ExamPlacement
name|c
init|=
operator|(
name|ExamPlacement
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|c
operator|.
name|variable
argument_list|()
operator|.
name|unassign
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
name|exam
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|placement
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|conflicts
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
name|ExamPlacement
name|c
init|=
operator|(
name|ExamPlacement
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|iConflictsToResolve
operator|.
name|put
argument_list|(
operator|(
name|Exam
operator|)
name|c
operator|.
name|variable
argument_list|()
argument_list|,
name|c
argument_list|)
expr_stmt|;
block|}
name|ExamPlacement
name|resolvedConf
init|=
name|iConflictsToResolve
operator|.
name|remove
argument_list|(
name|exam
argument_list|)
decl_stmt|;
name|backtrack
argument_list|(
name|depth
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|cur
operator|==
literal|null
condition|)
name|exam
operator|.
name|unassign
argument_list|(
literal|0
argument_list|)
expr_stmt|;
else|else
name|exam
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|cur
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|conflicts
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
name|ExamPlacement
name|p
init|=
operator|(
name|ExamPlacement
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|p
operator|.
name|variable
argument_list|()
operator|.
name|assign
argument_list|(
literal|0
argument_list|,
name|p
argument_list|)
expr_stmt|;
name|iConflictsToResolve
operator|.
name|remove
argument_list|(
operator|(
name|Exam
operator|)
name|p
operator|.
name|variable
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|resolvedConf
operator|!=
literal|null
condition|)
name|iConflictsToResolve
operator|.
name|put
argument_list|(
name|exam
argument_list|,
name|resolvedConf
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|backtrack
parameter_list|(
name|int
name|depth
parameter_list|)
block|{
if|if
condition|(
name|iDepth
operator|>
name|depth
operator|&&
name|iConflictsToResolve
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|iSuggestions
operator|.
name|size
argument_list|()
operator|==
name|iLimit
operator|&&
name|iSuggestions
operator|.
name|last
argument_list|()
operator|.
name|isBetter
argument_list|(
name|iModel
argument_list|)
condition|)
return|return;
name|iSuggestions
operator|.
name|add
argument_list|(
operator|new
name|ExamProposedChange
argument_list|(
name|iModel
argument_list|,
name|iInitialAssignment
argument_list|,
name|iInitialInfo
argument_list|,
name|iConflictsToResolve
operator|.
name|values
argument_list|()
argument_list|,
name|iResolvedExams
argument_list|)
argument_list|)
expr_stmt|;
name|iNrSolutions
operator|++
expr_stmt|;
if|if
condition|(
name|iSuggestions
operator|.
name|size
argument_list|()
operator|>
name|iLimit
condition|)
name|iSuggestions
operator|.
name|remove
argument_list|(
name|iSuggestions
operator|.
name|last
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|depth
operator|<=
literal|0
condition|)
return|return;
if|if
condition|(
name|iTimeOut
operator|>
literal|0
operator|&&
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|iStartTime
operator|>
name|iTimeOut
condition|)
block|{
name|iTimeoutReached
operator|=
literal|true
expr_stmt|;
return|return;
block|}
name|Exam
name|exam
init|=
operator|(
name|iDepth
operator|==
name|depth
operator|&&
operator|!
name|iResolvedExams
operator|.
name|contains
argument_list|(
name|iExam
argument_list|)
condition|?
name|iExam
else|:
name|iConflictsToResolve
operator|.
name|keys
argument_list|()
operator|.
name|nextElement
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|iResolvedExams
operator|.
name|contains
argument_list|(
name|exam
argument_list|)
condition|)
return|return;
name|iResolvedExams
operator|.
name|add
argument_list|(
name|exam
argument_list|)
expr_stmt|;
for|for
control|(
name|ExamPeriodPlacement
name|period
range|:
name|exam
operator|.
name|getPeriodPlacements
argument_list|()
control|)
block|{
comment|//if (exam.equals(iExam)&& !match(period.getPeriod().toString())) continue;
name|Set
name|rooms
init|=
name|findBestAvailableRooms
argument_list|(
name|exam
argument_list|,
name|period
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|rooms
operator|!=
literal|null
condition|)
block|{
name|tryPlacement
argument_list|(
operator|new
name|ExamPlacement
argument_list|(
name|exam
argument_list|,
name|period
argument_list|,
name|rooms
argument_list|)
argument_list|,
name|depth
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rooms
operator|=
name|findBestAvailableRooms
argument_list|(
name|exam
argument_list|,
name|period
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|rooms
operator|!=
literal|null
condition|)
name|tryPlacement
argument_list|(
operator|new
name|ExamPlacement
argument_list|(
name|exam
argument_list|,
name|period
argument_list|,
name|rooms
argument_list|)
argument_list|,
name|depth
argument_list|)
expr_stmt|;
block|}
block|}
name|iResolvedExams
operator|.
name|remove
argument_list|(
name|exam
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

