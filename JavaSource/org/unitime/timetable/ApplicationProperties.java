begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

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
name|net
operator|.
name|URLDecoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|model
operator|.
name|ApplicationConfig
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
name|SessionConfig
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
operator|.
name|Constants
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
name|util
operator|.
name|Formats
import|;
end_import

begin_comment
comment|/**  * Sets the system properties for any application.  * The properties in this file is adapted for each application      * @author Heston Fernandes, Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ApplicationProperties
block|{
specifier|private
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|sDF_file
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
literal|"dd-MMM-yy_HHmmssSSS"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Properties
name|props
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
specifier|private
specifier|static
name|long
name|appPropertiesLastModified
init|=
operator|-
literal|1
decl_stmt|,
name|custPropertiesLastModified
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
specifier|static
name|PropertyFileChangeListener
name|pfc
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|Properties
name|configProps
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|Map
argument_list|<
name|Long
argument_list|,
name|Properties
argument_list|>
name|sSessionProperties
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Properties
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|ThreadLocal
argument_list|<
name|Long
argument_list|>
name|sAcademicSession
init|=
operator|new
name|ThreadLocal
argument_list|<
name|Long
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|Long
name|initialValue
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
decl_stmt|;
specifier|public
specifier|static
name|Long
name|getSessionId
parameter_list|()
block|{
return|return
name|sAcademicSession
operator|.
name|get
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|setSessionId
parameter_list|(
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
name|sAcademicSession
operator|.
name|remove
argument_list|()
expr_stmt|;
else|else
block|{
name|sAcademicSession
operator|.
name|set
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Sets the properties  	 */
static|static
block|{
name|load
argument_list|()
expr_stmt|;
comment|// Spawn thread to dynamically reload
comment|// by design once this thread is set up it cannot be destroyed even if the reloaded property is set to false
name|String
name|dynamicReload
init|=
name|props
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
operator|(
name|appPropertiesLastModified
operator|>
literal|0
operator|||
name|custPropertiesLastModified
operator|>
literal|0
operator|)
operator|&&
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
name|pfc
operator|=
operator|new
name|PropertyFileChangeListener
argument_list|()
expr_stmt|;
name|pfc
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** 	 * Load properties  	 */
specifier|public
specifier|static
name|void
name|load
parameter_list|()
block|{
try|try
block|{
comment|// Load properties set in application.properties
name|URL
name|appPropertiesUrl
init|=
name|ApplicationProperties
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"application.properties"
argument_list|)
decl_stmt|;
if|if
condition|(
name|appPropertiesUrl
operator|!=
literal|null
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Reading "
operator|+
name|URLDecoder
operator|.
name|decode
argument_list|(
name|appPropertiesUrl
operator|.
name|getPath
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|props
operator|.
name|load
argument_list|(
name|appPropertiesUrl
operator|.
name|openStream
argument_list|()
argument_list|)
expr_stmt|;
block|}
try|try
block|{
try|try
block|{
name|appPropertiesLastModified
operator|=
operator|new
name|File
argument_list|(
name|appPropertiesUrl
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|appPropertiesLastModified
operator|=
operator|new
name|File
argument_list|(
name|appPropertiesUrl
operator|.
name|getPath
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
comment|// Load properties set in custom properties
name|String
name|customProperties
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.custom.properties"
argument_list|)
decl_stmt|;
if|if
condition|(
name|customProperties
operator|==
literal|null
condition|)
name|customProperties
operator|=
name|props
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.custom.properties"
argument_list|,
literal|"custom.properties"
argument_list|)
expr_stmt|;
name|URL
name|custPropertiesUrl
init|=
name|ApplicationProperties
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|customProperties
argument_list|)
decl_stmt|;
if|if
condition|(
name|custPropertiesUrl
operator|!=
literal|null
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Reading "
operator|+
name|URLDecoder
operator|.
name|decode
argument_list|(
name|custPropertiesUrl
operator|.
name|getPath
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|props
operator|.
name|load
argument_list|(
name|custPropertiesUrl
operator|.
name|openStream
argument_list|()
argument_list|)
expr_stmt|;
try|try
block|{
try|try
block|{
name|custPropertiesLastModified
operator|=
operator|new
name|File
argument_list|(
name|custPropertiesUrl
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|custPropertiesLastModified
operator|=
operator|new
name|File
argument_list|(
name|custPropertiesUrl
operator|.
name|getPath
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
if|else if
condition|(
operator|new
name|File
argument_list|(
name|customProperties
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Reading "
operator|+
name|customProperties
operator|+
literal|" ..."
argument_list|)
expr_stmt|;
name|FileInputStream
name|fis
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fis
operator|=
operator|new
name|FileInputStream
argument_list|(
name|customProperties
argument_list|)
expr_stmt|;
name|props
operator|.
name|load
argument_list|(
name|fis
argument_list|)
expr_stmt|;
name|custPropertiesLastModified
operator|=
operator|new
name|File
argument_list|(
name|customProperties
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|fis
operator|!=
literal|null
condition|)
name|fis
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
comment|// Load system properties
name|props
operator|.
name|putAll
argument_list|(
name|System
operator|.
name|getProperties
argument_list|()
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
block|}
comment|/** 	 * Reload properties from file application.properties 	 */
specifier|public
specifier|static
name|void
name|reloadIfNeeded
parameter_list|()
block|{
if|if
condition|(
name|appPropertiesLastModified
operator|>=
literal|0
condition|)
block|{
name|URL
name|appPropertiesUrl
init|=
name|ApplicationProperties
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
literal|"application.properties"
argument_list|)
decl_stmt|;
name|long
name|appPropTS
init|=
operator|-
literal|1
decl_stmt|;
try|try
block|{
try|try
block|{
name|appPropTS
operator|=
operator|new
name|File
argument_list|(
name|appPropertiesUrl
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|appPropTS
operator|=
operator|new
name|File
argument_list|(
name|appPropertiesUrl
operator|.
name|getPath
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
name|String
name|customProperties
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.custom.properties"
argument_list|)
decl_stmt|;
if|if
condition|(
name|customProperties
operator|==
literal|null
condition|)
name|customProperties
operator|=
name|props
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.custom.properties"
argument_list|,
literal|"custom.properties"
argument_list|)
expr_stmt|;
name|URL
name|custPropertiesUrl
init|=
name|ApplicationProperties
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|customProperties
argument_list|)
decl_stmt|;
name|long
name|custPropTS
init|=
operator|-
literal|1
decl_stmt|;
try|try
block|{
if|if
condition|(
name|custPropertiesUrl
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|custPropTS
operator|=
operator|new
name|File
argument_list|(
name|custPropertiesUrl
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|custPropTS
operator|=
operator|new
name|File
argument_list|(
name|custPropertiesUrl
operator|.
name|getPath
argument_list|()
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
block|}
if|else if
condition|(
operator|new
name|File
argument_list|(
name|customProperties
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|custPropTS
operator|=
operator|new
name|File
argument_list|(
name|customProperties
argument_list|)
operator|.
name|lastModified
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|appPropTS
operator|>
name|appPropertiesLastModified
operator|||
name|custPropTS
operator|>
name|custPropertiesLastModified
condition|)
name|load
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|Properties
name|getConfigProperties
parameter_list|()
block|{
if|if
condition|(
name|configProps
operator|==
literal|null
operator|&&
name|_RootDAO
operator|.
name|isConfigured
argument_list|()
condition|)
name|configProps
operator|=
name|ApplicationConfig
operator|.
name|toProperties
argument_list|()
expr_stmt|;
return|return
operator|(
name|configProps
operator|==
literal|null
condition|?
operator|new
name|Properties
argument_list|()
else|:
name|configProps
operator|)
return|;
block|}
specifier|public
specifier|static
name|void
name|clearConfigProperties
parameter_list|()
block|{
name|configProps
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
specifier|static
name|Properties
name|getSessionProperties
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
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
operator|new
name|Properties
argument_list|()
return|;
name|Properties
name|properties
init|=
name|sSessionProperties
operator|.
name|get
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|properties
operator|==
literal|null
condition|)
block|{
name|properties
operator|=
name|SessionConfig
operator|.
name|toProperties
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
name|sSessionProperties
operator|.
name|put
argument_list|(
name|sessionId
argument_list|,
name|properties
argument_list|)
expr_stmt|;
block|}
return|return
name|properties
return|;
block|}
specifier|public
specifier|static
name|void
name|clearSessionProperties
parameter_list|(
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
name|sSessionProperties
operator|.
name|clear
argument_list|()
expr_stmt|;
else|else
name|sSessionProperties
operator|.
name|remove
argument_list|(
name|sessionId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Retrieves value for the property key 	 * @param key 	 * @return null if invalid key / key does not exist 	 */
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|getProperty
argument_list|(
name|key
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|ApplicationProperty
name|property
parameter_list|)
block|{
return|return
name|property
operator|.
name|value
argument_list|()
return|;
block|}
comment|/** 	 * Retrieves value for the property key 	 * @param defaultValue 	 * @param key 	 * @return default value if invalid key / key does not exist 	 */
specifier|public
specifier|static
name|String
name|getProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|defaultValue
parameter_list|)
block|{
if|if
condition|(
name|key
operator|==
literal|null
operator|||
name|key
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|defaultValue
return|;
name|Long
name|sessionId
init|=
name|ApplicationProperties
operator|.
name|getSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
block|{
name|String
name|value
init|=
name|getSessionProperties
argument_list|(
name|sessionId
argument_list|)
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
return|return
name|value
return|;
block|}
name|String
name|value
init|=
name|getConfigProperties
argument_list|()
operator|.
name|getProperty
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
return|return
name|value
return|;
return|return
name|props
operator|.
name|getProperty
argument_list|(
name|key
argument_list|,
name|defaultValue
argument_list|)
return|;
block|}
comment|/** 	 * Return default properties (excluding application configuration) 	 */
specifier|public
specifier|static
name|Properties
name|getDefaultProperties
parameter_list|()
block|{
return|return
name|props
return|;
block|}
comment|/** 	 * Gets the properties used to configure the application  	 * @return Properties object 	 */
specifier|public
specifier|static
name|Properties
name|getProperties
parameter_list|()
block|{
name|Properties
name|ret
init|=
operator|(
name|Properties
operator|)
name|props
operator|.
name|clone
argument_list|()
decl_stmt|;
name|ret
operator|.
name|putAll
argument_list|(
name|getConfigProperties
argument_list|()
argument_list|)
expr_stmt|;
name|Long
name|sessionId
init|=
name|ApplicationProperties
operator|.
name|getSessionId
argument_list|()
decl_stmt|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
block|{
name|ret
operator|.
name|putAll
argument_list|(
name|getSessionProperties
argument_list|(
name|sessionId
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
comment|/** 	 * Most resources are located in /WEB-INF folder 	 * This function constructs the absolute path to /WEB-INF 	 * @return Absolute file path  	 */
specifier|public
specifier|static
name|String
name|getBasePath
parameter_list|()
block|{
comment|// Added ability to override the default way of getting the base dir
name|String
name|base
init|=
name|getProperty
argument_list|(
literal|"unitime.base.dir"
argument_list|)
decl_stmt|;
if|if
condition|(
name|base
operator|!=
literal|null
condition|)
return|return
name|base
return|;
comment|//Get the URL of the class location (usually in /WEB-INF/classes/...)
name|URL
name|url
init|=
name|ApplicationProperties
operator|.
name|class
operator|.
name|getProtectionDomain
argument_list|()
operator|.
name|getCodeSource
argument_list|()
operator|.
name|getLocation
argument_list|()
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
condition|)
return|return
literal|null
return|;
comment|//Get file and parent
name|File
name|file
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|// Try to use URI to avoid bug 4466485 on Windows (see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4466485)
name|file
operator|=
operator|new
name|File
argument_list|(
operator|new
name|URI
argument_list|(
name|url
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|e
parameter_list|)
block|{
name|file
operator|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|getFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|file
operator|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|getFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|File
name|parent
init|=
name|file
operator|.
name|getParentFile
argument_list|()
decl_stmt|;
comment|// Iterate up the folder structure till WEB-INF is encountered
while|while
condition|(
name|parent
operator|!=
literal|null
operator|&&
operator|!
name|parent
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"WEB-INF"
argument_list|)
condition|)
name|parent
operator|=
name|parent
operator|.
name|getParentFile
argument_list|()
expr_stmt|;
return|return
operator|(
name|parent
operator|==
literal|null
condition|?
literal|null
else|:
name|parent
operator|.
name|getAbsolutePath
argument_list|()
operator|)
return|;
block|}
specifier|public
specifier|static
name|File
name|getDataFolder
parameter_list|()
block|{
if|if
condition|(
name|getProperty
argument_list|(
literal|"unitime.data.dir"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|File
name|dir
init|=
operator|new
name|File
argument_list|(
name|getProperty
argument_list|(
literal|"unitime.data.dir"
argument_list|)
argument_list|)
decl_stmt|;
name|dir
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
return|return
name|dir
return|;
block|}
name|File
name|dir
init|=
operator|new
name|File
argument_list|(
name|getBasePath
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|dir
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"webapps"
argument_list|)
condition|)
name|dir
operator|=
name|dir
operator|.
name|getParentFile
argument_list|()
expr_stmt|;
name|dir
operator|=
name|dir
operator|.
name|getParentFile
argument_list|()
operator|.
name|getParentFile
argument_list|()
expr_stmt|;
name|dir
operator|=
operator|new
name|File
argument_list|(
name|dir
argument_list|,
literal|"data"
argument_list|)
expr_stmt|;
name|dir
operator|=
operator|new
name|File
argument_list|(
name|dir
argument_list|,
literal|"unitime"
argument_list|)
expr_stmt|;
name|dir
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
return|return
name|dir
return|;
block|}
specifier|public
specifier|static
name|File
name|getBlobFolder
parameter_list|()
block|{
name|File
name|dir
init|=
operator|new
name|File
argument_list|(
name|getDataFolder
argument_list|()
argument_list|,
literal|"blob"
argument_list|)
decl_stmt|;
name|dir
operator|.
name|mkdir
argument_list|()
expr_stmt|;
return|return
name|dir
return|;
block|}
specifier|public
specifier|static
name|File
name|getRestoreFolder
parameter_list|()
block|{
name|File
name|dir
init|=
operator|new
name|File
argument_list|(
name|getDataFolder
argument_list|()
argument_list|,
literal|"restore"
argument_list|)
decl_stmt|;
name|dir
operator|.
name|mkdir
argument_list|()
expr_stmt|;
return|return
name|dir
return|;
block|}
specifier|public
specifier|static
name|File
name|getPassivationFolder
parameter_list|()
block|{
name|File
name|dir
init|=
operator|new
name|File
argument_list|(
name|getDataFolder
argument_list|()
argument_list|,
literal|"passivate"
argument_list|)
decl_stmt|;
name|dir
operator|.
name|mkdir
argument_list|()
expr_stmt|;
return|return
name|dir
return|;
block|}
specifier|public
specifier|static
name|File
name|getTempFolder
parameter_list|()
block|{
name|File
name|dir
init|=
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|getBasePath
argument_list|()
argument_list|)
operator|.
name|getParentFile
argument_list|()
argument_list|,
literal|"temp"
argument_list|)
decl_stmt|;
name|dir
operator|.
name|mkdir
argument_list|()
expr_stmt|;
return|return
name|dir
return|;
block|}
specifier|public
specifier|static
name|File
name|getTempFile
parameter_list|(
name|String
name|prefix
parameter_list|,
name|String
name|ext
parameter_list|)
block|{
name|File
name|file
init|=
literal|null
decl_stmt|;
try|try
block|{
name|file
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
name|prefix
operator|+
literal|"_"
operator|+
name|sDF_file
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
argument_list|,
literal|"."
operator|+
name|ext
argument_list|,
name|getTempFolder
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
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
name|file
operator|=
operator|new
name|File
argument_list|(
name|getTempFolder
argument_list|()
argument_list|,
name|prefix
operator|+
literal|"_"
operator|+
name|sDF_file
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
operator|+
literal|"."
operator|+
name|ext
argument_list|)
expr_stmt|;
block|}
name|file
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
return|return
name|file
return|;
block|}
comment|/** 	 * Stop Property File Change Listener Thread  	 */
specifier|public
specifier|static
name|void
name|stopListener
parameter_list|()
block|{
if|if
condition|(
name|pfc
operator|!=
literal|null
operator|&&
name|pfc
operator|.
name|isAlive
argument_list|()
operator|&&
operator|!
name|pfc
operator|.
name|isInterrupted
argument_list|()
condition|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Stopping Property File Change Listener Thread ..."
argument_list|)
expr_stmt|;
name|pfc
operator|.
name|interrupt
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Thread to check if property file has changed      * and reload the properties on the fly. Interval = 1 minute      */
specifier|static
class|class
name|PropertyFileChangeListener
extends|extends
name|Thread
block|{
specifier|public
name|PropertyFileChangeListener
parameter_list|()
block|{
name|setName
argument_list|(
literal|"Property File Change Listener Thread"
argument_list|)
expr_stmt|;
name|setDaemon
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Starting Property File Change Listener Thread ..."
argument_list|)
expr_stmt|;
name|long
name|threadInterval
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
while|while
condition|(
literal|true
condition|)
block|{
try|try
block|{
name|sleep
argument_list|(
name|threadInterval
argument_list|)
expr_stmt|;
name|reloadIfNeeded
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Property File Change Listener Thread interrupted ..."
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"Property File Change Listener Thread failed, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

