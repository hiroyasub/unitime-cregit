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
name|FileInputStream
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
name|IOException
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
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|heuristics
operator|.
name|BacktrackNeighbourSelection
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
name|model
operator|.
name|Neighbour
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
name|model
operator|.
name|Value
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
name|model
operator|.
name|Variable
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
name|solution
operator|.
name|Solution
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
name|solution
operator|.
name|SolutionListener
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
name|solver
operator|.
name|SolverListener
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
name|ToolBox
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
name|studentsct
operator|.
name|StudentSectioningModel
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
name|studentsct
operator|.
name|StudentSectioningXMLSaver
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
name|studentsct
operator|.
name|check
operator|.
name|InevitableStudentConflicts
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
name|studentsct
operator|.
name|check
operator|.
name|OverlapCheck
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
name|studentsct
operator|.
name|check
operator|.
name|SectionLimitCheck
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
name|studentsct
operator|.
name|report
operator|.
name|CourseConflictTable
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
name|studentsct
operator|.
name|report
operator|.
name|DistanceConflictTable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Level
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BatchStudentSectioningTest
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|BatchStudentSectioningTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|DecimalFormat
name|sDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"0.000"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|boolean
name|sIncludeCourseDemands
init|=
literal|true
decl_stmt|;
specifier|private
specifier|static
name|boolean
name|sIncludeLastLikeStudents
init|=
literal|true
decl_stmt|;
specifier|private
specifier|static
name|boolean
name|sIncludeUseCommittedAssignments
init|=
literal|false
decl_stmt|;
specifier|public
specifier|static
name|void
name|batchSectioning
parameter_list|(
name|DataProperties
name|cfg
parameter_list|)
block|{
name|StudentSectioningModel
name|model
init|=
operator|new
name|StudentSectioningModel
argument_list|(
name|cfg
argument_list|)
decl_stmt|;
try|try
block|{
operator|new
name|BatchStudentSectioningLoader
argument_list|(
name|model
argument_list|)
operator|.
name|load
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
literal|"Unable to load problem, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return;
block|}
name|Solver
name|solver
init|=
operator|new
name|Solver
argument_list|(
name|cfg
argument_list|)
decl_stmt|;
name|Solution
name|solution
init|=
operator|new
name|Solution
argument_list|(
name|model
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|solver
operator|.
name|setInitalSolution
argument_list|(
name|solution
argument_list|)
expr_stmt|;
name|solver
operator|.
name|addSolverListener
argument_list|(
operator|new
name|SolverListener
argument_list|()
block|{
specifier|public
name|boolean
name|variableSelected
parameter_list|(
name|long
name|iteration
parameter_list|,
name|Variable
name|variable
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|valueSelected
parameter_list|(
name|long
name|iteration
parameter_list|,
name|Variable
name|variable
parameter_list|,
name|Value
name|value
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|neighbourSelected
parameter_list|(
name|long
name|iteration
parameter_list|,
name|Neighbour
name|neighbour
parameter_list|)
block|{
name|sLog
operator|.
name|debug
argument_list|(
literal|"Select["
operator|+
name|iteration
operator|+
literal|"]: "
operator|+
name|neighbour
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|solution
operator|.
name|addSolutionListener
argument_list|(
operator|new
name|SolutionListener
argument_list|()
block|{
specifier|public
name|void
name|solutionUpdated
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
block|}
specifier|public
name|void
name|getInfo
parameter_list|(
name|Solution
name|solution
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Map
name|info
parameter_list|)
block|{
block|}
specifier|public
name|void
name|getInfo
parameter_list|(
name|Solution
name|solution
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Map
name|info
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Collection
name|variables
parameter_list|)
block|{
block|}
specifier|public
name|void
name|bestCleared
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
block|}
specifier|public
name|void
name|bestSaved
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
name|StudentSectioningModel
name|m
init|=
operator|(
name|StudentSectioningModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"**BEST** V:"
operator|+
name|m
operator|.
name|assignedVariables
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|"/"
operator|+
name|m
operator|.
name|variables
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" - S:"
operator|+
name|m
operator|.
name|nrComplete
argument_list|()
operator|+
literal|"/"
operator|+
name|m
operator|.
name|getStudents
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" - TV:"
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|m
operator|.
name|getTotalValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|bestRestored
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
block|}
block|}
argument_list|)
expr_stmt|;
try|try
block|{
operator|new
name|StudentSectioningXMLSaver
argument_list|(
name|solver
argument_list|)
operator|.
name|save
argument_list|(
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|cfg
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|,
literal|"."
argument_list|)
argument_list|)
argument_list|,
literal|"input.xml"
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
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to save input data, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|solver
operator|.
name|start
argument_list|()
expr_stmt|;
try|try
block|{
name|solver
operator|.
name|getSolverThread
argument_list|()
operator|.
name|join
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
block|}
name|solution
operator|=
name|solver
operator|.
name|lastSolution
argument_list|()
expr_stmt|;
name|solution
operator|.
name|restoreBest
argument_list|()
expr_stmt|;
name|model
operator|=
operator|(
name|StudentSectioningModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
expr_stmt|;
try|try
block|{
name|File
name|outDir
init|=
operator|new
name|File
argument_list|(
name|cfg
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
name|CourseConflictTable
name|cct
init|=
operator|new
name|CourseConflictTable
argument_list|(
operator|(
name|StudentSectioningModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
argument_list|)
decl_stmt|;
name|cct
operator|.
name|createTable
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|save
argument_list|(
operator|new
name|File
argument_list|(
name|outDir
argument_list|,
literal|"conflicts-lastlike.csv"
argument_list|)
argument_list|)
expr_stmt|;
name|cct
operator|.
name|createTable
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
operator|.
name|save
argument_list|(
operator|new
name|File
argument_list|(
name|outDir
argument_list|,
literal|"conflicts-real.csv"
argument_list|)
argument_list|)
expr_stmt|;
name|DistanceConflictTable
name|dct
init|=
operator|new
name|DistanceConflictTable
argument_list|(
operator|(
name|StudentSectioningModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
argument_list|)
decl_stmt|;
name|dct
operator|.
name|createTable
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|save
argument_list|(
operator|new
name|File
argument_list|(
name|outDir
argument_list|,
literal|"distances-lastlike.csv"
argument_list|)
argument_list|)
expr_stmt|;
name|dct
operator|.
name|createTable
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
operator|.
name|save
argument_list|(
operator|new
name|File
argument_list|(
name|outDir
argument_list|,
literal|"distances-real.csv"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|cfg
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Test.InevitableStudentConflictsCheck"
argument_list|,
literal|false
argument_list|)
condition|)
block|{
name|InevitableStudentConflicts
name|ch
init|=
operator|new
name|InevitableStudentConflicts
argument_list|(
name|model
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|ch
operator|.
name|check
argument_list|()
condition|)
name|ch
operator|.
name|getCSVFile
argument_list|()
operator|.
name|save
argument_list|(
operator|new
name|File
argument_list|(
name|outDir
argument_list|,
literal|"inevitable-conflicts.csv"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
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
name|solution
operator|.
name|saveBest
argument_list|()
expr_stmt|;
name|model
operator|.
name|computeOnlineSectioningInfos
argument_list|()
expr_stmt|;
operator|new
name|OverlapCheck
argument_list|(
operator|(
name|StudentSectioningModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
argument_list|)
operator|.
name|check
argument_list|()
expr_stmt|;
operator|new
name|SectionLimitCheck
argument_list|(
operator|(
name|StudentSectioningModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
argument_list|)
operator|.
name|check
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Best solution found after "
operator|+
name|solution
operator|.
name|getBestTime
argument_list|()
operator|+
literal|" seconds ("
operator|+
name|solution
operator|.
name|getBestIteration
argument_list|()
operator|+
literal|" iterations)."
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Number of assigned variables is "
operator|+
name|solution
operator|.
name|getModel
argument_list|()
operator|.
name|assignedVariables
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Number of students with complete schedule is "
operator|+
name|model
operator|.
name|nrComplete
argument_list|()
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Total value of the solution is "
operator|+
name|solution
operator|.
name|getModel
argument_list|()
operator|.
name|getTotalValue
argument_list|()
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Average unassigned priority "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|model
operator|.
name|avgUnassignPriority
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Average number of requests "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|model
operator|.
name|avgNrRequests
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Unassigned request weight "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|model
operator|.
name|getUnassignedRequestWeight
argument_list|()
argument_list|)
operator|+
literal|" / "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|model
operator|.
name|getTotalRequestWeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Info: "
operator|+
name|ToolBox
operator|.
name|dict2string
argument_list|(
name|solution
operator|.
name|getExtendedInfo
argument_list|()
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|PrintWriter
name|pw
init|=
literal|null
decl_stmt|;
try|try
block|{
name|pw
operator|=
operator|new
name|PrintWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|cfg
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|,
literal|"."
argument_list|)
argument_list|)
argument_list|,
literal|"info.properties"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|TreeSet
name|entrySet
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|Comparator
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|Map
operator|.
name|Entry
name|e1
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|o1
decl_stmt|;
name|Map
operator|.
name|Entry
name|e2
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|o2
decl_stmt|;
return|return
operator|(
operator|(
name|Comparable
operator|)
name|e1
operator|.
name|getKey
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|getKey
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
name|entrySet
operator|.
name|addAll
argument_list|(
name|solution
operator|.
name|getExtendedInfo
argument_list|()
operator|.
name|entrySet
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|entrySet
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
name|Map
operator|.
name|Entry
name|entry
init|=
operator|(
name|Map
operator|.
name|Entry
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|pw
operator|.
name|println
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'.'
argument_list|)
operator|+
literal|"="
operator|+
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|pw
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to save info, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|pw
operator|!=
literal|null
condition|)
name|pw
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
try|try
block|{
operator|new
name|StudentSectioningXMLSaver
argument_list|(
name|solver
argument_list|)
operator|.
name|save
argument_list|(
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|cfg
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|,
literal|"."
argument_list|)
argument_list|)
argument_list|,
literal|"solution.xml"
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
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to save solution, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
try|try
block|{
operator|new
name|BatchStudentSectioningSaver
argument_list|(
name|solver
argument_list|)
operator|.
name|save
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
literal|"Unable to save solution, reason: "
operator|+
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
name|cfg
init|=
operator|new
name|DataProperties
argument_list|()
decl_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Termination.Class"
argument_list|,
literal|"net.sf.cpsolver.ifs.termination.GeneralTerminationCondition"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Termination.StopWhenComplete"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Termination.TimeOut"
argument_list|,
literal|"600"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Comparator.Class"
argument_list|,
literal|"net.sf.cpsolver.ifs.solution.GeneralSolutionComparator"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Value.Class"
argument_list|,
literal|"net.sf.cpsolver.ifs.heuristics.GeneralValueSelection"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Value.WeightConflicts"
argument_list|,
literal|"1.0"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Value.WeightNrAssignments"
argument_list|,
literal|"0.0"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Variable.Class"
argument_list|,
literal|"net.sf.cpsolver.ifs.heuristics.GeneralVariableSelection"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Neighbour.Class"
argument_list|,
literal|"net.sf.cpsolver.studentsct.heuristics.StudentSctNeighbourSelection"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"General.SaveBestUnassigned"
argument_list|,
literal|"-1"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Extensions.Classes"
argument_list|,
literal|"net.sf.cpsolver.ifs.extension.ConflictStatistics;net.sf.cpsolver.studentsct.extension.DistanceConflict"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Data.Initiative"
argument_list|,
literal|"woebegon"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Data.Term"
argument_list|,
literal|"Fal"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Data.Year"
argument_list|,
literal|"2007"
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Load.IncludeCourseDemands"
argument_list|,
operator|(
name|sIncludeCourseDemands
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Load.IncludeLastLikeStudents"
argument_list|,
operator|(
name|sIncludeLastLikeStudents
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"Load.IncludeUseCommittedAssignments"
argument_list|,
operator|(
name|sIncludeUseCommittedAssignments
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
comment|//cfg.setProperty("Load.MakeupAssignmentsFromRequiredPrefs", "true");
if|if
condition|(
name|args
operator|.
name|length
operator|>=
literal|1
condition|)
block|{
name|cfg
operator|.
name|load
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|cfg
operator|.
name|putAll
argument_list|(
name|System
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>=
literal|2
condition|)
block|{
name|File
name|logFile
init|=
operator|new
name|File
argument_list|(
name|ToolBox
operator|.
name|configureLogging
argument_list|(
name|args
index|[
literal|1
index|]
argument_list|,
name|cfg
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"General.Output"
argument_list|,
name|logFile
operator|.
name|getParentFile
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ToolBox
operator|.
name|configureLogging
argument_list|()
expr_stmt|;
name|cfg
operator|.
name|setProperty
argument_list|(
literal|"General.Output"
argument_list|,
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|,
literal|"."
argument_list|)
operator|+
name|File
operator|.
name|separator
operator|+
literal|"Sectioning-Test"
argument_list|)
expr_stmt|;
block|}
name|Logger
operator|.
name|getLogger
argument_list|(
name|BacktrackNeighbourSelection
operator|.
name|class
argument_list|)
operator|.
name|setLevel
argument_list|(
name|cfg
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Debug.BacktrackNeighbourSelection"
argument_list|,
literal|false
argument_list|)
condition|?
name|Level
operator|.
name|DEBUG
else|:
name|Level
operator|.
name|INFO
argument_list|)
expr_stmt|;
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|cfg
argument_list|)
expr_stmt|;
name|batchSectioning
argument_list|(
name|cfg
argument_list|)
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

