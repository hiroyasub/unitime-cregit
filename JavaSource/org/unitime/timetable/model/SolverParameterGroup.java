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
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Order
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|base
operator|.
name|BaseSolverParameterGroup
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
name|SolverParameterGroupDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverParameterGroup
extends|extends
name|BaseSolverParameterGroup
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeCourse
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeExam
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sTypeStudent
init|=
literal|2
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|SolverParameterGroup
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|SolverParameterGroup
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** 	 * Get the default value for a given key 	 * @param key Setting key 	 * @return Default value if found, null otherwise 	 */
specifier|public
specifier|static
name|SolverParameterGroup
name|findByName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|List
name|list
init|=
operator|(
operator|new
name|SolverParameterGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|SolverParameterGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
operator|(
name|SolverParameterGroup
operator|)
name|list
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
return|return
literal|null
return|;
block|}
comment|/** 	 * Get the default value for a given key 	 * @param key Setting key 	 * @return Default value if found, null otherwise 	 */
specifier|public
specifier|static
name|String
index|[]
name|getGroupNames
parameter_list|()
block|{
name|List
name|groups
init|=
operator|(
operator|new
name|SolverParameterGroupDAO
argument_list|()
operator|)
operator|.
name|findAll
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"order"
argument_list|)
argument_list|)
decl_stmt|;
name|String
index|[]
name|ret
init|=
operator|new
name|String
index|[
name|groups
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|groups
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SolverParameterGroup
name|group
init|=
operator|(
name|SolverParameterGroup
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
index|[
name|idx
operator|++
index|]
operator|=
name|group
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

