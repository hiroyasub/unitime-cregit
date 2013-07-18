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
name|webutil
operator|.
name|timegrid
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|BitSet
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
name|Locale
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
name|NaturalOrderComparator
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
name|server
operator|.
name|DayCode
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
name|DatePattern
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
name|PreferenceLevel
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
name|DateUtils
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
name|Formats
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TimetableGridCell
implements|implements
name|Serializable
implements|,
name|Comparable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2L
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|String
name|iShortComment
decl_stmt|;
specifier|private
name|String
name|iShortCommentNoColors
decl_stmt|;
specifier|private
name|String
name|iInstructor
decl_stmt|;
specifier|private
name|String
name|iOnClick
decl_stmt|;
specifier|private
name|String
name|iTitle
decl_stmt|;
specifier|private
name|String
name|iBackground
decl_stmt|;
specifier|private
name|int
name|iLength
decl_stmt|;
specifier|private
name|int
name|iNrMeetings
decl_stmt|;
specifier|private
name|int
name|iMeetingNumber
decl_stmt|;
specifier|private
name|long
name|iAssignmentId
decl_stmt|;
specifier|private
name|long
name|iRoomId
decl_stmt|;
specifier|private
name|String
name|iRoomName
decl_stmt|;
specifier|private
name|BitSet
name|iWeekCode
decl_stmt|;
specifier|private
name|String
name|iDatePatternName
decl_stmt|;
specifier|private
name|int
name|iDay
decl_stmt|;
specifier|private
name|int
name|iSlot
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorEmpty
init|=
literal|"rgb(255,255,255)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorRequired
init|=
literal|"rgb(80,80,200)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorStronglyPreferred
init|=
literal|"rgb(40,180,60)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorPreferred
init|=
literal|"rgb(170,240,60)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorNeutral
init|=
literal|"rgb(240,240,240)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorDiscouraged
init|=
literal|"rgb(240,210,60)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorStronglyDiscouraged
init|=
literal|"rgb(240,120,60)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorProhibited
init|=
literal|"rgb(220,50,40)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorNotAvailable
init|=
literal|"rgb(200,200,200)"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sBgColorNotAvailableButAssigned
init|=
name|sBgColorProhibited
decl_stmt|;
specifier|private
name|TimetableGridCell
name|iParent
init|=
literal|null
decl_stmt|;
specifier|public
name|TimetableGridCell
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|slot
parameter_list|,
name|long
name|assignmentId
parameter_list|,
name|long
name|roomId
parameter_list|,
name|String
name|roomName
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|shortComment
parameter_list|,
name|String
name|shortCommentNoColors
parameter_list|,
name|String
name|onClick
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|background
parameter_list|,
name|int
name|length
parameter_list|,
name|int
name|meetingNumber
parameter_list|,
name|int
name|nrMeetings
parameter_list|,
name|String
name|datePatternName
parameter_list|,
name|BitSet
name|weekCode
parameter_list|,
name|String
name|instructor
parameter_list|)
block|{
name|iDay
operator|=
name|day
expr_stmt|;
name|iSlot
operator|=
name|slot
expr_stmt|;
name|iAssignmentId
operator|=
name|assignmentId
expr_stmt|;
name|iName
operator|=
name|name
expr_stmt|;
name|iShortComment
operator|=
name|shortComment
expr_stmt|;
name|iShortCommentNoColors
operator|=
name|shortCommentNoColors
expr_stmt|;
name|iOnClick
operator|=
name|onClick
expr_stmt|;
name|iTitle
operator|=
name|title
expr_stmt|;
name|iBackground
operator|=
name|background
expr_stmt|;
name|iLength
operator|=
name|length
expr_stmt|;
name|iMeetingNumber
operator|=
name|meetingNumber
expr_stmt|;
name|iNrMeetings
operator|=
name|nrMeetings
expr_stmt|;
name|iRoomName
operator|=
name|roomName
expr_stmt|;
name|iRoomId
operator|=
name|roomId
expr_stmt|;
name|iWeekCode
operator|=
name|weekCode
expr_stmt|;
name|iDatePatternName
operator|=
name|datePatternName
expr_stmt|;
name|iInstructor
operator|=
name|instructor
expr_stmt|;
block|}
specifier|public
name|TimetableGridCell
name|copyCell
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|mtgNumber
parameter_list|)
block|{
name|TimetableGridCell
name|cell
init|=
operator|new
name|TimetableGridCell
argument_list|(
name|day
argument_list|,
name|iSlot
argument_list|,
name|iAssignmentId
argument_list|,
name|iRoomId
argument_list|,
name|iRoomName
argument_list|,
name|iName
argument_list|,
name|iShortComment
argument_list|,
name|iShortCommentNoColors
argument_list|,
name|iOnClick
argument_list|,
name|iTitle
argument_list|,
name|iBackground
argument_list|,
name|iLength
argument_list|,
name|mtgNumber
argument_list|,
name|iNrMeetings
argument_list|,
name|iDatePatternName
argument_list|,
name|iWeekCode
argument_list|,
name|iInstructor
argument_list|)
decl_stmt|;
name|cell
operator|.
name|iParent
operator|=
name|this
expr_stmt|;
return|return
name|cell
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
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getShortComment
parameter_list|()
block|{
return|return
name|iShortComment
return|;
block|}
specifier|public
name|String
name|getShortCommentNoColors
parameter_list|()
block|{
return|return
name|iShortCommentNoColors
return|;
block|}
specifier|public
name|String
name|getOnClick
parameter_list|()
block|{
return|return
name|iOnClick
return|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|String
name|getBackground
parameter_list|()
block|{
return|return
operator|(
name|iBackground
operator|==
literal|null
condition|?
name|sBgColorEmpty
else|:
name|iBackground
operator|)
return|;
block|}
specifier|public
name|int
name|getLength
parameter_list|()
block|{
return|return
name|iLength
return|;
block|}
specifier|public
name|int
name|getNrMeetings
parameter_list|()
block|{
return|return
name|iNrMeetings
return|;
block|}
specifier|public
name|int
name|getMeetingNumber
parameter_list|()
block|{
return|return
name|iMeetingNumber
return|;
block|}
specifier|public
name|long
name|getAssignmentId
parameter_list|()
block|{
return|return
name|iAssignmentId
return|;
block|}
specifier|public
name|long
name|getRoomId
parameter_list|()
block|{
return|return
name|iRoomId
return|;
block|}
specifier|public
name|String
name|getRoomName
parameter_list|()
block|{
return|return
name|iRoomName
return|;
block|}
specifier|public
name|void
name|setRoomName
parameter_list|(
name|String
name|roomName
parameter_list|)
block|{
name|iRoomName
operator|=
name|roomName
expr_stmt|;
block|}
specifier|public
name|String
name|getInstructor
parameter_list|()
block|{
return|return
operator|(
name|iInstructor
operator|==
literal|null
condition|?
literal|""
else|:
name|iInstructor
operator|)
return|;
block|}
specifier|public
specifier|static
name|String
name|pref2color
parameter_list|(
name|String
name|pref
parameter_list|)
block|{
return|return
name|PreferenceLevel
operator|.
name|prolog2bgColor
argument_list|(
name|pref
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|pref2color
parameter_list|(
name|int
name|pref
parameter_list|)
block|{
return|return
name|PreferenceLevel
operator|.
name|prolog2bgColor
argument_list|(
name|PreferenceLevel
operator|.
name|int2prolog
argument_list|(
name|pref
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|conflicts2color
parameter_list|(
name|int
name|nrConflicts
parameter_list|)
block|{
if|if
condition|(
name|nrConflicts
operator|>
literal|15
condition|)
name|nrConflicts
operator|=
literal|15
expr_stmt|;
name|String
name|color
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|nrConflicts
operator|==
literal|0
condition|)
block|{
name|color
operator|=
literal|"rgb(240,240,240)"
expr_stmt|;
block|}
if|else if
condition|(
name|nrConflicts
operator|<
literal|5
condition|)
block|{
name|color
operator|=
literal|"rgb(240,"
operator|+
operator|(
literal|240
operator|-
operator|(
literal|30
operator|*
name|nrConflicts
operator|/
literal|5
operator|)
operator|)
operator|+
literal|","
operator|+
operator|(
literal|240
operator|-
operator|(
literal|180
operator|*
name|nrConflicts
operator|/
literal|5
operator|)
operator|)
operator|+
literal|")"
expr_stmt|;
block|}
if|else if
condition|(
name|nrConflicts
operator|<
literal|10
condition|)
block|{
name|color
operator|=
literal|"rgb(240,"
operator|+
operator|(
literal|210
operator|-
operator|(
literal|90
operator|*
operator|(
name|nrConflicts
operator|-
literal|5
operator|)
operator|/
literal|5
operator|)
operator|)
operator|+
literal|",60)"
expr_stmt|;
block|}
else|else
block|{
name|color
operator|=
literal|"rgb("
operator|+
operator|(
literal|240
operator|-
operator|(
literal|20
operator|*
operator|(
name|nrConflicts
operator|-
literal|10
operator|)
operator|/
literal|5
operator|)
operator|)
operator|+
literal|","
operator|+
operator|(
literal|120
operator|-
operator|(
literal|70
operator|*
operator|(
name|nrConflicts
operator|-
literal|10
operator|)
operator|/
literal|5
operator|)
operator|)
operator|+
literal|","
operator|+
operator|(
literal|60
operator|-
operator|(
literal|20
operator|*
operator|(
name|nrConflicts
operator|-
literal|10
operator|)
operator|/
literal|5
operator|)
operator|)
operator|+
literal|")"
expr_stmt|;
block|}
return|return
name|color
return|;
block|}
specifier|public
specifier|static
name|String
name|conflicts2colorFast
parameter_list|(
name|int
name|nrConflicts
parameter_list|)
block|{
if|if
condition|(
name|nrConflicts
operator|==
literal|0
condition|)
return|return
literal|"rgb(240,240,240)"
return|;
if|if
condition|(
name|nrConflicts
operator|==
literal|1
condition|)
return|return
literal|"rgb(240,210,60)"
return|;
if|if
condition|(
name|nrConflicts
operator|==
literal|2
condition|)
return|return
literal|"rgb(240,120,60)"
return|;
return|return
literal|"rgb(220,50,40)"
return|;
block|}
specifier|public
name|BitSet
name|getWeekCode
parameter_list|()
block|{
return|return
name|iWeekCode
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|TimetableGridCell
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|TimetableGridCell
name|c
init|=
operator|(
name|TimetableGridCell
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
operator|(
name|iWeekCode
operator|==
literal|null
operator|||
name|c
operator|.
name|iWeekCode
operator|==
literal|null
condition|?
literal|0
else|:
name|Double
operator|.
name|compare
argument_list|(
name|iWeekCode
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
argument_list|,
name|c
operator|.
name|iWeekCode
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|)
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
operator|(
name|iWeekCode
operator|==
literal|null
operator|||
name|c
operator|.
name|iWeekCode
operator|==
literal|null
condition|?
literal|0
else|:
name|Double
operator|.
name|compare
argument_list|(
name|iWeekCode
operator|.
name|length
argument_list|()
argument_list|,
name|c
operator|.
name|iWeekCode
operator|.
name|length
argument_list|()
argument_list|)
operator|)
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
operator|new
name|NaturalOrderComparator
argument_list|()
operator|.
name|compare
argument_list|(
name|iName
argument_list|,
name|c
operator|.
name|iName
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|hasDays
parameter_list|()
block|{
return|return
name|iWeekCode
operator|!=
literal|null
operator|&&
name|iDatePatternName
operator|!=
literal|null
return|;
block|}
specifier|public
name|String
name|getDays
parameter_list|()
block|{
return|return
name|iDatePatternName
return|;
block|}
specifier|public
name|void
name|setDays
parameter_list|(
name|String
name|days
parameter_list|)
block|{
name|iDatePatternName
operator|=
name|days
expr_stmt|;
block|}
specifier|public
name|int
name|getDay
parameter_list|()
block|{
return|return
name|iDay
return|;
block|}
specifier|public
name|int
name|getSlot
parameter_list|()
block|{
return|return
name|iSlot
return|;
block|}
specifier|public
specifier|static
name|String
name|formatDatePattern
parameter_list|(
name|DatePattern
name|dp
parameter_list|,
name|int
name|dayCode
parameter_list|)
block|{
if|if
condition|(
name|dp
operator|==
literal|null
operator|||
name|dp
operator|.
name|isDefault
argument_list|()
condition|)
return|return
literal|null
return|;
comment|// if (dp.getType() != DatePattern.sTypeExtended) return dp.getName();
name|BitSet
name|weekCode
init|=
name|dp
operator|.
name|getPatternBitSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|weekCode
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|dp
operator|.
name|getName
argument_list|()
return|;
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
name|setLenient
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Date
name|dpFirstDate
init|=
name|DateUtils
operator|.
name|getDate
argument_list|(
literal|1
argument_list|,
name|dp
operator|.
name|getSession
argument_list|()
operator|.
name|getPatternStartMonth
argument_list|()
argument_list|,
name|dp
operator|.
name|getSession
argument_list|()
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|dpFirstDate
argument_list|)
expr_stmt|;
name|int
name|idx
init|=
name|weekCode
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|idx
argument_list|)
expr_stmt|;
name|Date
name|first
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|idx
operator|<
name|weekCode
operator|.
name|size
argument_list|()
operator|&&
name|first
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|weekCode
operator|.
name|get
argument_list|(
name|idx
argument_list|)
condition|)
block|{
name|int
name|dow
init|=
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|dow
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
name|dayCode
operator|&
name|DayCode
operator|.
name|MON
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|first
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|TUESDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|TUE
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|first
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|WEDNESDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|WED
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|first
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|THURSDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|THU
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|first
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|FRIDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|FRI
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|first
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SATURDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|SAT
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|first
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SUNDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|SUN
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|first
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
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
name|idx
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|first
operator|==
literal|null
condition|)
return|return
name|dp
operator|.
name|getName
argument_list|()
return|;
name|cal
operator|.
name|setTime
argument_list|(
name|dpFirstDate
argument_list|)
expr_stmt|;
name|idx
operator|=
name|weekCode
operator|.
name|length
argument_list|()
operator|-
literal|1
expr_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|idx
argument_list|)
expr_stmt|;
name|Date
name|last
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|idx
operator|>=
literal|0
operator|&&
name|last
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|weekCode
operator|.
name|get
argument_list|(
name|idx
argument_list|)
condition|)
block|{
name|int
name|dow
init|=
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|dow
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
name|dayCode
operator|&
name|DayCode
operator|.
name|MON
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|last
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|TUESDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|TUE
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|last
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|WEDNESDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|WED
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|last
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|THURSDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|THU
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|last
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|FRIDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|FRI
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|last
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SATURDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|SAT
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|last
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SUNDAY
case|:
if|if
condition|(
operator|(
name|dayCode
operator|&
name|DayCode
operator|.
name|SUN
operator|.
name|getCode
argument_list|()
operator|)
operator|!=
literal|0
condition|)
name|last
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|idx
operator|--
expr_stmt|;
block|}
if|if
condition|(
name|last
operator|==
literal|null
condition|)
return|return
name|dp
operator|.
name|getName
argument_list|()
return|;
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|dpf
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EVENT_SHORT
argument_list|)
decl_stmt|;
return|return
name|dpf
operator|.
name|format
argument_list|(
name|first
argument_list|)
operator|+
operator|(
name|first
operator|.
name|equals
argument_list|(
name|last
argument_list|)
condition|?
literal|""
else|:
literal|" - "
operator|+
name|dpf
operator|.
name|format
argument_list|(
name|last
argument_list|)
operator|)
return|;
block|}
specifier|public
name|TimetableGridCell
name|getParent
parameter_list|()
block|{
return|return
name|iParent
return|;
block|}
block|}
end_class

end_unit

