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
name|onlinesectioning
operator|.
name|solver
package|;
end_package

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
name|RoomLocation
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Course
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Instructor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Section
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|online
operator|.
name|selection
operator|.
name|SuggestionsBranchAndBound
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
name|gwt
operator|.
name|resources
operator|.
name|StudentSectioningConstants
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
name|gwt
operator|.
name|server
operator|.
name|Query
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
name|SuggestionsFilter
implements|implements
name|SuggestionsBranchAndBound
operator|.
name|SuggestionFilter
block|{
specifier|private
specifier|static
name|StudentSectioningConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Query
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|Date
name|iFirstDate
init|=
literal|null
decl_stmt|;
specifier|public
name|SuggestionsFilter
parameter_list|(
name|String
name|filter
parameter_list|,
name|Date
name|firstDate
parameter_list|)
block|{
name|iFilter
operator|=
operator|new
name|Query
argument_list|(
name|filter
argument_list|)
expr_stmt|;
name|iFirstDate
operator|=
name|firstDate
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|match
parameter_list|(
name|Course
name|course
parameter_list|,
name|Section
name|section
parameter_list|)
block|{
return|return
name|iFilter
operator|.
name|match
argument_list|(
operator|new
name|SectionMatcher
argument_list|(
name|course
argument_list|,
name|section
argument_list|)
argument_list|)
return|;
block|}
specifier|private
class|class
name|SectionMatcher
implements|implements
name|Query
operator|.
name|TermMatcher
block|{
specifier|private
name|Course
name|iCourse
decl_stmt|;
specifier|private
name|Section
name|iSection
decl_stmt|;
specifier|public
name|SectionMatcher
parameter_list|(
name|Course
name|course
parameter_list|,
name|Section
name|section
parameter_list|)
block|{
name|iCourse
operator|=
name|course
expr_stmt|;
name|iSection
operator|=
name|section
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|match
parameter_list|(
name|String
name|attr
parameter_list|,
name|String
name|term
parameter_list|)
block|{
if|if
condition|(
name|term
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|attr
operator|==
literal|null
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"crn"
argument_list|)
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"id"
argument_list|)
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"externalId"
argument_list|)
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"exid"
argument_list|)
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"name"
argument_list|)
condition|)
block|{
if|if
condition|(
name|iSection
operator|.
name|getName
argument_list|(
name|iCourse
operator|.
name|getId
argument_list|()
argument_list|)
operator|!=
literal|null
operator|&&
name|iSection
operator|.
name|getName
argument_list|(
name|iCourse
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|term
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
if|if
condition|(
name|attr
operator|==
literal|null
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"course"
argument_list|)
condition|)
block|{
if|if
condition|(
name|iCourse
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|term
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|||
name|iCourse
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|term
argument_list|)
operator|||
name|iCourse
operator|.
name|getCourseNumber
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|term
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
if|if
condition|(
name|attr
operator|==
literal|null
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"day"
argument_list|)
condition|)
block|{
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|==
literal|null
operator|&&
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"none"
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|int
name|day
init|=
name|parseDay
argument_list|(
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|day
operator|>
literal|0
operator|&&
operator|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
operator|&
name|day
operator|)
operator|==
name|day
condition|)
return|return
literal|true
return|;
block|}
block|}
if|if
condition|(
name|attr
operator|==
literal|null
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"time"
argument_list|)
condition|)
block|{
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|==
literal|null
operator|&&
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"none"
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|int
name|start
init|=
name|parseStart
argument_list|(
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|start
operator|>=
literal|0
operator|&&
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|==
name|start
condition|)
return|return
literal|true
return|;
block|}
block|}
if|if
condition|(
name|attr
operator|!=
literal|null
operator|&&
name|attr
operator|.
name|equals
argument_list|(
literal|"before"
argument_list|)
condition|)
block|{
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|int
name|end
init|=
name|parseStart
argument_list|(
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|end
operator|>=
literal|0
operator|&&
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|+
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getLength
argument_list|()
operator|-
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getBreakTime
argument_list|()
operator|/
literal|5
operator|<=
name|end
condition|)
return|return
literal|true
return|;
block|}
block|}
if|if
condition|(
name|attr
operator|!=
literal|null
operator|&&
name|attr
operator|.
name|equals
argument_list|(
literal|"after"
argument_list|)
condition|)
block|{
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|int
name|start
init|=
name|parseStart
argument_list|(
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|start
operator|>=
literal|0
operator|&&
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|>=
name|start
condition|)
return|return
literal|true
return|;
block|}
block|}
if|if
condition|(
name|attr
operator|==
literal|null
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"date"
argument_list|)
condition|)
block|{
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|==
literal|null
operator|&&
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"none"
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getWeekCode
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|df
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_PATTERN
argument_list|)
decl_stmt|;
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
name|iFirstDate
argument_list|)
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
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getWeekCode
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getWeekCode
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|DayCode
name|day
init|=
literal|null
decl_stmt|;
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
name|day
operator|=
name|DayCode
operator|.
name|MON
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|TUESDAY
case|:
name|day
operator|=
name|DayCode
operator|.
name|TUE
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|WEDNESDAY
case|:
name|day
operator|=
name|DayCode
operator|.
name|WED
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|THURSDAY
case|:
name|day
operator|=
name|DayCode
operator|.
name|THU
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|FRIDAY
case|:
name|day
operator|=
name|DayCode
operator|.
name|FRI
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SATURDAY
case|:
name|day
operator|=
name|DayCode
operator|.
name|SAT
expr_stmt|;
break|break;
case|case
name|Calendar
operator|.
name|SUNDAY
case|:
name|day
operator|=
name|DayCode
operator|.
name|SUN
expr_stmt|;
break|break;
block|}
if|if
condition|(
operator|(
name|iSection
operator|.
name|getTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
operator|&
name|day
operator|.
name|getCode
argument_list|()
operator|)
operator|==
name|day
operator|.
name|getCode
argument_list|()
condition|)
block|{
name|int
name|d
init|=
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_MONTH
argument_list|)
decl_stmt|;
name|int
name|m
init|=
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MONTH
argument_list|)
operator|+
literal|1
decl_stmt|;
if|if
condition|(
name|df
operator|.
name|format
argument_list|(
name|cal
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|equalsIgnoreCase
argument_list|(
name|term
argument_list|)
operator|||
name|eq
argument_list|(
name|d
operator|+
literal|"."
operator|+
name|m
operator|+
literal|"."
argument_list|,
name|term
argument_list|)
operator|||
name|eq
argument_list|(
name|m
operator|+
literal|"/"
operator|+
name|d
argument_list|,
name|term
argument_list|)
condition|)
return|return
literal|true
return|;
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
block|}
block|}
block|}
if|if
condition|(
name|attr
operator|==
literal|null
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"room"
argument_list|)
condition|)
block|{
if|if
condition|(
operator|(
name|iSection
operator|.
name|getRooms
argument_list|()
operator|==
literal|null
operator|||
name|iSection
operator|.
name|getRooms
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
operator|&&
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"none"
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iSection
operator|.
name|getRooms
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|RoomLocation
name|r
range|:
name|iSection
operator|.
name|getRooms
argument_list|()
control|)
block|{
if|if
condition|(
name|has
argument_list|(
name|r
operator|.
name|getName
argument_list|()
argument_list|,
name|term
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
block|}
block|}
if|if
condition|(
name|attr
operator|==
literal|null
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"instr"
argument_list|)
operator|||
name|attr
operator|.
name|equals
argument_list|(
literal|"instructor"
argument_list|)
condition|)
block|{
if|if
condition|(
name|attr
operator|!=
literal|null
operator|&&
operator|!
name|iSection
operator|.
name|hasInstructors
argument_list|()
operator|&&
name|term
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"none"
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iSection
operator|.
name|hasInstructors
argument_list|()
condition|)
for|for
control|(
name|Instructor
name|instructor
range|:
name|iSection
operator|.
name|getInstructors
argument_list|()
control|)
block|{
if|if
condition|(
name|has
argument_list|(
name|instructor
operator|.
name|getName
argument_list|()
argument_list|,
name|term
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|instructor
operator|.
name|getEmail
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|String
name|email
init|=
name|instructor
operator|.
name|getEmail
argument_list|()
decl_stmt|;
if|if
condition|(
name|email
operator|.
name|indexOf
argument_list|(
literal|'@'
argument_list|)
operator|>=
literal|0
condition|)
name|email
operator|=
name|email
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|email
operator|.
name|indexOf
argument_list|(
literal|'@'
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|eq
argument_list|(
name|email
argument_list|,
name|term
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
specifier|private
name|boolean
name|eq
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|term
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
name|term
argument_list|)
return|;
block|}
specifier|private
name|boolean
name|has
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|term
parameter_list|)
block|{
if|if
condition|(
name|name
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|String
name|t
range|:
name|name
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
control|)
if|if
condition|(
name|t
operator|.
name|equalsIgnoreCase
argument_list|(
name|term
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|private
name|int
name|parseDay
parameter_list|(
name|String
name|token
parameter_list|)
block|{
name|int
name|days
init|=
literal|0
decl_stmt|;
name|boolean
name|found
init|=
literal|false
decl_stmt|;
do|do
block|{
name|found
operator|=
literal|false
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
name|CONSTANTS
operator|.
name|longDays
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
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|longDays
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator||=
name|DayCode
operator|.
name|values
argument_list|()
index|[
name|i
index|]
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|longDays
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|days
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
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|days
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator||=
name|DayCode
operator|.
name|values
argument_list|()
index|[
name|i
index|]
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|days
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|days
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
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|days
argument_list|()
index|[
name|i
index|]
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|)
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator||=
name|DayCode
operator|.
name|values
argument_list|()
index|[
name|i
index|]
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|shortDays
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
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator||=
name|DayCode
operator|.
name|values
argument_list|()
index|[
name|i
index|]
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|shortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|CONSTANTS
operator|.
name|freeTimeShortDays
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
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|CONSTANTS
operator|.
name|freeTimeShortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|toLowerCase
argument_list|()
argument_list|)
condition|)
block|{
name|days
operator||=
name|DayCode
operator|.
name|values
argument_list|()
index|[
name|i
index|]
operator|.
name|getCode
argument_list|()
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
name|CONSTANTS
operator|.
name|freeTimeShortDays
argument_list|()
index|[
name|i
index|]
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
do|while
condition|(
name|found
condition|)
do|;
return|return
operator|(
name|token
operator|.
name|isEmpty
argument_list|()
condition|?
name|days
else|:
literal|0
operator|)
return|;
block|}
specifier|private
name|int
name|parseStart
parameter_list|(
name|String
name|token
parameter_list|)
block|{
name|int
name|startHour
init|=
literal|0
decl_stmt|,
name|startMin
init|=
literal|0
decl_stmt|;
name|String
name|number
init|=
literal|""
decl_stmt|;
while|while
condition|(
operator|!
name|token
operator|.
name|isEmpty
argument_list|()
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|>=
literal|'0'
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|<=
literal|'9'
condition|)
block|{
name|number
operator|+=
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
name|number
operator|.
name|length
argument_list|()
operator|>
literal|2
condition|)
block|{
name|startHour
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
operator|/
literal|100
expr_stmt|;
name|startMin
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
operator|%
literal|100
expr_stmt|;
block|}
else|else
block|{
name|startHour
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|":"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|number
operator|=
literal|""
expr_stmt|;
while|while
condition|(
operator|!
name|token
operator|.
name|isEmpty
argument_list|()
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|>=
literal|'0'
operator|&&
name|token
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|<=
literal|'9'
condition|)
block|{
name|number
operator|+=
name|token
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|number
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
name|startMin
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|token
operator|.
name|startsWith
argument_list|(
literal|" "
argument_list|)
condition|)
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|boolean
name|hasAmOrPm
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"am"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"a"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"pm"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|startHour
operator|<
literal|12
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|token
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"p"
argument_list|)
condition|)
block|{
name|token
operator|=
name|token
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|hasAmOrPm
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|startHour
operator|<
literal|12
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
block|}
if|if
condition|(
name|startHour
operator|<
literal|7
operator|&&
operator|!
name|hasAmOrPm
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
if|if
condition|(
name|startMin
operator|%
literal|5
operator|!=
literal|0
condition|)
name|startMin
operator|=
literal|5
operator|*
operator|(
operator|(
name|startMin
operator|+
literal|2
operator|)
operator|/
literal|5
operator|)
expr_stmt|;
if|if
condition|(
name|startHour
operator|==
literal|7
operator|&&
name|startMin
operator|==
literal|0
operator|&&
operator|!
name|hasAmOrPm
condition|)
name|startHour
operator|+=
literal|12
expr_stmt|;
return|return
operator|(
literal|60
operator|*
name|startHour
operator|+
name|startMin
operator|)
operator|/
literal|5
return|;
block|}
block|}
block|}
end_class

end_unit

