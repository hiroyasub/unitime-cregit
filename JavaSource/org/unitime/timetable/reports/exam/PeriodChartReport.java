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
name|Enumeration
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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

begin_class
specifier|public
class|class
name|PeriodChartReport
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
name|PeriodChartReport
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
literal|"PERIOD ASSIGNMENT"
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
if|if
condition|(
name|iLimit
operator|>=
literal|0
condition|)
name|setFooter
argument_list|(
literal|"limit="
operator|+
name|iLimit
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|setRoomCode
parameter_list|(
name|String
name|roomCode
parameter_list|)
block|{
name|String
name|codes
init|=
name|super
operator|.
name|setRoomCode
argument_list|(
name|roomCode
argument_list|)
decl_stmt|;
if|if
condition|(
name|codes
operator|!=
literal|null
operator|&&
name|codes
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|setFooter
argument_list|(
name|codes
operator|+
operator|(
name|iLimit
operator|>=
literal|0
condition|?
literal|" (limit="
operator|+
name|iLimit
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
return|return
name|codes
return|;
block|}
specifier|public
name|void
name|printReport
parameter_list|()
throws|throws
name|DocumentException
block|{
name|Hashtable
argument_list|<
name|ExamPeriod
argument_list|,
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
argument_list|>
name|period2courseSections
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
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
name|period2courseSections
operator|.
name|get
argument_list|(
name|exam
operator|.
name|getPeriod
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
name|period2courseSections
operator|.
name|put
argument_list|(
name|exam
operator|.
name|getPeriod
argument_list|()
argument_list|,
name|sections
argument_list|)
expr_stmt|;
block|}
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
name|sections
operator|.
name|add
argument_list|(
name|section
argument_list|)
expr_stmt|;
block|}
block|}
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|times
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|fixedTimes
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|Integer
argument_list|,
name|String
argument_list|>
name|days
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|TreeSet
name|weeks
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
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
name|i
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
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|times
operator|.
name|put
argument_list|(
name|period
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|period
operator|.
name|getStartTimeLabel
argument_list|()
argument_list|)
expr_stmt|;
name|days
operator|.
name|put
argument_list|(
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|,
name|period
operator|.
name|getStartDateLabel
argument_list|()
argument_list|)
expr_stmt|;
name|fixedTimes
operator|.
name|put
argument_list|(
name|period
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|lpad
argument_list|(
name|period
operator|.
name|getStartTimeLabel
argument_list|()
argument_list|,
literal|'0'
argument_list|,
literal|6
argument_list|)
operator|+
literal|" - "
operator|+
name|lpad
argument_list|(
name|period
operator|.
name|getEndTimeLabel
argument_list|()
argument_list|,
literal|'0'
argument_list|,
literal|6
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|boolean
name|headerPrinted
init|=
literal|false
decl_stmt|;
name|Hashtable
name|totalADay
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|String
name|timesThisPage
init|=
literal|null
decl_stmt|;
name|int
name|nrCols
init|=
literal|0
decl_stmt|;
if|if
condition|(
operator|!
name|iTotals
condition|)
block|{
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"Time            Exam            Enrl  Exam            Enrl  Exam            Enrl  Exam            Enrl  Exam            Enrl"
block|,
literal|"--------------- --------------- ----  --------------- ----  --------------- ----  --------------- ----  --------------- ----"
block|}
argument_list|)
expr_stmt|;
name|printHeader
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|int
name|dIdx
init|=
literal|0
init|;
name|dIdx
operator|<
name|days
operator|.
name|size
argument_list|()
condition|;
name|dIdx
operator|+=
name|nrCols
control|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|ToolBox
operator|.
name|sortEnumeration
argument_list|(
name|times
operator|.
name|keys
argument_list|()
argument_list|)
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|int
name|time
init|=
operator|(
operator|(
name|Integer
operator|)
name|e
operator|.
name|nextElement
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|String
name|timeStr
init|=
operator|(
name|String
operator|)
name|times
operator|.
name|get
argument_list|(
operator|new
name|Integer
argument_list|(
name|time
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|header1
init|=
literal|""
decl_stmt|;
name|String
name|header2
init|=
literal|""
decl_stmt|;
name|String
name|header3
init|=
literal|""
decl_stmt|;
name|Vector
name|periods
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|String
name|firstDay
init|=
literal|null
decl_stmt|;
name|int
name|firstDayOffset
init|=
literal|0
decl_stmt|;
name|String
name|lastDay
init|=
literal|null
decl_stmt|;
name|nrCols
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|Enumeration
name|f
init|=
name|ToolBox
operator|.
name|sortEnumeration
argument_list|(
name|days
operator|.
name|keys
argument_list|()
argument_list|)
init|;
name|f
operator|.
name|hasMoreElements
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|int
name|day
init|=
operator|(
operator|(
name|Integer
operator|)
name|f
operator|.
name|nextElement
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|String
name|dayStr
init|=
name|days
operator|.
name|get
argument_list|(
name|day
argument_list|)
decl_stmt|;
if|if
condition|(
name|idx
operator|<
name|dIdx
operator|||
operator|(
name|firstDay
operator|!=
literal|null
operator|&&
operator|(
name|dayStr
operator|.
name|startsWith
argument_list|(
literal|"Mon"
argument_list|)
operator|||
name|day
operator|>=
name|firstDayOffset
operator|+
literal|7
operator|)
operator|)
operator|||
name|nrCols
operator|==
operator|(
name|iTotals
condition|?
literal|6
else|:
literal|5
operator|)
condition|)
continue|continue;
if|if
condition|(
name|firstDay
operator|==
literal|null
condition|)
block|{
name|firstDay
operator|=
name|dayStr
expr_stmt|;
name|firstDayOffset
operator|=
name|day
expr_stmt|;
block|}
name|lastDay
operator|=
name|dayStr
expr_stmt|;
name|header1
operator|+=
name|mpad
argument_list|(
name|dayStr
argument_list|,
literal|20
argument_list|)
operator|+
literal|"  "
expr_stmt|;
name|header2
operator|+=
literal|"Exam            Enrl  "
expr_stmt|;
name|header3
operator|+=
literal|"=============== ====  "
expr_stmt|;
name|ExamPeriod
name|period
init|=
literal|null
decl_stmt|;
name|nrCols
operator|++
expr_stmt|;
for|for
control|(
name|Iterator
name|i
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
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|p
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|time
operator|!=
name|p
operator|.
name|getStartSlot
argument_list|()
operator|||
name|day
operator|!=
name|p
operator|.
name|getDateOffset
argument_list|()
condition|)
continue|continue;
name|period
operator|=
name|p
expr_stmt|;
break|break;
block|}
name|periods
operator|.
name|add
argument_list|(
name|period
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iTotals
condition|)
name|setHeader
argument_list|(
operator|new
name|String
index|[]
block|{
name|timeStr
block|,
name|header1
block|,
name|header2
block|,
name|header3
block|}
argument_list|)
expr_stmt|;
name|int
name|nextLines
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Enumeration
name|f
init|=
name|periods
operator|.
name|elements
argument_list|()
init|;
name|f
operator|.
name|hasMoreElements
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
name|f
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|period
operator|==
literal|null
condition|)
continue|continue;
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
name|period2courseSections
operator|.
name|get
argument_list|(
name|period
argument_list|)
decl_stmt|;
if|if
condition|(
name|sections
operator|==
literal|null
condition|)
continue|continue;
name|int
name|linesThisSections
init|=
literal|6
decl_stmt|;
for|for
control|(
name|ExamSectionInfo
name|section
range|:
name|sections
control|)
if|if
condition|(
name|iLimit
operator|<
literal|0
operator|||
name|section
operator|.
name|getNrStudents
argument_list|()
operator|>=
name|iLimit
condition|)
name|linesThisSections
operator|++
expr_stmt|;
name|nextLines
operator|=
name|Math
operator|.
name|max
argument_list|(
name|nextLines
argument_list|,
name|linesThisSections
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iTotals
condition|)
block|{
if|if
condition|(
operator|!
name|headerPrinted
condition|)
block|{
name|printHeader
argument_list|()
expr_stmt|;
name|setPageName
argument_list|(
name|timeStr
operator|+
operator|(
name|days
operator|.
name|size
argument_list|()
operator|>
name|nrCols
condition|?
literal|" ("
operator|+
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
name|setCont
argument_list|(
name|timeStr
operator|+
operator|(
name|days
operator|.
name|size
argument_list|()
operator|>
name|nrCols
condition|?
literal|" ("
operator|+
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
name|timesThisPage
operator|=
name|timeStr
expr_stmt|;
block|}
if|else if
condition|(
name|timesThisPage
operator|!=
literal|null
operator|&&
name|getLineNumber
argument_list|()
operator|+
name|nextLines
operator|<=
name|iNrLines
condition|)
block|{
name|println
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|println
argument_list|(
name|timeStr
argument_list|)
expr_stmt|;
name|println
argument_list|(
name|header1
argument_list|)
expr_stmt|;
name|println
argument_list|(
name|header2
argument_list|)
expr_stmt|;
name|println
argument_list|(
name|header3
argument_list|)
expr_stmt|;
name|timesThisPage
operator|+=
literal|", "
operator|+
name|timeStr
expr_stmt|;
name|setPageName
argument_list|(
name|timesThisPage
operator|+
operator|(
name|days
operator|.
name|size
argument_list|()
operator|>
name|nrCols
condition|?
literal|" ("
operator|+
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
name|setCont
argument_list|(
name|timesThisPage
operator|+
operator|(
name|days
operator|.
name|size
argument_list|()
operator|>
name|nrCols
condition|?
literal|" ("
operator|+
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|newPage
argument_list|()
expr_stmt|;
name|timesThisPage
operator|=
name|timeStr
expr_stmt|;
name|setPageName
argument_list|(
name|timeStr
operator|+
operator|(
name|days
operator|.
name|size
argument_list|()
operator|>
name|nrCols
condition|?
literal|" ("
operator|+
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
name|setCont
argument_list|(
name|timeStr
operator|+
operator|(
name|days
operator|.
name|size
argument_list|()
operator|>
name|nrCols
condition|?
literal|" ("
operator|+
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|")"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|nextLines
operator|==
literal|0
condition|)
continue|continue;
name|setCont
argument_list|(
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|" "
operator|+
name|fixedTimes
operator|.
name|get
argument_list|(
name|time
argument_list|)
argument_list|)
expr_stmt|;
name|setPageName
argument_list|(
name|firstDay
operator|+
literal|" - "
operator|+
name|lastDay
operator|+
literal|" "
operator|+
name|fixedTimes
operator|.
name|get
argument_list|(
name|time
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|headerPrinted
operator|=
literal|true
expr_stmt|;
name|int
name|max
init|=
literal|0
decl_stmt|;
name|Vector
name|lines
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|f
init|=
name|periods
operator|.
name|elements
argument_list|()
init|;
name|f
operator|.
name|hasMoreElements
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
name|f
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|period
operator|==
literal|null
condition|)
block|{
name|Vector
name|linesThisPeriod
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|linesThisPeriod
operator|.
name|add
argument_list|(
name|lpad
argument_list|(
literal|"0"
argument_list|,
literal|20
argument_list|)
argument_list|)
expr_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|linesThisPeriod
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|TreeSet
argument_list|<
name|ExamSectionInfo
argument_list|>
name|sections
init|=
name|period2courseSections
operator|.
name|get
argument_list|(
name|period
argument_list|)
decl_stmt|;
if|if
condition|(
name|sections
operator|==
literal|null
condition|)
name|sections
operator|=
operator|new
name|TreeSet
argument_list|()
expr_stmt|;
name|Vector
name|linesThisPeriod
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|int
name|total
init|=
literal|0
decl_stmt|;
name|int
name|totalListed
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ExamSectionInfo
name|section
range|:
name|sections
control|)
block|{
name|total
operator|+=
name|section
operator|.
name|getNrStudents
argument_list|()
expr_stmt|;
if|if
condition|(
name|iLimit
operator|>=
literal|0
operator|&&
name|section
operator|.
name|getNrStudents
argument_list|()
operator|<
name|iLimit
condition|)
continue|continue;
name|totalListed
operator|+=
name|section
operator|.
name|getNrStudents
argument_list|()
expr_stmt|;
name|String
name|code
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|iRoomCodes
operator|!=
literal|null
operator|&&
operator|!
name|iRoomCodes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
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
name|String
name|c
init|=
name|iRoomCodes
operator|.
name|get
argument_list|(
name|room
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
name|code
operator|=
name|c
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|iItype
condition|)
block|{
if|if
condition|(
name|iExternal
condition|)
block|{
name|linesThisPeriod
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
literal|4
argument_list|)
operator|+
name|rpad
argument_list|(
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
operator|(
name|code
operator|==
literal|null
operator|||
name|code
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|' '
else|:
name|code
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|)
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
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|linesThisPeriod
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
name|section
operator|.
name|getName
argument_list|()
argument_list|,
literal|15
argument_list|)
operator|+
operator|(
name|code
operator|==
literal|null
operator|||
name|code
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|' '
else|:
name|code
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|)
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
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|linesThisPeriod
operator|.
name|add
argument_list|(
name|rpad
argument_list|(
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
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|5
argument_list|)
operator|+
literal|" "
operator|+
name|rpad
argument_list|(
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
literal|3
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|code
operator|==
literal|null
operator|||
name|code
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|' '
else|:
name|code
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|)
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
literal|4
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iTotals
condition|)
block|{
if|if
condition|(
name|totalListed
operator|!=
name|total
condition|)
name|linesThisPeriod
operator|.
name|insertElementAt
argument_list|(
name|mpad
argument_list|(
literal|"("
operator|+
name|totalListed
operator|+
literal|")"
argument_list|,
literal|13
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
literal|""
operator|+
name|total
argument_list|,
literal|6
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
else|else
name|linesThisPeriod
operator|.
name|insertElementAt
argument_list|(
name|lpad
argument_list|(
literal|""
operator|+
name|total
argument_list|,
literal|20
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|linesThisPeriod
operator|.
name|insertElementAt
argument_list|(
name|rpad
argument_list|(
name|period
operator|.
name|getStartDateLabel
argument_list|()
argument_list|,
literal|13
argument_list|)
operator|+
literal|" "
operator|+
name|lpad
argument_list|(
literal|""
operator|+
name|total
argument_list|,
literal|6
argument_list|)
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|max
operator|=
name|Math
operator|.
name|max
argument_list|(
name|max
argument_list|,
name|linesThisPeriod
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Integer
name|td
init|=
operator|(
name|Integer
operator|)
name|totalADay
operator|.
name|get
argument_list|(
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|)
decl_stmt|;
name|totalADay
operator|.
name|put
argument_list|(
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|,
operator|new
name|Integer
argument_list|(
name|total
operator|+
operator|(
name|td
operator|==
literal|null
condition|?
literal|0
else|:
name|td
operator|.
name|intValue
argument_list|()
operator|)
argument_list|)
argument_list|)
expr_stmt|;
name|lines
operator|.
name|add
argument_list|(
name|linesThisPeriod
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|max
condition|;
name|i
operator|++
control|)
block|{
name|String
name|line
init|=
literal|""
decl_stmt|;
if|if
condition|(
operator|!
name|iTotals
condition|)
block|{
if|if
condition|(
name|i
operator|==
literal|0
operator|||
name|iNewPage
condition|)
name|line
operator|+=
name|fixedTimes
operator|.
name|get
argument_list|(
name|time
argument_list|)
operator|+
literal|" "
expr_stmt|;
else|else
name|line
operator|+=
name|rpad
argument_list|(
literal|""
argument_list|,
literal|16
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Enumeration
name|f
init|=
name|lines
operator|.
name|elements
argument_list|()
init|;
name|f
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Vector
name|linesThisPeriod
init|=
operator|(
name|Vector
operator|)
name|f
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|i
operator|<
name|linesThisPeriod
operator|.
name|size
argument_list|()
condition|)
name|line
operator|+=
operator|(
name|String
operator|)
name|linesThisPeriod
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
else|else
name|line
operator|+=
name|rpad
argument_list|(
literal|""
argument_list|,
literal|20
argument_list|)
expr_stmt|;
if|if
condition|(
name|f
operator|.
name|hasMoreElements
argument_list|()
condition|)
name|line
operator|+=
literal|"  "
expr_stmt|;
block|}
name|println
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|iTotals
operator|&&
operator|!
name|iNewPage
condition|)
block|{
if|if
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
name|println
argument_list|(
literal|"                --------------- ----  --------------- ----  --------------- ----  --------------- ----  --------------- ----"
argument_list|)
expr_stmt|;
else|else
name|println
argument_list|(
literal|"--------------- --------------- ----  --------------- ----  --------------- ----  --------------- ----  --------------- ----"
argument_list|)
expr_stmt|;
block|}
name|setCont
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iTotals
condition|)
block|{
if|if
condition|(
name|getLineNumber
argument_list|()
operator|+
literal|5
operator|>
name|iNrLines
condition|)
block|{
name|newPage
argument_list|()
expr_stmt|;
name|setPageName
argument_list|(
literal|"Totals"
argument_list|)
expr_stmt|;
block|}
else|else
name|println
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|println
argument_list|(
literal|"Total Student Exams"
argument_list|)
expr_stmt|;
name|String
name|line1
init|=
literal|""
decl_stmt|,
name|line2
init|=
literal|""
decl_stmt|,
name|line3
init|=
literal|""
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Enumeration
name|f
init|=
name|ToolBox
operator|.
name|sortEnumeration
argument_list|(
name|days
operator|.
name|keys
argument_list|()
argument_list|)
init|;
name|f
operator|.
name|hasMoreElements
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|Integer
name|day
init|=
operator|(
name|Integer
operator|)
name|f
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|idx
operator|<
name|dIdx
operator|||
name|idx
operator|>=
name|dIdx
operator|+
name|nrCols
condition|)
continue|continue;
name|line1
operator|+=
name|mpad
argument_list|(
operator|(
name|String
operator|)
name|days
operator|.
name|get
argument_list|(
name|day
argument_list|)
argument_list|,
literal|20
argument_list|)
operator|+
literal|"  "
expr_stmt|;
name|line2
operator|+=
literal|"=============== ====  "
expr_stmt|;
name|line3
operator|+=
name|lpad
argument_list|(
name|totalADay
operator|.
name|get
argument_list|(
name|day
argument_list|)
operator|==
literal|null
condition|?
literal|""
else|:
name|totalADay
operator|.
name|get
argument_list|(
name|day
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
literal|20
argument_list|)
operator|+
literal|"  "
expr_stmt|;
block|}
name|println
argument_list|(
name|line1
argument_list|)
expr_stmt|;
name|println
argument_list|(
name|line2
argument_list|)
expr_stmt|;
name|println
argument_list|(
name|line3
argument_list|)
expr_stmt|;
name|timesThisPage
operator|=
literal|null
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

