begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|exam
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
name|FileOutputStream
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|Test
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
name|dom4j
operator|.
name|io
operator|.
name|OutputFormat
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|io
operator|.
name|XMLWriter
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
name|ExamTest
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
name|ExamTest
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
class|class
name|ShutdownHook
extends|extends
name|Thread
block|{
name|Solver
name|iSolver
init|=
literal|null
decl_stmt|;
specifier|public
name|ShutdownHook
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|setName
argument_list|(
literal|"ShutdownHook"
argument_list|)
expr_stmt|;
name|iSolver
operator|=
name|solver
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|iSolver
operator|.
name|isRunning
argument_list|()
condition|)
name|iSolver
operator|.
name|stopSolver
argument_list|()
expr_stmt|;
name|Solution
name|solution
init|=
name|iSolver
operator|.
name|lastSolution
argument_list|()
decl_stmt|;
if|if
condition|(
name|solution
operator|.
name|getBestInfo
argument_list|()
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"No best solution found."
argument_list|)
expr_stmt|;
block|}
else|else
name|solution
operator|.
name|restoreBest
argument_list|()
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Best solution:"
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
literal|1
argument_list|)
argument_list|)
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
name|nrAssignedVariables
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
if|if
condition|(
name|iSolver
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyBoolean
argument_list|(
literal|"General.Save"
argument_list|,
literal|false
argument_list|)
condition|)
operator|new
name|ExamDatabaseSaver
argument_list|(
name|iSolver
argument_list|)
operator|.
name|save
argument_list|()
expr_stmt|;
name|File
name|outFile
init|=
operator|new
name|File
argument_list|(
name|iSolver
operator|.
name|getProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"General.OutputFile"
argument_list|,
name|iSolver
operator|.
name|getProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"General.Output"
argument_list|)
operator|+
name|File
operator|.
name|separator
operator|+
literal|"solution.xml"
argument_list|)
argument_list|)
decl_stmt|;
name|FileOutputStream
name|fos
init|=
operator|new
name|FileOutputStream
argument_list|(
name|outFile
argument_list|)
decl_stmt|;
operator|(
operator|new
name|XMLWriter
argument_list|(
name|fos
argument_list|,
name|OutputFormat
operator|.
name|createPrettyPrint
argument_list|()
argument_list|)
operator|)
operator|.
name|write
argument_list|(
operator|(
operator|(
name|ExamModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|save
argument_list|()
argument_list|)
expr_stmt|;
name|fos
operator|.
name|flush
argument_list|()
expr_stmt|;
name|fos
operator|.
name|close
argument_list|()
expr_stmt|;
name|Test
operator|.
name|createReports
argument_list|(
operator|(
name|ExamModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
argument_list|,
name|outFile
operator|.
name|getParentFile
argument_list|()
argument_list|,
name|outFile
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|outFile
operator|.
name|getName
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Progress
operator|.
name|removeInstance
argument_list|(
name|solution
operator|.
name|getModel
argument_list|()
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
literal|"Exam-Test"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
literal|"true"
operator|.
name|equals
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"debug"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
condition|)
name|Logger
operator|.
name|getRootLogger
argument_list|()
operator|.
name|setLevel
argument_list|(
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
name|ExamModel
name|model
init|=
operator|new
name|ExamModel
argument_list|(
name|cfg
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
try|try
block|{
operator|new
name|ExamDatabaseLoader
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
name|solver
operator|.
name|setInitalSolution
argument_list|(
operator|new
name|Solution
argument_list|(
name|model
argument_list|)
argument_list|)
expr_stmt|;
name|solver
operator|.
name|currentSolution
argument_list|()
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
name|Dictionary
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
name|Dictionary
name|info
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Vector
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
name|ExamModel
name|m
init|=
operator|(
name|ExamModel
operator|)
name|solution
operator|.
name|getModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|sLog
operator|.
name|isInfoEnabled
argument_list|()
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"**BEST["
operator|+
name|solution
operator|.
name|getIteration
argument_list|()
operator|+
literal|"]** "
operator|+
operator|(
name|m
operator|.
name|nrUnassignedVariables
argument_list|()
operator|>
literal|0
condition|?
literal|"V:"
operator|+
name|m
operator|.
name|nrAssignedVariables
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
literal|" - "
else|:
literal|""
operator|)
operator|+
literal|"T:"
operator|+
operator|new
name|DecimalFormat
argument_list|(
literal|"0.00"
argument_list|)
operator|.
name|format
argument_list|(
name|m
operator|.
name|getTotalValue
argument_list|()
argument_list|)
operator|+
literal|" ("
operator|+
name|m
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
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
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|addShutdownHook
argument_list|(
operator|new
name|ShutdownHook
argument_list|(
name|solver
argument_list|)
argument_list|)
expr_stmt|;
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

