begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|webutil
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
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|mail
operator|.
name|Authenticator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Multipart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|PasswordAuthentication
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Transport
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|Message
operator|.
name|RecipientType
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|InternetAddress
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeBodyPart
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeMessage
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|mail
operator|.
name|internet
operator|.
name|MimeMultipart
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
name|commons
operator|.
name|logging
operator|.
name|Log
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
name|logging
operator|.
name|LogFactory
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
name|timetable
operator|.
name|ApplicationProperties
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
name|Event
operator|.
name|MultiMeeting
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
name|EventEmail
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|EventEmail
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Event
name|iEvent
init|=
literal|null
decl_stmt|;
specifier|private
name|TreeSet
argument_list|<
name|MultiMeeting
argument_list|>
name|iMeetings
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iNote
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iAction
init|=
name|sActionCreate
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sActionCreate
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sActionApprove
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sActionReject
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sActionAddMeeting
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sActionUpdate
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sActionDelete
init|=
literal|5
decl_stmt|;
specifier|public
name|EventEmail
parameter_list|(
name|Event
name|event
parameter_list|,
name|int
name|action
parameter_list|,
name|TreeSet
argument_list|<
name|MultiMeeting
argument_list|>
name|meetings
parameter_list|,
name|String
name|note
parameter_list|)
block|{
name|iEvent
operator|=
name|event
expr_stmt|;
name|iAction
operator|=
name|action
expr_stmt|;
name|iMeetings
operator|=
name|meetings
expr_stmt|;
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|void
name|send
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|String
name|subject
init|=
literal|null
decl_stmt|;
try|try
block|{
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|Roles
operator|.
name|ADMIN_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getRole
argument_list|()
argument_list|)
operator|||
name|Roles
operator|.
name|EVENT_MGR_ROLE
operator|.
name|equals
argument_list|(
name|user
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|iAction
operator|!=
name|sActionReject
operator|&&
name|iAction
operator|!=
name|sActionApprove
condition|)
return|return;
block|}
switch|switch
condition|(
name|iAction
condition|)
block|{
case|case
name|sActionCreate
case|:
name|subject
operator|=
literal|"Event "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|" created."
expr_stmt|;
break|break;
case|case
name|sActionApprove
case|:
name|subject
operator|=
literal|"Event "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|" approved."
expr_stmt|;
break|break;
case|case
name|sActionReject
case|:
name|subject
operator|=
literal|"Event "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|" rejected."
expr_stmt|;
break|break;
case|case
name|sActionUpdate
case|:
name|subject
operator|=
literal|"Event "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|" updated."
expr_stmt|;
break|break;
case|case
name|sActionAddMeeting
case|:
name|subject
operator|=
literal|"Event "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|" updated (one or more meetings added)."
expr_stmt|;
break|break;
case|case
name|sActionDelete
case|:
name|subject
operator|=
literal|"Event "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|" updated (one or more meetings deleted)."
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.event.confirmationEmail"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
condition|)
block|{
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_MSSG
argument_list|,
literal|"Confirmation emails are disabled."
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|message
init|=
literal|"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
decl_stmt|;
name|message
operator|+=
literal|"<html><head>"
expr_stmt|;
name|message
operator|+=
literal|"<title>"
operator|+
name|subject
operator|+
literal|"</title>"
expr_stmt|;
name|message
operator|+=
literal|"<meta http-equiv='Content-Type' content='text/html; charset=windows-1250'>"
expr_stmt|;
name|message
operator|+=
literal|"<meta name='Author' content='UniTime LLC'>"
expr_stmt|;
name|message
operator|+=
literal|"<style type='text/css'>"
expr_stmt|;
name|message
operator|+=
literal|"<!--"
operator|+
literal|"A:link     { color: blue; text-decoration: none; }"
operator|+
literal|"A:visited  { color: blue; text-decoration: none; }"
operator|+
literal|"A:active   { color: blue; text-decoration: none; }"
operator|+
literal|"A:hover    { color: blue; text-decoration: none; }"
operator|+
literal|"-->"
expr_stmt|;
name|message
operator|+=
literal|"</style></head><body bgcolor='#ffffff' style='font-size: 10pt; font-family: arial;'>"
expr_stmt|;
name|message
operator|+=
literal|"<table border='0' width='800' align='center' cellspacing='10'>"
expr_stmt|;
name|message
operator|+=
literal|"<tr><td colspan='2' style='border-bottom: 2px #2020FF solid;'><font size='+2'>"
expr_stmt|;
name|message
operator|+=
name|iEvent
operator|.
name|getEventName
argument_list|()
expr_stmt|;
name|message
operator|+=
literal|"</td></tr>"
expr_stmt|;
name|message
operator|+=
literal|"<tr><td>Event Type</td><td>"
operator|+
name|iEvent
operator|.
name|getEventTypeLabel
argument_list|()
operator|+
literal|"</td></tr>"
expr_stmt|;
if|if
condition|(
name|iEvent
operator|.
name|getMinCapacity
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|message
operator|+=
literal|"<tr><td>"
operator|+
operator|(
name|iEvent
operator|.
name|getEventType
argument_list|()
operator|==
name|Event
operator|.
name|sEventTypeSpecial
condition|?
literal|"Expected Size"
else|:
literal|"Event Capacity"
operator|)
operator|+
literal|":</td>"
expr_stmt|;
if|if
condition|(
name|iEvent
operator|.
name|getMaxCapacity
argument_list|()
operator|==
literal|null
operator|||
name|iEvent
operator|.
name|getMinCapacity
argument_list|()
operator|.
name|equals
argument_list|(
name|iEvent
operator|.
name|getMaxCapacity
argument_list|()
argument_list|)
condition|)
block|{
name|message
operator|+=
literal|"<td>"
operator|+
name|iEvent
operator|.
name|getMinCapacity
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
block|}
else|else
block|{
name|message
operator|+=
literal|"<td>"
operator|+
name|iEvent
operator|.
name|getMinCapacity
argument_list|()
operator|+
literal|" - "
operator|+
name|iEvent
operator|.
name|getMaxCapacity
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|message
operator|+=
literal|"<tr><td>Sponsoring Organization</td><td>"
operator|+
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"</td></tr>"
expr_stmt|;
block|}
if|if
condition|(
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|message
operator|+=
literal|"<tr><td>Main Contact</td><td>"
expr_stmt|;
name|message
operator|+=
literal|"<a href='mailto:"
operator|+
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getEmailAddress
argument_list|()
operator|+
literal|"'>"
expr_stmt|;
if|if
condition|(
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getLastName
argument_list|()
operator|!=
literal|null
condition|)
name|message
operator|+=
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getLastName
argument_list|()
expr_stmt|;
if|if
condition|(
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|)
name|message
operator|+=
literal|", "
operator|+
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getFirstName
argument_list|()
expr_stmt|;
if|if
condition|(
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|)
name|message
operator|+=
literal|", "
operator|+
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getMiddleName
argument_list|()
expr_stmt|;
name|message
operator|+=
literal|"</a></td></tr>"
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|iMeetings
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|message
operator|+=
literal|"<tr><td colspan='2' style='border-bottom: 1px #2020FF solid; font-variant:small-caps;'>"
expr_stmt|;
name|message
operator|+=
literal|"<br><font size='+1'>"
expr_stmt|;
switch|switch
condition|(
name|iAction
condition|)
block|{
case|case
name|sActionCreate
case|:
name|message
operator|+=
literal|"Following meetings were created by you or on your behalf"
expr_stmt|;
break|break;
case|case
name|sActionApprove
case|:
name|message
operator|+=
literal|"Following meetings were approved"
expr_stmt|;
break|break;
case|case
name|sActionReject
case|:
name|message
operator|+=
literal|"Following meetings were rejected"
expr_stmt|;
if|if
condition|(
name|iNote
operator|!=
literal|null
operator|&&
name|iNote
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|message
operator|+=
literal|" (see the note bellow for more details)"
expr_stmt|;
break|break;
case|case
name|sActionAddMeeting
case|:
name|message
operator|+=
literal|"Following meetings were added by you or on your behalf"
expr_stmt|;
break|break;
case|case
name|sActionDelete
case|:
name|message
operator|+=
literal|"Following meetings were deleted by you or on your behalf"
expr_stmt|;
break|break;
block|}
name|message
operator|+=
literal|"</font>"
expr_stmt|;
name|message
operator|+=
literal|"</td></tr><tr><td colspan='2'>"
expr_stmt|;
name|message
operator|+=
literal|"<table border='0' width='100%'>"
expr_stmt|;
name|message
operator|+=
literal|"<tr><td><i>Date</i></td><td><i>Time</i></td><td><i>Location</i></td></tr>"
expr_stmt|;
for|for
control|(
name|MultiMeeting
name|m
range|:
name|iMeetings
control|)
block|{
name|message
operator|+=
literal|"<tr><td>"
expr_stmt|;
name|message
operator|+=
name|m
operator|.
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|message
operator|+=
operator|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
literal|" - "
operator|+
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|last
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
else|:
literal|""
operator|)
expr_stmt|;
name|message
operator|+=
literal|"</td><td>"
expr_stmt|;
name|message
operator|+=
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|startTime
argument_list|()
operator|+
literal|" - "
operator|+
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|stopTime
argument_list|()
expr_stmt|;
name|message
operator|+=
literal|"</td><td>"
expr_stmt|;
name|message
operator|+=
operator|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
expr_stmt|;
name|message
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
name|message
operator|+=
literal|"</table></td></tr>"
expr_stmt|;
block|}
if|if
condition|(
name|iNote
operator|!=
literal|null
operator|&&
name|iNote
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|message
operator|+=
literal|"<tr><td colspan='2' style='border-bottom: 1px #2020FF solid; font-variant:small-caps;'>"
expr_stmt|;
name|message
operator|+=
literal|"<br><font size='+1'>Notes</font>"
expr_stmt|;
name|message
operator|+=
literal|"</td></tr><tr><td colspan='2'>"
expr_stmt|;
name|message
operator|+=
name|iNote
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
expr_stmt|;
name|message
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
if|if
condition|(
name|iAction
operator|!=
name|sActionCreate
condition|)
block|{
name|message
operator|+=
literal|"<tr><td colspan='2' style='border-bottom: 1px #2020FF solid; font-variant:small-caps;'>"
expr_stmt|;
name|message
operator|+=
literal|"<br><font size='+1'>All Meetings of "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|"</font>"
expr_stmt|;
name|message
operator|+=
literal|"</td></tr>"
expr_stmt|;
if|if
condition|(
name|iEvent
operator|.
name|getMeetings
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|message
operator|+=
literal|"<tr><td colspan='2' style='background-color:';>"
expr_stmt|;
name|message
operator|+=
literal|"No meeting left, the event "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|" was deleted as well."
expr_stmt|;
name|message
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
else|else
block|{
name|message
operator|+=
literal|"<tr><td colspan='2'>"
expr_stmt|;
name|message
operator|+=
literal|"<table border='0' width='100%'>"
expr_stmt|;
name|message
operator|+=
literal|"<tr><td><i>Date</i></td><td><i>Time</i></td><td><i>Location</i></td><td><i>Capacity</i></td><td><i>Approved</i></td></tr>"
expr_stmt|;
for|for
control|(
name|MultiMeeting
name|m
range|:
name|iEvent
operator|.
name|getMultiMeetings
argument_list|()
control|)
block|{
name|message
operator|+=
literal|"<tr><td>"
expr_stmt|;
name|message
operator|+=
name|m
operator|.
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
expr_stmt|;
name|message
operator|+=
operator|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
literal|" - "
operator|+
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|last
argument_list|()
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
else|:
literal|""
operator|)
expr_stmt|;
name|message
operator|+=
literal|"</td><td>"
expr_stmt|;
name|message
operator|+=
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|startTime
argument_list|()
operator|+
literal|" - "
operator|+
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|stopTime
argument_list|()
expr_stmt|;
name|message
operator|+=
literal|"</td><td>"
expr_stmt|;
name|message
operator|+=
operator|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|)
expr_stmt|;
name|message
operator|+=
literal|"</td><td>"
expr_stmt|;
name|message
operator|+=
operator|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
literal|" "
operator|+
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getLocation
argument_list|()
operator|.
name|getCapacity
argument_list|()
operator|)
expr_stmt|;
name|message
operator|+=
literal|"</td><td>"
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|isPast
argument_list|()
condition|)
block|{
name|message
operator|+=
literal|""
expr_stmt|;
block|}
if|else if
condition|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
condition|)
block|{
name|message
operator|+=
literal|"<i>Waiting Approval</i>"
expr_stmt|;
block|}
else|else
block|{
name|message
operator|+=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|m
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|message
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
name|message
operator|+=
literal|"</table></td></tr>"
expr_stmt|;
block|}
name|message
operator|+=
literal|"<tr><td colspan='2' style='border-bottom: 1px #2020FF solid; font-variant:small-caps;'>"
expr_stmt|;
name|message
operator|+=
literal|"<br><font size='+1'>All Notes of "
operator|+
name|iEvent
operator|.
name|getEventName
argument_list|()
operator|+
literal|"</font>"
expr_stmt|;
name|message
operator|+=
literal|"</td></tr><tr><td colspan='2'>"
expr_stmt|;
name|message
operator|+=
literal|"<table border='0' width='100%' cellspacing='0' cellpadding='3'>"
expr_stmt|;
name|message
operator|+=
literal|"<tr><td><i>Date</i></td><td><i>Action</i></td><td><i>Meetings</i></td><td><i>Note</i></td></tr>"
expr_stmt|;
for|for
control|(
name|EventNote
name|note
range|:
operator|new
name|TreeSet
argument_list|<
name|EventNote
argument_list|>
argument_list|(
name|iEvent
operator|.
name|getNotes
argument_list|()
argument_list|)
control|)
block|{
name|message
operator|+=
literal|"<tr style=\"background-color:"
operator|+
name|EventNote
operator|.
name|sEventNoteTypeBgColor
index|[
name|note
operator|.
name|getNoteType
argument_list|()
index|]
operator|+
literal|";\" valign='top'>"
expr_stmt|;
name|message
operator|+=
literal|"<td>"
operator|+
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd hh:mmaa"
argument_list|)
operator|.
name|format
argument_list|(
name|note
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
operator|+
literal|"</td>"
expr_stmt|;
name|message
operator|+=
literal|"<td>"
operator|+
name|EventNote
operator|.
name|sEventNoteTypeName
index|[
name|note
operator|.
name|getNoteType
argument_list|()
index|]
operator|+
literal|"</td>"
expr_stmt|;
name|message
operator|+=
literal|"<td>"
operator|+
name|note
operator|.
name|getMeetingsHtml
argument_list|()
operator|+
literal|"</td>"
expr_stmt|;
name|message
operator|+=
literal|"<td>"
operator|+
operator|(
name|note
operator|.
name|getTextNote
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|note
operator|.
name|getTextNote
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
operator|)
operator|+
literal|"</td>"
expr_stmt|;
name|message
operator|+=
literal|"</tr>"
expr_stmt|;
block|}
name|message
operator|+=
literal|"</table></td></tr>"
expr_stmt|;
block|}
name|message
operator|+=
literal|"<tr><td colspan='2'>&nbsp;</td></tr>"
expr_stmt|;
name|message
operator|+=
literal|"<tr><td colspan='2' style='border-top: 1px #2020FF solid;' align='center'>"
expr_stmt|;
name|message
operator|+=
literal|"This email was automatically generated at "
expr_stmt|;
name|message
operator|+=
name|request
operator|.
name|getScheme
argument_list|()
operator|+
literal|"://"
operator|+
name|request
operator|.
name|getServerName
argument_list|()
operator|+
literal|":"
operator|+
name|request
operator|.
name|getServerPort
argument_list|()
operator|+
name|request
operator|.
name|getContextPath
argument_list|()
expr_stmt|;
name|message
operator|+=
literal|",<br>by UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|"."
operator|+
name|Constants
operator|.
name|BLD_NUMBER
operator|.
name|replaceAll
argument_list|(
literal|"@build.number@"
argument_list|,
literal|"?"
argument_list|)
expr_stmt|;
name|message
operator|+=
literal|" (Univesity Timetabling Application, http://www.unitime.org)."
expr_stmt|;
name|message
operator|+=
literal|"</td></tr></table>"
expr_stmt|;
name|Properties
name|p
init|=
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
decl_stmt|;
if|if
condition|(
name|p
operator|.
name|getProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|)
operator|==
literal|null
operator|&&
name|p
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
operator|!=
literal|null
condition|)
name|p
operator|.
name|setProperty
argument_list|(
literal|"mail.smtp.host"
argument_list|,
name|p
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.smtp.host"
argument_list|)
argument_list|)
expr_stmt|;
name|Authenticator
name|a
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.user"
argument_list|)
operator|!=
literal|null
operator|&&
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.pwd"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|p
operator|.
name|setProperty
argument_list|(
literal|"mail.smtp.user"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.user"
argument_list|)
argument_list|)
expr_stmt|;
name|p
operator|.
name|setProperty
argument_list|(
literal|"mail.smtp.auth"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|a
operator|=
operator|new
name|Authenticator
argument_list|()
block|{
specifier|public
name|PasswordAuthentication
name|getPasswordAuthentication
parameter_list|()
block|{
return|return
operator|new
name|PasswordAuthentication
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.user"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.mail.pwd"
argument_list|)
argument_list|)
return|;
block|}
block|}
expr_stmt|;
block|}
name|javax
operator|.
name|mail
operator|.
name|Session
name|mailSession
init|=
name|javax
operator|.
name|mail
operator|.
name|Session
operator|.
name|getDefaultInstance
argument_list|(
name|p
argument_list|,
name|a
argument_list|)
decl_stmt|;
name|MimeMessage
name|mail
init|=
operator|new
name|MimeMessage
argument_list|(
name|mailSession
argument_list|)
decl_stmt|;
name|mail
operator|.
name|setSubject
argument_list|(
name|subject
argument_list|)
expr_stmt|;
name|InternetAddress
name|from
init|=
operator|new
name|InternetAddress
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.contact.email"
argument_list|)
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.inquiry.sender.name"
argument_list|)
argument_list|)
decl_stmt|;
name|mail
operator|.
name|setFrom
argument_list|(
name|from
argument_list|)
expr_stmt|;
name|MimeBodyPart
name|text
init|=
operator|new
name|MimeBodyPart
argument_list|()
decl_stmt|;
name|text
operator|.
name|setContent
argument_list|(
name|message
argument_list|,
literal|"text/html"
argument_list|)
expr_stmt|;
name|Multipart
name|body
init|=
operator|new
name|MimeMultipart
argument_list|()
decl_stmt|;
name|body
operator|.
name|addBodyPart
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|String
name|to
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|!=
literal|null
operator|&&
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getEmailAddress
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|mail
operator|.
name|addRecipient
argument_list|(
name|RecipientType
operator|.
name|TO
argument_list|,
operator|new
name|InternetAddress
argument_list|(
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getEmailAddress
argument_list|()
argument_list|,
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|to
operator|=
literal|"<a href='mailto:"
operator|+
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getEmailAddress
argument_list|()
operator|+
literal|"'>"
operator|+
name|iEvent
operator|.
name|getMainContact
argument_list|()
operator|.
name|getShortName
argument_list|()
operator|+
literal|"</a>"
expr_stmt|;
block|}
if|if
condition|(
name|iEvent
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
operator|&&
name|iEvent
operator|.
name|getEmail
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|iEvent
operator|.
name|getEmail
argument_list|()
argument_list|,
literal|";:,\n\r\t"
argument_list|)
init|;
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|email
init|=
name|stk
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|mail
operator|.
name|addRecipient
argument_list|(
name|RecipientType
operator|.
name|CC
argument_list|,
operator|new
name|InternetAddress
argument_list|(
name|email
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|to
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|to
operator|+=
literal|", "
expr_stmt|;
name|to
operator|+=
name|email
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|!=
literal|null
operator|&&
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
operator|&&
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|mail
operator|.
name|addRecipient
argument_list|(
name|RecipientType
operator|.
name|TO
argument_list|,
operator|new
name|InternetAddress
argument_list|(
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getEmail
argument_list|()
argument_list|,
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|to
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|to
operator|+=
literal|", "
expr_stmt|;
name|to
operator|+=
literal|"<a href='mailto:"
operator|+
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getEmail
argument_list|()
operator|+
literal|"'>"
operator|+
name|iEvent
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"</a>"
expr_stmt|;
block|}
name|mail
operator|.
name|setSentDate
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|mail
operator|.
name|setContent
argument_list|(
name|body
argument_list|)
expr_stmt|;
name|Transport
operator|.
name|send
argument_list|(
name|mail
argument_list|)
expr_stmt|;
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_MSSG
argument_list|,
name|subject
operator|+
literal|" Confirmation email sent to "
operator|+
name|to
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|request
operator|.
name|getSession
argument_list|()
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_WARN
argument_list|,
operator|(
name|subject
operator|==
literal|null
condition|?
literal|""
else|:
name|subject
operator|+
literal|" "
operator|)
operator|+
literal|"Unable to send confirmation email, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

