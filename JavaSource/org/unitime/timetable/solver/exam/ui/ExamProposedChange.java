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
operator|.
name|ui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|Vector
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|assignment
operator|.
name|Assignment
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
name|PreferenceLevel
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamProposedChange
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|ExamProposedChange
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5497603865422857068L
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|iAssignments
init|=
literal|null
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|ExamAssignment
argument_list|>
name|iConflicts
init|=
literal|null
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|ExamAssignment
argument_list|>
name|iInitials
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iSelectedExamId
init|=
literal|null
decl_stmt|;
specifier|private
name|double
name|iValue
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iNrAssigned
init|=
literal|0
decl_stmt|;
specifier|public
name|ExamProposedChange
parameter_list|()
block|{
name|iAssignments
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|iConflicts
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|iInitials
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
block|}
specifier|public
name|ExamProposedChange
parameter_list|(
name|ExamModel
name|model
parameter_list|,
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|assignment
parameter_list|,
name|Hashtable
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|initialAssignment
parameter_list|,
name|Hashtable
argument_list|<
name|Exam
argument_list|,
name|ExamAssignment
argument_list|>
name|initialInfo
parameter_list|,
name|Collection
argument_list|<
name|ExamPlacement
argument_list|>
name|conflicts
parameter_list|,
name|Vector
argument_list|<
name|Exam
argument_list|>
name|resolvedExams
parameter_list|)
block|{
name|iValue
operator|=
name|model
operator|.
name|getTotalValue
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|iNrAssigned
operator|=
name|model
operator|.
name|nrAssignedVariables
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
if|if
condition|(
name|conflicts
operator|!=
literal|null
condition|)
block|{
name|iConflicts
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
for|for
control|(
name|ExamPlacement
name|conflict
range|:
name|conflicts
control|)
name|iConflicts
operator|.
name|add
argument_list|(
name|initialInfo
operator|.
name|get
argument_list|(
operator|(
name|Exam
operator|)
name|conflict
operator|.
name|variable
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|iAssignments
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
name|iInitials
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
name|resolvedExams
control|)
block|{
name|ExamPlacement
name|current
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|exam
argument_list|)
decl_stmt|;
name|ExamPlacement
name|initial
init|=
name|initialAssignment
operator|.
name|get
argument_list|(
name|exam
argument_list|)
decl_stmt|;
if|if
condition|(
name|initial
operator|==
literal|null
condition|)
block|{
name|iAssignments
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|,
name|current
argument_list|,
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|initial
operator|.
name|equals
argument_list|(
name|current
argument_list|)
condition|)
block|{
name|iAssignments
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|,
name|current
argument_list|,
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
name|iInitials
operator|.
name|put
argument_list|(
name|exam
operator|.
name|getId
argument_list|()
argument_list|,
name|initialInfo
operator|.
name|get
argument_list|(
name|exam
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Exam
name|exam
range|:
name|model
operator|.
name|assignedVariables
argument_list|(
name|assignment
argument_list|)
control|)
block|{
if|if
condition|(
name|resolvedExams
operator|.
name|contains
argument_list|(
name|exam
argument_list|)
condition|)
continue|continue;
name|ExamPlacement
name|current
init|=
operator|(
name|ExamPlacement
operator|)
name|assignment
operator|.
name|getValue
argument_list|(
name|exam
argument_list|)
decl_stmt|;
name|ExamPlacement
name|initial
init|=
name|initialAssignment
operator|.
name|get
argument_list|(
name|exam
argument_list|)
decl_stmt|;
if|if
condition|(
name|initial
operator|==
literal|null
condition|)
block|{
name|iAssignments
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|,
name|current
argument_list|,
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|initial
operator|.
name|equals
argument_list|(
name|current
argument_list|)
condition|)
block|{
name|iAssignments
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|,
name|current
argument_list|,
name|assignment
argument_list|)
argument_list|)
expr_stmt|;
name|iInitials
operator|.
name|put
argument_list|(
name|exam
operator|.
name|getId
argument_list|()
argument_list|,
name|initialInfo
operator|.
name|get
argument_list|(
name|exam
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|addChange
parameter_list|(
name|ExamAssignmentInfo
name|change
parameter_list|,
name|ExamAssignment
name|initial
parameter_list|)
block|{
for|for
control|(
name|ExamAssignment
name|assignment
range|:
name|iAssignments
control|)
block|{
if|if
condition|(
name|assignment
operator|.
name|getExamId
argument_list|()
operator|.
name|equals
argument_list|(
name|change
operator|.
name|getExamId
argument_list|()
argument_list|)
condition|)
block|{
name|iAssignments
operator|.
name|remove
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|iInitials
operator|.
name|remove
argument_list|(
name|assignment
operator|.
name|getExamId
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|iAssignments
control|)
block|{
if|if
condition|(
name|conflict
operator|.
name|getExamId
argument_list|()
operator|.
name|equals
argument_list|(
name|change
operator|.
name|getExamId
argument_list|()
argument_list|)
condition|)
block|{
name|iConflicts
operator|.
name|remove
argument_list|(
name|conflict
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
comment|//if (initial!=null&& initial.assignmentEquals(change)) return;
if|if
condition|(
name|change
operator|.
name|getPeriodId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iAssignments
operator|.
name|add
argument_list|(
name|change
argument_list|)
expr_stmt|;
if|if
condition|(
name|initial
operator|!=
literal|null
operator|&&
name|initial
operator|.
name|getPeriodId
argument_list|()
operator|!=
literal|null
condition|)
name|iInitials
operator|.
name|put
argument_list|(
name|initial
operator|.
name|getExamId
argument_list|()
argument_list|,
name|initial
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iConflicts
operator|.
name|add
argument_list|(
name|initial
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|iConflicts
operator|.
name|isEmpty
argument_list|()
operator|&&
name|iAssignments
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isBetter
parameter_list|(
name|ExamModel
name|model
parameter_list|,
name|Assignment
argument_list|<
name|Exam
argument_list|,
name|ExamPlacement
argument_list|>
name|assignment
parameter_list|)
block|{
if|if
condition|(
name|iNrAssigned
operator|>
name|model
operator|.
name|nrAssignedVariables
argument_list|(
name|assignment
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iNrAssigned
operator|==
name|model
operator|.
name|nrAssignedVariables
argument_list|(
name|assignment
argument_list|)
operator|&&
name|iValue
operator|<
name|model
operator|.
name|getTotalValue
argument_list|(
name|assignment
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|ExamAssignment
argument_list|>
name|getConflicts
parameter_list|()
block|{
return|return
name|iConflicts
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|getAssignments
parameter_list|()
block|{
return|return
name|iAssignments
return|;
block|}
specifier|public
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|ExamAssignment
argument_list|>
name|getAssignmentTable
parameter_list|()
block|{
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|ExamAssignment
argument_list|>
name|table
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|iConflicts
control|)
name|table
operator|.
name|put
argument_list|(
name|conflict
operator|.
name|getExamId
argument_list|()
argument_list|,
operator|new
name|ExamAssignment
argument_list|(
name|conflict
operator|.
name|getExam
argument_list|()
argument_list|,
operator|(
name|ExamPeriod
operator|)
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
for|for
control|(
name|ExamAssignment
name|assignment
range|:
name|iAssignments
control|)
name|table
operator|.
name|put
argument_list|(
name|assignment
operator|.
name|getExamId
argument_list|()
argument_list|,
name|assignment
argument_list|)
expr_stmt|;
return|return
name|table
return|;
block|}
specifier|public
name|ExamAssignment
name|getInitial
parameter_list|(
name|ExamAssignment
name|current
parameter_list|)
block|{
return|return
name|iInitials
operator|.
name|get
argument_list|(
name|current
operator|.
name|getExamId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|ExamAssignmentInfo
name|getCurrent
parameter_list|(
name|ExamInfo
name|exam
parameter_list|)
block|{
return|return
name|getCurrent
argument_list|(
name|exam
operator|.
name|getExamId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|ExamAssignmentInfo
name|getCurrent
parameter_list|(
name|Long
name|examId
parameter_list|)
block|{
for|for
control|(
name|ExamAssignmentInfo
name|assignment
range|:
name|iAssignments
control|)
if|if
condition|(
name|assignment
operator|.
name|getExamId
argument_list|()
operator|.
name|equals
argument_list|(
name|examId
argument_list|)
condition|)
return|return
name|assignment
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|ExamAssignment
name|getConflict
parameter_list|(
name|ExamInfo
name|exam
parameter_list|)
block|{
return|return
name|getConflict
argument_list|(
name|exam
operator|.
name|getExamId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|ExamAssignment
name|getConflict
parameter_list|(
name|Long
name|examId
parameter_list|)
block|{
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|iConflicts
control|)
if|if
condition|(
name|conflict
operator|.
name|getExamId
argument_list|()
operator|.
name|equals
argument_list|(
name|examId
argument_list|)
condition|)
return|return
name|conflict
return|;
return|return
literal|null
return|;
block|}
specifier|public
name|ExamAssignment
name|getInitial
parameter_list|(
name|Long
name|currentId
parameter_list|)
block|{
return|return
name|iInitials
operator|.
name|get
argument_list|(
name|currentId
argument_list|)
return|;
block|}
specifier|public
name|int
name|getNrAssigned
parameter_list|()
block|{
return|return
name|iAssignments
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|int
name|getNrUnassigned
parameter_list|()
block|{
return|return
name|iConflicts
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|double
name|getValue
parameter_list|()
block|{
name|double
name|value
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|iConflicts
control|)
name|value
operator|-=
name|conflict
operator|.
name|getPlacementValue
argument_list|()
expr_stmt|;
for|for
control|(
name|ExamAssignment
name|current
range|:
name|iAssignments
control|)
name|value
operator|+=
name|current
operator|.
name|getPlacementValue
argument_list|()
expr_stmt|;
for|for
control|(
name|ExamAssignment
name|initial
range|:
name|iInitials
operator|.
name|values
argument_list|()
control|)
name|value
operator|-=
name|initial
operator|.
name|getPlacementValue
argument_list|()
expr_stmt|;
return|return
name|value
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExamProposedChange
name|change
parameter_list|)
block|{
name|int
name|cmp
init|=
name|Double
operator|.
name|compare
argument_list|(
name|getNrUnassigned
argument_list|()
argument_list|,
name|change
operator|.
name|getNrUnassigned
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|Double
operator|.
name|compare
argument_list|(
name|getValue
argument_list|()
argument_list|,
name|change
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setSelected
parameter_list|(
name|Long
name|examId
parameter_list|)
block|{
name|iSelectedExamId
operator|=
name|examId
expr_stmt|;
block|}
specifier|public
name|Long
name|getSelected
parameter_list|()
block|{
return|return
name|iSelectedExamId
return|;
block|}
specifier|public
name|String
name|getHtmlTable
parameter_list|()
block|{
name|String
name|ret
init|=
literal|"<table border='0' cellspacing='0' cellpadding='3' width='100%'>"
decl_stmt|;
name|ret
operator|+=
literal|"<tr>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Examination</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Period Change</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Room Change</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>Direct</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>&gt;2 A Day</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"<td><i>BTB</i></td>"
expr_stmt|;
name|ret
operator|+=
literal|"</tr>"
expr_stmt|;
for|for
control|(
name|ExamAssignment
name|current
range|:
name|iAssignments
control|)
block|{
name|ExamAssignment
name|initial
init|=
name|iInitials
operator|.
name|get
argument_list|(
name|current
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|bgColor
init|=
operator|(
name|current
operator|.
name|getExamId
argument_list|()
operator|.
name|equals
argument_list|(
name|iSelectedExamId
argument_list|)
condition|?
literal|"rgb(168,187,225)"
else|:
literal|null
operator|)
decl_stmt|;
name|ret
operator|+=
literal|"<tr "
operator|+
operator|(
name|bgColor
operator|==
literal|null
condition|?
literal|""
else|:
literal|"style=\"background-color:"
operator|+
name|bgColor
operator|+
literal|";\" "
operator|)
operator|+
literal|"onmouseover=\"this.style.backgroundColor='rgb(223,231,242)';this.style.cursor='hand';this.style.cursor='pointer';\" "
operator|+
literal|"onmouseout=\"this.style.backgroundColor='"
operator|+
operator|(
name|bgColor
operator|==
literal|null
condition|?
literal|"transparent"
else|:
name|bgColor
operator|)
operator|+
literal|"';\" "
operator|+
literal|"onclick=\"document.location='examInfo.do?examId="
operator|+
name|current
operator|.
name|getExamId
argument_list|()
operator|+
literal|"&op=Select&noCacheTS="
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|"';\">"
expr_stmt|;
name|ret
operator|+=
literal|"<td nowrap>"
expr_stmt|;
name|ret
operator|+=
literal|"<img src='images/action_delete.png' border='0' onclick=\"document.location='examInfo.do?delete="
operator|+
name|current
operator|.
name|getExamId
argument_list|()
operator|+
literal|"&op=Select&noCacheTS="
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|"';event.cancelBubble=true;\">&nbsp;"
expr_stmt|;
name|ret
operator|+=
name|current
operator|.
name|getExamNameHtml
argument_list|()
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
if|if
condition|(
name|initial
operator|!=
literal|null
operator|&&
operator|!
name|initial
operator|.
name|getPeriodId
argument_list|()
operator|.
name|equals
argument_list|(
name|current
operator|.
name|getPeriodId
argument_list|()
argument_list|)
condition|)
name|ret
operator|+=
name|initial
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
operator|+
literal|"&rarr; "
expr_stmt|;
if|if
condition|(
name|initial
operator|==
literal|null
condition|)
name|ret
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>&rarr; "
expr_stmt|;
name|ret
operator|+=
name|current
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
if|if
condition|(
name|initial
operator|!=
literal|null
operator|&&
operator|!
name|initial
operator|.
name|getRoomIds
argument_list|()
operator|.
name|equals
argument_list|(
name|current
operator|.
name|getRoomIds
argument_list|()
argument_list|)
condition|)
name|ret
operator|+=
name|initial
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|", "
argument_list|)
operator|+
literal|"&rarr; "
expr_stmt|;
if|if
condition|(
name|initial
operator|==
literal|null
condition|)
name|ret
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>&rarr; "
expr_stmt|;
name|ret
operator|+=
name|current
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
if|if
condition|(
name|current
operator|.
name|getNrRooms
argument_list|()
operator|==
literal|0
operator|&&
name|current
operator|.
name|getMaxRooms
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|current
operator|.
name|getExamId
argument_list|()
operator|.
name|equals
argument_list|(
name|iSelectedExamId
argument_list|)
condition|)
name|ret
operator|+=
literal|"<i>Select below ...</i>"
expr_stmt|;
else|else
name|ret
operator|+=
literal|"<i><font color='red'>Not selected ...</font></i>"
expr_stmt|;
block|}
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrDirectConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrDirectConflicts
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrMoreThanTwoADayConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrMoreThanTwoADayConflicts
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrBackToBackConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrBackToBackConflicts
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|current
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|+=
literal|" (d:"
operator|+
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
argument_list|)
operator|+
literal|")"
expr_stmt|;
name|ret
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|iConflicts
control|)
block|{
name|String
name|bgColor
init|=
operator|(
name|conflict
operator|.
name|getExamId
argument_list|()
operator|.
name|equals
argument_list|(
name|iSelectedExamId
argument_list|)
condition|?
literal|"rgb(168,187,225)"
else|:
literal|null
operator|)
decl_stmt|;
name|ret
operator|+=
literal|"<tr "
operator|+
operator|(
name|bgColor
operator|==
literal|null
condition|?
literal|""
else|:
literal|"style=\"background-color:"
operator|+
name|bgColor
operator|+
literal|";\" "
operator|)
operator|+
literal|"onmouseover=\"this.style.backgroundColor='rgb(223,231,242)';this.style.cursor='hand';this.style.cursor='pointer';\" "
operator|+
literal|"onmouseout=\"this.style.backgroundColor='"
operator|+
operator|(
name|bgColor
operator|==
literal|null
condition|?
literal|"transparent"
else|:
name|bgColor
operator|)
operator|+
literal|"';\" "
operator|+
literal|"onclick=\"document.location='examInfo.do?examId="
operator|+
name|conflict
operator|.
name|getExamId
argument_list|()
operator|+
literal|"&op=Select&noCacheTS="
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|"';\">"
expr_stmt|;
name|ret
operator|+=
literal|"<td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|conflict
operator|.
name|getExamNameHtml
argument_list|()
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|conflict
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
operator|+
literal|"&rarr;<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
operator|+
literal|"</td>"
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|conflict
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|", "
argument_list|)
operator|+
literal|"&rarr;<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrDirectConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrMoreThanTwoADayConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrBackToBackConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|conflict
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|+=
literal|" (d:"
operator|+
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
operator|+
literal|")"
expr_stmt|;
name|ret
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
name|ret
operator|+=
literal|"</table>"
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|getHtmlLine
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|String
name|ret
init|=
literal|"<tr onmouseover=\"this.style.backgroundColor='rgb(223,231,242)';this.style.cursor='hand';this.style.cursor='pointer';\" onmouseout=\"this.style.backgroundColor='transparent';\" onclick=\"document.location='examInfo.do?suggestion="
operator|+
name|index
operator|+
literal|"&op=Select&noCacheTS="
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|"';\">"
decl_stmt|;
name|String
name|name
init|=
literal|""
decl_stmt|,
name|period
init|=
literal|""
decl_stmt|,
name|room
init|=
literal|""
decl_stmt|,
name|dc
init|=
literal|""
decl_stmt|,
name|btb
init|=
literal|""
decl_stmt|,
name|m2d
init|=
literal|""
decl_stmt|;
for|for
control|(
name|ExamAssignment
name|current
range|:
name|iAssignments
control|)
block|{
name|ExamAssignment
name|initial
init|=
name|iInitials
operator|.
name|get
argument_list|(
name|current
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|name
operator|+=
literal|"<br>"
expr_stmt|;
name|period
operator|+=
literal|"<br>"
expr_stmt|;
name|room
operator|+=
literal|"<br>"
expr_stmt|;
name|dc
operator|+=
literal|"<br>"
expr_stmt|;
name|m2d
operator|+=
literal|"<br>"
expr_stmt|;
name|btb
operator|+=
literal|"<br>"
expr_stmt|;
block|}
name|name
operator|+=
name|current
operator|.
name|getExamNameHtml
argument_list|()
expr_stmt|;
if|if
condition|(
name|initial
operator|!=
literal|null
operator|&&
operator|!
name|initial
operator|.
name|getPeriodId
argument_list|()
operator|.
name|equals
argument_list|(
name|current
operator|.
name|getPeriodId
argument_list|()
argument_list|)
condition|)
name|period
operator|+=
name|initial
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
operator|+
literal|"&rarr; "
expr_stmt|;
name|period
operator|+=
name|current
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
expr_stmt|;
if|if
condition|(
name|initial
operator|!=
literal|null
operator|&&
operator|!
name|initial
operator|.
name|getRoomIds
argument_list|()
operator|.
name|equals
argument_list|(
name|current
operator|.
name|getRoomIds
argument_list|()
argument_list|)
condition|)
name|room
operator|+=
name|initial
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|", "
argument_list|)
operator|+
literal|"&rarr; "
expr_stmt|;
name|room
operator|+=
name|current
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|dc
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrDirectConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrDirectConflicts
argument_list|()
argument_list|)
expr_stmt|;
name|m2d
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrMoreThanTwoADayConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrMoreThanTwoADayConflicts
argument_list|()
argument_list|)
expr_stmt|;
name|btb
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrBackToBackConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrBackToBackConflicts
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|current
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
operator|>
literal|0
condition|)
name|btb
operator|+=
literal|" (d:"
operator|+
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|false
argument_list|,
operator|(
name|initial
operator|==
literal|null
condition|?
literal|0
else|:
name|initial
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
operator|)
argument_list|,
name|current
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
argument_list|)
operator|+
literal|")"
expr_stmt|;
block|}
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|iConflicts
control|)
block|{
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|name
operator|+=
literal|"<br>"
expr_stmt|;
name|period
operator|+=
literal|"<br>"
expr_stmt|;
name|room
operator|+=
literal|"<br>"
expr_stmt|;
name|dc
operator|+=
literal|"<br>"
expr_stmt|;
name|m2d
operator|+=
literal|"<br>"
expr_stmt|;
name|btb
operator|+=
literal|"<br>"
expr_stmt|;
block|}
name|name
operator|+=
name|conflict
operator|.
name|getExamNameHtml
argument_list|()
expr_stmt|;
name|period
operator|+=
name|conflict
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
operator|+
literal|"&rarr;<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
operator|+
literal|"</td>"
expr_stmt|;
name|room
operator|+=
name|conflict
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|", "
argument_list|)
operator|+
literal|"&rarr;<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
expr_stmt|;
name|dc
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrDirectConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|m2d
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrMoreThanTwoADayConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|btb
operator|+=
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrBackToBackConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|conflict
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
operator|>
literal|0
condition|)
name|btb
operator|+=
literal|" (d:"
operator|+
name|ClassAssignmentDetails
operator|.
name|dispNumberShort
argument_list|(
literal|true
argument_list|,
name|conflict
operator|.
name|getPlacementNrDistanceBackToBackConflicts
argument_list|()
argument_list|,
literal|0
argument_list|)
operator|+
literal|")"
expr_stmt|;
block|}
name|ret
operator|+=
literal|"<td align='right' width='1%' nowrap>"
operator|+
name|ClassAssignmentDetails
operator|.
name|dispNumber
argument_list|(
name|Math
operator|.
name|round
argument_list|(
name|getValue
argument_list|()
argument_list|)
argument_list|)
operator|+
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|name
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|period
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|room
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|dc
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|m2d
expr_stmt|;
name|ret
operator|+=
literal|"</td><td nowrap>"
expr_stmt|;
name|ret
operator|+=
name|btb
expr_stmt|;
name|ret
operator|+=
literal|"</td></tr>"
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|(
name|String
name|delim
parameter_list|)
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|iConflicts
control|)
block|{
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|+=
name|delim
expr_stmt|;
name|ret
operator|+=
name|conflict
operator|.
name|getExamName
argument_list|()
operator|+
literal|" "
operator|+
name|conflict
operator|.
name|getPeriodName
argument_list|()
operator|+
literal|" "
operator|+
name|conflict
operator|.
name|getRoomsName
argument_list|(
literal|", "
argument_list|)
operator|+
literal|" -> Not Assigned"
expr_stmt|;
block|}
for|for
control|(
name|ExamAssignment
name|current
range|:
name|iAssignments
control|)
block|{
if|if
condition|(
name|ret
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|ret
operator|+=
name|delim
expr_stmt|;
name|ExamAssignment
name|initial
init|=
name|iInitials
operator|.
name|get
argument_list|(
name|current
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
name|ret
operator|+=
name|current
operator|.
name|getExamName
argument_list|()
operator|+
literal|" "
operator|+
operator|(
name|initial
operator|==
literal|null
condition|?
literal|"Not Assigned"
else|:
name|initial
operator|.
name|getPeriodName
argument_list|()
operator|+
literal|" "
operator|+
name|initial
operator|.
name|getRoomsName
argument_list|(
literal|", "
argument_list|)
operator|)
operator|+
literal|" -> "
operator|+
name|current
operator|.
name|getPeriodName
argument_list|()
operator|+
literal|" "
operator|+
name|current
operator|.
name|getRoomsName
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toString
argument_list|(
literal|"\n"
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|isUnassigned
parameter_list|(
name|Long
name|examId
parameter_list|)
block|{
for|for
control|(
name|ExamAssignment
name|conflict
range|:
name|getConflicts
argument_list|()
control|)
if|if
condition|(
name|examId
operator|.
name|equals
argument_list|(
name|conflict
operator|.
name|getExamId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

