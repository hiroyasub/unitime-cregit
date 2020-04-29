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
name|onlinesectioning
operator|.
name|custom
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|defaults
operator|.
name|ApplicationProperty
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
name|gwt
operator|.
name|resources
operator|.
name|StudentSectioningMessages
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
name|gwt
operator|.
name|shared
operator|.
name|SectioningException
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Holder
parameter_list|<
name|T
parameter_list|>
block|{
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
name|String
name|iName
decl_stmt|;
name|ApplicationProperty
name|iProperty
decl_stmt|;
name|String
name|iDefaultProvider
init|=
literal|null
decl_stmt|;
name|T
name|iProvider
decl_stmt|;
name|String
name|iProviderClass
decl_stmt|;
name|Logger
name|iLog
decl_stmt|;
specifier|public
name|Holder
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|name
parameter_list|,
name|ApplicationProperty
name|property
parameter_list|,
name|Class
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|defaultProvider
parameter_list|)
block|{
name|iLog
operator|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|iName
operator|=
name|name
operator|.
name|getSimpleName
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"(?<=[^A-Z])([A-Z])"
argument_list|,
literal|" $1"
argument_list|)
expr_stmt|;
name|iProperty
operator|=
name|property
expr_stmt|;
name|iDefaultProvider
operator|=
operator|(
name|defaultProvider
operator|==
literal|null
condition|?
literal|null
else|:
name|defaultProvider
operator|.
name|getName
argument_list|()
operator|)
expr_stmt|;
block|}
specifier|public
name|Holder
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|name
parameter_list|,
name|ApplicationProperty
name|property
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|property
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|disposeProvider
parameter_list|()
block|{
if|if
condition|(
name|iProvider
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|iLog
operator|.
name|info
argument_list|(
literal|"Disposing old provider"
argument_list|)
expr_stmt|;
name|iProvider
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"dispose"
argument_list|)
operator|.
name|invoke
argument_list|(
name|iProvider
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|warn
argument_list|(
literal|"Failed to dispose: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|iProvider
operator|=
literal|null
expr_stmt|;
name|iProviderClass
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|T
name|getProvider
parameter_list|()
block|{
name|String
name|providerClass
init|=
name|iProperty
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|providerClass
operator|==
literal|null
operator|||
name|providerClass
operator|.
name|isEmpty
argument_list|()
condition|)
name|providerClass
operator|=
name|iDefaultProvider
expr_stmt|;
if|if
condition|(
name|providerClass
operator|==
literal|null
operator|||
name|providerClass
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|iProvider
operator|!=
literal|null
condition|)
name|disposeProvider
argument_list|()
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|providerClass
operator|.
name|equals
argument_list|(
name|iProviderClass
argument_list|)
condition|)
block|{
if|if
condition|(
name|iProvider
operator|!=
literal|null
condition|)
name|disposeProvider
argument_list|()
expr_stmt|;
name|iProviderClass
operator|=
name|providerClass
expr_stmt|;
name|iLog
operator|.
name|info
argument_list|(
literal|"Creating an instance of "
operator|+
name|iProviderClass
argument_list|)
expr_stmt|;
try|try
block|{
name|iProvider
operator|=
operator|(
name|T
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|iProviderClass
argument_list|)
operator|.
name|getDeclaredConstructor
argument_list|()
operator|.
name|newInstance
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|iLog
operator|.
name|error
argument_list|(
literal|"Failed to create an instance of "
operator|+
name|iProviderClass
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionCustomProvider
argument_list|(
name|iName
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
return|return
name|iProvider
return|;
block|}
specifier|public
specifier|synchronized
name|void
name|release
parameter_list|()
block|{
name|disposeProvider
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|boolean
name|hasProvider
parameter_list|()
block|{
return|return
name|getProvider
argument_list|()
operator|!=
literal|null
return|;
block|}
block|}
end_class

end_unit

