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
name|model
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
name|model
operator|.
name|base
operator|.
name|BaseExamPeriod
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
name|ExamPeriodDAO
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
name|ExamPeriod
extends|extends
name|BaseExamPeriod
implements|implements
name|Comparable
argument_list|<
name|ExamPeriod
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|ExamPeriod
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|ExamPeriod
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|ExamPeriod
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|dateOffset
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|startSlot
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|length
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceLevel
name|prefLevel
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|examType
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|session
argument_list|,
name|dateOffset
argument_list|,
name|startSlot
argument_list|,
name|length
argument_list|,
name|prefLevel
argument_list|,
name|examType
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|String
name|PERIOD_ATTR_NAME
init|=
literal|"periodList"
decl_stmt|;
specifier|public
name|Date
name|getStartDate
parameter_list|()
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|getSession
argument_list|()
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|public
name|void
name|setStartDate
parameter_list|(
name|Date
name|startDate
parameter_list|)
block|{
name|long
name|diff
init|=
name|startDate
operator|.
name|getTime
argument_list|()
operator|-
name|getSession
argument_list|()
operator|.
name|getExamBeginDate
argument_list|()
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|setDateOffset
argument_list|(
operator|(
name|int
operator|)
name|Math
operator|.
name|round
argument_list|(
name|diff
operator|/
operator|(
literal|1000.0
operator|*
literal|60
operator|*
literal|60
operator|*
literal|24
operator|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getStartHour
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|getStartSlot
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
literal|60
return|;
block|}
specifier|public
name|int
name|getStartMinute
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|getStartSlot
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|%
literal|60
return|;
block|}
specifier|public
name|Date
name|getStartTime
parameter_list|()
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|getSession
argument_list|()
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
name|getStartHour
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
name|getStartMinute
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|public
name|int
name|getEndSlot
parameter_list|()
block|{
return|return
name|getStartSlot
argument_list|()
operator|+
name|getLength
argument_list|()
return|;
block|}
specifier|public
name|int
name|getEndHour
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|getEndSlot
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
literal|60
return|;
block|}
specifier|public
name|int
name|getEndMinute
parameter_list|()
block|{
return|return
operator|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|getEndSlot
argument_list|()
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|%
literal|60
return|;
block|}
specifier|public
name|Date
name|getEndTime
parameter_list|()
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|getSession
argument_list|()
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
name|getEndHour
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
name|getEndMinute
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|public
name|String
name|getStartDateLabel
parameter_list|()
block|{
return|return
operator|new
name|SimpleDateFormat
argument_list|(
literal|"EEE MM/dd"
argument_list|)
operator|.
name|format
argument_list|(
name|getStartDate
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getStartTimeLabel
parameter_list|()
block|{
name|int
name|min
init|=
name|getStartSlot
argument_list|()
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|startHour
init|=
name|min
operator|/
literal|60
decl_stmt|;
name|int
name|startMin
init|=
name|min
operator|%
literal|60
decl_stmt|;
return|return
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
return|;
block|}
specifier|public
name|String
name|getEndTimeLabel
parameter_list|()
block|{
name|int
name|min
init|=
operator|(
name|getStartSlot
argument_list|()
operator|+
name|getLength
argument_list|()
operator|)
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
name|int
name|endHour
init|=
name|min
operator|/
literal|60
decl_stmt|;
name|int
name|endMin
init|=
name|min
operator|%
literal|60
decl_stmt|;
return|return
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
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getStartDateLabel
argument_list|()
operator|+
literal|" "
operator|+
name|getStartTimeLabel
argument_list|()
operator|+
literal|" - "
operator|+
name|getEndTimeLabel
argument_list|()
return|;
block|}
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|getStartDateLabel
argument_list|()
operator|+
literal|" "
operator|+
name|getStartTimeLabel
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExamPeriod
name|period
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getExamType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|period
operator|.
name|getExamType
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
name|getDateOffset
argument_list|()
operator|.
name|compareTo
argument_list|(
name|period
operator|.
name|getDateOffset
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
name|getStartSlot
argument_list|()
operator|.
name|compareTo
argument_list|(
name|period
operator|.
name|getStartSlot
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
name|findAll
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|Integer
name|type
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|findAll
argument_list|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|type
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|TreeSet
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Integer
name|type
parameter_list|)
block|{
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
name|ret
operator|.
name|addAll
argument_list|(
operator|new
name|ExamPeriodDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select ep from ExamPeriod ep where ep.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|ret
operator|.
name|addAll
argument_list|(
operator|new
name|ExamPeriodDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select ep from ExamPeriod ep where ep.session.uniqueId=:sessionId and ep.examType=:type"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|type
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|ExamPeriod
name|findByDateStart
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|int
name|dateOffset
parameter_list|,
name|int
name|startSlot
parameter_list|)
block|{
return|return
operator|(
name|ExamPeriod
operator|)
operator|new
name|ExamPeriodDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select ep from ExamPeriod ep where "
operator|+
literal|"ep.session.uniqueId=:sessionId and ep.dateOffset=:dateOffset and ep.startSlot=:startSlot"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"dateOffset"
argument_list|,
name|dateOffset
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|startSlot
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|ExamPeriod
name|findByIndex
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Integer
name|type
parameter_list|,
name|Integer
name|idx
parameter_list|)
block|{
if|if
condition|(
name|idx
operator|==
literal|null
operator|||
name|idx
operator|<
literal|0
condition|)
return|return
literal|null
return|;
name|int
name|x
init|=
literal|0
decl_stmt|;
name|TreeSet
name|periods
init|=
name|findAll
argument_list|(
name|sessionId
argument_list|,
name|type
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|periods
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
name|x
operator|++
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|x
operator|==
name|idx
condition|)
return|return
name|period
return|;
block|}
return|return
operator|(
name|periods
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|ExamPeriod
operator|)
name|periods
operator|.
name|last
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getAbbreviation
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isBackToBack
parameter_list|(
name|ExamPeriod
name|period
parameter_list|,
name|boolean
name|isDayBreakBackToBack
parameter_list|)
block|{
if|if
condition|(
operator|!
name|isDayBreakBackToBack
operator|&&
operator|!
name|period
operator|.
name|getDateOffset
argument_list|()
operator|.
name|equals
argument_list|(
name|getDateOffset
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|findAll
argument_list|(
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|getExamType
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
name|ExamPeriod
name|p
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|compareTo
argument_list|(
name|p
argument_list|)
operator|<
literal|0
operator|&&
name|p
operator|.
name|compareTo
argument_list|(
name|period
argument_list|)
operator|<
literal|0
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|compareTo
argument_list|(
name|p
argument_list|)
operator|>
literal|0
operator|&&
name|p
operator|.
name|compareTo
argument_list|(
name|period
argument_list|)
operator|>
literal|0
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|overlap
parameter_list|(
name|Assignment
name|assignment
parameter_list|)
block|{
return|return
name|overlap
argument_list|(
name|assignment
argument_list|,
name|Constants
operator|.
name|EXAM_TRAVEL_TIME_SLOTS
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|overlap
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|int
name|nrTravelSlots
parameter_list|)
block|{
comment|//check date pattern
name|DatePattern
name|dp
init|=
name|assignment
operator|.
name|getDatePattern
argument_list|()
decl_stmt|;
name|int
name|dpIndex
init|=
name|getDateOffset
argument_list|()
operator|-
name|getSession
argument_list|()
operator|.
name|getExamBeginOffset
argument_list|()
operator|-
operator|(
name|dp
operator|.
name|getOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|dp
operator|.
name|getOffset
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|dp
operator|.
name|getPattern
argument_list|()
operator|==
literal|null
operator|||
name|dpIndex
operator|<
literal|0
operator|||
name|dpIndex
operator|>=
name|dp
operator|.
name|getPattern
argument_list|()
operator|.
name|length
argument_list|()
operator|||
name|dp
operator|.
name|getPattern
argument_list|()
operator|.
name|charAt
argument_list|(
name|dpIndex
argument_list|)
operator|!=
literal|'1'
condition|)
return|return
literal|false
return|;
comment|//check day of week
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|getSession
argument_list|()
operator|.
name|getExamBeginDate
argument_list|()
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
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|)
condition|)
block|{
case|case
name|Calendar
operator|.
name|MONDAY
case|:
if|if
condition|(
operator|(
name|assignment
operator|.
name|getDays
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_MON
index|]
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
break|break;
case|case
name|Calendar
operator|.
name|TUESDAY
case|:
if|if
condition|(
operator|(
name|assignment
operator|.
name|getDays
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
break|break;
case|case
name|Calendar
operator|.
name|WEDNESDAY
case|:
if|if
condition|(
operator|(
name|assignment
operator|.
name|getDays
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_WED
index|]
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
break|break;
case|case
name|Calendar
operator|.
name|THURSDAY
case|:
if|if
condition|(
operator|(
name|assignment
operator|.
name|getDays
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_THU
index|]
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
break|break;
case|case
name|Calendar
operator|.
name|FRIDAY
case|:
if|if
condition|(
operator|(
name|assignment
operator|.
name|getDays
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
break|break;
case|case
name|Calendar
operator|.
name|SATURDAY
case|:
if|if
condition|(
operator|(
name|assignment
operator|.
name|getDays
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SAT
index|]
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
break|break;
case|case
name|Calendar
operator|.
name|SUNDAY
case|:
if|if
condition|(
operator|(
name|assignment
operator|.
name|getDays
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SUN
index|]
operator|)
operator|==
literal|0
condition|)
return|return
literal|false
return|;
break|break;
block|}
comment|//check time
return|return
name|getStartSlot
argument_list|()
operator|-
name|nrTravelSlots
operator|<
name|assignment
operator|.
name|getStartSlot
argument_list|()
operator|+
name|assignment
operator|.
name|getSlotPerMtg
argument_list|()
operator|&&
name|assignment
operator|.
name|getStartSlot
argument_list|()
operator|<
name|getStartSlot
argument_list|()
operator|+
name|getLength
argument_list|()
operator|+
name|nrTravelSlots
return|;
block|}
specifier|public
name|boolean
name|overlap
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
return|return
name|overlap
argument_list|(
name|meeting
argument_list|,
name|Constants
operator|.
name|EXAM_TRAVEL_TIME_SLOTS
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|overlap
parameter_list|(
name|Meeting
name|meeting
parameter_list|,
name|int
name|nrTravelSlots
parameter_list|)
block|{
if|if
condition|(
operator|!
name|meeting
operator|.
name|getMeetingDate
argument_list|()
operator|.
name|equals
argument_list|(
name|getStartDate
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|getStartSlot
argument_list|()
operator|-
name|nrTravelSlots
operator|<
name|meeting
operator|.
name|getStopPeriod
argument_list|()
operator|&&
name|meeting
operator|.
name|getStartPeriod
argument_list|()
operator|<
name|getStartSlot
argument_list|()
operator|+
name|getLength
argument_list|()
operator|+
name|nrTravelSlots
return|;
block|}
specifier|public
name|List
name|findOverlappingClassMeetings
parameter_list|()
block|{
return|return
name|findOverlappingClassMeetings
argument_list|(
name|Constants
operator|.
name|EXAM_TRAVEL_TIME_SLOTS
argument_list|)
return|;
block|}
specifier|public
name|List
name|findOverlappingClassMeetings
parameter_list|(
name|int
name|nrTravelSlots
parameter_list|)
block|{
return|return
operator|new
name|ExamPeriodDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select m from Meeting m where "
operator|+
literal|"m.eventType.reference=:eventType and "
operator|+
literal|"m.meetingDate=:startDate and m.startPeriod< :endSlot and m.stopPeriod> :startSlot"
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"startDate"
argument_list|,
name|getStartDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|getStartSlot
argument_list|()
operator|-
name|nrTravelSlots
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|getEndSlot
argument_list|()
operator|+
name|nrTravelSlots
argument_list|)
operator|.
name|setString
argument_list|(
literal|"eventType"
argument_list|,
name|EventType
operator|.
name|sEventTypeClass
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|List
name|findOverlappingClassMeetings
parameter_list|(
name|Long
name|classId
parameter_list|)
block|{
return|return
name|findOverlappingClassMeetings
argument_list|(
name|classId
argument_list|,
name|Constants
operator|.
name|EXAM_TRAVEL_TIME_SLOTS
argument_list|)
return|;
block|}
specifier|public
name|List
name|findOverlappingClassMeetings
parameter_list|(
name|Long
name|classId
parameter_list|,
name|int
name|nrTravelSlots
parameter_list|)
block|{
return|return
operator|new
name|ExamPeriodDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select m from Meeting m inner join m.event.relatedCourses r where "
operator|+
literal|"m.eventType.reference=:eventType and "
operator|+
literal|"m.meetingDate=:startDate and m.startPeriod< :endSlot and m.stopPeriod> :startSlot and "
operator|+
literal|"r.ownerType=:classType and r.ownerId=:classId"
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"startDate"
argument_list|,
name|getStartDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|getStartSlot
argument_list|()
operator|-
name|nrTravelSlots
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|getEndSlot
argument_list|()
operator|+
name|nrTravelSlots
argument_list|)
operator|.
name|setString
argument_list|(
literal|"eventType"
argument_list|,
name|EventType
operator|.
name|sEventTypeClass
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"classType"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"classId"
argument_list|,
name|classId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|int
name|getIndex
parameter_list|()
block|{
name|int
name|index
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|findAll
argument_list|(
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|getExamType
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
if|if
condition|(
name|compareTo
argument_list|(
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
operator|>
literal|0
condition|)
name|index
operator|++
expr_stmt|;
block|}
return|return
name|index
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|ExamPeriod
name|newExamPeriod
init|=
operator|new
name|ExamPeriod
argument_list|()
decl_stmt|;
name|newExamPeriod
operator|.
name|setExamType
argument_list|(
name|getExamType
argument_list|()
argument_list|)
expr_stmt|;
name|newExamPeriod
operator|.
name|setDateOffset
argument_list|(
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
name|newExamPeriod
operator|.
name|setLength
argument_list|(
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
name|newExamPeriod
operator|.
name|setPrefLevel
argument_list|(
name|getPrefLevel
argument_list|()
argument_list|)
expr_stmt|;
name|newExamPeriod
operator|.
name|setStartSlot
argument_list|(
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|newExamPeriod
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|newExamPeriod
operator|)
return|;
block|}
specifier|public
name|ExamPeriod
name|findSameExamPeriodInSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
return|return
operator|(
operator|(
name|ExamPeriod
operator|)
operator|(
operator|new
name|ExamPeriodDAO
argument_list|()
operator|)
operator|.
name|getQuery
argument_list|(
literal|"select distinct ep from ExamPeriod ep where ep.session.uniqueId = :sessionId"
operator|+
literal|" and ep.examType = :examType"
operator|+
literal|" and ep.dateOffset = :dateOffset"
operator|+
literal|" and ep.length = :length"
operator|+
literal|" and ep.prefLevel.uniqueId = :prefLevelId"
operator|+
literal|" and ep.startSlot = :startSlot"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"examType"
argument_list|,
name|getExamType
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"dateOffset"
argument_list|,
name|getDateOffset
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"length"
argument_list|,
name|getLength
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"prefLevelId"
argument_list|,
name|getPrefLevel
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|getStartSlot
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|getDayOfWeek
parameter_list|()
block|{
name|Calendar
name|c
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|getSession
argument_list|()
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|weakOverlap
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
return|return
name|getDayOfWeek
argument_list|()
operator|==
name|meeting
operator|.
name|getDayOfWeek
argument_list|()
operator|&&
name|getStartSlot
argument_list|()
operator|<
name|meeting
operator|.
name|getStopPeriod
argument_list|()
operator|&&
name|meeting
operator|.
name|getStartPeriod
argument_list|()
operator|<
name|getStartSlot
argument_list|()
operator|+
name|getLength
argument_list|()
return|;
block|}
block|}
end_class

end_unit

