begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|shared
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|AcademicSessionProvider
block|{
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
function_decl|;
specifier|public
name|String
name|getAcademicSessionName
parameter_list|()
function_decl|;
specifier|public
name|void
name|addAcademicSessionChangeHandler
parameter_list|(
name|AcademicSessionChangeHandler
name|handler
parameter_list|)
function_decl|;
specifier|public
specifier|static
interface|interface
name|AcademicSessionChangeEvent
block|{
specifier|public
name|Long
name|getNewAcademicSessionId
parameter_list|()
function_decl|;
block|}
specifier|public
specifier|static
interface|interface
name|AcademicSessionChangeHandler
block|{
specifier|public
name|void
name|onAcademicSessionChange
parameter_list|(
name|AcademicSessionChangeEvent
name|event
parameter_list|)
function_decl|;
block|}
block|}
end_interface

end_unit

