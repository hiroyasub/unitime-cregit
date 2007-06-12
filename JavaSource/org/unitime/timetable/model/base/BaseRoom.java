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

begin_comment
comment|/**  * This is an object that contains data related to the ROOM table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="ROOM"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseRoom
extends|extends
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Location
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"Room"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_BUILDING_ABBV
init|=
literal|"buildingAbbv"
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
name|PROP_EXTERNAL_UNIQUE_ID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SCHEDULED_ROOM_TYPE
init|=
literal|"scheduledRoomType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CLASSIFICATION
init|=
literal|"classification"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseRoom
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseRoom
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
name|BaseRoom
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
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|buildingAbbv
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|roomNumber
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|scheduledRoomType
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|classification
decl_stmt|;
comment|// many to one
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Building
name|building
decl_stmt|;
comment|/** 	 * Return the value associated with the column: buildingAbbv 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getBuildingAbbv
parameter_list|()
block|{
return|return
name|buildingAbbv
return|;
block|}
comment|/** 	 * Set the value related to the column: buildingAbbv 	 * @param buildingAbbv the buildingAbbv value 	 */
specifier|public
name|void
name|setBuildingAbbv
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|buildingAbbv
parameter_list|)
block|{
name|this
operator|.
name|buildingAbbv
operator|=
name|buildingAbbv
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ROOM_NUMBER 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getRoomNumber
parameter_list|()
block|{
return|return
name|roomNumber
return|;
block|}
comment|/** 	 * Set the value related to the column: ROOM_NUMBER 	 * @param roomNumber the ROOM_NUMBER value 	 */
specifier|public
name|void
name|setRoomNumber
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|roomNumber
parameter_list|)
block|{
name|this
operator|.
name|roomNumber
operator|=
name|roomNumber
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: EXTERNAL_UID 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|externalUniqueId
return|;
block|}
comment|/** 	 * Set the value related to the column: EXTERNAL_UID 	 * @param externalUniqueId the EXTERNAL_UID value 	 */
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
parameter_list|)
block|{
name|this
operator|.
name|externalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SCHEDULED_ROOM_TYPE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getScheduledRoomType
parameter_list|()
block|{
return|return
name|scheduledRoomType
return|;
block|}
comment|/** 	 * Set the value related to the column: SCHEDULED_ROOM_TYPE 	 * @param scheduledRoomType the SCHEDULED_ROOM_TYPE value 	 */
specifier|public
name|void
name|setScheduledRoomType
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|scheduledRoomType
parameter_list|)
block|{
name|this
operator|.
name|scheduledRoomType
operator|=
name|scheduledRoomType
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: CLASSIFICATION 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getClassification
parameter_list|()
block|{
return|return
name|classification
return|;
block|}
comment|/** 	 * Set the value related to the column: CLASSIFICATION 	 * @param classification the CLASSIFICATION value 	 */
specifier|public
name|void
name|setClassification
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|classification
parameter_list|)
block|{
name|this
operator|.
name|classification
operator|=
name|classification
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: BUILDING_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Building
name|getBuilding
parameter_list|()
block|{
return|return
name|building
return|;
block|}
comment|/** 	 * Set the value related to the column: BUILDING_ID 	 * @param building the BUILDING_ID value 	 */
specifier|public
name|void
name|setBuilding
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Building
name|building
parameter_list|)
block|{
name|this
operator|.
name|building
operator|=
name|building
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|obj
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Room
operator|)
condition|)
return|return
literal|false
return|;
else|else
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Room
name|room
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Room
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
operator|||
literal|null
operator|==
name|room
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
literal|false
return|;
else|else
return|return
operator|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|room
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|Integer
operator|.
name|MIN_VALUE
operator|==
name|this
operator|.
name|hashCode
condition|)
block|{
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
else|else
block|{
name|String
name|hashStr
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|hashStr
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|this
operator|.
name|hashCode
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

