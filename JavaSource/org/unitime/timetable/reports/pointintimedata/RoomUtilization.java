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
name|reports
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
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
name|NonUniversityLocation
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
name|PitClass
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
name|PointInTimeData
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
name|Room
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author says  *  */
end_comment

begin_class
specifier|public
class|class
name|RoomUtilization
extends|extends
name|BasePointInTimeDataReports
block|{
specifier|protected
name|ArrayList
argument_list|<
name|Long
argument_list|>
name|iDepartmentIds
decl_stmt|;
specifier|protected
name|ArrayList
argument_list|<
name|Long
argument_list|>
name|iRoomTypeIds
decl_stmt|;
specifier|public
name|RoomUtilization
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|getParameters
argument_list|()
operator|.
name|add
argument_list|(
name|Parameter
operator|.
name|DEPARTMENTS
argument_list|)
expr_stmt|;
name|getParameters
argument_list|()
operator|.
name|add
argument_list|(
name|Parameter
operator|.
name|RoomTypes
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|intializeHeader
parameter_list|()
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|hdr
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnRoomDepartmentCode
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnRoomDepartmentAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnRoomDepartmentName
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnBuilding
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnRoom
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnRoomType
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnStationHours
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnOccupancy
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnOrganizedWeeklyRoomHours
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnNotOrganizedWeeklyRoomHours
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnWeeklyRoomHours
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnOrganizedWeeklyStudentClassHours
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnNotOrganizedWeeklyStudentClassHours
argument_list|()
argument_list|)
expr_stmt|;
name|hdr
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnWeeklyStudentClassHours
argument_list|()
argument_list|)
expr_stmt|;
name|setHeader
argument_list|(
name|hdr
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|reportName
parameter_list|()
block|{
return|return
operator|(
name|MSG
operator|.
name|roomUtilizationReport
argument_list|()
operator|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|reportDescription
parameter_list|()
block|{
return|return
operator|(
name|MSG
operator|.
name|roomUtilizationReportNote
argument_list|()
operator|)
return|;
block|}
specifier|private
name|void
name|addRowForLocation
parameter_list|(
name|Location
name|location
parameter_list|,
name|LocationHours
name|locationHours
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|row
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|row
operator|.
name|add
argument_list|(
name|location
operator|.
name|getControllingDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|location
operator|.
name|getControllingDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|location
operator|.
name|getControllingDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|location
operator|.
name|getControllingDepartment
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|location
operator|.
name|getControllingDepartment
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|location
operator|.
name|getControllingDepartment
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|location
operator|instanceof
name|NonUniversityLocation
condition|)
block|{
name|NonUniversityLocation
name|nul
init|=
operator|(
name|NonUniversityLocation
operator|)
name|location
decl_stmt|;
name|row
operator|.
name|add
argument_list|(
name|nul
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Room
name|r
init|=
operator|(
name|Room
operator|)
name|location
decl_stmt|;
name|row
operator|.
name|add
argument_list|(
name|r
operator|.
name|getBuildingAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|r
operator|.
name|getRoomNumber
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|row
operator|.
name|add
argument_list|(
name|location
operator|.
name|getRoomTypeLabel
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
operator|(
name|Float
operator|.
name|toString
argument_list|(
name|location
operator|.
name|getCapacity
argument_list|()
operator|.
name|intValue
argument_list|()
operator|*
name|locationHours
operator|.
name|getWeeklyRoomHours
argument_list|()
argument_list|)
operator|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|Float
operator|.
name|toString
argument_list|(
name|locationHours
operator|.
name|getWeeklyStudentClassHours
argument_list|()
operator|/
operator|(
name|location
operator|.
name|getCapacity
argument_list|()
operator|.
name|intValue
argument_list|()
operator|*
name|locationHours
operator|.
name|getWeeklyRoomHours
argument_list|()
operator|)
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|Float
operator|.
name|toString
argument_list|(
name|locationHours
operator|.
name|getOrganizedWeeklyRoomHours
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|Float
operator|.
name|toString
argument_list|(
name|locationHours
operator|.
name|getNotOrganizedWeeklyRoomHours
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|Float
operator|.
name|toString
argument_list|(
name|locationHours
operator|.
name|getWeeklyRoomHours
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|Float
operator|.
name|toString
argument_list|(
name|locationHours
operator|.
name|getOrganizedWeeklyStudentClassHours
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|Float
operator|.
name|toString
argument_list|(
name|locationHours
operator|.
name|getNotOrganizedWeeklyStudentClassHours
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|row
operator|.
name|add
argument_list|(
name|Float
operator|.
name|toString
argument_list|(
name|locationHours
operator|.
name|getWeeklyStudentClassHours
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|addDataRow
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|createRoomUtilizationReportFor
parameter_list|(
name|PointInTimeData
name|pointInTimeData
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|HashMap
argument_list|<
name|Long
argument_list|,
name|RoomUtilization
operator|.
name|LocationHours
argument_list|>
name|locationUtilization
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|RoomUtilization
operator|.
name|LocationHours
argument_list|>
argument_list|()
decl_stmt|;
name|HashSet
argument_list|<
name|PitClass
argument_list|>
name|pitClasses
init|=
name|findAllPitClassesWithContactHoursForRoomDepartmentsAndRoomTypes
argument_list|(
name|pointInTimeData
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
for|for
control|(
name|PitClass
name|pc
range|:
name|pitClasses
control|)
block|{
for|for
control|(
name|Long
name|locationPermanentId
range|:
name|pc
operator|.
name|getLocationPermanentIdList
argument_list|()
control|)
block|{
name|LocationHours
name|lh
init|=
name|locationUtilization
operator|.
name|get
argument_list|(
name|locationPermanentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|lh
operator|==
literal|null
condition|)
block|{
name|lh
operator|=
operator|new
name|LocationHours
argument_list|(
name|locationPermanentId
argument_list|,
name|getStandardMinutesInReportingHour
argument_list|()
argument_list|,
name|getStandardWeeksInReportingTerm
argument_list|()
argument_list|)
expr_stmt|;
name|locationUtilization
operator|.
name|put
argument_list|(
name|locationPermanentId
argument_list|,
name|lh
argument_list|)
expr_stmt|;
block|}
name|lh
operator|.
name|addRoomHours
argument_list|(
name|pc
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|pointInTimeData
operator|.
name|getSession
argument_list|()
operator|.
name|getRooms
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|pointInTimeData
operator|.
name|getSession
argument_list|()
operator|.
name|getRooms
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|HashSet
argument_list|<
name|Location
argument_list|>
name|locations
init|=
operator|new
name|HashSet
argument_list|<
name|Location
argument_list|>
argument_list|()
decl_stmt|;
name|locations
operator|.
name|addAll
argument_list|(
name|pointInTimeData
operator|.
name|getSession
argument_list|()
operator|.
name|getRooms
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Location
name|l
range|:
name|locations
control|)
block|{
name|LocationHours
name|lh
init|=
name|locationUtilization
operator|.
name|get
argument_list|(
name|l
operator|.
name|getPermanentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|lh
operator|!=
literal|null
operator|&&
name|l
operator|.
name|getControllingDepartment
argument_list|()
operator|!=
literal|null
operator|&&
name|getDepartmentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|l
operator|.
name|getControllingDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|&&
name|getRoomTypeIds
argument_list|()
operator|.
name|contains
argument_list|(
name|l
operator|.
name|getRoomType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|addRowForLocation
argument_list|(
name|l
argument_list|,
name|lh
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|lh
operator|!=
literal|null
operator|&&
name|l
operator|.
name|getControllingDepartment
argument_list|()
operator|==
literal|null
operator|&&
name|getRoomTypeIds
argument_list|()
operator|.
name|contains
argument_list|(
name|l
operator|.
name|getRoomType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|addRowForLocation
argument_list|(
name|l
argument_list|,
name|lh
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|protected
name|HashSet
argument_list|<
name|PitClass
argument_list|>
name|findAllPitClassesWithContactHoursForRoomDepartmentsAndRoomTypes
parameter_list|(
name|PointInTimeData
name|pointInTimeData
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|HashSet
argument_list|<
name|PitClass
argument_list|>
name|pitClasses
init|=
operator|new
name|HashSet
argument_list|<
name|PitClass
argument_list|>
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"select pc"
argument_list|)
operator|.
name|append
argument_list|(
literal|" from PitClass pc "
argument_list|)
operator|.
name|append
argument_list|(
literal|" where pc.uniqueId in ( select pcm.pitClassEvent.pitClass.uniqueId from PitClassMeeting pcm, Location l inner join l.roomDepts as rd "
operator|+
literal|" where pcm.pitClassEvent.pitClass.pitSchedulingSubpart.pitInstrOfferingConfig.pitInstructionalOffering.pointInTimeData.uniqueId = :pitdUid"
argument_list|)
operator|.
name|append
argument_list|(
literal|" and pcm.locationPermanentId = l.permanentId"
argument_list|)
operator|.
name|append
argument_list|(
literal|" and l.session.uniqueId = pcm.pitClassEvent.pitClass.pitSchedulingSubpart.pitInstrOfferingConfig.pitInstructionalOffering.pointInTimeData.session.uniqueId"
argument_list|)
comment|//		  .append(" and rd.control = true")
operator|.
name|append
argument_list|(
literal|" and rd.department.uniqueId = :deptId"
argument_list|)
operator|.
name|append
argument_list|(
literal|" and l.roomType.uniqueId in ( "
argument_list|)
expr_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Long
name|rtId
range|:
name|getRoomTypeIds
argument_list|()
control|)
block|{
if|if
condition|(
name|first
condition|)
block|{
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|rtId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|" ) ) "
argument_list|)
expr_stmt|;
empty_stmt|;
for|for
control|(
name|Long
name|deptId
range|:
name|getDepartmentIds
argument_list|()
control|)
block|{
name|pitClasses
operator|.
name|addAll
argument_list|(
operator|(
name|List
argument_list|<
name|PitClass
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"pitdUid"
argument_list|,
name|pointInTimeData
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|deptId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|pitClasses
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|parseParameters
parameter_list|()
block|{
name|super
operator|.
name|parseParameters
argument_list|()
expr_stmt|;
if|if
condition|(
name|getParameterValues
argument_list|()
operator|.
name|get
argument_list|(
name|Parameter
operator|.
name|DEPARTMENTS
argument_list|)
operator|.
name|size
argument_list|()
operator|<
literal|1
condition|)
block|{
comment|//TODO: error
block|}
else|else
block|{
name|setDepartmentIds
argument_list|(
name|getParameterValues
argument_list|()
operator|.
name|get
argument_list|(
name|Parameter
operator|.
name|DEPARTMENTS
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getParameterValues
argument_list|()
operator|.
name|get
argument_list|(
name|Parameter
operator|.
name|RoomTypes
argument_list|)
operator|.
name|size
argument_list|()
operator|<
literal|1
condition|)
block|{
comment|//TODO: error
block|}
else|else
block|{
name|setRoomTypeIds
argument_list|(
name|getParameterValues
argument_list|()
operator|.
name|get
argument_list|(
name|Parameter
operator|.
name|RoomTypes
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|void
name|runReport
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|PointInTimeData
name|pitd
init|=
operator|(
name|PointInTimeData
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from PointInTimeData pitd where pitd.uniqueId = :uid"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uid"
argument_list|,
name|getPointInTimeDataUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
name|createRoomUtilizationReportFor
argument_list|(
name|pitd
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ArrayList
argument_list|<
name|Long
argument_list|>
name|getDepartmentIds
parameter_list|()
block|{
return|return
name|iDepartmentIds
return|;
block|}
specifier|public
name|void
name|setDepartmentIds
parameter_list|(
name|ArrayList
argument_list|<
name|Object
argument_list|>
name|departmentIds
parameter_list|)
block|{
name|this
operator|.
name|iDepartmentIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|departmentIds
control|)
block|{
name|this
operator|.
name|iDepartmentIds
operator|.
name|add
argument_list|(
operator|(
name|Long
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|ArrayList
argument_list|<
name|Long
argument_list|>
name|getRoomTypeIds
parameter_list|()
block|{
return|return
name|iRoomTypeIds
return|;
block|}
specifier|public
name|void
name|setRoomTypeIds
parameter_list|(
name|ArrayList
argument_list|<
name|Object
argument_list|>
name|roomTypeIds
parameter_list|)
block|{
name|this
operator|.
name|iRoomTypeIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|roomTypeIds
control|)
block|{
name|this
operator|.
name|iRoomTypeIds
operator|.
name|add
argument_list|(
operator|(
name|Long
operator|)
name|o
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
class|class
name|LocationHours
block|{
specifier|private
name|float
name|iWeeklyStudentClassHours
init|=
literal|0.0f
decl_stmt|;
specifier|private
name|float
name|iOrganizedWeeklyStudentClassHours
init|=
literal|0.0f
decl_stmt|;
specifier|private
name|float
name|iNotOrganizedWeeklyStudentClassHours
init|=
literal|0.0f
decl_stmt|;
specifier|private
name|Long
name|iLocationPermanentId
decl_stmt|;
specifier|private
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|organizedPeriods
init|=
operator|new
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|notOrganizedPeriods
init|=
operator|new
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|allPeriods
init|=
operator|new
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Float
name|iStandardMinutesInReportingHour
decl_stmt|;
specifier|private
name|Float
name|iStandardWeeksInReportingTerm
decl_stmt|;
specifier|private
name|HashSet
argument_list|<
name|Long
argument_list|>
name|classIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|Long
name|getLocationPermanentId
parameter_list|()
block|{
return|return
name|iLocationPermanentId
return|;
block|}
specifier|public
name|float
name|getOrganizedWeeklyRoomHours
parameter_list|()
block|{
return|return
operator|(
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
literal|1.0f
operator|)
operator|*
name|organizedPeriods
operator|.
name|size
argument_list|()
operator|/
name|iStandardMinutesInReportingHour
operator|/
name|iStandardWeeksInReportingTerm
operator|)
return|;
block|}
specifier|public
name|float
name|getNotOrganizedWeeklyRoomHours
parameter_list|()
block|{
return|return
operator|(
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
literal|1.0f
operator|)
operator|*
name|notOrganizedPeriods
operator|.
name|size
argument_list|()
operator|/
name|iStandardMinutesInReportingHour
operator|/
name|iStandardWeeksInReportingTerm
operator|)
return|;
block|}
specifier|public
name|float
name|getOrganizedWeeklyStudentClassHours
parameter_list|()
block|{
return|return
name|iOrganizedWeeklyStudentClassHours
return|;
block|}
specifier|public
name|float
name|getNotOrganizedWeeklyStudentClassHours
parameter_list|()
block|{
return|return
name|iNotOrganizedWeeklyStudentClassHours
return|;
block|}
specifier|public
name|float
name|getWeeklyRoomHours
parameter_list|()
block|{
return|return
operator|(
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
literal|1.0f
operator|)
operator|*
name|allPeriods
operator|.
name|size
argument_list|()
operator|/
name|iStandardMinutesInReportingHour
operator|/
name|iStandardWeeksInReportingTerm
operator|)
return|;
block|}
specifier|public
name|float
name|getWeeklyStudentClassHours
parameter_list|()
block|{
return|return
name|this
operator|.
name|iWeeklyStudentClassHours
return|;
block|}
specifier|public
name|LocationHours
parameter_list|(
name|Long
name|locationPermanentId
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
name|this
operator|.
name|iLocationPermanentId
operator|=
name|locationPermanentId
expr_stmt|;
name|this
operator|.
name|iStandardMinutesInReportingHour
operator|=
name|standardMinutesInReportingHour
expr_stmt|;
name|this
operator|.
name|iStandardWeeksInReportingTerm
operator|=
name|standardWeeksInReportingTerm
expr_stmt|;
block|}
specifier|private
name|void
name|addPeriods
parameter_list|(
name|PitClass
name|pitClass
parameter_list|)
block|{
if|if
condition|(
name|pitClass
operator|.
name|getPeriodsForLocation
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|pitClass
operator|.
name|isOrganized
argument_list|()
condition|)
block|{
name|organizedPeriods
operator|.
name|addAll
argument_list|(
name|pitClass
operator|.
name|getPeriodsForLocation
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|notOrganizedPeriods
operator|.
name|addAll
argument_list|(
name|pitClass
operator|.
name|getPeriodsForLocation
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|allPeriods
operator|.
name|addAll
argument_list|(
name|pitClass
operator|.
name|getPeriodsForLocation
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|addRoomHours
parameter_list|(
name|PitClass
name|pitClass
parameter_list|)
block|{
if|if
condition|(
name|classIds
operator|.
name|contains
argument_list|(
name|pitClass
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Error
argument_list|(
literal|"Counted class twice:  "
operator|+
name|pitClass
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
name|classIds
operator|.
name|add
argument_list|(
name|pitClass
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|addPeriods
argument_list|(
name|pitClass
argument_list|)
expr_stmt|;
name|iWeeklyStudentClassHours
operator|+=
operator|(
name|pitClass
operator|.
name|getAllWeeklyStudentClassHoursForLocation
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|,
name|iStandardMinutesInReportingHour
argument_list|,
name|iStandardWeeksInReportingTerm
argument_list|)
operator|)
expr_stmt|;
name|iOrganizedWeeklyStudentClassHours
operator|+=
operator|(
name|pitClass
operator|.
name|getOrganizedWeeklyStudentClassHoursForLocation
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|,
name|iStandardMinutesInReportingHour
argument_list|,
name|iStandardWeeksInReportingTerm
argument_list|)
operator|)
expr_stmt|;
name|iNotOrganizedWeeklyStudentClassHours
operator|+=
operator|(
name|pitClass
operator|.
name|getNotOrganizedWeeklyStudentClassHoursForLocation
argument_list|(
name|getLocationPermanentId
argument_list|()
argument_list|,
name|iStandardMinutesInReportingHour
argument_list|,
name|iStandardWeeksInReportingTerm
argument_list|)
operator|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

