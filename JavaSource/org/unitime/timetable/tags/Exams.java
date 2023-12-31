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
name|tags
package|;
end_package

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
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|defaults
operator|.
name|CommonValues
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
name|defaults
operator|.
name|UserProperty
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
name|ExamType
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
name|context
operator|.
name|HttpSessionContext
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

begin_comment
comment|/**  * @author Tomas Muller, Zuzana Mullerova  */
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
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
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
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|pageContext
operator|.
name|getServletContext
argument_list|()
argument_list|)
return|;
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
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Examinations
argument_list|)
operator|&&
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ExaminationSchedule
argument_list|)
condition|)
return|return
name|EVAL_PAGE
return|;
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
for|for
control|(
name|Iterator
argument_list|<
name|Exam
argument_list|>
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
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|,
name|Right
operator|.
name|ExaminationView
argument_list|)
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
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
name|iAdd
operator|||
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ExaminationAdd
argument_list|)
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
name|MSG
operator|.
name|sectionTitleExamination
argument_list|()
else|:
name|MSG
operator|.
name|sectionTitleExaminations
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|iAdd
operator|&&
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ExaminationAdd
argument_list|)
condition|)
name|title
operator|=
literal|"<table width='100%'><tr><td width='100%'>"
operator|+
literal|"<DIV class=\"WelcomeRowHeadNoLine\">"
operator|+
name|MSG
operator|.
name|sectionTitleExaminations
argument_list|()
operator|+
literal|"</DIV>"
operator|+
literal|"</td><td style='padding-bottom: 2px'>"
operator|+
literal|"<input type=\"button\" onclick=\"document.location='examEdit.action?firstType="
operator|+
name|getType
argument_list|()
operator|+
literal|"&firstId="
operator|+
name|objectId
operator|+
literal|"';\" "
operator|+
literal|"class=\"btn\" accesskey='"
operator|+
name|MSG
operator|.
name|accessAddExamination
argument_list|()
operator|+
literal|"' title='"
operator|+
name|MSG
operator|.
name|titleAddExamination
argument_list|(
name|MSG
operator|.
name|accessAddExamination
argument_list|()
argument_list|)
operator|+
literal|"' value='"
operator|+
name|MSG
operator|.
name|actionAddExamination
argument_list|()
operator|+
literal|"'>"
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
name|MSG
operator|.
name|columnExamClassesCourses
argument_list|()
block|,
name|MSG
operator|.
name|columnExamType
argument_list|()
block|,
name|MSG
operator|.
name|columnExamLength
argument_list|()
block|,
name|MSG
operator|.
name|columnExamSeatingType
argument_list|()
block|,
name|MSG
operator|.
name|columnExamSize
argument_list|()
block|,
name|MSG
operator|.
name|columnExamMaxRooms
argument_list|()
block|,
name|MSG
operator|.
name|columnExamInstructor
argument_list|()
block|,
name|MSG
operator|.
name|columnExamPeriodPreferences
argument_list|()
block|,
name|MSG
operator|.
name|columnExamRoomPreferences
argument_list|()
block|,
name|MSG
operator|.
name|columnExamDistributionPreferences
argument_list|()
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
name|CommonValues
operator|.
name|VerticalGrid
operator|.
name|eq
argument_list|(
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|timeText
init|=
name|CommonValues
operator|.
name|TextGrid
operator|.
name|eq
argument_list|(
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|instructorNameFormat
init|=
name|getSessionContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|NameFormat
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
name|Long
name|solverType
init|=
operator|(
name|solver
operator|==
literal|null
condition|?
literal|null
else|:
name|solver
operator|.
name|getExamTypeId
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
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|solverType
argument_list|)
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
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Examinations
argument_list|)
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
name|MSG
operator|.
name|columnExamClassesCourses
argument_list|()
block|,
name|MSG
operator|.
name|columnExamType
argument_list|()
block|,
name|MSG
operator|.
name|columnExamLength
argument_list|()
block|,
name|MSG
operator|.
name|columnExamSeatingType
argument_list|()
block|,
name|MSG
operator|.
name|columnExamSize
argument_list|()
block|,
name|MSG
operator|.
name|columnExamMaxRooms
argument_list|()
block|,
name|MSG
operator|.
name|columnExamInstructor
argument_list|()
block|,
name|MSG
operator|.
name|columnExamAssignedPeriod
argument_list|()
block|,
name|MSG
operator|.
name|columnExamAssignedRoom
argument_list|()
block|,
name|MSG
operator|.
name|columnExamStudentConflicts
argument_list|()
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
name|boolean
name|view
init|=
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|exam
argument_list|,
name|Right
operator|.
name|ExaminationDetail
argument_list|)
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
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|solverType
argument_list|)
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
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getType
argument_list|()
operator|==
name|ExamType
operator|.
name|sExamTypeMidterm
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
name|exam
operator|.
name|getExamType
argument_list|()
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
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|timeText
condition|)
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
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
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
name|perPref
operator|=
literal|"<img border='0' src='pattern?v="
operator|+
operator|(
name|timeVertical
condition|?
literal|1
else|:
literal|0
operator|)
operator|+
literal|"&x="
operator|+
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"' title='"
operator|+
name|px
operator|.
name|toString
argument_list|()
operator|+
literal|"'>"
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
literal|"onClick=\"document.location='examDetail.action?examId="
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
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getLabel
argument_list|()
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
name|MSG
operator|.
name|examSeatingTypeNormal
argument_list|()
else|:
name|MSG
operator|.
name|examSeatingTypeExam
argument_list|()
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
name|MSG
operator|.
name|columnExamClassesCourses
argument_list|()
block|,
name|MSG
operator|.
name|columnExamType
argument_list|()
block|,
name|MSG
operator|.
name|columnExamInstructor
argument_list|()
block|,
name|MSG
operator|.
name|columnExamPeriod
argument_list|()
block|,
name|MSG
operator|.
name|columnExamRoom
argument_list|()
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
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|solverType
argument_list|)
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
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getLabel
argument_list|()
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

