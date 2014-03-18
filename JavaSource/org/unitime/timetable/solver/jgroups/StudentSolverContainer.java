begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|jgroups
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Map
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|remote
operator|.
name|BackupFileFilter
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
name|StudentSolver
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
name|StudentSolver
operator|.
name|StudentSolverDisposeListener
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
name|MemoryCounter
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentSolverContainer
implements|implements
name|SolverContainer
argument_list|<
name|StudentSolverProxy
argument_list|>
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|StudentSolverContainer
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
name|Map
argument_list|<
name|String
argument_list|,
name|StudentSolver
argument_list|>
name|iStudentSolvers
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|StudentSolver
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|PassivationThread
name|iPassivation
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getSolvers
parameter_list|()
block|{
return|return
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|iStudentSolvers
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|StudentSolverProxy
name|getSolver
parameter_list|(
name|String
name|user
parameter_list|)
block|{
return|return
name|iStudentSolvers
operator|.
name|get
argument_list|(
name|user
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|long
name|getMemUsage
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|StudentSolverProxy
name|solver
init|=
name|getSolver
argument_list|(
name|user
argument_list|)
decl_stmt|;
return|return
name|solver
operator|==
literal|null
condition|?
literal|0
else|:
operator|new
name|MemoryCounter
argument_list|()
operator|.
name|estimate
argument_list|(
name|solver
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|StudentSolverProxy
name|createSolver
parameter_list|(
name|String
name|user
parameter_list|,
name|DataProperties
name|config
parameter_list|)
block|{
name|StudentSolver
name|solver
init|=
operator|new
name|StudentSolver
argument_list|(
name|config
argument_list|,
operator|new
name|SolverOnDispose
argument_list|(
name|user
argument_list|)
argument_list|)
decl_stmt|;
name|iStudentSolvers
operator|.
name|put
argument_list|(
name|user
argument_list|,
name|solver
argument_list|)
expr_stmt|;
return|return
name|solver
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|unloadSolver
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|StudentSolver
name|solver
init|=
name|iStudentSolvers
operator|.
name|get
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
name|solver
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasSolver
parameter_list|(
name|String
name|user
parameter_list|)
block|{
return|return
name|iStudentSolvers
operator|.
name|containsKey
argument_list|(
name|user
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getUsage
parameter_list|()
block|{
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|StudentSolverProxy
name|solver
range|:
name|iStudentSolvers
operator|.
name|values
argument_list|()
control|)
block|{
name|ret
operator|++
expr_stmt|;
if|if
condition|(
operator|!
name|solver
operator|.
name|isPassivated
argument_list|()
condition|)
name|ret
operator|++
expr_stmt|;
try|try
block|{
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
name|ret
operator|++
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
empty_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|start
parameter_list|()
block|{
name|iPassivation
operator|=
operator|new
name|PassivationThread
argument_list|(
name|ApplicationProperties
operator|.
name|getPassivationFolder
argument_list|()
argument_list|)
expr_stmt|;
name|iPassivation
operator|.
name|start
argument_list|()
expr_stmt|;
name|File
name|folder
init|=
name|ApplicationProperties
operator|.
name|getRestoreFolder
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|folder
operator|.
name|exists
argument_list|()
operator|||
operator|!
name|folder
operator|.
name|isDirectory
argument_list|()
condition|)
return|return;
name|File
index|[]
name|files
init|=
name|folder
operator|.
name|listFiles
argument_list|(
operator|new
name|BackupFileFilter
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|,
name|SolverParameterGroup
operator|.
name|sTypeStudent
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|files
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|File
name|file
init|=
name|files
index|[
name|i
index|]
decl_stmt|;
name|String
name|user
init|=
name|file
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
literal|"sct_"
operator|.
name|length
argument_list|()
argument_list|,
name|file
operator|.
name|getName
argument_list|()
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
decl_stmt|;
name|StudentSolver
name|solver
init|=
operator|new
name|StudentSolver
argument_list|(
operator|new
name|DataProperties
argument_list|()
argument_list|,
operator|new
name|SolverOnDispose
argument_list|(
name|user
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|.
name|restore
argument_list|(
name|folder
argument_list|,
name|user
argument_list|)
condition|)
block|{
if|if
condition|(
name|ApplicationProperties
operator|.
name|getPassivationFolder
argument_list|()
operator|!=
literal|null
condition|)
name|solver
operator|.
name|passivate
argument_list|(
name|ApplicationProperties
operator|.
name|getPassivationFolder
argument_list|()
argument_list|,
name|user
argument_list|)
expr_stmt|;
name|iStudentSolvers
operator|.
name|put
argument_list|(
name|user
argument_list|,
name|solver
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|stop
parameter_list|()
block|{
name|File
name|folder
init|=
name|ApplicationProperties
operator|.
name|getRestoreFolder
argument_list|()
decl_stmt|;
if|if
condition|(
name|folder
operator|.
name|exists
argument_list|()
operator|&&
operator|!
name|folder
operator|.
name|isDirectory
argument_list|()
condition|)
return|return;
name|folder
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
name|File
index|[]
name|old
init|=
name|folder
operator|.
name|listFiles
argument_list|(
operator|new
name|BackupFileFilter
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
name|SolverParameterGroup
operator|.
name|sTypeStudent
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|old
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|old
index|[
name|i
index|]
operator|.
name|delete
argument_list|()
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|StudentSolver
argument_list|>
name|entry
range|:
name|iStudentSolvers
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|backup
argument_list|(
name|folder
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iPassivation
operator|.
name|destroy
argument_list|()
expr_stmt|;
block|}
specifier|protected
class|class
name|SolverOnDispose
implements|implements
name|StudentSolverDisposeListener
block|{
name|String
name|iUser
init|=
literal|null
decl_stmt|;
specifier|public
name|SolverOnDispose
parameter_list|(
name|String
name|user
parameter_list|)
block|{
name|iUser
operator|=
name|user
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onDispose
parameter_list|()
block|{
name|iStudentSolvers
operator|.
name|remove
argument_list|(
name|iUser
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
class|class
name|PassivationThread
extends|extends
name|Thread
block|{
specifier|private
name|File
name|iFolder
init|=
literal|null
decl_stmt|;
specifier|public
name|long
name|iDelay
init|=
literal|30000
decl_stmt|;
specifier|public
name|boolean
name|iContinue
init|=
literal|true
decl_stmt|;
specifier|public
name|PassivationThread
parameter_list|(
name|File
name|folder
parameter_list|)
block|{
name|iFolder
operator|=
name|folder
expr_stmt|;
name|setName
argument_list|(
literal|"Passivation[StudentSectioning]"
argument_list|)
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setPriority
argument_list|(
name|Thread
operator|.
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Solver passivation thread started."
argument_list|)
expr_stmt|;
while|while
condition|(
name|iContinue
condition|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|StudentSolver
argument_list|>
name|entry
range|:
name|iStudentSolvers
operator|.
name|entrySet
argument_list|()
control|)
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|passivateIfNeeded
argument_list|(
name|iFolder
argument_list|,
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
name|sleep
argument_list|(
name|iDelay
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
break|break;
block|}
block|}
name|sLog
operator|.
name|info
argument_list|(
literal|"Solver passivation thread finished."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Solver passivation thread failed, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|destroy
parameter_list|()
block|{
name|iContinue
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|isAlive
argument_list|()
condition|)
name|interrupt
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

