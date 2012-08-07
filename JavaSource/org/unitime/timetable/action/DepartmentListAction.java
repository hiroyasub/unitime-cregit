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
name|defaults
operator|.
name|CommonValues
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
name|defaults
operator|.
name|UserProperty
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
name|DepartmentListForm
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
comment|/**  * MyEclipse Struts * Creation date: 02-18-2005 *  * XDoclet definition: * @struts:action path="/DepartmentList" name="departmentListForm" input="/admin/departmentList.jsp" scope="request" validate="true" */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/departmentList"
argument_list|)
specifier|public
class|class
name|DepartmentListAction
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Departments
argument_list|)
expr_stmt|;
name|DepartmentListForm
name|departmentListForm
init|=
operator|(
name|DepartmentListForm
operator|)
name|form
decl_stmt|;
name|departmentListForm
operator|.
name|setDepartments
argument_list|(
name|Department
operator|.
name|findAll
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Apply"
operator|.
name|equals
argument_list|(
name|departmentListForm
operator|.
name|getOp
argument_list|()
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Departments.showUnusedDepts"
argument_list|,
name|departmentListForm
operator|.
name|getShowUnusedDepts
argument_list|()
condition|?
literal|"1"
else|:
literal|"0"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|departmentListForm
operator|.
name|setShowUnusedDepts
argument_list|(
literal|"1"
operator|.
name|equals
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"Departments.showUnusedDepts"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|boolean
name|dispLastChanges
init|=
name|CommonValues
operator|.
name|Yes
operator|.
name|eq
argument_list|(
name|UserProperty
operator|.
name|DisplayLastChanges
operator|.
name|get
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
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
name|PdfWebTable
name|webTable
init|=
operator|new
name|PdfWebTable
argument_list|(
operator|(
name|dispLastChanges
condition|?
literal|10
else|:
literal|9
operator|)
argument_list|,
literal|"Department List - "
operator|+
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Session"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierLabel
argument_list|()
argument_list|,
literal|"departmentList.do?ord=%%"
argument_list|,
operator|(
name|dispLastChanges
condition|?
operator|new
name|String
index|[]
block|{
literal|"Number"
block|,
literal|"Abbv"
block|,
literal|"Name"
block|,
literal|"External\nManager"
block|,
literal|"Subjects"
block|,
literal|"Rooms"
block|,
literal|"Status"
block|,
literal|"Dist Pref\nPriority"
block|,
literal|"Allow\nRequired"
block|,
literal|"Last\nChange"
block|}
else|:
operator|new
name|String
index|[]
block|{
literal|"Number"
block|,
literal|"Abbreviation"
block|,
literal|"Name"
block|,
literal|"External\nManager"
block|,
literal|"Subjects"
block|,
literal|"Rooms"
block|,
literal|"Status"
block|,
literal|"Dist Pref\nPriority"
block|,
literal|"Allow\nRequired"
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
literal|"right"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"right"
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
literal|false
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|departmentListForm
operator|.
name|getDepartments
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|departmentListForm
operator|.
name|getShowUnusedDepts
argument_list|()
operator|||
operator|!
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|d
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|DecimalFormat
name|df5
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"####0"
argument_list|)
decl_stmt|;
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
name|String
name|allowReq
init|=
literal|""
decl_stmt|;
name|int
name|allowReqOrd
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|isAllowReqRoom
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqRoom
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
literal|"room"
expr_stmt|;
name|allowReqOrd
operator|+=
literal|1
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|.
name|isAllowReqTime
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqTime
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
literal|"time"
expr_stmt|;
name|allowReqOrd
operator|+=
literal|2
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|.
name|isAllowReqDistribution
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqDistribution
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
literal|"distribution"
expr_stmt|;
name|allowReqOrd
operator|+=
literal|4
expr_stmt|;
block|}
if|if
condition|(
name|allowReqOrd
operator|==
literal|7
condition|)
name|allowReq
operator|=
literal|"all"
expr_stmt|;
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
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
name|d
operator|.
name|getName
argument_list|()
block|,
operator|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
else|:
literal|""
operator|)
block|,
name|df5
operator|.
name|format
argument_list|(
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
name|df5
operator|.
name|format
argument_list|(
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
operator|(
name|d
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
literal|"@@ITALIC "
else|:
literal|""
operator|)
operator|+
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
operator|(
name|d
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
literal|"@@END_ITALIC "
else|:
literal|""
operator|)
block|,
operator|(
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|==
literal|null
operator|&&
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
literal|0
condition|?
literal|""
else|:
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|toString
argument_list|()
operator|)
block|,
name|allowReq
block|,
name|lastChangeStr
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
name|d
operator|.
name|getName
argument_list|()
block|,
operator|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
else|:
literal|""
operator|)
block|,
operator|new
name|Integer
argument_list|(
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
operator|new
name|Integer
argument_list|(
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|getOrd
argument_list|()
block|,
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
block|,
operator|new
name|Integer
argument_list|(
name|allowReqOrd
argument_list|)
block|,
name|lastChangeCmp
block|}
argument_list|)
expr_stmt|;
block|}
block|}
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"departments"
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
name|sessionContext
argument_list|,
literal|"DepartmentList.ord"
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
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
operator|(
name|dispLastChanges
condition|?
literal|10
else|:
literal|9
operator|)
argument_list|,
literal|""
argument_list|,
literal|"departmentList.do?ord=%%"
argument_list|,
operator|(
name|dispLastChanges
condition|?
operator|new
name|String
index|[]
block|{
literal|"Code"
block|,
literal|"Abbv"
block|,
literal|"Name"
block|,
literal|"External<br>Manager"
block|,
literal|"Subjects"
block|,
literal|"Rooms"
block|,
literal|"Status"
block|,
literal|"Dist&nbsp;Pref Priority"
block|,
literal|"Allow Required"
block|,
literal|"Last Change"
block|}
else|:
operator|new
name|String
index|[]
block|{
literal|"Code"
block|,
literal|"Abbreviation"
block|,
literal|"Name"
block|,
literal|"External Manager"
block|,
literal|"Subjects"
block|,
literal|"Rooms"
block|,
literal|"Status"
block|,
literal|"Dist Pref Priority"
block|,
literal|"Allow Required"
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
literal|"right"
block|,
literal|"right"
block|,
literal|"left"
block|,
literal|"right"
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
literal|false
block|}
argument_list|)
decl_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"DepartmentList.ord"
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
name|webTable
operator|.
name|enableHR
argument_list|(
literal|"#9CB0CE"
argument_list|)
expr_stmt|;
name|webTable
operator|.
name|setRowStyle
argument_list|(
literal|"white-space: nowrap"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|departmentListForm
operator|.
name|getDepartments
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|departmentListForm
operator|.
name|getShowUnusedDepts
argument_list|()
operator|||
operator|!
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|d
operator|.
name|getTimetableManagers
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|DecimalFormat
name|df5
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"####0"
argument_list|)
decl_stmt|;
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
literal|"&nbsp;"
else|:
literal|"<span title='"
operator|+
name|lastChange
operator|.
name|getLabel
argument_list|()
operator|+
literal|"'>"
operator|+
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
operator|+
literal|"</span>"
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
name|String
name|allowReq
init|=
literal|""
decl_stmt|;
name|int
name|allowReqOrd
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|isAllowReqRoom
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqRoom
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
literal|"room"
expr_stmt|;
name|allowReqOrd
operator|+=
literal|1
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|.
name|isAllowReqTime
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqTime
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
literal|"time"
expr_stmt|;
name|allowReqOrd
operator|+=
literal|2
expr_stmt|;
block|}
if|if
condition|(
name|d
operator|.
name|isAllowReqDistribution
argument_list|()
operator|!=
literal|null
operator|&&
name|d
operator|.
name|isAllowReqDistribution
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|allowReq
operator|.
name|isEmpty
argument_list|()
condition|)
name|allowReq
operator|+=
literal|", "
expr_stmt|;
name|allowReq
operator|+=
literal|"distribution"
expr_stmt|;
name|allowReqOrd
operator|+=
literal|4
expr_stmt|;
block|}
if|if
condition|(
name|allowReqOrd
operator|==
literal|7
condition|)
name|allowReq
operator|=
literal|"all"
expr_stmt|;
if|if
condition|(
name|allowReqOrd
operator|==
literal|0
condition|)
name|allowReq
operator|=
literal|"&nbsp;"
expr_stmt|;
name|webTable
operator|.
name|addLine
argument_list|(
literal|"onClick=\"document.location='departmentEdit.do?op=Edit&id="
operator|+
name|d
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
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|"&nbsp;"
else|:
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
literal|"<A name='"
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"'>"
operator|+
name|d
operator|.
name|getName
argument_list|()
operator|+
literal|"</A>"
block|,
operator|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"<span title='"
operator|+
name|d
operator|.
name|getExternalMgrLabel
argument_list|()
operator|+
literal|"'>"
operator|+
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
operator|+
literal|"</span>"
else|:
literal|"&nbsp;"
operator|)
block|,
name|df5
operator|.
name|format
argument_list|(
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
name|df5
operator|.
name|format
argument_list|(
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
operator|(
name|d
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
literal|"<i>"
else|:
literal|"&nbsp;"
operator|)
operator|+
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
operator|(
name|d
operator|.
name|getStatusType
argument_list|()
operator|==
literal|null
condition|?
literal|"</i>"
else|:
literal|""
operator|)
block|,
operator|(
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|==
literal|null
operator|&&
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
literal|0
condition|?
literal|"&nbsp;"
else|:
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|toString
argument_list|()
operator|)
block|,
name|allowReq
block|,
name|lastChangeStr
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|"&nbsp;"
else|:
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
name|d
operator|.
name|getName
argument_list|()
block|,
operator|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
else|:
literal|""
operator|)
block|,
operator|new
name|Integer
argument_list|(
name|d
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
operator|new
name|Integer
argument_list|(
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
block|,
name|d
operator|.
name|effectiveStatusType
argument_list|()
operator|.
name|getOrd
argument_list|()
block|,
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
block|,
operator|new
name|Integer
argument_list|(
name|allowReqOrd
argument_list|)
block|,
name|lastChangeCmp
block|}
argument_list|)
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
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"DepartmentList.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDepartmentList"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

