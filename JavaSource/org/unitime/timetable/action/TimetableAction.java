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
name|form
operator|.
name|TimetableForm
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
name|timegrid
operator|.
name|PdfTimetableGridTable
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
name|timegrid
operator|.
name|TimetableGridModel
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
name|timegrid
operator|.
name|TimetableGridTable
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TimetableAction
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
name|TimetableForm
name|myForm
init|=
operator|(
name|TimetableForm
operator|)
name|form
decl_stmt|;
comment|// Check Access
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
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
name|op
operator|==
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"resource"
argument_list|)
operator|!=
literal|null
condition|)
name|op
operator|=
literal|"Change"
expr_stmt|;
if|if
condition|(
literal|"Change"
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
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
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
name|TimetableGridTable
name|table
init|=
operator|(
name|TimetableGridTable
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Timetable.table"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"Show"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|table
operator|.
name|setFindString
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filter"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"i"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"mode"
argument_list|)
argument_list|)
condition|)
name|table
operator|.
name|setResourceType
argument_list|(
name|TimetableGridModel
operator|.
name|sResourceTypeInstructor
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"r"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"mode"
argument_list|)
argument_list|)
condition|)
name|table
operator|.
name|setResourceType
argument_list|(
name|TimetableGridModel
operator|.
name|sResourceTypeRoom
argument_list|)
expr_stmt|;
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
block|}
name|myForm
operator|.
name|setLoaded
argument_list|(
name|table
operator|.
name|reload
argument_list|(
name|request
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
condition|)
block|{
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"timetable"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|PdfTimetableGridTable
operator|.
name|export2Pdf
argument_list|(
name|table
argument_list|,
name|file
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
comment|//response.sendRedirect("temp/"+file.getName());
block|}
name|myForm
operator|.
name|setOp
argument_list|(
literal|"Change"
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showTimetable"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

