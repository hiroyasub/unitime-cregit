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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 08-28-2006  *   * XDoclet definition:  * @struts:form name="applicationConfigForm"  */
end_comment

begin_class
specifier|public
class|class
name|ApplicationConfigForm
extends|extends
name|ActionForm
block|{
comment|// --------------------------------------------------------- Instance Variables
specifier|private
name|String
name|op
decl_stmt|;
comment|/** key property */
specifier|private
name|String
name|key
decl_stmt|;
comment|/** value property */
specifier|private
name|String
name|value
decl_stmt|;
comment|/** description property */
specifier|private
name|String
name|description
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**       * Method validate      * @param mapping      * @param request      * @return ActionErrors      */
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
name|key
operator|==
literal|null
operator|||
name|key
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
literal|"key"
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
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|value
operator|=
literal|""
expr_stmt|;
return|return
name|errors
return|;
block|}
comment|/**       * Method reset      * @param mapping      * @param request      */
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
name|op
operator|=
literal|null
expr_stmt|;
name|key
operator|=
literal|""
expr_stmt|;
name|value
operator|=
literal|""
expr_stmt|;
name|description
operator|=
literal|""
expr_stmt|;
block|}
comment|/**       * Returns the key.      * @return String      */
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|key
return|;
block|}
comment|/**       * Set the key.      * @param key The key to set      */
specifier|public
name|void
name|setKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
name|key
expr_stmt|;
block|}
comment|/**       * Returns the value.      * @return String      */
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**       * Set the value.      * @param value The value to set      */
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
comment|/**       * Returns the description.      * @return String      */
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
comment|/**       * Set the description.      * @param description The description to set      */
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
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
name|this
operator|.
name|op
operator|=
name|op
expr_stmt|;
block|}
block|}
end_class

end_unit

