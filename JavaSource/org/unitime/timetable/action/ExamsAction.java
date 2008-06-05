begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|security
operator|.
name|auth
operator|.
name|login
operator|.
name|LoginContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|security
operator|.
name|auth
operator|.
name|login
operator|.
name|LoginException
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
name|unitime
operator|.
name|commons
operator|.
name|MultiComparable
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
name|authenticate
operator|.
name|jaas
operator|.
name|UserPasswordHandler
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
name|ExamsForm
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
name|ApplicationConfig
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
name|ExamInfo
operator|.
name|ExamSectionInfo
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
name|PdfWebTable
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamsAction
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
name|ExamsForm
name|myForm
init|=
operator|(
name|ExamsForm
operator|)
name|form
decl_stmt|;
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
literal|"Apply"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getUsername
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getUsername
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|myForm
operator|.
name|getPassword
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getPassword
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
try|try
block|{
name|UserPasswordHandler
name|handler
init|=
operator|new
name|UserPasswordHandler
argument_list|(
name|myForm
operator|.
name|getUsername
argument_list|()
argument_list|,
name|myForm
operator|.
name|getPassword
argument_list|()
argument_list|)
decl_stmt|;
name|LoginContext
name|lc
init|=
operator|new
name|LoginContext
argument_list|(
literal|"Timetabling"
argument_list|,
name|handler
argument_list|)
decl_stmt|;
name|lc
operator|.
name|login
argument_list|()
expr_stmt|;
name|Set
name|creds
init|=
name|lc
operator|.
name|getSubject
argument_list|()
operator|.
name|getPublicCredentials
argument_list|()
decl_stmt|;
if|if
condition|(
name|creds
operator|==
literal|null
operator|||
name|creds
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|myForm
operator|.
name|setMessage
argument_list|(
literal|"Authentication failed"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|creds
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
name|o
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|User
condition|)
block|{
name|User
name|user
init|=
operator|(
name|User
operator|)
name|o
decl_stmt|;
name|HttpSession
name|session
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"loggedOn"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"hdnCallingScreen"
argument_list|,
literal|"main.jsp"
argument_list|)
expr_stmt|;
name|Web
operator|.
name|setUser
argument_list|(
name|session
argument_list|,
name|user
argument_list|)
expr_stmt|;
name|String
name|appStatus
init|=
name|ApplicationConfig
operator|.
name|getConfigValue
argument_list|(
name|Constants
operator|.
name|CFG_APP_ACCESS_LEVEL
argument_list|,
name|Constants
operator|.
name|APP_ACL_ALL
argument_list|)
decl_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CFG_APP_ACCESS_LEVEL
argument_list|,
name|appStatus
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"authUserExtId"
argument_list|,
name|user
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"loginFromExams"
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"personal"
argument_list|)
return|;
comment|//response.sendRedirect("selectPrimaryRole.do"); break;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|LoginException
name|le
parameter_list|)
block|{
name|myForm
operator|.
name|setMessage
argument_list|(
literal|"Authentication failed"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|myForm
operator|.
name|load
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"exams.order"
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
name|myForm
operator|.
name|getSession
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getSubjectArea
argument_list|()
operator|!=
literal|null
operator|&&
name|myForm
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|List
name|exams
init|=
literal|null
decl_stmt|;
if|if
condition|(
literal|"--ALL--"
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
name|exams
operator|=
name|Exam
operator|.
name|findAll
argument_list|(
name|myForm
operator|.
name|getSession
argument_list|()
argument_list|,
name|myForm
operator|.
name|getExamType
argument_list|()
argument_list|)
expr_stmt|;
else|else
block|{
name|SubjectArea
name|sa
init|=
name|SubjectArea
operator|.
name|findByAbbv
argument_list|(
name|myForm
operator|.
name|getSession
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sa
operator|!=
literal|null
condition|)
name|exams
operator|=
name|Exam
operator|.
name|findExamsOfSubjectArea
argument_list|(
name|sa
operator|.
name|getUniqueId
argument_list|()
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
name|exams
operator|!=
literal|null
operator|&&
operator|!
name|exams
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Vector
argument_list|<
name|ExamAssignment
argument_list|>
name|assignments
init|=
operator|new
name|Vector
argument_list|()
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
if|if
condition|(
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|)
name|assignments
operator|.
name|add
argument_list|(
operator|new
name|ExamAssignment
argument_list|(
name|exam
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|assignments
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|PdfWebTable
name|table
init|=
name|getTable
argument_list|(
literal|true
argument_list|,
name|myForm
argument_list|,
name|assignments
argument_list|)
decl_stmt|;
if|if
condition|(
name|table
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setTable
argument_list|(
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
literal|"exams.order"
argument_list|)
argument_list|)
argument_list|,
name|table
operator|.
name|getNrColumns
argument_list|()
argument_list|,
name|table
operator|.
name|getLines
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
specifier|private
name|PdfWebTable
name|getTable
parameter_list|(
name|boolean
name|html
parameter_list|,
name|ExamsForm
name|form
parameter_list|,
name|Vector
argument_list|<
name|ExamAssignment
argument_list|>
name|exams
parameter_list|)
block|{
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
name|PdfWebTable
name|table
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|7
argument_list|,
name|form
operator|.
name|getSessionLabel
argument_list|()
operator|+
literal|" "
operator|+
name|form
operator|.
name|getExamTypeLabel
argument_list|()
operator|+
literal|" examinations"
operator|+
operator|(
literal|"--ALL--"
operator|.
name|equals
argument_list|(
name|form
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|?
literal|""
else|:
literal|" ("
operator|+
name|form
operator|.
name|getSubjectArea
argument_list|()
operator|+
literal|")"
operator|)
argument_list|,
literal|"exams.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Subject"
block|,
literal|"Course"
block|,
operator|(
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.report.external"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|?
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.report.external.name"
argument_list|,
literal|"External Id"
argument_list|)
else|:
literal|"Instruction Type"
operator|)
block|,
literal|"Section"
block|,
literal|"Date"
block|,
literal|"Time"
block|,
literal|"Room"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
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
block|}
argument_list|)
decl_stmt|;
name|table
operator|.
name|setRowStyle
argument_list|(
literal|"white-space:nowrap"
argument_list|)
expr_stmt|;
name|String
name|noRoom
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.report.noroom"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
for|for
control|(
name|ExamAssignment
name|exam
range|:
name|exams
control|)
block|{
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
operator|!
literal|"--ALL--"
operator|.
name|equals
argument_list|(
name|form
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
operator|&&
operator|!
name|section
operator|.
name|getSubject
argument_list|()
operator|.
name|equals
argument_list|(
name|form
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
condition|)
continue|continue;
name|table
operator|.
name|addLine
argument_list|(
operator|new
name|String
index|[]
block|{
name|section
operator|.
name|getSubject
argument_list|()
block|,
name|section
operator|.
name|getCourseNbr
argument_list|()
block|,
name|section
operator|.
name|getItype
argument_list|()
block|,
name|section
operator|.
name|getSection
argument_list|()
block|,
name|exam
operator|.
name|getDate
argument_list|(
literal|false
argument_list|)
block|,
name|exam
operator|.
name|getTime
argument_list|(
literal|false
argument_list|)
block|,
operator|(
name|exam
operator|.
name|getNrRooms
argument_list|()
operator|==
literal|0
condition|?
name|noRoom
else|:
name|exam
operator|.
name|getRoomsName
argument_list|(
literal|", "
argument_list|)
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
operator|new
name|MultiComparable
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
name|exam
operator|.
name|getPeriodOrd
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
name|exam
operator|.
name|getPeriodOrd
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
name|exam
operator|.
name|getPeriodOrd
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
name|exam
operator|.
name|getPeriodOrd
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|exam
operator|.
name|getPeriodOrd
argument_list|()
argument_list|,
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
name|section
operator|.
name|getItype
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|exam
operator|.
name|getPeriod
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
name|exam
operator|.
name|getPeriodOrd
argument_list|()
argument_list|)
block|,
operator|new
name|MultiComparable
argument_list|(
name|exam
operator|.
name|getRoomsName
argument_list|(
literal|":"
argument_list|)
argument_list|,
name|section
operator|.
name|getSubject
argument_list|()
argument_list|,
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|section
operator|.
name|getItype
argument_list|()
argument_list|,
name|section
operator|.
name|getSection
argument_list|()
argument_list|,
name|exam
operator|.
name|getPeriodOrd
argument_list|()
argument_list|)
block|,                             }
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|table
return|;
block|}
block|}
end_class

end_unit

