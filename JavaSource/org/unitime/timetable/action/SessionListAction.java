begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|hibernate
operator|.
name|HibernateException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|model
operator|.
name|RoomType
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
name|dao
operator|.
name|SessionDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:action path="/sessionList" name="sessionListForm" input="/admin/sessionList.jsp" scope="request" validate="true"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/sessionList"
argument_list|)
specifier|public
class|class
name|SessionListAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 * @throws HibernateException 	 */
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
comment|// Check access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|AcademicSessions
argument_list|)
expr_stmt|;
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|12
argument_list|,
literal|""
argument_list|,
literal|"sessionList.do?order=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Default"
block|,
literal|"Academic<br>Session"
block|,
literal|"Academic<br>Initiative"
block|,
literal|"Session<br>Begins"
block|,
literal|"Classes<br>End"
block|,
literal|"Session<br>Ends"
block|,
literal|"Exams<br>Begins"
block|,
literal|"Date<br>Pattern"
block|,
literal|"Status"
block|,
literal|"Subject<br>Areas"
block|,
literal|"Events<br>Begins"
block|,
literal|"Events<br>Ends"
block|,
literal|"Event<br>Management"
block|,
literal|"<br>Enrollment"
block|,
literal|"Deadline<br>Change"
block|,
literal|"<br>Drop"
block|,
literal|"Sectioning<br>Status"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"center"
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
block|,
literal|"left"
block|,
literal|"left"
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
literal|false
block|,
literal|false
block|,
literal|false
block|,
literal|true
block|,
literal|false
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
name|DecimalFormat
name|df5
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"####0"
argument_list|)
decl_stmt|;
name|DateFormat
name|df
init|=
name|DateFormat
operator|.
name|getDateInstance
argument_list|()
decl_stmt|;
for|for
control|(
name|Session
name|s
range|:
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
name|String
name|roomTypes
init|=
literal|""
decl_stmt|;
name|boolean
name|all
init|=
literal|true
decl_stmt|;
for|for
control|(
name|RoomType
name|t
range|:
name|RoomType
operator|.
name|findAll
argument_list|()
control|)
block|{
if|if
condition|(
name|t
operator|.
name|getOption
argument_list|(
name|s
argument_list|)
operator|.
name|canScheduleEvents
argument_list|()
condition|)
block|{
if|if
condition|(
name|roomTypes
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|roomTypes
operator|+=
literal|", "
expr_stmt|;
name|roomTypes
operator|+=
name|t
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
else|else
name|all
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|all
condition|)
name|roomTypes
operator|=
literal|"<i>All</i>"
expr_stmt|;
if|if
condition|(
name|roomTypes
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|roomTypes
operator|=
literal|"<i>N/A</i>"
expr_stmt|;
name|Calendar
name|ce
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|ce
operator|.
name|setTime
argument_list|(
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
expr_stmt|;
name|ce
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|WEEK_OF_YEAR
argument_list|,
name|s
operator|.
name|getLastWeekToEnroll
argument_list|()
argument_list|)
expr_stmt|;
name|ce
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|Calendar
name|cc
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|cc
operator|.
name|setTime
argument_list|(
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
expr_stmt|;
name|cc
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|WEEK_OF_YEAR
argument_list|,
name|s
operator|.
name|getLastWeekToChange
argument_list|()
argument_list|)
expr_stmt|;
name|cc
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|Calendar
name|cd
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|cd
operator|.
name|setTime
argument_list|(
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
expr_stmt|;
name|cd
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|WEEK_OF_YEAR
argument_list|,
name|s
operator|.
name|getLastWeekToDrop
argument_list|()
argument_list|)
expr_stmt|;
name|cd
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|s
argument_list|,
name|Right
operator|.
name|AcademicSessionEdit
argument_list|)
condition|?
literal|"onClick=\"document.location='sessionEdit.do?doit=editSession&sessionId="
operator|+
name|s
operator|.
name|getSessionId
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|s
operator|.
name|getIsDefault
argument_list|()
condition|?
literal|"<img src='images/tick.gif'> "
else|:
literal|"&nbsp; "
block|,
name|s
operator|.
name|getAcademicTerm
argument_list|()
operator|+
literal|" "
operator|+
name|s
operator|.
name|getSessionStartYear
argument_list|()
block|,
name|s
operator|.
name|academicInitiativeDisplayString
argument_list|()
block|,
name|df
operator|.
name|format
argument_list|(
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
block|,
name|df
operator|.
name|format
argument_list|(
name|s
operator|.
name|getClassesEndDateTime
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
block|,
name|df
operator|.
name|format
argument_list|(
name|s
operator|.
name|getSessionEndDateTime
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
block|,
operator|(
name|s
operator|.
name|getExamBeginDate
argument_list|()
operator|==
literal|null
condition|?
literal|"N/A"
else|:
name|df
operator|.
name|format
argument_list|(
name|s
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
operator|)
block|,
name|s
operator|.
name|getDefaultDatePattern
argument_list|()
operator|!=
literal|null
condition|?
name|s
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|"-"
block|,
name|s
operator|.
name|statusDisplayString
argument_list|()
block|,
name|df5
operator|.
name|format
argument_list|(
name|s
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
operator|(
name|s
operator|.
name|getEventBeginDate
argument_list|()
operator|==
literal|null
condition|?
literal|"N/A"
else|:
name|df
operator|.
name|format
argument_list|(
name|s
operator|.
name|getEventBeginDate
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
operator|)
block|,
operator|(
name|s
operator|.
name|getEventEndDate
argument_list|()
operator|==
literal|null
condition|?
literal|"N/A"
else|:
name|df
operator|.
name|format
argument_list|(
name|s
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
operator|)
block|,
name|roomTypes
block|,
name|df
operator|.
name|format
argument_list|(
name|ce
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
block|,
name|df
operator|.
name|format
argument_list|(
name|cc
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
block|,
name|df
operator|.
name|format
argument_list|(
name|cd
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
block|,
operator|(
name|s
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|==
literal|null
condition|?
literal|"&nbsp;"
else|:
name|s
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
operator|)
block|, 						 }
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|s
operator|.
name|getIsDefault
argument_list|()
condition|?
literal|"<img src='images/tick.gif'>"
else|:
literal|""
block|,
name|s
operator|.
name|getLabel
argument_list|()
block|,
name|s
operator|.
name|academicInitiativeDisplayString
argument_list|()
block|,
name|s
operator|.
name|getSessionBeginDateTime
argument_list|()
block|,
name|s
operator|.
name|getClassesEndDateTime
argument_list|()
block|,
name|s
operator|.
name|getSessionEndDateTime
argument_list|()
block|,
name|s
operator|.
name|getExamBeginDate
argument_list|()
block|,
name|s
operator|.
name|getDefaultDatePattern
argument_list|()
operator|!=
literal|null
condition|?
name|s
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|"-"
block|,
name|s
operator|.
name|statusDisplayString
argument_list|()
block|,
name|df5
operator|.
name|format
argument_list|(
name|s
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
name|s
operator|.
name|getEventBeginDate
argument_list|()
block|,
name|s
operator|.
name|getEventEndDate
argument_list|()
block|,
name|roomTypes
block|,
name|ce
operator|.
name|getTime
argument_list|()
block|,
name|cc
operator|.
name|getTime
argument_list|()
block|,
name|cd
operator|.
name|getTime
argument_list|()
block|,
operator|(
name|s
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|==
literal|null
condition|?
literal|" "
else|:
name|s
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
operator|)
block|}
argument_list|)
expr_stmt|;
block|}
name|webTable
operator|.
name|enableHR
argument_list|(
literal|"#9CB0CE"
argument_list|)
expr_stmt|;
name|int
name|orderCol
init|=
literal|4
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|orderCol
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|orderCol
operator|=
literal|4
expr_stmt|;
block|}
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"table"
argument_list|,
name|webTable
operator|.
name|printTable
argument_list|(
name|orderCol
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showSessionList"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

