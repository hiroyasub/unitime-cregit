begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|defaults
operator|.
name|ApplicationProperty
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
operator|.
name|rooms
operator|.
name|RoomDetailsBackend
operator|.
name|UrlSigner
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, James Marshall, Zuzana Mullerova  */
end_comment

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
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
name|Long
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|b
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
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
specifier|public
specifier|static
name|Building
name|findByBldgAbbv
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|String
name|bldgAbbv
parameter_list|)
block|{
return|return
operator|(
name|Building
operator|)
operator|(
name|hibSession
operator|==
literal|null
condition|?
name|BuildingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
else|:
name|hibSession
operator|)
operator|.
name|createQuery
argument_list|(
literal|"from Building b where session.uniqueId=:sessionId and b.abbreviation=:bldgAbbv"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"bldgAbbv"
argument_list|,
name|bldgAbbv
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Building
name|findByName
parameter_list|(
name|String
name|name
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|Building
operator|)
operator|(
operator|new
name|BuildingDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select b from Building b where b.session.uniqueId=:sessionId and b.name=:name"
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
literal|"name"
argument_list|,
name|name
argument_list|)
operator|.
name|uniqueResult
argument_list|()
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
name|String
name|externalUniqueId
init|=
name|bldg
operator|.
name|getExternalUniqueId
argument_list|()
decl_stmt|;
if|if
condition|(
name|externalUniqueId
operator|!=
literal|null
condition|)
block|{
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
name|externalUniqueId
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
else|else
block|{
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
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|bldg
operator|.
name|getCoordinateX
argument_list|()
argument_list|,
name|extBldg
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
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
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|bldg
operator|.
name|getCoordinateY
argument_list|()
argument_list|,
name|extBldg
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
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
return|return
name|findByExternalIdAndSession
argument_list|(
name|externalId
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
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
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|externalId
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
name|sessionId
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
specifier|public
specifier|static
name|List
argument_list|<
name|Building
argument_list|>
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|BuildingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select b from Building b where b.session.uniqueId=:sessionId order by b.abbreviation"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|Deprecated
specifier|public
name|String
name|getHtmlHint
parameter_list|()
block|{
name|String
name|hint
init|=
name|getName
argument_list|()
decl_stmt|;
name|String
name|minimap
init|=
name|ApplicationProperty
operator|.
name|RoomHintMinimapUrl
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|minimap
operator|!=
literal|null
operator|&&
name|getCoordinateX
argument_list|()
operator|!=
literal|null
operator|&&
name|getCoordinateY
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|minimap
operator|=
name|minimap
operator|.
name|replace
argument_list|(
literal|"%x"
argument_list|,
name|getCoordinateX
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%y"
argument_list|,
name|getCoordinateY
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%n"
argument_list|,
name|getAbbreviation
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|"%i"
argument_list|,
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|apikey
init|=
name|ApplicationProperty
operator|.
name|RoomMapStaticApiKey
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|apikey
operator|!=
literal|null
operator|&&
operator|!
name|apikey
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|minimap
operator|+=
literal|"&key="
operator|+
name|apikey
expr_stmt|;
name|String
name|secret
init|=
name|ApplicationProperty
operator|.
name|RoomMapStaticSecret
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|secret
operator|!=
literal|null
operator|&&
operator|!
name|secret
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|minimap
operator|+=
literal|"&signature="
operator|+
operator|new
name|UrlSigner
argument_list|(
name|secret
argument_list|)
operator|.
name|signRequest
argument_list|(
name|minimap
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
name|hint
operator|+=
literal|"<br><img src=\\'"
operator|+
name|minimap
operator|+
literal|"\\' border=\\'0\\' style=\\'border: 1px solid #9CB0CE;\\'/>"
expr_stmt|;
block|}
return|return
name|hint
return|;
block|}
block|}
end_class

end_unit

