begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessage
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
name|dao
operator|.
name|RoomTypeDAO
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomTypeEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3139971302727896389L
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iReference
decl_stmt|;
specifier|private
name|String
name|iLabel
decl_stmt|;
specifier|private
name|boolean
name|iCanEdit
init|=
literal|false
decl_stmt|;
specifier|private
name|int
name|iType
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iOrder
init|=
operator|-
literal|1
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
if|if
condition|(
name|iReference
operator|==
literal|null
operator|||
name|iReference
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"reference"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
try|try
block|{
name|RoomType
name|rt
init|=
name|RoomType
operator|.
name|findByReference
argument_list|(
name|iReference
argument_list|)
decl_stmt|;
if|if
condition|(
name|rt
operator|!=
literal|null
operator|&&
operator|!
name|rt
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|iUniqueId
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"reference"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|iReference
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"reference"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|iLabel
operator|==
literal|null
operator|||
name|iLabel
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"label"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"List"
expr_stmt|;
name|iUniqueId
operator|=
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|iReference
operator|=
literal|null
expr_stmt|;
name|iLabel
operator|=
literal|null
expr_stmt|;
name|iCanEdit
operator|=
literal|false
expr_stmt|;
name|iType
operator|=
literal|0
expr_stmt|;
name|iOrder
operator|=
name|RoomType
operator|.
name|findAll
argument_list|()
operator|.
name|size
argument_list|()
expr_stmt|;
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
name|setReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|iReference
operator|=
name|reference
expr_stmt|;
block|}
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|iReference
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
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setOrder
parameter_list|(
name|int
name|order
parameter_list|)
block|{
name|iOrder
operator|=
name|order
expr_stmt|;
block|}
specifier|public
name|int
name|getOrder
parameter_list|()
block|{
return|return
name|iOrder
return|;
block|}
specifier|public
name|void
name|setCanEdit
parameter_list|(
name|boolean
name|canEdit
parameter_list|)
block|{
name|iCanEdit
operator|=
name|canEdit
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCanEdit
parameter_list|()
block|{
return|return
name|iCanEdit
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|int
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|RoomType
name|t
parameter_list|)
block|{
if|if
condition|(
name|t
operator|==
literal|null
condition|)
block|{
name|reset
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|setCanEdit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setOp
argument_list|(
literal|"Save"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setUniqueId
argument_list|(
name|t
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|setReference
argument_list|(
name|t
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|setLabel
argument_list|(
name|t
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|setType
argument_list|(
name|t
operator|.
name|isRoom
argument_list|()
condition|?
literal|0
else|:
literal|1
argument_list|)
expr_stmt|;
name|setCanEdit
argument_list|(
name|t
operator|.
name|countRooms
argument_list|()
operator|==
literal|0
argument_list|)
expr_stmt|;
name|setOrder
argument_list|(
name|t
operator|.
name|getOrd
argument_list|()
argument_list|)
expr_stmt|;
name|setOp
argument_list|(
literal|"Update"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|RoomType
name|saveOrUpdate
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
name|RoomType
name|t
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>=
literal|0
condition|)
name|t
operator|=
name|RoomTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|t
operator|==
literal|null
condition|)
name|t
operator|=
operator|new
name|RoomType
argument_list|()
expr_stmt|;
name|t
operator|.
name|setReference
argument_list|(
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setLabel
argument_list|(
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|t
operator|.
name|setRoom
argument_list|(
name|getType
argument_list|()
operator|==
literal|0
argument_list|)
expr_stmt|;
if|if
condition|(
name|t
operator|.
name|getOrd
argument_list|()
operator|==
literal|null
condition|)
name|t
operator|.
name|setOrd
argument_list|(
name|RoomType
operator|.
name|findAll
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|setUniqueId
argument_list|(
name|t
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|t
return|;
block|}
specifier|public
name|void
name|delete
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|<
literal|0
condition|)
return|return;
name|RoomType
name|t
init|=
name|RoomTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|RoomType
name|other
range|:
name|RoomTypeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|hibSession
argument_list|)
control|)
block|{
if|if
condition|(
name|other
operator|.
name|getOrd
argument_list|()
operator|>
name|t
operator|.
name|getOrd
argument_list|()
condition|)
block|{
name|other
operator|.
name|setOrd
argument_list|(
name|other
operator|.
name|getOrd
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|other
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|delete
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

