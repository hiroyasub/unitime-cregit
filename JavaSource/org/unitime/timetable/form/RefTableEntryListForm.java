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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:form name="refTableEntryListForm"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|RefTableEntryListForm
extends|extends
name|ActionForm
block|{
comment|/** 	 *  	 */
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|Collection
name|refTableEntries
decl_stmt|;
comment|// --------------------------------------------------------- Methods
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
name|refTableEntries
operator|=
operator|new
name|ArrayList
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * @return Returns the sessions. 	 */
specifier|public
name|Collection
name|getRefTableEntries
parameter_list|()
block|{
return|return
name|refTableEntries
return|;
block|}
comment|/** 	 * @param sessions The sessions to set. 	 */
specifier|public
name|void
name|setRefTableEntries
parameter_list|(
name|Collection
name|refTableEntries
parameter_list|)
block|{
name|this
operator|.
name|refTableEntries
operator|=
name|refTableEntries
expr_stmt|;
block|}
block|}
end_class

end_unit

