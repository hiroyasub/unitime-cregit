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
name|gwt
operator|.
name|client
operator|.
name|instructor
package|;
end_package

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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|Cookies
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|InstructorCookie
block|{
specifier|private
specifier|static
name|InstructorCookie
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iSortAttributesBy
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iSortInstructorsBy
init|=
literal|0
decl_stmt|;
specifier|private
name|InstructorCookie
parameter_list|()
block|{
try|try
block|{
name|String
name|cookie
init|=
name|Cookies
operator|.
name|getCookie
argument_list|(
literal|"UniTime:Instructor"
argument_list|)
decl_stmt|;
if|if
condition|(
name|cookie
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|params
init|=
name|cookie
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|iSortAttributesBy
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iSortInstructorsBy
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
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
specifier|public
specifier|static
name|InstructorCookie
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|InstructorCookie
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|private
name|void
name|save
parameter_list|()
block|{
name|String
name|cookie
init|=
name|iSortAttributesBy
operator|+
literal|"|"
operator|+
name|iSortInstructorsBy
decl_stmt|;
name|Date
name|expires
init|=
operator|new
name|Date
argument_list|(
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|604800000l
argument_list|)
decl_stmt|;
comment|// expires in 7 days
name|Cookies
operator|.
name|setCookie
argument_list|(
literal|"UniTime:Instructor"
argument_list|,
name|cookie
argument_list|,
name|expires
argument_list|)
expr_stmt|;
block|}
specifier|public
name|int
name|getSortAttributesBy
parameter_list|()
block|{
return|return
name|iSortAttributesBy
return|;
block|}
specifier|public
name|void
name|setSortAttributesBy
parameter_list|(
name|int
name|sortAttributesBy
parameter_list|)
block|{
name|iSortAttributesBy
operator|=
name|sortAttributesBy
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getSortInstructorsBy
parameter_list|()
block|{
return|return
name|iSortInstructorsBy
return|;
block|}
specifier|public
name|void
name|setSortInstructorsBy
parameter_list|(
name|int
name|sortInstructorsBy
parameter_list|)
block|{
name|iSortInstructorsBy
operator|=
name|sortInstructorsBy
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

