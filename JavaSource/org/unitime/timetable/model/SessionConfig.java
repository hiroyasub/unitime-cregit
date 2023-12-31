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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|BaseSessionConfig
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
name|SessionConfigDAO
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
name|_RootDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SessionConfig
extends|extends
name|BaseSessionConfig
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
name|SessionConfig
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|SessionConfig
name|getConfig
parameter_list|(
name|String
name|key
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
name|SessionConfig
operator|)
name|SessionConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where key = :key and session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|key
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
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|SessionConfig
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
name|SessionConfig
argument_list|>
operator|)
name|SessionConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where session.uniqueId = :sessionId order by key"
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
specifier|static
name|String
name|getConfigValue
parameter_list|(
name|String
name|key
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|String
name|defaultValue
parameter_list|)
block|{
comment|//return defaultValue if hibernate is not yet initialized or no session is given
if|if
condition|(
operator|!
name|_RootDAO
operator|.
name|isConfigured
argument_list|()
operator|||
name|sessionId
operator|==
literal|null
condition|)
return|return
name|defaultValue
return|;
name|String
name|value
init|=
operator|(
name|String
operator|)
name|SessionConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select value from SessionConfig where key = :key and session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"key"
argument_list|,
name|key
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
name|uniqueResult
argument_list|()
decl_stmt|;
return|return
operator|(
name|value
operator|==
literal|null
condition|?
name|defaultValue
else|:
name|value
operator|)
return|;
block|}
specifier|public
specifier|static
name|Properties
name|toProperties
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|Properties
name|properties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|_RootDAO
operator|.
name|isConfigured
argument_list|()
operator|||
name|sessionId
operator|==
literal|null
condition|)
return|return
name|properties
return|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|SessionConfig
name|config
range|:
operator|(
name|List
argument_list|<
name|SessionConfig
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from SessionConfig where session.uniqueId = :sessionId"
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
control|)
block|{
name|properties
operator|.
name|setProperty
argument_list|(
name|config
operator|.
name|getKey
argument_list|()
argument_list|,
name|config
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|config
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return
name|properties
return|;
block|}
block|}
end_class

end_unit

