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
name|remote
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
name|FileFilter
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
name|SolverParameterGroup
operator|.
name|SolverType
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BackupFileFilter
implements|implements
name|FileFilter
block|{
specifier|public
specifier|static
name|String
name|sXmlExtension
init|=
literal|".backup.xml"
decl_stmt|;
specifier|private
name|SolverType
name|iType
decl_stmt|;
specifier|public
name|BackupFileFilter
parameter_list|(
name|SolverType
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
block|}
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
name|iType
operator|!=
literal|null
operator|&&
operator|!
name|file
operator|.
name|getName
argument_list|()
operator|.
name|startsWith
argument_list|(
name|iType
operator|.
name|getPrefix
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|file
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
name|sXmlExtension
argument_list|)
return|;
block|}
specifier|public
name|String
name|getUser
parameter_list|(
name|File
name|file
parameter_list|)
block|{
if|if
condition|(
name|accept
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|iType
operator|!=
literal|null
condition|)
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
name|iType
operator|.
name|getPrefix
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|name
operator|.
name|endsWith
argument_list|(
name|sXmlExtension
argument_list|)
condition|)
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|name
operator|.
name|length
argument_list|()
operator|-
name|sXmlExtension
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|name
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

