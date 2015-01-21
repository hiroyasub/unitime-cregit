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
name|gwt
operator|.
name|client
operator|.
name|aria
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
name|aria
operator|.
name|client
operator|.
name|Roles
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|PasswordTextBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AriaPasswordTextBox
extends|extends
name|PasswordTextBox
implements|implements
name|HasAriaLabel
block|{
annotation|@
name|Override
specifier|public
name|void
name|setAriaLabel
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|text
operator|==
literal|null
operator|||
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
name|Roles
operator|.
name|getTextboxRole
argument_list|()
operator|.
name|removeAriaLabelledbyProperty
argument_list|(
name|getElement
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|Roles
operator|.
name|getTextboxRole
argument_list|()
operator|.
name|setAriaLabelProperty
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAriaLabel
parameter_list|()
block|{
return|return
name|Roles
operator|.
name|getTextboxRole
argument_list|()
operator|.
name|getAriaLabelProperty
argument_list|(
name|getElement
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

