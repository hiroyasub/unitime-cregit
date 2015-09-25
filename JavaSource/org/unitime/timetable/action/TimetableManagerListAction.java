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
name|TimetableManagerBuilder
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts  * Creation date: 04-11-2005  *  * XDoclet definition:  * @struts:action  * @struts:action-forward name="success" path="schedDeputyListTile" redirect="true"  * @struts:action-forward name="fail" path="/error.jsp" redirect="true"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/timetableManagerList"
argument_list|)
specifier|public
class|class
name|TimetableManagerListAction
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
comment|/**      * Reads list of schedule deputies and assistants and displays them in the form of a HTML table      * @param mapping      * @param form      * @param request      * @param response      * @return ActionForward      * @throws Exception      */
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
name|String
name|errM
init|=
literal|""
decl_stmt|;
comment|// Check permissions
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|TimetableManagers
argument_list|)
expr_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"timetableManagerList.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|boolean
name|showAll
init|=
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
literal|"TimetableManagers.showAll"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"all"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|showAll
operator|=
operator|(
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"all"
argument_list|)
argument_list|)
operator|)
expr_stmt|;
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"TimetableManagers.showAll"
argument_list|,
name|showAll
condition|?
literal|"1"
else|:
literal|"0"
argument_list|)
expr_stmt|;
block|}
name|PdfWebTable
name|table
init|=
operator|new
name|TimetableManagerBuilder
argument_list|()
operator|.
name|getManagersTable
argument_list|(
name|sessionContext
argument_list|,
literal|true
argument_list|,
name|showAll
argument_list|)
decl_stmt|;
name|int
name|order
init|=
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"timetableManagerList.ord"
argument_list|)
decl_stmt|;
name|String
name|tblData
init|=
operator|(
name|order
operator|>=
literal|1
condition|?
name|table
operator|.
name|printTable
argument_list|(
name|order
argument_list|)
else|:
name|table
operator|.
name|printTable
argument_list|()
operator|)
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
name|ExportUtils
operator|.
name|exportPDF
argument_list|(
operator|new
name|TimetableManagerBuilder
argument_list|()
operator|.
name|getManagersTable
argument_list|(
name|sessionContext
argument_list|,
literal|false
argument_list|,
name|showAll
argument_list|)
argument_list|,
name|order
argument_list|,
name|response
argument_list|,
literal|"managers"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"schedDeputyList"
argument_list|,
name|errM
operator|+
name|tblData
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"showAllManagers"
argument_list|,
name|showAll
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"success"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

