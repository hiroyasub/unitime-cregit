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
name|io
operator|.
name|File
import|;
end_import

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
name|SolverForm
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
name|SolverProxy
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

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverAction
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
name|SolverForm
name|myForm
init|=
operator|(
name|SolverForm
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
literal|"n"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"confirm"
argument_list|)
argument_list|)
condition|)
name|op
operator|=
literal|null
expr_stmt|;
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
name|SolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"Export XML"
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
name|byte
index|[]
name|buf
init|=
name|solver
operator|.
name|exportXml
argument_list|()
decl_stmt|;
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"solution"
argument_list|,
literal|"xml"
argument_list|)
decl_stmt|;
name|FileOutputStream
name|fos
init|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|fos
operator|.
name|write
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|fos
operator|.
name|flush
argument_list|()
expr_stmt|;
name|fos
operator|.
name|close
argument_list|()
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
name|WebSolver
operator|.
name|saveSolution
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|op
operator|.
name|indexOf
argument_list|(
literal|"As New"
argument_list|)
operator|>=
literal|0
argument_list|,
name|op
operator|.
name|indexOf
argument_list|(
literal|"Commit"
argument_list|)
operator|>=
literal|0
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
name|removeSolver
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
name|reload
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
name|String
name|solutionId
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|getAttribute
argument_list|(
literal|"Solver.selectedSolutionId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getSelectOwner
argument_list|()
condition|)
name|ownerId
operator|=
name|myForm
operator|.
name|getOwner
argument_list|()
expr_stmt|;
if|else if
condition|(
operator|!
name|myForm
operator|.
name|getOwners
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ownerId
operator|=
operator|new
name|Long
index|[
name|myForm
operator|.
name|getOwners
argument_list|()
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|myForm
operator|.
name|getOwners
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|ownerId
index|[
name|i
index|]
operator|=
operator|(
operator|(
name|SolverForm
operator|.
name|LongIdValue
operator|)
name|myForm
operator|.
name|getOwners
argument_list|()
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getId
argument_list|()
expr_stmt|;
block|}
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
name|createSolver
argument_list|(
name|sessionId
argument_list|,
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|ownerId
argument_list|,
operator|(
name|solutionId
operator|==
literal|null
condition|?
literal|null
else|:
name|solutionId
operator|)
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
name|sTypeCourse
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
if|if
condition|(
literal|"Student Sectioning"
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
name|finalSectioning
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
literal|"Export Solution"
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
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"solution"
argument_list|,
literal|"csv"
argument_list|)
decl_stmt|;
name|solver
operator|.
name|export
argument_list|()
operator|.
name|save
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
comment|/*        		response.sendRedirect("temp/"+file.getName());        		response.setContentType("text/csv");        		*/
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

