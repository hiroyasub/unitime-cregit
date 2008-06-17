begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|ActionMessage
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
name|hibernate
operator|.
name|Query
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Transaction
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
name|timetable
operator|.
name|form
operator|.
name|DesignatorEditForm
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
name|Designator
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
name|SubjectArea
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
name|TimetableManager
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
name|DepartmentalInstructorDAO
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
name|DesignatorDAO
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
name|SubjectAreaDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-26-2006  *   * XDoclet definition:  * @struts:action path="/designatorEdit" name="designatorEditForm" input="/user/designatorEdit.jsp" scope="request"  */
end_comment

begin_class
specifier|public
class|class
name|DesignatorEditAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**       * Method execute      * @param mapping      * @param form      * @param request      * @param response      * @return ActionForward      */
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
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|httpSession
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
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
name|DesignatorEditForm
name|frm
init|=
operator|(
name|DesignatorEditForm
operator|)
name|form
decl_stmt|;
name|ActionMessages
name|errors
init|=
literal|null
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
literal|"Invalid operation"
argument_list|)
throw|;
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
literal|"button.backToPrevious"
argument_list|)
argument_list|)
condition|)
block|{
if|if
condition|(
name|BackTracker
operator|.
name|doBack
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
condition|)
return|return
literal|null
return|;
block|}
comment|// Set up Lists
name|frm
operator|.
name|setOp
argument_list|(
name|op
argument_list|)
expr_stmt|;
name|Set
name|subjectAreas
init|=
name|TimetableManager
operator|.
name|getSubjectAreas
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setSubjectAreas
argument_list|(
name|subjectAreas
argument_list|)
expr_stmt|;
comment|// Add New Designator - from subject area
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
literal|"button.addDesignator"
argument_list|)
argument_list|)
condition|)
block|{
name|String
name|subjectAreaId
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"subjectAreaId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|subjectAreaId
operator|==
literal|null
operator|||
name|subjectAreaId
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
literal|"Invalid Subject Area Id"
argument_list|)
throw|;
name|frm
operator|.
name|setSubjectAreaId
argument_list|(
operator|new
name|Long
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setReadOnly
argument_list|(
literal|"subject"
argument_list|)
expr_stmt|;
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayDesignatorDetail"
argument_list|)
return|;
block|}
comment|// Add New Designator - from instructor
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
literal|"button.addDesignator2"
argument_list|)
argument_list|)
condition|)
block|{
name|String
name|instructorId
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"instructorId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|instructorId
operator|==
literal|null
operator|||
name|instructorId
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
literal|"Invalid Instructor Id"
argument_list|)
throw|;
name|frm
operator|.
name|setInstructorId
argument_list|(
operator|new
name|Long
argument_list|(
name|instructorId
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setReadOnly
argument_list|(
literal|"instructor"
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSubjectAreas
argument_list|(
operator|new
name|DepartmentalInstructorDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|instructorId
argument_list|)
argument_list|)
operator|.
name|getDepartment
argument_list|()
operator|.
name|getSubjectAreas
argument_list|()
argument_list|)
expr_stmt|;
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayDesignatorDetail"
argument_list|)
return|;
block|}
comment|// Edit Designator - load details
if|if
condition|(
name|op
operator|.
name|equalsIgnoreCase
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"op.edit"
argument_list|)
argument_list|)
condition|)
block|{
name|doLoad
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayDesignatorDetail"
argument_list|)
return|;
block|}
comment|// Save/Update Designator
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
literal|"button.saveDesignator"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.updateDesignator"
argument_list|)
argument_list|)
condition|)
block|{
comment|// Validate
name|errors
operator|=
name|frm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|!=
literal|0
operator|||
operator|!
name|isDesignatorUnique
argument_list|(
name|frm
argument_list|)
condition|)
block|{
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
name|errors
operator|.
name|add
argument_list|(
literal|"uniqueId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"This combination of Subject / Instructor / Code already exists."
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayDesignatorDetail"
argument_list|)
return|;
block|}
name|doSaveOrUpdate
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|rsc
argument_list|,
name|op
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|BackTracker
operator|.
name|doBack
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
condition|)
return|return
literal|null
return|;
block|}
comment|// Delete Designator
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
literal|"button.deleteDesignator"
argument_list|)
argument_list|)
condition|)
block|{
name|doDelete
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|BackTracker
operator|.
name|doBack
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
condition|)
return|return
literal|null
return|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayDesignatorList"
argument_list|)
return|;
block|}
comment|/**      * Checks that combination of Subject/Instructor/Code       * does not already exist      * @param frm      * @return      */
specifier|private
name|boolean
name|isDesignatorUnique
parameter_list|(
name|DesignatorEditForm
name|frm
parameter_list|)
block|{
name|String
name|query
init|=
literal|"from Designator "
operator|+
literal|"where subjectArea=:subjectArea and instructor=:instructor and code=:code"
decl_stmt|;
if|if
condition|(
name|frm
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|frm
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|>
literal|0L
condition|)
block|{
name|query
operator|+=
literal|" and uniqueId!=:uniqueId"
expr_stmt|;
block|}
name|DesignatorDAO
name|ddao
init|=
operator|new
name|DesignatorDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|ddao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|q
operator|.
name|setInteger
argument_list|(
literal|"subjectArea"
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"instructor"
argument_list|,
name|frm
operator|.
name|getInstructorId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setString
argument_list|(
literal|"code"
argument_list|,
name|frm
operator|.
name|getCode
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|frm
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|>
literal|0L
condition|)
block|{
name|q
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|frm
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|q
operator|.
name|list
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
return|;
block|}
comment|/**      * @param request      * @param frm      */
specifier|private
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|DesignatorEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|id
init|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"id"
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
operator|||
name|id
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
literal|"Designator Unique Id was not supplied"
argument_list|)
throw|;
name|DesignatorDAO
name|ddao
init|=
operator|new
name|DesignatorDAO
argument_list|()
decl_stmt|;
name|Designator
name|d
init|=
name|ddao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|id
argument_list|)
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setUniqueId
argument_list|(
operator|new
name|Long
argument_list|(
name|id
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSubjectAreaId
argument_list|(
name|d
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setInstructorId
argument_list|(
name|d
operator|.
name|getInstructor
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCode
argument_list|(
name|d
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setReadOnly
argument_list|(
literal|"both"
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param request      * @param frm      */
specifier|private
name|void
name|setupInstructors
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|DesignatorEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|frm
operator|.
name|getUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
name|frm
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|>
literal|0L
condition|)
block|{
name|frm
operator|.
name|setReadOnly
argument_list|(
literal|"both"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|frm
operator|.
name|getReadOnly
argument_list|()
operator|.
name|equals
argument_list|(
literal|"subject"
argument_list|)
operator|||
name|frm
operator|.
name|getReadOnly
argument_list|()
operator|.
name|equals
argument_list|(
literal|"both"
argument_list|)
operator|)
operator|&&
operator|(
name|frm
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|==
literal|null
operator|||
name|frm
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|SubjectAreaDAO
name|sdao
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
decl_stmt|;
name|SubjectArea
name|sa
init|=
name|sdao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setSubjectAreaAbbv
argument_list|(
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|frm
operator|.
name|getReadOnly
argument_list|()
operator|.
name|equals
argument_list|(
literal|"instructor"
argument_list|)
operator|||
name|frm
operator|.
name|getReadOnly
argument_list|()
operator|.
name|equals
argument_list|(
literal|"both"
argument_list|)
operator|)
operator|&&
operator|(
name|frm
operator|.
name|getInstructorName
argument_list|()
operator|==
literal|null
operator|||
name|frm
operator|.
name|getInstructorName
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|DepartmentalInstructorDAO
name|ddao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|DepartmentalInstructor
name|di
init|=
name|ddao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getInstructorId
argument_list|()
argument_list|)
decl_stmt|;
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|httpSession
argument_list|)
decl_stmt|;
name|String
name|nameFormat
init|=
name|Settings
operator|.
name|getSettingValue
argument_list|(
name|user
argument_list|,
name|Constants
operator|.
name|SETTINGS_INSTRUCTOR_NAME_FORMAT
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setInstructorName
argument_list|(
name|di
operator|.
name|getName
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
operator|!=
literal|null
operator|&&
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|>
literal|0
condition|)
block|{
name|SubjectAreaDAO
name|sdao
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
decl_stmt|;
name|SubjectArea
name|sa
init|=
name|sdao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
decl_stmt|;
name|LookupTables
operator|.
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|frm
operator|.
name|getInstructorId
argument_list|()
operator|!=
literal|null
operator|&&
name|frm
operator|.
name|getInstructorId
argument_list|()
operator|.
name|longValue
argument_list|()
operator|>
literal|0L
condition|)
block|{
name|DepartmentalInstructorDAO
name|ddao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|DepartmentalInstructor
name|di
init|=
name|ddao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getInstructorId
argument_list|()
argument_list|)
decl_stmt|;
name|LookupTables
operator|.
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|di
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * @param request      * @param frm      */
specifier|private
name|void
name|doSaveOrUpdate
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|DesignatorEditForm
name|frm
parameter_list|,
name|MessageResources
name|rsc
parameter_list|,
name|String
name|op
parameter_list|)
throws|throws
name|Exception
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|DesignatorDAO
name|ddao
init|=
operator|new
name|DesignatorDAO
argument_list|()
decl_stmt|;
name|SubjectAreaDAO
name|sdao
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
decl_stmt|;
name|DepartmentalInstructorDAO
name|didao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|hibSession
operator|=
name|ddao
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|SubjectArea
name|sa
init|=
name|sdao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
decl_stmt|;
name|DepartmentalInstructor
name|di
init|=
name|didao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getInstructorId
argument_list|()
argument_list|)
decl_stmt|;
name|Designator
name|d
init|=
literal|null
decl_stmt|;
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
literal|"button.saveDesignator"
argument_list|)
argument_list|)
condition|)
block|{
name|d
operator|=
operator|new
name|Designator
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|d
operator|=
name|ddao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|d
operator|.
name|setInstructor
argument_list|(
name|di
argument_list|)
expr_stmt|;
name|d
operator|.
name|setSubjectArea
argument_list|(
name|sa
argument_list|)
expr_stmt|;
name|d
operator|.
name|setCode
argument_list|(
name|frm
operator|.
name|getCode
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|di
operator|.
name|addTodesignatorSubjectAreas
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|sa
operator|.
name|addTodesignatorInstructors
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|di
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|sa
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|di
argument_list|,
name|d
operator|.
name|toString
argument_list|()
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DESIGNATOR_EDIT
argument_list|,
operator|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.saveDesignator"
argument_list|)
argument_list|)
condition|?
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
else|:
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
operator|)
argument_list|,
name|sa
argument_list|,
name|sa
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|refresh
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|refresh
argument_list|(
name|di
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|refresh
argument_list|(
name|sa
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|(
name|e
operator|)
throw|;
block|}
block|}
comment|/**      * @param request      * @param frm      */
specifier|private
name|void
name|doDelete
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|DesignatorEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|DesignatorDAO
name|ddao
init|=
operator|new
name|DesignatorDAO
argument_list|()
decl_stmt|;
name|hibSession
operator|=
name|ddao
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|Designator
name|d
init|=
name|ddao
operator|.
name|get
argument_list|(
name|frm
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|SubjectArea
name|sa
init|=
name|d
operator|.
name|getSubjectArea
argument_list|()
decl_stmt|;
name|DepartmentalInstructor
name|di
init|=
name|d
operator|.
name|getInstructor
argument_list|()
decl_stmt|;
name|sa
operator|.
name|getDesignatorInstructors
argument_list|()
operator|.
name|remove
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|di
operator|.
name|getDesignatorSubjectAreas
argument_list|()
operator|.
name|remove
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|di
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|sa
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|di
argument_list|,
name|d
operator|.
name|toString
argument_list|()
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DESIGNATOR_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
name|sa
argument_list|,
name|sa
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|d
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|refresh
argument_list|(
name|di
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|refresh
argument_list|(
name|sa
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|(
name|e
operator|)
throw|;
block|}
block|}
block|}
end_class

end_unit

