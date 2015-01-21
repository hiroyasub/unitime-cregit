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
name|HasAriaLabel
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
name|dom
operator|.
name|client
operator|.
name|Style
operator|.
name|VerticalAlign
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
implements|implements
name|HasAriaLabel
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
specifier|private
name|Element
name|iImageElement
init|=
literal|null
decl_stmt|;
specifier|private
name|SpanElement
name|iTextElement
init|=
literal|null
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
name|iImageElement
operator|!=
literal|null
condition|)
name|iAnchor
operator|.
name|removeChild
argument_list|(
name|iImageElement
argument_list|)
expr_stmt|;
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
name|iImageElement
operator|=
name|img
operator|.
name|getElement
argument_list|()
expr_stmt|;
name|iImageElement
operator|.
name|getStyle
argument_list|()
operator|.
name|setVerticalAlign
argument_list|(
name|VerticalAlign
operator|.
name|MIDDLE
argument_list|)
expr_stmt|;
name|iAnchor
operator|.
name|insertFirst
argument_list|(
name|iImageElement
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
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
if|if
condition|(
name|iTextElement
operator|==
literal|null
condition|)
block|{
name|iTextElement
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
operator|.
name|appendChild
argument_list|(
name|iTextElement
argument_list|)
expr_stmt|;
block|}
name|iTextElement
operator|.
name|setInnerText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
operator|(
name|iTextElement
operator|==
literal|null
condition|?
literal|null
else|:
name|iTextElement
operator|.
name|getInnerText
argument_list|()
operator|)
return|;
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
name|iAnchor
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
name|iElement
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
name|iElement
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

