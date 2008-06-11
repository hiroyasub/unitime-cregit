begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|UserData
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
name|errors
operator|.
name|add
argument_list|(
literal|"eventDateFrom"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Date (From)"
argument_list|)
argument_list|)
expr_stmt|;
if|else if
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
literal|"Date '"
operator|+
name|iEventDateFrom
operator|+
literal|"' (From)"
argument_list|)
argument_list|)
expr_stmt|;
else|else
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
comment|//no end
block|}
if|else if
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
literal|"Date '"
operator|+
name|iEventDateTo
operator|+
literal|"' (To)"
argument_list|)
argument_list|)
expr_stmt|;
else|else
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
if|if
condition|(
name|end
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
name|sEventTypeFinalExam
block|,
name|Event
operator|.
name|sEventTypeMidtermExam
block|}
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|String
name|eventTypes
init|=
name|UserData
operator|.
name|getProperty
argument_list|(
name|session
argument_list|,
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
name|session
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
name|session
operator|.
name|getAttribute
argument_list|(
literal|"EventList.EventMainContactSubstring"
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
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
name|session
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
name|session
operator|.
name|getAttribute
argument_list|(
literal|"EventList.EventDateTo"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|HttpSession
name|session
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
name|UserData
operator|.
name|setProperty
argument_list|(
name|session
argument_list|,
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
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventNameSubstring"
argument_list|)
expr_stmt|;
else|else
name|session
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
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventMainContactSubstring"
argument_list|)
expr_stmt|;
else|else
name|session
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
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventDateFrom"
argument_list|)
expr_stmt|;
else|else
name|session
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
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"EventList.EventDateTo"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"EventList.EventDateTo"
argument_list|,
name|iEventDateTo
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
block|}
end_class

end_unit

