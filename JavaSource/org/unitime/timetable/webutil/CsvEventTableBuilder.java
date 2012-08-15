begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|CSVFile
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|CSVFile
operator|.
name|CSVField
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
name|dao
operator|.
name|ClassEventDAO
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
name|ExamEventDAO
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
name|SessionContext
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

begin_class
specifier|public
class|class
name|CsvEventTableBuilder
extends|extends
name|WebEventTableBuilder
block|{
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyy/MM/dd"
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|tf
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"hh:mmaa"
argument_list|)
decl_stmt|;
specifier|public
name|CsvEventTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getMaxResults
parameter_list|()
block|{
return|return
literal|1500
return|;
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
name|CSVFile
name|csv
parameter_list|,
name|Meeting
name|meeting
parameter_list|)
block|{
name|String
name|cap
init|=
literal|""
decl_stmt|;
name|int
name|minCap
init|=
operator|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMinCapacity
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMinCapacity
argument_list|()
operator|)
decl_stmt|;
name|int
name|maxCap
init|=
operator|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMaxCapacity
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMaxCapacity
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|minCap
operator|>=
literal|0
operator|&&
name|maxCap
operator|>=
literal|0
condition|)
block|{
if|if
condition|(
name|minCap
operator|==
name|maxCap
condition|)
name|cap
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|minCap
argument_list|)
expr_stmt|;
else|else
name|cap
operator|=
name|minCap
operator|+
literal|"-"
operator|+
name|maxCap
expr_stmt|;
block|}
name|int
name|enrl
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|Event
operator|.
name|sEventTypeClass
operator|==
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
condition|)
block|{
name|ClassEvent
name|ce
init|=
operator|new
name|ClassEventDAO
argument_list|()
operator|.
name|get
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ce
operator|.
name|getClazz
argument_list|()
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|enrl
operator|=
name|ce
operator|.
name|getClazz
argument_list|()
operator|.
name|getEnrollment
argument_list|()
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|Event
operator|.
name|sEventTypeFinalExam
operator|==
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
operator|||
name|Event
operator|.
name|sEventTypeMidtermExam
operator|==
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
condition|)
block|{
name|ExamEvent
name|ee
init|=
operator|new
name|ExamEventDAO
argument_list|()
operator|.
name|get
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|enrl
operator|=
name|ee
operator|.
name|getExam
argument_list|()
operator|.
name|countStudents
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|Event
operator|.
name|sEventTypeCourse
operator|==
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventType
argument_list|()
condition|)
block|{
name|CourseEvent
name|ce
init|=
operator|new
name|CourseEventDAO
argument_list|()
operator|.
name|get
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|RelatedCourseInfo
name|rci
range|:
name|ce
operator|.
name|getRelatedCourses
argument_list|()
control|)
name|enrl
operator|+=
name|rci
operator|.
name|countStudents
argument_list|()
expr_stmt|;
block|}
name|csv
operator|.
name|addLine
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventName
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|startTime
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|stopTime
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|getRoomLabel
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getEventTypeAbbv
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|getApprovedDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|df
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getApprovedDate
argument_list|()
argument_list|)
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMainContact
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMainContact
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMainContact
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getMainContact
argument_list|()
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|enrl
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|cap
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getSponsoringOrganization
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|meeting
operator|.
name|getEvent
argument_list|()
operator|.
name|getSponsoringOrganization
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
block|}
argument_list|)
expr_stmt|;
block|}
specifier|public
name|File
name|csvTableForEvents
parameter_list|(
name|SessionContext
name|context
parameter_list|,
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
argument_list|,
name|context
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
try|try
block|{
name|CSVFile
name|csv
init|=
operator|new
name|CSVFile
argument_list|()
decl_stmt|;
name|csv
operator|.
name|setHeader
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
literal|"Name"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Date"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Start"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"End"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Location"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Type"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Approved"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Main Contact"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Email"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Enrollment"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Attend/Limit"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Sponsoring Org"
argument_list|)
block|}
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
name|context
operator|.
name|hasPermissionAnyAuthority
argument_list|(
name|m
argument_list|,
name|Right
operator|.
name|EventMeetingApprove
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
name|csv
argument_list|,
name|meeting
argument_list|)
expr_stmt|;
block|}
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"events"
argument_list|,
literal|"csv"
argument_list|)
decl_stmt|;
name|csv
operator|.
name|save
argument_list|(
name|file
argument_list|)
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
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|File
name|csvTableForMeetings
parameter_list|(
name|SessionContext
name|context
parameter_list|,
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
argument_list|,
name|context
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
try|try
block|{
name|CSVFile
name|csv
init|=
operator|new
name|CSVFile
argument_list|()
decl_stmt|;
name|csv
operator|.
name|setHeader
argument_list|(
operator|new
name|CSVField
index|[]
block|{
operator|new
name|CSVField
argument_list|(
literal|"Name"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Date"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Start"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"End"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Location"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Type"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Approved"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Main Contact"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Email"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Enrollment"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Attend/Limit"
argument_list|)
block|,
operator|new
name|CSVField
argument_list|(
literal|"Sponsoring Org"
argument_list|)
block|}
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
name|csv
argument_list|,
name|meeting
argument_list|)
expr_stmt|;
block|}
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"events"
argument_list|,
literal|"csv"
argument_list|)
decl_stmt|;
name|csv
operator|.
name|save
argument_list|(
name|file
argument_list|)
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
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

