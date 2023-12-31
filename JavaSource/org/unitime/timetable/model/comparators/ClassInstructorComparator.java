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
operator|.
name|comparators
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|ClassInstructor
import|;
end_import

begin_class
specifier|public
class|class
name|ClassInstructorComparator
implements|implements
name|Comparator
block|{
specifier|private
name|ClassComparator
name|iCC
init|=
literal|null
decl_stmt|;
specifier|public
name|ClassInstructorComparator
parameter_list|(
name|ClassComparator
name|cc
parameter_list|)
block|{
name|iCC
operator|=
name|cc
expr_stmt|;
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|ClassInstructor
name|ci1
init|=
operator|(
name|ClassInstructor
operator|)
name|o1
decl_stmt|;
name|ClassInstructor
name|ci2
init|=
operator|(
name|ClassInstructor
operator|)
name|o2
decl_stmt|;
return|return
name|iCC
operator|.
name|compare
argument_list|(
name|ci1
operator|.
name|getClassInstructing
argument_list|()
argument_list|,
name|ci2
operator|.
name|getClassInstructing
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

