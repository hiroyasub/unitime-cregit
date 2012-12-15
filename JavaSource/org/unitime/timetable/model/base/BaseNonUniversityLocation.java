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
name|Location
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
name|NonUniversityLocation
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
name|RoomType
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|BaseNonUniversityLocation
extends|extends
name|Location
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
name|iName
decl_stmt|;
specifier|private
name|RoomType
name|iRoomType
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
name|BaseNonUniversityLocation
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseNonUniversityLocation
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
name|RoomType
name|getRoomType
parameter_list|()
block|{
return|return
name|iRoomType
return|;
block|}
specifier|public
name|void
name|setRoomType
parameter_list|(
name|RoomType
name|roomType
parameter_list|)
block|{
name|iRoomType
operator|=
name|roomType
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
name|NonUniversityLocation
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
name|NonUniversityLocation
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
name|NonUniversityLocation
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
literal|"NonUniversityLocation["
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
literal|"NonUniversityLocation["
operator|+
literal|"\n	Area: "
operator|+
name|getArea
argument_list|()
operator|+
literal|"\n	Capacity: "
operator|+
name|getCapacity
argument_list|()
operator|+
literal|"\n	CoordinateX: "
operator|+
name|getCoordinateX
argument_list|()
operator|+
literal|"\n	CoordinateY: "
operator|+
name|getCoordinateY
argument_list|()
operator|+
literal|"\n	DisplayName: "
operator|+
name|getDisplayName
argument_list|()
operator|+
literal|"\n	EventDepartment: "
operator|+
name|getEventDepartment
argument_list|()
operator|+
literal|"\n	ExamCapacity: "
operator|+
name|getExamCapacity
argument_list|()
operator|+
literal|"\n	IgnoreRoomCheck: "
operator|+
name|getIgnoreRoomCheck
argument_list|()
operator|+
literal|"\n	IgnoreTooFar: "
operator|+
name|getIgnoreTooFar
argument_list|()
operator|+
literal|"\n	ManagerIds: "
operator|+
name|getManagerIds
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	Pattern: "
operator|+
name|getPattern
argument_list|()
operator|+
literal|"\n	PermanentId: "
operator|+
name|getPermanentId
argument_list|()
operator|+
literal|"\n	RoomType: "
operator|+
name|getRoomType
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

