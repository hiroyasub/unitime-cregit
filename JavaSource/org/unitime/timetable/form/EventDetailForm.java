begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
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
name|Collection
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
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
name|ActionMapping
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
name|Location
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
name|MeetingDAO
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

begin_class
specifier|public
class|class
name|EventDetailForm
extends|extends
name|ActionForm
block|{
specifier|private
name|String
name|iId
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|String
name|iEventName
decl_stmt|;
specifier|private
name|String
name|iEventType
decl_stmt|;
specifier|private
name|String
name|iMinCapacity
decl_stmt|;
specifier|private
name|String
name|iMaxCapacity
decl_stmt|;
specifier|private
name|String
name|iSponsoringOrgName
decl_stmt|;
specifier|private
name|Long
name|iSelected
decl_stmt|;
specifier|private
name|Long
name|iSelectedStandardNote
decl_stmt|;
specifier|private
name|String
name|iNewEventNote
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|MeetingBean
argument_list|>
name|iMeetings
init|=
operator|new
name|Vector
argument_list|<
name|MeetingBean
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|String
argument_list|>
name|iNotes
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|ContactBean
name|iMainContact
decl_stmt|;
specifier|private
name|Vector
argument_list|<
name|ContactBean
argument_list|>
name|iAdditionalContacts
init|=
operator|new
name|Vector
argument_list|<
name|ContactBean
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|iAdditionalEmails
decl_stmt|;
specifier|private
name|boolean
name|iCanEdit
decl_stmt|;
specifier|private
name|String
name|iPreviousId
decl_stmt|;
specifier|private
name|String
name|iNextId
decl_stmt|;
specifier|private
name|boolean
name|iAttendanceRequired
decl_stmt|;
specifier|private
name|Event
name|iEvent
decl_stmt|;
specifier|private
name|Long
index|[]
name|iSelectedMeetings
decl_stmt|;
specifier|private
name|Boolean
name|iCanDelete
decl_stmt|;
specifier|private
name|Boolean
name|iCanApprove
decl_stmt|;
specifier|private
name|Boolean
name|iIsManager
decl_stmt|;
comment|/**  	 * Method validate 	 * @param mapping 	 * @param request 	 * @return ActionErrors 	 */
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iEvent
operator|=
literal|null
expr_stmt|;
name|iEventName
operator|=
literal|null
expr_stmt|;
name|iMinCapacity
operator|=
literal|null
expr_stmt|;
name|iMaxCapacity
operator|=
literal|null
expr_stmt|;
name|iSponsoringOrgName
operator|=
literal|null
expr_stmt|;
name|iMainContact
operator|=
literal|null
expr_stmt|;
name|iAdditionalContacts
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iMeetings
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iNotes
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iCanEdit
operator|=
literal|false
expr_stmt|;
name|iCanDelete
operator|=
literal|false
expr_stmt|;
name|iCanApprove
operator|=
literal|false
expr_stmt|;
name|iPreviousId
operator|=
literal|null
expr_stmt|;
name|iNextId
operator|=
literal|null
expr_stmt|;
name|iAttendanceRequired
operator|=
literal|false
expr_stmt|;
name|iAdditionalEmails
operator|=
literal|null
expr_stmt|;
name|iSelectedMeetings
operator|=
literal|null
expr_stmt|;
name|iIsManager
operator|=
literal|false
expr_stmt|;
block|}
specifier|public
name|Event
name|getEvent
parameter_list|()
block|{
return|return
name|iEvent
return|;
block|}
specifier|public
name|void
name|setEvent
parameter_list|(
name|Event
name|event
parameter_list|)
block|{
name|iEvent
operator|=
name|event
expr_stmt|;
block|}
specifier|public
name|String
name|getEventName
parameter_list|()
block|{
return|return
name|iEventName
return|;
block|}
specifier|public
name|void
name|setEventName
parameter_list|(
name|String
name|eventName
parameter_list|)
block|{
name|iEventName
operator|=
name|eventName
expr_stmt|;
block|}
specifier|public
name|String
name|getEventType
parameter_list|()
block|{
return|return
name|iEventType
return|;
block|}
specifier|public
name|void
name|setEventType
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|iEventType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getMinCapacity
parameter_list|()
block|{
return|return
name|iMinCapacity
return|;
block|}
specifier|public
name|void
name|setMinCapacity
parameter_list|(
name|String
name|minCapacity
parameter_list|)
block|{
name|iMinCapacity
operator|=
name|minCapacity
expr_stmt|;
block|}
specifier|public
name|String
name|getMaxCapacity
parameter_list|()
block|{
return|return
name|iMaxCapacity
return|;
block|}
specifier|public
name|void
name|setMaxCapacity
parameter_list|(
name|String
name|maxCapacity
parameter_list|)
block|{
name|iMaxCapacity
operator|=
name|maxCapacity
expr_stmt|;
block|}
specifier|public
name|String
name|getSponsoringOrgName
parameter_list|()
block|{
return|return
name|iSponsoringOrgName
return|;
block|}
specifier|public
name|void
name|setSponsoringOrgName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iSponsoringOrgName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
operator|.
name|iId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|this
operator|.
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|Long
name|getSelected
parameter_list|()
block|{
return|return
name|iSelected
return|;
block|}
specifier|public
name|void
name|setSelected
parameter_list|(
name|Long
name|selectedId
parameter_list|)
block|{
name|iSelected
operator|=
name|selectedId
expr_stmt|;
block|}
specifier|public
name|boolean
name|getAttendanceRequired
parameter_list|()
block|{
return|return
name|iAttendanceRequired
return|;
block|}
specifier|public
name|void
name|setAttendanceRequired
parameter_list|(
name|boolean
name|attReq
parameter_list|)
block|{
name|iAttendanceRequired
operator|=
name|attReq
expr_stmt|;
block|}
specifier|public
name|String
name|getAdditionalEmails
parameter_list|()
block|{
return|return
name|iAdditionalEmails
return|;
block|}
specifier|public
name|void
name|setAdditionalEmails
parameter_list|(
name|String
name|emails
parameter_list|)
block|{
name|iAdditionalEmails
operator|=
name|emails
expr_stmt|;
block|}
specifier|public
name|Vector
argument_list|<
name|MeetingBean
argument_list|>
name|getMeetings
parameter_list|()
block|{
return|return
name|iMeetings
return|;
block|}
specifier|public
name|void
name|addMeeting
parameter_list|(
name|MeetingBean
name|meeting
parameter_list|)
block|{
name|iMeetings
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ContactBean
name|getMainContact
parameter_list|()
block|{
return|return
name|iMainContact
return|;
block|}
specifier|public
name|void
name|setMainContact
parameter_list|(
name|EventContact
name|contact
parameter_list|)
block|{
name|iMainContact
operator|=
operator|new
name|ContactBean
argument_list|()
expr_stmt|;
name|iMainContact
operator|.
name|setFirstName
argument_list|(
name|contact
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|iMainContact
operator|.
name|setMiddleName
argument_list|(
name|contact
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|iMainContact
operator|.
name|setLastName
argument_list|(
name|contact
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|iMainContact
operator|.
name|setEmail
argument_list|(
name|contact
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
expr_stmt|;
name|iMainContact
operator|.
name|setPhone
argument_list|(
name|contact
operator|.
name|getPhone
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Vector
argument_list|<
name|ContactBean
argument_list|>
name|getAdditionalContacts
parameter_list|()
block|{
return|return
name|iAdditionalContacts
return|;
block|}
specifier|public
name|void
name|addAdditionalContact
parameter_list|(
name|String
name|firstName
parameter_list|,
name|String
name|middleName
parameter_list|,
name|String
name|lastName
parameter_list|,
name|String
name|email
parameter_list|,
name|String
name|phone
parameter_list|)
block|{
name|ContactBean
name|contact
init|=
operator|new
name|ContactBean
argument_list|()
decl_stmt|;
name|contact
operator|.
name|setFirstName
argument_list|(
name|firstName
argument_list|)
expr_stmt|;
name|contact
operator|.
name|setMiddleName
argument_list|(
name|middleName
argument_list|)
expr_stmt|;
name|contact
operator|.
name|setLastName
argument_list|(
name|lastName
argument_list|)
expr_stmt|;
name|contact
operator|.
name|setEmail
argument_list|(
name|email
argument_list|)
expr_stmt|;
name|contact
operator|.
name|setPhone
argument_list|(
name|phone
argument_list|)
expr_stmt|;
name|iAdditionalContacts
operator|.
name|add
argument_list|(
name|contact
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Vector
argument_list|<
name|String
argument_list|>
name|getNotes
parameter_list|()
block|{
return|return
name|iNotes
return|;
block|}
specifier|public
name|void
name|addNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNotes
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|StandardEventNote
argument_list|>
name|getStandardNotes
parameter_list|()
block|{
return|return
name|StandardEventNote
operator|.
name|findAll
argument_list|()
return|;
block|}
specifier|public
name|Long
name|getSelectedStandardNote
parameter_list|()
block|{
return|return
name|iSelectedStandardNote
return|;
block|}
specifier|public
name|void
name|setSelectedStandardNote
parameter_list|(
name|Long
name|selected
parameter_list|)
block|{
name|iSelectedStandardNote
operator|=
name|selected
expr_stmt|;
block|}
specifier|public
name|String
name|getNewEventNote
parameter_list|()
block|{
return|return
name|iNewEventNote
return|;
block|}
specifier|public
name|void
name|setNewEventNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNewEventNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanEdit
parameter_list|()
block|{
return|return
name|iCanEdit
return|;
block|}
specifier|public
name|void
name|setCanEdit
parameter_list|(
name|boolean
name|canEdit
parameter_list|)
block|{
name|iCanEdit
operator|=
name|canEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanDelete
parameter_list|()
block|{
return|return
name|iCanDelete
return|;
block|}
specifier|public
name|void
name|setCanDelete
parameter_list|(
name|boolean
name|canDelete
parameter_list|)
block|{
name|iCanDelete
operator|=
name|canDelete
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanApprove
parameter_list|()
block|{
return|return
name|iCanApprove
return|;
block|}
specifier|public
name|void
name|setCanApprove
parameter_list|(
name|boolean
name|canApprove
parameter_list|)
block|{
name|iCanApprove
operator|=
name|canApprove
expr_stmt|;
block|}
specifier|public
name|String
name|getPreviousId
parameter_list|()
block|{
return|return
name|iPreviousId
return|;
block|}
specifier|public
name|void
name|setPreviousId
parameter_list|(
name|String
name|prevId
parameter_list|)
block|{
name|iPreviousId
operator|=
name|prevId
expr_stmt|;
block|}
specifier|public
name|String
name|getNextId
parameter_list|()
block|{
return|return
name|iNextId
return|;
block|}
specifier|public
name|void
name|setNextId
parameter_list|(
name|String
name|nextId
parameter_list|)
block|{
name|iNextId
operator|=
name|nextId
expr_stmt|;
block|}
specifier|public
name|Meeting
name|getSelectedMeeting
parameter_list|()
block|{
return|return
operator|(
name|MeetingDAO
operator|.
name|getInstance
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|iSelected
argument_list|)
return|;
block|}
specifier|public
name|Long
index|[]
name|getSelectedMeetings
parameter_list|()
block|{
return|return
name|iSelectedMeetings
return|;
block|}
specifier|public
name|void
name|setSelectedMeetings
parameter_list|(
name|Long
index|[]
name|selectedMeetings
parameter_list|)
block|{
name|iSelectedMeetings
operator|=
name|selectedMeetings
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanSelectAll
parameter_list|()
block|{
for|for
control|(
name|MeetingBean
name|m
range|:
name|getMeetings
argument_list|()
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getCanSelect
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|setIsManager
parameter_list|(
name|Boolean
name|isManager
parameter_list|)
block|{
name|iIsManager
operator|=
name|isManager
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getIsManager
parameter_list|()
block|{
return|return
name|iIsManager
return|;
block|}
specifier|public
specifier|static
class|class
name|MeetingBean
implements|implements
name|Comparable
argument_list|<
name|MeetingBean
argument_list|>
block|{
specifier|private
name|String
name|iDate
decl_stmt|;
specifier|private
name|String
name|iTime
decl_stmt|;
specifier|private
name|String
name|iLocation
decl_stmt|;
specifier|private
name|int
name|iLocationCapacity
decl_stmt|;
specifier|private
name|String
name|iApprovedDate
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iIsPast
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCanSelectMeeting
init|=
literal|false
decl_stmt|;
specifier|private
name|TreeSet
argument_list|<
name|MeetingBean
argument_list|>
name|iOverlaps
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
specifier|private
name|Long
name|iEventId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iType
init|=
literal|null
decl_stmt|;
specifier|public
name|MeetingBean
parameter_list|(
name|Date
name|date
parameter_list|,
name|int
name|startTime
parameter_list|,
name|int
name|endTime
parameter_list|,
name|Location
name|location
parameter_list|)
block|{
name|iDate
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"EEE MM/dd, yyyy"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
operator|.
name|format
argument_list|(
name|date
argument_list|)
expr_stmt|;
name|iTime
operator|=
operator|(
name|startTime
operator|==
literal|0
operator|&&
name|endTime
operator|==
name|Constants
operator|.
name|SLOTS_PER_DAY
condition|?
literal|"All Day"
else|:
name|Constants
operator|.
name|toTime
argument_list|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|startTime
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
argument_list|)
operator|+
literal|" - "
operator|+
name|Constants
operator|.
name|toTime
argument_list|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|endTime
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
argument_list|)
operator|)
expr_stmt|;
name|iLocation
operator|=
operator|(
name|location
operator|==
literal|null
condition|?
literal|""
else|:
name|location
operator|.
name|getLabel
argument_list|()
operator|)
expr_stmt|;
name|iLocationCapacity
operator|=
operator|(
name|location
operator|==
literal|null
condition|?
literal|0
else|:
name|location
operator|.
name|getCapacity
argument_list|()
operator|)
expr_stmt|;
block|}
specifier|public
name|MeetingBean
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
name|iEventId
operator|=
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventName
argument_list|()
expr_stmt|;
name|iType
operator|=
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventTypeAbbv
argument_list|()
expr_stmt|;
name|iUniqueId
operator|=
name|meeting
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iTime
operator|=
operator|(
name|meeting
operator|.
name|isAllDay
argument_list|()
condition|?
literal|"All Day"
else|:
name|Constants
operator|.
name|toTime
argument_list|(
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
argument_list|)
operator|+
literal|" - "
operator|+
name|Constants
operator|.
name|toTime
argument_list|(
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
argument_list|)
operator|)
expr_stmt|;
name|Location
name|location
init|=
name|meeting
operator|.
name|getLocation
argument_list|()
decl_stmt|;
name|iLocation
operator|=
operator|(
name|location
operator|==
literal|null
condition|?
literal|""
else|:
name|location
operator|.
name|getLabel
argument_list|()
operator|)
expr_stmt|;
name|iLocationCapacity
operator|=
operator|(
name|location
operator|==
literal|null
condition|?
literal|0
else|:
name|location
operator|.
name|getCapacity
argument_list|()
operator|)
expr_stmt|;
name|iApprovedDate
operator|=
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
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
operator|)
expr_stmt|;
name|iDate
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"EEE MM/dd, yyyy"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|iIsPast
operator|=
name|meeting
operator|.
name|getStartTime
argument_list|()
operator|.
name|before
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Long
name|getEventId
parameter_list|()
block|{
return|return
name|iEventId
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|TreeSet
argument_list|<
name|MeetingBean
argument_list|>
name|getOverlaps
parameter_list|()
block|{
return|return
name|iOverlaps
return|;
block|}
specifier|public
name|String
name|getDate
parameter_list|()
block|{
return|return
name|iDate
return|;
block|}
specifier|public
name|void
name|setDate
parameter_list|(
name|String
name|date
parameter_list|)
block|{
name|iDate
operator|=
name|date
expr_stmt|;
block|}
comment|//    	public Long getId() { return Math.round(1000.0*Math.random()); }
specifier|public
name|String
name|getTime
parameter_list|()
block|{
return|return
name|iTime
return|;
block|}
specifier|public
name|void
name|setTime
parameter_list|(
name|String
name|time
parameter_list|)
block|{
name|iTime
operator|=
name|time
expr_stmt|;
block|}
specifier|public
name|String
name|getLocation
parameter_list|()
block|{
return|return
name|iLocation
return|;
block|}
specifier|public
name|void
name|setLocation
parameter_list|(
name|String
name|location
parameter_list|)
block|{
name|iLocation
operator|=
name|location
expr_stmt|;
block|}
specifier|public
name|int
name|getLocationCapacity
parameter_list|()
block|{
return|return
name|iLocationCapacity
return|;
block|}
specifier|public
name|void
name|setLocationCapacity
parameter_list|(
name|int
name|capacity
parameter_list|)
block|{
name|iLocationCapacity
operator|=
name|capacity
expr_stmt|;
block|}
specifier|public
name|String
name|getApprovedDate
parameter_list|()
block|{
return|return
name|iApprovedDate
return|;
block|}
specifier|public
name|void
name|setApprovedDate
parameter_list|(
name|String
name|approvedDate
parameter_list|)
block|{
name|iApprovedDate
operator|=
name|approvedDate
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|id
parameter_list|)
block|{
name|iUniqueId
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|boolean
name|getIsPast
parameter_list|()
block|{
return|return
name|iIsPast
return|;
block|}
specifier|public
name|void
name|setIsPast
parameter_list|(
name|boolean
name|isPast
parameter_list|)
block|{
name|iIsPast
operator|=
name|isPast
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanSelect
parameter_list|()
block|{
return|return
name|iCanSelectMeeting
return|;
block|}
specifier|public
name|void
name|setCanSelect
parameter_list|(
name|boolean
name|canSelect
parameter_list|)
block|{
name|iCanSelectMeeting
operator|=
name|canSelect
expr_stmt|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|MeetingBean
name|meeting
parameter_list|)
block|{
name|int
name|cmp
init|=
name|iName
operator|.
name|compareTo
argument_list|(
name|meeting
operator|.
name|iName
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|iUniqueId
operator|.
name|compareTo
argument_list|(
name|meeting
operator|.
name|iUniqueId
argument_list|)
return|;
block|}
block|}
specifier|public
class|class
name|ContactBean
block|{
specifier|private
name|String
name|iFirstName
decl_stmt|;
specifier|private
name|String
name|iMiddleName
decl_stmt|;
specifier|private
name|String
name|iLastName
decl_stmt|;
specifier|private
name|String
name|iEmail
decl_stmt|;
specifier|private
name|String
name|iPhone
decl_stmt|;
specifier|public
name|ContactBean
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|iFirstName
return|;
block|}
specifier|public
name|void
name|setFirstName
parameter_list|(
name|String
name|firstName
parameter_list|)
block|{
name|iFirstName
operator|=
name|firstName
expr_stmt|;
block|}
specifier|public
name|String
name|getMiddleName
parameter_list|()
block|{
return|return
name|iMiddleName
return|;
block|}
specifier|public
name|void
name|setMiddleName
parameter_list|(
name|String
name|middleName
parameter_list|)
block|{
name|iMiddleName
operator|=
name|middleName
expr_stmt|;
block|}
specifier|public
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|iLastName
return|;
block|}
specifier|public
name|void
name|setLastName
parameter_list|(
name|String
name|lastName
parameter_list|)
block|{
name|iLastName
operator|=
name|lastName
expr_stmt|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|String
name|getPhone
parameter_list|()
block|{
return|return
name|iPhone
return|;
block|}
specifier|public
name|void
name|setPhone
parameter_list|(
name|String
name|phone
parameter_list|)
block|{
name|iPhone
operator|=
name|phone
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

