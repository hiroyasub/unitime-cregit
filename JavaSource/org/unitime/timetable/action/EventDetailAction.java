begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|Web
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|WebTable
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
name|form
operator|.
name|EventDetailForm
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
name|ClassEvent
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
name|Class_
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
name|CourseEvent
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
name|CourseOffering
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
name|EventContact
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
name|ExamEvent
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
name|ExamOwner
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
name|InstrOfferingConfig
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
name|InstructionalOffering
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
name|RelatedCourseInfo
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
name|Roles
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
name|StandardEventNote
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
name|CourseEventDAO
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
name|util
operator|.
name|Constants
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
name|webutil
operator|.
name|BackTracker
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
name|webutil
operator|.
name|Navigation
import|;
end_import

begin_comment
comment|/**  * @author Zuzana Mullerova  */
end_comment

begin_class
specifier|public
class|class
name|EventDetailAction
extends|extends
name|Action
block|{
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|EventDetailForm
name|myForm
init|=
operator|(
name|EventDetailForm
operator|)
name|form
decl_stmt|;
name|String
name|iOp
init|=
name|myForm
operator|.
name|getOp
argument_list|()
decl_stmt|;
name|HttpSession
name|webSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|webSession
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|webSession
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
if|if
condition|(
name|iOp
operator|!=
literal|null
condition|)
block|{
comment|//return to event list
if|if
condition|(
name|iOp
operator|.
name|equals
argument_list|(
literal|"Back"
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getId
argument_list|()
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
literal|"A"
operator|+
name|myForm
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEventList"
argument_list|)
return|;
block|}
if|if
condition|(
name|iOp
operator|.
name|equals
argument_list|(
literal|"Previous"
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getPreviousId
argument_list|()
operator|!=
literal|null
condition|)
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"eventDetail.do?id="
operator|+
name|myForm
operator|.
name|getPreviousId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|iOp
operator|.
name|equals
argument_list|(
literal|"Next"
argument_list|)
condition|)
block|{
if|if
condition|(
name|myForm
operator|.
name|getPreviousId
argument_list|()
operator|!=
literal|null
condition|)
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"eventDetail.do?id="
operator|+
name|myForm
operator|.
name|getNextId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|iOp
operator|.
name|equals
argument_list|(
literal|"Delete"
argument_list|)
condition|)
block|{
if|if
condition|(
literal|"y"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"confirm"
argument_list|)
argument_list|)
condition|)
block|{
comment|//					doDelete(myForm, request);
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEventDetail"
argument_list|)
return|;
block|}
block|}
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|==
literal|null
operator|&&
name|myForm
operator|.
name|getId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showEventList"
argument_list|)
return|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|id
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
name|Event
name|event
init|=
operator|new
name|EventDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|event
operator|!=
literal|null
condition|)
block|{
name|myForm
operator|.
name|setEventName
argument_list|(
name|event
operator|.
name|getEventName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|event
operator|.
name|getEventName
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setEventType
argument_list|(
name|event
operator|.
name|getEventTypeLabel
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setMinCapacity
argument_list|(
name|event
operator|.
name|getMinCapacity
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|event
operator|.
name|getMinCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setMaxCapacity
argument_list|(
name|event
operator|.
name|getMaxCapacity
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|event
operator|.
name|getMaxCapacity
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setSponsoringOrg
argument_list|(
literal|"N/A yet"
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Course Event"
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getEventType
argument_list|()
argument_list|)
condition|)
block|{
name|myForm
operator|.
name|setAttendanceRequired
argument_list|(
operator|(
operator|(
name|CourseEvent
operator|)
name|event
operator|)
operator|.
name|isReqAttendance
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|event
operator|.
name|getNotes
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
name|EventNote
name|en
init|=
operator|(
name|EventNote
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|en
operator|.
name|getTextNote
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|myForm
operator|.
name|addNote
argument_list|(
name|en
operator|.
name|getTextNote
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|event
operator|.
name|getNotes
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
name|EventNote
name|en2
init|=
operator|(
name|EventNote
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|StandardEventNote
name|sen
init|=
name|en2
operator|.
name|getStandardNote
argument_list|()
decl_stmt|;
if|if
condition|(
name|sen
operator|!=
literal|null
condition|)
block|{
name|myForm
operator|.
name|addNote
argument_list|(
name|sen
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|event
operator|.
name|getMainContact
argument_list|()
operator|!=
literal|null
condition|)
name|myForm
operator|.
name|setMainContact
argument_list|(
name|event
operator|.
name|getMainContact
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|event
operator|.
name|getAdditionalContacts
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
name|EventContact
name|ec
init|=
operator|(
name|EventContact
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|myForm
operator|.
name|addAdditionalContact
argument_list|(
operator|(
name|ec
operator|.
name|getFirstName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|ec
operator|.
name|getFirstName
argument_list|()
operator|)
argument_list|,
operator|(
name|ec
operator|.
name|getMiddleName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|ec
operator|.
name|getMiddleName
argument_list|()
operator|)
argument_list|,
operator|(
name|ec
operator|.
name|getLastName
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|ec
operator|.
name|getLastName
argument_list|()
operator|)
argument_list|,
operator|(
name|ec
operator|.
name|getEmailAddress
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|ec
operator|.
name|getEmailAddress
argument_list|()
operator|)
argument_list|,
operator|(
name|ec
operator|.
name|getPhone
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|ec
operator|.
name|getPhone
argument_list|()
operator|)
argument_list|)
expr_stmt|;
block|}
name|SimpleDateFormat
name|iDateFormat
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"EEE MM/dd, yyyy"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
comment|//SimpleDateFormat dateFormatDay = new SimpleDateFormat("EEE", Locale.US);
name|SimpleDateFormat
name|iDateFormat2
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|event
operator|.
name|getMeetings
argument_list|()
argument_list|)
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
operator|(
name|Meeting
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|int
name|start
init|=
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|meeting
operator|.
name|getStartPeriod
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|+
operator|(
name|meeting
operator|.
name|getStartOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|meeting
operator|.
name|getStartOffset
argument_list|()
operator|)
decl_stmt|;
name|int
name|startHour
init|=
name|start
operator|/
literal|60
decl_stmt|;
name|int
name|startMin
init|=
name|start
operator|%
literal|60
decl_stmt|;
name|int
name|end
init|=
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|meeting
operator|.
name|getStopPeriod
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|+
operator|(
name|meeting
operator|.
name|getStopOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|meeting
operator|.
name|getStopOffset
argument_list|()
operator|)
decl_stmt|;
name|int
name|endHour
init|=
name|end
operator|/
literal|60
decl_stmt|;
name|int
name|endMin
init|=
name|end
operator|%
literal|60
decl_stmt|;
name|String
name|location
init|=
operator|(
name|meeting
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
decl_stmt|;
name|String
name|approvedDate
init|=
operator|(
name|meeting
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|iDateFormat2
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|myForm
operator|.
name|addMeeting
argument_list|(
name|iDateFormat
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
argument_list|,
operator|(
name|startHour
operator|>
literal|12
condition|?
name|startHour
operator|-
literal|12
else|:
name|startHour
operator|)
operator|+
literal|":"
operator|+
operator|(
name|startMin
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|startMin
operator|+
operator|(
name|startHour
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
argument_list|,
operator|(
name|endHour
operator|>
literal|12
condition|?
name|endHour
operator|-
literal|12
else|:
name|endHour
operator|)
operator|+
literal|":"
operator|+
operator|(
name|endMin
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|endMin
operator|+
operator|(
name|endHour
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
argument_list|,
name|location
argument_list|,
name|approvedDate
argument_list|)
expr_stmt|;
block|}
name|myForm
operator|.
name|setCanEdit
argument_list|(
name|user
operator|.
name|isAdmin
argument_list|()
operator|||
name|user
operator|.
name|hasRole
argument_list|(
name|Roles
operator|.
name|EVENT_MGR_ROLE
argument_list|)
operator|||
name|user
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|event
operator|.
name|getMainContact
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|instanceof
name|ClassEvent
operator|||
name|event
operator|instanceof
name|ExamEvent
condition|)
block|{
name|myForm
operator|.
name|setCanEdit
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|Long
name|nextId
init|=
name|Navigation
operator|.
name|getNext
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Navigation
operator|.
name|sInstructionalOfferingLevel
argument_list|,
name|event
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|Long
name|prevId
init|=
name|Navigation
operator|.
name|getPrevious
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Navigation
operator|.
name|sInstructionalOfferingLevel
argument_list|,
name|event
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|myForm
operator|.
name|setPreviousId
argument_list|(
name|prevId
operator|==
literal|null
condition|?
literal|null
else|:
name|prevId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|myForm
operator|.
name|setNextId
argument_list|(
name|nextId
operator|==
literal|null
condition|?
literal|null
else|:
name|nextId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Course Event"
operator|.
name|equals
argument_list|(
name|myForm
operator|.
name|getEventType
argument_list|()
argument_list|)
condition|)
block|{
name|CourseEvent
name|courseEvent
init|=
operator|new
name|CourseEventDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|id
argument_list|)
argument_list|)
decl_stmt|;
empty_stmt|;
if|if
condition|(
operator|!
name|courseEvent
operator|.
name|getRelatedCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|WebTable
name|table
init|=
operator|new
name|WebTable
argument_list|(
literal|3
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Object"
block|,
literal|"Type"
block|,
literal|"Title"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|courseEvent
operator|.
name|getRelatedCourses
argument_list|()
argument_list|)
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
name|RelatedCourseInfo
name|rci
init|=
operator|(
name|RelatedCourseInfo
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|onclick
init|=
literal|null
decl_stmt|,
name|name
init|=
literal|null
decl_stmt|,
name|type
init|=
literal|null
decl_stmt|,
name|title
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|rci
operator|.
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='classDetail.do?cid="
operator|+
name|clazz
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//clazz.getClassLabel();
name|type
operator|=
literal|"Class"
expr_stmt|;
name|title
operator|=
name|clazz
operator|.
name|getSchedulePrintNote
argument_list|()
expr_stmt|;
if|if
condition|(
name|title
operator|==
literal|null
operator|||
name|title
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|title
operator|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
name|InstrOfferingConfig
name|config
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|config
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='instructionalOfferingDetail.do?io="
operator|+
name|config
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
empty_stmt|;
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//config.getCourseName()+" ["+config.getName()+"]";
name|type
operator|=
literal|"Configuration"
expr_stmt|;
name|title
operator|=
name|config
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
name|InstructionalOffering
name|offering
init|=
operator|(
name|InstructionalOffering
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|offering
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='instructionalOfferingDetail.do?io="
operator|+
name|offering
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
empty_stmt|;
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//offering.getCourseName();
name|type
operator|=
literal|"Offering"
expr_stmt|;
name|title
operator|=
name|offering
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
name|CourseOffering
name|course
init|=
operator|(
name|CourseOffering
operator|)
name|rci
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='instructionalOfferingDetail.do?io="
operator|+
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
empty_stmt|;
name|name
operator|=
name|rci
operator|.
name|getLabel
argument_list|()
expr_stmt|;
comment|//course.getCourseName();
name|type
operator|=
literal|"Course"
expr_stmt|;
name|title
operator|=
name|course
operator|.
name|getTitle
argument_list|()
expr_stmt|;
break|break;
block|}
name|table
operator|.
name|addLine
argument_list|(
name|onclick
argument_list|,
operator|new
name|String
index|[]
block|{
name|name
block|,
name|type
block|,
name|title
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"EventDetail.table"
argument_list|,
name|table
operator|.
name|printTable
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|myForm
operator|.
name|setEventName
argument_list|(
literal|"There is no event with this ID"
argument_list|)
expr_stmt|;
block|}
block|}
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"eventDetail.do?id="
operator|+
name|myForm
operator|.
name|getId
argument_list|()
argument_list|,
name|myForm
operator|.
name|getEventName
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"show"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

