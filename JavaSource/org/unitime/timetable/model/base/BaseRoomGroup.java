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
name|Set
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
name|Department
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
name|RoomGroup
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
name|Session
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseRoomGroup
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
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|String
name|iAbbv
decl_stmt|;
specifier|private
name|String
name|iDescription
decl_stmt|;
specifier|private
name|Boolean
name|iGlobal
decl_stmt|;
specifier|private
name|Boolean
name|iDefaultGroup
decl_stmt|;
specifier|private
name|Department
name|iDepartment
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Location
argument_list|>
name|iRooms
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ABBV
init|=
literal|"abbv"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DESCRIPTION
init|=
literal|"description"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_GLOBAL
init|=
literal|"global"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_DEFAULT_GROUP
init|=
literal|"defaultGroup"
decl_stmt|;
specifier|public
name|BaseRoomGroup
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseRoomGroup
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
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
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
name|String
name|getAbbv
parameter_list|()
block|{
return|return
name|iAbbv
return|;
block|}
specifier|public
name|void
name|setAbbv
parameter_list|(
name|String
name|abbv
parameter_list|)
block|{
name|iAbbv
operator|=
name|abbv
expr_stmt|;
block|}
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|iDescription
return|;
block|}
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|iDescription
operator|=
name|description
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isGlobal
parameter_list|()
block|{
return|return
name|iGlobal
return|;
block|}
specifier|public
name|Boolean
name|getGlobal
parameter_list|()
block|{
return|return
name|iGlobal
return|;
block|}
specifier|public
name|void
name|setGlobal
parameter_list|(
name|Boolean
name|global
parameter_list|)
block|{
name|iGlobal
operator|=
name|global
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isDefaultGroup
parameter_list|()
block|{
return|return
name|iDefaultGroup
return|;
block|}
specifier|public
name|Boolean
name|getDefaultGroup
parameter_list|()
block|{
return|return
name|iDefaultGroup
return|;
block|}
specifier|public
name|void
name|setDefaultGroup
parameter_list|(
name|Boolean
name|defaultGroup
parameter_list|)
block|{
name|iDefaultGroup
operator|=
name|defaultGroup
expr_stmt|;
block|}
specifier|public
name|Department
name|getDepartment
parameter_list|()
block|{
return|return
name|iDepartment
return|;
block|}
specifier|public
name|void
name|setDepartment
parameter_list|(
name|Department
name|department
parameter_list|)
block|{
name|iDepartment
operator|=
name|department
expr_stmt|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Location
argument_list|>
name|getRooms
parameter_list|()
block|{
return|return
name|iRooms
return|;
block|}
specifier|public
name|void
name|setRooms
parameter_list|(
name|Set
argument_list|<
name|Location
argument_list|>
name|rooms
parameter_list|)
block|{
name|iRooms
operator|=
name|rooms
expr_stmt|;
block|}
specifier|public
name|void
name|addTorooms
parameter_list|(
name|Location
name|location
parameter_list|)
block|{
if|if
condition|(
name|iRooms
operator|==
literal|null
condition|)
name|iRooms
operator|=
operator|new
name|HashSet
argument_list|<
name|Location
argument_list|>
argument_list|()
expr_stmt|;
name|iRooms
operator|.
name|add
argument_list|(
name|location
argument_list|)
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
name|RoomGroup
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
name|RoomGroup
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
name|RoomGroup
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
literal|"RoomGroup["
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
literal|"RoomGroup["
operator|+
literal|"\n	Abbv: "
operator|+
name|getAbbv
argument_list|()
operator|+
literal|"\n	DefaultGroup: "
operator|+
name|getDefaultGroup
argument_list|()
operator|+
literal|"\n	Department: "
operator|+
name|getDepartment
argument_list|()
operator|+
literal|"\n	Description: "
operator|+
name|getDescription
argument_list|()
operator|+
literal|"\n	Global: "
operator|+
name|getGlobal
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
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

