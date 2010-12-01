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
name|solver
operator|.
name|studentsct
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
name|java
operator|.
name|util
operator|.
name|Map
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|StudentSolverProxy
block|{
specifier|public
name|String
name|getHost
parameter_list|()
function_decl|;
specifier|public
name|String
name|getHostLabel
parameter_list|()
function_decl|;
specifier|public
name|void
name|dispose
parameter_list|()
function_decl|;
specifier|public
name|void
name|load
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
function_decl|;
specifier|public
name|void
name|reload
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
function_decl|;
specifier|public
name|Date
name|getLoadedDate
parameter_list|()
function_decl|;
specifier|public
name|void
name|save
parameter_list|()
function_decl|;
specifier|public
name|void
name|start
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isRunning
parameter_list|()
function_decl|;
specifier|public
name|void
name|stopSolver
parameter_list|()
function_decl|;
specifier|public
name|void
name|restoreBest
parameter_list|()
function_decl|;
specifier|public
name|void
name|saveBest
parameter_list|()
function_decl|;
specifier|public
name|void
name|clear
parameter_list|()
function_decl|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|currentSolutionInfo
parameter_list|()
function_decl|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|bestSolutionInfo
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isWorking
parameter_list|()
function_decl|;
specifier|public
name|DataProperties
name|getProperties
parameter_list|()
function_decl|;
specifier|public
name|void
name|setProperties
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
function_decl|;
specifier|public
name|int
name|getDebugLevel
parameter_list|()
function_decl|;
specifier|public
name|void
name|setDebugLevel
parameter_list|(
name|int
name|level
parameter_list|)
function_decl|;
specifier|public
name|Map
name|getProgress
parameter_list|()
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|()
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|)
function_decl|;
specifier|public
name|String
name|getLog
parameter_list|(
name|int
name|level
parameter_list|,
name|boolean
name|includeDate
parameter_list|,
name|String
name|fromStage
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|backup
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|ownerId
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|restore
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|ownerId
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|restore
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|ownerId
parameter_list|,
name|boolean
name|removeFiles
parameter_list|)
function_decl|;
specifier|public
name|long
name|timeFromLastUsed
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isPassivated
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|activateIfNeeded
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|passivate
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|puid
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|passivateIfNeeded
parameter_list|(
name|File
name|folder
parameter_list|,
name|String
name|puid
parameter_list|)
function_decl|;
specifier|public
name|Date
name|getLastUsed
parameter_list|()
function_decl|;
specifier|public
name|void
name|interrupt
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

