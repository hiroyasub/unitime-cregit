begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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

begin_interface
specifier|public
interface|interface
name|GwtMessages
extends|extends
name|Messages
block|{
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} Help"
argument_list|)
name|String
name|pageHelp
parameter_list|(
name|String
name|pageTitle
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Version {0} built on {1}"
argument_list|)
name|String
name|pageVersion
parameter_list|(
name|String
name|version
parameter_list|,
name|String
name|buildDate
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&copy; 2008 - 2012 UniTime LLC,<br>distributed under GNU General Public License."
argument_list|)
name|String
name|pageCopyright
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Oooops, the loading is taking too much time... Something probably went wrong. You may need to reload this page."
argument_list|)
name|String
name|warnLoadingTooLong
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"The operation is taking a lot of time...<br>Click this message to cancel the operation."
argument_list|)
name|String
name|warnLoadingTooLongCanCancel
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Login is required to access this page."
argument_list|)
name|String
name|authenticationRequired
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Your timetabling session has expired. Please log in again."
argument_list|)
name|String
name|authenticationExpired
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Insufficient user privileges."
argument_list|)
name|String
name|authenticationInsufficient
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No academic session selected."
argument_list|)
name|String
name|authenticationNoSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Export in iCalendar format."
argument_list|)
name|String
name|exportICalendar
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Select All"
argument_list|)
name|String
name|opSelectAll
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Select All Conflicting"
argument_list|)
name|String
name|opSelectAllConflicting
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Clear Selection"
argument_list|)
name|String
name|opClearSelection
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&#10007; Remove"
argument_list|)
name|String
name|opDeleteSelectedMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&#10008 Remove All"
argument_list|)
name|String
name|opDeleteNewMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Setup Times ..."
argument_list|)
name|String
name|opChangeOffsets
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<b><i>+</i></b> Add Meetings ..."
argument_list|)
name|String
name|opAddMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Sort by {0}"
argument_list|)
name|String
name|opSortBy
parameter_list|(
name|String
name|column
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&#9744; {0}"
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
literal|"&#9746; {0}"
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
literal|"&#10003; Approve ..."
argument_list|)
name|String
name|opApproveSelectedMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&#10004; Approve All ..."
argument_list|)
name|String
name|opApproveAllMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&#10007; Reject ..."
argument_list|)
name|String
name|opRejectSelectedMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&#10008; Reject All ..."
argument_list|)
name|String
name|opRejectAllMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<i>?</i> Inquire ..."
argument_list|)
name|String
name|opInquireSelectedMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<b><i>?</i></b> Inquire ..."
argument_list|)
name|String
name|opInquireAllMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>A</u>pprove"
argument_list|)
name|String
name|opApprove
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>R</u>eject"
argument_list|)
name|String
name|opReject
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>I</u>nquire"
argument_list|)
name|String
name|opInquire
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>C</u>ancel"
argument_list|)
name|String
name|onCancel
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Date"
argument_list|)
name|String
name|colDate
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Day Of Week"
argument_list|)
name|String
name|colDayOfWeek
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"First Date"
argument_list|)
name|String
name|colFirstDate
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Last Date"
argument_list|)
name|String
name|colLastDate
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Published Time"
argument_list|)
name|String
name|colPublishedTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Published Start"
argument_list|)
name|String
name|colPublishedStartTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Published End"
argument_list|)
name|String
name|colPublishedEndTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Allocated Time"
argument_list|)
name|String
name|colAllocatedTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Allocated Start"
argument_list|)
name|String
name|colAllocatedStartTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Allocated End"
argument_list|)
name|String
name|colAllocatedEndTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Setup"
argument_list|)
name|String
name|colSetupTimeShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Setup Time"
argument_list|)
name|String
name|colSetupTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Teardown"
argument_list|)
name|String
name|colTeardownTimeShort
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Teardown Time"
argument_list|)
name|String
name|colTeardownTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Location"
argument_list|)
name|String
name|colLocation
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Capacity"
argument_list|)
name|String
name|colCapacity
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Approved"
argument_list|)
name|String
name|colApproval
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Name"
argument_list|)
name|String
name|colName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course"
argument_list|)
name|String
name|colCourse
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Offering"
argument_list|)
name|String
name|colOffering
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Configuration"
argument_list|)
name|String
name|colConfig
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Section"
argument_list|)
name|String
name|colSection
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Type"
argument_list|)
name|String
name|colType
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Time"
argument_list|)
name|String
name|colTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Instructor"
argument_list|)
name|String
name|colInstructor
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Instructor / Sponsor"
argument_list|)
name|String
name|colSponsorOrInstructor
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Main Contact"
argument_list|)
name|String
name|colMainContact
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Limit"
argument_list|)
name|String
name|colLimit
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Enrollment"
argument_list|)
name|String
name|colEnrollment
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Email"
argument_list|)
name|String
name|colEmail
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Phone"
argument_list|)
name|String
name|colPhone
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Subject"
argument_list|)
name|String
name|colSubject
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Subjects"
argument_list|)
name|String
name|colSubjects
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Course Number"
argument_list|)
name|String
name|colCourseNumber
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Courses"
argument_list|)
name|String
name|colCourses
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Config / Subpart"
argument_list|)
name|String
name|colConfigOrSubpart
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Configs / Subparts"
argument_list|)
name|String
name|colConfigsOrSubparts
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Class Number"
argument_list|)
name|String
name|colClassNumber
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Classess"
argument_list|)
name|String
name|colClasses
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"User"
argument_list|)
name|String
name|colUser
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Action"
argument_list|)
name|String
name|colAction
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Meetings"
argument_list|)
name|String
name|colMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Note"
argument_list|)
name|String
name|colNote
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Conflicts with {0}"
argument_list|)
name|String
name|conflictWith
parameter_list|(
name|String
name|event
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"not approved"
argument_list|)
name|String
name|approvalNotApproved
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"not approved"
argument_list|)
name|String
name|approvalNotApprovedPast
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"new meeting"
argument_list|)
name|String
name|approvalNewMeeting
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Setup / Teardown Times"
argument_list|)
name|String
name|dlgChangeOffsets
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Setup Time:"
argument_list|)
name|String
name|propSetupTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Teardown Time:"
argument_list|)
name|String
name|propTeardownTime
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Academic Session:"
argument_list|)
name|String
name|propAcademicSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Event Filter:"
argument_list|)
name|String
name|propEventFilter
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room Filter:"
argument_list|)
name|String
name|propRoomFilter
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Resource Type:"
argument_list|)
name|String
name|propResourceType
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Resource:"
argument_list|)
name|String
name|propResource
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Event Name:"
argument_list|)
name|String
name|propEventName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Sponsoring Organization:"
argument_list|)
name|String
name|propSponsor
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Enrollment:"
argument_list|)
name|String
name|propEnrollment
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Student Conflicts:"
argument_list|)
name|String
name|propStudentConflicts
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Event Type:"
argument_list|)
name|String
name|propEventType
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Expected Attendance:"
argument_list|)
name|String
name|propAttendance
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Main Contact:"
argument_list|)
name|String
name|propMainContact
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"First Name:"
argument_list|)
name|String
name|propFirstName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Middle Name:"
argument_list|)
name|String
name|propMiddleName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Last Name:"
argument_list|)
name|String
name|propLastName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Email:"
argument_list|)
name|String
name|propEmail
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Phone:"
argument_list|)
name|String
name|propPhone
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Additional Contacts:"
argument_list|)
name|String
name|propAdditionalContacts
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Contacts"
argument_list|)
name|String
name|propContacts
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Additional Emails:"
argument_list|)
name|String
name|propAdditionalEmails
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Additional Information:"
argument_list|)
name|String
name|propAdditionalInformation
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Last Change:"
argument_list|)
name|String
name|propLastChange
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Dates:"
argument_list|)
name|String
name|propDates
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Times:"
argument_list|)
name|String
name|propTimes
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Locations:"
argument_list|)
name|String
name|propLocations
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Requested By:"
argument_list|)
name|String
name|propRequestedBy
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"After:"
argument_list|)
name|String
name|propAfter
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Before:"
argument_list|)
name|String
name|propBefore
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Min:"
argument_list|)
name|String
name|propMin
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Max:"
argument_list|)
name|String
name|propMax
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"From:"
argument_list|)
name|String
name|propFrom
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"To:"
argument_list|)
name|String
name|propTo
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Notes:"
argument_list|)
name|String
name|propNotes
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Standard Notes:"
argument_list|)
name|String
name|propStandardNotes
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Attachement:"
argument_list|)
name|String
name|propAttachement
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Meetings:"
argument_list|)
name|String
name|propMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Events:"
argument_list|)
name|String
name|propEvents
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>O</u>k"
argument_list|)
name|String
name|buttonOk
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>C</u>ancel"
argument_list|)
name|String
name|buttonCancel
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>S</u>earch"
argument_list|)
name|String
name|buttonSearch
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>L</u>ookup"
argument_list|)
name|String
name|buttonLookup
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>A</u>dd Event"
argument_list|)
name|String
name|buttonAddEvent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>A</u>dd"
argument_list|)
name|String
name|buttonAddMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>P</u>rint"
argument_list|)
name|String
name|buttonPrint
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"E<u>x</u>port"
argument_list|)
name|String
name|buttonExportICal
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>B</u>ack"
argument_list|)
name|String
name|buttonBack
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>S</u>ave"
argument_list|)
name|String
name|buttonSave
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>E</u>dit"
argument_list|)
name|String
name|buttonEdit
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>L</u>ookup"
argument_list|)
name|String
name|buttonLookupMainContact
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"More<u>C</u>ontacts..."
argument_list|)
name|String
name|buttonLookupAdditionalContact
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>N</u>ext"
argument_list|)
name|String
name|buttonNext
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>P</u>revious"
argument_list|)
name|String
name|buttonPrevious
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&laquo;"
argument_list|)
name|String
name|buttonLeft
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"&raquo;"
argument_list|)
name|String
name|buttonRight
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>S</u>elect"
argument_list|)
name|String
name|buttonSelect
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Filter"
argument_list|)
name|String
name|sectFilter
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Event"
argument_list|)
name|String
name|sectEvent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Meetings"
argument_list|)
name|String
name|sectMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Courses / Classes"
argument_list|)
name|String
name|sectRelatedCourses
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Enrollments"
argument_list|)
name|String
name|sectEnrollments
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Notes"
argument_list|)
name|String
name|sectNotes
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Relations"
argument_list|)
name|String
name|sectRelations
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} timetable for {1}"
argument_list|)
name|String
name|sectTimetable
parameter_list|(
name|String
name|resource
parameter_list|,
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} events for {1}"
argument_list|)
name|String
name|sectEventList
parameter_list|(
name|String
name|resource
parameter_list|,
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} meetings for {1}"
argument_list|)
name|String
name|sectMeetingList
parameter_list|(
name|String
name|resource
parameter_list|,
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Loading {0}..."
argument_list|)
name|String
name|waitLoading
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Saving {0}..."
argument_list|)
name|String
name|waitSave
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Loading data for {0} ..."
argument_list|)
name|String
name|waitLoadingData
parameter_list|(
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Loading {0} timetable for {1} ..."
argument_list|)
name|String
name|waitLoadingTimetable
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Checking room availability..."
argument_list|)
name|String
name|waitCheckingRoomAvailability
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Loading academic sessions ..."
argument_list|)
name|String
name|waitLoadingSessions
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Approving meetings of {0} ..."
argument_list|)
name|String
name|waitForApproval
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Rejecting meetings of {0} ..."
argument_list|)
name|String
name|waitForRejection
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Inquiring about {0} ..."
argument_list|)
name|String
name|waitForInquiry
parameter_list|(
name|String
name|name
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Failed to load {0}: {1}"
argument_list|)
name|String
name|failedLoad
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Failed to save {0}: {1}"
argument_list|)
name|String
name|failedSave
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Failed to load academic sessions: {0}"
argument_list|)
name|String
name|failedLoadSessions
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No events matching the given criteria were found."
argument_list|)
name|String
name|failedNoEvents
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Failed to load enrollments: {0}."
argument_list|)
name|String
name|failedNoEnrollments
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Validation failed: {0}"
argument_list|)
name|String
name|failedValidation
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room availability failed: {0}"
argument_list|)
name|String
name|failedRoomAvailability
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Add meetings failed: {0}"
argument_list|)
name|String
name|failedAddMeetings
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} cannot be created through the event interface."
argument_list|)
name|String
name|failedSaveEventWrongType
parameter_list|(
name|String
name|eventType
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Meeting {0} has no location."
argument_list|)
name|String
name|failedSaveEventNoLocation
parameter_list|(
name|String
name|meeting
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} is not managed in UniTime or disabled for events at the moment."
argument_list|)
name|String
name|failedSaveEventWrongLocation
parameter_list|(
name|String
name|location
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Requested meeting date {0} is in the past or outside of the academic session."
argument_list|)
name|String
name|failedSaveEventPastOrOutside
parameter_list|(
name|String
name|meetingDate
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Meeting {0} is conflicting with {1}."
argument_list|)
name|String
name|failedSaveEventConflict
parameter_list|(
name|String
name|meeting
parameter_list|,
name|String
name|conflict
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"The event does no longer exist."
argument_list|)
name|String
name|failedApproveEventNoEvent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Insufficient rights to approve meeting {0}."
argument_list|)
name|String
name|failedApproveEventNoRightsToApprove
parameter_list|(
name|String
name|meeting
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Insufficient rights to reject meeting {0}."
argument_list|)
name|String
name|failedApproveEventNoRightsToReject
parameter_list|(
name|String
name|meeting
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Failed to hide academic session info: {0}"
argument_list|)
name|String
name|failedToHideSessionInfo
parameter_list|(
name|String
name|reason
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"There are more than {0} meetings matching the filter. Only {0} meetings are loaded."
argument_list|)
name|String
name|warnTooManyMeetings
parameter_list|(
name|int
name|maximum
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No academic session selected."
argument_list|)
name|String
name|warnNoSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No resource type selected."
argument_list|)
name|String
name|warnNoResourceType
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Waiting for the academic session {0} to load..."
argument_list|)
name|String
name|warnNoEventProperties
parameter_list|(
name|String
name|session
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Waiting for the room filter to load..."
argument_list|)
name|String
name|warnRoomFilterNotInitialized
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Waiting for the event filter to load..."
argument_list|)
name|String
name|warnEventFilterNotInitialized
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No date is selected."
argument_list|)
name|String
name|errorNoDateSelected
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No rooms are matching the filter."
argument_list|)
name|String
name|errorNoMatchingRooms
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Wrong event id provided."
argument_list|)
name|String
name|errorBadEventId
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Room"
argument_list|)
name|String
name|resourceRoom
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"All Rooms"
argument_list|)
name|String
name|allRooms
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<u>T</u>imetable"
argument_list|)
name|String
name|tabGrid
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"List of<u>E</u>vents"
argument_list|)
name|String
name|tabEventTable
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"List of<u>M</u>eetings"
argument_list|)
name|String
name|tabMeetingTable
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Students are required to attend this event."
argument_list|)
name|String
name|checkRequiredAttendance
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Display Conflicts"
argument_list|)
name|String
name|checkDisplayConflicts
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Include close by locations"
argument_list|)
name|String
name|checkIncludeNearby
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"One email per line please."
argument_list|)
name|String
name|hintAdditionalEmails
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No academic session is selected."
argument_list|)
name|String
name|hintNoSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Add Event"
argument_list|)
name|String
name|pageAddEvent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Edit Event"
argument_list|)
name|String
name|pageEditEvent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Event Detail"
argument_list|)
name|String
name|pageEventDetail
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"N/A"
argument_list|)
name|String
name|itemNotApplicable
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"All Departments"
argument_list|)
name|String
name|itemAllDepartments
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Add Meetings..."
argument_list|)
name|String
name|dialogAddMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Approve Meetings..."
argument_list|)
name|String
name|dialogApprove
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Reject Meetings..."
argument_list|)
name|String
name|dialogReject
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Inquire..."
argument_list|)
name|String
name|dialogInquire
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0}<br>{1}<br>{2} seats"
argument_list|)
name|String
name|singleRoomSelection
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|type
parameter_list|,
name|String
name|capacity
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0}<br>{1}<br>{2} - {3}"
argument_list|)
name|String
name|dateTimeHeader
parameter_list|(
name|String
name|dow
parameter_list|,
name|String
name|date
parameter_list|,
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
literal|"Selected"
argument_list|)
name|String
name|legendSelected
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not Selected"
argument_list|)
name|String
name|legendNotSelected
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Not in Session"
argument_list|)
name|String
name|legendNotInSession
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Classes Start/End"
argument_list|)
name|String
name|legendClassesStartOrEnd
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Examination Start"
argument_list|)
name|String
name|legendExamStart
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Holiday"
argument_list|)
name|String
name|legendHoliday
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Break"
argument_list|)
name|String
name|legendBreak
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Today"
argument_list|)
name|String
name|legendToday
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"In The Past"
argument_list|)
name|String
name|legendPast
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<span title=\"Conflicting event\" style=\"font-style:normal;\">&#9785;</span>"
argument_list|)
name|String
name|signConflict
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<span title=\"Selected event\" style=\"font-style:normal;\">&#9745;</span>"
argument_list|)
name|String
name|signSelected
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Event name is required."
argument_list|)
name|String
name|reqEventName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Main contact last name is required."
argument_list|)
name|String
name|reqMainContactLastName
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Main contact email is required."
argument_list|)
name|String
name|reqMainContactEmail
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"No meetings were defined."
argument_list|)
name|String
name|reqMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"One or more meetings is overlapping with an existing event."
argument_list|)
name|String
name|reqNoOverlaps
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"an event"
argument_list|)
name|String
name|anEvent
parameter_list|()
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"Requested meeting is in the past or outside of {0}."
argument_list|)
name|String
name|conflictPastOrOutside
parameter_list|(
name|String
name|academicSessionName
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"{0} is not managed in UniTime or disabled for events."
argument_list|)
name|String
name|conflictNotEventRoom
parameter_list|(
name|String
name|locationName
parameter_list|)
function_decl|;
annotation|@
name|DefaultMessage
argument_list|(
literal|"<i>File {0} attached.</i>"
argument_list|)
name|String
name|noteAttachement
parameter_list|(
name|String
name|fileName
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

