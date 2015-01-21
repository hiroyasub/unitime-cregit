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
name|interfaces
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * Interface to generate external links  *   * @author Heston Fernandes  */
end_comment

begin_interface
specifier|public
interface|interface
name|ExternalLinkLookup
block|{
comment|/** Attribute for the link label */
specifier|public
specifier|final
name|String
name|LINK_LABEL
init|=
literal|"label"
decl_stmt|;
comment|/** Attribute for the link location */
specifier|public
specifier|final
name|String
name|LINK_LOCATION
init|=
literal|"href"
decl_stmt|;
comment|/** 	 * Generate the link based on the attributes of the object 	 * @param obj object whose attributes may be used in constructing the link 	 * @return Map object containing two elements LINK_LABEL and LINK LOCATION 	 */
specifier|public
name|Map
name|getLink
parameter_list|(
name|Object
name|obj
parameter_list|)
throws|throws
name|Exception
function_decl|;
comment|/** 	 * Sets the error message (if any) 	 * @return 	 */
specifier|public
name|String
name|getErrorMessage
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

