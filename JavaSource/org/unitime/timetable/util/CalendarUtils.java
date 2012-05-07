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
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParsePosition
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

begin_comment
comment|/**  * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|CalendarUtils
block|{
comment|/** 	 * Check if a string is a valid date 	 * @param date String to be checked 	 * @param dateFormat format of the date e.g. MM/dd/yyyy - see SimpleDateFormat 	 * @return true if it is a valid date 	 */
specifier|public
specifier|static
name|boolean
name|isValidDate
parameter_list|(
name|String
name|date
parameter_list|,
name|String
name|dateFormat
parameter_list|)
block|{
if|if
condition|(
name|date
operator|==
literal|null
operator|||
name|dateFormat
operator|==
literal|null
operator|||
name|date
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|||
name|dateFormat
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|false
return|;
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|dateFormat
argument_list|)
decl_stmt|;
name|df
operator|.
name|setLenient
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ParsePosition
name|pos
init|=
operator|new
name|ParsePosition
argument_list|(
literal|0
argument_list|)
decl_stmt|;
try|try
block|{
name|Date
name|d
init|=
name|df
operator|.
name|parse
argument_list|(
name|date
argument_list|,
name|pos
argument_list|)
decl_stmt|;
if|if
condition|(
name|d
operator|==
literal|null
operator|||
name|pos
operator|.
name|getErrorIndex
argument_list|()
operator|!=
operator|-
literal|1
operator|||
name|pos
operator|.
name|getIndex
argument_list|()
operator|!=
name|date
operator|.
name|length
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
comment|/** 	 * Parse a string to give a Date object 	 * @param date 	 * @param dateFormat format of the date e.g. MM/dd/yyyy - see SimpleDateFormat 	 * @return null if not a valid date 	 */
specifier|public
specifier|static
name|Date
name|getDate
parameter_list|(
name|String
name|date
parameter_list|,
name|String
name|dateFormat
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|isValidDate
argument_list|(
name|date
argument_list|,
name|dateFormat
argument_list|)
condition|)
return|return
operator|new
name|SimpleDateFormat
argument_list|(
name|dateFormat
argument_list|)
operator|.
name|parse
argument_list|(
name|date
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|int
name|date2dayOfYear
parameter_list|(
name|int
name|sessionYear
parameter_list|,
name|Date
name|meetingDate
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
name|meetingDate
argument_list|)
expr_stmt|;
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
return|return
name|dayOfYear
return|;
block|}
specifier|public
specifier|static
name|Date
name|dateOfYear2date
parameter_list|(
name|int
name|sessionYear
parameter_list|,
name|int
name|dayOfYear
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
if|if
condition|(
name|dayOfYear
operator|<=
literal|0
condition|)
block|{
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|,
name|sessionYear
operator|-
literal|1
argument_list|)
expr_stmt|;
name|dayOfYear
operator|+=
name|c
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
name|dayOfYear
operator|>
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|)
condition|)
block|{
name|dayOfYear
operator|-=
name|c
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|)
expr_stmt|;
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|YEAR
argument_list|,
name|sessionYear
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|c
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|dayOfYear
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|getTime
argument_list|()
return|;
block|}
block|}
end_class

end_unit

