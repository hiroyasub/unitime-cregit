begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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

