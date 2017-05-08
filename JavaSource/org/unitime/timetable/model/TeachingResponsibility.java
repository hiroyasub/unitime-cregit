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
name|BaseTeachingResponsibility
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
name|TeachingResponsibilityDAO
import|;
end_import

begin_class
specifier|public
class|class
name|TeachingResponsibility
extends|extends
name|BaseTeachingResponsibility
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
enum|enum
name|Option
block|{
name|auxiliary
argument_list|(
literal|"Do not report"
argument_list|)
block|,
name|noexport
argument_list|(
literal|"Do not export"
argument_list|)
block|,
name|noevents
argument_list|(
literal|"Do not show in events"
argument_list|)
block|,
name|isdefault
argument_list|(
literal|"Default responsibility"
argument_list|)
block|, 		;
specifier|private
name|String
name|iName
decl_stmt|;
name|Option
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|int
name|toggle
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
block|}
specifier|public
name|TeachingResponsibility
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|TeachingResponsibility
argument_list|>
name|getInstructorTeachingResponsibilities
parameter_list|()
block|{
return|return
operator|(
name|List
argument_list|<
name|TeachingResponsibility
argument_list|>
operator|)
name|TeachingResponsibilityDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from TeachingResponsibility where instructor = true order by label"
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
specifier|static
name|TeachingResponsibility
name|getDefaultInstructorTeachingResponsibility
parameter_list|()
block|{
for|for
control|(
name|TeachingResponsibility
name|r
range|:
name|getInstructorTeachingResponsibilities
argument_list|()
control|)
if|if
condition|(
name|r
operator|.
name|hasOption
argument_list|(
name|Option
operator|.
name|isdefault
argument_list|)
condition|)
return|return
name|r
return|;
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|TeachingResponsibility
argument_list|>
name|getCoordinatorTeachingResponsibilities
parameter_list|()
block|{
return|return
operator|(
name|List
argument_list|<
name|TeachingResponsibility
argument_list|>
operator|)
name|TeachingResponsibilityDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from TeachingResponsibility where coordinator = true order by label"
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
specifier|static
name|TeachingResponsibility
name|getDefaultCoordinatorTeachingResponsibility
parameter_list|()
block|{
for|for
control|(
name|TeachingResponsibility
name|r
range|:
name|getCoordinatorTeachingResponsibilities
argument_list|()
control|)
if|if
condition|(
name|r
operator|.
name|hasOption
argument_list|(
name|Option
operator|.
name|isdefault
argument_list|)
condition|)
return|return
name|r
return|;
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|TeachingResponsibility
name|getTeachingResponsibility
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
name|TeachingResponsibility
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from TeachingResponsibility where reference = :reference"
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
name|hasOption
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
return|return
name|getOptions
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|getOptions
argument_list|()
operator|&
name|option
operator|.
name|toggle
argument_list|()
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|void
name|addOption
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
if|if
condition|(
operator|!
name|hasOption
argument_list|(
name|option
argument_list|)
condition|)
name|setOptions
argument_list|(
operator|(
name|getOptions
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getOptions
argument_list|()
operator|)
operator|+
name|option
operator|.
name|toggle
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|removeOption
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
if|if
condition|(
name|hasOption
argument_list|(
name|option
argument_list|)
condition|)
name|setOptions
argument_list|(
name|getOptions
argument_list|()
operator|-
name|option
operator|.
name|toggle
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|boolean
name|hasOption
parameter_list|(
name|Option
name|option
parameter_list|,
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
name|TeachingResponsibility
name|responsibility
init|=
name|getTeachingResponsibility
argument_list|(
name|reference
argument_list|,
name|hibSession
argument_list|)
decl_stmt|;
return|return
name|responsibility
operator|!=
literal|null
operator|&&
name|responsibility
operator|.
name|hasOption
argument_list|(
name|option
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|getMatchingResponsibilities
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|TeachingResponsibilityDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|responsibilities
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|TeachingResponsibility
name|responsibility
range|:
name|TeachingResponsibilityDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|(
name|hibSession
argument_list|)
control|)
block|{
if|if
condition|(
name|responsibility
operator|.
name|hasOption
argument_list|(
name|option
argument_list|)
condition|)
name|responsibilities
operator|.
name|add
argument_list|(
name|responsibility
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|responsibilities
return|;
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
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
name|TeachingResponsibilityDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(ci) from ClassInstructor ci where ci.responsibility.uniqueId = :responsibilityId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"responsibilityId"
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
if|if
condition|(
operator|(
operator|(
name|Number
operator|)
name|TeachingResponsibilityDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select count(oc) from OfferingCoordinator oc where oc.responsibility.uniqueId = :responsibilityId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"responsibilityId"
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
block|}
end_class

end_unit

