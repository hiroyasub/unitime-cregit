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
name|solver
operator|.
name|course
operator|.
name|ui
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ClassInstructor
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
name|ClassInstructorDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassInstructorInfo
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|ClassInstructorInfo
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|8576391767085203451L
decl_stmt|;
specifier|protected
name|Long
name|iId
decl_stmt|;
specifier|protected
name|String
name|iExternalUniqueId
init|=
literal|null
decl_stmt|;
specifier|protected
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|protected
name|boolean
name|iIsLead
init|=
literal|false
decl_stmt|;
specifier|protected
name|int
name|iShare
init|=
literal|0
decl_stmt|;
specifier|protected
specifier|transient
name|ClassInstructor
name|iInstructor
decl_stmt|;
specifier|public
name|ClassInstructorInfo
parameter_list|(
name|ClassInstructor
name|instructor
parameter_list|)
block|{
name|iId
operator|=
name|instructor
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|instructor
operator|.
name|getInstructor
argument_list|()
operator|.
name|getNameLastFirst
argument_list|()
expr_stmt|;
name|iIsLead
operator|=
name|instructor
operator|.
name|isLead
argument_list|()
expr_stmt|;
name|iShare
operator|=
name|instructor
operator|.
name|getPercentShare
argument_list|()
expr_stmt|;
name|iExternalUniqueId
operator|=
name|instructor
operator|.
name|getInstructor
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
name|iInstructor
operator|=
name|instructor
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
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|boolean
name|isLead
parameter_list|()
block|{
return|return
name|iIsLead
return|;
block|}
specifier|public
name|int
name|getShare
parameter_list|()
block|{
return|return
name|iShare
return|;
block|}
specifier|public
name|String
name|getExternalUniqueId
parameter_list|()
block|{
if|if
condition|(
name|iExternalUniqueId
operator|==
literal|null
operator|&&
name|iInstructor
operator|==
literal|null
condition|)
name|iExternalUniqueId
operator|=
name|getInstructor
argument_list|()
operator|.
name|getInstructor
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|ClassInstructor
name|getInstructor
parameter_list|()
block|{
if|if
condition|(
name|iInstructor
operator|==
literal|null
condition|)
name|iInstructor
operator|=
name|ClassInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iInstructor
return|;
block|}
specifier|public
name|ClassInstructor
name|getInstructor
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
return|return
name|ClassInstructorDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ClassInstructorInfo
name|i
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|i
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
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|i
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|getExternalUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
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
name|ClassInstructorInfo
operator|)
condition|)
return|return
literal|false
return|;
name|ClassInstructorInfo
name|i
init|=
operator|(
name|ClassInstructorInfo
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|getExternalUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|i
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|i
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

