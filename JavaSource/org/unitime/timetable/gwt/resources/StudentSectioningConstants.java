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

begin_comment
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_interface
specifier|public
interface|interface
name|StudentSectioningConstants
extends|extends
name|Constants
block|{
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Tip: Use Ctrl+1 (or Ctrl+Alt+1) to navigate to the first course, Ctrl+2 to the second, Ctrl+A to the first alternative, Ctr+B to the second alternative, etc."
block|,
literal|"Tip: Use Ctrl+Arrow to navigate, Ctrl+Shift+Up and Ctrl+Shith+Down to move a line around."
block|,
literal|"Tip: Use Ctrl+F (or Ctrl+Alt+F in some browsers) to open the Course Finder dialog."
block|,
literal|"Tip: Use Ctrl+N (or Ctrl+Alt+N in some browsers) to validate the screen and go next."
block|,
literal|"Tip: Start entering the name (e.g., ENGL 10600) of the course or a part of its title (e.g., History) to see suggestions."
block|,
literal|"Tip: The Substitute Course Requests below can be used to ensure that the desired number of courses are scheduled even when a Course Request (and its alternatives) are not available."
block|,
literal|"Tip: Enter a free time to aviod getting classes in time you need for something else."
block|,
literal|"Tip: All courses above a free time should not overlap with the free time (you will get the course even when the only possibility is to break the free time)."
block|,
literal|"Tip: All courses below a free time can not overlap with the free time (you will only get the course if there are sections that do not break the free time)."
block|,
literal|"Tip: Click this tip to see another tip."
block|,
literal|"Tip: There are no alternative free times."
block|,
literal|"Tip: Try not to break too many standard time patterns with a free time (see the numbers in the Course Finder dialog)."
block|,
literal|"Tip: Use Esc to hide suggestions, Ctrl+L (or Ctrl+Alt+L in some browsers) to show suggestions."
block|}
argument_list|)
name|String
index|[]
name|tips
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Tip: Use Up and Down to navigate through courses, Enter to select one."
block|,
literal|"Tip: Start entering the name of a course (e.g., ENGL 10600) or a part of its title (e.g., History) to see suggestions."
block|,
literal|"Tip: Click on a course to see its details."
block|,
literal|"Tip: Doubleclik on a course to select it."
block|,
literal|"Tip: Press Esc to close the dialog, Enter to select the inputed text or the selected course."
block|}
argument_list|)
name|String
index|[]
name|courseTips
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Tip: Enter a free time (e.g., Monday 8am - 10am) or use the mouse to select it."
block|,
literal|"Tip: The numbers in the selected times counts the number of overlapping standard time patterns (3x50, 2x75, 1x150), try to avoid overlapping too many of those."
block|}
argument_list|)
name|String
index|[]
name|freeTimeTips
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"Free "
argument_list|)
name|String
name|freePrefix
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"7:30a"
block|,
literal|"8:00a"
block|,
literal|"8:30a"
block|,
literal|"9:00a"
block|,
literal|"9:30a"
block|,
literal|"10:00a"
block|,
literal|"10:30a"
block|,
literal|"11:00a"
block|,
literal|"11:30a"
block|,
literal|"12:00p"
block|,
literal|"12:30p"
block|,
literal|"1:00p"
block|,
literal|"1:30p"
block|,
literal|"2:00p"
block|,
literal|"2:30p"
block|,
literal|"3:00p"
block|,
literal|"3:30p"
block|,
literal|"4:00p"
block|,
literal|"4:30p"
block|,
literal|"5:00p"
block|,
literal|"5:30p"
block|,
literal|"6:00p"
block|,
literal|"6:30p"
block|,
literal|"7:00p"
block|,
literal|"7:30p"
block|}
argument_list|)
annotation|@
name|DoNotTranslate
name|String
index|[]
name|freeTimePeriods
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
name|freeTimeDays
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
literal|"R"
block|,
literal|"F"
block|,
literal|"S"
block|,
literal|"U"
block|}
argument_list|)
name|String
index|[]
name|freeTimeShortDays
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
name|freeTimeLongDays
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"0"
block|,
literal|"2"
block|,
literal|"6"
block|,
literal|"8"
block|,
literal|"12"
block|,
literal|"14"
block|,
literal|"15"
block|,
literal|"16"
block|,
literal|"17"
block|,
literal|"18"
block|,
literal|"19"
block|,
literal|"20"
block|}
argument_list|)
annotation|@
name|DoNotTranslate
name|String
index|[]
name|freeTimeOneDay150
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
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"M"
block|,
literal|"T"
block|,
literal|"W"
block|,
literal|"R"
block|,
literal|"F"
block|,
literal|"S"
block|,
literal|"U"
block|}
argument_list|)
name|String
index|[]
name|shortDays
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
name|DefaultStringValue
argument_list|(
literal|"red"
argument_list|)
annotation|@
name|DoNotTranslate
name|String
name|freeTimeColor
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"steelblue"
argument_list|)
annotation|@
name|DoNotTranslate
name|String
name|teachingAssignmentColor
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
name|printReportShowUserName
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
name|numberOfCourses
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
name|numberOfAlternatives
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
name|String
name|requestDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd/yyyy HH:mm:ss"
argument_list|)
name|String
name|timeStampFormat
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
name|DefaultStringValue
argument_list|(
literal|"MM/dd"
argument_list|)
name|String
name|patternDateFormat
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
name|isAuthenticationRequired
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
name|tryAuthenticationWhenGuest
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
name|hasAuthenticationPin
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
name|allowEmptySchedule
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
name|allowUserLogin
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
name|allowCalendarExport
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"All"
block|,
literal|"Enrolled"
block|,
literal|"Not Enrolled"
block|,
literal|"Wait-Listed"
block|}
argument_list|)
name|String
index|[]
name|enrollmentFilterValues
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
name|showCourseTitle
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
name|courseFinderSuggestWhenEmpty
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
name|courseFinderShowRequired
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Assigned"
block|,
literal|"Reserved"
block|,
literal|"Not Assigned"
block|,
literal|"Wait-Listed"
block|,
literal|"Critical"
block|,
literal|"Assigned Critical"
block|,
literal|"Not Assigned Critical"
block|,
literal|"Important"
block|,
literal|"Assigned Important"
block|,
literal|"Not Assigned Important"
block|,
literal|"No-Substitutes"
block|,
literal|"Assigned No-Subs"
block|,
literal|"Not Assigned No-Subs"
block|,
block|}
argument_list|)
name|String
index|[]
name|assignmentType
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Consent"
block|,
literal|"No Consent"
block|,
literal|"Waiting"
block|,
literal|"Approved"
block|,
literal|"To Do"
block|,
block|}
argument_list|)
name|String
index|[]
name|consentTypeAbbv
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Any Consent Needed"
block|,
literal|"Consent Not Needed"
block|,
literal|"Consent Waiting Approval"
block|,
literal|"Consent Approved"
block|,
literal|"Waiting My Approval"
block|,
block|}
argument_list|)
name|String
index|[]
name|consentTypeLabel
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
block|,
block|}
argument_list|)
name|String
index|[]
name|overrideType
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"None"
argument_list|)
name|String
name|noOverride
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"[0-9]+"
block|,
block|}
argument_list|)
annotation|@
name|DoNotTranslate
name|String
index|[]
name|freeTimeDoNotParse
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
name|startOverCanChangeView
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
name|listOfClassesUseLockIcon
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
name|checkLastResult
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"0.000"
argument_list|)
annotation|@
name|DoNotTranslate
name|String
name|executionTimeFormat
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
name|courseRequestAutomaticallyAddFirstAlternative
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|500
argument_list|)
annotation|@
name|DoNotTranslate
name|int
name|dashboardMaxLines
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|10
argument_list|)
annotation|@
name|DoNotTranslate
name|int
name|degreePlanMaxAlternatives
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

