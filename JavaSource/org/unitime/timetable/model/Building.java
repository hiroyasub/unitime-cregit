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
name|Hashtable
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
name|TreeSet
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
name|BaseBuilding
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
name|BuildingDAO
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
name|Building
extends|extends
name|BaseBuilding
implements|implements
name|Comparable
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3256440313428981557L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Building
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Building
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
name|Building
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
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
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|externalUniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|abbreviation
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|name
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
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|session
argument_list|,
name|externalUniqueId
argument_list|,
name|abbreviation
argument_list|,
name|name
argument_list|,
name|coordinateX
argument_list|,
name|coordinateY
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** Request attribute name for available buildings **/
specifier|public
specifier|static
name|String
name|BLDG_LIST_ATTR_NAME
init|=
literal|"bldgsList"
decl_stmt|;
comment|/**      * @return Building Identifier of the form {Abbr} - {Name}      */
specifier|public
name|String
name|getAbbrName
parameter_list|()
block|{
return|return
name|this
operator|.
name|getAbbreviation
argument_list|()
operator|+
literal|" - "
operator|+
name|this
operator|.
name|getName
argument_list|()
return|;
block|}
comment|/**      * Dummy setter - does nothing (Do not use)      */
specifier|public
name|void
name|setAbbrName
parameter_list|(
name|String
name|abbrName
parameter_list|)
block|{
block|}
comment|/**      * @return Building Identifier of the form {Abbr} - {Name}      */
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getAbbrName
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
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
name|Building
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|Building
name|b
init|=
operator|(
name|Building
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getAbbreviation
argument_list|()
operator|.
name|compareTo
argument_list|(
name|b
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|b
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
comment|/**      *       * @param bldgAbbv      * @param sessionId      * @return      * @throws Exception      */
specifier|public
specifier|static
name|Building
name|findByBldgAbbv
parameter_list|(
name|String
name|bldgAbbv
parameter_list|,
name|Long
name|sessionId
parameter_list|)
throws|throws
name|Exception
block|{
name|List
name|bldgs
init|=
operator|(
operator|new
name|BuildingDAO
argument_list|()
operator|)
operator|.
name|getQuery
argument_list|(
literal|"SELECT distinct b FROM Building b "
operator|+
literal|"WHERE b.session.uniqueId=:sessionId AND b.abbreviation=:bldgAbbv"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"bldgAbbv"
argument_list|,
name|bldgAbbv
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|bldgs
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
operator|(
name|Building
operator|)
name|bldgs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
return|return
literal|null
return|;
block|}
comment|/* 	 * Update building information using External Building 	 * @param sessionId 	 */
specifier|public
specifier|static
name|void
name|updateBuildings
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|Session
name|currentSession
init|=
name|Session
operator|.
name|getSessionById
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
name|TreeSet
name|currentBuildings
init|=
operator|new
name|TreeSet
argument_list|(
name|currentSession
operator|.
name|getBuildings
argument_list|()
argument_list|)
decl_stmt|;
name|Hashtable
name|updateBuildings
init|=
name|ExternalBuilding
operator|.
name|getBuildings
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
name|Iterator
name|b
init|=
name|currentBuildings
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|BuildingDAO
name|bldgDAO
init|=
operator|new
name|BuildingDAO
argument_list|()
decl_stmt|;
while|while
condition|(
name|b
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Building
name|bldg
init|=
operator|(
name|Building
operator|)
name|b
operator|.
name|next
argument_list|()
decl_stmt|;
name|ExternalBuilding
name|extBldg
init|=
operator|(
name|ExternalBuilding
operator|)
name|updateBuildings
operator|.
name|get
argument_list|(
name|bldg
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|extBldg
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|updateBldgInfo
argument_list|(
name|bldg
argument_list|,
name|extBldg
argument_list|)
condition|)
block|{
name|bldgDAO
operator|.
name|update
argument_list|(
name|bldg
argument_list|)
expr_stmt|;
block|}
name|b
operator|.
name|remove
argument_list|()
expr_stmt|;
name|updateBuildings
operator|.
name|remove
argument_list|(
name|extBldg
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|b
operator|=
name|currentBuildings
operator|.
name|iterator
argument_list|()
expr_stmt|;
while|while
condition|(
name|b
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Building
name|bldg
init|=
operator|(
name|Building
operator|)
name|b
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|checkBuildingDelete
argument_list|(
name|bldg
argument_list|)
condition|)
block|{
name|currentSession
operator|.
name|getBuildings
argument_list|()
operator|.
name|remove
argument_list|(
name|bldg
argument_list|)
expr_stmt|;
name|bldgDAO
operator|.
name|delete
argument_list|(
name|bldg
argument_list|)
expr_stmt|;
block|}
block|}
name|Iterator
name|eb
init|=
operator|(
name|updateBuildings
operator|.
name|values
argument_list|()
operator|)
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|eb
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ExternalBuilding
name|extBldg
init|=
operator|(
name|ExternalBuilding
operator|)
name|eb
operator|.
name|next
argument_list|()
decl_stmt|;
name|Building
name|newBldg
init|=
operator|new
name|Building
argument_list|()
decl_stmt|;
name|newBldg
operator|.
name|setAbbreviation
argument_list|(
name|extBldg
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|newBldg
operator|.
name|setCoordinateX
argument_list|(
name|extBldg
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|newBldg
operator|.
name|setCoordinateY
argument_list|(
name|extBldg
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|newBldg
operator|.
name|setName
argument_list|(
name|extBldg
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|newBldg
operator|.
name|setSession
argument_list|(
name|currentSession
argument_list|)
expr_stmt|;
name|newBldg
operator|.
name|setExternalUniqueId
argument_list|(
name|extBldg
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|bldgDAO
operator|.
name|save
argument_list|(
name|newBldg
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
comment|/* 	 * Update building information 	 * @param bldg (Building) 	 * @param extBldg (ExternalBuilding) 	 * @return update  (True if updates are made) 	 */
specifier|private
specifier|static
name|boolean
name|updateBldgInfo
parameter_list|(
name|Building
name|bldg
parameter_list|,
name|ExternalBuilding
name|extBldg
parameter_list|)
block|{
name|boolean
name|updated
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|!
name|bldg
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|equals
argument_list|(
name|extBldg
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
condition|)
block|{
name|bldg
operator|.
name|setAbbreviation
argument_list|(
name|extBldg
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|updated
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|bldg
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|extBldg
operator|.
name|getDisplayName
argument_list|()
argument_list|)
condition|)
block|{
name|bldg
operator|.
name|setName
argument_list|(
name|extBldg
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|updated
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|bldg
operator|.
name|getCoordinateX
argument_list|()
operator|.
name|compareTo
argument_list|(
name|extBldg
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
operator|)
operator|!=
literal|0
condition|)
block|{
name|bldg
operator|.
name|setCoordinateX
argument_list|(
name|extBldg
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|updated
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|bldg
operator|.
name|getCoordinateY
argument_list|()
operator|.
name|compareTo
argument_list|(
name|extBldg
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
operator|)
operator|!=
literal|0
condition|)
block|{
name|bldg
operator|.
name|setCoordinateY
argument_list|(
name|extBldg
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|updated
operator|=
literal|true
expr_stmt|;
block|}
return|return
name|updated
return|;
block|}
comment|/* 	 * Check if building can be deleted 	 * @param bldg 	 * @return boolean  (True if building can be deleted) 	 */
specifier|private
specifier|static
name|boolean
name|checkBuildingDelete
parameter_list|(
name|Building
name|bldg
parameter_list|)
block|{
name|boolean
name|result
init|=
literal|false
decl_stmt|;
empty_stmt|;
name|List
name|rooms
init|=
operator|(
operator|new
name|RoomDAO
argument_list|()
operator|)
operator|.
name|getQuery
argument_list|(
literal|"from Room as rm "
operator|+
literal|"where rm.building.uniqueId = "
operator|+
operator|(
name|bldg
operator|.
name|getUniqueId
argument_list|()
operator|)
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|rooms
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|result
operator|=
literal|true
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|Building
name|b
init|=
operator|new
name|Building
argument_list|()
decl_stmt|;
name|b
operator|.
name|setAbbreviation
argument_list|(
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setCoordinateX
argument_list|(
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setCoordinateY
argument_list|(
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|b
return|;
block|}
specifier|public
name|Building
name|findSameBuildingInSession
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
name|Building
name|newBuilding
init|=
name|Building
operator|.
name|findByBldgAbbv
argument_list|(
name|this
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|newSession
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|newBuilding
operator|==
literal|null
operator|&&
name|this
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|newBuilding
operator|=
name|Building
operator|.
name|findByExternalIdAndSession
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|,
name|newSession
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|newBuilding
operator|)
return|;
block|}
specifier|public
specifier|static
name|Building
name|findByExternalIdAndSession
parameter_list|(
name|String
name|externalId
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
name|BuildingDAO
name|bDao
init|=
operator|new
name|BuildingDAO
argument_list|()
decl_stmt|;
name|List
name|bldgs
init|=
name|bDao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|Building
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
name|externalId
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
name|session
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
decl_stmt|;
if|if
condition|(
name|bldgs
operator|!=
literal|null
operator|&&
name|bldgs
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
operator|(
operator|(
name|Building
operator|)
name|bldgs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
return|;
block|}
return|return
operator|(
literal|null
operator|)
return|;
block|}
block|}
end_class

end_unit

