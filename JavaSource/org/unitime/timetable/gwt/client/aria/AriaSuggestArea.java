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
name|resources
operator|.
name|GwtAriaMessages
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
name|aria
operator|.
name|client
operator|.
name|AutocompleteValue
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
name|aria
operator|.
name|client
operator|.
name|Id
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
name|core
operator|.
name|shared
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
name|HandlesAllKeyEvents
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
name|HasBlurHandlers
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
name|HasFocusHandlers
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
name|KeyPressEvent
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
name|KeyUpEvent
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
name|HasSelectionHandlers
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
name|SelectionEvent
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
name|SelectionHandler
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
name|Composite
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
name|MenuBar
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
name|MenuItem
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
name|PopupPanel
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
name|SuggestOracle
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
name|ValueBoxBase
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
name|SuggestOracle
operator|.
name|Request
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
name|SuggestOracle
operator|.
name|Response
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
name|SuggestOracle
operator|.
name|Suggestion
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AriaSuggestArea
extends|extends
name|Composite
implements|implements
name|HasText
implements|,
name|HasValue
argument_list|<
name|String
argument_list|>
implements|,
name|HasSelectionHandlers
argument_list|<
name|Suggestion
argument_list|>
implements|,
name|Focusable
implements|,
name|HasEnabled
implements|,
name|HasAriaLabel
block|{
specifier|private
specifier|static
name|GwtAriaMessages
name|ARIA
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtAriaMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|AriaTextArea
name|iText
decl_stmt|;
specifier|private
name|SuggestOracle
name|iOracle
decl_stmt|;
specifier|private
name|PopupPanel
name|iSuggestionPopup
decl_stmt|;
specifier|private
name|SuggestionMenu
name|iSuggestionMenu
decl_stmt|;
specifier|private
name|ScrollPanel
name|iPopupScroll
decl_stmt|;
specifier|private
name|SuggestionCallback
name|iSuggestionCallback
decl_stmt|;
specifier|private
name|SuggestOracle
operator|.
name|Callback
name|iOracleCallback
decl_stmt|;
specifier|private
name|String
name|iCurrentText
init|=
literal|null
decl_stmt|;
specifier|public
name|AriaSuggestArea
parameter_list|(
name|SuggestOracle
name|oracle
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|AriaTextArea
argument_list|()
argument_list|,
name|oracle
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AriaSuggestArea
parameter_list|(
name|AriaTextArea
name|box
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|suggestions
parameter_list|)
block|{
name|this
argument_list|(
name|box
argument_list|,
operator|new
name|SimpleOracle
argument_list|(
name|suggestions
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AriaSuggestArea
parameter_list|(
name|AriaTextArea
name|box
parameter_list|,
name|SuggestOracle
name|oracle
parameter_list|)
block|{
name|iOracle
operator|=
name|oracle
expr_stmt|;
name|iText
operator|=
name|box
expr_stmt|;
name|iText
operator|.
name|setStyleName
argument_list|(
literal|"unitime-TextArea"
argument_list|)
expr_stmt|;
name|initWidget
argument_list|(
name|iText
argument_list|)
expr_stmt|;
name|addEventsToTextBox
argument_list|()
expr_stmt|;
name|iSuggestionMenu
operator|=
operator|new
name|SuggestionMenu
argument_list|()
expr_stmt|;
name|iPopupScroll
operator|=
operator|new
name|ScrollPanel
argument_list|(
name|iSuggestionMenu
argument_list|)
expr_stmt|;
name|iPopupScroll
operator|.
name|addStyleName
argument_list|(
literal|"scroll"
argument_list|)
expr_stmt|;
name|iSuggestionPopup
operator|=
operator|new
name|PopupPanel
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iSuggestionPopup
operator|.
name|setPreviewingAllNativeEvents
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iSuggestionPopup
operator|.
name|setStyleName
argument_list|(
literal|"unitime-SuggestBoxPopup"
argument_list|)
expr_stmt|;
name|iSuggestionPopup
operator|.
name|setWidget
argument_list|(
name|iPopupScroll
argument_list|)
expr_stmt|;
name|iSuggestionPopup
operator|.
name|addAutoHidePartner
argument_list|(
name|getElement
argument_list|()
argument_list|)
expr_stmt|;
name|iSuggestionCallback
operator|=
operator|new
name|SuggestionCallback
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuggestionSelected
parameter_list|(
name|Suggestion
name|suggestion
parameter_list|)
block|{
if|if
condition|(
operator|!
name|suggestion
operator|.
name|getReplacementString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|setStatus
argument_list|(
name|ARIA
operator|.
name|suggestionSelected
argument_list|(
name|status
argument_list|(
name|suggestion
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|iCurrentText
operator|=
name|suggestion
operator|.
name|getReplacementString
argument_list|()
expr_stmt|;
name|setText
argument_list|(
name|suggestion
operator|.
name|getReplacementString
argument_list|()
argument_list|)
expr_stmt|;
name|hideSuggestionList
argument_list|()
expr_stmt|;
name|fireSuggestionEvent
argument_list|(
name|suggestion
argument_list|)
expr_stmt|;
block|}
block|}
expr_stmt|;
name|iOracleCallback
operator|=
operator|new
name|SuggestOracle
operator|.
name|Callback
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onSuggestionsReady
parameter_list|(
name|Request
name|request
parameter_list|,
name|Response
name|response
parameter_list|)
block|{
if|if
condition|(
name|response
operator|.
name|getSuggestions
argument_list|()
operator|==
literal|null
operator|||
name|response
operator|.
name|getSuggestions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|iSuggestionPopup
operator|.
name|isShowing
argument_list|()
condition|)
name|iSuggestionPopup
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iSuggestionMenu
operator|.
name|clearItems
argument_list|()
expr_stmt|;
name|SuggestOracle
operator|.
name|Suggestion
name|first
init|=
literal|null
decl_stmt|;
for|for
control|(
name|SuggestOracle
operator|.
name|Suggestion
name|suggestion
range|:
name|response
operator|.
name|getSuggestions
argument_list|()
control|)
block|{
name|iSuggestionMenu
operator|.
name|addItem
argument_list|(
operator|new
name|SuggestionMenuItem
argument_list|(
name|suggestion
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|==
literal|null
condition|)
name|first
operator|=
name|suggestion
expr_stmt|;
block|}
name|iSuggestionMenu
operator|.
name|selectItem
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|iSuggestionMenu
operator|.
name|setWidth
argument_list|(
operator|(
name|iText
operator|.
name|getElement
argument_list|()
operator|.
name|getClientWidth
argument_list|()
operator|-
literal|2
operator|)
operator|+
literal|"px"
argument_list|)
expr_stmt|;
name|iSuggestionPopup
operator|.
name|showRelativeTo
argument_list|(
name|iText
argument_list|)
expr_stmt|;
name|iSuggestionMenu
operator|.
name|scrollToView
argument_list|()
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|getSuggestions
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
if|if
condition|(
name|first
operator|.
name|getReplacementString
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|setStatus
argument_list|(
name|status
argument_list|(
name|first
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|setStatus
argument_list|(
name|ARIA
operator|.
name|showingOneSuggestion
argument_list|(
name|status
argument_list|(
name|first
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setStatus
argument_list|(
name|ARIA
operator|.
name|showingMultipleSuggestions
argument_list|(
name|response
operator|.
name|getSuggestions
argument_list|()
operator|.
name|size
argument_list|()
argument_list|,
name|request
operator|.
name|getQuery
argument_list|()
argument_list|,
name|status
argument_list|(
name|first
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
expr_stmt|;
name|Roles
operator|.
name|getTextboxRole
argument_list|()
operator|.
name|setAriaAutocompleteProperty
argument_list|(
name|iText
operator|.
name|getElement
argument_list|()
argument_list|,
name|AutocompleteValue
operator|.
name|NONE
argument_list|)
expr_stmt|;
name|iSuggestionPopup
operator|.
name|getElement
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"id"
argument_list|,
name|DOM
operator|.
name|createUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|Roles
operator|.
name|getTextboxRole
argument_list|()
operator|.
name|setAriaOwnsProperty
argument_list|(
name|iText
operator|.
name|getElement
argument_list|()
argument_list|,
name|Id
operator|.
name|of
argument_list|(
name|iSuggestionPopup
operator|.
name|getElement
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iText
operator|.
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
name|showSuggestionList
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|status
parameter_list|(
name|Suggestion
name|suggestion
parameter_list|)
block|{
return|return
name|suggestion
operator|instanceof
name|HasStatus
condition|?
operator|(
operator|(
name|HasStatus
operator|)
name|suggestion
operator|)
operator|.
name|getStatusString
argument_list|()
else|:
name|suggestion
operator|.
name|getDisplayString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setStatus
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|AriaStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|addEventsToTextBox
parameter_list|()
block|{
class|class
name|TextBoxEvents
extends|extends
name|HandlesAllKeyEvents
implements|implements
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
block|{
specifier|public
name|void
name|onKeyDown
parameter_list|(
name|KeyDownEvent
name|event
parameter_list|)
block|{
switch|switch
condition|(
name|event
operator|.
name|getNativeKeyCode
argument_list|()
condition|)
block|{
case|case
name|KeyCodes
operator|.
name|KEY_DOWN
case|:
if|if
condition|(
name|moveSelectionDown
argument_list|()
condition|)
block|{
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
return|return;
block|}
if|if
condition|(
operator|!
name|isSuggestionListShowing
argument_list|()
operator|&&
operator|(
name|event
operator|.
name|getNativeEvent
argument_list|()
operator|.
name|getAltKey
argument_list|()
operator|||
name|iText
operator|.
name|getCursorPos
argument_list|()
operator|==
name|iText
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
operator|)
condition|)
name|showSuggestionList
argument_list|()
expr_stmt|;
break|break;
case|case
name|KeyCodes
operator|.
name|KEY_UP
case|:
if|if
condition|(
name|moveSelectionUp
argument_list|()
condition|)
block|{
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
return|return;
block|}
break|break;
case|case
name|KeyCodes
operator|.
name|KEY_ENTER
case|:
if|if
condition|(
name|isSuggestionListShowing
argument_list|()
condition|)
block|{
name|iSuggestionMenu
operator|.
name|executeSelected
argument_list|()
expr_stmt|;
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
return|return;
block|}
break|break;
case|case
name|KeyCodes
operator|.
name|KEY_TAB
case|:
if|if
condition|(
name|isSuggestionListShowing
argument_list|()
condition|)
block|{
name|iSuggestionMenu
operator|.
name|executeSelected
argument_list|()
expr_stmt|;
block|}
break|break;
case|case
name|KeyCodes
operator|.
name|KEY_ESCAPE
case|:
if|if
condition|(
name|isSuggestionListShowing
argument_list|()
condition|)
block|{
name|hideSuggestionList
argument_list|()
expr_stmt|;
name|event
operator|.
name|preventDefault
argument_list|()
expr_stmt|;
return|return;
block|}
block|}
name|delegateEvent
argument_list|(
name|AriaSuggestArea
operator|.
name|this
argument_list|,
name|event
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onKeyPress
parameter_list|(
name|KeyPressEvent
name|event
parameter_list|)
block|{
name|delegateEvent
argument_list|(
name|AriaSuggestArea
operator|.
name|this
argument_list|,
name|event
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onKeyUp
parameter_list|(
name|KeyUpEvent
name|event
parameter_list|)
block|{
name|refreshSuggestions
argument_list|()
expr_stmt|;
name|delegateEvent
argument_list|(
name|AriaSuggestArea
operator|.
name|this
argument_list|,
name|event
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|onValueChange
parameter_list|(
name|ValueChangeEvent
argument_list|<
name|String
argument_list|>
name|event
parameter_list|)
block|{
name|delegateEvent
argument_list|(
name|AriaSuggestArea
operator|.
name|this
argument_list|,
name|event
argument_list|)
expr_stmt|;
block|}
block|}
name|TextBoxEvents
name|events
init|=
operator|new
name|TextBoxEvents
argument_list|()
decl_stmt|;
name|events
operator|.
name|addKeyHandlersTo
argument_list|(
name|iText
argument_list|)
expr_stmt|;
name|iText
operator|.
name|addValueChangeHandler
argument_list|(
name|events
argument_list|)
expr_stmt|;
block|}
specifier|private
name|boolean
name|moveSelectionDown
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isSuggestionListShowing
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|iSuggestionMenu
operator|.
name|selectItem
argument_list|(
name|iSuggestionMenu
operator|.
name|getSelectedItemIndex
argument_list|()
operator|+
literal|1
argument_list|)
condition|)
block|{
if|if
condition|(
name|iSuggestionMenu
operator|.
name|getNumItems
argument_list|()
operator|>
literal|1
condition|)
name|setStatus
argument_list|(
name|ARIA
operator|.
name|onSuggestion
argument_list|(
name|iSuggestionMenu
operator|.
name|getSelectedItemIndex
argument_list|()
operator|+
literal|1
argument_list|,
name|iSuggestionMenu
operator|.
name|getNumItems
argument_list|()
argument_list|,
name|status
argument_list|(
name|iSuggestionMenu
operator|.
name|getSelectedSuggestion
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|private
name|boolean
name|moveSelectionUp
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isSuggestionListShowing
argument_list|()
condition|)
return|return
literal|false
return|;
name|boolean
name|selected
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|iSuggestionMenu
operator|.
name|getSelectedItemIndex
argument_list|()
operator|==
operator|-
literal|1
condition|)
block|{
name|selected
operator|=
name|iSuggestionMenu
operator|.
name|selectItem
argument_list|(
name|iSuggestionMenu
operator|.
name|getNumItems
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|selected
operator|=
name|iSuggestionMenu
operator|.
name|selectItem
argument_list|(
name|iSuggestionMenu
operator|.
name|getSelectedItemIndex
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|selected
condition|)
block|{
if|if
condition|(
name|iSuggestionMenu
operator|.
name|getNumItems
argument_list|()
operator|>
literal|1
condition|)
name|setStatus
argument_list|(
name|ARIA
operator|.
name|onSuggestion
argument_list|(
name|iSuggestionMenu
operator|.
name|getSelectedItemIndex
argument_list|()
operator|+
literal|1
argument_list|,
name|iSuggestionMenu
operator|.
name|getNumItems
argument_list|()
argument_list|,
name|status
argument_list|(
name|iSuggestionMenu
operator|.
name|getSelectedSuggestion
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|void
name|showSuggestionList
parameter_list|()
block|{
name|iCurrentText
operator|=
literal|null
expr_stmt|;
name|refreshSuggestions
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|refreshSuggestions
parameter_list|()
block|{
name|String
name|text
init|=
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|equals
argument_list|(
name|iCurrentText
argument_list|)
condition|)
block|{
return|return;
block|}
else|else
block|{
name|iCurrentText
operator|=
name|text
expr_stmt|;
block|}
name|showSuggestions
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|hideSuggestionList
parameter_list|()
block|{
if|if
condition|(
name|iSuggestionPopup
operator|.
name|isShowing
argument_list|()
condition|)
name|iSuggestionPopup
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|showSuggestions
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|iOracle
operator|.
name|requestSuggestions
argument_list|(
operator|new
name|Request
argument_list|(
name|text
argument_list|)
argument_list|,
name|iOracleCallback
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isSuggestionListShowing
parameter_list|()
block|{
return|return
name|iSuggestionPopup
operator|.
name|isShowing
argument_list|()
return|;
block|}
specifier|public
name|SuggestionMenu
name|getSuggestionMenu
parameter_list|()
block|{
return|return
name|iSuggestionMenu
return|;
block|}
specifier|private
class|class
name|SuggestionMenu
extends|extends
name|MenuBar
implements|implements
name|HasFocusHandlers
implements|,
name|HasBlurHandlers
block|{
name|SuggestionMenu
parameter_list|()
block|{
name|super
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|setFocusOnHoverEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|ONBLUR
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|ONFOCUS
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
switch|switch
condition|(
name|DOM
operator|.
name|eventGetType
argument_list|(
name|event
argument_list|)
condition|)
block|{
case|case
name|Event
operator|.
name|ONBLUR
case|:
name|BlurEvent
operator|.
name|fireNativeEvent
argument_list|(
name|event
argument_list|,
name|this
argument_list|)
expr_stmt|;
break|break;
case|case
name|Event
operator|.
name|ONFOCUS
case|:
name|FocusEvent
operator|.
name|fireNativeEvent
argument_list|(
name|event
argument_list|,
name|this
argument_list|)
expr_stmt|;
break|break;
block|}
name|super
operator|.
name|onBrowserEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getNumItems
parameter_list|()
block|{
return|return
name|getItems
argument_list|()
operator|.
name|size
argument_list|()
return|;
block|}
specifier|public
name|int
name|getSelectedItemIndex
parameter_list|()
block|{
name|MenuItem
name|selectedItem
init|=
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedItem
operator|!=
literal|null
condition|)
return|return
name|getItems
argument_list|()
operator|.
name|indexOf
argument_list|(
name|selectedItem
argument_list|)
return|;
return|return
operator|-
literal|1
return|;
block|}
specifier|public
name|boolean
name|selectItem
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|List
argument_list|<
name|MenuItem
argument_list|>
name|items
init|=
name|getItems
argument_list|()
decl_stmt|;
if|if
condition|(
name|index
operator|>
operator|-
literal|1
operator|&&
name|index
operator|<
name|items
operator|.
name|size
argument_list|()
condition|)
block|{
name|selectItem
argument_list|(
name|items
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|)
expr_stmt|;
name|iPopupScroll
operator|.
name|ensureVisible
argument_list|(
name|items
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|scrollToView
parameter_list|()
block|{
name|List
argument_list|<
name|MenuItem
argument_list|>
name|items
init|=
name|getItems
argument_list|()
decl_stmt|;
name|int
name|index
init|=
name|getSelectedItemIndex
argument_list|()
decl_stmt|;
if|if
condition|(
name|index
operator|>
operator|-
literal|1
operator|&&
name|index
operator|<
name|items
operator|.
name|size
argument_list|()
condition|)
block|{
name|iPopupScroll
operator|.
name|ensureVisible
argument_list|(
name|items
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|executeSelected
parameter_list|()
block|{
name|MenuItem
name|selected
init|=
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|selected
operator|.
name|getScheduledCommand
argument_list|()
operator|.
name|execute
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
specifier|public
name|Suggestion
name|getSelectedSuggestion
parameter_list|()
block|{
name|MenuItem
name|selectedItem
init|=
name|getSelectedItem
argument_list|()
decl_stmt|;
return|return
name|selectedItem
operator|==
literal|null
condition|?
literal|null
else|:
operator|(
operator|(
name|SuggestionMenuItem
operator|)
name|selectedItem
operator|)
operator|.
name|getSuggestion
argument_list|()
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
name|addHandler
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
name|addHandler
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
block|}
specifier|private
class|class
name|SuggestionMenuItem
extends|extends
name|MenuItem
block|{
specifier|private
name|Suggestion
name|iSuggestion
init|=
literal|null
decl_stmt|;
specifier|private
name|SuggestionMenuItem
parameter_list|(
specifier|final
name|Suggestion
name|suggestion
parameter_list|)
block|{
name|super
argument_list|(
name|suggestion
operator|.
name|getDisplayString
argument_list|()
argument_list|,
name|iOracle
operator|.
name|isDisplayStringHTML
argument_list|()
argument_list|,
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
name|iSuggestionCallback
operator|.
name|onSuggestionSelected
argument_list|(
name|suggestion
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setStyleName
argument_list|(
literal|"item"
argument_list|)
expr_stmt|;
name|getElement
argument_list|()
operator|.
name|setAttribute
argument_list|(
literal|"whiteSpace"
argument_list|,
literal|"pre-wrap"
argument_list|)
expr_stmt|;
name|iSuggestion
operator|=
name|suggestion
expr_stmt|;
block|}
specifier|public
name|Suggestion
name|getSuggestion
parameter_list|()
block|{
return|return
name|iSuggestion
return|;
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
name|String
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
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iText
operator|.
name|getValue
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|String
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
name|String
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
name|iText
operator|.
name|setValue
argument_list|(
name|value
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
annotation|@
name|Override
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|iText
operator|.
name|getText
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
name|iText
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addSelectionHandler
parameter_list|(
name|SelectionHandler
argument_list|<
name|Suggestion
argument_list|>
name|handler
parameter_list|)
block|{
return|return
name|addHandler
argument_list|(
name|handler
argument_list|,
name|SelectionEvent
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
specifier|private
name|void
name|fireSuggestionEvent
parameter_list|(
name|Suggestion
name|selectedSuggestion
parameter_list|)
block|{
name|SelectionEvent
operator|.
name|fire
argument_list|(
name|this
argument_list|,
name|selectedSuggestion
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
name|iText
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
name|iText
operator|.
name|setAccessKey
argument_list|(
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
name|iText
operator|.
name|setFocus
argument_list|(
name|focused
argument_list|)
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
name|iText
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
name|boolean
name|isEnabled
parameter_list|()
block|{
return|return
name|iText
operator|.
name|isEnabled
argument_list|()
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
name|iText
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ValueBoxBase
argument_list|<
name|String
argument_list|>
name|getValueBox
parameter_list|()
block|{
return|return
name|iText
return|;
block|}
specifier|public
specifier|static
interface|interface
name|SuggestionCallback
block|{
name|void
name|onSuggestionSelected
parameter_list|(
name|Suggestion
name|suggestion
parameter_list|)
function_decl|;
block|}
specifier|public
specifier|static
interface|interface
name|HasStatus
block|{
specifier|public
name|String
name|getStatusString
parameter_list|()
function_decl|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAriaLabel
parameter_list|()
block|{
return|return
name|iText
operator|.
name|getAriaLabel
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
name|iText
operator|.
name|setAriaLabel
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SimpleOracle
extends|extends
name|SuggestOracle
block|{
name|List
argument_list|<
name|String
argument_list|>
name|iSuggestions
decl_stmt|;
specifier|public
name|SimpleOracle
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|suggestions
parameter_list|)
block|{
name|iSuggestions
operator|=
name|suggestions
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|requestSuggestions
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|,
specifier|final
name|Callback
name|callback
parameter_list|)
block|{
name|List
argument_list|<
name|SimpleSuggestion
argument_list|>
name|suggestions
init|=
operator|new
name|ArrayList
argument_list|<
name|SimpleSuggestion
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|suggestion
range|:
name|iSuggestions
control|)
block|{
if|if
condition|(
name|suggestion
operator|.
name|equalsIgnoreCase
argument_list|(
name|request
operator|.
name|getQuery
argument_list|()
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|suggestion
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|request
operator|.
name|getQuery
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
name|suggestions
operator|.
name|add
argument_list|(
operator|new
name|SimpleSuggestion
argument_list|(
name|suggestion
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|suggestions
operator|.
name|size
argument_list|()
operator|>=
name|request
operator|.
name|getLimit
argument_list|()
condition|)
break|break;
block|}
name|callback
operator|.
name|onSuggestionsReady
argument_list|(
name|request
argument_list|,
operator|new
name|Response
argument_list|(
name|suggestions
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isDisplayStringHTML
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|SimpleSuggestion
implements|implements
name|SuggestOracle
operator|.
name|Suggestion
block|{
specifier|private
name|String
name|iSuggestion
decl_stmt|;
name|SimpleSuggestion
parameter_list|(
name|String
name|suggestion
parameter_list|)
block|{
name|iSuggestion
operator|=
name|suggestion
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDisplayString
parameter_list|()
block|{
return|return
name|iSuggestion
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getReplacementString
parameter_list|()
block|{
return|return
name|iSuggestion
return|;
block|}
block|}
block|}
end_class

end_unit

