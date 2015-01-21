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
name|Iterator
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
name|commons
operator|.
name|Debug
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
name|Location
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
name|dao
operator|.
name|LocationDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomFeatureEditForm
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
literal|8725073025870425705L
decl_stmt|;
comment|// --------------------------------------------------------- Instance
comment|// Variables
specifier|private
name|String
name|doit
decl_stmt|;
specifier|private
name|String
name|deptCode
decl_stmt|;
specifier|private
name|String
name|deptName
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|abbv
decl_stmt|;
specifier|private
name|String
name|id
decl_stmt|;
specifier|private
name|boolean
name|global
decl_stmt|;
specifier|private
name|Collection
name|assignedRooms
decl_stmt|;
specifier|private
name|Collection
name|notAssignedRooms
decl_stmt|;
specifier|private
name|String
index|[]
name|assigned
decl_stmt|;
specifier|private
name|String
index|[]
name|notAssigned
decl_stmt|;
specifier|private
name|String
index|[]
name|assignedSelected
init|=
block|{}
decl_stmt|;
specifier|private
name|String
index|[]
name|notAssignedSelected
init|=
block|{}
decl_stmt|;
specifier|private
name|Long
name|iSessionId
decl_stmt|;
specifier|private
name|Long
name|featureTypeId
decl_stmt|;
specifier|public
name|Collection
name|getAssignedRooms
parameter_list|()
block|{
return|return
name|assignedRooms
return|;
block|}
specifier|public
name|void
name|setAssignedRooms
parameter_list|(
name|Collection
name|assignedRooms
parameter_list|)
block|{
name|this
operator|.
name|assignedRooms
operator|=
name|assignedRooms
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getAssigned
parameter_list|()
block|{
return|return
name|assigned
return|;
block|}
specifier|public
name|void
name|setAssigned
parameter_list|(
name|String
index|[]
name|assigned
parameter_list|)
block|{
name|this
operator|.
name|assigned
operator|=
name|assigned
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getNotAssigned
parameter_list|()
block|{
return|return
name|notAssigned
return|;
block|}
specifier|public
name|void
name|setNotAssigned
parameter_list|(
name|String
index|[]
name|notAssigned
parameter_list|)
block|{
name|this
operator|.
name|notAssigned
operator|=
name|notAssigned
expr_stmt|;
block|}
specifier|public
name|Collection
name|getNotAssignedRooms
parameter_list|()
block|{
return|return
name|notAssignedRooms
return|;
block|}
specifier|public
name|void
name|setNotAssignedRooms
parameter_list|(
name|Collection
name|notAssignedRooms
parameter_list|)
block|{
name|this
operator|.
name|notAssignedRooms
operator|=
name|notAssignedRooms
expr_stmt|;
block|}
specifier|public
name|boolean
name|isGlobal
parameter_list|()
block|{
return|return
name|global
return|;
block|}
specifier|public
name|void
name|setGlobal
parameter_list|(
name|boolean
name|global
parameter_list|)
block|{
name|this
operator|.
name|global
operator|=
name|global
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
comment|/** 	 *  	 *  	 */
specifier|public
name|void
name|setRooms
parameter_list|()
block|{
name|int
name|i
init|=
literal|0
decl_stmt|;
name|Iterator
name|iter
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|assignedRooms
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|assignedSelection
init|=
operator|new
name|String
index|[
name|assignedRooms
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|assigned
operator|=
operator|new
name|String
index|[
name|assignedRooms
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
for|for
control|(
name|iter
operator|=
name|assignedRooms
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|r
init|=
operator|(
name|Location
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|assignedSelection
index|[
name|i
index|]
operator|=
name|r
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|assigned
index|[
name|i
index|]
operator|=
name|r
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|assignedSelected
operator|=
name|assignedSelection
expr_stmt|;
block|}
name|i
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|notAssignedRooms
operator|!=
literal|null
condition|)
block|{
name|notAssigned
operator|=
operator|new
name|String
index|[
name|notAssignedRooms
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
for|for
control|(
name|iter
operator|=
name|notAssignedRooms
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|r
init|=
operator|(
name|Location
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|notAssigned
index|[
name|i
index|]
operator|=
name|r
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|String
index|[]
name|getAssignedSelected
parameter_list|()
block|{
return|return
name|assignedSelected
return|;
block|}
specifier|public
name|void
name|setAssignedSelected
parameter_list|(
name|String
index|[]
name|assignedSelected
parameter_list|)
block|{
name|this
operator|.
name|assignedSelected
operator|=
name|assignedSelected
expr_stmt|;
block|}
specifier|public
name|String
index|[]
name|getNotAssignedSelected
parameter_list|()
block|{
return|return
name|notAssignedSelected
return|;
block|}
specifier|public
name|void
name|setNotAssignedSelected
parameter_list|(
name|String
index|[]
name|notAssignedSelected
parameter_list|)
block|{
name|this
operator|.
name|notAssignedSelected
operator|=
name|notAssignedSelected
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
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|==
literal|null
operator|||
name|name
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"roomFeature"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|abbv
operator|==
literal|null
operator|||
name|abbv
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"roomFeature"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Abbreviation"
argument_list|)
argument_list|)
expr_stmt|;
block|}
try|try
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|RoomFeature
operator|.
name|getAllGlobalRoomFeatures
argument_list|(
name|getSessionId
argument_list|()
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomFeature
name|rf
init|=
operator|(
name|RoomFeature
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rf
operator|.
name|getLabel
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
operator|&&
operator|!
name|rf
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Department
name|dept
init|=
operator|(
name|deptCode
operator|==
literal|null
condition|?
literal|null
else|:
name|Department
operator|.
name|findByDeptCode
argument_list|(
name|deptCode
argument_list|,
name|getSessionId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|RoomFeature
operator|.
name|getAllDepartmentRoomFeatures
argument_list|(
name|dept
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomFeature
name|rf
init|=
operator|(
name|RoomFeature
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rf
operator|.
name|getLabel
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
operator|&&
operator|!
name|rf
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.exists"
argument_list|,
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"name"
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
if|if
condition|(
operator|!
name|global
operator|&&
operator|(
name|deptCode
operator|==
literal|null
operator|||
name|deptCode
operator|.
name|equalsIgnoreCase
argument_list|(
literal|""
argument_list|)
operator|)
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"Department"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Department"
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
specifier|public
name|String
name|getDeptCode
parameter_list|()
block|{
return|return
name|deptCode
return|;
block|}
specifier|public
name|void
name|setDeptCode
parameter_list|(
name|String
name|deptCode
parameter_list|)
block|{
name|this
operator|.
name|deptCode
operator|=
name|deptCode
expr_stmt|;
block|}
specifier|public
name|String
name|getDeptName
parameter_list|()
block|{
return|return
name|deptName
return|;
block|}
specifier|public
name|void
name|setDeptName
parameter_list|(
name|String
name|deptName
parameter_list|)
block|{
name|this
operator|.
name|deptName
operator|=
name|deptName
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
name|getAbbv
parameter_list|()
block|{
return|return
name|abbv
return|;
block|}
specifier|public
name|void
name|setAbbv
parameter_list|(
name|String
name|abbv
parameter_list|)
block|{
name|this
operator|.
name|abbv
operator|=
name|abbv
expr_stmt|;
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
name|name
operator|=
literal|""
expr_stmt|;
name|abbv
operator|=
literal|""
expr_stmt|;
name|iSessionId
operator|=
literal|null
expr_stmt|;
name|featureTypeId
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|iSessionId
return|;
block|}
specifier|public
name|void
name|setSessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|iSessionId
operator|=
name|sessionId
expr_stmt|;
block|}
specifier|public
name|Long
name|getFeatureTypeId
parameter_list|()
block|{
return|return
name|featureTypeId
return|;
block|}
specifier|public
name|void
name|setFeatureTypeId
parameter_list|(
name|Long
name|featureTypeId
parameter_list|)
block|{
name|this
operator|.
name|featureTypeId
operator|=
name|featureTypeId
expr_stmt|;
block|}
specifier|public
name|String
name|getFeatures
parameter_list|(
name|String
name|locationId
parameter_list|)
block|{
name|Location
name|location
init|=
name|LocationDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|locationId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|location
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|String
name|features
init|=
literal|""
decl_stmt|;
for|for
control|(
name|GlobalRoomFeature
name|feature
range|:
name|location
operator|.
name|getGlobalRoomFeatures
argument_list|()
control|)
block|{
if|if
condition|(
name|feature
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|features
operator|.
name|isEmpty
argument_list|()
condition|)
name|features
operator|+=
literal|", "
expr_stmt|;
name|features
operator|+=
literal|"<span title='"
operator|+
name|feature
operator|.
name|getLabel
argument_list|()
operator|+
literal|"' style='white-space:nowrap;'>"
operator|+
name|feature
operator|.
name|getLabelWithType
argument_list|()
operator|+
literal|"</span>"
expr_stmt|;
block|}
for|for
control|(
name|DepartmentRoomFeature
name|feature
range|:
name|location
operator|.
name|getDepartmentRoomFeatures
argument_list|()
control|)
block|{
if|if
condition|(
name|feature
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|id
argument_list|)
condition|)
continue|continue;
if|if
condition|(
operator|!
name|features
operator|.
name|isEmpty
argument_list|()
condition|)
name|features
operator|+=
literal|", "
expr_stmt|;
name|features
operator|+=
literal|"<span title='"
operator|+
name|feature
operator|.
name|getLabel
argument_list|()
operator|+
literal|"' style='white-space:nowrap;'>"
operator|+
name|feature
operator|.
name|getLabelWithType
argument_list|()
operator|+
literal|"</span>"
expr_stmt|;
block|}
return|return
name|features
return|;
block|}
block|}
end_class

end_unit

