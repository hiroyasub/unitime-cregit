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
name|FileOutputStream
import|;
end_import

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
name|text
operator|.
name|SimpleDateFormat
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|Progress
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
name|ProgressListener
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
name|Progress
operator|.
name|Message
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
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|OutputFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|SAXReader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|XMLWriter
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
name|Debug
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
name|Email
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
name|backup
operator|.
name|SessionBackup
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
name|backup
operator|.
name|SessionRestore
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
name|dataexchange
operator|.
name|DataExchangeHelper
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
name|dataexchange
operator|.
name|DataExchangeHelper
operator|.
name|LogWriter
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
name|DataImportForm
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
name|DataImportForm
operator|.
name|ExportType
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
name|UserContext
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
comment|/**   * MyEclipse Struts  * Creation date: 01-24-2007  *   * XDoclet definition:  * @struts.action path="/dataImport" name="dataImportForm" input="/form/dataImport.jsp" scope="request" validate="true"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/dataImport"
argument_list|)
specifier|public
class|class
name|DataImportAction
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
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
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
block|{
specifier|final
name|DataImportForm
name|myForm
init|=
operator|(
name|DataImportForm
operator|)
name|form
decl_stmt|;
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|DataExchange
argument_list|)
expr_stmt|;
name|Session
name|session
init|=
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
decl_stmt|;
if|if
condition|(
literal|"Import"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
comment|// Validate input
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
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"display"
argument_list|)
return|;
block|}
name|QueueProcessor
operator|.
name|getInstance
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|ImportQueItem
argument_list|(
name|session
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|myForm
argument_list|,
name|request
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Export"
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
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"display"
argument_list|)
return|;
block|}
name|QueueProcessor
operator|.
name|getInstance
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|ExportQueItem
argument_list|(
name|session
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|myForm
argument_list|,
name|request
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"dataImport.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"dataImport.ord"
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
name|DateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"h:mma"
argument_list|)
decl_stmt|;
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
literal|null
argument_list|,
literal|null
argument_list|,
literal|"Data Exchange"
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
literal|"Data exchange in progress"
argument_list|,
literal|"dataImport.do?ord=%%"
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
literal|"<img src='images/Delete16.gif' border='0' onClick=\"if (confirm('Do you really want to remove this data exchange?')) document.location='dataImport.do?remove="
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
literal|"onClick=\"document.location='dataImport.do?log="
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
name|hasOutput
argument_list|()
condition|?
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
else|:
literal|""
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
specifier|public
specifier|abstract
class|class
name|DataExchangeQueueItem
extends|extends
name|QueueItem
implements|implements
name|LogWriter
block|{
name|DataImportForm
name|iForm
decl_stmt|;
name|String
name|iUrl
decl_stmt|;
name|boolean
name|iImport
decl_stmt|;
name|String
name|iSessionName
decl_stmt|;
name|Progress
name|iProgress
decl_stmt|;
specifier|public
name|DataExchangeQueueItem
parameter_list|(
name|Session
name|session
parameter_list|,
name|UserContext
name|owner
parameter_list|,
name|DataImportForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|boolean
name|isImport
parameter_list|)
block|{
name|super
argument_list|(
name|session
argument_list|,
name|owner
argument_list|)
expr_stmt|;
name|iForm
operator|=
operator|(
name|DataImportForm
operator|)
name|form
operator|.
name|clone
argument_list|()
expr_stmt|;
name|iUrl
operator|=
name|request
operator|.
name|getScheme
argument_list|()
operator|+
literal|"://"
operator|+
name|request
operator|.
name|getServerName
argument_list|()
operator|+
literal|":"
operator|+
name|request
operator|.
name|getServerPort
argument_list|()
operator|+
name|request
operator|.
name|getContextPath
argument_list|()
expr_stmt|;
name|iImport
operator|=
name|isImport
expr_stmt|;
name|iSessionName
operator|=
name|session
operator|.
name|getAcademicTerm
argument_list|()
operator|+
name|session
operator|.
name|getAcademicYear
argument_list|()
operator|+
name|session
operator|.
name|getAcademicInitiative
argument_list|()
expr_stmt|;
name|iProgress
operator|=
name|Progress
operator|.
name|getInstance
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|iProgress
operator|.
name|addProgressListener
argument_list|(
operator|new
name|ProgressListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|statusChanged
parameter_list|(
name|String
name|status
parameter_list|)
block|{
name|log
argument_list|(
name|status
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|progressSaved
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|progressRestored
parameter_list|()
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|progressMessagePrinted
parameter_list|(
name|Message
name|message
parameter_list|)
block|{
name|log
argument_list|(
name|message
operator|.
name|toHtmlString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|progressChanged
parameter_list|(
name|long
name|currentProgress
parameter_list|,
name|long
name|maxProgress
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|phaseChanged
parameter_list|(
name|String
name|phase
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|double
name|progress
parameter_list|()
block|{
name|double
name|p
init|=
name|iProgress
operator|.
name|getProgress
argument_list|()
decl_stmt|;
name|long
name|m
init|=
name|iProgress
operator|.
name|getProgressMax
argument_list|()
decl_stmt|;
return|return
operator|(
name|m
operator|<=
literal|0
condition|?
literal|0.0
else|:
name|p
operator|>=
name|m
condition|?
literal|1.0
else|:
name|p
operator|/
name|m
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|status
parameter_list|()
block|{
name|String
name|phase
init|=
name|iProgress
operator|.
name|getPhase
argument_list|()
decl_stmt|;
return|return
operator|(
name|phase
operator|==
literal|null
operator|||
name|phase
operator|.
name|isEmpty
argument_list|()
condition|?
name|super
operator|.
name|status
argument_list|()
else|:
name|phase
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|type
parameter_list|()
block|{
return|return
literal|"Data Exchange"
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
operator|(
name|iImport
condition|?
literal|"Import of "
operator|+
name|iForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileName
argument_list|()
else|:
literal|"Export of "
operator|+
name|iForm
operator|.
name|getExportType
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|println
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|log
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|abstract
name|void
name|executeDataExchange
parameter_list|()
throws|throws
name|Exception
function_decl|;
annotation|@
name|Override
specifier|protected
name|void
name|execute
parameter_list|()
throws|throws
name|Exception
block|{
try|try
block|{
name|log
argument_list|(
name|iImport
condition|?
literal|"Importing "
operator|+
name|iForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileName
argument_list|()
operator|+
literal|" ("
operator|+
name|iForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileSize
argument_list|()
operator|+
literal|" bytes)..."
else|:
literal|"Exporting "
operator|+
name|iForm
operator|.
name|getExportType
argument_list|()
operator|.
name|getType
argument_list|()
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|Long
name|start
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|executeDataExchange
argument_list|()
expr_stmt|;
name|Long
name|stop
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|log
argument_list|(
operator|(
name|iImport
condition|?
literal|"Import"
else|:
literal|"Export"
operator|)
operator|+
literal|" finished in "
operator|+
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
operator|.
name|format
argument_list|(
operator|(
name|stop
operator|-
name|start
operator|)
operator|/
literal|1000.0
argument_list|)
operator|+
literal|" seconds."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|error
argument_list|(
literal|"Unable to "
operator|+
operator|(
name|iImport
condition|?
literal|"import "
operator|+
name|iForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileName
argument_list|()
else|:
literal|"export"
operator|)
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|setError
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|Progress
operator|.
name|removeInstance
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iForm
operator|.
name|getEmail
argument_list|()
operator|&&
name|iForm
operator|.
name|getAddress
argument_list|()
operator|!=
literal|null
operator|&&
name|iForm
operator|.
name|getAddress
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
name|Email
name|mail
init|=
operator|new
name|Email
argument_list|()
decl_stmt|;
name|mail
operator|.
name|setSubject
argument_list|(
literal|"Data "
operator|+
operator|(
name|iImport
condition|?
literal|"import"
else|:
literal|"export"
operator|)
operator|+
literal|" finished."
argument_list|)
expr_stmt|;
name|mail
operator|.
name|setHTML
argument_list|(
name|log
argument_list|()
operator|+
literal|"<br><br>"
operator|+
literal|"This email was automatically generated at "
operator|+
name|iUrl
operator|+
literal|", by "
operator|+
literal|"UniTime "
operator|+
name|Constants
operator|.
name|getVersion
argument_list|()
operator|+
literal|" (Univesity Timetabling Application, http://www.unitime.org)."
argument_list|)
expr_stmt|;
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|iForm
operator|.
name|getAddress
argument_list|()
argument_list|,
literal|";,\n\r "
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
name|mail
operator|.
name|addRecipient
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.email.notif.data"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
name|mail
operator|.
name|addNotifyCC
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|iImport
operator|&&
name|hasOutput
argument_list|()
operator|&&
name|output
argument_list|()
operator|.
name|exists
argument_list|()
condition|)
name|mail
operator|.
name|addAttachement
argument_list|(
name|output
argument_list|()
argument_list|,
name|iSessionName
operator|+
literal|"_"
operator|+
name|iForm
operator|.
name|getExportType
argument_list|()
operator|.
name|getType
argument_list|()
operator|+
literal|"."
operator|+
name|output
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
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
argument_list|)
expr_stmt|;
name|mail
operator|.
name|send
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|error
argument_list|(
literal|"Unable to send email: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|setError
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
class|class
name|ImportQueItem
extends|extends
name|DataExchangeQueueItem
block|{
specifier|public
name|ImportQueItem
parameter_list|(
name|Session
name|session
parameter_list|,
name|UserContext
name|owner
parameter_list|,
name|DataImportForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|super
argument_list|(
name|session
argument_list|,
name|owner
argument_list|,
name|form
argument_list|,
name|request
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|executeDataExchange
parameter_list|()
throws|throws
name|Exception
block|{
if|if
condition|(
name|iForm
operator|.
name|getFile
argument_list|()
operator|.
name|getFileName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".dat"
argument_list|)
condition|)
block|{
operator|new
name|SessionRestore
argument_list|(
name|iForm
operator|.
name|getFile
argument_list|()
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|iProgress
argument_list|)
operator|.
name|restore
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|DataExchangeHelper
operator|.
name|importDocument
argument_list|(
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|iForm
operator|.
name|getFile
argument_list|()
operator|.
name|getInputStream
argument_list|()
argument_list|)
argument_list|,
name|getOwnerId
argument_list|()
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
class|class
name|ExportQueItem
extends|extends
name|DataExchangeQueueItem
block|{
specifier|public
name|ExportQueItem
parameter_list|(
name|Session
name|session
parameter_list|,
name|UserContext
name|owner
parameter_list|,
name|DataImportForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|super
argument_list|(
name|session
argument_list|,
name|owner
argument_list|,
name|form
argument_list|,
name|request
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|executeDataExchange
parameter_list|()
throws|throws
name|Exception
block|{
name|ExportType
name|type
init|=
name|iForm
operator|.
name|getExportType
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|==
name|ExportType
operator|.
name|SESSION
condition|)
block|{
name|FileOutputStream
name|out
init|=
operator|new
name|FileOutputStream
argument_list|(
name|createOutput
argument_list|(
literal|"session"
argument_list|,
literal|"dat"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|SessionBackup
name|backup
init|=
operator|new
name|SessionBackup
argument_list|(
name|out
argument_list|,
name|iProgress
argument_list|)
decl_stmt|;
name|backup
operator|.
name|backup
argument_list|(
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|Properties
name|params
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|type
operator|.
name|setOptions
argument_list|(
name|params
argument_list|)
expr_stmt|;
name|Document
name|document
init|=
name|DataExchangeHelper
operator|.
name|exportDocument
argument_list|(
name|type
operator|.
name|getType
argument_list|()
argument_list|,
name|getSession
argument_list|()
argument_list|,
name|params
argument_list|,
name|this
argument_list|)
decl_stmt|;
if|if
condition|(
name|document
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"XML document not created: unknown reason."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|FileOutputStream
name|fos
init|=
operator|new
name|FileOutputStream
argument_list|(
name|createOutput
argument_list|(
name|type
operator|.
name|getType
argument_list|()
argument_list|,
literal|"xml"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
operator|(
operator|new
name|XMLWriter
argument_list|(
name|fos
argument_list|,
name|OutputFormat
operator|.
name|createPrettyPrint
argument_list|()
argument_list|)
operator|)
operator|.
name|write
argument_list|(
name|document
argument_list|)
expr_stmt|;
name|fos
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

