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
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|core
operator|.
name|userdetails
operator|.
name|UserDetails
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
name|defaults
operator|.
name|UserProperty
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|UserContext
extends|extends
name|UserDetails
block|{
specifier|public
name|String
name|getExternalUserId
parameter_list|()
function_decl|;
specifier|public
name|String
name|getName
parameter_list|()
function_decl|;
specifier|public
name|String
name|getEmail
parameter_list|()
function_decl|;
specifier|public
name|UserAuthority
name|getCurrentAuthority
parameter_list|()
function_decl|;
specifier|public
name|void
name|setCurrentAuthority
parameter_list|(
name|UserAuthority
name|authority
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasAuthority
parameter_list|(
name|UserAuthority
name|authority
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasAuthority
parameter_list|(
name|String
name|role
parameter_list|,
name|Long
name|uniqueId
parameter_list|)
function_decl|;
specifier|public
name|UserAuthority
name|getAuthority
parameter_list|(
name|String
name|role
parameter_list|,
name|Long
name|uniqueId
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasAuthority
parameter_list|(
name|String
name|authority
parameter_list|)
function_decl|;
specifier|public
name|UserAuthority
name|getAuthority
parameter_list|(
name|String
name|authority
parameter_list|)
function_decl|;
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|getAuthorities
parameter_list|()
function_decl|;
specifier|public
name|List
argument_list|<
name|?
extends|extends
name|UserAuthority
argument_list|>
name|getAuthorities
parameter_list|(
name|String
name|role
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
function_decl|;
specifier|public
name|Long
name|getCurrentAcademicSessionId
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|hasDepartment
parameter_list|(
name|Long
name|departmentId
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|hasRole
parameter_list|(
name|String
name|role
parameter_list|)
function_decl|;
specifier|public
name|String
name|getProperty
parameter_list|(
name|String
name|key
parameter_list|)
function_decl|;
specifier|public
name|String
name|getProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|defaultValue
parameter_list|)
function_decl|;
specifier|public
name|void
name|setProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
function_decl|;
specifier|public
name|String
name|getProperty
parameter_list|(
name|UserProperty
name|property
parameter_list|)
function_decl|;
specifier|public
name|void
name|setProperty
parameter_list|(
name|UserProperty
name|property
parameter_list|,
name|String
name|value
parameter_list|)
function_decl|;
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getProperties
parameter_list|()
function_decl|;
specifier|public
specifier|static
interface|interface
name|Chameleon
block|{
specifier|public
name|UserContext
name|getOriginalUserContext
parameter_list|()
function_decl|;
block|}
specifier|public
name|String
name|getTrueExternalUserId
parameter_list|()
function_decl|;
specifier|public
name|String
name|getTrueName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

