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
name|resources
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|GwtConstants
extends|extends
name|Constants
block|{
annotation|@
name|DefaultStringValue
argument_list|(
literal|"4.1"
argument_list|)
annotation|@
name|DoNotTranslate
name|String
name|version
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"&copy; 2008 - 2016 The Apereo Foundation"
argument_list|)
annotation|@
name|DoNotTranslate
name|String
name|copyright
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|true
argument_list|)
annotation|@
name|DoNotTranslate
name|boolean
name|useAmPm
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|false
argument_list|)
annotation|@
name|DoNotTranslate
name|boolean
name|firstDayThenMonth
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Mon"
block|,
literal|"Tue"
block|,
literal|"Wed"
block|,
literal|"Thu"
block|,
literal|"Fri"
block|,
literal|"Sat"
block|,
literal|"Sun"
block|}
argument_list|)
name|String
index|[]
name|days
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
name|String
name|eventDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd"
argument_list|)
name|String
name|eventDateFormatShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd, yyyy"
argument_list|)
name|String
name|eventDateFormatLong
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"EEE MM/dd, yyyy"
argument_list|)
name|String
name|meetingDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"EEE MM/dd"
argument_list|)
name|String
name|examPeriodDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"EEE"
block|,
literal|"MM/dd"
block|}
argument_list|)
name|String
index|[]
name|examPeriodPreferenceDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd/yyyy hh:mmaa"
argument_list|)
name|String
name|timeStampFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd hh:mmaa"
argument_list|)
name|String
name|timeStampFormatShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|3
argument_list|)
annotation|@
name|DoNotTranslate
name|int
name|eventSlotIncrement
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|90
argument_list|)
annotation|@
name|DoNotTranslate
name|int
name|eventStartDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|210
argument_list|)
annotation|@
name|DoNotTranslate
name|int
name|eventStopDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|12
argument_list|)
annotation|@
name|DoNotTranslate
name|int
name|eventLengthDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|10000
argument_list|)
annotation|@
name|DoNotTranslate
name|int
name|maxMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Monday"
block|,
literal|"Tuesday"
block|,
literal|"Wednesday"
block|,
literal|"Thursday"
block|,
literal|"Friday"
block|,
literal|"Saturday"
block|,
literal|"Sunday"
block|}
argument_list|)
name|String
index|[]
name|longDays
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"M"
block|,
literal|"T"
block|,
literal|"W"
block|,
literal|"Th"
block|,
literal|"F"
block|,
literal|"S"
block|,
literal|"Su"
block|}
argument_list|)
name|String
index|[]
name|shortDays
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"midnight"
argument_list|)
name|String
name|timeMidnight
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"midnight"
argument_list|)
name|String
name|timeMidnightEnd
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"noon"
argument_list|)
name|String
name|timeNoon
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"all day"
argument_list|)
name|String
name|timeAllDay
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"pm"
argument_list|)
name|String
name|timePm
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"am"
argument_list|)
name|String
name|timeAm
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"p"
argument_list|)
name|String
name|timeShortPm
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"a"
argument_list|)
name|String
name|timeShortAm
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"h"
block|,
literal|"hr"
block|,
literal|"hrs"
block|,
literal|"hour"
block|,
literal|"hours"
block|}
argument_list|)
name|String
index|[]
name|parseTimeHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"m"
block|,
literal|"min"
block|,
literal|"mins"
block|,
literal|"minute"
block|,
literal|"minutes"
block|}
argument_list|)
name|String
index|[]
name|parseTimeMinutes
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"am"
block|,
literal|"a"
block|}
argument_list|)
name|String
index|[]
name|parseTimeAm
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"pm"
block|,
literal|"p"
block|}
argument_list|)
name|String
index|[]
name|parseTimePm
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"noon"
block|}
argument_list|)
name|String
index|[]
name|parseTimeNoon
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"midnight"
block|}
argument_list|)
name|String
index|[]
name|parseTimeMidnight
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"Daily"
argument_list|)
name|String
name|daily
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"Arrange Hours"
argument_list|)
name|String
name|arrangeHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"blue"
block|,
literal|"green"
block|,
literal|"orange"
block|,
literal|"yellow"
block|,
literal|"pink"
block|,
literal|"purple"
block|,
literal|"teal"
block|,
literal|"darkpurple"
block|,
literal|"steelblue"
block|,
literal|"lightblue"
block|,
literal|"lightgreen"
block|,
literal|"yellowgreen"
block|,
literal|"redorange"
block|,
literal|"lightbrown"
block|,
literal|"lightpurple"
block|,
literal|"grey"
block|,
literal|"bluegrey"
block|,
literal|"lightteal"
block|,
literal|"yellowgrey"
block|,
literal|"brown"
block|,
literal|"red"
block|}
argument_list|)
annotation|@
name|DoNotTranslate
name|String
index|[]
name|meetingColors
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Room Timetable"
block|,
literal|"Subject Timetable"
block|,
literal|"Curriculum Timetable"
block|,
literal|"Departmental Timetable"
block|,
literal|"Personal Timetable"
block|,
literal|"Course Timetable"
block|,
literal|"Student Group Timetable"
block|}
argument_list|)
name|String
index|[]
name|resourceType
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Room"
block|,
literal|"Subject"
block|,
literal|"Curriculum"
block|,
literal|"Department"
block|,
literal|"Person"
block|,
literal|"Course"
block|,
literal|"Student Group"
block|}
argument_list|)
name|String
index|[]
name|resourceName
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Class Event"
block|,
literal|"Final Examination Event"
block|,
literal|"Midterm Examination Event"
block|,
literal|"Course Related Event"
block|,
literal|"Special Event"
block|,
literal|"Not Available"
block|,
literal|"Message"
block|}
argument_list|)
name|String
index|[]
name|eventTypeName
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Class"
block|,
literal|"Final Examination"
block|,
literal|"Midterm Examination"
block|,
literal|"Course"
block|,
literal|"Special"
block|,
literal|"Not Available"
block|,
literal|"Message"
block|}
argument_list|)
name|String
index|[]
name|eventTypeAbbv
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Class"
block|,
literal|"Final"
block|,
literal|"Midterm"
block|,
literal|"Course"
block|,
literal|"Special"
block|,
literal|"N/A"
block|,
literal|"Message"
block|}
argument_list|)
name|String
index|[]
name|eventTypeShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Pending"
block|,
literal|"Approved"
block|,
literal|"Rejected"
block|,
literal|"Cancelled"
block|}
argument_list|)
name|String
index|[]
name|eventApprovalStatus
parameter_list|()
function_decl|;
comment|// firstDay|lastDay|firstSlot|lastSlot|step
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Workdays \u00d7 Daytime|0|4|90|222|6"
block|,
literal|"All Week \u00d7 Daytime|0|6|90|222|6"
block|,
literal|"Workdays \u00d7 Evening|0|4|222|288|6"
block|,
literal|"All Week \u00d7 Evening|0|5|222|288|6"
block|,
literal|"All Week \u00d7 All Times|0|6|0|288|6"
block|}
argument_list|)
name|String
index|[]
name|roomSharingModes
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MMMM d"
argument_list|)
name|String
name|weekSelectionDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"EEEE MMMM d"
argument_list|)
name|String
name|dateSelectionDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"EEEE MMMM d yyyy"
argument_list|)
name|String
name|singleDateSelectionFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"M/d"
argument_list|)
name|String
name|dateFormatShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"h:mma"
argument_list|)
name|String
name|timeFormatShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MMM d, yyyy"
argument_list|)
name|String
name|sessionDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
name|String
name|dateEntryFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Override: Allow Time Conflict"
block|,
literal|"Override: Can Assign Over Limit"
block|,
literal|"Override: Time Conflict& Over Limit"
block|}
argument_list|)
name|String
index|[]
name|reservationOverrideTypeName
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Time Conflict"
block|,
literal|"Over Limit"
block|,
literal|"Time& Limit"
block|}
argument_list|)
name|String
index|[]
name|reservationOverrideTypeAbbv
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|false
argument_list|)
annotation|@
name|DoNotTranslate
name|boolean
name|displayMidtermPeriodPreferencesAsCalendar
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"No Event Management"
block|,
literal|"Authenticated Users Can Request Events Managers Can Approve"
block|,
literal|"Departmental Users Can Request Events Managers Can Approve"
block|,
literal|"Event Managers Can Request Or Approve Events"
block|,
literal|"Authenticated Users Can Request Events No Approval"
block|,
literal|"Departmental Users Can Request Events No Approval"
block|,
literal|"Event Managers Can Request Events No Approval"
block|,
block|}
argument_list|)
name|String
index|[]
name|eventStatusName
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Disabled"
block|,
literal|"Enabled"
block|,
literal|"Only Department"
block|,
literal|"Only Managers"
block|,
literal|"Enabled<br>No Approval"
block|,
literal|"Only Department<br>No Approval"
block|,
literal|"Only Managers<br>No Approval"
block|,
block|}
argument_list|)
name|String
index|[]
name|eventStatusAbbv
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"#,##0.##"
argument_list|)
name|String
name|roomAreaFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"#0.0###"
argument_list|)
name|String
name|roomCoordinateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"square feet"
argument_list|)
name|String
name|roomAreaUnitsLong
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"ft&sup2;"
argument_list|)
name|String
name|roomAreaUnitsShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"ft2"
argument_list|)
name|String
name|roomAreaUnitsShortPlainText
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"square meters"
argument_list|)
name|String
name|roomAreaMetricUnitsLong
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"m&sup2;"
argument_list|)
name|String
name|roomAreaMetricUnitsShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"m2"
argument_list|)
name|String
name|roomAreaMetricUnitsShortPlainText
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|false
argument_list|)
annotation|@
name|DoNotTranslate
name|boolean
name|timeGridStudentGroupDoesNotOverlap
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|false
argument_list|)
annotation|@
name|DoNotTranslate
name|boolean
name|searchWhenPageIsLoaded
parameter_list|()
function_decl|;
annotation|@
name|DoNotTranslate
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
name|String
name|filterDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"All"
block|,
literal|"My"
block|,
literal|"Approved"
block|,
literal|"Unapproved"
block|,
literal|"Awaiting"
block|,
literal|"Conflicting"
block|,
literal|"My Awaiting"
block|,
literal|"Cancelled"
block|,
literal|"Expiring"
block|,
block|}
argument_list|)
name|String
index|[]
name|eventModeAbbv
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"All Events"
block|,
literal|"My Events"
block|,
literal|"Approved Events"
block|,
literal|"Not Approved Events"
block|,
literal|"Awaiting Events"
block|,
literal|"Conflicting Events"
block|,
literal|"Awaiting My Approval"
block|,
literal|"Cancelled / Rejected"
block|,
literal|"Expiring Events"
block|,
block|}
argument_list|)
name|String
index|[]
name|eventModeLabel
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"All"
block|,
literal|"Student"
block|,
literal|"Instructor"
block|,
literal|"Coordinator"
block|,
literal|"Contact"
block|,
block|}
argument_list|)
name|String
index|[]
name|eventRole
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"All"
block|,
literal|"Expired"
block|,
literal|"Not Expired"
block|,
block|}
argument_list|)
name|String
index|[]
name|reservationModeAbbv
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"All Reservations"
block|,
literal|"Expired"
block|,
literal|"Not Expired"
block|,
block|}
argument_list|)
name|String
index|[]
name|reservationModeLabel
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringMapValue
argument_list|(
block|{
literal|"R"
block|,
literal|"Req"
block|,
literal|"-2"
block|,
literal|"StrPref"
block|,
literal|"-1"
block|,
literal|"Pref"
block|,
literal|"0"
block|,
literal|""
block|,
literal|"1"
block|,
literal|"Disc"
block|,
literal|"2"
block|,
literal|"StrDisc"
block|,
literal|"P"
block|,
literal|"Proh"
block|,
literal|"N"
block|,
literal|"N/A"
block|,
block|}
argument_list|)
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|preferenceAbbreviation
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

