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
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|configuration
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|configuration
operator|.
name|ConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|configuration
operator|.
name|PropertiesConfiguration
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|configuration
operator|.
name|event
operator|.
name|ConfigurationEvent
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|configuration
operator|.
name|event
operator|.
name|ConfigurationListener
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|configuration
operator|.
name|reloading
operator|.
name|FileChangedReloadingStrategy
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
name|ApplicationProperties
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
name|listeners
operator|.
name|SessionListener
import|;
end_import

begin_comment
comment|/**  * Custom Implementation of MessageResources using Commons Configuration  * Searches for file messages_{locale language}.properties.   * If not found uses messages.properties.   * Used mainly in development to avoid reloading of application.  *   * @author Heston Fernandes, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MessageResources
extends|extends
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|MessageResources
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|3234198544455822319L
decl_stmt|;
specifier|private
specifier|static
name|PropertiesConfiguration
name|resource
decl_stmt|;
specifier|private
specifier|static
name|String
name|resourceFile
decl_stmt|;
specifier|public
name|MessageResources
parameter_list|(
name|MessageResourcesFactory
name|factory
parameter_list|,
name|String
name|config
parameter_list|,
name|boolean
name|returnNull
parameter_list|)
block|{
name|super
argument_list|(
name|factory
argument_list|,
name|config
argument_list|,
name|returnNull
argument_list|)
expr_stmt|;
block|}
specifier|public
name|MessageResources
parameter_list|(
name|MessageResourcesFactory
name|factory
parameter_list|,
name|String
name|config
parameter_list|)
block|{
name|super
argument_list|(
name|factory
argument_list|,
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getMessage
parameter_list|(
name|Locale
name|locale
parameter_list|,
name|String
name|key
parameter_list|)
block|{
comment|// See MessageResource documentation for complete list and order of search of respurce bundles
comment|// For our purpose only 2 file names are searched
if|if
condition|(
name|resource
operator|==
literal|null
condition|)
block|{
comment|// get the configuration for the specified locale
name|resource
operator|=
operator|(
name|PropertiesConfiguration
operator|)
name|getConfiguration
argument_list|(
name|this
operator|.
name|config
operator|+
literal|"_"
operator|+
name|locale
operator|.
name|getLanguage
argument_list|()
operator|+
literal|".properties"
argument_list|)
expr_stmt|;
name|resourceFile
operator|=
name|this
operator|.
name|config
operator|+
literal|"_"
operator|+
name|locale
operator|.
name|getLanguage
argument_list|()
operator|+
literal|".properties"
expr_stmt|;
if|if
condition|(
name|resource
operator|==
literal|null
operator|||
operator|!
name|resource
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
comment|// look for the key in the root configuration
name|resource
operator|=
operator|(
name|PropertiesConfiguration
operator|)
name|getConfiguration
argument_list|(
name|this
operator|.
name|config
operator|+
literal|".properties"
argument_list|)
expr_stmt|;
name|resourceFile
operator|=
name|this
operator|.
name|config
operator|+
literal|".properties"
expr_stmt|;
block|}
if|if
condition|(
name|resource
operator|==
literal|null
condition|)
return|return
literal|null
return|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|key
operator|+
literal|" - "
operator|+
name|resource
operator|.
name|getString
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|resource
operator|.
name|getString
argument_list|(
name|key
argument_list|)
return|;
block|}
specifier|private
name|Configuration
name|getConfiguration
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|Configuration
name|configuration
init|=
literal|null
decl_stmt|;
name|URL
name|url
init|=
name|Thread
operator|.
name|currentThread
argument_list|()
operator|.
name|getContextClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|!=
literal|null
condition|)
block|{
name|PropertiesConfiguration
name|pc
init|=
operator|new
name|PropertiesConfiguration
argument_list|()
decl_stmt|;
name|pc
operator|.
name|setURL
argument_list|(
name|url
argument_list|)
expr_stmt|;
comment|// Set reloading strategy
name|String
name|dynamicReload
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.properties.dynamic_reload"
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|dynamicReload
operator|!=
literal|null
operator|&&
name|dynamicReload
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"true"
argument_list|)
condition|)
block|{
name|long
name|refreshDelay
init|=
name|Constants
operator|.
name|getPositiveInteger
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.properties.dynamic_reload_interval"
argument_list|)
argument_list|,
literal|15000
argument_list|)
decl_stmt|;
name|FileChangedReloadingStrategy
name|strategy
init|=
operator|new
name|FileChangedReloadingStrategy
argument_list|()
decl_stmt|;
name|strategy
operator|.
name|setRefreshDelay
argument_list|(
name|refreshDelay
argument_list|)
expr_stmt|;
name|pc
operator|.
name|setReloadingStrategy
argument_list|(
name|strategy
argument_list|)
expr_stmt|;
name|pc
operator|.
name|addConfigurationListener
argument_list|(
operator|new
name|MessageResourcesCfgListener
argument_list|(
name|pc
operator|.
name|getBasePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|pc
operator|.
name|load
argument_list|()
expr_stmt|;
name|configuration
operator|=
name|pc
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConfigurationException
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
literal|"Message Resources configuration exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|configuration
return|;
block|}
specifier|public
specifier|static
name|PropertiesConfiguration
name|getResource
parameter_list|()
block|{
return|return
name|resource
return|;
block|}
specifier|public
specifier|static
name|void
name|setResource
parameter_list|(
name|PropertiesConfiguration
name|resource
parameter_list|)
block|{
name|MessageResources
operator|.
name|resource
operator|=
name|resource
expr_stmt|;
block|}
specifier|public
specifier|static
name|String
name|getResourceFile
parameter_list|()
block|{
return|return
name|resourceFile
return|;
block|}
specifier|public
specifier|static
name|void
name|setResourceFile
parameter_list|(
name|String
name|resourceFile
parameter_list|)
block|{
name|MessageResources
operator|.
name|resourceFile
operator|=
name|resourceFile
expr_stmt|;
block|}
class|class
name|MessageResourcesCfgListener
implements|implements
name|ConfigurationListener
block|{
specifier|private
name|String
name|basePath
decl_stmt|;
specifier|public
name|MessageResourcesCfgListener
parameter_list|(
name|String
name|basePath
parameter_list|)
block|{
name|this
operator|.
name|basePath
operator|=
name|basePath
expr_stmt|;
block|}
specifier|public
name|void
name|configurationChanged
parameter_list|(
name|ConfigurationEvent
name|arg0
parameter_list|)
block|{
name|SessionListener
operator|.
name|reloadMessageResources
argument_list|(
name|basePath
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

