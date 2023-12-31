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
name|webutil
package|;
end_package

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
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|CommonValues
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
name|UserProperty
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
name|SessionContext
import|;
end_import

begin_comment
comment|/**  * Miscellaneous function to generate javascript based on settings  *   * @author Heston Fernandes, Tomas Muller, Zuzana Mullerova  */
end_comment

begin_class
specifier|public
class|class
name|JavascriptFunctions
block|{
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|boolean
name|isJsConfirm
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
return|return
operator|(
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|?
name|CommonValues
operator|.
name|Yes
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|ConfirmationDialogs
argument_list|)
argument_list|)
else|:
literal|true
operator|)
return|;
block|}
comment|/**      * Returns the javascript variable 'jsConfirm' set to true/false depending      * on the user setting. This function is called from JSPs and is used to      * determine whether confirmation dialogs are displayed to the user      * @param user User Object      * @return String "var jsConfirm = true;" OR "var jsConfirm = false;"      */
specifier|public
specifier|static
name|String
name|getJsConfirm
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
return|return
literal|"var jsConfirm = "
operator|+
operator|(
name|isJsConfirm
argument_list|(
name|context
argument_list|)
condition|?
literal|"true"
else|:
literal|"false"
operator|)
operator|+
literal|";"
return|;
block|}
specifier|public
specifier|static
name|String
name|getInheritInstructorPreferencesCondition
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
name|String
name|inheritInstrPref
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|InheritInstructorPrefs
argument_list|)
decl_stmt|;
if|if
condition|(
name|CommonValues
operator|.
name|Always
operator|.
name|eq
argument_list|(
name|inheritInstrPref
argument_list|)
condition|)
block|{
return|return
literal|"true"
return|;
block|}
if|else if
condition|(
name|CommonValues
operator|.
name|Never
operator|.
name|eq
argument_list|(
name|inheritInstrPref
argument_list|)
condition|)
block|{
return|return
literal|"false"
return|;
block|}
else|else
block|{
return|return
literal|"confirm('"
operator|+
name|MSG
operator|.
name|confirmApplyInstructorPreferencesToClass
argument_list|()
operator|+
literal|"')"
return|;
block|}
block|}
specifier|public
specifier|static
name|String
name|getCancelInheritInstructorPreferencesCondition
parameter_list|(
name|SessionContext
name|context
parameter_list|)
block|{
name|String
name|inheritInstrPref
init|=
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|InheritInstructorPrefs
argument_list|)
decl_stmt|;
if|if
condition|(
name|CommonValues
operator|.
name|Always
operator|.
name|eq
argument_list|(
name|inheritInstrPref
argument_list|)
condition|)
block|{
return|return
literal|"true"
return|;
block|}
if|else if
condition|(
name|CommonValues
operator|.
name|Never
operator|.
name|eq
argument_list|(
name|inheritInstrPref
argument_list|)
condition|)
block|{
return|return
literal|"false"
return|;
block|}
else|else
block|{
return|return
literal|"confirm('"
operator|+
name|MSG
operator|.
name|confirmRemoveInstructorPreferencesFromClass
argument_list|()
operator|+
literal|"')"
return|;
block|}
block|}
block|}
end_class

end_unit

