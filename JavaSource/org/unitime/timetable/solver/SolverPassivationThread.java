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
name|solver
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
name|Hashtable
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
name|Map
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverPassivationThread
extends|extends
name|Thread
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
name|SolverPassivationThread
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|File
name|iFolder
init|=
literal|null
decl_stmt|;
specifier|private
name|Hashtable
name|iSolvers
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|long
name|sDelay
init|=
literal|30000
decl_stmt|;
specifier|public
name|SolverPassivationThread
parameter_list|(
name|File
name|folder
parameter_list|,
name|Hashtable
name|solvers
parameter_list|)
block|{
name|iFolder
operator|=
name|folder
expr_stmt|;
name|iSolvers
operator|=
name|solvers
expr_stmt|;
name|setName
argument_list|(
literal|"SolverPasivationThread"
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
literal|true
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|iSolvers
operator|.
name|entrySet
argument_list|()
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
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|SolverProxy
name|solver
init|=
operator|(
name|SolverProxy
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|solver
operator|.
name|passivateIfNeeded
argument_list|(
name|iFolder
argument_list|,
name|puid
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|sleep
argument_list|(
name|sDelay
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
name|error
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
block|}
end_class

end_unit

