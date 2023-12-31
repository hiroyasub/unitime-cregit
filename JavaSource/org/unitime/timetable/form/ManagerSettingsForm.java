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
name|StringTokenizer
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 10-17-2005  *   * XDoclet definition:  * @struts:form name="managerSettingsForm"  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ManagerSettingsForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5955499033542263250L
decl_stmt|;
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
specifier|private
name|String
name|name
decl_stmt|;
comment|/** defaultValue property */
specifier|private
name|String
name|value
decl_stmt|;
specifier|private
name|String
name|defaultValue
decl_stmt|;
comment|/** allowedValues property */
specifier|private
name|String
index|[]
name|allowedValues
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
name|value
operator|==
literal|null
operator|||
name|value
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
literal|"value"
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
name|name
operator|=
literal|""
expr_stmt|;
name|value
operator|=
literal|""
expr_stmt|;
name|defaultValue
operator|=
literal|""
expr_stmt|;
name|allowedValues
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
comment|/**       * Set the value.      * @param defaultValue The value to set      */
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
comment|/**      * @return Returns the op.      */
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
return|;
block|}
comment|/**      * @param op The op to set.      */
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
comment|/**      * @return Returns the allowedValues.      */
specifier|public
name|String
index|[]
name|getAllowedValues
parameter_list|()
block|{
return|return
name|allowedValues
return|;
block|}
comment|/**      * @param allowedValues The allowedValues to set.      */
specifier|public
name|void
name|setAllowedValues
parameter_list|(
name|String
index|[]
name|allowedValues
parameter_list|)
block|{
name|this
operator|.
name|allowedValues
operator|=
name|allowedValues
expr_stmt|;
block|}
comment|/**      * @param allowedValues The allowedValues to set.      */
specifier|public
name|void
name|setAllowedValues
parameter_list|(
name|String
name|allowedValues
parameter_list|)
block|{
name|StringTokenizer
name|strTok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|allowedValues
argument_list|,
literal|","
argument_list|)
decl_stmt|;
name|this
operator|.
name|allowedValues
operator|=
operator|new
name|String
index|[
name|strTok
operator|.
name|countTokens
argument_list|()
index|]
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|strTok
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|this
operator|.
name|allowedValues
index|[
name|i
operator|++
index|]
operator|=
name|strTok
operator|.
name|nextElement
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
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
name|getDefaultValue
parameter_list|()
block|{
return|return
name|defaultValue
return|;
block|}
specifier|public
name|void
name|setDefaultValue
parameter_list|(
name|String
name|defaultValue
parameter_list|)
block|{
name|this
operator|.
name|defaultValue
operator|=
name|defaultValue
expr_stmt|;
block|}
block|}
end_class

end_unit

