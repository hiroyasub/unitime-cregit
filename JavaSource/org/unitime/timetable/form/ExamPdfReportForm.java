begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
package|;
end_package

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
name|TreeSet
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
name|HttpSession
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
name|ActionErrors
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
name|UserData
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
name|reports
operator|.
name|exam
operator|.
name|AbbvExamScheduleByCourseReport
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
name|reports
operator|.
name|exam
operator|.
name|AbbvScheduleByCourseReport
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
name|reports
operator|.
name|exam
operator|.
name|ConflictsByCourseAndInstructorReport
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
name|reports
operator|.
name|exam
operator|.
name|ConflictsByCourseAndStudentReport
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
name|reports
operator|.
name|exam
operator|.
name|ExamScheduleByPeriodReport
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
name|reports
operator|.
name|exam
operator|.
name|ExamVerificationReport
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
name|reports
operator|.
name|exam
operator|.
name|InstructorExamReport
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
name|reports
operator|.
name|exam
operator|.
name|PeriodChartReport
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
name|reports
operator|.
name|exam
operator|.
name|ScheduleByCourseReport
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
name|reports
operator|.
name|exam
operator|.
name|ScheduleByPeriodReport
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
name|reports
operator|.
name|exam
operator|.
name|ScheduleByRoomReport
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
name|reports
operator|.
name|exam
operator|.
name|StudentExamReport
import|;
end_import

