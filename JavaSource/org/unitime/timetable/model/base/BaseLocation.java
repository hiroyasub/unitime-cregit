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
name|model
operator|.
name|base
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|model
operator|.
name|Assignment
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
name|model
operator|.
name|Department
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
name|model
operator|.
name|ExamLocationPref
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
name|model
operator|.
name|ExamType
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
name|model
operator|.
name|Location
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
name|model
operator|.
name|RoomDept
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
name|model
operator|.
name|RoomFeature
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
name|model
operator|.
name|RoomGroup
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
name|model
operator|.
name|Session
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseLocation
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|Long
name|iPermanentId
decl_stmt|;
specifier|private
name|Integer
name|iCapacity
decl_stmt|;
specifier|private
name|Double
name|iCoordinateX
decl_stmt|;
specifier|private
name|Double
name|iCoordinateY
decl_stmt|;
specifier|private
name|Boolean
name|iIgnoreTooFar
decl_stmt|;
specifier|private
name|Boolean
name|iIgnoreRoomCheck
decl_stmt|;
specifier|private
name|Double
name|iArea
decl_stmt|;
specifier|private
name|Integer
name|iEventStatus
decl_stmt|;
specifier|private
name|String
name|iNote
decl_stmt|;
specifier|private
name|Integer
name|iBreakTime
decl_stmt|;
specifier|private
name|String
name|iManagerIds
decl_stmt|;
specifier|private
name|String
name|iPattern
decl_stmt|;
specifier|private
name|String
name|iShareNote
decl_stmt|;
specifier|private
name|String
name|iEventAvailability
decl_stmt|;
specifier|private
name|Integer
name|iExamCapacity
decl_stmt|;
specifier|private
name|String
name|iDisplayName
decl_stmt|;
specifier|private
name|String
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|Department
name|iEventDepartment
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|RoomFeature
argument_list|>
name|iFeatures
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ExamType
argument_list|>
name|iExamTypes
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ExamLocationPref
argument_list|>
name|iExamPreferences
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Assignment
argument_list|>
name|iAssignments
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|RoomGroup
argument_list|>
name|iRoomGroups
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|RoomDept
argument_list|>
name|iRoomDepts
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PERMANENT_ID
init|=
literal|"permanentId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CAPACITY
init|=
literal|"capacity"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COORDINATE_X
init|=
literal|"coordinateX"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COORDINATE_Y
init|=
literal|"coordinateY"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_IGNORE_TOO_FAR
init|=
literal|"ignoreTooFar"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_IGNORE_ROOM_CHECK
init|=
literal|"ignoreRoomCheck"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_AREA
init|=
literal|"area"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EVENT_STATUS
init|=
literal|"eventStatus"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NOTE
init|=
literal|"note"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_BREAK_TIME
init|=
literal|"breakTime"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MANAGER_IDS
init|=
literal|"managerIds"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PATTERN
init|=
literal|"pattern"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SHARE_NOTE
init|=
literal|"shareNote"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_AVAILABILITY
init|=
literal|"eventAvailability"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXAM_CAPACITY
init|=
literal|"examCapacity"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DISPLAY_NAME
init|=
literal|"displayName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
name|BaseLocation
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseLocation
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Long
name|getPermanentId
parameter_list|()
block|{
return|return
name|iPermanentId
return|;
block|}
specifier|public
name|void
name|setPermanentId
parameter_list|(
name|Long
name|permanentId
parameter_list|)
block|{
name|iPermanentId
operator|=
name|permanentId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getCapacity
parameter_list|()
block|{
return|return
name|iCapacity
return|;
block|}
specifier|public
name|void
name|setCapacity
parameter_list|(
name|Integer
name|capacity
parameter_list|)
block|{
name|iCapacity
operator|=
name|capacity
expr_stmt|;
block|}
specifier|public
name|Double
name|getCoordinateX
parameter_list|()
block|{
return|return
name|iCoordinateX
return|;
block|}
specifier|public
name|void
name|setCoordinateX
parameter_list|(
name|Double
name|coordinateX
parameter_list|)
block|{
name|iCoordinateX
operator|=
name|coordinateX
expr_stmt|;
block|}
specifier|public
name|Double
name|getCoordinateY
parameter_list|()
block|{
return|return
name|iCoordinateY
return|;
block|}
specifier|public
name|void
name|setCoordinateY
parameter_list|(
name|Double
name|coordinateY
parameter_list|)
block|{
name|iCoordinateY
operator|=
name|coordinateY
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isIgnoreTooFar
parameter_list|()
block|{
return|return
name|iIgnoreTooFar
return|;
block|}
specifier|public
name|Boolean
name|getIgnoreTooFar
parameter_list|()
block|{
return|return
name|iIgnoreTooFar
return|;
block|}
specifier|public
name|void
name|setIgnoreTooFar
parameter_list|(
name|Boolean
name|ignoreTooFar
parameter_list|)
block|{
name|iIgnoreTooFar
operator|=
name|ignoreTooFar
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isIgnoreRoomCheck
parameter_list|()
block|{
return|return
name|iIgnoreRoomCheck
return|;
block|}
specifier|public
name|Boolean
name|getIgnoreRoomCheck
parameter_list|()
block|{
return|return
name|iIgnoreRoomCheck
return|;
block|}
specifier|public
name|void
name|setIgnoreRoomCheck
parameter_list|(
name|Boolean
name|ignoreRoomCheck
parameter_list|)
block|{
name|iIgnoreRoomCheck
operator|=
name|ignoreRoomCheck
expr_stmt|;
block|}
specifier|public
name|Double
name|getArea
parameter_list|()
block|{
return|return
name|iArea
return|;
block|}
specifier|public
name|void
name|setArea
parameter_list|(
name|Double
name|area
parameter_list|)
block|{
name|iArea
operator|=
name|area
expr_stmt|;
block|}
specifier|public
name|Integer
name|getEventStatus
parameter_list|()
block|{
return|return
name|iEventStatus
return|;
block|}
specifier|public
name|void
name|setEventStatus
parameter_list|(
name|Integer
name|eventStatus
parameter_list|)
block|{
name|iEventStatus
operator|=
name|eventStatus
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|Integer
name|getBreakTime
parameter_list|()
block|{
return|return
name|iBreakTime
return|;
block|}
specifier|public
name|void
name|setBreakTime
parameter_list|(
name|Integer
name|breakTime
parameter_list|)
block|{
name|iBreakTime
operator|=
name|breakTime
expr_stmt|;
block|}
specifier|public
name|String
name|getManagerIds
parameter_list|()
block|{
return|return
name|iManagerIds
return|;
block|}
specifier|public
name|void
name|setManagerIds
parameter_list|(
name|String
name|managerIds
parameter_list|)
block|{
name|iManagerIds
operator|=
name|managerIds
expr_stmt|;
block|}
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|iPattern
return|;
block|}
specifier|public
name|void
name|setPattern
parameter_list|(
name|String
name|pattern
parameter_list|)
block|{
name|iPattern
operator|=
name|pattern
expr_stmt|;
block|}
specifier|public
name|String
name|getShareNote
parameter_list|()
block|{
return|return
name|iShareNote
return|;
block|}
specifier|public
name|void
name|setShareNote
parameter_list|(
name|String
name|shareNote
parameter_list|)
block|{
name|iShareNote
operator|=
name|shareNote
expr_stmt|;
block|}
specifier|public
name|String
name|getEventAvailability
parameter_list|()
block|{
return|return
name|iEventAvailability
return|;
block|}
specifier|public
name|void
name|setEventAvailability
parameter_list|(
name|String
name|eventAvailability
parameter_list|)
block|{
name|iEventAvailability
operator|=
name|eventAvailability
expr_stmt|;
block|}
specifier|public
name|Integer
name|getExamCapacity
parameter_list|()
block|{
return|return
name|iExamCapacity
return|;
block|}
specifier|public
name|void
name|setExamCapacity
parameter_list|(
name|Integer
name|examCapacity
parameter_list|)
block|{
name|iExamCapacity
operator|=
name|examCapacity
expr_stmt|;
block|}
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|iDisplayName
return|;
block|}
specifier|public
name|void
name|setDisplayName
parameter_list|(
name|String
name|displayName
parameter_list|)
block|{
name|iDisplayName
operator|=
name|displayName
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|Department
name|getEventDepartment
parameter_list|()
block|{
return|return
name|iEventDepartment
return|;
block|}
specifier|public
name|void
name|setEventDepartment
parameter_list|(
name|Department
name|eventDepartment
parameter_list|)
block|{
name|iEventDepartment
operator|=
name|eventDepartment
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|RoomFeature
argument_list|>
name|getFeatures
parameter_list|()
block|{
return|return
name|iFeatures
return|;
block|}
specifier|public
name|void
name|setFeatures
parameter_list|(
name|Set
argument_list|<
name|RoomFeature
argument_list|>
name|features
parameter_list|)
block|{
name|iFeatures
operator|=
name|features
expr_stmt|;
block|}
specifier|public
name|void
name|addTofeatures
parameter_list|(
name|RoomFeature
name|roomFeature
parameter_list|)
block|{
if|if
condition|(
name|iFeatures
operator|==
literal|null
condition|)
name|iFeatures
operator|=
operator|new
name|HashSet
argument_list|<
name|RoomFeature
argument_list|>
argument_list|()
expr_stmt|;
name|iFeatures
operator|.
name|add
argument_list|(
name|roomFeature
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ExamType
argument_list|>
name|getExamTypes
parameter_list|()
block|{
return|return
name|iExamTypes
return|;
block|}
specifier|public
name|void
name|setExamTypes
parameter_list|(
name|Set
argument_list|<
name|ExamType
argument_list|>
name|examTypes
parameter_list|)
block|{
name|iExamTypes
operator|=
name|examTypes
expr_stmt|;
block|}
specifier|public
name|void
name|addToexamTypes
parameter_list|(
name|ExamType
name|examType
parameter_list|)
block|{
if|if
condition|(
name|iExamTypes
operator|==
literal|null
condition|)
name|iExamTypes
operator|=
operator|new
name|HashSet
argument_list|<
name|ExamType
argument_list|>
argument_list|()
expr_stmt|;
name|iExamTypes
operator|.
name|add
argument_list|(
name|examType
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ExamLocationPref
argument_list|>
name|getExamPreferences
parameter_list|()
block|{
return|return
name|iExamPreferences
return|;
block|}
specifier|public
name|void
name|setExamPreferences
parameter_list|(
name|Set
argument_list|<
name|ExamLocationPref
argument_list|>
name|examPreferences
parameter_list|)
block|{
name|iExamPreferences
operator|=
name|examPreferences
expr_stmt|;
block|}
specifier|public
name|void
name|addToexamPreferences
parameter_list|(
name|ExamLocationPref
name|examLocationPref
parameter_list|)
block|{
if|if
condition|(
name|iExamPreferences
operator|==
literal|null
condition|)
name|iExamPreferences
operator|=
operator|new
name|HashSet
argument_list|<
name|ExamLocationPref
argument_list|>
argument_list|()
expr_stmt|;
name|iExamPreferences
operator|.
name|add
argument_list|(
name|examLocationPref
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Assignment
argument_list|>
name|getAssignments
parameter_list|()
block|{
return|return
name|iAssignments
return|;
block|}
specifier|public
name|void
name|setAssignments
parameter_list|(
name|Set
argument_list|<
name|Assignment
argument_list|>
name|assignments
parameter_list|)
block|{
name|iAssignments
operator|=
name|assignments
expr_stmt|;
block|}
specifier|public
name|void
name|addToassignments
parameter_list|(
name|Assignment
name|assignment
parameter_list|)
block|{
if|if
condition|(
name|iAssignments
operator|==
literal|null
condition|)
name|iAssignments
operator|=
operator|new
name|HashSet
argument_list|<
name|Assignment
argument_list|>
argument_list|()
expr_stmt|;
name|iAssignments
operator|.
name|add
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|RoomGroup
argument_list|>
name|getRoomGroups
parameter_list|()
block|{
return|return
name|iRoomGroups
return|;
block|}
specifier|public
name|void
name|setRoomGroups
parameter_list|(
name|Set
argument_list|<
name|RoomGroup
argument_list|>
name|roomGroups
parameter_list|)
block|{
name|iRoomGroups
operator|=
name|roomGroups
expr_stmt|;
block|}
specifier|public
name|void
name|addToroomGroups
parameter_list|(
name|RoomGroup
name|roomGroup
parameter_list|)
block|{
if|if
condition|(
name|iRoomGroups
operator|==
literal|null
condition|)
name|iRoomGroups
operator|=
operator|new
name|HashSet
argument_list|<
name|RoomGroup
argument_list|>
argument_list|()
expr_stmt|;
name|iRoomGroups
operator|.
name|add
argument_list|(
name|roomGroup
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|RoomDept
argument_list|>
name|getRoomDepts
parameter_list|()
block|{
return|return
name|iRoomDepts
return|;
block|}
specifier|public
name|void
name|setRoomDepts
parameter_list|(
name|Set
argument_list|<
name|RoomDept
argument_list|>
name|roomDepts
parameter_list|)
block|{
name|iRoomDepts
operator|=
name|roomDepts
expr_stmt|;
block|}
specifier|public
name|void
name|addToroomDepts
parameter_list|(
name|RoomDept
name|roomDept
parameter_list|)
block|{
if|if
condition|(
name|iRoomDepts
operator|==
literal|null
condition|)
name|iRoomDepts
operator|=
operator|new
name|HashSet
argument_list|<
name|RoomDept
argument_list|>
argument_list|()
expr_stmt|;
name|iRoomDepts
operator|.
name|add
argument_list|(
name|roomDept
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|Location
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|Location
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Location
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"Location["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"Location["
operator|+
literal|"\n	Area: "
operator|+
name|getArea
argument_list|()
operator|+
literal|"\n	BreakTime: "
operator|+
name|getBreakTime
argument_list|()
operator|+
literal|"\n	Capacity: "
operator|+
name|getCapacity
argument_list|()
operator|+
literal|"\n	CoordinateX: "
operator|+
name|getCoordinateX
argument_list|()
operator|+
literal|"\n	CoordinateY: "
operator|+
name|getCoordinateY
argument_list|()
operator|+
literal|"\n	DisplayName: "
operator|+
name|getDisplayName
argument_list|()
operator|+
literal|"\n	EventAvailability: "
operator|+
name|getEventAvailability
argument_list|()
operator|+
literal|"\n	EventDepartment: "
operator|+
name|getEventDepartment
argument_list|()
operator|+
literal|"\n	EventStatus: "
operator|+
name|getEventStatus
argument_list|()
operator|+
literal|"\n	ExamCapacity: "
operator|+
name|getExamCapacity
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
argument_list|()
operator|+
literal|"\n	IgnoreRoomCheck: "
operator|+
name|getIgnoreRoomCheck
argument_list|()
operator|+
literal|"\n	IgnoreTooFar: "
operator|+
name|getIgnoreTooFar
argument_list|()
operator|+
literal|"\n	ManagerIds: "
operator|+
name|getManagerIds
argument_list|()
operator|+
literal|"\n	Note: "
operator|+
name|getNote
argument_list|()
operator|+
literal|"\n	Pattern: "
operator|+
name|getPattern
argument_list|()
operator|+
literal|"\n	PermanentId: "
operator|+
name|getPermanentId
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
argument_list|()
operator|+
literal|"\n	ShareNote: "
operator|+
name|getShareNote
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

