begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|weights
package|;
end_package

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
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Lecture
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
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
name|DataProperties
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
name|Session
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
name|SessionDAO
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

begin_class
specifier|public
class|class
name|AverageHoursAWeekClassWeights
implements|implements
name|ClassWeightProvider
block|{
specifier|private
name|BitSet
index|[]
name|iDaysOfWeek
init|=
literal|null
decl_stmt|;
specifier|private
name|double
name|iCoeficient
init|=
literal|1.0
decl_stmt|;
specifier|public
name|AverageHoursAWeekClassWeights
parameter_list|(
name|DataProperties
name|config
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|config
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SessionId"
argument_list|,
operator|-
literal|1l
argument_list|)
argument_list|)
decl_stmt|;
name|iDaysOfWeek
operator|=
operator|new
name|BitSet
index|[
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
index|]
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
name|iDaysOfWeek
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|iDaysOfWeek
index|[
name|i
index|]
operator|=
operator|new
name|BitSet
argument_list|()
expr_stmt|;
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
name|cal
operator|.
name|setTime
argument_list|(
name|DateUtils
operator|.
name|getDate
argument_list|(
literal|1
argument_list|,
name|session
operator|.
name|getPatternStartMonth
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Date
name|last
init|=
name|DateUtils
operator|.
name|getDate
argument_list|(
literal|1
argument_list|,
name|session
operator|.
name|getPatternEndMonth
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|cal
operator|.
name|getTime
argument_list|()
operator|.
name|before
argument_list|(
name|last
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
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_MON
index|]
operator|.
name|set
argument_list|(
name|idx
argument_list|)
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|TUESDAY
case|:
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
operator|.
name|set
argument_list|(
name|idx
argument_list|)
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|WEDNESDAY
case|:
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_WED
index|]
operator|.
name|set
argument_list|(
name|idx
argument_list|)
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|THURSDAY
case|:
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_THU
index|]
operator|.
name|set
argument_list|(
name|idx
argument_list|)
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|FRIDAY
case|:
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
operator|.
name|set
argument_list|(
name|idx
argument_list|)
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SATURDAY
case|:
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_SAT
index|]
operator|.
name|set
argument_list|(
name|idx
argument_list|)
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SUNDAY
case|:
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_SUN
index|]
operator|.
name|set
argument_list|(
name|idx
argument_list|)
expr_stmt|;
break|break;
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
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|BitSet
name|ddp
init|=
name|session
operator|.
name|getDefaultDatePattern
argument_list|()
operator|.
name|getPatternBitSet
argument_list|()
decl_stmt|;
name|iCoeficient
operator|=
literal|5.0
operator|/
operator|(
name|intersection
argument_list|(
name|ddp
argument_list|,
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_MON
index|]
argument_list|)
operator|+
name|intersection
argument_list|(
name|ddp
argument_list|,
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
argument_list|)
operator|+
name|intersection
argument_list|(
name|ddp
argument_list|,
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_WED
index|]
argument_list|)
operator|+
name|intersection
argument_list|(
name|ddp
argument_list|,
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_THU
index|]
argument_list|)
operator|+
name|intersection
argument_list|(
name|ddp
argument_list|,
name|iDaysOfWeek
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
argument_list|)
operator|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|intersection
parameter_list|(
name|BitSet
name|a
parameter_list|,
name|BitSet
name|b
parameter_list|)
block|{
name|BitSet
name|c
init|=
operator|(
name|BitSet
operator|)
name|a
operator|.
name|clone
argument_list|()
decl_stmt|;
name|c
operator|.
name|and
argument_list|(
name|b
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|cardinality
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|double
name|getWeight
parameter_list|(
name|Lecture
name|lecture
parameter_list|)
block|{
name|double
name|nrMeetingSlots
init|=
literal|0
decl_stmt|;
name|int
name|nrTimes
init|=
literal|0
decl_stmt|;
for|for
control|(
name|TimeLocation
name|time
range|:
name|lecture
operator|.
name|timeLocations
argument_list|()
control|)
block|{
for|for
control|(
name|int
name|d
init|=
literal|0
init|;
name|d
operator|<
name|Constants
operator|.
name|DAY_CODES
operator|.
name|length
condition|;
name|d
operator|++
control|)
if|if
condition|(
operator|(
name|time
operator|.
name|getDayCode
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|d
index|]
operator|)
operator|!=
literal|0
condition|)
block|{
name|nrMeetingSlots
operator|+=
name|intersection
argument_list|(
name|time
operator|.
name|getWeekCode
argument_list|()
argument_list|,
name|iDaysOfWeek
index|[
name|d
index|]
argument_list|)
operator|*
name|time
operator|.
name|getNrSlotsPerMeeting
argument_list|()
expr_stmt|;
block|}
name|nrTimes
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|nrTimes
operator|==
literal|0
condition|)
return|return
literal|1.0
return|;
return|return
name|Math
operator|.
name|round
argument_list|(
literal|100.0
operator|*
name|iCoeficient
operator|*
name|nrMeetingSlots
operator|/
operator|(
literal|12.0
operator|*
name|nrTimes
operator|)
argument_list|)
operator|/
literal|100.0
return|;
block|}
block|}
end_class

end_unit

