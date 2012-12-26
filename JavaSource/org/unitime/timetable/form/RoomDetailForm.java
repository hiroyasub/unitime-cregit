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
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|ExamType
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-12-2006  *   * XDoclet definition:  * @struts.form name="roomDetailForm"  */
end_comment

begin_class
specifier|public
class|class
name|RoomDetailForm
extends|extends
name|ActionForm
block|{
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|id
decl_stmt|;
specifier|private
name|String
name|doit
decl_stmt|;
specifier|private
name|String
name|sharingTable
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|externalId
decl_stmt|;
specifier|private
name|Integer
name|capacity
decl_stmt|;
specifier|private
name|Double
name|coordinateX
decl_stmt|;
specifier|private
name|Double
name|coordinateY
decl_stmt|;
specifier|private
name|Long
name|type
decl_stmt|;
specifier|private
name|String
name|typeName
decl_stmt|;
specifier|private
name|Collection
name|groups
decl_stmt|;
specifier|private
name|Collection
name|globalFeatures
decl_stmt|;
specifier|private
name|Collection
name|departmentFeatures
decl_stmt|;
specifier|private
name|List
name|roomPrefs
decl_stmt|;
specifier|private
name|List
name|depts
decl_stmt|;
specifier|private
name|boolean
name|ignoreTooFar
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|ignoreRoomCheck
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|control
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|nonUniv
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|examEnabled
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Integer
name|examCapacity
decl_stmt|;
specifier|private
name|String
name|eventDepartment
decl_stmt|;
specifier|private
name|String
name|area
decl_stmt|;
specifier|private
name|String
name|breakTime
decl_stmt|;
specifier|private
name|String
name|note
decl_stmt|;
specifier|private
name|String
name|eventStatus
decl_stmt|;
specifier|private
name|Long
name|previos
decl_stmt|,
name|next
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|542603705961314236L
decl_stmt|;
comment|/**  	 * Method validate 	 * @param mapping 	 * @param request 	 * @return ActionErrors 	 */
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
return|return
literal|null
return|;
block|}
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 */
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
name|ignoreTooFar
operator|=
literal|false
expr_stmt|;
name|ignoreRoomCheck
operator|=
literal|false
expr_stmt|;
name|control
operator|=
literal|null
expr_stmt|;
name|examEnabled
operator|.
name|clear
argument_list|()
expr_stmt|;
name|previos
operator|=
literal|null
expr_stmt|;
name|next
operator|=
literal|null
expr_stmt|;
name|eventDepartment
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|Integer
name|getCapacity
parameter_list|()
block|{
return|return
name|capacity
return|;
block|}
specifier|public
name|void
name|setCapacity
parameter_list|(
name|Integer
name|capacity
parameter_list|)
block|{
name|this
operator|.
name|capacity
operator|=
name|capacity
expr_stmt|;
block|}
specifier|public
name|Double
name|getCoordinateX
parameter_list|()
block|{
return|return
name|coordinateX
return|;
block|}
specifier|public
name|void
name|setCoordinateX
parameter_list|(
name|Double
name|coordinateX
parameter_list|)
block|{
name|this
operator|.
name|coordinateX
operator|=
name|coordinateX
expr_stmt|;
block|}
specifier|public
name|Double
name|getCoordinateY
parameter_list|()
block|{
return|return
name|coordinateY
return|;
block|}
specifier|public
name|void
name|setCoordinateY
parameter_list|(
name|Double
name|coordinateY
parameter_list|)
block|{
name|this
operator|.
name|coordinateY
operator|=
name|coordinateY
expr_stmt|;
block|}
specifier|public
name|Collection
name|getGlobalFeatures
parameter_list|()
block|{
return|return
name|globalFeatures
return|;
block|}
specifier|public
name|void
name|setGlobalFeatures
parameter_list|(
name|Collection
name|globalFeatures
parameter_list|)
block|{
name|this
operator|.
name|globalFeatures
operator|=
name|globalFeatures
expr_stmt|;
block|}
specifier|public
name|Collection
name|getDepartmentFeatures
parameter_list|()
block|{
return|return
name|departmentFeatures
return|;
block|}
specifier|public
name|void
name|setDepartmentFeatures
parameter_list|(
name|Collection
name|departmentFeatures
parameter_list|)
block|{
name|this
operator|.
name|departmentFeatures
operator|=
name|departmentFeatures
expr_stmt|;
block|}
specifier|public
name|Collection
name|getGroups
parameter_list|()
block|{
return|return
name|groups
return|;
block|}
specifier|public
name|void
name|setGroups
parameter_list|(
name|Collection
name|groups
parameter_list|)
block|{
name|this
operator|.
name|groups
operator|=
name|groups
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
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
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getExternalId
parameter_list|()
block|{
return|return
name|externalId
return|;
block|}
specifier|public
name|void
name|setExternalId
parameter_list|(
name|String
name|externalId
parameter_list|)
block|{
name|this
operator|.
name|externalId
operator|=
name|externalId
expr_stmt|;
block|}
specifier|public
name|Long
name|getType
parameter_list|()
block|{
return|return
name|type
return|;
block|}
specifier|public
name|void
name|setType
parameter_list|(
name|Long
name|type
parameter_list|)
block|{
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|String
name|getTypeName
parameter_list|()
block|{
return|return
name|typeName
return|;
block|}
specifier|public
name|void
name|setTypeName
parameter_list|(
name|String
name|typeName
parameter_list|)
block|{
name|this
operator|.
name|typeName
operator|=
name|typeName
expr_stmt|;
block|}
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
block|}
specifier|public
name|String
name|getSharingTable
parameter_list|()
block|{
return|return
name|sharingTable
return|;
block|}
specifier|public
name|void
name|setSharingTable
parameter_list|(
name|String
name|sharingTable
parameter_list|)
block|{
name|this
operator|.
name|sharingTable
operator|=
name|sharingTable
expr_stmt|;
block|}
specifier|public
name|String
name|getDoit
parameter_list|()
block|{
return|return
name|doit
return|;
block|}
specifier|public
name|void
name|setDoit
parameter_list|(
name|String
name|doit
parameter_list|)
block|{
name|this
operator|.
name|doit
operator|=
name|doit
expr_stmt|;
block|}
specifier|public
name|void
name|setIgnoreTooFar
parameter_list|(
name|boolean
name|ignoreTooFar
parameter_list|)
block|{
name|this
operator|.
name|ignoreTooFar
operator|=
name|ignoreTooFar
expr_stmt|;
block|}
specifier|public
name|boolean
name|getIgnoreTooFar
parameter_list|()
block|{
return|return
name|ignoreTooFar
return|;
block|}
specifier|public
name|boolean
name|isIgnoreRoomCheck
parameter_list|()
block|{
return|return
name|ignoreRoomCheck
return|;
block|}
specifier|public
name|void
name|setIgnoreRoomCheck
parameter_list|(
name|boolean
name|ignoreRoomCheck
parameter_list|)
block|{
name|this
operator|.
name|ignoreRoomCheck
operator|=
name|ignoreRoomCheck
expr_stmt|;
block|}
specifier|public
name|String
name|getControl
parameter_list|()
block|{
return|return
name|control
return|;
block|}
specifier|public
name|void
name|setControl
parameter_list|(
name|String
name|control
parameter_list|)
block|{
name|this
operator|.
name|control
operator|=
name|control
expr_stmt|;
block|}
specifier|public
name|List
name|getRoomPrefs
parameter_list|()
block|{
return|return
name|roomPrefs
return|;
block|}
specifier|public
name|void
name|setRoomPrefs
parameter_list|(
name|List
name|roomPrefs
parameter_list|)
block|{
name|this
operator|.
name|roomPrefs
operator|=
name|roomPrefs
expr_stmt|;
block|}
specifier|public
name|List
name|getDepts
parameter_list|()
block|{
return|return
name|depts
return|;
block|}
specifier|public
name|void
name|setDepts
parameter_list|(
name|List
name|depts
parameter_list|)
block|{
name|this
operator|.
name|depts
operator|=
name|depts
expr_stmt|;
block|}
specifier|public
name|boolean
name|isNonUniv
parameter_list|()
block|{
return|return
name|nonUniv
return|;
block|}
specifier|public
name|void
name|setNonUniv
parameter_list|(
name|boolean
name|nonUniv
parameter_list|)
block|{
name|this
operator|.
name|nonUniv
operator|=
name|nonUniv
expr_stmt|;
block|}
specifier|public
name|Integer
name|getExamCapacity
parameter_list|()
block|{
return|return
name|examCapacity
return|;
block|}
specifier|public
name|void
name|setExamCapacity
parameter_list|(
name|Integer
name|examCapacity
parameter_list|)
block|{
name|this
operator|.
name|examCapacity
operator|=
name|examCapacity
expr_stmt|;
block|}
specifier|public
name|boolean
name|getExamEnabled
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|Boolean
name|enabled
init|=
name|examEnabled
operator|.
name|get
argument_list|(
name|type
argument_list|)
decl_stmt|;
return|return
name|enabled
operator|!=
literal|null
operator|&&
name|enabled
return|;
block|}
specifier|public
name|void
name|setExamEnabled
parameter_list|(
name|String
name|type
parameter_list|,
name|boolean
name|examEnabled
parameter_list|)
block|{
name|this
operator|.
name|examEnabled
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|examEnabled
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getExamEnabledProblems
parameter_list|()
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|ExamType
name|type
range|:
name|ExamType
operator|.
name|findAll
argument_list|()
control|)
block|{
if|if
condition|(
name|getExamEnabled
argument_list|(
name|type
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
literal|", "
expr_stmt|;
name|ret
operator|+=
name|type
operator|.
name|getLabel
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Long
name|getNext
parameter_list|()
block|{
return|return
name|next
return|;
block|}
specifier|public
name|void
name|setNext
parameter_list|(
name|Long
name|next
parameter_list|)
block|{
name|this
operator|.
name|next
operator|=
name|next
expr_stmt|;
block|}
specifier|public
name|Long
name|getPrevious
parameter_list|()
block|{
return|return
name|previos
return|;
block|}
specifier|public
name|void
name|setPrevious
parameter_list|(
name|Long
name|previous
parameter_list|)
block|{
name|this
operator|.
name|previos
operator|=
name|previous
expr_stmt|;
block|}
specifier|public
name|String
name|getEventDepartment
parameter_list|()
block|{
return|return
name|eventDepartment
return|;
block|}
specifier|public
name|void
name|setEventDepartment
parameter_list|(
name|String
name|eventDepartment
parameter_list|)
block|{
name|this
operator|.
name|eventDepartment
operator|=
name|eventDepartment
expr_stmt|;
block|}
specifier|public
name|String
name|getArea
parameter_list|()
block|{
return|return
name|area
return|;
block|}
specifier|public
name|void
name|setArea
parameter_list|(
name|String
name|area
parameter_list|)
block|{
name|this
operator|.
name|area
operator|=
name|area
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|note
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|this
operator|.
name|note
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|String
name|getBreakTime
parameter_list|()
block|{
return|return
name|breakTime
return|;
block|}
specifier|public
name|void
name|setBreakTime
parameter_list|(
name|String
name|breakTime
parameter_list|)
block|{
name|this
operator|.
name|breakTime
operator|=
name|breakTime
expr_stmt|;
block|}
specifier|public
name|String
name|getEventStatus
parameter_list|()
block|{
return|return
name|eventStatus
return|;
block|}
specifier|public
name|void
name|setEventStatus
parameter_list|(
name|String
name|eventStatus
parameter_list|)
block|{
name|this
operator|.
name|eventStatus
operator|=
name|eventStatus
expr_stmt|;
block|}
block|}
end_class

end_unit

