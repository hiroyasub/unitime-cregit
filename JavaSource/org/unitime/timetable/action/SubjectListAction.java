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
name|io
operator|.
name|File
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
name|List
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
name|hibernate
operator|.
name|HibernateException
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
name|SubjectListForm
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
name|ChangeLog
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
name|Department
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
name|Settings
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
comment|/**  * MyEclipse Struts * Creation date: 02-18-2005 *  * XDoclet definition: * @struts:action path="/subjectList" name="subjectListForm" input="/admin/subjectList.jsp" scope="request" validate="true" */
end_comment

begin_class
specifier|public
class|class
name|SubjectListAction
extends|extends
name|Action
block|{
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
name|HttpSession
name|webSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|webSession
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|webSession
argument_list|)
decl_stmt|;
name|SubjectListForm
name|subjectListForm
init|=
operator|(
name|SubjectListForm
operator|)
name|form
decl_stmt|;
name|subjectListForm
operator|.
name|setSubjects
argument_list|(
name|SubjectArea
operator|.
name|getSubjectAreaList
argument_list|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
argument_list|)
condition|)
block|{
name|boolean
name|dispLastChanges
init|=
operator|(
operator|!
literal|"no"
operator|.
name|equals
argument_list|(
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_DISP_LAST_CHANGES
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|PdfWebTable
name|webTable
init|=
operator|new
name|PdfWebTable
argument_list|(
operator|(
name|dispLastChanges
condition|?
literal|7
else|:
literal|6
operator|)
argument_list|,
literal|"Subject Area List - "
operator|+
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|ACAD_YRTERM_LABEL_ATTR_NAME
argument_list|)
argument_list|,
literal|"subjectList.do?ord=%%"
argument_list|,
operator|(
name|dispLastChanges
condition|?
operator|new
name|String
index|[]
block|{
literal|"Abbv"
block|,
literal|"Title"
block|,
literal|"Department"
block|,
literal|"Managers"
block|,
literal|"Sched Book\nOnly"
block|,
literal|"Pseudo"
block|,
literal|"Last Change"
block|}
else|:
operator|new
name|String
index|[]
block|{
literal|"Abbv"
block|,
literal|"Title"
block|,
literal|"Departmnet"
block|,
literal|"Managers"
block|,
literal|"Sched Book\nOnly"
block|,
literal|"Pseudo"
block|}
operator|)
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
literal|"right"
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
literal|false
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|subjectListForm
operator|.
name|getSubjects
argument_list|()
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
name|SubjectArea
name|s
init|=
operator|(
name|SubjectArea
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Department
name|d
init|=
name|s
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
name|String
name|sdName
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|s
operator|.
name|getManagers
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimetableManager
name|mgr
init|=
operator|(
name|TimetableManager
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|sdName
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|sdName
operator|=
name|sdName
operator|+
literal|"\n"
expr_stmt|;
name|sdName
operator|=
name|sdName
operator|+
name|mgr
operator|.
name|getFirstName
argument_list|()
operator|+
literal|" "
operator|+
name|mgr
operator|.
name|getLastName
argument_list|()
expr_stmt|;
block|}
name|String
name|lastChangeStr
init|=
literal|null
decl_stmt|;
name|Long
name|lastChangeCmp
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dispLastChanges
condition|)
block|{
name|List
name|changes
init|=
name|ChangeLog
operator|.
name|findLastNChanges
argument_list|(
name|d
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|ChangeLog
name|lastChange
init|=
operator|(
name|changes
operator|==
literal|null
operator|||
name|changes
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|ChangeLog
operator|)
name|changes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
decl_stmt|;
name|lastChangeStr
operator|=
operator|(
name|lastChange
operator|==
literal|null
condition|?
literal|""
else|:
name|ChangeLog
operator|.
name|sDFdate
operator|.
name|format
argument_list|(
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
operator|+
literal|" by "
operator|+
name|lastChange
operator|.
name|getManager
argument_list|()
operator|.
name|getShortName
argument_list|()
operator|)
expr_stmt|;
name|lastChangeCmp
operator|=
operator|new
name|Long
argument_list|(
name|lastChange
operator|==
literal|null
condition|?
literal|0
else|:
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|webTable
operator|.
name|addLine
argument_list|(
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|s
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
block|,
name|s
operator|.
name|getLongTitle
argument_list|()
block|,
operator|(
name|d
operator|==
literal|null
operator|)
condition|?
literal|""
else|:
name|d
operator|.
name|getDeptCode
argument_list|()
operator|+
operator|(
name|d
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|": "
operator|+
name|d
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
block|,
operator|(
name|sdName
operator|==
literal|null
operator|||
name|sdName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|?
literal|""
else|:
name|sdName
block|,
name|s
operator|.
name|isScheduleBookOnly
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"Yes"
else|:
literal|"No"
block|,
name|s
operator|.
name|isPseudoSubjectArea
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"Yes"
else|:
literal|"No"
block|,
name|lastChangeStr
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|s
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
block|,
name|s
operator|.
name|getLongTitle
argument_list|()
block|,
operator|(
name|d
operator|==
literal|null
operator|)
condition|?
literal|""
else|:
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|sdName
block|,
name|s
operator|.
name|isScheduleBookOnly
argument_list|()
operator|.
name|toString
argument_list|()
block|,
name|s
operator|.
name|isPseudoSubjectArea
argument_list|()
operator|.
name|toString
argument_list|()
block|,
name|lastChangeCmp
block|}
argument_list|)
expr_stmt|;
block|}
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"subjects"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|webTable
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
literal|"SubjectList.ord"
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
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showSubjectList"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

