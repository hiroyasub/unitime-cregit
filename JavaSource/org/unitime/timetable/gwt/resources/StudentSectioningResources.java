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
name|resources
package|;
end_package

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|resources
operator|.
name|client
operator|.
name|ClientBundle
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
name|resources
operator|.
name|client
operator|.
name|ImageResource
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|StudentSectioningResources
extends|extends
name|ClientBundle
block|{
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/up_Down.png"
argument_list|)
name|ImageResource
name|up_Down
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/up.png"
argument_list|)
name|ImageResource
name|up
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/up_Over.png"
argument_list|)
name|ImageResource
name|up_Over
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/down_Down.png"
argument_list|)
name|ImageResource
name|down_Down
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/down.png"
argument_list|)
name|ImageResource
name|down
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/down_Over.png"
argument_list|)
name|ImageResource
name|down_Over
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/search_picker_Disabled.png"
argument_list|)
name|ImageResource
name|search_picker_Disabled
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/search_picker_Assigned.png"
argument_list|)
name|ImageResource
name|search_picker_Assigned
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/search_picker_Down.png"
argument_list|)
name|ImageResource
name|search_picker_Down
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/search_picker_Normal.png"
argument_list|)
name|ImageResource
name|search_picker_Normal
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/search_picker_Over.png"
argument_list|)
name|ImageResource
name|search_picker_Over
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/search_picker.png"
argument_list|)
name|ImageResource
name|search_picker
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/roadrunner16.png"
argument_list|)
name|ImageResource
name|distantConflict
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/tick.png"
argument_list|)
name|ImageResource
name|saved
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/lock.png"
argument_list|)
name|ImageResource
name|locked
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/lock_unlock.png"
argument_list|)
name|ImageResource
name|unlocked
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/warning.png"
argument_list|)
name|ImageResource
name|courseLocked
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/cancel.png"
argument_list|)
name|ImageResource
name|cancelled
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/error.png"
argument_list|)
name|ImageResource
name|error
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/warning.png"
argument_list|)
name|ImageResource
name|warning
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/info16.png"
argument_list|)
name|ImageResource
name|info
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/printer.png"
argument_list|)
name|ImageResource
name|print
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/letter.png"
argument_list|)
name|ImageResource
name|email
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/date.png"
argument_list|)
name|ImageResource
name|calendar
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/comments.png"
argument_list|)
name|ImageResource
name|comments
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/group.png"
argument_list|)
name|ImageResource
name|highDemand
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_delete.png"
argument_list|)
name|ImageResource
name|unassignment
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_add.png"
argument_list|)
name|ImageResource
name|assignment
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/expand_node_btn.gif"
argument_list|)
name|ImageResource
name|treeClosed
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/collapse_node_btn.gif"
argument_list|)
name|ImageResource
name|treeOpen
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/loading_small.gif"
argument_list|)
name|ImageResource
name|loading_small
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/note.png"
argument_list|)
name|ImageResource
name|note
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/overlap.png"
argument_list|)
name|ImageResource
name|overlap
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/delete_Down.png"
argument_list|)
name|ImageResource
name|delete_Down
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/delete.png"
argument_list|)
name|ImageResource
name|delete
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/delete_Over.png"
argument_list|)
name|ImageResource
name|delete_Over
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/indent-top-line.png"
argument_list|)
name|ImageResource
name|indentTopLine
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/indent-middle-line.png"
argument_list|)
name|ImageResource
name|indentMiddleLine
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/indent-bottom-line.png"
argument_list|)
name|ImageResource
name|indentLastLine
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/indent-top-space.png"
argument_list|)
name|ImageResource
name|indentTopSpace
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/indent-blank-space.png"
argument_list|)
name|ImageResource
name|indentBlankSpace
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/star.png"
argument_list|)
name|ImageResource
name|activePlan
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/add.png"
argument_list|)
name|ImageResource
name|quickAddCourse
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/arrow_left.png"
argument_list|)
name|ImageResource
name|arrowBack
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/arrow_right.png"
argument_list|)
name|ImageResource
name|arrowForward
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/info.png"
argument_list|)
name|ImageResource
name|statusInfo
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/warn.png"
argument_list|)
name|ImageResource
name|statusWarning
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/alert.png"
argument_list|)
name|ImageResource
name|statusError
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/check.png"
argument_list|)
name|ImageResource
name|statusDone
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/finder_assigned.png"
argument_list|)
name|ImageResource
name|finderAssigned
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/filter_plus.png"
argument_list|)
name|ImageResource
name|filterAddAlternative
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/filter_swap.png"
argument_list|)
name|ImageResource
name|filterSwap
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/times.png"
argument_list|)
name|ImageResource
name|filterRemoveAlternative
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/recycle.png"
argument_list|)
name|ImageResource
name|filterActivate
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/user_business_boss.png"
argument_list|)
name|ImageResource
name|isInstructing
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/time.png"
argument_list|)
name|ImageResource
name|specRegCanNotSubmit
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check.png"
argument_list|)
name|ImageResource
name|specRegCanEnroll
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check.png"
argument_list|)
name|ImageResource
name|specRegApproved
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/time.png"
argument_list|)
name|ImageResource
name|specRegPending
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/attention.png"
argument_list|)
name|ImageResource
name|specRegCancelled
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/stop.png"
argument_list|)
name|ImageResource
name|specRegRejected
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/reply.png"
argument_list|)
name|ImageResource
name|specRegDraft
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/tick.png"
argument_list|)
name|ImageResource
name|specRegApplied
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check.png"
argument_list|)
name|ImageResource
name|requestsWaitList
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check.png"
argument_list|)
name|ImageResource
name|requestsCritical
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check_blue.png"
argument_list|)
name|ImageResource
name|requestsImportant
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_delete.png"
argument_list|)
name|ImageResource
name|requestsNotCritical
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/login.png"
argument_list|)
name|ImageResource
name|requestEnrolled
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check.png"
argument_list|)
name|ImageResource
name|requestSaved
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/stop.png"
argument_list|)
name|ImageResource
name|requestRejected
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/time.png"
argument_list|)
name|ImageResource
name|requestPending
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/attention.png"
argument_list|)
name|ImageResource
name|requestCancelled
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/attention.png"
argument_list|)
name|ImageResource
name|requestNeeded
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/stop_gray.png"
argument_list|)
name|ImageResource
name|requestNotNeeded
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check_gray.png"
argument_list|)
name|ImageResource
name|waitListNotActive
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/stop.png"
argument_list|)
name|ImageResource
name|requestError
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/accept.png"
argument_list|)
name|ImageResource
name|on
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/cross.png"
argument_list|)
name|ImageResource
name|off
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/star.png"
argument_list|)
name|ImageResource
name|degreePlanCritical
parameter_list|()
function_decl|;
annotation|@
name|Source
argument_list|(
literal|"org/unitime/timetable/gwt/resources/icons/action_check.png"
argument_list|)
name|ImageResource
name|courseEnrolled
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

