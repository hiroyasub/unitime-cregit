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
name|dom
operator|.
name|client
operator|.
name|Document
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
name|Event
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
name|Focusable
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
name|Label
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
name|impl
operator|.
name|FocusImpl
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClickableHint
extends|extends
name|Label
implements|implements
name|HasAriaLabel
implements|,
name|Focusable
block|{
specifier|public
name|ClickableHint
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|super
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|getElement
argument_list|()
operator|.
name|setTabIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-Hint"
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|KEYEVENTS
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getLinkRole
argument_list|()
operator|.
name|set
argument_list|(
name|getElement
argument_list|()
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
name|getLinkRole
argument_list|()
operator|.
name|getAriaLabelProperty
argument_list|(
name|getElement
argument_list|()
argument_list|)
return|;
block|}
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
name|getLinkRole
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
name|getLinkRole
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
name|int
name|getTabIndex
parameter_list|()
block|{
return|return
name|getElement
argument_list|()
operator|.
name|getTabIndex
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setTabIndex
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|getElement
argument_list|()
operator|.
name|setTabIndex
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAccessKey
parameter_list|(
name|char
name|key
parameter_list|)
block|{
name|FocusImpl
operator|.
name|getFocusImplForWidget
argument_list|()
operator|.
name|setAccessKey
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|key
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setFocus
parameter_list|(
name|boolean
name|focused
parameter_list|)
block|{
if|if
condition|(
name|focused
condition|)
name|getElement
argument_list|()
operator|.
name|focus
argument_list|()
expr_stmt|;
else|else
name|getElement
argument_list|()
operator|.
name|blur
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|onClick
parameter_list|()
block|{
name|getElement
argument_list|()
operator|.
name|dispatchEvent
argument_list|(
name|Document
operator|.
name|get
argument_list|()
operator|.
name|createClickEvent
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onBrowserEvent
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
name|super
operator|.
name|onBrowserEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|event
operator|.
name|getTypeInt
argument_list|()
operator|&
name|Event
operator|.
name|KEYEVENTS
operator|)
operator|!=
literal|0
condition|)
block|{
name|int
name|type
init|=
name|DOM
operator|.
name|eventGetType
argument_list|(
name|event
argument_list|)
decl_stmt|;
name|char
name|keyCode
init|=
operator|(
name|char
operator|)
name|event
operator|.
name|getKeyCode
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|Event
operator|.
name|ONKEYUP
case|:
if|if
condition|(
name|keyCode
operator|==
literal|' '
operator|||
name|keyCode
operator|==
literal|'\n'
operator|||
name|keyCode
operator|==
literal|'\r'
condition|)
name|onClick
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
end_class

end_unit

