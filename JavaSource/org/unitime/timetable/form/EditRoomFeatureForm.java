begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|DepartmentRoomFeature
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
name|GlobalRoomFeature
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
comment|/**   * MyEclipse Struts  * Creation date: 05-12-2006  *   * XDoclet definition:  * @struts.form name="editRoomFeatureForm"  */
end_comment

begin_class
specifier|public
class|class
name|EditRoomFeatureForm
extends|extends
name|ActionForm
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|7728130917482276173L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
name|String
name|doit
decl_stmt|;
name|String
name|id
decl_stmt|;
name|String
name|roomLabel
decl_stmt|;
specifier|private
name|List
name|globalRoomFeatureIds
decl_stmt|;
specifier|private
name|List
name|departmentRoomFeatureIds
decl_stmt|;
specifier|private
name|List
name|globalRoomFeatureNames
decl_stmt|;
specifier|private
name|List
name|departmentRoomFeatureNames
decl_stmt|;
specifier|private
name|List
name|globalRoomFeaturesEditable
decl_stmt|;
specifier|private
name|List
name|departmentRoomFeaturesEditable
decl_stmt|;
specifier|private
name|List
name|globalRoomFeaturesAssigned
decl_stmt|;
specifier|private
name|List
name|departmentRoomFeaturesAssigned
decl_stmt|;
comment|// --------------------------------------------------------- Classes
comment|/** Factory to create dynamic list element for room groups */
specifier|protected
name|DynamicListObjectFactory
name|factoryRoomFeatures
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
name|globalRoomFeatureIds
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
expr_stmt|;
name|departmentRoomFeatureIds
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
expr_stmt|;
name|globalRoomFeatureNames
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
expr_stmt|;
name|departmentRoomFeatureNames
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
expr_stmt|;
name|globalRoomFeaturesEditable
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
expr_stmt|;
name|departmentRoomFeaturesEditable
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
expr_stmt|;
name|globalRoomFeaturesAssigned
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
expr_stmt|;
name|departmentRoomFeaturesAssigned
operator|=
name|DynamicList
operator|.
name|getInstance
argument_list|(
operator|new
name|ArrayList
argument_list|()
argument_list|,
name|factoryRoomFeatures
argument_list|)
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
name|getRoomLabel
parameter_list|()
block|{
return|return
name|roomLabel
return|;
block|}
specifier|public
name|void
name|setRoomLabel
parameter_list|(
name|String
name|roomLabel
parameter_list|)
block|{
name|this
operator|.
name|roomLabel
operator|=
name|roomLabel
expr_stmt|;
block|}
specifier|public
name|List
name|getGlobalRoomFeatureIds
parameter_list|()
block|{
return|return
name|globalRoomFeatureIds
return|;
block|}
specifier|public
name|void
name|setGlobalRoomFeatureIds
parameter_list|(
name|List
name|globalRoomFeatureIds
parameter_list|)
block|{
name|this
operator|.
name|globalRoomFeatureIds
operator|=
name|globalRoomFeatureIds
expr_stmt|;
block|}
specifier|public
name|List
name|getGlobalRoomFeatureNames
parameter_list|()
block|{
return|return
name|globalRoomFeatureNames
return|;
block|}
specifier|public
name|void
name|setGlobalRoomFeatureNames
parameter_list|(
name|List
name|globalRoomFeatureNames
parameter_list|)
block|{
name|this
operator|.
name|globalRoomFeatureNames
operator|=
name|globalRoomFeatureNames
expr_stmt|;
block|}
specifier|public
name|List
name|getGlobalRoomFeaturesEditable
parameter_list|()
block|{
return|return
name|globalRoomFeaturesEditable
return|;
block|}
specifier|public
name|void
name|setGlobalRoomFeaturesEditable
parameter_list|(
name|List
name|globalRoomFeaturesEditable
parameter_list|)
block|{
name|this
operator|.
name|globalRoomFeaturesEditable
operator|=
name|globalRoomFeaturesEditable
expr_stmt|;
block|}
specifier|public
name|String
name|getGlobalRoomFeaturesEditable
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|globalRoomFeaturesEditable
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
name|setGlobalRoomFeaturesEditable
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
name|globalRoomFeaturesEditable
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
name|getDepartmentRoomFeatureIds
parameter_list|()
block|{
return|return
name|departmentRoomFeatureIds
return|;
block|}
specifier|public
name|void
name|setDepartmentRoomFeatureIds
parameter_list|(
name|List
name|departmentRoomFeatureIds
parameter_list|)
block|{
name|this
operator|.
name|departmentRoomFeatureIds
operator|=
name|departmentRoomFeatureIds
expr_stmt|;
block|}
specifier|public
name|List
name|getDepartmentRoomFeatureNames
parameter_list|()
block|{
return|return
name|departmentRoomFeatureNames
return|;
block|}
specifier|public
name|void
name|setDepartmentRoomFeatureNames
parameter_list|(
name|List
name|departmentRoomFeatureNames
parameter_list|)
block|{
name|this
operator|.
name|departmentRoomFeatureNames
operator|=
name|departmentRoomFeatureNames
expr_stmt|;
block|}
specifier|public
name|List
name|getDepartmentRoomFeaturesEditable
parameter_list|()
block|{
return|return
name|departmentRoomFeaturesEditable
return|;
block|}
specifier|public
name|void
name|setDepartmentRoomFeaturesEditable
parameter_list|(
name|List
name|departmentRoomFeaturesEditable
parameter_list|)
block|{
name|this
operator|.
name|departmentRoomFeaturesEditable
operator|=
name|departmentRoomFeaturesEditable
expr_stmt|;
block|}
specifier|public
name|String
name|getdepartmentRoomFeaturesEditable
parameter_list|(
name|int
name|key
parameter_list|)
block|{
return|return
name|departmentRoomFeaturesEditable
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
name|setdepartmentRoomFeaturesEditable
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
name|departmentRoomFeaturesEditable
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
name|addToGlobalRoomFeatures
parameter_list|(
name|GlobalRoomFeature
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
name|globalRoomFeatureIds
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
name|globalRoomFeatureNames
operator|.
name|add
argument_list|(
name|rf
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|globalRoomFeaturesEditable
operator|.
name|add
argument_list|(
name|editable
argument_list|)
expr_stmt|;
name|this
operator|.
name|globalRoomFeaturesAssigned
operator|.
name|add
argument_list|(
name|assigned
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addToDepartmentRoomFeatures
parameter_list|(
name|DepartmentRoomFeature
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
name|departmentRoomFeatureIds
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
name|departmentRoomFeatureNames
operator|.
name|add
argument_list|(
name|rf
operator|.
name|getLabel
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
name|departmentRoomFeaturesEditable
operator|.
name|add
argument_list|(
name|editable
argument_list|)
expr_stmt|;
name|this
operator|.
name|departmentRoomFeaturesAssigned
operator|.
name|add
argument_list|(
name|assigned
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
name|getGlobalRoomFeaturesAssigned
parameter_list|()
block|{
return|return
name|globalRoomFeaturesAssigned
return|;
block|}
specifier|public
name|void
name|setGlobalRoomFeaturesAssigned
parameter_list|(
name|List
name|globalRoomFeaturesAssigned
parameter_list|)
block|{
name|this
operator|.
name|globalRoomFeaturesAssigned
operator|=
name|globalRoomFeaturesAssigned
expr_stmt|;
block|}
specifier|public
name|List
name|getDepartmentRoomFeaturesAssigned
parameter_list|()
block|{
return|return
name|departmentRoomFeaturesAssigned
return|;
block|}
specifier|public
name|void
name|setDepartmentRoomFeaturesAssigned
parameter_list|(
name|List
name|departmentRoomFeaturesAssigned
parameter_list|)
block|{
name|this
operator|.
name|departmentRoomFeaturesAssigned
operator|=
name|departmentRoomFeaturesAssigned
expr_stmt|;
block|}
block|}
end_class

end_unit

