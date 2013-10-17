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
name|Set
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
name|JenrlConstraint
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
name|StudentConflictsReport
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
name|StudentConflictsReport
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
name|JenrlConstraint
name|jenrl
range|:
name|model
operator|.
name|getJenrlConstraints
argument_list|()
control|)
block|{
if|if
condition|(
name|jenrl
operator|.
name|isInConflict
argument_list|()
operator|&&
operator|!
name|jenrl
operator|.
name|isToBeIgnored
argument_list|()
condition|)
name|iGroups
operator|.
name|add
argument_list|(
operator|new
name|JenrlInfo
argument_list|(
name|solver
argument_list|,
name|jenrl
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Lecture
name|lecture
range|:
name|model
operator|.
name|assignedVariables
argument_list|()
control|)
block|{
name|iGroups
operator|.
name|addAll
argument_list|(
name|JenrlInfo
operator|.
name|getCommitedJenrlInfos
argument_list|(
name|solver
argument_list|,
name|lecture
argument_list|)
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|model
operator|.
name|constantVariables
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|Lecture
name|lecture
range|:
name|model
operator|.
name|constantVariables
argument_list|()
control|)
block|{
if|if
condition|(
name|lecture
operator|.
name|getAssignment
argument_list|()
operator|!=
literal|null
condition|)
name|iGroups
operator|.
name|addAll
argument_list|(
name|JenrlInfo
operator|.
name|getCommitedJenrlInfos
argument_list|(
name|solver
argument_list|,
name|lecture
argument_list|)
operator|.
name|values
argument_list|()
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
block|}
end_class

end_unit

