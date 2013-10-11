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
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jgroups
operator|.
name|Address
import|;
end_import

begin_interface
specifier|public
interface|interface
name|ReplicatedSolverContainer
parameter_list|<
name|T
parameter_list|>
extends|extends
name|RemoteSolverContainer
argument_list|<
name|T
argument_list|>
block|{
specifier|public
name|Object
name|dispatch
parameter_list|(
name|Collection
argument_list|<
name|Address
argument_list|>
name|addresses
parameter_list|,
name|String
name|user
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|T
name|createProxy
parameter_list|(
name|Collection
argument_list|<
name|Address
argument_list|>
name|addresses
parameter_list|,
name|String
name|user
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasMaster
parameter_list|(
name|String
name|user
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

