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
name|page
package|;
end_package

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
name|page
operator|.
name|PageLabelDisplay
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
name|UniTimeFrameDialog
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
name|GwtMessages
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
name|shared
operator|.
name|MenuInterface
operator|.
name|PageNameInterface
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
name|googlecode
operator|.
name|mgwt
operator|.
name|dom
operator|.
name|client
operator|.
name|event
operator|.
name|tap
operator|.
name|TapEvent
import|;
end_import

begin_import
import|import
name|com
operator|.
name|googlecode
operator|.
name|mgwt
operator|.
name|dom
operator|.
name|client
operator|.
name|event
operator|.
name|tap
operator|.
name|TapHandler
import|;
end_import

begin_import
import|import
name|com
operator|.
name|googlecode
operator|.
name|mgwt
operator|.
name|ui
operator|.
name|client
operator|.
name|MGWT
import|;
end_import

begin_import
import|import
name|com
operator|.
name|googlecode
operator|.
name|mgwt
operator|.
name|ui
operator|.
name|client
operator|.
name|widget
operator|.
name|button
operator|.
name|ImageButton
import|;
end_import

begin_import
import|import
name|com
operator|.
name|googlecode
operator|.
name|mgwt
operator|.
name|ui
operator|.
name|client
operator|.
name|widget
operator|.
name|image
operator|.
name|ImageHolder
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MobilePageLabelImpl
extends|extends
name|P
implements|implements
name|PageLabelDisplay
block|{
specifier|private
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|P
name|iName
decl_stmt|;
specifier|private
name|ImageButton
name|iHelp
decl_stmt|;
specifier|private
name|ImageButton
name|iClose
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iUrl
init|=
literal|null
decl_stmt|;
specifier|public
name|MobilePageLabelImpl
parameter_list|()
block|{
name|iName
operator|=
operator|new
name|P
argument_list|(
literal|"text"
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|iName
argument_list|)
expr_stmt|;
name|iHelp
operator|=
operator|new
name|ImageButton
argument_list|(
name|ImageHolder
operator|.
name|get
argument_list|()
operator|.
name|about
argument_list|()
argument_list|)
expr_stmt|;
name|iHelp
operator|.
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|P
name|help
init|=
operator|new
name|P
argument_list|(
literal|"icon"
argument_list|)
decl_stmt|;
name|help
operator|.
name|add
argument_list|(
name|iHelp
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|iHelp
operator|.
name|addTapHandler
argument_list|(
operator|new
name|TapHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onTap
parameter_list|(
name|TapEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|iUrl
operator|==
literal|null
operator|||
name|iUrl
operator|.
name|isEmpty
argument_list|()
condition|)
return|return;
if|if
condition|(
name|MGWT
operator|.
name|getFormFactor
argument_list|()
operator|.
name|isTablet
argument_list|()
condition|)
name|UniTimeFrameDialog
operator|.
name|openDialog
argument_list|(
name|MESSAGES
operator|.
name|pageHelp
argument_list|(
name|getText
argument_list|()
argument_list|)
argument_list|,
name|iUrl
argument_list|)
expr_stmt|;
else|else
name|ToolBox
operator|.
name|open
argument_list|(
name|iUrl
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasParentWindow
argument_list|()
condition|)
block|{
name|iClose
operator|=
operator|new
name|ImageButton
argument_list|(
name|ImageHolder
operator|.
name|get
argument_list|()
operator|.
name|remove
argument_list|()
argument_list|)
expr_stmt|;
name|P
name|close
init|=
operator|new
name|P
argument_list|(
literal|"icon"
argument_list|)
decl_stmt|;
name|close
operator|.
name|add
argument_list|(
name|iClose
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|close
argument_list|)
expr_stmt|;
name|iClose
operator|.
name|addTapHandler
argument_list|(
operator|new
name|TapHandler
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onTap
parameter_list|(
name|TapEvent
name|event
parameter_list|)
block|{
name|tellParentToCloseThisWindo
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
specifier|native
name|boolean
name|hasParentWindow
parameter_list|()
comment|/*-{ 		return ($wnd.parent&& $wnd.parent.hasGwtDialog()); 	}-*/
function_decl|;
specifier|public
specifier|static
specifier|native
name|boolean
name|tellParentToCloseThisWindo
parameter_list|()
comment|/*-{ 		$wnd.parent.hideGwtDialog(); 	}-*/
function_decl|;
annotation|@
name|Override
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|iName
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
name|iName
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|iHelp
operator|.
name|setTitle
argument_list|(
name|MESSAGES
operator|.
name|pageHelp
argument_list|(
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|PageNameInterface
name|getValue
parameter_list|()
block|{
return|return
operator|new
name|PageNameInterface
argument_list|(
name|getText
argument_list|()
argument_list|,
name|iUrl
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValue
parameter_list|(
name|PageNameInterface
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
name|PageNameInterface
name|value
parameter_list|,
name|boolean
name|fireEvents
parameter_list|)
block|{
name|iUrl
operator|=
name|value
operator|.
name|getHelpUrl
argument_list|()
expr_stmt|;
name|iHelp
operator|.
name|setVisible
argument_list|(
name|iUrl
operator|!=
literal|null
operator|&&
operator|!
name|iUrl
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|setText
argument_list|(
name|value
operator|.
name|getName
argument_list|()
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
name|value
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|HandlerRegistration
name|addValueChangeHandler
parameter_list|(
name|ValueChangeHandler
argument_list|<
name|PageNameInterface
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
block|}
end_class

end_unit

