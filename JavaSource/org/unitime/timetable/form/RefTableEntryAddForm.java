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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|RefTableEntryAddForm
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
literal|6654932547373433298L
decl_stmt|;
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

