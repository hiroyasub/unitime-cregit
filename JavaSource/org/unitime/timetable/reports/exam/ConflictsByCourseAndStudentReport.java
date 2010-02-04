begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|dao
operator|.
name|StudentDAO
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

begin_class
specifier|public
class|class
name|ConflictsByCourseAndStudentReport
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
name|ConflictsByCourseAndStudentReport
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
name|int
name|examType
parameter_list|,
name|SubjectArea
name|subjectArea
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
literal|"CONFLICTS BY COURSE AND STUDENT"
argument_list|,
name|session
argument_list|,
name|examType
argument_list|,
name|subjectArea
argument_list|,
name|exams
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"  Loading students ..."
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|StudentDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select s.uniqueId, s.externalUniqueId, s.lastName, s.firstName, s.middleName from Student s where s.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|iterate
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
if|if
condition|(
name|o
index|[
literal|2
index|]
operator|!=
literal|null
condition|)
name|iStudentNames
operator|.
name|put
argument_list|(
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
argument_list|,
operator|(
name|String
operator|)
name|o
index|[
literal|2
index|]
operator|+
operator|(
name|o
index|[
literal|3
index|]
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
operator|(
operator|(
name|String
operator|)
name|o
index|[
literal|3
index|]
operator|)
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|)
operator|+
operator|(
name|o
index|[
literal|4
index|]
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
operator|(
operator|(
name|String
operator|)
name|o
index|[
literal|4
index|]
operator|)
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
operator|)
argument_list|)
expr_stmt|;
if|else if
condition|(
name|o
index|[
literal|1
index|]
operator|!=
literal|null
condition|)
name|iStudentNames
operator|.
name|put
argument_list|(
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
argument_list|,
operator|(
name|String
operator|)
name|o
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
else|else
name|iStudentNames
operator|.
name|put
argument_list|(
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
argument_list|,
literal|"N/A"
argument_list|)
expr_stmt|;
block|}
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
name|getSections
argument_list|()
control|)
block|{
if|if
condition|(
name|getSubjectArea
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|.
name|equals
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
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
literal|"Subj Crsnbr "
operator|+
operator|(
name|iItype
condition|?
name|iExternal
condition|?
literal|"ExtnID "
else|:
literal|"InsTyp "
else|:
literal|""
operator|)
operator|+
literal|"Sect  Date And Time                Name                      Type   Subj Crsnbr "
operator|+
operator|(
name|iItype
condition|?
name|iExternal
condition|?
literal|"ExtnID "
else|:
literal|"InsTyp "
else|:
literal|""
operator|)
operator|+
literal|"Sect  Time"
block|,
literal|"---- ------ "
operator|+
operator|(
name|iItype
condition|?
literal|"------ "
else|:
literal|""
operator|)
operator|+
literal|"----- ---------------------------- ------------------------- ------ ---- ------ "
operator|+
operator|(
name|iItype
condition|?
literal|"------ "
else|:
literal|""
operator|)
operator|+
literal|"----- ---------------------"
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
name|ExamPeriod
name|period
init|=
name|exam
operator|.
name|getPeriod
argument_list|()
decl_stmt|;
name|iCoursePrinted
operator|=
literal|false
expr_stmt|;
name|Vector
argument_list|<
name|Long
argument_list|>
name|students
init|=
operator|new
name|Vector
argument_list|<
name|Long
argument_list|>
argument_list|(
name|section
operator|.
name|getStudentIds
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|students
argument_list|,
operator|new
name|Comparator
argument_list|<
name|Long
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Long
name|s1
parameter_list|,
name|Long
name|s2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|iStudentNames
operator|.
name|get
argument_list|(
name|s1
argument_list|)
operator|.
name|compareTo
argument_list|(
name|iStudentNames
operator|.
name|get
argument_list|(
name|s2
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|s1
operator|.
name|compareTo
argument_list|(
name|s2
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|students
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
name|getDirectConflicts
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
name|studentId
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
name|getSections
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|other
operator|.
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|studentId
argument_list|)
condition|)
continue|continue;
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
literal|4
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
literal|6
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iCoursePrinted
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
name|iCoursePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
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
name|exam
operator|.
name|getPeriodNameFixedLength
argument_list|()
argument_list|,
literal|28
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iStudentPrinted
condition|?
literal|""
else|:
name|iStudentNames
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
argument_list|,
literal|25
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
literal|"DIRECT"
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|other
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|other
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
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
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
name|lpad
argument_list|(
name|other
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|other
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getTimeFixedLength
argument_list|()
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
condition|?
literal|""
else|:
name|subject
argument_list|,
literal|4
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
literal|6
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iCoursePrinted
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
name|iCoursePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
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
name|exam
operator|.
name|getPeriodNameFixedLength
argument_list|()
argument_list|,
literal|28
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iStudentPrinted
condition|?
literal|""
else|:
name|iStudentNames
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
argument_list|,
literal|25
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
literal|"CLASS"
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
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
literal|4
argument_list|)
operator|+
literal|" "
operator|+
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
literal|6
argument_list|)
operator|+
literal|" "
operator|+
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
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
name|lpad
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
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
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
else|else
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
literal|4
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
literal|6
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iCoursePrinted
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
name|iCoursePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
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
name|exam
operator|.
name|getPeriodNameFixedLength
argument_list|()
argument_list|,
literal|28
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iStudentPrinted
condition|?
literal|""
else|:
name|iStudentNames
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
argument_list|,
literal|25
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
literal|"EVENT"
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
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
literal|24
else|:
literal|17
operator|)
argument_list|)
operator|+
literal|" "
operator|+
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
name|getMoreThanTwoADaysConflicts
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
name|studentId
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
for|for
control|(
name|ExamSectionInfo
name|other
range|:
name|otherExam
operator|.
name|getSections
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|other
operator|.
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|studentId
argument_list|)
condition|)
continue|continue;
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
literal|4
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
literal|6
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iCoursePrinted
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
name|iCoursePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
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
name|exam
operator|.
name|getPeriodNameFixedLength
argument_list|()
argument_list|,
literal|28
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iStudentPrinted
condition|?
literal|""
else|:
name|iStudentNames
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
argument_list|,
literal|25
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
literal|">2-DAY"
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|other
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|other
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
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
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
name|lpad
argument_list|(
name|other
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|other
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getTimeFixedLength
argument_list|()
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
name|getBackToBackConflicts
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
name|studentId
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
name|getSections
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|other
operator|.
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|studentId
argument_list|)
condition|)
continue|continue;
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
literal|4
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
literal|6
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|iItype
condition|?
name|rpad
argument_list|(
name|iCoursePrinted
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
name|iCoursePrinted
condition|?
literal|""
else|:
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
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
name|exam
operator|.
name|getPeriodNameFixedLength
argument_list|()
argument_list|,
literal|28
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iStudentPrinted
condition|?
literal|""
else|:
name|iStudentNames
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
argument_list|,
literal|25
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
literal|"BTB"
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|other
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|other
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
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
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
name|lpad
argument_list|(
name|other
operator|.
name|getSection
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|other
operator|.
name|getExamAssignment
argument_list|()
operator|.
name|getTimeFixedLength
argument_list|()
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

