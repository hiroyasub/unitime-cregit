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
name|api
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Type
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
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
name|SessionContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|ApiHelper
block|{
specifier|public
name|Long
name|getAcademicSessionId
parameter_list|()
function_decl|;
specifier|public
name|SessionContext
name|getSessionContext
parameter_list|()
function_decl|;
specifier|public
parameter_list|<
name|P
parameter_list|>
name|P
name|getRequest
parameter_list|(
name|Type
name|requestType
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|public
parameter_list|<
name|R
parameter_list|>
name|void
name|setResponse
parameter_list|(
name|R
name|response
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|public
name|void
name|sendError
parameter_list|(
name|int
name|code
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|public
name|void
name|sendError
parameter_list|(
name|int
name|code
parameter_list|,
name|String
name|message
parameter_list|)
throws|throws
name|IOException
function_decl|;
specifier|public
name|String
name|getParameter
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
specifier|public
name|String
index|[]
name|getParameterValues
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
specifier|public
name|Enumeration
argument_list|<
name|String
argument_list|>
name|getParameterNames
parameter_list|()
function_decl|;
specifier|public
name|org
operator|.
name|hibernate
operator|.
name|Session
name|getHibSession
parameter_list|()
function_decl|;
specifier|public
name|void
name|close
parameter_list|()
function_decl|;
specifier|public
name|String
name|getOptinalParameter
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|defaultValue
parameter_list|)
function_decl|;
specifier|public
name|String
name|getRequiredParameter
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
specifier|public
name|Integer
name|getOptinalParameterInteger
parameter_list|(
name|String
name|name
parameter_list|,
name|Integer
name|defaultValue
parameter_list|)
function_decl|;
specifier|public
name|Integer
name|getRequiredParameterInteger
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
specifier|public
name|Long
name|getOptinalParameterLong
parameter_list|(
name|String
name|name
parameter_list|,
name|Long
name|defaultValue
parameter_list|)
function_decl|;
specifier|public
name|Long
name|getRequiredParameterLong
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
specifier|public
name|Boolean
name|getOptinalParameterBoolean
parameter_list|(
name|String
name|name
parameter_list|,
name|Boolean
name|defaultValue
parameter_list|)
function_decl|;
specifier|public
name|Boolean
name|getRequiredParameterBoolean
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

