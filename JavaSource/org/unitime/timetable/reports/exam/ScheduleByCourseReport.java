begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|reports
operator|.
name|exam
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
name|Collection
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
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamInfo
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
name|ExamRoomInfo
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
name|ExamInfo
operator|.
name|ExamSectionInfo
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
name|DocumentException
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ScheduleByCourseReport
extends|extends
name|PdfLegacyExamReport
block|{
specifier|protected
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|ScheduleByCourseReport
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|ScheduleByCourseReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|File
name|file
parameter_list|,
name|Session
name|session
parameter_list|,
name|ExamType
name|examType
parameter_list|,
name|Collection
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|,
name|Collection
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|exams
parameter_list|)
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
name|super
argument_list|(
name|mode
argument_list|,
name|file
argument_list|,
literal|"SCHEDULE BY COURSE"
argument_list|,
name|session
argument_list|,
name|examType
argument_list|,
name|subjectAreas
argument_list|,
name|exams
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|printReport
parameter_list|()
throws|throws
name|DocumentException
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"  Sorting sections..."
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
argument_list|>
name|subject2courseSections
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
for|for
control|(
name|ExamInfo
name|exam
range|:
name|getExams
argument_list|()
control|)
block|{
for|for
control|(
name|ExamSectionInfo
name|section
range|:
name|exam
operator|.
name|getSectionsIncludeCrosslistedDummies
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|hasSubjectArea
argument_list|(
name|section
argument_list|)
condition|)
continue|continue;
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
name|subject2courseSections
operator|.
name|get
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sections
operator|==
literal|null
condition|)
block|{
name|sections
operator|=
operator|new
name|TreeSet
argument_list|()
expr_stmt|;
name|subject2courseSections
operator|.
name|put
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|sections
argument_list|)
expr_stmt|;
block|}
name|sections
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
block|}
block|}
name|sLog
operator|.
name|debug
argument_list|(
literal|"  Printing report..."
argument_list|)
expr_stmt|;
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Subject Course   "
operator|+
operator|(
name|iItype
condition|?
name|iExternal
condition|?
literal|"ExtnID "
else|:
literal|"Type   "
else|:
literal|""
operator|)
operator|+
literal|"Section     Meeting Times                       Enrl    Date And Time                 Room         Cap ExCap"
block|,
literal|"------- -------- "
operator|+
operator|(
name|iItype
condition|?
literal|"------ "
else|:
literal|""
operator|)
operator|+
literal|"--------- ------------------------------------ -----  ------------------------------ ----------- ----- -----"
block|}
argument_list|)
expr_stmt|;
name|printHeader
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|subject2courseSections
operator|.
name|keySet
argument_list|()
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
name|String
name|subject
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
name|subject2courseSections
operator|.
name|get
argument_list|(
name|subject
argument_list|)
decl_stmt|;
name|setPageName
argument_list|(
name|subject
argument_list|)
expr_stmt|;
name|setCont
argument_list|(
name|subject
argument_list|)
expr_stmt|;
name|iSubjectPrinted
operator|=
literal|false
expr_stmt|;
name|iCoursePrinted
operator|=
literal|false
expr_stmt|;
name|String
name|lastCourse
init|=
literal|null
decl_stmt|;
name|iITypePrinted
operator|=
literal|false
expr_stmt|;
name|String
name|lastItype
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|ExamSectionInfo
argument_list|>
name|j
init|=
name|sections
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
name|ExamSectionInfo
name|section
init|=
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|iCoursePrinted
operator|&&
operator|!
name|section
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|equals
argument_list|(
name|lastCourse
argument_list|)
condition|)
block|{
name|iCoursePrinted
operator|=
literal|false
expr_stmt|;
name|iITypePrinted
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|iITypePrinted
operator|&&
operator|!
name|section
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|lastItype
argument_list|)
condition|)
name|iITypePrinted
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getRooms
argument_list|()
operator|==
literal|null
operator|||
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getRooms
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|println
argument_list|(
name|rpad
argument_list|(
name|iSubjectPrinted
condition|?
literal|""
else|:
name|subject
argument_list|,
literal|7
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iCoursePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|8
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iITypePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
name|lpad
argument_list|(
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|9
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|getMeetingTime
argument_list|(
name|section
argument_list|)
argument_list|,
literal|36
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|section
operator|.
name|getNrStudents
argument_list|()
argument_list|)
argument_list|,
literal|5
argument_list|)
operator|+
literal|"  "
operator|+
name|rpad
argument_list|(
operator|(
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getPeriodNameFixedLength
argument_list|()
operator|)
argument_list|,
literal|30
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|iNoRoom
operator|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|getLineNumber
argument_list|()
operator|+
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getRooms
argument_list|()
operator|.
name|size
argument_list|()
operator|>
name|iNrLines
condition|)
name|newPage
argument_list|()
expr_stmt|;
name|boolean
name|firstRoom
init|=
literal|true
decl_stmt|;
for|for
control|(
name|ExamRoomInfo
name|room
range|:
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|println
argument_list|(
name|rpad
argument_list|(
operator|!
name|firstRoom
operator|||
name|iSubjectPrinted
condition|?
literal|""
else|:
name|subject
argument_list|,
literal|7
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
operator|!
name|firstRoom
operator|||
name|iCoursePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|8
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
operator|!
name|firstRoom
operator|||
name|iITypePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
name|lpad
argument_list|(
operator|!
name|firstRoom
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|9
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
operator|!
name|firstRoom
condition|?
literal|""
else|:
name|getMeetingTime
argument_list|(
name|section
argument_list|)
argument_list|,
literal|36
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
operator|!
name|firstRoom
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|section
operator|.
name|getNrStudents
argument_list|()
argument_list|)
argument_list|,
literal|5
argument_list|)
operator|+
literal|"  "
operator|+
name|rpad
argument_list|(
operator|!
name|firstRoom
condition|?
literal|""
else|:
operator|(
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|section
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getPeriodNameFixedLength
argument_list|()
operator|)
argument_list|,
literal|30
argument_list|)
operator|+
literal|" "
operator|+
name|formatRoom
argument_list|(
name|room
operator|.
name|getName
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
literal|""
operator|+
name|room
operator|.
name|getCapacity
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
literal|""
operator|+
name|room
operator|.
name|getExamCapacity
argument_list|()
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|firstRoom
operator|=
literal|false
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iNewPage
condition|)
block|{
name|iSubjectPrinted
operator|=
name|iITypePrinted
operator|=
name|iCoursePrinted
operator|=
literal|false
expr_stmt|;
name|lastItype
operator|=
name|lastCourse
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|iSubjectPrinted
operator|=
name|iITypePrinted
operator|=
name|iCoursePrinted
operator|=
literal|true
expr_stmt|;
name|lastItype
operator|=
name|section
operator|.
name|getItype
argument_list|()
expr_stmt|;
name|lastCourse
operator|=
name|section
operator|.
name|getCourseNbr
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|j
operator|.
name|hasNext
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|iNewPage
condition|)
name|println
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
name|setCont
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|newPage
argument_list|()
expr_stmt|;
block|}
block|}
name|lastPage
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

