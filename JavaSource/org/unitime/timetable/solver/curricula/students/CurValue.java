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
name|model
operator|.
name|Value
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CurValue
extends|extends
name|Value
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
block|{
specifier|private
name|CurStudent
name|iStudent
decl_stmt|;
specifier|public
name|CurValue
parameter_list|(
name|CurVariable
name|course
parameter_list|,
name|CurStudent
name|student
parameter_list|)
block|{
name|super
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|iStudent
operator|=
name|student
expr_stmt|;
block|}
specifier|public
name|CurStudent
name|getStudent
parameter_list|()
block|{
return|return
name|iStudent
return|;
block|}
specifier|public
name|double
name|toDouble
parameter_list|(
name|Assignment
argument_list|<
name|CurVariable
argument_list|,
name|CurValue
argument_list|>
name|assignment
parameter_list|)
block|{
name|CurValue
name|current
init|=
name|assignment
operator|.
name|getValue
argument_list|(
name|variable
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|current
operator|==
literal|null
condition|)
return|return
name|variable
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|penalty
argument_list|(
name|assignment
argument_list|,
name|iStudent
argument_list|)
return|;
return|return
name|variable
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|penalty
argument_list|(
name|assignment
argument_list|,
name|iStudent
argument_list|,
name|current
operator|.
name|getStudent
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

