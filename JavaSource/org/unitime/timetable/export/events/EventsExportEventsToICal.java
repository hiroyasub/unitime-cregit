begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|DateFormat
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
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
name|Locale
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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|ContactInterface
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
name|Constants
import|;
end_import

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:events.ics"
argument_list|)
specifier|public
class|class
name|EventsExportEventsToICal
extends|extends
name|EventsExporter
block|{
specifier|private
name|DateFormat
name|iDateFormat
decl_stmt|,
name|iTimeFormat
decl_stmt|;
specifier|private
specifier|static
name|String
index|[]
name|DAYS
init|=
operator|new
name|String
index|[]
block|{
literal|"MO"
block|,
literal|"TU"
block|,
literal|"WE"
block|,
literal|"TH"
block|,
literal|"FR"
block|,
literal|"SA"
block|,
literal|"SU"
block|}
decl_stmt|;
specifier|public
name|EventsExportEventsToICal
parameter_list|()
block|{
name|iDateFormat
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyyMMdd"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
expr_stmt|;
name|iDateFormat
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
name|iTimeFormat
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"HHmmss"
argument_list|,
name|Locale
operator|.
name|US
argument_list|)
expr_stmt|;
name|iTimeFormat
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
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"events.ics"
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
parameter_list|)
throws|throws
name|IOException
block|{
name|helper
operator|.
name|setup
argument_list|(
literal|"text/calendar"
argument_list|,
name|reference
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|PrintWriter
name|out
init|=
name|helper
operator|.
name|getWriter
argument_list|()
decl_stmt|;
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
literal|"X-WR-CALNAME:UniTime Schedule"
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
name|getVersion
argument_list|()
operator|+
literal|"/Events Calendar//NONSGML v1.0//EN"
argument_list|)
expr_stmt|;
for|for
control|(
name|EventInterface
name|event
range|:
name|events
control|)
name|print
argument_list|(
name|out
argument_list|,
name|event
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"END:VCALENDAR"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|print
parameter_list|(
name|PrintWriter
name|out
parameter_list|,
name|EventInterface
name|event
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|print
argument_list|(
name|out
argument_list|,
name|event
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|print
parameter_list|(
name|PrintWriter
name|out
parameter_list|,
name|EventInterface
name|event
parameter_list|,
name|ICalendarStatus
name|status
parameter_list|)
throws|throws
name|IOException
block|{
name|TreeSet
argument_list|<
name|ICalendarMeeting
argument_list|>
name|meetings
init|=
operator|new
name|TreeSet
argument_list|<
name|ICalendarMeeting
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Integer
argument_list|>
name|days
init|=
operator|new
name|TreeSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|event
operator|.
name|hasMeetings
argument_list|()
condition|)
name|meetings
label|:
for|for
control|(
name|MeetingInterface
name|m
range|:
name|event
operator|.
name|getMeetings
argument_list|()
control|)
block|{
if|if
condition|(
name|m
operator|.
name|isArrangeHours
argument_list|()
condition|)
continue|continue;
name|ICalendarMeeting
name|x
init|=
operator|new
name|ICalendarMeeting
argument_list|(
name|m
argument_list|,
name|status
operator|!=
literal|null
condition|?
name|status
else|:
name|m
operator|.
name|isApproved
argument_list|()
condition|?
name|ICalendarStatus
operator|.
name|CONFIRMED
else|:
name|ICalendarStatus
operator|.
name|TENTATIVE
argument_list|)
decl_stmt|;
for|for
control|(
name|ICalendarMeeting
name|icm
range|:
name|meetings
control|)
if|if
condition|(
name|icm
operator|.
name|merge
argument_list|(
name|x
argument_list|)
condition|)
continue|continue
name|meetings
continue|;
name|meetings
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
name|days
operator|.
name|add
argument_list|(
name|x
operator|.
name|getDayOfWeek
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|meetings
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
name|ICalendarMeeting
name|first
init|=
name|meetings
operator|.
name|first
argument_list|()
decl_stmt|;
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
literal|"SEQUENCE:"
operator|+
operator|(
name|event
operator|.
name|hasNotes
argument_list|()
condition|?
name|event
operator|.
name|getNotes
argument_list|()
operator|.
name|size
argument_list|()
else|:
literal|0
operator|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"UID:"
operator|+
name|event
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"SUMMARY:"
operator|+
name|event
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"DESCRIPTION:"
operator|+
operator|(
name|event
operator|.
name|getInstruction
argument_list|()
operator|!=
literal|null
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
operator|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"DTSTART:"
operator|+
name|first
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"DTEND:"
operator|+
name|first
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|print
argument_list|(
literal|"RRULE:FREQ=WEEKLY;BYDAY="
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|Integer
argument_list|>
name|i
init|=
name|days
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
name|out
operator|.
name|print
argument_list|(
name|DAYS
index|[
name|i
operator|.
name|next
argument_list|()
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|out
operator|.
name|print
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
literal|";WKST=MO;UNTIL="
operator|+
name|meetings
operator|.
name|last
argument_list|()
operator|.
name|getEndDate
argument_list|()
operator|+
literal|"T"
operator|+
name|first
operator|.
name|getEndTime
argument_list|()
operator|+
literal|"Z"
argument_list|)
expr_stmt|;
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|first
operator|.
name|iStart
argument_list|)
expr_stmt|;
name|String
name|date
init|=
name|iDateFormat
operator|.
name|format
argument_list|(
name|cal
operator|.
name|getTime
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|dow
init|=
name|first
operator|.
name|getDayOfWeek
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|>
name|extra
init|=
operator|new
name|ArrayList
argument_list|<
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|date
operator|.
name|compareTo
argument_list|(
name|meetings
operator|.
name|last
argument_list|()
operator|.
name|getEndDate
argument_list|()
argument_list|)
operator|<=
literal|0
condition|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|ICalendarMeeting
name|ics
range|:
name|meetings
control|)
block|{
if|if
condition|(
name|date
operator|.
name|equals
argument_list|(
name|ics
operator|.
name|getStartDate
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
if|if
condition|(
operator|!
name|first
operator|.
name|same
argument_list|(
name|ics
argument_list|)
condition|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|x
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|extra
operator|.
name|add
argument_list|(
name|x
argument_list|)
expr_stmt|;
name|x
operator|.
name|add
argument_list|(
literal|"RECURRENCE-ID:"
operator|+
name|ics
operator|.
name|getStartDate
argument_list|()
operator|+
literal|"T"
operator|+
name|first
operator|.
name|getStartTime
argument_list|()
operator|+
literal|"Z"
argument_list|)
expr_stmt|;
name|x
operator|.
name|add
argument_list|(
literal|"DTSTART:"
operator|+
name|ics
operator|.
name|getStart
argument_list|()
argument_list|)
expr_stmt|;
name|x
operator|.
name|add
argument_list|(
literal|"DTEND:"
operator|+
name|ics
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
name|x
operator|.
name|add
argument_list|(
literal|"LOCATION:"
operator|+
name|ics
operator|.
name|getLocation
argument_list|()
argument_list|)
expr_stmt|;
name|x
operator|.
name|add
argument_list|(
literal|"STATUS:"
operator|+
name|ics
operator|.
name|getStatus
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|found
operator|&&
name|days
operator|.
name|contains
argument_list|(
name|dow
argument_list|)
condition|)
name|out
operator|.
name|println
argument_list|(
literal|"EXDATE:"
operator|+
name|date
operator|+
literal|"T"
operator|+
name|first
operator|.
name|getStartTime
argument_list|()
operator|+
literal|"Z"
argument_list|)
expr_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|date
operator|=
name|iDateFormat
operator|.
name|format
argument_list|(
name|cal
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
name|dow
operator|=
operator|(
name|dow
operator|+
literal|1
operator|)
operator|%
literal|7
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
literal|"LOCATION:"
operator|+
name|first
operator|.
name|getLocation
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"STATUS:"
operator|+
name|first
operator|.
name|getStatus
argument_list|()
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|hasInstructors
argument_list|()
condition|)
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ContactInterface
name|instructor
range|:
name|event
operator|.
name|getInstructors
argument_list|()
control|)
block|{
name|out
operator|.
name|println
argument_list|(
operator|(
name|idx
operator|++
operator|==
literal|0
condition|?
literal|"ORGANIZER"
else|:
literal|"ATTENDEE"
operator|)
operator|+
literal|";ROLE=CHAIR;CN=\""
operator|+
name|instructor
operator|.
name|getName
argument_list|(
name|MESSAGES
argument_list|)
operator|+
literal|"\":MAILTO:"
operator|+
operator|(
name|instructor
operator|.
name|hasEmail
argument_list|()
condition|?
name|instructor
operator|.
name|getEmail
argument_list|()
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|event
operator|.
name|hasSponsor
argument_list|()
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"ORGANIZER;ROLE=CHAIR;CN=\""
operator|+
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"\":MAILTO:"
operator|+
operator|(
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|hasEmail
argument_list|()
condition|?
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|getEmail
argument_list|()
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|event
operator|.
name|hasContact
argument_list|()
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"ORGANIZER;ROLE=CHAIR;CN=\""
operator|+
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|getName
argument_list|(
name|MESSAGES
argument_list|)
operator|+
literal|"\":MAILTO:"
operator|+
operator|(
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|hasEmail
argument_list|()
condition|?
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|getEmail
argument_list|()
else|:
literal|""
operator|)
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
for|for
control|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|x
range|:
name|extra
control|)
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
literal|"SEQUENCE:"
operator|+
operator|(
name|event
operator|.
name|hasNotes
argument_list|()
condition|?
name|event
operator|.
name|getNotes
argument_list|()
operator|.
name|size
argument_list|()
else|:
literal|0
operator|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"UID:"
operator|+
name|event
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"SUMMARY:"
operator|+
name|event
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"DESCRIPTION:"
operator|+
operator|(
name|event
operator|.
name|getInstruction
argument_list|()
operator|!=
literal|null
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
operator|)
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|s
range|:
name|x
control|)
name|out
operator|.
name|println
argument_list|(
name|s
argument_list|)
expr_stmt|;
if|if
condition|(
name|event
operator|.
name|hasInstructors
argument_list|()
condition|)
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|ContactInterface
name|instructor
range|:
name|event
operator|.
name|getInstructors
argument_list|()
control|)
block|{
name|out
operator|.
name|println
argument_list|(
operator|(
name|idx
operator|++
operator|==
literal|0
condition|?
literal|"ORGANIZER"
else|:
literal|"ATTENDEE"
operator|)
operator|+
literal|";ROLE=CHAIR;CN=\""
operator|+
name|instructor
operator|.
name|getName
argument_list|(
name|MESSAGES
argument_list|)
operator|+
literal|"\":MAILTO:"
operator|+
operator|(
name|instructor
operator|.
name|hasEmail
argument_list|()
condition|?
name|instructor
operator|.
name|getEmail
argument_list|()
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|event
operator|.
name|hasSponsor
argument_list|()
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"ORGANIZER;ROLE=CHAIR;CN=\""
operator|+
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"\":MAILTO:"
operator|+
operator|(
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|hasEmail
argument_list|()
condition|?
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|getEmail
argument_list|()
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|event
operator|.
name|hasContact
argument_list|()
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"ORGANIZER;ROLE=CHAIR;CN=\""
operator|+
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|getName
argument_list|(
name|MESSAGES
argument_list|)
operator|+
literal|"\":MAILTO:"
operator|+
operator|(
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|hasEmail
argument_list|()
condition|?
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|getEmail
argument_list|()
else|:
literal|""
operator|)
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
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|protected
name|boolean
name|checkRights
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
specifier|public
specifier|static
enum|enum
name|ICalendarStatus
block|{
name|CANCELLED
block|,
name|TENTATIVE
block|,
name|CONFIRMED
block|}
empty_stmt|;
specifier|public
class|class
name|ICalendarMeeting
implements|implements
name|Comparable
argument_list|<
name|ICalendarMeeting
argument_list|>
block|{
specifier|private
name|Date
name|iStart
decl_stmt|,
name|iEnd
decl_stmt|;
specifier|private
name|String
name|iLocation
decl_stmt|;
specifier|private
name|int
name|iDayOfWeek
decl_stmt|;
specifier|private
name|ICalendarStatus
name|iStatus
decl_stmt|;
specifier|public
name|ICalendarMeeting
parameter_list|(
name|MeetingInterface
name|meeting
parameter_list|,
name|ICalendarStatus
name|status
parameter_list|)
block|{
name|iStart
operator|=
operator|new
name|Date
argument_list|(
name|meeting
operator|.
name|getStartTime
argument_list|()
argument_list|)
expr_stmt|;
name|iEnd
operator|=
operator|new
name|Date
argument_list|(
name|meeting
operator|.
name|getStopTime
argument_list|()
argument_list|)
expr_stmt|;
name|iDayOfWeek
operator|=
name|meeting
operator|.
name|getDayOfWeek
argument_list|()
expr_stmt|;
name|iLocation
operator|=
name|meeting
operator|.
name|getLocationName
argument_list|()
expr_stmt|;
name|iStatus
operator|=
name|status
expr_stmt|;
block|}
specifier|public
name|String
name|getStart
parameter_list|()
block|{
return|return
name|iDateFormat
operator|.
name|format
argument_list|(
name|iStart
argument_list|)
operator|+
literal|"T"
operator|+
name|iTimeFormat
operator|.
name|format
argument_list|(
name|iStart
argument_list|)
operator|+
literal|"Z"
return|;
block|}
specifier|public
name|String
name|getStartTime
parameter_list|()
block|{
return|return
name|iTimeFormat
operator|.
name|format
argument_list|(
name|iStart
argument_list|)
return|;
block|}
specifier|public
name|String
name|getStartDate
parameter_list|()
block|{
return|return
name|iDateFormat
operator|.
name|format
argument_list|(
name|iStart
argument_list|)
return|;
block|}
specifier|public
name|String
name|getEnd
parameter_list|()
block|{
return|return
name|iDateFormat
operator|.
name|format
argument_list|(
name|iEnd
argument_list|)
operator|+
literal|"T"
operator|+
name|iTimeFormat
operator|.
name|format
argument_list|(
name|iEnd
argument_list|)
operator|+
literal|"Z"
return|;
block|}
specifier|public
name|String
name|getEndTime
parameter_list|()
block|{
return|return
name|iTimeFormat
operator|.
name|format
argument_list|(
name|iEnd
argument_list|)
return|;
block|}
specifier|public
name|String
name|getEndDate
parameter_list|()
block|{
return|return
name|iDateFormat
operator|.
name|format
argument_list|(
name|iEnd
argument_list|)
return|;
block|}
specifier|public
name|int
name|getDayOfWeek
parameter_list|()
block|{
return|return
name|iDayOfWeek
return|;
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
name|ICalendarStatus
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|boolean
name|merge
parameter_list|(
name|ICalendarMeeting
name|m
parameter_list|)
block|{
if|if
condition|(
name|m
operator|.
name|getStart
argument_list|()
operator|.
name|equals
argument_list|(
name|getStart
argument_list|()
argument_list|)
operator|&&
name|m
operator|.
name|getEnd
argument_list|()
operator|.
name|equals
argument_list|(
name|getEnd
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|m
operator|.
name|getStatus
argument_list|()
operator|==
name|ICalendarStatus
operator|.
name|TENTATIVE
condition|)
name|iStatus
operator|=
name|ICalendarStatus
operator|.
name|TENTATIVE
expr_stmt|;
name|iLocation
operator|+=
literal|", "
operator|+
name|m
operator|.
name|getLocation
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|same
parameter_list|(
name|ICalendarMeeting
name|m
parameter_list|)
block|{
return|return
name|getStartTime
argument_list|()
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getStartTime
argument_list|()
argument_list|)
operator|&&
name|getEndTime
argument_list|()
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getEndTime
argument_list|()
argument_list|)
operator|&&
name|getLocation
argument_list|()
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getLocation
argument_list|()
argument_list|)
operator|&&
name|getStatus
argument_list|()
operator|.
name|equals
argument_list|(
name|m
operator|.
name|getStatus
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ICalendarMeeting
name|m
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getStart
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m
operator|.
name|getStart
argument_list|()
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
name|cmp
operator|=
name|getEnd
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m
operator|.
name|getEnd
argument_list|()
argument_list|)
expr_stmt|;
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
name|getStatus
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m
operator|.
name|getStatus
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

