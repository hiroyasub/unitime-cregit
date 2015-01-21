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
name|Messages
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|GwtAriaMessages
extends|extends
name|Messages
block|{
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} dialog opened."
argument_list|)
name|String
name|dialogOpened
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} dialog closed."
argument_list|)
name|String
name|dialogClosed
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected {0}"
argument_list|)
name|String
name|suggestionSelected
parameter_list|(
name|String
name|suggestion
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There is one suggestion {0}. Use enter to select it."
argument_list|)
name|String
name|showingOneSuggestion
parameter_list|(
name|String
name|suggestion
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are {0, number} suggestions to {1}. Use up and down arrows to navigate. First suggestion is {2}."
argument_list|)
name|String
name|showingMultipleSuggestions
parameter_list|(
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|query
parameter_list|,
name|String
name|suggestion
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are {0, number} suggestions. Use up and down arrows to navigate. First suggestion is {1}."
argument_list|)
name|String
name|showingMultipleSuggestionsNoQuery
parameter_list|(
annotation|@
name|PluralCount
name|int
name|nbrSuggestion
parameter_list|,
name|String
name|suggestion
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are {0, number} suggestions. Use up and down arrows to navigate."
argument_list|)
name|String
name|showingMultipleSuggestionsNoQueryNoneSelected
parameter_list|(
annotation|@
name|PluralCount
name|int
name|nbrSuggestion
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are {0, number} suggestions to {1}. Use up and down arrows to navigate."
argument_list|)
name|String
name|showingMultipleSuggestionsNoneSelected
parameter_list|(
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|query
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Suggestion {0, number} of {1, number}. {2}"
argument_list|)
name|String
name|onSuggestion
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|suggestion
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Suggestion: {0}"
argument_list|)
name|String
name|onSuggestionNoCount
parameter_list|(
name|String
name|suggestion
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Logged in as {0}, click here to log out."
argument_list|)
name|String
name|userAuthenticated
parameter_list|(
name|String
name|user
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Logged in as guest, click here to log in."
argument_list|)
name|String
name|userGuest
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not authenticated, click here to log in."
argument_list|)
name|String
name|userNotAuthenticated
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Logged in as {0}, click here to lookup a student."
argument_list|)
name|String
name|userAuthenticatedLookup
parameter_list|(
name|String
name|user
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No academic session selected, click here to change the session."
argument_list|)
name|String
name|sessionNoSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Current academic session is {1} {0} campus {2}, click here to change the session."
argument_list|)
name|String
name|sessionCurrent
parameter_list|(
name|String
name|year
parameter_list|,
name|String
name|term
parameter_list|,
name|String
name|campus
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"User name"
argument_list|)
name|String
name|propUserName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Password"
argument_list|)
name|String
name|propPassword
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Pin number"
argument_list|)
name|String
name|propPinNumber
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"User authentication dialog opened, please enter your user name and password."
argument_list|)
name|String
name|authenticationDialogOpened
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Log in as guest, no user name or password needed."
argument_list|)
name|String
name|buttonLogInAsGuest
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session selection dialog opened, please select an academic session. Use Alt + Up and Alt + Down to navigate, Alt + Enter to confirm the selection."
argument_list|)
name|String
name|sessionSelectorDialogOpened
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session selection dialog opened, please select an academic session. Use Alt + Up and Alt + Down to navigate, Alt + Enter to confirm the selection. Academic session {0} of {1}: {3} {2} campus {4}."
argument_list|)
name|String
name|sessionSelectorDialogOpenedWithSelection
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|year
parameter_list|,
name|String
name|term
parameter_list|,
name|String
name|campus
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session {0} of {1}: {3} {2} campus {4}."
argument_list|)
name|String
name|sessionSelectorShowingSession
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|year
parameter_list|,
name|String
name|term
parameter_list|,
name|String
name|campus
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session {1} {0} campus {2} selected."
argument_list|)
name|String
name|sessionSelectorDialogSelected
parameter_list|(
name|String
name|year
parameter_list|,
name|String
name|term
parameter_list|,
name|String
name|campus
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Priority {0, number} course or free time request."
argument_list|)
name|String
name|titleRequestedCourse
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"First alternative to the priority {0, number} course request."
argument_list|)
name|String
name|titleRequestedCourseFirstAlternative
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Second alternative to the priority {0, number} course request."
argument_list|)
name|String
name|titleRequestedCourseSecondAlternative
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Priority {0, number} alternate course request. Access key {1}."
argument_list|)
name|String
name|titleRequestedAlternate
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|,
name|String
name|accessKey
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"First alternative to the priority {0, number} alternate course request."
argument_list|)
name|String
name|titleRequestedAlternateFirstAlternative
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Second alternative to the priority {0, number} alternate course request."
argument_list|)
name|String
name|titleRequestedAlternateSecondAlternative
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Check to wait list priority {0, number} course request, if it is not available."
argument_list|)
name|String
name|titleRequestedWaitList
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Check to wait list for {0} course request."
argument_list|)
name|String
name|titleRequestedWaitListForCourse
parameter_list|(
name|String
name|course
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Opens Course Finder dialog for priority {0, number} course or free time request."
argument_list|)
name|String
name|altRequestedCourseFinder
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Opens Course Finder dialog for priority {0, number} first alternative course request."
argument_list|)
name|String
name|altRequestedCourseFirstAlternativeFinder
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Opens Course Finder dialog for priority {0, number} second alternative course request."
argument_list|)
name|String
name|altRequestedCourseSecondAlternativeFinder
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Opens Course Finder dialog for priority {0, number} alternate course request."
argument_list|)
name|String
name|altRequestedAlternateFinder
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Opens Course Finder dialog for priority {0, number} first alternative alternate course request."
argument_list|)
name|String
name|altRequestedAlternateFirstFinder
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Opens Course Finder dialog for priority {0, number} second alternative alternate course request."
argument_list|)
name|String
name|altRequestedAlternateSecondFinder
parameter_list|(
annotation|@
name|PluralCount
name|int
name|priority
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Swaps priority {0, number} course request with priority {1, number} course request including alternatives and wait list information."
argument_list|)
name|String
name|altSwapCourseRequest
parameter_list|(
annotation|@
name|PluralCount
name|int
name|p1
parameter_list|,
annotation|@
name|PluralCount
name|int
name|p2
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Delete priority {0, number} course request including alternatives and wait list information."
argument_list|)
name|String
name|altDeleteRequest
parameter_list|(
annotation|@
name|PluralCount
name|int
name|p1
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Swaps priority {0, number} alternate course request with priority {1, number} alternate course request including alternatives and wait list information."
argument_list|)
name|String
name|altSwapAlternateRequest
parameter_list|(
annotation|@
name|PluralCount
name|int
name|p1
parameter_list|,
annotation|@
name|PluralCount
name|int
name|p2
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Swaps priority {0, number} course request with priority {1, number} alternate course request including alternatives and wait list information."
argument_list|)
name|String
name|altSwapCourseAlternateRequest
parameter_list|(
annotation|@
name|PluralCount
name|int
name|p1
parameter_list|,
annotation|@
name|PluralCount
name|int
name|p2
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Delete priority {0, number} alternate course request including alternatives and wait list information."
argument_list|)
name|String
name|altDeleteAlternateRequest
parameter_list|(
annotation|@
name|PluralCount
name|int
name|p1
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course Finder dialog opened."
argument_list|)
name|String
name|courseFinderDialogOpened
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course Finder Filter. Enter a text to look for a course or a free time. Press Ctrl + Alt + C for course selection, Ctrl + Alt + T for free time selection."
argument_list|)
name|String
name|courseFinderFilterAllowsFreeTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course Finder Filter. Enter a text to look for a course."
argument_list|)
name|String
name|courseFinderFilter
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Looking for a course. On a selected course press Ctrl + Alt + D for details, Ctrl + Alt + L for a list of classes."
argument_list|)
name|String
name|courseFinderCoursesTab
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Looking for a free time."
argument_list|)
name|String
name|courseFinderFreeTimeTab
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No course is selected."
argument_list|)
name|String
name|courseFinderNoCourse
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course {0} of {1}: {2} {3}"
argument_list|)
name|String
name|courseFinderSelected
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course {0} of {1}: {2} {3} entitled {4}"
argument_list|)
name|String
name|courseFinderSelectedWithTitle
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|,
name|String
name|title
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course {0} of {1}: {2} {3} entitled {4} with note {5}"
argument_list|)
name|String
name|courseFinderSelectedWithTitleAndNote
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|note
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course {0} of {1}: {2} {3} with note {4}"
argument_list|)
name|String
name|courseFinderSelectedWithNote
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|,
name|String
name|note
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Free Time {0}"
argument_list|)
name|String
name|courseFinderSelectedFreeTime
parameter_list|(
name|String
name|ft
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Check to pin the class {0} in the assigned time and room."
argument_list|)
name|String
name|classPin
parameter_list|(
name|String
name|clazz
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Check to pin the class {0} in the assigned time and room."
argument_list|)
name|String
name|freeTimePin
parameter_list|(
name|String
name|freeeTime
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Previous assignment of {0}: {1}"
argument_list|)
name|String
name|previousAssignment
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|assignment
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0}: {1}"
argument_list|)
name|String
name|classAssignment
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|assignment
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Class {0} of {1}: {2}"
argument_list|)
name|String
name|classSelected
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|clazz
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Free Time {0}"
argument_list|)
name|String
name|freeTimeAssignment
parameter_list|(
name|String
name|assignment
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0}: {1}"
argument_list|)
name|String
name|courseUnassginment
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|message
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Free Time {0}: {1}"
argument_list|)
name|String
name|freeTimeUnassignment
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|message
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Available"
argument_list|)
name|String
name|colLimit
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Current Time"
argument_list|)
name|String
name|colTimeCurrent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"New Time"
argument_list|)
name|String
name|colTimeNew
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Current Date"
argument_list|)
name|String
name|colDateCurrent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"New Date"
argument_list|)
name|String
name|colDateNew
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Current Room"
argument_list|)
name|String
name|colRoomCurrent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"New Room"
argument_list|)
name|String
name|colRoomNew
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are no alternatives for {0}. Press Escape to hide alternatives."
argument_list|)
name|String
name|suggestionsNoAlternative
parameter_list|(
name|String
name|source
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are no alternatives for {0} matching {1}. Change the filter and click the Search button. Press Escape to hide alternatives."
argument_list|)
name|String
name|suggestionsNoAlternativeWithFilter
parameter_list|(
name|String
name|source
parameter_list|,
name|String
name|filter
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are {0, number} alternatives to {1}. Use up and down arrows to navigate. To select an alternative press enter. Press Escape to hide alternatives. To filter alternatives type in a text and click the Search button."
argument_list|)
name|String
name|showingAlternatives
parameter_list|(
annotation|@
name|PluralCount
name|int
name|nbrSuggestions
parameter_list|,
name|String
name|query
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Alternative {0, number} of {1, number}. {2}"
argument_list|)
name|String
name|showingAlternative
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrAlternatives
parameter_list|,
name|String
name|alternative
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected alternative {0}"
argument_list|)
name|String
name|selectedAlternative
parameter_list|(
name|String
name|alternative
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Assigned {0}. "
argument_list|)
name|String
name|assigned
parameter_list|(
name|String
name|assignment
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unassigned {0}. "
argument_list|)
name|String
name|unassigned
parameter_list|(
name|String
name|assignment
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Arrange Hours"
argument_list|)
name|String
name|arrangeHours
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} {1}"
argument_list|)
name|String
name|courseFinderCourse
parameter_list|(
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} {1} entitled {2}"
argument_list|)
name|String
name|courseFinderCourseWithTitle
parameter_list|(
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|,
name|String
name|title
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} {1} entitled {2} with note {3}"
argument_list|)
name|String
name|courseFinderCourseWithTitleAndNote
parameter_list|(
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|note
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} {1} with note {2}"
argument_list|)
name|String
name|courseFinderCourseWithNote
parameter_list|(
name|String
name|subject
parameter_list|,
name|String
name|course
parameter_list|,
name|String
name|note
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} {1} available {2}"
argument_list|)
name|String
name|courseFinderClassAvailable
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|assignment
parameter_list|,
name|String
name|availability
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} {1} not available"
argument_list|)
name|String
name|courseFinderClassNotAvailable
parameter_list|(
name|String
name|clazz
parameter_list|,
name|String
name|assignment
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Showing List of Classes. Use Alt + Up and Alt + Down to navigate, Alt + Enter to open Suggestions for the selected class."
argument_list|)
name|String
name|listOfClasses
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Showing Timetable grid."
argument_list|)
name|String
name|timetable
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Showing Course Requests."
argument_list|)
name|String
name|courseRequests
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"add {0} {1}"
argument_list|)
name|String
name|chipAdd
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"change {0} to {1}"
argument_list|)
name|String
name|chipReplace
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"remove {0} {1}"
argument_list|)
name|String
name|chipDelete
parameter_list|(
name|String
name|command
parameter_list|,
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"blank"
argument_list|)
name|String
name|emptyFilter
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room filter: {0}"
argument_list|)
name|String
name|roomFilter
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Event filter: {0}"
argument_list|)
name|String
name|eventFilter
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic session: {0}"
argument_list|)
name|String
name|academicSession
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Weeks: {0}"
argument_list|)
name|String
name|weekSelection
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Rooms: {0}"
argument_list|)
name|String
name|roomSelection
parameter_list|(
name|String
name|value
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Tab {0} of {1}: {2}, press enter to select"
argument_list|)
name|String
name|tabNotSelected
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrTabs
parameter_list|,
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Tab {0} of {1}: {2} selected"
argument_list|)
name|String
name|tabSelected
parameter_list|(
annotation|@
name|PluralCount
name|int
name|index
parameter_list|,
annotation|@
name|PluralCount
name|int
name|nbrTabs
parameter_list|,
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected tab {0}"
argument_list|)
name|String
name|onTabSelected
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Show column {0}"
argument_list|)
name|String
name|opShow
parameter_list|(
name|String
name|column
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Hide column {0}"
argument_list|)
name|String
name|opHide
parameter_list|(
name|String
name|column
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Enable {0}"
argument_list|)
name|String
name|opCheck
parameter_list|(
name|String
name|column
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Disable {0}"
argument_list|)
name|String
name|opUncheck
parameter_list|(
name|String
name|column
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Every {0} of {1}"
argument_list|)
name|String
name|datesDayOfWeekSelection
parameter_list|(
name|String
name|dayOfWeek
parameter_list|,
name|String
name|month
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Week {0} starting {1}"
argument_list|)
name|String
name|datesWeekSelection
parameter_list|(
annotation|@
name|PluralCount
name|int
name|weekNumber
parameter_list|,
name|String
name|firstDate
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No dates are selected."
argument_list|)
name|String
name|datesNothingSelected
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected {0}"
argument_list|)
name|String
name|datesSelected
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unselected {0}"
argument_list|)
name|String
name|datesUnselected
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected work days for {0}"
argument_list|)
name|String
name|datesSelectedWorkDays
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected work days for {0} starting today"
argument_list|)
name|String
name|datesSelectedWorkDaysFuture
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected all but holidays for {0}"
argument_list|)
name|String
name|datesSelectedAllButVacations
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected all days for {0}"
argument_list|)
name|String
name|datesSelectedAll
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected all class days for {0}"
argument_list|)
name|String
name|datesSelectedAllClassDays
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected all class days for {0} starting today"
argument_list|)
name|String
name|datesSelectedAllClassDaysFuture
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Dates selection"
argument_list|)
name|String
name|datesSelection
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Dates selection, selected {0}, cursor at {1}."
argument_list|)
name|String
name|datesSelectionWithSelection
parameter_list|(
name|String
name|selection
parameter_list|,
name|String
name|cursor
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Dates selection, no dates are selected, cursor at {0}."
argument_list|)
name|String
name|datesSelectionNoSelection
parameter_list|(
name|String
name|cursor
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Dates selection, selected {0}"
argument_list|)
name|String
name|datesSelectionWithSelectionNoCursor
parameter_list|(
name|String
name|selection
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Start Time"
argument_list|)
name|String
name|startTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"End Time"
argument_list|)
name|String
name|endTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} from {1} to {2} in {3} ({4})"
argument_list|)
name|String
name|dateTimeRoomSelection
parameter_list|(
name|String
name|date
parameter_list|,
name|String
name|start
parameter_list|,
name|String
name|end
parameter_list|,
name|String
name|room
parameter_list|,
name|String
name|hint
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected {0}"
argument_list|)
name|String
name|selectedSelection
parameter_list|(
name|String
name|selection
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} in {1}"
argument_list|)
name|String
name|dateRoomSelection
parameter_list|(
name|String
name|date
parameter_list|,
name|String
name|room
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"from {0} to {1}"
argument_list|)
name|String
name|timeSelection
parameter_list|(
name|String
name|start
parameter_list|,
name|String
name|end
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"available"
argument_list|)
name|String
name|selectionAvailable
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Select meetings. Showing rooms {1} to {2} and {0} days. Use arrows to navigate, enter to select."
argument_list|)
name|String
name|meetingSelectionDescriptionRoomsHorizontal
parameter_list|(
annotation|@
name|PluralCount
name|int
name|nrDays
parameter_list|,
annotation|@
name|PluralCount
name|int
name|firstRoom
parameter_list|,
annotation|@
name|PluralCount
name|int
name|lastRoom
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Select meetings. Showing days {1} to {2} and {0} rooms. Use arrows to navigate, enter to select."
argument_list|)
name|String
name|meetingSelectionDescriptionDatesHorizontal
parameter_list|(
annotation|@
name|PluralCount
name|int
name|nrRooms
parameter_list|,
annotation|@
name|PluralCount
name|int
name|firstDay
parameter_list|,
annotation|@
name|PluralCount
name|int
name|lastDay
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected {0} in {1}"
argument_list|)
name|String
name|dateRoomSelected
parameter_list|(
name|String
name|date
parameter_list|,
name|String
name|room
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Unselected {0} in {1}"
argument_list|)
name|String
name|dateRoomUnselected
parameter_list|(
name|String
name|date
parameter_list|,
name|String
name|room
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Can not select {0} in {1}, room is not available."
argument_list|)
name|String
name|dateRoomCanNotSelect
parameter_list|(
name|String
name|date
parameter_list|,
name|String
name|room
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No meetings are selected."
argument_list|)
name|String
name|meetingSelectionNothingSelected
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected meetings: {0}."
argument_list|)
name|String
name|meetingSelectionSelected
parameter_list|(
name|String
name|meetings
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Suggestion: {0}"
argument_list|)
name|String
name|singleDateCursor
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Selected: {0}"
argument_list|)
name|String
name|singleDateSelected
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Calendar opened, user arrows to navigate, enter to select the suggested date. Or type in a particular date. Showing {0}"
argument_list|)
name|String
name|singleDatePopupOpenedNoDateSelected
parameter_list|(
name|String
name|month
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Calendar opened, user arrows to navigate, enter to select the suggested date. Or type in a particular date. Suggestion: {0}"
argument_list|)
name|String
name|singleDatePopupOpenedDateSelected
parameter_list|(
name|String
name|date
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"People lookup. Enter name, use arrows to navigate through suggested people, enter to select."
argument_list|)
name|String
name|peopleLookupName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

