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
name|Department
name|department
parameter_list|)
block|{
name|setRoomType
argument_list|(
name|roomType
argument_list|)
expr_stmt|;
name|setDepartment
argument_list|(
name|department
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
enum|enum
name|Status
block|{
name|NoEventManagement
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
block|,
name|AuthenticatedUsersCanRequestEventsManagersCanApprove
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
block|,
name|DepartmentalUsersCanRequestEventsManagersCanApprove
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
block|,
name|EventManagersCanRequestOrApproveEvents
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
block|,
name|AuthenticatedUsersCanRequestEventsNoApproval
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
block|,
name|DepartmentalUsersCanRequestEventsNoApproval
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
block|,
name|EventManagersCanRequestEventsNoApproval
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
block|, 		;
specifier|private
name|boolean
name|iUserRequest
decl_stmt|,
name|iDeptRequest
decl_stmt|,
name|iMgrRequest
decl_stmt|,
name|iMgrApproval
decl_stmt|;
name|Status
parameter_list|(
name|boolean
name|userRequest
parameter_list|,
name|boolean
name|deptRequest
parameter_list|,
name|boolean
name|mgrRequest
parameter_list|,
name|boolean
name|mgrApproval
parameter_list|)
block|{
name|iUserRequest
operator|=
name|userRequest
expr_stmt|;
name|iDeptRequest
operator|=
name|deptRequest
expr_stmt|;
name|iMgrRequest
operator|=
name|mgrRequest
expr_stmt|;
name|iMgrApproval
operator|=
name|mgrApproval
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAuthenticatedUsersCanRequestEvents
parameter_list|()
block|{
return|return
name|iUserRequest
return|;
block|}
specifier|public
name|boolean
name|isDepartmentalUsersCanRequestEvents
parameter_list|()
block|{
return|return
name|iDeptRequest
return|;
block|}
specifier|public
name|boolean
name|isEventManagersCanApprove
parameter_list|()
block|{
return|return
name|iMgrApproval
return|;
block|}
specifier|public
name|boolean
name|isEventManagersCanRequestEvents
parameter_list|()
block|{
return|return
name|iMgrRequest
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
name|name
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"(\\p{Ll})(\\p{Lu})"
argument_list|,
literal|"$1 $2"
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|int
name|getDefaultStatus
parameter_list|()
block|{
return|return
name|Status
operator|.
name|NoEventManagement
operator|.
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|Status
name|getEventStatus
parameter_list|()
block|{
return|return
name|Status
operator|.
name|values
argument_list|()
index|[
name|getStatus
argument_list|()
operator|==
literal|null
condition|?
name|getDefaultStatus
argument_list|()
else|:
name|getStatus
argument_list|()
index|]
return|;
block|}
annotation|@
name|Deprecated
specifier|public
name|boolean
name|canScheduleEvents
parameter_list|()
block|{
switch|switch
condition|(
name|Status
operator|.
name|values
argument_list|()
index|[
name|getStatus
argument_list|()
operator|==
literal|null
condition|?
name|getDefaultStatus
argument_list|()
else|:
name|getStatus
argument_list|()
index|]
condition|)
block|{
case|case
name|NoEventManagement
case|:
return|return
literal|false
return|;
default|default:
return|return
literal|true
return|;
block|}
block|}
block|}
end_class

end_unit

