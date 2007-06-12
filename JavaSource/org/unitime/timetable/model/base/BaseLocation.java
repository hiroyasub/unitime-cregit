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
comment|/**  * This is an object that contains data related to the  table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table=""  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseLocation
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"Location"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CAPACITY
init|=
literal|"capacity"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COORDINATE_X
init|=
literal|"coordinateX"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_COORDINATE_Y
init|=
literal|"coordinateY"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_IGNORE_TOO_FAR
init|=
literal|"ignoreTooFar"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_IGNORE_ROOM_CHECK
init|=
literal|"ignoreRoomCheck"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MANAGER_IDS
init|=
literal|"managerIds"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PATTERN
init|=
literal|"pattern"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DISPLAY_NAME
init|=
literal|"displayName"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseLocation
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseLocation
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|BaseLocation
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
name|this
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|this
operator|.
name|setCapacity
argument_list|(
name|capacity
argument_list|)
expr_stmt|;
name|this
operator|.
name|setCoordinateX
argument_list|(
name|coordinateX
argument_list|)
expr_stmt|;
name|this
operator|.
name|setCoordinateY
argument_list|(
name|coordinateY
argument_list|)
expr_stmt|;
name|this
operator|.
name|setIgnoreTooFar
argument_list|(
name|ignoreTooFar
argument_list|)
expr_stmt|;
name|this
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|ignoreRoomCheck
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
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// primary key
specifier|private
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|capacity
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateX
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateY
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreTooFar
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreRoomCheck
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|managerIds
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|pattern
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|displayName
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
name|Session
name|session
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|features
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|assignments
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|roomGroups
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|roomDepts
decl_stmt|;
comment|/** 	 * Return the unique identifier of this class      * @hibernate.id      *  generator-class="org.unitime.commons.hibernate.id.UniqueIdGenerator"      *  column="UNIQUEID"      */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|uniqueId
return|;
block|}
comment|/** 	 * Set the unique identifier of this class 	 * @param uniqueId the new ID 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|uniqueId
operator|=
name|uniqueId
expr_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|Integer
operator|.
name|MIN_VALUE
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: CAPACITY 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getCapacity
parameter_list|()
block|{
return|return
name|capacity
return|;
block|}
comment|/** 	 * Set the value related to the column: CAPACITY 	 * @param capacity the CAPACITY value 	 */
specifier|public
name|void
name|setCapacity
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|capacity
parameter_list|)
block|{
name|this
operator|.
name|capacity
operator|=
name|capacity
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: COORDINATE_X 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getCoordinateX
parameter_list|()
block|{
return|return
name|coordinateX
return|;
block|}
comment|/** 	 * Set the value related to the column: COORDINATE_X 	 * @param coordinateX the COORDINATE_X value 	 */
specifier|public
name|void
name|setCoordinateX
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateX
parameter_list|)
block|{
name|this
operator|.
name|coordinateX
operator|=
name|coordinateX
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: COORDINATE_Y 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Integer
name|getCoordinateY
parameter_list|()
block|{
return|return
name|coordinateY
return|;
block|}
comment|/** 	 * Set the value related to the column: COORDINATE_Y 	 * @param coordinateY the COORDINATE_Y value 	 */
specifier|public
name|void
name|setCoordinateY
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateY
parameter_list|)
block|{
name|this
operator|.
name|coordinateY
operator|=
name|coordinateY
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: IGNORE_TOO_FAR 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isIgnoreTooFar
parameter_list|()
block|{
return|return
name|ignoreTooFar
return|;
block|}
comment|/** 	 * Set the value related to the column: IGNORE_TOO_FAR 	 * @param ignoreTooFar the IGNORE_TOO_FAR value 	 */
specifier|public
name|void
name|setIgnoreTooFar
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreTooFar
parameter_list|)
block|{
name|this
operator|.
name|ignoreTooFar
operator|=
name|ignoreTooFar
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: IGNORE_ROOM_CHECK 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isIgnoreRoomCheck
parameter_list|()
block|{
return|return
name|ignoreRoomCheck
return|;
block|}
comment|/** 	 * Set the value related to the column: IGNORE_ROOM_CHECK 	 * @param ignoreRoomCheck the IGNORE_ROOM_CHECK value 	 */
specifier|public
name|void
name|setIgnoreRoomCheck
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreRoomCheck
parameter_list|)
block|{
name|this
operator|.
name|ignoreRoomCheck
operator|=
name|ignoreRoomCheck
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: MANAGER_IDS 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getManagerIds
parameter_list|()
block|{
return|return
name|managerIds
return|;
block|}
comment|/** 	 * Set the value related to the column: MANAGER_IDS 	 * @param managerIds the MANAGER_IDS value 	 */
specifier|public
name|void
name|setManagerIds
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|managerIds
parameter_list|)
block|{
name|this
operator|.
name|managerIds
operator|=
name|managerIds
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: PATTERN 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|pattern
return|;
block|}
comment|/** 	 * Set the value related to the column: PATTERN 	 * @param pattern the PATTERN value 	 */
specifier|public
name|void
name|setPattern
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|pattern
parameter_list|)
block|{
name|this
operator|.
name|pattern
operator|=
name|pattern
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DISPLAY_NAME 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|displayName
return|;
block|}
comment|/** 	 * Set the value related to the column: DISPLAY_NAME 	 * @param displayName the DISPLAY_NAME value 	 */
specifier|public
name|void
name|setDisplayName
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|displayName
parameter_list|)
block|{
name|this
operator|.
name|displayName
operator|=
name|displayName
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SESSION_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|session
return|;
block|}
comment|/** 	 * Set the value related to the column: SESSION_ID 	 * @param session the SESSION_ID value 	 */
specifier|public
name|void
name|setSession
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
parameter_list|)
block|{
name|this
operator|.
name|session
operator|=
name|session
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: features 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getFeatures
parameter_list|()
block|{
return|return
name|features
return|;
block|}
comment|/** 	 * Set the value related to the column: features 	 * @param features the features value 	 */
specifier|public
name|void
name|setFeatures
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|features
parameter_list|)
block|{
name|this
operator|.
name|features
operator|=
name|features
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: assignments 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getAssignments
parameter_list|()
block|{
return|return
name|assignments
return|;
block|}
comment|/** 	 * Set the value related to the column: assignments 	 * @param assignments the assignments value 	 */
specifier|public
name|void
name|setAssignments
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|assignments
parameter_list|)
block|{
name|this
operator|.
name|assignments
operator|=
name|assignments
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: roomGroups 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getRoomGroups
parameter_list|()
block|{
return|return
name|roomGroups
return|;
block|}
comment|/** 	 * Set the value related to the column: roomGroups 	 * @param roomGroups the roomGroups value 	 */
specifier|public
name|void
name|setRoomGroups
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|roomGroups
parameter_list|)
block|{
name|this
operator|.
name|roomGroups
operator|=
name|roomGroups
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: roomDepts 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getRoomDepts
parameter_list|()
block|{
return|return
name|roomDepts
return|;
block|}
comment|/** 	 * Set the value related to the column: roomDepts 	 * @param roomDepts the roomDepts value 	 */
specifier|public
name|void
name|setRoomDepts
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|roomDepts
parameter_list|)
block|{
name|this
operator|.
name|roomDepts
operator|=
name|roomDepts
expr_stmt|;
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
name|Location
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
name|Location
name|location
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
name|Location
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
name|location
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
name|location
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

