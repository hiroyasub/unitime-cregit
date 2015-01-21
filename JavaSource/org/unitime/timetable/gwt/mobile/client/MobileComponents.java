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
name|mobile
operator|.
name|client
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
name|mobile
operator|.
name|client
operator|.
name|page
operator|.
name|MobileMenu
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
name|mobile
operator|.
name|client
operator|.
name|page
operator|.
name|ReportFormFactor
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

begin_enum
specifier|public
enum|enum
name|MobileComponents
block|{
name|detectFormFactor
argument_list|(
literal|"UniTimeGWT:DetectFormFactor"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
name|ReportFormFactor
operator|.
name|report
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|,
name|menubar_mobile
argument_list|(
literal|"UniTimeGWT:MobileMenu"
argument_list|,
operator|new
name|ComponentFactory
argument_list|()
block|{
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
operator|new
name|MobileMenu
argument_list|()
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
block|, 	;
specifier|private
name|String
name|iId
decl_stmt|;
specifier|private
name|ComponentFactory
name|iFactory
decl_stmt|;
specifier|private
name|boolean
name|iMultiple
init|=
literal|false
decl_stmt|;
name|MobileComponents
parameter_list|(
name|String
name|id
parameter_list|,
name|ComponentFactory
name|factory
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iFactory
operator|=
name|factory
expr_stmt|;
block|}
name|MobileComponents
parameter_list|(
name|String
name|id
parameter_list|,
name|boolean
name|multiple
parameter_list|,
name|ComponentFactory
name|factory
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iFactory
operator|=
name|factory
expr_stmt|;
name|iMultiple
operator|=
name|multiple
expr_stmt|;
block|}
specifier|public
name|String
name|id
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
block|{
name|iFactory
operator|.
name|insert
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isMultiple
parameter_list|()
block|{
return|return
name|iMultiple
return|;
block|}
specifier|public
interface|interface
name|ComponentFactory
block|{
name|void
name|insert
parameter_list|(
name|RootPanel
name|panel
parameter_list|)
function_decl|;
block|}
block|}
end_enum

end_unit

