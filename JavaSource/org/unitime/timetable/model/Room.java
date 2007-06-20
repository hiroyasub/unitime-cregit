begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
package|;
end_package

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
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|base
operator|.
name|BaseRoom
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
name|RoomDAO
import|;
end_import

begin_class
specifier|public
class|class
name|Room
extends|extends
name|BaseRoom
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Room
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Room
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|Room
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|capacity
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateX
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateY
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreTooFar
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreRoomCheck
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|capacity
argument_list|,
name|coordinateX
argument_list|,
name|coordinateY
argument_list|,
name|ignoreTooFar
argument_list|,
name|ignoreRoomCheck
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|String
name|bldgAbbvRoomNumber
parameter_list|()
block|{
return|return
name|getBuildingAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|getRoomNumber
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|bldgAbbvRoomNumber
argument_list|()
return|;
block|}
comment|/** Request attribute name for available rooms **/
specifier|public
specifier|static
name|String
name|ROOM_LIST_ATTR_NAME
init|=
literal|"roomsList"
decl_stmt|;
comment|/**      * Returns room label of the form BLDG ROOM e.g. HTM 101      * This method is used as a getter for property label      * @return Room Label      */
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|this
operator|.
name|bldgAbbvRoomNumber
argument_list|()
return|;
block|}
specifier|public
name|void
name|addToroomDepts
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|RoomDept
name|roomDept
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getRoomDepts
argument_list|()
condition|)
name|setRoomDepts
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getRoomDepts
argument_list|()
operator|.
name|add
argument_list|(
name|roomDept
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|Room
name|r
init|=
operator|new
name|Room
argument_list|()
decl_stmt|;
name|r
operator|.
name|setBuilding
argument_list|(
name|r
operator|.
name|getBuilding
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCapacity
argument_list|(
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setClassification
argument_list|(
name|getClassification
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCoordinateX
argument_list|(
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCoordinateY
argument_list|(
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDisplayName
argument_list|(
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|isIgnoreRoomCheck
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setIgnoreTooFar
argument_list|(
name|isIgnoreTooFar
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setPattern
argument_list|(
name|getPattern
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setRoomNumber
argument_list|(
name|getRoomNumber
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setScheduledRoomType
argument_list|(
name|getScheduledRoomType
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|r
return|;
block|}
specifier|public
name|Room
name|findSameRoomInSession
parameter_list|(
name|Session
name|newSession
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|newSession
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|Room
name|newRoom
init|=
literal|null
decl_stmt|;
name|RoomDAO
name|rDao
init|=
operator|new
name|RoomDAO
argument_list|()
decl_stmt|;
name|Building
name|newBuilding
init|=
name|getBuilding
argument_list|()
operator|.
name|findSameBuildingInSession
argument_list|(
name|newSession
argument_list|)
decl_stmt|;
comment|//		String query = "from Room r where r.building.uniqueId = " + newBuilding.getUniqueId().toString();
comment|//		query += " and r.session.uniqueId = " + newSession.getUniqueId().toString();
comment|//		query += " and r.roomNumber = '" + getRoomNumber() + "'";
name|List
name|rooms
init|=
name|rDao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|Room
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"building.uniqueId"
argument_list|,
name|newBuilding
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"session.uniqueId"
argument_list|,
name|newSession
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"roomNumber"
argument_list|,
name|getRoomNumber
argument_list|()
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|rooms
operator|!=
literal|null
operator|&&
name|rooms
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|newRoom
operator|=
operator|(
name|Room
operator|)
name|rooms
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|rooms
operator|=
name|rDao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|Room
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"externalUniqueId"
argument_list|,
name|getExternalUniqueId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"session.uniqueId"
argument_list|,
name|newSession
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
expr_stmt|;
if|if
condition|(
name|rooms
operator|!=
literal|null
operator|&&
name|rooms
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|newRoom
operator|=
operator|(
name|Room
operator|)
name|rooms
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|(
name|newRoom
operator|)
return|;
block|}
specifier|public
specifier|static
name|Room
name|findByBldgIdRoomNbr
parameter_list|(
name|Long
name|bldgId
parameter_list|,
name|String
name|roomNbr
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|Room
operator|)
operator|new
name|RoomDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from Room r where r.building.uniqueId=:bldgId and r.roomNumber=:roomNbr and r.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"bldgId"
argument_list|,
name|bldgId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"roomNbr"
argument_list|,
name|roomNbr
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
block|}
end_class

end_unit

