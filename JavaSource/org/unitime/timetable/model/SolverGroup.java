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
name|io
operator|.
name|Serializable
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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseSolverGroup
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
name|SolverGroupDAO
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
name|security
operator|.
name|Qualifiable
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
name|security
operator|.
name|UserContext
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
name|security
operator|.
name|UserQualifier
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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SolverGroup
extends|extends
name|BaseSolverGroup
implements|implements
name|Comparable
implements|,
name|Qualifiable
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
name|SolverGroup
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|SolverGroup
parameter_list|(
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
specifier|public
name|boolean
name|isExternalManager
parameter_list|()
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|d
operator|.
name|isExternalManager
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|Collection
name|getClasses
parameter_list|()
block|{
name|Vector
name|classes
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
name|getDepartments
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|classes
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getClasses
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|classes
return|;
block|}
specifier|public
name|Collection
name|getNotAssignedClasses
parameter_list|(
name|Solution
name|solution
parameter_list|)
block|{
name|Vector
name|classes
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
name|getDepartments
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|classes
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getNotAssignedClasses
argument_list|(
name|solution
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|classes
return|;
block|}
specifier|public
name|Set
name|getDistributionPreferences
parameter_list|()
block|{
name|TreeSet
name|prefs
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|prefs
operator|.
name|addAll
argument_list|(
name|d
operator|.
name|getDistributionPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|prefs
return|;
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|SolverGroup
argument_list|>
name|findBySessionId
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|SolverGroup
argument_list|>
argument_list|(
operator|(
operator|new
name|SolverGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select sg from SolverGroup sg where sg.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SolverGroup
name|findBySessionIdName
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|List
name|groups
init|=
operator|(
operator|new
name|SolverGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select sg from SolverGroup sg where sg.session.uniqueId=:sessionId and sg.name=:name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|name
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
name|groups
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|SolverGroup
operator|)
name|groups
operator|.
name|get
argument_list|(
literal|0
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|SolverGroup
name|findBySessionIdAbbv
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|abbv
parameter_list|)
block|{
name|List
name|groups
init|=
operator|(
operator|new
name|SolverGroupDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select sg from SolverGroup sg where sg.session.uniqueId=:sessionId and sg.abbv=:abbv"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"abbv"
argument_list|,
name|abbv
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
name|groups
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|SolverGroup
operator|)
name|groups
operator|.
name|get
argument_list|(
literal|0
argument_list|)
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
name|SolverGroup
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|SolverGroup
name|sg
init|=
operator|(
name|SolverGroup
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|sg
operator|.
name|getName
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
name|sg
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|sg
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Solution
name|getCommittedSolution
parameter_list|()
block|{
if|if
condition|(
name|getSolutions
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
for|for
control|(
name|Iterator
name|i
init|=
name|getSolutions
argument_list|()
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
name|Solution
name|s
init|=
operator|(
name|Solution
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|isCommited
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
return|return
name|s
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
name|int
name|getMinDistributionPriority
parameter_list|()
block|{
name|int
name|ret
init|=
name|Integer
operator|.
name|MAX_VALUE
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|=
name|Math
operator|.
name|min
argument_list|(
name|ret
argument_list|,
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|int
name|getMaxDistributionPriority
parameter_list|()
block|{
name|int
name|ret
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getDepartments
argument_list|()
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|=
name|Math
operator|.
name|max
argument_list|(
name|ret
argument_list|,
name|d
operator|.
name|getDistributionPrefPriority
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|SolverGroup
name|sg
init|=
operator|new
name|SolverGroup
argument_list|()
decl_stmt|;
name|sg
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setAbbv
argument_list|(
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|sg
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|sg
return|;
block|}
annotation|@
name|Override
specifier|public
name|Serializable
name|getQualifierId
parameter_list|()
block|{
return|return
name|getUniqueId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierType
parameter_list|()
block|{
return|return
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierReference
parameter_list|()
block|{
return|return
name|getAbbv
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getQualifierLabel
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|SolverGroup
argument_list|>
name|getUserSolverGroups
parameter_list|(
name|UserContext
name|user
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|SolverGroup
argument_list|>
name|solverGroups
init|=
operator|new
name|TreeSet
argument_list|<
name|SolverGroup
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|==
literal|null
condition|)
return|return
name|solverGroups
return|;
if|if
condition|(
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|DepartmentIndependent
argument_list|)
condition|)
name|solverGroups
operator|.
name|addAll
argument_list|(
name|SolverGroup
operator|.
name|findBySessionId
argument_list|(
name|user
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
for|for
control|(
name|UserQualifier
name|q
range|:
name|user
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"SolverGroup"
argument_list|)
control|)
name|solverGroups
operator|.
name|add
argument_list|(
name|SolverGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
operator|(
name|Long
operator|)
name|q
operator|.
name|getQualifierId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|solverGroups
return|;
block|}
specifier|public
name|boolean
name|isAllowStudentScheduling
parameter_list|()
block|{
for|for
control|(
name|Department
name|department
range|:
name|getDepartments
argument_list|()
control|)
if|if
condition|(
name|department
operator|.
name|isAllowStudentScheduling
argument_list|()
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

