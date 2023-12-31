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
name|form
package|;
end_package

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|InstructionalOfferingListFormInterface
block|{
specifier|public
name|Boolean
name|getDivSec
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getDemand
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getProjectedDemand
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getMinPerWk
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getLimit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getSnapshotLimit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getRoomLimit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getManager
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getDatePattern
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getTimePattern
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getPreferences
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getInstructor
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getTimetable
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getCredit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getSubpartCredit
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getSchedulePrintNote
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getNote
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getConsent
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getTitle
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getExams
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getInstructorAssignment
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getLms
parameter_list|()
function_decl|;
specifier|public
name|String
name|getWaitlist
parameter_list|()
function_decl|;
specifier|public
name|String
name|getSortBy
parameter_list|()
function_decl|;
specifier|public
name|Boolean
name|getFundingDepartment
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

