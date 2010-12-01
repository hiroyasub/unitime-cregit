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
name|ArrayList
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
name|Preference
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
name|util
operator|.
name|DynamicList
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
name|util
operator|.
name|DynamicListObjectFactory
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-12-2006  *   * XDoclet definition:  * @struts.form name="editRoomGroupForm"  */
end_comment

begin_class
specifier|public
class|class
name|EditRoomGroupForm
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
name|name
decl_stmt|;
specifier|private
name|String
name|doit
decl_stmt|;
specifier|private
name|List
name|globalRoomGroupIds
decl_stmt|;
specifier|private
name|List
name|managerRoomGroupIds
decl_stmt|;
specifier|private
name|List
name|globalRoomGroupNames
decl_stmt|;
specifier|private
name|List
name|managerRoomGroupNames
decl_stmt|;
specifier|private
name|List
name|globalRoomGroupsEditable
decl_stmt|;
specifier|private
name|List
name|managerRoomGroupsEditable
decl_stmt|;
specifier|private
name|List
name|globalRoomGroupsAssigned
decl_stmt|;
specifier|private
name|List
name|managerRoomGroupsAssigned
decl_stmt|;
comment|// --------------------------------------------------------- Classes
comment|/** Factory to create dynamic list element for room groups */
specifier|protected
name|DynamicListObjectFactory
name|factoryRoomGroups
init|=
operator|new
name|DynamicListObjectFactory
argument_list|()
block|{
specifier|public
name|Object
name|create
parameter_list|()
block|{
return|return
operator|new
name|String
argument_list|(
name|Preference
operator|.
name|BLANK_PREF_VALUE
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5665231020466902579L
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
name|globalRoomGroupIds
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
expr_stmt|;
name|managerRoomGroupIds
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
expr_stmt|;
name|globalRoomGroupNames
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
expr_stmt|;
name|managerRoomGroupNames
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
expr_stmt|;
name|globalRoomGroupsEditable
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
expr_stmt|;
name|managerRoomGroupsEditable
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
expr_stmt|;
name|globalRoomGroupsAssigned
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
expr_stmt|;
name|managerRoomGroupsAssigned
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomGroups
argument_list|)
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
name|List
name|getGlobalRoomGroupIds
parameter_list|()
block|{
return|return
name|globalRoomGroupIds
return|;
block|}
specifier|public
name|void
name|setGlobalRoomGroupIds
parameter_list|(
name|List
name|globalRoomGroupIds
parameter_list|)
block|{
name|this
operator|.
name|globalRoomGroupIds
operator|=
name|globalRoomGroupIds
expr_stmt|;
block|}
specifier|public
name|List
name|getGlobalRoomGroupNames
parameter_list|()
block|{
return|return
name|globalRoomGroupNames
return|;
block|}
specifier|public
name|void
name|setGlobalRoomGroupNames
parameter_list|(
name|List
name|globalRoomGroupNames
parameter_list|)
block|{
name|this
operator|.
name|globalRoomGroupNames
operator|=
name|globalRoomGroupNames
expr_stmt|;
block|}
specifier|public
name|List
name|getGlobalRoomGroupsEditable
parameter_list|()
block|{
return|return
name|globalRoomGroupsEditable
return|;
block|}
specifier|public
name|void
name|setGlobalRoomGroupsEditable
parameter_list|(
name|List
name|globalRoomGroupsEditable
parameter_list|)
block|{
name|this
operator|.
name|globalRoomGroupsEditable
operator|=
name|globalRoomGroupsEditable
expr_stmt|;
block|}
specifier|public
name|String
name|getGlobalRoomGroupsEditable
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|globalRoomGroupsEditable
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setGlobalRoomGroupsEditable
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|globalRoomGroupsEditable
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
name|getManagerRoomGroupIds
parameter_list|()
block|{
return|return
name|managerRoomGroupIds
return|;
block|}
specifier|public
name|void
name|setManagerRoomGroupIds
parameter_list|(
name|List
name|managerRoomGroupIds
parameter_list|)
block|{
name|this
operator|.
name|managerRoomGroupIds
operator|=
name|managerRoomGroupIds
expr_stmt|;
block|}
specifier|public
name|List
name|getManagerRoomGroupNames
parameter_list|()
block|{
return|return
name|managerRoomGroupNames
return|;
block|}
specifier|public
name|void
name|setManagerRoomGroupNames
parameter_list|(
name|List
name|managerRoomGroupNames
parameter_list|)
block|{
name|this
operator|.
name|managerRoomGroupNames
operator|=
name|managerRoomGroupNames
expr_stmt|;
block|}
specifier|public
name|List
name|getManagerRoomGroupsEditable
parameter_list|()
block|{
return|return
name|managerRoomGroupsEditable
return|;
block|}
specifier|public
name|void
name|setManagerRoomGroupsEditable
parameter_list|(
name|List
name|managerRoomGroupsEditable
parameter_list|)
block|{
name|this
operator|.
name|managerRoomGroupsEditable
operator|=
name|managerRoomGroupsEditable
expr_stmt|;
block|}
specifier|public
name|String
name|getManagerRoomGroupsEditable
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|managerRoomGroupsEditable
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setManagerRoomGroupsEditable
parameter_list|(
name|int
name|key
parameter_list|,
name|Object
name|value
parameter_list|)
block|{
name|this
operator|.
name|managerRoomGroupsEditable
operator|.
name|set
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToGlobalRoomGroups
parameter_list|(
name|RoomGroup
name|rf
parameter_list|,
name|Boolean
name|editable
parameter_list|,
name|Boolean
name|assigned
parameter_list|)
block|{
name|this
operator|.
name|globalRoomGroupIds
operator|.
name|add
argument_list|(
name|rf
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|globalRoomGroupNames
operator|.
name|add
argument_list|(
name|rf
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|globalRoomGroupsEditable
operator|.
name|add
argument_list|(
name|editable
argument_list|)
expr_stmt|;
name|this
operator|.
name|globalRoomGroupsAssigned
operator|.
name|add
argument_list|(
name|assigned
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToMangaerRoomGroups
parameter_list|(
name|RoomGroup
name|rf
parameter_list|,
name|Boolean
name|editable
parameter_list|,
name|Boolean
name|assigned
parameter_list|)
block|{
name|this
operator|.
name|managerRoomGroupIds
operator|.
name|add
argument_list|(
name|rf
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|managerRoomGroupNames
operator|.
name|add
argument_list|(
name|rf
operator|.
name|getName
argument_list|()
operator|+
literal|" ("
operator|+
operator|(
name|rf
operator|.
name|getDepartment
argument_list|()
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
name|rf
operator|.
name|getDepartment
argument_list|()
operator|.
name|getExternalMgrLabel
argument_list|()
else|:
name|rf
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|+
literal|" - "
operator|+
name|rf
operator|.
name|getDepartment
argument_list|()
operator|.
name|getName
argument_list|()
operator|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|this
operator|.
name|managerRoomGroupsEditable
operator|.
name|add
argument_list|(
name|editable
argument_list|)
expr_stmt|;
name|this
operator|.
name|managerRoomGroupsAssigned
operator|.
name|add
argument_list|(
name|assigned
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
name|getGlobalRoomGroupsAssigned
parameter_list|()
block|{
return|return
name|globalRoomGroupsAssigned
return|;
block|}
specifier|public
name|void
name|setGlobalRoomGroupsAssigned
parameter_list|(
name|List
name|globalRoomGroupsAssigned
parameter_list|)
block|{
name|this
operator|.
name|globalRoomGroupsAssigned
operator|=
name|globalRoomGroupsAssigned
expr_stmt|;
block|}
specifier|public
name|List
name|getManagerRoomGroupsAssigned
parameter_list|()
block|{
return|return
name|managerRoomGroupsAssigned
return|;
block|}
specifier|public
name|void
name|setManagerRoomGroupsAssigned
parameter_list|(
name|List
name|managerRoomGroupsAssigned
parameter_list|)
block|{
name|this
operator|.
name|managerRoomGroupsAssigned
operator|=
name|managerRoomGroupsAssigned
expr_stmt|;
block|}
block|}
end_class

end_unit

