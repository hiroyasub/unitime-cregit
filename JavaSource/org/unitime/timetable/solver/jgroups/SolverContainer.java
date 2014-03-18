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
name|util
operator|.
name|Set
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|SolverContainer
parameter_list|<
name|T
parameter_list|>
block|{
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getSolvers
parameter_list|()
function_decl|;
specifier|public
name|T
name|getSolver
parameter_list|(
name|String
name|user
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasSolver
parameter_list|(
name|String
name|user
parameter_list|)
function_decl|;
specifier|public
name|T
name|createSolver
parameter_list|(
name|String
name|user
parameter_list|,
name|DataProperties
name|config
parameter_list|)
function_decl|;
specifier|public
name|void
name|unloadSolver
parameter_list|(
name|String
name|user
parameter_list|)
function_decl|;
specifier|public
name|int
name|getUsage
parameter_list|()
function_decl|;
specifier|public
name|void
name|start
parameter_list|()
function_decl|;
specifier|public
name|void
name|stop
parameter_list|()
function_decl|;
specifier|public
name|long
name|getMemUsage
parameter_list|(
name|String
name|user
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

