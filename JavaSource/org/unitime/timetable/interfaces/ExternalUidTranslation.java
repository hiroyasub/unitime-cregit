begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|interfaces
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|ExternalUidTranslation
block|{
specifier|public
specifier|static
enum|enum
name|Source
block|{
name|Staff
block|,
comment|// Staff/DepartmentalInstructor tables
name|Student
block|,
comment|// Student table
name|User
block|,
comment|// Authentication, TimetableManager, etc.
name|LDAP
comment|// LDAP lookup
block|}
specifier|public
name|String
name|translate
parameter_list|(
name|String
name|uid
parameter_list|,
name|Source
name|source
parameter_list|,
name|Source
name|target
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

