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
name|Hashtable
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
specifier|public
class|class
name|EventsExportEventsToICal
extends|extends
name|EventsExportEventsToCSV
block|{
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
specifier|private
name|void
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
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyyMMdd"
argument_list|)
decl_stmt|;
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
name|SimpleDateFormat
name|tf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"HHmmss"
argument_list|)
decl_stmt|;
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
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|date2loc
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|approved
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
argument_list|()
decl_stmt|;
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
name|Date
name|startTime
init|=
operator|new
name|Date
argument_list|(
name|m
operator|.
name|getStartTime
argument_list|()
argument_list|)
decl_stmt|;
name|Date
name|stopTime
init|=
operator|new
name|Date
argument_list|(
name|m
operator|.
name|getStopTime
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|date
init|=
name|df
operator|.
name|format
argument_list|(
name|startTime
argument_list|)
operator|+
literal|"T"
operator|+
name|tf
operator|.
name|format
argument_list|(
name|startTime
argument_list|)
operator|+
literal|"Z/"
operator|+
name|df
operator|.
name|format
argument_list|(
name|stopTime
argument_list|)
operator|+
literal|"T"
operator|+
name|tf
operator|.
name|format
argument_list|(
name|stopTime
argument_list|)
operator|+
literal|"Z"
decl_stmt|;
name|String
name|loc
init|=
name|m
operator|.
name|getLocationName
argument_list|()
decl_stmt|;
name|String
name|l
init|=
name|date2loc
operator|.
name|get
argument_list|(
name|date
argument_list|)
decl_stmt|;
name|date2loc
operator|.
name|put
argument_list|(
name|date
argument_list|,
operator|(
name|l
operator|==
literal|null
operator|||
name|l
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|l
operator|+
literal|", "
operator|)
operator|+
name|loc
argument_list|)
expr_stmt|;
name|Boolean
name|a
init|=
name|approved
operator|.
name|get
argument_list|(
name|date
argument_list|)
decl_stmt|;
name|approved
operator|.
name|put
argument_list|(
name|date
argument_list|,
operator|(
name|a
operator|==
literal|null
operator|||
name|a
operator|)
operator|&&
name|m
operator|.
name|isApproved
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|firstDate
init|=
literal|null
decl_stmt|;
for|for
control|(
name|String
name|date
range|:
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|date2loc
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
name|String
name|loc
init|=
name|date2loc
operator|.
name|get
argument_list|(
name|date
argument_list|)
decl_stmt|;
name|String
name|start
init|=
name|date
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|date
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|end
init|=
name|date
operator|.
name|substring
argument_list|(
name|date
operator|.
name|indexOf
argument_list|(
literal|'/'
argument_list|)
operator|+
literal|1
argument_list|)
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
literal|"SEQUENCE:0"
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
name|start
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"DTEND:"
operator|+
name|end
argument_list|)
expr_stmt|;
if|if
condition|(
name|firstDate
operator|==
literal|null
condition|)
block|{
name|firstDate
operator|=
name|date
expr_stmt|;
name|String
name|rdate
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|d
range|:
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|date2loc
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|d
operator|.
name|equals
argument_list|(
name|date
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|rdate
operator|.
name|isEmpty
argument_list|()
condition|)
name|rdate
operator|+=
literal|","
expr_stmt|;
name|rdate
operator|+=
name|d
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|rdate
operator|.
name|isEmpty
argument_list|()
condition|)
name|out
operator|.
name|println
argument_list|(
literal|"RDATE;VALUE=PERIOD:"
operator|+
name|rdate
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|println
argument_list|(
literal|"RECURRENCE-ID:"
operator|+
name|start
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|println
argument_list|(
literal|"LOCATION:"
operator|+
name|loc
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"STATUS:"
operator|+
operator|(
name|approved
operator|.
name|get
argument_list|(
name|date
argument_list|)
condition|?
literal|"CONFIRMED"
else|:
literal|"TENTATIVE"
operator|)
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
argument_list|()
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
name|out
operator|.
name|println
argument_list|(
literal|"END:VEVENT"
argument_list|)
expr_stmt|;
block|}
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
block|}
end_class

end_unit

