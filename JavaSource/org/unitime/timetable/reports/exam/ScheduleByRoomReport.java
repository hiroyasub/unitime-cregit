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
name|Location
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
name|Room
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
name|ScheduleByRoomReport
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
name|ScheduleByRoomReport
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
literal|"SCHEDULE BY ROOM"
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
block|}
specifier|public
name|void
name|printReport
parameter_list|()
throws|throws
name|DocumentException
block|{
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Bldg  Room  Capacity  ExCap Period Date And Time                          Subj Crsnbr "
operator|+
operator|(
name|iItype
condition|?
literal|"InsTyp "
else|:
literal|""
operator|)
operator|+
literal|"Sect  Enrl"
block|,
literal|"----- ----- -------- ------ ------ -------------------------------------- ---- ------ "
operator|+
operator|(
name|iItype
condition|?
literal|"------ "
else|:
literal|""
operator|)
operator|+
literal|"---- -----"
block|}
argument_list|)
expr_stmt|;
name|printHeader
argument_list|()
expr_stmt|;
name|Vector
name|periods
init|=
operator|new
name|Vector
argument_list|(
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
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|Location
operator|.
name|findAllExamLocations
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
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Room
name|room
init|=
operator|(
name|location
operator|instanceof
name|Room
condition|?
operator|(
name|Room
operator|)
name|location
else|:
literal|null
operator|)
decl_stmt|;
name|iPeriodPrinted
operator|=
literal|false
expr_stmt|;
name|setPageName
argument_list|(
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|setCont
argument_list|(
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|periods
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
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|iStudentPrinted
operator|=
literal|false
expr_stmt|;
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
operator|new
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
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
name|hasRoom
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|&&
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
block|{
name|iSubjectPrinted
operator|=
name|iCoursePrinted
operator|=
name|iITypePrinted
operator|=
literal|false
expr_stmt|;
name|ExamSectionInfo
name|lastSection
init|=
literal|null
decl_stmt|;
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
if|if
condition|(
name|lastSection
operator|!=
literal|null
operator|&&
name|iSubjectPrinted
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
name|iITypePrinted
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
block|}
name|println
argument_list|(
operator|(
name|room
operator|!=
literal|null
condition|?
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|room
operator|.
name|getBuildingAbbv
argument_list|()
argument_list|,
literal|5
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
name|room
operator|.
name|getRoomNumber
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
else|:
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|location
operator|.
name|getLabel
argument_list|()
argument_list|,
literal|9
argument_list|)
operator|)
operator|+
name|lpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
argument_list|)
argument_list|,
literal|8
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|getExamCapacity
argument_list|()
argument_list|)
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
name|iStudentPrinted
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|periods
operator|.
name|indexOf
argument_list|(
name|period
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|,
literal|6
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
name|formatPeriod
argument_list|(
name|period
argument_list|)
argument_list|,
literal|38
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|iSubjectPrinted
condition|?
literal|""
else|:
name|section
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
literal|4
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
argument_list|)
expr_stmt|;
name|iPeriodPrinted
operator|=
name|iStudentPrinted
operator|=
name|iSubjectPrinted
operator|=
name|iCoursePrinted
operator|=
name|iITypePrinted
operator|=
operator|!
name|iNewPage
expr_stmt|;
name|lastSection
operator|=
name|section
expr_stmt|;
block|}
comment|//println("");
block|}
block|}
if|if
condition|(
operator|!
name|iStudentPrinted
condition|)
block|{
name|println
argument_list|(
operator|(
name|room
operator|!=
literal|null
condition|?
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|room
operator|.
name|getBuildingAbbv
argument_list|()
argument_list|,
literal|5
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
name|room
operator|.
name|getRoomNumber
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
else|:
name|rpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|location
operator|.
name|getLabel
argument_list|()
argument_list|,
literal|11
argument_list|)
operator|+
literal|" "
operator|)
operator|+
name|lpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
argument_list|)
argument_list|,
literal|8
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
name|iPeriodPrinted
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|location
operator|.
name|getExamCapacity
argument_list|()
argument_list|)
argument_list|,
literal|6
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
name|periods
operator|.
name|indexOf
argument_list|(
name|period
argument_list|)
operator|+
literal|1
argument_list|)
argument_list|,
literal|6
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|formatPeriod
argument_list|(
name|period
argument_list|)
argument_list|,
literal|38
argument_list|)
argument_list|)
expr_stmt|;
name|iPeriodPrinted
operator|=
operator|!
name|iNewPage
expr_stmt|;
comment|//println("");
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

