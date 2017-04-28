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
name|model
operator|.
name|base
operator|.
name|BasePitClass
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

begin_class
specifier|public
class|class
name|PitClass
extends|extends
name|BasePitClass
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|642241232877633526L
decl_stmt|;
specifier|private
name|float
name|weeklyClassHours
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|float
name|weeklyStudentClassHours
init|=
operator|-
literal|1
decl_stmt|;
specifier|public
name|PitClass
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|private
name|HashMap
argument_list|<
name|Long
argument_list|,
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
argument_list|>
name|locationPeriodUseMap
init|=
literal|null
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Long
argument_list|>
name|locationPermanentIdList
init|=
literal|null
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
name|uniquePeriods
init|=
literal|null
decl_stmt|;
specifier|private
name|float
name|calculateWCHforLocationFromPeriods
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
name|float
name|minutesInReportingHour
init|=
operator|(
name|standardMinutesInReportingHour
operator|==
literal|null
condition|?
name|ApplicationProperty
operator|.
name|StandardMinutesInReportingHour
operator|.
name|floatValue
argument_list|()
else|:
name|standardMinutesInReportingHour
operator|.
name|floatValue
argument_list|()
operator|)
decl_stmt|;
name|float
name|weeksInReportingTerm
init|=
operator|(
name|standardWeeksInReportingTerm
operator|==
literal|null
condition|?
name|ApplicationProperty
operator|.
name|StandardWeeksInReportingTerm
operator|.
name|floatValue
argument_list|()
else|:
name|standardWeeksInReportingTerm
operator|)
decl_stmt|;
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|periods
init|=
name|getLocationPeriodUseMap
argument_list|()
operator|.
name|get
argument_list|(
name|location
argument_list|)
decl_stmt|;
if|if
condition|(
name|periods
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|periods
operator|.
name|size
argument_list|()
operator|*
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
literal|1.0f
operator|)
operator|/
name|minutesInReportingHour
operator|/
name|weeksInReportingTerm
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0f
operator|)
return|;
block|}
block|}
specifier|public
name|HashMap
argument_list|<
name|Long
argument_list|,
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
argument_list|>
name|getLocationPeriodUseMap
parameter_list|()
block|{
if|if
condition|(
name|locationPeriodUseMap
operator|==
literal|null
condition|)
block|{
name|initializeRoomData
argument_list|()
expr_stmt|;
block|}
return|return
operator|(
name|locationPeriodUseMap
operator|)
return|;
block|}
specifier|public
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|getUniquePeriods
parameter_list|()
block|{
if|if
condition|(
name|uniquePeriods
operator|==
literal|null
condition|)
block|{
name|initializeRoomData
argument_list|()
expr_stmt|;
block|}
return|return
operator|(
name|uniquePeriods
operator|)
return|;
block|}
specifier|private
name|void
name|initializeRoomData
parameter_list|()
block|{
name|locationPeriodUseMap
operator|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|PitClassEvent
name|pce
range|:
name|this
operator|.
name|getPitClassEvents
argument_list|()
control|)
block|{
for|for
control|(
name|PitClassMeeting
name|pcm
range|:
name|pce
operator|.
name|getPitClassMeetings
argument_list|()
control|)
block|{
if|if
condition|(
operator|(
name|this
operator|.
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getPitInstrOfferingConfig
argument_list|()
operator|.
name|getPitInstructionalOffering
argument_list|()
operator|.
name|getPointInTimeData
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionBeginDateTime
argument_list|()
operator|.
name|compareTo
argument_list|(
name|pcm
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|<=
literal|0
operator|)
operator|&&
name|this
operator|.
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getPitInstrOfferingConfig
argument_list|()
operator|.
name|getPitInstructionalOffering
argument_list|()
operator|.
name|getPointInTimeData
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getClassesEndDateTime
argument_list|()
operator|.
name|compareTo
argument_list|(
name|pcm
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|>=
literal|0
condition|)
block|{
for|for
control|(
name|PitClassMeetingUtilPeriod
name|pcmup
range|:
name|pcm
operator|.
name|getPitClassMeetingUtilPeriods
argument_list|()
control|)
block|{
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|periods
init|=
name|locationPeriodUseMap
operator|.
name|get
argument_list|(
name|pcm
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|periods
operator|==
literal|null
condition|)
block|{
name|periods
operator|=
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
expr_stmt|;
name|locationPeriodUseMap
operator|.
name|put
argument_list|(
name|pcm
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|,
name|periods
argument_list|)
expr_stmt|;
block|}
name|periods
operator|.
name|add
argument_list|(
name|pcmup
operator|.
name|periodDateTime
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|locationPermanentIdList
operator|=
name|locationPeriodUseMap
operator|.
name|keySet
argument_list|()
expr_stmt|;
name|uniquePeriods
operator|=
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
expr_stmt|;
for|for
control|(
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|periods
range|:
name|locationPeriodUseMap
operator|.
name|values
argument_list|()
control|)
block|{
name|uniquePeriods
operator|.
name|addAll
argument_list|(
name|periods
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getLocationPermanentIdList
parameter_list|()
block|{
if|if
condition|(
name|locationPermanentIdList
operator|==
literal|null
condition|)
block|{
name|initializeRoomData
argument_list|()
expr_stmt|;
block|}
return|return
operator|(
name|locationPermanentIdList
operator|)
return|;
block|}
specifier|public
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|getPeriodsForLocation
parameter_list|(
name|Long
name|location
parameter_list|)
block|{
return|return
operator|(
name|getLocationPeriodUseMap
argument_list|()
operator|.
name|get
argument_list|(
name|location
argument_list|)
operator|)
return|;
block|}
specifier|public
name|boolean
name|isOrganized
parameter_list|()
block|{
return|return
operator|(
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|)
return|;
block|}
specifier|public
name|float
name|countRoomsForPeriod
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|period
parameter_list|)
block|{
name|float
name|cnt
init|=
literal|0f
decl_stmt|;
for|for
control|(
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|periods
range|:
name|getLocationPeriodUseMap
argument_list|()
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
name|periods
operator|.
name|contains
argument_list|(
name|period
argument_list|)
condition|)
block|{
name|cnt
operator|++
expr_stmt|;
block|}
block|}
return|return
operator|(
name|cnt
operator|)
return|;
block|}
specifier|private
name|float
name|calculateWSCHfromPeriodsForLocation
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
name|float
name|minutesInReportingHour
init|=
operator|(
name|standardMinutesInReportingHour
operator|==
literal|null
condition|?
name|ApplicationProperty
operator|.
name|StandardMinutesInReportingHour
operator|.
name|floatValue
argument_list|()
else|:
name|standardMinutesInReportingHour
operator|.
name|floatValue
argument_list|()
operator|)
decl_stmt|;
name|float
name|weeksInReportingTerm
init|=
operator|(
name|standardWeeksInReportingTerm
operator|==
literal|null
condition|?
name|ApplicationProperty
operator|.
name|StandardWeeksInReportingTerm
operator|.
name|floatValue
argument_list|()
else|:
name|standardWeeksInReportingTerm
operator|)
decl_stmt|;
name|HashSet
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|>
name|periods
init|=
name|getLocationPeriodUseMap
argument_list|()
operator|.
name|get
argument_list|(
name|location
argument_list|)
decl_stmt|;
name|float
name|wrm
init|=
literal|0.0f
decl_stmt|;
for|for
control|(
name|java
operator|.
name|util
operator|.
name|Date
name|period
range|:
name|periods
control|)
block|{
name|wrm
operator|+=
operator|(
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
literal|1.0f
operator|)
operator|/
name|countRoomsForPeriod
argument_list|(
name|period
argument_list|)
operator|)
expr_stmt|;
block|}
return|return
operator|(
name|wrm
operator|*
name|getStudentEnrollments
argument_list|()
operator|.
name|size
argument_list|()
operator|/
name|minutesInReportingHour
operator|/
name|weeksInReportingTerm
operator|)
return|;
block|}
specifier|private
name|float
name|calculateWCH
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
name|float
name|minutesInReportingHour
init|=
operator|(
name|standardMinutesInReportingHour
operator|==
literal|null
condition|?
name|ApplicationProperty
operator|.
name|StandardMinutesInReportingHour
operator|.
name|floatValue
argument_list|()
else|:
name|standardMinutesInReportingHour
operator|.
name|floatValue
argument_list|()
operator|)
decl_stmt|;
name|float
name|weeksInReportingTerm
init|=
operator|(
name|standardWeeksInReportingTerm
operator|==
literal|null
condition|?
name|ApplicationProperty
operator|.
name|StandardWeeksInReportingTerm
operator|.
name|floatValue
argument_list|()
else|:
name|standardWeeksInReportingTerm
operator|)
decl_stmt|;
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
name|getUniquePeriods
argument_list|()
operator|.
name|size
argument_list|()
operator|/
name|minutesInReportingHour
operator|/
name|weeksInReportingTerm
operator|)
return|;
block|}
specifier|private
name|float
name|calculateWSCH
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
return|return
operator|(
name|getAllWeeklyClassHours
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|*
name|this
operator|.
name|getStudentEnrollments
argument_list|()
operator|.
name|size
argument_list|()
operator|)
return|;
block|}
specifier|public
name|float
name|getOrganizedWeeklyRoomHoursFromUtilPeriodsForLocation
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|calculateWCHforLocationFromPeriods
argument_list|(
name|location
argument_list|,
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|float
name|getAllWeeklyRoomHoursFromUtilPeriodsForLocation
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
return|return
operator|(
name|calculateWCHforLocationFromPeriods
argument_list|(
name|location
argument_list|,
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
specifier|public
name|float
name|getNotOrganizedWeeklyRoomHoursFromUtilPeriodsForLocation
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
operator|!
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|calculateWCHforLocationFromPeriods
argument_list|(
name|location
argument_list|,
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|float
name|getOrganizedWeeklyClassHours
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|getAllWeeklyClassHours
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|float
name|getNotOrganizedWeeklyClassHours
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
operator|!
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|getAllWeeklyClassHours
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|float
name|getOrganizedWeeklyStudentClassHours
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|getAllWeeklyStudentClassHours
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|float
name|getNotOrganizedWeeklyStudentClassHours
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
operator|!
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|getAllWeeklyStudentClassHours
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|float
name|getAllWeeklyClassHours
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|weeklyClassHours
operator|<
literal|0
condition|)
block|{
name|this
operator|.
name|weeklyClassHours
operator|=
name|calculateWCH
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|this
operator|.
name|weeklyClassHours
operator|)
return|;
block|}
specifier|public
name|float
name|getAllWeeklyStudentClassHours
parameter_list|(
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|weeklyStudentClassHours
operator|<
literal|0
condition|)
block|{
name|this
operator|.
name|weeklyStudentClassHours
operator|=
name|calculateWSCH
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|this
operator|.
name|weeklyStudentClassHours
operator|)
return|;
block|}
specifier|public
name|float
name|getAllWeeklyStudentClassHoursForLocation
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
return|return
operator|(
name|calculateWSCHfromPeriodsForLocation
argument_list|(
name|location
argument_list|,
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
specifier|public
name|float
name|getOrganizedWeeklyStudentClassHoursForLocation
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|getAllWeeklyStudentClassHoursForLocation
argument_list|(
name|location
argument_list|,
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|float
name|getNotOrganizedWeeklyStudentClassHoursForLocation
parameter_list|(
name|Long
name|location
parameter_list|,
name|Float
name|standardMinutesInReportingHour
parameter_list|,
name|Float
name|standardWeeksInReportingTerm
parameter_list|)
block|{
if|if
condition|(
operator|!
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|isOrganized
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
return|return
operator|(
name|getAllWeeklyStudentClassHoursForLocation
argument_list|(
name|location
argument_list|,
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|0
operator|)
return|;
block|}
block|}
specifier|public
name|HashMap
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|,
name|Float
argument_list|>
name|findPeriodEnrollmentsForCriteria
parameter_list|(
name|HashSet
argument_list|<
name|Long
argument_list|>
name|validRoomPermanentIds
parameter_list|,
name|ArrayList
argument_list|<
name|Long
argument_list|>
name|positionTypes
parameter_list|)
block|{
name|HashMap
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|,
name|Float
argument_list|>
name|periodEnrollmentMap
init|=
operator|new
name|HashMap
argument_list|<
name|java
operator|.
name|util
operator|.
name|Date
argument_list|,
name|Float
argument_list|>
argument_list|()
decl_stmt|;
name|float
name|ratio
init|=
literal|0.0f
decl_stmt|;
if|if
condition|(
name|positionTypes
operator|!=
literal|null
operator|&&
operator|!
name|positionTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|this
operator|.
name|getPitClassInstructors
argument_list|()
operator|==
literal|null
operator|||
name|this
operator|.
name|getPitClassInstructors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|positionTypes
operator|.
name|contains
argument_list|(
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|ratio
operator|=
literal|1.0f
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|PitClassInstructor
name|pci
range|:
name|this
operator|.
name|getPitClassInstructors
argument_list|()
control|)
block|{
if|if
condition|(
name|pci
operator|.
name|getPitDepartmentalInstructor
argument_list|()
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
operator|&&
name|positionTypes
operator|.
name|contains
argument_list|(
name|pci
operator|.
name|getPitDepartmentalInstructor
argument_list|()
operator|.
name|getPositionType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
block|{
name|ratio
operator|+=
operator|(
name|pci
operator|.
name|getNormalizedPercentShare
argument_list|()
operator|.
name|intValue
argument_list|()
operator|/
literal|100.0f
operator|)
expr_stmt|;
block|}
if|else if
condition|(
name|pci
operator|.
name|getPitDepartmentalInstructor
argument_list|()
operator|.
name|getPositionType
argument_list|()
operator|==
literal|null
operator|&&
name|positionTypes
operator|.
name|contains
argument_list|(
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|ratio
operator|+=
operator|(
name|pci
operator|.
name|getNormalizedPercentShare
argument_list|()
operator|.
name|intValue
argument_list|()
operator|/
literal|100.0f
operator|)
expr_stmt|;
block|}
block|}
block|}
block|}
else|else
block|{
name|ratio
operator|=
literal|1.0f
expr_stmt|;
block|}
name|HashSet
argument_list|<
name|Long
argument_list|>
name|applicableLocationPermananetIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Long
name|permId
range|:
name|getLocationPermanentIdList
argument_list|()
control|)
block|{
if|if
condition|(
name|validRoomPermanentIds
operator|.
name|contains
argument_list|(
name|permId
argument_list|)
condition|)
block|{
name|applicableLocationPermananetIds
operator|.
name|add
argument_list|(
name|permId
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|applicableLocationPermananetIds
operator|.
name|size
argument_list|()
operator|==
name|getLocationPermanentIdList
argument_list|()
operator|.
name|size
argument_list|()
condition|)
block|{
for|for
control|(
name|java
operator|.
name|util
operator|.
name|Date
name|period
range|:
name|uniquePeriods
control|)
block|{
name|periodEnrollmentMap
operator|.
name|put
argument_list|(
name|period
argument_list|,
operator|(
name|this
operator|.
name|getEnrollment
argument_list|()
operator|.
name|intValue
argument_list|()
operator|*
name|ratio
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|PitClassEvent
name|pce
range|:
name|this
operator|.
name|getPitClassEvents
argument_list|()
control|)
block|{
for|for
control|(
name|PitClassMeeting
name|pcm
range|:
name|pce
operator|.
name|getPitClassMeetings
argument_list|()
control|)
block|{
if|if
condition|(
operator|(
name|this
operator|.
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getPitInstrOfferingConfig
argument_list|()
operator|.
name|getPitInstructionalOffering
argument_list|()
operator|.
name|getPointInTimeData
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionBeginDateTime
argument_list|()
operator|.
name|compareTo
argument_list|(
name|pcm
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|<=
literal|0
operator|)
operator|&&
name|this
operator|.
name|getPitSchedulingSubpart
argument_list|()
operator|.
name|getPitInstrOfferingConfig
argument_list|()
operator|.
name|getPitInstructionalOffering
argument_list|()
operator|.
name|getPointInTimeData
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getClassesEndDateTime
argument_list|()
operator|.
name|compareTo
argument_list|(
name|pcm
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|>=
literal|0
condition|)
block|{
if|if
condition|(
name|applicableLocationPermananetIds
operator|.
name|contains
argument_list|(
name|pcm
operator|.
name|getLocationPermanentId
argument_list|()
argument_list|)
condition|)
block|{
for|for
control|(
name|PitClassMeetingUtilPeriod
name|pcmup
range|:
name|pcm
operator|.
name|getPitClassMeetingUtilPeriods
argument_list|()
control|)
block|{
name|periodEnrollmentMap
operator|.
name|put
argument_list|(
name|pcmup
operator|.
name|periodDateTime
argument_list|()
argument_list|,
operator|(
name|this
operator|.
name|getEnrollment
argument_list|()
operator|.
name|intValue
argument_list|()
operator|*
operator|(
literal|1.0f
operator|/
name|countRoomsForPeriod
argument_list|(
name|pcmup
operator|.
name|periodDateTime
argument_list|()
argument_list|)
operator|)
operator|*
name|ratio
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
return|return
operator|(
name|periodEnrollmentMap
operator|)
return|;
block|}
block|}
end_class

end_unit

