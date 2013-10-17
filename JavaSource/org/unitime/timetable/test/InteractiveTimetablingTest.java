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
name|test
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
name|io
operator|.
name|FileWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
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
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|interactive
operator|.
name|Suggestion
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
name|interactive
operator|.
name|Suggestions
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
name|interactive
operator|.
name|SuggestionsModel
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
name|TimetableLoader
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
name|TimetableSolver
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
name|TimetableXMLLoader
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
name|Lecture
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
name|TimetableModel
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
name|ifs
operator|.
name|solver
operator|.
name|Solver
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
name|ifs
operator|.
name|util
operator|.
name|DataProperties
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
name|ifs
operator|.
name|util
operator|.
name|Progress
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
name|ifs
operator|.
name|util
operator|.
name|ProgressWriter
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
name|ifs
operator|.
name|util
operator|.
name|ToolBox
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InteractiveTimetablingTest
block|{
specifier|private
specifier|static
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
name|sDateFormat
init|=
operator|new
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
argument_list|(
literal|"yyMMdd_HHmmss"
argument_list|,
name|java
operator|.
name|util
operator|.
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|java
operator|.
name|text
operator|.
name|DecimalFormat
name|sDoubleFormat
init|=
operator|new
name|java
operator|.
name|text
operator|.
name|DecimalFormat
argument_list|(
literal|"0.000"
argument_list|,
operator|new
name|java
operator|.
name|text
operator|.
name|DecimalFormatSymbols
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
name|sLogger
init|=
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
operator|.
name|getLogger
argument_list|(
name|InteractiveTimetablingTest
operator|.
name|class
argument_list|)
decl_stmt|;
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
name|DataProperties
name|properties
init|=
name|ToolBox
operator|.
name|loadProperties
argument_list|(
operator|new
name|java
operator|.
name|io
operator|.
name|File
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
argument_list|)
decl_stmt|;
name|properties
operator|.
name|putAll
argument_list|(
name|System
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Output"
argument_list|,
name|properties
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|,
literal|"."
argument_list|)
operator|+
name|File
operator|.
name|separator
operator|+
operator|(
name|sDateFormat
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>
literal|1
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Input"
argument_list|,
name|args
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>
literal|2
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Output"
argument_list|,
name|args
index|[
literal|2
index|]
operator|+
name|File
operator|.
name|separator
operator|+
operator|(
name|sDateFormat
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
operator|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Output folder: "
operator|+
name|properties
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|)
argument_list|)
expr_stmt|;
name|ToolBox
operator|.
name|configureLogging
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|)
argument_list|,
name|properties
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|File
name|outDir
init|=
operator|new
name|File
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|,
literal|"."
argument_list|)
argument_list|)
decl_stmt|;
name|outDir
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
name|Solver
name|solver
init|=
operator|new
name|TimetableSolver
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|TimetableModel
name|model
init|=
operator|new
name|TimetableModel
argument_list|(
name|properties
argument_list|)
decl_stmt|;
name|Progress
operator|.
name|getInstance
argument_list|(
name|model
argument_list|)
operator|.
name|addProgressListener
argument_list|(
operator|new
name|ProgressWriter
argument_list|(
name|System
operator|.
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|TimetableLoader
name|loader
init|=
operator|new
name|TimetableXMLLoader
argument_list|(
name|model
argument_list|)
decl_stmt|;
name|loader
operator|.
name|load
argument_list|()
expr_stmt|;
name|solver
operator|.
name|setInitalSolution
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|solver
operator|.
name|initSolver
argument_list|()
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Starting from: "
operator|+
name|ToolBox
operator|.
name|dict2string
argument_list|(
name|model
operator|.
name|getExtendedInfo
argument_list|()
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|PrintWriter
name|csv
init|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
name|outDir
operator|.
name|toString
argument_list|()
operator|+
name|File
operator|.
name|separator
operator|+
literal|"stat.csv"
argument_list|)
argument_list|)
decl_stmt|;
name|csv
operator|.
name|println
argument_list|(
literal|"class,timeout,#sol,#comb,time,best,timeout,#sol,#comb,time,best"
argument_list|)
expr_stmt|;
name|csv
operator|.
name|flush
argument_list|()
expr_stmt|;
name|int
name|depth
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"depth"
argument_list|,
literal|"3"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|nrClasses1
init|=
literal|0
decl_stmt|,
name|nrClasses2
init|=
literal|0
decl_stmt|;
name|double
name|bestValue1
init|=
literal|0
decl_stmt|,
name|bestValue2
init|=
literal|0
decl_stmt|;
name|long
name|combinations1
init|=
literal|0
decl_stmt|,
name|combinations2
init|=
literal|0
decl_stmt|;
name|long
name|solutions1
init|=
literal|0
decl_stmt|,
name|solutions2
init|=
literal|0
decl_stmt|;
name|double
name|time1
init|=
literal|0
decl_stmt|,
name|time2
init|=
literal|0
decl_stmt|;
name|int
name|timeout
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Lecture
name|lect
range|:
name|model
operator|.
name|variables
argument_list|()
control|)
block|{
name|SuggestionsModel
name|m1
init|=
operator|new
name|SuggestionsModel
argument_list|()
decl_stmt|;
name|m1
operator|.
name|setDepth
argument_list|(
name|depth
argument_list|)
expr_stmt|;
name|m1
operator|.
name|setTimeout
argument_list|(
literal|5000
argument_list|)
expr_stmt|;
name|m1
operator|.
name|setClassId
argument_list|(
name|lect
operator|.
name|getClassId
argument_list|()
argument_list|)
expr_stmt|;
name|Suggestions
name|s1
init|=
operator|new
name|Suggestions
argument_list|(
name|solver
argument_list|,
name|m1
argument_list|)
decl_stmt|;
name|long
name|t0
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|s1
operator|.
name|computeSuggestions
argument_list|()
expr_stmt|;
name|long
name|t1
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|csv
operator|.
name|print
argument_list|(
name|lect
operator|.
name|getName
argument_list|()
operator|+
literal|","
operator|+
operator|(
name|s1
operator|.
name|getTimeoutReached
argument_list|()
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|","
operator|+
name|s1
operator|.
name|getNrSolutions
argument_list|()
operator|+
literal|","
operator|+
name|s1
operator|.
name|getNrCombinationsConsidered
argument_list|()
operator|+
literal|","
operator|+
operator|(
name|t1
operator|-
name|t0
operator|)
operator|+
literal|","
operator|+
operator|(
name|s1
operator|.
name|getSuggestions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"-"
else|:
operator|(
operator|(
name|Suggestion
operator|)
name|s1
operator|.
name|getSuggestions
argument_list|()
operator|.
name|first
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
operator|)
operator|+
literal|","
argument_list|)
expr_stmt|;
name|csv
operator|.
name|flush
argument_list|()
expr_stmt|;
name|SuggestionsModel
name|m2
init|=
operator|new
name|SuggestionsModel
argument_list|()
decl_stmt|;
name|m2
operator|.
name|setDepth
argument_list|(
name|m1
operator|.
name|getDepth
argument_list|()
argument_list|)
expr_stmt|;
name|m2
operator|.
name|setTimeout
argument_list|(
literal|360000
argument_list|)
expr_stmt|;
name|m2
operator|.
name|setClassId
argument_list|(
name|lect
operator|.
name|getClassId
argument_list|()
argument_list|)
expr_stmt|;
name|Suggestions
name|s2
init|=
operator|new
name|Suggestions
argument_list|(
name|solver
argument_list|,
name|m2
argument_list|)
decl_stmt|;
name|long
name|t2
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|s2
operator|.
name|computeSuggestions
argument_list|()
expr_stmt|;
name|long
name|t3
init|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
decl_stmt|;
name|combinations1
operator|+=
name|s1
operator|.
name|getNrCombinationsConsidered
argument_list|()
expr_stmt|;
name|combinations2
operator|+=
name|s2
operator|.
name|getNrCombinationsConsidered
argument_list|()
expr_stmt|;
name|time1
operator|+=
operator|(
name|t1
operator|-
name|t0
operator|)
operator|/
literal|1000.0
expr_stmt|;
name|time2
operator|+=
operator|(
name|t3
operator|-
name|t2
operator|)
operator|/
literal|1000.0
expr_stmt|;
name|solutions1
operator|+=
name|s1
operator|.
name|getNrSolutions
argument_list|()
expr_stmt|;
name|solutions2
operator|+=
name|s2
operator|.
name|getNrSolutions
argument_list|()
expr_stmt|;
if|if
condition|(
name|s1
operator|.
name|getTimeoutReached
argument_list|()
condition|)
name|timeout
operator|++
expr_stmt|;
if|if
condition|(
operator|!
name|s1
operator|.
name|getSuggestions
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|s2
operator|.
name|getSuggestions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Suggestion
name|x1
init|=
operator|(
name|Suggestion
operator|)
name|s1
operator|.
name|getSuggestions
argument_list|()
operator|.
name|first
argument_list|()
decl_stmt|;
name|Suggestion
name|x2
init|=
operator|(
name|Suggestion
operator|)
name|s2
operator|.
name|getSuggestions
argument_list|()
operator|.
name|first
argument_list|()
decl_stmt|;
name|bestValue1
operator|+=
name|x1
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|bestValue2
operator|+=
name|x2
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|nrClasses1
operator|++
expr_stmt|;
name|nrClasses2
operator|++
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|s2
operator|.
name|getSuggestions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|nrClasses2
operator|++
expr_stmt|;
block|}
name|csv
operator|.
name|println
argument_list|(
operator|(
name|s2
operator|.
name|getTimeoutReached
argument_list|()
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|","
operator|+
name|s2
operator|.
name|getNrSolutions
argument_list|()
operator|+
literal|","
operator|+
name|s2
operator|.
name|getNrCombinationsConsidered
argument_list|()
operator|+
literal|","
operator|+
operator|(
name|t3
operator|-
name|t2
operator|)
operator|+
literal|","
operator|+
operator|(
name|s2
operator|.
name|getSuggestions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|"-"
else|:
operator|(
operator|(
name|Suggestion
operator|)
name|s2
operator|.
name|getSuggestions
argument_list|()
operator|.
name|first
argument_list|()
operator|)
operator|.
name|getValue
argument_list|()
operator|)
argument_list|)
expr_stmt|;
name|csv
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
name|csv
operator|.
name|close
argument_list|()
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Number of solutions: "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|solutions1
operator|/
name|solutions2
argument_list|)
operator|+
literal|"% ("
operator|+
name|solutions1
operator|+
literal|" of "
operator|+
name|solutions2
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Number of combinations: "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|combinations1
operator|/
name|combinations2
argument_list|)
operator|+
literal|"% ("
operator|+
name|combinations1
operator|+
literal|" of "
operator|+
name|combinations2
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Average time needed: "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
name|time1
operator|/
name|model
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|" (versus "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
name|time2
operator|/
name|model
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Timeout reached: "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|timeout
operator|/
name|model
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|" ("
operator|+
name|timeout
operator|+
literal|"x)"
argument_list|)
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Improvement found: "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|nrClasses1
operator|/
name|model
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|" ("
operator|+
name|nrClasses1
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Improvement found (w/o time limit): "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
literal|100.0
operator|*
name|nrClasses2
operator|/
name|model
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|" ("
operator|+
name|nrClasses2
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Average improvement: "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
name|bestValue1
operator|/
name|nrClasses1
argument_list|)
argument_list|)
expr_stmt|;
name|sLogger
operator|.
name|info
argument_list|(
literal|"Average improvement (w/o time limit): "
operator|+
name|sDoubleFormat
operator|.
name|format
argument_list|(
name|bestValue2
operator|/
name|nrClasses1
argument_list|)
argument_list|)
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

