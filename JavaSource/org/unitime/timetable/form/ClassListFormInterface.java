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
name|ClassListFormInterface
extends|extends
name|InstructionalOfferingListFormInterface
block|{
specifier|public
name|String
index|[]
name|getSubjectAreaIds
parameter_list|()
function_decl|;
specifier|public
name|String
name|getCourseNbr
parameter_list|()
function_decl|;
specifier|public
name|String
name|getSortBy
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterManager
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterAssignedRoom
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterInstructor
parameter_list|()
function_decl|;
specifier|public
name|String
name|getFilterIType
parameter_list|()
function_decl|;
specifier|public
name|int
name|getFilterDayCode
parameter_list|()
function_decl|;
specifier|public
name|int
name|getFilterStartSlot
parameter_list|()
function_decl|;
specifier|public
name|int
name|getFilterLength
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|getSortByKeepSubparts
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|getShowCrossListedClasses
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isReturnAllControlClassesForSubjects
parameter_list|()
function_decl|;
specifier|public
name|void
name|setSortBy
parameter_list|(
name|String
name|sortBy
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterAssignedRoom
parameter_list|(
name|String
name|filterAssignedRoom
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterManager
parameter_list|(
name|String
name|filterManager
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterIType
parameter_list|(
name|String
name|filterIType
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterDayCode
parameter_list|(
name|int
name|filterDayCode
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterStartSlot
parameter_list|(
name|int
name|filterStartSlot
parameter_list|)
function_decl|;
specifier|public
name|void
name|setFilterLength
parameter_list|(
name|int
name|filterLength
parameter_list|)
function_decl|;
specifier|public
name|void
name|setSortByKeepSubparts
parameter_list|(
name|boolean
name|sortByKeepSubparts
parameter_list|)
function_decl|;
specifier|public
name|void
name|setShowCrossListedClasses
parameter_list|(
name|boolean
name|showCrossListedClasses
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|getIncludeCancelledClasses
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

