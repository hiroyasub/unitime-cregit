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
name|BaseExternalRoom
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

begin_class
specifier|public
class|class
name|ExternalRoom
extends|extends
name|BaseExternalRoom
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
name|ExternalRoom
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|ExternalRoom
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
name|ExternalRoom
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
name|ExternalBuilding
name|building
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|roomNumber
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
name|String
name|classification
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|RoomType
name|roomType
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|isInstructional
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|building
argument_list|,
name|roomNumber
argument_list|,
name|capacity
argument_list|,
name|classification
argument_list|,
name|roomType
argument_list|,
name|isInstructional
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
name|ExternalRoom
name|findExternalRoomForSession
parameter_list|(
name|String
name|externalUniqueId
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
name|ExternalRoomDAO
name|erDao
init|=
operator|new
name|ExternalRoomDAO
argument_list|()
decl_stmt|;
name|List
name|rooms
init|=
name|erDao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|ExternalRoom
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
name|externalUniqueId
argument_list|)
argument_list|)
operator|.
name|createCriteria
argument_list|(
literal|"building"
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
name|rooms
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
name|ExternalRoom
operator|)
name|rooms
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
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|ExternalRoomDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from ExternalRoom r where r.building.session.uniqueId=:sessionId"
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
specifier|public
specifier|static
name|ExternalRoom
name|findByBldgAbbvRoomNbr
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|bldgAbbv
parameter_list|,
name|String
name|roomNbr
parameter_list|)
block|{
return|return
operator|(
name|ExternalRoom
operator|)
operator|new
name|ExternalRoomDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from ExternalRoom r where r.building.session.uniqueId=:sessionId and "
operator|+
literal|"r.building.abbreviation=:bldgAbbv and r.roomNumber=:roomNbr"
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
name|setString
argument_list|(
literal|"roomNbr"
argument_list|,
name|roomNbr
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
block|}
end_class

end_unit

