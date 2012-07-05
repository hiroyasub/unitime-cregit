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
name|Element
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
name|PdfPCell
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
name|PdfClassListTableBuilder
extends|extends
name|PdfInstructionalOfferingTableBuilder
block|{
specifier|public
name|PdfClassListTableBuilder
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
name|File
name|pdfTableForClasses
parameter_list|(
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|ClassListForm
name|form
parameter_list|,
name|SessionContext
name|context
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
literal|null
argument_list|,
literal|"Department"
argument_list|,
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
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"classes"
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
literal|0.77f
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
name|co
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
name|pdfBuildClassRow
argument_list|(
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
operator|++
name|ct
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
specifier|protected
name|PdfPCell
name|pdfBuildPrefGroupLabel
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
name|Color
name|color
init|=
operator|(
name|isEditable
condition|?
name|Color
operator|.
name|BLACK
else|:
name|Color
operator|.
name|GRAY
operator|)
decl_stmt|;
name|String
name|label
init|=
name|prefGroup
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Class_
name|aClass
init|=
operator|(
name|Class_
operator|)
name|prefGroup
decl_stmt|;
name|label
operator|=
name|aClass
operator|.
name|getClassLabel
argument_list|(
name|co
argument_list|)
expr_stmt|;
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
literal|""
expr_stmt|;
block|}
name|PdfPCell
name|cell
init|=
name|createCell
argument_list|()
decl_stmt|;
name|addText
argument_list|(
name|cell
argument_list|,
name|indentSpaces
operator|+
name|label
argument_list|,
name|co
operator|.
name|isIsControl
argument_list|()
argument_list|,
literal|false
argument_list|,
name|Element
operator|.
name|ALIGN_LEFT
argument_list|,
name|color
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
name|cell
return|;
block|}
else|else
return|return
name|super
operator|.
name|pdfBuildPrefGroupLabel
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
return|;
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

