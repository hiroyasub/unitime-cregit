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
name|StudentSolverForm
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
name|SolverParameterDef
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
name|jgroups
operator|.
name|SolverServer
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
name|SolverServerService
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
name|solver
operator|.
name|studentsct
operator|.
name|StudentSolverProxy
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
literal|"/studentSolver"
argument_list|)
specifier|public
class|class
name|StudentSolverAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
name|studentSectioningSolverService
decl_stmt|;
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
name|SolverServerService
name|solverServerService
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
name|StudentSolverForm
name|myForm
init|=
operator|(
name|StudentSolverForm
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
name|StudentSectioningSolver
argument_list|)
expr_stmt|;
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
for|for
control|(
name|SolverServer
name|server
range|:
name|solverServerService
operator|.
name|getServers
argument_list|(
literal|true
argument_list|)
control|)
name|hosts
operator|.
name|add
argument_list|(
name|server
operator|.
name|getHost
argument_list|()
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|hosts
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|SolverLocalEnabled
operator|.
name|isTrue
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
name|StudentSolverProxy
name|solver
init|=
name|studentSectioningSolverService
operator|.
name|getSolver
argument_list|()
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
argument_list|(
literal|"y"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"reload"
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
name|Right
operator|.
name|StudentSectioningSolutionExportXml
argument_list|)
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
literal|"Store To Best"
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
name|SolverParameterDef
name|statusToSet
init|=
name|SolverParameterDef
operator|.
name|findByNameType
argument_list|(
literal|"Save.StudentSectioningStatusToSet"
argument_list|,
name|SolverParameterGroup
operator|.
name|sTypeStudent
argument_list|)
decl_stmt|;
if|if
condition|(
name|statusToSet
operator|!=
literal|null
condition|)
block|{
name|DataProperties
name|config
init|=
name|solver
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|config
operator|.
name|setProperty
argument_list|(
literal|"Save.StudentSectioningStatusToSet"
argument_list|,
name|myForm
operator|.
name|getParameterValue
argument_list|(
name|statusToSet
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|solver
operator|.
name|setProperties
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
name|solver
operator|.
name|save
argument_list|()
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
name|studentSectioningSolverService
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
name|DataProperties
name|config
init|=
name|studentSectioningSolverService
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
decl_stmt|;
name|studentSectioningSolverService
operator|.
name|reload
argument_list|(
name|config
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
name|DataProperties
name|config
init|=
name|studentSectioningSolverService
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
decl_stmt|;
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
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
block|{
name|solver
operator|=
name|studentSectioningSolverService
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

