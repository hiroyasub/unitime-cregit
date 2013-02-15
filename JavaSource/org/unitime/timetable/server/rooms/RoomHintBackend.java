begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
operator|.
name|rooms
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
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
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|ApplicationProperties
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
name|server
operator|.
name|GwtRpcImplementation
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
name|RoomHintRequest
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
name|RoomHintResponse
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
name|Building
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
name|GlobalRoomFeature
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
name|dao
operator|.
name|BuildingDAO
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
name|dao
operator|.
name|LocationDAO
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
name|SessionContext
import|;
end_import

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.gwt.shared.RoomInterface$RoomHintRequest"
argument_list|)
specifier|public
class|class
name|RoomHintBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|RoomHintRequest
argument_list|,
name|RoomHintResponse
argument_list|>
block|{
specifier|protected
specifier|static
specifier|final
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|RoomHintResponse
name|execute
parameter_list|(
name|RoomHintRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|RoomHintResponse
name|response
init|=
operator|new
name|RoomHintResponse
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getLocationId
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|Location
name|location
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|response
operator|.
name|setId
argument_list|(
name|location
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setLabel
argument_list|(
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setDisplayName
argument_list|(
name|location
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setRoomTypeLabel
argument_list|(
name|location
operator|.
name|getRoomTypeLabel
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|minimap
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.minimap.hint"
argument_list|)
decl_stmt|;
if|if
condition|(
name|minimap
operator|!=
literal|null
operator|&&
name|location
operator|.
name|getCoordinateX
argument_list|()
operator|!=
literal|null
operator|&&
name|location
operator|.
name|getCoordinateY
argument_list|()
operator|!=
literal|null
condition|)
name|response
operator|.
name|setMiniMapUrl
argument_list|(
name|minimap
operator|.
name|replace
argument_list|(
literal|"%x"
argument_list|,
name|location
operator|.
name|getCoordinateX
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%y"
argument_list|,
name|location
operator|.
name|getCoordinateY
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%n"
argument_list|,
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%i"
argument_list|,
name|location
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|location
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCapacity
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|location
operator|.
name|getExamCapacity
argument_list|()
operator|!=
literal|null
operator|&&
name|location
operator|.
name|getExamCapacity
argument_list|()
operator|>
literal|0
operator|&&
operator|!
name|location
operator|.
name|getExamCapacity
argument_list|()
operator|.
name|equals
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
argument_list|)
operator|&&
operator|!
name|location
operator|.
name|getExamTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|response
operator|.
name|setExamCapacity
argument_list|(
name|location
operator|.
name|getExamCapacity
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|location
operator|.
name|getExamTypes
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|response
operator|.
name|setExamType
argument_list|(
name|location
operator|.
name|getExamTypes
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|location
operator|.
name|getArea
argument_list|()
operator|!=
literal|null
condition|)
name|response
operator|.
name|setArea
argument_list|(
operator|new
name|DecimalFormat
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.room.area.units.format"
argument_list|,
literal|"#,##0.00"
argument_list|)
argument_list|)
operator|.
name|format
argument_list|(
name|location
operator|.
name|getArea
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|MSG
operator|.
name|roomAreaUnitsShort
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|GlobalRoomFeature
name|f
range|:
name|location
operator|.
name|getGlobalRoomFeatures
argument_list|()
control|)
block|{
name|String
name|type
init|=
operator|(
name|f
operator|.
name|getFeatureType
argument_list|()
operator|==
literal|null
condition|?
name|MESSAGES
operator|.
name|roomFeatures
argument_list|()
else|:
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getReference
argument_list|()
operator|)
decl_stmt|;
name|response
operator|.
name|addFeature
argument_list|(
name|type
argument_list|,
name|f
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|groups
init|=
literal|""
decl_stmt|;
for|for
control|(
name|RoomGroup
name|g
range|:
name|location
operator|.
name|getGlobalRoomGroups
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|groups
operator|.
name|isEmpty
argument_list|()
condition|)
name|groups
operator|+=
literal|", "
expr_stmt|;
name|groups
operator|+=
name|g
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|groups
operator|.
name|isEmpty
argument_list|()
condition|)
name|response
operator|.
name|setGroups
argument_list|(
name|groups
argument_list|)
expr_stmt|;
name|response
operator|.
name|setEventStatus
argument_list|(
name|location
operator|.
name|getEventDepartment
argument_list|()
operator|==
literal|null
condition|?
name|MESSAGES
operator|.
name|noEventDepartment
argument_list|()
else|:
name|location
operator|.
name|getEffectiveEventStatus
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setNote
argument_list|(
name|location
operator|.
name|getEventMessage
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.roomHint.showBreakTime"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
name|response
operator|.
name|setBreakTime
argument_list|(
name|location
operator|.
name|getEffectiveBreakTime
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
else|else
block|{
name|Building
name|building
init|=
name|BuildingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|-
name|request
operator|.
name|getLocationId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|building
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|response
operator|.
name|setId
argument_list|(
operator|-
name|building
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setLabel
argument_list|(
name|building
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|minimap
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.minimap.hint"
argument_list|)
decl_stmt|;
if|if
condition|(
name|minimap
operator|!=
literal|null
operator|&&
name|building
operator|.
name|getCoordinateX
argument_list|()
operator|!=
literal|null
operator|&&
name|building
operator|.
name|getCoordinateY
argument_list|()
operator|!=
literal|null
condition|)
name|response
operator|.
name|setMiniMapUrl
argument_list|(
name|minimap
operator|.
name|replace
argument_list|(
literal|"%x"
argument_list|,
name|building
operator|.
name|getCoordinateX
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%y"
argument_list|,
name|building
operator|.
name|getCoordinateY
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%n"
argument_list|,
name|building
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%i"
argument_list|,
name|building
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|building
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
block|}
block|}
end_class

end_unit

