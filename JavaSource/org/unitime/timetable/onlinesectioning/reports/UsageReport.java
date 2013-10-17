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
name|onlinesectioning
operator|.
name|reports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|OnlineSectioningLog
operator|.
name|Action
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UsageReport
implements|implements
name|OnlineSectioningReport
operator|.
name|Report
block|{
specifier|private
name|DateFormat
name|dfHour
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yy-MM-dd HH"
argument_list|)
decl_stmt|;
specifier|private
name|DateFormat
name|dfDay
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yy-MM-dd"
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|getYear
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"year"
argument_list|,
literal|"2012"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getTerm
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"term"
argument_list|,
literal|"Fall"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getCampus
parameter_list|()
block|{
return|return
name|System
operator|.
name|getProperty
argument_list|(
literal|"campus"
argument_list|,
literal|"PWL"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|File
name|getReportFolder
parameter_list|()
block|{
return|return
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|,
literal|"~"
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getOperations
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"section"
block|,
literal|"suggestions"
block|,
literal|"reload-student"
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|process
parameter_list|(
name|OnlineSectioningReport
name|report
parameter_list|,
name|String
name|student
parameter_list|,
name|List
argument_list|<
name|Action
argument_list|>
name|actions
parameter_list|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|hours
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|days
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Action
name|action
range|:
name|actions
control|)
block|{
name|Date
name|d
init|=
operator|new
name|Date
argument_list|(
name|action
operator|.
name|getStartTime
argument_list|()
argument_list|)
decl_stmt|;
name|report
operator|.
name|inc
argument_list|(
name|action
operator|.
name|getOperation
argument_list|()
operator|+
literal|" ["
operator|+
name|dfHour
operator|.
name|format
argument_list|(
name|d
argument_list|)
operator|+
literal|"]"
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"HourDistribution"
argument_list|,
name|dfHour
operator|.
name|format
argument_list|(
name|d
argument_list|)
argument_list|,
name|action
operator|.
name|getOperation
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"DayDistribution"
argument_list|,
name|dfDay
operator|.
name|format
argument_list|(
name|d
argument_list|)
argument_list|,
name|action
operator|.
name|getOperation
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
literal|"reload-student"
operator|.
name|equals
argument_list|(
name|action
operator|.
name|getOperation
argument_list|()
argument_list|)
condition|)
block|{
name|hours
operator|.
name|add
argument_list|(
name|dfHour
operator|.
name|format
argument_list|(
name|d
argument_list|)
argument_list|)
expr_stmt|;
name|days
operator|.
name|add
argument_list|(
name|dfDay
operator|.
name|format
argument_list|(
name|d
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|s
range|:
name|days
control|)
block|{
name|report
operator|.
name|inc
argument_list|(
literal|"Student ["
operator|+
name|s
operator|+
literal|"]"
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"DayDistribution"
argument_list|,
name|s
argument_list|,
literal|"Students"
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|s
range|:
name|hours
control|)
block|{
name|report
operator|.
name|inc
argument_list|(
literal|"Student ["
operator|+
name|s
operator|+
literal|"]"
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|report
operator|.
name|inc
argument_list|(
literal|"HourDistribution"
argument_list|,
name|s
argument_list|,
literal|"Students"
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
name|long
name|done
init|=
name|Math
operator|.
name|round
argument_list|(
name|report
operator|.
name|inc
argument_list|(
literal|"Students"
argument_list|,
literal|1.0
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|done
operator|%
literal|100
operator|)
operator|==
literal|0
condition|)
block|{
name|OnlineSectioningReport
operator|.
name|sLog
operator|.
name|info
argument_list|(
literal|"---- after "
operator|+
name|done
operator|+
literal|" students"
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|name
range|:
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|report
operator|.
name|iCounters
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
name|OnlineSectioningReport
operator|.
name|sLog
operator|.
name|info
argument_list|(
name|name
operator|+
literal|": "
operator|+
name|report
operator|.
name|iCounters
operator|.
name|get
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
try|try
block|{
operator|new
name|OnlineSectioningReport
argument_list|(
operator|new
name|UsageReport
argument_list|()
argument_list|)
operator|.
name|run
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

