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
literal|"org/unitime/timetable/gwt/resources/icons/help_icon.gif"
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
block|}
end_interface

end_unit

