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
name|rooms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|rooms
operator|.
name|RoomsTable
operator|.
name|DepartmentCell
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
name|rooms
operator|.
name|RoomsTable
operator|.
name|HasRefresh
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
name|rooms
operator|.
name|RoomsTable
operator|.
name|SortOperation
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
name|P
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
name|UniTimeTable
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
name|UniTimeTableHeader
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
name|UniTimeTableHeader
operator|.
name|Operation
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
name|resources
operator|.
name|GwtResources
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
name|EventInterface
operator|.
name|FilterRpcResponse
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
operator|.
name|FeatureInterface
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
operator|.
name|RoomFeaturesColumn
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
name|user
operator|.
name|client
operator|.
name|ui
operator|.
name|HasHorizontalAlignment
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
name|Widget
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
name|HasHorizontalAlignment
operator|.
name|HorizontalAlignmentConstant
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomFeaturesTable
extends|extends
name|UniTimeTable
argument_list|<
name|FeatureInterface
argument_list|>
block|{
specifier|protected
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
specifier|protected
specifier|static
specifier|final
name|GwtResources
name|RESOURCES
init|=
name|GWT
operator|.
name|create
argument_list|(
name|GwtResources
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|boolean
name|iGlobal
decl_stmt|;
specifier|private
name|RoomFeaturesColumn
name|iSortBy
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAsc
init|=
literal|true
decl_stmt|;
specifier|public
name|RoomFeaturesTable
parameter_list|(
name|boolean
name|isGlobal
parameter_list|)
block|{
name|setStyleName
argument_list|(
literal|"unitime-RoomFeatures"
argument_list|)
expr_stmt|;
name|iGlobal
operator|=
name|isGlobal
expr_stmt|;
name|List
argument_list|<
name|UniTimeTableHeader
argument_list|>
name|header
init|=
operator|new
name|ArrayList
argument_list|<
name|UniTimeTableHeader
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RoomFeaturesColumn
name|column
range|:
name|RoomFeaturesColumn
operator|.
name|values
argument_list|()
control|)
block|{
name|int
name|nrCells
init|=
name|getNbrCells
argument_list|(
name|column
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|nrCells
condition|;
name|idx
operator|++
control|)
block|{
name|UniTimeTableHeader
name|h
init|=
operator|new
name|UniTimeTableHeader
argument_list|(
name|getColumnName
argument_list|(
name|column
argument_list|,
name|idx
argument_list|)
argument_list|,
name|getColumnAlignment
argument_list|(
name|column
argument_list|,
name|idx
argument_list|)
argument_list|)
decl_stmt|;
name|header
operator|.
name|add
argument_list|(
name|h
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
specifier|final
name|RoomFeaturesColumn
name|column
range|:
name|RoomFeaturesColumn
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|RoomFeaturesComparator
operator|.
name|isApplicable
argument_list|(
name|column
argument_list|)
operator|&&
name|getNbrCells
argument_list|(
name|column
argument_list|)
operator|>
literal|0
condition|)
block|{
specifier|final
name|UniTimeTableHeader
name|h
init|=
name|header
operator|.
name|get
argument_list|(
name|getCellIndex
argument_list|(
name|column
argument_list|)
argument_list|)
decl_stmt|;
name|Operation
name|op
init|=
operator|new
name|SortOperation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|doSort
argument_list|(
name|column
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isApplicable
parameter_list|()
block|{
return|return
name|getRowCount
argument_list|()
operator|>
literal|1
operator|&&
name|h
operator|.
name|isVisible
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasSeparator
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|MESSAGES
operator|.
name|opSortBy
argument_list|(
name|getColumnName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getColumnName
parameter_list|()
block|{
return|return
name|h
operator|.
name|getHTML
argument_list|()
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|" "
argument_list|)
return|;
block|}
block|}
decl_stmt|;
name|h
operator|.
name|addOperation
argument_list|(
name|op
argument_list|)
expr_stmt|;
block|}
block|}
name|addRow
argument_list|(
literal|null
argument_list|,
name|header
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|getCellCount
argument_list|(
literal|0
argument_list|)
condition|;
name|i
operator|++
control|)
name|getCellFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
literal|0
argument_list|,
name|i
argument_list|,
literal|"unitime-ClickableTableHeader"
argument_list|)
expr_stmt|;
name|setSortBy
argument_list|(
name|RoomCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getRoomGroupsSortBy
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|doSort
parameter_list|(
name|RoomFeaturesColumn
name|column
parameter_list|)
block|{
if|if
condition|(
name|column
operator|==
name|iSortBy
condition|)
block|{
name|iAsc
operator|=
operator|!
name|iAsc
expr_stmt|;
block|}
else|else
block|{
name|iSortBy
operator|=
name|column
expr_stmt|;
name|iAsc
operator|=
literal|true
expr_stmt|;
block|}
name|RoomCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setSortRoomGroupsBy
argument_list|(
name|getSortBy
argument_list|()
argument_list|)
expr_stmt|;
name|sort
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasSortBy
parameter_list|()
block|{
return|return
name|iSortBy
operator|!=
literal|null
return|;
block|}
specifier|public
name|int
name|getSortBy
parameter_list|()
block|{
return|return
name|iSortBy
operator|==
literal|null
condition|?
literal|0
else|:
name|iAsc
condition|?
literal|1
operator|+
name|iSortBy
operator|.
name|ordinal
argument_list|()
else|:
operator|-
literal|1
operator|-
name|iSortBy
operator|.
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|void
name|setSortBy
parameter_list|(
name|int
name|sortBy
parameter_list|)
block|{
if|if
condition|(
name|sortBy
operator|==
literal|0
condition|)
block|{
name|iSortBy
operator|=
literal|null
expr_stmt|;
name|iAsc
operator|=
literal|true
expr_stmt|;
block|}
if|else if
condition|(
name|sortBy
operator|>
literal|0
condition|)
block|{
name|iSortBy
operator|=
name|RoomFeaturesColumn
operator|.
name|values
argument_list|()
index|[
name|sortBy
operator|-
literal|1
index|]
expr_stmt|;
name|iAsc
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|iSortBy
operator|=
name|RoomFeaturesColumn
operator|.
name|values
argument_list|()
index|[
operator|-
literal|1
operator|-
name|sortBy
index|]
expr_stmt|;
name|iAsc
operator|=
literal|false
expr_stmt|;
block|}
name|sort
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|sort
parameter_list|()
block|{
if|if
condition|(
name|iSortBy
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|getNbrCells
argument_list|(
name|iSortBy
argument_list|)
operator|==
literal|0
condition|)
name|iSortBy
operator|=
name|RoomFeaturesColumn
operator|.
name|NAME
expr_stmt|;
name|UniTimeTableHeader
name|header
init|=
name|getHeader
argument_list|(
name|getCellIndex
argument_list|(
name|iSortBy
argument_list|)
argument_list|)
decl_stmt|;
name|sort
argument_list|(
name|header
argument_list|,
operator|new
name|RoomFeaturesComparator
argument_list|(
name|iSortBy
argument_list|,
literal|true
argument_list|)
argument_list|,
name|iAsc
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|int
name|getNbrCells
parameter_list|(
name|RoomFeaturesColumn
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|DEPARTMENT
case|:
return|return
name|iGlobal
condition|?
literal|0
else|:
literal|1
return|;
default|default:
return|return
literal|1
return|;
block|}
block|}
specifier|public
name|String
name|getColumnName
parameter_list|(
name|RoomFeaturesColumn
name|column
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|NAME
case|:
return|return
name|MESSAGES
operator|.
name|colName
argument_list|()
return|;
case|case
name|ABBREVIATION
case|:
return|return
name|MESSAGES
operator|.
name|colAbbreviation
argument_list|()
return|;
case|case
name|TYPE
case|:
return|return
name|MESSAGES
operator|.
name|colType
argument_list|()
return|;
case|case
name|DEPARTMENT
case|:
return|return
name|MESSAGES
operator|.
name|colDepartment
argument_list|()
return|;
case|case
name|ROOMS
case|:
return|return
name|MESSAGES
operator|.
name|colRooms
argument_list|()
return|;
default|default:
return|return
name|column
operator|.
name|name
argument_list|()
return|;
block|}
block|}
specifier|protected
name|HorizontalAlignmentConstant
name|getColumnAlignment
parameter_list|(
name|RoomFeaturesColumn
name|column
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
default|default:
return|return
name|HasHorizontalAlignment
operator|.
name|ALIGN_LEFT
return|;
block|}
block|}
specifier|protected
name|int
name|getCellIndex
parameter_list|(
name|RoomFeaturesColumn
name|column
parameter_list|)
block|{
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|RoomFeaturesColumn
name|c
range|:
name|RoomFeaturesColumn
operator|.
name|values
argument_list|()
control|)
if|if
condition|(
name|c
operator|.
name|ordinal
argument_list|()
operator|<
name|column
operator|.
name|ordinal
argument_list|()
condition|)
name|ret
operator|+=
name|getNbrCells
argument_list|(
name|c
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|protected
name|Widget
name|getCell
parameter_list|(
specifier|final
name|FeatureInterface
name|feature
parameter_list|,
specifier|final
name|RoomFeaturesColumn
name|column
parameter_list|,
specifier|final
name|int
name|idx
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|NAME
case|:
return|return
operator|new
name|Label
argument_list|(
name|feature
operator|.
name|getLabel
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|feature
operator|.
name|getLabel
argument_list|()
argument_list|)
return|;
case|case
name|ABBREVIATION
case|:
return|return
operator|new
name|Label
argument_list|(
name|feature
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|feature
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
case|case
name|TYPE
case|:
if|if
condition|(
name|feature
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
else|else
block|{
name|Label
name|type
init|=
operator|new
name|Label
argument_list|(
name|feature
operator|.
name|getType
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
decl_stmt|;
name|type
operator|.
name|setTitle
argument_list|(
name|feature
operator|.
name|getType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|type
return|;
block|}
case|case
name|DEPARTMENT
case|:
return|return
operator|new
name|DepartmentCell
argument_list|(
name|feature
operator|.
name|getDepartment
argument_list|()
argument_list|)
return|;
case|case
name|ROOMS
case|:
if|if
condition|(
name|feature
operator|.
name|hasRooms
argument_list|()
condition|)
return|return
operator|new
name|RoomsCell
argument_list|(
name|feature
argument_list|)
return|;
else|else
return|return
literal|null
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
specifier|public
name|int
name|addFeature
parameter_list|(
specifier|final
name|FeatureInterface
name|feature
parameter_list|)
block|{
name|List
argument_list|<
name|Widget
argument_list|>
name|widgets
init|=
operator|new
name|ArrayList
argument_list|<
name|Widget
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RoomFeaturesColumn
name|column
range|:
name|RoomFeaturesColumn
operator|.
name|values
argument_list|()
control|)
block|{
name|int
name|nbrCells
init|=
name|getNbrCells
argument_list|(
name|column
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|nbrCells
condition|;
name|idx
operator|++
control|)
block|{
name|Widget
name|cell
init|=
name|getCell
argument_list|(
name|feature
argument_list|,
name|column
argument_list|,
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|cell
operator|==
literal|null
condition|)
name|cell
operator|=
operator|new
name|P
argument_list|()
expr_stmt|;
name|widgets
operator|.
name|add
argument_list|(
name|cell
argument_list|)
expr_stmt|;
block|}
block|}
name|int
name|row
init|=
name|addRow
argument_list|(
name|feature
argument_list|,
name|widgets
argument_list|)
decl_stmt|;
name|getRowFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|row
argument_list|,
literal|"row"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|col
init|=
literal|0
init|;
name|col
operator|<
name|getCellCount
argument_list|(
name|row
argument_list|)
condition|;
name|col
operator|++
control|)
name|getCellFormatter
argument_list|()
operator|.
name|setStyleName
argument_list|(
name|row
argument_list|,
name|col
argument_list|,
literal|"cell"
argument_list|)
expr_stmt|;
return|return
name|row
return|;
block|}
specifier|public
specifier|static
class|class
name|RoomsCell
extends|extends
name|P
block|{
specifier|public
name|RoomsCell
parameter_list|(
name|FeatureInterface
name|feature
parameter_list|)
block|{
name|super
argument_list|(
literal|"rooms"
argument_list|)
expr_stmt|;
if|if
condition|(
name|feature
operator|.
name|hasRooms
argument_list|()
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|FilterRpcResponse
operator|.
name|Entity
argument_list|>
name|i
init|=
name|feature
operator|.
name|getRooms
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|FilterRpcResponse
operator|.
name|Entity
name|room
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|add
argument_list|(
operator|new
name|RoomGroupsTable
operator|.
name|RoomCell
argument_list|(
name|room
argument_list|,
name|i
operator|.
name|hasNext
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|void
name|refreshTable
parameter_list|()
block|{
for|for
control|(
name|int
name|r
init|=
literal|1
init|;
name|r
operator|<
name|getRowCount
argument_list|()
condition|;
name|r
operator|++
control|)
block|{
for|for
control|(
name|int
name|c
init|=
literal|0
init|;
name|c
operator|<
name|getCellCount
argument_list|(
name|r
argument_list|)
condition|;
name|c
operator|++
control|)
block|{
name|Widget
name|w
init|=
name|getWidget
argument_list|(
name|r
argument_list|,
name|c
argument_list|)
decl_stmt|;
if|if
condition|(
name|w
operator|instanceof
name|HasRefresh
condition|)
operator|(
operator|(
name|HasRefresh
operator|)
name|w
operator|)
operator|.
name|refresh
argument_list|()
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|FeatureInterface
name|getFeature
parameter_list|(
name|Long
name|featureId
parameter_list|)
block|{
if|if
condition|(
name|featureId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|featureId
operator|.
name|equals
argument_list|(
name|getData
argument_list|(
name|i
argument_list|)
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
return|return
name|getData
argument_list|(
name|i
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

