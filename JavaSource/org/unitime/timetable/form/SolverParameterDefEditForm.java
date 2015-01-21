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
name|SolverParameterDef
import|;
end_import

begin_comment
comment|/**   * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverParameterDefEditForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7546894309851294183L
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|/** solverParameterDef property */
specifier|private
name|SolverParameterDef
name|solverParameterDef
init|=
operator|new
name|SolverParameterDef
argument_list|()
decl_stmt|;
empty_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Returns the solverParameterDef. 	 * @return SolverParameterDef 	 */
specifier|public
name|SolverParameterDef
name|getSolverParameterDef
parameter_list|()
block|{
return|return
name|solverParameterDef
return|;
block|}
comment|/**  	 * Set the solverParameterDef. 	 * @param solverParameterDef The solverParameterDef to set 	 */
specifier|public
name|void
name|setSolverParameterDef
parameter_list|(
name|SolverParameterDef
name|solverParameterDef
parameter_list|)
block|{
name|this
operator|.
name|solverParameterDef
operator|=
name|solverParameterDef
expr_stmt|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|solverParameterDef
operator|.
name|getUniqueId
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @param uniqueId 	 */
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|solverParameterDef
operator|.
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|solverParameterDef
operator|.
name|getName
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @param key 	 */
specifier|public
name|void
name|setKey
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|solverParameterDef
operator|.
name|setName
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|getDesc
parameter_list|()
block|{
return|return
name|solverParameterDef
operator|.
name|getDescription
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @param desc 	 */
specifier|public
name|void
name|setDesc
parameter_list|(
name|String
name|desc
parameter_list|)
block|{
name|solverParameterDef
operator|.
name|setDescription
argument_list|(
name|desc
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 * @return 	 */
specifier|public
name|String
name|getDefaultValue
parameter_list|()
block|{
return|return
name|solverParameterDef
operator|.
name|getDefault
argument_list|()
return|;
block|}
comment|/** 	 *  	 * @param DefaultValue 	 */
specifier|public
name|void
name|setDefaultValue
parameter_list|(
name|String
name|DefaultValue
parameter_list|)
block|{
name|solverParameterDef
operator|.
name|setDefault
argument_list|(
name|DefaultValue
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

