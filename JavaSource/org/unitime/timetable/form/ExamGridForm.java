begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
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
name|HashMap
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
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|Map
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
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
name|solver
operator|.
name|WebSolver
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
name|solver
operator|.
name|exam
operator|.
name|ExamSolverProxy
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
name|ComboBoxLookup
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
name|timegrid
operator|.
name|ExamGridTable
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamGridForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1429431006186003906L
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|>
name|iPeriods
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|int
name|iSessionBeginWeek
decl_stmt|;
specifier|private
name|Date
name|iSessionBeginDate
decl_stmt|;
specifier|private
name|Date
name|iExamBeginDate
decl_stmt|;
specifier|private
name|boolean
name|iShowSections
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iOp
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
name|iExamType
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|iDate
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|iStartTime
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
name|iEndTime
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|int
name|iResource
init|=
name|ExamGridTable
operator|.
name|sResourceRoom
decl_stmt|;
specifier|private
name|int
name|iBackground
init|=
name|ExamGridTable
operator|.
name|sBgNone
decl_stmt|;
specifier|private
name|String
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iDispMode
init|=
name|ExamGridTable
operator|.
name|sDispModePerWeekVertical
decl_stmt|;
specifier|private
name|int
name|iOrder
init|=
name|ExamGridTable
operator|.
name|sOrderByNameAsc
decl_stmt|;
specifier|private
name|boolean
name|iBgPreferences
init|=
literal|false
decl_stmt|;
specifier|public
name|int
name|getDate
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
return|return
name|iDate
operator|.
name|get
argument_list|(
name|examType
argument_list|)
return|;
block|}
specifier|public
name|void
name|setDate
parameter_list|(
name|String
name|examType
parameter_list|,
name|int
name|date
parameter_list|)
block|{
name|iDate
operator|.
name|put
argument_list|(
name|examType
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAllDates
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
return|return
name|iDate
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|==
name|Integer
operator|.
name|MIN_VALUE
return|;
block|}
specifier|public
name|int
name|getStartTime
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
return|return
name|iStartTime
operator|.
name|get
argument_list|(
name|examType
argument_list|)
return|;
block|}
specifier|public
name|void
name|setStartTime
parameter_list|(
name|String
name|examType
parameter_list|,
name|int
name|startTime
parameter_list|)
block|{
name|iStartTime
operator|.
name|put
argument_list|(
name|examType
argument_list|,
name|startTime
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getEndTime
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
return|return
name|iEndTime
operator|.
name|get
argument_list|(
name|examType
argument_list|)
return|;
block|}
specifier|public
name|void
name|setEndTime
parameter_list|(
name|String
name|examType
parameter_list|,
name|int
name|endTime
parameter_list|)
block|{
name|iEndTime
operator|.
name|put
argument_list|(
name|examType
argument_list|,
name|endTime
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getResource
parameter_list|()
block|{
return|return
name|iResource
return|;
block|}
specifier|public
name|void
name|setResource
parameter_list|(
name|int
name|resource
parameter_list|)
block|{
name|iResource
operator|=
name|resource
expr_stmt|;
block|}
specifier|public
name|int
name|getBackground
parameter_list|()
block|{
return|return
name|iBackground
return|;
block|}
specifier|public
name|void
name|setBackground
parameter_list|(
name|int
name|background
parameter_list|)
block|{
name|iBackground
operator|=
name|background
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
name|void
name|setFilter
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
specifier|public
name|int
name|getDispMode
parameter_list|()
block|{
return|return
name|iDispMode
return|;
block|}
specifier|public
name|void
name|setDispMode
parameter_list|(
name|int
name|dispMode
parameter_list|)
block|{
name|iDispMode
operator|=
name|dispMode
expr_stmt|;
block|}
specifier|public
name|int
name|getOrder
parameter_list|()
block|{
return|return
name|iOrder
return|;
block|}
specifier|public
name|void
name|setOrder
parameter_list|(
name|int
name|order
parameter_list|)
block|{
name|iOrder
operator|=
name|order
expr_stmt|;
block|}
specifier|public
name|boolean
name|getBgPreferences
parameter_list|()
block|{
return|return
name|iBgPreferences
return|;
block|}
specifier|public
name|void
name|setBgPreferences
parameter_list|(
name|boolean
name|bgPreferences
parameter_list|)
block|{
name|iBgPreferences
operator|=
name|bgPreferences
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iDate
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iStartTime
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iEndTime
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iResource
operator|=
name|ExamGridTable
operator|.
name|sResourceRoom
expr_stmt|;
name|iBackground
operator|=
name|ExamGridTable
operator|.
name|sBgNone
expr_stmt|;
name|iFilter
operator|=
literal|null
expr_stmt|;
name|iDispMode
operator|=
name|ExamGridTable
operator|.
name|sDispModePerWeekVertical
expr_stmt|;
name|iOrder
operator|=
name|ExamGridTable
operator|.
name|sOrderByNameAsc
expr_stmt|;
name|iBgPreferences
operator|=
literal|false
expr_stmt|;
name|iOp
operator|=
literal|null
expr_stmt|;
name|iShowSections
operator|=
literal|false
expr_stmt|;
name|iExamType
operator|=
literal|null
expr_stmt|;
try|try
block|{
name|ExamSolverProxy
name|solver
init|=
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
name|iExamType
operator|=
name|solver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLong
argument_list|(
literal|"Exam.Type"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|Date
name|getExamBeginDate
parameter_list|()
block|{
return|return
name|iExamBeginDate
return|;
block|}
specifier|public
name|TreeSet
name|getPeriods
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
return|return
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
return|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|SessionContext
name|context
parameter_list|)
throws|throws
name|Exception
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
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|iSessionId
operator|=
name|session
operator|.
name|getUniqueId
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
name|setTime
argument_list|(
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
expr_stmt|;
name|iSessionBeginWeek
operator|=
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|WEEK_OF_YEAR
argument_list|)
expr_stmt|;
name|iSessionBeginDate
operator|=
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
expr_stmt|;
name|iExamBeginDate
operator|=
name|session
operator|.
name|getExamBeginDate
argument_list|()
expr_stmt|;
name|iPeriods
operator|.
name|clear
argument_list|()
expr_stmt|;
for|for
control|(
name|ExamType
name|type
range|:
name|ExamType
operator|.
name|findAllUsed
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|iPeriods
operator|.
name|put
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|ExamPeriod
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|setDate
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.date."
operator|+
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|MIN_VALUE
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setStartTime
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.start."
operator|+
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getFirstStart
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setEndTime
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.end."
operator|+
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getLastEnd
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|setResource
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.resource"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|ExamGridTable
operator|.
name|sResourceRoom
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setBackground
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.background"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|ExamGridTable
operator|.
name|sBgNone
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setFilter
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.filter"
argument_list|)
argument_list|)
expr_stmt|;
name|setDispMode
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.dispMode"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|ExamGridTable
operator|.
name|sDispModePerWeekVertical
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setOrder
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.order"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|ExamGridTable
operator|.
name|sOrderByNameAsc
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setBgPreferences
argument_list|(
literal|"1"
operator|.
name|equals
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamGrid.bgPref"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|setExamType
argument_list|(
name|context
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
operator|==
literal|null
condition|?
name|iExamType
else|:
operator|(
name|Long
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
literal|"Exam.Type"
argument_list|)
argument_list|)
expr_stmt|;
name|setShowSections
argument_list|(
literal|"1"
operator|.
name|equals
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"ExamReport.showSections"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|SessionContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
for|for
control|(
name|ExamType
name|type
range|:
name|ExamType
operator|.
name|findAllUsed
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.date."
operator|+
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getDate
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.start."
operator|+
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getStartTime
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.end."
operator|+
name|type
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getEndTime
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.resource"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getResource
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.background"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getBackground
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.filter"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getFilter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.dispMode"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getDispMode
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.order"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|getOrder
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamGrid.bgPref"
argument_list|,
name|getBgPreferences
argument_list|()
condition|?
literal|"1"
else|:
literal|"0"
argument_list|)
expr_stmt|;
name|context
operator|.
name|setAttribute
argument_list|(
literal|"Exam.Type"
argument_list|,
name|getExamType
argument_list|()
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"ExamReport.showSections"
argument_list|,
name|getShowSections
argument_list|()
condition|?
literal|"1"
else|:
literal|"0"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getDates
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|ret
init|=
operator|new
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
argument_list|()
decl_stmt|;
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"All Dates"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|MIN_VALUE
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|HashSet
name|added
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
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
name|DATE_EVENT_SHORT
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
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
name|setTime
argument_list|(
name|period
operator|.
name|getStartDate
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|week
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|cal
operator|.
name|getTime
argument_list|()
operator|.
name|after
argument_list|(
name|iSessionBeginDate
argument_list|)
operator|&&
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|WEEK_OF_YEAR
argument_list|)
operator|!=
name|iSessionBeginWeek
condition|)
block|{
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
operator|-
literal|7
argument_list|)
expr_stmt|;
name|week
operator|++
expr_stmt|;
block|}
while|while
condition|(
name|cal
operator|.
name|getTime
argument_list|()
operator|.
name|before
argument_list|(
name|iSessionBeginDate
argument_list|)
operator|&&
name|cal
operator|.
name|get
argument_list|(
name|Calendar
operator|.
name|WEEK_OF_YEAR
argument_list|)
operator|!=
name|iSessionBeginWeek
condition|)
block|{
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_WEEK
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|week
operator|--
expr_stmt|;
block|}
name|cal
operator|.
name|setTime
argument_list|(
name|period
operator|.
name|getStartDate
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|added
operator|.
name|add
argument_list|(
literal|1000
operator|+
name|week
argument_list|)
condition|)
block|{
while|while
condition|(
name|cal
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
name|cal
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
name|String
name|first
init|=
name|df
operator|.
name|format
argument_list|(
name|cal
operator|.
name|getTime
argument_list|()
argument_list|)
decl_stmt|;
while|while
condition|(
name|cal
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
name|SUNDAY
condition|)
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
name|String
name|end
init|=
name|df
operator|.
name|format
argument_list|(
name|cal
operator|.
name|getTime
argument_list|()
argument_list|)
decl_stmt|;
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"Week "
operator|+
name|week
operator|+
literal|" ("
operator|+
name|first
operator|+
literal|" - "
operator|+
name|end
operator|+
literal|")"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
literal|1000
operator|+
name|week
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|added
operator|.
name|add
argument_list|(
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|)
condition|)
block|{
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|ExamGridTable
operator|.
name|sDF
operator|.
name|format
argument_list|(
name|period
operator|.
name|getStartDate
argument_list|()
argument_list|)
argument_list|,
name|period
operator|.
name|getDateOffset
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|int
name|getFirstDate
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
name|int
name|startDate
init|=
name|Integer
operator|.
name|MAX_VALUE
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|startDate
operator|=
name|Math
operator|.
name|min
argument_list|(
name|startDate
argument_list|,
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|startDate
return|;
block|}
specifier|public
name|int
name|getLastDate
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
name|int
name|endDate
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|endDate
operator|=
name|Math
operator|.
name|max
argument_list|(
name|endDate
argument_list|,
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|endDate
return|;
block|}
specifier|public
name|int
name|getFirstStart
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
name|int
name|startSlot
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|startSlot
operator|<
literal|0
condition|)
name|startSlot
operator|=
name|period
operator|.
name|getStartSlot
argument_list|()
expr_stmt|;
else|else
name|startSlot
operator|=
name|Math
operator|.
name|min
argument_list|(
name|startSlot
argument_list|,
name|period
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|startSlot
return|;
block|}
specifier|public
name|int
name|getLastEnd
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
name|int
name|endSlot
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|endSlot
operator|<
literal|0
condition|)
name|endSlot
operator|=
name|period
operator|.
name|getEndSlot
argument_list|()
expr_stmt|;
else|else
name|endSlot
operator|=
name|Math
operator|.
name|max
argument_list|(
name|endSlot
argument_list|,
name|period
operator|.
name|getEndSlot
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|endSlot
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getStartTimes
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|ret
init|=
operator|new
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
argument_list|()
decl_stmt|;
name|HashSet
name|added
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|added
operator|.
name|add
argument_list|(
name|period
operator|.
name|getStartSlot
argument_list|()
argument_list|)
condition|)
block|{
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|period
operator|.
name|getStartTimeLabel
argument_list|()
argument_list|,
name|period
operator|.
name|getStartSlot
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getEndTimes
parameter_list|(
name|String
name|examType
parameter_list|)
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|ret
init|=
operator|new
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
argument_list|()
decl_stmt|;
name|HashSet
name|added
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|iPeriods
operator|.
name|get
argument_list|(
name|examType
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ExamPeriod
name|period
init|=
operator|(
name|ExamPeriod
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|added
operator|.
name|add
argument_list|(
name|period
operator|.
name|getEndSlot
argument_list|()
argument_list|)
condition|)
block|{
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|period
operator|.
name|getEndTimeLabel
argument_list|()
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|period
operator|.
name|getEndSlot
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getResources
parameter_list|()
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|ret
init|=
operator|new
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
argument_list|()
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
name|ExamGridTable
operator|.
name|sResources
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|ExamGridTable
operator|.
name|sResources
index|[
name|i
index|]
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getBackgrounds
parameter_list|()
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|ret
init|=
operator|new
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
argument_list|()
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
name|ExamGridTable
operator|.
name|sBackgrounds
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|ExamGridTable
operator|.
name|sBackgrounds
index|[
name|i
index|]
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getDispModes
parameter_list|()
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|ret
init|=
operator|new
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
argument_list|()
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
name|ExamGridTable
operator|.
name|sDispModes
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|ExamGridTable
operator|.
name|sDispModes
index|[
name|i
index|]
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|getOrders
parameter_list|()
block|{
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|ret
init|=
operator|new
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
argument_list|()
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
name|ExamGridTable
operator|.
name|sOrders
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|ret
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|ExamGridTable
operator|.
name|sOrders
index|[
name|i
index|]
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|Long
name|getExamType
parameter_list|()
block|{
return|return
name|iExamType
return|;
block|}
specifier|public
name|void
name|setExamType
parameter_list|(
name|Long
name|type
parameter_list|)
block|{
name|iExamType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|int
name|getSessionBeginWeek
parameter_list|()
block|{
return|return
name|iSessionBeginWeek
return|;
block|}
specifier|public
name|Date
name|getSessionBeginDate
parameter_list|()
block|{
return|return
name|iSessionBeginDate
return|;
block|}
specifier|public
name|boolean
name|getShowSections
parameter_list|()
block|{
return|return
name|iShowSections
return|;
block|}
specifier|public
name|void
name|setShowSections
parameter_list|(
name|boolean
name|showSections
parameter_list|)
block|{
name|iShowSections
operator|=
name|showSections
expr_stmt|;
block|}
block|}
end_class

end_unit

