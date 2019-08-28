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
name|onlinesectioning
operator|.
name|custom
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CustomSpecialRegistrationHolder
block|{
specifier|public
specifier|static
name|SpecialRegistrationProvider
name|getProvider
parameter_list|()
block|{
return|return
name|Customization
operator|.
name|SpecialRegistrationProvider
operator|.
name|getProvider
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|release
parameter_list|()
block|{
name|Customization
operator|.
name|SpecialRegistrationProvider
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|hasProvider
parameter_list|()
block|{
return|return
name|Customization
operator|.
name|SpecialRegistrationProvider
operator|.
name|hasProvider
argument_list|()
return|;
block|}
block|}
end_class

end_unit

