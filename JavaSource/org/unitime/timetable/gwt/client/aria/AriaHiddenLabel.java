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
name|dom
operator|.
name|client
operator|.
name|Element
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
name|DOM
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
name|HasHTML
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
name|HasText
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
name|Widget
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AriaHiddenLabel
extends|extends
name|Widget
implements|implements
name|HasHTML
implements|,
name|HasText
block|{
specifier|public
name|AriaHiddenLabel
parameter_list|(
name|String
name|text
parameter_list|,
name|boolean
name|asHtml
parameter_list|)
block|{
name|setElement
argument_list|(
operator|(
name|Element
operator|)
name|DOM
operator|.
name|createSpan
argument_list|()
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-AriaHiddenLabel"
argument_list|)
expr_stmt|;
if|if
condition|(
name|text
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|asHtml
condition|)
name|setHTML
argument_list|(
name|text
argument_list|)
expr_stmt|;
else|else
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|AriaHiddenLabel
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|this
argument_list|(
name|text
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AriaHiddenLabel
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|getElement
argument_list|()
operator|.
name|getInnerText
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|getElement
argument_list|()
operator|.
name|setInnerText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getHTML
parameter_list|()
block|{
return|return
name|getElement
argument_list|()
operator|.
name|getInnerHTML
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setHTML
parameter_list|(
name|String
name|html
parameter_list|)
block|{
name|getElement
argument_list|()
operator|.
name|setInnerHTML
argument_list|(
name|html
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