begin_comment
comment|/*  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamPdfReportForm
extends|extends
name|ExamReportForm
block|{
specifier|private
name|String
index|[]
name|iReports
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iMode
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAll
init|=
literal|false
decl_stmt|;
specifier|private
name|String
index|[]
name|iSubjects
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iDispRooms
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iNoRoom
init|=
literal|""
decl_stmt|;
specifier|private
name|boolean
name|iDirect
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iM2d
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iBtb
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iLimit
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iTotals
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iDispLimit
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iRoomCodes
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iEmail
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iAddr
decl_stmt|,
name|iCc
decl_stmt|,
name|iBcc
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iEmailDeputies
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iItype
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iReport
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iMessage
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSubject
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iSince
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iEmailInstructors
decl_stmt|,
name|iEmailStudents
decl_stmt|;
specifier|private
name|boolean
name|iClassSchedule
init|=
literal|false
decl_stmt|;
specifier|public
specifier|static
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
name|sRegisteredReports
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sModes
init|=
block|{
literal|"PDF (Letter)"
block|,
literal|"PDF (Ledger)"
block|,
literal|"Text"
block|}
decl_stmt|;
specifier|public
specifier|static
name|int
name|sDeliveryDownload
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
name|int
name|sDeliveryEmail
init|=
literal|1
decl_stmt|;
static|static
block|{
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Schedule by Course"
argument_list|,
name|ScheduleByCourseReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Student Conflicts"
argument_list|,
name|ConflictsByCourseAndStudentReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Instuctor Conflicts"
argument_list|,
name|ConflictsByCourseAndInstructorReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Schedule by Period"
argument_list|,
name|ScheduleByPeriodReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Schedule by Period (Exams)"
argument_list|,
name|ExamScheduleByPeriodReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Schedule by Room"
argument_list|,
name|ScheduleByRoomReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Period Chart"
argument_list|,
name|PeriodChartReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Verification"
argument_list|,
name|ExamVerificationReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Abbreviated Schedule"
argument_list|,
name|AbbvScheduleByCourseReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Abbreviated Schedule (Exams)"
argument_list|,
name|AbbvExamScheduleByCourseReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Individual Instructor Schedule"
argument_list|,
name|InstructorExamReport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sRegisteredReports
operator|.
name|put
argument_list|(
literal|"Individual Student Schedule"
argument_list|,
name|StudentExamReport
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
name|super
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|iReports
operator|==
literal|null
operator|||
name|iReports
operator|.
name|length
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"reports"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No report selected."
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|iAll
operator|&&
operator|(
name|iSubjects
operator|==
literal|null
operator|||
name|iSubjects
operator|.
name|length
operator|==
literal|0
operator|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"subjects"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No subject area selected."
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|super
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|iReports
operator|=
literal|null
expr_stmt|;
name|iMode
operator|=
name|sModes
index|[
literal|0
index|]
expr_stmt|;
name|iAll
operator|=
literal|false
expr_stmt|;
name|iDispRooms
operator|=
literal|false
expr_stmt|;
name|iNoRoom
operator|=
literal|null
expr_stmt|;
name|iDirect
operator|=
literal|false
expr_stmt|;
name|iM2d
operator|=
literal|false
expr_stmt|;
name|iBtb
operator|=
literal|false
expr_stmt|;
name|iLimit
operator|=
literal|null
expr_stmt|;
name|iTotals
operator|=
literal|false
expr_stmt|;
name|iRoomCodes
operator|=
literal|null
expr_stmt|;
name|iEmail
operator|=
literal|false
expr_stmt|;
name|iAddr
operator|=
literal|null
expr_stmt|;
name|iCc
operator|=
literal|null
expr_stmt|;
name|iBcc
operator|=
literal|null
expr_stmt|;
name|iEmailDeputies
operator|=
literal|false
expr_stmt|;
name|iSubject
operator|=
literal|"Examination Report"
expr_stmt|;
name|iMessage
operator|=
literal|null
expr_stmt|;
name|iReport
operator|=
literal|null
expr_stmt|;
name|iDispLimit
operator|=
literal|false
expr_stmt|;
name|iSince
operator|=
literal|null
expr_stmt|;
name|iEmailInstructors
operator|=
literal|false
expr_stmt|;
name|iEmailStudents
operator|=
literal|false
expr_stmt|;
name|iClassSchedule
operator|=
literal|false
expr_stmt|;
name|iItype
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|getAddress
argument_list|()
operator|==
literal|null
condition|)
block|{
name|TimetableManager
name|manager
init|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
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
name|getEmailAddress
argument_list|()
operator|!=
literal|null
condition|)
name|setAddress
argument_list|(
name|manager
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|super
operator|.
name|load
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|setAll
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamPdfReport.all"
argument_list|)
operator|==
literal|null
condition|?
literal|true
else|:
operator|(
name|Boolean
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamPdfReport.all"
argument_list|)
argument_list|)
expr_stmt|;
name|setReports
argument_list|(
operator|(
name|String
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamPdfReport.reports"
argument_list|)
argument_list|)
expr_stmt|;
name|setMode
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamPdfReport.mode"
argument_list|)
operator|==
literal|null
condition|?
name|sModes
index|[
literal|0
index|]
else|:
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamPdfReport.mode"
argument_list|)
argument_list|)
expr_stmt|;
name|setSubjects
argument_list|(
operator|(
name|String
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamPdfReport.subjects"
argument_list|)
argument_list|)
expr_stmt|;
name|setDispRooms
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.dispRooms"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|setNoRoom
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.noRoom"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.report.noroom"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setDirect
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.direct"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|setM2d
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.m2d"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|setBtb
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.btb"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|setLimit
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.limit"
argument_list|)
argument_list|)
expr_stmt|;
name|setTotals
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.totals"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|setRoomCodes
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.roomCodes"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.report.roomcode"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setEmail
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.email"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|setAddress
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.addr"
argument_list|)
argument_list|)
expr_stmt|;
name|setCc
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.cc"
argument_list|)
argument_list|)
expr_stmt|;
name|setBcc
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.bcc"
argument_list|)
argument_list|)
expr_stmt|;
name|setEmailDeputies
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.emailDeputies"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|setMessage
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.message"
argument_list|)
argument_list|)
expr_stmt|;
name|setSubject
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.subject"
argument_list|,
literal|"Examination Report"
argument_list|)
argument_list|)
expr_stmt|;
name|setDispLimit
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.dispLimit"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|setSince
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.since"
argument_list|)
argument_list|)
expr_stmt|;
name|setEmailInstructors
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.emailInstructors"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|setEmailStudents
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.emailStudents"
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|setItype
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.itype"
argument_list|,
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.report.itype"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setClassSchedule
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.cschedule"
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|super
operator|.
name|save
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamPdfReport.reports"
argument_list|,
name|getReports
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamPdfReport.mode"
argument_list|,
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamPdfReport.all"
argument_list|,
name|getAll
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamPdfReport.subjects"
argument_list|,
name|getSubjects
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.dispRooms"
argument_list|,
name|getDispRooms
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.noRoom"
argument_list|,
name|getNoRoom
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.direct"
argument_list|,
name|getDirect
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.m2d"
argument_list|,
name|getM2d
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.btb"
argument_list|,
name|getBtb
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.limit"
argument_list|,
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.totals"
argument_list|,
name|getTotals
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.roomCodes"
argument_list|,
name|getRoomCodes
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.email"
argument_list|,
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.addr"
argument_list|,
name|getAddress
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.cc"
argument_list|,
name|getCc
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.bcc"
argument_list|,
name|getBcc
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.emailDeputies"
argument_list|,
name|getEmailDeputies
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.message"
argument_list|,
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.subject"
argument_list|,
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.dispLimit"
argument_list|,
name|getDispLimit
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.since"
argument_list|,
name|getSince
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.emailInstructors"
argument_list|,
name|getEmailInstructors
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.emailStudents"
argument_list|,
name|getEmailStudents
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.itype"
argument_list|,
name|getItype
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|session
argument_list|,
literal|"ExamPdfReport.cschedule"
argument_list|,
name|getClassSchedule
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getReports
parameter_list|()
block|{
return|return
name|iReports
return|;
block|}
specifier|public
name|void
name|setReports
parameter_list|(
name|String
index|[]
name|reports
parameter_list|)
block|{
name|iReports
operator|=
name|reports
expr_stmt|;
block|}
specifier|public
name|String
name|getMode
parameter_list|()
block|{
return|return
name|iMode
return|;
block|}
specifier|public
name|void
name|setMode
parameter_list|(
name|String
name|mode
parameter_list|)
block|{
name|iMode
operator|=
name|mode
expr_stmt|;
block|}
specifier|public
name|int
name|getModeIdx
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sModes
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|sModes
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|iMode
argument_list|)
condition|)
return|return
name|i
return|;
return|return
literal|0
return|;
block|}
specifier|public
name|boolean
name|getAll
parameter_list|()
block|{
return|return
name|iAll
return|;
block|}
specifier|public
name|void
name|setAll
parameter_list|(
name|boolean
name|all
parameter_list|)
block|{
name|iAll
operator|=
name|all
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getSubjects
parameter_list|()
block|{
return|return
name|iSubjects
return|;
block|}
specifier|public
name|void
name|setSubjects
parameter_list|(
name|String
index|[]
name|subjects
parameter_list|)
block|{
name|iSubjects
operator|=
name|subjects
expr_stmt|;
block|}
specifier|public
name|boolean
name|getDispRooms
parameter_list|()
block|{
return|return
name|iDispRooms
return|;
block|}
specifier|public
name|void
name|setDispRooms
parameter_list|(
name|boolean
name|dispRooms
parameter_list|)
block|{
name|iDispRooms
operator|=
name|dispRooms
expr_stmt|;
block|}
specifier|public
name|String
name|getNoRoom
parameter_list|()
block|{
return|return
name|iNoRoom
return|;
block|}
specifier|public
name|void
name|setNoRoom
parameter_list|(
name|String
name|noRoom
parameter_list|)
block|{
name|iNoRoom
operator|=
name|noRoom
expr_stmt|;
block|}
specifier|public
name|boolean
name|getBtb
parameter_list|()
block|{
return|return
name|iBtb
return|;
block|}
specifier|public
name|void
name|setBtb
parameter_list|(
name|boolean
name|btb
parameter_list|)
block|{
name|iBtb
operator|=
name|btb
expr_stmt|;
block|}
specifier|public
name|boolean
name|getM2d
parameter_list|()
block|{
return|return
name|iM2d
return|;
block|}
specifier|public
name|void
name|setM2d
parameter_list|(
name|boolean
name|m2d
parameter_list|)
block|{
name|iM2d
operator|=
name|m2d
expr_stmt|;
block|}
specifier|public
name|boolean
name|getDirect
parameter_list|()
block|{
return|return
name|iDirect
return|;
block|}
specifier|public
name|void
name|setDirect
parameter_list|(
name|boolean
name|direct
parameter_list|)
block|{
name|iDirect
operator|=
name|direct
expr_stmt|;
block|}
specifier|public
name|String
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|String
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getTotals
parameter_list|()
block|{
return|return
name|iTotals
return|;
block|}
specifier|public
name|void
name|setTotals
parameter_list|(
name|boolean
name|totals
parameter_list|)
block|{
name|iTotals
operator|=
name|totals
expr_stmt|;
block|}
specifier|public
name|String
name|getRoomCodes
parameter_list|()
block|{
return|return
name|iRoomCodes
return|;
block|}
specifier|public
name|void
name|setRoomCodes
parameter_list|(
name|String
name|roomCodes
parameter_list|)
block|{
name|iRoomCodes
operator|=
name|roomCodes
expr_stmt|;
block|}
specifier|public
name|boolean
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|boolean
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|boolean
name|getEmailDeputies
parameter_list|()
block|{
return|return
name|iEmailDeputies
return|;
block|}
specifier|public
name|void
name|setEmailDeputies
parameter_list|(
name|boolean
name|emailDeputies
parameter_list|)
block|{
name|iEmailDeputies
operator|=
name|emailDeputies
expr_stmt|;
block|}
specifier|public
name|String
name|getAddress
parameter_list|()
block|{
return|return
name|iAddr
return|;
block|}
specifier|public
name|void
name|setAddress
parameter_list|(
name|String
name|addr
parameter_list|)
block|{
name|iAddr
operator|=
name|addr
expr_stmt|;
block|}
specifier|public
name|String
name|getCc
parameter_list|()
block|{
return|return
name|iCc
return|;
block|}
specifier|public
name|void
name|setCc
parameter_list|(
name|String
name|cc
parameter_list|)
block|{
name|iCc
operator|=
name|cc
expr_stmt|;
block|}
specifier|public
name|String
name|getBcc
parameter_list|()
block|{
return|return
name|iBcc
return|;
block|}
specifier|public
name|void
name|setBcc
parameter_list|(
name|String
name|bcc
parameter_list|)
block|{
name|iBcc
operator|=
name|bcc
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanEmail
parameter_list|()
block|{
return|return
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
operator|!=
literal|null
operator|&&
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
return|;
block|}
specifier|public
name|String
name|getReport
parameter_list|()
block|{
return|return
name|iReport
return|;
block|}
specifier|public
name|void
name|setReport
parameter_list|(
name|String
name|report
parameter_list|)
block|{
name|iReport
operator|=
name|report
expr_stmt|;
block|}
specifier|public
name|void
name|log
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iReport
operator|+=
name|message
operator|+
literal|"<br>"
expr_stmt|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|iSubject
return|;
block|}
specifier|public
name|void
name|setSubject
parameter_list|(
name|String
name|subject
parameter_list|)
block|{
name|iSubject
operator|=
name|subject
expr_stmt|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|String
argument_list|>
name|getAllReports
parameter_list|()
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|sRegisteredReports
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
index|[]
name|getModes
parameter_list|()
block|{
return|return
name|sModes
return|;
block|}
specifier|public
name|boolean
name|getDispLimit
parameter_list|()
block|{
return|return
name|iDispLimit
return|;
block|}
specifier|public
name|void
name|setDispLimit
parameter_list|(
name|boolean
name|dispLimit
parameter_list|)
block|{
name|iDispLimit
operator|=
name|dispLimit
expr_stmt|;
block|}
specifier|public
name|String
name|getSince
parameter_list|()
block|{
return|return
name|iSince
return|;
block|}
specifier|public
name|void
name|setSince
parameter_list|(
name|String
name|since
parameter_list|)
block|{
name|iSince
operator|=
name|since
expr_stmt|;
block|}
specifier|public
name|boolean
name|getEmailInstructors
parameter_list|()
block|{
return|return
name|iEmailInstructors
return|;
block|}
specifier|public
name|void
name|setEmailInstructors
parameter_list|(
name|boolean
name|emailInstructors
parameter_list|)
block|{
name|iEmailInstructors
operator|=
name|emailInstructors
expr_stmt|;
block|}
specifier|public
name|boolean
name|getEmailStudents
parameter_list|()
block|{
return|return
name|iEmailStudents
return|;
block|}
specifier|public
name|void
name|setEmailStudents
parameter_list|(
name|boolean
name|emailStudents
parameter_list|)
block|{
name|iEmailStudents
operator|=
name|emailStudents
expr_stmt|;
block|}
specifier|public
name|boolean
name|getItype
parameter_list|()
block|{
return|return
name|iItype
return|;
block|}
specifier|public
name|void
name|setItype
parameter_list|(
name|boolean
name|itype
parameter_list|)
block|{
name|iItype
operator|=
name|itype
expr_stmt|;
block|}
specifier|public
name|boolean
name|getClassSchedule
parameter_list|()
block|{
return|return
name|iClassSchedule
return|;
block|}
specifier|public
name|void
name|setClassSchedule
parameter_list|(
name|boolean
name|classSchedule
parameter_list|)
block|{
name|iClassSchedule
operator|=
name|classSchedule
expr_stmt|;
block|}
block|}
end_class

end_unit

