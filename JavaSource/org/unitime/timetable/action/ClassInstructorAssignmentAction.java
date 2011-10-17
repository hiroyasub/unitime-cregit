begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
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
name|Action
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
name|apache
operator|.
name|struts
operator|.
name|util
operator|.
name|MessageResources
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
name|User
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
name|web
operator|.
name|Web
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|ApplicationProperties
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
name|ClassInstructorAssignmentForm
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
name|ExternalInstrOfferingConfigAssignInstructorsAction
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
name|DepartmentalInstructor
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
name|Settings
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
name|UserData
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
name|comparators
operator|.
name|ClassComparator
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
name|comparators
operator|.
name|ClassCourseComparator
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
name|comparators
operator|.
name|DepartmentalInstructorComparator
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
name|comparators
operator|.
name|SchedulingSubpartComparator
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
name|InstrOfferingConfigDAO
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
name|WebSolver
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
name|Constants
import|;
end_import

begin_class
specifier|public
class|class
name|ClassInstructorAssignmentAction
extends|extends
name|Action
block|{
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Method execute      * @param mapping      * @param form      * @param request      * @param response      * @return ActionForward      */
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
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|exceptionAccessDenied
argument_list|()
argument_list|)
throw|;
block|}
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|ClassInstructorAssignmentForm
name|frm
init|=
operator|(
name|ClassInstructorAssignmentForm
operator|)
name|form
decl_stmt|;
comment|// Get operation
name|String
name|op
init|=
operator|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
operator|==
literal|null
operator|)
condition|?
operator|(
name|frm
operator|.
name|getOp
argument_list|()
operator|==
literal|null
operator|||
name|frm
operator|.
name|getOp
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|?
operator|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"op"
argument_list|)
operator|==
literal|null
operator|)
condition|?
literal|null
else|:
name|request
operator|.
name|getAttribute
argument_list|(
literal|"op"
argument_list|)
operator|.
name|toString
argument_list|()
else|:
name|frm
operator|.
name|getOp
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
name|op
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"hdnOp"
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
operator|||
name|op
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|exceptionOperationNotInterpreted
argument_list|()
operator|+
name|op
argument_list|)
throw|;
comment|// Instructional Offering Config Id
name|String
name|instrOffrConfigId
init|=
literal|""
decl_stmt|;
comment|// Set the operation
name|frm
operator|.
name|setOp
argument_list|(
name|op
argument_list|)
expr_stmt|;
comment|// Set the proxy so we can get the class time and room
name|frm
operator|.
name|setProxy
argument_list|(
name|WebSolver
operator|.
name|getClassAssignmentProxy
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|instrOffrConfigId
operator|=
operator|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"uid"
argument_list|)
operator|==
literal|null
operator|)
condition|?
operator|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"uid"
argument_list|)
operator|==
literal|null
operator|)
condition|?
name|frm
operator|.
name|getInstrOffrConfigId
argument_list|()
operator|!=
literal|null
condition|?
name|frm
operator|.
name|getInstrOffrConfigId
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
else|:
name|request
operator|.
name|getAttribute
argument_list|(
literal|"uid"
argument_list|)
operator|.
name|toString
argument_list|()
else|:
name|request
operator|.
name|getParameter
argument_list|(
literal|"uid"
argument_list|)
expr_stmt|;
name|InstrOfferingConfigDAO
name|iocDao
init|=
operator|new
name|InstrOfferingConfigDAO
argument_list|()
decl_stmt|;
name|InstrOfferingConfig
name|ioc
init|=
name|iocDao
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|instrOffrConfigId
argument_list|)
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setInstrOffrConfigId
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|instrOffrConfigId
argument_list|)
argument_list|)
expr_stmt|;
name|ArrayList
name|instructors
init|=
operator|new
name|ArrayList
argument_list|(
name|ioc
operator|.
name|getDepartment
argument_list|()
operator|.
name|getInstructors
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|instructors
argument_list|,
operator|new
name|DepartmentalInstructorComparator
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|DepartmentalInstructor
operator|.
name|INSTR_LIST_ATTR_NAME
argument_list|,
name|instructors
argument_list|)
expr_stmt|;
comment|// First access to screen
name|Boolean
name|areEqual
init|=
name|op
operator|.
name|equalsIgnoreCase
argument_list|(
name|MSG
operator|.
name|actionAssignInstructors
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|op
operator|.
name|equalsIgnoreCase
argument_list|(
name|MSG
operator|.
name|actionAssignInstructors
argument_list|()
argument_list|)
condition|)
block|{
name|doLoad
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|instrOffrConfigId
argument_list|,
name|user
argument_list|,
name|ioc
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
name|actionUpdateClassInstructorsAssignment
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionNextIO
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionPreviousIO
argument_list|()
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionUnassignAllInstructorsFromConfig
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionUnassignAllInstructorsFromConfig
argument_list|()
argument_list|)
condition|)
block|{
name|frm
operator|.
name|unassignAllInstructors
argument_list|()
expr_stmt|;
block|}
comment|// Validate input prefs
name|ActionMessages
name|errors
init|=
name|frm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
comment|// No errors - Update class
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
try|try
block|{
name|frm
operator|.
name|updateClasses
argument_list|()
expr_stmt|;
name|InstrOfferingConfig
name|cfg
init|=
operator|new
name|InstrOfferingConfigDAO
argument_list|()
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getInstrOffrConfigId
argument_list|()
argument_list|)
decl_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
literal|null
argument_list|,
name|request
argument_list|,
name|cfg
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|CLASS_INSTR_ASSIGN
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
name|cfg
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
literal|null
argument_list|)
expr_stmt|;
name|String
name|className
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.external.instr_offr_config.assign_instructors_action.class"
argument_list|)
decl_stmt|;
if|if
condition|(
name|className
operator|!=
literal|null
operator|&&
name|className
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|ExternalInstrOfferingConfigAssignInstructorsAction
name|assignAction
init|=
operator|(
name|ExternalInstrOfferingConfigAssignInstructorsAction
operator|)
operator|(
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
operator|)
decl_stmt|;
name|assignAction
operator|.
name|performExternalInstrOfferingConfigAssignInstructorsAction
argument_list|(
name|ioc
argument_list|,
name|InstrOfferingConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"io"
argument_list|,
name|frm
operator|.
name|getInstrOfferingId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|MSG
operator|.
name|actionNextIO
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
literal|"classInstructorAssignment.do?uid="
operator|+
name|frm
operator|.
name|getNextId
argument_list|()
operator|+
literal|"&op="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|MSG
operator|.
name|actionAssignInstructors
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
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
name|actionPreviousIO
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
literal|"classInstructorAssignment.do?uid="
operator|+
name|frm
operator|.
name|getPreviousId
argument_list|()
operator|+
literal|"&op="
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|MSG
operator|.
name|actionAssignInstructors
argument_list|()
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"op"
argument_list|,
literal|"view"
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"instructionalOfferingDetail"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
block|}
else|else
block|{
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.delete"
argument_list|)
argument_list|)
condition|)
block|{
name|frm
operator|.
name|deleteInstructor
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addInstructor"
argument_list|)
argument_list|)
condition|)
block|{
name|frm
operator|.
name|addInstructor
argument_list|()
expr_stmt|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"classInstructorAssignment"
argument_list|)
return|;
block|}
comment|/**      * Loads the form with the classes that are part of the instructional offering config      * @param frm Form object      * @param instrCoffrConfigId Instructional Offering Config Id      * @param user User object      */
specifier|private
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ClassInstructorAssignmentForm
name|frm
parameter_list|,
name|String
name|instrOffrConfigId
parameter_list|,
name|User
name|user
parameter_list|,
name|InstrOfferingConfig
name|ioc
parameter_list|)
throws|throws
name|Exception
block|{
name|HttpSession
name|session
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
comment|// Check uniqueid
if|if
condition|(
name|instrOffrConfigId
operator|==
literal|null
operator|||
name|instrOffrConfigId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|exceptionMissingIOConfig
argument_list|()
argument_list|)
throw|;
comment|// Load details
name|InstructionalOffering
name|io
init|=
name|ioc
operator|.
name|getInstructionalOffering
argument_list|()
decl_stmt|;
comment|// Load form properties
name|frm
operator|.
name|setInstrOffrConfigId
argument_list|(
name|ioc
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setInstrOffrConfigLimit
argument_list|(
name|ioc
operator|.
name|getLimit
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setInstrOfferingId
argument_list|(
name|io
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|displayExternalIds
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.class_setup.show_display_external_ids"
argument_list|,
literal|"false"
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setDisplayExternalId
argument_list|(
operator|new
name|Boolean
argument_list|(
name|displayExternalIds
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|name
init|=
name|io
operator|.
name|getCourseNameWithTitle
argument_list|()
decl_stmt|;
if|if
condition|(
name|io
operator|.
name|hasMultipleConfigurations
argument_list|()
condition|)
block|{
name|name
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
block|}
name|frm
operator|.
name|setInstrOfferingName
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|ioc
operator|.
name|getSchedulingSubparts
argument_list|()
operator|==
literal|null
operator|||
name|ioc
operator|.
name|getSchedulingSubparts
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|exceptionIOConfigUndefined
argument_list|()
argument_list|)
throw|;
name|InstrOfferingConfig
name|config
init|=
name|ioc
operator|.
name|getNextInstrOfferingConfig
argument_list|(
name|session
argument_list|,
name|user
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setNextId
argument_list|(
name|config
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frm
operator|.
name|setNextId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|config
operator|=
name|ioc
operator|.
name|getPreviousInstrOfferingConfig
argument_list|(
name|session
argument_list|,
name|user
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|config
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setPreviousId
argument_list|(
name|config
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frm
operator|.
name|setPreviousId
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|ArrayList
name|subpartList
init|=
operator|new
name|ArrayList
argument_list|(
name|ioc
operator|.
name|getSchedulingSubparts
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|subpartList
argument_list|,
operator|new
name|SchedulingSubpartComparator
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|subpartList
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SchedulingSubpart
name|ss
init|=
operator|(
name|SchedulingSubpart
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|ss
operator|.
name|getClasses
argument_list|()
operator|==
literal|null
operator|||
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
name|MSG
operator|.
name|exceptionInitialIOSetupIncomplete
argument_list|()
argument_list|)
throw|;
if|if
condition|(
name|ss
operator|.
name|getParentSubpart
argument_list|()
operator|==
literal|null
condition|)
block|{
name|loadClasses
argument_list|(
name|frm
argument_list|,
name|user
argument_list|,
name|ss
operator|.
name|getClasses
argument_list|()
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|,
operator|new
name|String
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|private
name|void
name|loadClasses
parameter_list|(
name|ClassInstructorAssignmentForm
name|frm
parameter_list|,
name|User
name|user
parameter_list|,
name|Set
name|classes
parameter_list|,
name|Boolean
name|isReadOnly
parameter_list|,
name|String
name|indent
parameter_list|)
block|{
if|if
condition|(
name|classes
operator|!=
literal|null
operator|&&
name|classes
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|ArrayList
name|classesList
init|=
operator|new
name|ArrayList
argument_list|(
name|classes
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"yes"
operator|.
name|equals
argument_list|(
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_KEEP_SORT
argument_list|)
argument_list|)
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|classesList
argument_list|,
operator|new
name|ClassCourseComparator
argument_list|(
name|UserData
operator|.
name|getProperty
argument_list|(
name|user
operator|.
name|getId
argument_list|()
argument_list|,
literal|"InstructionalOfferingList.sortBy"
argument_list|,
name|ClassCourseComparator
operator|.
name|getName
argument_list|(
name|ClassCourseComparator
operator|.
name|SortBy
operator|.
name|NAME
argument_list|)
argument_list|)
argument_list|,
name|frm
operator|.
name|getProxy
argument_list|()
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|classesList
argument_list|,
operator|new
name|ClassComparator
argument_list|(
name|ClassComparator
operator|.
name|COMPARE_BY_ITYPE
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Boolean
name|readOnlyClass
init|=
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|Class_
name|cls
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|classesList
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|cls
operator|=
operator|(
name|Class_
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|isReadOnly
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|readOnlyClass
operator|=
operator|new
name|Boolean
argument_list|(
name|isReadOnly
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|readOnlyClass
operator|=
operator|new
name|Boolean
argument_list|(
operator|!
name|cls
operator|.
name|isLimitedEditable
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|frm
operator|.
name|addToClasses
argument_list|(
name|cls
argument_list|,
name|readOnlyClass
argument_list|,
name|indent
argument_list|)
expr_stmt|;
name|loadClasses
argument_list|(
name|frm
argument_list|,
name|user
argument_list|,
name|cls
operator|.
name|getChildClasses
argument_list|()
argument_list|,
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|,
name|indent
operator|+
literal|"&nbsp;&nbsp;&nbsp;&nbsp;"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

