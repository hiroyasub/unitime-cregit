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
package|;
end_package

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|JProf
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CacheElement
parameter_list|<
name|T
parameter_list|>
block|{
specifier|private
name|T
name|iElement
decl_stmt|;
specifier|private
name|long
name|iCreated
decl_stmt|;
specifier|public
name|CacheElement
parameter_list|(
name|T
name|element
parameter_list|)
block|{
name|iElement
operator|=
name|element
expr_stmt|;
name|iCreated
operator|=
name|JProf
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
block|}
specifier|public
name|T
name|element
parameter_list|()
block|{
return|return
name|iElement
return|;
block|}
specifier|public
name|long
name|created
parameter_list|()
block|{
return|return
name|iCreated
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|o
operator|instanceof
name|CacheElement
operator|&&
operator|(
operator|(
name|CacheElement
argument_list|<
name|T
argument_list|>
operator|)
name|o
operator|)
operator|.
name|element
argument_list|()
operator|.
name|equals
argument_list|(
name|element
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|o
operator|.
name|equals
argument_list|(
name|element
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|element
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

