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
name|onlinesectioning
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Externalizable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectInput
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ObjectOutput
import|;
end_import

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
name|org
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|model
operator|.
name|RoomLocation
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
name|DistanceMetric
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|Externalizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|infinispan
operator|.
name|commons
operator|.
name|marshall
operator|.
name|SerializeWith
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|SerializeWith
argument_list|(
name|XRoom
operator|.
name|XRoomSerializer
operator|.
name|class
argument_list|)
specifier|public
class|class
name|XRoom
implements|implements
name|Serializable
implements|,
name|Externalizable
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
name|iExternalId
decl_stmt|;
specifier|private
name|boolean
name|iIgnoreTooFar
decl_stmt|;
specifier|private
name|Double
name|iX
decl_stmt|,
name|iY
decl_stmt|;
specifier|public
name|XRoom
parameter_list|()
block|{
block|}
specifier|public
name|XRoom
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|readExternal
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
specifier|public
name|XRoom
parameter_list|(
name|Location
name|location
parameter_list|)
block|{
name|iUniqueId
operator|=
name|location
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iExternalId
operator|=
name|location
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|location
operator|.
name|getLabelWithDisplayName
argument_list|()
expr_stmt|;
name|iIgnoreTooFar
operator|=
name|location
operator|.
name|isIgnoreTooFar
argument_list|()
expr_stmt|;
name|iX
operator|=
name|location
operator|.
name|getCoordinateX
argument_list|()
expr_stmt|;
name|iY
operator|=
name|location
operator|.
name|getCoordinateY
argument_list|()
expr_stmt|;
block|}
specifier|public
name|XRoom
parameter_list|(
name|RoomLocation
name|location
parameter_list|)
block|{
name|iUniqueId
operator|=
name|location
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|location
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iIgnoreTooFar
operator|=
name|location
operator|.
name|getIgnoreTooFar
argument_list|()
expr_stmt|;
name|iX
operator|=
name|location
operator|.
name|getPosX
argument_list|()
expr_stmt|;
name|iY
operator|=
name|location
operator|.
name|getPosY
argument_list|()
expr_stmt|;
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
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|iExternalId
return|;
block|}
specifier|public
name|boolean
name|getIgnoreTooFar
parameter_list|()
block|{
return|return
name|iIgnoreTooFar
return|;
block|}
specifier|public
name|Double
name|getX
parameter_list|()
block|{
return|return
name|iX
return|;
block|}
specifier|public
name|Double
name|getY
parameter_list|()
block|{
return|return
name|iY
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
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
name|XRoom
operator|)
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
name|XRoom
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|int
name|getDistanceInMinutes
parameter_list|(
name|DistanceMetric
name|m
parameter_list|,
name|XRoom
name|other
parameter_list|)
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|0
return|;
if|if
condition|(
name|getIgnoreTooFar
argument_list|()
operator|||
name|other
operator|.
name|getIgnoreTooFar
argument_list|()
condition|)
return|return
literal|0
return|;
return|return
name|m
operator|.
name|getDistanceInMinutes
argument_list|(
name|getUniqueId
argument_list|()
argument_list|,
name|getX
argument_list|()
argument_list|,
name|getY
argument_list|()
argument_list|,
name|other
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|other
operator|.
name|getX
argument_list|()
argument_list|,
name|other
operator|.
name|getY
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|readExternal
parameter_list|(
name|ObjectInput
name|in
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
name|iUniqueId
operator|=
name|in
operator|.
name|readLong
argument_list|()
expr_stmt|;
name|iName
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iExternalId
operator|=
operator|(
name|String
operator|)
name|in
operator|.
name|readObject
argument_list|()
expr_stmt|;
name|iIgnoreTooFar
operator|=
name|in
operator|.
name|readBoolean
argument_list|()
expr_stmt|;
if|if
condition|(
name|in
operator|.
name|readBoolean
argument_list|()
condition|)
block|{
name|iX
operator|=
name|in
operator|.
name|readDouble
argument_list|()
expr_stmt|;
name|iY
operator|=
name|in
operator|.
name|readDouble
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|writeExternal
parameter_list|(
name|ObjectOutput
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|writeLong
argument_list|(
name|iUniqueId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iName
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeObject
argument_list|(
name|iExternalId
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iIgnoreTooFar
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeBoolean
argument_list|(
name|iX
operator|!=
literal|null
operator|&&
name|iY
operator|!=
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|iX
operator|!=
literal|null
operator|&&
name|iY
operator|!=
literal|null
condition|)
block|{
name|out
operator|.
name|writeDouble
argument_list|(
name|iX
argument_list|)
expr_stmt|;
name|out
operator|.
name|writeDouble
argument_list|(
name|iY
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|XRoomSerializer
implements|implements
name|Externalizer
argument_list|<
name|XRoom
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|writeObject
parameter_list|(
name|ObjectOutput
name|output
parameter_list|,
name|XRoom
name|object
parameter_list|)
throws|throws
name|IOException
block|{
name|object
operator|.
name|writeExternal
argument_list|(
name|output
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|XRoom
name|readObject
parameter_list|(
name|ObjectInput
name|input
parameter_list|)
throws|throws
name|IOException
throws|,
name|ClassNotFoundException
block|{
return|return
operator|new
name|XRoom
argument_list|(
name|input
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

