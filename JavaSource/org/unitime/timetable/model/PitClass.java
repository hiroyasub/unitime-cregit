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
name|weeklyContactHours
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
name|float
name|weeklyStudentContactHours
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
name|float
name|calculateWCHfromPeriods
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
name|float
name|contactPeriods
init|=
literal|0
decl_stmt|;
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
name|getSessionEndDateTime
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
name|contactPeriods
operator|+=
name|pcm
operator|.
name|getPitClassMeetingUtilPeriods
argument_list|()
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|contactPeriods
operator|*
literal|5
operator|/
name|roomCount
argument_list|()
operator|.
name|floatValue
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
name|float
name|contactMinutes
init|=
literal|0
decl_stmt|;
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
name|getSessionEndDateTime
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
name|contactMinutes
operator|+=
name|pcm
operator|.
name|getCalculatedMinPerMtg
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|contactMinutes
operator|/
name|roomCount
argument_list|()
operator|.
name|floatValue
argument_list|()
operator|/
name|minutesInReportingHour
operator|/
name|weeksInReportingTerm
operator|)
return|;
block|}
specifier|private
name|Integer
name|roomCount
parameter_list|()
block|{
if|if
condition|(
name|getNbrRooms
argument_list|()
operator|==
literal|null
operator|||
name|getNbrRooms
argument_list|()
operator|.
name|intValue
argument_list|()
operator|<=
literal|0
condition|)
block|{
return|return
operator|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
operator|)
return|;
block|}
return|return
operator|(
name|getNbrRooms
argument_list|()
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
name|calculateWCH
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
name|getOrganizedWeeklyContactHoursFromUtilPeriods
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
name|getAllWeeklyContactHoursFromUtilPeriods
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
name|getAllWeeklyContactHoursFromUtilPeriods
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
name|calculateWCHfromPeriods
argument_list|(
name|standardMinutesInReportingHour
argument_list|,
name|standardWeeksInReportingTerm
argument_list|)
operator|)
return|;
block|}
specifier|public
name|float
name|getOrganizedWeeklyContactHours
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
name|getAllWeeklyContactHours
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
name|getNotOrganizedWeeklyContactHours
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
name|getAllWeeklyContactHours
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
name|getOrganizedWeeklyStudentContactHours
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
name|getAllWeeklyStudentContactHours
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
name|getNotOrganizedWeeklyStudentContactHours
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
name|getAllWeeklyStudentContactHours
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
name|getAllWeeklyContactHours
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
name|weeklyContactHours
operator|<
literal|0
condition|)
block|{
name|this
operator|.
name|weeklyContactHours
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
name|weeklyContactHours
operator|)
return|;
block|}
specifier|public
name|float
name|getAllWeeklyStudentContactHours
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
name|weeklyStudentContactHours
operator|<
literal|0
condition|)
block|{
name|this
operator|.
name|weeklyStudentContactHours
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
name|weeklyStudentContactHours
operator|)
return|;
block|}
block|}
end_class

end_unit
