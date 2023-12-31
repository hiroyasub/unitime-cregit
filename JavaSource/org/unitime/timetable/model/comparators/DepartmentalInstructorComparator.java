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
name|DepartmentalInstructor
import|;
end_import

begin_comment
comment|/**  * Compares Staff based on specified criteria  * Defaults to compare by name  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|DepartmentalInstructorComparator
implements|implements
name|Comparator
block|{
specifier|public
specifier|static
specifier|final
name|short
name|COMPARE_BY_NAME
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|short
name|COMPARE_BY_POSITION
init|=
literal|2
decl_stmt|;
specifier|private
name|short
name|compareBy
decl_stmt|;
specifier|public
name|DepartmentalInstructorComparator
parameter_list|()
block|{
name|compareBy
operator|=
name|COMPARE_BY_NAME
expr_stmt|;
block|}
specifier|public
name|DepartmentalInstructorComparator
parameter_list|(
name|short
name|compareBy
parameter_list|)
block|{
if|if
condition|(
name|compareBy
operator|!=
name|COMPARE_BY_NAME
operator|&&
name|compareBy
operator|!=
name|COMPARE_BY_POSITION
condition|)
block|{
name|this
operator|.
name|compareBy
operator|=
name|COMPARE_BY_NAME
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|compareBy
operator|=
name|compareBy
expr_stmt|;
block|}
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
if|if
condition|(
operator|!
operator|(
name|o1
operator|instanceof
name|DepartmentalInstructor
operator|)
operator|||
name|o1
operator|==
literal|null
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"o1 Class must be of type DepartmentalInstructor and cannot be null"
argument_list|)
throw|;
if|if
condition|(
operator|!
operator|(
name|o2
operator|instanceof
name|DepartmentalInstructor
operator|)
operator|||
name|o2
operator|==
literal|null
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"o2 Class must be of type DepartmentalInstructor and cannot be null"
argument_list|)
throw|;
name|DepartmentalInstructor
name|s1
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|o1
decl_stmt|;
name|DepartmentalInstructor
name|s2
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|o2
decl_stmt|;
if|if
condition|(
name|compareBy
operator|==
name|COMPARE_BY_POSITION
condition|)
block|{
name|Integer
name|l1
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|s1
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
condition|)
name|l1
operator|=
name|s1
operator|.
name|getPositionType
argument_list|()
operator|.
name|getSortOrder
argument_list|()
expr_stmt|;
name|Integer
name|l2
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|s2
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
condition|)
name|l2
operator|=
name|s2
operator|.
name|getPositionType
argument_list|()
operator|.
name|getSortOrder
argument_list|()
expr_stmt|;
name|int
name|ret
init|=
name|l1
operator|.
name|compareTo
argument_list|(
name|l2
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|!=
literal|0
condition|)
return|return
name|ret
return|;
block|}
return|return
name|s1
operator|.
name|nameLastNameFirst
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|s2
operator|.
name|nameLastNameFirst
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

