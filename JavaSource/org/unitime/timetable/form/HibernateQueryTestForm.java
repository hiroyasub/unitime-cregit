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
comment|/**   * MyEclipse Struts  * Creation date: 12-16-2005  *   * XDoclet definition:  * @struts:form name="hibernateQueryTestForm"  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|HibernateQueryTestForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5970479864977610427L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|/** query property */
specifier|private
name|String
name|query
decl_stmt|;
comment|/** listSize property */
specifier|private
name|String
name|listSize
decl_stmt|;
specifier|private
name|int
name|start
init|=
literal|0
decl_stmt|;
specifier|private
name|boolean
name|next
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|export
init|=
literal|false
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
name|query
operator|==
literal|null
operator|||
name|query
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
literal|"query"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Invalid value for query"
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
name|query
operator|=
literal|""
expr_stmt|;
name|listSize
operator|=
literal|""
expr_stmt|;
name|start
operator|=
literal|0
expr_stmt|;
name|next
operator|=
literal|false
expr_stmt|;
name|export
operator|=
literal|false
expr_stmt|;
block|}
comment|/**       * Returns the query.      * @return String      */
specifier|public
name|String
name|getQuery
parameter_list|()
block|{
return|return
name|query
return|;
block|}
comment|/**       * Set the query.      * @param query The query to set      */
specifier|public
name|void
name|setQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
block|}
comment|/**       * Returns the listSize.      * @return String      */
specifier|public
name|String
name|getListSize
parameter_list|()
block|{
return|return
name|listSize
return|;
block|}
comment|/**       * Set the listSize.      * @param listSize The listSize to set      */
specifier|public
name|void
name|setListSize
parameter_list|(
name|String
name|listSize
parameter_list|)
block|{
name|this
operator|.
name|listSize
operator|=
name|listSize
expr_stmt|;
block|}
specifier|public
name|int
name|getStart
parameter_list|()
block|{
return|return
name|start
return|;
block|}
specifier|public
name|void
name|setStart
parameter_list|(
name|int
name|start
parameter_list|)
block|{
name|this
operator|.
name|start
operator|=
name|start
expr_stmt|;
block|}
specifier|public
name|boolean
name|isNext
parameter_list|()
block|{
return|return
name|next
return|;
block|}
specifier|public
name|void
name|setNext
parameter_list|(
name|boolean
name|next
parameter_list|)
block|{
name|this
operator|.
name|next
operator|=
name|next
expr_stmt|;
block|}
specifier|public
name|boolean
name|isExport
parameter_list|()
block|{
return|return
name|export
return|;
block|}
specifier|public
name|void
name|setExport
parameter_list|(
name|boolean
name|export
parameter_list|)
block|{
name|this
operator|.
name|export
operator|=
name|export
expr_stmt|;
block|}
block|}
end_class

end_unit

