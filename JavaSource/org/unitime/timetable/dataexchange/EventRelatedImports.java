begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|dataexchange
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
name|Set
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|Email
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
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer, Tomas Muller  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|EventRelatedImports
extends|extends
name|BaseImport
block|{
specifier|protected
name|String
name|timeFormat
init|=
literal|null
decl_stmt|;
specifier|protected
name|Vector
argument_list|<
name|String
argument_list|>
name|changeList
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|TreeSet
argument_list|<
name|String
argument_list|>
name|missingLocations
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|Vector
argument_list|<
name|String
argument_list|>
name|notes
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|String
name|dateFormat
init|=
literal|null
decl_stmt|;
specifier|protected
name|boolean
name|trimLeadingZerosFromExternalId
init|=
literal|false
decl_stmt|;
specifier|protected
name|Session
name|session
init|=
literal|null
decl_stmt|;
specifier|protected
specifier|abstract
name|String
name|getEmailSubject
parameter_list|()
function_decl|;
comment|/** 	 *  	 */
specifier|public
name|EventRelatedImports
parameter_list|()
block|{
block|}
specifier|protected
name|void
name|addNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|notes
operator|.
name|add
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|clearNotes
parameter_list|()
block|{
name|notes
operator|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|updateChangeList
parameter_list|(
name|boolean
name|changed
parameter_list|)
block|{
if|if
condition|(
name|changed
operator|&&
name|notes
operator|!=
literal|null
condition|)
block|{
name|changeList
operator|.
name|addAll
argument_list|(
name|notes
argument_list|)
expr_stmt|;
name|String
name|note
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|it
init|=
name|notes
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
name|note
operator|=
operator|(
name|String
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
name|info
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
block|}
name|clearNotes
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|addMissingLocation
parameter_list|(
name|String
name|location
parameter_list|)
block|{
name|missingLocations
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|reportMissingLocations
parameter_list|()
block|{
if|if
condition|(
operator|!
name|missingLocations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|changeList
operator|.
name|add
argument_list|(
literal|"\nMissing Locations\n"
argument_list|)
expr_stmt|;
name|info
argument_list|(
literal|"\nMissing Locations\n"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|it
init|=
name|missingLocations
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
name|String
name|location
init|=
operator|(
name|String
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|changeList
operator|.
name|add
argument_list|(
literal|"\t"
operator|+
name|location
argument_list|)
expr_stmt|;
name|info
argument_list|(
literal|"\t"
operator|+
name|location
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|protected
name|void
name|mailLoadResults
parameter_list|()
block|{
try|try
block|{
name|Email
name|email
init|=
name|Email
operator|.
name|createEmail
argument_list|()
decl_stmt|;
name|email
operator|.
name|setSubject
argument_list|(
literal|"UniTime (Data Import): "
operator|+
name|getEmailSubject
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|mail
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|it
init|=
name|changeList
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
name|mail
operator|+=
operator|(
name|String
operator|)
name|it
operator|.
name|next
argument_list|()
operator|+
literal|"\n"
expr_stmt|;
block|}
name|email
operator|.
name|setText
argument_list|(
name|mail
argument_list|)
expr_stmt|;
name|email
operator|.
name|addRecipient
argument_list|(
name|getManager
argument_list|()
operator|.
name|getEmailAddress
argument_list|()
argument_list|,
name|getManager
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ApplicationProperty
operator|.
name|EmailNotificationDataExchange
operator|.
name|isTrue
argument_list|()
condition|)
name|email
operator|.
name|addNotifyCC
argument_list|()
expr_stmt|;
name|email
operator|.
name|send
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|Session
name|findSession
parameter_list|(
name|String
name|academicInitiative
parameter_list|,
name|String
name|academicYear
parameter_list|,
name|String
name|academicTerm
parameter_list|)
block|{
return|return
operator|(
name|Session
operator|)
name|this
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from Session as s where s.academicInitiative = :academicInititive and s.academicYear = :academicYear  and s.academicTerm = :academicTerm"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"academicInititive"
argument_list|,
name|academicInitiative
argument_list|)
operator|.
name|setString
argument_list|(
literal|"academicYear"
argument_list|,
name|academicYear
argument_list|)
operator|.
name|setString
argument_list|(
literal|"academicTerm"
argument_list|,
name|academicTerm
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
specifier|protected
name|List
name|findNonUniversityLocationsWithIdOrName
parameter_list|(
name|String
name|id
parameter_list|,
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|id
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|this
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct l from NonUniversityLocation as l where l.externalUniqueId=:id and l.session.uniqueId=:sessionId"
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
name|setString
argument_list|(
literal|"id"
argument_list|,
name|id
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
operator|)
return|;
block|}
if|if
condition|(
name|name
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|this
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct l from NonUniversityLocation as l where l.name=:name and l.session.uniqueId=:sessionId"
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
name|setString
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
operator|)
return|;
block|}
return|return
operator|(
operator|new
name|ArrayList
argument_list|()
operator|)
return|;
block|}
specifier|protected
name|List
name|findNonUniversityLocationsWithName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|name
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|this
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct l from NonUniversityLocation as l where l.name=:name and l.session.uniqueId=:sessionId"
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
name|setString
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
operator|)
return|;
block|}
return|return
operator|(
operator|new
name|ArrayList
argument_list|()
operator|)
return|;
block|}
specifier|protected
class|class
name|TimeObject
block|{
specifier|private
name|Integer
name|startPeriod
decl_stmt|;
specifier|private
name|Integer
name|endPeriod
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Integer
argument_list|>
name|days
decl_stmt|;
specifier|private
name|String
name|patternName
init|=
literal|null
decl_stmt|;
name|TimeObject
parameter_list|(
name|String
name|startTime
parameter_list|,
name|String
name|endTime
parameter_list|,
name|String
name|daysOfWeek
parameter_list|)
throws|throws
name|Exception
block|{
name|this
argument_list|(
name|startTime
argument_list|,
name|endTime
argument_list|,
name|daysOfWeek
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|TimeObject
parameter_list|(
name|String
name|startTime
parameter_list|,
name|String
name|endTime
parameter_list|,
name|String
name|daysOfWeek
parameter_list|,
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
name|startPeriod
operator|=
name|str2Slot
argument_list|(
name|startTime
argument_list|)
expr_stmt|;
name|endPeriod
operator|=
name|str2Slot
argument_list|(
name|endTime
argument_list|)
expr_stmt|;
name|patternName
operator|=
name|name
expr_stmt|;
if|if
condition|(
name|endPeriod
operator|==
literal|0
condition|)
block|{
comment|// if the end period is midnight then the meeting ends at the end of the day i.e. last slot
name|endPeriod
operator|=
name|Constants
operator|.
name|SLOTS_PER_DAY
expr_stmt|;
block|}
if|if
condition|(
name|startPeriod
operator|>=
name|endPeriod
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Invalid time '"
operator|+
name|startTime
operator|+
literal|"' must be before ("
operator|+
name|endTime
operator|+
literal|")."
argument_list|)
throw|;
block|}
if|if
condition|(
name|daysOfWeek
operator|==
literal|null
operator|||
name|daysOfWeek
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return;
block|}
name|setDaysOfWeek
argument_list|(
name|daysOfWeek
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|setDaysOfWeek
parameter_list|(
name|String
name|daysOfWeek
parameter_list|)
block|{
name|days
operator|=
operator|new
name|TreeSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
expr_stmt|;
name|String
name|tmpDays
init|=
name|daysOfWeek
decl_stmt|;
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"Th"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|THURSDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"Th"
argument_list|,
literal|".."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"R"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|THURSDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"R"
argument_list|,
literal|".."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"Su"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|SUNDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"Su"
argument_list|,
literal|".."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"U"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|SUNDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"U"
argument_list|,
literal|".."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"M"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|MONDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"M"
argument_list|,
literal|"."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"T"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|TUESDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"T"
argument_list|,
literal|"."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"W"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|WEDNESDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"W"
argument_list|,
literal|"."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"F"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|FRIDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"F"
argument_list|,
literal|"."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|tmpDays
operator|.
name|contains
argument_list|(
literal|"S"
argument_list|)
condition|)
block|{
name|days
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|SATURDAY
argument_list|)
expr_stmt|;
name|tmpDays
operator|=
name|tmpDays
operator|.
name|replace
argument_list|(
literal|"S"
argument_list|,
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|getDayCode
parameter_list|()
block|{
if|if
condition|(
name|days
operator|==
literal|null
operator|||
name|days
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|0
return|;
name|int
name|dayCode
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|days
operator|.
name|contains
argument_list|(
name|Calendar
operator|.
name|MONDAY
argument_list|)
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_MON
index|]
expr_stmt|;
if|if
condition|(
name|days
operator|.
name|contains
argument_list|(
name|Calendar
operator|.
name|TUESDAY
argument_list|)
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_TUE
index|]
expr_stmt|;
if|if
condition|(
name|days
operator|.
name|contains
argument_list|(
name|Calendar
operator|.
name|WEDNESDAY
argument_list|)
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_WED
index|]
expr_stmt|;
if|if
condition|(
name|days
operator|.
name|contains
argument_list|(
name|Calendar
operator|.
name|THURSDAY
argument_list|)
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_THU
index|]
expr_stmt|;
if|if
condition|(
name|days
operator|.
name|contains
argument_list|(
name|Calendar
operator|.
name|FRIDAY
argument_list|)
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_FRI
index|]
expr_stmt|;
if|if
condition|(
name|days
operator|.
name|contains
argument_list|(
name|Calendar
operator|.
name|SATURDAY
argument_list|)
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SAT
index|]
expr_stmt|;
if|if
condition|(
name|days
operator|.
name|contains
argument_list|(
name|Calendar
operator|.
name|SUNDAY
argument_list|)
condition|)
name|dayCode
operator|+=
name|Constants
operator|.
name|DAY_CODES
index|[
name|Constants
operator|.
name|DAY_SUN
index|]
expr_stmt|;
return|return
name|dayCode
return|;
block|}
specifier|public
name|Integer
name|getStartPeriod
parameter_list|()
block|{
return|return
name|startPeriod
return|;
block|}
specifier|public
name|void
name|setStartPeriod
parameter_list|(
name|Integer
name|startPeriod
parameter_list|)
block|{
name|this
operator|.
name|startPeriod
operator|=
name|startPeriod
expr_stmt|;
block|}
specifier|public
name|Integer
name|getEndPeriod
parameter_list|()
block|{
return|return
name|endPeriod
return|;
block|}
specifier|public
name|void
name|setEndPeriod
parameter_list|(
name|Integer
name|endPeriod
parameter_list|)
block|{
name|this
operator|.
name|endPeriod
operator|=
name|endPeriod
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Integer
argument_list|>
name|getDays
parameter_list|()
block|{
return|return
name|days
return|;
block|}
specifier|public
name|void
name|setDays
parameter_list|(
name|Set
argument_list|<
name|Integer
argument_list|>
name|days
parameter_list|)
block|{
name|this
operator|.
name|days
operator|=
name|days
expr_stmt|;
block|}
specifier|public
name|Meeting
name|asMeeting
parameter_list|()
block|{
name|Meeting
name|meeting
init|=
operator|new
name|Meeting
argument_list|()
decl_stmt|;
name|meeting
operator|.
name|setClassCanOverride
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setStartOffset
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setStartPeriod
argument_list|(
name|this
operator|.
name|getStartPeriod
argument_list|()
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setStopOffset
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setStopPeriod
argument_list|(
name|this
operator|.
name|getEndPeriod
argument_list|()
argument_list|)
expr_stmt|;
name|meeting
operator|.
name|setStatus
argument_list|(
name|Meeting
operator|.
name|Status
operator|.
name|PENDING
argument_list|)
expr_stmt|;
return|return
operator|(
name|meeting
operator|)
return|;
block|}
specifier|public
name|Integer
name|str2Slot
parameter_list|(
name|String
name|timeString
parameter_list|)
throws|throws
name|Exception
block|{
name|int
name|slot
init|=
operator|-
literal|1
decl_stmt|;
try|try
block|{
name|Date
name|date
init|=
name|CalendarUtils
operator|.
name|getDate
argument_list|(
name|timeString
argument_list|,
name|timeFormat
argument_list|)
decl_stmt|;
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"HHmm"
argument_list|)
decl_stmt|;
name|int
name|time
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|date
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|hour
init|=
name|time
operator|/
literal|100
decl_stmt|;
name|int
name|min
init|=
name|time
operator|%
literal|100
decl_stmt|;
if|if
condition|(
name|hour
operator|>=
literal|24
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Invalid time '"
operator|+
name|timeString
operator|+
literal|"' -- hour ("
operator|+
name|hour
operator|+
literal|") must be between 0 and 23."
argument_list|)
throw|;
if|if
condition|(
name|min
operator|>=
literal|60
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Invalid time '"
operator|+
name|timeString
operator|+
literal|"' -- minute ("
operator|+
name|min
operator|+
literal|") must be between 0 and 59."
argument_list|)
throw|;
if|if
condition|(
operator|(
name|min
operator|%
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|)
operator|!=
literal|0
condition|)
block|{
name|min
operator|=
name|min
operator|-
operator|(
name|min
operator|%
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|)
expr_stmt|;
comment|//throw new Exception("Invalid time '"+timeString+"' -- minute ("+min+") must be divisible by "+Constants.SLOT_LENGTH_MIN+".");
block|}
name|slot
operator|=
operator|(
name|hour
operator|*
literal|60
operator|+
name|min
operator|-
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
name|Constants
operator|.
name|SLOT_LENGTH_MIN
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Invalid time '"
operator|+
name|timeString
operator|+
literal|"' -- not a number."
argument_list|)
throw|;
block|}
if|if
condition|(
name|slot
operator|<
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Invalid time '"
operator|+
name|timeString
operator|+
literal|"', did not meet format: "
operator|+
name|timeFormat
argument_list|)
throw|;
return|return
operator|(
name|slot
operator|)
return|;
block|}
specifier|public
name|String
name|getPatternName
parameter_list|()
block|{
return|return
name|patternName
return|;
block|}
specifier|public
name|void
name|setPatternName
parameter_list|(
name|String
name|patternName
parameter_list|)
block|{
name|this
operator|.
name|patternName
operator|=
name|patternName
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

