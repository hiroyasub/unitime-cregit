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
name|form
operator|.
name|LastChangesForm
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
name|ExportUtils
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
annotation|@
name|Service
argument_list|(
literal|"/lastChanges"
argument_list|)
specifier|public
class|class
name|LastChangesAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
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
name|LastChangesForm
name|myForm
init|=
operator|(
name|LastChangesForm
operator|)
name|form
decl_stmt|;
comment|// Check Access
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|LastChanges
argument_list|)
expr_stmt|;
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
literal|"Apply"
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
name|myForm
operator|.
name|save
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|myForm
operator|.
name|load
argument_list|(
name|request
argument_list|)
expr_stmt|;
block|}
name|Long
name|sessionId
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"departments"
argument_list|,
name|Department
operator|.
name|findAll
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"subjAreas"
argument_list|,
operator|new
name|TreeSet
argument_list|(
name|SubjectArea
operator|.
name|getSubjectAreaList
argument_list|(
name|sessionId
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"managers"
argument_list|,
name|TimetableManager
operator|.
name|getManagerList
argument_list|()
argument_list|)
expr_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"lastChanges.ord2"
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
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|7
argument_list|,
literal|"Last Changes"
argument_list|,
literal|"lastChanges.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Date"
block|,
literal|"Department"
block|,
literal|"Subject"
block|,
literal|"Manager"
block|,
literal|"Page"
block|,
literal|"Object"
block|,
literal|"Operation"
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
block|}
argument_list|)
decl_stmt|;
name|List
name|changes
init|=
name|ChangeLog
operator|.
name|findLastNChanges
argument_list|(
name|sessionId
argument_list|,
operator|(
name|myForm
operator|.
name|getManagerId
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getManagerId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|?
literal|null
else|:
name|myForm
operator|.
name|getManagerId
argument_list|()
operator|)
argument_list|,
operator|(
name|myForm
operator|.
name|getSubjAreaId
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getSubjAreaId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|?
literal|null
else|:
name|myForm
operator|.
name|getSubjAreaId
argument_list|()
operator|)
argument_list|,
operator|(
name|myForm
operator|.
name|getDepartmentId
argument_list|()
operator|==
literal|null
operator|||
name|myForm
operator|.
name|getDepartmentId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|<
literal|0
condition|?
literal|null
else|:
name|myForm
operator|.
name|getDepartmentId
argument_list|()
operator|)
argument_list|,
name|myForm
operator|.
name|getN
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|changes
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|changes
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
name|printLastChangeTableRow
argument_list|(
name|request
argument_list|,
name|webTable
argument_list|,
operator|(
name|ChangeLog
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
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
literal|"lastChanges.ord2"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|&&
name|changes
operator|!=
literal|null
condition|)
block|{
name|PdfWebTable
name|pdfTable
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|7
argument_list|,
literal|"Last Changes"
argument_list|,
literal|"lastChanges.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Date"
block|,
literal|"Department"
block|,
literal|"Subject"
block|,
literal|"Manager"
block|,
literal|"Page"
block|,
literal|"Object"
block|,
literal|"Operation"
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
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|changes
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
name|printLastChangeTableRow
argument_list|(
name|request
argument_list|,
name|pdfTable
argument_list|,
operator|(
name|ChangeLog
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|ExportUtils
operator|.
name|exportPDF
argument_list|(
name|pdfTable
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"lastChanges.ord2"
argument_list|)
argument_list|,
name|response
argument_list|,
literal|"lastChanges"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"display"
argument_list|)
return|;
block|}
specifier|private
name|int
name|printLastChangeTableRow
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|WebTable
name|webTable
parameter_list|,
name|ChangeLog
name|lastChange
parameter_list|,
name|boolean
name|html
parameter_list|)
block|{
if|if
condition|(
name|lastChange
operator|==
literal|null
condition|)
return|return
literal|0
return|;
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
name|ChangeLog
operator|.
name|sDF
operator|.
name|format
argument_list|(
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
block|,
operator|(
name|lastChange
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
operator|(
name|html
condition|?
literal|"<span title='"
operator|+
name|lastChange
operator|.
name|getDepartment
argument_list|()
operator|.
name|getHtmlTitle
argument_list|()
operator|+
literal|"'>"
operator|+
name|lastChange
operator|.
name|getDepartment
argument_list|()
operator|.
name|getShortLabel
argument_list|()
operator|+
literal|"</span>"
else|:
name|lastChange
operator|.
name|getDepartment
argument_list|()
operator|.
name|getShortLabel
argument_list|()
operator|)
operator|)
block|,
operator|(
name|lastChange
operator|.
name|getSubjectArea
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|lastChange
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|)
block|,
operator|(
name|html
condition|?
name|lastChange
operator|.
name|getManager
argument_list|()
operator|.
name|getShortName
argument_list|()
else|:
name|lastChange
operator|.
name|getManager
argument_list|()
operator|.
name|getShortName
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"&nbsp;"
argument_list|,
literal|" "
argument_list|)
operator|)
block|,
name|lastChange
operator|.
name|getSourceTitle
argument_list|()
block|,
name|lastChange
operator|.
name|getObjectTitle
argument_list|()
block|,
name|lastChange
operator|.
name|getOperationTitle
argument_list|()
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
operator|new
name|Long
argument_list|(
name|lastChange
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
block|,
operator|(
name|lastChange
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|lastChange
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|)
block|,
operator|(
name|lastChange
operator|.
name|getSubjectArea
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|lastChange
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
operator|)
block|,
name|lastChange
operator|.
name|getManager
argument_list|()
operator|.
name|getName
argument_list|()
block|,
name|lastChange
operator|.
name|getSourceTitle
argument_list|()
block|,
comment|//new Integer(lastChange.getSource().ordinal()),
name|lastChange
operator|.
name|getObjectTitle
argument_list|()
block|,
operator|new
name|Integer
argument_list|(
name|lastChange
operator|.
name|getOperation
argument_list|()
operator|.
name|ordinal
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
return|return
literal|1
return|;
block|}
block|}
end_class

end_unit

