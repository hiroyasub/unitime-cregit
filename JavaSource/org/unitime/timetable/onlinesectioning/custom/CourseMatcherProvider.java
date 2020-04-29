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
name|custom
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|match
operator|.
name|CourseMatcher
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
comment|/**  * {@link CourseMatcher} provider interface. Such provider can be used to filter out what courses  * a student can see / select in the Scheduling Assistant.  */
end_comment

begin_interface
specifier|public
interface|interface
name|CourseMatcherProvider
block|{
comment|/** 	 * Create a course matcher instance 	 * @param context current session context (e.g., can be used to check permission, current user role etc.) 	 * @param studentId current student unique id 	 * @return course matcher instance, null if no additional filtering is to be made 	 */
specifier|public
name|CourseMatcher
name|getCourseMatcher
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Long
name|studentId
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

