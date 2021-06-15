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
name|ExamPeriod
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
name|ExamScheduleByPeriodReport
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
name|ExamScheduleByPeriodReport
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|ExamScheduleByPeriodReport
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
literal|"SCHEDULE BY PERIOD"
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
name|setHeaderLine
argument_list|(
operator|new
name|Line
argument_list|(
name|rpad
argument_list|(
literal|"Date And Time"
argument_list|,
literal|30
argument_list|)
argument_list|,
name|rpad
argument_list|(
literal|"Subject"
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
literal|"Course"
argument_list|,
literal|8
argument_list|)
argument_list|,
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iExternal
condition|?
literal|"ExtnID"
else|:
literal|"Type"
argument_list|,
literal|6
argument_list|)
else|:
name|NULL
operator|)
argument_list|,
name|rpad
argument_list|(
literal|"Section"
argument_list|,
literal|10
argument_list|)
argument_list|,
name|rpad
argument_list|(
literal|"Meeting Times"
argument_list|,
literal|35
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|"Enrl"
argument_list|,
literal|5
argument_list|)
argument_list|,
operator|(
name|iDispRooms
condition|?
name|rpad
argument_list|(
literal|"Room"
argument_list|,
literal|11
argument_list|)
else|:
name|NULL
operator|)
argument_list|,
operator|(
name|iDispRooms
condition|?
name|lpad
argument_list|(
literal|"Cap"
argument_list|,
literal|5
argument_list|)
else|:
name|NULL
operator|)
argument_list|,
operator|(
name|iDispRooms
condition|?
name|lpad
argument_list|(
literal|"ExCap"
argument_list|,
literal|6
argument_list|)
else|:
name|NULL
operator|)
argument_list|)
argument_list|,
operator|new
name|Line
argument_list|(
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|30
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|7
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|8
argument_list|)
argument_list|,
operator|(
name|iItype
condition|?
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|6
argument_list|)
else|:
name|NULL
operator|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|10
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|35
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|5
argument_list|)
argument_list|,
operator|(
name|iDispRooms
condition|?
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|11
argument_list|)
else|:
name|NULL
operator|)
argument_list|,
operator|(
name|iDispRooms
condition|?
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|5
argument_list|)
else|:
name|NULL
operator|)
argument_list|,
operator|(
name|iDispRooms
condition|?
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|6
argument_list|)
else|:
name|NULL
operator|)
argument_list|)
argument_list|)
expr_stmt|;
name|printHeader
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  Sorting exams..."
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|ExamAssignmentInfo
argument_list|>
name|exams
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|ExamAssignmentInfo
name|exam
range|:
name|getExams
argument_list|()
control|)
block|{
if|if
condition|(
name|exam
operator|.
name|getPeriod
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|hasSubjectArea
argument_list|(
name|exam
argument_list|)
condition|)
continue|continue;
name|exams
operator|.
name|add
argument_list|(
name|exam
argument_list|)
expr_stmt|;
block|}
name|sLog
operator|.
name|debug
argument_list|(
literal|"  Printing report..."
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|p
init|=
name|ExamPeriod
operator|.
name|findAll
argument_list|(
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|getExamType
argument_list|()
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|p
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|p
operator|.
name|next
argument_list|()
decl_stmt|;
name|iPeriodPrinted
operator|=
literal|false
expr_stmt|;
name|setPageName
argument_list|(
name|formatPeriod
argument_list|(
name|period
argument_list|)
argument_list|)
expr_stmt|;
name|setCont
argument_list|(
name|formatPeriod
argument_list|(
name|period
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|ExamAssignmentInfo
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
name|ExamAssignmentInfo
name|exam
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|period
operator|.
name|equals
argument_list|(
name|exam
operator|.
name|getPeriod
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|iPeriodPrinted
condition|)
block|{
if|if
condition|(
operator|!
name|iNewPage
condition|)
name|println
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ExamSectionInfo
name|lastSection
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
name|exam
operator|.
name|getSectionsIncludeCrosslistedDummies
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
operator|!
name|hasSubjectArea
argument_list|(
name|section
argument_list|)
condition|)
continue|continue;
name|iSubjectPrinted
operator|=
name|iCoursePrinted
operator|=
name|iStudentPrinted
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|lastSection
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|section
operator|.
name|getSubject
argument_list|()
operator|.
name|equals
argument_list|(
name|lastSection
operator|.
name|getSubject
argument_list|()
argument_list|)
condition|)
block|{
name|iSubjectPrinted
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|equals
argument_list|(
name|lastSection
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
condition|)
block|{
name|iCoursePrinted
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|lastSection
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
block|{
name|iStudentPrinted
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
block|}
name|lastSection
operator|=
name|section
expr_stmt|;
if|if
condition|(
operator|!
name|iDispRooms
condition|)
block|{
name|println
argument_list|(
name|rpad
argument_list|(
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|formatPeriod
argument_list|(
name|period
argument_list|)
argument_list|,
literal|30
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
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
argument_list|,
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
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
else|:
name|NULL
operator|)
argument_list|,
name|formatSection10
argument_list|(
name|section
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|getMeetingTime
argument_list|(
name|section
argument_list|)
argument_list|,
literal|35
argument_list|)
argument_list|,
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
argument_list|)
expr_stmt|;
name|iPeriodPrinted
operator|=
operator|!
name|iNewPage
expr_stmt|;
block|}
else|else
block|{
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
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|formatPeriod
argument_list|(
name|period
argument_list|)
argument_list|,
literal|30
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
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
argument_list|,
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
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
else|:
name|NULL
operator|)
argument_list|,
name|formatSection10
argument_list|(
name|section
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|getMeetingTime
argument_list|(
name|section
argument_list|)
argument_list|,
literal|35
argument_list|)
argument_list|,
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
argument_list|,
operator|new
name|Cell
argument_list|(
name|iNoRoom
argument_list|)
argument_list|)
expr_stmt|;
name|iPeriodPrinted
operator|=
operator|!
name|iNewPage
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
name|getNrLinesPerPage
argument_list|()
operator|&&
name|getNrLinesPerPage
argument_list|()
operator|>
literal|0
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
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|formatPeriod
argument_list|(
name|period
argument_list|)
argument_list|,
literal|30
argument_list|)
argument_list|,
name|rpad
argument_list|(
operator|!
name|firstRoom
operator|||
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
operator|!
name|firstRoom
operator|||
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
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
argument_list|,
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
operator|!
name|firstRoom
operator|||
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
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
else|:
name|NULL
operator|)
argument_list|,
name|formatSection10
argument_list|(
operator|!
name|firstRoom
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
name|rpad
argument_list|(
operator|!
name|firstRoom
condition|?
operator|new
name|Cell
argument_list|(
literal|""
argument_list|)
else|:
name|getMeetingTime
argument_list|(
name|section
argument_list|)
argument_list|,
literal|35
argument_list|)
argument_list|,
name|lpad
argument_list|(
operator|!
name|firstRoom
operator|&&
name|isSkipRepeating
argument_list|()
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
argument_list|,
name|formatRoom
argument_list|(
name|room
argument_list|)
argument_list|,
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
argument_list|,
name|lpad
argument_list|(
literal|""
operator|+
name|room
operator|.
name|getExamCapacity
argument_list|()
argument_list|,
literal|6
argument_list|)
argument_list|)
expr_stmt|;
name|firstRoom
operator|=
literal|false
expr_stmt|;
block|}
name|iPeriodPrinted
operator|=
operator|!
name|iNewPage
expr_stmt|;
block|}
block|}
block|}
block|}
name|setCont
argument_list|(
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iPeriodPrinted
operator|&&
name|p
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
if|if
condition|(
name|iPeriodPrinted
condition|)
name|lastPage
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

