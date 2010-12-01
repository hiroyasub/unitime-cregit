begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseRoomTypeOption
import|;
end_import

begin_class
specifier|public
class|class
name|RoomTypeOption
extends|extends
name|BaseRoomTypeOption
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
name|RoomTypeOption
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|RoomTypeOption
parameter_list|(
name|RoomType
name|roomType
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
name|setRoomType
argument_list|(
name|roomType
argument_list|)
expr_stmt|;
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|final
name|int
name|sStatusNoOptions
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sStatusScheduleEvents
init|=
literal|1
decl_stmt|;
specifier|public
name|boolean
name|can
parameter_list|(
name|int
name|operation
parameter_list|)
block|{
return|return
operator|(
name|getStatus
argument_list|()
operator|.
name|intValue
argument_list|()
operator|&
name|operation
operator|)
operator|==
name|operation
return|;
block|}
specifier|public
name|void
name|set
parameter_list|(
name|int
name|operation
parameter_list|)
block|{
if|if
condition|(
operator|!
name|can
argument_list|(
name|operation
argument_list|)
condition|)
name|setStatus
argument_list|(
name|getStatus
argument_list|()
operator|+
name|operation
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|int
name|operation
parameter_list|)
block|{
if|if
condition|(
name|can
argument_list|(
name|operation
argument_list|)
condition|)
name|setStatus
argument_list|(
name|getStatus
argument_list|()
operator|-
name|operation
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|canScheduleEvents
parameter_list|()
block|{
return|return
name|can
argument_list|(
name|sStatusScheduleEvents
argument_list|)
return|;
block|}
specifier|public
name|void
name|setScheduleEvents
parameter_list|(
name|boolean
name|enable
parameter_list|)
block|{
if|if
condition|(
name|enable
condition|)
name|set
argument_list|(
name|sStatusScheduleEvents
argument_list|)
expr_stmt|;
else|else
name|reset
argument_list|(
name|sStatusScheduleEvents
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

