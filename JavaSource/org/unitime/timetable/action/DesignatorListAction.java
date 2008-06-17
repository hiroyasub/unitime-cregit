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
name|commons
operator|.
name|web
operator|.
name|WebTable
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
name|DesignatorListForm
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
name|DesignatorListBuilder
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-26-2006  *   * XDoclet definition:  * @struts:action path="/designatorList" name="designatorListForm" input="/user/designatorList.jsp" scope="request"  */
end_comment

begin_class
specifier|public
class|class
name|DesignatorListAction
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
name|DesignatorListForm
name|frm
init|=
operator|(
name|DesignatorListForm
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
name|op
operator|=
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"op.view"
argument_list|)
expr_stmt|;
comment|// Set up Lists
name|frm
operator|.
name|setOp
argument_list|(
name|op
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSubjectAreas
argument_list|(
name|TimetableManager
operator|.
name|getSubjectAreas
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"designatorList.do?subjectAreaId="
operator|+
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|,
literal|"Designator List"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|// Check column ordering - default to name
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"designatorList.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"order"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
comment|// First access to screen
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
literal|"op.view"
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
operator|||
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
name|Set
name|s
init|=
operator|(
name|Set
operator|)
name|frm
operator|.
name|getSubjectAreas
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|Debug
operator|.
name|debug
argument_list|(
literal|"Exactly 1 subject area found ... "
argument_list|)
expr_stmt|;
name|SubjectArea
name|sa
init|=
operator|(
name|SubjectArea
operator|)
name|s
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
name|frm
operator|.
name|setSubjectAreaId
argument_list|(
name|sa
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
name|setEditable
argument_list|(
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
operator|||
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|isLimitedEditableBy
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|html
init|=
operator|new
name|DesignatorListBuilder
argument_list|()
operator|.
name|htmlTableForSubjectArea
argument_list|(
name|request
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"designatorList.ord"
argument_list|)
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"designatorList"
argument_list|,
name|html
argument_list|)
expr_stmt|;
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
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
condition|)
name|subjectAreaId
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"subjectAreaId"
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreaId
operator|==
literal|null
condition|)
name|subjectAreaId
operator|=
operator|(
name|String
operator|)
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
operator|&&
name|subjectAreaId
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
name|frm
operator|.
name|setSubjectAreaId
argument_list|(
name|subjectAreaId
argument_list|)
expr_stmt|;
name|SubjectArea
name|sa
init|=
operator|(
operator|new
name|SubjectAreaDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|subjectAreaId
argument_list|)
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setEditable
argument_list|(
name|sa
operator|==
literal|null
condition|?
literal|false
else|:
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
operator|||
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|isLimitedEditableBy
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|html
init|=
operator|new
name|DesignatorListBuilder
argument_list|()
operator|.
name|htmlTableForSubjectArea
argument_list|(
name|request
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"designatorList.ord"
argument_list|)
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"designatorList"
argument_list|,
name|html
argument_list|)
expr_stmt|;
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frm
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
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
comment|// View Button Clicked
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
literal|"button.exportPDF"
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
literal|"button.displayDesignatorList"
argument_list|)
argument_list|)
condition|)
block|{
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
comment|// Validation fails
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
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
literal|"displayDesignatorList"
argument_list|)
return|;
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
literal|"button.exportPDF"
argument_list|)
argument_list|)
condition|)
operator|new
name|DesignatorListBuilder
argument_list|()
operator|.
name|pdfTableForSubjectArea
argument_list|(
name|request
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"designatorList.ord"
argument_list|)
argument_list|)
expr_stmt|;
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
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|SubjectArea
name|sa
init|=
operator|(
operator|new
name|SubjectAreaDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setEditable
argument_list|(
name|sa
operator|==
literal|null
condition|?
literal|false
else|:
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
operator|||
name|sa
operator|.
name|getDepartment
argument_list|()
operator|.
name|isLimitedEditableBy
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frm
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|String
name|html
init|=
operator|new
name|DesignatorListBuilder
argument_list|()
operator|.
name|htmlTableForSubjectArea
argument_list|(
name|request
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|,
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"designatorList.ord"
argument_list|)
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"designatorList"
argument_list|,
name|html
argument_list|)
expr_stmt|;
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayDesignatorList"
argument_list|)
return|;
block|}
comment|// Add new designator
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
comment|// Validation fails
if|if
condition|(
name|errors
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
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
literal|"displayDesignatorList"
argument_list|)
return|;
block|}
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
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"addDesignator"
argument_list|)
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
block|}
end_class

end_unit

