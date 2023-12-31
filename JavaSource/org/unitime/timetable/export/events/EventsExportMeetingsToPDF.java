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
name|export
operator|.
name|events
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|export
operator|.
name|ExportHelper
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
name|export
operator|.
name|PDFPrinter
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
name|client
operator|.
name|events
operator|.
name|EventComparator
operator|.
name|EventMeetingSortBy
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
name|EventInterface
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
name|EventInterface
operator|.
name|ApprovalStatus
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
name|EventInterface
operator|.
name|EventFlag
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
name|EventInterface
operator|.
name|EventLookupRpcRequest
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
name|EventInterface
operator|.
name|EventType
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
name|EventInterface
operator|.
name|MeetingInterface
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
name|Formats
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:meetings.pdf"
argument_list|)
specifier|public
class|class
name|EventsExportMeetingsToPDF
extends|extends
name|EventsExporter
block|{
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"meetings.pdf"
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|print
parameter_list|(
name|ExportHelper
name|helper
parameter_list|,
name|EventLookupRpcRequest
name|request
parameter_list|,
name|List
argument_list|<
name|EventInterface
argument_list|>
name|events
parameter_list|,
name|int
name|eventCookieFlags
parameter_list|,
name|EventMeetingSortBy
name|sort
parameter_list|,
name|boolean
name|asc
parameter_list|)
throws|throws
name|IOException
block|{
name|Printer
name|printer
init|=
operator|new
name|PDFPrinter
argument_list|(
name|helper
operator|.
name|getOutputStream
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
name|printer
operator|.
name|getContentType
argument_list|()
argument_list|,
name|reference
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|hideColumns
argument_list|(
name|printer
argument_list|,
name|events
argument_list|,
name|eventCookieFlags
argument_list|)
expr_stmt|;
name|print
argument_list|(
name|printer
argument_list|,
name|meetings
argument_list|(
name|events
argument_list|,
name|sort
argument_list|,
name|asc
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|hideColumn
parameter_list|(
name|Printer
name|out
parameter_list|,
name|List
argument_list|<
name|EventInterface
argument_list|>
name|events
parameter_list|,
name|EventFlag
name|flag
parameter_list|)
block|{
switch|switch
condition|(
name|flag
condition|)
block|{
case|case
name|SHOW_SECTION
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_TITLE
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|3
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_NOTE
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|4
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_PUBLISHED_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|6
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_ALLOCATED_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|7
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_SETUP_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|8
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_TEARDOWN_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|9
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_CAPACITY
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|11
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_MEETING_CONTACTS
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|12
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_ENROLLMENT
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|13
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_LIMIT
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|14
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_SPONSOR
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|15
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_REQUESTED_SERVICES
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|16
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_MAIN_CONTACT
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|17
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_APPROVAL
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|18
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_LAST_CHANGE
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|19
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
specifier|protected
name|void
name|print
parameter_list|(
name|Printer
name|out
parameter_list|,
name|Set
argument_list|<
name|EventMeeting
argument_list|>
name|meetings
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|printHeader
argument_list|(
comment|/*  0 */
name|MESSAGES
operator|.
name|colName
argument_list|()
argument_list|,
comment|/*  1 */
name|MESSAGES
operator|.
name|colSection
argument_list|()
argument_list|,
comment|/*  2 */
name|MESSAGES
operator|.
name|colType
argument_list|()
argument_list|,
comment|/*  3 */
name|MESSAGES
operator|.
name|colTitle
argument_list|()
argument_list|,
comment|/*  4 */
name|MESSAGES
operator|.
name|colNote
argument_list|()
argument_list|,
comment|/*  5 */
name|MESSAGES
operator|.
name|colDate
argument_list|()
argument_list|,
comment|/*  6 */
name|MESSAGES
operator|.
name|colPublishedTime
argument_list|()
argument_list|,
comment|/*  7 */
name|MESSAGES
operator|.
name|colAllocatedTime
argument_list|()
argument_list|,
comment|/*  8 */
name|MESSAGES
operator|.
name|colSetupTimeShort
argument_list|()
argument_list|,
comment|/*  9 */
name|MESSAGES
operator|.
name|colTeardownTimeShort
argument_list|()
argument_list|,
comment|/* 10 */
name|MESSAGES
operator|.
name|colLocation
argument_list|()
argument_list|,
comment|/* 11 */
name|MESSAGES
operator|.
name|colCapacity
argument_list|()
argument_list|,
comment|/* 12 */
name|MESSAGES
operator|.
name|colMeetingContacts
argument_list|()
argument_list|,
comment|/* 13 */
name|MESSAGES
operator|.
name|colEnrollment
argument_list|()
argument_list|,
comment|/* 14 */
name|MESSAGES
operator|.
name|colLimit
argument_list|()
argument_list|,
comment|/* 15 */
name|MESSAGES
operator|.
name|colSponsorOrInstructor
argument_list|()
argument_list|,
comment|/* 16 */
name|MESSAGES
operator|.
name|colRequestedServices
argument_list|()
argument_list|,
comment|/* 17 */
name|MESSAGES
operator|.
name|colMainContact
argument_list|()
argument_list|,
comment|/* 18 */
name|MESSAGES
operator|.
name|colApproval
argument_list|()
argument_list|,
comment|/* 19 */
name|MESSAGES
operator|.
name|colLastChange
argument_list|()
argument_list|)
expr_stmt|;
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|df
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT
argument_list|)
decl_stmt|;
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|dfMeeting
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_MEETING
argument_list|)
decl_stmt|;
name|EventInterface
name|last
init|=
literal|null
decl_stmt|;
for|for
control|(
name|EventMeeting
name|em
range|:
name|meetings
control|)
block|{
name|EventInterface
name|event
init|=
name|em
operator|.
name|getEvent
argument_list|()
decl_stmt|;
name|MeetingInterface
name|meeting
init|=
name|em
operator|.
name|getMeeting
argument_list|()
decl_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
operator|!
name|last
operator|.
name|equals
argument_list|(
name|event
argument_list|)
condition|)
block|{
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|last
operator|=
name|event
expr_stmt|;
block|}
name|out
operator|.
name|printLine
argument_list|(
name|getName
argument_list|(
name|event
argument_list|)
argument_list|,
name|getSection
argument_list|(
name|event
argument_list|)
argument_list|,
name|event
operator|.
name|hasInstruction
argument_list|()
condition|?
name|event
operator|.
name|getInstruction
argument_list|()
else|:
name|event
operator|.
name|getType
argument_list|()
operator|.
name|getAbbreviation
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|,
name|getTitle
argument_list|(
name|event
argument_list|)
argument_list|,
name|event
operator|.
name|hasEventNote
argument_list|()
condition|?
name|event
operator|.
name|getEventNote
argument_list|(
literal|"\n"
argument_list|)
operator|.
name|replace
argument_list|(
literal|"<br>"
argument_list|,
literal|"\n"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\<.*?\\>"
argument_list|,
literal|""
argument_list|)
else|:
literal|""
argument_list|,
name|meeting
operator|.
name|isArrangeHours
argument_list|()
condition|?
name|event
operator|.
name|hasMessage
argument_list|()
condition|?
name|event
operator|.
name|getMessage
argument_list|()
else|:
name|CONSTANTS
operator|.
name|arrangeHours
argument_list|()
else|:
name|dfMeeting
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
argument_list|,
name|meeting
operator|.
name|isArrangeHours
argument_list|()
operator|&&
name|event
operator|.
name|hasMessage
argument_list|()
condition|?
name|CONSTANTS
operator|.
name|arrangeHours
argument_list|()
else|:
name|meeting
operator|.
name|getMeetingTime
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|,
name|meeting
operator|.
name|getAllocatedTime
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|meeting
operator|.
name|getStartOffset
argument_list|()
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
operator|-
name|meeting
operator|.
name|getEndOffset
argument_list|()
argument_list|)
argument_list|,
name|meeting
operator|.
name|getLocationName
argument_list|(
name|MESSAGES
argument_list|)
argument_list|,
name|meeting
operator|.
name|hasLocation
argument_list|()
operator|&&
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|hasSize
argument_list|()
condition|?
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getSize
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|,
name|meeting
operator|.
name|getMeetingContacts
argument_list|(
name|CONSTANTS
operator|.
name|meetingContactsSeparator
argument_list|()
argument_list|,
name|MESSAGES
argument_list|)
argument_list|,
name|event
operator|.
name|hasEnrollment
argument_list|()
condition|?
name|event
operator|.
name|getEnrollment
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasMaxCapacity
argument_list|()
condition|?
name|event
operator|.
name|getMaxCapacity
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasInstructors
argument_list|()
condition|?
name|event
operator|.
name|getInstructorNames
argument_list|(
literal|"\n"
argument_list|,
name|MESSAGES
argument_list|)
else|:
name|event
operator|.
name|hasSponsor
argument_list|()
condition|?
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasRequestedServices
argument_list|()
condition|?
name|event
operator|.
name|getRequestedServices
argument_list|(
literal|"\n"
argument_list|)
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasContact
argument_list|()
condition|?
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|getName
argument_list|(
name|MESSAGES
argument_list|)
else|:
literal|null
argument_list|,
name|event
operator|.
name|getType
argument_list|()
operator|==
name|EventType
operator|.
name|Unavailabile
condition|?
literal|""
else|:
name|meeting
operator|.
name|getApprovalStatus
argument_list|()
operator|==
name|ApprovalStatus
operator|.
name|Approved
condition|?
name|df
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getApprovalDate
argument_list|()
argument_list|)
else|:
name|meeting
operator|.
name|getApprovalStatus
argument_list|()
operator|==
name|ApprovalStatus
operator|.
name|Cancelled
condition|?
name|MESSAGES
operator|.
name|approvalCancelled
argument_list|()
else|:
name|meeting
operator|.
name|getApprovalStatus
argument_list|()
operator|==
name|ApprovalStatus
operator|.
name|Rejected
condition|?
name|MESSAGES
operator|.
name|approvalRejected
argument_list|()
else|:
name|meeting
operator|.
name|getApprovalStatus
argument_list|()
operator|==
name|ApprovalStatus
operator|.
name|Deleted
condition|?
name|MESSAGES
operator|.
name|approvalDeleted
argument_list|()
else|:
name|meeting
operator|.
name|isPast
argument_list|()
condition|?
name|MESSAGES
operator|.
name|approvalNotApprovedPast
argument_list|()
else|:
name|event
operator|.
name|getExpirationDate
argument_list|()
operator|!=
literal|null
condition|?
name|MESSAGES
operator|.
name|approvalExpire
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|event
operator|.
name|getExpirationDate
argument_list|()
argument_list|)
argument_list|)
else|:
name|MESSAGES
operator|.
name|approvalNotApproved
argument_list|()
argument_list|,
name|event
operator|.
name|getLastNote
argument_list|()
operator|!=
literal|null
condition|?
name|df
operator|.
name|format
argument_list|(
name|event
operator|.
name|getLastNote
argument_list|()
operator|.
name|getDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|+
name|event
operator|.
name|getLastNote
argument_list|()
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

