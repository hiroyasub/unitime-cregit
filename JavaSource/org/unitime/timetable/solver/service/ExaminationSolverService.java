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
name|solver
operator|.
name|service
package|;
end_package

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
name|List
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
name|cpsolver
operator|.
name|ifs
operator|.
name|extension
operator|.
name|ConflictStatistics
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|ApplicationProperties
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
name|defaults
operator|.
name|ApplicationProperty
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
name|defaults
operator|.
name|SessionAttribute
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
name|defaults
operator|.
name|UserProperty
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
name|resources
operator|.
name|GwtConstants
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
name|SolverParameter
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
name|SolverParameterDef
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
name|SolverParameterGroup
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
name|SolverPredefinedSetting
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
name|SolverPredefinedSettingDAO
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
name|solver
operator|.
name|jgroups
operator|.
name|RemoteSolver
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
name|jgroups
operator|.
name|SolverContainer
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"examinationSolverService"
argument_list|)
specifier|public
class|class
name|ExaminationSolverService
implements|implements
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
block|{
specifier|protected
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ExaminationSolverService
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
name|SolverServerService
name|solverServerService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|DataProperties
name|createConfig
parameter_list|(
name|Long
name|settingsId
parameter_list|,
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|options
parameter_list|)
block|{
name|DataProperties
name|properties
init|=
operator|new
name|DataProperties
argument_list|()
decl_stmt|;
comment|// Load properties
for|for
control|(
name|SolverParameterDef
name|def
range|:
operator|(
name|List
argument_list|<
name|SolverParameterDef
argument_list|>
operator|)
name|SolverPredefinedSettingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from SolverParameterDef where group.type = :type"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|SolverParameterGroup
operator|.
name|sTypeExam
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|def
operator|.
name|getDefault
argument_list|()
operator|!=
literal|null
condition|)
name|properties
operator|.
name|put
argument_list|(
name|def
operator|.
name|getName
argument_list|()
argument_list|,
name|def
operator|.
name|getDefault
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|options
operator|!=
literal|null
operator|&&
name|options
operator|.
name|containsKey
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
name|properties
operator|.
name|put
argument_list|(
name|def
operator|.
name|getName
argument_list|()
argument_list|,
name|options
operator|.
name|get
argument_list|(
name|def
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|SolverPredefinedSetting
name|settings
init|=
name|SolverPredefinedSettingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|settingsId
argument_list|)
decl_stmt|;
for|for
control|(
name|SolverParameter
name|param
range|:
name|settings
operator|.
name|getParameters
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|isVisible
argument_list|()
operator|||
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getGroup
argument_list|()
operator|.
name|getType
argument_list|()
operator|!=
name|SolverParameterGroup
operator|.
name|sTypeExam
condition|)
continue|continue;
name|properties
operator|.
name|put
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|param
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|options
operator|!=
literal|null
operator|&&
name|options
operator|.
name|containsKey
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
name|properties
operator|.
name|put
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|options
operator|.
name|get
argument_list|(
name|param
operator|.
name|getDefinition
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.SettingsId"
argument_list|,
name|settings
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|// Generate extensions
name|String
name|ext
init|=
name|properties
operator|.
name|getProperty
argument_list|(
literal|"Extensions.Classes"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
if|if
condition|(
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"ExamGeneral.CBS"
argument_list|,
literal|true
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|ext
operator|.
name|isEmpty
argument_list|()
condition|)
name|ext
operator|+=
literal|";"
expr_stmt|;
name|ext
operator|+=
name|ConflictStatistics
operator|.
name|class
operator|.
name|getName
argument_list|()
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"ConflictStatistics.Print"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
name|String
name|mode
init|=
name|properties
operator|.
name|getProperty
argument_list|(
literal|"ExamBasic.Mode"
argument_list|,
literal|"Initial"
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"MPP"
operator|.
name|equals
argument_list|(
name|mode
argument_list|)
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.MPP"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"Extensions.Classes"
argument_list|,
name|ext
argument_list|)
expr_stmt|;
comment|// Interactive mode?
if|if
condition|(
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Basic.DisobeyHard"
argument_list|,
literal|false
argument_list|)
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.InteractiveMode"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
comment|// When finished?
if|if
condition|(
literal|"No Action"
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"ExamBasic.WhenFinished"
argument_list|)
argument_list|)
condition|)
block|{
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Save"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.CreateNewSolution"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Unload"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"Save"
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"ExamBasic.WhenFinished"
argument_list|)
argument_list|)
condition|)
block|{
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Save"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.CreateNewSolution"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Unload"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
literal|"Save and Unload"
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"ExamBasic.WhenFinished"
argument_list|)
argument_list|)
condition|)
block|{
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Save"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.CreateNewSolution"
argument_list|,
literal|"false"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.Unload"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
comment|// XML save/load properties
name|properties
operator|.
name|setProperty
argument_list|(
literal|"Xml.ShowNames"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"Exam.GreatDeluge"
argument_list|,
operator|(
literal|"Great Deluge"
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"Exam.Algorithm"
argument_list|,
literal|"Great Deluge"
argument_list|)
argument_list|)
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"Search.GreatDeluge"
argument_list|,
operator|(
literal|"Great Deluge"
operator|.
name|equals
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"Exam.Algorithm"
argument_list|,
literal|"Great Deluge"
argument_list|)
argument_list|)
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
comment|// Distances Matrics
if|if
condition|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"Distances.Ellipsoid"
argument_list|)
operator|==
literal|null
operator|||
name|properties
operator|.
name|getProperty
argument_list|(
literal|"Distances.Ellipsoid"
argument_list|)
operator|.
name|equals
argument_list|(
literal|"DEFAULT"
argument_list|)
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
literal|"Distances.Ellipsoid"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
name|ApplicationProperty
operator|.
name|DistanceEllipsoid
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"Parallel.NrSolvers"
argument_list|)
operator|==
literal|null
condition|)
block|{
name|properties
operator|.
name|setProperty
argument_list|(
literal|"Parallel.NrSolvers"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Math
operator|.
name|max
argument_list|(
literal|1
argument_list|,
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|availableProcessors
argument_list|()
operator|/
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.UseAmPm"
argument_list|,
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
name|properties
operator|.
name|expand
argument_list|()
expr_stmt|;
return|return
name|properties
return|;
block|}
annotation|@
name|Override
specifier|public
name|ExamSolverProxy
name|createSolver
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
operator|!
name|sessionContext
operator|.
name|isAuthenticated
argument_list|()
operator|||
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|removeSolver
argument_list|()
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.SessionId"
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.OwnerPuid"
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.StartTime"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
operator|(
operator|new
name|Date
argument_list|()
operator|)
operator|.
name|getTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|host
init|=
name|properties
operator|.
name|getProperty
argument_list|(
literal|"General.Host"
argument_list|)
decl_stmt|;
name|String
name|instructorFormat
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|NameFormat
argument_list|)
decl_stmt|;
if|if
condition|(
name|instructorFormat
operator|!=
literal|null
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.InstructorFormat"
argument_list|,
name|instructorFormat
argument_list|)
expr_stmt|;
name|ExamSolverProxy
name|solver
init|=
name|solverServerService
operator|.
name|createExamSolver
argument_list|(
name|host
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|,
name|properties
argument_list|)
decl_stmt|;
name|solver
operator|.
name|load
argument_list|(
name|properties
argument_list|)
expr_stmt|;
return|return
name|solver
return|;
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
literal|"Failed to start the solver: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|(
name|e
operator|instanceof
name|RuntimeException
condition|?
operator|(
name|RuntimeException
operator|)
name|e
else|:
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
operator|)
throw|;
block|}
block|}
specifier|public
name|ExamSolverProxy
name|getSolver
parameter_list|(
name|String
name|puid
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
try|try
block|{
name|ExamSolverProxy
name|proxy
init|=
name|solverServerService
operator|.
name|getExamSolverContainer
argument_list|()
operator|.
name|getSolver
argument_list|(
name|puid
argument_list|)
decl_stmt|;
if|if
condition|(
name|proxy
operator|==
literal|null
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
operator|&&
operator|!
name|sessionId
operator|.
name|equals
argument_list|(
name|proxy
operator|.
name|getProperties
argument_list|()
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SessionId"
argument_list|,
literal|null
argument_list|)
argument_list|)
condition|)
return|return
literal|null
return|;
return|return
name|proxy
return|;
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
literal|"Unable to retrieve solver, reason:"
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
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|ExamSolverProxy
name|getSolver
parameter_list|()
block|{
name|ProxyHolder
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
name|h
init|=
operator|(
name|ProxyHolder
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationSolver
argument_list|)
decl_stmt|;
name|ExamSolverProxy
name|solver
init|=
operator|(
name|h
operator|!=
literal|null
operator|&&
name|h
operator|.
name|isValid
argument_list|()
condition|?
name|h
operator|.
name|getProxy
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
try|try
block|{
if|if
condition|(
name|solver
operator|instanceof
name|RemoteSolver
operator|&&
operator|(
operator|(
name|RemoteSolver
operator|)
name|solver
operator|)
operator|.
name|exists
argument_list|()
condition|)
return|return
operator|(
name|ExamSolverProxy
operator|)
name|solver
return|;
else|else
name|sessionContext
operator|.
name|removeAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationSolver
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sessionContext
operator|.
name|removeAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationSolver
argument_list|)
expr_stmt|;
block|}
empty_stmt|;
block|}
if|if
condition|(
operator|!
name|sessionContext
operator|.
name|isAuthenticated
argument_list|()
condition|)
return|return
literal|null
return|;
name|Long
name|sessionId
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationUser
argument_list|)
decl_stmt|;
if|if
condition|(
name|puid
operator|!=
literal|null
condition|)
block|{
name|solver
operator|=
name|getSolver
argument_list|(
name|puid
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
name|sessionContext
operator|.
name|setAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationSolver
argument_list|,
operator|new
name|ProxyHolder
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
argument_list|(
name|puid
argument_list|,
name|solver
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|solver
return|;
block|}
block|}
name|solver
operator|=
name|getSolver
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
name|sessionContext
operator|.
name|setAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationSolver
argument_list|,
operator|new
name|ProxyHolder
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|,
name|solver
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|solver
return|;
block|}
annotation|@
name|Override
specifier|public
name|ExamSolverProxy
name|getSolverNoSessionCheck
parameter_list|()
block|{
if|if
condition|(
operator|!
name|sessionContext
operator|.
name|isAuthenticated
argument_list|()
condition|)
return|return
literal|null
return|;
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationUser
argument_list|)
decl_stmt|;
if|if
condition|(
name|puid
operator|!=
literal|null
condition|)
block|{
name|ExamSolverProxy
name|solver
init|=
name|getSolver
argument_list|(
name|puid
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
return|return
name|solver
return|;
block|}
return|return
name|getSolver
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalUserId
argument_list|()
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeSolver
parameter_list|()
block|{
try|try
block|{
name|sessionContext
operator|.
name|removeAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationSolver
argument_list|)
expr_stmt|;
name|ExamSolverProxy
name|solver
init|=
name|getSolverNoSessionCheck
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
name|solver
operator|.
name|interrupt
argument_list|()
expr_stmt|;
name|solver
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
name|sessionContext
operator|.
name|removeAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ExaminationUser
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
name|warn
argument_list|(
literal|"Failed to remove a solver: "
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
annotation|@
name|Override
specifier|public
name|ExamSolverProxy
name|reload
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
block|{
try|try
block|{
name|ExamSolverProxy
name|solver
init|=
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
return|return
name|createSolver
argument_list|(
name|properties
argument_list|)
return|;
name|DataProperties
name|oldProperties
init|=
name|solver
operator|.
name|getProperties
argument_list|()
decl_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.SessionId"
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"Exam.Type"
argument_list|,
name|oldProperties
operator|.
name|getProperty
argument_list|(
literal|"Exam.Type"
argument_list|)
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.OwnerPuid"
argument_list|,
name|oldProperties
operator|.
name|getProperty
argument_list|(
literal|"General.OwnerPuid"
argument_list|)
argument_list|)
expr_stmt|;
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.StartTime"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
operator|(
operator|new
name|Date
argument_list|()
operator|)
operator|.
name|getTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|instructorFormat
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|NameFormat
argument_list|)
decl_stmt|;
if|if
condition|(
name|instructorFormat
operator|!=
literal|null
condition|)
name|properties
operator|.
name|setProperty
argument_list|(
literal|"General.InstructorFormat"
argument_list|,
name|instructorFormat
argument_list|)
expr_stmt|;
name|solver
operator|.
name|reload
argument_list|(
name|properties
argument_list|)
expr_stmt|;
return|return
name|solver
return|;
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
literal|"Failed to reload the solver: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|(
name|e
operator|instanceof
name|RuntimeException
condition|?
operator|(
name|RuntimeException
operator|)
name|e
else|:
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
operator|)
throw|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
name|getSolvers
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
name|solvers
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
argument_list|()
decl_stmt|;
name|SolverContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
name|container
init|=
name|solverServerService
operator|.
name|getExamSolverContainer
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|user
range|:
name|container
operator|.
name|getSolvers
argument_list|()
control|)
name|solvers
operator|.
name|put
argument_list|(
name|user
argument_list|,
name|container
operator|.
name|getSolver
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|solvers
return|;
block|}
annotation|@
name|Override
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
name|getLocalSolvers
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
name|solvers
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|ExamSolverProxy
argument_list|>
argument_list|()
decl_stmt|;
name|SolverContainer
argument_list|<
name|ExamSolverProxy
argument_list|>
name|container
init|=
name|solverServerService
operator|.
name|getLocalServer
argument_list|()
operator|.
name|getExamSolverContainer
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|user
range|:
name|container
operator|.
name|getSolvers
argument_list|()
control|)
name|solvers
operator|.
name|put
argument_list|(
name|user
argument_list|,
name|container
operator|.
name|getSolver
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|solvers
return|;
block|}
block|}
end_class

end_unit

