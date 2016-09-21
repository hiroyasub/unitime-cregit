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
name|widgets
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
name|HashMap
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
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|client
operator|.
name|ToolBox
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
name|aria
operator|.
name|AriaButton
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
name|resources
operator|.
name|GwtResources
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
name|core
operator|.
name|client
operator|.
name|GWT
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
name|core
operator|.
name|client
operator|.
name|Scheduler
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
name|core
operator|.
name|client
operator|.
name|Scheduler
operator|.
name|ScheduledCommand
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
name|Style
operator|.
name|Unit
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
name|ClickEvent
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
name|ClickHandler
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
name|KeyCodes
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
name|KeyDownEvent
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
name|KeyDownHandler
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
name|regexp
operator|.
name|shared
operator|.
name|MatchResult
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
name|regexp
operator|.
name|shared
operator|.
name|RegExp
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
name|Button
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
name|ComplexPanel
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
name|UniTimeHeaderPanel
extends|extends
name|P
block|{
specifier|public
specifier|static
specifier|final
name|GwtResources
name|RESOURCES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtResources
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|RegExp
name|sAcessKeyRegExp
init|=
name|RegExp
operator|.
name|compile
argument_list|(
literal|"<u>(\\w)</u>"
argument_list|,
literal|"i"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|RegExp
name|sStripAcessKeyRegExp
init|=
name|RegExp
operator|.
name|compile
argument_list|(
literal|"(.*)<u>(\\w)</u>(.*)"
argument_list|,
literal|"i"
argument_list|)
decl_stmt|;
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|iOperations
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|ClickHandler
argument_list|>
name|iClickHandlers
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ClickHandler
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|P
name|iMessage
decl_stmt|;
specifier|private
name|P
name|iTitle
decl_stmt|;
specifier|private
name|P
name|iLeft
decl_stmt|,
name|iContent
decl_stmt|,
name|iRight
decl_stmt|,
name|iButtons
decl_stmt|;
specifier|private
name|Image
name|iLoadingImage
decl_stmt|;
specifier|private
name|OpenCloseSectionImage
name|iOpenCloseImage
decl_stmt|;
specifier|private
name|boolean
name|iRotateFocus
init|=
literal|false
decl_stmt|;
specifier|private
name|KeyDownHandler
name|iKeyDownHandler
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|UniTimeHeaderPanel
argument_list|>
name|iClones
init|=
operator|new
name|ArrayList
argument_list|<
name|UniTimeHeaderPanel
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|UniTimeHeaderPanel
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|super
argument_list|(
literal|"unitime-HeaderPanel"
argument_list|)
expr_stmt|;
name|iLeft
operator|=
operator|new
name|P
argument_list|(
literal|"left"
argument_list|,
literal|"unitime-NoPrint"
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|iLeft
argument_list|)
expr_stmt|;
name|iRight
operator|=
operator|new
name|P
argument_list|(
literal|"right"
argument_list|,
literal|"unitime-NoPrint"
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|iRight
argument_list|)
expr_stmt|;
name|iButtons
operator|=
operator|new
name|P
argument_list|(
name|DOM
operator|.
name|createSpan
argument_list|()
argument_list|,
literal|"buttons"
argument_list|)
expr_stmt|;
name|iRight
operator|.
name|add
argument_list|(
name|iButtons
argument_list|)
expr_stmt|;
name|iContent
operator|=
operator|new
name|P
argument_list|(
literal|"content"
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|iContent
argument_list|)
expr_stmt|;
name|iOpenCloseImage
operator|=
operator|new
name|OpenCloseSectionImage
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iOpenCloseImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iOpenCloseImage
operator|.
name|addStyleName
argument_list|(
literal|"open-close"
argument_list|)
expr_stmt|;
name|iLeft
operator|.
name|add
argument_list|(
name|iOpenCloseImage
argument_list|)
expr_stmt|;
name|iLoadingImage
operator|=
operator|new
name|Image
argument_list|(
name|RESOURCES
operator|.
name|loading_small
argument_list|()
argument_list|)
expr_stmt|;
name|iLoadingImage
operator|.
name|addStyleName
argument_list|(
literal|"loading"
argument_list|)
expr_stmt|;
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iLeft
operator|.
name|add
argument_list|(
name|iLoadingImage
argument_list|)
expr_stmt|;
name|iTitle
operator|=
operator|new
name|P
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
name|iTitle
operator|.
name|setHTML
argument_list|(
name|title
argument_list|)
expr_stmt|;
name|iLeft
operator|.
name|add
argument_list|(
name|iTitle
argument_list|)
expr_stmt|;
name|iMessage
operator|=
operator|new
name|P
argument_list|(
literal|"message"
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iContent
operator|.
name|add
argument_list|(
name|iMessage
argument_list|)
expr_stmt|;
name|iKeyDownHandler
operator|=
operator|new
name|KeyDownHandler
argument_list|()
block|{
specifier|private
name|void
name|focus
parameter_list|(
name|KeyDownEvent
name|event
parameter_list|,
specifier|final
name|Button
name|buttonToFocus
parameter_list|)
block|{
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
name|event
operator|.
name|stopPropagation
argument_list|()
expr_stmt|;
name|Scheduler
operator|.
name|get
argument_list|()
operator|.
name|scheduleDeferred
argument_list|(
operator|new
name|ScheduledCommand
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|buttonToFocus
operator|.
name|setFocus
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onKeyDown
parameter_list|(
name|KeyDownEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|iRotateFocus
operator|&&
name|event
operator|.
name|getNativeKeyCode
argument_list|()
operator|==
name|KeyCodes
operator|.
name|KEY_TAB
operator|&&
name|event
operator|.
name|getSource
argument_list|()
operator|!=
literal|null
operator|&&
name|event
operator|.
name|getSource
argument_list|()
operator|instanceof
name|Button
condition|)
block|{
comment|// first button
name|ComplexPanel
name|panel
init|=
name|iButtons
decl_stmt|;
name|Button
name|firstButton
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|panel
operator|.
name|getWidgetCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Button
name|button
init|=
operator|(
name|Button
operator|)
name|panel
operator|.
name|getWidget
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|button
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
name|firstButton
operator|=
name|button
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|firstButton
operator|==
literal|null
condition|)
return|return;
comment|// last button
if|if
condition|(
operator|!
name|iClones
operator|.
name|isEmpty
argument_list|()
condition|)
name|panel
operator|=
name|iClones
operator|.
name|get
argument_list|(
name|iClones
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
operator|.
name|iButtons
expr_stmt|;
name|Button
name|lastButton
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|panel
operator|.
name|getWidgetCount
argument_list|()
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|Button
name|button
init|=
operator|(
name|Button
operator|)
name|panel
operator|.
name|getWidget
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|button
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
name|lastButton
operator|=
name|button
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|lastButton
operator|==
literal|null
condition|)
return|return;
comment|// last to first
if|if
condition|(
name|lastButton
operator|.
name|equals
argument_list|(
name|event
operator|.
name|getSource
argument_list|()
argument_list|)
operator|&&
operator|!
name|event
operator|.
name|isShiftKeyDown
argument_list|()
condition|)
name|focus
argument_list|(
name|event
argument_list|,
name|firstButton
argument_list|)
expr_stmt|;
comment|// first to last
if|if
condition|(
name|firstButton
operator|.
name|equals
argument_list|(
name|event
operator|.
name|getSource
argument_list|()
argument_list|)
operator|&&
name|event
operator|.
name|isShiftKeyDown
argument_list|()
condition|)
name|focus
argument_list|(
name|event
argument_list|,
name|lastButton
argument_list|)
expr_stmt|;
block|}
block|}
block|}
expr_stmt|;
block|}
specifier|public
name|void
name|setRotateFocus
parameter_list|(
name|boolean
name|rotateFocus
parameter_list|)
block|{
name|iRotateFocus
operator|=
name|rotateFocus
expr_stmt|;
block|}
specifier|public
name|void
name|setTitleStyleName
parameter_list|(
name|String
name|styleName
parameter_list|)
block|{
name|iTitle
operator|.
name|setStyleName
argument_list|(
name|styleName
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addCollapsibleHandler
parameter_list|(
name|ValueChangeHandler
argument_list|<
name|Boolean
argument_list|>
name|handler
parameter_list|)
block|{
name|iOpenCloseImage
operator|.
name|addValueChangeHandler
argument_list|(
name|handler
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setCollapsible
parameter_list|(
name|Boolean
name|opened
parameter_list|)
block|{
name|iOpenCloseImage
operator|.
name|setVisible
argument_list|(
name|opened
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|opened
operator|!=
literal|null
condition|)
name|iOpenCloseImage
operator|.
name|setValue
argument_list|(
name|opened
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isCollapsible
parameter_list|()
block|{
return|return
name|iOpenCloseImage
operator|.
name|isVisible
argument_list|()
condition|?
name|iOpenCloseImage
operator|.
name|getValue
argument_list|()
else|:
literal|null
return|;
block|}
specifier|public
name|void
name|setHeaderTitle
parameter_list|(
name|String
name|title
parameter_list|)
block|{
name|iTitle
operator|.
name|setHTML
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getHeaderTitle
parameter_list|()
block|{
return|return
name|iTitle
operator|.
name|getHTML
argument_list|()
return|;
block|}
specifier|public
name|UniTimeHeaderPanel
parameter_list|()
block|{
name|this
argument_list|(
literal|"&nbsp;"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clearMessage
parameter_list|()
block|{
name|iMessage
operator|.
name|setHTML
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
name|clone
operator|.
name|clearMessage
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setErrorMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|message
operator|==
literal|null
operator|||
name|message
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|clearMessage
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setHTML
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setStyleName
argument_list|(
literal|"error"
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
name|clone
operator|.
name|setErrorMessage
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setWarningMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|message
operator|==
literal|null
operator|||
name|message
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|clearMessage
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setHTML
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setStyleName
argument_list|(
literal|"warning"
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
name|clone
operator|.
name|setWarningMessage
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|message
operator|==
literal|null
operator|||
name|message
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|clearMessage
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setHTML
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setStyleName
argument_list|(
literal|"message"
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
name|clone
operator|.
name|setMessage
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|showLoading
parameter_list|()
block|{
name|iMessage
operator|.
name|setHTML
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iMessage
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|iLoadingImage
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
name|clone
operator|.
name|showLoading
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addButton
parameter_list|(
name|String
name|operation
parameter_list|,
name|String
name|name
parameter_list|,
name|ClickHandler
name|clickHandler
parameter_list|)
block|{
name|addButton
argument_list|(
name|operation
argument_list|,
name|name
argument_list|,
literal|null
argument_list|,
name|clickHandler
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addButton
parameter_list|(
name|String
name|operation
parameter_list|,
name|String
name|name
parameter_list|,
name|Integer
name|width
parameter_list|,
name|ClickHandler
name|clickHandler
parameter_list|)
block|{
name|addButton
argument_list|(
name|operation
argument_list|,
name|name
argument_list|,
name|guessAccessKey
argument_list|(
name|name
argument_list|)
argument_list|,
name|width
operator|==
literal|null
condition|?
literal|null
else|:
name|width
operator|+
literal|"px"
argument_list|,
name|clickHandler
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Character
name|guessAccessKey
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|MatchResult
name|result
init|=
name|sAcessKeyRegExp
operator|.
name|exec
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
operator|(
name|result
operator|==
literal|null
condition|?
literal|null
else|:
name|result
operator|.
name|getGroup
argument_list|(
literal|1
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|String
name|stripAccessKey
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|""
return|;
name|MatchResult
name|result
init|=
name|sStripAcessKeyRegExp
operator|.
name|exec
argument_list|(
name|name
argument_list|)
decl_stmt|;
return|return
operator|(
name|result
operator|==
literal|null
condition|?
name|name
else|:
name|result
operator|.
name|getGroup
argument_list|(
literal|1
argument_list|)
operator|+
name|result
operator|.
name|getGroup
argument_list|(
literal|2
argument_list|)
operator|+
name|result
operator|.
name|getGroup
argument_list|(
literal|3
argument_list|)
operator|)
return|;
block|}
specifier|private
name|Button
name|addButton
parameter_list|(
name|String
name|operation
parameter_list|,
name|String
name|name
parameter_list|,
name|Character
name|accessKey
parameter_list|,
name|String
name|width
parameter_list|,
name|ClickHandler
name|clickHandler
parameter_list|)
block|{
name|Button
name|button
init|=
operator|new
name|AriaButton
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|button
operator|.
name|addClickHandler
argument_list|(
name|clickHandler
argument_list|)
expr_stmt|;
name|ToolBox
operator|.
name|setWhiteSpace
argument_list|(
name|button
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
argument_list|,
literal|"nowrap"
argument_list|)
expr_stmt|;
if|if
condition|(
name|accessKey
operator|!=
literal|null
condition|)
name|button
operator|.
name|setAccessKey
argument_list|(
name|accessKey
argument_list|)
expr_stmt|;
if|if
condition|(
name|width
operator|!=
literal|null
condition|)
name|ToolBox
operator|.
name|setMinWidth
argument_list|(
name|button
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
argument_list|,
name|width
argument_list|)
expr_stmt|;
name|iOperations
operator|.
name|put
argument_list|(
name|operation
argument_list|,
name|iButtons
operator|.
name|getWidgetCount
argument_list|()
argument_list|)
expr_stmt|;
name|iClickHandlers
operator|.
name|put
argument_list|(
name|operation
argument_list|,
name|clickHandler
argument_list|)
expr_stmt|;
name|iButtons
operator|.
name|add
argument_list|(
name|button
argument_list|)
expr_stmt|;
name|button
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setMarginLeft
argument_list|(
literal|4
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
block|{
name|Button
name|clonedButton
init|=
name|clone
operator|.
name|addButton
argument_list|(
name|operation
argument_list|,
name|name
argument_list|,
literal|null
argument_list|,
name|width
argument_list|,
name|clickHandler
argument_list|)
decl_stmt|;
name|clonedButton
operator|.
name|addKeyDownHandler
argument_list|(
name|iKeyDownHandler
argument_list|)
expr_stmt|;
block|}
name|button
operator|.
name|addKeyDownHandler
argument_list|(
name|iKeyDownHandler
argument_list|)
expr_stmt|;
return|return
name|button
return|;
block|}
specifier|public
name|void
name|setEnabled
parameter_list|(
name|int
name|button
parameter_list|,
name|boolean
name|enabled
parameter_list|)
block|{
name|Button
name|b
init|=
operator|(
name|Button
operator|)
name|iButtons
operator|.
name|getWidget
argument_list|(
name|button
argument_list|)
decl_stmt|;
name|b
operator|.
name|setVisible
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|b
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
name|clone
operator|.
name|setEnabled
argument_list|(
name|button
argument_list|,
name|enabled
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setEnabled
parameter_list|(
name|String
name|operation
parameter_list|,
name|boolean
name|enabled
parameter_list|)
block|{
name|Integer
name|op
init|=
name|iOperations
operator|.
name|get
argument_list|(
name|operation
argument_list|)
decl_stmt|;
if|if
condition|(
name|op
operator|!=
literal|null
condition|)
name|setEnabled
argument_list|(
name|iOperations
operator|.
name|get
argument_list|(
name|operation
argument_list|)
argument_list|,
name|enabled
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEnabled
parameter_list|(
name|int
name|button
parameter_list|)
block|{
return|return
operator|(
operator|(
name|Button
operator|)
name|iButtons
operator|.
name|getWidget
argument_list|(
name|button
argument_list|)
operator|)
operator|.
name|isVisible
argument_list|()
return|;
block|}
specifier|public
name|Boolean
name|isEnabled
parameter_list|(
name|String
name|operation
parameter_list|)
block|{
name|Integer
name|op
init|=
name|iOperations
operator|.
name|get
argument_list|(
name|operation
argument_list|)
decl_stmt|;
return|return
operator|(
name|op
operator|==
literal|null
condition|?
literal|null
else|:
name|isEnabled
argument_list|(
name|op
argument_list|)
operator|)
return|;
block|}
specifier|public
name|void
name|setFocus
parameter_list|(
name|int
name|button
parameter_list|,
name|boolean
name|focus
parameter_list|)
block|{
name|Button
name|b
init|=
operator|(
name|Button
operator|)
name|iButtons
operator|.
name|getWidget
argument_list|(
name|button
argument_list|)
decl_stmt|;
name|b
operator|.
name|setFocus
argument_list|(
name|focus
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setFocus
parameter_list|(
name|String
name|operation
parameter_list|,
name|boolean
name|focus
parameter_list|)
block|{
name|Integer
name|op
init|=
name|iOperations
operator|.
name|get
argument_list|(
name|operation
argument_list|)
decl_stmt|;
if|if
condition|(
name|op
operator|!=
literal|null
condition|)
name|setFocus
argument_list|(
name|iOperations
operator|.
name|get
argument_list|(
name|operation
argument_list|)
argument_list|,
name|focus
argument_list|)
expr_stmt|;
block|}
specifier|public
name|UniTimeHeaderPanel
name|clonePanel
parameter_list|(
name|String
name|newTitle
parameter_list|)
block|{
name|UniTimeHeaderPanel
name|clone
init|=
operator|new
name|UniTimeHeaderPanel
argument_list|(
name|newTitle
operator|==
literal|null
condition|?
literal|"&nbsp;"
else|:
name|newTitle
argument_list|)
decl_stmt|;
name|iClones
operator|.
name|add
argument_list|(
name|clone
argument_list|)
expr_stmt|;
name|clone
operator|.
name|iMessage
operator|.
name|setHTML
argument_list|(
name|iMessage
operator|.
name|getHTML
argument_list|()
argument_list|)
expr_stmt|;
name|clone
operator|.
name|iMessage
operator|.
name|setVisible
argument_list|(
name|iMessage
operator|.
name|isVisible
argument_list|()
argument_list|)
expr_stmt|;
name|clone
operator|.
name|iMessage
operator|.
name|setStyleName
argument_list|(
name|iMessage
operator|.
name|getStyleName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iOperations
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|op
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|entry
range|:
name|iOperations
operator|.
name|entrySet
argument_list|()
control|)
if|if
condition|(
name|entry
operator|.
name|getValue
argument_list|()
operator|==
name|i
condition|)
name|op
operator|=
name|entry
operator|.
name|getKey
argument_list|()
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
continue|continue;
specifier|final
name|Button
name|button
init|=
operator|(
name|Button
operator|)
name|iButtons
operator|.
name|getWidget
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|ClickHandler
name|clickHandler
init|=
name|iClickHandlers
operator|.
name|get
argument_list|(
name|op
argument_list|)
decl_stmt|;
if|if
condition|(
name|clickHandler
operator|==
literal|null
condition|)
name|clickHandler
operator|=
operator|new
name|ClickHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClick
parameter_list|(
name|ClickEvent
name|event
parameter_list|)
block|{
name|button
operator|.
name|click
argument_list|()
expr_stmt|;
block|}
block|}
expr_stmt|;
name|String
name|width
init|=
name|ToolBox
operator|.
name|getMinWidth
argument_list|(
name|button
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
argument_list|)
decl_stmt|;
name|Button
name|clonedButton
init|=
name|clone
operator|.
name|addButton
argument_list|(
name|op
argument_list|,
name|button
operator|.
name|getHTML
argument_list|()
argument_list|,
literal|null
argument_list|,
name|width
argument_list|,
name|clickHandler
argument_list|)
decl_stmt|;
name|clonedButton
operator|.
name|addKeyDownHandler
argument_list|(
name|iKeyDownHandler
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|button
operator|.
name|isVisible
argument_list|()
condition|)
name|clone
operator|.
name|setEnabled
argument_list|(
name|op
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
return|return
name|clone
return|;
block|}
specifier|public
name|UniTimeHeaderPanel
name|clonePanel
parameter_list|()
block|{
return|return
name|clonePanel
argument_list|(
name|iTitle
operator|.
name|getHTML
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|,
name|boolean
name|propagate
parameter_list|)
block|{
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
if|if
condition|(
name|propagate
condition|)
for|for
control|(
name|UniTimeHeaderPanel
name|clone
range|:
name|iClones
control|)
name|clone
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|,
name|propagate
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
name|setVisible
argument_list|(
name|visible
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insertLeft
parameter_list|(
name|Widget
name|widget
parameter_list|,
name|boolean
name|first
parameter_list|)
block|{
name|widget
operator|.
name|addStyleName
argument_list|(
literal|"left-widget"
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
condition|)
name|iLeft
operator|.
name|insert
argument_list|(
name|widget
argument_list|,
literal|0
argument_list|)
expr_stmt|;
else|else
name|iLeft
operator|.
name|add
argument_list|(
name|widget
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insertWidget
parameter_list|(
name|Widget
name|widget
parameter_list|)
block|{
name|widget
operator|.
name|addStyleName
argument_list|(
literal|"widget"
argument_list|)
expr_stmt|;
name|iContent
operator|.
name|insert
argument_list|(
name|widget
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|insertRight
parameter_list|(
name|Widget
name|widget
parameter_list|,
name|boolean
name|first
parameter_list|)
block|{
name|widget
operator|.
name|addStyleName
argument_list|(
literal|"right-widget"
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
condition|)
name|iRight
operator|.
name|insert
argument_list|(
name|widget
argument_list|,
literal|0
argument_list|)
expr_stmt|;
else|else
name|iRight
operator|.
name|add
argument_list|(
name|widget
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

