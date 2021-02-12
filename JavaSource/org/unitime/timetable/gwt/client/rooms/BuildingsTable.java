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
name|Comparator
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
name|admin
operator|.
name|AdminCookie
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
name|shared
operator|.
name|RoomInterface
operator|.
name|BuildingInterface
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
name|BuildingsColumn
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

begin_class
specifier|public
class|class
name|BuildingsTable
extends|extends
name|UniTimeTable
argument_list|<
name|BuildingInterface
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
specifier|private
name|BuildingsColumn
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
name|BuildingsTable
parameter_list|()
block|{
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
name|BuildingsColumn
name|col
range|:
name|BuildingsColumn
operator|.
name|values
argument_list|()
control|)
block|{
specifier|final
name|UniTimeTableHeader
name|h
init|=
operator|new
name|UniTimeTableHeader
argument_list|(
name|getColumnName
argument_list|(
name|col
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|BuildingComparator
operator|.
name|isApplicable
argument_list|(
name|col
argument_list|)
condition|)
block|{
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
name|col
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
name|header
operator|.
name|add
argument_list|(
name|h
argument_list|)
expr_stmt|;
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
name|AdminCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|getSortBuildingsBy
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getColumnName
parameter_list|(
name|BuildingsColumn
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
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
name|EXTERNAL_ID
case|:
return|return
name|MESSAGES
operator|.
name|colExternalId
argument_list|()
return|;
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
name|COORDINATES
case|:
return|return
name|MESSAGES
operator|.
name|colCoordinates
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
specifier|public
name|Widget
name|getColumnWidget
parameter_list|(
name|BuildingsColumn
name|column
parameter_list|,
name|BuildingInterface
name|building
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|ABBREVIATION
case|:
return|return
operator|new
name|Label
argument_list|(
name|building
operator|.
name|getAbbreviation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|building
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
case|case
name|EXTERNAL_ID
case|:
return|return
operator|new
name|Label
argument_list|(
name|building
operator|.
name|getExternalId
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|building
operator|.
name|getExternalId
argument_list|()
argument_list|)
return|;
case|case
name|NAME
case|:
return|return
operator|new
name|Label
argument_list|(
name|building
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|building
operator|.
name|getName
argument_list|()
argument_list|)
return|;
case|case
name|COORDINATES
case|:
if|if
condition|(
operator|!
name|building
operator|.
name|hasCoordinates
argument_list|()
condition|)
return|return
operator|new
name|Label
argument_list|()
return|;
return|return
operator|new
name|Label
argument_list|(
name|MESSAGES
operator|.
name|coordinates
argument_list|(
name|building
operator|.
name|getX
argument_list|()
argument_list|,
name|building
operator|.
name|getY
argument_list|()
argument_list|)
argument_list|)
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
specifier|protected
name|void
name|addRow
parameter_list|(
name|BuildingInterface
name|building
parameter_list|)
block|{
name|List
argument_list|<
name|Widget
argument_list|>
name|line
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
name|BuildingsColumn
name|col
range|:
name|BuildingsColumn
operator|.
name|values
argument_list|()
control|)
name|line
operator|.
name|add
argument_list|(
name|getColumnWidget
argument_list|(
name|col
argument_list|,
name|building
argument_list|)
argument_list|)
expr_stmt|;
name|addRow
argument_list|(
name|building
argument_list|,
name|line
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setData
parameter_list|(
name|List
argument_list|<
name|BuildingInterface
argument_list|>
name|buildings
parameter_list|)
block|{
name|clearTable
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|buildings
operator|!=
literal|null
condition|)
for|for
control|(
name|BuildingInterface
name|building
range|:
name|buildings
control|)
name|addRow
argument_list|(
name|building
argument_list|)
expr_stmt|;
name|sort
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|doSort
parameter_list|(
name|BuildingsColumn
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
name|AdminCookie
operator|.
name|getInstance
argument_list|()
operator|.
name|setSortBuildingsBy
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
name|BuildingsColumn
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
name|BuildingsColumn
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
name|UniTimeTableHeader
name|header
init|=
name|getHeader
argument_list|(
name|iSortBy
operator|.
name|ordinal
argument_list|()
argument_list|)
decl_stmt|;
name|sort
argument_list|(
name|header
argument_list|,
operator|new
name|BuildingComparator
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
specifier|public
specifier|static
class|class
name|BuildingComparator
implements|implements
name|Comparator
argument_list|<
name|BuildingInterface
argument_list|>
block|{
specifier|private
name|BuildingsColumn
name|iColumn
decl_stmt|;
specifier|private
name|boolean
name|iAsc
decl_stmt|;
specifier|public
name|BuildingComparator
parameter_list|(
name|BuildingsColumn
name|column
parameter_list|,
name|boolean
name|asc
parameter_list|)
block|{
name|iColumn
operator|=
name|column
expr_stmt|;
name|iAsc
operator|=
name|asc
expr_stmt|;
block|}
specifier|public
name|int
name|compareById
parameter_list|(
name|BuildingInterface
name|r1
parameter_list|,
name|BuildingInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getId
argument_list|()
argument_list|,
name|r2
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByName
parameter_list|(
name|BuildingInterface
name|r1
parameter_list|,
name|BuildingInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getName
argument_list|()
argument_list|,
name|r2
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByAbbreviation
parameter_list|(
name|BuildingInterface
name|r1
parameter_list|,
name|BuildingInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|r2
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareByExternalId
parameter_list|(
name|BuildingInterface
name|r1
parameter_list|,
name|BuildingInterface
name|r2
parameter_list|)
block|{
return|return
name|compare
argument_list|(
name|r1
operator|.
name|getExternalId
argument_list|()
argument_list|,
name|r2
operator|.
name|getExternalId
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|int
name|compareByColumn
parameter_list|(
name|BuildingInterface
name|r1
parameter_list|,
name|BuildingInterface
name|r2
parameter_list|)
block|{
switch|switch
condition|(
name|iColumn
condition|)
block|{
case|case
name|NAME
case|:
return|return
name|compareByName
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|ABBREVIATION
case|:
return|return
name|compareByAbbreviation
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
case|case
name|EXTERNAL_ID
case|:
return|return
name|compareByExternalId
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
default|default:
return|return
name|compareByAbbreviation
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|boolean
name|isApplicable
parameter_list|(
name|BuildingsColumn
name|column
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|ABBREVIATION
case|:
case|case
name|NAME
case|:
case|case
name|EXTERNAL_ID
case|:
return|return
literal|true
return|;
default|default:
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|BuildingInterface
name|r1
parameter_list|,
name|BuildingInterface
name|r2
parameter_list|)
block|{
name|int
name|cmp
init|=
name|compareByColumn
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareByAbbreviation
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|==
literal|0
condition|)
name|cmp
operator|=
name|compareById
argument_list|(
name|r1
argument_list|,
name|r2
argument_list|)
expr_stmt|;
return|return
operator|(
name|iAsc
condition|?
name|cmp
else|:
operator|-
name|cmp
operator|)
return|;
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
if|if
condition|(
name|s1
operator|==
literal|null
operator|||
name|s1
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
name|s2
operator|==
literal|null
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
operator|-
literal|1
else|:
name|s1
operator|.
name|compareToIgnoreCase
argument_list|(
name|s2
argument_list|)
operator|)
return|;
block|}
block|}
specifier|protected
name|int
name|compare
parameter_list|(
name|Number
name|n1
parameter_list|,
name|Number
name|n2
parameter_list|)
block|{
return|return
operator|(
name|n1
operator|==
literal|null
condition|?
name|n2
operator|==
literal|null
condition|?
literal|0
else|:
operator|-
literal|1
else|:
name|n2
operator|==
literal|null
condition|?
literal|1
else|:
name|Double
operator|.
name|compare
argument_list|(
name|n1
operator|.
name|doubleValue
argument_list|()
argument_list|,
name|n2
operator|.
name|doubleValue
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
block|}
end_class

end_unit

