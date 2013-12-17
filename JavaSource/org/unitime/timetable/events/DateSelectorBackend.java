begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|events
package|;
end_package

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
name|List
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
name|command
operator|.
name|client
operator|.
name|GwtRpcResponseList
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|shared
operator|.
name|EventInterface
operator|.
name|RequestSessionDetails
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
name|shared
operator|.
name|EventInterface
operator|.
name|SessionMonth
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
name|EventDateMapping
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
name|ExamType
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
name|DateUtils
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|RequestSessionDetails
operator|.
name|class
argument_list|)
specifier|public
class|class
name|DateSelectorBackend
extends|extends
name|EventAction
argument_list|<
name|RequestSessionDetails
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|SessionMonth
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|SessionMonth
argument_list|>
name|execute
parameter_list|(
name|RequestSessionDetails
name|command
parameter_list|,
name|EventContext
name|context
parameter_list|)
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
name|command
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|GwtRpcResponseList
argument_list|<
name|SessionMonth
argument_list|>
name|response
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|SessionMonth
argument_list|>
argument_list|()
decl_stmt|;
name|Calendar
name|calendar
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Date
argument_list|>
name|finals
init|=
operator|new
name|ArrayList
argument_list|<
name|Date
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Number
name|dateOffset
range|:
operator|(
name|List
argument_list|<
name|Number
argument_list|>
operator|)
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct dateOffset from ExamPeriod where session.uniqueId = :sessionId and examType.type = :finalType"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|command
operator|.
name|getSessionId
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"finalType"
argument_list|,
name|ExamType
operator|.
name|sExamTypeFinal
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|calendar
operator|.
name|setTime
argument_list|(
name|session
operator|.
name|getExamBeginDate
argument_list|()
argument_list|)
expr_stmt|;
name|calendar
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|dateOffset
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|finals
operator|.
name|add
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|EventDateMapping
operator|.
name|Class2EventDateMap
name|class2eventDateMap
init|=
operator|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|EventDateMappings
argument_list|)
condition|?
name|EventDateMapping
operator|.
name|getMapping
argument_list|(
name|command
operator|.
name|getSessionId
argument_list|()
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
for|for
control|(
name|int
name|month
init|=
name|session
operator|.
name|getStartMonth
argument_list|()
init|;
name|month
operator|<=
name|session
operator|.
name|getEndMonth
argument_list|()
condition|;
name|month
operator|++
control|)
block|{
name|calendar
operator|.
name|setTime
argument_list|(
name|DateUtils
operator|.
name|getDate
argument_list|(
literal|1
argument_list|,
name|month
argument_list|,
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|SessionMonth
name|m
init|=
operator|new
name|SessionMonth
argument_list|(
name|calendar
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
argument_list|,
name|calendar
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|MONTH
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|nrDays
init|=
name|calendar
operator|.
name|getActualMaximum
argument_list|(
name|Calendar
operator|.
name|DAY_OF_MONTH
argument_list|)
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
name|nrDays
condition|;
name|i
operator|++
control|)
block|{
switch|switch
condition|(
name|session
operator|.
name|getHoliday
argument_list|(
literal|1
operator|+
name|i
argument_list|,
name|month
argument_list|)
condition|)
block|{
case|case
name|Session
operator|.
name|sHolidayTypeBreak
case|:
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|BREAK
argument_list|)
expr_stmt|;
break|break;
case|case
name|Session
operator|.
name|sHolidayTypeHoliday
case|:
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|HOLIDAY
argument_list|)
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|compare
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
operator|==
literal|0
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|START
argument_list|)
expr_stmt|;
if|if
condition|(
name|compare
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|,
name|session
operator|.
name|getClassesEndDateTime
argument_list|()
argument_list|)
operator|==
literal|0
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|END
argument_list|)
expr_stmt|;
if|if
condition|(
name|compare
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
operator|>=
literal|0
operator|&&
name|compare
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|,
name|session
operator|.
name|getClassesEndDateTime
argument_list|()
argument_list|)
operator|<=
literal|0
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|CLASSES
argument_list|)
expr_stmt|;
for|for
control|(
name|Date
name|finalDate
range|:
name|finals
control|)
block|{
if|if
condition|(
name|compare
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|,
name|finalDate
argument_list|)
operator|==
literal|0
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|FINALS
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|compare
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|,
name|session
operator|.
name|getEventBeginDate
argument_list|()
argument_list|)
operator|<
literal|0
operator|||
name|compare
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|,
name|session
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
operator|>
literal|0
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|DISABLED
argument_list|)
expr_stmt|;
if|else if
condition|(
name|context
operator|.
name|isPastOrOutside
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|)
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|PAST
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|calendar
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
name|SATURDAY
case|:
case|case
name|Calendar
operator|.
name|SUNDAY
case|:
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|WEEKEND
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|class2eventDateMap
operator|!=
literal|null
operator|&&
name|class2eventDateMap
operator|.
name|hasClassDate
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|)
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|DATE_MAPPING_CLASS
argument_list|)
expr_stmt|;
if|if
condition|(
name|class2eventDateMap
operator|!=
literal|null
operator|&&
name|class2eventDateMap
operator|.
name|hasEventDate
argument_list|(
name|calendar
operator|.
name|getTime
argument_list|()
argument_list|)
condition|)
name|m
operator|.
name|setFlag
argument_list|(
name|i
argument_list|,
name|SessionMonth
operator|.
name|Flag
operator|.
name|DATE_MAPPING_EVENT
argument_list|)
expr_stmt|;
name|calendar
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
name|response
operator|.
name|add
argument_list|(
name|m
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
specifier|private
name|int
name|compare
parameter_list|(
name|Date
name|d1
parameter_list|,
name|Date
name|d2
parameter_list|)
block|{
name|Calendar
name|c1
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|c1
operator|.
name|setTime
argument_list|(
name|d1
argument_list|)
expr_stmt|;
name|Calendar
name|c2
init|=
name|Calendar
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|c2
operator|.
name|setTime
argument_list|(
name|d2
argument_list|)
expr_stmt|;
name|int
name|cmp
init|=
name|compare
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|,
name|Calendar
operator|.
name|YEAR
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
return|return
name|compare
argument_list|(
name|c1
argument_list|,
name|c2
argument_list|,
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|)
return|;
block|}
specifier|private
name|int
name|compare
parameter_list|(
name|Calendar
name|c1
parameter_list|,
name|Calendar
name|c2
parameter_list|,
name|int
name|field
parameter_list|)
block|{
return|return
operator|new
name|Integer
argument_list|(
name|c1
operator|.
name|get
argument_list|(
name|field
argument_list|)
argument_list|)
operator|.
name|compareTo
argument_list|(
name|c2
operator|.
name|get
argument_list|(
name|field
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

