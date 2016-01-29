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
name|events
package|;
end_package

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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|apache
operator|.
name|commons
operator|.
name|fileupload
operator|.
name|FileItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|command
operator|.
name|client
operator|.
name|GwtRpcException
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|resources
operator|.
name|GwtMessages
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
name|server
operator|.
name|UploadServlet
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
name|ApproveEventRpcRequest
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|NoteInterface
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
name|SaveOrApproveEventRpcResponse
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
name|Event
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
name|EventNote
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
name|Meeting
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
name|dao
operator|.
name|EventDAO
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
name|dao
operator|.
name|SessionDAO
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|GwtRpcImplements
argument_list|(
name|ApproveEventRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ApproveEventBackend
extends|extends
name|EventAction
argument_list|<
name|ApproveEventRpcRequest
argument_list|,
name|SaveOrApproveEventRpcResponse
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|SaveOrApproveEventRpcResponse
name|execute
parameter_list|(
name|ApproveEventRpcRequest
name|request
parameter_list|,
name|EventContext
name|context
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
decl_stmt|;
try|try
block|{
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
name|SaveOrApproveEventRpcResponse
name|response
init|=
operator|new
name|SaveOrApproveEventRpcResponse
argument_list|()
decl_stmt|;
name|Event
name|event
init|=
operator|(
name|request
operator|.
name|getEvent
argument_list|()
operator|==
literal|null
operator|||
name|request
operator|.
name|getEvent
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getEvent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|event
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedApproveEventNoEvent
argument_list|()
argument_list|)
throw|;
if|if
condition|(
operator|!
name|request
operator|.
name|hasMeetings
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedApproveEventNoMeetings
argument_list|()
argument_list|)
throw|;
name|Date
name|now
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Meeting
argument_list|>
name|affectedMeetings
init|=
operator|new
name|HashSet
argument_list|<
name|Meeting
argument_list|>
argument_list|()
decl_stmt|;
name|meetings
label|:
for|for
control|(
name|Iterator
argument_list|<
name|Meeting
argument_list|>
name|i
init|=
name|event
operator|.
name|getMeetings
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Meeting
name|meeting
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|MeetingInterface
name|m
range|:
name|request
operator|.
name|getMeetings
argument_list|()
control|)
block|{
if|if
condition|(
name|meeting
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|response
operator|.
name|addUpdatedMeeting
argument_list|(
name|m
argument_list|)
expr_stmt|;
name|affectedMeetings
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getOperation
argument_list|()
condition|)
block|{
case|case
name|REJECT
case|:
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|meeting
argument_list|,
name|Right
operator|.
name|EventMeetingApprove
argument_list|)
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedApproveEventNoRightsToReject
argument_list|(
name|toString
argument_list|(
name|meeting
argument_list|)
argument_list|)
argument_list|)
throw|;
comment|// hibSession.delete(meeting);
comment|// i.remove();
name|meeting
operator|.
name|setStatus
argument_list|(
name|Meeting
operator|.
name|Status
operator|.
name|REJECTED
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setApprovalDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|m
operator|.
name|setApprovalDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|m
operator|.
name|setApprovalStatus
argument_list|(
name|meeting
operator|.
name|getApprovalStatus
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
break|break;
case|case
name|APPROVE
case|:
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|meeting
argument_list|,
name|Right
operator|.
name|EventMeetingApprove
argument_list|)
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedApproveEventNoRightsToApprove
argument_list|(
name|toString
argument_list|(
name|meeting
argument_list|)
argument_list|)
argument_list|)
throw|;
name|meeting
operator|.
name|setStatus
argument_list|(
name|Meeting
operator|.
name|Status
operator|.
name|APPROVED
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setApprovalDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|m
operator|.
name|setApprovalDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|m
operator|.
name|setApprovalStatus
argument_list|(
name|meeting
operator|.
name|getApprovalStatus
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
break|break;
case|case
name|CANCEL
case|:
switch|switch
condition|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
condition|)
block|{
case|case
name|Event
operator|.
name|sEventTypeFinalExam
case|:
case|case
name|Event
operator|.
name|sEventTypeMidtermExam
case|:
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|meeting
argument_list|,
name|Right
operator|.
name|EventMeetingCancelExam
argument_list|)
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedApproveEventNoRightsToCancel
argument_list|(
name|toString
argument_list|(
name|meeting
argument_list|)
argument_list|)
argument_list|)
throw|;
break|break;
case|case
name|Event
operator|.
name|sEventTypeClass
case|:
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|meeting
argument_list|,
name|Right
operator|.
name|EventMeetingCancelClass
argument_list|)
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedApproveEventNoRightsToCancel
argument_list|(
name|toString
argument_list|(
name|meeting
argument_list|)
argument_list|)
argument_list|)
throw|;
break|break;
default|default:
if|if
condition|(
operator|!
name|context
operator|.
name|hasPermission
argument_list|(
name|meeting
argument_list|,
name|Right
operator|.
name|EventMeetingCancel
argument_list|)
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|failedApproveEventNoRightsToCancel
argument_list|(
name|toString
argument_list|(
name|meeting
argument_list|)
argument_list|)
argument_list|)
throw|;
break|break;
block|}
name|meeting
operator|.
name|setStatus
argument_list|(
name|Meeting
operator|.
name|Status
operator|.
name|CANCELLED
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setApprovalDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|m
operator|.
name|setApprovalDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|m
operator|.
name|setApprovalStatus
argument_list|(
name|meeting
operator|.
name|getApprovalStatus
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
break|break;
block|}
continue|continue
name|meetings
continue|;
block|}
block|}
block|}
name|EventNote
name|note
init|=
operator|new
name|EventNote
argument_list|()
decl_stmt|;
name|note
operator|.
name|setEvent
argument_list|(
name|event
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getOperation
argument_list|()
condition|)
block|{
case|case
name|APPROVE
case|:
name|note
operator|.
name|setNoteType
argument_list|(
name|EventNote
operator|.
name|sEventNoteTypeApproval
argument_list|)
expr_stmt|;
break|break;
case|case
name|REJECT
case|:
name|note
operator|.
name|setNoteType
argument_list|(
name|EventNote
operator|.
name|sEventNoteTypeRejection
argument_list|)
expr_stmt|;
break|break;
case|case
name|CANCEL
case|:
name|note
operator|.
name|setNoteType
argument_list|(
name|EventNote
operator|.
name|sEventNoteTypeCancel
argument_list|)
expr_stmt|;
break|break;
default|default:
name|note
operator|.
name|setNoteType
argument_list|(
name|EventNote
operator|.
name|sEventNoteTypeInquire
argument_list|)
expr_stmt|;
block|}
name|note
operator|.
name|setTimeStamp
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|note
operator|.
name|setUser
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getTrueName
argument_list|()
argument_list|)
expr_stmt|;
name|note
operator|.
name|setUserId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getTrueExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
name|note
operator|.
name|setAffectedMeetings
argument_list|(
name|affectedMeetings
argument_list|)
expr_stmt|;
name|note
operator|.
name|setMeetings
argument_list|(
name|EventInterface
operator|.
name|toString
argument_list|(
name|response
operator|.
name|getUpdatedMeetings
argument_list|()
argument_list|,
name|CONSTANTS
argument_list|,
literal|"\n"
argument_list|,
operator|new
name|EventInterface
operator|.
name|DateFormatter
argument_list|()
block|{
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|dfShort
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT_SHORT
argument_list|)
decl_stmt|;
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|dfLong
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT_LONG
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|formatFirstDate
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
return|return
name|dfShort
operator|.
name|format
argument_list|(
name|date
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|formatLastDate
parameter_list|(
name|Date
name|date
parameter_list|)
block|{
return|return
name|dfLong
operator|.
name|format
argument_list|(
name|date
argument_list|)
return|;
block|}
block|}
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|hasMessage
argument_list|()
condition|)
name|note
operator|.
name|setTextNote
argument_list|(
name|request
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|FileItem
name|attachment
init|=
operator|(
name|FileItem
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|UploadServlet
operator|.
name|SESSION_LAST_FILE
argument_list|)
decl_stmt|;
if|if
condition|(
name|attachment
operator|!=
literal|null
condition|)
block|{
name|note
operator|.
name|setAttachedName
argument_list|(
name|attachment
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|note
operator|.
name|setAttachedFile
argument_list|(
name|attachment
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|note
operator|.
name|setAttachedContentType
argument_list|(
name|attachment
operator|.
name|getContentType
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|event
operator|.
name|getNotes
argument_list|()
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|NoteInterface
name|n
init|=
operator|new
name|NoteInterface
argument_list|()
decl_stmt|;
name|n
operator|.
name|setId
argument_list|(
name|note
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setDate
argument_list|(
name|now
argument_list|)
expr_stmt|;
name|n
operator|.
name|setMeetings
argument_list|(
name|note
operator|.
name|getMeetings
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setUser
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getTrueName
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setType
argument_list|(
name|NoteInterface
operator|.
name|NoteType
operator|.
name|values
argument_list|()
index|[
name|note
operator|.
name|getNoteType
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|n
operator|.
name|setNote
argument_list|(
name|request
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setAttachment
argument_list|(
name|attachment
operator|==
literal|null
condition|?
literal|null
else|:
name|attachment
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|n
operator|.
name|setLink
argument_list|(
name|attachment
operator|==
literal|null
condition|?
literal|null
else|:
name|QueryEncoderBackend
operator|.
name|encode
argument_list|(
literal|"event="
operator|+
name|event
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"&note="
operator|+
name|note
operator|.
name|getUserId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|addNote
argument_list|(
name|n
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|getMeetings
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|response
operator|.
name|setEvent
argument_list|(
name|EventDetailBackend
operator|.
name|getEventDetail
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|,
name|event
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|getEvent
argument_list|()
operator|.
name|setId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|hibSession
operator|.
name|update
argument_list|(
name|event
argument_list|)
expr_stmt|;
name|response
operator|.
name|setEvent
argument_list|(
name|EventDetailBackend
operator|.
name|getEventDetail
argument_list|(
name|session
argument_list|,
name|event
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|tx
operator|=
literal|null
expr_stmt|;
operator|new
name|EventEmail
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
operator|.
name|send
argument_list|(
name|context
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
if|if
condition|(
name|ex
operator|instanceof
name|GwtRpcException
condition|)
throw|throw
operator|(
name|GwtRpcException
operator|)
name|ex
throw|;
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

