begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|solver
operator|.
name|multicriteria
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Enrollment
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
name|studentsct
operator|.
name|model
operator|.
name|FreeTimeRequest
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
name|studentsct
operator|.
name|model
operator|.
name|Request
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
name|studentsct
operator|.
name|model
operator|.
name|Section
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
name|studentsct
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
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|solver
operator|.
name|OnlineSectioningModel
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
name|onlinesectioning
operator|.
name|solver
operator|.
name|multicriteria
operator|.
name|MultiCriteriaBranchAndBoundSelection
operator|.
name|SelectionCriterion
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BestPenaltyCriterion
implements|implements
name|SelectionCriterion
block|{
specifier|private
name|Student
name|iStudent
decl_stmt|;
specifier|private
name|OnlineSectioningModel
name|iModel
decl_stmt|;
specifier|public
name|BestPenaltyCriterion
parameter_list|(
name|Student
name|student
parameter_list|,
name|OnlineSectioningModel
name|model
parameter_list|)
block|{
name|iStudent
operator|=
name|student
expr_stmt|;
name|iModel
operator|=
name|model
expr_stmt|;
block|}
specifier|private
name|Request
name|getRequest
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
operator|(
name|index
operator|<
literal|0
operator|||
name|index
operator|>=
name|iStudent
operator|.
name|getRequests
argument_list|()
operator|.
name|size
argument_list|()
condition|?
literal|null
else|:
name|iStudent
operator|.
name|getRequests
argument_list|()
operator|.
name|get
argument_list|(
name|index
argument_list|)
operator|)
return|;
block|}
specifier|private
name|boolean
name|isFreeTime
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|Request
name|r
init|=
name|getRequest
argument_list|(
name|index
argument_list|)
decl_stmt|;
return|return
name|r
operator|!=
literal|null
operator|&&
name|r
operator|instanceof
name|FreeTimeRequest
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Enrollment
index|[]
name|current
parameter_list|,
name|Enrollment
index|[]
name|best
parameter_list|)
block|{
if|if
condition|(
name|best
operator|==
literal|null
condition|)
return|return
operator|-
literal|1
return|;
comment|// 0. best priority& alternativity ignoring free time requests
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|current
operator|.
name|length
condition|;
name|idx
operator|++
control|)
block|{
if|if
condition|(
name|isFreeTime
argument_list|(
name|idx
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|!=
literal|null
operator|&&
name|best
index|[
name|idx
index|]
operator|.
name|getAssignments
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|current
index|[
name|idx
index|]
operator|==
literal|null
operator|||
name|current
index|[
name|idx
index|]
operator|.
name|getSections
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|1
return|;
comment|// higher priority request assigned
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|.
name|getPriority
argument_list|()
operator|<
name|current
index|[
name|idx
index|]
operator|.
name|getPriority
argument_list|()
condition|)
return|return
literal|1
return|;
comment|// less alternative request assigned
block|}
else|else
block|{
if|if
condition|(
name|current
index|[
name|idx
index|]
operator|!=
literal|null
operator|&&
name|current
index|[
name|idx
index|]
operator|.
name|getAssignments
argument_list|()
operator|!=
literal|null
condition|)
return|return
operator|-
literal|1
return|;
comment|// higher priority request assigned
block|}
block|}
comment|// 1. minimize number of penalties
name|int
name|bestPenalties
init|=
literal|0
decl_stmt|,
name|currentPenalties
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|current
operator|.
name|length
condition|;
name|idx
operator|++
control|)
block|{
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|!=
literal|null
operator|&&
name|best
index|[
name|idx
index|]
operator|.
name|getAssignments
argument_list|()
operator|!=
literal|null
operator|&&
name|best
index|[
name|idx
index|]
operator|.
name|isCourseRequest
argument_list|()
condition|)
block|{
for|for
control|(
name|Section
name|section
range|:
name|best
index|[
name|idx
index|]
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|iModel
operator|.
name|isOverExpected
argument_list|(
name|section
argument_list|,
name|best
index|[
name|idx
index|]
operator|.
name|getRequest
argument_list|()
argument_list|)
condition|)
name|bestPenalties
operator|++
expr_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|current
index|[
name|idx
index|]
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|iModel
operator|.
name|isOverExpected
argument_list|(
name|section
argument_list|,
name|current
index|[
name|idx
index|]
operator|.
name|getRequest
argument_list|()
argument_list|)
condition|)
name|currentPenalties
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|currentPenalties
operator|<
name|bestPenalties
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
name|bestPenalties
operator|<
name|currentPenalties
condition|)
return|return
literal|1
return|;
return|return
literal|0
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|canImprove
parameter_list|(
name|int
name|maxIdx
parameter_list|,
name|Enrollment
index|[]
name|current
parameter_list|,
name|Enrollment
index|[]
name|best
parameter_list|)
block|{
comment|// 0. best priority& alternativity ignoring free time requests
name|int
name|alt
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|current
operator|.
name|length
condition|;
name|idx
operator|++
control|)
block|{
if|if
condition|(
name|isFreeTime
argument_list|(
name|idx
argument_list|)
condition|)
continue|continue;
name|Request
name|request
init|=
name|getRequest
argument_list|(
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|idx
operator|<
name|maxIdx
condition|)
block|{
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|current
index|[
name|idx
index|]
operator|==
literal|null
condition|)
return|return
literal|false
return|;
comment|// higher priority request assigned
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|.
name|getPriority
argument_list|()
operator|<
name|current
index|[
name|idx
index|]
operator|.
name|getPriority
argument_list|()
condition|)
return|return
literal|false
return|;
comment|// less alternative request assigned
if|if
condition|(
name|request
operator|.
name|isAlternative
argument_list|()
condition|)
name|alt
operator|--
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|current
index|[
name|idx
index|]
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
comment|// higher priority request assigned
if|if
condition|(
operator|!
name|request
operator|.
name|isAlternative
argument_list|()
condition|)
name|alt
operator|++
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|.
name|getPriority
argument_list|()
operator|>
literal|0
condition|)
return|return
literal|true
return|;
comment|// alternativity can be improved
block|}
else|else
block|{
if|if
condition|(
operator|!
name|request
operator|.
name|isAlternative
argument_list|()
operator|||
name|alt
operator|>
literal|0
condition|)
return|return
literal|true
return|;
comment|// priority can be improved
block|}
block|}
block|}
comment|// 1. maximize number of penalties
name|int
name|bestPenalties
init|=
literal|0
decl_stmt|,
name|currentPenalties
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|current
operator|.
name|length
condition|;
name|idx
operator|++
control|)
block|{
if|if
condition|(
name|best
index|[
name|idx
index|]
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Section
name|section
range|:
name|best
index|[
name|idx
index|]
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|iModel
operator|.
name|isOverExpected
argument_list|(
name|section
argument_list|,
name|best
index|[
name|idx
index|]
operator|.
name|getRequest
argument_list|()
argument_list|)
condition|)
name|bestPenalties
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|current
index|[
name|idx
index|]
operator|!=
literal|null
operator|&&
name|idx
operator|<
name|maxIdx
condition|)
block|{
for|for
control|(
name|Section
name|section
range|:
name|current
index|[
name|idx
index|]
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|iModel
operator|.
name|isOverExpected
argument_list|(
name|section
argument_list|,
name|current
index|[
name|idx
index|]
operator|.
name|getRequest
argument_list|()
argument_list|)
condition|)
name|currentPenalties
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|currentPenalties
operator|<
name|bestPenalties
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|bestPenalties
operator|<
name|currentPenalties
condition|)
return|return
literal|false
return|;
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|double
name|getTotalWeight
parameter_list|(
name|Enrollment
index|[]
name|assignment
parameter_list|)
block|{
return|return
literal|0.0
return|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Enrollment
name|e1
parameter_list|,
name|Enrollment
name|e2
parameter_list|)
block|{
comment|// 1. alternativity
if|if
condition|(
name|e1
operator|.
name|getPriority
argument_list|()
operator|<
name|e2
operator|.
name|getPriority
argument_list|()
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
name|e1
operator|.
name|getPriority
argument_list|()
operator|>
name|e2
operator|.
name|getPriority
argument_list|()
condition|)
return|return
literal|1
return|;
comment|// 2. maximize number of penalties
name|int
name|p1
init|=
literal|0
decl_stmt|,
name|p2
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|e1
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|iModel
operator|.
name|isOverExpected
argument_list|(
name|section
argument_list|,
name|e1
operator|.
name|getRequest
argument_list|()
argument_list|)
condition|)
name|p1
operator|++
expr_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|e2
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|iModel
operator|.
name|isOverExpected
argument_list|(
name|section
argument_list|,
name|e2
operator|.
name|getRequest
argument_list|()
argument_list|)
condition|)
name|p2
operator|++
expr_stmt|;
if|if
condition|(
name|p1
operator|<
name|p2
condition|)
return|return
operator|-
literal|1
return|;
if|if
condition|(
name|p2
operator|<
name|p1
condition|)
return|return
literal|1
return|;
return|return
literal|0
return|;
block|}
block|}
end_class

end_unit

