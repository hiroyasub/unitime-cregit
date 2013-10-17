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
name|form
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|ClassListFormInterface
extends|extends
name|InstructionalOfferingListFormInterface
block|{
specifier|public
name|String
index|[]
name|getSubjectAreaIds
parameter_list|()
function_decl|;
specifier|public
name|String
name|getCourseNbr
parameter_list|()
function_decl|;
specifier|public
name|String
name|getSortBy
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterManager
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterAssignedRoom
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterInstructor
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterIType
parameter_list|()
function_decl|;
specifier|public
name|int
name|getFilterDayCode
parameter_list|()
function_decl|;
specifier|public
name|int
name|getFilterStartSlot
parameter_list|()
function_decl|;
specifier|public
name|int
name|getFilterLength
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|getSortByKeepSubparts
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|getShowCrossListedClasses
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isReturnAllControlClassesForSubjects
parameter_list|()
function_decl|;
specifier|public
name|void
name|setSortBy
parameter_list|(
name|String
name|sortBy
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterAssignedRoom
parameter_list|(
name|String
name|filterAssignedRoom
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterManager
parameter_list|(
name|String
name|filterManager
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterIType
parameter_list|(
name|String
name|filterIType
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterDayCode
parameter_list|(
name|int
name|filterDayCode
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterStartSlot
parameter_list|(
name|int
name|filterStartSlot
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterLength
parameter_list|(
name|int
name|filterLength
parameter_list|)
function_decl|;
specifier|public
name|void
name|setSortByKeepSubparts
parameter_list|(
name|boolean
name|sortByKeepSubparts
parameter_list|)
function_decl|;
specifier|public
name|void
name|setShowCrossListedClasses
parameter_list|(
name|boolean
name|showCrossListedClasses
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

