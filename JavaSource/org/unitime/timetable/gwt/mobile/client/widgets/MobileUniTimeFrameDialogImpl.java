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
name|mobile
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
name|client
operator|.
name|widgets
operator|.
name|LoadingWidget
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
name|UniTimeFrameDialogDisplay
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
name|user
operator|.
name|client
operator|.
name|History
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
name|MobileUniTimeFrameDialogImpl
implements|implements
name|UniTimeFrameDialogDisplay
block|{
specifier|private
name|PopupPanel
name|iPopup
decl_stmt|;
specifier|private
name|Frame
name|iFrame
decl_stmt|;
specifier|private
name|Timer
name|iCheckLoadingWidgetIsShowing
decl_stmt|;
specifier|private
name|String
name|iText
decl_stmt|;
specifier|private
name|int
name|iScrollTop
init|=
literal|0
decl_stmt|,
name|iScrollLeft
init|=
literal|0
decl_stmt|;
specifier|public
name|MobileUniTimeFrameDialogImpl
parameter_list|()
block|{
name|iPopup
operator|=
operator|new
name|PopupPanel
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|iPopup
operator|.
name|addStyleName
argument_list|(
literal|"unitime-MobileFrameDialog"
argument_list|)
expr_stmt|;
name|iPopup
operator|.
name|setGlassEnabled
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
name|setStyleName
argument_list|(
literal|"frame"
argument_list|)
expr_stmt|;
name|iPopup
operator|.
name|add
argument_list|(
name|iFrame
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
block|}
block|}
argument_list|)
expr_stmt|;
name|History
operator|.
name|addValueChangeHandler
argument_list|(
operator|new
name|ValueChangeHandler
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
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
if|if
condition|(
operator|!
name|event
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|getText
argument_list|()
argument_list|)
condition|)
name|hideDialog
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|iPopup
operator|.
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
name|Window
operator|.
name|scrollTo
argument_list|(
name|iScrollLeft
argument_list|,
name|iScrollTop
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
name|AUTO
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
name|hideDialog
argument_list|()
expr_stmt|;
name|GwtHint
operator|.
name|hideHint
argument_list|()
expr_stmt|;
name|iScrollLeft
operator|=
name|Window
operator|.
name|getScrollLeft
argument_list|()
expr_stmt|;
name|iScrollTop
operator|=
name|Window
operator|.
name|getScrollTop
argument_list|()
expr_stmt|;
name|Window
operator|.
name|scrollTo
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
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
name|iCheckLoadingWidgetIsShowing
operator|.
name|schedule
argument_list|(
literal|30000
argument_list|)
expr_stmt|;
name|History
operator|.
name|newItem
argument_list|(
name|title
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iPopup
operator|.
name|setPopupPosition
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|iPopup
operator|.
name|show
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
name|HIDDEN
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|hideDialog
parameter_list|()
block|{
if|if
condition|(
name|iPopup
operator|.
name|isShowing
argument_list|()
condition|)
name|iPopup
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isShowing
parameter_list|()
block|{
return|return
name|iPopup
operator|.
name|isShowing
argument_list|()
return|;
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
operator|=
name|text
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
comment|/*-{ 		element.onload = function() { 			@org.unitime.timetable.gwt.mobile.client.widgets.MobileUniTimeFrameDialogImpl::notifyFrameLoaded()(); 		} 		if (element.addEventListener) { 			element.addEventListener("load", function() { 				@org.unitime.timetable.gwt.mobile.client.widgets.MobileUniTimeFrameDialogImpl::notifyFrameLoaded()(); 			}, false); 		} else if (element.attachEvent) { 			element.attachEvent("onload", function() { 				@org.unitime.timetable.gwt.mobile.client.widgets.MobileUniTimeFrameDialogImpl::notifyFrameLoaded()(); 			}); 		} 	}-*/
function_decl|;
block|}
end_class

end_unit

