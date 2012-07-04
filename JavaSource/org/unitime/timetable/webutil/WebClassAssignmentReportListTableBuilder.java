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
name|TreeSet
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
name|HttpSession
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
name|timetable
operator|.
name|form
operator|.
name|ClassAssignmentsReportForm
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
name|ClassInstructor
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
name|DatePattern
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
name|model
operator|.
name|comparators
operator|.
name|InstructorComparator
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
name|TimetableManagerDAO
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  *  */
end_comment

begin_class
specifier|public
class|class
name|WebClassAssignmentReportListTableBuilder
extends|extends
name|WebClassListTableBuilder
block|{
comment|/** 	 *  	 */
specifier|public
name|WebClassAssignmentReportListTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|disabledColor
operator|=
literal|"black"
expr_stmt|;
block|}
specifier|protected
name|String
name|additionalNote
parameter_list|()
block|{
return|return
operator|(
literal|" Room Assignments"
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|TableCell
name|buildDatePatternCell
parameter_list|(
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|boolean
name|isEditable
parameter_list|)
block|{
name|Assignment
name|a
init|=
literal|null
decl_stmt|;
name|AssignmentPreferenceInfo
name|p
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getDisplayTimetable
argument_list|()
operator|&&
name|isShowTimetable
argument_list|()
operator|&&
name|classAssignment
operator|!=
literal|null
operator|&&
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
try|try
block|{
name|a
operator|=
name|classAssignment
operator|.
name|getAssignment
argument_list|(
operator|(
name|Class_
operator|)
name|prefGroup
argument_list|)
expr_stmt|;
name|p
operator|=
name|classAssignment
operator|.
name|getAssignmentInfo
argument_list|(
operator|(
name|Class_
operator|)
name|prefGroup
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
block|}
block|}
name|DatePattern
name|dp
init|=
operator|(
name|a
operator|!=
literal|null
condition|?
name|a
operator|.
name|getDatePattern
argument_list|()
else|:
name|prefGroup
operator|.
name|effectiveDatePattern
argument_list|()
operator|)
decl_stmt|;
name|TableCell
name|cell
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dp
operator|==
literal|null
condition|)
block|{
name|cell
operator|=
name|initNormalCell
argument_list|(
literal|""
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|cell
operator|=
name|initNormalCell
argument_list|(
literal|"<div title='"
operator|+
name|sDateFormat
operator|.
name|format
argument_list|(
name|dp
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|sDateFormat
operator|.
name|format
argument_list|(
name|dp
operator|.
name|getEndDate
argument_list|()
argument_list|)
operator|+
literal|"' "
operator|+
operator|(
name|p
operator|==
literal|null
condition|?
literal|""
else|:
literal|"style='color:"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|p
operator|.
name|getDatePatternPref
argument_list|()
argument_list|)
operator|+
literal|";'"
operator|)
operator|+
literal|">"
operator|+
name|dp
operator|.
name|getName
argument_list|()
operator|+
literal|"</div>"
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
block|}
name|cell
operator|.
name|setAlign
argument_list|(
literal|"center"
argument_list|)
expr_stmt|;
return|return
operator|(
name|cell
operator|)
return|;
block|}
specifier|public
name|void
name|htmlTableForClasses
parameter_list|(
name|HttpSession
name|session
parameter_list|,
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|ClassAssignmentsReportForm
name|form
parameter_list|,
name|User
name|user
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
name|Collection
name|classes
init|=
operator|(
name|Collection
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
name|session
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
name|getDisplayTimetable
argument_list|()
condition|)
block|{
name|boolean
name|hasTimetable
init|=
literal|false
decl_stmt|;
try|try
block|{
name|String
name|managerId
init|=
operator|(
name|String
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|TMTBL_MGR_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|TimetableManager
name|manager
init|=
operator|(
operator|new
name|TimetableManagerDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|managerId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|manager
operator|!=
literal|null
operator|&&
name|manager
operator|.
name|canSeeTimetable
argument_list|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
argument_list|,
name|user
argument_list|)
operator|&&
name|classAssignment
operator|!=
literal|null
condition|)
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
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
name|setDisplayTimetable
argument_list|(
name|hasTimetable
argument_list|)
expr_stmt|;
block|}
name|setUserSettings
argument_list|(
name|user
argument_list|)
expr_stmt|;
if|if
condition|(
name|examAssignment
operator|!=
literal|null
operator|||
name|Exam
operator|.
name|hasTimetable
argument_list|(
operator|(
name|Long
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SESSION_ID_ATTR_NAME
argument_list|)
argument_list|)
condition|)
block|{
name|setShowExam
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setShowExamTimetable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setShowExamName
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|setShowInstructor
argument_list|(
literal|true
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
name|ct
operator|=
literal|0
expr_stmt|;
name|table
operator|=
name|this
operator|.
name|initTable
argument_list|(
name|outputStream
argument_list|,
operator|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|==
literal|null
condition|?
literal|null
else|:
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getUniqueId
argument_list|()
operator|)
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
name|user
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
if|if
condition|(
name|table
operator|!=
literal|null
condition|)
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
name|buildInstructor
parameter_list|(
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|boolean
name|isEditable
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|this
operator|.
name|initNormalCell
argument_list|(
literal|""
argument_list|,
name|isEditable
argument_list|)
decl_stmt|;
if|if
condition|(
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
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
name|aClass
operator|.
name|isDisplayInstructor
argument_list|()
operator|&&
operator|!
name|aClass
operator|.
name|getClassInstructors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|TreeSet
name|sortedInstructors
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|InstructorComparator
argument_list|()
argument_list|)
decl_stmt|;
name|sortedInstructors
operator|.
name|addAll
argument_list|(
name|aClass
operator|.
name|getClassInstructors
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|sortedInstructors
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
name|ClassInstructor
name|ci
init|=
operator|(
name|ClassInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|label
init|=
name|ci
operator|.
name|getInstructor
argument_list|()
operator|.
name|getName
argument_list|(
name|getInstructorNameFormat
argument_list|()
argument_list|)
decl_stmt|;
name|cell
operator|.
name|addContent
argument_list|(
name|label
operator|+
operator|(
name|i
operator|.
name|hasNext
argument_list|()
condition|?
literal|"<br>"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|cell
operator|.
name|addContent
argument_list|(
literal|"&nbsp; "
argument_list|)
expr_stmt|;
block|}
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
operator|.
name|addContent
argument_list|(
literal|"&nbsp; "
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

