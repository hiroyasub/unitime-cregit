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
operator|.
name|curricula
operator|.
name|students
package|;
end_package

begin_import
import|import
name|org
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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|termination
operator|.
name|GeneralTerminationCondition
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
name|termination
operator|.
name|TerminationCondition
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CurTermination
implements|implements
name|TerminationCondition
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
block|{
specifier|protected
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
name|GeneralTerminationCondition
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|int
name|iMaxIter
decl_stmt|;
specifier|private
name|double
name|iTimeOut
decl_stmt|;
specifier|private
name|boolean
name|iStopWhenComplete
decl_stmt|;
specifier|private
name|long
name|iMaxIdle
decl_stmt|;
specifier|public
name|CurTermination
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
block|{
name|iMaxIter
operator|=
name|properties
operator|.
name|getPropertyInt
argument_list|(
literal|"Termination.MaxIters"
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iTimeOut
operator|=
name|properties
operator|.
name|getPropertyDouble
argument_list|(
literal|"Termination.TimeOut"
argument_list|,
operator|-
literal|1.0
argument_list|)
expr_stmt|;
name|iStopWhenComplete
operator|=
name|properties
operator|.
name|getPropertyBoolean
argument_list|(
literal|"Termination.StopWhenComplete"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iMaxIdle
operator|=
name|properties
operator|.
name|getPropertyLong
argument_list|(
literal|"Termination.MaxIdle"
argument_list|,
literal|10000
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|canContinue
parameter_list|(
name|Solution
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|currentSolution
parameter_list|)
block|{
if|if
condition|(
name|iMaxIter
operator|>=
literal|0
operator|&&
name|currentSolution
operator|.
name|getIteration
argument_list|()
operator|>=
name|iMaxIter
condition|)
block|{
name|sLogger
operator|.
name|info
argument_list|(
literal|"Maximum number of iteration reached."
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
if|if
condition|(
name|iTimeOut
operator|>=
literal|0
operator|&&
name|currentSolution
operator|.
name|getTime
argument_list|()
operator|>
name|iTimeOut
condition|)
block|{
name|sLogger
operator|.
name|info
argument_list|(
literal|"Timeout reached."
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
if|if
condition|(
name|iStopWhenComplete
operator|||
operator|(
name|iMaxIter
operator|<
literal|0
operator|&&
name|iTimeOut
operator|<
literal|0
operator|)
condition|)
block|{
name|boolean
name|ret
init|=
operator|(
name|currentSolution
operator|.
name|getModel
argument_list|()
operator|.
name|nrUnassignedVariables
argument_list|(
name|currentSolution
operator|.
name|getAssignment
argument_list|()
argument_list|)
operator|!=
literal|0
operator|)
decl_stmt|;
if|if
condition|(
operator|!
name|ret
condition|)
name|sLogger
operator|.
name|info
argument_list|(
literal|"Complete solution found."
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
if|if
condition|(
name|currentSolution
operator|.
name|getIteration
argument_list|()
operator|-
name|currentSolution
operator|.
name|getBestIteration
argument_list|()
operator|>
name|iMaxIdle
condition|)
block|{
name|sLogger
operator|.
name|info
argument_list|(
literal|"Maximum idle iterations reached."
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

