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
name|util
operator|.
name|List
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DistanceMetric
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
name|defaults
operator|.
name|CommonValues
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
name|UserProperty
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
name|events
operator|.
name|EventAction
operator|.
name|EventContext
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
name|shared
operator|.
name|RoomInterface
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
name|AcademicSessionInterface
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
name|DepartmentInterface
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
name|ExamTypeInterface
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
name|PreferenceInterface
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
name|RoomPropertiesInterface
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
name|RoomPropertiesRequest
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
name|RoomTypeInterface
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
name|DepartmentRoomFeature
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
name|PreferenceLevel
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
name|RoomType
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
name|RoomFeatureTypeDAO
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
name|SessionDAO
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
name|GwtRpcImplements
argument_list|(
name|RoomPropertiesRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|RoomPropertiesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|RoomPropertiesRequest
argument_list|,
name|RoomPropertiesInterface
argument_list|>
block|{
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
annotation|@
name|Override
specifier|public
name|RoomPropertiesInterface
name|execute
parameter_list|(
name|RoomPropertiesRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Rooms
argument_list|)
expr_stmt|;
name|RoomPropertiesInterface
name|response
init|=
operator|new
name|RoomPropertiesInterface
argument_list|()
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
condition|)
name|response
operator|.
name|setAcademicSession
argument_list|(
operator|new
name|AcademicSessionInterface
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Session"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanEditDepartments
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EditRoomDepartments
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanExportCsv
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|RoomsExportCsv
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanExportPdf
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|RoomsExportPdf
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanEditRoomExams
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EditRoomDepartmentsExams
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanAddRoom
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|AddRoom
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanAddNonUniversity
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|AddNonUnivLocation
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanSeeCourses
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|InstructionalOfferings
argument_list|)
operator|||
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Classes
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanSeeExams
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Examinations
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanSeeEvents
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Events
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|response
operator|.
name|setCanChangeAvailability
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditAvailability
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangeControll
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditChangeControll
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangeEventAvailability
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditEventAvailability
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangeEventProperties
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditChangeEventProperties
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangeExamStatus
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditChangeExaminationStatus
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangeExternalId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditChangeExternalId
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangeFeatures
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditFeatures
argument_list|)
operator|||
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditGlobalFeatures
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangeGroups
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditGroups
argument_list|)
operator|||
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditGlobalGroups
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangePicture
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditChangePicture
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanChangePreferences
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditPreference
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|RoomType
name|type
range|:
name|RoomType
operator|.
name|findAll
argument_list|()
control|)
name|response
operator|.
name|addRoomType
argument_list|(
operator|new
name|RoomTypeInterface
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|type
operator|.
name|getLabel
argument_list|()
argument_list|,
name|type
operator|.
name|isRoom
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Building
name|b
range|:
name|Building
operator|.
name|findAll
argument_list|(
name|response
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|BuildingInterface
name|building
init|=
operator|new
name|BuildingInterface
argument_list|(
name|b
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|b
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|b
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|building
operator|.
name|setX
argument_list|(
name|b
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|building
operator|.
name|setY
argument_list|(
name|b
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addBuilding
argument_list|(
name|building
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|RoomFeatureType
name|type
range|:
operator|new
name|TreeSet
argument_list|<
name|RoomFeatureType
argument_list|>
argument_list|(
name|RoomFeatureTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
argument_list|)
control|)
name|response
operator|.
name|addFeatureType
argument_list|(
operator|new
name|FeatureTypeInterface
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|type
operator|.
name|getReference
argument_list|()
argument_list|,
name|type
operator|.
name|getLabel
argument_list|()
argument_list|,
name|type
operator|.
name|isShowInEventManagement
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|ExamType
name|type
range|:
name|ExamType
operator|.
name|findAllUsed
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
name|response
operator|.
name|addExamType
argument_list|(
operator|new
name|ExamTypeInterface
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|type
operator|.
name|getReference
argument_list|()
argument_list|,
name|type
operator|.
name|getLabel
argument_list|()
argument_list|,
name|type
operator|.
name|getType
argument_list|()
operator|==
name|ExamType
operator|.
name|sExamTypeFinal
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Department
name|d
range|:
name|Department
operator|.
name|getUserDepartments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
control|)
block|{
name|DepartmentInterface
name|department
init|=
operator|new
name|DepartmentInterface
argument_list|()
decl_stmt|;
name|department
operator|.
name|setId
argument_list|(
name|d
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setDeptCode
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setAbbreviation
argument_list|(
name|d
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setLabel
argument_list|(
name|d
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExternal
argument_list|(
name|d
operator|.
name|isExternalManager
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setEvent
argument_list|(
name|d
operator|.
name|isAllowEvents
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExtAbbreviation
argument_list|(
name|d
operator|.
name|getExternalMgrAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setExtLabel
argument_list|(
name|d
operator|.
name|getExternalMgrLabel
argument_list|()
argument_list|)
expr_stmt|;
name|department
operator|.
name|setTitle
argument_list|(
name|d
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addDepartment
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
name|response
operator|.
name|setHorizontal
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
name|CommonValues
operator|.
name|HorizontalGrid
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setGridAsText
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|==
literal|null
condition|?
literal|false
else|:
name|CommonValues
operator|.
name|TextGrid
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
literal|true
condition|;
name|i
operator|++
control|)
block|{
name|String
name|mode
init|=
name|ApplicationProperty
operator|.
name|RoomSharingMode
operator|.
name|value
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
literal|1
operator|+
name|i
argument_list|)
argument_list|,
name|i
operator|<
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
operator|.
name|length
condition|?
name|CONSTANTS
operator|.
name|roomSharingModes
argument_list|()
index|[
name|i
index|]
else|:
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|mode
operator|==
literal|null
operator|||
name|mode
operator|.
name|isEmpty
argument_list|()
condition|)
break|break;
name|response
operator|.
name|addMode
argument_list|(
operator|new
name|RoomInterface
operator|.
name|RoomSharingDisplayMode
argument_list|(
name|mode
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|boolean
name|filterDepartments
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
decl_stmt|;
name|boolean
name|includeGlobalGroups
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditGlobalGroups
argument_list|)
decl_stmt|;
name|boolean
name|includeDeptGroups
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditGroups
argument_list|)
decl_stmt|;
for|for
control|(
name|RoomGroup
name|g
range|:
name|RoomGroup
operator|.
name|getAllRoomGroupsForSession
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
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
if|if
condition|(
name|g
operator|.
name|getDepartment
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|includeDeptGroups
condition|)
continue|continue;
if|if
condition|(
name|filterDepartments
operator|&&
operator|!
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasQualifier
argument_list|(
name|g
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
continue|continue;
name|group
operator|.
name|setDepartment
argument_list|(
name|response
operator|.
name|getDepartment
argument_list|(
name|g
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|.
name|setTitle
argument_list|(
operator|(
name|g
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
operator|||
name|g
operator|.
name|getDescription
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|g
operator|.
name|getName
argument_list|()
else|:
name|g
operator|.
name|getDescription
argument_list|()
operator|)
operator|+
literal|" ("
operator|+
name|g
operator|.
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|includeGlobalGroups
condition|)
continue|continue;
name|group
operator|.
name|setTitle
argument_list|(
operator|(
name|g
operator|.
name|getDescription
argument_list|()
operator|==
literal|null
operator|||
name|g
operator|.
name|getDescription
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|g
operator|.
name|getName
argument_list|()
else|:
name|g
operator|.
name|getDescription
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
name|response
operator|.
name|addGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditGlobalFeatures
argument_list|)
condition|)
block|{
for|for
control|(
name|GlobalRoomFeature
name|f
range|:
name|RoomFeature
operator|.
name|getAllGlobalRoomFeatures
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
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
if|if
condition|(
name|f
operator|.
name|getFeatureType
argument_list|()
operator|!=
literal|null
condition|)
name|feature
operator|.
name|setType
argument_list|(
name|response
operator|.
name|getFeatureType
argument_list|(
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|feature
operator|.
name|setTitle
argument_list|(
name|f
operator|.
name|getLabel
argument_list|()
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
block|}
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|RoomEditFeatures
argument_list|)
condition|)
block|{
for|for
control|(
name|DepartmentRoomFeature
name|f
range|:
name|RoomFeature
operator|.
name|getAllDepartmentRoomFeaturesInSession
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|filterDepartments
operator|&&
operator|!
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasQualifier
argument_list|(
name|f
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
continue|continue;
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
if|if
condition|(
name|f
operator|.
name|getFeatureType
argument_list|()
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
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|,
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|,
name|f
operator|.
name|getFeatureType
argument_list|()
operator|.
name|isShowInEventManagement
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|feature
operator|.
name|setDepartment
argument_list|(
name|response
operator|.
name|getDepartment
argument_list|(
name|f
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|feature
operator|.
name|setTitle
argument_list|(
name|f
operator|.
name|getLabel
argument_list|()
operator|+
literal|" ("
operator|+
name|f
operator|.
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|")"
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
block|}
for|for
control|(
name|PreferenceLevel
name|pref
range|:
name|PreferenceLevel
operator|.
name|getPreferenceLevelList
argument_list|(
literal|false
argument_list|)
control|)
block|{
name|response
operator|.
name|addPreference
argument_list|(
operator|new
name|PreferenceInterface
argument_list|(
name|pref
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|PreferenceLevel
operator|.
name|prolog2bgColor
argument_list|(
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|,
name|pref
operator|.
name|getPrefProlog
argument_list|()
argument_list|,
name|pref
operator|.
name|getPrefName
argument_list|()
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|DistanceMetric
operator|.
name|Ellipsoid
name|ellipsoid
init|=
name|DistanceMetric
operator|.
name|Ellipsoid
operator|.
name|valueOf
argument_list|(
name|ApplicationProperty
operator|.
name|DistanceEllipsoid
operator|.
name|value
argument_list|()
argument_list|)
decl_stmt|;
name|response
operator|.
name|setEllipsoid
argument_list|(
name|ellipsoid
operator|.
name|getEclipsoindName
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setGoogleMap
argument_list|(
name|ApplicationProperty
operator|.
name|RoomUseGoogleMap
operator|.
name|isTrue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|getAcademicSession
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Session
name|session
range|:
operator|(
name|List
argument_list|<
name|Session
argument_list|>
operator|)
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select f from Session f, Session s where "
operator|+
literal|"s.uniqueId = :sessionId and s.sessionBeginDateTime< f.sessionBeginDateTime and s.academicInitiative = f.academicInitiative "
operator|+
literal|"order by f.sessionBeginDateTime"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|response
operator|.
name|getAcademicSessionId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|AcademicSessionInterface
name|s
init|=
operator|new
name|AcademicSessionInterface
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|session
operator|.
name|getLabel
argument_list|()
argument_list|)
decl_stmt|;
name|EventContext
name|cx
init|=
operator|new
name|EventContext
argument_list|(
name|context
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|s
operator|.
name|setCanAddRoom
argument_list|(
name|cx
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|AddRoom
argument_list|)
argument_list|)
expr_stmt|;
name|s
operator|.
name|setCanAddNonUniversity
argument_list|(
name|cx
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|AddNonUnivLocation
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|isCanAddRoom
argument_list|()
operator|||
name|s
operator|.
name|isCanAddNonUniversity
argument_list|()
condition|)
name|response
operator|.
name|addFutureSession
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

