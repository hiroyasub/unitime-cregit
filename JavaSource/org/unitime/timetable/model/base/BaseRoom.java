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
name|Building
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
name|Room
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
name|BaseRoom
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
name|iBuildingAbbv
decl_stmt|;
specifier|private
name|String
name|iRoomNumber
decl_stmt|;
specifier|private
name|String
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|String
name|iClassification
decl_stmt|;
specifier|private
name|RoomType
name|iRoomType
decl_stmt|;
specifier|private
name|Building
name|iBuilding
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ROOM_NUMBER
init|=
literal|"roomNumber"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CLASSIFICATION
init|=
literal|"classification"
decl_stmt|;
specifier|public
name|BaseRoom
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseRoom
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
name|getBuildingAbbv
parameter_list|()
block|{
return|return
name|iBuildingAbbv
return|;
block|}
specifier|public
name|void
name|setBuildingAbbv
parameter_list|(
name|String
name|buildingAbbv
parameter_list|)
block|{
name|iBuildingAbbv
operator|=
name|buildingAbbv
expr_stmt|;
block|}
specifier|public
name|String
name|getRoomNumber
parameter_list|()
block|{
return|return
name|iRoomNumber
return|;
block|}
specifier|public
name|void
name|setRoomNumber
parameter_list|(
name|String
name|roomNumber
parameter_list|)
block|{
name|iRoomNumber
operator|=
name|roomNumber
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getClassification
parameter_list|()
block|{
return|return
name|iClassification
return|;
block|}
specifier|public
name|void
name|setClassification
parameter_list|(
name|String
name|classification
parameter_list|)
block|{
name|iClassification
operator|=
name|classification
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
name|Building
name|getBuilding
parameter_list|()
block|{
return|return
name|iBuilding
return|;
block|}
specifier|public
name|void
name|setBuilding
parameter_list|(
name|Building
name|building
parameter_list|)
block|{
name|iBuilding
operator|=
name|building
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
name|Room
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
name|Room
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
name|Room
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
literal|"Room["
operator|+
name|getUniqueId
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
literal|"Room["
operator|+
literal|"\n	Building: "
operator|+
name|getBuilding
argument_list|()
operator|+
literal|"\n	Capacity: "
operator|+
name|getCapacity
argument_list|()
operator|+
literal|"\n	Classification: "
operator|+
name|getClassification
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
literal|"\n	ExamCapacity: "
operator|+
name|getExamCapacity
argument_list|()
operator|+
literal|"\n	ExamType: "
operator|+
name|getExamType
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
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
literal|"\n	RoomNumber: "
operator|+
name|getRoomNumber
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

