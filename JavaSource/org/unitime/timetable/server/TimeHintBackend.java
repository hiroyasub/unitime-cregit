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
name|server
package|;
end_package

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
name|net
operator|.
name|sf
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
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|client
operator|.
name|TimeHint
operator|.
name|TimeHintRequest
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
name|TimeHint
operator|.
name|TimeHintResponse
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
name|GwtRpcException
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
name|model
operator|.
name|Class_
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
name|Exam
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
name|ExamPeriod
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
name|PeriodPreferenceModel
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
name|TimePatternDays
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
name|TimePatternTime
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
name|TimePref
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
name|Class_DAO
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
name|ExamDAO
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
name|ExamPeriodDAO
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
name|SessionContext
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
name|webutil
operator|.
name|RequiredTimeTable
import|;
end_import

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|TimeHintRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|TimeHintBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|TimeHintRequest
argument_list|,
name|TimeHintResponse
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|TimeHintResponse
name|execute
parameter_list|(
name|TimeHintRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|String
index|[]
name|params
init|=
name|request
operator|.
name|getParameter
argument_list|()
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|params
operator|.
name|length
operator|==
literal|2
condition|)
block|{
name|Long
name|examId
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|params
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|Long
name|periodId
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|params
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
name|Exam
name|exam
init|=
name|ExamDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|examId
argument_list|)
decl_stmt|;
name|ExamPeriod
name|period
init|=
name|ExamPeriodDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|periodId
argument_list|)
decl_stmt|;
name|PeriodPreferenceModel
name|px
init|=
operator|new
name|PeriodPreferenceModel
argument_list|(
name|exam
operator|.
name|getSession
argument_list|()
argument_list|,
name|period
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|px
operator|.
name|load
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|RequiredTimeTable
name|m
init|=
operator|new
name|RequiredTimeTable
argument_list|(
name|px
argument_list|)
decl_stmt|;
return|return
operator|new
name|TimeHintResponse
argument_list|(
literal|"$wnd."
operator|+
name|m
operator|.
name|print
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|)
operator|.
name|replace
argument_list|(
literal|");\n</script>"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|"<script language=\"javascript\">\ndocument.write("
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|" "
argument_list|)
argument_list|)
return|;
block|}
if|else if
condition|(
name|params
operator|.
name|length
operator|==
literal|3
condition|)
block|{
name|Long
name|classId
init|=
name|Long
operator|.
name|valueOf
argument_list|(
name|params
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|int
name|days
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
name|int
name|slot
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
name|Class_
name|clazz
init|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|classId
argument_list|)
decl_stmt|;
for|for
control|(
name|TimePref
name|p
range|:
operator|(
name|Set
argument_list|<
name|TimePref
argument_list|>
operator|)
name|clazz
operator|.
name|effectivePreferences
argument_list|(
name|TimePref
operator|.
name|class
argument_list|)
control|)
block|{
if|if
condition|(
name|p
operator|.
name|getTimePattern
argument_list|()
operator|.
name|getType
argument_list|()
operator|==
name|TimePattern
operator|.
name|sTypeExactTime
condition|)
continue|continue;
name|boolean
name|match
init|=
literal|false
decl_stmt|;
for|for
control|(
name|TimePatternDays
name|d
range|:
name|p
operator|.
name|getTimePattern
argument_list|()
operator|.
name|getDays
argument_list|()
control|)
block|{
if|if
condition|(
name|d
operator|.
name|getDayCode
argument_list|()
operator|==
name|days
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|match
condition|)
continue|continue;
name|match
operator|=
literal|false
expr_stmt|;
for|for
control|(
name|TimePatternTime
name|t
range|:
name|p
operator|.
name|getTimePattern
argument_list|()
operator|.
name|getTimes
argument_list|()
control|)
block|{
if|if
condition|(
name|t
operator|.
name|getStartSlot
argument_list|()
operator|==
name|slot
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|match
condition|)
continue|continue;
name|RequiredTimeTable
name|m
init|=
name|p
operator|.
name|getRequiredTimeTable
argument_list|(
operator|new
name|TimeLocation
argument_list|(
name|days
argument_list|,
name|slot
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0.0
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|0
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|new
name|TimeHintResponse
argument_list|(
literal|"$wnd."
operator|+
name|m
operator|.
name|print
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|)
operator|.
name|replace
argument_list|(
literal|");\n</script>"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|"<script language=\"javascript\">\ndocument.write("
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|" "
argument_list|)
argument_list|)
return|;
block|}
block|}
throw|throw
operator|new
name|GwtRpcException
argument_list|(
literal|"No matching time preference found."
argument_list|)
throw|;
block|}
block|}
end_class

end_unit

