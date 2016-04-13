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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseRoomGroupPref
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomGroupPref
extends|extends
name|BaseRoomGroupPref
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
name|RoomGroupPref
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|RoomGroupPref
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
name|preferenceText
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getRoomGroup
argument_list|()
operator|.
name|getName
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|preferenceAbbv
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getRoomGroup
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|)
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|RoomGroupPref
name|pref
init|=
operator|new
name|RoomGroupPref
argument_list|()
decl_stmt|;
name|pref
operator|.
name|setPrefLevel
argument_list|(
name|getPrefLevel
argument_list|()
argument_list|)
expr_stmt|;
name|pref
operator|.
name|setRoomGroup
argument_list|(
name|getRoomGroup
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|pref
return|;
block|}
specifier|public
name|boolean
name|isSame
parameter_list|(
name|Preference
name|other
parameter_list|)
block|{
if|if
condition|(
name|other
operator|==
literal|null
operator|||
operator|!
operator|(
name|other
operator|instanceof
name|RoomGroupPref
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|ToolBox
operator|.
name|equals
argument_list|(
name|getRoomGroup
argument_list|()
argument_list|,
operator|(
operator|(
name|RoomGroupPref
operator|)
name|other
operator|)
operator|.
name|getRoomGroup
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|preferenceTitle
parameter_list|()
block|{
return|return
name|MSG
operator|.
name|prefTitleRoomGroup
argument_list|(
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
argument_list|,
name|getRoomGroup
argument_list|()
operator|.
name|getNameWithTitle
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|Type
operator|.
name|ROOM_GROUP
return|;
block|}
block|}
end_class

end_unit

