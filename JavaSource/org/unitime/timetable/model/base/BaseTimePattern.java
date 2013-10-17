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
name|TimePattern
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
name|TimePatternDays
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
name|TimePatternTime
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseTimePattern
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
name|Integer
name|iMinPerMtg
decl_stmt|;
specifier|private
name|Integer
name|iSlotsPerMtg
decl_stmt|;
specifier|private
name|Integer
name|iNrMeetings
decl_stmt|;
specifier|private
name|Integer
name|iBreakTime
decl_stmt|;
specifier|private
name|Integer
name|iType
decl_stmt|;
specifier|private
name|Boolean
name|iVisible
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|TimePatternTime
argument_list|>
name|iTimes
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|TimePatternDays
argument_list|>
name|iDays
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Department
argument_list|>
name|iDepartments
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
name|PROP_MINS_PMT
init|=
literal|"minPerMtg"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SLOTS_PMT
init|=
literal|"slotsPerMtg"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NR_MTGS
init|=
literal|"nrMeetings"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_BREAK_TIME
init|=
literal|"breakTime"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_TYPE
init|=
literal|"type"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_VISIBLE
init|=
literal|"visible"
decl_stmt|;
specifier|public
name|BaseTimePattern
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseTimePattern
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
name|Integer
name|getMinPerMtg
parameter_list|()
block|{
return|return
name|iMinPerMtg
return|;
block|}
specifier|public
name|void
name|setMinPerMtg
parameter_list|(
name|Integer
name|minPerMtg
parameter_list|)
block|{
name|iMinPerMtg
operator|=
name|minPerMtg
expr_stmt|;
block|}
specifier|public
name|Integer
name|getSlotsPerMtg
parameter_list|()
block|{
return|return
name|iSlotsPerMtg
return|;
block|}
specifier|public
name|void
name|setSlotsPerMtg
parameter_list|(
name|Integer
name|slotsPerMtg
parameter_list|)
block|{
name|iSlotsPerMtg
operator|=
name|slotsPerMtg
expr_stmt|;
block|}
specifier|public
name|Integer
name|getNrMeetings
parameter_list|()
block|{
return|return
name|iNrMeetings
return|;
block|}
specifier|public
name|void
name|setNrMeetings
parameter_list|(
name|Integer
name|nrMeetings
parameter_list|)
block|{
name|iNrMeetings
operator|=
name|nrMeetings
expr_stmt|;
block|}
specifier|public
name|Integer
name|getBreakTime
parameter_list|()
block|{
return|return
name|iBreakTime
return|;
block|}
specifier|public
name|void
name|setBreakTime
parameter_list|(
name|Integer
name|breakTime
parameter_list|)
block|{
name|iBreakTime
operator|=
name|breakTime
expr_stmt|;
block|}
specifier|public
name|Integer
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|Integer
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isVisible
parameter_list|()
block|{
return|return
name|iVisible
return|;
block|}
specifier|public
name|Boolean
name|getVisible
parameter_list|()
block|{
return|return
name|iVisible
return|;
block|}
specifier|public
name|void
name|setVisible
parameter_list|(
name|Boolean
name|visible
parameter_list|)
block|{
name|iVisible
operator|=
name|visible
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
name|TimePatternTime
argument_list|>
name|getTimes
parameter_list|()
block|{
return|return
name|iTimes
return|;
block|}
specifier|public
name|void
name|setTimes
parameter_list|(
name|Set
argument_list|<
name|TimePatternTime
argument_list|>
name|times
parameter_list|)
block|{
name|iTimes
operator|=
name|times
expr_stmt|;
block|}
specifier|public
name|void
name|addTotimes
parameter_list|(
name|TimePatternTime
name|timePatternTime
parameter_list|)
block|{
if|if
condition|(
name|iTimes
operator|==
literal|null
condition|)
name|iTimes
operator|=
operator|new
name|HashSet
argument_list|<
name|TimePatternTime
argument_list|>
argument_list|()
expr_stmt|;
name|iTimes
operator|.
name|add
argument_list|(
name|timePatternTime
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|TimePatternDays
argument_list|>
name|getDays
parameter_list|()
block|{
return|return
name|iDays
return|;
block|}
specifier|public
name|void
name|setDays
parameter_list|(
name|Set
argument_list|<
name|TimePatternDays
argument_list|>
name|days
parameter_list|)
block|{
name|iDays
operator|=
name|days
expr_stmt|;
block|}
specifier|public
name|void
name|addTodays
parameter_list|(
name|TimePatternDays
name|timePatternDays
parameter_list|)
block|{
if|if
condition|(
name|iDays
operator|==
literal|null
condition|)
name|iDays
operator|=
operator|new
name|HashSet
argument_list|<
name|TimePatternDays
argument_list|>
argument_list|()
expr_stmt|;
name|iDays
operator|.
name|add
argument_list|(
name|timePatternDays
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
name|TimePattern
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
name|TimePattern
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
name|TimePattern
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
literal|"TimePattern["
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
literal|"TimePattern["
operator|+
literal|"\n	BreakTime: "
operator|+
name|getBreakTime
argument_list|()
operator|+
literal|"\n	MinPerMtg: "
operator|+
name|getMinPerMtg
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	NrMeetings: "
operator|+
name|getNrMeetings
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
argument_list|()
operator|+
literal|"\n	SlotsPerMtg: "
operator|+
name|getSlotsPerMtg
argument_list|()
operator|+
literal|"\n	Type: "
operator|+
name|getType
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"\n	Visible: "
operator|+
name|getVisible
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

