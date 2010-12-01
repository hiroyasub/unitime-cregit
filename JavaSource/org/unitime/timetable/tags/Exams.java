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
name|tags
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
name|Enumeration
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
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|JspException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|BodyTagSupport
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
name|User
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
name|Web
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
name|model
operator|.
name|BuildingPref
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
name|Class_
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
name|CourseOffering
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
name|DistributionPref
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
name|MidtermPeriodPreferenceModel
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
name|ExamPeriodPref
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
name|InstrOfferingConfig
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
name|InstructionalOffering
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
name|PeriodPreferenceModel
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
name|model
operator|.
name|RoomFeaturePref
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
name|RoomGroupPref
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
name|RoomPref
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
name|Session
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
name|Settings
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
name|TimetableManager
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
name|Constants
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
name|RequiredTimeTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Exams
extends|extends
name|BodyTagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|666904499562226756L
decl_stmt|;
specifier|private
name|String
name|iType
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAdd
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iId
init|=
literal|null
decl_stmt|;
specifier|public
name|Exams
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setAdd
parameter_list|(
name|boolean
name|add
parameter_list|)
block|{
name|iAdd
operator|=
name|add
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAdd
parameter_list|()
block|{
return|return
name|iAdd
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|int
name|doStartTag
parameter_list|()
throws|throws
name|JspException
block|{
return|return
name|EVAL_BODY_BUFFERED
return|;
block|}
specifier|public
name|int
name|doEndTag
parameter_list|()
throws|throws
name|JspException
block|{
try|try
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|pageContext
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
condition|)
return|return
name|EVAL_PAGE
return|;
name|TimetableManager
name|manager
init|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|String
name|objectIdStr
init|=
operator|(
name|getBodyContent
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getBodyContent
argument_list|()
operator|.
name|getString
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|objectIdStr
operator|==
literal|null
operator|||
name|objectIdStr
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|objectIdStr
operator|=
operator|(
name|getId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|getId
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
expr_stmt|;
if|if
condition|(
name|objectIdStr
operator|==
literal|null
operator|||
name|objectIdStr
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|EVAL_PAGE
return|;
name|Long
name|objectId
init|=
name|Long
operator|.
name|parseLong
argument_list|(
name|objectIdStr
argument_list|)
decl_stmt|;
name|boolean
name|edit
init|=
operator|(
name|manager
operator|==
literal|null
operator|||
name|session
operator|==
literal|null
condition|?
literal|false
else|:
name|manager
operator|.
name|canEditExams
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
operator|)
decl_stmt|;
name|boolean
name|view
init|=
operator|(
name|manager
operator|==
literal|null
operator|||
name|session
operator|==
literal|null
condition|?
literal|false
else|:
name|manager
operator|.
name|canSeeExams
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
operator|)
decl_stmt|;
name|List
name|exams
init|=
name|Exam
operator|.
name|findAllRelated
argument_list|(
name|getType
argument_list|()
argument_list|,
name|objectId
argument_list|)
decl_stmt|;
if|if
condition|(
name|exams
operator|==
literal|null
operator|||
name|exams
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|edit
operator|||
operator|!
name|iAdd
condition|)
return|return
name|EVAL_PAGE
return|;
block|}
name|String
name|title
init|=
operator|(
name|exams
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|?
literal|"Examination"
else|:
literal|"Examinations"
operator|)
decl_stmt|;
if|if
condition|(
name|edit
operator|&&
name|iAdd
condition|)
name|title
operator|=
literal|"<table width='100%'><tr><td width='100%'>"
operator|+
literal|"<DIV class=\"WelcomeRowHeadNoLine\">Examinations</DIV>"
operator|+
literal|"</td><td style='padding-bottom: 2px'>"
operator|+
literal|"<input type=\"button\" onclick=\"document.location='examEdit.do?firstType="
operator|+
name|getType
argument_list|()
operator|+
literal|"&firstId="
operator|+
name|objectId
operator|+
literal|"';\" class=\"btn\" accesskey='X' title='Add Examination (Alt+X)' value=\"Add Examination\">"
operator|+
literal|"</td></tr></table>"
expr_stmt|;
name|WebTable
name|table
init|=
operator|new
name|WebTable
argument_list|(
literal|10
argument_list|,
name|title
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Classes / Courses"
block|,
literal|"Type"
block|,
literal|"Length"
block|,
literal|"Seating<br>Type"
block|,
literal|"Size"
block|,
literal|"Max<br>Rooms"
block|,
literal|"Instructor"
block|,
literal|"Period<br>Preferences"
block|,
literal|"Room<br>Preferences"
block|,
literal|"Distribution<br>Preferences"
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
literal|"right"
block|,
literal|"center"
block|,
literal|"right"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
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
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|boolean
name|timeVertical
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridVertical
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|boolean
name|timeText
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridAsText
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|String
name|instructorNameFormat
init|=
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_NAME_FORMAT
argument_list|)
decl_stmt|;
name|String
name|backId
init|=
literal|null
decl_stmt|;
if|if
condition|(
literal|"PreferenceGroup"
operator|.
name|equals
argument_list|(
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getParameter
argument_list|(
literal|"backType"
argument_list|)
argument_list|)
condition|)
name|backId
operator|=
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
expr_stmt|;
if|if
condition|(
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"examId"
argument_list|)
operator|!=
literal|null
condition|)
name|backId
operator|=
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"examId"
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|boolean
name|hasExamHash
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|exams
operator|!=
literal|null
condition|)
block|{
name|ExamSolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|pageContext
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|solverType
init|=
operator|(
name|solver
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|solver
operator|.
name|getExamType
argument_list|()
operator|)
decl_stmt|;
name|boolean
name|hasSolution
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|exams
argument_list|)
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
literal|null
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
operator|&&
name|solverType
operator|==
name|exam
operator|.
name|getExamType
argument_list|()
condition|)
name|assignment
operator|=
name|solver
operator|.
name|getAssignment
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|)
name|assignment
operator|=
operator|new
name|ExamAssignment
argument_list|(
name|exam
argument_list|)
expr_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
operator|&&
name|assignment
operator|.
name|getPeriodId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|hasSolution
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|view
condition|)
block|{
if|if
condition|(
name|hasSolution
condition|)
name|table
operator|=
operator|new
name|WebTable
argument_list|(
literal|10
argument_list|,
name|title
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Classes / Courses"
block|,
literal|"Type"
block|,
literal|"Length"
block|,
literal|"Seating<br>Type"
block|,
literal|"Size"
block|,
literal|"Max<br>Rooms"
block|,
literal|"Instructor"
block|,
literal|"Assigned<br>Period"
block|,
literal|"Assigned<br>Room"
block|,
literal|"Student<br>Conflicts"
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
literal|"right"
block|,
literal|"center"
block|,
literal|"right"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
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
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|exams
argument_list|)
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
name|String
name|objects
init|=
literal|""
decl_stmt|,
name|instructors
init|=
literal|""
decl_stmt|,
name|perPref
init|=
literal|""
decl_stmt|,
name|roomPref
init|=
literal|""
decl_stmt|,
name|distPref
init|=
literal|""
decl_stmt|;
name|ExamAssignmentInfo
name|assignment
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
operator|&&
name|solverType
operator|==
name|exam
operator|.
name|getExamType
argument_list|()
condition|)
name|assignment
operator|=
name|solver
operator|.
name|getAssignmentInfo
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|)
name|assignment
operator|=
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|exam
operator|.
name|getOwnerObjects
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
name|Object
name|object
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|objects
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|objects
operator|+=
literal|"<br>"
expr_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|Class_
condition|)
name|objects
operator|+=
operator|(
operator|(
name|Class_
operator|)
name|object
operator|)
operator|.
name|getClassLabel
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|InstrOfferingConfig
condition|)
name|objects
operator|+=
operator|(
operator|(
name|InstrOfferingConfig
operator|)
name|object
operator|)
operator|.
name|toString
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|InstructionalOffering
condition|)
name|objects
operator|+=
operator|(
operator|(
name|InstructionalOffering
operator|)
name|object
operator|)
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|CourseOffering
condition|)
name|objects
operator|+=
operator|(
operator|(
name|CourseOffering
operator|)
name|object
operator|)
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
else|else
name|objects
operator|+=
name|object
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|hasSolution
operator|||
name|assignment
operator|==
literal|null
operator|||
name|assignment
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|)
block|{
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
operator|!
name|roomPref
operator|.
name|endsWith
argument_list|(
literal|"<br>"
argument_list|)
condition|)
name|roomPref
operator|+=
literal|"<br>"
expr_stmt|;
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|BuildingPref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
operator|!
name|roomPref
operator|.
name|endsWith
argument_list|(
literal|"<br>"
argument_list|)
condition|)
name|roomPref
operator|+=
literal|"<br>"
expr_stmt|;
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|RoomFeaturePref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
operator|!
name|roomPref
operator|.
name|endsWith
argument_list|(
literal|"<br>"
argument_list|)
condition|)
name|roomPref
operator|+=
literal|"<br>"
expr_stmt|;
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|RoomGroupPref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|endsWith
argument_list|(
literal|"<br>"
argument_list|)
condition|)
name|roomPref
operator|=
name|roomPref
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|roomPref
operator|.
name|length
argument_list|()
operator|-
literal|"<br>"
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|timeText
operator|||
name|Exam
operator|.
name|sExamTypeMidterm
operator|==
name|exam
operator|.
name|getExamType
argument_list|()
condition|)
block|{
if|if
condition|(
name|Exam
operator|.
name|sExamTypeMidterm
operator|==
name|exam
operator|.
name|getExamType
argument_list|()
condition|)
block|{
name|MidtermPeriodPreferenceModel
name|epx
init|=
operator|new
name|MidtermPeriodPreferenceModel
argument_list|(
name|exam
operator|.
name|getSession
argument_list|()
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|epx
operator|.
name|load
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|perPref
operator|+=
name|epx
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|perPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|PeriodPreferenceModel
name|px
init|=
operator|new
name|PeriodPreferenceModel
argument_list|(
name|exam
operator|.
name|getSession
argument_list|()
argument_list|,
literal|null
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
argument_list|)
decl_stmt|;
name|px
operator|.
name|load
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|RequiredTimeTable
name|rtt
init|=
operator|new
name|RequiredTimeTable
argument_list|(
name|px
argument_list|)
decl_stmt|;
name|File
name|imageFileName
init|=
literal|null
decl_stmt|;
try|try
block|{
name|imageFileName
operator|=
name|rtt
operator|.
name|createImage
argument_list|(
name|timeVertical
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|String
name|rttTitle
init|=
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|imageFileName
operator|!=
literal|null
condition|)
name|perPref
operator|=
literal|"<img border='0' src='temp/"
operator|+
operator|(
name|imageFileName
operator|.
name|getName
argument_list|()
operator|)
operator|+
literal|"' title='"
operator|+
name|rttTitle
operator|+
literal|"'>"
expr_stmt|;
else|else
name|perPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|hasSolution
condition|)
name|distPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
expr_stmt|;
else|else
name|distPref
operator|=
literal|"<i>Not Assigned</i>"
expr_stmt|;
block|}
else|else
block|{
name|perPref
operator|=
operator|(
name|view
condition|?
name|assignment
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
else|:
name|assignment
operator|.
name|getPeriodAbbreviation
argument_list|()
operator|)
expr_stmt|;
name|roomPref
operator|=
operator|(
name|view
condition|?
name|assignment
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|"<br>"
argument_list|)
else|:
name|assignment
operator|.
name|getRoomsName
argument_list|(
literal|"<br>"
argument_list|)
operator|)
expr_stmt|;
name|int
name|dc
init|=
name|assignment
operator|.
name|getNrDirectConflicts
argument_list|()
decl_stmt|;
name|String
name|dcStr
init|=
operator|(
name|dc
operator|<=
literal|0
condition|?
literal|"<font color='gray'>0</font>"
else|:
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
name|dc
operator|+
literal|"</font>"
operator|)
decl_stmt|;
name|int
name|m2d
init|=
name|assignment
operator|.
name|getNrMoreThanTwoConflicts
argument_list|()
decl_stmt|;
name|String
name|m2dStr
init|=
operator|(
name|m2d
operator|<=
literal|0
condition|?
literal|"<font color='gray'>0</font>"
else|:
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
name|m2d
operator|+
literal|"</font>"
operator|)
decl_stmt|;
name|int
name|btb
init|=
name|assignment
operator|.
name|getNrBackToBackConflicts
argument_list|()
decl_stmt|;
name|int
name|dbtb
init|=
name|assignment
operator|.
name|getNrDistanceBackToBackConflicts
argument_list|()
decl_stmt|;
name|String
name|btbStr
init|=
operator|(
name|btb
operator|<=
literal|0
operator|&&
name|dbtb
operator|<=
literal|0
condition|?
literal|"<font color='gray'>0</font>"
else|:
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
name|btb
operator|+
operator|(
name|dbtb
operator|>
literal|0
condition|?
literal|" (d:"
operator|+
name|dbtb
operator|+
literal|")"
else|:
literal|""
operator|)
operator|+
literal|"</font>"
operator|)
decl_stmt|;
name|distPref
operator|=
operator|(
name|view
condition|?
name|dcStr
operator|+
literal|", "
operator|+
name|m2dStr
operator|+
literal|", "
operator|+
name|btbStr
else|:
literal|"<i>N/A</i>"
operator|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
operator|new
name|TreeSet
argument_list|(
name|exam
operator|.
name|getInstructors
argument_list|()
argument_list|)
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
name|DepartmentalInstructor
name|instructor
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|instructors
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|instructors
operator|+=
literal|"<br>"
expr_stmt|;
name|instructors
operator|+=
name|instructor
operator|.
name|getName
argument_list|(
name|instructorNameFormat
argument_list|)
expr_stmt|;
block|}
name|int
name|nrStudents
init|=
name|exam
operator|.
name|getSize
argument_list|()
decl_stmt|;
if|if
condition|(
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|backId
argument_list|)
condition|)
block|{
name|objects
operator|=
literal|"<A name='examHash'>"
operator|+
name|objects
operator|+
literal|"</A>"
expr_stmt|;
name|hasExamHash
operator|=
literal|true
expr_stmt|;
block|}
name|table
operator|.
name|addLine
argument_list|(
operator|(
name|view
condition|?
literal|"onClick=\"document.location='examDetail.do?examId="
operator|+
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
operator|)
argument_list|,
operator|new
name|String
index|[]
block|{
name|objects
block|,
name|Exam
operator|.
name|sExamTypes
index|[
name|exam
operator|.
name|getExamType
argument_list|()
index|]
block|,
name|exam
operator|.
name|getLength
argument_list|()
operator|.
name|toString
argument_list|()
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
name|nrStudents
argument_list|)
block|,
name|exam
operator|.
name|getMaxNbrRooms
argument_list|()
operator|.
name|toString
argument_list|()
block|,
name|instructors
block|,
name|perPref
block|,
name|roomPref
block|,
name|distPref
block|}
argument_list|,
literal|null
argument_list|,
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
operator|!
name|hasSolution
condition|)
return|return
name|EVAL_PAGE
return|;
name|table
operator|=
operator|new
name|WebTable
argument_list|(
literal|5
argument_list|,
name|title
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Classes / Courses"
block|,
literal|"Type"
block|,
literal|"Instructor"
block|,
literal|"Period"
block|,
literal|"Room"
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
literal|"left"
block|,
literal|"left"
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
literal|true
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|exams
argument_list|)
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
name|String
name|objects
init|=
literal|""
decl_stmt|,
name|instructors
init|=
literal|""
decl_stmt|,
name|perPref
init|=
literal|""
decl_stmt|,
name|roomPref
init|=
literal|""
decl_stmt|;
name|ExamAssignmentInfo
name|assignment
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
operator|&&
name|solverType
operator|==
name|exam
operator|.
name|getExamType
argument_list|()
condition|)
name|assignment
operator|=
name|solver
operator|.
name|getAssignmentInfo
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|else if
condition|(
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|)
name|assignment
operator|=
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|)
expr_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|exam
operator|.
name|getOwnerObjects
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
name|Object
name|object
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|objects
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|objects
operator|+=
literal|"<br>"
expr_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|Class_
condition|)
name|objects
operator|+=
operator|(
operator|(
name|Class_
operator|)
name|object
operator|)
operator|.
name|getClassLabel
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|InstrOfferingConfig
condition|)
name|objects
operator|+=
operator|(
operator|(
name|InstrOfferingConfig
operator|)
name|object
operator|)
operator|.
name|toString
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|InstructionalOffering
condition|)
name|objects
operator|+=
operator|(
operator|(
name|InstructionalOffering
operator|)
name|object
operator|)
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|CourseOffering
condition|)
name|objects
operator|+=
operator|(
operator|(
name|CourseOffering
operator|)
name|object
operator|)
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
else|else
name|objects
operator|+=
name|object
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|==
literal|null
operator|||
name|assignment
operator|.
name|getPeriodId
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|perPref
operator|=
name|assignment
operator|.
name|getPeriodName
argument_list|()
expr_stmt|;
name|roomPref
operator|=
name|assignment
operator|.
name|getRoomsName
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
operator|new
name|TreeSet
argument_list|(
name|exam
operator|.
name|getInstructors
argument_list|()
argument_list|)
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
name|DepartmentalInstructor
name|instructor
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|instructors
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|instructors
operator|+=
literal|"<br>"
expr_stmt|;
name|instructors
operator|+=
name|instructor
operator|.
name|getName
argument_list|(
name|instructorNameFormat
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|backId
argument_list|)
condition|)
block|{
name|objects
operator|=
literal|"<A name='examHash'>"
operator|+
name|objects
operator|+
literal|"</A>"
expr_stmt|;
name|hasExamHash
operator|=
literal|true
expr_stmt|;
block|}
name|table
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|objects
block|,
name|Exam
operator|.
name|sExamTypes
index|[
name|exam
operator|.
name|getExamType
argument_list|()
index|]
block|,
name|instructors
block|,
name|perPref
block|,
name|roomPref
block|}
argument_list|,
literal|null
argument_list|,
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<table width='100%' border='0' cellspacing='0' cellpadding='3'>"
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Exam"
operator|.
name|equals
argument_list|(
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getParameter
argument_list|(
literal|"backType"
argument_list|)
argument_list|)
condition|)
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<tr><td colpan='9'><A name='examHash'>&nbsp;</A></td></tr>"
argument_list|)
expr_stmt|;
name|hasExamHash
operator|=
literal|true
expr_stmt|;
block|}
else|else
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<tr><td colpan='9'>&nbsp;</td></tr>"
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|table
operator|.
name|printTable
argument_list|()
argument_list|)
expr_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"</table>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasExamHash
condition|)
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<SCRIPT type='text/javascript' language='javascript'>location.hash = 'examHash';</SCRIPT>"
argument_list|)
expr_stmt|;
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
try|try
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|print
argument_list|(
literal|"<font color='red'>ERROR: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"</font>"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|io
parameter_list|)
block|{
block|}
block|}
return|return
name|EVAL_PAGE
return|;
block|}
block|}
end_class

end_unit

