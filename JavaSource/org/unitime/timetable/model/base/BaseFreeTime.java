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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|FreeTime
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseFreeTime
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
name|iDayCode
decl_stmt|;
specifier|private
name|Integer
name|iStartSlot
decl_stmt|;
specifier|private
name|Integer
name|iLength
decl_stmt|;
specifier|private
name|Integer
name|iCategory
decl_stmt|;
specifier|private
name|Session
name|iSession
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
name|PROP_DAY_CODE
init|=
literal|"dayCode"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_START_SLOT
init|=
literal|"startSlot"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LENGTH
init|=
literal|"length"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CATEGORY
init|=
literal|"category"
decl_stmt|;
specifier|public
name|BaseFreeTime
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseFreeTime
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
name|getDayCode
parameter_list|()
block|{
return|return
name|iDayCode
return|;
block|}
specifier|public
name|void
name|setDayCode
parameter_list|(
name|Integer
name|dayCode
parameter_list|)
block|{
name|iDayCode
operator|=
name|dayCode
expr_stmt|;
block|}
specifier|public
name|Integer
name|getStartSlot
parameter_list|()
block|{
return|return
name|iStartSlot
return|;
block|}
specifier|public
name|void
name|setStartSlot
parameter_list|(
name|Integer
name|startSlot
parameter_list|)
block|{
name|iStartSlot
operator|=
name|startSlot
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLength
parameter_list|()
block|{
return|return
name|iLength
return|;
block|}
specifier|public
name|void
name|setLength
parameter_list|(
name|Integer
name|length
parameter_list|)
block|{
name|iLength
operator|=
name|length
expr_stmt|;
block|}
specifier|public
name|Integer
name|getCategory
parameter_list|()
block|{
return|return
name|iCategory
return|;
block|}
specifier|public
name|void
name|setCategory
parameter_list|(
name|Integer
name|category
parameter_list|)
block|{
name|iCategory
operator|=
name|category
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
name|FreeTime
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
name|FreeTime
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
name|FreeTime
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
literal|"FreeTime["
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
literal|"FreeTime["
operator|+
literal|"\n	Category: "
operator|+
name|getCategory
argument_list|()
operator|+
literal|"\n	DayCode: "
operator|+
name|getDayCode
argument_list|()
operator|+
literal|"\n	Length: "
operator|+
name|getLength
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
literal|"\n	StartSlot: "
operator|+
name|getStartSlot
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

