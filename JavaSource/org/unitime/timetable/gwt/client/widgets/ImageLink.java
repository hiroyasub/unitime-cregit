begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|AnchorElement
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
name|SpanElement
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
name|ImageLink
extends|extends
name|Widget
block|{
specifier|private
name|Image
name|iImage
decl_stmt|;
specifier|private
name|String
name|iUrl
decl_stmt|;
specifier|private
name|String
name|iTarget
decl_stmt|;
specifier|private
name|SpanElement
name|iElement
decl_stmt|;
specifier|private
name|AnchorElement
name|iAnchor
decl_stmt|;
specifier|public
name|ImageLink
parameter_list|(
name|Image
name|img
parameter_list|,
name|String
name|url
parameter_list|)
block|{
name|initElements
argument_list|()
expr_stmt|;
name|setImage
argument_list|(
name|img
argument_list|)
expr_stmt|;
name|setUrl
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ImageLink
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|initElements
parameter_list|()
block|{
name|iElement
operator|=
name|Document
operator|.
name|get
argument_list|()
operator|.
name|createSpanElement
argument_list|()
expr_stmt|;
name|iAnchor
operator|=
name|Document
operator|.
name|get
argument_list|()
operator|.
name|createAnchorElement
argument_list|()
expr_stmt|;
name|iElement
operator|.
name|appendChild
argument_list|(
name|iAnchor
argument_list|)
expr_stmt|;
name|setElement
argument_list|(
name|iElement
argument_list|)
expr_stmt|;
name|sinkEvents
argument_list|(
name|Event
operator|.
name|MOUSEEVENTS
argument_list|)
expr_stmt|;
name|setTarget
argument_list|(
literal|"_blank"
argument_list|)
expr_stmt|;
block|}
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
name|event
operator|.
name|getTypeInt
argument_list|()
operator|==
name|Event
operator|.
name|ONMOUSEOVER
condition|)
block|{
name|iAnchor
operator|.
name|getStyle
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"cursor"
argument_list|,
literal|"hand"
argument_list|)
expr_stmt|;
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
name|Image
name|getImage
parameter_list|()
block|{
return|return
name|iImage
return|;
block|}
specifier|public
name|void
name|setImage
parameter_list|(
name|Image
name|img
parameter_list|)
block|{
if|if
condition|(
name|img
operator|==
literal|null
condition|)
return|return;
name|iImage
operator|=
name|img
expr_stmt|;
name|iAnchor
operator|.
name|appendChild
argument_list|(
name|img
operator|.
name|getElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getUrl
parameter_list|()
block|{
return|return
name|iUrl
return|;
block|}
specifier|public
name|void
name|setUrl
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|iUrl
operator|=
name|url
expr_stmt|;
name|iAnchor
operator|.
name|setHref
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getTarget
parameter_list|()
block|{
return|return
name|iTarget
return|;
block|}
specifier|public
name|void
name|setTarget
parameter_list|(
name|String
name|target
parameter_list|)
block|{
name|iTarget
operator|=
name|target
expr_stmt|;
name|iAnchor
operator|.
name|setTarget
argument_list|(
name|target
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

