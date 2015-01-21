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
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SubSet
parameter_list|<
name|T
extends|extends
name|Comparable
parameter_list|<
name|T
parameter_list|>
parameter_list|>
extends|extends
name|TreeSet
argument_list|<
name|T
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|int
name|iLimit
init|=
operator|-
literal|1
decl_stmt|;
specifier|public
name|SubSet
parameter_list|(
name|Integer
name|limit
parameter_list|,
name|Comparator
argument_list|<
name|T
argument_list|>
name|comparator
parameter_list|)
block|{
name|super
argument_list|(
name|comparator
argument_list|)
expr_stmt|;
name|iLimit
operator|=
operator|(
name|limit
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|limit
operator|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|add
parameter_list|(
name|T
name|e
parameter_list|)
block|{
if|if
condition|(
name|iLimit
operator|<=
literal|0
operator|||
name|size
argument_list|()
operator|<
name|iLimit
condition|)
block|{
return|return
name|super
operator|.
name|add
argument_list|(
name|e
argument_list|)
return|;
block|}
name|T
name|last
init|=
name|last
argument_list|()
decl_stmt|;
if|if
condition|(
name|comparator
argument_list|()
operator|.
name|compare
argument_list|(
name|last
argument_list|,
name|e
argument_list|)
operator|>
literal|0
condition|)
block|{
name|remove
argument_list|(
name|last
argument_list|)
expr_stmt|;
return|return
name|super
operator|.
name|add
argument_list|(
name|e
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|boolean
name|isLimitReached
parameter_list|()
block|{
return|return
operator|(
name|iLimit
operator|>
literal|0
operator|&&
name|size
argument_list|()
operator|>=
name|iLimit
operator|)
return|;
block|}
block|}
end_class

end_unit

