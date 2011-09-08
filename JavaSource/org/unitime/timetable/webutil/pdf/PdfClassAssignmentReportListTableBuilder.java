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
operator|.
name|pdf
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

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
name|FileOutputStream
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
name|timetable
operator|.
name|ApplicationProperties
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
name|util
operator|.
name|PdfEventHandler
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
name|PdfFont
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|Paragraph
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|Rectangle
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|pdf
operator|.
name|PdfPTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PdfClassAssignmentReportListTableBuilder
extends|extends
name|PdfClassListTableBuilder
block|{
specifier|protected
name|Color
name|sDisableColor
init|=
name|Color
operator|.
name|BLACK
decl_stmt|;
specifier|public
name|PdfClassAssignmentReportListTableBuilder
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
literal|" Room Assignments"
operator|)
return|;
block|}
specifier|public
name|File
name|pdfTableForClasses
parameter_list|(
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
parameter_list|)
block|{
name|FileOutputStream
name|out
init|=
literal|null
decl_stmt|;
try|try
block|{
name|setVisibleColumns
argument_list|(
name|form
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
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"classassign"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|float
index|[]
name|widths
init|=
name|getWidths
argument_list|()
decl_stmt|;
name|float
name|totalWidth
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|widths
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|totalWidth
operator|+=
name|widths
index|[
name|i
index|]
expr_stmt|;
name|iDocument
operator|=
operator|new
name|Document
argument_list|(
operator|new
name|Rectangle
argument_list|(
literal|60f
operator|+
name|totalWidth
argument_list|,
literal|60f
operator|+
literal|1.30f
operator|*
name|totalWidth
argument_list|)
argument_list|,
literal|30f
argument_list|,
literal|30f
argument_list|,
literal|30f
argument_list|,
literal|30f
argument_list|)
expr_stmt|;
name|out
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|iWriter
operator|=
name|PdfEventHandler
operator|.
name|initFooter
argument_list|(
name|iDocument
argument_list|,
name|out
argument_list|)
expr_stmt|;
name|iDocument
operator|.
name|open
argument_list|()
expr_stmt|;
name|Class_
name|c
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
name|c
operator|=
operator|(
name|Class_
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
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
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
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
name|iPdfTable
operator|!=
literal|null
condition|)
block|{
name|iDocument
operator|.
name|add
argument_list|(
name|iPdfTable
argument_list|)
expr_stmt|;
name|iDocument
operator|.
name|newPage
argument_list|()
expr_stmt|;
block|}
name|iPdfTable
operator|=
operator|new
name|PdfPTable
argument_list|(
name|getWidths
argument_list|()
argument_list|)
expr_stmt|;
name|iPdfTable
operator|.
name|setWidthPercentage
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|iPdfTable
operator|.
name|getDefaultCell
argument_list|()
operator|.
name|setPadding
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|iPdfTable
operator|.
name|getDefaultCell
argument_list|()
operator|.
name|setBorderWidth
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|iPdfTable
operator|.
name|setSplitRows
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|subjectArea
operator|=
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
expr_stmt|;
name|ct
operator|=
literal|0
expr_stmt|;
name|iDocument
operator|.
name|add
argument_list|(
operator|new
name|Paragraph
argument_list|(
name|labelForTable
argument_list|(
name|subjectArea
argument_list|)
argument_list|,
name|PdfFont
operator|.
name|getBigFont
argument_list|(
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|iDocument
operator|.
name|add
argument_list|(
operator|new
name|Paragraph
argument_list|(
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
name|pdfBuildTableHeader
argument_list|(
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
argument_list|)
expr_stmt|;
block|}
name|pdfBuildClassRow
argument_list|(
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
operator|++
name|ct
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
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|iPdfTable
operator|!=
literal|null
condition|)
name|iDocument
operator|.
name|add
argument_list|(
name|iPdfTable
argument_list|)
expr_stmt|;
name|iDocument
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|file
return|;
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
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|out
operator|!=
literal|null
condition|)
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

