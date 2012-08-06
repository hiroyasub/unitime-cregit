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
name|Date
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
name|ExamGridForm
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
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
name|ExamPeriod
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
name|RoomAvailability
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
name|PdfExamGridTable
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/examGrid"
argument_list|)
specifier|public
class|class
name|ExamGridAction
extends|extends
name|Action
block|{
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
name|ExamGridForm
name|myForm
init|=
operator|(
name|ExamGridForm
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
name|ExaminationTimetable
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
name|sessionContext
argument_list|)
expr_stmt|;
block|}
name|myForm
operator|.
name|load
argument_list|(
name|sessionContext
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Cbs"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"resource"
argument_list|)
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setResource
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"resource"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filter"
argument_list|)
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setFilter
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
condition|)
block|{
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
name|Date
index|[]
name|bounds
init|=
name|ExamPeriod
operator|.
name|getBounds
argument_list|(
name|session
argument_list|,
name|myForm
operator|.
name|getExamType
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|exclude
init|=
operator|(
name|myForm
operator|.
name|getExamType
argument_list|()
operator|==
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Exam
operator|.
name|sExamTypeFinal
condition|?
name|RoomAvailabilityInterface
operator|.
name|sFinalExamType
else|:
name|RoomAvailabilityInterface
operator|.
name|sMidtermExamType
operator|)
decl_stmt|;
if|if
condition|(
name|bounds
operator|!=
literal|null
condition|)
block|{
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|.
name|activate
argument_list|(
name|session
argument_list|,
name|bounds
index|[
literal|0
index|]
argument_list|,
name|bounds
index|[
literal|1
index|]
argument_list|,
name|exclude
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|RoomAvailability
operator|.
name|setAvailabilityWarning
argument_list|(
name|request
argument_list|,
name|session
argument_list|,
name|myForm
operator|.
name|getExamType
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
name|PdfExamGridTable
name|table
init|=
operator|new
name|PdfExamGridTable
argument_list|(
name|myForm
argument_list|,
name|sessionContext
argument_list|,
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"table"
argument_list|,
name|table
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
name|table
operator|.
name|export
argument_list|(
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
literal|"showGrid"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

