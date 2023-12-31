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
name|core
operator|.
name|client
operator|.
name|JavaScriptObject
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
name|i18n
operator|.
name|client
operator|.
name|LocaleInfo
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
name|Window
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
name|HTML
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
name|Widget
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|GwtHint
extends|extends
name|PopupPanel
block|{
specifier|private
specifier|static
name|GwtHint
name|sInstance
decl_stmt|;
specifier|public
name|GwtHint
parameter_list|(
name|String
name|html
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|setStyleName
argument_list|(
literal|"unitime-PopupHint"
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|GwtHint
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
block|{
name|sInstance
operator|=
operator|new
name|GwtHint
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|Client
operator|.
name|addGwtPageChangedHandler
argument_list|(
operator|new
name|Client
operator|.
name|GwtPageChangedHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onChange
parameter_list|(
name|Client
operator|.
name|GwtPageChangeEvent
name|event
parameter_list|)
block|{
name|hideHint
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
return|return
name|sInstance
return|;
block|}
specifier|public
specifier|static
specifier|native
name|void
name|createTriggers
parameter_list|()
comment|/*-{ 		$wnd.showGwtHint = function(source, content) { 			@org.unitime.timetable.gwt.client.GwtHint::_showHint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;)(source, content); 		}; 		$wnd.hideGwtHint = function() { 			@org.unitime.timetable.gwt.client.GwtHint::hideHint()(); 		}; 	}-*/
function_decl|;
comment|/** Never use from GWT code */
specifier|public
specifier|static
name|void
name|_showHint
parameter_list|(
name|JavaScriptObject
name|source
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|showHint
argument_list|(
operator|(
name|Element
operator|)
name|source
operator|.
name|cast
argument_list|()
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|showHint
parameter_list|(
specifier|final
name|Element
name|relativeObject
parameter_list|,
name|String
name|content
parameter_list|)
block|{
name|showHint
argument_list|(
name|relativeObject
argument_list|,
operator|new
name|HTML
argument_list|(
name|content
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|showHint
parameter_list|(
specifier|final
name|Element
name|relativeObject
parameter_list|,
name|Widget
name|content
parameter_list|)
block|{
name|showHint
argument_list|(
name|relativeObject
argument_list|,
name|content
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|showHint
parameter_list|(
specifier|final
name|Element
name|relativeObject
parameter_list|,
name|Widget
name|content
parameter_list|,
specifier|final
name|boolean
name|showRelativeToTheObject
parameter_list|)
block|{
name|getInstance
argument_list|()
operator|.
name|setWidget
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|getInstance
argument_list|()
operator|.
name|setPopupPositionAndShow
argument_list|(
operator|new
name|PositionCallback
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|setPosition
parameter_list|(
name|int
name|offsetWidth
parameter_list|,
name|int
name|offsetHeight
parameter_list|)
block|{
if|if
condition|(
name|relativeObject
operator|!=
literal|null
operator|&&
name|showRelativeToTheObject
condition|)
block|{
name|int
name|textBoxOffsetWidth
init|=
name|relativeObject
operator|.
name|getOffsetWidth
argument_list|()
decl_stmt|;
name|int
name|offsetWidthDiff
init|=
name|offsetWidth
operator|-
name|textBoxOffsetWidth
decl_stmt|;
name|int
name|left
decl_stmt|;
if|if
condition|(
name|LocaleInfo
operator|.
name|getCurrentLocale
argument_list|()
operator|.
name|isRTL
argument_list|()
condition|)
block|{
name|int
name|textBoxAbsoluteLeft
init|=
name|relativeObject
operator|.
name|getAbsoluteLeft
argument_list|()
decl_stmt|;
name|left
operator|=
name|textBoxAbsoluteLeft
operator|-
name|offsetWidthDiff
expr_stmt|;
if|if
condition|(
name|offsetWidthDiff
operator|>
literal|0
condition|)
block|{
name|int
name|windowRight
init|=
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|+
name|Window
operator|.
name|getScrollLeft
argument_list|()
decl_stmt|;
name|int
name|windowLeft
init|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
decl_stmt|;
name|int
name|textBoxLeftValForRightEdge
init|=
name|textBoxAbsoluteLeft
operator|+
name|textBoxOffsetWidth
decl_stmt|;
name|int
name|distanceToWindowRight
init|=
name|windowRight
operator|-
name|textBoxLeftValForRightEdge
decl_stmt|;
name|int
name|distanceFromWindowLeft
init|=
name|textBoxLeftValForRightEdge
operator|-
name|windowLeft
decl_stmt|;
if|if
condition|(
name|distanceFromWindowLeft
operator|<
name|offsetWidth
operator|&&
name|distanceToWindowRight
operator|>=
name|offsetWidthDiff
condition|)
block|{
name|left
operator|=
name|textBoxAbsoluteLeft
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|left
operator|=
name|relativeObject
operator|.
name|getAbsoluteLeft
argument_list|()
expr_stmt|;
if|if
condition|(
name|offsetWidthDiff
operator|>
literal|0
condition|)
block|{
name|int
name|windowRight
init|=
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|+
name|Window
operator|.
name|getScrollLeft
argument_list|()
decl_stmt|;
name|int
name|windowLeft
init|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
decl_stmt|;
name|int
name|distanceToWindowRight
init|=
name|windowRight
operator|-
name|left
decl_stmt|;
name|int
name|distanceFromWindowLeft
init|=
name|left
operator|-
name|windowLeft
decl_stmt|;
if|if
condition|(
name|distanceToWindowRight
operator|<
name|offsetWidth
operator|&&
name|distanceFromWindowLeft
operator|>=
name|offsetWidthDiff
condition|)
block|{
name|left
operator|-=
name|offsetWidthDiff
expr_stmt|;
block|}
block|}
block|}
name|int
name|top
init|=
name|relativeObject
operator|.
name|getAbsoluteTop
argument_list|()
decl_stmt|;
name|int
name|windowTop
init|=
name|Window
operator|.
name|getScrollTop
argument_list|()
decl_stmt|;
name|int
name|windowBottom
init|=
name|Window
operator|.
name|getScrollTop
argument_list|()
operator|+
name|Window
operator|.
name|getClientHeight
argument_list|()
decl_stmt|;
name|int
name|distanceFromWindowTop
init|=
name|top
operator|-
name|windowTop
decl_stmt|;
name|int
name|distanceToWindowBottom
init|=
name|windowBottom
operator|-
operator|(
name|top
operator|+
name|relativeObject
operator|.
name|getOffsetHeight
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|distanceToWindowBottom
operator|<
name|offsetHeight
operator|&&
name|distanceFromWindowTop
operator|>=
name|offsetHeight
condition|)
block|{
name|top
operator|-=
name|offsetHeight
expr_stmt|;
block|}
else|else
block|{
name|top
operator|+=
name|relativeObject
operator|.
name|getOffsetHeight
argument_list|()
expr_stmt|;
block|}
name|getInstance
argument_list|()
operator|.
name|setPopupPosition
argument_list|(
name|left
argument_list|,
name|top
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|int
name|left
init|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
operator|+
literal|10
decl_stmt|;
name|int
name|top
init|=
name|Window
operator|.
name|getScrollTop
argument_list|()
operator|+
name|Window
operator|.
name|getClientHeight
argument_list|()
operator|-
name|offsetHeight
operator|-
literal|10
decl_stmt|;
if|if
condition|(
name|relativeObject
operator|!=
literal|null
operator|&&
name|left
operator|+
name|offsetWidth
operator|>=
name|relativeObject
operator|.
name|getAbsoluteLeft
argument_list|()
operator|&&
name|top
operator|<=
name|relativeObject
operator|.
name|getAbsoluteBottom
argument_list|()
condition|)
name|left
operator|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
operator|+
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|-
name|offsetWidth
operator|-
literal|10
expr_stmt|;
name|getInstance
argument_list|()
operator|.
name|setPopupPosition
argument_list|(
name|left
argument_list|,
name|top
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|hideHint
parameter_list|()
block|{
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

