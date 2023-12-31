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
name|RoomFeature
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
name|RoomFeatureType
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseRoomFeature
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
name|iLabel
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
name|RoomFeatureType
name|iFeatureType
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
name|PROP_LABEL
init|=
literal|"label"
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
name|BaseRoomFeature
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseRoomFeature
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
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|iLabel
operator|=
name|label
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
name|RoomFeatureType
name|getFeatureType
parameter_list|()
block|{
return|return
name|iFeatureType
return|;
block|}
specifier|public
name|void
name|setFeatureType
parameter_list|(
name|RoomFeatureType
name|featureType
parameter_list|)
block|{
name|iFeatureType
operator|=
name|featureType
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
name|RoomFeature
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
name|RoomFeature
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
name|RoomFeature
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
literal|"RoomFeature["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|getLabel
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
literal|"RoomFeature["
operator|+
literal|"\n	Abbv: "
operator|+
name|getAbbv
argument_list|()
operator|+
literal|"\n	Description: "
operator|+
name|getDescription
argument_list|()
operator|+
literal|"\n	FeatureType: "
operator|+
name|getFeatureType
argument_list|()
operator|+
literal|"\n	Label: "
operator|+
name|getLabel
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

