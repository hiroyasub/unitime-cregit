begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|Constants
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
literal|"3.5"
argument_list|)
name|String
name|version
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"&copy; 2008 - 2014 UniTime LLC"
argument_list|)
name|String
name|copyright
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|true
argument_list|)
name|boolean
name|useAmPm
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|false
argument_list|)
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
name|int
name|eventSlotIncrement
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|90
argument_list|)
name|int
name|eventStartDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|210
argument_list|)
name|int
name|eventStopDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|12
argument_list|)
name|int
name|eventLengthDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|10000
argument_list|)
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
name|String
index|[]
name|meetingColors
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Room Schedule"
block|,
literal|"Subject Schedule"
block|,
literal|"Curriculum Schedule"
block|,
literal|"Departmental Schedule"
block|,
literal|"Personal Schedule"
block|,
literal|"Course Schedule"
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
block|}
end_interface

end_unit

