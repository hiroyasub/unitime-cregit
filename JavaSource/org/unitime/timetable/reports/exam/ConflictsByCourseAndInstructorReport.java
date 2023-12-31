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
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignmentInfo
operator|.
name|BackToBackConflict
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
operator|.
name|DirectConflict
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
operator|.
name|MoreThanTwoADayConflict
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
name|ExamInstructorInfo
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
name|ConflictsByCourseAndInstructorReport
extends|extends
name|PdfLegacyExamReport
block|{
specifier|protected
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ConflictsByCourseAndStudentReport
operator|.
name|class
argument_list|)
decl_stmt|;
name|Hashtable
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|iStudentNames
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
specifier|public
name|ConflictsByCourseAndInstructorReport
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
literal|"CONFLICTS BY COURSE AND INSTRUCTOR"
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
condition|)
continue|continue;
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
name|setHeaderLine
argument_list|(
operator|new
name|Line
argument_list|(
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
literal|"Date"
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
literal|"Time"
argument_list|,
literal|6
argument_list|)
argument_list|,
name|rpad
argument_list|(
literal|"Name"
argument_list|,
literal|25
argument_list|)
argument_list|,
name|rpad
argument_list|(
literal|"Type"
argument_list|,
literal|6
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
literal|"Time"
argument_list|,
literal|15
argument_list|)
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
literal|7
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|6
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|25
argument_list|)
argument_list|,
name|lpad
argument_list|(
literal|""
argument_list|,
literal|'-'
argument_list|,
literal|6
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
literal|15
argument_list|)
argument_list|)
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
if|if
condition|(
name|iSubjectPrinted
condition|)
name|newPage
argument_list|()
expr_stmt|;
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
name|ExamAssignmentInfo
name|exam
init|=
name|section
operator|.
name|getExamAssignmentInfo
argument_list|()
decl_stmt|;
if|if
condition|(
name|exam
operator|==
literal|null
operator|||
name|exam
operator|.
name|getPeriod
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|iCoursePrinted
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|ExamInstructorInfo
name|instructor
range|:
name|exam
operator|.
name|getInstructors
argument_list|()
control|)
block|{
name|iStudentPrinted
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|iDirect
condition|)
for|for
control|(
name|DirectConflict
name|conflict
range|:
name|exam
operator|.
name|getInstructorDirectConflicts
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|conflict
operator|.
name|getStudents
argument_list|()
operator|.
name|contains
argument_list|(
name|instructor
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|iPeriodPrinted
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|conflict
operator|.
name|getOtherExam
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ExamSectionInfo
name|other
range|:
name|conflict
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getSectionsIncludeCrosslistedDummies
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|conflict
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getInstructors
argument_list|()
operator|.
name|contains
argument_list|(
name|instructor
argument_list|)
condition|)
continue|continue;
name|println
argument_list|(
name|rpad
argument_list|(
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|subject
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
name|iCoursePrinted
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
name|iCoursePrinted
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
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|7
argument_list|)
else|:
name|formatShortPeriodNoEndTimeDate
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|6
argument_list|)
else|:
name|formatShortPeriodNoEndTimeTime
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
name|rpad
argument_list|(
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|instructor
operator|.
name|getName
argument_list|()
argument_list|,
literal|25
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
literal|"DIRECT"
argument_list|,
literal|6
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|other
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|other
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
name|other
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
name|other
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
operator|new
name|Cell
argument_list|(
name|other
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getTimeFixedLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iSubjectPrinted
operator|=
name|iCoursePrinted
operator|=
name|iStudentPrinted
operator|=
name|iPeriodPrinted
operator|=
operator|!
name|iNewPage
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|conflict
operator|.
name|getOtherEventId
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|conflict
operator|.
name|isOtherClass
argument_list|()
condition|)
block|{
name|println
argument_list|(
name|rpad
argument_list|(
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|subject
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
name|iCoursePrinted
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
name|iCoursePrinted
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
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|7
argument_list|)
else|:
name|formatShortPeriodNoEndTimeDate
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|6
argument_list|)
else|:
name|formatShortPeriodNoEndTimeTime
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
name|rpad
argument_list|(
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|instructor
operator|.
name|getName
argument_list|()
argument_list|,
literal|25
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
literal|"CLASS"
argument_list|,
literal|6
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|conflict
operator|.
name|getOtherClass
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|conflict
operator|.
name|getOtherClass
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
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
name|iExternal
condition|?
name|conflict
operator|.
name|getOtherClass
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
else|:
name|conflict
operator|.
name|getOtherClass
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItypeDesc
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
name|iUseClassSuffix
operator|&&
name|conflict
operator|.
name|getOtherClass
argument_list|()
operator|.
name|getClassSuffix
argument_list|()
operator|!=
literal|null
condition|?
name|conflict
operator|.
name|getOtherClass
argument_list|()
operator|.
name|getClassSuffix
argument_list|()
else|:
name|conflict
operator|.
name|getOtherClass
argument_list|()
operator|.
name|getSectionNumberString
argument_list|()
argument_list|)
argument_list|,
operator|new
name|Cell
argument_list|(
name|getMeetingTime
argument_list|(
name|conflict
operator|.
name|getOtherEventTime
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|println
argument_list|(
name|rpad
argument_list|(
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|subject
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
name|iCoursePrinted
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
name|iCoursePrinted
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
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|7
argument_list|)
else|:
name|formatShortPeriodNoEndTimeDate
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|6
argument_list|)
else|:
name|formatShortPeriodNoEndTimeTime
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
name|rpad
argument_list|(
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|instructor
operator|.
name|getName
argument_list|()
argument_list|,
literal|25
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
literal|"EVENT"
argument_list|,
literal|6
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|conflict
operator|.
name|getOtherEventName
argument_list|()
argument_list|,
operator|(
name|iItype
condition|?
literal|34
else|:
literal|27
operator|)
argument_list|)
operator|.
name|withColSpan
argument_list|(
name|iItype
condition|?
literal|4
else|:
literal|3
argument_list|)
argument_list|,
name|getMeetingTime
argument_list|(
name|conflict
operator|.
name|getOtherEventTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|iSubjectPrinted
operator|=
name|iCoursePrinted
operator|=
name|iStudentPrinted
operator|=
name|iPeriodPrinted
operator|=
operator|!
name|iNewPage
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iM2d
condition|)
for|for
control|(
name|MoreThanTwoADayConflict
name|conflict
range|:
name|exam
operator|.
name|getInstructorMoreThanTwoADaysConflicts
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|conflict
operator|.
name|getStudents
argument_list|()
operator|.
name|contains
argument_list|(
name|instructor
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|iPeriodPrinted
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|ExamAssignment
name|otherExam
range|:
name|conflict
operator|.
name|getOtherExams
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|otherExam
operator|.
name|getInstructors
argument_list|()
operator|.
name|contains
argument_list|(
name|instructor
argument_list|)
condition|)
continue|continue;
for|for
control|(
name|ExamSectionInfo
name|other
range|:
name|otherExam
operator|.
name|getSectionsIncludeCrosslistedDummies
argument_list|()
control|)
block|{
name|println
argument_list|(
name|rpad
argument_list|(
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|subject
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
name|iCoursePrinted
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
name|iCoursePrinted
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
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|7
argument_list|)
else|:
name|formatShortPeriodNoEndTimeDate
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|6
argument_list|)
else|:
name|formatShortPeriodNoEndTimeTime
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
name|rpad
argument_list|(
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|instructor
operator|.
name|getName
argument_list|()
argument_list|,
literal|25
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
literal|">2-DAY"
argument_list|,
literal|6
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|other
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|other
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
name|other
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
name|other
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
operator|new
name|Cell
argument_list|(
name|other
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getTimeFixedLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iSubjectPrinted
operator|=
name|iCoursePrinted
operator|=
name|iStudentPrinted
operator|=
name|iPeriodPrinted
operator|=
operator|!
name|iNewPage
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|iBtb
condition|)
for|for
control|(
name|BackToBackConflict
name|conflict
range|:
name|exam
operator|.
name|getInstructorBackToBackConflicts
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|conflict
operator|.
name|getStudents
argument_list|()
operator|.
name|contains
argument_list|(
name|instructor
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|iPeriodPrinted
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|ExamSectionInfo
name|other
range|:
name|conflict
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getSectionsIncludeCrosslistedDummies
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|conflict
operator|.
name|getOtherExam
argument_list|()
operator|.
name|getInstructors
argument_list|()
operator|.
name|contains
argument_list|(
name|instructor
argument_list|)
condition|)
continue|continue;
name|println
argument_list|(
name|rpad
argument_list|(
name|iSubjectPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|subject
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
name|iCoursePrinted
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
name|iCoursePrinted
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
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|7
argument_list|)
else|:
name|formatShortPeriodNoEndTimeDate
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
operator|(
name|iCoursePrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
name|rpad
argument_list|(
literal|""
argument_list|,
literal|6
argument_list|)
else|:
name|formatShortPeriodNoEndTimeTime
argument_list|(
name|exam
argument_list|)
operator|)
argument_list|,
name|rpad
argument_list|(
name|iStudentPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
name|instructor
operator|.
name|getName
argument_list|()
argument_list|,
literal|25
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|iPeriodPrinted
operator|&&
name|isSkipRepeating
argument_list|()
condition|?
literal|""
else|:
literal|"BTB"
argument_list|,
literal|6
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|other
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|7
argument_list|)
argument_list|,
name|rpad
argument_list|(
name|other
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
name|other
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
name|other
operator|.
name|getSection
argument_list|()
argument_list|)
argument_list|,
operator|new
name|Cell
argument_list|(
name|other
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getTimeFixedLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iSubjectPrinted
operator|=
name|iCoursePrinted
operator|=
name|iStudentPrinted
operator|=
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
block|}
if|if
condition|(
name|iSubjectPrinted
condition|)
name|lastPage
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

