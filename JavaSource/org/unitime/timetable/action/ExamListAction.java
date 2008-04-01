begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|action
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
name|Date
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
name|Iterator
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
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
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessages
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
name|Web
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
name|WebTable
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
name|ExamListForm
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
name|BuildingPref
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
name|DistributionPref
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
name|EveningPeriodPreferenceModel
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
name|ExamPeriodPref
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
name|PeriodPreferenceModel
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
name|Preference
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
name|RoomFeaturePref
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
name|RoomGroupPref
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
name|RoomPref
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
name|SubjectAreaDAO
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
name|WebSolver
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
name|webutil
operator|.
name|BackTracker
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
name|webutil
operator|.
name|Navigation
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
name|webutil
operator|.
name|PdfWebTable
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
name|webutil
operator|.
name|RequiredTimeTable
import|;
end_import

begin_class
specifier|public
class|class
name|ExamListAction
extends|extends
name|Action
block|{
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|ExamListForm
name|myForm
init|=
operator|(
name|ExamListForm
operator|)
name|form
decl_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|TimetableManager
name|manager
init|=
operator|(
name|user
operator|==
literal|null
condition|?
literal|null
else|:
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
operator|)
decl_stmt|;
name|Session
name|session
init|=
operator|(
name|user
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
operator|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
name|session
operator|==
literal|null
operator|||
operator|!
name|manager
operator|.
name|canSeeExams
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
comment|// Read operation to be performed
name|String
name|op
init|=
operator|(
name|myForm
operator|.
name|getOp
argument_list|()
operator|!=
literal|null
condition|?
name|myForm
operator|.
name|getOp
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
operator|&&
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|myForm
operator|.
name|setSubjectAreaId
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|)
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setCourseNbr
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|op
operator|==
literal|null
operator|&&
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|myForm
operator|.
name|setExamType
argument_list|(
operator|(
name|Integer
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"ExamList.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Search"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getSubjectAreaId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|,
name|myForm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|,
name|myForm
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"Exam.Type"
argument_list|,
name|myForm
operator|.
name|getExamType
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|PdfWebTable
name|table
init|=
name|getExamTable
argument_list|(
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|,
name|user
argument_list|,
name|manager
argument_list|,
name|session
argument_list|,
name|myForm
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|table
operator|!=
literal|null
condition|)
block|{
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"exams"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|table
operator|.
name|exportPdf
argument_list|(
name|file
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"ExamList.ord"
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_OPEN_URL
argument_list|,
literal|"temp/"
operator|+
name|file
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
literal|"Add Examination"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"addExam"
argument_list|)
return|;
block|}
name|myForm
operator|.
name|setSubjectAreas
argument_list|(
name|TimetableManager
operator|.
name|getSubjectAreas
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|SubjectArea
name|firstSubjectArea
init|=
operator|(
name|SubjectArea
operator|)
name|myForm
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|myForm
operator|.
name|setSubjectAreaId
argument_list|(
name|firstSubjectArea
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|myForm
operator|.
name|getSubjectAreaId
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getSubjectAreaId
argument_list|()
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
block|{
name|PdfWebTable
name|table
init|=
name|getExamTable
argument_list|(
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|,
name|user
argument_list|,
name|manager
argument_list|,
name|session
argument_list|,
name|myForm
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|table
operator|!=
literal|null
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ExamList.table"
argument_list|,
name|table
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"ExamList.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Vector
name|ids
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|table
operator|.
name|getLines
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|WebTable
operator|.
name|WebTableLine
name|line
init|=
operator|(
name|WebTable
operator|.
name|WebTableLine
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|ids
operator|.
name|add
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|line
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Navigation
operator|.
name|set
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Navigation
operator|.
name|sInstructionalOfferingLevel
argument_list|,
name|ids
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"exams"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No examination matching the above criteria was found."
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|subjectAreaName
init|=
literal|""
decl_stmt|;
try|try
block|{
name|subjectAreaName
operator|=
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|myForm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"examList.do?op=Search&examType="
operator|+
name|myForm
operator|.
name|getExamType
argument_list|()
operator|+
literal|"&subjectAreaId="
operator|+
name|myForm
operator|.
name|getSubjectAreaId
argument_list|()
operator|+
literal|"&courseNbr="
operator|+
name|myForm
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|Exam
operator|.
name|sExamTypes
index|[
name|myForm
operator|.
name|getExamType
argument_list|()
index|]
operator|+
literal|" Exams ("
operator|+
operator|(
name|Constants
operator|.
name|ALL_OPTION_VALUE
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
condition|?
literal|"All"
else|:
name|subjectAreaName
operator|+
operator|(
name|myForm
operator|.
name|getCourseNbr
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
literal|" "
operator|+
name|myForm
operator|.
name|getCourseNbr
argument_list|()
operator|)
operator|)
operator|+
literal|")"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"list"
argument_list|)
return|;
block|}
specifier|public
name|PdfWebTable
name|getExamTable
parameter_list|(
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|User
name|user
parameter_list|,
name|TimetableManager
name|manager
parameter_list|,
name|Session
name|session
parameter_list|,
name|ExamListForm
name|form
parameter_list|,
name|boolean
name|html
parameter_list|)
block|{
name|Collection
name|exams
init|=
operator|(
name|form
operator|.
name|getSubjectAreaId
argument_list|()
operator|==
literal|null
operator|||
name|form
operator|.
name|getSubjectAreaId
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
literal|"null"
operator|.
name|equals
argument_list|(
name|form
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
condition|?
literal|null
else|:
name|Constants
operator|.
name|ALL_OPTION_VALUE
operator|.
name|equals
argument_list|(
name|form
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
condition|?
name|Exam
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|form
operator|.
name|getExamType
argument_list|()
argument_list|)
else|:
name|Exam
operator|.
name|findExamsOfCourse
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|form
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
argument_list|,
name|form
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|form
operator|.
name|getExamType
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|exams
operator|==
literal|null
operator|||
name|exams
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|String
name|nl
init|=
operator|(
name|html
condition|?
literal|"<br>"
else|:
literal|"\n"
operator|)
decl_stmt|;
name|boolean
name|timeVertical
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridVertical
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|boolean
name|timeText
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridAsText
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|PdfWebTable
name|table
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|11
argument_list|,
name|Exam
operator|.
name|sExamTypes
index|[
name|form
operator|.
name|getExamType
argument_list|()
index|]
operator|+
literal|" Examinations"
argument_list|,
literal|"examList.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Classes / Courses"
block|,
literal|"Length"
block|,
literal|"Seating"
operator|+
name|nl
operator|+
literal|"Type"
block|,
literal|"Size"
block|,
literal|"Max"
operator|+
name|nl
operator|+
literal|"Rooms"
block|,
literal|"Instructor"
block|,
literal|"Period"
operator|+
name|nl
operator|+
literal|"Preferences"
block|,
literal|"Room"
operator|+
name|nl
operator|+
literal|"Preferences"
block|,
literal|"Distribution"
operator|+
name|nl
operator|+
literal|"Preferences"
block|,
literal|"Assigned"
operator|+
name|nl
operator|+
literal|"Period"
block|,
literal|"Assigned"
operator|+
name|nl
operator|+
literal|"Room"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"right"
block|,
literal|"center"
block|,
literal|"right"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
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
name|Exam
name|exam
init|=
operator|(
name|Exam
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|objects
init|=
literal|""
decl_stmt|,
name|perPref
init|=
literal|""
decl_stmt|,
name|roomPref
init|=
literal|""
decl_stmt|,
name|distPref
init|=
literal|""
decl_stmt|,
name|per
init|=
literal|""
decl_stmt|,
name|rooms
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Enumeration
name|e
init|=
name|exam
operator|.
name|getOwnerObjects
argument_list|()
operator|.
name|elements
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Object
name|object
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|objects
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|objects
operator|+=
name|nl
expr_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|Class_
condition|)
name|objects
operator|+=
operator|(
operator|(
name|Class_
operator|)
name|object
operator|)
operator|.
name|getClassLabel
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|InstrOfferingConfig
condition|)
name|objects
operator|+=
operator|(
operator|(
name|InstrOfferingConfig
operator|)
name|object
operator|)
operator|.
name|toString
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|InstructionalOffering
condition|)
name|objects
operator|+=
operator|(
operator|(
name|InstructionalOffering
operator|)
name|object
operator|)
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
if|else if
condition|(
name|object
operator|instanceof
name|CourseOffering
condition|)
name|objects
operator|+=
operator|(
operator|(
name|CourseOffering
operator|)
name|object
operator|)
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
else|else
name|objects
operator|+=
name|object
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|ExamAssignment
name|ea
init|=
operator|(
name|examAssignment
operator|!=
literal|null
condition|?
name|examAssignment
operator|.
name|getAssignment
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
else|:
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|?
operator|new
name|ExamAssignment
argument_list|(
name|exam
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|ea
operator|!=
literal|null
condition|)
block|{
name|per
operator|=
operator|(
name|html
condition|?
name|ea
operator|.
name|getPeriodAbbreviationWithPref
argument_list|()
else|:
name|ea
operator|.
name|getPeriodAbbreviation
argument_list|()
operator|)
expr_stmt|;
name|rooms
operator|=
operator|(
name|html
condition|?
name|ea
operator|.
name|getRoomsNameWithPref
argument_list|(
name|nl
argument_list|)
else|:
name|ea
operator|.
name|getRoomsName
argument_list|(
name|nl
argument_list|)
operator|)
expr_stmt|;
block|}
if|if
condition|(
name|html
condition|)
block|{
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomPref
operator|+=
name|nl
expr_stmt|;
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|BuildingPref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomPref
operator|+=
name|nl
expr_stmt|;
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|RoomFeaturePref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomPref
operator|+=
name|nl
expr_stmt|;
name|roomPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|RoomGroupPref
operator|.
name|class
argument_list|)
expr_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|endsWith
argument_list|(
name|nl
argument_list|)
condition|)
name|roomPref
operator|=
name|roomPref
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|roomPref
operator|.
name|length
argument_list|()
operator|-
name|nl
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|timeText
operator|||
name|Exam
operator|.
name|sExamTypeEvening
operator|==
name|exam
operator|.
name|getExamType
argument_list|()
condition|)
block|{
if|if
condition|(
name|Exam
operator|.
name|sExamTypeEvening
operator|==
name|exam
operator|.
name|getExamType
argument_list|()
condition|)
block|{
name|EveningPeriodPreferenceModel
name|epx
init|=
operator|new
name|EveningPeriodPreferenceModel
argument_list|(
name|exam
operator|.
name|getSession
argument_list|()
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|epx
operator|.
name|canDo
argument_list|()
condition|)
block|{
name|epx
operator|.
name|load
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|perPref
operator|+=
name|epx
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|perPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|perPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|PeriodPreferenceModel
name|px
init|=
operator|new
name|PeriodPreferenceModel
argument_list|(
name|exam
operator|.
name|getSession
argument_list|()
argument_list|,
name|ea
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
argument_list|)
decl_stmt|;
name|px
operator|.
name|load
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|RequiredTimeTable
name|rtt
init|=
operator|new
name|RequiredTimeTable
argument_list|(
name|px
argument_list|)
decl_stmt|;
name|File
name|imageFileName
init|=
literal|null
decl_stmt|;
try|try
block|{
name|imageFileName
operator|=
name|rtt
operator|.
name|createImage
argument_list|(
name|timeVertical
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|String
name|title
init|=
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|imageFileName
operator|!=
literal|null
condition|)
name|perPref
operator|=
literal|"<img border='0' src='temp/"
operator|+
operator|(
name|imageFileName
operator|.
name|getName
argument_list|()
operator|)
operator|+
literal|"' title='"
operator|+
name|title
operator|+
literal|"'>"
expr_stmt|;
else|else
name|perPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
name|distPref
operator|+=
name|exam
operator|.
name|getEffectivePrefHtmlForPrefType
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|effectivePreferences
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
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
name|Preference
name|pref
init|=
operator|(
name|Preference
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomPref
operator|+=
name|nl
expr_stmt|;
name|roomPref
operator|+=
name|PreferenceLevel
operator|.
name|prolog2abbv
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|pref
operator|.
name|preferenceText
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|effectivePreferences
argument_list|(
name|BuildingPref
operator|.
name|class
argument_list|)
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
name|Preference
name|pref
init|=
operator|(
name|Preference
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomPref
operator|+=
name|nl
expr_stmt|;
name|roomPref
operator|+=
name|PreferenceLevel
operator|.
name|prolog2abbv
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|pref
operator|.
name|preferenceText
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|effectivePreferences
argument_list|(
name|RoomFeaturePref
operator|.
name|class
argument_list|)
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
name|Preference
name|pref
init|=
operator|(
name|Preference
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomPref
operator|+=
name|nl
expr_stmt|;
name|roomPref
operator|+=
name|PreferenceLevel
operator|.
name|prolog2abbv
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|pref
operator|.
name|preferenceText
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|effectivePreferences
argument_list|(
name|RoomGroupPref
operator|.
name|class
argument_list|)
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
name|Preference
name|pref
init|=
operator|(
name|Preference
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|roomPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomPref
operator|+=
name|nl
expr_stmt|;
name|roomPref
operator|+=
name|PreferenceLevel
operator|.
name|prolog2abbv
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|pref
operator|.
name|preferenceText
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|effectivePreferences
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
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
name|Preference
name|pref
init|=
operator|(
name|Preference
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|perPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|perPref
operator|+=
name|nl
expr_stmt|;
name|perPref
operator|+=
name|PreferenceLevel
operator|.
name|prolog2abbv
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|pref
operator|.
name|preferenceText
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|j
init|=
name|exam
operator|.
name|effectivePreferences
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
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
name|DistributionPref
name|pref
init|=
operator|(
name|DistributionPref
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|distPref
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|distPref
operator|+=
name|nl
expr_stmt|;
name|distPref
operator|+=
name|PreferenceLevel
operator|.
name|prolog2abbv
argument_list|(
name|pref
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|pref
operator|.
name|preferenceText
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
literal|" ("
argument_list|,
literal|", "
argument_list|,
literal|")"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&lt;"
argument_list|,
literal|"<"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&gt;"
argument_list|,
literal|">"
argument_list|)
expr_stmt|;
block|}
block|}
name|ExamInfo
name|ei
init|=
operator|(
name|ea
operator|==
literal|null
condition|?
operator|new
name|ExamInfo
argument_list|(
name|exam
argument_list|)
else|:
name|ea
operator|)
decl_stmt|;
name|int
name|nrStudents
init|=
name|ei
operator|.
name|getNrStudents
argument_list|()
decl_stmt|;
name|String
name|instructors
init|=
name|ei
operator|.
name|getInstructorName
argument_list|(
name|nl
argument_list|)
decl_stmt|;
name|table
operator|.
name|addLine
argument_list|(
literal|"onClick=\"document.location='examDetail.do?examId="
operator|+
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
argument_list|,
operator|new
name|String
index|[]
block|{
operator|(
name|html
condition|?
literal|"<a name='"
operator|+
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
else|:
literal|""
operator|)
operator|+
name|objects
operator|+
operator|(
name|html
condition|?
literal|"</a>"
else|:
literal|""
operator|)
block|,
name|exam
operator|.
name|getLength
argument_list|()
operator|.
name|toString
argument_list|()
block|,
operator|(
name|Exam
operator|.
name|sSeatingTypeNormal
operator|==
name|exam
operator|.
name|getSeatingType
argument_list|()
condition|?
literal|"Normal"
else|:
literal|"Exam"
operator|)
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|nrStudents
argument_list|)
block|,
name|exam
operator|.
name|getMaxNbrRooms
argument_list|()
operator|.
name|toString
argument_list|()
block|,
name|instructors
block|,
name|perPref
block|,
name|roomPref
block|,
name|distPref
block|,
name|per
block|,
name|rooms
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|exam
operator|.
name|firstOwner
argument_list|()
block|,
name|exam
operator|.
name|getLength
argument_list|()
block|,
name|exam
operator|.
name|getSeatingType
argument_list|()
block|,
name|nrStudents
block|,
name|exam
operator|.
name|getMaxNbrRooms
argument_list|()
block|,
name|instructors
block|,
name|perPref
block|,
name|roomPref
block|,
name|distPref
block|,
operator|(
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Date
argument_list|(
literal|0
argument_list|)
else|:
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|.
name|getStartTime
argument_list|()
operator|)
block|,
name|rooms
block|}
argument_list|,
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|table
return|;
block|}
block|}
end_class

end_unit

