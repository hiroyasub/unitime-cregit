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
name|Collection
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
name|List
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
name|Session
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
name|StudentGroupReservationEditForm
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
name|StudentGroupReservation
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
name|ReservationTypeDAO
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
name|StudentGroupDAO
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
name|StudentGroupReservationDAO
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
comment|/**   * MyEclipse Struts  * Creation date: 09-01-2006  *   * XDoclet definition:  * @struts:action path="/studentGroupReservationEdit" name="studentGroupReservationEditForm" input="/user/studentGroupReservationEdit.jsp" scope="request"  */
end_comment

begin_class
specifier|public
class|class
name|StudentGroupReservationEditAction
extends|extends
name|ReservationAction
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
name|StudentGroupReservationEditForm
name|frm
init|=
operator|(
name|StudentGroupReservationEditForm
operator|)
name|form
decl_stmt|;
name|ActionMessages
name|errors
init|=
literal|null
decl_stmt|;
name|String
name|op
init|=
name|frm
operator|.
name|getOp
argument_list|()
decl_stmt|;
comment|// Set up lists
name|LookupTables
operator|.
name|setupStudentGroups
argument_list|(
name|request
argument_list|)
expr_stmt|;
comment|// New reservation
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
literal|"button.reservationNextStep"
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
block|}
comment|// Add Blank Rows
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
literal|"button.addReservationRow"
argument_list|)
argument_list|)
condition|)
block|{
name|frm
operator|.
name|addBlankRows
argument_list|()
expr_stmt|;
block|}
comment|// Delete rows
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
literal|"button.removeReservation"
argument_list|)
argument_list|)
condition|)
block|{
name|frm
operator|.
name|removeRow
argument_list|(
name|super
operator|.
name|getDeletedRowId
argument_list|(
name|request
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Update
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
literal|"button.updateReservation"
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
block|}
else|else
block|{
name|doUpdate
argument_list|(
name|request
argument_list|,
name|frm
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|doBack
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"displayStuGrpReservationDetail"
argument_list|)
return|;
block|}
comment|/**      * Update reservations      * @param request      * @param frm      * @throws Exception      */
specifier|private
name|void
name|doUpdate
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|StudentGroupReservationEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
comment|// Get input data
name|Long
name|owner
init|=
name|frm
operator|.
name|getOwnerId
argument_list|()
decl_stmt|;
name|String
name|ownerType
init|=
name|frm
operator|.
name|getOwnerType
argument_list|()
decl_stmt|;
name|List
name|resvIds
init|=
name|frm
operator|.
name|getReservationId
argument_list|()
decl_stmt|;
name|List
name|resvTypes
init|=
name|frm
operator|.
name|getReservationType
argument_list|()
decl_stmt|;
name|List
name|resvPriorities
init|=
name|frm
operator|.
name|getPriority
argument_list|()
decl_stmt|;
name|List
name|reservedSpaces
init|=
name|frm
operator|.
name|getReserved
argument_list|()
decl_stmt|;
name|List
name|priorEnrollments
init|=
name|frm
operator|.
name|getPriorEnrollment
argument_list|()
decl_stmt|;
name|List
name|projEnrollments
init|=
name|frm
operator|.
name|getProjectedEnrollment
argument_list|()
decl_stmt|;
name|List
name|stuGroups
init|=
name|frm
operator|.
name|getStudentGroupId
argument_list|()
decl_stmt|;
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
name|StudentGroupReservationDAO
name|sdao
init|=
operator|new
name|StudentGroupReservationDAO
argument_list|()
decl_stmt|;
name|StudentGroupDAO
name|sgdao
init|=
operator|new
name|StudentGroupDAO
argument_list|()
decl_stmt|;
name|ReservationTypeDAO
name|rdao
init|=
operator|new
name|ReservationTypeDAO
argument_list|()
decl_stmt|;
name|Object
name|ownerObj
init|=
name|super
operator|.
name|getOwner
argument_list|(
name|frm
argument_list|)
decl_stmt|;
name|hibSession
operator|=
name|sdao
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
comment|// Delete reservations not in resvId list
name|Collection
name|stuGrpResv
init|=
name|super
operator|.
name|getReservations
argument_list|(
name|frm
argument_list|,
name|StudentGroupReservation
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|stuGrpResv
operator|!=
literal|null
operator|&&
name|stuGrpResv
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|iter
init|=
name|stuGrpResv
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|StudentGroupReservation
name|resv
init|=
operator|(
name|StudentGroupReservation
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|resvIds
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|resvId
init|=
operator|(
name|String
operator|)
name|resvIds
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|resvId
operator|!=
literal|null
operator|&&
name|resvId
operator|.
name|equals
argument_list|(
name|resv
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|found
condition|)
block|{
name|hibSession
operator|.
name|delete
argument_list|(
name|resv
argument_list|)
expr_stmt|;
name|iter
operator|.
name|remove
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|ownerObj
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Add / Update
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|resvIds
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|resvId
init|=
operator|(
name|String
operator|)
name|resvIds
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|resvType
init|=
operator|(
name|String
operator|)
name|resvTypes
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|resvPriority
init|=
operator|(
name|String
operator|)
name|resvPriorities
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|reservedSpace
init|=
operator|(
name|String
operator|)
name|reservedSpaces
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|priorEnrollment
init|=
operator|(
name|String
operator|)
name|priorEnrollments
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|projEnrollment
init|=
operator|(
name|String
operator|)
name|projEnrollments
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|stuGrp
init|=
operator|(
name|String
operator|)
name|stuGroups
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|StudentGroupReservation
name|r
init|=
literal|null
decl_stmt|;
comment|// New Reservation
if|if
condition|(
name|resvId
operator|==
literal|null
operator|||
name|resvId
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|r
operator|=
operator|new
name|StudentGroupReservation
argument_list|()
expr_stmt|;
block|}
comment|// Load existing reservation
else|else
block|{
name|r
operator|=
name|sdao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|resvId
operator|.
name|trim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|r
operator|.
name|setOwner
argument_list|(
name|owner
argument_list|)
expr_stmt|;
name|r
operator|.
name|setOwnerClassId
argument_list|(
name|ownerType
argument_list|)
expr_stmt|;
name|r
operator|.
name|setPriority
argument_list|(
operator|new
name|Integer
argument_list|(
name|resvPriority
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|.
name|setReserved
argument_list|(
operator|new
name|Integer
argument_list|(
name|reservedSpace
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|.
name|setReservationType
argument_list|(
name|rdao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|resvType
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|priorEnrollment
operator|==
literal|null
operator|||
name|priorEnrollment
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|r
operator|.
name|setPriorEnrollment
argument_list|(
literal|null
argument_list|)
expr_stmt|;
else|else
name|r
operator|.
name|setPriorEnrollment
argument_list|(
operator|new
name|Integer
argument_list|(
name|priorEnrollment
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|projEnrollment
operator|==
literal|null
operator|||
name|projEnrollment
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|r
operator|.
name|setProjectedEnrollment
argument_list|(
literal|null
argument_list|)
expr_stmt|;
else|else
name|r
operator|.
name|setProjectedEnrollment
argument_list|(
operator|new
name|Integer
argument_list|(
name|projEnrollment
argument_list|)
argument_list|)
expr_stmt|;
name|r
operator|.
name|setStudentGroup
argument_list|(
name|sgdao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|stuGrp
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|ownerObj
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|clear
argument_list|()
expr_stmt|;
name|hibSession
operator|.
name|refresh
argument_list|(
name|ownerObj
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
throw|throw
operator|(
name|e
operator|)
throw|;
block|}
block|}
comment|/**      * Load existing reservations data for the owner       * @param request      * @param frm      * @throws Exception      */
specifier|public
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|StudentGroupReservationEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
name|Collection
name|stuGroupResv
init|=
name|super
operator|.
name|getReservations
argument_list|(
name|frm
argument_list|,
name|StudentGroupReservation
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|stuGroupResv
operator|!=
literal|null
operator|&&
name|stuGroupResv
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|iter
init|=
name|stuGroupResv
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|StudentGroupReservation
name|resv
init|=
operator|(
name|StudentGroupReservation
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|frm
operator|.
name|addReservation
argument_list|(
name|resv
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Add blank rows
if|if
condition|(
name|frm
operator|.
name|getAddBlankRow
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
name|frm
operator|.
name|addBlankRows
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

