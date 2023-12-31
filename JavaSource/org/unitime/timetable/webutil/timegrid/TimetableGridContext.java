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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TimetableGridContext
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|String
name|iFilter
decl_stmt|;
specifier|private
name|int
name|iStartDayDayOfWeek
decl_stmt|;
specifier|private
name|int
name|iResourceType
decl_stmt|;
specifier|private
name|int
name|iFirstDay
decl_stmt|;
specifier|private
name|int
name|iBgMode
decl_stmt|;
specifier|private
name|boolean
name|iShowEvents
decl_stmt|;
specifier|private
name|int
name|iFirstSlot
decl_stmt|,
name|iLastSlot
decl_stmt|,
name|iDayCode
decl_stmt|;
specifier|private
name|BitSet
name|iPattern
decl_stmt|;
specifier|private
name|float
name|iNrWeeks
decl_stmt|;
specifier|private
name|int
name|iSlotsPerWeek
decl_stmt|;
specifier|public
name|TimetableGridContext
parameter_list|()
block|{
block|}
specifier|public
name|TimetableGridContext
parameter_list|(
name|TimetableGridTable
name|table
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
name|DatePattern
name|dp
init|=
name|session
operator|.
name|getDefaultDatePatternNotNull
argument_list|()
decl_stmt|;
name|iFilter
operator|=
name|table
operator|.
name|getFindString
argument_list|()
expr_stmt|;
name|iStartDayDayOfWeek
operator|=
name|Constants
operator|.
name|getDayOfWeek
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
name|iFirstDay
operator|=
operator|(
name|table
operator|.
name|getWeek
argument_list|()
operator|==
operator|-
literal|100
condition|?
operator|-
literal|1
else|:
name|DateUtils
operator|.
name|getFirstDayOfWeek
argument_list|(
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|,
name|table
operator|.
name|getWeek
argument_list|()
argument_list|)
operator|-
name|session
operator|.
name|getDayOfYear
argument_list|(
literal|1
argument_list|,
name|session
operator|.
name|getPatternStartMonth
argument_list|()
argument_list|)
operator|-
literal|1
operator|)
expr_stmt|;
name|iResourceType
operator|=
name|table
operator|.
name|getResourceType
argument_list|()
expr_stmt|;
name|iBgMode
operator|=
name|table
operator|.
name|getBgMode
argument_list|()
expr_stmt|;
name|iShowEvents
operator|=
name|table
operator|.
name|getShowEvents
argument_list|()
expr_stmt|;
name|iFirstSlot
operator|=
name|table
operator|.
name|firstSlot
argument_list|()
expr_stmt|;
name|iLastSlot
operator|=
name|table
operator|.
name|lastSlot
argument_list|()
expr_stmt|;
name|iDayCode
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|int
name|day
init|=
name|table
operator|.
name|startDay
argument_list|()
init|;
name|day
operator|<=
name|table
operator|.
name|endDay
argument_list|()
condition|;
name|day
operator|++
control|)
if|if
condition|(
operator|!
name|table
operator|.
name|skipDay
argument_list|(
name|day
argument_list|)
condition|)
name|iDayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|day
index|]
expr_stmt|;
name|iPattern
operator|=
name|dp
operator|.
name|getPatternBitSet
argument_list|()
expr_stmt|;
name|iNrWeeks
operator|=
name|dp
operator|.
name|getEffectiveNumberOfWeeks
argument_list|()
expr_stmt|;
if|if
condition|(
name|iFirstDay
operator|<
literal|0
operator|&&
name|ApplicationProperty
operator|.
name|TimetableGridUtilizationSkipHolidays
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|int
name|nrDays
init|=
literal|0
decl_stmt|;
name|int
name|idx
init|=
operator|-
literal|1
decl_stmt|;
name|int
name|daysInWeek
index|[]
init|=
operator|new
name|int
index|[]
block|{
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|}
decl_stmt|;
while|while
condition|(
operator|(
name|idx
operator|=
name|iPattern
operator|.
name|nextSetBit
argument_list|(
literal|1
operator|+
name|idx
argument_list|)
operator|)
operator|>=
literal|0
condition|)
block|{
name|int
name|dow
init|=
operator|(
operator|(
name|idx
operator|+
name|iStartDayDayOfWeek
operator|)
operator|%
literal|7
operator|)
decl_stmt|;
if|if
condition|(
operator|(
name|iDayCode
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|dow
index|]
operator|)
operator|!=
literal|0
condition|)
block|{
name|nrDays
operator|++
expr_stmt|;
name|daysInWeek
index|[
name|dow
index|]
operator|++
expr_stmt|;
block|}
block|}
name|float
name|weekDays
init|=
literal|1f
operator|/
name|table
operator|.
name|nrDays
argument_list|()
decl_stmt|;
if|if
condition|(
name|weekDays
operator|>=
literal|0.2f
condition|)
block|{
name|iNrWeeks
operator|=
name|weekDays
operator|*
name|nrDays
expr_stmt|;
block|}
else|else
block|{
name|iNrWeeks
operator|=
literal|0.2f
operator|*
operator|(
name|daysInWeek
index|[
literal|0
index|]
operator|+
name|daysInWeek
index|[
literal|1
index|]
operator|+
name|daysInWeek
index|[
literal|2
index|]
operator|+
name|daysInWeek
index|[
literal|3
index|]
operator|+
name|daysInWeek
index|[
literal|4
index|]
operator|)
expr_stmt|;
block|}
block|}
name|iSlotsPerWeek
operator|=
operator|(
name|iLastSlot
operator|-
name|iFirstSlot
operator|+
literal|1
operator|)
operator|*
name|table
operator|.
name|nrDays
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|int
name|getResourceType
parameter_list|()
block|{
return|return
name|iResourceType
return|;
block|}
specifier|public
name|int
name|getFirstDay
parameter_list|()
block|{
return|return
name|iFirstDay
return|;
block|}
specifier|public
name|int
name|getBgMode
parameter_list|()
block|{
return|return
name|iBgMode
return|;
block|}
specifier|public
name|boolean
name|isShowEvents
parameter_list|()
block|{
return|return
name|iShowEvents
return|;
block|}
specifier|public
name|int
name|getStartDayDayOfWeek
parameter_list|()
block|{
return|return
name|iStartDayDayOfWeek
return|;
block|}
specifier|public
name|int
name|getFirstSlot
parameter_list|()
block|{
return|return
name|iFirstSlot
return|;
block|}
specifier|public
name|int
name|getLastSlot
parameter_list|()
block|{
return|return
name|iLastSlot
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
name|BitSet
name|getDefaultDatePattern
parameter_list|()
block|{
return|return
name|iPattern
return|;
block|}
specifier|public
name|float
name|getNumberOfWeeks
parameter_list|()
block|{
return|return
operator|(
name|iFirstDay
operator|>=
literal|0
condition|?
literal|1.0f
else|:
name|iNrWeeks
operator|)
return|;
block|}
specifier|public
name|int
name|getSlotsPerWeek
parameter_list|()
block|{
return|return
name|iSlotsPerWeek
return|;
block|}
block|}
end_class

end_unit

