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
name|RoomType
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
name|RoomTypeOption
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseRoomTypeOption
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
name|RoomType
name|iRoomType
decl_stmt|;
specifier|private
name|Department
name|iDepartment
decl_stmt|;
specifier|private
name|Integer
name|iStatus
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|Integer
name|iBreakTime
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_STATUS
init|=
literal|"status"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MESSAGE
init|=
literal|"message"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_BREAK_TIME
init|=
literal|"breakTime"
decl_stmt|;
specifier|public
name|BaseRoomTypeOption
parameter_list|()
block|{
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
name|RoomType
name|getRoomType
parameter_list|()
block|{
return|return
name|iRoomType
return|;
block|}
specifier|public
name|void
name|setRoomType
parameter_list|(
name|RoomType
name|roomType
parameter_list|)
block|{
name|iRoomType
operator|=
name|roomType
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
name|Integer
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|void
name|setStatus
parameter_list|(
name|Integer
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
block|}
specifier|public
name|String
name|getMessage
parameter_list|()
block|{
return|return
name|iMessage
return|;
block|}
specifier|public
name|void
name|setMessage
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|iMessage
operator|=
name|message
expr_stmt|;
block|}
specifier|public
name|Integer
name|getBreakTime
parameter_list|()
block|{
return|return
name|iBreakTime
return|;
block|}
specifier|public
name|void
name|setBreakTime
parameter_list|(
name|Integer
name|breakTime
parameter_list|)
block|{
name|iBreakTime
operator|=
name|breakTime
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
name|RoomTypeOption
operator|)
condition|)
return|return
literal|false
return|;
name|RoomTypeOption
name|roomTypeOption
init|=
operator|(
name|RoomTypeOption
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getRoomType
argument_list|()
operator|==
literal|null
operator|||
name|roomTypeOption
operator|.
name|getRoomType
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|getRoomType
argument_list|()
operator|.
name|equals
argument_list|(
name|roomTypeOption
operator|.
name|getRoomType
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getDepartment
argument_list|()
operator|==
literal|null
operator|||
name|roomTypeOption
operator|.
name|getDepartment
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|getDepartment
argument_list|()
operator|.
name|equals
argument_list|(
name|roomTypeOption
operator|.
name|getDepartment
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getRoomType
argument_list|()
operator|==
literal|null
operator|||
name|getDepartment
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
name|getRoomType
argument_list|()
operator|.
name|hashCode
argument_list|()
operator|^
name|getDepartment
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
literal|"RoomTypeOption["
operator|+
name|getRoomType
argument_list|()
operator|+
literal|", "
operator|+
name|getDepartment
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
literal|"RoomTypeOption["
operator|+
literal|"\n	BreakTime: "
operator|+
name|getBreakTime
argument_list|()
operator|+
literal|"\n	Department: "
operator|+
name|getDepartment
argument_list|()
operator|+
literal|"\n	Message: "
operator|+
name|getMessage
argument_list|()
operator|+
literal|"\n	RoomType: "
operator|+
name|getRoomType
argument_list|()
operator|+
literal|"\n	Status: "
operator|+
name|getStatus
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

