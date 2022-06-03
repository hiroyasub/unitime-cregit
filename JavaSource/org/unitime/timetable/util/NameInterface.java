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
name|util
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|NameInterface
block|{
specifier|public
name|String
name|getFirstName
parameter_list|()
function_decl|;
specifier|public
name|String
name|getMiddleName
parameter_list|()
function_decl|;
specifier|public
name|String
name|getLastName
parameter_list|()
function_decl|;
specifier|public
name|String
name|getAcademicTitle
parameter_list|()
function_decl|;
specifier|public
name|String
name|getExternalUniqueId
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

