begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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

