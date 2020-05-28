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
name|ui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|InstructorConstraint
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
name|SoftInstructorConstraint
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
name|solver
operator|.
name|interactive
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|DiscouragedInstructorBtbReport
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|HashSet
name|iGroups
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
specifier|public
name|DiscouragedInstructorBtbReport
parameter_list|(
name|Solver
name|solver
parameter_list|)
block|{
name|TimetableModel
name|model
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
for|for
control|(
name|InstructorConstraint
name|ic
range|:
name|model
operator|.
name|getInstructorConstraints
argument_list|()
control|)
block|{
name|InstructorConstraint
operator|.
name|InstructorConstraintContext
name|context
init|=
name|ic
operator|.
name|getContext
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
name|HashSet
name|checked
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|slot
init|=
literal|1
init|;
name|slot
operator|<
name|Constants
operator|.
name|SLOTS_PER_DAY
operator|*
name|Constants
operator|.
name|NR_DAYS
condition|;
name|slot
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|slot
operator|%
name|Constants
operator|.
name|SLOTS_PER_DAY
operator|)
operator|==
literal|0
condition|)
continue|continue;
for|for
control|(
name|Placement
name|placement
range|:
name|context
operator|.
name|getPlacements
argument_list|(
name|slot
argument_list|)
control|)
block|{
for|for
control|(
name|Placement
name|prevPlacement
range|:
name|context
operator|.
name|getPlacements
argument_list|(
name|slot
operator|-
literal|1
argument_list|,
name|placement
argument_list|)
control|)
block|{
if|if
condition|(
name|prevPlacement
operator|.
name|equals
argument_list|(
name|placement
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|checked
operator|.
name|add
argument_list|(
name|prevPlacement
operator|+
literal|"."
operator|+
name|placement
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|placement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|hasIntersection
argument_list|(
name|prevPlacement
operator|.
name|getTimeLocation
argument_list|()
argument_list|)
condition|)
continue|continue;
name|double
name|dist
init|=
name|Placement
operator|.
name|getDistanceInMeters
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|prevPlacement
argument_list|,
name|placement
argument_list|)
decl_stmt|;
if|if
condition|(
name|dist
operator|>
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|getInstructorNoPreferenceLimit
argument_list|()
operator|&&
name|dist
operator|<=
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|getInstructorDiscouragedLimit
argument_list|()
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
name|dist
argument_list|,
name|prevPlacement
argument_list|,
name|placement
argument_list|,
name|PreferenceLevel
operator|.
name|sDiscouraged
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|dist
operator|>
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|getInstructorDiscouragedLimit
argument_list|()
operator|&&
name|dist
operator|<=
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|getInstructorProhibitedLimit
argument_list|()
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
name|dist
argument_list|,
name|prevPlacement
argument_list|,
name|placement
argument_list|,
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|dist
operator|>
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|getInstructorProhibitedLimit
argument_list|()
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
name|dist
argument_list|,
name|prevPlacement
argument_list|,
name|placement
argument_list|,
name|ic
operator|.
name|isIgnoreDistances
argument_list|()
condition|?
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
else|:
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|doComputeDistanceConflictsBetweenNonBTBClasses
argument_list|()
condition|)
block|{
for|for
control|(
name|Lecture
name|l1
range|:
name|ic
operator|.
name|variables
argument_list|()
control|)
block|{
name|Placement
name|p1
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|l1
argument_list|)
decl_stmt|;
name|TimeLocation
name|t1
init|=
operator|(
name|p1
operator|==
literal|null
condition|?
literal|null
else|:
name|p1
operator|.
name|getTimeLocation
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|t1
operator|==
literal|null
condition|)
continue|continue;
name|Placement
name|before
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Lecture
name|l2
range|:
name|ic
operator|.
name|variables
argument_list|()
control|)
block|{
name|Placement
name|p2
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|l2
argument_list|)
decl_stmt|;
if|if
condition|(
name|p2
operator|==
literal|null
operator|||
name|l2
operator|.
name|equals
argument_list|(
name|l1
argument_list|)
condition|)
continue|continue;
name|TimeLocation
name|t2
init|=
name|p2
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|t2
operator|==
literal|null
operator|||
operator|!
name|t1
operator|.
name|shareDays
argument_list|(
name|t2
argument_list|)
operator|||
operator|!
name|t1
operator|.
name|shareWeeks
argument_list|(
name|t2
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t2
operator|.
name|getLength
argument_list|()
operator|<
name|t1
operator|.
name|getStartSlot
argument_list|()
condition|)
block|{
name|int
name|distanceInMinutes
init|=
name|Placement
operator|.
name|getDistanceInMinutes
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|p1
argument_list|,
name|p2
argument_list|)
decl_stmt|;
if|if
condition|(
name|distanceInMinutes
operator|>
name|t2
operator|.
name|getBreakTime
argument_list|()
operator|+
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
operator|(
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t2
operator|.
name|getLength
argument_list|()
operator|)
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
name|Placement
operator|.
name|getDistanceInMeters
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|p1
argument_list|,
name|p2
argument_list|)
argument_list|,
name|p1
argument_list|,
name|p2
argument_list|,
name|ic
operator|.
name|isIgnoreDistances
argument_list|()
condition|?
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
else|:
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
argument_list|)
expr_stmt|;
if|else if
condition|(
name|distanceInMinutes
operator|>
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
operator|(
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t2
operator|.
name|getLength
argument_list|()
operator|)
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
name|Placement
operator|.
name|getDistanceInMeters
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|p1
argument_list|,
name|p2
argument_list|)
argument_list|,
name|p1
argument_list|,
name|p2
argument_list|,
name|PreferenceLevel
operator|.
name|sDiscouraged
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t2
operator|.
name|getLength
argument_list|()
operator|<=
name|t1
operator|.
name|getStartSlot
argument_list|()
condition|)
block|{
if|if
condition|(
name|before
operator|==
literal|null
operator|||
name|before
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|<
name|t2
operator|.
name|getStartSlot
argument_list|()
condition|)
name|before
operator|=
name|p2
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ic
operator|.
name|getUnavailabilities
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Placement
name|c
range|:
name|ic
operator|.
name|getUnavailabilities
argument_list|()
control|)
block|{
name|TimeLocation
name|t2
init|=
name|c
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|t1
operator|==
literal|null
operator|||
name|t2
operator|==
literal|null
operator|||
operator|!
name|t1
operator|.
name|shareDays
argument_list|(
name|t2
argument_list|)
operator|||
operator|!
name|t1
operator|.
name|shareWeeks
argument_list|(
name|t2
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t2
operator|.
name|getLength
argument_list|()
operator|<=
name|t1
operator|.
name|getStartSlot
argument_list|()
condition|)
block|{
if|if
condition|(
name|before
operator|==
literal|null
operator|||
name|before
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|<
name|t2
operator|.
name|getStartSlot
argument_list|()
condition|)
name|before
operator|=
name|c
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|before
operator|==
literal|null
condition|)
continue|continue;
name|TimeLocation
name|t0
init|=
name|before
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|t0
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t0
operator|.
name|getLength
argument_list|()
operator|<
name|t1
operator|.
name|getStartSlot
argument_list|()
condition|)
block|{
comment|// not back-to-back
if|if
condition|(
name|Placement
operator|.
name|getDistanceInMinutes
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|before
argument_list|,
name|p1
argument_list|)
operator|>
name|t0
operator|.
name|getBreakTime
argument_list|()
operator|+
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
operator|(
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t0
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t0
operator|.
name|getLength
argument_list|()
operator|)
condition|)
block|{
comment|// too far apart
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
operator|-
name|Placement
operator|.
name|getDistanceInMinutes
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|before
argument_list|,
name|p1
argument_list|)
argument_list|,
name|before
argument_list|,
name|p1
argument_list|,
name|ic
operator|.
name|isIgnoreDistances
argument_list|()
condition|?
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
else|:
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|before
operator|!=
literal|null
operator|&&
name|Placement
operator|.
name|getDistanceInMinutes
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|before
argument_list|,
name|p1
argument_list|)
operator|>
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|getInstructorLongTravelInMinutes
argument_list|()
condition|)
block|{
comment|// long travel
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
operator|-
name|Placement
operator|.
name|getDistanceInMinutes
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|before
argument_list|,
name|p1
argument_list|)
argument_list|,
name|before
argument_list|,
name|p1
argument_list|,
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|Placement
operator|.
name|getDistanceInMinutes
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|before
argument_list|,
name|p1
argument_list|)
operator|>
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
operator|(
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t0
operator|.
name|getStartSlot
argument_list|()
operator|-
name|t0
operator|.
name|getLength
argument_list|()
operator|)
condition|)
block|{
comment|// too far if no break time
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
operator|-
name|Placement
operator|.
name|getDistanceInMinutes
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|before
argument_list|,
name|p1
argument_list|)
argument_list|,
name|before
argument_list|,
name|p1
argument_list|,
name|PreferenceLevel
operator|.
name|sDiscouraged
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|ic
operator|instanceof
name|SoftInstructorConstraint
condition|)
block|{
for|for
control|(
name|Lecture
name|l1
range|:
name|ic
operator|.
name|variables
argument_list|()
control|)
block|{
name|Placement
name|p1
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|l1
argument_list|)
decl_stmt|;
name|TimeLocation
name|t1
init|=
operator|(
name|p1
operator|==
literal|null
condition|?
literal|null
else|:
name|p1
operator|.
name|getTimeLocation
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|t1
operator|==
literal|null
condition|)
continue|continue;
for|for
control|(
name|Lecture
name|l2
range|:
name|ic
operator|.
name|variables
argument_list|()
control|)
block|{
if|if
condition|(
name|l1
operator|.
name|compareTo
argument_list|(
name|l2
argument_list|)
operator|<=
literal|0
condition|)
continue|continue;
name|Placement
name|p2
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|l2
argument_list|)
decl_stmt|;
if|if
condition|(
name|p2
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|p1
operator|.
name|canShareRooms
argument_list|(
name|p2
argument_list|)
operator|&&
name|p1
operator|.
name|sameRooms
argument_list|(
name|p2
argument_list|)
condition|)
continue|continue;
name|TimeLocation
name|t2
init|=
name|p2
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|t1
operator|==
literal|null
operator|||
name|t2
operator|==
literal|null
operator|||
operator|!
name|t1
operator|.
name|hasIntersection
argument_list|(
name|t2
argument_list|)
condition|)
continue|continue;
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
literal|0
argument_list|,
name|p1
argument_list|,
name|p2
argument_list|,
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ic
operator|.
name|getUnavailabilities
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Placement
name|c
range|:
name|ic
operator|.
name|getUnavailabilities
argument_list|()
control|)
block|{
if|if
condition|(
name|c
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|hasIntersection
argument_list|(
name|t1
argument_list|)
operator|&&
operator|(
operator|!
name|l1
operator|.
name|canShareRoom
argument_list|(
name|c
operator|.
name|variable
argument_list|()
argument_list|)
operator|||
operator|!
name|p1
operator|.
name|sameRooms
argument_list|(
name|c
argument_list|)
operator|)
condition|)
block|{
if|if
condition|(
name|checked
operator|.
name|add
argument_list|(
name|p1
operator|+
literal|"."
operator|+
name|c
argument_list|)
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
literal|0
argument_list|,
name|p1
argument_list|,
name|c
argument_list|,
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|ic
operator|.
name|isIgnoreDistances
argument_list|()
condition|)
block|{
name|TimeLocation
name|t2
init|=
name|c
operator|.
name|getTimeLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|t1
operator|.
name|shareDays
argument_list|(
name|t2
argument_list|)
operator|&&
name|t1
operator|.
name|shareWeeks
argument_list|(
name|t2
argument_list|)
operator|&&
operator|(
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t1
operator|.
name|getLength
argument_list|()
operator|==
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|||
name|t2
operator|.
name|getStartSlot
argument_list|()
operator|+
name|t2
operator|.
name|getLength
argument_list|()
operator|==
name|t1
operator|.
name|getStartSlot
argument_list|()
operator|)
operator|&&
name|Placement
operator|.
name|getDistanceInMeters
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|p1
argument_list|,
name|c
argument_list|)
operator|>
name|model
operator|.
name|getDistanceMetric
argument_list|()
operator|.
name|getInstructorProhibitedLimit
argument_list|()
condition|)
block|{
if|if
condition|(
name|checked
operator|.
name|add
argument_list|(
name|p1
operator|+
literal|"."
operator|+
name|c
argument_list|)
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|DiscouragedBtb
argument_list|(
name|solver
argument_list|,
name|ic
argument_list|,
name|Placement
operator|.
name|getDistanceInMeters
argument_list|(
name|model
operator|.
name|getDistanceMetric
argument_list|()
argument_list|,
name|p1
argument_list|,
name|c
argument_list|)
argument_list|,
name|p1
argument_list|,
name|c
argument_list|,
name|PreferenceLevel
operator|.
name|sProhibited
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
block|}
block|}
specifier|public
name|Set
name|getGroups
parameter_list|()
block|{
return|return
name|iGroups
return|;
block|}
specifier|public
class|class
name|DiscouragedBtb
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
name|String
name|iPreference
decl_stmt|;
name|Long
name|iInstructorId
decl_stmt|;
name|String
name|iInstructorName
decl_stmt|;
name|ClassAssignmentDetails
name|iFirst
decl_stmt|,
name|iSecond
decl_stmt|;
name|double
name|iDistance
decl_stmt|;
specifier|public
name|DiscouragedBtb
parameter_list|(
name|Solver
name|solver
parameter_list|,
name|InstructorConstraint
name|ic
parameter_list|,
name|double
name|distance
parameter_list|,
name|Placement
name|first
parameter_list|,
name|Placement
name|second
parameter_list|,
name|String
name|pref
parameter_list|)
block|{
name|iPreference
operator|=
name|pref
expr_stmt|;
name|iInstructorId
operator|=
name|ic
operator|.
name|getResourceId
argument_list|()
expr_stmt|;
name|iInstructorName
operator|=
name|ic
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iDistance
operator|=
name|distance
expr_stmt|;
name|iFirst
operator|=
operator|new
name|ClassAssignmentDetails
argument_list|(
name|solver
argument_list|,
operator|(
name|Lecture
operator|)
name|first
operator|.
name|variable
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iSecond
operator|=
operator|new
name|ClassAssignmentDetails
argument_list|(
name|solver
argument_list|,
operator|(
name|Lecture
operator|)
name|second
operator|.
name|variable
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getPreference
parameter_list|()
block|{
return|return
name|iPreference
return|;
block|}
specifier|public
name|String
name|getInstructorName
parameter_list|()
block|{
return|return
name|iInstructorName
return|;
block|}
specifier|public
name|Long
name|getInstructorId
parameter_list|()
block|{
return|return
name|iInstructorId
return|;
block|}
specifier|public
name|ClassAssignmentDetails
name|getFirst
parameter_list|()
block|{
return|return
name|iFirst
return|;
block|}
specifier|public
name|ClassAssignmentDetails
name|getSecond
parameter_list|()
block|{
return|return
name|iSecond
return|;
block|}
specifier|public
name|double
name|getDistance
parameter_list|()
block|{
return|return
name|iDistance
return|;
block|}
block|}
block|}
end_class

end_unit

