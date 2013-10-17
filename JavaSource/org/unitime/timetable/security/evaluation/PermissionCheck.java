begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|evaluation
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|access
operator|.
name|AccessDeniedException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|Qualifiable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|UserContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|PermissionCheck
block|{
specifier|public
name|void
name|checkPermission
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|)
throws|throws
name|AccessDeniedException
function_decl|;
specifier|public
name|void
name|checkPermission
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|)
throws|throws
name|AccessDeniedException
function_decl|;
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
throws|throws
name|AccessDeniedException
function_decl|;
specifier|public
name|void
name|checkPermissionAnyAuthority
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
throws|throws
name|AccessDeniedException
function_decl|;
specifier|public
name|boolean
name|hasPermission
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasPermission
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Serializable
name|targetId
parameter_list|,
name|String
name|targetType
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasPermissionAnyAuthority
parameter_list|(
name|UserContext
name|user
parameter_list|,
name|Object
name|targetObject
parameter_list|,
name|Right
name|right
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

