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
name|FilterPanel
extends|extends
name|P
block|{
name|P
name|iLeft
init|=
literal|null
decl_stmt|,
name|iMiddle
init|=
literal|null
decl_stmt|,
name|iRight
init|=
literal|null
decl_stmt|;
specifier|public
name|FilterPanel
parameter_list|()
block|{
name|super
argument_list|(
literal|"unitime-FilterPanel"
argument_list|)
expr_stmt|;
name|iLeft
operator|=
operator|new
name|P
argument_list|(
literal|"filter-left"
argument_list|)
expr_stmt|;
name|super
operator|.
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
literal|"filter-right"
argument_list|)
expr_stmt|;
name|super
operator|.
name|add
argument_list|(
name|iRight
argument_list|)
expr_stmt|;
name|iMiddle
operator|=
operator|new
name|P
argument_list|(
literal|"filter-middle"
argument_list|)
expr_stmt|;
name|super
operator|.
name|add
argument_list|(
name|iMiddle
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|add
parameter_list|(
name|Widget
name|w
parameter_list|)
block|{
name|w
operator|.
name|addStyleName
argument_list|(
literal|"filter-item"
argument_list|)
expr_stmt|;
name|super
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addMiddle
parameter_list|(
name|Widget
name|w
parameter_list|)
block|{
name|w
operator|.
name|addStyleName
argument_list|(
literal|"filter-item"
argument_list|)
expr_stmt|;
name|iMiddle
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addLeft
parameter_list|(
name|Widget
name|w
parameter_list|)
block|{
name|w
operator|.
name|addStyleName
argument_list|(
literal|"filter-item"
argument_list|)
expr_stmt|;
name|iLeft
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addRight
parameter_list|(
name|Widget
name|w
parameter_list|)
block|{
name|w
operator|.
name|addStyleName
argument_list|(
literal|"filter-item"
argument_list|)
expr_stmt|;
name|iRight
operator|.
name|add
argument_list|(
name|w
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

