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
name|util
operator|.
name|List
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
name|ExternalRoom
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
name|_RootDAO
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
specifier|abstract
class|class
name|BaseExternalRoomDAO
extends|extends
name|_RootDAO
argument_list|<
name|ExternalRoom
argument_list|,
name|Long
argument_list|>
block|{
specifier|private
specifier|static
name|ExternalRoomDAO
name|sInstance
decl_stmt|;
specifier|public
specifier|static
name|ExternalRoomDAO
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|ExternalRoomDAO
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|Class
argument_list|<
name|ExternalRoom
argument_list|>
name|getReferenceClass
parameter_list|()
block|{
return|return
name|ExternalRoom
operator|.
name|class
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|ExternalRoom
argument_list|>
name|findByRoomType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|roomTypeId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from ExternalRoom x where x.roomType.uniqueId = :roomTypeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"roomTypeId"
argument_list|,
name|roomTypeId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
specifier|public
name|List
argument_list|<
name|ExternalRoom
argument_list|>
name|findByBuilding
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|buildingId
parameter_list|)
block|{
return|return
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from ExternalRoom x where x.building.uniqueId = :buildingId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"buildingId"
argument_list|,
name|buildingId
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
block|}
end_class

end_unit

