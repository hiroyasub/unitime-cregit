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
name|shared
operator|.
name|MenuInterface
operator|.
name|InfoInterface
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
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|Composite
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InfoPanel
extends|extends
name|Composite
implements|implements
name|InfoPanelDisplay
block|{
specifier|private
name|InfoPanelDisplay
name|IMPL
init|=
name|GWT
operator|.
name|create
argument_list|(
name|InfoPanelDisplay
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|boolean
name|iPreventDefault
init|=
literal|false
decl_stmt|;
specifier|public
name|InfoPanel
parameter_list|()
block|{
name|initWidget
argument_list|(
name|IMPL
operator|.
name|asWidget
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
name|IMPL
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
name|IMPL
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
name|String
name|getHint
parameter_list|()
block|{
return|return
name|IMPL
operator|.
name|getHint
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setHint
parameter_list|(
name|String
name|hint
parameter_list|)
block|{
name|IMPL
operator|.
name|setHint
argument_list|(
name|hint
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setUrl
parameter_list|(
name|String
name|url
parameter_list|)
block|{
name|IMPL
operator|.
name|setUrl
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setInfo
parameter_list|(
name|InfoInterface
name|info
parameter_list|)
block|{
name|IMPL
operator|.
name|setInfo
argument_list|(
name|info
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setCallback
parameter_list|(
name|Callback
name|callback
parameter_list|)
block|{
name|IMPL
operator|.
name|setCallback
argument_list|(
name|callback
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isPopupShowing
parameter_list|()
block|{
return|return
name|IMPL
operator|.
name|isPopupShowing
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setClickHandler
parameter_list|(
name|ClickHandler
name|handler
parameter_list|)
block|{
name|IMPL
operator|.
name|setClickHandler
argument_list|(
name|handler
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getAriaLabel
parameter_list|()
block|{
return|return
name|IMPL
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
name|IMPL
operator|.
name|setAriaLabel
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isPreventDefault
parameter_list|()
block|{
return|return
name|iPreventDefault
return|;
block|}
specifier|public
name|void
name|setPreventDefault
parameter_list|(
name|boolean
name|preventDefault
parameter_list|)
block|{
name|iPreventDefault
operator|=
name|preventDefault
expr_stmt|;
block|}
block|}
end_class

end_unit

