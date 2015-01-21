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
name|OutputStream
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
annotation|@
name|Override
specifier|protected
name|PdfPCell
name|pdfBuildDatePatternCell
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
name|PdfPCell
name|cell
init|=
name|createCell
argument_list|()
decl_stmt|;
if|if
condition|(
name|dp
operator|!=
literal|null
condition|)
block|{
name|Color
name|color
init|=
operator|(
name|isEditable
condition|?
name|sEnableColor
else|:
name|sDisableColor
operator|)
decl_stmt|;
name|addText
argument_list|(
name|cell
argument_list|,
name|dp
operator|.
name|getName
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|Element
operator|.
name|ALIGN_CENTER
argument_list|,
name|color
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
return|return
name|cell
return|;
block|}
specifier|public
name|void
name|pdfTableForClasses
parameter_list|(
name|OutputStream
name|out
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
name|SessionContext
name|context
parameter_list|)
throws|throws
name|Exception
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
name|setShowDemand
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
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
block|}
annotation|@
name|Override
specifier|protected
name|PdfPCell
name|pdfBuildInstructor
parameter_list|(
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|boolean
name|isEditable
parameter_list|)
block|{
name|Color
name|color
init|=
operator|(
name|isEditable
condition|?
name|sEnableColor
else|:
name|sDisableColor
operator|)
decl_stmt|;
name|PdfPCell
name|cell
init|=
name|createCell
argument_list|()
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
name|addText
argument_list|(
name|cell
argument_list|,
name|label
argument_list|,
literal|false
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
block|}
block|}
block|}
return|return
name|cell
return|;
block|}
block|}
end_class

end_unit

