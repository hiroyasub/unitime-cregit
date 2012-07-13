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
name|HashSet
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
name|StringTokenizer
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
name|Transaction
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
name|defaults
operator|.
name|SessionAttribute
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
name|InstructorListUpdateForm
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
name|ExternalClassEditAction
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
name|ClassInstructor
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
name|Department
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
name|Staff
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
name|StaffComparator
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
name|DepartmentDAO
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 07-18-2006  *   * XDoclet definition:  * @struts.action path="/updateInstructorList" name="updateInstructorListForm" input="/user/updateInstructorList.jsp" scope="request"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/instructorListUpdate"
argument_list|)
specifier|public
class|class
name|InstructorListUpdateAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
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
comment|//Check permissions
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ManageInstructors
argument_list|)
expr_stmt|;
name|InstructorListUpdateForm
name|frm
init|=
operator|(
name|InstructorListUpdateForm
operator|)
name|form
decl_stmt|;
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
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
comment|// Cancel - Go back to Instructors Detail Screen
if|if
condition|(
name|op
operator|!=
literal|null
operator|&&
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.backToInstructorList"
argument_list|)
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
literal|"instructorList.do"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Update - Update the instructor and go back to Instructor List Screen
if|if
condition|(
name|op
operator|!=
literal|null
operator|&&
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.update"
argument_list|)
argument_list|)
condition|)
block|{
name|update
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructorList.do"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|// Refresh - set filters
if|if
condition|(
name|op
operator|!=
literal|null
operator|&&
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.applyFilter"
argument_list|)
argument_list|)
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"filterApplied"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
block|}
name|Collection
name|assigned
init|=
name|getAssigned
argument_list|()
decl_stmt|;
if|if
condition|(
name|assigned
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setAssignedInstr
argument_list|(
name|assigned
argument_list|)
expr_stmt|;
block|}
name|Collection
name|available
init|=
name|getAvailable
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|available
operator|!=
literal|null
condition|)
block|{
name|frm
operator|.
name|setAvailableInstr
argument_list|(
name|available
argument_list|)
expr_stmt|;
block|}
comment|// Get Position Types
name|LookupTables
operator|.
name|setupPositionTypes
argument_list|(
name|request
argument_list|)
expr_stmt|;
name|setupFilters
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setInstructors
argument_list|()
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showUpdateInstructorList"
argument_list|)
return|;
block|}
comment|/**      * @param request      */
specifier|private
name|void
name|setupFilters
parameter_list|(
name|InstructorListUpdateForm
name|frm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|String
index|[]
name|defaultPosTypes
init|=
block|{
literal|"ADMIN_STAFF"
block|,
literal|"CLERICAL_STAFF"
block|,
literal|"SERVICE_STAFF"
block|,
literal|"FELLOWSHIP"
block|,
literal|"UNDRGRD_TEACH_ASST"
block|,
literal|"EMERITUS OTHER"
block|}
decl_stmt|;
name|boolean
name|filterSet
init|=
literal|"1"
operator|.
name|equals
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"instrListFilter"
argument_list|,
literal|"0"
argument_list|)
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
name|setDisplayListType
argument_list|(
literal|"both"
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setDisplayPosType
argument_list|(
name|defaultPosTypes
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
name|setDisplayListType
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"displayListType"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|displayPosType
init|=
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"displayPosType"
argument_list|)
decl_stmt|;
if|if
condition|(
name|displayPosType
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|arr
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|displayPosType
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
name|arr
operator|=
operator|new
name|String
index|[]
block|{
literal|"X"
block|}
expr_stmt|;
block|}
else|else
block|{
name|StringTokenizer
name|strTok
init|=
operator|new
name|StringTokenizer
argument_list|(
name|displayPosType
argument_list|)
decl_stmt|;
name|arr
operator|=
operator|new
name|String
index|[
name|strTok
operator|.
name|countTokens
argument_list|()
index|]
expr_stmt|;
name|int
name|ct
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|strTok
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|arr
index|[
name|ct
operator|++
index|]
operator|=
operator|(
name|String
operator|)
name|strTok
operator|.
name|nextToken
argument_list|()
expr_stmt|;
block|}
block|}
name|frm
operator|.
name|setDisplayPosType
argument_list|(
name|arr
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|==
literal|null
operator|||
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|frm
operator|.
name|setDisplayListType
argument_list|(
literal|"both"
argument_list|)
expr_stmt|;
if|if
condition|(
name|frm
operator|.
name|getDisplayPosType
argument_list|()
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|filterApplied
operator|!=
literal|null
condition|)
name|frm
operator|.
name|setDisplayPosType
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"X"
block|}
argument_list|)
expr_stmt|;
else|else
name|frm
operator|.
name|setDisplayPosType
argument_list|(
name|defaultPosTypes
argument_list|)
expr_stmt|;
block|}
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"instrListFilter"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"displayListType"
argument_list|,
name|frm
operator|.
name|getDisplayListType
argument_list|()
argument_list|)
expr_stmt|;
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|setProperty
argument_list|(
literal|"displayPosType"
argument_list|,
name|Constants
operator|.
name|arrayToStr
argument_list|(
name|frm
operator|.
name|getDisplayPosType
argument_list|()
argument_list|,
literal|""
argument_list|,
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 *  	 * @param frm 	 * @param request 	 */
specifier|private
name|void
name|update
parameter_list|(
name|InstructorListUpdateForm
name|frm
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
throws|throws
name|Exception
block|{
name|String
index|[]
name|selectedAssigned
init|=
name|frm
operator|.
name|getAssignedSelected
argument_list|()
decl_stmt|;
name|String
index|[]
name|selectedNotAssigned
init|=
name|frm
operator|.
name|getAvailableSelected
argument_list|()
decl_stmt|;
name|Collection
name|assigned
init|=
name|getAssigned
argument_list|()
decl_stmt|;
name|Collection
name|available
init|=
name|getAvailable
argument_list|(
name|frm
argument_list|,
name|request
argument_list|)
decl_stmt|;
name|StringBuffer
name|s1
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|s2
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedAssigned
operator|.
name|length
operator|!=
literal|0
condition|)
block|{
name|s1
operator|.
name|append
argument_list|(
name|Constants
operator|.
name|arrayToStr
argument_list|(
name|selectedAssigned
argument_list|,
literal|""
argument_list|,
literal|","
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|selectedNotAssigned
operator|.
name|length
operator|!=
literal|0
condition|)
block|{
name|s2
operator|.
name|append
argument_list|(
name|Constants
operator|.
name|arrayToStr
argument_list|(
name|selectedNotAssigned
argument_list|,
literal|""
argument_list|,
literal|","
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|DepartmentalInstructorDAO
name|idao
init|=
operator|new
name|DepartmentalInstructorDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|idao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
name|HashSet
argument_list|<
name|Class_
argument_list|>
name|updatedClasses
init|=
operator|new
name|HashSet
argument_list|<
name|Class_
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
comment|//remove instructor from assigned
if|if
condition|(
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|.
name|equals
argument_list|(
literal|"assigned"
argument_list|)
operator|||
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|.
name|equals
argument_list|(
literal|"both"
argument_list|)
operator|)
condition|)
block|{
for|for
control|(
name|Iterator
name|iter
init|=
name|assigned
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
name|DepartmentalInstructor
name|inst
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s1
operator|.
name|indexOf
argument_list|(
name|inst
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|==
operator|-
literal|1
condition|)
block|{
if|if
condition|(
operator|!
name|inst
operator|.
name|getExams
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|inst
operator|.
name|getClasses
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|inst
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|INSTRUCTOR_MANAGE
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
name|inst
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|inst
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
name|ClassInstructor
name|ci
init|=
operator|(
name|ClassInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|updatedClasses
operator|.
name|add
argument_list|(
name|ci
operator|.
name|getClassInstructing
argument_list|()
argument_list|)
expr_stmt|;
name|ci
operator|.
name|getClassInstructing
argument_list|()
operator|.
name|getClassInstructors
argument_list|()
operator|.
name|remove
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|ci
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|ci
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|inst
operator|.
name|getAssignments
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
name|Assignment
name|a
init|=
operator|(
name|Assignment
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|a
operator|.
name|getInstructors
argument_list|()
operator|.
name|remove
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|a
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|inst
operator|.
name|getDesignatorSubjectAreas
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
name|Designator
name|d
init|=
operator|(
name|Designator
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|SubjectArea
name|sa
init|=
name|d
operator|.
name|getSubjectArea
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
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|sa
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|delete
argument_list|(
name|inst
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|!=
literal|null
operator|&&
operator|(
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|.
name|equals
argument_list|(
literal|"available"
argument_list|)
operator|||
name|frm
operator|.
name|getDisplayListType
argument_list|()
operator|.
name|equals
argument_list|(
literal|"both"
argument_list|)
operator|)
condition|)
block|{
comment|//move instructor from staff to department
for|for
control|(
name|Iterator
name|iter
init|=
name|available
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
name|Staff
name|staff
init|=
operator|(
name|Staff
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|s2
operator|.
name|indexOf
argument_list|(
name|staff
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|DepartmentalInstructor
name|inst
init|=
operator|new
name|DepartmentalInstructor
argument_list|()
decl_stmt|;
name|inst
operator|.
name|setLastName
argument_list|(
name|staff
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|inst
operator|.
name|setEmail
argument_list|(
name|staff
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|Department
name|d
init|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|deptId
argument_list|)
argument_list|)
decl_stmt|;
name|inst
operator|.
name|setDepartment
argument_list|(
name|d
argument_list|)
expr_stmt|;
if|if
condition|(
name|staff
operator|.
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|inst
operator|.
name|setFirstName
argument_list|(
name|staff
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|staff
operator|.
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|inst
operator|.
name|setMiddleName
argument_list|(
name|staff
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|staff
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|inst
operator|.
name|setExternalUniqueId
argument_list|(
name|staff
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|staff
operator|.
name|getPositionType
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|inst
operator|.
name|setPositionType
argument_list|(
name|staff
operator|.
name|getPositionType
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|inst
operator|.
name|setIgnoreToFar
argument_list|(
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|save
argument_list|(
name|inst
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|sessionContext
argument_list|,
name|inst
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|INSTRUCTOR_MANAGE
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
name|inst
operator|.
name|getDepartment
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|String
name|className
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.external.class.edit_action.class"
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
name|ExternalClassEditAction
name|editAction
init|=
operator|(
name|ExternalClassEditAction
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
for|for
control|(
name|Class_
name|c
range|:
name|updatedClasses
control|)
block|{
name|editAction
operator|.
name|performExternalClassEditAction
argument_list|(
name|c
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
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
try|try
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
operator|&&
name|tx
operator|.
name|isActive
argument_list|()
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e1
parameter_list|)
block|{
block|}
throw|throw
name|e
throw|;
block|}
block|}
comment|/** 	 *  	 * @param frm 	 * @param request 	 * @return 	 * @throws Exception 	 */
specifier|private
name|Collection
name|getAvailable
parameter_list|(
name|InstructorListUpdateForm
name|frm
parameter_list|,
name|HttpServletRequest
name|request
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
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|Department
name|d
init|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|deptId
argument_list|)
argument_list|)
decl_stmt|;
name|List
name|available
init|=
name|Staff
operator|.
name|getStaffByDept
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|available
argument_list|,
operator|new
name|StaffComparator
argument_list|(
name|StaffComparator
operator|.
name|COMPARE_BY_POSITION
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|available
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/** 	 *  	 * @param frm 	 * @param request 	 * @return 	 * @throws Exception 	 */
specifier|private
name|Collection
name|getAssigned
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|deptId
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|assigned
init|=
name|DepartmentalInstructor
operator|.
name|findInstructorsForDepartment
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|deptId
argument_list|)
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|assigned
argument_list|,
operator|new
name|DepartmentalInstructorComparator
argument_list|(
name|DepartmentalInstructorComparator
operator|.
name|COMPARE_BY_POSITION
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|assigned
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

