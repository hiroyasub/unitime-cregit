begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Date
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
name|GwtHint
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
name|AriaStatus
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
name|page
operator|.
name|UniTimeNotifications
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
name|dom
operator|.
name|client
operator|.
name|BodyElement
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
name|FrameElement
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
name|Overflow
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
name|LoadEvent
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
name|LoadHandler
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
name|CloseEvent
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
name|CloseHandler
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
name|Timer
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
name|Frame
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
name|RootPanel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UniTimeFrameDialogImpl
extends|extends
name|UniTimeDialogBox
implements|implements
name|UniTimeFrameDialogDisplay
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
name|Frame
name|iFrame
init|=
literal|null
decl_stmt|;
specifier|private
name|Timer
name|iCheckLoadingWidgetIsShowing
init|=
literal|null
decl_stmt|;
specifier|public
name|UniTimeFrameDialogImpl
parameter_list|()
block|{
name|super
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|setEscapeToHide
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|iFrame
operator|=
operator|new
name|Frame
argument_list|()
expr_stmt|;
name|iFrame
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setBorderWidth
argument_list|(
literal|0
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
name|hookFrameLoaded
argument_list|(
operator|(
name|FrameElement
operator|)
name|iFrame
operator|.
name|getElement
argument_list|()
operator|.
name|cast
argument_list|()
argument_list|)
expr_stmt|;
name|setWidget
argument_list|(
name|iFrame
argument_list|)
expr_stmt|;
name|iCheckLoadingWidgetIsShowing
operator|=
operator|new
name|Timer
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|isShowing
argument_list|()
condition|)
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
name|UniTimeNotifications
operator|.
name|error
argument_list|(
name|getText
argument_list|()
operator|+
literal|" does not seem to load, "
operator|+
literal|"please check<a href='"
operator|+
name|iFrame
operator|.
name|getUrl
argument_list|()
operator|+
literal|"' style='white-space: nowrap;'>"
operator|+
name|iFrame
operator|.
name|getUrl
argument_list|()
operator|+
literal|"</a> for yourself."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
expr_stmt|;
name|addCloseHandler
argument_list|(
operator|new
name|CloseHandler
argument_list|<
name|PopupPanel
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onClose
parameter_list|(
name|CloseEvent
argument_list|<
name|PopupPanel
argument_list|>
name|event
parameter_list|)
block|{
if|if
condition|(
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|isShowing
argument_list|()
condition|)
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
name|RootPanel
operator|.
name|getBodyElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setOverflow
argument_list|(
name|Overflow
operator|.
name|AUTO
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iFrame
operator|.
name|addLoadHandler
argument_list|(
operator|new
name|LoadHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onLoad
parameter_list|(
name|LoadEvent
name|event
parameter_list|)
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
try|try
block|{
name|FrameElement
name|frame
init|=
operator|(
name|FrameElement
operator|)
name|iFrame
operator|.
name|getElement
argument_list|()
operator|.
name|cast
argument_list|()
decl_stmt|;
name|BodyElement
name|body
init|=
name|frame
operator|.
name|getContentDocument
argument_list|()
operator|.
name|getBody
argument_list|()
decl_stmt|;
if|if
condition|(
name|body
operator|.
name|getScrollWidth
argument_list|()
operator|>
name|body
operator|.
name|getClientWidth
argument_list|()
condition|)
block|{
name|iFrame
operator|.
name|setWidth
argument_list|(
name|Math
operator|.
name|min
argument_list|(
name|frame
operator|.
name|getClientWidth
argument_list|()
operator|+
name|body
operator|.
name|getScrollWidth
argument_list|()
operator|-
name|body
operator|.
name|getClientWidth
argument_list|()
argument_list|,
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|*
literal|95
operator|/
literal|100
argument_list|)
operator|+
literal|"px"
argument_list|)
expr_stmt|;
name|setPopupPosition
argument_list|(
name|Math
operator|.
name|max
argument_list|(
name|Window
operator|.
name|getScrollLeft
argument_list|()
operator|+
operator|(
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|-
name|getOffsetWidth
argument_list|()
operator|)
operator|/
literal|2
argument_list|,
literal|0
argument_list|)
argument_list|,
name|Math
operator|.
name|max
argument_list|(
name|Window
operator|.
name|getScrollTop
argument_list|()
operator|+
operator|(
name|Window
operator|.
name|getClientHeight
argument_list|()
operator|-
name|getOffsetHeight
argument_list|()
operator|)
operator|/
literal|2
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setFrameSize
parameter_list|(
name|String
name|width
parameter_list|,
name|String
name|height
parameter_list|)
block|{
try|try
block|{
name|iFrame
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setWidth
argument_list|(
name|Double
operator|.
name|parseDouble
argument_list|(
name|width
argument_list|)
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|iFrame
operator|.
name|setWidth
argument_list|(
name|width
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|iFrame
operator|.
name|getElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setHeight
argument_list|(
name|Double
operator|.
name|parseDouble
argument_list|(
name|height
argument_list|)
argument_list|,
name|Unit
operator|.
name|PX
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|iFrame
operator|.
name|setHeight
argument_list|(
name|height
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|center
parameter_list|()
block|{
name|super
operator|.
name|center
argument_list|()
expr_stmt|;
name|iCheckLoadingWidgetIsShowing
operator|.
name|schedule
argument_list|(
literal|30000
argument_list|)
expr_stmt|;
name|RootPanel
operator|.
name|getBodyElement
argument_list|()
operator|.
name|getStyle
argument_list|()
operator|.
name|setOverflow
argument_list|(
name|Overflow
operator|.
name|HIDDEN
argument_list|)
expr_stmt|;
name|AriaStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|setText
argument_list|(
name|ARIA
operator|.
name|dialogOpened
argument_list|(
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|openDialog
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|source
parameter_list|,
name|String
name|width
parameter_list|,
name|String
name|height
parameter_list|)
block|{
if|if
condition|(
name|isShowing
argument_list|()
condition|)
name|hide
argument_list|()
expr_stmt|;
name|GwtHint
operator|.
name|hideHint
argument_list|()
expr_stmt|;
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|show
argument_list|(
literal|"Loading "
operator|+
name|title
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|title
argument_list|)
expr_stmt|;
name|String
name|hash
init|=
literal|null
decl_stmt|;
name|int
name|hashIdx
init|=
name|source
operator|.
name|lastIndexOf
argument_list|(
literal|'#'
argument_list|)
decl_stmt|;
if|if
condition|(
name|hashIdx
operator|>=
literal|0
condition|)
block|{
name|hash
operator|=
name|source
operator|.
name|substring
argument_list|(
name|hashIdx
argument_list|)
expr_stmt|;
name|source
operator|=
name|source
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|hashIdx
argument_list|)
expr_stmt|;
block|}
name|iFrame
operator|.
name|setUrl
argument_list|(
name|source
operator|+
operator|(
name|source
operator|.
name|indexOf
argument_list|(
literal|'?'
argument_list|)
operator|>=
literal|0
condition|?
literal|"&"
else|:
literal|"?"
operator|)
operator|+
literal|"noCacheTS="
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
operator|(
name|hash
operator|==
literal|null
condition|?
literal|""
else|:
name|hash
operator|)
argument_list|)
expr_stmt|;
name|String
name|w
init|=
operator|(
name|width
operator|==
literal|null
operator|||
name|width
operator|.
name|isEmpty
argument_list|()
condition|?
name|String
operator|.
name|valueOf
argument_list|(
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|*
literal|3
operator|/
literal|4
argument_list|)
else|:
name|width
operator|)
decl_stmt|;
name|String
name|h
init|=
operator|(
name|height
operator|==
literal|null
operator|||
name|height
operator|.
name|isEmpty
argument_list|()
condition|?
name|String
operator|.
name|valueOf
argument_list|(
name|Window
operator|.
name|getClientHeight
argument_list|()
operator|*
literal|3
operator|/
literal|4
argument_list|)
else|:
name|height
operator|)
decl_stmt|;
if|if
condition|(
name|w
operator|.
name|endsWith
argument_list|(
literal|"%"
argument_list|)
condition|)
name|w
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|w
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|w
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
operator|*
name|Window
operator|.
name|getClientWidth
argument_list|()
operator|/
literal|100
argument_list|)
expr_stmt|;
if|if
condition|(
name|h
operator|.
name|endsWith
argument_list|(
literal|"%"
argument_list|)
condition|)
name|h
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|h
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|h
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
operator|*
name|Window
operator|.
name|getClientHeight
argument_list|()
operator|/
literal|100
argument_list|)
expr_stmt|;
name|setFrameSize
argument_list|(
name|w
argument_list|,
name|h
argument_list|)
expr_stmt|;
name|center
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|hideDialog
parameter_list|()
block|{
name|hide
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|notifyFrameLoaded
parameter_list|()
block|{
name|LoadingWidget
operator|.
name|getInstance
argument_list|()
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|native
name|void
name|hookFrameLoaded
parameter_list|(
name|FrameElement
name|element
parameter_list|)
comment|/*-{ 		element.onload = function() { 			@org.unitime.timetable.gwt.client.widgets.UniTimeFrameDialogImpl::notifyFrameLoaded()(); 		} 		if (element.addEventListener) { 			element.addEventListener("load", function() { 				@org.unitime.timetable.gwt.client.widgets.UniTimeFrameDialogImpl::notifyFrameLoaded()(); 			}, false); 		} else if (element.attachEvent) { 			element.attachEvent("onload", function() { 				@org.unitime.timetable.gwt.client.widgets.UniTimeFrameDialogImpl::notifyFrameLoaded()(); 			}); 		} 	}-*/
function_decl|;
block|}
end_class

end_unit

