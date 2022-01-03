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
name|ArrayList
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|FlexibleConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|GroupConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|constraint
operator|.
name|JenrlConstraint
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|criteria
operator|.
name|StudentConflict
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|criteria
operator|.
name|additional
operator|.
name|ImportantStudentConflict
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Placement
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|Student
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|assignment
operator|.
name|Assignment
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
name|solver
operator|.
name|Solver
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
name|ClassAssignmentDetails
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
name|ComputeConflictTableRequest
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
name|DistributionInfo
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
name|JenrlInfo
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
name|StudentConflictInfo
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
name|TimetableSolver
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
name|GroupConstraintInfo
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|ComputeConflictTableRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ComputeConflictTableBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ComputeConflictTableRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|ClassAssignmentDetails
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
name|SolverService
argument_list|<
name|SolverProxy
argument_list|>
name|courseTimetablingSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|ClassAssignmentDetails
argument_list|>
name|execute
parameter_list|(
name|ComputeConflictTableRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|context
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|Suggestions
argument_list|)
expr_stmt|;
name|SuggestionsContext
name|cx
init|=
operator|new
name|SuggestionsContext
argument_list|()
decl_stmt|;
name|String
name|instructorFormat
init|=
name|context
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
name|cx
operator|.
name|setInstructorNameFormat
argument_list|(
name|instructorFormat
argument_list|)
expr_stmt|;
name|SolverProxy
name|solver
init|=
name|courseTimetablingSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|solver
operator|==
literal|null
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|warnSolverNotLoaded
argument_list|()
argument_list|)
throw|;
if|if
condition|(
name|solver
operator|.
name|isWorking
argument_list|()
condition|)
throw|throw
operator|new
name|GwtRpcException
argument_list|(
name|MESSAGES
operator|.
name|warnSolverIsWorking
argument_list|()
argument_list|)
throw|;
name|List
argument_list|<
name|ClassAssignmentDetails
argument_list|>
name|response
init|=
name|solver
operator|.
name|computeConfTable
argument_list|(
name|cx
argument_list|,
name|request
argument_list|)
decl_stmt|;
return|return
operator|(
name|response
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|GwtRpcResponseList
argument_list|<
name|ClassAssignmentDetails
argument_list|>
argument_list|(
name|response
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|ClassAssignmentDetails
argument_list|>
name|computeConfTable
parameter_list|(
name|SuggestionsContext
name|context
parameter_list|,
name|TimetableSolver
name|solver
parameter_list|,
name|ComputeConflictTableRequest
name|request
parameter_list|)
block|{
name|List
argument_list|<
name|ClassAssignmentDetails
argument_list|>
name|conflicts
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentDetails
argument_list|>
argument_list|()
decl_stmt|;
name|Lecture
name|lecture
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Lecture
name|l
range|:
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
operator|.
name|variables
argument_list|()
control|)
if|if
condition|(
name|l
operator|.
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getClassId
argument_list|()
argument_list|)
condition|)
name|lecture
operator|=
name|l
expr_stmt|;
if|if
condition|(
name|lecture
operator|==
literal|null
condition|)
for|for
control|(
name|Lecture
name|l
range|:
operator|(
operator|(
name|TimetableModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|constantVariables
argument_list|()
control|)
if|if
condition|(
name|l
operator|.
name|getClassId
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getClassId
argument_list|()
argument_list|)
condition|)
name|lecture
operator|=
name|l
expr_stmt|;
for|for
control|(
name|TimeLocation
name|t
range|:
name|lecture
operator|.
name|timeLocations
argument_list|()
control|)
block|{
if|if
condition|(
name|PreferenceLevel
operator|.
name|sProhibited
operator|.
name|equals
argument_list|(
name|PreferenceLevel
operator|.
name|int2prolog
argument_list|(
name|t
operator|.
name|getPreference
argument_list|()
argument_list|)
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|t
operator|.
name|getPreference
argument_list|()
operator|>
literal|500
condition|)
continue|continue;
name|ClassAssignmentDetails
name|conflict
init|=
name|createConflict
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|lecture
argument_list|,
name|t
argument_list|)
decl_stmt|;
if|if
condition|(
name|conflict
operator|!=
literal|null
condition|)
name|conflicts
operator|.
name|add
argument_list|(
name|conflict
argument_list|)
expr_stmt|;
block|}
return|return
name|conflicts
return|;
block|}
specifier|protected
specifier|static
name|ClassAssignmentDetails
name|createConflict
parameter_list|(
name|SuggestionsContext
name|context
parameter_list|,
name|Solver
name|solver
parameter_list|,
name|Lecture
name|lecture
parameter_list|,
name|TimeLocation
name|time
parameter_list|)
block|{
name|Assignment
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|assignment
init|=
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getAssignment
argument_list|()
decl_stmt|;
name|Placement
name|currentPlacement
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|lecture
argument_list|)
decl_stmt|;
if|if
condition|(
name|currentPlacement
operator|==
literal|null
condition|)
block|{
name|List
argument_list|<
name|Placement
argument_list|>
name|values
init|=
name|lecture
operator|.
name|values
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
name|currentPlacement
operator|=
operator|(
name|values
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|values
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
expr_stmt|;
block|}
if|if
condition|(
name|currentPlacement
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Placement
name|dummyPlacement
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|currentPlacement
operator|.
name|isMultiRoom
argument_list|()
condition|)
name|dummyPlacement
operator|=
operator|new
name|Placement
argument_list|(
name|lecture
argument_list|,
name|time
argument_list|,
name|currentPlacement
operator|.
name|getRoomLocations
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|dummyPlacement
operator|=
operator|new
name|Placement
argument_list|(
name|lecture
argument_list|,
name|time
argument_list|,
name|currentPlacement
operator|.
name|getRoomLocation
argument_list|()
argument_list|)
expr_stmt|;
name|ClassAssignmentDetails
name|suggestion
init|=
name|ClassAssignmentDetailsBackend
operator|.
name|createClassAssignmentDetails
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|lecture
argument_list|,
name|dummyPlacement
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|TimetableModel
name|m
init|=
operator|(
name|TimetableModel
operator|)
name|solver
operator|.
name|currentSolution
argument_list|()
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|StudentConflict
name|imp
init|=
operator|(
name|StudentConflict
operator|)
name|m
operator|.
name|getCriterion
argument_list|(
name|ImportantStudentConflict
operator|.
name|class
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|Placement
argument_list|,
name|Integer
argument_list|>
name|committed
init|=
operator|new
name|HashMap
argument_list|<
name|Placement
argument_list|,
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|dummyPlacement
operator|.
name|getCommitedConflicts
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Student
name|s
range|:
name|lecture
operator|.
name|students
argument_list|()
control|)
block|{
name|Set
argument_list|<
name|Placement
argument_list|>
name|confs
init|=
name|s
operator|.
name|conflictPlacements
argument_list|(
name|dummyPlacement
argument_list|)
decl_stmt|;
if|if
condition|(
name|confs
operator|==
literal|null
condition|)
continue|continue;
for|for
control|(
name|Placement
name|commitedPlacement
range|:
name|confs
control|)
block|{
name|Integer
name|current
init|=
name|committed
operator|.
name|get
argument_list|(
name|commitedPlacement
argument_list|)
decl_stmt|;
name|committed
operator|.
name|put
argument_list|(
name|commitedPlacement
argument_list|,
name|Integer
operator|.
name|valueOf
argument_list|(
literal|1
operator|+
operator|(
name|current
operator|==
literal|null
condition|?
literal|0
else|:
name|current
operator|.
name|intValue
argument_list|()
operator|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|JenrlConstraint
name|jenrl
range|:
name|lecture
operator|.
name|jenrlConstraints
argument_list|()
control|)
block|{
name|long
name|j
init|=
name|jenrl
operator|.
name|jenrl
argument_list|(
name|assignment
argument_list|,
name|lecture
argument_list|,
name|dummyPlacement
argument_list|)
decl_stmt|;
if|if
condition|(
name|j
operator|>
literal|0
operator|&&
operator|!
name|jenrl
operator|.
name|isToBeIgnored
argument_list|()
condition|)
block|{
if|if
condition|(
name|jenrl
operator|.
name|areStudentConflictsDistance
argument_list|(
name|assignment
argument_list|,
name|dummyPlacement
argument_list|)
condition|)
continue|continue;
name|JenrlInfo
name|jInfo
init|=
operator|new
name|JenrlInfo
argument_list|()
decl_stmt|;
name|jInfo
operator|.
name|setJenrl
argument_list|(
operator|(
name|int
operator|)
name|j
argument_list|)
expr_stmt|;
name|jInfo
operator|.
name|setIsHard
argument_list|(
name|jenrl
operator|.
name|areStudentConflictsHard
argument_list|()
argument_list|)
expr_stmt|;
name|jInfo
operator|.
name|setIsDistance
argument_list|(
name|jenrl
operator|.
name|areStudentConflictsDistance
argument_list|(
name|assignment
argument_list|,
name|dummyPlacement
argument_list|)
argument_list|)
expr_stmt|;
name|jInfo
operator|.
name|setIsImportant
argument_list|(
name|imp
operator|!=
literal|null
operator|&&
name|jenrl
operator|.
name|priority
argument_list|()
operator|>
literal|0.0
argument_list|)
expr_stmt|;
name|jInfo
operator|.
name|setIsWorkDay
argument_list|(
name|jenrl
operator|.
name|areStudentConflictsWorkday
argument_list|(
name|assignment
argument_list|,
name|dummyPlacement
argument_list|)
argument_list|)
expr_stmt|;
name|jInfo
operator|.
name|setIsFixed
argument_list|(
name|jenrl
operator|.
name|first
argument_list|()
operator|.
name|nrTimeLocations
argument_list|()
operator|==
literal|1
operator|&&
name|jenrl
operator|.
name|second
argument_list|()
operator|.
name|nrTimeLocations
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|jInfo
operator|.
name|setIsInstructor
argument_list|(
name|jenrl
operator|.
name|getNrInstructors
argument_list|()
operator|>
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|jenrl
operator|.
name|first
argument_list|()
operator|.
name|equals
argument_list|(
name|lecture
argument_list|)
condition|)
block|{
if|if
condition|(
name|jenrl
operator|.
name|second
argument_list|()
operator|.
name|isCommitted
argument_list|()
condition|)
name|jInfo
operator|.
name|setIsCommited
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|suggestion
operator|.
name|addStudentConflict
argument_list|(
operator|new
name|StudentConflictInfo
argument_list|(
name|jInfo
argument_list|,
name|ClassAssignmentDetailsBackend
operator|.
name|createClassAssignmentDetails
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|jenrl
operator|.
name|second
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|jenrl
operator|.
name|first
argument_list|()
operator|.
name|isCommitted
argument_list|()
condition|)
name|jInfo
operator|.
name|setIsCommited
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|suggestion
operator|.
name|addStudentConflict
argument_list|(
operator|new
name|StudentConflictInfo
argument_list|(
name|jInfo
argument_list|,
name|ClassAssignmentDetailsBackend
operator|.
name|createClassAssignmentDetails
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|jenrl
operator|.
name|first
argument_list|()
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Placement
argument_list|,
name|Integer
argument_list|>
name|x
range|:
name|committed
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Placement
name|p
init|=
name|x
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|Integer
name|cnt
init|=
name|x
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|JenrlInfo
name|jenrl
init|=
operator|new
name|JenrlInfo
argument_list|()
decl_stmt|;
name|jenrl
operator|.
name|setIsCommited
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|jenrl
operator|.
name|setJenrl
argument_list|(
name|cnt
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|jenrl
operator|.
name|setIsFixed
argument_list|(
name|lecture
operator|.
name|nrTimeLocations
argument_list|()
operator|==
literal|1
argument_list|)
expr_stmt|;
name|jenrl
operator|.
name|setIsHard
argument_list|(
name|lecture
operator|.
name|isSingleSection
argument_list|()
argument_list|)
expr_stmt|;
name|jenrl
operator|.
name|setIsDistance
argument_list|(
name|StudentConflict
operator|.
name|distance
argument_list|(
name|m
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|dummyPlacement
argument_list|,
name|p
argument_list|)
argument_list|)
expr_stmt|;
name|jenrl
operator|.
name|setIsWorkDay
argument_list|(
name|StudentConflict
operator|.
name|workday
argument_list|(
name|m
operator|.
name|getStudentWorkDayLimit
argument_list|()
argument_list|,
name|dummyPlacement
argument_list|,
name|p
argument_list|)
argument_list|)
expr_stmt|;
name|suggestion
operator|.
name|addStudentConflict
argument_list|(
operator|new
name|StudentConflictInfo
argument_list|(
name|jenrl
argument_list|,
name|ClassAssignmentDetailsBackend
operator|.
name|createClassAssignmentDetails
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|p
operator|.
name|variable
argument_list|()
argument_list|,
name|p
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|GroupConstraint
name|gc
range|:
name|lecture
operator|.
name|groupConstraints
argument_list|()
control|)
block|{
if|if
condition|(
name|gc
operator|.
name|getType
argument_list|()
operator|==
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|SAME_ROOM
condition|)
continue|continue;
name|int
name|curPref
init|=
name|gc
operator|.
name|getCurrentPreference
argument_list|(
name|assignment
argument_list|,
name|dummyPlacement
argument_list|)
decl_stmt|;
if|if
condition|(
name|gc
operator|.
name|getType
argument_list|()
operator|==
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|BTB
condition|)
block|{
name|gc
operator|.
name|setType
argument_list|(
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|BTB_TIME
argument_list|)
expr_stmt|;
name|curPref
operator|=
name|gc
operator|.
name|getCurrentPreference
argument_list|(
name|assignment
argument_list|,
name|dummyPlacement
argument_list|)
expr_stmt|;
name|gc
operator|.
name|setType
argument_list|(
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|BTB
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|gc
operator|.
name|getType
argument_list|()
operator|==
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|SAME_STUDENTS
condition|)
block|{
name|gc
operator|.
name|setType
argument_list|(
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|DIFF_TIME
argument_list|)
expr_stmt|;
name|curPref
operator|=
name|gc
operator|.
name|getCurrentPreference
argument_list|(
name|assignment
argument_list|,
name|dummyPlacement
argument_list|)
expr_stmt|;
name|gc
operator|.
name|setType
argument_list|(
name|GroupConstraint
operator|.
name|ConstraintType
operator|.
name|SAME_STUDENTS
argument_list|)
expr_stmt|;
block|}
name|boolean
name|sat
init|=
operator|(
name|curPref
operator|<=
literal|0
operator|)
decl_stmt|;
if|if
condition|(
name|sat
condition|)
continue|continue;
name|DistributionInfo
name|dist
init|=
operator|new
name|DistributionInfo
argument_list|(
name|ClassAssignmentDetailsBackend
operator|.
name|toGroupConstraintInfo
argument_list|(
operator|new
name|GroupConstraintInfo
argument_list|(
name|assignment
argument_list|,
name|gc
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|dist
operator|.
name|getInfo
argument_list|()
operator|.
name|setValue
argument_list|(
operator|(
name|double
operator|)
name|curPref
argument_list|)
expr_stmt|;
name|dist
operator|.
name|getInfo
argument_list|()
operator|.
name|setIsSatisfied
argument_list|(
literal|false
argument_list|)
expr_stmt|;
for|for
control|(
name|Lecture
name|another
range|:
name|gc
operator|.
name|variables
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|another
operator|.
name|equals
argument_list|(
name|lecture
argument_list|)
operator|&&
name|assignment
operator|.
name|getValue
argument_list|(
name|another
argument_list|)
operator|!=
literal|null
condition|)
name|dist
operator|.
name|addClass
argument_list|(
name|ClassAssignmentDetailsBackend
operator|.
name|createClassAssignmentDetails
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|another
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|suggestion
operator|.
name|addDistributionConflict
argument_list|(
name|dist
argument_list|)
expr_stmt|;
block|}
name|HashMap
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
name|dummies
init|=
operator|new
name|HashMap
argument_list|<
name|Lecture
argument_list|,
name|Placement
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|dummyPlacement
operator|!=
literal|null
condition|)
name|dummies
operator|.
name|put
argument_list|(
name|lecture
argument_list|,
name|dummyPlacement
argument_list|)
expr_stmt|;
for|for
control|(
name|FlexibleConstraint
name|fc
range|:
name|lecture
operator|.
name|getFlexibleGroupConstraints
argument_list|()
control|)
block|{
if|if
condition|(
name|fc
operator|.
name|isHard
argument_list|()
operator|||
name|fc
operator|.
name|getNrViolations
argument_list|(
name|assignment
argument_list|,
literal|null
argument_list|,
name|dummies
argument_list|)
operator|==
literal|0.0
condition|)
continue|continue;
name|DistributionInfo
name|dist
init|=
operator|new
name|DistributionInfo
argument_list|(
name|ClassAssignmentDetailsBackend
operator|.
name|toGroupConstraintInfo
argument_list|(
operator|new
name|GroupConstraintInfo
argument_list|(
name|assignment
argument_list|,
name|fc
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|dist
operator|.
name|getInfo
argument_list|()
operator|.
name|setValue
argument_list|(
name|Math
operator|.
name|abs
argument_list|(
name|fc
operator|.
name|getCurrentPreference
argument_list|(
name|assignment
argument_list|,
literal|null
argument_list|,
name|dummies
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|dist
operator|.
name|getInfo
argument_list|()
operator|.
name|setIsSatisfied
argument_list|(
literal|false
argument_list|)
expr_stmt|;
for|for
control|(
name|Lecture
name|another
range|:
name|fc
operator|.
name|variables
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|another
operator|.
name|equals
argument_list|(
name|lecture
argument_list|)
operator|&&
name|assignment
operator|.
name|getValue
argument_list|(
name|another
argument_list|)
operator|!=
literal|null
condition|)
name|dist
operator|.
name|addClass
argument_list|(
name|ClassAssignmentDetailsBackend
operator|.
name|createClassAssignmentDetails
argument_list|(
name|context
argument_list|,
name|solver
argument_list|,
name|another
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|suggestion
operator|.
name|addDistributionConflict
argument_list|(
name|dist
argument_list|)
expr_stmt|;
block|}
return|return
name|suggestion
return|;
block|}
block|}
end_class

end_unit

