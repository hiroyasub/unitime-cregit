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
name|pointintimedata
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|access
operator|.
name|prepost
operator|.
name|PreAuthorize
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
name|PointInTimeDataReportsInterface
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
name|PointInTimeDataReportsInterface
operator|.
name|PITDParametersInterface
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
name|PointInTimeDataReportsInterface
operator|.
name|PITDParametersRpcRequest
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
name|reports
operator|.
name|pointintimedata
operator|.
name|BasePointInTimeDataReports
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

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|PITDParametersRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|PITDParametersBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|PITDParametersRpcRequest
argument_list|,
name|PITDParametersInterface
argument_list|>
block|{
specifier|protected
specifier|static
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
annotation|@
name|Autowired
specifier|private
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('PointInTimeDataReports')"
argument_list|)
specifier|public
name|PITDParametersInterface
name|execute
parameter_list|(
name|PITDParametersRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|PITDParametersInterface
name|ret
init|=
operator|new
name|PITDParametersInterface
argument_list|()
decl_stmt|;
for|for
control|(
name|BasePointInTimeDataReports
operator|.
name|Parameter
name|p
range|:
name|BasePointInTimeDataReports
operator|.
name|Parameter
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|p
operator|.
name|allowSingleSelection
argument_list|()
operator|&&
operator|!
name|p
operator|.
name|allowMultiSelection
argument_list|()
condition|)
continue|continue;
name|PointInTimeDataReportsInterface
operator|.
name|Parameter
name|parameter
init|=
operator|new
name|PointInTimeDataReportsInterface
operator|.
name|Parameter
argument_list|()
decl_stmt|;
name|parameter
operator|.
name|setMultiSelect
argument_list|(
name|p
operator|.
name|allowMultiSelection
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setName
argument_list|(
name|getLocalizedText
argument_list|(
name|p
argument_list|)
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setType
argument_list|(
name|p
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setTextField
argument_list|(
name|p
operator|.
name|isTextField
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|setDefaultTextValue
argument_list|(
name|p
operator|.
name|defaultValue
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|values
init|=
name|p
operator|.
name|values
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|.
name|isTextField
argument_list|()
condition|)
block|{
name|ret
operator|.
name|addParameter
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|values
operator|==
literal|null
operator|||
name|values
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|e
range|:
name|values
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|PointInTimeDataReportsInterface
operator|.
name|IdValue
name|v
init|=
operator|new
name|PointInTimeDataReportsInterface
operator|.
name|IdValue
argument_list|()
decl_stmt|;
name|v
operator|.
name|setText
argument_list|(
name|e
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|v
operator|.
name|setValue
argument_list|(
name|e
operator|.
name|getKey
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|parameter
operator|.
name|values
argument_list|()
operator|.
name|add
argument_list|(
name|v
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|parameter
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addParameter
argument_list|(
name|parameter
argument_list|)
expr_stmt|;
block|}
name|ret
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|private
name|String
name|getLocalizedText
parameter_list|(
name|BasePointInTimeDataReports
operator|.
name|Parameter
name|parameter
parameter_list|)
block|{
switch|switch
condition|(
name|parameter
condition|)
block|{
case|case
name|PITD
case|:
return|return
name|MESSAGES
operator|.
name|optionPointInTimeData
argument_list|()
return|;
case|case
name|PITD2
case|:
return|return
name|MESSAGES
operator|.
name|optionPointInTimeDataComparison
argument_list|()
return|;
case|case
name|BUILDING
case|:
return|return
name|MESSAGES
operator|.
name|optionBuilding
argument_list|()
return|;
case|case
name|BUILDINGS
case|:
return|return
name|MESSAGES
operator|.
name|optionBuildings
argument_list|()
return|;
case|case
name|DEPARTMENT
case|:
return|return
name|MESSAGES
operator|.
name|optionDepartment
argument_list|()
return|;
case|case
name|DEPARTMENTS
case|:
return|return
name|MESSAGES
operator|.
name|optionDepartments
argument_list|()
return|;
case|case
name|ROOM
case|:
return|return
name|MESSAGES
operator|.
name|optionRoom
argument_list|()
return|;
case|case
name|ROOMS
case|:
return|return
name|MESSAGES
operator|.
name|optionRooms
argument_list|()
return|;
case|case
name|SESSION
case|:
return|return
name|MESSAGES
operator|.
name|optionAcademicSession
argument_list|()
return|;
case|case
name|SUBJECT
case|:
return|return
name|MESSAGES
operator|.
name|optionSubjectArea
argument_list|()
return|;
case|case
name|SUBJECTS
case|:
return|return
name|MESSAGES
operator|.
name|optionSubjectAreas
argument_list|()
return|;
case|case
name|MINUTES_IN_REPORTING_HOUR
case|:
return|return
operator|(
name|MESSAGES
operator|.
name|optionMinutesInReportingHour
argument_list|()
operator|)
return|;
case|case
name|WEEKS_IN_REPORTING_TERM
case|:
return|return
operator|(
name|MESSAGES
operator|.
name|optionWeeksInReportingTerm
argument_list|()
operator|)
return|;
case|case
name|MINIMUM_LOCATION_CAPACITY
case|:
return|return
operator|(
name|MESSAGES
operator|.
name|optionMinimumLocationCapacity
argument_list|()
operator|)
return|;
case|case
name|MAXIMUM_LOCATION_CAPACITY
case|:
return|return
operator|(
name|MESSAGES
operator|.
name|optionMaximumLocationCapacity
argument_list|()
operator|)
return|;
case|case
name|DistributionType
case|:
return|return
name|MESSAGES
operator|.
name|optionDistributionType
argument_list|()
return|;
case|case
name|DistributionTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionDistributionTypes
argument_list|()
return|;
case|case
name|DemandOfferingType
case|:
return|return
name|MESSAGES
operator|.
name|optionDemandOfferingType
argument_list|()
return|;
case|case
name|DemandOfferingTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionDemandOfferingTypes
argument_list|()
return|;
case|case
name|OfferingConsentType
case|:
return|return
name|MESSAGES
operator|.
name|optionOfferingConsentType
argument_list|()
return|;
case|case
name|OfferingConsentTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionOfferingConsentTypes
argument_list|()
return|;
case|case
name|CourseCreditFormat
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseCreditFormat
argument_list|()
return|;
case|case
name|CourseCreditFormats
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseCreditFormats
argument_list|()
return|;
case|case
name|CourseCreditType
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseCreditType
argument_list|()
return|;
case|case
name|CourseCreditTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseCreditTypes
argument_list|()
return|;
case|case
name|CourseCreditUnitType
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseCreditUnitType
argument_list|()
return|;
case|case
name|CourseCreditUnitTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseCreditUnitTypes
argument_list|()
return|;
case|case
name|PositionType
case|:
return|return
name|MESSAGES
operator|.
name|optionPositionType
argument_list|()
return|;
case|case
name|PositionTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionPositionTypes
argument_list|()
return|;
case|case
name|DepartmentStatusType
case|:
return|return
name|MESSAGES
operator|.
name|optionDepartmentStatusType
argument_list|()
return|;
case|case
name|DepartmentStatusTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionDepartmentStatusTypes
argument_list|()
return|;
case|case
name|RoomType
case|:
return|return
name|MESSAGES
operator|.
name|optionRoomType
argument_list|()
return|;
case|case
name|RoomTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionRoomTypes
argument_list|()
return|;
case|case
name|StudentSectioningStatus
case|:
return|return
name|MESSAGES
operator|.
name|optionStudentSectioningStatus
argument_list|()
return|;
case|case
name|StudentSectioningStatuses
case|:
return|return
name|MESSAGES
operator|.
name|optionStudentSectioningStatuses
argument_list|()
return|;
case|case
name|ExamType
case|:
return|return
name|MESSAGES
operator|.
name|optionExamType
argument_list|()
return|;
case|case
name|ExamTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionExamTypes
argument_list|()
return|;
case|case
name|RoomFeatureType
case|:
return|return
name|MESSAGES
operator|.
name|optionRoomFeatureType
argument_list|()
return|;
case|case
name|RoomFeatureTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionRoomFeatureTypes
argument_list|()
return|;
case|case
name|CourseType
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseType
argument_list|()
return|;
case|case
name|CourseTypes
case|:
return|return
name|MESSAGES
operator|.
name|optionCourseTypes
argument_list|()
return|;
default|default:
return|return
name|parameter
operator|.
name|text
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

