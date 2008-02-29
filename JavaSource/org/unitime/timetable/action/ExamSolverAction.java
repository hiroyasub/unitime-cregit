begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Hashtable
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
name|timetable
operator|.
name|form
operator|.
name|ExamSolverForm
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
name|SolverParameterGroup
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
name|remote
operator|.
name|SolverRegisterService
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamSolverAction
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
name|ExamSolverForm
name|myForm
init|=
operator|(
name|ExamSolverForm
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
try|try
block|{
name|SolverRegisterService
operator|.
name|setupLocalSolver
argument_list|(
name|request
operator|.
name|getRequestURL
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|request
operator|.
name|getRequestURL
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|"/"
argument_list|)
argument_list|)
argument_list|,
name|request
operator|.
name|getServerName
argument_list|()
argument_list|,
name|SolverRegisterService
operator|.
name|getPort
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
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
name|op
operator|==
literal|null
condition|)
block|{
name|myForm
operator|.
name|init
argument_list|()
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showSolver"
argument_list|)
return|;
block|}
name|ExamSolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"Restore From Best"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
name|solver
operator|.
name|restoreBest
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
literal|"Save To Best"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
name|solver
operator|.
name|saveBest
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|op
operator|.
name|startsWith
argument_list|(
literal|"Save"
argument_list|)
operator|&&
operator|!
name|op
operator|.
name|equals
argument_list|(
literal|"Save To Best"
argument_list|)
condition|)
block|{
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
name|solver
operator|.
name|restoreBest
argument_list|()
expr_stmt|;
name|solver
operator|.
name|save
argument_list|()
expr_stmt|;
name|myForm
operator|.
name|setChangeTab
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Unload"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
name|WebSolver
operator|.
name|removeExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|init
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
literal|"Clear"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
name|solver
operator|.
name|clear
argument_list|()
expr_stmt|;
name|myForm
operator|.
name|setChangeTab
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// Reload
if|if
condition|(
literal|"Reload Input Data"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
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
literal|"showSolver"
argument_list|)
return|;
block|}
name|WebSolver
operator|.
name|reloadExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|myForm
operator|.
name|getSetting
argument_list|()
argument_list|,
operator|new
name|Hashtable
argument_list|(
name|myForm
operator|.
name|getParameterValues
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setChangeTab
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Start"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Load"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|boolean
name|start
init|=
literal|"Start"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
operator|&&
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is working, stop it first."
argument_list|)
throw|;
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
literal|"showSolver"
argument_list|)
return|;
block|}
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|Long
name|sessionId
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getUniqueId
argument_list|()
decl_stmt|;
name|Long
name|settingsId
init|=
name|myForm
operator|.
name|getSetting
argument_list|()
decl_stmt|;
name|Long
index|[]
name|ownerId
init|=
literal|null
decl_stmt|;
name|Hashtable
name|extra
init|=
operator|new
name|Hashtable
argument_list|(
name|myForm
operator|.
name|getParameterValues
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
block|{
name|solver
operator|=
name|WebSolver
operator|.
name|createExamSolver
argument_list|(
name|sessionId
argument_list|,
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|settingsId
argument_list|,
name|extra
argument_list|,
name|start
argument_list|,
name|myForm
operator|.
name|getHost
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|start
condition|)
block|{
name|solver
operator|.
name|setProperties
argument_list|(
name|WebSolver
operator|.
name|createProperties
argument_list|(
name|settingsId
argument_list|,
name|extra
argument_list|,
name|SolverParameterGroup
operator|.
name|sTypeExam
argument_list|)
argument_list|)
expr_stmt|;
name|solver
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
name|myForm
operator|.
name|setChangeTab
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Stop"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Solver is not started."
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isRunning
argument_list|()
condition|)
name|solver
operator|.
name|stopSolver
argument_list|()
expr_stmt|;
name|myForm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|init
argument_list|()
expr_stmt|;
block|}
if|if
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
name|myForm
operator|.
name|init
argument_list|()
expr_stmt|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showSolver"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

