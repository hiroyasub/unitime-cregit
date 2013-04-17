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
comment|/**  * @author Stephanie Schluttenhofer  *  */
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
literal|""
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
name|String
name|indentSpaces
parameter_list|,
name|boolean
name|isEditable
parameter_list|,
name|String
name|prevLabel
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
name|indentSpaces
argument_list|,
name|isEditable
argument_list|)
decl_stmt|;
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
literal|null
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
name|String
index|[]
name|columns
decl_stmt|;
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
name|String
index|[]
name|tcolumns
init|=
block|{
name|LABEL
block|,
name|MSG
operator|.
name|columnDemand
argument_list|()
block|,
name|MSG
operator|.
name|columnLimit
argument_list|()
block|,
name|MSG
operator|.
name|columnRoomRatio
argument_list|()
block|,
name|MSG
operator|.
name|columnDatePattern
argument_list|()
block|,
name|MSG
operator|.
name|columnTimePattern
argument_list|()
block|,
name|MSG
operator|.
name|columnPreferences
argument_list|()
block|,
name|MSG
operator|.
name|columnInstructor
argument_list|()
block|,
name|MSG
operator|.
name|columnTimetable
argument_list|()
block|,
name|MSG
operator|.
name|columnSchedulePrintNote
argument_list|()
block|}
decl_stmt|;
name|columns
operator|=
name|tcolumns
expr_stmt|;
block|}
else|else
block|{
name|String
index|[]
name|tcolumns
init|=
block|{
name|LABEL
block|,
name|MSG
operator|.
name|columnLimit
argument_list|()
block|,
name|MSG
operator|.
name|columnRoomRatio
argument_list|()
block|,
name|MSG
operator|.
name|columnDatePattern
argument_list|()
block|,
name|MSG
operator|.
name|columnTimePattern
argument_list|()
block|,
name|MSG
operator|.
name|columnPreferences
argument_list|()
block|,
name|MSG
operator|.
name|columnInstructor
argument_list|()
block|,
name|MSG
operator|.
name|columnTimetable
argument_list|()
block|,
name|MSG
operator|.
name|columnSchedulePrintNote
argument_list|()
block|}
decl_stmt|;
name|columns
operator|=
name|tcolumns
expr_stmt|;
block|}
empty_stmt|;
name|setVisibleColumns
argument_list|(
name|columns
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
literal|""
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
block|}
end_class

end_unit

