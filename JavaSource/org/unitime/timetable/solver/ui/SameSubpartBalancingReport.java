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
name|Collection
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
name|Hashtable
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
name|SpreadConstraint
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
name|SameSubpartBalancingReport
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
name|SameSubpartBalancingReport
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
name|SpreadConstraint
name|spread
range|:
name|model
operator|.
name|getSpreadConstraints
argument_list|()
control|)
block|{
if|if
condition|(
name|spread
operator|.
name|getPenalty
argument_list|()
operator|==
literal|0
condition|)
continue|continue;
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|SameSubpartBalancingGroup
argument_list|(
name|solver
argument_list|,
name|spread
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
name|SameSubpartBalancingGroup
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
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|int
index|[]
index|[]
name|iLimit
decl_stmt|;
specifier|private
name|int
index|[]
index|[]
name|iUsage
decl_stmt|;
specifier|private
name|HashSet
index|[]
index|[]
name|iCourses
decl_stmt|;
specifier|public
name|SameSubpartBalancingGroup
parameter_list|(
name|Solver
name|solver
parameter_list|,
name|SpreadConstraint
name|spread
parameter_list|)
block|{
name|iName
operator|=
name|spread
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iLimit
operator|=
operator|new
name|int
index|[
name|Constants
operator|.
name|SLOTS_PER_DAY_NO_EVENINGS
index|]
index|[
name|Constants
operator|.
name|NR_DAYS_WEEK
index|]
expr_stmt|;
name|iUsage
operator|=
operator|new
name|int
index|[
name|Constants
operator|.
name|SLOTS_PER_DAY_NO_EVENINGS
index|]
index|[
name|Constants
operator|.
name|NR_DAYS_WEEK
index|]
expr_stmt|;
name|iCourses
operator|=
operator|new
name|HashSet
index|[
name|Constants
operator|.
name|SLOTS_PER_DAY_NO_EVENINGS
index|]
index|[
name|Constants
operator|.
name|NR_DAYS_WEEK
index|]
expr_stmt|;
name|Hashtable
name|detailCache
init|=
operator|new
name|Hashtable
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
name|Constants
operator|.
name|SLOTS_PER_DAY_NO_EVENINGS
condition|;
name|i
operator|++
control|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|;
name|j
operator|++
control|)
block|{
name|iLimit
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
name|spread
operator|.
name|getMaxCourses
argument_list|()
index|[
name|i
index|]
index|[
name|j
index|]
expr_stmt|;
name|iUsage
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
name|spread
operator|.
name|getNrCourses
argument_list|()
index|[
name|i
index|]
index|[
name|j
index|]
expr_stmt|;
name|iCourses
index|[
name|i
index|]
index|[
name|j
index|]
operator|=
operator|new
name|HashSet
argument_list|(
name|spread
operator|.
name|getCourses
argument_list|()
index|[
name|i
index|]
index|[
name|j
index|]
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Placement
name|placement
range|:
name|spread
operator|.
name|getCourses
argument_list|()
index|[
name|i
index|]
index|[
name|j
index|]
control|)
block|{
name|Lecture
name|lecture
init|=
operator|(
name|Lecture
operator|)
name|placement
operator|.
name|variable
argument_list|()
decl_stmt|;
name|ClassAssignmentDetails
name|ca
init|=
operator|(
name|ClassAssignmentDetails
operator|)
name|detailCache
operator|.
name|get
argument_list|(
name|lecture
operator|.
name|getClassId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ca
operator|==
literal|null
condition|)
block|{
name|ca
operator|=
operator|new
name|ClassAssignmentDetails
argument_list|(
name|solver
argument_list|,
name|lecture
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|detailCache
operator|.
name|put
argument_list|(
name|lecture
operator|.
name|getClassId
argument_list|()
argument_list|,
name|ca
argument_list|)
expr_stmt|;
block|}
name|iCourses
index|[
name|i
index|]
index|[
name|j
index|]
operator|.
name|add
argument_list|(
name|ca
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|int
name|getLimit
parameter_list|(
name|int
name|slot
parameter_list|,
name|int
name|day
parameter_list|)
block|{
if|if
condition|(
name|slot
argument_list|<
name|Constants
operator|.
name|DAY_SLOTS_FIRST
operator|||
name|slot
argument_list|>
name|Constants
operator|.
name|DAY_SLOTS_LAST
condition|)
return|return
literal|0
return|;
if|if
condition|(
name|day
operator|>=
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|)
return|return
literal|0
return|;
return|return
name|iLimit
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
return|;
block|}
specifier|public
name|int
name|getUsage
parameter_list|(
name|int
name|slot
parameter_list|,
name|int
name|day
parameter_list|)
block|{
if|if
condition|(
name|slot
argument_list|<
name|Constants
operator|.
name|DAY_SLOTS_FIRST
operator|||
name|slot
argument_list|>
name|Constants
operator|.
name|DAY_SLOTS_LAST
condition|)
return|return
literal|0
return|;
if|if
condition|(
name|day
operator|>=
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|)
return|return
literal|0
return|;
return|return
name|iUsage
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
return|;
block|}
specifier|public
name|Collection
name|getClasses
parameter_list|(
name|int
name|slot
parameter_list|,
name|int
name|day
parameter_list|)
block|{
if|if
condition|(
name|slot
argument_list|<
name|Constants
operator|.
name|DAY_SLOTS_FIRST
operator|||
name|slot
argument_list|>
name|Constants
operator|.
name|DAY_SLOTS_LAST
condition|)
return|return
operator|new
name|HashSet
argument_list|(
literal|0
argument_list|)
return|;
if|if
condition|(
name|day
operator|>=
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|)
return|return
operator|new
name|HashSet
argument_list|(
literal|0
argument_list|)
return|;
return|return
name|iCourses
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
return|;
block|}
specifier|public
name|int
name|getLimit
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
if|if
condition|(
name|slot
argument_list|<
name|Constants
operator|.
name|DAY_SLOTS_FIRST
operator|||
name|slot
argument_list|>
name|Constants
operator|.
name|DAY_SLOTS_LAST
condition|)
return|return
literal|0
return|;
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|day
init|=
literal|0
init|;
name|day
operator|<
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|;
name|day
operator|++
control|)
name|ret
operator|+=
name|iLimit
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|int
name|getUsage
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
if|if
condition|(
name|slot
argument_list|<
name|Constants
operator|.
name|DAY_SLOTS_FIRST
operator|||
name|slot
argument_list|>
name|Constants
operator|.
name|DAY_SLOTS_LAST
condition|)
return|return
literal|0
return|;
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|day
init|=
literal|0
init|;
name|day
operator|<
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|;
name|day
operator|++
control|)
name|ret
operator|+=
name|iUsage
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|Collection
name|getClasses
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
if|if
condition|(
name|slot
argument_list|<
name|Constants
operator|.
name|DAY_SLOTS_FIRST
operator|||
name|slot
argument_list|>
name|Constants
operator|.
name|DAY_SLOTS_LAST
condition|)
return|return
operator|new
name|HashSet
argument_list|(
literal|0
argument_list|)
return|;
name|HashSet
name|ret
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|day
init|=
literal|0
init|;
name|day
operator|<
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|;
name|day
operator|++
control|)
name|ret
operator|.
name|addAll
argument_list|(
name|iCourses
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|int
name|getExcess
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
if|if
condition|(
name|slot
argument_list|<
name|Constants
operator|.
name|DAY_SLOTS_FIRST
operator|||
name|slot
argument_list|>
name|Constants
operator|.
name|DAY_SLOTS_LAST
condition|)
return|return
literal|0
return|;
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|day
init|=
literal|0
init|;
name|day
operator|<
name|Constants
operator|.
name|NR_DAYS_WEEK
condition|;
name|day
operator|++
control|)
name|ret
operator|+=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|iUsage
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
operator|-
name|iLimit
index|[
name|slot
operator|-
name|Constants
operator|.
name|DAY_SLOTS_FIRST
index|]
index|[
name|day
index|]
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
block|}
end_class

end_unit

