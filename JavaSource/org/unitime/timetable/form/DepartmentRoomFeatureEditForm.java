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
name|Set
import|;
end_import

begin_comment
comment|/**  *  * XDoclet definition:  * @struts:form name="roomFeatureEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|DepartmentRoomFeatureEditForm
extends|extends
name|RoomFeatureEditForm
block|{
name|String
name|deptCode
init|=
literal|null
decl_stmt|;
name|String
name|label
init|=
literal|null
decl_stmt|;
name|Integer
name|uniqueId
init|=
literal|null
decl_stmt|;
name|Collection
name|rooms
init|=
literal|null
decl_stmt|;
comment|/** 	 * @return 	 */
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|String
name|getDeptCode
parameter_list|()
block|{
return|return
name|deptCode
return|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|Collection
name|getRooms
parameter_list|()
block|{
return|return
name|rooms
return|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|Integer
name|getUniqueId
parameter_list|()
block|{
return|return
name|uniqueId
return|;
block|}
comment|/** 	 * @param label 	 */
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|this
operator|.
name|label
operator|=
name|label
expr_stmt|;
block|}
comment|/** 	 * @param owner 	 */
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
comment|/** 	 * @param rooms 	 */
specifier|public
name|void
name|setRooms
parameter_list|(
name|Set
name|rooms
parameter_list|)
block|{
name|this
operator|.
name|rooms
operator|=
name|rooms
expr_stmt|;
block|}
comment|/** 	 * @param uniqueId 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Integer
name|uniqueId
parameter_list|)
block|{
name|this
operator|.
name|uniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
comment|/** 	 * Comment for<code>serialVersionUID</code> 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3689069555917796146L
decl_stmt|;
block|}
end_class

end_unit

