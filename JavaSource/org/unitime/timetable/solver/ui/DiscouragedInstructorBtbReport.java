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

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|ic
operator|.
name|getResource
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
name|ic
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
name|p1
range|:
name|ic
operator|.
name|assignedVariables
argument_list|()
control|)
block|{
name|TimeLocation
name|t1
init|=
operator|(
name|p1
operator|.
name|getAssignment
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|p1
operator|.
name|getAssignment
argument_list|()
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
name|p2
range|:
name|ic
operator|.
name|assignedVariables
argument_list|()
control|)
block|{
if|if
condition|(
name|p2
operator|.
name|getAssignment
argument_list|()
operator|==
literal|null
operator|||
name|p2
operator|.
name|equals
argument_list|(
name|p1
argument_list|)
condition|)
continue|continue;
name|TimeLocation
name|t2
init|=
name|p2
operator|.
name|getAssignment
argument_list|()
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
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|p2
operator|.
name|getAssignment
argument_list|()
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
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|p2
operator|.
name|getAssignment
argument_list|()
argument_list|)
argument_list|,
name|p1
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|p2
operator|.
name|getAssignment
argument_list|()
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
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|p2
operator|.
name|getAssignment
argument_list|()
argument_list|)
argument_list|,
name|p1
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|p2
operator|.
name|getAssignment
argument_list|()
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
operator|.
name|getAssignment
argument_list|()
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
operator|.
name|getAssignment
argument_list|()
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
name|before
argument_list|,
name|p1
operator|.
name|getAssignment
argument_list|()
argument_list|)
argument_list|,
name|before
argument_list|,
name|p1
operator|.
name|getAssignment
argument_list|()
argument_list|,
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
argument_list|)
argument_list|)
expr_stmt|;
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

