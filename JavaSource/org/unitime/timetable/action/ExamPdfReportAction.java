begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Date
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
name|ActionMessages
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
name|commons
operator|.
name|web
operator|.
name|WebTable
operator|.
name|WebTableLine
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
name|ApplicationProperty
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
name|ExamPdfReportForm
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
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|exam
operator|.
name|ExamSolverProxy
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
name|service
operator|.
name|SolverService
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
name|util
operator|.
name|Formats
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
name|LookupTables
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
name|queue
operator|.
name|PdfExamReportQueueItem
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
name|queue
operator|.
name|QueueItem
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
name|queue
operator|.
name|QueueProcessor
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/examPdfReport"
argument_list|)
specifier|public
class|class
name|ExamPdfReportAction
extends|extends
name|Action
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
name|ExamPdfReportAction
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
name|examinationSolverService
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
name|ExamPdfReportForm
name|myForm
init|=
operator|(
name|ExamPdfReportForm
operator|)
name|form
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ExaminationPdfReports
argument_list|)
expr_stmt|;
name|ExamSolverProxy
name|examSolver
init|=
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|examSolver
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|ApplicationProperty
operator|.
name|ExaminationPdfReportsCanUseSolution
operator|.
name|isTrue
argument_list|()
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_WARN
argument_list|,
literal|"Examination PDF reports are generated from the current solution (in-memory solution taken from the solver)."
argument_list|)
expr_stmt|;
else|else
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_WARN
argument_list|,
literal|"Examination PDF reports are generated from the saved solution (solver assignments are ignored)."
argument_list|)
expr_stmt|;
block|}
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
literal|"Generate"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
name|myForm
operator|.
name|save
argument_list|(
name|sessionContext
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|load
argument_list|(
name|sessionContext
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setSubjectAreas
argument_list|(
name|SubjectArea
operator|.
name|getUserSubjectAreas
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getAddress
argument_list|()
operator|==
literal|null
condition|)
name|myForm
operator|.
name|setAddress
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Generate"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|myForm
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
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|QueueProcessor
operator|.
name|getInstance
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|PdfExamReportQueueItem
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
operator|(
name|ExamPdfReportForm
operator|)
name|myForm
operator|.
name|clone
argument_list|()
argument_list|,
name|request
argument_list|,
name|examSolver
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"remove"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|QueueProcessor
operator|.
name|getInstance
argument_list|()
operator|.
name|remove
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"remove"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|WebTable
name|table
init|=
name|getQueueTable
argument_list|(
name|request
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
literal|"table"
argument_list|,
name|table
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"examPdfReport.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|LookupTables
operator|.
name|setupExamTypes
argument_list|(
name|request
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
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
name|WebTable
name|getQueueTable
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"examPdfReport.ord"
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
name|String
name|log
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"log"
argument_list|)
decl_stmt|;
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|df
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|TIME_SHORT
argument_list|)
decl_stmt|;
name|String
name|ownerId
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
condition|)
name|ownerId
operator|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|QueueItem
argument_list|>
name|queue
init|=
name|QueueProcessor
operator|.
name|getInstance
argument_list|()
operator|.
name|getItems
argument_list|(
name|ownerId
argument_list|,
literal|null
argument_list|,
name|PdfExamReportQueueItem
operator|.
name|TYPE
argument_list|)
decl_stmt|;
if|if
condition|(
name|queue
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|WebTable
name|table
init|=
operator|new
name|WebTable
argument_list|(
literal|9
argument_list|,
literal|"Reports in progress"
argument_list|,
literal|"examPdfReport.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Name"
block|,
literal|"Status"
block|,
literal|"Progress"
block|,
literal|"Owner"
block|,
literal|"Session"
block|,
literal|"Created"
block|,
literal|"Started"
block|,
literal|"Finished"
block|,
literal|"Output"
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
literal|"center"
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
block|}
argument_list|)
decl_stmt|;
name|Date
name|now
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|long
name|timeToShow
init|=
literal|1000
operator|*
literal|60
operator|*
literal|60
decl_stmt|;
for|for
control|(
name|QueueItem
name|item
range|:
name|queue
control|)
block|{
if|if
condition|(
name|item
operator|.
name|finished
argument_list|()
operator|!=
literal|null
operator|&&
name|now
operator|.
name|getTime
argument_list|()
operator|-
name|item
operator|.
name|finished
argument_list|()
operator|.
name|getTime
argument_list|()
operator|>
name|timeToShow
condition|)
continue|continue;
if|if
condition|(
name|item
operator|.
name|getSession
argument_list|()
operator|==
literal|null
condition|)
continue|continue;
name|String
name|name
init|=
name|item
operator|.
name|name
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|>
literal|60
condition|)
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|57
argument_list|)
operator|+
literal|"..."
expr_stmt|;
name|String
name|delete
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|item
operator|.
name|getOwnerId
argument_list|()
argument_list|)
operator|&&
operator|(
name|item
operator|.
name|started
argument_list|()
operator|==
literal|null
operator|||
name|item
operator|.
name|finished
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|delete
operator|=
literal|"<img src='images/action_delete.png' border='0' onClick=\"if (confirm('Do you really want to remove this report?')) document.location='examPdfReport.do?remove="
operator|+
name|item
operator|.
name|getId
argument_list|()
operator|+
literal|"'; event.cancelBubble=true;\">"
expr_stmt|;
block|}
name|WebTableLine
name|line
init|=
name|table
operator|.
name|addLine
argument_list|(
name|item
operator|.
name|log
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
literal|"onClick=\"document.location='examPdfReport.do?log="
operator|+
name|item
operator|.
name|getId
argument_list|()
operator|+
literal|"';\""
argument_list|,
operator|new
name|String
index|[]
block|{
name|name
operator|+
operator|(
name|delete
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|delete
operator|)
block|,
name|item
operator|.
name|status
argument_list|()
block|,
operator|(
name|item
operator|.
name|progress
argument_list|()
operator|<=
literal|0.0
operator|||
name|item
operator|.
name|progress
argument_list|()
operator|>=
literal|1.0
condition|?
literal|""
else|:
name|String
operator|.
name|valueOf
argument_list|(
name|Math
operator|.
name|round
argument_list|(
literal|100
operator|*
name|item
operator|.
name|progress
argument_list|()
argument_list|)
argument_list|)
operator|+
literal|"%"
operator|)
block|,
name|item
operator|.
name|getOwnerName
argument_list|()
block|,
name|item
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
block|,
name|df
operator|.
name|format
argument_list|(
name|item
operator|.
name|created
argument_list|()
argument_list|)
block|,
name|item
operator|.
name|started
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|df
operator|.
name|format
argument_list|(
name|item
operator|.
name|started
argument_list|()
argument_list|)
block|,
name|item
operator|.
name|finished
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|df
operator|.
name|format
argument_list|(
name|item
operator|.
name|finished
argument_list|()
argument_list|)
block|,
name|item
operator|.
name|output
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|"<A href='temp/"
operator|+
name|item
operator|.
name|output
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"'>"
operator|+
name|item
operator|.
name|output
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
name|item
operator|.
name|output
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
operator|.
name|toUpperCase
argument_list|()
operator|+
literal|"</A>"
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|item
operator|.
name|getId
argument_list|()
block|,
name|item
operator|.
name|status
argument_list|()
block|,
name|item
operator|.
name|progress
argument_list|()
block|,
name|item
operator|.
name|getOwnerName
argument_list|()
block|,
name|item
operator|.
name|getSession
argument_list|()
block|,
name|item
operator|.
name|created
argument_list|()
operator|.
name|getTime
argument_list|()
block|,
name|item
operator|.
name|started
argument_list|()
operator|==
literal|null
condition|?
name|Long
operator|.
name|MAX_VALUE
else|:
name|item
operator|.
name|started
argument_list|()
operator|.
name|getTime
argument_list|()
block|,
name|item
operator|.
name|finished
argument_list|()
operator|==
literal|null
condition|?
name|Long
operator|.
name|MAX_VALUE
else|:
name|item
operator|.
name|finished
argument_list|()
operator|.
name|getTime
argument_list|()
block|,
literal|null
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
name|log
operator|!=
literal|null
operator|&&
name|log
operator|.
name|equals
argument_list|(
name|item
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"logname"
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"logid"
argument_list|,
name|item
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"log"
argument_list|,
name|item
operator|.
name|log
argument_list|()
argument_list|)
expr_stmt|;
name|line
operator|.
name|setBgColor
argument_list|(
literal|"rgb(168,187,225)"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|log
operator|==
literal|null
operator|&&
name|item
operator|.
name|started
argument_list|()
operator|!=
literal|null
operator|&&
name|item
operator|.
name|finished
argument_list|()
operator|==
literal|null
operator|&&
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
operator|.
name|equals
argument_list|(
name|item
operator|.
name|getOwnerId
argument_list|()
argument_list|)
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"logname"
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"logid"
argument_list|,
name|item
operator|.
name|getId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"log"
argument_list|,
name|item
operator|.
name|log
argument_list|()
argument_list|)
expr_stmt|;
name|line
operator|.
name|setBgColor
argument_list|(
literal|"rgb(168,187,225)"
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

