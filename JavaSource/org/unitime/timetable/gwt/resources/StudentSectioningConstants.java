begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
literal|"Tip: The Alternate Course Requests below can be used to ensure that the desired number of courses are scheduled even when a Course Request (and its alternatives) are not available."
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
name|String
name|freeTimeColor
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|false
argument_list|)
name|boolean
name|printReportShowUserName
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|12
argument_list|)
name|int
name|numberOfCourses
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|3
argument_list|)
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
name|boolean
name|isAuthenticationRequired
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|true
argument_list|)
name|boolean
name|tryAuthenticationWhenGuest
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

