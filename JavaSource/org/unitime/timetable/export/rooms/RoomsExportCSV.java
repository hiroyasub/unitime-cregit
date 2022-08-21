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
name|export
operator|.
name|rooms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|export
operator|.
name|CSVPrinter
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
name|export
operator|.
name|ExportHelper
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
name|RoomDetailInterface
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
name|RoomsColumn
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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:rooms.csv"
argument_list|)
specifier|public
class|class
name|RoomsExportCSV
extends|extends
name|RoomsExporter
block|{
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"rooms.csv"
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|print
parameter_list|(
name|ExportHelper
name|helper
parameter_list|,
name|List
argument_list|<
name|RoomDetailInterface
argument_list|>
name|rooms
parameter_list|,
name|ExportContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|RoomsExportCsv
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Column
argument_list|>
name|columns
init|=
operator|new
name|ArrayList
argument_list|<
name|Column
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|RoomsColumn
name|column
range|:
name|RoomsColumn
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
argument_list|,
name|context
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
name|Column
name|c
init|=
operator|new
name|Column
argument_list|(
name|column
argument_list|,
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|isColumnVisible
argument_list|(
name|c
argument_list|,
name|context
argument_list|)
condition|)
name|columns
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
name|Printer
name|printer
init|=
operator|new
name|CSVPrinter
argument_list|(
name|helper
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
name|printer
operator|.
name|getContentType
argument_list|()
argument_list|,
name|reference
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|String
index|[]
name|header
init|=
operator|new
name|String
index|[
name|columns
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|columns
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|header
index|[
name|i
index|]
operator|=
name|getColumnName
argument_list|(
name|columns
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|context
argument_list|)
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
name|printer
operator|.
name|printHeader
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|printer
operator|.
name|flush
argument_list|()
expr_stmt|;
for|for
control|(
name|RoomDetailInterface
name|room
range|:
name|rooms
control|)
block|{
name|String
index|[]
name|row
init|=
operator|new
name|String
index|[
name|columns
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|columns
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|row
index|[
name|i
index|]
operator|=
name|getCell
argument_list|(
name|room
argument_list|,
name|columns
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|context
argument_list|)
expr_stmt|;
name|printer
operator|.
name|printLine
argument_list|(
name|row
argument_list|)
expr_stmt|;
name|printer
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
name|printer
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|int
name|getNbrCells
parameter_list|(
name|RoomsColumn
name|column
parameter_list|,
name|ExportContext
name|ec
parameter_list|)
block|{
switch|switch
condition|(
name|column
condition|)
block|{
case|case
name|COORDINATES
case|:
return|return
literal|2
return|;
case|case
name|PICTURES
case|:
case|case
name|MAP
case|:
return|return
literal|0
return|;
block|}
return|return
name|super
operator|.
name|getNbrCells
argument_list|(
name|column
argument_list|,
name|ec
argument_list|)
return|;
block|}
specifier|protected
name|int
name|getNbrColumns
parameter_list|(
name|ExportContext
name|context
parameter_list|)
block|{
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|RoomsColumn
name|rc
range|:
name|RoomsColumn
operator|.
name|values
argument_list|()
control|)
name|ret
operator|+=
name|getNbrCells
argument_list|(
name|rc
argument_list|,
name|context
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|getColumnName
parameter_list|(
name|Column
name|column
parameter_list|,
name|ExportContext
name|context
parameter_list|)
block|{
switch|switch
condition|(
name|column
operator|.
name|getColumn
argument_list|()
condition|)
block|{
case|case
name|COORDINATES
case|:
if|if
condition|(
name|column
operator|.
name|getIndex
argument_list|()
operator|==
literal|0
condition|)
return|return
name|MESSAGES
operator|.
name|colCoordinateX
argument_list|()
return|;
else|else
return|return
name|MESSAGES
operator|.
name|colCoordinateY
argument_list|()
return|;
block|}
return|return
name|super
operator|.
name|getColumnName
argument_list|(
name|column
argument_list|,
name|context
argument_list|)
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getCell
parameter_list|(
name|RoomDetailInterface
name|room
parameter_list|,
name|Column
name|column
parameter_list|,
name|ExportContext
name|context
parameter_list|)
block|{
switch|switch
condition|(
name|column
operator|.
name|getColumn
argument_list|()
condition|)
block|{
case|case
name|NAME
case|:
return|return
name|room
operator|.
name|hasDisplayName
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
name|getDisplayName
argument_list|()
argument_list|)
else|:
name|room
operator|.
name|getLabel
argument_list|()
return|;
case|case
name|EXTERNAL_ID
case|:
return|return
name|room
operator|.
name|hasExternalId
argument_list|()
condition|?
name|room
operator|.
name|getExternalId
argument_list|()
else|:
literal|""
return|;
case|case
name|TYPE
case|:
return|return
name|room
operator|.
name|getRoomType
argument_list|()
operator|.
name|getLabel
argument_list|()
return|;
case|case
name|CAPACITY
case|:
return|return
name|room
operator|.
name|getCapacity
argument_list|()
operator|==
literal|null
condition|?
literal|"0"
else|:
name|room
operator|.
name|getCapacity
argument_list|()
operator|.
name|toString
argument_list|()
return|;
case|case
name|EXAM_CAPACITY
case|:
return|return
name|room
operator|.
name|getExamCapacity
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|room
operator|.
name|getExamCapacity
argument_list|()
operator|.
name|toString
argument_list|()
return|;
case|case
name|AREA
case|:
return|return
name|room
operator|.
name|getArea
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|room
operator|.
name|getArea
argument_list|()
operator|.
name|toString
argument_list|()
return|;
case|case
name|COORDINATES
case|:
if|if
condition|(
name|column
operator|.
name|getIndex
argument_list|()
operator|==
literal|0
condition|)
return|return
name|room
operator|.
name|getX
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|room
operator|.
name|getX
argument_list|()
operator|.
name|toString
argument_list|()
return|;
else|else
return|return
name|room
operator|.
name|getY
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|room
operator|.
name|getY
argument_list|()
operator|.
name|toString
argument_list|()
return|;
case|case
name|ROOM_CHECK
case|:
return|return
name|room
operator|.
name|isIgnoreRoomCheck
argument_list|()
condition|?
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
else|:
name|MESSAGES
operator|.
name|exportTrue
argument_list|()
return|;
case|case
name|DISTANCE_CHECK
case|:
return|return
name|room
operator|.
name|isIgnoreTooFar
argument_list|()
condition|?
name|MESSAGES
operator|.
name|exportFalse
argument_list|()
else|:
name|MESSAGES
operator|.
name|exportTrue
argument_list|()
return|;
case|case
name|PREFERENCE
case|:
return|return
name|context
operator|.
name|pref2string
argument_list|(
name|room
operator|.
name|getDepartments
argument_list|()
argument_list|)
return|;
case|case
name|AVAILABILITY
case|:
return|return
name|room
operator|.
name|getAvailability
argument_list|()
return|;
case|case
name|DEPARTMENTS
case|:
return|return
name|context
operator|.
name|dept2string
argument_list|(
name|room
operator|.
name|getDepartments
argument_list|()
argument_list|,
literal|true
argument_list|)
return|;
case|case
name|CONTROL_DEPT
case|:
return|return
name|context
operator|.
name|dept2string
argument_list|(
name|room
operator|.
name|getControlDepartment
argument_list|()
argument_list|,
literal|true
argument_list|)
return|;
case|case
name|EXAM_TYPES
case|:
return|return
name|context
operator|.
name|examTypes2string
argument_list|(
name|room
operator|.
name|getExamTypes
argument_list|()
argument_list|)
return|;
case|case
name|PERIOD_PREF
case|:
return|return
name|room
operator|.
name|getPeriodPreference
argument_list|()
return|;
case|case
name|EVENT_DEPARTMENT
case|:
return|return
name|context
operator|.
name|dept2string
argument_list|(
name|room
operator|.
name|getEventDepartment
argument_list|()
argument_list|,
literal|false
argument_list|)
return|;
case|case
name|EVENT_STATUS
case|:
return|return
operator|(
name|room
operator|.
name|getEventStatus
argument_list|()
operator|!=
literal|null
condition|?
name|CONSTANTS
operator|.
name|eventStatusAbbv
argument_list|()
index|[
name|room
operator|.
name|getEventStatus
argument_list|()
index|]
else|:
name|room
operator|.
name|getDefaultEventStatus
argument_list|()
operator|!=
literal|null
condition|?
name|CONSTANTS
operator|.
name|eventStatusAbbv
argument_list|()
index|[
name|room
operator|.
name|getDefaultEventStatus
argument_list|()
index|]
else|:
literal|""
operator|)
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
return|;
case|case
name|EVENT_AVAILABILITY
case|:
return|return
name|room
operator|.
name|getEventAvailability
argument_list|()
return|;
case|case
name|EVENT_MESSAGE
case|:
return|return
name|room
operator|.
name|getEventNote
argument_list|()
operator|!=
literal|null
condition|?
name|room
operator|.
name|getEventNote
argument_list|()
else|:
name|room
operator|.
name|getDefaultEventNote
argument_list|()
return|;
case|case
name|BREAK_TIME
case|:
return|return
name|room
operator|.
name|getBreakTime
argument_list|()
operator|!=
literal|null
condition|?
name|room
operator|.
name|getBreakTime
argument_list|()
operator|.
name|toString
argument_list|()
else|:
name|room
operator|.
name|getDefaultBreakTime
argument_list|()
operator|!=
literal|null
condition|?
name|room
operator|.
name|getDefaultBreakTime
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|""
return|;
case|case
name|GROUPS
case|:
return|return
name|context
operator|.
name|groups2string
argument_list|(
name|room
operator|.
name|getGroups
argument_list|()
argument_list|)
return|;
case|case
name|FEATURES
case|:
if|if
condition|(
name|column
operator|.
name|getIndex
argument_list|()
operator|==
literal|0
condition|)
return|return
name|context
operator|.
name|features2string
argument_list|(
name|room
operator|.
name|getFeatures
argument_list|()
argument_list|,
literal|null
argument_list|)
return|;
else|else
return|return
name|context
operator|.
name|features2string
argument_list|(
name|room
operator|.
name|getFeatures
argument_list|()
argument_list|,
name|context
operator|.
name|getRoomFeatureTypes
argument_list|()
operator|.
name|get
argument_list|(
name|column
operator|.
name|getIndex
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
return|;
case|case
name|SERVICES
case|:
return|return
name|context
operator|.
name|services2string
argument_list|(
name|room
operator|.
name|getServices
argument_list|()
argument_list|,
name|room
operator|.
name|getEventDepartment
argument_list|()
argument_list|)
return|;
case|case
name|PARTITION
case|:
if|if
condition|(
name|room
operator|.
name|getParent
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|room
operator|.
name|getParent
argument_list|()
operator|.
name|hasDisplayName
argument_list|()
condition|?
name|MESSAGES
operator|.
name|label
argument_list|(
name|room
operator|.
name|getParent
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|,
name|room
operator|.
name|getParent
argument_list|()
operator|.
name|getDisplayName
argument_list|()
argument_list|)
else|:
name|room
operator|.
name|getParent
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
return|;
default|default:
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

