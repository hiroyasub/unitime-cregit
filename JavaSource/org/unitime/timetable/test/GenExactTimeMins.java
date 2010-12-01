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
name|test
package|;
end_package

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
name|GenExactTimeMins
block|{
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"insert into EXACT_TIME_MINS (UNIQUEID, MINS_MIN, MINS_MAX, NR_SLOTS, BREAK_TIME)"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  values (PREF_GROUP_SEQ.nextval, 0, 0, 0, 0);"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|minPerMtg
init|=
literal|5
init|;
name|minPerMtg
operator|<=
literal|720
condition|;
name|minPerMtg
operator|+=
literal|5
control|)
block|{
name|int
name|slotsPerMtg
init|=
operator|(
name|int
operator|)
name|Math
operator|.
name|round
argument_list|(
operator|(
literal|6.0
operator|/
literal|5.0
operator|)
operator|*
name|minPerMtg
operator|/
name|Constants
operator|.
name|SLOT_LENGTH_MIN
argument_list|)
decl_stmt|;
if|if
condition|(
name|minPerMtg
operator|<
literal|30.0
condition|)
name|slotsPerMtg
operator|=
name|Math
operator|.
name|min
argument_list|(
literal|6
argument_list|,
name|slotsPerMtg
argument_list|)
expr_stmt|;
name|int
name|breakTime
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|slotsPerMtg
operator|%
literal|12
operator|==
literal|0
condition|)
name|breakTime
operator|=
literal|10
expr_stmt|;
if|else if
condition|(
name|slotsPerMtg
operator|>
literal|6
condition|)
name|breakTime
operator|=
literal|15
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"insert into EXACT_TIME_MINS (UNIQUEID, MINS_MIN, MINS_MAX, NR_SLOTS, BREAK_TIME)"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  values (PREF_GROUP_SEQ.nextval, "
operator|+
operator|(
name|minPerMtg
operator|-
literal|4
operator|)
operator|+
literal|", "
operator|+
name|minPerMtg
operator|+
literal|", "
operator|+
name|slotsPerMtg
operator|+
literal|", "
operator|+
name|breakTime
operator|+
literal|");"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

