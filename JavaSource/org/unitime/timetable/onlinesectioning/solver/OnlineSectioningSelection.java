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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|OnlineSectioningSelection
block|{
specifier|public
name|void
name|setModel
parameter_list|(
name|OnlineSectioningModel
name|model
parameter_list|)
function_decl|;
specifier|public
name|void
name|setPreferredSections
parameter_list|(
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
parameter_list|)
function_decl|;
specifier|public
name|void
name|setRequiredSections
parameter_list|(
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
parameter_list|)
function_decl|;
specifier|public
name|void
name|setRequiredFreeTimes
parameter_list|(
name|Set
argument_list|<
name|FreeTimeRequest
argument_list|>
name|requiredFreeTimes
parameter_list|)
function_decl|;
specifier|public
name|BranchBoundSelection
operator|.
name|BranchBoundNeighbour
name|select
parameter_list|(
name|Student
name|student
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

