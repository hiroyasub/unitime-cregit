begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2010 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
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
name|Department
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
name|Session
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
name|Solution
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
name|SolverGroup
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
name|TimetableManager
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSolverGroup
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
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|String
name|iAbbv
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|TimetableManager
argument_list|>
name|iTimetableManagers
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Department
argument_list|>
name|iDepartments
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Solution
argument_list|>
name|iSolutions
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ABBV
init|=
literal|"abbv"
decl_stmt|;
specifier|public
name|BaseSolverGroup
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseSolverGroup
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
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
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getAbbv
parameter_list|()
block|{
return|return
name|iAbbv
return|;
block|}
specifier|public
name|void
name|setAbbv
parameter_list|(
name|String
name|abbv
parameter_list|)
block|{
name|iAbbv
operator|=
name|abbv
expr_stmt|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|TimetableManager
argument_list|>
name|getTimetableManagers
parameter_list|()
block|{
return|return
name|iTimetableManagers
return|;
block|}
specifier|public
name|void
name|setTimetableManagers
parameter_list|(
name|Set
argument_list|<
name|TimetableManager
argument_list|>
name|timetableManagers
parameter_list|)
block|{
name|iTimetableManagers
operator|=
name|timetableManagers
expr_stmt|;
block|}
specifier|public
name|void
name|addTotimetableManagers
parameter_list|(
name|TimetableManager
name|timetableManager
parameter_list|)
block|{
if|if
condition|(
name|iTimetableManagers
operator|==
literal|null
condition|)
name|iTimetableManagers
operator|=
operator|new
name|HashSet
argument_list|<
name|TimetableManager
argument_list|>
argument_list|()
expr_stmt|;
name|iTimetableManagers
operator|.
name|add
argument_list|(
name|timetableManager
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Department
argument_list|>
name|getDepartments
parameter_list|()
block|{
return|return
name|iDepartments
return|;
block|}
specifier|public
name|void
name|setDepartments
parameter_list|(
name|Set
argument_list|<
name|Department
argument_list|>
name|departments
parameter_list|)
block|{
name|iDepartments
operator|=
name|departments
expr_stmt|;
block|}
specifier|public
name|void
name|addTodepartments
parameter_list|(
name|Department
name|department
parameter_list|)
block|{
if|if
condition|(
name|iDepartments
operator|==
literal|null
condition|)
name|iDepartments
operator|=
operator|new
name|HashSet
argument_list|<
name|Department
argument_list|>
argument_list|()
expr_stmt|;
name|iDepartments
operator|.
name|add
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Solution
argument_list|>
name|getSolutions
parameter_list|()
block|{
return|return
name|iSolutions
return|;
block|}
specifier|public
name|void
name|setSolutions
parameter_list|(
name|Set
argument_list|<
name|Solution
argument_list|>
name|solutions
parameter_list|)
block|{
name|iSolutions
operator|=
name|solutions
expr_stmt|;
block|}
specifier|public
name|void
name|addTosolutions
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
if|if
condition|(
name|iSolutions
operator|==
literal|null
condition|)
name|iSolutions
operator|=
operator|new
name|HashSet
argument_list|<
name|Solution
argument_list|>
argument_list|()
expr_stmt|;
name|iSolutions
operator|.
name|add
argument_list|(
name|solution
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|SolverGroup
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|SolverGroup
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|SolverGroup
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"SolverGroup["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|getName
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"SolverGroup["
operator|+
literal|"\n	Abbv: "
operator|+
name|getAbbv
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

