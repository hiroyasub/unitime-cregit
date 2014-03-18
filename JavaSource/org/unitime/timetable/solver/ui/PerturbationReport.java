begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Iterator
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DistanceMetric
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PerturbationReport
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
name|PerturbationReport
parameter_list|(
name|Solver
name|solver
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
name|Lecture
name|lecture
range|:
name|model
operator|.
name|perturbVariables
argument_list|(
name|assignment
argument_list|)
control|)
block|{
name|Placement
name|placement
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|lecture
argument_list|)
decl_stmt|;
name|Placement
name|initial
init|=
name|lecture
operator|.
name|getInitialAssignment
argument_list|()
decl_stmt|;
if|if
condition|(
name|placement
operator|==
literal|null
operator|||
name|initial
operator|==
literal|null
operator|||
name|placement
operator|.
name|equals
argument_list|(
name|initial
argument_list|)
condition|)
continue|continue;
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|PerturbationGroup
argument_list|(
name|solver
argument_list|,
name|lecture
argument_list|)
argument_list|)
expr_stmt|;
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
name|PerturbationGroup
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
name|ClassAssignmentDetails
name|iDetail
init|=
literal|null
decl_stmt|;
specifier|public
name|long
name|affectedStudents
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|affectedInstructors
init|=
literal|0
decl_stmt|;
specifier|public
name|long
name|affectedStudentsByTime
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|affectedInstructorsByTime
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|differentRoom
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|affectedInstructorsByRoom
init|=
literal|0
decl_stmt|;
specifier|public
name|long
name|affectedStudentsByRoom
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|differentBuilding
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|affectedInstructorsByBldg
init|=
literal|0
decl_stmt|;
specifier|public
name|long
name|affectedStudentsByBldg
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|deltaRoomPreferences
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|differentTime
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|differentDay
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|differentHour
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|tooFarForInstructors
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|tooFarForStudents
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|deltaStudentConflicts
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|newStudentConflicts
init|=
literal|0
decl_stmt|;
specifier|public
name|double
name|deltaTimePreferences
init|=
literal|0
decl_stmt|;
specifier|public
name|int
name|deltaInstructorDistancePreferences
init|=
literal|0
decl_stmt|;
specifier|public
name|double
name|distance
init|=
literal|0
decl_stmt|;
specifier|public
name|PerturbationGroup
parameter_list|(
name|Solver
name|solver
parameter_list|,
name|Lecture
name|lecture
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
name|assignedPlacement
init|=
operator|(
name|Placement
operator|)
name|assignment
operator|.
name|getValue
argument_list|(
name|lecture
argument_list|)
decl_stmt|;
name|Placement
name|initialPlacement
init|=
operator|(
name|Placement
operator|)
name|lecture
operator|.
name|getInitialAssignment
argument_list|()
decl_stmt|;
name|iDetail
operator|=
operator|new
name|ClassAssignmentDetails
argument_list|(
name|solver
argument_list|,
name|lecture
argument_list|,
name|initialPlacement
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|iDetail
operator|.
name|setAssigned
argument_list|(
operator|new
name|AssignmentPreferenceInfo
argument_list|(
name|solver
argument_list|,
name|assignedPlacement
argument_list|,
literal|false
argument_list|)
argument_list|,
name|assignedPlacement
operator|.
name|getRoomIds
argument_list|()
argument_list|,
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDayCode
argument_list|()
argument_list|,
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|,
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getTimePatternId
argument_list|()
argument_list|,
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDatePatternId
argument_list|()
argument_list|)
expr_stmt|;
name|affectedStudents
operator|=
name|lecture
operator|.
name|classLimit
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|affectedInstructors
operator|=
name|lecture
operator|.
name|getInstructorConstraints
argument_list|()
operator|.
name|size
argument_list|()
expr_stmt|;
name|affectedStudentsByTime
operator|=
operator|(
name|initialPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|equals
argument_list|(
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
argument_list|)
condition|?
literal|0
else|:
name|lecture
operator|.
name|classLimit
argument_list|(
name|assignment
argument_list|)
operator|)
expr_stmt|;
name|affectedInstructorsByTime
operator|=
operator|(
name|initialPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|equals
argument_list|(
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
argument_list|)
condition|?
literal|0
else|:
name|lecture
operator|.
name|getInstructorConstraints
argument_list|()
operator|.
name|size
argument_list|()
operator|)
expr_stmt|;
name|differentRoom
operator|=
name|initialPlacement
operator|.
name|nrDifferentRooms
argument_list|(
name|assignedPlacement
argument_list|)
expr_stmt|;
name|affectedInstructorsByRoom
operator|=
name|differentRoom
operator|*
name|lecture
operator|.
name|getInstructorConstraints
argument_list|()
operator|.
name|size
argument_list|()
expr_stmt|;
name|affectedStudentsByRoom
operator|=
name|differentRoom
operator|*
name|lecture
operator|.
name|classLimit
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|differentBuilding
operator|=
name|initialPlacement
operator|.
name|nrDifferentBuildings
argument_list|(
name|assignedPlacement
argument_list|)
expr_stmt|;
name|affectedInstructorsByBldg
operator|=
name|differentBuilding
operator|*
name|lecture
operator|.
name|getInstructorConstraints
argument_list|()
operator|.
name|size
argument_list|()
expr_stmt|;
name|affectedStudentsByBldg
operator|=
name|differentBuilding
operator|*
name|lecture
operator|.
name|classLimit
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|deltaRoomPreferences
operator|=
name|assignedPlacement
operator|.
name|sumRoomPreference
argument_list|()
operator|-
name|initialPlacement
operator|.
name|sumRoomPreference
argument_list|()
expr_stmt|;
name|differentTime
operator|=
operator|(
name|initialPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|equals
argument_list|(
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
argument_list|)
condition|?
literal|0
else|:
literal|1
operator|)
expr_stmt|;
name|differentDay
operator|=
operator|(
name|initialPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDayCode
argument_list|()
operator|!=
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getDayCode
argument_list|()
condition|?
literal|1
else|:
literal|0
operator|)
expr_stmt|;
name|differentHour
operator|=
operator|(
name|initialPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|!=
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getStartSlot
argument_list|()
condition|?
literal|1
else|:
literal|0
operator|)
expr_stmt|;
name|deltaStudentConflicts
operator|=
name|lecture
operator|.
name|countStudentConflicts
argument_list|(
name|assignment
argument_list|,
name|assignedPlacement
argument_list|)
operator|-
name|lecture
operator|.
name|countInitialStudentConflicts
argument_list|()
expr_stmt|;
name|deltaTimePreferences
operator|=
operator|(
name|assignedPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getNormalizedPreference
argument_list|()
operator|-
name|initialPlacement
operator|.
name|getTimeLocation
argument_list|()
operator|.
name|getNormalizedPreference
argument_list|()
operator|)
expr_stmt|;
name|DistanceMetric
name|m
init|=
operator|(
operator|(
name|TimetableModel
operator|)
name|lecture
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getDistanceMetric
argument_list|()
decl_stmt|;
name|distance
operator|=
name|Placement
operator|.
name|getDistanceInMeters
argument_list|(
name|m
argument_list|,
name|initialPlacement
argument_list|,
name|assignedPlacement
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|lecture
operator|.
name|getInstructorConstraints
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|distance
operator|>
name|m
operator|.
name|getInstructorNoPreferenceLimit
argument_list|()
operator|&&
name|distance
operator|<=
name|m
operator|.
name|getInstructorDiscouragedLimit
argument_list|()
condition|)
block|{
name|tooFarForInstructors
operator|+=
name|PreferenceLevel
operator|.
name|sIntLevelDiscouraged
expr_stmt|;
block|}
if|else if
condition|(
name|distance
operator|>
name|m
operator|.
name|getInstructorDiscouragedLimit
argument_list|()
operator|&&
name|distance
operator|<=
name|m
operator|.
name|getInstructorProhibitedLimit
argument_list|()
condition|)
block|{
name|tooFarForInstructors
operator|+=
name|PreferenceLevel
operator|.
name|sIntLevelStronglyDiscouraged
expr_stmt|;
block|}
if|else if
condition|(
name|distance
operator|>
name|m
operator|.
name|getInstructorProhibitedLimit
argument_list|()
condition|)
block|{
name|tooFarForInstructors
operator|+=
name|PreferenceLevel
operator|.
name|sIntLevelProhibited
expr_stmt|;
block|}
block|}
if|if
condition|(
name|distance
operator|>
name|m
operator|.
name|minutes2meters
argument_list|(
literal|10
argument_list|)
condition|)
name|tooFarForStudents
operator|=
operator|(
name|int
operator|)
name|lecture
operator|.
name|classLimit
argument_list|(
name|assignment
argument_list|)
expr_stmt|;
name|Set
name|newStudentConflictsVect
init|=
name|lecture
operator|.
name|conflictStudents
argument_list|(
name|assignment
argument_list|,
name|assignedPlacement
argument_list|)
decl_stmt|;
name|Set
name|initialStudentConflicts
init|=
name|lecture
operator|.
name|initialStudentConflicts
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|e
init|=
name|newStudentConflictsVect
operator|.
name|iterator
argument_list|()
init|;
name|e
operator|.
name|hasNext
argument_list|()
condition|;
control|)
if|if
condition|(
operator|!
name|initialStudentConflicts
operator|.
name|contains
argument_list|(
name|e
operator|.
name|next
argument_list|()
argument_list|)
condition|)
name|newStudentConflicts
operator|++
expr_stmt|;
for|for
control|(
name|InstructorConstraint
name|ic
range|:
name|lecture
operator|.
name|getInstructorConstraints
argument_list|()
control|)
block|{
for|for
control|(
name|Lecture
name|lect
range|:
name|ic
operator|.
name|variables
argument_list|()
control|)
block|{
if|if
condition|(
name|lect
operator|.
name|equals
argument_list|(
name|lecture
argument_list|)
condition|)
continue|continue;
name|int
name|initialPreference
init|=
operator|(
name|lect
operator|.
name|getInitialAssignment
argument_list|()
operator|==
literal|null
condition|?
name|PreferenceLevel
operator|.
name|sIntLevelNeutral
else|:
name|ic
operator|.
name|getDistancePreference
argument_list|(
name|initialPlacement
argument_list|,
operator|(
name|Placement
operator|)
name|lect
operator|.
name|getInitialAssignment
argument_list|()
argument_list|)
operator|)
decl_stmt|;
name|int
name|assignedPreference
init|=
operator|(
name|assignment
operator|.
name|getValue
argument_list|(
name|lect
argument_list|)
operator|==
literal|null
condition|?
name|PreferenceLevel
operator|.
name|sIntLevelNeutral
else|:
name|ic
operator|.
name|getDistancePreference
argument_list|(
name|assignedPlacement
argument_list|,
operator|(
name|Placement
operator|)
name|assignment
operator|.
name|getValue
argument_list|(
name|lect
argument_list|)
argument_list|)
operator|)
decl_stmt|;
name|deltaInstructorDistancePreferences
operator|+=
operator|(
name|assignedPreference
operator|-
name|initialPreference
operator|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|ClassAssignmentDetails
name|getClazz
parameter_list|()
block|{
return|return
name|iDetail
return|;
block|}
block|}
block|}
end_class

end_unit

