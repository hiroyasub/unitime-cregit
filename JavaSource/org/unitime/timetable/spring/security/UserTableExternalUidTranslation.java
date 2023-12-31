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
name|spring
operator|.
name|security
package|;
end_package

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|interfaces
operator|.
name|ExternalUidTranslation
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
name|User
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
name|UserDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|UserTableExternalUidTranslation
implements|implements
name|ExternalUidTranslation
block|{
specifier|public
name|String
name|translate
parameter_list|(
name|String
name|uid
parameter_list|,
name|Source
name|source
parameter_list|,
name|Source
name|target
parameter_list|)
block|{
if|if
condition|(
name|uid
operator|==
literal|null
operator|||
name|source
operator|.
name|equals
argument_list|(
name|target
argument_list|)
condition|)
return|return
name|uid
return|;
if|if
condition|(
name|source
operator|.
name|equals
argument_list|(
name|Source
operator|.
name|LDAP
argument_list|)
condition|)
return|return
name|uid2ext
argument_list|(
name|uid
argument_list|)
return|;
if|if
condition|(
name|target
operator|.
name|equals
argument_list|(
name|Source
operator|.
name|LDAP
argument_list|)
condition|)
return|return
name|ext2uid
argument_list|(
name|uid
argument_list|)
return|;
return|return
name|uid
return|;
block|}
specifier|public
name|String
name|uid2ext
parameter_list|(
name|String
name|username
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|UserDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|User
name|user
init|=
operator|(
name|User
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from User where username=:username"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"username"
argument_list|,
name|username
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
return|return
operator|(
name|user
operator|==
literal|null
condition|?
name|username
else|:
name|user
operator|.
name|getExternalUniqueId
argument_list|()
operator|)
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
name|String
name|ext2uid
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|UserDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
name|User
name|user
init|=
operator|(
name|User
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from User where externalUniqueId=:externalUniqueId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalUniqueId"
argument_list|,
name|externalUniqueId
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
return|return
operator|(
name|user
operator|==
literal|null
condition|?
name|externalUniqueId
else|:
name|user
operator|.
name|getUsername
argument_list|()
operator|)
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
block|}
end_class

end_unit

