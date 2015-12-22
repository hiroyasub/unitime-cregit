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
name|admin
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|widgets
operator|.
name|P
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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|HasClickHandlers
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeEvent
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
name|event
operator|.
name|logical
operator|.
name|shared
operator|.
name|ValueChangeHandler
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
name|event
operator|.
name|shared
operator|.
name|HandlerRegistration
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
name|CheckBox
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
name|HasValue
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
name|ScrollPanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MultiSelect
parameter_list|<
name|T
parameter_list|>
extends|extends
name|ScrollPanel
implements|implements
name|HasValue
argument_list|<
name|List
argument_list|<
name|T
argument_list|>
argument_list|>
block|{
specifier|private
name|P
name|iPanel
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Item
argument_list|>
name|iItems
init|=
operator|new
name|ArrayList
argument_list|<
name|Item
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|MultiSelect
parameter_list|()
block|{
name|setStyleName
argument_list|(
literal|"unitime-MultiSelect"
argument_list|)
expr_stmt|;
name|iPanel
operator|=
operator|new
name|P
argument_list|(
literal|"content"
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iPanel
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addItem
parameter_list|(
name|T
name|id
parameter_list|,
name|String
name|text
parameter_list|,
name|boolean
name|asHTML
parameter_list|)
block|{
name|Item
name|item
init|=
operator|new
name|Item
argument_list|(
name|id
argument_list|,
name|text
argument_list|,
name|asHTML
argument_list|)
decl_stmt|;
name|iItems
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
name|iPanel
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addItem
parameter_list|(
name|T
name|id
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|addItem
argument_list|(
name|id
argument_list|,
name|text
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSelected
parameter_list|(
name|T
name|id
parameter_list|)
block|{
for|for
control|(
name|Item
name|item
range|:
name|iItems
control|)
block|{
if|if
condition|(
name|item
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
return|return
name|item
operator|.
name|getValue
argument_list|()
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|setSelected
parameter_list|(
name|T
name|id
parameter_list|,
name|boolean
name|value
parameter_list|)
block|{
for|for
control|(
name|Item
name|item
range|:
name|iItems
control|)
block|{
if|if
condition|(
name|item
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
name|item
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|List
argument_list|<
name|T
argument_list|>
name|getSelectedIds
parameter_list|()
block|{
name|List
argument_list|<
name|T
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|T
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Item
name|item
range|:
name|iItems
control|)
block|{
if|if
condition|(
name|item
operator|.
name|getValue
argument_list|()
condition|)
name|ret
operator|.
name|add
argument_list|(
name|item
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|List
argument_list|<
name|Item
argument_list|>
name|getSelectedItems
parameter_list|()
block|{
name|List
argument_list|<
name|Item
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|Item
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Item
name|item
range|:
name|iItems
control|)
block|{
if|if
condition|(
name|item
operator|.
name|getValue
argument_list|()
condition|)
name|ret
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|List
argument_list|<
name|Item
argument_list|>
name|getItems
parameter_list|()
block|{
return|return
name|iItems
return|;
block|}
class|class
name|Item
extends|extends
name|CheckBox
implements|implements
name|HasClickHandlers
block|{
specifier|private
name|T
name|iId
decl_stmt|;
name|Item
parameter_list|(
name|T
name|id
parameter_list|,
name|String
name|name
parameter_list|,
name|boolean
name|asHTML
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|asHTML
argument_list|)
expr_stmt|;
name|iId
operator|=
name|id
expr_stmt|;
name|addStyleName
argument_list|(
literal|"item"
argument_list|)
expr_stmt|;
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|Boolean
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|Boolean
argument_list|>
name|event
parameter_list|)
block|{
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|MultiSelect
operator|.
name|this
argument_list|,
name|MultiSelect
operator|.
name|this
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|T
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|T
name|id
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addValueChangeHandler
parameter_list|(
name|ValueChangeHandler
argument_list|<
name|List
argument_list|<
name|T
argument_list|>
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|addHandler
argument_list|(
name|handler
argument_list|,
name|ValueChangeEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|T
argument_list|>
name|getValue
parameter_list|()
block|{
return|return
name|getSelectedIds
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|List
argument_list|<
name|T
argument_list|>
name|value
parameter_list|)
block|{
name|setValue
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|List
argument_list|<
name|T
argument_list|>
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
for|for
control|(
name|Item
name|item
range|:
name|iItems
control|)
name|item
operator|.
name|setValue
argument_list|(
name|value
operator|.
name|contains
argument_list|(
name|item
operator|.
name|getId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|fireEvents
condition|)
name|ValueChangeEvent
operator|.
name|fire
argument_list|(
name|this
argument_list|,
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

