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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|CourseReservationEditForm
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
name|ExternalCourseOfferingReservationEditAction
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
name|CourseOfferingReservation
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
name|ReservationType
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
name|CourseOfferingComparator
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
name|CourseReservationComparator
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
name|CourseOfferingDAO
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
name|CourseOfferingReservationDAO
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
name|model
operator|.
name|dao
operator|.
name|InstructionalOfferingDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 09-01-2006  *   * XDoclet definition:  * @struts:action path="/courseReservationEdit" name="courseReservationEditForm" input="/user/courseReservationEdit.jsp" scope="request"  */
end_comment

begin_class
specifier|public
class|class
name|CourseReservationEditAction
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
name|CourseReservationEditForm
name|frm
init|=
operator|(
name|CourseReservationEditForm
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
comment|//TODO Reservations - functionality to be removed later
if|if
condition|(
name|frm
operator|.
name|getOwnerId
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setOwnerId
argument_list|(
operator|(
name|Long
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"ownerId"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getOwnerType
argument_list|()
operator|==
literal|null
condition|)
name|frm
operator|.
name|setOwnerType
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
literal|"ownerType"
argument_list|)
argument_list|)
expr_stmt|;
comment|//End Bypass
comment|// Set up lists
name|setupCourseOfferings
argument_list|(
name|request
argument_list|,
name|frm
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
literal|"button.addReservationIo"
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
name|Collection
name|coList
init|=
operator|(
name|Collection
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
name|CourseOffering
operator|.
name|CRS_OFFERING_LIST_ATTR_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|coList
operator|!=
literal|null
operator|&&
name|coList
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|frm
operator|.
name|getCourseOfferingId
argument_list|()
operator|.
name|size
argument_list|()
operator|<
name|coList
operator|.
name|size
argument_list|()
condition|)
name|frm
operator|.
name|addBlankRows
argument_list|()
expr_stmt|;
else|else
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
name|ActionMessages
argument_list|()
expr_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"courseOfferingId"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Cannot add reservations that exceed the number of courses in the offering."
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
else|else
block|{
name|frm
operator|.
name|addBlankRows
argument_list|()
expr_stmt|;
block|}
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
literal|"displayCourseReservationDetail"
argument_list|)
return|;
block|}
comment|/**      * Setup course offerings       * @param request      * @param frm      */
specifier|private
name|void
name|setupCourseOfferings
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|CourseReservationEditForm
name|frm
parameter_list|)
block|{
name|String
name|ownerType
init|=
name|frm
operator|.
name|getOwnerType
argument_list|()
decl_stmt|;
name|Long
name|ownerId
init|=
name|frm
operator|.
name|getOwnerId
argument_list|()
decl_stmt|;
name|InstructionalOffering
name|io
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|ownerType
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|RESV_OWNER_IO
argument_list|)
condition|)
block|{
name|io
operator|=
operator|new
name|InstructionalOfferingDAO
argument_list|()
operator|.
name|get
argument_list|(
name|ownerId
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ownerType
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|RESV_OWNER_CONFIG
argument_list|)
condition|)
block|{
name|InstrOfferingConfig
name|config
init|=
operator|new
name|InstrOfferingConfigDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|ownerId
operator|.
name|intValue
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|io
operator|=
name|config
operator|.
name|getInstructionalOffering
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|ownerType
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|RESV_OWNER_CLASS
argument_list|)
condition|)
block|{
name|Class_
name|cls
init|=
operator|new
name|Class_DAO
argument_list|()
operator|.
name|get
argument_list|(
name|ownerId
argument_list|)
decl_stmt|;
name|io
operator|=
name|cls
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
expr_stmt|;
block|}
comment|//TODO Reservations - functionality to be removed later
if|if
condition|(
name|ownerType
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|RESV_OWNER_COURSE
argument_list|)
condition|)
block|{
name|CourseOffering
name|crs
init|=
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|get
argument_list|(
name|ownerId
argument_list|)
decl_stmt|;
name|io
operator|=
name|crs
operator|.
name|getInstructionalOffering
argument_list|()
expr_stmt|;
block|}
comment|//End Bypass
name|Vector
name|coList
init|=
operator|new
name|Vector
argument_list|(
name|io
operator|.
name|getCourseOfferings
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|coList
argument_list|,
operator|new
name|CourseOfferingComparator
argument_list|()
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|CourseOffering
operator|.
name|CRS_OFFERING_LIST_ATTR_NAME
argument_list|,
name|coList
argument_list|)
expr_stmt|;
block|}
comment|/**      * Update reservations      * @param request      * @param frm      * @throws Exception      */
specifier|private
name|void
name|doUpdate
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|CourseReservationEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
comment|//Get Reservation Type
name|ReservationType
name|rt
init|=
name|ReservationType
operator|.
name|getReservationTypebyRef
argument_list|(
name|Constants
operator|.
name|RESV_TYPE_PERM_REF
argument_list|)
decl_stmt|;
if|if
condition|(
name|rt
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Reservation type for ref "
operator|+
name|Constants
operator|.
name|RESV_TYPE_PERM_REF
operator|+
literal|" could not be loaded"
argument_list|)
throw|;
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
name|courseOfferings
init|=
name|frm
operator|.
name|getCourseOfferingId
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
name|CourseOfferingReservationDAO
name|cordao
init|=
operator|new
name|CourseOfferingReservationDAO
argument_list|()
decl_stmt|;
name|CourseOfferingDAO
name|codao
init|=
operator|new
name|CourseOfferingDAO
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
name|cordao
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
name|coResv
init|=
name|super
operator|.
name|getReservations
argument_list|(
name|frm
argument_list|,
name|CourseOfferingReservation
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|coResv
operator|!=
literal|null
operator|&&
name|coResv
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
name|coResv
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
name|CourseOfferingReservation
name|resv
init|=
operator|(
name|CourseOfferingReservation
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
name|co
init|=
operator|(
name|String
operator|)
name|courseOfferings
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|CourseOfferingReservation
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
name|CourseOfferingReservation
argument_list|()
expr_stmt|;
block|}
comment|// Load existing reservation
else|else
block|{
name|r
operator|=
name|cordao
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
comment|//r.setReservationType(rdao.get(new Integer(resvType)));
name|r
operator|.
name|setReservationType
argument_list|(
name|rt
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
name|setCourseOffering
argument_list|(
name|codao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|co
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
name|String
name|className
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.external.reservation.edit_action.class"
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
name|ExternalCourseOfferingReservationEditAction
name|editAction
init|=
operator|(
name|ExternalCourseOfferingReservationEditAction
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
name|editAction
operator|.
name|performExternalCourseOfferingReservationEditAction
argument_list|(
name|ownerObj
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
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
name|CourseReservationEditForm
name|frm
parameter_list|)
throws|throws
name|Exception
block|{
name|Vector
name|courseResv
init|=
operator|new
name|Vector
argument_list|(
name|super
operator|.
name|getReservations
argument_list|(
name|frm
argument_list|,
name|CourseOfferingReservation
operator|.
name|class
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseResv
operator|!=
literal|null
operator|&&
name|courseResv
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|Collections
operator|.
name|sort
argument_list|(
name|courseResv
argument_list|,
operator|new
name|CourseReservationComparator
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|courseResv
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
name|CourseOfferingReservation
name|resv
init|=
operator|(
name|CourseOfferingReservation
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
name|Collection
name|coList
init|=
operator|(
name|Collection
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
name|CourseOffering
operator|.
name|CRS_OFFERING_LIST_ATTR_NAME
argument_list|)
decl_stmt|;
if|if
condition|(
name|frm
operator|.
name|getAddBlankRow
argument_list|()
operator|.
name|booleanValue
argument_list|()
operator|&&
name|courseResv
operator|.
name|size
argument_list|()
operator|<
name|coList
operator|.
name|size
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

