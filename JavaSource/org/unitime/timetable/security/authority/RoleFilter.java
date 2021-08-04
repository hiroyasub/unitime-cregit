begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|authority
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
name|security
operator|.
name|Qualifiable
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
name|UserAuthority
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoleFilter
implements|implements
name|OtherAuthority
block|{
specifier|private
name|String
name|iRole
decl_stmt|;
specifier|private
name|Qualifiable
index|[]
name|iFilter
decl_stmt|;
specifier|public
name|RoleFilter
parameter_list|(
name|String
name|role
parameter_list|,
name|Qualifiable
modifier|...
name|filter
parameter_list|)
block|{
name|iRole
operator|=
name|role
expr_stmt|;
name|iFilter
operator|=
name|filter
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isMatch
parameter_list|(
name|UserAuthority
name|authority
parameter_list|)
block|{
if|if
condition|(
name|iRole
operator|!=
literal|null
operator|&&
operator|!
name|iRole
operator|.
name|equals
argument_list|(
name|authority
operator|.
name|getRole
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Qualifiable
name|q
range|:
name|iFilter
control|)
if|if
condition|(
operator|!
name|authority
operator|.
name|hasQualifier
argument_list|(
name|q
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

