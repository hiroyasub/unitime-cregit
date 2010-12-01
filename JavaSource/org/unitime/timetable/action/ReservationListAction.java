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
name|ActionErrors
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
name|ReservationListForm
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
name|ReservationsTableBuilder
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
name|pdf
operator|.
name|PdfReservationsTableBuilder
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 08-30-2006  *   * XDoclet definition:  * @struts:action path="/reservationList" name="reservationListForm" input="/user/reservationList.jsp" scope="request"  */
end_comment

begin_class
specifier|public
class|class
name|ReservationListAction
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
name|httpSession
argument_list|)
decl_stmt|;
name|ReservationListForm
name|frm
init|=
operator|(
name|ReservationListForm
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
literal|"button.saveReservation"
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
literal|"button.updateReservation"
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
literal|"button.deleteReservation"
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
literal|"button.addReservation"
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
literal|"button.reservationNextStep"
argument_list|)
argument_list|)
condition|)
block|{
name|setupFilters
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
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
name|String
name|courseNbr
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"courseNbr"
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseNbr
operator|==
literal|null
condition|)
name|courseNbr
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"courseNbr"
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|==
literal|null
condition|)
name|courseNbr
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
name|CRS_NBR_ATTR_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|==
literal|null
condition|)
name|courseNbr
operator|=
literal|""
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
name|frm
operator|.
name|setCourseNbr
argument_list|(
name|courseNbr
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
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|,
name|frm
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
name|doSearch
argument_list|(
name|frm
argument_list|,
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
else|else
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
name|frm
operator|.
name|setSubjectAreaId
argument_list|(
operator|(
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
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
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
name|doSearch
argument_list|(
name|frm
argument_list|,
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"reservationList.do"
argument_list|,
literal|"Reservation List"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayReservationList"
argument_list|)
return|;
block|}
comment|// View Button Clicked / Export PDF
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
literal|"button.displayReservationList"
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
literal|"button.exportPDF"
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
literal|"displayReservationList"
argument_list|)
return|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"filterApplied"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|setupFilters
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
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
block|{
name|doExportToPdf
argument_list|(
name|frm
argument_list|,
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
name|doSearch
argument_list|(
name|frm
argument_list|,
name|request
argument_list|,
name|errors
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
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|,
name|frm
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"reservationList.do"
argument_list|,
literal|"Reservation List"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayReservationList"
argument_list|)
return|;
block|}
comment|// Add new reservation
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
literal|"button.addReservationIo"
argument_list|)
argument_list|)
operator|&&
operator|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"addNewError"
argument_list|)
operator|==
literal|null
operator|||
operator|!
name|request
operator|.
name|getAttribute
argument_list|(
literal|"addNewError"
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"true"
argument_list|)
operator|)
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
name|frm
operator|.
name|setOp
argument_list|(
literal|null
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
literal|"displayReservationList"
argument_list|)
return|;
block|}
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"reservationList.do"
argument_list|,
literal|"Reservation List"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"addReservation"
argument_list|)
return|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayReservationList"
argument_list|)
return|;
block|}
comment|/**      * Export to Pdf      * @param frm      * @param request      * @param errors      */
specifier|private
name|void
name|doExportToPdf
parameter_list|(
name|ReservationListForm
name|frm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|ActionMessages
name|errors
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
operator|new
name|PdfReservationsTableBuilder
argument_list|()
operator|.
name|pdfTableForSubjectArea
argument_list|(
name|request
argument_list|,
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|,
name|frm
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|frm
operator|.
name|getIoResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getConfigResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getClassResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getCourseResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getIndResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getSgResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getAaResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getPosResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getCrsResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
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
operator|new
name|Exception
argument_list|(
literal|"Pdf export failed - "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
comment|/**      * @param frm      * @param request      * @param errors      */
specifier|private
name|void
name|doSearch
parameter_list|(
name|ReservationListForm
name|frm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|ActionMessages
name|errors
parameter_list|)
throws|throws
name|Exception
block|{
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
name|String
name|html
init|=
operator|new
name|ReservationsTableBuilder
argument_list|()
operator|.
name|htmlTableForSubjectArea
argument_list|(
name|user
argument_list|,
name|frm
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|,
name|frm
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
name|frm
operator|.
name|getIoResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getConfigResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getClassResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getCourseResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getIndResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getSgResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getAaResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getPosResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|,
name|frm
operator|.
name|getCrsResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"reservationList"
argument_list|,
name|html
argument_list|)
expr_stmt|;
if|if
condition|(
name|html
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|errors
operator|==
literal|null
condition|)
name|errors
operator|=
operator|new
name|ActionErrors
argument_list|()
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"search"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No reservations found matching the search criteria and filter settings"
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param frm      * @param request      */
specifier|private
name|void
name|setupFilters
parameter_list|(
name|ReservationListForm
name|frm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|boolean
name|filterSet
init|=
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"resvListFilter"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|String
name|filterApplied
init|=
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"filterApplied"
argument_list|)
decl_stmt|;
if|if
condition|(
name|filterApplied
operator|!=
literal|null
operator|&&
operator|!
name|filterApplied
operator|.
name|equals
argument_list|(
literal|"1"
argument_list|)
condition|)
name|filterApplied
operator|=
literal|null
expr_stmt|;
if|if
condition|(
operator|!
name|filterSet
condition|)
block|{
name|frm
operator|.
name|setIoResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setConfigResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setClassResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCourseResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setIndResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSgResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setAaResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setPosResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCrsResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|filterApplied
operator|==
literal|null
condition|)
block|{
name|frm
operator|.
name|setIoResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayIoResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setConfigResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayConfigResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setClassResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayClassResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCourseResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayCourseResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setIndResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeIndResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSgResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeSgResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setAaResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeAaResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setPosResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includePosResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCrsResv
argument_list|(
operator|new
name|Boolean
argument_list|(
name|UserData
operator|.
name|getPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeCrsResv"
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|frm
operator|.
name|getIoResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setIoResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getConfigResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setConfigResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getClassResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setClassResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getCourseResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setCourseResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getIndResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setIndResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getSgResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setSgResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getAaResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setAaResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getPosResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setPosResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getCrsResv
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setCrsResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
comment|// check that at least one filter is checked
if|if
condition|(
operator|!
name|frm
operator|.
name|getIoResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
operator|!
name|frm
operator|.
name|getConfigResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
operator|!
name|frm
operator|.
name|getClassResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
operator|!
name|frm
operator|.
name|getCourseResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|frm
operator|.
name|setIoResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|frm
operator|.
name|getIndResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
operator|!
name|frm
operator|.
name|getSgResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
operator|!
name|frm
operator|.
name|getAaResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
operator|!
name|frm
operator|.
name|getPosResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
operator|!
name|frm
operator|.
name|getCrsResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|frm
operator|.
name|setAaResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setCrsResv
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"resvListFilter"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayIoResv"
argument_list|,
name|frm
operator|.
name|getIoResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayConfigResv"
argument_list|,
name|frm
operator|.
name|getConfigResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayClassResv"
argument_list|,
name|frm
operator|.
name|getClassResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"displayCourseResv"
argument_list|,
name|frm
operator|.
name|getCourseResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeIndResv"
argument_list|,
name|frm
operator|.
name|getIndResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeSgResv"
argument_list|,
name|frm
operator|.
name|getSgResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeAaResv"
argument_list|,
name|frm
operator|.
name|getAaResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includePosResv"
argument_list|,
name|frm
operator|.
name|getPosResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
name|UserData
operator|.
name|setPropertyBoolean
argument_list|(
name|httpSession
argument_list|,
literal|"includeCrsResv"
argument_list|,
name|frm
operator|.
name|getCrsResv
argument_list|()
operator|.
name|booleanValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

