begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|curricula
operator|.
name|students
package|;
end_package

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|heuristics
operator|.
name|NeighbourSelection
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
name|model
operator|.
name|Neighbour
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
name|solution
operator|.
name|Solution
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
name|DataProperties
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CurSimpleMove
implements|implements
name|NeighbourSelection
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
block|{
specifier|private
name|CurVariableSelection
name|iVariableSelection
decl_stmt|;
specifier|private
name|CurValueSelection
name|iValueSelection
decl_stmt|;
specifier|protected
name|CurSimpleMove
parameter_list|(
name|DataProperties
name|config
parameter_list|)
block|{
name|iVariableSelection
operator|=
operator|new
name|CurVariableSelection
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|iValueSelection
operator|=
operator|new
name|CurValueSelection
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|init
parameter_list|(
name|Solver
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|solver
parameter_list|)
block|{
block|}
annotation|@
name|Override
specifier|public
name|Neighbour
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|selectNeighbour
parameter_list|(
name|Solution
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|solution
parameter_list|)
block|{
name|CurVariable
name|var
init|=
name|iVariableSelection
operator|.
name|selectVariable
argument_list|(
name|solution
argument_list|)
decl_stmt|;
if|if
condition|(
name|var
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|CurValue
name|val
init|=
name|iValueSelection
operator|.
name|selectValueFast
argument_list|(
name|solution
argument_list|,
name|var
argument_list|)
decl_stmt|;
return|return
operator|(
name|val
operator|==
literal|null
condition|?
literal|null
else|:
operator|new
name|CurSimpleAssignment
argument_list|(
name|val
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

