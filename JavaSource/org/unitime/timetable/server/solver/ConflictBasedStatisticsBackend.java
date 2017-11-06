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
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|GwtRpcResponseList
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
name|SuggestionsInterface
operator|.
name|CBSNode
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
name|SuggestionsInterface
operator|.
name|ConflictBasedStatisticsRequest
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
name|SuggestionsInterface
operator|.
name|SelectedAssignment
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
name|PreferenceLevel
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
name|ui
operator|.
name|ConflictStatisticsInfo
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
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|ConflictBasedStatisticsRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ConflictBasedStatisticsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ConflictBasedStatisticsRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
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
specifier|public
specifier|static
name|boolean
name|isAllSubjects
parameter_list|(
name|String
name|subjects
parameter_list|)
block|{
if|if
condition|(
name|subjects
operator|==
literal|null
operator|||
name|subjects
operator|.
name|isEmpty
argument_list|()
operator|||
name|subjects
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|ALL_OPTION_VALUE
argument_list|)
condition|)
return|return
literal|true
return|;
for|for
control|(
name|String
name|id
range|:
name|subjects
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
if|if
condition|(
name|Constants
operator|.
name|ALL_OPTION_VALUE
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
name|execute
parameter_list|(
name|ConflictBasedStatisticsRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
if|if
condition|(
name|request
operator|.
name|hasClassId
argument_list|()
condition|)
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Suggestions
argument_list|)
expr_stmt|;
else|else
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ConflictStatistics
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|request
operator|.
name|hasClassId
argument_list|()
condition|)
block|{
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Cbs.type"
argument_list|,
name|request
operator|.
name|isVariableOriented
argument_list|()
condition|?
literal|"0"
else|:
literal|"1"
argument_list|)
expr_stmt|;
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"Cbs.limit"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|request
operator|.
name|getLimit
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|SolverProxy
name|solver
init|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
name|ConflictStatisticsInfo
name|info
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|solver
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|hasClassId
argument_list|()
condition|)
name|info
operator|=
name|solver
operator|.
name|getCbsInfo
argument_list|(
name|request
operator|.
name|getClassId
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|info
operator|=
name|solver
operator|.
name|getCbsInfo
argument_list|()
expr_stmt|;
block|}
else|else
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
condition|)
block|{
for|for
control|(
name|String
name|solutionId
range|:
name|solutionIdsStr
operator|.
name|split
argument_list|(
literal|","
argument_list|)
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
name|solutionId
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
name|ConflictStatisticsInfo
name|x
init|=
operator|(
name|ConflictStatisticsInfo
operator|)
name|solution
operator|.
name|getInfo
argument_list|(
literal|"CBSInfo"
argument_list|)
decl_stmt|;
if|if
condition|(
name|x
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|info
operator|==
literal|null
condition|)
name|info
operator|=
name|x
expr_stmt|;
else|else
name|info
operator|.
name|merge
argument_list|(
name|x
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
name|info
operator|==
literal|null
operator|||
name|info
operator|.
name|getCBS
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
name|convert
argument_list|(
name|info
operator|.
name|getCBS
argument_list|()
argument_list|,
name|request
operator|.
name|getClassId
argument_list|()
argument_list|,
name|request
operator|.
name|isVariableOriented
argument_list|()
argument_list|,
name|request
operator|.
name|getLimit
argument_list|()
operator|/
literal|100.0
argument_list|)
return|;
block|}
specifier|protected
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
name|convert
parameter_list|(
name|Collection
argument_list|<
name|ConflictStatisticsInfo
operator|.
name|CBSVariable
argument_list|>
name|variables
parameter_list|,
name|Long
name|classId
parameter_list|,
name|boolean
name|variableOriented
parameter_list|,
name|double
name|limit
parameter_list|)
block|{
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
name|response
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|CBSNode
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|variableOriented
condition|)
block|{
if|if
condition|(
name|classId
operator|!=
literal|null
condition|)
name|variables
operator|=
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|variables
argument_list|,
name|limit
argument_list|)
expr_stmt|;
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSVariable
name|var
range|:
name|variables
control|)
block|{
name|CBSNode
name|varNode
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|classId
operator|==
literal|null
condition|)
block|{
name|varNode
operator|=
name|variableNode
argument_list|(
name|var
argument_list|)
expr_stmt|;
name|response
operator|.
name|add
argument_list|(
name|varNode
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|classId
operator|.
name|equals
argument_list|(
name|var
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
continue|continue;
block|}
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSValue
name|val
range|:
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|var
operator|.
name|values
argument_list|()
argument_list|,
name|limit
argument_list|)
control|)
block|{
name|CBSNode
name|valNode
init|=
name|valueNode
argument_list|(
name|val
argument_list|)
decl_stmt|;
if|if
condition|(
name|varNode
operator|!=
literal|null
condition|)
name|varNode
operator|.
name|addNode
argument_list|(
name|valNode
argument_list|)
expr_stmt|;
else|else
name|response
operator|.
name|add
argument_list|(
name|valNode
argument_list|)
expr_stmt|;
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSConstraint
name|con
range|:
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|val
operator|.
name|constraints
argument_list|()
argument_list|,
name|limit
argument_list|)
control|)
block|{
name|CBSNode
name|conNode
init|=
name|constraintNode
argument_list|(
name|con
argument_list|)
decl_stmt|;
name|valNode
operator|.
name|addNode
argument_list|(
name|conNode
argument_list|)
expr_stmt|;
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSAssignment
name|ass
range|:
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|con
operator|.
name|assignments
argument_list|()
argument_list|,
name|limit
argument_list|)
control|)
name|conNode
operator|.
name|addNode
argument_list|(
name|assignmentNode
argument_list|(
name|ass
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
else|else
block|{
name|Collection
argument_list|<
name|ConflictStatisticsInfo
operator|.
name|CBSConstraint
argument_list|>
name|constraints
init|=
name|ConflictStatisticsInfo
operator|.
name|transpose
argument_list|(
name|variables
argument_list|,
name|classId
argument_list|)
decl_stmt|;
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSConstraint
name|consraint
range|:
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|constraints
argument_list|,
name|limit
argument_list|)
control|)
block|{
name|CBSNode
name|conNode
init|=
name|constraintNode
argument_list|(
name|consraint
argument_list|)
decl_stmt|;
name|response
operator|.
name|add
argument_list|(
name|conNode
argument_list|)
expr_stmt|;
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSVariable
name|variable
range|:
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|consraint
operator|.
name|variables
argument_list|()
argument_list|,
name|limit
argument_list|)
control|)
block|{
name|CBSNode
name|varNode
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|classId
operator|==
literal|null
condition|)
block|{
name|varNode
operator|=
name|variableNode
argument_list|(
name|variable
argument_list|)
expr_stmt|;
name|conNode
operator|.
name|addNode
argument_list|(
name|varNode
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSValue
name|value
range|:
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|variable
operator|.
name|values
argument_list|()
argument_list|,
name|limit
argument_list|)
control|)
block|{
name|CBSNode
name|valNode
init|=
name|valueNode
argument_list|(
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|varNode
operator|!=
literal|null
condition|)
name|varNode
operator|.
name|addNode
argument_list|(
name|valNode
argument_list|)
expr_stmt|;
else|else
name|conNode
operator|.
name|addNode
argument_list|(
name|valNode
argument_list|)
expr_stmt|;
for|for
control|(
name|ConflictStatisticsInfo
operator|.
name|CBSAssignment
name|ass
range|:
name|ConflictStatisticsInfo
operator|.
name|filter
argument_list|(
name|value
operator|.
name|assignments
argument_list|()
argument_list|,
name|limit
argument_list|)
control|)
name|valNode
operator|.
name|addNode
argument_list|(
name|assignmentNode
argument_list|(
name|ass
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|response
return|;
block|}
specifier|private
name|CBSNode
name|variableNode
parameter_list|(
name|ConflictStatisticsInfo
operator|.
name|CBSVariable
name|variable
parameter_list|)
block|{
name|CBSNode
name|node
init|=
operator|new
name|CBSNode
argument_list|()
decl_stmt|;
name|node
operator|.
name|setCount
argument_list|(
name|variable
operator|.
name|getCounter
argument_list|()
argument_list|)
expr_stmt|;
name|node
operator|.
name|setName
argument_list|(
name|variable
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|node
operator|.
name|setPref
argument_list|(
name|variable
operator|.
name|getPref
argument_list|()
argument_list|)
expr_stmt|;
name|node
operator|.
name|setClassId
argument_list|(
name|variable
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
specifier|private
name|CBSNode
name|valueNode
parameter_list|(
name|ConflictStatisticsInfo
operator|.
name|CBSValue
name|value
parameter_list|)
block|{
name|CBSNode
name|node
init|=
operator|new
name|CBSNode
argument_list|()
decl_stmt|;
name|node
operator|.
name|setCount
argument_list|(
name|value
operator|.
name|getCounter
argument_list|()
argument_list|)
expr_stmt|;
name|SelectedAssignment
name|sa
init|=
operator|new
name|SelectedAssignment
argument_list|()
decl_stmt|;
name|sa
operator|.
name|setClassId
argument_list|(
name|value
operator|.
name|variable
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setDatePatternId
argument_list|(
name|value
operator|.
name|getDatePatternId
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setDays
argument_list|(
name|value
operator|.
name|getDayCode
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setPatternId
argument_list|(
name|value
operator|.
name|getPatternId
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setStartSlot
argument_list|(
name|value
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setRoomIds
argument_list|(
name|value
operator|.
name|getRoomIds
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|html
init|=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|value
operator|.
name|getTimePref
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|value
operator|.
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
name|value
operator|.
name|getStartTime
argument_list|()
operator|+
literal|" - "
operator|+
name|value
operator|.
name|getEndTime
argument_list|()
operator|+
literal|" "
operator|+
name|value
operator|.
name|getDatePatternName
argument_list|()
operator|+
literal|"</font> "
decl_stmt|;
name|String
name|name
init|=
name|value
operator|.
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
name|value
operator|.
name|getStartTime
argument_list|()
operator|+
literal|" - "
operator|+
name|value
operator|.
name|getEndTime
argument_list|()
operator|+
literal|" "
operator|+
name|value
operator|.
name|getDatePatternName
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
name|value
operator|.
name|getRoomIds
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|html
operator|+=
operator|(
name|i
operator|>
literal|0
condition|?
literal|", "
else|:
literal|""
operator|)
operator|+
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|value
operator|.
name|getRoomPrefs
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|value
operator|.
name|getRoomNames
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|+
literal|"</font>"
expr_stmt|;
name|name
operator|+=
operator|(
name|i
operator|>
literal|0
condition|?
literal|", "
else|:
literal|""
operator|)
operator|+
name|value
operator|.
name|getRoomNames
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|value
operator|.
name|getInstructorName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|html
operator|+=
literal|" "
operator|+
name|value
operator|.
name|getInstructorName
argument_list|()
expr_stmt|;
block|}
name|node
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|node
operator|.
name|setHTML
argument_list|(
name|html
argument_list|)
expr_stmt|;
name|node
operator|.
name|setSelection
argument_list|(
name|sa
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
specifier|private
name|CBSNode
name|constraintNode
parameter_list|(
name|ConflictStatisticsInfo
operator|.
name|CBSConstraint
name|constraint
parameter_list|)
block|{
name|CBSNode
name|node
init|=
operator|new
name|CBSNode
argument_list|()
decl_stmt|;
name|node
operator|.
name|setCount
argument_list|(
name|constraint
operator|.
name|getCounter
argument_list|()
argument_list|)
expr_stmt|;
name|node
operator|.
name|setPref
argument_list|(
name|constraint
operator|.
name|getPref
argument_list|()
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|constraint
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|ConflictStatisticsInfo
operator|.
name|sConstraintTypeBalanc
case|:
name|node
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|constraintDeptSpread
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|ConflictStatisticsInfo
operator|.
name|sConstraintTypeSpread
case|:
name|node
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|constraintSameSubpartSpread
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|ConflictStatisticsInfo
operator|.
name|sConstraintTypeGroup
case|:
name|node
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|constraintDistribution
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|ConflictStatisticsInfo
operator|.
name|sConstraintTypeInstructor
case|:
name|node
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|constraintInstructor
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|node
operator|.
name|setLink
argument_list|(
literal|"gwt.jsp?page=timetableGrid&resource=1&filter="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
literal|"&search=1"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
block|}
break|break;
case|case
name|ConflictStatisticsInfo
operator|.
name|sConstraintTypeRoom
case|:
name|node
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|constraintRoom
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|node
operator|.
name|setLink
argument_list|(
literal|"gwt.jsp?page=timetableGrid&resource=0&filter="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
literal|"&search=1"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
block|}
break|break;
case|case
name|ConflictStatisticsInfo
operator|.
name|sConstraintTypeClassLimit
case|:
name|node
operator|.
name|setName
argument_list|(
name|MESSAGES
operator|.
name|constraintClassLimit
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
name|node
operator|.
name|setName
argument_list|(
name|constraint
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
name|MESSAGES
operator|.
name|constraintUnknown
argument_list|()
else|:
name|constraint
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|node
return|;
block|}
specifier|private
name|CBSNode
name|assignmentNode
parameter_list|(
name|ConflictStatisticsInfo
operator|.
name|CBSAssignment
name|assignment
parameter_list|)
block|{
name|CBSNode
name|node
init|=
operator|new
name|CBSNode
argument_list|()
decl_stmt|;
name|node
operator|.
name|setCount
argument_list|(
name|assignment
operator|.
name|getCounter
argument_list|()
argument_list|)
expr_stmt|;
name|SelectedAssignment
name|sa
init|=
operator|new
name|SelectedAssignment
argument_list|()
decl_stmt|;
name|sa
operator|.
name|setClassId
argument_list|(
name|assignment
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setDatePatternId
argument_list|(
name|assignment
operator|.
name|getDatePatternId
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setDays
argument_list|(
name|assignment
operator|.
name|getDayCode
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setPatternId
argument_list|(
name|assignment
operator|.
name|getPatternId
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setStartSlot
argument_list|(
name|assignment
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|sa
operator|.
name|setRoomIds
argument_list|(
name|assignment
operator|.
name|getRoomIds
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|name
init|=
name|assignment
operator|.
name|getVariableName
argument_list|()
operator|+
literal|" "
operator|+
name|assignment
operator|.
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
name|assignment
operator|.
name|getStartTime
argument_list|()
operator|+
literal|" - "
operator|+
name|assignment
operator|.
name|getEndTime
argument_list|()
operator|+
literal|" "
operator|+
name|assignment
operator|.
name|getDatePatternName
argument_list|()
decl_stmt|;
name|String
name|html
init|=
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|assignment
operator|.
name|getPref
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|assignment
operator|.
name|getVariableName
argument_list|()
operator|+
literal|"</font>&larr;<font color='"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|assignment
operator|.
name|getTimePref
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|assignment
operator|.
name|getDays
argument_list|()
operator|+
literal|" "
operator|+
name|assignment
operator|.
name|getStartTime
argument_list|()
operator|+
literal|" - "
operator|+
name|assignment
operator|.
name|getEndTime
argument_list|()
operator|+
literal|" "
operator|+
name|assignment
operator|.
name|getDatePatternName
argument_list|()
operator|+
literal|"</font> "
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
name|assignment
operator|.
name|getRoomIds
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|html
operator|+=
operator|(
name|i
operator|>
literal|0
condition|?
literal|", "
else|:
literal|""
operator|)
operator|+
literal|"<font color='"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
operator|(
operator|(
name|Integer
operator|)
name|assignment
operator|.
name|getRoomPrefs
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|assignment
operator|.
name|getRoomNames
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|+
literal|"</font>"
expr_stmt|;
name|name
operator|+=
operator|(
name|i
operator|>
literal|0
condition|?
literal|", "
else|:
literal|""
operator|)
operator|+
name|assignment
operator|.
name|getRoomNames
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|.
name|getInstructorName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|html
operator|+=
literal|" "
operator|+
name|assignment
operator|.
name|getInstructorName
argument_list|()
expr_stmt|;
block|}
name|node
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|node
operator|.
name|setHTML
argument_list|(
name|html
argument_list|)
expr_stmt|;
name|node
operator|.
name|setSelection
argument_list|(
name|sa
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
block|}
end_class

end_unit
