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
name|event
operator|.
name|dom
operator|.
name|client
operator|.
name|BlurEvent
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
name|BlurHandler
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
name|FocusEvent
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
name|FocusHandler
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
name|HasAllFocusHandlers
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
name|MouseDownEvent
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
name|MouseDownHandler
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
name|MouseOutEvent
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
name|MouseOutHandler
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
name|MouseOverEvent
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
name|MouseOverHandler
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
name|MouseUpEvent
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
name|MouseUpHandler
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
name|resources
operator|.
name|client
operator|.
name|ImageResource
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
name|HasEnabled
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
name|Image
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ImageButton
extends|extends
name|Image
implements|implements
name|HasEnabled
implements|,
name|Focusable
implements|,
name|HasAriaLabel
implements|,
name|HasAllFocusHandlers
block|{
specifier|private
name|ImageResource
name|iUp
init|=
literal|null
decl_stmt|,
name|iDown
init|=
literal|null
decl_stmt|,
name|iOver
init|=
literal|null
decl_stmt|,
name|iDisabled
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iEnabled
init|=
literal|true
decl_stmt|,
name|iFocusing
init|=
literal|false
decl_stmt|;
specifier|public
name|ImageButton
parameter_list|(
name|ImageResource
name|faceUp
parameter_list|,
name|ImageResource
name|faceDown
parameter_list|,
name|ImageResource
name|faceOver
parameter_list|,
name|ImageResource
name|faceDisabled
parameter_list|)
block|{
name|iUp
operator|=
name|faceUp
expr_stmt|;
name|iDown
operator|=
name|faceDown
expr_stmt|;
name|iOver
operator|=
name|faceOver
expr_stmt|;
name|iDisabled
operator|=
name|faceDisabled
expr_stmt|;
name|setResource
argument_list|(
name|iUp
argument_list|)
expr_stmt|;
name|setTabIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-ImageButton"
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getButtonRole
argument_list|()
operator|.
name|set
argument_list|(
name|getElement
argument_list|()
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|KEYEVENTS
argument_list|)
expr_stmt|;
name|addMouseOverHandler
argument_list|(
operator|new
name|MouseOverHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseOver
parameter_list|(
name|MouseOverEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isEnabled
argument_list|()
operator|&&
name|iOver
operator|!=
literal|null
condition|)
name|setResource
argument_list|(
name|iOver
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addMouseOutHandler
argument_list|(
operator|new
name|MouseOutHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseOut
parameter_list|(
name|MouseOutEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isEnabled
argument_list|()
condition|)
name|setResource
argument_list|(
name|iUp
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addMouseDownHandler
argument_list|(
operator|new
name|MouseDownHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseDown
parameter_list|(
name|MouseDownEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isEnabled
argument_list|()
operator|&&
name|iDown
operator|!=
literal|null
condition|)
name|setResource
argument_list|(
name|iDown
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addMouseUpHandler
argument_list|(
operator|new
name|MouseUpHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onMouseUp
parameter_list|(
name|MouseUpEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isEnabled
argument_list|()
condition|)
name|setResource
argument_list|(
name|iUp
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addStyleName
argument_list|(
literal|"unitime-ImageButton-focus"
argument_list|)
expr_stmt|;
name|addFocusHandler
argument_list|(
operator|new
name|FocusHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFocus
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isEnabled
argument_list|()
operator|&&
name|iOver
operator|!=
literal|null
condition|)
name|setResource
argument_list|(
name|iOver
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|addBlurHandler
argument_list|(
operator|new
name|BlurHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onBlur
parameter_list|(
name|BlurEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isEnabled
argument_list|()
condition|)
name|setResource
argument_list|(
name|iUp
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ImageButton
parameter_list|(
name|ImageResource
name|faceUp
parameter_list|,
name|ImageResource
name|faceDown
parameter_list|,
name|ImageResource
name|faceOver
parameter_list|)
block|{
name|this
argument_list|(
name|faceUp
argument_list|,
name|faceDown
argument_list|,
name|faceOver
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ImageButton
parameter_list|(
name|ImageResource
name|faceUp
parameter_list|,
name|ImageResource
name|faceOver
parameter_list|)
block|{
name|this
argument_list|(
name|faceUp
argument_list|,
literal|null
argument_list|,
name|faceOver
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|iEnabled
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|enabled
parameter_list|)
block|{
if|if
condition|(
name|iEnabled
operator|!=
name|enabled
condition|)
block|{
name|iEnabled
operator|=
name|enabled
expr_stmt|;
name|setResource
argument_list|(
name|enabled
condition|?
name|iUp
else|:
name|iDisabled
operator|!=
literal|null
condition|?
name|iDisabled
else|:
name|iUp
argument_list|)
expr_stmt|;
if|if
condition|(
name|enabled
condition|)
block|{
name|removeStyleName
argument_list|(
literal|"unitime-ImageButton-disabled"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|addStyleName
argument_list|(
literal|"unitime-ImageButton-disabled"
argument_list|)
expr_stmt|;
block|}
name|Roles
operator|.
name|getButtonRole
argument_list|()
operator|.
name|setAriaDisabledState
argument_list|(
name|getElement
argument_list|()
argument_list|,
operator|!
name|enabled
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setFace
parameter_list|(
name|ImageResource
name|face
parameter_list|)
block|{
name|setResource
argument_list|(
name|face
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
name|setAccessKey
parameter_list|(
name|char
name|key
parameter_list|)
block|{
name|setAccessKey
argument_list|(
name|getElement
argument_list|()
argument_list|,
name|key
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|native
name|void
name|setAccessKey
parameter_list|(
name|Element
name|elem
parameter_list|,
name|char
name|key
parameter_list|)
comment|/*-{ 		elem.accessKey = String.fromCharCode(key); 	}-*/
function_decl|;
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
if|if
condition|(
operator|!
name|isEnabled
argument_list|()
condition|)
return|return;
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
name|ONKEYDOWN
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
block|{
if|if
condition|(
name|iDown
operator|!=
literal|null
condition|)
name|setResource
argument_list|(
name|iDown
argument_list|)
expr_stmt|;
block|}
break|break;
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
block|{
name|setResource
argument_list|(
name|iUp
argument_list|)
expr_stmt|;
name|onClick
argument_list|()
expr_stmt|;
block|}
break|break;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAriaLabel
parameter_list|()
block|{
return|return
name|getAltText
argument_list|()
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
name|setAltText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setAltText
parameter_list|(
name|String
name|altText
parameter_list|)
block|{
name|super
operator|.
name|setAltText
argument_list|(
name|altText
argument_list|)
expr_stmt|;
if|if
condition|(
name|getTitle
argument_list|()
operator|==
literal|null
operator|||
name|getTitle
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|setTitle
argument_list|(
name|altText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addFocusHandler
parameter_list|(
name|FocusHandler
name|handler
parameter_list|)
block|{
return|return
name|addDomHandler
argument_list|(
name|handler
argument_list|,
name|FocusEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addBlurHandler
parameter_list|(
name|BlurHandler
name|handler
parameter_list|)
block|{
return|return
name|addDomHandler
argument_list|(
name|handler
argument_list|,
name|BlurEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

