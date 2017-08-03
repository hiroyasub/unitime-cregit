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
name|HashSet
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
name|BaseEventServiceProvider
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
name|EventServiceProviderDAO
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

begin_class
specifier|public
class|class
name|EventServiceProvider
extends|extends
name|BaseEventServiceProvider
implements|implements
name|Comparable
argument_list|<
name|EventServiceProvider
argument_list|>
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
name|EventServiceProvider
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|EventServiceProvider
name|getEventServiceProvider
parameter_list|(
name|String
name|reference
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
if|if
condition|(
name|reference
operator|==
literal|null
operator|||
name|reference
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|EventServiceProvider
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where reference = :reference"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"reference"
argument_list|,
name|reference
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isUsed
parameter_list|()
block|{
if|if
condition|(
operator|(
operator|(
name|Number
operator|)
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(e) from Event e inner join e.requestedServices p where p.uniqueId = :providerId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"providerId"
argument_list|,
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
operator|.
name|intValue
argument_list|()
operator|>
literal|0
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|compareTo
parameter_list|(
name|EventServiceProvider
name|p
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getLabel
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|p
operator|.
name|getLabel
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
specifier|public
specifier|static
name|TreeSet
argument_list|<
name|EventServiceProvider
argument_list|>
name|getServiceProviders
parameter_list|(
name|UserContext
name|user
parameter_list|)
block|{
name|TreeSet
argument_list|<
name|EventServiceProvider
argument_list|>
name|providers
init|=
operator|new
name|TreeSet
argument_list|<
name|EventServiceProvider
argument_list|>
argument_list|()
decl_stmt|;
name|providers
operator|.
name|addAll
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where visible = true and session is null"
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
expr_stmt|;
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
name|providers
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
block|{
name|providers
operator|.
name|addAll
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where visible = true and session = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|user
operator|.
name|getCurrentAcademicSessionId
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
expr_stmt|;
block|}
else|else
block|{
name|providers
operator|.
name|addAll
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where visible = true and session = :sessionId and department is null"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|user
operator|.
name|getCurrentAcademicSessionId
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
expr_stmt|;
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
literal|"Department"
argument_list|)
control|)
name|providers
operator|.
name|addAll
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where visible = true and department = :departmentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
operator|(
name|Long
operator|)
name|q
operator|.
name|getQualifierId
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
expr_stmt|;
block|}
return|return
name|providers
return|;
block|}
specifier|public
name|EventServiceProvider
name|findInSession
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|hibSession
operator|==
literal|null
condition|)
name|hibSession
operator|=
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
expr_stmt|;
if|if
condition|(
name|getSession
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
name|this
return|;
block|}
if|else if
condition|(
name|getDepartment
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|(
name|EventServiceProvider
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where session = :sessionId and department is null and reference = :reference"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"reference"
argument_list|,
name|getReference
argument_list|()
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
else|else
block|{
return|return
operator|(
name|EventServiceProvider
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where session = :sessionId and department.deptCode = :deptCode and reference = :reference"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"deptCode"
argument_list|,
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"reference"
argument_list|,
name|getReference
argument_list|()
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
block|}
specifier|public
name|EventServiceProvider
name|findInSession
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
name|findInSession
argument_list|(
literal|null
argument_list|,
name|sessionId
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|EventServiceProvider
argument_list|>
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|List
argument_list|<
name|EventServiceProvider
argument_list|>
operator|)
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from EventServiceProvider where visible = true and (session is null or session = :sessionId)"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getLocationIds
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|getSession
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|isAllRooms
argument_list|()
condition|)
return|return
literal|null
return|;
name|HashSet
argument_list|<
name|Long
argument_list|>
name|ids
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId from Room l inner join l.allowedServices s where s.uniqueId = :serviceId and l.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"serviceId"
argument_list|,
name|getUniqueId
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
decl_stmt|;
name|ids
operator|.
name|addAll
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId from NonUniversityLocation l inner join l.allowedServices s where s.uniqueId = :serviceId and l.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"serviceId"
argument_list|,
name|getUniqueId
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
expr_stmt|;
return|return
name|ids
return|;
block|}
if|else if
condition|(
name|getDepartment
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|isAllRooms
argument_list|()
condition|)
return|return
literal|null
return|;
name|HashSet
argument_list|<
name|Long
argument_list|>
name|ids
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId from Room l inner join l.allowedServices s where s.uniqueId = :serviceId and l.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"serviceId"
argument_list|,
name|getUniqueId
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
decl_stmt|;
name|ids
operator|.
name|addAll
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId from NonUniversityLocation l inner join l.allowedServices s where s.uniqueId = :serviceId and l.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"serviceId"
argument_list|,
name|getUniqueId
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
expr_stmt|;
return|return
name|ids
return|;
block|}
else|else
block|{
if|if
condition|(
name|isAllRooms
argument_list|()
condition|)
block|{
return|return
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId from Location l where l.eventDepartment = :departmentId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
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
else|else
block|{
name|HashSet
argument_list|<
name|Long
argument_list|>
name|ids
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId from Room l inner join l.allowedServices s where s.uniqueId = :serviceId and l.eventDepartment = s.department"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"serviceId"
argument_list|,
name|getUniqueId
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
decl_stmt|;
name|ids
operator|.
name|addAll
argument_list|(
name|EventServiceProviderDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select l.uniqueId from NonUniversityLocation l inner join l.allowedServices s where s.uniqueId = :serviceId and l.eventDepartment = s.department"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"serviceId"
argument_list|,
name|getUniqueId
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
expr_stmt|;
return|return
name|ids
return|;
block|}
block|}
block|}
block|}
end_class

end_unit

