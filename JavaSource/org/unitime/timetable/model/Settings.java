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
name|BaseSettings
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
name|SettingsDAO
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
name|Settings
extends|extends
name|BaseSettings
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
name|Settings
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Settings
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
comment|/**      * Retrieves the user setting id /value if exists      * @param currRole Current Role      * @param uSettings User Settings Object      * @param keyId Setting UniqueId      * @param defaultValue Default Value      * @return Array of Setting id /value if found, otherwise returns -1 / default value      */
specifier|public
specifier|static
name|String
index|[]
name|getSettingValue
parameter_list|(
name|String
name|currRole
parameter_list|,
name|Set
name|uSettings
parameter_list|,
name|Long
name|keyId
parameter_list|,
name|String
name|defaultValue
parameter_list|)
block|{
name|String
index|[]
name|data
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|data
index|[
literal|0
index|]
operator|=
literal|"-1"
expr_stmt|;
name|data
index|[
literal|1
index|]
operator|=
name|defaultValue
expr_stmt|;
if|if
condition|(
name|uSettings
operator|==
literal|null
condition|)
return|return
name|data
return|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
try|try
block|{
name|_RootDAO
name|rootDao
init|=
operator|new
name|_RootDAO
argument_list|()
decl_stmt|;
name|hibSession
operator|=
name|rootDao
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|Iterator
name|i
init|=
name|uSettings
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ManagerSettings
name|mgrSettings
init|=
operator|(
name|ManagerSettings
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|hibSession
operator|.
name|update
argument_list|(
name|mgrSettings
argument_list|)
expr_stmt|;
if|if
condition|(
name|mgrSettings
operator|.
name|getKey
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|==
name|keyId
operator|.
name|intValue
argument_list|()
condition|)
block|{
name|data
index|[
literal|0
index|]
operator|=
name|mgrSettings
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|data
index|[
literal|1
index|]
operator|=
name|mgrSettings
operator|.
name|getValue
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|ex
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
comment|//if (hibSession!=null&& hibSession.isOpen()) hibSession.close();
block|}
return|return
name|data
return|;
block|}
comment|/** 	 * Get the default value for a given key 	 * @param key Setting key 	 * @return Default value if found, null otherwise 	 */
specifier|public
specifier|static
name|Settings
name|getSetting
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|Settings
name|settings
init|=
literal|null
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
try|try
block|{
name|SettingsDAO
name|sDao
init|=
operator|new
name|SettingsDAO
argument_list|()
decl_stmt|;
name|hibSession
operator|=
name|sDao
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|List
name|settingsList
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|Settings
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
literal|"key"
argument_list|,
name|key
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
name|settingsList
operator|.
name|size
argument_list|()
operator|!=
literal|0
condition|)
name|settings
operator|=
operator|(
name|Settings
operator|)
name|settingsList
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
name|settings
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
comment|//if (hibSession!=null&& hibSession.isOpen()) hibSession.close();
block|}
return|return
name|settings
return|;
block|}
block|}
end_class

end_unit

