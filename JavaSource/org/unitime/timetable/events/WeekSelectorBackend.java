begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|text
operator|.
name|DateFormat
import|;
end_import

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
name|Locale
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
name|client
operator|.
name|widgets
operator|.
name|WeekSelector
operator|.
name|WeekSelectorRequest
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
name|GwtRpcHelper
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
name|GwtRpcImplementation
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
name|WeekInterface
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

begin_class
specifier|public
class|class
name|WeekSelectorBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|WeekSelectorRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|WeekInterface
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|WeekInterface
argument_list|>
name|execute
parameter_list|(
name|WeekSelectorRequest
name|command
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|)
block|{
name|GwtRpcResponseList
argument_list|<
name|WeekInterface
argument_list|>
name|ret
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|WeekInterface
argument_list|>
argument_list|()
decl_stmt|;
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
name|session
operator|.
name|getEventBeginDate
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|)
operator|!=
name|Calendar
operator|.
name|MONDAY
condition|)
block|{
name|c
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
block|}
name|int
name|sessionYear
init|=
name|session
operator|.
name|getSessionStartYear
argument_list|()
decl_stmt|;
name|DateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd"
argument_list|)
decl_stmt|;
while|while
condition|(
name|c
operator|.
name|getTime
argument_list|()
operator|.
name|before
argument_list|(
name|session
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
condition|)
block|{
name|int
name|dayOfYear
init|=
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
operator|<
name|sessionYear
condition|)
block|{
name|Calendar
name|x
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
name|x
operator|.
name|set
argument_list|(
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
argument_list|,
literal|11
argument_list|,
literal|31
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|dayOfYear
operator|-=
name|x
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|)
operator|>
name|sessionYear
condition|)
block|{
name|Calendar
name|x
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
name|x
operator|.
name|set
argument_list|(
name|sessionYear
argument_list|,
literal|11
argument_list|,
literal|31
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|dayOfYear
operator|+=
name|x
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|)
expr_stmt|;
block|}
name|WeekInterface
name|week
init|=
operator|new
name|WeekInterface
argument_list|()
decl_stmt|;
name|week
operator|.
name|setDayOfYear
argument_list|(
name|dayOfYear
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
literal|7
condition|;
name|i
operator|++
control|)
block|{
name|week
operator|.
name|addDayName
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|c
operator|.
name|getTime
argument_list|()
argument_list|)
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
literal|1
argument_list|)
expr_stmt|;
block|}
name|ret
operator|.
name|add
argument_list|(
name|week
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

