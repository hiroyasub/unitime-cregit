begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|form
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
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
name|solver
operator|.
name|course
operator|.
name|ui
operator|.
name|ClassInfoModel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassInfoForm
extends|extends
name|ActionForm
block|{
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|ClassInfoModel
name|iModel
decl_stmt|;
specifier|private
name|String
name|iMessage
decl_stmt|;
specifier|private
name|String
name|iMinRoomSize
init|=
literal|null
decl_stmt|,
name|iMaxRoomSize
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iRoomFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAllowRoomConflict
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iRoomOrder
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iAllRooms
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iKeepConflictingAssignments
init|=
literal|false
decl_stmt|;
specifier|public
specifier|static
name|String
name|sRoomOrdNameAsc
init|=
literal|"Name [asc]"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sRoomOrdNameDesc
init|=
literal|"Name [desc]"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sRoomOrdSizeAsc
init|=
literal|"Size [asc]"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sRoomOrdSizeDesc
init|=
literal|"Size [desc]"
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sRoomOrds
init|=
operator|new
name|String
index|[]
block|{
name|sRoomOrdNameAsc
block|,
name|sRoomOrdNameDesc
block|,
name|sRoomOrdSizeAsc
block|,
name|sRoomOrdSizeDesc
block|}
decl_stmt|;
specifier|private
name|String
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
return|return
name|errors
return|;
block|}
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|iOp
operator|=
literal|null
expr_stmt|;
name|iModel
operator|=
literal|null
expr_stmt|;
name|iMessage
operator|=
literal|null
expr_stmt|;
name|iMinRoomSize
operator|=
literal|null
expr_stmt|;
name|iMaxRoomSize
operator|=
literal|null
expr_stmt|;
name|iRoomFilter
operator|=
literal|null
expr_stmt|;
name|iAllowRoomConflict
operator|=
literal|false
expr_stmt|;
name|iAllRooms
operator|=
literal|false
expr_stmt|;
name|iRoomOrder
operator|=
name|sRoomOrdNameAsc
expr_stmt|;
name|iFilter
operator|=
literal|null
expr_stmt|;
name|iKeepConflictingAssignments
operator|=
literal|false
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
name|iRoomOrder
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.RoomOrd"
argument_list|)
expr_stmt|;
name|iMinRoomSize
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.MinRoomSize"
argument_list|)
expr_stmt|;
name|iMaxRoomSize
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.MaxRoomSize"
argument_list|)
expr_stmt|;
name|iRoomFilter
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.RoomFilter"
argument_list|)
expr_stmt|;
name|iAllowRoomConflict
operator|=
literal|"true"
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.AllowRoomConflict"
argument_list|)
argument_list|)
expr_stmt|;
name|iAllRooms
operator|=
literal|"true"
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.AllRooms"
argument_list|)
argument_list|)
expr_stmt|;
name|iFilter
operator|=
operator|(
name|String
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.Filter"
argument_list|)
expr_stmt|;
name|iKeepConflictingAssignments
operator|=
literal|"true"
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ClassInfo.KeepConflictingAssignments"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|save
parameter_list|(
name|HttpSession
name|session
parameter_list|)
block|{
if|if
condition|(
name|iRoomOrder
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ClassInfo.RoomOrd"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.RoomOrd"
argument_list|,
name|iRoomOrder
argument_list|)
expr_stmt|;
if|if
condition|(
name|iMinRoomSize
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ClassInfo.MinRoomSize"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.MinRoomSize"
argument_list|,
name|iMinRoomSize
argument_list|)
expr_stmt|;
if|if
condition|(
name|iMaxRoomSize
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ClassInfo.MaxRoomSize"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.MaxRoomSize"
argument_list|,
name|iMaxRoomSize
argument_list|)
expr_stmt|;
if|if
condition|(
name|iRoomFilter
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ClassInfo.RoomFilter"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.RoomFilter"
argument_list|,
name|iRoomFilter
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.AllowRoomConflict"
argument_list|,
operator|(
name|iAllowRoomConflict
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.AllRooms"
argument_list|,
operator|(
name|iAllRooms
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|iKeepConflictingAssignments
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ClassInfo.KeepConflictingAssignments"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.KeepConflictingAssignments"
argument_list|,
operator|(
name|iKeepConflictingAssignments
condition|?
literal|"true"
else|:
literal|"false"
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iFilter
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ClassInfo.Filter"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ClassInfo.Filter"
argument_list|,
name|iFilter
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|ClassInfoModel
name|getModel
parameter_list|()
block|{
return|return
name|iModel
return|;
block|}
specifier|public
name|void
name|setModel
parameter_list|(
name|ClassInfoModel
name|model
parameter_list|)
block|{
name|iModel
operator|=
name|model
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
name|String
name|getMinRoomSize
parameter_list|()
block|{
return|return
name|iMinRoomSize
return|;
block|}
specifier|public
name|void
name|setMinRoomSize
parameter_list|(
name|String
name|minRoomSize
parameter_list|)
block|{
name|iMinRoomSize
operator|=
name|minRoomSize
expr_stmt|;
block|}
specifier|public
name|String
name|getMaxRoomSize
parameter_list|()
block|{
return|return
name|iMaxRoomSize
return|;
block|}
specifier|public
name|void
name|setMaxRoomSize
parameter_list|(
name|String
name|maxRoomSize
parameter_list|)
block|{
name|iMaxRoomSize
operator|=
name|maxRoomSize
expr_stmt|;
block|}
specifier|public
name|String
name|getRoomFilter
parameter_list|()
block|{
return|return
name|iRoomFilter
return|;
block|}
specifier|public
name|void
name|setRoomFilter
parameter_list|(
name|String
name|roomFilter
parameter_list|)
block|{
name|iRoomFilter
operator|=
name|roomFilter
expr_stmt|;
block|}
specifier|public
name|boolean
name|getAllowRoomConflict
parameter_list|()
block|{
return|return
name|iAllowRoomConflict
return|;
block|}
specifier|public
name|void
name|setAllowRoomConflict
parameter_list|(
name|boolean
name|allowRoomConflict
parameter_list|)
block|{
name|iAllowRoomConflict
operator|=
name|allowRoomConflict
expr_stmt|;
block|}
specifier|public
name|boolean
name|getAllRooms
parameter_list|()
block|{
return|return
name|iAllRooms
return|;
block|}
specifier|public
name|void
name|setAllRooms
parameter_list|(
name|boolean
name|allRooms
parameter_list|)
block|{
name|iAllRooms
operator|=
name|allRooms
expr_stmt|;
block|}
specifier|public
name|String
name|getRoomOrder
parameter_list|()
block|{
return|return
name|iRoomOrder
return|;
block|}
specifier|public
name|void
name|setRoomOrder
parameter_list|(
name|String
name|ord
parameter_list|)
block|{
name|iRoomOrder
operator|=
name|ord
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getRoomOrders
parameter_list|()
block|{
return|return
name|sRoomOrds
return|;
block|}
specifier|public
name|String
name|getFilter
parameter_list|()
block|{
return|return
name|iFilter
return|;
block|}
specifier|public
name|void
name|setFilter
parameter_list|(
name|String
name|filter
parameter_list|)
block|{
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
specifier|public
name|boolean
name|getKeepConflictingAssignments
parameter_list|()
block|{
return|return
name|iKeepConflictingAssignments
return|;
block|}
specifier|public
name|void
name|setKeepConflictingAssignments
parameter_list|(
name|boolean
name|unassignConflictingAssignments
parameter_list|)
block|{
name|iKeepConflictingAssignments
operator|=
name|unassignConflictingAssignments
expr_stmt|;
block|}
block|}
end_class

end_unit

