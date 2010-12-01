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
name|form
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamInfoModel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamInfoForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|424087977258798931L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|ExamInfoModel
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
name|boolean
name|iComputeSuggestions
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iFilter
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iDepth
init|=
literal|2
decl_stmt|;
specifier|private
name|long
name|iTimeout
init|=
literal|5000
decl_stmt|;
specifier|private
name|int
name|iLimit
init|=
literal|30
decl_stmt|;
specifier|private
name|Long
index|[]
name|iRoomFeatures
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
index|[]
name|iRoomTypes
init|=
literal|null
decl_stmt|;
specifier|private
name|Long
index|[]
name|iRoomGroups
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
name|iRoomOrder
operator|=
name|sRoomOrdNameAsc
expr_stmt|;
name|iComputeSuggestions
operator|=
literal|false
expr_stmt|;
name|iFilter
operator|=
literal|null
expr_stmt|;
name|iDepth
operator|=
literal|2
expr_stmt|;
name|iTimeout
operator|=
literal|5000
expr_stmt|;
name|iLimit
operator|=
literal|30
expr_stmt|;
name|iRoomTypes
operator|=
literal|null
expr_stmt|;
name|iRoomFeatures
operator|=
literal|null
expr_stmt|;
name|iRoomGroups
operator|=
literal|null
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
literal|"ExamInfo.RoomOrd"
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
literal|"ExamInfo.MinRoomSize"
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
literal|"ExamInfo.MaxRoomSize"
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
literal|"ExamInfo.RoomFilter"
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
literal|"ExamInfo.AllowRoomConflict"
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
literal|"ExamInfo.Filter"
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.Limit"
argument_list|)
operator|!=
literal|null
condition|)
name|iLimit
operator|=
operator|(
name|Integer
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.Limit"
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.Depth"
argument_list|)
operator|!=
literal|null
condition|)
name|iDepth
operator|=
operator|(
name|Integer
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.Depth"
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.Timeout"
argument_list|)
operator|!=
literal|null
condition|)
name|iTimeout
operator|=
operator|(
name|Long
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.Timeout"
argument_list|)
expr_stmt|;
if|if
condition|(
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.ComputeSuggestions"
argument_list|)
operator|!=
literal|null
condition|)
name|iComputeSuggestions
operator|=
operator|(
name|Boolean
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.ComputeSuggestions"
argument_list|)
expr_stmt|;
name|iRoomTypes
operator|=
operator|(
name|Long
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.RoomTypes"
argument_list|)
expr_stmt|;
name|iRoomGroups
operator|=
operator|(
name|Long
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.RoomGroups"
argument_list|)
expr_stmt|;
name|iRoomFeatures
operator|=
operator|(
name|Long
index|[]
operator|)
name|session
operator|.
name|getAttribute
argument_list|(
literal|"ExamInfo.RoomFeatures"
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
literal|"ExamInfo.RoomOrd"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.RoomOrd"
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
literal|"ExamInfo.MinRoomSize"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.MinRoomSize"
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
literal|"ExamInfo.MaxRoomSize"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.MaxRoomSize"
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
literal|"ExamInfo.RoomFilter"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.RoomFilter"
argument_list|,
name|iRoomFilter
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.AllowRoomConflict"
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
literal|"ExamInfo.Filter"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.Filter"
argument_list|,
name|iFilter
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.Depth"
argument_list|,
name|iDepth
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.Timeout"
argument_list|,
name|iTimeout
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.Limit"
argument_list|,
name|iLimit
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.ComputeSuggestions"
argument_list|,
name|iComputeSuggestions
argument_list|)
expr_stmt|;
if|if
condition|(
name|iRoomTypes
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ExamInfo.RoomTypes"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.RoomTypes"
argument_list|,
name|iRoomTypes
argument_list|)
expr_stmt|;
if|if
condition|(
name|iRoomGroups
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ExamInfo.RoomGroups"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.RoomGroups"
argument_list|,
name|iRoomGroups
argument_list|)
expr_stmt|;
if|if
condition|(
name|iRoomFeatures
operator|==
literal|null
condition|)
name|session
operator|.
name|removeAttribute
argument_list|(
literal|"ExamInfo.RoomFeatures"
argument_list|)
expr_stmt|;
else|else
name|session
operator|.
name|setAttribute
argument_list|(
literal|"ExamInfo.RoomFeatures"
argument_list|,
name|iRoomFeatures
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
name|ExamInfoModel
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
name|ExamInfoModel
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
name|boolean
name|getComputeSuggestions
parameter_list|()
block|{
return|return
name|iComputeSuggestions
return|;
block|}
specifier|public
name|void
name|setComputeSuggestions
parameter_list|(
name|boolean
name|computeSuggestions
parameter_list|)
block|{
name|iComputeSuggestions
operator|=
name|computeSuggestions
expr_stmt|;
block|}
specifier|public
name|int
name|getLimit
parameter_list|()
block|{
return|return
name|iLimit
return|;
block|}
specifier|public
name|void
name|setLimit
parameter_list|(
name|int
name|limit
parameter_list|)
block|{
name|iLimit
operator|=
name|limit
expr_stmt|;
block|}
specifier|public
name|int
name|getDepth
parameter_list|()
block|{
return|return
name|iDepth
return|;
block|}
specifier|public
name|void
name|setDepth
parameter_list|(
name|int
name|depth
parameter_list|)
block|{
name|iDepth
operator|=
name|depth
expr_stmt|;
block|}
specifier|public
name|long
name|getTimeout
parameter_list|()
block|{
return|return
name|iTimeout
return|;
block|}
specifier|public
name|void
name|setTimeout
parameter_list|(
name|long
name|timeout
parameter_list|)
block|{
name|iTimeout
operator|=
name|timeout
expr_stmt|;
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
name|Long
index|[]
name|getRoomTypes
parameter_list|()
block|{
return|return
name|iRoomTypes
return|;
block|}
specifier|public
name|void
name|setRoomTypes
parameter_list|(
name|Long
index|[]
name|rts
parameter_list|)
block|{
name|iRoomTypes
operator|=
name|rts
expr_stmt|;
block|}
specifier|public
name|Long
index|[]
name|getRoomGroups
parameter_list|()
block|{
return|return
name|iRoomGroups
return|;
block|}
specifier|public
name|void
name|setRoomGroups
parameter_list|(
name|Long
index|[]
name|rgs
parameter_list|)
block|{
name|iRoomGroups
operator|=
name|rgs
expr_stmt|;
block|}
specifier|public
name|Long
index|[]
name|getRoomFeatures
parameter_list|()
block|{
return|return
name|iRoomFeatures
return|;
block|}
specifier|public
name|void
name|setRoomFeatures
parameter_list|(
name|Long
index|[]
name|rfs
parameter_list|)
block|{
name|iRoomFeatures
operator|=
name|rfs
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|RoomFeature
argument_list|>
name|getAllRoomFeatures
parameter_list|()
block|{
return|return
name|RoomFeature
operator|.
name|getAllGlobalRoomFeatures
argument_list|()
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|RoomGroup
argument_list|>
name|getAllRoomGroups
parameter_list|()
block|{
return|return
name|RoomGroup
operator|.
name|getAllGlobalRoomGroups
argument_list|()
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|RoomType
argument_list|>
name|getAllRoomTypes
parameter_list|()
block|{
return|return
name|RoomType
operator|.
name|findAll
argument_list|()
return|;
block|}
block|}
end_class

end_unit

