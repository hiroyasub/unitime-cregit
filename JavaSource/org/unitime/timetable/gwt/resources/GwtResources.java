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
name|resources
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
name|resources
operator|.
name|client
operator|.
name|ClientBundle
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
name|resources
operator|.
name|client
operator|.
name|ImageResource
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|GwtResources
extends|extends
name|ClientBundle
extends|,
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
name|Tree
operator|.
name|Resources
block|{
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/loading.gif"
argument_list|)
name|ImageResource
name|loading
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/loading_small.gif"
argument_list|)
name|ImageResource
name|loading_small
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/help.png"
argument_list|)
name|ImageResource
name|help
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/expand_node_btn.gif"
argument_list|)
name|ImageResource
name|treeClosed
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/collapse_node_btn.gif"
argument_list|)
name|ImageResource
name|treeOpen
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/end_node_btn.gif"
argument_list|)
name|ImageResource
name|treeLeaf
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/minimize.gif"
argument_list|)
name|ImageResource
name|menu_opened
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/minimize_RO.gif"
argument_list|)
name|ImageResource
name|menu_opened_hover
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/openMenu.gif"
argument_list|)
name|ImageResource
name|menu_closed
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/openMenu_RO.gif"
argument_list|)
name|ImageResource
name|menu_closed_hover
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_add.png"
argument_list|)
name|ImageResource
name|add
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_delete.png"
argument_list|)
name|ImageResource
name|delete
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/accept.png"
argument_list|)
name|ImageResource
name|on
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/cross.png"
argument_list|)
name|ImageResource
name|off
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/application_edit.png"
argument_list|)
name|ImageResource
name|edit
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/date.png"
argument_list|)
name|ImageResource
name|calendar
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/download.png"
argument_list|)
name|ImageResource
name|download
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/times.png"
argument_list|)
name|ImageResource
name|filter_clear
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/dropdown_close.png"
argument_list|)
name|ImageResource
name|filter_close
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/dropdown_open.png"
argument_list|)
name|ImageResource
name|filter_open
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/white_star.png"
argument_list|)
name|ImageResource
name|star
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/black_star.png"
argument_list|)
name|ImageResource
name|starSelected
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/confirm.png"
argument_list|)
name|ImageResource
name|confirm
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/alert.png"
argument_list|)
name|ImageResource
name|alert
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/warning.png"
argument_list|)
name|ImageResource
name|warning
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/help_small.png"
argument_list|)
name|ImageResource
name|helpIcon
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/close.png"
argument_list|)
name|ImageResource
name|close
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/info.png"
argument_list|)
name|ImageResource
name|info
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/finder.png"
argument_list|)
name|ImageResource
name|finder
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/cancel.png"
argument_list|)
name|ImageResource
name|cancel
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

