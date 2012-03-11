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
name|Messages
import|;
end_import

begin_interface
specifier|public
interface|interface
name|GwtMessages
extends|extends
name|Messages
block|{
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} Help"
argument_list|)
name|String
name|pageHelp
parameter_list|(
name|String
name|pageTitle
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Version {0} built on {1}"
argument_list|)
name|String
name|pageVersion
parameter_list|(
name|String
name|version
parameter_list|,
name|String
name|buildDate
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&copy; 2008 - 2012 UniTime LLC,<br>distributed under GNU General Public License."
argument_list|)
name|String
name|pageCopyright
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Login is required to access this page."
argument_list|)
name|String
name|authenticationRequired
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Your timetabling session has expired. Please log in again."
argument_list|)
name|String
name|authenticationExpired
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Insufficient user privileges."
argument_list|)
name|String
name|authenticationInsufficient
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No academic session selected."
argument_list|)
name|String
name|authenticationNoSession
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

