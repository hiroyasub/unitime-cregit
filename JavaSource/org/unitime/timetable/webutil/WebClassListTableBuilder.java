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
name|webutil
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
name|ArrayList
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
name|JspWriter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|util
operator|.
name|HtmlUtils
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
name|htmlgen
operator|.
name|TableCell
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
name|htmlgen
operator|.
name|TableStream
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
name|form
operator|.
name|ClassListForm
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
name|ExamOwner
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
name|InstructionalMethod
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
name|LearningManagementSystemInfo
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
name|PreferenceGroup
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
name|SchedulingSubpart
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
name|StudentClassEnrollment
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
name|SubjectArea
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
name|comparators
operator|.
name|ClassComparator
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
name|comparators
operator|.
name|ClassCourseComparator
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
name|comparators
operator|.
name|SchedulingSubpartComparator
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
name|SchedulingSubpartDAO
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
name|UserContext
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
name|CachedClassAssignmentProxy
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
name|ClassAssignmentProxy
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
name|ExamAssignmentProxy
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer, Tomas Muller, Zuzana Mullerova  *  */
end_comment

begin_class
specifier|public
class|class
name|WebClassListTableBuilder
extends|extends
name|WebInstructionalOfferingTableBuilder
block|{
specifier|protected
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
specifier|protected
name|String
name|getSchedulePrintNoteLabel
parameter_list|()
block|{
return|return
name|MSG
operator|.
name|columnStudentScheduleNote
argument_list|()
return|;
block|}
comment|/** 	 *  	 */
specifier|public
name|WebClassListTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|String
name|additionalNote
parameter_list|()
block|{
return|return
operator|(
operator|new
name|String
argument_list|()
operator|)
return|;
block|}
specifier|protected
name|String
name|labelForTable
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<p style=\"page-break-before: always\" class=\"WelcomeRowHead\"><b><font size=\"+1\">"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|subjectArea
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" - "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|subjectArea
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|additionalNote
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</font></b></p>"
argument_list|)
expr_stmt|;
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|htmlTableForClasses
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|ClassListForm
name|form
parameter_list|,
name|JspWriter
name|outputStream
parameter_list|,
name|String
name|backType
parameter_list|,
name|String
name|backId
parameter_list|)
block|{
name|this
operator|.
name|setVisibleColumns
argument_list|(
name|form
argument_list|)
expr_stmt|;
name|setBackType
argument_list|(
name|backType
argument_list|)
expr_stmt|;
name|setBackId
argument_list|(
name|backId
argument_list|)
expr_stmt|;
name|TreeSet
name|classes
init|=
operator|(
name|TreeSet
operator|)
name|form
operator|.
name|getClasses
argument_list|()
decl_stmt|;
name|Navigation
operator|.
name|set
argument_list|(
name|context
argument_list|,
name|Navigation
operator|.
name|sClassLevel
argument_list|,
name|classes
argument_list|)
expr_stmt|;
if|if
condition|(
name|isShowTimetable
argument_list|()
condition|)
block|{
name|boolean
name|hasTimetable
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ClassAssignments
argument_list|)
operator|&&
name|classAssignment
operator|!=
literal|null
condition|)
block|{
try|try
block|{
if|if
condition|(
name|classAssignment
operator|instanceof
name|CachedClassAssignmentProxy
condition|)
block|{
operator|(
operator|(
name|CachedClassAssignmentProxy
operator|)
name|classAssignment
operator|)
operator|.
name|setCache
argument_list|(
name|classes
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|classes
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
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|classAssignment
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|hasTimetable
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
name|setDisplayTimetable
argument_list|(
name|hasTimetable
argument_list|)
expr_stmt|;
block|}
name|setUserSettings
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isShowExam
argument_list|()
condition|)
name|setShowExamTimetable
argument_list|(
name|examAssignment
operator|!=
literal|null
operator|||
name|Exam
operator|.
name|hasTimetable
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|TableStream
name|table
init|=
literal|null
decl_stmt|;
name|int
name|ct
init|=
literal|0
decl_stmt|;
name|Iterator
name|it
init|=
name|classes
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|SubjectArea
name|subjectArea
init|=
literal|null
decl_stmt|;
name|String
name|prevLabel
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|c
init|=
operator|(
name|Class_
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
if|if
condition|(
name|subjectArea
operator|==
literal|null
operator|||
operator|!
name|subjectArea
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|co
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|table
operator|!=
literal|null
condition|)
block|{
name|table
operator|.
name|tableComplete
argument_list|()
expr_stmt|;
try|try
block|{
name|outputStream
operator|.
name|print
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
name|subjectArea
operator|=
name|co
operator|.
name|getSubjectArea
argument_list|()
expr_stmt|;
name|ct
operator|=
literal|0
expr_stmt|;
try|try
block|{
name|outputStream
operator|.
name|print
argument_list|(
name|labelForTable
argument_list|(
name|subjectArea
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|table
operator|=
name|this
operator|.
name|initTable
argument_list|(
name|outputStream
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|buildClassRow
argument_list|(
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
operator|++
name|ct
argument_list|,
name|table
argument_list|,
name|co
argument_list|,
name|c
argument_list|,
literal|0
argument_list|,
name|context
argument_list|,
name|prevLabel
argument_list|)
expr_stmt|;
name|prevLabel
operator|=
name|c
operator|.
name|getClassLabel
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
name|table
operator|.
name|tableComplete
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|TableCell
name|buildPrefGroupLabel
parameter_list|(
name|CourseOffering
name|co
parameter_list|,
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|int
name|indentSpaces
parameter_list|,
name|boolean
name|isEditable
parameter_list|,
name|String
name|prevLabel
parameter_list|,
name|String
name|icon
parameter_list|)
block|{
if|if
condition|(
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
name|TableCell
name|cell
init|=
name|initNormalCell
argument_list|(
literal|""
argument_list|,
name|isEditable
argument_list|)
decl_stmt|;
if|if
condition|(
name|indentSpaces
operator|>
literal|0
condition|)
block|{
name|int
name|pad
init|=
name|indentSpaces
operator|*
name|indent
decl_stmt|;
if|if
condition|(
name|icon
operator|!=
literal|null
condition|)
name|pad
operator|-=
name|indent
expr_stmt|;
name|cell
operator|.
name|setStyle
argument_list|(
literal|"padding-left: "
operator|+
name|pad
operator|+
literal|"px;"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|icon
operator|!=
literal|null
condition|)
name|cell
operator|.
name|addContent
argument_list|(
name|icon
argument_list|)
expr_stmt|;
name|Class_
name|aClass
init|=
operator|(
name|Class_
operator|)
name|prefGroup
decl_stmt|;
if|if
condition|(
operator|!
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"<font color='"
operator|+
name|disabledColor
operator|+
literal|"'>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"PreferenceGroup"
operator|.
name|equals
argument_list|(
name|getBackType
argument_list|()
argument_list|)
operator|&&
name|prefGroup
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|getBackId
argument_list|()
argument_list|)
condition|)
name|cell
operator|.
name|addContent
argument_list|(
literal|"<A name=\"back\"></A>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|co
operator|.
name|isIsControl
argument_list|()
condition|)
name|cell
operator|.
name|addContent
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
literal|"<A name=\"A"
operator|+
name|prefGroup
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"\"></A>"
argument_list|)
expr_stmt|;
name|String
name|label
init|=
name|aClass
operator|.
name|getClassLabel
argument_list|(
name|co
argument_list|)
decl_stmt|;
name|String
name|title
init|=
name|aClass
operator|.
name|getClassLabelWithTitle
argument_list|(
name|co
argument_list|)
decl_stmt|;
if|if
condition|(
name|prevLabel
operator|!=
literal|null
operator|&&
name|label
operator|.
name|equals
argument_list|(
name|prevLabel
argument_list|)
condition|)
block|{
name|label
operator|=
literal|"&nbsp;"
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|aClass
operator|.
name|isEnabledForStudentScheduling
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|title
operator|+=
literal|" - Do Not Display In Schedule Book."
expr_stmt|;
name|label
operator|=
literal|"<i>"
operator|+
name|label
operator|+
literal|"</i>"
expr_stmt|;
block|}
name|cell
operator|.
name|addContent
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setTitle
argument_list|(
name|title
argument_list|)
expr_stmt|;
if|if
condition|(
name|co
operator|.
name|isIsControl
argument_list|()
condition|)
name|cell
operator|.
name|addContent
argument_list|(
literal|"</b>"
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setNoWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|isEditable
condition|)
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"</font>"
argument_list|)
expr_stmt|;
block|}
name|InstructionalMethod
name|im
init|=
name|aClass
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalMethod
argument_list|()
decl_stmt|;
if|if
condition|(
name|im
operator|!=
literal|null
condition|)
name|cell
operator|.
name|addContent
argument_list|(
literal|" (<span title='"
operator|+
name|im
operator|.
name|getLabel
argument_list|()
operator|+
literal|"'>"
operator|+
name|im
operator|.
name|getReference
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|super
operator|.
name|buildPrefGroupLabel
argument_list|(
name|co
argument_list|,
name|prefGroup
argument_list|,
name|indentSpaces
argument_list|,
name|isEditable
argument_list|,
name|prevLabel
argument_list|,
name|icon
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|void
name|htmlTableForClasses
parameter_list|(
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|CourseOffering
name|co
parameter_list|,
name|TreeSet
name|classes
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|JspWriter
name|outputStream
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|columnList
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|LABEL
argument_list|)
expr_stmt|;
if|if
condition|(
name|StudentClassEnrollment
operator|.
name|sessionHasEnrollments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnDemand
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnLimit
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|co
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getCurrentSnapshotDate
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnSnapshotLimit
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnRoomRatio
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnDatePattern
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnTimePattern
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnTimetable
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnSchedulePrintNote
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|LearningManagementSystemInfo
operator|.
name|isLmsInfoDefinedForSession
argument_list|(
name|co
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSessionId
argument_list|()
argument_list|)
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnLms
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|setVisibleColumns
argument_list|(
name|columnList
argument_list|)
expr_stmt|;
if|if
condition|(
name|isShowTimetable
argument_list|()
condition|)
block|{
name|boolean
name|hasTimetable
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ClassAssignments
argument_list|)
operator|&&
name|classAssignment
operator|!=
literal|null
condition|)
block|{
try|try
block|{
if|if
condition|(
name|classAssignment
operator|instanceof
name|CachedClassAssignmentProxy
condition|)
block|{
operator|(
operator|(
name|CachedClassAssignmentProxy
operator|)
name|classAssignment
operator|)
operator|.
name|setCache
argument_list|(
name|classes
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|classes
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
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|classAssignment
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|hasTimetable
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
name|setDisplayTimetable
argument_list|(
name|hasTimetable
argument_list|)
expr_stmt|;
name|setShowDivSec
argument_list|(
name|hasTimetable
argument_list|)
expr_stmt|;
block|}
name|setUserSettings
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
name|TableStream
name|table
init|=
name|this
operator|.
name|initTable
argument_list|(
name|outputStream
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|Iterator
name|it
init|=
name|classes
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Class_
name|cls
init|=
literal|null
decl_stmt|;
name|String
name|prevLabel
init|=
literal|null
decl_stmt|;
name|int
name|ct
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|cls
operator|=
operator|(
name|Class_
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
name|this
operator|.
name|buildClassRow
argument_list|(
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
operator|++
name|ct
argument_list|,
name|table
argument_list|,
name|co
argument_list|,
name|cls
argument_list|,
literal|0
argument_list|,
name|context
argument_list|,
name|prevLabel
argument_list|)
expr_stmt|;
name|prevLabel
operator|=
name|cls
operator|.
name|getClassLabel
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
name|table
operator|.
name|tableComplete
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|htmlTableForSubpartClasses
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|Long
name|schedulingSubpartId
parameter_list|,
name|JspWriter
name|outputStream
parameter_list|,
name|String
name|backType
parameter_list|,
name|String
name|backId
parameter_list|)
block|{
name|setBackType
argument_list|(
name|backType
argument_list|)
expr_stmt|;
name|setBackId
argument_list|(
name|backId
argument_list|)
expr_stmt|;
if|if
condition|(
name|schedulingSubpartId
operator|!=
literal|null
condition|)
block|{
name|SchedulingSubpartDAO
name|ssDao
init|=
operator|new
name|SchedulingSubpartDAO
argument_list|()
decl_stmt|;
name|SchedulingSubpart
name|ss
init|=
name|ssDao
operator|.
name|get
argument_list|(
name|schedulingSubpartId
argument_list|)
decl_stmt|;
name|TreeSet
name|ts
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|ClassComparator
argument_list|(
name|ClassComparator
operator|.
name|COMPARE_BY_HIERARCHY
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|CommonValues
operator|.
name|Yes
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|ClassesKeepSort
argument_list|)
argument_list|)
condition|)
block|{
name|ts
operator|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|ClassCourseComparator
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ClassList.sortBy"
argument_list|,
name|ClassCourseComparator
operator|.
name|getName
argument_list|(
name|ClassCourseComparator
operator|.
name|SortBy
operator|.
name|NAME
argument_list|)
argument_list|)
argument_list|,
name|classAssignment
argument_list|,
literal|"1"
operator|.
name|equals
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ClassList.sortByKeepSubparts"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ts
operator|.
name|addAll
argument_list|(
name|ss
operator|.
name|getClasses
argument_list|()
argument_list|)
expr_stmt|;
name|Navigation
operator|.
name|set
argument_list|(
name|context
argument_list|,
name|Navigation
operator|.
name|sClassLevel
argument_list|,
name|ts
argument_list|)
expr_stmt|;
name|this
operator|.
name|htmlTableForClasses
argument_list|(
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
name|ss
operator|.
name|getControllingCourseOffering
argument_list|()
argument_list|,
name|ts
argument_list|,
name|ss
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|context
argument_list|,
name|outputStream
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|TreeSet
name|getExams
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
comment|//exams directly attached to the given class
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
comment|//check whether the given class is of the first subpart of the config
name|SchedulingSubpart
name|subpart
init|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|subpart
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|ret
return|;
name|InstrOfferingConfig
name|config
init|=
name|subpart
operator|.
name|getInstrOfferingConfig
argument_list|()
decl_stmt|;
name|SchedulingSubpartComparator
name|cmp
init|=
operator|new
name|SchedulingSubpartComparator
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|config
operator|.
name|getSchedulingSubparts
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
name|SchedulingSubpart
name|s
init|=
operator|(
name|SchedulingSubpart
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|cmp
operator|.
name|compare
argument_list|(
name|s
argument_list|,
name|subpart
argument_list|)
operator|<
literal|0
condition|)
return|return
name|ret
return|;
block|}
name|InstructionalOffering
name|offering
init|=
name|config
operator|.
name|getInstructionalOffering
argument_list|()
decl_stmt|;
comment|//check passed -- add config/offering/course exams to the class exams
name|ret
operator|.
name|addAll
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|,
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addAll
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|,
name|offering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|offering
operator|.
name|getCourseOfferings
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
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|.
name|addAll
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|,
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|protected
name|TableCell
name|buildNote
parameter_list|(
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|boolean
name|isEditable
parameter_list|,
name|UserContext
name|user
parameter_list|)
block|{
name|TableCell
name|cell
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
name|Class_
name|c
init|=
operator|(
name|Class_
operator|)
name|prefGroup
decl_stmt|;
name|String
name|offeringNote
init|=
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getNotes
argument_list|()
decl_stmt|;
name|String
name|classNote
init|=
name|c
operator|.
name|getNotes
argument_list|()
decl_stmt|;
name|String
name|note
init|=
operator|(
name|offeringNote
operator|==
literal|null
operator|||
name|offeringNote
operator|.
name|isEmpty
argument_list|()
condition|?
name|classNote
else|:
name|offeringNote
operator|+
operator|(
name|classNote
operator|==
literal|null
operator|||
name|classNote
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"\n"
operator|+
name|classNote
operator|)
operator|)
decl_stmt|;
if|if
condition|(
name|note
operator|!=
literal|null
operator|&&
operator|!
name|note
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|CommonValues
operator|.
name|NoteAsShortText
operator|.
name|eq
argument_list|(
name|user
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|ManagerNoteDisplay
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|classNote
operator|!=
literal|null
operator|&&
operator|!
name|classNote
operator|.
name|isEmpty
argument_list|()
condition|)
name|note
operator|=
name|classNote
expr_stmt|;
if|if
condition|(
name|note
operator|.
name|length
argument_list|()
operator|>
literal|20
condition|)
name|note
operator|=
name|note
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|20
argument_list|)
operator|+
literal|"..."
expr_stmt|;
name|cell
operator|=
name|initNormalCell
argument_list|(
name|note
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setAlign
argument_list|(
literal|"left"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|CommonValues
operator|.
name|NoteAsFullText
operator|.
name|eq
argument_list|(
name|user
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|ManagerNoteDisplay
argument_list|)
argument_list|)
condition|)
block|{
name|cell
operator|=
name|initNormalCell
argument_list|(
name|note
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setAlign
argument_list|(
literal|"left"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|cell
operator|=
name|initNormalCell
argument_list|(
literal|"<IMG border='0' alt='"
operator|+
name|MSG
operator|.
name|altHasNoteToMgr
argument_list|()
operator|+
literal|"' title='"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|note
argument_list|)
operator|+
literal|"' align='absmiddle' src='images/note.png'>"
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setAlign
argument_list|(
literal|"center"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|cell
operator|=
name|this
operator|.
name|initNormalCell
argument_list|(
literal|"&nbsp;"
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|cell
operator|=
name|this
operator|.
name|initNormalCell
argument_list|(
literal|"&nbsp;"
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|cell
operator|)
return|;
block|}
block|}
end_class

end_unit

