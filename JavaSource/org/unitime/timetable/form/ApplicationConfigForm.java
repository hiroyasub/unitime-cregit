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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|timetable
operator|.
name|defaults
operator|.
name|ApplicationProperty
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
name|Session
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
name|SessionDAO
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 08-28-2006  *   * XDoclet definition:  * @struts:form name="applicationConfigForm"  *  * @author Tomas Muller  */
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
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|4677371360700536609L
decl_stmt|;
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
specifier|private
name|boolean
name|allSessions
decl_stmt|;
specifier|private
name|Long
index|[]
name|sessions
init|=
literal|null
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
literal|"list"
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
name|allSessions
operator|=
literal|false
expr_stmt|;
name|sessions
operator|=
literal|null
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
specifier|public
name|boolean
name|isAllSessions
parameter_list|()
block|{
return|return
name|allSessions
return|;
block|}
specifier|public
name|void
name|setAllSessions
parameter_list|(
name|boolean
name|allSessions
parameter_list|)
block|{
name|this
operator|.
name|allSessions
operator|=
name|allSessions
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Session
argument_list|>
name|getListSessions
parameter_list|()
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|Session
argument_list|>
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Long
index|[]
name|getSessions
parameter_list|()
block|{
return|return
name|sessions
return|;
block|}
specifier|public
name|void
name|setSessions
parameter_list|(
name|Long
index|[]
name|sessions
parameter_list|)
block|{
name|this
operator|.
name|sessions
operator|=
name|sessions
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
name|ApplicationProperty
name|p
init|=
name|ApplicationProperty
operator|.
name|fromKey
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Class
name|type
init|=
name|p
operator|.
name|type
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
operator|||
name|type
operator|.
name|equals
argument_list|(
name|String
operator|.
name|class
argument_list|)
condition|)
return|return
literal|null
return|;
if|if
condition|(
name|type
operator|.
name|equals
argument_list|(
name|Class
operator|.
name|class
argument_list|)
operator|&&
name|p
operator|.
name|implementation
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|p
operator|.
name|implementation
argument_list|()
operator|.
name|isInterface
argument_list|()
condition|)
return|return
literal|"class implementing "
operator|+
name|p
operator|.
name|implementation
argument_list|()
operator|.
name|getSimpleName
argument_list|()
return|;
else|else
return|return
literal|"class extending "
operator|+
name|p
operator|.
name|implementation
argument_list|()
operator|.
name|getSimpleName
argument_list|()
return|;
block|}
return|return
name|type
operator|.
name|getSimpleName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
specifier|public
name|String
name|getValues
parameter_list|()
block|{
name|ApplicationProperty
name|p
init|=
name|ApplicationProperty
operator|.
name|fromKey
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|p
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|String
index|[]
name|vals
init|=
name|p
operator|.
name|availableValues
argument_list|()
decl_stmt|;
if|if
condition|(
name|vals
operator|!=
literal|null
operator|&&
name|vals
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|vals
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
name|ret
operator|+=
literal|", "
expr_stmt|;
name|ret
operator|+=
name|vals
index|[
name|i
index|]
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|getDefault
parameter_list|()
block|{
name|ApplicationProperty
name|p
init|=
name|ApplicationProperty
operator|.
name|fromKey
argument_list|(
name|key
argument_list|)
decl_stmt|;
return|return
operator|(
name|p
operator|!=
literal|null
condition|?
name|p
operator|.
name|defaultValue
argument_list|()
else|:
literal|null
operator|)
return|;
block|}
block|}
end_class

end_unit

