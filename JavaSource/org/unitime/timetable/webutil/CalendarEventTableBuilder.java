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
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|TimeZone
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
name|Debug
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
name|form
operator|.
name|EventListForm
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
name|MeetingListForm
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
name|util
operator|.
name|Constants
import|;
end_import

begin_class
specifier|public
class|class
name|CalendarEventTableBuilder
extends|extends
name|WebEventTableBuilder
block|{
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyyMMdd"
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|tf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"HHmmss"
argument_list|)
decl_stmt|;
specifier|public
name|CalendarEventTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|df
operator|.
name|setTimeZone
argument_list|(
name|TimeZone
operator|.
name|getTimeZone
argument_list|(
literal|"UTC"
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setTimeZone
argument_list|(
name|TimeZone
operator|.
name|getTimeZone
argument_list|(
literal|"UTC"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|(
name|EventListForm
name|form
parameter_list|)
block|{
name|String
name|type
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|form
operator|.
name|getEventTypes
argument_list|()
operator|.
name|length
operator|<
literal|5
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|form
operator|.
name|getEventTypes
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|type
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|type
operator|+=
literal|", "
expr_stmt|;
name|type
operator|+=
name|Event
operator|.
name|sEventTypesAbbv
index|[
name|form
operator|.
name|getEventTypes
argument_list|()
index|[
name|i
index|]
index|]
expr_stmt|;
block|}
name|type
operator|+=
literal|" "
expr_stmt|;
block|}
name|String
name|name
decl_stmt|;
switch|switch
condition|(
name|form
operator|.
name|getMode
argument_list|()
condition|)
block|{
case|case
name|EventListForm
operator|.
name|sModeAllApprovedEvents
case|:
name|name
operator|=
literal|"Approved "
operator|+
name|type
operator|+
literal|"Events"
expr_stmt|;
break|break;
case|case
name|EventListForm
operator|.
name|sModeAllConflictingEvents
case|:
name|name
operator|=
literal|"Conflicting "
operator|+
name|type
operator|+
literal|"Events"
expr_stmt|;
break|break;
case|case
name|EventListForm
operator|.
name|sModeAllEvents
case|:
name|name
operator|=
name|type
operator|+
literal|"Events"
expr_stmt|;
break|break;
case|case
name|EventListForm
operator|.
name|sModeAllEventsWaitingApproval
case|:
name|name
operator|=
name|type
operator|+
literal|"Events Awaiting Approval"
expr_stmt|;
break|break;
case|case
name|EventListForm
operator|.
name|sModeEvents4Approval
case|:
name|name
operator|=
name|type
operator|+
literal|"Events Awaiting My Approval"
expr_stmt|;
break|break;
case|case
name|EventListForm
operator|.
name|sModeMyEvents
case|:
name|name
operator|=
literal|"My "
operator|+
name|type
operator|+
literal|"Events"
expr_stmt|;
break|break;
default|default :
name|name
operator|=
literal|"Events"
expr_stmt|;
block|}
if|if
condition|(
name|form
operator|.
name|getEventDateFrom
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getEventDateFrom
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|form
operator|.
name|getEventDateTo
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getEventDateTo
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|name
operator|+=
literal|" Between "
operator|+
name|form
operator|.
name|getEventDateFrom
argument_list|()
operator|+
literal|" And "
operator|+
name|form
operator|.
name|getEventDateTo
argument_list|()
expr_stmt|;
block|}
name|name
operator|+=
literal|" From "
operator|+
name|form
operator|.
name|getEventDateFrom
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|form
operator|.
name|getEventDateTo
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getEventDateTo
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|name
operator|+=
literal|" Till "
operator|+
name|form
operator|.
name|getEventDateTo
argument_list|()
expr_stmt|;
block|}
return|return
name|name
return|;
block|}
specifier|public
name|void
name|printMeeting
parameter_list|(
name|PrintWriter
name|out
parameter_list|,
name|Meeting
name|meeting
parameter_list|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"BEGIN:VEVENT"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"UID:m"
operator|+
name|meeting
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"DTSTART:"
operator|+
name|df
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getStartTime
argument_list|()
argument_list|)
operator|+
literal|"T"
operator|+
name|tf
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getStartTime
argument_list|()
argument_list|)
operator|+
literal|"Z"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"DTEND:"
operator|+
name|df
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getStopTime
argument_list|()
argument_list|)
operator|+
literal|"T"
operator|+
name|tf
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getStopTime
argument_list|()
argument_list|)
operator|+
literal|"Z"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"SUMMARY:"
operator|+
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventName
argument_list|()
operator|+
literal|" ("
operator|+
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventTypeLabel
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
if|if
condition|(
name|meeting
operator|.
name|getLocation
argument_list|()
operator|!=
literal|null
condition|)
name|out
operator|.
name|println
argument_list|(
literal|"LOCATION:"
operator|+
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"STATUS:"
operator|+
operator|(
name|meeting
operator|.
name|isApproved
argument_list|()
condition|?
literal|"CONFIRMED"
else|:
literal|"TENTATIVE"
operator|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"END:VEVENT"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|File
name|calendarTableForEvents
parameter_list|(
name|EventListForm
name|form
parameter_list|)
block|{
name|List
name|events
init|=
name|loadEvents
argument_list|(
name|form
argument_list|)
decl_stmt|;
if|if
condition|(
name|events
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|boolean
name|mainContact
init|=
name|form
operator|.
name|isAdmin
argument_list|()
operator|||
name|form
operator|.
name|isEventManager
argument_list|()
decl_stmt|;
name|PrintWriter
name|out
init|=
literal|null
decl_stmt|;
try|try
block|{
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"events"
argument_list|,
literal|"ics"
argument_list|)
decl_stmt|;
name|out
operator|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"BEGIN:VCALENDAR"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"VERSION:2.0"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"CALSCALE:GREGORIAN"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"METHOD:PUBLISH"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"X-WR-CALNAME:"
operator|+
name|getName
argument_list|(
name|form
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"X-WR-TIMEZONE:"
operator|+
name|TimeZone
operator|.
name|getDefault
argument_list|()
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"PRODID:-//UniTime "
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
operator|+
literal|"/UniTime Events//NONSGML v1.0//EN"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|events
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Event
name|event
init|=
operator|(
name|Event
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|form
operator|.
name|getMode
argument_list|()
operator|==
name|EventListForm
operator|.
name|sModeEvents4Approval
condition|)
block|{
name|boolean
name|myApproval
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|j
init|=
name|event
operator|.
name|getMeetings
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Meeting
name|m
init|=
operator|(
name|Meeting
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
operator|&&
name|m
operator|.
name|getLocation
argument_list|()
operator|!=
literal|null
operator|&&
name|form
operator|.
name|getManagingDepartments
argument_list|()
operator|.
name|contains
argument_list|(
name|m
operator|.
name|getLocation
argument_list|()
operator|.
name|getControllingDepartment
argument_list|()
argument_list|)
condition|)
block|{
name|myApproval
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|myApproval
condition|)
continue|continue;
block|}
for|for
control|(
name|Meeting
name|meeting
range|:
operator|(
name|Set
argument_list|<
name|Meeting
argument_list|>
operator|)
name|event
operator|.
name|getMeetings
argument_list|()
control|)
name|printMeeting
argument_list|(
name|out
argument_list|,
name|meeting
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
literal|"END:VEVENT"
argument_list|)
expr_stmt|;
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
name|out
operator|=
literal|null
expr_stmt|;
return|return
name|file
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|out
operator|!=
literal|null
condition|)
block|{
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
return|return
literal|null
return|;
block|}
specifier|public
name|File
name|calendarTableForMeetings
parameter_list|(
name|MeetingListForm
name|form
parameter_list|)
block|{
name|List
name|meetings
init|=
name|loadMeetings
argument_list|(
name|form
argument_list|)
decl_stmt|;
if|if
condition|(
name|meetings
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|boolean
name|mainContact
init|=
name|form
operator|.
name|isAdmin
argument_list|()
operator|||
name|form
operator|.
name|isEventManager
argument_list|()
decl_stmt|;
name|PrintWriter
name|out
init|=
literal|null
decl_stmt|;
try|try
block|{
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"events"
argument_list|,
literal|"ics"
argument_list|)
decl_stmt|;
name|out
operator|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"BEGIN:VCALENDAR"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"VERSION:2.0"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"CALSCALE:GREGORIAN"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"METHOD:PUBLISH"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"X-WR-CALNAME:"
operator|+
name|getName
argument_list|(
name|form
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"X-WR-TIMEZONE:"
operator|+
name|TimeZone
operator|.
name|getDefault
argument_list|()
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"PRODID:-//UniTime "
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
operator|+
literal|"/UniTime Events//NONSGML v1.0//EN"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|meetings
operator|.
name|iterator
argument_list|()
init|;
name|it
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
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|printMeeting
argument_list|(
name|out
argument_list|,
name|meeting
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
literal|"END:VEVENT"
argument_list|)
expr_stmt|;
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
name|out
operator|=
literal|null
expr_stmt|;
return|return
name|file
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|out
operator|!=
literal|null
condition|)
block|{
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
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

