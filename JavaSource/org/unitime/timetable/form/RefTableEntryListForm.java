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
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:form name="refTableEntryListForm"  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|RefTableEntryListForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5632195965988404841L
decl_stmt|;
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

