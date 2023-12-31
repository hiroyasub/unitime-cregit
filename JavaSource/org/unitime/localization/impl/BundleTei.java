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
name|localization
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|TagData
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|TagExtraInfo
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|VariableInfo
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BundleTei
extends|extends
name|TagExtraInfo
block|{
specifier|public
name|VariableInfo
index|[]
name|getVariableInfo
parameter_list|(
name|TagData
name|data
parameter_list|)
block|{
name|String
name|name
init|=
name|data
operator|.
name|getAttributeString
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|String
name|id
init|=
name|data
operator|.
name|getAttributeString
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
try|try
block|{
name|Class
operator|.
name|forName
argument_list|(
name|Localization
operator|.
name|ROOT
operator|+
name|name
argument_list|)
expr_stmt|;
return|return
operator|new
name|VariableInfo
index|[]
block|{
operator|new
name|VariableInfo
argument_list|(
name|id
operator|==
literal|null
condition|?
name|BundleTag
operator|.
name|DEFAULT_ID
else|:
name|id
argument_list|,
name|Localization
operator|.
name|ROOT
operator|+
name|name
argument_list|,
literal|true
argument_list|,
name|VariableInfo
operator|.
name|NESTED
argument_list|)
block|}
return|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
return|return
operator|new
name|VariableInfo
index|[]
block|{
operator|new
name|VariableInfo
argument_list|(
name|id
operator|==
literal|null
condition|?
name|BundleTag
operator|.
name|DEFAULT_ID
else|:
name|id
argument_list|,
name|name
argument_list|,
literal|true
argument_list|,
name|VariableInfo
operator|.
name|NESTED
argument_list|)
block|}
return|;
block|}
block|}
block|}
end_class

end_unit

