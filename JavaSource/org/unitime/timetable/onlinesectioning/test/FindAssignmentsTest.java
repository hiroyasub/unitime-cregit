begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|test
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
name|List
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
name|ClassAssignmentInterface
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
name|CourseRequestInterface
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
name|_RootDAO
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
name|OnlineSectioningServer
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
name|OnlineSectioningTestFwk
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
name|FindAssignmentAction
import|;
end_import

begin_class
specifier|public
class|class
name|FindAssignmentsTest
extends|extends
name|OnlineSectioningTestFwk
block|{
static|static
block|{
name|System
operator|.
name|setProperty
argument_list|(
literal|"jprof"
argument_list|,
literal|"cpu"
argument_list|)
expr_stmt|;
comment|// wall or cpu
block|}
specifier|public
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
parameter_list|()
block|{
comment|// getServer().getConfig().setProperty("Neighbour.BranchAndBoundTimeout", "-1");
comment|// getServer().getConfig().setProperty("StudentWeights.NoTimeFactor", "0.0");
comment|// getServer().getConfig().setProperty("StudentWeights.SelectionFactor", "0.0");
comment|// getServer().getConfig().setProperty("StudentWeights.PenaltyFactor", "0.0");
comment|// getServer().getConfig().setProperty("StudentWeights.AvgPenaltyFactor", "0.0");
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Operation
argument_list|>
name|operations
init|=
operator|new
name|ArrayList
argument_list|<
name|Operation
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|Long
name|studentId
range|:
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s.uniqueId from Student s where s.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getServer
argument_list|()
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|CourseRequestInterface
name|request
init|=
name|getServer
argument_list|()
operator|.
name|getRequest
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|==
literal|null
operator|||
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|operations
operator|.
name|add
argument_list|(
operator|new
name|Operation
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|double
name|execute
parameter_list|(
name|OnlineSectioningServer
name|s
parameter_list|)
block|{
name|CourseRequestInterface
name|request
init|=
name|s
operator|.
name|getRequest
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|request
operator|!=
literal|null
operator|&&
operator|!
name|request
operator|.
name|getCourses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|FindAssignmentAction
name|action
init|=
operator|new
name|FindAssignmentAction
argument_list|(
name|request
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|ClassAssignment
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
name|s
operator|.
name|execute
argument_list|(
name|action
argument_list|)
expr_stmt|;
return|return
name|action
operator|.
name|value
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|1.0
return|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|operations
return|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
operator|new
name|FindAssignmentsTest
argument_list|()
operator|.
name|test
argument_list|(
operator|-
literal|1
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|20
argument_list|,
literal|50
argument_list|,
literal|100
argument_list|,
literal|250
argument_list|,
literal|1000
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

