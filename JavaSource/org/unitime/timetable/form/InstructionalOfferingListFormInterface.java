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
name|form
package|;
end_package

begin_interface
specifier|public
interface|interface
name|InstructionalOfferingListFormInterface
block|{
specifier|public
name|Boolean
name|getDivSec
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getDemand
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getProjectedDemand
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getMinPerWk
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getLimit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getRoomLimit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getManager
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getDatePattern
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getTimePattern
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getPreferences
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getInstructor
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getTimetable
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getCredit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getSubpartCredit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getSchedulePrintNote
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getNote
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getConsent
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getTitle
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getExams
parameter_list|()
function_decl|;
specifier|public
name|String
name|getSortBy
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

