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
name|solver
package|;
end_package

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
name|StringTokenizer
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
name|Progress
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
name|SolverInterface
operator|.
name|ProgressLogLevel
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
name|SolverInterface
operator|.
name|SolutionLog
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
name|SolverInterface
operator|.
name|SolverLogPageRequest
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
name|SolverInterface
operator|.
name|SolverLogPageResponse
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
name|SolverInterface
operator|.
name|SolverType
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
name|Solution
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
name|SolutionDAO
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|CommonSolverInterface
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
name|instructor
operator|.
name|InstructorSchedulingProxy
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
name|ui
operator|.
name|LogInfo
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
name|BackTracker
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|SolverLogPageRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SolverLogPageBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SolverLogPageRequest
argument_list|,
name|SolverLogPageResponse
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
name|Autowired
name|SolverService
argument_list|<
name|InstructorSchedulingProxy
argument_list|>
name|instructorSchedulingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|SolverLogPageResponse
name|execute
parameter_list|(
name|SolverLogPageRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
switch|switch
condition|(
name|request
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|COURSE
case|:
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|SolverLog
argument_list|)
expr_stmt|;
break|break;
case|case
name|EXAM
case|:
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ExaminationSolverLog
argument_list|)
expr_stmt|;
break|break;
case|case
name|STUDENT
case|:
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|StudentSectioningSolverLog
argument_list|)
expr_stmt|;
break|break;
case|case
name|INSTRUCTOR
case|:
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructorSchedulingSolverLog
argument_list|)
expr_stmt|;
break|break;
block|}
name|ProgressLogLevel
name|level
init|=
name|request
operator|.
name|getLevel
argument_list|()
decl_stmt|;
if|if
condition|(
name|level
operator|==
literal|null
condition|)
name|level
operator|=
name|ProgressLogLevel
operator|.
name|INFO
expr_stmt|;
name|SolverService
argument_list|<
name|?
extends|extends
name|CommonSolverInterface
argument_list|>
name|service
init|=
name|getSolverService
argument_list|(
name|request
operator|.
name|getType
argument_list|()
argument_list|)
decl_stmt|;
name|CommonSolverInterface
name|solver
init|=
name|service
operator|.
name|getSolver
argument_list|()
decl_stmt|;
name|SolverLogPageResponse
name|response
init|=
operator|new
name|SolverLogPageResponse
argument_list|(
name|level
argument_list|)
decl_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getType
argument_list|()
operator|==
name|SolverType
operator|.
name|COURSE
condition|)
block|{
name|String
name|solutionIdsStr
init|=
operator|(
name|String
operator|)
name|context
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|SelectedSolution
argument_list|)
decl_stmt|;
if|if
condition|(
name|solutionIdsStr
operator|!=
literal|null
operator|&&
operator|!
name|solutionIdsStr
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|solutionIdsStr
argument_list|,
literal|","
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|Solution
name|solution
init|=
name|SolutionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|s
operator|.
name|nextToken
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|solution
operator|!=
literal|null
condition|)
block|{
name|LogInfo
name|log
init|=
operator|(
name|LogInfo
operator|)
name|solution
operator|.
name|getInfo
argument_list|(
literal|"LogInfo"
argument_list|)
decl_stmt|;
if|if
condition|(
name|log
operator|!=
literal|null
condition|)
block|{
name|SolutionLog
name|sl
init|=
operator|new
name|SolutionLog
argument_list|(
name|solution
operator|.
name|getOwner
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Progress
operator|.
name|Message
name|m
range|:
name|log
operator|.
name|getLog
argument_list|()
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getLevel
argument_list|()
operator|>=
name|level
operator|.
name|ordinal
argument_list|()
condition|)
block|{
name|sl
operator|.
name|addMessage
argument_list|(
name|m
operator|.
name|getLevel
argument_list|()
argument_list|,
name|m
operator|.
name|getDate
argument_list|()
argument_list|,
name|m
operator|.
name|getMessage
argument_list|()
argument_list|,
name|m
operator|.
name|getTrace
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|response
operator|.
name|addSolutionLog
argument_list|(
name|sl
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|response
return|;
block|}
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|warnSolverNotStartedSolutionNotSelected
argument_list|()
argument_list|)
throw|;
block|}
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|warnSolverNotStarted
argument_list|()
argument_list|)
throw|;
block|}
name|List
argument_list|<
name|Progress
operator|.
name|Message
argument_list|>
name|log
init|=
name|solver
operator|.
name|getProgressLog
argument_list|(
name|level
operator|.
name|ordinal
argument_list|()
argument_list|,
literal|null
argument_list|,
name|request
operator|.
name|getLastDate
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|log
operator|!=
literal|null
condition|)
for|for
control|(
name|Progress
operator|.
name|Message
name|m
range|:
name|log
control|)
name|response
operator|.
name|addMessage
argument_list|(
name|m
operator|.
name|getLevel
argument_list|()
argument_list|,
name|m
operator|.
name|getDate
argument_list|()
argument_list|,
name|m
operator|.
name|getMessage
argument_list|()
argument_list|,
name|m
operator|.
name|getTrace
argument_list|()
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|request
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|COURSE
case|:
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|context
argument_list|,
literal|"gwt.jsp?page=solverlog&type=course"
argument_list|,
name|MESSAGES
operator|.
name|pageCourseTimetablingSolverLog
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|EXAM
case|:
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|context
argument_list|,
literal|"gwt.jsp?page=solverlog&type=exam"
argument_list|,
name|MESSAGES
operator|.
name|pageExaminationTimetablingSolverLog
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|INSTRUCTOR
case|:
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|context
argument_list|,
literal|"gwt.jsp?page=solverlog&type=instructor"
argument_list|,
name|MESSAGES
operator|.
name|pageInstructorSchedulingSolverLog
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|STUDENT
case|:
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|context
argument_list|,
literal|"gwt.jsp?page=solverlog&type=student"
argument_list|,
name|MESSAGES
operator|.
name|pageStudentSchedulingSolverLog
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
block|}
return|return
name|response
return|;
block|}
specifier|protected
name|SolverService
argument_list|<
name|?
extends|extends
name|CommonSolverInterface
argument_list|>
name|getSolverService
parameter_list|(
name|SolverType
name|type
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|COURSE
case|:
return|return
name|courseTimetablingSolverService
return|;
case|case
name|EXAM
case|:
return|return
name|examinationSolverService
return|;
case|case
name|STUDENT
case|:
return|return
name|studentSectioningSolverService
return|;
case|case
name|INSTRUCTOR
case|:
return|return
name|instructorSchedulingSolverService
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
name|MESSAGES
operator|.
name|errorSolverInvalidType
argument_list|(
name|type
operator|.
name|name
argument_list|()
argument_list|)
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

