begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * TO DO   * - getSelected  * - setSelected  *   */
end_comment

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
name|EventContact
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
name|iMinCapacity
decl_stmt|;
specifier|private
name|String
name|iMaxCapacity
decl_stmt|;
specifier|private
name|String
name|iSponsoringOrg
decl_stmt|;
specifier|private
name|Long
name|iSelected
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
name|NoteBean
argument_list|>
name|iNotes
init|=
operator|new
name|Vector
argument_list|<
name|NoteBean
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
name|iSponsoringOrg
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
name|getSponsoringOrg
parameter_list|()
block|{
return|return
name|iSponsoringOrg
return|;
block|}
specifier|public
name|void
name|setSponsoringOrg
parameter_list|(
name|String
name|sponsoringOrg
parameter_list|)
block|{
name|iSponsoringOrg
operator|=
name|sponsoringOrg
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
name|getDoit
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setDoit
parameter_list|(
name|String
name|doit
parameter_list|)
block|{
name|this
operator|.
name|iOp
operator|=
name|doit
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
name|String
name|date
parameter_list|,
name|String
name|startTime
parameter_list|,
name|String
name|endTime
parameter_list|,
name|String
name|location
parameter_list|,
name|String
name|approvedDate
parameter_list|)
block|{
name|MeetingBean
name|meeting
init|=
operator|new
name|MeetingBean
argument_list|()
decl_stmt|;
name|meeting
operator|.
name|setDate
argument_list|(
name|date
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setStartTime
argument_list|(
name|startTime
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setEndTime
argument_list|(
name|endTime
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setLocation
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setApprovedDate
argument_list|(
name|approvedDate
argument_list|)
expr_stmt|;
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
name|NoteBean
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
name|textNote
parameter_list|)
block|{
name|NoteBean
name|note
init|=
operator|new
name|NoteBean
argument_list|()
decl_stmt|;
name|note
operator|.
name|setTextNote
argument_list|(
name|textNote
argument_list|)
expr_stmt|;
name|iNotes
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addStandardNote
parameter_list|(
name|String
name|standardNote
parameter_list|)
block|{
name|NoteBean
name|note
init|=
operator|new
name|NoteBean
argument_list|()
decl_stmt|;
name|note
operator|.
name|setStandardNote
argument_list|(
name|standardNote
argument_list|)
expr_stmt|;
name|iNotes
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
specifier|public
class|class
name|MeetingBean
block|{
specifier|private
name|String
name|iDate
decl_stmt|;
specifier|private
name|String
name|iStartTime
decl_stmt|;
specifier|private
name|String
name|iEndTime
decl_stmt|;
specifier|private
name|String
name|iLocation
decl_stmt|;
specifier|private
name|String
name|iApprovedDate
decl_stmt|;
specifier|public
name|MeetingBean
parameter_list|()
block|{
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
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|Math
operator|.
name|round
argument_list|(
literal|1000.0
operator|*
name|Math
operator|.
name|random
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getStartTime
parameter_list|()
block|{
return|return
name|iStartTime
return|;
block|}
specifier|public
name|void
name|setStartTime
parameter_list|(
name|String
name|time
parameter_list|)
block|{
name|iStartTime
operator|=
name|time
expr_stmt|;
block|}
specifier|public
name|String
name|getEndTime
parameter_list|()
block|{
return|return
name|iEndTime
return|;
block|}
specifier|public
name|void
name|setEndTime
parameter_list|(
name|String
name|time
parameter_list|)
block|{
name|iEndTime
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
specifier|public
class|class
name|NoteBean
block|{
specifier|private
name|String
name|iTextNote
decl_stmt|;
specifier|private
name|String
name|iStandardNote
decl_stmt|;
specifier|public
name|NoteBean
parameter_list|()
block|{
block|}
specifier|public
name|String
name|getTextNote
parameter_list|()
block|{
return|return
name|iTextNote
return|;
block|}
specifier|public
name|void
name|setTextNote
parameter_list|(
name|String
name|textNote
parameter_list|)
block|{
name|iTextNote
operator|=
name|textNote
expr_stmt|;
block|}
specifier|public
name|String
name|getStandardNote
parameter_list|()
block|{
return|return
name|iStandardNote
return|;
block|}
specifier|public
name|void
name|setStandardNote
parameter_list|(
name|String
name|standardNote
parameter_list|)
block|{
name|iStandardNote
operator|=
name|standardNote
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

