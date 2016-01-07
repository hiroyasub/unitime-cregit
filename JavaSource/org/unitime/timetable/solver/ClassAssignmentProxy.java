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
name|solver
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
operator|.
name|TimeBlock
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
name|Assignment
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
name|Class_
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
name|solver
operator|.
name|ui
operator|.
name|AssignmentPreferenceInfo
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|ClassAssignmentProxy
block|{
specifier|public
name|Assignment
name|getAssignment
parameter_list|(
name|Long
name|classId
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Assignment
name|getAssignment
parameter_list|(
name|Class_
name|clazz
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|AssignmentPreferenceInfo
name|getAssignmentInfo
parameter_list|(
name|Long
name|classId
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|AssignmentPreferenceInfo
name|getAssignmentInfo
parameter_list|(
name|Class_
name|clazz
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Hashtable
name|getAssignmentTable
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Hashtable
name|getAssignmentInfoTable
parameter_list|(
name|Collection
name|classesOrClassIds
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|boolean
name|hasConflicts
parameter_list|(
name|Long
name|offeringId
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Set
argument_list|<
name|Assignment
argument_list|>
name|getConflicts
parameter_list|(
name|Long
name|classId
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|public
name|Set
argument_list|<
name|TimeBlock
argument_list|>
name|getConflictingTimeBlocks
parameter_list|(
name|Long
name|classId
parameter_list|)
throws|throws
name|Exception
function_decl|;
block|}
end_interface

end_unit

