begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|ApplicationProperties
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
name|ExternalRoomDAO
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
name|RoomDeptDAO
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
name|setRoomType
argument_list|(
name|getRoomType
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
name|r
operator|.
name|setPermanentId
argument_list|(
name|getPermanentId
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setExamCapacity
argument_list|(
name|getExamCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setExamType
argument_list|(
name|getExamType
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
name|newRoom
operator|=
operator|(
name|Room
operator|)
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
literal|"permanentId"
argument_list|,
name|getPermanentId
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
name|uniqueResult
argument_list|()
expr_stmt|;
if|if
condition|(
name|newRoom
operator|==
literal|null
operator|&&
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|newRoom
operator|=
operator|(
name|Room
operator|)
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
name|uniqueResult
argument_list|()
expr_stmt|;
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
specifier|public
name|void
name|addExternalRoomDept
parameter_list|(
name|ExternalRoomDepartment
name|externalRoomDept
parameter_list|,
name|Set
name|externalRoomDepts
parameter_list|)
block|{
name|Department
name|dept
init|=
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|externalRoomDept
operator|.
name|getDepartmentCode
argument_list|()
argument_list|,
name|this
operator|.
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|RoomDept
name|roomDept
init|=
literal|null
decl_stmt|;
name|RoomDeptDAO
name|rdDao
init|=
operator|new
name|RoomDeptDAO
argument_list|()
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
block|{
name|roomDept
operator|=
operator|new
name|RoomDept
argument_list|()
expr_stmt|;
name|roomDept
operator|.
name|setRoom
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|roomDept
operator|.
name|setControl
argument_list|(
operator|new
name|Boolean
argument_list|(
name|ExternalRoomDepartment
operator|.
name|isControllingExternalDept
argument_list|(
name|externalRoomDept
argument_list|,
name|externalRoomDepts
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|roomDept
operator|.
name|setDepartment
argument_list|(
name|dept
argument_list|)
expr_stmt|;
name|this
operator|.
name|addToroomDepts
argument_list|(
name|roomDept
argument_list|)
expr_stmt|;
name|dept
operator|.
name|addToroomDepts
argument_list|(
name|roomDept
argument_list|)
expr_stmt|;
name|rdDao
operator|.
name|saveOrUpdate
argument_list|(
name|roomDept
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|addNewExternalRoomsToSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|String
name|query
init|=
literal|"from ExternalRoom er where er.building.session.uniqueId=:sessionId"
decl_stmt|;
name|boolean
name|updateExistingRooms
init|=
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.external.room.update.existing"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|updateExistingRooms
condition|)
name|query
operator|+=
literal|" and er.externalUniqueId not in (select r.externalUniqueId from Room r where r.session.uniqueId =:sessionId)"
expr_stmt|;
name|boolean
name|resetRoomFeatures
init|=
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.external.room.update.existing.features"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|resetRoomDepartments
init|=
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.external.room.update.existing.departments"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|classifications
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.external.room.update.classifications"
argument_list|)
decl_stmt|;
if|if
condition|(
name|classifications
operator|!=
literal|null
condition|)
block|{
name|String
name|classificationsQuery
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|c
range|:
name|classifications
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
if|if
condition|(
name|c
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
if|if
condition|(
operator|!
name|classificationsQuery
operator|.
name|isEmpty
argument_list|()
condition|)
name|classificationsQuery
operator|+=
literal|", "
expr_stmt|;
name|classificationsQuery
operator|+=
literal|"'"
operator|+
name|c
operator|.
name|trim
argument_list|()
operator|+
literal|"'"
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|classificationsQuery
operator|.
name|isEmpty
argument_list|()
condition|)
name|query
operator|+=
literal|" and er.classification in ("
operator|+
name|classificationsQuery
operator|+
literal|")"
expr_stmt|;
block|}
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|ExternalRoomDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
for|for
control|(
name|ExternalRoom
name|er
range|:
operator|(
name|List
argument_list|<
name|ExternalRoom
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
name|query
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Building
name|b
init|=
name|Building
operator|.
name|findByExternalIdAndSession
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|session
argument_list|)
decl_stmt|;
if|if
condition|(
name|b
operator|==
literal|null
condition|)
block|{
name|b
operator|=
operator|new
name|Building
argument_list|()
expr_stmt|;
name|b
operator|.
name|setAbbreviation
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setCoordinateX
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setCoordinateY
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setExternalUniqueId
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setName
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|updateExistingRooms
condition|)
block|{
name|b
operator|.
name|setAbbreviation
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setCoordinateX
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setCoordinateY
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setName
argument_list|(
name|er
operator|.
name|getBuilding
argument_list|()
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|b
argument_list|)
expr_stmt|;
block|}
name|Room
name|r
init|=
operator|(
name|Room
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from Room r where r.building.session.uniqueId = :sessionId and r.externalUniqueId = :externalId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|er
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|r
operator|==
literal|null
condition|)
block|{
name|r
operator|=
operator|new
name|Room
argument_list|()
expr_stmt|;
name|r
operator|.
name|setBuilding
argument_list|(
name|b
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCapacity
argument_list|(
name|er
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setExamCapacity
argument_list|(
name|er
operator|.
name|getExamCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setClassification
argument_list|(
name|er
operator|.
name|getClassification
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCoordinateX
argument_list|(
name|er
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCoordinateY
argument_list|(
name|er
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDisplayName
argument_list|(
name|er
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setExternalUniqueId
argument_list|(
name|er
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setIgnoreRoomCheck
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|.
name|setIgnoreTooFar
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|.
name|setRoomNumber
argument_list|(
name|er
operator|.
name|getRoomNumber
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setRoomType
argument_list|(
name|er
operator|.
name|getRoomType
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|r
operator|.
name|setFeatures
argument_list|(
operator|new
name|HashSet
argument_list|<
name|RoomFeature
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|ExternalRoomFeature
name|erf
range|:
name|er
operator|.
name|getRoomFeatures
argument_list|()
control|)
block|{
name|GlobalRoomFeature
name|grf
init|=
name|GlobalRoomFeature
operator|.
name|findGlobalRoomFeatureForLabel
argument_list|(
name|erf
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|grf
operator|==
literal|null
condition|)
name|grf
operator|=
name|GlobalRoomFeature
operator|.
name|findGlobalRoomFeatureForAbbv
argument_list|(
name|erf
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|grf
operator|!=
literal|null
condition|)
name|r
operator|.
name|getFeatures
argument_list|()
operator|.
name|add
argument_list|(
name|grf
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|ExternalRoomDepartment
name|erd
range|:
name|er
operator|.
name|getRoomDepartments
argument_list|()
control|)
name|r
operator|.
name|addExternalRoomDept
argument_list|(
name|erd
argument_list|,
name|er
operator|.
name|getRoomDepartments
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|updateExistingRooms
condition|)
block|{
name|r
operator|.
name|setBuilding
argument_list|(
name|b
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCapacity
argument_list|(
name|er
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setExamCapacity
argument_list|(
name|er
operator|.
name|getExamCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setClassification
argument_list|(
name|er
operator|.
name|getClassification
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCoordinateX
argument_list|(
name|er
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setCoordinateY
argument_list|(
name|er
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDisplayName
argument_list|(
name|er
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setRoomNumber
argument_list|(
name|er
operator|.
name|getRoomNumber
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setRoomType
argument_list|(
name|er
operator|.
name|getRoomType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|resetRoomFeatures
condition|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|RoomFeature
argument_list|>
name|i
init|=
name|r
operator|.
name|getFeatures
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomFeature
name|rf
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rf
operator|instanceof
name|GlobalRoomFeature
condition|)
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|ExternalRoomFeature
name|erf
range|:
name|er
operator|.
name|getRoomFeatures
argument_list|()
control|)
block|{
name|GlobalRoomFeature
name|grf
init|=
name|GlobalRoomFeature
operator|.
name|findGlobalRoomFeatureForLabel
argument_list|(
name|erf
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|grf
operator|==
literal|null
condition|)
name|grf
operator|=
name|GlobalRoomFeature
operator|.
name|findGlobalRoomFeatureForAbbv
argument_list|(
name|erf
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|grf
operator|!=
literal|null
condition|)
name|r
operator|.
name|getFeatures
argument_list|()
operator|.
name|add
argument_list|(
name|grf
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|resetRoomDepartments
condition|)
block|{
for|for
control|(
name|ExternalRoomDepartment
name|erd
range|:
name|er
operator|.
name|getRoomDepartments
argument_list|()
control|)
name|r
operator|.
name|addExternalRoomDept
argument_list|(
name|erd
argument_list|,
name|er
operator|.
name|getRoomDepartments
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getRoomTypeLabel
parameter_list|()
block|{
return|return
name|getRoomType
argument_list|()
operator|.
name|getLabel
argument_list|()
return|;
block|}
block|}
end_class

end_unit

