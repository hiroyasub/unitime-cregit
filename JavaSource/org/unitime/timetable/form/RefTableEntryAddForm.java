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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:form name="refTableEntryForm"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|RefTableEntryAddForm
extends|extends
name|ActionForm
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|/** reference property */
specifier|private
name|String
name|reference
decl_stmt|;
comment|/** label property */
specifier|private
name|String
name|label
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Returns the reference. 	 * @return String 	 */
specifier|public
name|String
name|getReference
parameter_list|()
block|{
return|return
name|reference
return|;
block|}
comment|/**  	 * Set the reference. 	 * @param reference The reference to set 	 */
specifier|public
name|void
name|setReference
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|this
operator|.
name|reference
operator|=
name|reference
expr_stmt|;
block|}
comment|/**  	 * Returns the label. 	 * @return String 	 */
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
comment|/**  	 * Set the label. 	 * @param label The label to set 	 */
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
block|}
end_class

end_unit

