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
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

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
name|Set
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
name|DataProperties
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
name|Debug
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
name|form
operator|.
name|SolverForm
operator|.
name|LongIdValue
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
name|SolverGroup
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
name|remote
operator|.
name|RemoteSolverServerProxy
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
name|ExportUtils
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/solver"
argument_list|)
specifier|public
class|class
name|SolverAction
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
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
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
name|SolverForm
name|myForm
init|=
operator|(
name|SolverForm
operator|)
name|form
decl_stmt|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Solver
argument_list|)
expr_stmt|;
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
if|if
condition|(
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
name|CanSelectSolverServer
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|hosts
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
name|servers
init|=
name|SolverRegisterService
operator|.
name|getInstance
argument_list|()
operator|.
name|getServers
argument_list|()
decl_stmt|;
synchronized|synchronized
init|(
name|servers
init|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|servers
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
block|{
name|RemoteSolverServerProxy
name|server
init|=
operator|(
name|RemoteSolverServerProxy
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|server
operator|.
name|isActive
argument_list|()
condition|)
name|hosts
operator|.
name|add
argument_list|(
name|server
operator|.
name|getAddress
argument_list|()
operator|.
name|getHostName
argument_list|()
operator|+
literal|":"
operator|+
name|server
operator|.
name|getPort
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|hosts
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|isLocalSolverEnabled
argument_list|()
condition|)
name|hosts
operator|.
name|add
argument_list|(
literal|0
argument_list|,
literal|"local"
argument_list|)
expr_stmt|;
name|hosts
operator|.
name|add
argument_list|(
literal|0
argument_list|,
literal|"auto"
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hosts"
argument_list|,
name|hosts
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|SolverForm
operator|.
name|LongIdValue
argument_list|>
name|owners
init|=
operator|new
name|ArrayList
argument_list|<
name|SolverForm
operator|.
name|LongIdValue
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|SolverGroup
name|owner
range|:
name|SolverGroup
operator|.
name|getUserSolverGroups
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|owner
argument_list|,
name|Right
operator|.
name|TimetablesSolutionLoadEmpty
argument_list|)
condition|)
name|owners
operator|.
name|add
argument_list|(
operator|new
name|LongIdValue
argument_list|(
name|owner
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|owner
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|owners
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|myForm
operator|.
name|setOwner
argument_list|(
operator|new
name|Long
index|[]
block|{
name|owners
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getId
argument_list|()
block|}
argument_list|)
expr_stmt|;
if|else if
condition|(
operator|!
name|owners
operator|.
name|isEmpty
argument_list|()
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"owners"
argument_list|,
name|owners
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
argument_list|(
literal|"y"
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
name|SolverProxy
name|solver
init|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setOwner
argument_list|(
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLongArry
argument_list|(
literal|"General.SolverGroupId"
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|myForm
operator|.
name|getOwner
argument_list|()
argument_list|,
literal|"SolverGroup"
argument_list|,
name|Right
operator|.
name|SolverSolutionExportXml
argument_list|)
expr_stmt|;
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
name|OutputStream
name|out
init|=
name|ExportUtils
operator|.
name|getXmlOutputStream
argument_list|(
name|response
argument_list|,
literal|"solution"
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
name|buf
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|myForm
operator|.
name|getOwner
argument_list|()
argument_list|,
literal|"SolverGroup"
argument_list|,
name|Right
operator|.
name|SolverSolutionSave
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|.
name|indexOf
argument_list|(
literal|"Commit"
argument_list|)
operator|>=
literal|0
condition|)
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|myForm
operator|.
name|getOwner
argument_list|()
argument_list|,
literal|"SolverGroup"
argument_list|,
name|Right
operator|.
name|TimetablesSolutionCommit
argument_list|)
expr_stmt|;
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
argument_list|(
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
name|courseTimetablingSolverService
operator|.
name|removeSolver
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
argument_list|(
literal|false
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
name|courseTimetablingSolverService
operator|.
name|reload
argument_list|(
name|courseTimetablingSolverService
operator|.
name|createConfig
argument_list|(
name|myForm
operator|.
name|getSetting
argument_list|()
argument_list|,
name|myForm
operator|.
name|getParameterValues
argument_list|()
argument_list|)
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
name|myForm
operator|.
name|getOwner
argument_list|()
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
name|DataProperties
name|config
init|=
name|courseTimetablingSolverService
operator|.
name|createConfig
argument_list|(
name|settingsId
argument_list|,
name|myForm
operator|.
name|getParameterValues
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solutionId
operator|!=
literal|null
condition|)
name|config
operator|.
name|setProperty
argument_list|(
literal|"General.SolutionId"
argument_list|,
name|solutionId
argument_list|)
expr_stmt|;
if|if
condition|(
name|myForm
operator|.
name|getHost
argument_list|()
operator|!=
literal|null
condition|)
name|config
operator|.
name|setProperty
argument_list|(
literal|"General.Host"
argument_list|,
name|myForm
operator|.
name|getHost
argument_list|()
argument_list|)
expr_stmt|;
name|config
operator|.
name|setProperty
argument_list|(
literal|"General.SolverGroupId"
argument_list|,
name|ownerId
argument_list|)
expr_stmt|;
name|config
operator|.
name|setProperty
argument_list|(
literal|"General.StartSolver"
argument_list|,
operator|new
name|Boolean
argument_list|(
name|start
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
block|{
name|solver
operator|=
name|courseTimetablingSolverService
operator|.
name|createSolver
argument_list|(
name|config
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
name|config
argument_list|)
expr_stmt|;
name|solver
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
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
argument_list|(
literal|false
argument_list|)
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
argument_list|(
literal|false
argument_list|)
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|myForm
operator|.
name|getOwner
argument_list|()
argument_list|,
literal|"SolverGroup"
argument_list|,
name|Right
operator|.
name|SolverSolutionExportCsv
argument_list|)
expr_stmt|;
name|ExportUtils
operator|.
name|exportCSV
argument_list|(
name|solver
operator|.
name|export
argument_list|()
argument_list|,
name|response
argument_list|,
literal|"solution"
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
literal|"showSolver"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

