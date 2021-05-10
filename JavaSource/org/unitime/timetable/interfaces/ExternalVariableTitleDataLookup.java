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
name|interfaces
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|CourseCreditFormat
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
name|Session
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
name|SubjectArea
import|;
end_import

begin_interface
specifier|public
interface|interface
name|ExternalVariableTitleDataLookup
block|{
specifier|public
name|boolean
name|isVariableTitleCourse
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|,
name|String
name|courseNumber
parameter_list|,
name|Session
name|academicSession
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
function_decl|;
specifier|public
name|CourseCreditFormat
name|courseCreditFormatForCourse
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|,
name|String
name|courseNumber
parameter_list|,
name|Session
name|academicSession
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
function_decl|;
specifier|public
name|Float
name|minCreditForCourse
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|,
name|String
name|courseNumber
parameter_list|,
name|Session
name|academicSession
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
function_decl|;
specifier|public
name|Float
name|maxCreditForCourse
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|,
name|String
name|courseNumber
parameter_list|,
name|Session
name|academicSession
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
function_decl|;
specifier|public
name|TreeSet
argument_list|<
name|String
argument_list|>
name|listExistingTitlesForCourse
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|,
name|String
name|courseNumber
parameter_list|,
name|Session
name|academicSession
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
function_decl|;
block|}
end_interface

end_unit
