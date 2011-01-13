begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|server
package|;
end_package

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
name|net
operator|.
name|sf
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
name|extension
operator|.
name|DistanceConflict
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
name|extension
operator|.
name|TimeOverlapsCounter
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
name|heuristics
operator|.
name|selection
operator|.
name|BranchBoundSelection
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
name|Config
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
name|CourseRequest
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
name|Subpart
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SuggestionSelection
extends|extends
name|BranchBoundSelection
block|{
specifier|protected
name|Set
argument_list|<
name|FreeTimeRequest
argument_list|>
name|iRequiredFreeTimes
decl_stmt|;
specifier|protected
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Config
argument_list|>
name|iRequiredConfig
init|=
operator|new
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Config
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Hashtable
argument_list|<
name|Subpart
argument_list|,
name|Section
argument_list|>
argument_list|>
name|iRequiredSection
init|=
operator|new
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Hashtable
argument_list|<
name|Subpart
argument_list|,
name|Section
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
name|iPreferredSections
init|=
literal|null
decl_stmt|;
specifier|public
name|SuggestionSelection
parameter_list|(
name|DataProperties
name|properties
parameter_list|,
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
name|preferredSections
parameter_list|,
name|Hashtable
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
name|requiredSections
parameter_list|,
name|Set
argument_list|<
name|FreeTimeRequest
argument_list|>
name|requiredFreeTimes
parameter_list|)
block|{
name|super
argument_list|(
name|properties
argument_list|)
expr_stmt|;
name|iRequiredFreeTimes
operator|=
name|requiredFreeTimes
expr_stmt|;
if|if
condition|(
name|requiredSections
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|CourseRequest
argument_list|,
name|Set
argument_list|<
name|Section
argument_list|>
argument_list|>
name|entry
range|:
name|requiredSections
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|Hashtable
argument_list|<
name|Subpart
argument_list|,
name|Section
argument_list|>
name|subSection
init|=
operator|new
name|Hashtable
argument_list|<
name|Subpart
argument_list|,
name|Section
argument_list|>
argument_list|()
decl_stmt|;
name|iRequiredSection
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|subSection
argument_list|)
expr_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|entry
operator|.
name|getValue
argument_list|()
control|)
block|{
if|if
condition|(
name|subSection
operator|.
name|isEmpty
argument_list|()
condition|)
name|iRequiredConfig
operator|.
name|put
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|section
operator|.
name|getSubpart
argument_list|()
operator|.
name|getConfig
argument_list|()
argument_list|)
expr_stmt|;
name|subSection
operator|.
name|put
argument_list|(
name|section
operator|.
name|getSubpart
argument_list|()
argument_list|,
name|section
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|iPreferredSections
operator|=
name|preferredSections
expr_stmt|;
block|}
specifier|public
name|Selection
name|getSelection
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
return|return
operator|new
name|Selection
argument_list|(
name|student
argument_list|)
return|;
block|}
specifier|public
class|class
name|Selection
extends|extends
name|BranchBoundSelection
operator|.
name|Selection
block|{
specifier|public
name|Selection
parameter_list|(
name|Student
name|student
parameter_list|)
block|{
name|super
argument_list|(
name|student
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAllowed
parameter_list|(
name|int
name|idx
parameter_list|,
name|Enrollment
name|enrollment
parameter_list|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|isCourseRequest
argument_list|()
condition|)
block|{
name|CourseRequest
name|request
init|=
operator|(
name|CourseRequest
operator|)
name|enrollment
operator|.
name|getRequest
argument_list|()
decl_stmt|;
name|Config
name|reqConfig
init|=
name|iRequiredConfig
operator|.
name|get
argument_list|(
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|reqConfig
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|reqConfig
operator|.
name|equals
argument_list|(
name|enrollment
operator|.
name|getConfig
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
name|Hashtable
argument_list|<
name|Subpart
argument_list|,
name|Section
argument_list|>
name|reqSections
init|=
name|iRequiredSection
operator|.
name|get
argument_list|(
name|request
argument_list|)
decl_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|enrollment
operator|.
name|getSections
argument_list|()
control|)
block|{
name|Section
name|reqSection
init|=
name|reqSections
operator|.
name|get
argument_list|(
name|section
operator|.
name|getSubpart
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|reqSection
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
operator|!
name|section
operator|.
name|equals
argument_list|(
name|reqSection
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
block|}
block|}
if|else if
condition|(
name|iRequiredFreeTimes
operator|.
name|contains
argument_list|(
name|enrollment
operator|.
name|getRequest
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|getAssignments
argument_list|()
operator|==
literal|null
operator|||
name|enrollment
operator|.
name|getAssignments
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|Enrollment
name|firstConflict
parameter_list|(
name|int
name|idx
parameter_list|,
name|Enrollment
name|enrollment
parameter_list|)
block|{
name|Enrollment
name|conflict
init|=
name|super
operator|.
name|firstConflict
argument_list|(
name|idx
argument_list|,
name|enrollment
argument_list|)
decl_stmt|;
if|if
condition|(
name|conflict
operator|!=
literal|null
condition|)
return|return
name|conflict
return|;
return|return
operator|(
name|isAllowed
argument_list|(
name|idx
argument_list|,
name|enrollment
argument_list|)
condition|?
literal|null
else|:
name|enrollment
operator|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|boolean
name|canLeaveUnassigned
parameter_list|(
name|Request
name|request
parameter_list|)
block|{
if|if
condition|(
name|request
operator|instanceof
name|CourseRequest
condition|)
block|{
if|if
condition|(
name|iRequiredConfig
operator|.
name|get
argument_list|(
name|request
argument_list|)
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
if|else if
condition|(
name|iRequiredFreeTimes
operator|.
name|contains
argument_list|(
name|request
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|protected
name|List
argument_list|<
name|Enrollment
argument_list|>
name|values
parameter_list|(
specifier|final
name|CourseRequest
name|request
parameter_list|)
block|{
return|return
name|super
operator|.
name|values
argument_list|(
name|request
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|double
name|getWeight
parameter_list|(
name|Enrollment
name|enrollment
parameter_list|,
name|Set
argument_list|<
name|DistanceConflict
operator|.
name|Conflict
argument_list|>
name|distanceConflicts
parameter_list|,
name|Set
argument_list|<
name|TimeOverlapsCounter
operator|.
name|Conflict
argument_list|>
name|timeOverlappingConflicts
parameter_list|)
block|{
name|double
name|weight
init|=
name|super
operator|.
name|getWeight
argument_list|(
name|enrollment
argument_list|,
name|distanceConflicts
argument_list|,
name|timeOverlappingConflicts
argument_list|)
decl_stmt|;
if|if
condition|(
name|enrollment
operator|.
name|isCourseRequest
argument_list|()
operator|&&
name|iPreferredSections
operator|!=
literal|null
condition|)
block|{
name|Set
argument_list|<
name|Section
argument_list|>
name|preferred
init|=
name|iPreferredSections
operator|.
name|get
argument_list|(
operator|(
name|CourseRequest
operator|)
name|enrollment
operator|.
name|getRequest
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|preferred
operator|!=
literal|null
operator|&&
operator|!
name|preferred
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|double
name|nrPreferred
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Section
name|section
range|:
name|enrollment
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|preferred
operator|.
name|contains
argument_list|(
name|section
argument_list|)
condition|)
name|nrPreferred
operator|++
expr_stmt|;
name|double
name|preferredFraction
init|=
name|nrPreferred
operator|/
name|preferred
operator|.
name|size
argument_list|()
decl_stmt|;
name|weight
operator|*=
literal|1.0
operator|+
literal|0.5
operator|*
name|preferredFraction
expr_stmt|;
comment|// add up to 50% for preferred sections
block|}
block|}
return|return
name|weight
return|;
block|}
annotation|@
name|Override
specifier|protected
name|double
name|getBound
parameter_list|(
name|Request
name|r
parameter_list|)
block|{
name|double
name|bound
init|=
name|super
operator|.
name|getBound
argument_list|(
name|r
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|instanceof
name|CourseRequest
condition|)
block|{
name|Set
argument_list|<
name|Section
argument_list|>
name|preferred
init|=
name|iPreferredSections
operator|.
name|get
argument_list|(
operator|(
name|CourseRequest
operator|)
name|r
argument_list|)
decl_stmt|;
if|if
condition|(
name|preferred
operator|!=
literal|null
operator|&&
operator|!
name|preferred
operator|.
name|isEmpty
argument_list|()
condition|)
name|bound
operator|*=
literal|1.5
expr_stmt|;
comment|// add 50% if can be preferred
block|}
return|return
name|bound
return|;
block|}
block|}
block|}
end_class

end_unit

