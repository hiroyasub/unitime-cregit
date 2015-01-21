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
name|PageHeader
extends|extends
name|Composite
implements|implements
name|PageHeaderDisplay
block|{
specifier|private
name|PageHeaderDisplay
name|IMPL
init|=
name|GWT
operator|.
name|create
argument_list|(
name|PageHeaderDisplay
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|PageHeader
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
name|InfoPanel
name|getLeft
parameter_list|()
block|{
return|return
name|IMPL
operator|.
name|getLeft
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InfoPanel
name|getMiddle
parameter_list|()
block|{
return|return
name|IMPL
operator|.
name|getMiddle
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|InfoPanel
name|getRight
parameter_list|()
block|{
return|return
name|IMPL
operator|.
name|getRight
argument_list|()
return|;
block|}
block|}
end_class

end_unit

