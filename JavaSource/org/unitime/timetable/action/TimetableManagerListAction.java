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

