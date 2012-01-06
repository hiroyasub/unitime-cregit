begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|resources
package|;
end_package

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|Constants
import|;
end_import

begin_interface
specifier|public
interface|interface
name|GwtConstants
extends|extends
name|Constants
block|{
annotation|@
name|DefaultStringValue
argument_list|(
literal|"3.3"
argument_list|)
name|String
name|version
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"&copy; 2008 - 2012 UniTime LLC"
argument_list|)
name|String
name|copyright
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

