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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|defaults
operator|.
name|ApplicationProperty
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|GwtConstants
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
name|FeatureTypeInterface
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
name|GroupInterface
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
name|gwt
operator|.
name|shared
operator|.
name|RoomInterface
operator|.
name|RoomPictureInterface
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
name|AttachmentType
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
name|LocationPicture
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
name|RoomFeatureType
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
operator|.
name|rooms
operator|.
name|RoomDetailsBackend
operator|.
name|UrlSigner
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|RoomHintRequest
operator|.
name|class
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
specifier|protected
specifier|static
specifier|final
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
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
name|ApplicationProperty
operator|.
name|RoomHintMinimapUrl
operator|.
name|value
argument_list|()
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
block|{
name|minimap
operator|=
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
expr_stmt|;
name|String
name|apikey
init|=
name|ApplicationProperty
operator|.
name|RoomMapStaticApiKey
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|apikey
operator|!=
literal|null
operator|&&
operator|!
name|apikey
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|minimap
operator|+=
literal|"&key="
operator|+
name|apikey
expr_stmt|;
name|String
name|secret
init|=
name|ApplicationProperty
operator|.
name|RoomMapStaticSecret
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|secret
operator|!=
literal|null
operator|&&
operator|!
name|secret
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|minimap
operator|+=
literal|"&signature="
operator|+
operator|new
name|UrlSigner
argument_list|(
name|secret
argument_list|)
operator|.
name|signRequest
argument_list|(
name|minimap
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
name|response
operator|.
name|setMiniMapUrl
argument_list|(
name|minimap
argument_list|)
expr_stmt|;
block|}
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
name|CONSTANTS
operator|.
name|roomAreaFormat
argument_list|()
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
operator|(
name|ApplicationProperty
operator|.
name|RoomAreaUnitsMetric
operator|.
name|isTrue
argument_list|()
condition|?
name|MSG
operator|.
name|roomAreaMetricUnitsShort
argument_list|()
else|:
name|MSG
operator|.
name|roomAreaUnitsShort
argument_list|()
operator|)
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
name|FeatureInterface
name|feature
init|=
operator|new
name|FeatureInterface
argument_list|(
name|f
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|f
operator|.
name|getAbbv
argument_list|()
argument_list|,
name|f
operator|.
name|getLabel
argument_list|()
argument_list|)
decl_stmt|;
name|feature
operator|.
name|setDescription
argument_list|(
name|f
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|RoomFeatureType
name|t
init|=
name|f
operator|.
name|getFeatureType
argument_list|()
decl_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
name|feature
operator|.
name|setType
argument_list|(
operator|new
name|FeatureTypeInterface
argument_list|(
name|t
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|t
operator|.
name|getReference
argument_list|()
argument_list|,
name|t
operator|.
name|getLabel
argument_list|()
argument_list|,
name|t
operator|.
name|isShowInEventManagement
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|addFeature
argument_list|(
name|feature
argument_list|)
expr_stmt|;
block|}
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
name|GroupInterface
name|group
init|=
operator|new
name|GroupInterface
argument_list|(
name|g
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|g
operator|.
name|getAbbv
argument_list|()
argument_list|,
name|g
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|group
operator|.
name|setDescription
argument_list|(
name|g
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
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
literal|null
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
name|setEventDepartment
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
name|getEventDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|location
operator|.
name|getEventDepartment
argument_list|()
operator|.
name|getName
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
name|ApplicationProperty
operator|.
name|RoomHintShowBreakTime
operator|.
name|isTrue
argument_list|()
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
name|response
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|location
operator|.
name|isIgnoreRoomCheck
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|LocationPicture
name|picture
range|:
operator|new
name|TreeSet
argument_list|<
name|LocationPicture
argument_list|>
argument_list|(
name|location
operator|.
name|getPictures
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|picture
operator|.
name|getType
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
operator|!
name|AttachmentType
operator|.
name|VisibilityFlag
operator|.
name|IS_IMAGE
operator|.
name|in
argument_list|(
name|picture
operator|.
name|getType
argument_list|()
operator|.
name|getVisibility
argument_list|()
argument_list|)
operator|||
operator|!
name|AttachmentType
operator|.
name|VisibilityFlag
operator|.
name|SHOW_ROOM_TOOLTIP
operator|.
name|in
argument_list|(
name|picture
operator|.
name|getType
argument_list|()
operator|.
name|getVisibility
argument_list|()
argument_list|)
operator|)
condition|)
continue|continue;
name|response
operator|.
name|addPicture
argument_list|(
operator|new
name|RoomPictureInterface
argument_list|(
name|picture
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|picture
operator|.
name|getFileName
argument_list|()
argument_list|,
name|picture
operator|.
name|getContentType
argument_list|()
argument_list|,
name|picture
operator|.
name|getTimeStamp
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|,
name|RoomPicturesBackend
operator|.
name|getPictureType
argument_list|(
name|picture
operator|.
name|getType
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|ApplicationProperty
operator|.
name|RoomHintMinimapUrl
operator|.
name|value
argument_list|()
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

