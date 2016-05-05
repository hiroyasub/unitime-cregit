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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|BaseSolverPredefinedSetting
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
name|SolverPredefinedSettingDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverPredefinedSetting
extends|extends
name|BaseSolverPredefinedSetting
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
name|String
index|[]
name|sAppearances
init|=
operator|new
name|String
index|[]
block|{
literal|"Timetables"
block|,
literal|"Solver"
block|,
literal|"Examination Solver"
block|,
literal|"Student Sectioning Solver"
block|,
literal|"Instructor Scheduling Solver"
block|}
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|APPEARANCE_TIMETABLES
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|APPEARANCE_SOLVER
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|APPEARANCE_EXAM_SOLVER
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|APPEARANCE_STUDENT_SOLVER
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|APPEARANCE_INSTRUCTOR_SOLVER
init|=
literal|4
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|SolverPredefinedSetting
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|SolverPredefinedSetting
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
name|SolverPredefinedSetting
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
name|SolverPredefinedSettingDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|SolverPredefinedSetting
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
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|SolverPredefinedSetting
operator|)
name|list
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
index|[]
name|getNames
parameter_list|(
name|Integer
name|appearance
parameter_list|)
block|{
name|List
name|list
init|=
operator|(
operator|new
name|SolverPredefinedSettingDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|SolverPredefinedSetting
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
literal|"appearance"
argument_list|,
name|appearance
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"name"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
operator|new
name|String
index|[]
block|{}
return|;
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|list
operator|.
name|size
argument_list|()
index|]
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
name|list
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|SolverPredefinedSetting
name|set
init|=
operator|(
name|SolverPredefinedSetting
operator|)
name|list
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|names
index|[
name|i
index|]
operator|=
name|set
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
return|return
name|names
return|;
block|}
specifier|public
specifier|static
name|Vector
name|getIdValueList
parameter_list|(
name|Integer
name|appearance
parameter_list|)
block|{
name|List
name|list
init|=
operator|(
operator|new
name|SolverPredefinedSettingDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|SolverPredefinedSetting
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
literal|"appearance"
argument_list|,
name|appearance
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"name"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
name|Vector
name|idValueList
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|list
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
name|SolverPredefinedSetting
name|set
init|=
operator|(
name|SolverPredefinedSetting
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|idValueList
operator|.
name|add
argument_list|(
operator|new
name|IdValue
argument_list|(
name|set
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|set
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|idValueList
return|;
block|}
specifier|public
specifier|static
class|class
name|IdValue
block|{
specifier|private
name|Long
name|iId
decl_stmt|;
specifier|private
name|String
name|iValue
decl_stmt|;
specifier|private
name|String
name|iType
decl_stmt|;
specifier|private
name|boolean
name|iEnabled
decl_stmt|;
specifier|public
name|IdValue
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
name|value
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|IdValue
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|value
parameter_list|,
name|String
name|type
parameter_list|)
block|{
name|this
argument_list|(
name|id
argument_list|,
name|value
argument_list|,
name|type
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|IdValue
parameter_list|(
name|Long
name|id
parameter_list|,
name|String
name|value
parameter_list|,
name|String
name|type
parameter_list|,
name|boolean
name|enabled
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iValue
operator|=
name|value
expr_stmt|;
name|iType
operator|=
name|type
expr_stmt|;
name|iEnabled
operator|=
name|enabled
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|boolean
name|getEnabled
parameter_list|()
block|{
return|return
name|iEnabled
return|;
block|}
specifier|public
name|boolean
name|getDisabled
parameter_list|()
block|{
return|return
operator|!
name|iEnabled
return|;
block|}
block|}
block|}
end_class

end_unit

