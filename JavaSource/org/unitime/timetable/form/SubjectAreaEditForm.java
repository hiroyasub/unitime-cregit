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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|web
operator|.
name|Web
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
name|SubjectArea
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
name|Constants
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-15-2007  *   * XDoclet definition:  * @struts.form name="subjectAreaEditForm"  */
end_comment

begin_class
specifier|public
class|class
name|SubjectAreaEditForm
extends|extends
name|ActionForm
block|{
comment|/* 	 * Generated fields 	 */
comment|/** op property */
specifier|private
name|Long
name|uniqueId
decl_stmt|;
specifier|private
name|String
name|op
decl_stmt|;
specifier|private
name|String
name|abbv
decl_stmt|;
specifier|private
name|String
name|shortTitle
decl_stmt|;
specifier|private
name|String
name|longTitle
decl_stmt|;
specifier|private
name|String
name|externalId
decl_stmt|;
specifier|private
name|Long
name|department
decl_stmt|;
specifier|private
name|Boolean
name|scheduleBkOnly
decl_stmt|;
specifier|private
name|Boolean
name|pseudo
decl_stmt|;
comment|/* 	 * Generated Methods 	 */
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
name|abbv
operator|==
literal|null
operator|||
name|abbv
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"abbv"
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
if|if
condition|(
name|shortTitle
operator|==
literal|null
operator|||
name|shortTitle
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"shortTitle"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Short Title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|longTitle
operator|==
literal|null
operator|||
name|longTitle
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"longTitle"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"Long Title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|department
operator|==
literal|null
operator|||
name|department
operator|.
name|longValue
argument_list|()
operator|<=
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"department"
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
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|Long
name|sessionId
init|=
operator|(
name|Long
operator|)
operator|(
operator|(
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
operator|)
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SESSION_ID_ATTR_NAME
argument_list|)
operator|)
decl_stmt|;
name|SubjectArea
name|sa
init|=
name|SubjectArea
operator|.
name|findByAbbv
argument_list|(
name|sessionId
argument_list|,
name|abbv
argument_list|)
decl_stmt|;
if|if
condition|(
name|uniqueId
operator|==
literal|null
operator|&&
name|sa
operator|!=
literal|null
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"abbv"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"A subject area with the abbreviation exists for the academic session"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|uniqueId
operator|!=
literal|null
operator|&&
name|sa
operator|!=
literal|null
operator|&&
operator|!
name|sa
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|uniqueId
argument_list|)
condition|)
name|errors
operator|.
name|add
argument_list|(
literal|"abbv"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"A subject area with the abbreviation exists for the academic session"
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|uniqueId
operator|=
literal|null
expr_stmt|;
name|op
operator|=
literal|null
expr_stmt|;
name|abbv
operator|=
literal|null
expr_stmt|;
name|shortTitle
operator|=
literal|null
expr_stmt|;
name|longTitle
operator|=
literal|null
expr_stmt|;
name|externalId
operator|=
literal|null
expr_stmt|;
name|department
operator|=
literal|null
expr_stmt|;
name|scheduleBkOnly
operator|=
literal|null
expr_stmt|;
name|pseudo
operator|=
literal|null
expr_stmt|;
block|}
comment|/**  	 * Returns the op. 	 * @return String 	 */
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
return|;
block|}
comment|/**  	 * Set the op. 	 * @param op The op to set 	 */
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
if|if
condition|(
name|abbv
operator|!=
literal|null
condition|)
name|this
operator|.
name|abbv
operator|=
name|abbv
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
else|else
name|this
operator|.
name|abbv
operator|=
name|abbv
expr_stmt|;
block|}
specifier|public
name|Long
name|getDepartment
parameter_list|()
block|{
return|return
name|department
return|;
block|}
specifier|public
name|void
name|setDepartment
parameter_list|(
name|Long
name|department
parameter_list|)
block|{
name|this
operator|.
name|department
operator|=
name|department
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
name|String
name|getLongTitle
parameter_list|()
block|{
return|return
name|longTitle
return|;
block|}
specifier|public
name|void
name|setLongTitle
parameter_list|(
name|String
name|longTitle
parameter_list|)
block|{
name|this
operator|.
name|longTitle
operator|=
name|longTitle
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getPseudo
parameter_list|()
block|{
return|return
name|pseudo
return|;
block|}
specifier|public
name|void
name|setPseudo
parameter_list|(
name|Boolean
name|pseudo
parameter_list|)
block|{
if|if
condition|(
name|pseudo
operator|==
literal|null
condition|)
name|this
operator|.
name|pseudo
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
else|else
name|this
operator|.
name|pseudo
operator|=
name|pseudo
expr_stmt|;
block|}
specifier|public
name|Boolean
name|getScheduleBkOnly
parameter_list|()
block|{
return|return
name|scheduleBkOnly
return|;
block|}
specifier|public
name|void
name|setScheduleBkOnly
parameter_list|(
name|Boolean
name|scheduleBkOnly
parameter_list|)
block|{
if|if
condition|(
name|scheduleBkOnly
operator|==
literal|null
condition|)
name|this
operator|.
name|scheduleBkOnly
operator|=
name|Boolean
operator|.
name|FALSE
expr_stmt|;
else|else
name|this
operator|.
name|scheduleBkOnly
operator|=
name|scheduleBkOnly
expr_stmt|;
block|}
specifier|public
name|String
name|getShortTitle
parameter_list|()
block|{
return|return
name|shortTitle
return|;
block|}
specifier|public
name|void
name|setShortTitle
parameter_list|(
name|String
name|shortTitle
parameter_list|)
block|{
name|this
operator|.
name|shortTitle
operator|=
name|shortTitle
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|uniqueId
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
if|if
condition|(
name|uniqueId
operator|!=
literal|null
operator|&&
name|uniqueId
operator|.
name|longValue
argument_list|()
operator|<=
literal|0
condition|)
name|this
operator|.
name|uniqueId
operator|=
literal|null
expr_stmt|;
else|else
name|this
operator|.
name|uniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
block|}
end_class

end_unit

