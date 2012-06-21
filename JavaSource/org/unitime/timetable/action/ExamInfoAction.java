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
name|form
operator|.
name|ExamInfoForm
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
name|model
operator|.
name|dao
operator|.
name|ExamDAO
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
name|WebSolver
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
name|ui
operator|.
name|ExamInfoModel
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/examInfo"
argument_list|)
specifier|public
class|class
name|ExamInfoAction
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
name|ExamInfoForm
name|myForm
init|=
operator|(
name|ExamInfoForm
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
name|ExamInfoModel
name|model
init|=
operator|(
name|ExamInfoModel
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.model"
argument_list|)
decl_stmt|;
if|if
condition|(
name|model
operator|==
literal|null
condition|)
block|{
name|model
operator|=
operator|new
name|ExamInfoModel
argument_list|()
expr_stmt|;
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.model"
argument_list|,
name|model
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|op
operator|==
literal|null
operator|&&
name|model
operator|.
name|getExam
argument_list|()
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"examId"
argument_list|)
operator|==
literal|null
condition|)
block|{
name|op
operator|=
literal|"Apply"
expr_stmt|;
block|}
if|if
condition|(
literal|"Apply"
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
if|else if
condition|(
literal|"Refresh"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
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
name|myForm
operator|.
name|setModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|model
operator|.
name|apply
argument_list|(
name|request
argument_list|,
name|myForm
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
block|{
name|model
operator|.
name|clear
argument_list|(
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"Apply"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|model
operator|.
name|refreshRooms
argument_list|()
expr_stmt|;
name|model
operator|.
name|refreshSuggestions
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
literal|"Search Deeper"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|setDepth
argument_list|(
name|myForm
operator|.
name|getDepth
argument_list|()
operator|+
literal|1
argument_list|)
expr_stmt|;
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
name|model
operator|.
name|refreshSuggestions
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
literal|"Search Longer"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|setTimeout
argument_list|(
literal|2
operator|*
name|myForm
operator|.
name|getTimeout
argument_list|()
argument_list|)
expr_stmt|;
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
name|model
operator|.
name|refreshSuggestions
argument_list|()
expr_stmt|;
block|}
name|model
operator|.
name|setSolver
argument_list|(
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"examId"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|model
operator|.
name|setExam
argument_list|(
operator|new
name|ExamDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"examId"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
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
if|if
condition|(
name|model
operator|.
name|getExam
argument_list|()
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No exam given."
argument_list|)
throw|;
if|if
condition|(
name|RoomAvailability
operator|.
name|getInstance
argument_list|()
operator|!=
literal|null
operator|&&
name|op
operator|==
literal|null
condition|)
block|{
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
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
name|model
operator|.
name|getExam
argument_list|()
operator|.
name|getExamType
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|exclude
init|=
operator|(
name|model
operator|.
name|getExam
argument_list|()
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
name|model
operator|.
name|getExam
argument_list|()
operator|.
name|getExamType
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Select"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
synchronized|synchronized
init|(
name|model
init|)
block|{
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"period"
argument_list|)
operator|!=
literal|null
condition|)
name|model
operator|.
name|setPeriod
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"period"
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
literal|"room"
argument_list|)
operator|!=
literal|null
condition|)
name|model
operator|.
name|setRooms
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"room"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"suggestion"
argument_list|)
operator|!=
literal|null
condition|)
name|model
operator|.
name|setSuggestion
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"suggestion"
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
literal|"delete"
argument_list|)
operator|!=
literal|null
condition|)
name|model
operator|.
name|delete
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"delete"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"Assign"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
synchronized|synchronized
init|(
name|model
init|)
block|{
name|String
name|message
init|=
name|model
operator|.
name|assign
argument_list|()
decl_stmt|;
if|if
condition|(
name|message
operator|==
literal|null
operator|||
name|message
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|myForm
operator|.
name|setOp
argument_list|(
literal|"Close"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|myForm
operator|.
name|setMessage
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
literal|"Close"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|setOp
argument_list|(
literal|"Close"
argument_list|)
expr_stmt|;
block|}
comment|/*         BackTracker.markForBack(                 request,                 "examInfo.do?examId=" + model.getExam().getExamId(),                 "Exam Info ("+ model.getExam().getExamName() +")",                 true, false);         */
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

