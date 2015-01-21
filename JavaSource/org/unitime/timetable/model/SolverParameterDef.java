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
name|ArrayList
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
name|HibernateException
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
name|commons
operator|.
name|Debug
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
name|BaseSolverParameterDef
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
name|SolverParameterDefDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverParameterDef
extends|extends
name|BaseSolverParameterDef
implements|implements
name|Comparable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|SolverParameterDef
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|SolverParameterDef
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
comment|/* 	 * @return all SolverParameterDefs 	 */
specifier|public
specifier|static
name|ArrayList
name|getAll
parameter_list|()
throws|throws
name|HibernateException
block|{
return|return
operator|(
name|ArrayList
operator|)
operator|(
operator|new
name|SolverParameterDefDAO
argument_list|()
operator|.
name|findAll
argument_list|()
operator|)
return|;
block|}
comment|/** 	 * @param id 	 * @return 	 * @throws HibernateException 	 */
specifier|public
specifier|static
name|SolverParameterDef
name|getSolverParameterDefById
parameter_list|(
name|Long
name|id
parameter_list|)
throws|throws
name|HibernateException
block|{
return|return
operator|(
operator|new
name|SolverParameterDefDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|id
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param id 	 * @throws HibernateException 	 */
specifier|public
specifier|static
name|void
name|deleteSolverParameterDefById
parameter_list|(
name|Long
name|id
parameter_list|)
throws|throws
name|HibernateException
block|{
name|SolverParameterDef
name|solverParameterDef
init|=
name|SolverParameterDef
operator|.
name|getSolverParameterDefById
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|solverParameterDef
operator|!=
literal|null
condition|)
block|{
operator|(
operator|new
name|SolverParameterDefDAO
argument_list|()
operator|)
operator|.
name|delete
argument_list|(
name|solverParameterDef
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 *  	 * @throws HibernateException 	 */
specifier|public
name|void
name|saveOrUpdate
parameter_list|()
throws|throws
name|HibernateException
block|{
operator|(
operator|new
name|SolverParameterDefDAO
argument_list|()
operator|)
operator|.
name|saveOrUpdate
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Deprecated
specifier|public
specifier|static
name|SolverParameterDef
name|findByNameGroup
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|SolverParameterDef
name|def
init|=
literal|null
decl_stmt|;
try|try
block|{
name|List
name|list
init|=
name|SolverParameterDefDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|SolverParameterDef
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
operator|!
name|list
operator|.
name|isEmpty
argument_list|()
condition|)
name|def
operator|=
operator|(
name|SolverParameterDef
operator|)
name|list
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|def
return|;
block|}
specifier|public
specifier|static
name|SolverParameterDef
name|findByNameGroup
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|group
parameter_list|)
block|{
return|return
name|findByNameGroup
argument_list|(
name|SolverParameterDefDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|name
argument_list|,
name|group
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SolverParameterDef
name|findByNameGroup
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|group
parameter_list|)
block|{
name|List
argument_list|<
name|SolverParameterDef
argument_list|>
name|list
init|=
operator|(
name|List
argument_list|<
name|SolverParameterDef
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SolverParameterDef where name = :name and group.name = :group"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
operator|.
name|setString
argument_list|(
literal|"group"
argument_list|,
name|group
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
return|return
name|list
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
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
name|SolverParameterDef
name|findByNameType
parameter_list|(
name|String
name|name
parameter_list|,
name|int
name|type
parameter_list|)
block|{
return|return
name|findByNameType
argument_list|(
name|SolverParameterDefDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|name
argument_list|,
name|type
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SolverParameterDef
name|findByNameType
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|String
name|name
parameter_list|,
name|int
name|type
parameter_list|)
block|{
name|List
argument_list|<
name|SolverParameterDef
argument_list|>
name|list
init|=
operator|(
name|List
argument_list|<
name|SolverParameterDef
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SolverParameterDef where name = :name and group.type = :type"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|name
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|type
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
return|return
name|list
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|list
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
comment|/** 	 * Get the default value for a given key 	 * @param key Setting key 	 * @return Default value if found, null otherwise 	 */
specifier|public
specifier|static
name|List
name|findByGroup
parameter_list|(
name|SolverParameterGroup
name|group
parameter_list|)
block|{
return|return
operator|(
operator|new
name|SolverParameterDefDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|SolverParameterDef
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
literal|"group"
argument_list|,
name|group
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"order"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|SolverParameterDef
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|SolverParameterDef
name|p
init|=
operator|(
name|SolverParameterDef
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getOrder
argument_list|()
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getOrder
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDefault
parameter_list|()
block|{
name|String
name|ret
init|=
name|super
operator|.
name|getDefault
argument_list|()
decl_stmt|;
return|return
name|ret
operator|==
literal|null
condition|?
literal|""
else|:
name|ret
return|;
block|}
block|}
end_class

end_unit

