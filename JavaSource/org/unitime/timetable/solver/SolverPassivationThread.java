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
name|solver
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
name|studentsct
operator|.
name|StudentSolverProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverPassivationThread
extends|extends
name|Thread
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
name|SolverPassivationThread
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|File
name|iFolder
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|?
extends|extends
name|SolverProxy
argument_list|>
name|iSolvers
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|?
extends|extends
name|ExamSolverProxy
argument_list|>
name|iExamSolvers
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|?
extends|extends
name|StudentSolverProxy
argument_list|>
name|iStudentSolvers
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|long
name|sDelay
init|=
literal|30000
decl_stmt|;
specifier|public
name|SolverPassivationThread
parameter_list|(
name|File
name|folder
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|?
extends|extends
name|SolverProxy
argument_list|>
name|solvers
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|?
extends|extends
name|ExamSolverProxy
argument_list|>
name|examSolvers
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|?
extends|extends
name|StudentSolverProxy
argument_list|>
name|studentSolvers
parameter_list|)
block|{
name|iFolder
operator|=
name|folder
expr_stmt|;
name|iSolvers
operator|=
name|solvers
expr_stmt|;
name|iExamSolvers
operator|=
name|examSolvers
expr_stmt|;
name|iStudentSolvers
operator|=
name|studentSolvers
expr_stmt|;
name|setName
argument_list|(
literal|"SolverPasivationThread"
argument_list|)
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setPriority
argument_list|(
name|Thread
operator|.
name|MIN_PRIORITY
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Solver passivation thread started."
argument_list|)
expr_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|iSolvers
operator|.
name|entrySet
argument_list|()
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
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|SolverProxy
name|solver
init|=
operator|(
name|SolverProxy
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|solver
operator|.
name|passivateIfNeeded
argument_list|(
name|iFolder
argument_list|,
name|puid
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|iExamSolvers
operator|.
name|entrySet
argument_list|()
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
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|ExamSolverProxy
name|solver
init|=
operator|(
name|ExamSolverProxy
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|solver
operator|.
name|passivateIfNeeded
argument_list|(
name|iFolder
argument_list|,
name|puid
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|iStudentSolvers
operator|.
name|entrySet
argument_list|()
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
name|String
name|puid
init|=
operator|(
name|String
operator|)
name|entry
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|StudentSolverProxy
name|solver
init|=
operator|(
name|StudentSolverProxy
operator|)
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|solver
operator|.
name|passivateIfNeeded
argument_list|(
name|iFolder
argument_list|,
name|puid
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|sleep
argument_list|(
name|sDelay
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
break|break;
block|}
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Solver passivation thread finished."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|print
argument_list|(
literal|"Solver passivation thread failed, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
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

