begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|course
operator|.
name|ui
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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|defaults
operator|.
name|ApplicationProperty
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
name|resources
operator|.
name|GwtConstants
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
operator|.
name|TimeBlock
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
name|Assignment
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
name|model
operator|.
name|TimePattern
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
name|TimePatternDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassTimeInfo
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|ClassTimeInfo
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|342155197631035341L
decl_stmt|;
specifier|private
name|Long
name|iClassId
decl_stmt|;
specifier|private
name|int
name|iStartSlot
decl_stmt|;
specifier|private
name|int
name|iPreference
decl_stmt|;
specifier|private
name|Long
name|iTimePatternId
init|=
literal|null
decl_stmt|;
specifier|private
specifier|transient
name|TimePattern
name|iTimePattern
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iHashCode
decl_stmt|;
specifier|private
name|int
name|iDayCode
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
name|iBreakTime
decl_stmt|;
specifier|private
name|int
name|iMinsPerMtg
decl_stmt|;
specifier|private
name|ClassDateInfo
name|iDate
init|=
literal|null
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Date
argument_list|>
name|iDates
init|=
literal|null
decl_stmt|;
specifier|public
name|ClassTimeInfo
parameter_list|(
name|Long
name|classId
parameter_list|,
name|int
name|dayCode
parameter_list|,
name|int
name|startTime
parameter_list|,
name|int
name|length
parameter_list|,
name|int
name|minsPerMtg
parameter_list|,
name|int
name|pref
parameter_list|,
name|TimePattern
name|timePattern
parameter_list|,
name|ClassDateInfo
name|date
parameter_list|,
name|int
name|breakTime
parameter_list|,
name|List
argument_list|<
name|Date
argument_list|>
name|dates
parameter_list|)
block|{
name|iClassId
operator|=
name|classId
expr_stmt|;
name|iPreference
operator|=
name|pref
expr_stmt|;
name|iStartSlot
operator|=
name|startTime
expr_stmt|;
name|iDayCode
operator|=
name|dayCode
expr_stmt|;
name|iMinsPerMtg
operator|=
name|minsPerMtg
expr_stmt|;
name|iLength
operator|=
name|length
expr_stmt|;
name|iBreakTime
operator|=
name|breakTime
expr_stmt|;
name|iNrMeetings
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|iDayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|i
index|]
operator|)
operator|==
literal|0
condition|)
continue|continue;
name|iNrMeetings
operator|++
expr_stmt|;
block|}
name|iHashCode
operator|=
name|combine
argument_list|(
name|combine
argument_list|(
name|iDayCode
argument_list|,
name|iStartSlot
argument_list|)
argument_list|,
name|combine
argument_list|(
name|iLength
argument_list|,
name|date
operator|.
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iDate
operator|=
name|date
expr_stmt|;
name|iTimePatternId
operator|=
name|timePattern
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iTimePattern
operator|=
name|timePattern
expr_stmt|;
name|iDates
operator|=
name|dates
expr_stmt|;
block|}
specifier|public
name|ClassTimeInfo
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|int
name|preference
parameter_list|,
name|int
name|datePreference
parameter_list|)
block|{
name|this
argument_list|(
name|assignment
operator|.
name|getClassId
argument_list|()
argument_list|,
name|assignment
operator|.
name|getDays
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|,
name|assignment
operator|.
name|getStartSlot
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|,
name|assignment
operator|.
name|getSlotPerMtg
argument_list|()
argument_list|,
name|assignment
operator|.
name|getMinutesPerMeeting
argument_list|()
argument_list|,
name|preference
argument_list|,
name|assignment
operator|.
name|getTimePattern
argument_list|()
argument_list|,
operator|new
name|ClassDateInfo
argument_list|(
name|assignment
argument_list|,
name|datePreference
argument_list|)
argument_list|,
name|assignment
operator|.
name|getBreakTime
argument_list|()
argument_list|,
name|assignment
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getDurationModel
argument_list|()
operator|.
name|getDates
argument_list|(
name|assignment
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getMinutesPerWk
argument_list|()
argument_list|,
name|assignment
operator|.
name|getDatePattern
argument_list|()
argument_list|,
name|assignment
operator|.
name|getDays
argument_list|()
argument_list|,
name|assignment
operator|.
name|getMinutesPerMeeting
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|ClassTimeInfo
parameter_list|(
name|ClassTimeInfo
name|time
parameter_list|,
name|ClassDateInfo
name|date
parameter_list|,
name|List
argument_list|<
name|Date
argument_list|>
name|dates
parameter_list|)
block|{
name|iClassId
operator|=
name|time
operator|.
name|iClassId
expr_stmt|;
name|iPreference
operator|=
name|time
operator|.
name|getPreference
argument_list|()
expr_stmt|;
name|iStartSlot
operator|=
name|time
operator|.
name|getStartSlot
argument_list|()
expr_stmt|;
name|iDayCode
operator|=
name|time
operator|.
name|getDayCode
argument_list|()
expr_stmt|;
name|iMinsPerMtg
operator|=
name|time
operator|.
name|getMinutesPerMeeting
argument_list|()
expr_stmt|;
name|iLength
operator|=
name|time
operator|.
name|getLength
argument_list|()
expr_stmt|;
name|iBreakTime
operator|=
name|time
operator|.
name|getBreakTime
argument_list|()
expr_stmt|;
name|iNrMeetings
operator|=
name|time
operator|.
name|getNrMeetings
argument_list|()
expr_stmt|;
name|iDate
operator|=
name|date
expr_stmt|;
name|iTimePatternId
operator|=
name|time
operator|.
name|getTimePatternId
argument_list|()
expr_stmt|;
if|if
condition|(
name|time
operator|.
name|iTimePattern
operator|!=
literal|null
condition|)
name|iTimePattern
operator|=
name|time
operator|.
name|iTimePattern
expr_stmt|;
name|iHashCode
operator|=
name|combine
argument_list|(
name|combine
argument_list|(
name|iDayCode
argument_list|,
name|iStartSlot
argument_list|)
argument_list|,
name|combine
argument_list|(
name|iLength
argument_list|,
name|date
operator|.
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iDates
operator|=
name|dates
expr_stmt|;
block|}
specifier|public
name|ClassTimeInfo
parameter_list|(
name|Assignment
name|assignment
parameter_list|)
block|{
name|this
argument_list|(
name|assignment
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
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
name|getBreakTime
parameter_list|()
block|{
return|return
name|iBreakTime
return|;
block|}
specifier|public
name|int
name|getDayCode
parameter_list|()
block|{
return|return
name|iDayCode
return|;
block|}
specifier|public
name|int
name|getMinutesPerMeeting
parameter_list|()
block|{
return|return
name|iMinsPerMtg
return|;
block|}
specifier|public
name|String
name|getDayHeader
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
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
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
operator|(
name|iDayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|i
index|]
operator|)
operator|!=
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
index|[
name|i
index|]
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|getStartTimeHeader
parameter_list|()
block|{
name|int
name|min
init|=
name|iStartSlot
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
return|return
name|Constants
operator|.
name|toTime
argument_list|(
name|min
argument_list|)
return|;
block|}
specifier|public
name|String
name|getEndTimeHeader
parameter_list|()
block|{
name|int
name|min
init|=
operator|(
name|iStartSlot
operator|+
name|iLength
operator|)
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|-
name|getBreakTime
argument_list|()
decl_stmt|;
return|return
name|Constants
operator|.
name|toTime
argument_list|(
name|min
argument_list|)
return|;
block|}
specifier|public
name|String
name|getEndTimeHeaderNoAdj
parameter_list|()
block|{
name|int
name|min
init|=
operator|(
name|iStartSlot
operator|+
name|iLength
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
return|return
name|Constants
operator|.
name|toTime
argument_list|(
name|min
argument_list|)
return|;
block|}
specifier|public
name|int
name|getStartSlot
parameter_list|()
block|{
return|return
name|iStartSlot
return|;
block|}
specifier|public
name|boolean
name|shareDays
parameter_list|(
name|ClassTimeInfo
name|anotherLocation
parameter_list|)
block|{
return|return
operator|(
operator|(
name|iDayCode
operator|&
name|anotherLocation
operator|.
name|iDayCode
operator|)
operator|!=
literal|0
operator|)
return|;
block|}
specifier|public
name|boolean
name|shareHours
parameter_list|(
name|ClassTimeInfo
name|anotherLocation
parameter_list|)
block|{
return|return
operator|(
name|iStartSlot
operator|+
name|iLength
operator|>
name|anotherLocation
operator|.
name|iStartSlot
operator|)
operator|&&
operator|(
name|anotherLocation
operator|.
name|iStartSlot
operator|+
name|anotherLocation
operator|.
name|iLength
operator|>
name|iStartSlot
operator|)
return|;
block|}
specifier|public
name|boolean
name|shareWeeks
parameter_list|(
name|ClassTimeInfo
name|anotherLocation
parameter_list|)
block|{
return|return
name|getDate
argument_list|()
operator|.
name|getPattern
argument_list|()
operator|.
name|intersects
argument_list|(
name|anotherLocation
operator|.
name|getDate
argument_list|()
operator|.
name|getPattern
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|overlaps
parameter_list|(
name|ClassTimeInfo
name|anotherLocation
parameter_list|)
block|{
return|return
name|shareDays
argument_list|(
name|anotherLocation
argument_list|)
operator|&&
name|shareHours
argument_list|(
name|anotherLocation
argument_list|)
operator|&&
name|shareWeeks
argument_list|(
name|anotherLocation
argument_list|)
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|getDayHeader
argument_list|()
operator|+
literal|" "
operator|+
name|getStartTimeHeader
argument_list|()
return|;
block|}
specifier|public
name|String
name|getLongName
parameter_list|()
block|{
return|return
name|getDayHeader
argument_list|()
operator|+
literal|" "
operator|+
name|getStartTimeHeader
argument_list|()
operator|+
literal|" - "
operator|+
name|getEndTimeHeader
argument_list|()
operator|+
literal|" "
operator|+
name|getDatePatternName
argument_list|()
return|;
block|}
specifier|public
name|String
name|getLongNameNoAdj
parameter_list|()
block|{
return|return
name|getDayHeader
argument_list|()
operator|+
literal|" "
operator|+
name|getStartTimeHeader
argument_list|()
operator|+
literal|" - "
operator|+
name|getEndTimeHeaderNoAdj
argument_list|()
operator|+
literal|" "
operator|+
name|getDatePatternName
argument_list|()
return|;
block|}
specifier|public
name|String
name|getNameHtml
parameter_list|()
block|{
return|return
literal|"<span onmouseover=\"showGwtTimeHint(this, '"
operator|+
name|iClassId
operator|+
literal|","
operator|+
name|iDayCode
operator|+
literal|","
operator|+
name|iStartSlot
operator|+
literal|"');\" onmouseout=\"hideGwtTimeHint();\""
operator|+
literal|" style='color:"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|";'>"
operator|+
name|getName
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|String
name|getLongNameHtml
parameter_list|()
block|{
return|return
literal|"<span onmouseover=\"showGwtTimeHint(this, '"
operator|+
name|iClassId
operator|+
literal|","
operator|+
name|iDayCode
operator|+
literal|","
operator|+
name|iStartSlot
operator|+
literal|"');\" onmouseout=\"hideGwtTimeHint();\""
operator|+
literal|" style='color:"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|";'>"
operator|+
name|getDayHeader
argument_list|()
operator|+
literal|" "
operator|+
name|getStartTimeHeader
argument_list|()
operator|+
literal|" - "
operator|+
name|getEndTimeHeader
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|int
name|getPreference
parameter_list|()
block|{
return|return
name|iPreference
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
name|getNrSlotsPerMeeting
parameter_list|()
block|{
return|return
name|iLength
return|;
block|}
specifier|public
name|Long
name|getTimePatternId
parameter_list|()
block|{
return|return
name|iTimePatternId
return|;
block|}
specifier|public
name|TimePattern
name|getTimePattern
parameter_list|()
block|{
if|if
condition|(
name|iTimePattern
operator|==
literal|null
condition|)
name|iTimePattern
operator|=
name|TimePatternDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iTimePatternId
argument_list|)
expr_stmt|;
return|return
name|iTimePattern
return|;
block|}
specifier|public
name|TimePattern
name|getTimePattern
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
return|return
name|TimePatternDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iTimePatternId
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
specifier|public
name|ClassDateInfo
name|getDate
parameter_list|()
block|{
return|return
name|iDate
return|;
block|}
specifier|public
name|Long
name|getDatePatternId
parameter_list|()
block|{
return|return
name|getDate
argument_list|()
operator|.
name|getId
argument_list|()
return|;
block|}
specifier|public
name|DatePattern
name|getDatePattern
parameter_list|()
block|{
return|return
name|getDate
argument_list|()
operator|.
name|getDatePattern
argument_list|()
return|;
block|}
specifier|public
name|String
name|getDatePatternName
parameter_list|()
block|{
return|return
name|getDate
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|iHashCode
return|;
block|}
specifier|public
name|boolean
name|equals
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
name|ClassTimeInfo
operator|)
condition|)
return|return
literal|false
return|;
name|ClassTimeInfo
name|t
init|=
operator|(
name|ClassTimeInfo
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getStartSlot
argument_list|()
operator|!=
name|t
operator|.
name|getStartSlot
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getLength
argument_list|()
operator|!=
name|t
operator|.
name|getLength
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getDayCode
argument_list|()
operator|!=
name|t
operator|.
name|getDayCode
argument_list|()
condition|)
return|return
literal|false
return|;
return|return
name|ToolBox
operator|.
name|equals
argument_list|(
name|getTimePatternId
argument_list|()
argument_list|,
name|t
operator|.
name|getTimePatternId
argument_list|()
argument_list|)
operator|&&
name|ToolBox
operator|.
name|equals
argument_list|(
name|getDatePatternId
argument_list|()
argument_list|,
name|t
operator|.
name|getDatePatternId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ClassTimeInfo
name|time
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getDate
argument_list|()
operator|.
name|compareTo
argument_list|(
name|time
operator|.
name|getDate
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
name|getTimePattern
argument_list|()
operator|.
name|compareTo
argument_list|(
name|time
operator|.
name|getTimePattern
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
name|cmp
operator|=
name|getDayCode
argument_list|()
operator|-
name|time
operator|.
name|getDayCode
argument_list|()
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
name|cmp
operator|=
name|getStartSlot
argument_list|()
operator|-
name|time
operator|.
name|getStartSlot
argument_list|()
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
name|hashCode
argument_list|()
operator|-
name|time
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|int
name|combine
parameter_list|(
name|int
name|a
parameter_list|,
name|int
name|b
parameter_list|)
block|{
name|int
name|ret
init|=
literal|0
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
literal|15
condition|;
name|i
operator|++
control|)
name|ret
operator|=
name|ret
operator||
operator|(
operator|(
name|a
operator|&
operator|(
literal|1
operator|<<
name|i
operator|)
operator|)
operator|<<
name|i
operator|)
operator||
operator|(
operator|(
name|b
operator|&
operator|(
literal|1
operator|<<
name|i
operator|)
operator|)
operator|<<
operator|(
name|i
operator|+
literal|1
operator|)
operator|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|getDatePatternId
argument_list|()
operator|+
literal|":"
operator|+
name|getTimePatternId
argument_list|()
operator|+
literal|":"
operator|+
name|getDayCode
argument_list|()
operator|+
literal|":"
operator|+
name|getStartSlot
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|Date
argument_list|>
name|getDates
parameter_list|()
block|{
return|return
name|iDates
return|;
block|}
specifier|public
name|TimeBlock
name|overlaps
parameter_list|(
name|Collection
argument_list|<
name|TimeBlock
argument_list|>
name|times
parameter_list|)
block|{
if|if
condition|(
name|times
operator|==
literal|null
operator|||
name|times
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|int
name|breakTimeStart
init|=
name|ApplicationProperty
operator|.
name|RoomAvailabilityClassBreakTimeStart
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|int
name|breakTimeStop
init|=
name|ApplicationProperty
operator|.
name|RoomAvailabilityClassBreakTimeStop
operator|.
name|intValue
argument_list|()
decl_stmt|;
for|for
control|(
name|Date
name|date
range|:
name|getDates
argument_list|()
control|)
block|{
name|DummyTimeBlock
name|dummy
init|=
operator|new
name|DummyTimeBlock
argument_list|(
name|date
argument_list|,
name|breakTimeStart
argument_list|,
name|breakTimeStop
argument_list|)
decl_stmt|;
for|for
control|(
name|TimeBlock
name|time
range|:
name|times
control|)
if|if
condition|(
name|dummy
operator|.
name|overlaps
argument_list|(
name|time
argument_list|)
condition|)
return|return
name|time
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|List
argument_list|<
name|TimeBlock
argument_list|>
name|allOverlaps
parameter_list|(
name|Collection
argument_list|<
name|TimeBlock
argument_list|>
name|times
parameter_list|)
block|{
if|if
condition|(
name|times
operator|==
literal|null
operator|||
name|times
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
name|int
name|breakTimeStart
init|=
name|ApplicationProperty
operator|.
name|RoomAvailabilityClassBreakTimeStart
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|int
name|breakTimeStop
init|=
name|ApplicationProperty
operator|.
name|RoomAvailabilityClassBreakTimeStop
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|TimeBlock
argument_list|>
name|blocks
init|=
operator|new
name|ArrayList
argument_list|<
name|TimeBlock
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Date
name|date
range|:
name|getDates
argument_list|()
control|)
block|{
name|DummyTimeBlock
name|dummy
init|=
operator|new
name|DummyTimeBlock
argument_list|(
name|date
argument_list|,
name|breakTimeStart
argument_list|,
name|breakTimeStop
argument_list|)
decl_stmt|;
for|for
control|(
name|TimeBlock
name|time
range|:
name|times
control|)
if|if
condition|(
name|dummy
operator|.
name|overlaps
argument_list|(
name|time
argument_list|)
condition|)
name|blocks
operator|.
name|add
argument_list|(
name|time
argument_list|)
expr_stmt|;
block|}
return|return
name|blocks
return|;
block|}
specifier|public
class|class
name|DummyTimeBlock
implements|implements
name|TimeBlock
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3806087343289917036L
decl_stmt|;
specifier|private
name|Date
name|iD1
decl_stmt|,
name|iD2
decl_stmt|;
specifier|private
name|DummyTimeBlock
parameter_list|(
name|Date
name|d
parameter_list|,
name|int
name|breakTimeStart
parameter_list|,
name|int
name|breakTimeStop
parameter_list|)
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
name|d
argument_list|)
expr_stmt|;
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
operator|-
name|breakTimeStart
decl_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR
argument_list|,
name|min
operator|/
literal|60
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
name|min
operator|%
literal|60
argument_list|)
expr_stmt|;
name|iD1
operator|=
name|c
operator|.
name|getTime
argument_list|()
expr_stmt|;
name|min
operator|=
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
operator|+
name|breakTimeStop
expr_stmt|;
name|c
operator|.
name|setTime
argument_list|(
name|d
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
name|min
operator|/
literal|60
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
name|min
operator|%
literal|60
argument_list|)
expr_stmt|;
name|iD2
operator|=
name|c
operator|.
name|getTime
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Long
name|getEventId
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getEventName
parameter_list|()
block|{
return|return
literal|"Dummy event"
return|;
block|}
specifier|public
name|String
name|getEventType
parameter_list|()
block|{
return|return
name|RoomAvailabilityInterface
operator|.
name|sClassType
return|;
block|}
specifier|public
name|Date
name|getStartTime
parameter_list|()
block|{
return|return
name|iD1
return|;
block|}
specifier|public
name|Date
name|getEndTime
parameter_list|()
block|{
return|return
name|iD2
return|;
block|}
specifier|public
name|boolean
name|overlaps
parameter_list|(
name|TimeBlock
name|block
parameter_list|)
block|{
return|return
name|block
operator|.
name|getStartTime
argument_list|()
operator|.
name|compareTo
argument_list|(
name|iD2
argument_list|)
operator|<
literal|0
operator|&&
name|iD1
operator|.
name|compareTo
argument_list|(
name|block
operator|.
name|getEndTime
argument_list|()
argument_list|)
operator|<
literal|0
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|iD1
operator|+
literal|" - "
operator|+
name|iD2
return|;
block|}
block|}
block|}
end_class

end_unit

