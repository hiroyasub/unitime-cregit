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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|RefTableEntry
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts * Creation date: 02-18-2005 *  * XDoclet definition: * @struts:form name="refTableEntryEditForm" */
end_comment

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|RefTableEntryEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3604015148650913385L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
name|RefTableEntry
name|refTableEntry
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/** 	 * @return Returns the refTableEntry. 	 */
specifier|public
name|RefTableEntry
name|getRefTableEntry
parameter_list|()
block|{
return|return
name|refTableEntry
return|;
block|}
comment|/** 	 * @param refTableEntry The refTableEntry to set. 	 */
specifier|public
name|void
name|setRefTableEntry
parameter_list|(
name|RefTableEntry
name|refTableEntry
parameter_list|)
block|{
name|this
operator|.
name|refTableEntry
operator|=
name|refTableEntry
expr_stmt|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|arg0
parameter_list|)
block|{
return|return
name|refTableEntry
operator|.
name|equals
argument_list|(
name|arg0
argument_list|)
return|;
block|}
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|refTableEntry
operator|.
name|getReference
argument_list|()
return|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|refTableEntry
operator|.
name|getLabel
argument_list|()
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|refTableEntry
operator|.
name|hashCode
argument_list|()
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
name|refTableEntry
operator|.
name|setReference
argument_list|(
name|reference
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|refTableEntry
operator|.
name|setLabel
argument_list|(
name|label
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|refTableEntry
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** 	 * @return 	 */
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|refTableEntry
operator|.
name|getUniqueId
argument_list|()
return|;
block|}
comment|/** 	 * @param refTableEntryId 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|refTableEntry
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

