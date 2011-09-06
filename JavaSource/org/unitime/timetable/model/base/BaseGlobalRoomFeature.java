begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|GlobalRoomFeature
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
name|RoomFeature
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

begin_class
specifier|public
specifier|abstract
class|class
name|BaseGlobalRoomFeature
extends|extends
name|RoomFeature
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
name|String
name|iSisReference
decl_stmt|;
specifier|private
name|String
name|iSisValue
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SIS_REFERENCE
init|=
literal|"sisReference"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SIS_VALUE
init|=
literal|"sisValue"
decl_stmt|;
specifier|public
name|BaseGlobalRoomFeature
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseGlobalRoomFeature
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
name|String
name|getSisReference
parameter_list|()
block|{
return|return
name|iSisReference
return|;
block|}
specifier|public
name|void
name|setSisReference
parameter_list|(
name|String
name|sisReference
parameter_list|)
block|{
name|iSisReference
operator|=
name|sisReference
expr_stmt|;
block|}
specifier|public
name|String
name|getSisValue
parameter_list|()
block|{
return|return
name|iSisValue
return|;
block|}
specifier|public
name|void
name|setSisValue
parameter_list|(
name|String
name|sisValue
parameter_list|)
block|{
name|iSisValue
operator|=
name|sisValue
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
name|GlobalRoomFeature
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
name|GlobalRoomFeature
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
name|GlobalRoomFeature
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
literal|"GlobalRoomFeature["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|getLabel
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
literal|"GlobalRoomFeature["
operator|+
literal|"\n	Abbv: "
operator|+
name|getAbbv
argument_list|()
operator|+
literal|"\n	Label: "
operator|+
name|getLabel
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
argument_list|()
operator|+
literal|"\n	SisReference: "
operator|+
name|getSisReference
argument_list|()
operator|+
literal|"\n	SisValue: "
operator|+
name|getSisValue
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

