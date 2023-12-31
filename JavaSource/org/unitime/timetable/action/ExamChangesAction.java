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
name|action
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
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
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
name|ToolBox
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|WebTable
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
name|form
operator|.
name|ExamChangesForm
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
name|DepartmentStatusType
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
name|Exam
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|WebSolver
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
name|util
operator|.
name|ExportUtils
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
name|util
operator|.
name|LookupTables
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
name|webutil
operator|.
name|PdfWebTable
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/examChanges"
argument_list|)
specifier|public
class|class
name|ExamChangesAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|ExamChangesForm
name|myForm
init|=
operator|(
name|ExamChangesForm
operator|)
name|form
decl_stmt|;
comment|// Check Access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ExaminationAssignmentChanges
argument_list|)
expr_stmt|;
name|String
name|op
init|=
operator|(
name|myForm
operator|.
name|getOp
argument_list|()
operator|!=
literal|null
condition|?
name|myForm
operator|.
name|getOp
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Apply"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|save
argument_list|(
name|sessionContext
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"Refresh"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
name|myForm
operator|.
name|load
argument_list|(
name|sessionContext
argument_list|)
expr_stmt|;
name|ExamSolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|setNoSolver
argument_list|(
name|solver
operator|==
literal|null
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|ExamAssignmentInfo
index|[]
argument_list|>
name|changes
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getSubjectArea
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getSubjectArea
argument_list|()
operator|!=
literal|0
operator|&&
name|myForm
operator|.
name|getExamType
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|ExamChangesForm
operator|.
name|sChangeInitial
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getChangeType
argument_list|()
argument_list|)
condition|)
name|changes
operator|=
name|solver
operator|.
name|getChangesToInitial
argument_list|(
name|myForm
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|ExamChangesForm
operator|.
name|sChangeBest
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getChangeType
argument_list|()
argument_list|)
condition|)
name|changes
operator|=
name|solver
operator|.
name|getChangesToBest
argument_list|(
name|myForm
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
else|else
block|{
comment|//sChangeSaved
name|changes
operator|=
operator|new
name|Vector
argument_list|<
name|ExamAssignmentInfo
index|[]
argument_list|>
argument_list|()
expr_stmt|;
name|List
name|exams
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getSubjectArea
argument_list|()
operator|<
literal|0
condition|)
name|exams
operator|=
name|Exam
operator|.
name|findAll
argument_list|(
name|solver
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|solver
operator|.
name|getExamTypeId
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|exams
operator|=
name|Exam
operator|.
name|findExamsOfSubjectArea
argument_list|(
name|myForm
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|solver
operator|.
name|getExamTypeId
argument_list|()
argument_list|)
expr_stmt|;
name|exams
label|:
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
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ExamAssignment
name|assignment
init|=
name|solver
operator|.
name|getAssignment
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|assignment
operator|==
literal|null
operator|&&
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|assignment
operator|==
literal|null
operator|||
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|==
literal|null
condition|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
index|[]
block|{
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|)
block|,
name|solver
operator|.
name|getAssignmentInfo
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|assignment
operator|.
name|getPeriodId
argument_list|()
argument_list|)
condition|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
index|[]
block|{
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|)
block|,
name|solver
operator|.
name|getAssignmentInfo
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|exam
operator|.
name|getAssignedRooms
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
operator|(
name|assignment
operator|.
name|getRooms
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|assignment
operator|.
name|getRooms
argument_list|()
operator|.
name|size
argument_list|()
operator|)
condition|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
index|[]
block|{
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|)
block|,
name|solver
operator|.
name|getAssignmentInfo
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|getAssignedRooms
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
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|assignment
operator|.
name|hasRoom
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignmentInfo
index|[]
block|{
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|)
block|,
name|solver
operator|.
name|getAssignmentInfo
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
continue|continue
name|exams
continue|;
block|}
block|}
block|}
block|}
block|}
block|}
block|}
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"examChanges.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|WebTable
name|table
init|=
name|getTable
argument_list|(
literal|true
argument_list|,
name|myForm
argument_list|,
name|changes
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|&&
name|table
operator|!=
literal|null
condition|)
block|{
name|ExportUtils
operator|.
name|exportPDF
argument_list|(
name|getTable
argument_list|(
literal|false
argument_list|,
name|myForm
argument_list|,
name|changes
argument_list|)
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"examChanges.ord"
argument_list|)
argument_list|,
name|response
argument_list|,
literal|"changes"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|table
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setTable
argument_list|(
name|table
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"examChanges.ord"
argument_list|)
argument_list|)
argument_list|,
literal|9
argument_list|,
name|changes
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupExamTypes
argument_list|(
name|request
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|DepartmentStatusType
operator|.
name|Status
operator|.
name|ExamTimetable
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showReport"
argument_list|)
return|;
block|}
specifier|public
name|PdfWebTable
name|getTable
parameter_list|(
name|boolean
name|html
parameter_list|,
name|ExamChangesForm
name|form
parameter_list|,
name|Collection
argument_list|<
name|ExamAssignmentInfo
index|[]
argument_list|>
name|changes
parameter_list|)
block|{
if|if
condition|(
name|changes
operator|==
literal|null
operator|||
name|changes
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|String
name|nl
init|=
operator|(
name|html
condition|?
literal|"<br>"
else|:
literal|"\n"
operator|)
decl_stmt|;
name|PdfWebTable
name|table
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|9
argument_list|,
literal|"Examination Assignment Changes"
argument_list|,
literal|"examChanges.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
operator|(
name|form
operator|.
name|getShowSections
argument_list|()
condition|?
literal|"Classes / Courses"
else|:
literal|"Examination"
operator|)
block|,
literal|"Period"
block|,
literal|"Room"
block|,
literal|"Seating"
operator|+
name|nl
operator|+
literal|"Type"
block|,
literal|"Students"
block|,
literal|"Instructor"
block|,
literal|"Direct"
block|,
literal|">2 A Day"
block|,
literal|"Back-To-Back"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"center"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"right"
block|,
literal|"right"
block|,
literal|"right"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|false
block|,
literal|true
block|,
literal|false
block|,
literal|false
block|,
literal|false
block|}
argument_list|)
decl_stmt|;
name|table
operator|.
name|setRowStyle
argument_list|(
literal|"white-space:nowrap"
argument_list|)
expr_stmt|;
try|try
block|{
for|for
control|(
name|ExamAssignmentInfo
index|[]
name|change
range|:
name|changes
control|)
block|{
name|ExamAssignmentInfo
name|old
init|=
name|change
index|[
name|form
operator|.
name|getReverse
argument_list|()
condition|?
literal|1
else|:
literal|0
index|]
decl_stmt|;
name|ExamAssignmentInfo
name|exam
init|=
name|change
index|[
name|form
operator|.
name|getReverse
argument_list|()
condition|?
literal|0
else|:
literal|1
index|]
decl_stmt|;
name|String
name|period
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|ToolBox
operator|.
name|equals
argument_list|(
name|old
operator|.
name|getPeriodId
argument_list|()
argument_list|,
name|exam
operator|.
name|getPeriodId
argument_list|()
argument_list|)
condition|)
block|{
name|period
operator|=
operator|(
name|html
condition|?
name|exam
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
else|:
name|exam
operator|.
name|getPeriodAbbreviation
argument_list|()
operator|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|html
condition|)
block|{
name|period
operator|=
operator|(
name|old
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
else|:
name|old
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
operator|)
expr_stmt|;
name|period
operator|+=
literal|"&rarr; "
expr_stmt|;
name|period
operator|+=
operator|(
name|exam
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
else|:
name|exam
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
operator|)
expr_stmt|;
block|}
else|else
block|{
name|period
operator|=
operator|(
name|old
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"@@ITALIC not-assigned @END_ITALIC"
else|:
name|old
operator|.
name|getPeriodAbbreviation
argument_list|()
operator|)
expr_stmt|;
name|period
operator|+=
literal|" -> "
expr_stmt|;
name|period
operator|+=
operator|(
name|exam
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"@@ITALIC not-assigned @@END_ITALIC"
else|:
name|exam
operator|.
name|getPeriodAbbreviation
argument_list|()
operator|)
expr_stmt|;
block|}
block|}
name|String
name|room
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|ToolBox
operator|.
name|equals
argument_list|(
name|old
operator|.
name|getRooms
argument_list|()
argument_list|,
name|exam
operator|.
name|getRooms
argument_list|()
argument_list|)
condition|)
block|{
name|room
operator|=
operator|(
name|html
condition|?
name|exam
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|", "
argument_list|)
else|:
name|exam
operator|.
name|getRoomsName
argument_list|(
literal|", "
argument_list|)
operator|)
expr_stmt|;
block|}
if|else if
condition|(
name|exam
operator|.
name|getMaxRooms
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|html
condition|)
block|{
name|room
operator|+=
literal|"<table border='0'><tr><td valign='middle'>"
expr_stmt|;
name|room
operator|+=
operator|(
name|old
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
else|:
name|old
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|"<br>"
argument_list|)
operator|)
expr_stmt|;
name|room
operator|+=
literal|"</td><td valign='middle'>&rarr;</td><td valign='middle'>"
expr_stmt|;
name|room
operator|+=
operator|(
name|exam
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'><i>not-assigned</i></font>"
else|:
name|exam
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|"<br>"
argument_list|)
operator|)
expr_stmt|;
name|room
operator|+=
literal|"</td></tr></table>"
expr_stmt|;
block|}
else|else
block|{
name|room
operator|=
operator|(
name|old
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"@@ITALIC not-assigned @END_ITALIC"
else|:
name|old
operator|.
name|getRoomsName
argument_list|(
literal|", "
argument_list|)
operator|)
expr_stmt|;
name|room
operator|+=
literal|" -> "
expr_stmt|;
name|room
operator|+=
operator|(
name|exam
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"@@ITALIC not-assigned @@END_ITALIC"
else|:
name|exam
operator|.
name|getRoomsName
argument_list|(
literal|", "
argument_list|)
operator|)
expr_stmt|;
block|}
block|}
name|int
name|xdc
init|=
name|exam
operator|.
name|getNrDirectConflicts
argument_list|()
decl_stmt|;
name|int
name|dc
init|=
name|xdc
operator|-
name|old
operator|.
name|getNrDirectConflicts
argument_list|()
decl_stmt|;
name|String
name|dcStr
init|=
operator|(
name|xdc
operator|<=
literal|0
condition|?
literal|""
else|:
name|html
condition|?
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"P"
argument_list|)
operator|+
literal|"'>"
operator|+
name|xdc
operator|+
literal|"</font>"
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|xdc
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|html
operator|&&
name|dc
operator|<
literal|0
condition|)
name|dcStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"R"
argument_list|)
operator|+
literal|"'> ("
operator|+
name|dc
operator|+
literal|")</font>"
expr_stmt|;
if|if
condition|(
name|html
operator|&&
name|dc
operator|>
literal|0
condition|)
name|dcStr
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
literal|"'> (+"
operator|+
name|dc
operator|+
literal|")</font>"
expr_stmt|;
if|if
condition|(
operator|!
name|html
operator|&&
name|dc
operator|<
literal|0
condition|)
name|dcStr
operator|+=
literal|" ("
operator|+
name|dc
operator|+
literal|")"
expr_stmt|;
if|if
condition|(
operator|!
name|html
operator|&&
name|dc
operator|>
literal|0
condition|)
name|dcStr
operator|+=
literal|" (+"
operator|+
name|dc
operator|+
literal|")"
expr_stmt|;
name|int
name|xm2d
init|=
name|exam
operator|.
name|getNrMoreThanTwoConflicts
argument_list|()
decl_stmt|;
name|int
name|m2d
init|=
name|exam
operator|.
name|getNrMoreThanTwoConflicts
argument_list|()
operator|-
name|old
operator|.
name|getNrMoreThanTwoConflicts
argument_list|()
decl_stmt|;
name|String
name|m2dStr
init|=
operator|(
name|xm2d
operator|<=
literal|0
condition|?
literal|""
else|:
name|html
condition|?
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"2"
argument_list|)
operator|+
literal|"'>"
operator|+
name|xm2d
operator|+
literal|"</font>"
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|xm2d
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|html
operator|&&
name|m2d
operator|<
literal|0
condition|)
name|m2dStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"-2"
argument_list|)
operator|+
literal|"'> ("
operator|+
name|m2d
operator|+
literal|")</font>"
expr_stmt|;
if|if
condition|(
name|html
operator|&&
name|m2d
operator|>
literal|0
condition|)
name|m2dStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"2"
argument_list|)
operator|+
literal|"'> (+"
operator|+
name|m2d
operator|+
literal|")</font>"
expr_stmt|;
if|if
condition|(
operator|!
name|html
operator|&&
name|m2d
operator|<
literal|0
condition|)
name|m2dStr
operator|+=
literal|" ("
operator|+
name|m2d
operator|+
literal|")"
expr_stmt|;
if|if
condition|(
operator|!
name|html
operator|&&
name|m2d
operator|>
literal|0
condition|)
name|m2dStr
operator|+=
literal|" (+"
operator|+
name|m2d
operator|+
literal|")"
expr_stmt|;
name|int
name|xbtb
init|=
name|exam
operator|.
name|getNrBackToBackConflicts
argument_list|()
decl_stmt|;
name|int
name|btb
init|=
name|exam
operator|.
name|getNrBackToBackConflicts
argument_list|()
operator|-
name|old
operator|.
name|getNrBackToBackConflicts
argument_list|()
decl_stmt|;
name|int
name|dbtb
init|=
name|exam
operator|.
name|getNrDistanceBackToBackConflicts
argument_list|()
operator|-
name|old
operator|.
name|getNrDistanceBackToBackConflicts
argument_list|()
decl_stmt|;
name|String
name|btbStr
init|=
operator|(
name|xbtb
operator|<=
literal|0
condition|?
literal|""
else|:
name|html
condition|?
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"1"
argument_list|)
operator|+
literal|"'>"
operator|+
name|xbtb
operator|+
literal|"</font>"
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|xbtb
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|html
condition|)
block|{
if|if
condition|(
name|btb
operator|<
literal|0
condition|)
name|btbStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"-1"
argument_list|)
operator|+
literal|"'> ("
operator|+
name|btb
operator|+
literal|"</font>"
expr_stmt|;
if|else if
condition|(
name|btb
operator|>
literal|0
condition|)
name|btbStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"1"
argument_list|)
operator|+
literal|"'> (+"
operator|+
name|btb
operator|+
literal|"</font>"
expr_stmt|;
if|else if
condition|(
name|dbtb
operator|!=
literal|0
condition|)
name|btbStr
operator|+=
literal|" ("
operator|+
name|String
operator|.
name|valueOf
argument_list|(
name|btb
argument_list|)
expr_stmt|;
if|if
condition|(
name|dbtb
operator|<
literal|0
condition|)
name|btbStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"-1"
argument_list|)
operator|+
literal|"'> d:"
operator|+
name|dbtb
operator|+
literal|"</font>"
expr_stmt|;
if|if
condition|(
name|dbtb
operator|>
literal|0
condition|)
name|btbStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"1"
argument_list|)
operator|+
literal|"'> d:+"
operator|+
name|dbtb
operator|+
literal|"</font>"
expr_stmt|;
if|if
condition|(
name|btb
operator|<
literal|0
condition|)
name|btbStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"-1"
argument_list|)
operator|+
literal|"'>)</font>"
expr_stmt|;
if|else if
condition|(
name|btb
operator|>
literal|0
condition|)
name|btbStr
operator|+=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
literal|"1"
argument_list|)
operator|+
literal|"'>)</font>"
expr_stmt|;
if|else if
condition|(
name|dbtb
operator|!=
literal|0
condition|)
name|btbStr
operator|+=
literal|")"
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|btb
operator|<
literal|0
condition|)
name|btbStr
operator|+=
literal|" ("
operator|+
name|btb
expr_stmt|;
if|else if
condition|(
name|btb
operator|>
literal|0
condition|)
name|btbStr
operator|+=
literal|" (+"
operator|+
name|btb
expr_stmt|;
if|else if
condition|(
name|dbtb
operator|!=
literal|0
condition|)
name|btbStr
operator|+=
literal|" ("
operator|+
name|String
operator|.
name|valueOf
argument_list|(
name|btb
argument_list|)
expr_stmt|;
if|if
condition|(
name|dbtb
operator|<
literal|0
condition|)
name|btbStr
operator|+=
literal|" d:"
operator|+
name|dbtb
expr_stmt|;
if|if
condition|(
name|dbtb
operator|>
literal|0
condition|)
name|btbStr
operator|+=
literal|" d:+"
operator|+
name|dbtb
expr_stmt|;
if|if
condition|(
name|btb
operator|<
literal|0
condition|)
name|btbStr
operator|+=
literal|")"
expr_stmt|;
if|else if
condition|(
name|btb
operator|>
literal|0
condition|)
name|btbStr
operator|+=
literal|")"
expr_stmt|;
if|else if
condition|(
name|dbtb
operator|!=
literal|0
condition|)
name|btbStr
operator|+=
literal|")"
expr_stmt|;
block|}
name|table
operator|.
name|addLine
argument_list|(
literal|"onClick=\"showGwtDialog('Examination Assignment', 'examInfo.do?examId="
operator|+
name|exam
operator|.
name|getExamId
argument_list|()
operator|+
literal|"','900','90%');\""
argument_list|,
operator|new
name|String
index|[]
block|{
operator|(
name|html
condition|?
literal|"<a name='"
operator|+
name|exam
operator|.
name|getExamId
argument_list|()
operator|+
literal|"'>"
else|:
literal|""
operator|)
operator|+
operator|(
name|form
operator|.
name|getShowSections
argument_list|()
condition|?
name|exam
operator|.
name|getSectionName
argument_list|(
name|nl
argument_list|)
else|:
name|exam
operator|.
name|getExamName
argument_list|()
operator|)
operator|+
operator|(
name|html
condition|?
literal|"</a>"
else|:
literal|""
operator|)
block|,
name|period
block|,
name|room
block|,
operator|(
name|Exam
operator|.
name|sSeatingTypeNormal
operator|==
name|exam
operator|.
name|getSeatingType
argument_list|()
condition|?
literal|"Normal"
else|:
literal|"Exam"
operator|)
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|exam
operator|.
name|getNrStudents
argument_list|()
argument_list|)
block|,
name|exam
operator|.
name|getInstructorName
argument_list|(
literal|", "
argument_list|)
block|,
name|dcStr
block|,
name|m2dStr
block|,
name|btbStr
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|exam
block|,
operator|(
name|exam
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
name|old
operator|.
name|getPeriodOrd
argument_list|()
else|:
name|exam
operator|.
name|getPeriodOrd
argument_list|()
operator|)
block|,
operator|(
name|exam
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|?
literal|"0"
operator|+
name|old
operator|.
name|getRoomsName
argument_list|(
literal|":"
argument_list|)
else|:
name|exam
operator|.
name|getRoomsName
argument_list|(
literal|":"
argument_list|)
operator|)
block|,
name|exam
operator|.
name|getSeatingType
argument_list|()
block|,
name|exam
operator|.
name|getNrStudents
argument_list|()
block|,
name|exam
operator|.
name|getInstructorName
argument_list|(
literal|":"
argument_list|)
block|,
name|dc
block|,
name|m2d
block|,
name|btb
block|}
argument_list|,
name|exam
operator|.
name|getExamId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|table
operator|.
name|addLine
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"<font color='red'>ERROR:"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"</font>"
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
return|return
name|table
return|;
block|}
block|}
end_class

end_unit

