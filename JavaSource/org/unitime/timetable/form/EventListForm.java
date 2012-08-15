begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|StringTokenizer
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|SponsoringOrganization
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
name|CalendarUtils
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
name|ComboBoxLookup
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
name|WebTextValidation
import|;
end_import

begin_comment
comment|/**  * @author Zuzana Mullerova  */
end_comment

begin_class
specifier|public
class|class
name|EventListForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5206194674045902244L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|String
name|iEventNameSubstring
decl_stmt|;
specifier|private
name|String
name|iEventMainContactSubstring
decl_stmt|;
specifier|private
name|String
name|iEventDateFrom
decl_stmt|;
specifier|private
name|String
name|iEventDateTo
decl_stmt|;
specifier|private
name|Integer
index|[]
name|iEventTypes
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iSponsorOrgId
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeMyEvents
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeEvents4Approval
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeAllEvents
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeAllApprovedEvents
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeAllEventsWaitingApproval
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeAllConflictingEvents
init|=
literal|5
decl_stmt|;
specifier|private
name|int
name|iMode
init|=
name|sModeMyEvents
decl_stmt|;
specifier|private
name|boolean
name|iConf
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iDayMon
decl_stmt|,
name|iDayTue
decl_stmt|,
name|iDayWed
decl_stmt|,
name|iDayThu
decl_stmt|,
name|iDayFri
decl_stmt|,
name|iDaySat
decl_stmt|,
name|iDaySun
decl_stmt|;
specifier|private
name|int
name|iStartTime
decl_stmt|;
specifier|private
name|int
name|iStopTime
decl_stmt|;
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
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|iOp
operator|!=
literal|null
operator|&&
operator|!
operator|(
literal|"Search"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Add Event"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"iCalendar"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Cancel Event"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Cancel"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|||
literal|"Export CSV"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"op"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Invalid Operation."
argument_list|)
argument_list|)
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|iEventNameSubstring
operator|!=
literal|null
operator|&&
name|iEventNameSubstring
operator|.
name|length
argument_list|()
operator|>
literal|50
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"eventName"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"The event name cannot exceed 50 characters."
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|WebTextValidation
operator|.
name|isTextValid
argument_list|(
name|iEventNameSubstring
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|iEventNameSubstring
operator|=
literal|""
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"eventName"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidCharacters"
argument_list|,
literal|"Event Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iEventMainContactSubstring
operator|!=
literal|null
operator|&&
name|iEventMainContactSubstring
operator|.
name|length
argument_list|()
operator|>
literal|50
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"mainContact"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"The event name cannot exceed 50 characters."
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|WebTextValidation
operator|.
name|isTextValid
argument_list|(
name|iEventMainContactSubstring
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|iEventMainContactSubstring
operator|=
literal|""
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"mainContact"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidCharacters"
argument_list|,
literal|"Requested by"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iStartTime
operator|>=
literal|0
operator|&&
name|iStopTime
operator|>=
literal|0
operator|&&
name|iStartTime
operator|>=
name|iStopTime
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"stopTime"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"From Time must be earlier than To Time."
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|df
init|=
literal|"MM/dd/yyyy"
decl_stmt|;
name|Date
name|start
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|iEventDateFrom
operator|==
literal|null
operator|||
name|iEventDateFrom
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|iEventDateFrom
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|WebTextValidation
operator|.
name|isTextValid
argument_list|(
name|iEventDateFrom
argument_list|,
literal|true
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|iEventDateFrom
argument_list|,
name|df
argument_list|)
condition|)
block|{
name|iEventDateFrom
operator|=
literal|""
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"eventDateFrom"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"From date invalid"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"eventDateFrom"
argument_list|,
name|iEventDateFrom
argument_list|)
expr_stmt|;
name|start
operator|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|iEventDateFrom
argument_list|,
name|df
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|iEventDateFrom
operator|=
literal|""
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"eventDateFrom"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"From date invalid"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|Date
name|end
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|iEventDateTo
operator|==
literal|null
operator|||
name|iEventDateTo
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|iEventDateTo
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|WebTextValidation
operator|.
name|isTextValid
argument_list|(
name|iEventDateTo
argument_list|,
literal|true
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|CalendarUtils
operator|.
name|isValidDate
argument_list|(
name|iEventDateTo
argument_list|,
name|df
argument_list|)
condition|)
block|{
name|iEventDateTo
operator|=
literal|""
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"eventDateTo"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"To date invalid"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"eventDateTo"
argument_list|,
name|iEventDateTo
argument_list|)
expr_stmt|;
name|end
operator|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|iEventDateTo
argument_list|,
name|df
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|iEventDateTo
operator|=
literal|""
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"eventDateTo"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.invalidDate"
argument_list|,
literal|"To date invalid"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|end
operator|!=
literal|null
operator|&&
name|start
operator|!=
literal|null
operator|&&
operator|!
name|start
operator|.
name|equals
argument_list|(
name|end
argument_list|)
operator|&&
operator|!
name|start
operator|.
name|before
argument_list|(
name|end
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"eventDateTo"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Date From cannot occur after Date To"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|errors
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
name|iEventDateFrom
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|iEventDateTo
operator|=
literal|null
expr_stmt|;
name|iEventTypes
operator|=
operator|new
name|Integer
index|[]
block|{
name|Event
operator|.
name|sEventTypeSpecial
block|}
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
name|iMode
operator|=
name|sModeAllEvents
expr_stmt|;
name|iSponsorOrgId
operator|=
literal|null
expr_stmt|;
name|iConf
operator|=
literal|false
expr_stmt|;
name|iStartTime
operator|=
operator|-
literal|1
expr_stmt|;
name|iStopTime
operator|=
operator|-
literal|1
expr_stmt|;
name|iDayMon
operator|=
literal|false
expr_stmt|;
name|iDayTue
operator|=
literal|false
expr_stmt|;
name|iDayWed
operator|=
literal|false
expr_stmt|;
name|iDayThu
operator|=
literal|false
expr_stmt|;
name|iDayFri
operator|=
literal|false
expr_stmt|;
name|iDaySat
operator|=
literal|false
expr_stmt|;
name|iDaySun
operator|=
literal|false
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
name|String
name|eventTypes
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"EventList.EventTypesInt"
argument_list|,
name|Event
operator|.
name|sEventTypeFinalExam
operator|+
literal|","
operator|+
name|Event
operator|.
name|sEventTypeMidtermExam
argument_list|)
decl_stmt|;
name|StringTokenizer
name|stk
init|=
operator|new
name|StringTokenizer
argument_list|(
name|eventTypes
argument_list|,
literal|","
argument_list|)
decl_stmt|;
name|iEventTypes
operator|=
operator|new
name|Integer
index|[
name|stk
operator|.
name|countTokens
argument_list|()
index|]
expr_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|stk
operator|.
name|hasMoreTokens
argument_list|()
condition|)
name|iEventTypes
index|[
name|idx
operator|++
index|]
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|stk
operator|.
name|nextToken
argument_list|()
argument_list|)
expr_stmt|;
name|iEventNameSubstring
operator|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.EventNameSubstring"
argument_list|)
expr_stmt|;
name|iEventMainContactSubstring
operator|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.EventMainContactSubstring"
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.EventDateFrom"
argument_list|)
operator|!=
literal|null
condition|)
name|iEventDateFrom
operator|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.EventDateFrom"
argument_list|)
expr_stmt|;
name|iEventDateTo
operator|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.EventDateTo"
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.Mode"
argument_list|)
operator|!=
literal|null
condition|)
name|iMode
operator|=
operator|(
name|Integer
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.Mode"
argument_list|)
expr_stmt|;
else|else
block|{
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventMeetingApprove
argument_list|)
condition|)
block|{
name|iMode
operator|=
name|sModeEvents4Approval
expr_stmt|;
block|}
if|else if
condition|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|HasRole
argument_list|)
condition|)
block|{
name|iMode
operator|=
name|sModeAllEvents
expr_stmt|;
block|}
else|else
block|{
name|iMode
operator|=
name|sModeMyEvents
expr_stmt|;
block|}
block|}
name|iSponsorOrgId
operator|=
operator|(
name|Long
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.SponsoringOrganizationId"
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.Conf"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iConf
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.Conf"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.StartTime"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iStartTime
operator|=
operator|(
name|Integer
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.StartTime"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.StopTime"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iStopTime
operator|=
operator|(
name|Integer
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.StopTime"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayMon"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDayMon
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayMon"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayTue"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDayTue
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayTue"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayWed"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDayWed
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayWed"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayThu"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDayThu
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayThu"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayFri"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDayFri
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DayFri"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DaySat"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDaySat
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DaySat"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DaySun"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|iDaySun
operator|=
operator|(
name|Boolean
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"EventList.DaySun"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|save
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
name|String
name|eventTypes
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|iEventTypes
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|iEventTypes
operator|.
name|length
condition|;
name|idx
operator|++
control|)
name|eventTypes
operator|+=
operator|(
name|idx
operator|>
literal|0
condition|?
literal|","
else|:
literal|""
operator|)
operator|+
name|iEventTypes
index|[
name|idx
index|]
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"EventList.EventTypesInt"
argument_list|,
name|eventTypes
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEventNameSubstring
operator|==
literal|null
condition|)
name|context
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventNameSubstring"
argument_list|)
expr_stmt|;
else|else
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.EventNameSubstring"
argument_list|,
name|iEventNameSubstring
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEventMainContactSubstring
operator|==
literal|null
condition|)
name|context
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventMainContactSubstring"
argument_list|)
expr_stmt|;
else|else
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.EventMainContactSubstring"
argument_list|,
name|iEventMainContactSubstring
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEventDateFrom
operator|==
literal|null
condition|)
name|context
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventDateFrom"
argument_list|)
expr_stmt|;
else|else
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.EventDateFrom"
argument_list|,
name|iEventDateFrom
argument_list|)
expr_stmt|;
if|if
condition|(
name|iEventDateTo
operator|==
literal|null
condition|)
name|context
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventDateTo"
argument_list|)
expr_stmt|;
else|else
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.EventDateTo"
argument_list|,
name|iEventDateTo
argument_list|)
expr_stmt|;
if|if
condition|(
name|iSponsorOrgId
operator|==
literal|null
condition|)
name|context
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.SponsoringOrganizationId"
argument_list|)
expr_stmt|;
else|else
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.SponsoringOrganizationId"
argument_list|,
name|iSponsorOrgId
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.Mode"
argument_list|,
name|iMode
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.Conf"
argument_list|,
name|iConf
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.StartTime"
argument_list|,
name|iStartTime
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.StopTime"
argument_list|,
name|iStopTime
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.DayMon"
argument_list|,
name|iDayMon
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.DayTue"
argument_list|,
name|iDayTue
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.DayWed"
argument_list|,
name|iDayWed
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.DayThu"
argument_list|,
name|iDayThu
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.DayFri"
argument_list|,
name|iDayFri
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.DaySat"
argument_list|,
name|iDaySat
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"EventList.DaySun"
argument_list|,
name|iDaySun
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getEventNameSubstring
parameter_list|()
block|{
return|return
name|iEventNameSubstring
return|;
block|}
specifier|public
name|void
name|setEventNameSubstring
parameter_list|(
name|String
name|substring
parameter_list|)
block|{
name|iEventNameSubstring
operator|=
name|substring
expr_stmt|;
block|}
specifier|public
name|String
name|getEventMainContactSubstring
parameter_list|()
block|{
return|return
name|iEventMainContactSubstring
return|;
block|}
specifier|public
name|void
name|setEventMainContactSubstring
parameter_list|(
name|String
name|substring
parameter_list|)
block|{
name|iEventMainContactSubstring
operator|=
name|substring
expr_stmt|;
block|}
specifier|public
name|String
name|getEventDateFrom
parameter_list|()
block|{
return|return
name|iEventDateFrom
return|;
block|}
specifier|public
name|void
name|setEventDateFrom
parameter_list|(
name|String
name|date
parameter_list|)
block|{
name|iEventDateFrom
operator|=
name|date
expr_stmt|;
block|}
specifier|public
name|String
name|getEventDateTo
parameter_list|()
block|{
return|return
name|iEventDateTo
return|;
block|}
specifier|public
name|void
name|setEventDateTo
parameter_list|(
name|String
name|date
parameter_list|)
block|{
name|iEventDateTo
operator|=
name|date
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
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|Integer
index|[]
name|getEventTypes
parameter_list|()
block|{
return|return
name|iEventTypes
return|;
block|}
specifier|public
name|void
name|setEventTypes
parameter_list|(
name|Integer
index|[]
name|types
parameter_list|)
block|{
name|iEventTypes
operator|=
name|types
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getAllEventTypes
parameter_list|()
block|{
return|return
name|Event
operator|.
name|sEventTypes
return|;
block|}
specifier|public
name|int
name|getMode
parameter_list|()
block|{
return|return
name|iMode
return|;
block|}
specifier|public
name|void
name|setMode
parameter_list|(
name|int
name|mode
parameter_list|)
block|{
name|iMode
operator|=
name|mode
expr_stmt|;
block|}
specifier|public
name|Long
name|getSponsoringOrganization
parameter_list|()
block|{
return|return
name|iSponsorOrgId
return|;
block|}
specifier|public
name|void
name|setSponsoringOrganization
parameter_list|(
name|Long
name|org
parameter_list|)
block|{
name|iSponsorOrgId
operator|=
name|org
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|SponsoringOrganization
argument_list|>
name|getSponsoringOrganizations
parameter_list|()
block|{
return|return
name|SponsoringOrganization
operator|.
name|findAll
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|getDispConflicts
parameter_list|()
block|{
return|return
name|iConf
return|;
block|}
specifier|public
name|void
name|setDispConflicts
parameter_list|(
name|boolean
name|conf
parameter_list|)
block|{
name|iConf
operator|=
name|conf
expr_stmt|;
block|}
specifier|public
name|int
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
name|int
name|startTime
parameter_list|)
block|{
name|iStartTime
operator|=
name|startTime
expr_stmt|;
block|}
specifier|public
name|int
name|getStopTime
parameter_list|()
block|{
return|return
name|iStopTime
return|;
block|}
specifier|public
name|void
name|setStopTime
parameter_list|(
name|int
name|stopTime
parameter_list|)
block|{
name|iStopTime
operator|=
name|stopTime
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDayMon
parameter_list|()
block|{
return|return
name|iDayMon
return|;
block|}
specifier|public
name|void
name|setDayMon
parameter_list|(
name|boolean
name|dayMon
parameter_list|)
block|{
name|iDayMon
operator|=
name|dayMon
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDayTue
parameter_list|()
block|{
return|return
name|iDayTue
return|;
block|}
specifier|public
name|void
name|setDayTue
parameter_list|(
name|boolean
name|dayTue
parameter_list|)
block|{
name|iDayTue
operator|=
name|dayTue
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDayWed
parameter_list|()
block|{
return|return
name|iDayWed
return|;
block|}
specifier|public
name|void
name|setDayWed
parameter_list|(
name|boolean
name|dayWed
parameter_list|)
block|{
name|iDayWed
operator|=
name|dayWed
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDayThu
parameter_list|()
block|{
return|return
name|iDayThu
return|;
block|}
specifier|public
name|void
name|setDayThu
parameter_list|(
name|boolean
name|dayThu
parameter_list|)
block|{
name|iDayThu
operator|=
name|dayThu
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDayFri
parameter_list|()
block|{
return|return
name|iDayFri
return|;
block|}
specifier|public
name|void
name|setDayFri
parameter_list|(
name|boolean
name|dayFri
parameter_list|)
block|{
name|iDayFri
operator|=
name|dayFri
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDaySat
parameter_list|()
block|{
return|return
name|iDaySat
return|;
block|}
specifier|public
name|void
name|setDaySat
parameter_list|(
name|boolean
name|daySat
parameter_list|)
block|{
name|iDaySat
operator|=
name|daySat
expr_stmt|;
block|}
specifier|public
name|boolean
name|isDaySun
parameter_list|()
block|{
return|return
name|iDaySun
return|;
block|}
specifier|public
name|void
name|setDaySun
parameter_list|(
name|boolean
name|daySun
parameter_list|)
block|{
name|iDaySun
operator|=
name|daySun
expr_stmt|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getTimes
parameter_list|()
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|times
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|times
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|""
argument_list|,
literal|"-1"
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|hour
decl_stmt|;
name|int
name|minute
decl_stmt|;
name|String
name|ampm
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
literal|288
condition|;
name|i
operator|=
name|i
operator|+
literal|3
control|)
block|{
name|hour
operator|=
operator|(
name|i
operator|/
literal|12
operator|)
operator|%
literal|12
expr_stmt|;
if|if
condition|(
name|hour
operator|==
literal|0
condition|)
name|hour
operator|=
literal|12
expr_stmt|;
name|minute
operator|=
name|i
operator|%
literal|12
operator|*
literal|5
expr_stmt|;
if|if
condition|(
name|i
operator|/
literal|144
operator|==
literal|0
condition|)
name|ampm
operator|=
literal|"am"
expr_stmt|;
else|else
name|ampm
operator|=
literal|"pm"
expr_stmt|;
name|times
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|hour
operator|+
literal|":"
operator|+
operator|(
name|minute
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|minute
operator|+
literal|" "
operator|+
name|ampm
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|times
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getStopTimes
parameter_list|()
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|times
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|times
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|""
argument_list|,
literal|"-1"
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|hour
decl_stmt|;
name|int
name|minute
decl_stmt|;
name|String
name|ampm
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|3
init|;
name|i
operator|<=
literal|288
condition|;
name|i
operator|=
name|i
operator|+
literal|3
control|)
block|{
name|hour
operator|=
operator|(
name|i
operator|/
literal|12
operator|)
operator|%
literal|12
expr_stmt|;
if|if
condition|(
name|hour
operator|==
literal|0
condition|)
name|hour
operator|=
literal|12
expr_stmt|;
name|minute
operator|=
name|i
operator|%
literal|12
operator|*
literal|5
expr_stmt|;
if|if
condition|(
operator|(
name|i
operator|/
literal|144
operator|)
operator|%
literal|2
operator|==
literal|0
condition|)
name|ampm
operator|=
literal|"am"
expr_stmt|;
else|else
name|ampm
operator|=
literal|"pm"
expr_stmt|;
name|times
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|hour
operator|+
literal|":"
operator|+
operator|(
name|minute
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|minute
operator|+
literal|" "
operator|+
name|ampm
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|times
return|;
block|}
block|}
end_class

end_unit

