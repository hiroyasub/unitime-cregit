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
name|action
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMessages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
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
name|defaults
operator|.
name|ApplicationProperty
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
name|defaults
operator|.
name|CommonValues
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
name|defaults
operator|.
name|UserProperty
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
name|form
operator|.
name|DistributionPrefsForm
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
name|form
operator|.
name|SchedulingSubpartEditForm
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
name|ChangeLog
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
name|model
operator|.
name|CourseCreditUnitConfig
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
name|CourseOffering
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
name|DatePattern
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
name|DistributionPref
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
name|InstrOfferingConfig
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
name|InstructionalOffering
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
name|PreferenceLevel
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
name|SchedulingSubpart
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
name|TimePattern
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
name|dao
operator|.
name|Class_DAO
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
name|dao
operator|.
name|SchedulingSubpartDAO
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
name|rights
operator|.
name|Right
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
name|util
operator|.
name|LookupTables
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
name|webutil
operator|.
name|BackTracker
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
name|webutil
operator|.
name|DistributionPrefsTableBuilder
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts  * Creation date: 03-24-2006  *  * XDoclet definition:  * @struts.action path="/schedulingSubpartDetail" name="schedulingSubpartEditForm" input="schedulingSubpartDetailTile" scope="request" validate="true"  * @struts.action-forward name="instructionalOfferingSearch" path="/instructionalOfferingSearch.do"  * @struts.action-forward name="displaySchedulingSubpart" path="schedulingSubpartDetailTile"  * @struts.action-forward name="addDistributionPrefs" path="/distributionPrefs.do"  *  * @author Tomas Muller, Zuzana Mullerova  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/schedulingSubpartDetail"
argument_list|)
specifier|public
class|class
name|SchedulingSubpartDetailAction
extends|extends
name|PreferencesAction
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/** 	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
comment|// Set common lookup tables
name|super
operator|.
name|execute
argument_list|(
name|mapping
argument_list|,
name|form
argument_list|,
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|SchedulingSubpartEditForm
name|frm
init|=
operator|(
name|SchedulingSubpartEditForm
operator|)
name|form
decl_stmt|;
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
comment|// Read parameters
name|String
name|subpartId
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"ssuid"
argument_list|)
operator|==
literal|null
condition|?
name|request
operator|.
name|getAttribute
argument_list|(
literal|"ssuid"
argument_list|)
operator|!=
literal|null
condition|?
name|request
operator|.
name|getAttribute
argument_list|(
literal|"ssuid"
argument_list|)
operator|.
name|toString
argument_list|()
else|:
literal|null
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"ssuid"
argument_list|)
decl_stmt|;
name|String
name|op
init|=
name|frm
operator|.
name|getOp
argument_list|()
decl_stmt|;
comment|// Check op exists
if|if
condition|(
name|op
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|errorNullOperationNotSupported
argument_list|()
argument_list|)
throw|;
comment|// Read subpart id from form
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionEditSubpart
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionAddDistributionPreference
argument_list|()
argument_list|)
comment|// || op.equals(rsc.getMessage("button.backToInstrOffrDet")) for deletion
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionNextSubpart
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionPreviousSubpart
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionClearClassPreferencesOnSubpart
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionEditSubpartInstructorAssignmentPreferences
argument_list|()
argument_list|)
condition|)
block|{
name|subpartId
operator|=
name|frm
operator|.
name|getSchedulingSubpartId
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|frm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
name|Debug
operator|.
name|debug
argument_list|(
literal|"op: "
operator|+
name|op
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"subpart: "
operator|+
name|subpartId
argument_list|)
expr_stmt|;
comment|// Check subpart exists
if|if
condition|(
name|subpartId
operator|==
literal|null
operator|||
name|subpartId
operator|.
name|trim
argument_list|()
operator|==
literal|""
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|errorSubpartInfoNotSupplied
argument_list|()
argument_list|)
throw|;
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|subpartId
argument_list|,
literal|"SchedulingSubpart"
argument_list|,
name|Right
operator|.
name|SchedulingSubpartDetail
argument_list|)
expr_stmt|;
name|boolean
name|timeVertical
init|=
name|CommonValues
operator|.
name|VerticalGrid
operator|.
name|eq
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|GridOrientation
argument_list|)
argument_list|)
decl_stmt|;
comment|// If subpart id is not null - load subpart info
name|SchedulingSubpartDAO
name|sdao
init|=
operator|new
name|SchedulingSubpartDAO
argument_list|()
decl_stmt|;
name|SchedulingSubpart
name|ss
init|=
name|sdao
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subpartId
argument_list|)
argument_list|)
decl_stmt|;
comment|// Edit Preference - Redirect to prefs edit screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionEditSubpart
argument_list|()
argument_list|)
operator|&&
name|subpartId
operator|!=
literal|null
operator|&&
name|subpartId
operator|.
name|trim
argument_list|()
operator|!=
literal|""
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"schedulingSubpartEdit.do?ssuid="
operator|+
name|ss
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionEditSubpartInstructorAssignmentPreferences
argument_list|()
argument_list|)
operator|&&
name|subpartId
operator|!=
literal|null
operator|&&
name|subpartId
operator|.
name|trim
argument_list|()
operator|!=
literal|""
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"schedulingSubpartInstrAssgnEdit.do?ssuid="
operator|+
name|ss
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Add Distribution Preference - Redirect to dist prefs screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionAddDistributionPreference
argument_list|()
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|ss
argument_list|,
name|Right
operator|.
name|DistributionPreferenceSubpart
argument_list|)
expr_stmt|;
name|CourseOffering
name|cco
init|=
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|cco
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"schedSubpartId"
argument_list|,
name|subpartId
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"courseOffrId"
argument_list|,
name|cco
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"classId"
argument_list|,
name|DistributionPrefsForm
operator|.
name|ALL_CLASSES_SELECT
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"addDistributionPrefs"
argument_list|)
return|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionNextSubpart
argument_list|()
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"schedulingSubpartDetail.do?ssuid="
operator|+
name|frm
operator|.
name|getNextId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionClearClassPreferencesOnSubpart
argument_list|()
argument_list|)
operator|&&
literal|"y"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"confirm"
argument_list|)
argument_list|)
condition|)
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|ss
argument_list|,
name|Right
operator|.
name|SchedulingSubpartDetailClearClassPreferences
argument_list|)
expr_stmt|;
name|Class_DAO
name|cdao
init|=
operator|new
name|Class_DAO
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Class_
name|c
init|=
operator|(
name|Class_
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|c
operator|.
name|getPreferences
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|cdao
operator|.
name|saveOrUpdate
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
name|ChangeLog
operator|.
name|addChange
argument_list|(
literal|null
argument_list|,
name|sessionContext
argument_list|,
name|ss
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|SCHEDULING_SUBPART_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|CLEAR_ALL_PREF
argument_list|,
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
argument_list|,
name|ss
operator|.
name|getManagingDept
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionPreviousSubpart
argument_list|()
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"schedulingSubpartDetail.do?ssuid="
operator|+
name|frm
operator|.
name|getPreviousId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Load form attributes that are constant
name|doLoad
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|ss
argument_list|,
name|subpartId
argument_list|)
expr_stmt|;
comment|// Initialize Preferences for initial load
name|Set
name|timePatterns
init|=
literal|null
decl_stmt|;
name|frm
operator|.
name|setAvailableTimePatterns
argument_list|(
name|TimePattern
operator|.
name|findApplicable
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|ss
operator|.
name|getMinutesPerWk
argument_list|()
argument_list|,
name|ss
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|,
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getDurationModel
argument_list|()
argument_list|,
literal|false
argument_list|,
name|ss
operator|.
name|getManagingDept
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|initPrefs
argument_list|(
name|frm
argument_list|,
name|ss
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|timePatterns
operator|=
name|ss
operator|.
name|getTimePatterns
argument_list|()
expr_stmt|;
comment|// Display distribution Prefs
name|DistributionPrefsTableBuilder
name|tbl
init|=
operator|new
name|DistributionPrefsTableBuilder
argument_list|()
decl_stmt|;
name|String
name|html
init|=
name|tbl
operator|.
name|getDistPrefsTableForSchedulingSubpart
argument_list|(
name|request
argument_list|,
name|sessionContext
argument_list|,
name|ss
argument_list|)
decl_stmt|;
if|if
condition|(
name|html
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|DistributionPref
operator|.
name|DIST_PREF_REQUEST_ATTR
argument_list|,
name|html
argument_list|)
expr_stmt|;
comment|// Process Preferences Action
name|processPrefAction
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|errors
argument_list|)
expr_stmt|;
name|setupDatePatterns
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|ss
argument_list|)
expr_stmt|;
comment|// Generate Time Pattern Grids
name|super
operator|.
name|generateTimePatternGrids
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|ss
argument_list|,
name|ss
operator|.
name|getMinutesPerWk
argument_list|()
argument_list|,
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getDurationModel
argument_list|()
argument_list|,
name|ss
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|,
name|timePatterns
argument_list|,
literal|"init"
argument_list|,
name|timeVertical
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupDatePatterns
argument_list|(
name|request
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|MSG
operator|.
name|dropDefaultDatePattern
argument_list|()
argument_list|,
name|ss
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultDatePatternNotNull
argument_list|()
argument_list|,
name|ss
operator|.
name|getManagingDept
argument_list|()
argument_list|,
name|ss
operator|.
name|effectiveDatePattern
argument_list|()
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupRooms
argument_list|(
name|request
argument_list|,
name|ss
argument_list|)
expr_stmt|;
comment|// Room Prefs
name|LookupTables
operator|.
name|setupBldgs
argument_list|(
name|request
argument_list|,
name|ss
argument_list|)
expr_stmt|;
comment|// Building Prefs
name|LookupTables
operator|.
name|setupRoomFeatures
argument_list|(
name|request
argument_list|,
name|ss
argument_list|)
expr_stmt|;
comment|// Preference Levels
name|LookupTables
operator|.
name|setupRoomGroups
argument_list|(
name|request
argument_list|,
name|ss
argument_list|)
expr_stmt|;
comment|// Room Groups
name|LookupTables
operator|.
name|setupInstructorAttributes
argument_list|(
name|request
argument_list|,
name|ss
argument_list|)
expr_stmt|;
comment|// Instructor Attributes
name|LookupTables
operator|.
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|sessionContext
argument_list|,
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"schedulingSubpartDetail.do?ssuid="
operator|+
name|frm
operator|.
name|getSchedulingSubpartId
argument_list|()
argument_list|,
name|MSG
operator|.
name|backSubpart
argument_list|(
name|ss
operator|.
name|getSchedulingSubpartLabel
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displaySchedulingSubpart"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	     * Loads the non-editable scheduling subpart info into the form 	     * @param request 	     * @param frm 	     * @param ss 	     * @param subpartId 	     */
specifier|private
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SchedulingSubpartEditForm
name|frm
parameter_list|,
name|SchedulingSubpart
name|ss
parameter_list|,
name|String
name|subpartId
parameter_list|)
block|{
name|CourseOffering
name|co
init|=
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
decl_stmt|;
comment|// Set Session Variables
name|InstructionalOfferingSearchAction
operator|.
name|setLastInstructionalOffering
argument_list|(
name|sessionContext
argument_list|,
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
argument_list|)
expr_stmt|;
comment|// populate form
name|InstrOfferingConfig
name|ioc
init|=
name|ss
operator|.
name|getInstrOfferingConfig
argument_list|()
decl_stmt|;
name|InstructionalOffering
name|io
init|=
name|ioc
operator|.
name|getInstructionalOffering
argument_list|()
decl_stmt|;
name|frm
operator|.
name|setInstrOfferingId
argument_list|(
name|io
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSchedulingSubpartId
argument_list|(
name|subpartId
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setInstructionalType
argument_list|(
name|ss
operator|.
name|getItype
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|label
init|=
name|ss
operator|.
name|getItype
argument_list|()
operator|.
name|getAbbv
argument_list|()
decl_stmt|;
if|if
condition|(
name|io
operator|.
name|hasMultipleConfigurations
argument_list|()
condition|)
name|label
operator|+=
literal|" ["
operator|+
name|ioc
operator|.
name|getName
argument_list|()
operator|+
literal|"]"
expr_stmt|;
name|frm
operator|.
name|setInstructionalTypeLabel
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setUnlimitedEnroll
argument_list|(
name|ioc
operator|.
name|isUnlimitedEnrollment
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setItypeBasic
argument_list|(
name|ss
operator|.
name|getItype
argument_list|()
operator|==
literal|null
operator|||
name|ss
operator|.
name|getItype
argument_list|()
operator|.
name|getBasic
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|frm
operator|.
name|getItypeBasic
argument_list|()
condition|)
name|LookupTables
operator|.
name|setupItypes
argument_list|(
name|request
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSubjectArea
argument_list|(
name|co
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSubjectAreaId
argument_list|(
name|co
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCourseNbr
argument_list|(
name|co
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCourseTitle
argument_list|(
name|co
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setAutoSpreadInTime
argument_list|(
name|ss
operator|.
name|isAutoSpreadInTime
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setStudentAllowOverlap
argument_list|(
name|ss
operator|.
name|isStudentAllowOverlap
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDatePattern
argument_list|(
name|ss
operator|.
name|getDatePattern
argument_list|()
operator|==
literal|null
condition|?
name|Long
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|ss
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDatePatternEditable
argument_list|(
name|ApplicationProperty
operator|.
name|WaitListCanChangeDatePattern
operator|.
name|isTrue
argument_list|()
operator|||
name|ioc
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|0
operator|||
operator|!
name|io
operator|.
name|effectiveWaitList
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getCreditText
argument_list|()
operator|==
literal|null
operator|||
name|frm
operator|.
name|getCreditText
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
if|if
condition|(
name|ss
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|CourseCreditUnitConfig
name|credit
init|=
name|ss
operator|.
name|getCredit
argument_list|()
decl_stmt|;
name|frm
operator|.
name|setCreditText
argument_list|(
name|credit
operator|.
name|creditText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ss
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
operator|&&
name|ss
operator|.
name|getItype
argument_list|()
operator|.
name|equals
argument_list|(
name|ss
operator|.
name|getParentSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
argument_list|)
condition|)
block|{
name|frm
operator|.
name|setSameItypeAsParent
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frm
operator|.
name|setSameItypeAsParent
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|SchedulingSubpart
name|next
init|=
name|ss
operator|.
name|getNextSchedulingSubpart
argument_list|(
name|sessionContext
argument_list|,
name|Right
operator|.
name|SchedulingSubpartDetail
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setNextId
argument_list|(
name|next
operator|==
literal|null
condition|?
literal|null
else|:
name|next
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|SchedulingSubpart
name|previous
init|=
name|ss
operator|.
name|getPreviousSchedulingSubpart
argument_list|(
name|sessionContext
argument_list|,
name|Right
operator|.
name|SchedulingSubpartDetail
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setPreviousId
argument_list|(
name|previous
operator|==
literal|null
condition|?
literal|null
else|:
name|previous
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|// Set Parent Subpart
name|String
name|parentSubpart
init|=
literal|""
decl_stmt|;
name|SchedulingSubpart
name|parentSS
init|=
name|ss
operator|.
name|getParentSubpart
argument_list|()
decl_stmt|;
name|frm
operator|.
name|setParentSubpartId
argument_list|(
name|parentSS
operator|==
literal|null
operator|||
operator|!
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|parentSS
argument_list|,
name|Right
operator|.
name|SchedulingSubpartDetail
argument_list|)
condition|?
literal|null
else|:
name|parentSS
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setParentSubpartLabel
argument_list|(
name|parentSS
operator|==
literal|null
condition|?
literal|null
else|:
name|parentSS
operator|.
name|getSchedulingSubpartLabel
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
name|parentSS
operator|!=
literal|null
condition|)
block|{
name|parentSubpart
operator|=
name|parentSS
operator|.
name|getItype
argument_list|()
operator|.
name|getAbbv
argument_list|()
operator|+
literal|" - "
operator|+
name|parentSubpart
expr_stmt|;
name|parentSS
operator|=
name|parentSS
operator|.
name|getParentSubpart
argument_list|()
expr_stmt|;
block|}
name|frm
operator|.
name|setParentSubpart
argument_list|(
name|parentSubpart
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setManagingDeptName
argument_list|(
name|ss
operator|.
name|getManagingDept
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|ss
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getManagingDeptLabel
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setControllingDept
argument_list|(
name|ss
operator|.
name|getControllingDept
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|setupDatePatterns
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|SchedulingSubpartEditForm
name|frm
parameter_list|,
name|SchedulingSubpart
name|ss
parameter_list|)
throws|throws
name|Exception
block|{
name|DatePattern
name|selectedDatePattern
init|=
name|ss
operator|.
name|effectiveDatePattern
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedDatePattern
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|DatePattern
argument_list|>
name|children
init|=
name|selectedDatePattern
operator|.
name|findChildren
argument_list|()
decl_stmt|;
for|for
control|(
name|DatePattern
name|dp
range|:
name|children
control|)
block|{
if|if
condition|(
operator|!
name|frm
operator|.
name|getDatePatternPrefs
argument_list|()
operator|.
name|contains
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
name|frm
operator|.
name|addToDatePatternPrefs
argument_list|(
name|dp
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|PreferenceLevel
operator|.
name|PREF_LEVEL_NEUTRAL
argument_list|)
expr_stmt|;
block|}
block|}
name|frm
operator|.
name|sortDatePatternPrefs
argument_list|(
name|frm
operator|.
name|getDatePatternPrefs
argument_list|()
argument_list|,
name|frm
operator|.
name|getDatePatternPrefLevels
argument_list|()
argument_list|,
name|children
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

