begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
operator|.
name|menu
package|;
end_package

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
name|form
operator|.
name|ListSolutionsForm
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcLogging
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
name|GwtRpcLogging
operator|.
name|Level
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
name|GwtMessages
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
name|MenuInterface
operator|.
name|InfoPairInterface
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
name|MenuInterface
operator|.
name|SolverInfoInterface
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
name|MenuInterface
operator|.
name|SolverInfoRpcRequest
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
name|SolverGroup
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
name|TimetableManager
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
name|ExamTypeDAO
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
name|model
operator|.
name|dao
operator|.
name|SolverGroupDAO
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
name|SolverProxy
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
name|service
operator|.
name|SolverService
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
name|studentsct
operator|.
name|StudentSolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|SolverInfoRpcRequest
operator|.
name|class
argument_list|)
annotation|@
name|GwtRpcLogging
argument_list|(
name|Level
operator|.
name|DISABLED
argument_list|)
specifier|public
class|class
name|SolverInfoBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SolverInfoRpcRequest
argument_list|,
name|SolverInfoInterface
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
name|examinationSolverService
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|StudentSolverProxy
argument_list|>
name|studentSectioningSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|SolverInfoInterface
name|execute
parameter_list|(
name|SolverInfoRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|SolverInfoInterface
name|ret
init|=
operator|new
name|SolverInfoInterface
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
try|try
block|{
name|SolverProxy
name|solver
init|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
name|ExamSolverProxy
name|examSolver
init|=
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
name|StudentSolverProxy
name|studentSolver
init|=
name|studentSectioningSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
name|Map
name|progress
init|=
operator|(
name|studentSolver
operator|!=
literal|null
condition|?
name|studentSolver
operator|.
name|getProgress
argument_list|()
else|:
name|examSolver
operator|!=
literal|null
condition|?
name|examSolver
operator|.
name|getProgress
argument_list|()
else|:
name|solver
operator|!=
literal|null
condition|?
name|solver
operator|.
name|getProgress
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|progress
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|DataProperties
name|properties
init|=
operator|(
name|studentSolver
operator|!=
literal|null
condition|?
name|studentSolver
operator|.
name|getProperties
argument_list|()
else|:
name|examSolver
operator|!=
literal|null
condition|?
name|examSolver
operator|.
name|getProperties
argument_list|()
else|:
name|solver
operator|.
name|getProperties
argument_list|()
operator|)
decl_stmt|;
name|String
name|progressStatus
init|=
operator|(
name|String
operator|)
name|progress
operator|.
name|get
argument_list|(
literal|"STATUS"
argument_list|)
decl_stmt|;
name|String
name|progressPhase
init|=
operator|(
name|String
operator|)
name|progress
operator|.
name|get
argument_list|(
literal|"PHASE"
argument_list|)
decl_stmt|;
name|long
name|progressCur
init|=
operator|(
operator|(
name|Long
operator|)
name|progress
operator|.
name|get
argument_list|(
literal|"PROGRESS"
argument_list|)
operator|)
operator|.
name|longValue
argument_list|()
decl_stmt|;
name|long
name|progressMax
init|=
operator|(
operator|(
name|Long
operator|)
name|progress
operator|.
name|get
argument_list|(
literal|"MAX_PROGRESS"
argument_list|)
operator|)
operator|.
name|longValue
argument_list|()
decl_stmt|;
name|String
name|version
init|=
operator|(
name|String
operator|)
name|progress
operator|.
name|get
argument_list|(
literal|"VERSION"
argument_list|)
decl_stmt|;
if|if
condition|(
name|version
operator|==
literal|null
operator|||
literal|"-1"
operator|.
name|equals
argument_list|(
name|version
argument_list|)
condition|)
name|version
operator|=
literal|"N/A"
expr_stmt|;
name|double
name|progressPercent
init|=
literal|100.0
operator|*
operator|(
operator|(
name|double
operator|)
operator|(
name|progressCur
operator|<
name|progressMax
condition|?
name|progressCur
else|:
name|progressMax
operator|)
operator|)
operator|/
operator|(
operator|(
name|double
operator|)
name|progressMax
operator|)
decl_stmt|;
name|String
name|runnerName
init|=
name|getName
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"General.OwnerPuid"
argument_list|,
literal|"N/A"
argument_list|)
argument_list|)
decl_stmt|;
name|Long
index|[]
name|solverGroupId
init|=
name|properties
operator|.
name|getPropertyLongArry
argument_list|(
literal|"General.SolverGroupId"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|String
name|ownerName
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|solverGroupId
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|solverGroupId
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
name|ownerName
operator|+=
literal|"& "
expr_stmt|;
name|ownerName
operator|+=
name|getName
argument_list|(
operator|(
operator|new
name|SolverGroupDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|solverGroupId
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|examSolver
operator|!=
literal|null
condition|)
name|ownerName
operator|=
name|ExamTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|examSolver
operator|.
name|getExamTypeId
argument_list|()
argument_list|)
operator|.
name|getLabel
argument_list|()
expr_stmt|;
if|if
condition|(
name|ownerName
operator|==
literal|null
operator|||
name|ownerName
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|ownerName
operator|=
literal|"N/A"
expr_stmt|;
if|if
condition|(
name|ownerName
operator|.
name|equals
argument_list|(
literal|"N/A"
argument_list|)
condition|)
name|ownerName
operator|=
name|runnerName
expr_stmt|;
if|if
condition|(
name|runnerName
operator|.
name|equals
argument_list|(
literal|"N/A"
argument_list|)
condition|)
name|runnerName
operator|=
name|ownerName
expr_stmt|;
if|if
condition|(
operator|!
name|ownerName
operator|.
name|equals
argument_list|(
name|runnerName
argument_list|)
condition|)
name|ownerName
operator|=
name|runnerName
operator|+
literal|" as "
operator|+
name|ownerName
expr_stmt|;
if|if
condition|(
name|ownerName
operator|.
name|length
argument_list|()
operator|>
literal|50
condition|)
name|ownerName
operator|=
name|ownerName
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|47
argument_list|)
operator|+
literal|"..."
expr_stmt|;
name|ret
operator|.
name|setType
argument_list|(
name|studentSolver
operator|!=
literal|null
condition|?
name|MESSAGES
operator|.
name|solverStudent
argument_list|()
else|:
name|examSolver
operator|!=
literal|null
condition|?
name|MESSAGES
operator|.
name|solverExamination
argument_list|()
else|:
name|MESSAGES
operator|.
name|solverCourse
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldType
argument_list|()
argument_list|,
name|ret
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setUrl
argument_list|(
name|studentSolver
operator|!=
literal|null
condition|?
literal|"studentSolver.do"
else|:
name|examSolver
operator|!=
literal|null
condition|?
literal|"examSolver.do"
else|:
literal|"solver.do"
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldSolver
argument_list|()
argument_list|,
name|progressStatus
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setSolver
argument_list|(
name|progressStatus
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldPhase
argument_list|()
argument_list|,
name|progressPhase
argument_list|)
expr_stmt|;
if|if
condition|(
name|progressMax
operator|>
literal|0
condition|)
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldProgress
argument_list|()
argument_list|,
operator|(
name|progressCur
operator|<
name|progressMax
condition|?
name|progressCur
else|:
name|progressMax
operator|)
operator|+
literal|" of "
operator|+
name|progressMax
operator|+
literal|" ("
operator|+
operator|new
name|DecimalFormat
argument_list|(
literal|"0.0"
argument_list|)
operator|.
name|format
argument_list|(
name|progressPercent
argument_list|)
operator|+
literal|"%)"
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldOwner
argument_list|()
argument_list|,
name|ownerName
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldHost
argument_list|()
argument_list|,
operator|(
name|studentSolver
operator|!=
literal|null
condition|?
name|studentSolver
operator|.
name|getHost
argument_list|()
else|:
name|examSolver
operator|!=
literal|null
condition|?
name|examSolver
operator|.
name|getHost
argument_list|()
else|:
name|solver
operator|.
name|getHost
argument_list|()
operator|)
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldSession
argument_list|()
argument_list|,
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|properties
operator|.
name|getPropertyLong
argument_list|(
literal|"General.SessionId"
argument_list|,
literal|null
argument_list|)
argument_list|)
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|InfoPairInterface
name|p
init|=
name|ret
operator|.
name|addPair
argument_list|(
name|MESSAGES
operator|.
name|fieldVersion
argument_list|()
argument_list|,
name|version
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|isIncludeSolutionInfo
argument_list|()
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|info
init|=
literal|null
decl_stmt|;
try|try
block|{
if|if
condition|(
name|studentSolver
operator|!=
literal|null
condition|)
block|{
name|info
operator|=
name|studentSolver
operator|.
name|statusSolutionInfo
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|examSolver
operator|!=
literal|null
condition|)
block|{
name|info
operator|=
name|examSolver
operator|.
name|statusSolutionInfo
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
name|info
operator|=
name|solver
operator|.
name|statusSolutionInfo
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|info
operator|!=
literal|null
operator|&&
operator|!
name|info
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|p
operator|.
name|setSeparator
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|String
argument_list|>
name|keys
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
operator|new
name|ListSolutionsForm
operator|.
name|InfoComparator
argument_list|()
argument_list|)
decl_stmt|;
name|keys
operator|.
name|addAll
argument_list|(
name|info
operator|.
name|keySet
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|key
range|:
name|keys
control|)
name|ret
operator|.
name|addPair
argument_list|(
name|key
argument_list|,
operator|(
name|String
operator|)
name|info
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|private
name|String
name|getName
parameter_list|(
name|String
name|puid
parameter_list|)
block|{
return|return
name|getName
argument_list|(
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|puid
argument_list|)
argument_list|)
return|;
block|}
specifier|private
name|String
name|getName
parameter_list|(
name|TimetableManager
name|mgr
parameter_list|)
block|{
if|if
condition|(
name|mgr
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|mgr
operator|.
name|getShortName
argument_list|()
return|;
block|}
specifier|private
name|String
name|getName
parameter_list|(
name|SolverGroup
name|gr
parameter_list|)
block|{
if|if
condition|(
name|gr
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|gr
operator|.
name|getAbbv
argument_list|()
return|;
block|}
block|}
end_class

end_unit

