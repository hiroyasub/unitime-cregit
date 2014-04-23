begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|rooms
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
name|GwtHint
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
name|SimpleForm
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
name|command
operator|.
name|client
operator|.
name|GwtRpcService
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
name|command
operator|.
name|client
operator|.
name|GwtRpcServiceAsync
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
name|RoomInterface
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
name|core
operator|.
name|client
operator|.
name|JavaScriptObject
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
name|user
operator|.
name|client
operator|.
name|rpc
operator|.
name|AsyncCallback
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
name|HTML
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
name|Label
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
name|SimplePanel
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
name|RoomHint
block|{
specifier|private
specifier|static
name|long
name|sLastLocationId
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
specifier|static
name|GwtRpcServiceAsync
name|RPC
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtRpcService
operator|.
name|class
argument_list|)
decl_stmt|;
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
specifier|static
name|boolean
name|sShowHint
init|=
literal|false
decl_stmt|;
specifier|public
specifier|static
name|Widget
name|content
parameter_list|(
name|RoomInterface
operator|.
name|RoomHintResponse
name|room
parameter_list|,
name|String
name|prefix
parameter_list|,
name|String
name|distance
parameter_list|)
block|{
name|SimpleForm
name|form
init|=
operator|new
name|SimpleForm
argument_list|()
decl_stmt|;
name|form
operator|.
name|removeStyleName
argument_list|(
literal|"unitime-NotPrintableBottomLine"
argument_list|)
expr_stmt|;
name|form
operator|.
name|addRow
argument_list|(
operator|new
name|Label
argument_list|(
operator|(
name|prefix
operator|==
literal|null
operator|||
name|prefix
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|prefix
operator|+
literal|" "
operator|)
operator|+
operator|(
name|room
operator|.
name|hasDisplayName
argument_list|()
operator|||
name|room
operator|.
name|hasRoomTypeLabel
argument_list|()
condition|?
name|MESSAGES
operator|.
name|label
argument_list|(
name|room
operator|.
name|getLabel
argument_list|()
argument_list|,
name|room
operator|.
name|hasDisplayName
argument_list|()
condition|?
name|room
operator|.
name|getDisplayName
argument_list|()
else|:
name|room
operator|.
name|getRoomTypeLabel
argument_list|()
argument_list|)
else|:
name|room
operator|.
name|getLabel
argument_list|()
operator|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|hasMiniMapUrl
argument_list|()
condition|)
block|{
name|Image
name|image
init|=
operator|new
name|Image
argument_list|(
name|room
operator|.
name|getMiniMapUrl
argument_list|()
argument_list|)
decl_stmt|;
name|image
operator|.
name|setStyleName
argument_list|(
literal|"minimap"
argument_list|)
expr_stmt|;
name|form
operator|.
name|addRow
argument_list|(
name|image
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|room
operator|.
name|hasCapacity
argument_list|()
condition|)
block|{
if|if
condition|(
name|room
operator|.
name|hasExamCapacity
argument_list|()
condition|)
block|{
if|if
condition|(
name|room
operator|.
name|hasExamType
argument_list|()
condition|)
block|{
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomCapacity
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|capacityWithExamType
argument_list|(
name|room
operator|.
name|getCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|room
operator|.
name|getExamCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|room
operator|.
name|getExamType
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomCapacity
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|capacityWithExam
argument_list|(
name|room
operator|.
name|getCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|room
operator|.
name|getExamCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomCapacity
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|capacity
argument_list|(
name|room
operator|.
name|getCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|room
operator|.
name|hasArea
argument_list|()
condition|)
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomArea
argument_list|()
argument_list|,
operator|new
name|HTML
argument_list|(
name|room
operator|.
name|getArea
argument_list|()
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|hasFeatures
argument_list|()
condition|)
for|for
control|(
name|String
name|name
range|:
name|room
operator|.
name|getFeatureNames
argument_list|()
control|)
name|form
operator|.
name|addRow
argument_list|(
name|name
operator|+
literal|":"
argument_list|,
operator|new
name|Label
argument_list|(
name|room
operator|.
name|getFeatures
argument_list|(
name|name
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|hasGroups
argument_list|()
condition|)
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomGroups
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|room
operator|.
name|getGroups
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|hasEventStatus
argument_list|()
condition|)
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomEventStatus
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|room
operator|.
name|getEventStatus
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|hasEventDepartment
argument_list|()
condition|)
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomEventDepartment
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|room
operator|.
name|getEventDepartment
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|hasBreakTime
argument_list|()
condition|)
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomBreakTime
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|breakTime
argument_list|(
name|room
operator|.
name|getBreakTime
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|hasNote
argument_list|()
condition|)
name|form
operator|.
name|addRow
argument_list|(
operator|new
name|HTML
argument_list|(
name|room
operator|.
name|getNote
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|room
operator|.
name|isIgnoreRoomCheck
argument_list|()
condition|)
name|form
operator|.
name|addRow
argument_list|(
operator|new
name|HTML
argument_list|(
name|MESSAGES
operator|.
name|ignoreRoomCheck
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|distance
operator|!=
literal|null
operator|&&
operator|!
name|distance
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
literal|"0"
operator|.
name|equals
argument_list|(
name|distance
argument_list|)
condition|)
name|form
operator|.
name|addRow
argument_list|(
name|MESSAGES
operator|.
name|propRoomDistance
argument_list|()
argument_list|,
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|roomDistance
argument_list|(
name|distance
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|SimplePanel
name|panel
init|=
operator|new
name|SimplePanel
argument_list|(
name|form
argument_list|)
decl_stmt|;
name|panel
operator|.
name|setStyleName
argument_list|(
literal|"unitime-RoomHint"
argument_list|)
expr_stmt|;
return|return
name|panel
return|;
block|}
comment|/** Never use from GWT code */
specifier|public
specifier|static
name|void
name|_showRoomHint
parameter_list|(
name|JavaScriptObject
name|source
parameter_list|,
name|String
name|locationId
parameter_list|,
name|String
name|prefix
parameter_list|,
name|String
name|distance
parameter_list|)
block|{
name|showHint
argument_list|(
operator|(
name|Element
operator|)
name|source
operator|.
name|cast
argument_list|()
argument_list|,
name|Long
operator|.
name|valueOf
argument_list|(
name|locationId
argument_list|)
argument_list|,
name|prefix
argument_list|,
name|distance
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|showHint
parameter_list|(
specifier|final
name|Element
name|relativeObject
parameter_list|,
specifier|final
name|long
name|locationId
parameter_list|,
specifier|final
name|String
name|prefix
parameter_list|,
specifier|final
name|String
name|distance
parameter_list|,
specifier|final
name|boolean
name|showRelativeToTheObject
parameter_list|)
block|{
name|sLastLocationId
operator|=
name|locationId
expr_stmt|;
name|sShowHint
operator|=
literal|true
expr_stmt|;
name|RPC
operator|.
name|execute
argument_list|(
name|RoomInterface
operator|.
name|RoomHintRequest
operator|.
name|load
argument_list|(
name|locationId
argument_list|)
argument_list|,
operator|new
name|AsyncCallback
argument_list|<
name|RoomInterface
operator|.
name|RoomHintResponse
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|onFailure
parameter_list|(
name|Throwable
name|caught
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|void
name|onSuccess
parameter_list|(
name|RoomInterface
operator|.
name|RoomHintResponse
name|result
parameter_list|)
block|{
if|if
condition|(
name|result
operator|!=
literal|null
operator|&&
name|locationId
operator|==
name|sLastLocationId
operator|&&
name|sShowHint
condition|)
name|GwtHint
operator|.
name|showHint
argument_list|(
name|relativeObject
argument_list|,
name|content
argument_list|(
name|result
argument_list|,
name|prefix
argument_list|,
name|distance
argument_list|)
argument_list|,
name|showRelativeToTheObject
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|hideHint
parameter_list|()
block|{
name|sShowHint
operator|=
literal|false
expr_stmt|;
name|GwtHint
operator|.
name|hideHint
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|native
name|void
name|createTriggers
parameter_list|()
comment|/*-{ 	$wnd.showGwtRoomHint = function(source, content, prefix, distance) { 		@org.unitime.timetable.gwt.client.rooms.RoomHint::_showRoomHint(Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(source, content, prefix, distance); 	}; 	$wnd.hideGwtRoomHint = function() { 		@org.unitime.timetable.gwt.client.rooms.RoomHint::hideHint()(); 	}; 	}-*/
function_decl|;
block|}
end_class

end_unit

