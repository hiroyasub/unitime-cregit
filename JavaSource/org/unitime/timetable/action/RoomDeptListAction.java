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
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|RoomDeptListForm
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
name|Roles
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
name|Room
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
name|RoomDept
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
name|Session
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
name|TimetableManagerDAO
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
name|RequiredTimeTable
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 05-05-2006  *   * XDoclet definition:  * @struts.action path="/roomDeptList" name="roomDeptListForm" input="/admin/roomDeptList.jsp" parameter="doit" scope="request" validate="true"  * @struts.action-forward name="showRoomDeptList" path="roomDeptListTile"  */
end_comment

begin_class
specifier|public
class|class
name|RoomDeptListAction
extends|extends
name|Action
block|{
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
name|RoomDeptListForm
name|roomDeptListForm
init|=
operator|(
name|RoomDeptListForm
operator|)
name|form
decl_stmt|;
name|HttpSession
name|webSession
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
name|webSession
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
name|buildDeptTable
argument_list|(
name|request
argument_list|,
name|roomDeptListForm
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomDeptList"
argument_list|)
return|;
block|}
comment|/** 	 *  	 * @param request 	 * @param roomDeptListForm 	 * @throws Exception 	 */
specifier|private
name|void
name|buildDeptTable
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|RoomDeptListForm
name|roomDeptListForm
parameter_list|)
throws|throws
name|Exception
block|{
name|HttpSession
name|webSession
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
name|webSession
argument_list|)
decl_stmt|;
name|Long
name|sessionId
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getSessionId
argument_list|()
decl_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"roomDeptList.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|5
argument_list|,
literal|"Room Departments"
argument_list|,
literal|"roomDeptList.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Dept"
block|,
literal|"Department Abbreviation"
block|,
literal|"Room"
block|,
literal|"Capacity"
block|,
literal|"Room Availability&amp; Sharing"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"right"
block|,
literal|"left"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|webTable
operator|.
name|setRowStyle
argument_list|(
literal|"white-space:nowrap"
argument_list|)
expr_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
literal|null
decl_stmt|;
name|boolean
name|timeVertical
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridVertical
argument_list|(
name|user
argument_list|)
decl_stmt|;
name|String
name|timeGridSize
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridSize
argument_list|(
name|user
argument_list|)
decl_stmt|;
comment|//get depts owned by user
name|Set
name|depts
init|=
name|getDepts
argument_list|(
name|user
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|depts
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
name|Department
name|d
init|=
operator|(
name|Department
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|Set
name|rooms
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|iterRD
init|=
name|d
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|iterRD
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomDept
name|rd
init|=
operator|(
name|RoomDept
operator|)
name|iterRD
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|rd
operator|.
name|getRoom
argument_list|()
operator|instanceof
name|Room
condition|)
name|rooms
operator|.
name|add
argument_list|(
name|rd
operator|.
name|getRoom
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|rooms
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|String
name|rmLabel
init|=
literal|""
decl_stmt|;
name|String
name|rmCapacity
init|=
literal|""
decl_stmt|;
name|String
name|rmDept
init|=
literal|""
decl_stmt|;
name|boolean
name|firstRoom
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Iterator
name|iterRoom
init|=
name|rooms
operator|.
name|iterator
argument_list|()
init|;
name|iterRoom
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Room
name|room
init|=
operator|(
name|Room
operator|)
name|iterRoom
operator|.
name|next
argument_list|()
decl_stmt|;
name|rmLabel
operator|+=
literal|"<TR><TD nowrap "
operator|+
operator|(
operator|!
name|firstRoom
condition|?
literal|"style='border-top:black 1px dashed;'"
else|:
literal|""
operator|)
operator|+
literal|">"
expr_stmt|;
name|rmCapacity
operator|+=
literal|"<TR><TD nowrap "
operator|+
operator|(
operator|!
name|firstRoom
condition|?
literal|"style='border-top:black 1px dashed;'"
else|:
literal|""
operator|)
operator|+
literal|" align='right'>"
expr_stmt|;
name|rmLabel
operator|=
name|rmLabel
operator|+
name|room
operator|.
name|getLabel
argument_list|()
expr_stmt|;
name|rmCapacity
operator|=
name|rmCapacity
operator|+
name|room
operator|.
name|getCapacity
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|rmLabel
operator|+=
literal|"<B>&nbsp;</B></TD></TR>"
expr_stmt|;
name|rmCapacity
operator|+=
literal|"<B>&nbsp;</B></TD></TR>"
expr_stmt|;
comment|// get pattern column
name|RequiredTimeTable
name|rtt
init|=
name|room
operator|.
name|getRoomSharingTable
argument_list|()
decl_stmt|;
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|setDefaultSelection
argument_list|(
name|timeGridSize
argument_list|)
expr_stmt|;
name|File
name|imageFileName
init|=
literal|null
decl_stmt|;
try|try
block|{
name|imageFileName
operator|=
name|rtt
operator|.
name|createImage
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|TreeSet
name|sortedDepts
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|Comparator
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|Department
name|d1
init|=
operator|(
name|Department
operator|)
name|o1
decl_stmt|;
name|Department
name|d2
init|=
operator|(
name|Department
operator|)
name|o2
decl_stmt|;
return|return
name|d1
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|compareTo
argument_list|(
name|d2
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|iterDept
init|=
name|room
operator|.
name|getRoomDepts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|iterDept
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|RoomDept
name|rd
init|=
operator|(
name|RoomDept
operator|)
name|iterDept
operator|.
name|next
argument_list|()
decl_stmt|;
name|Department
name|department
init|=
name|rd
operator|.
name|getDepartment
argument_list|()
decl_stmt|;
name|sortedDepts
operator|.
name|add
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
name|int
name|deptRow
init|=
literal|0
decl_stmt|;
name|rmDept
operator|+=
literal|"<TR><TD nowrap valign='center' "
operator|+
operator|(
name|firstRoom
condition|?
literal|""
else|:
literal|"style='border-top:black 1px dashed' "
operator|)
operator|+
literal|"rowspan='"
operator|+
name|Math
operator|.
name|max
argument_list|(
literal|2
argument_list|,
name|sortedDepts
operator|.
name|size
argument_list|()
argument_list|)
operator|+
literal|"'>"
expr_stmt|;
if|if
condition|(
name|imageFileName
operator|!=
literal|null
condition|)
block|{
name|rmDept
operator|+=
literal|"<img border='0' src='temp/"
operator|+
operator|(
name|imageFileName
operator|.
name|getName
argument_list|()
operator|)
operator|+
literal|"'>&nbsp;&nbsp;"
expr_stmt|;
block|}
name|rmDept
operator|+=
literal|"</TD>"
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|sortedDepts
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
name|Department
name|department
init|=
operator|(
name|Department
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|deptRow
operator|>
literal|0
condition|)
block|{
name|rmLabel
operator|+=
literal|"<TR><TD><B>&nbsp;</B></TD></TR>"
expr_stmt|;
name|rmCapacity
operator|+=
literal|"<TR><TD><B>&nbsp;</B></TD></TR>"
expr_stmt|;
block|}
name|rmDept
operator|+=
operator|(
name|deptRow
operator|>
literal|0
condition|?
literal|"<TR>"
else|:
literal|""
operator|)
expr_stmt|;
name|rmDept
operator|+=
literal|"<TD nowrap width='100%' style='color:#"
operator|+
name|department
operator|.
name|getRoomSharingColor
argument_list|(
literal|null
argument_list|)
operator|+
literal|";font-weight:bold;"
operator|+
operator|(
operator|!
name|firstRoom
operator|&&
name|deptRow
operator|==
literal|0
condition|?
literal|"border-top:black 1px dashed;"
else|:
literal|""
operator|)
operator|+
literal|"'>"
expr_stmt|;
name|rmDept
operator|+=
name|department
operator|.
name|getAbbreviation
argument_list|()
expr_stmt|;
name|rmDept
operator|+=
literal|"</TD></TR>"
expr_stmt|;
name|deptRow
operator|++
expr_stmt|;
block|}
while|while
condition|(
name|deptRow
operator|<
literal|2
condition|)
block|{
name|rmLabel
operator|+=
literal|"<TR><TD><B>&nbsp;</B></TD></TR>"
expr_stmt|;
name|rmCapacity
operator|+=
literal|"<TR><TD><B>&nbsp;</B></TD></TR>"
expr_stmt|;
name|rmDept
operator|+=
literal|"<TR><TD><B>&nbsp;</B></TD></TR>"
expr_stmt|;
name|deptRow
operator|++
expr_stmt|;
block|}
name|firstRoom
operator|=
literal|false
expr_stmt|;
block|}
name|webTable
operator|.
name|addLine
argument_list|(
literal|"onClick=\"document.location='roomDeptEdit.do?doit=editRoomDept&id="
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
argument_list|,
operator|new
name|String
index|[]
block|{
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
literal|"<table width='100%' border='0' cellspacing='0' cellpadding='1'>"
operator|+
name|rmLabel
operator|+
literal|"</table>"
block|,
literal|"<table width='100%' border='0' cellspacing='0' cellpadding='1'>"
operator|+
name|rmCapacity
operator|+
literal|"</table>"
block|,
literal|"<table width='100%' border='0' cellspacing='0' cellpadding='1'>"
operator|+
name|rmDept
operator|+
literal|"</table>"
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
literal|null
block|,
literal|null
block|,
literal|null
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|webTable
operator|.
name|addLine
argument_list|(
literal|"onClick=\"document.location='roomDeptEdit.do?doit=editRoomDept&id="
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
argument_list|,
operator|new
name|String
index|[]
block|{
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
literal|"<I>No Room Currently Assigned</I>"
block|,
literal|""
block|,
literal|""
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|d
operator|.
name|getDeptCode
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
literal|null
block|,
literal|null
block|,
literal|null
block|}
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"roomDepts"
argument_list|,
name|webTable
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
literal|"roomDeptList.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 *  	 * @param user 	 * @return 	 */
specifier|private
name|Set
name|getDepts
parameter_list|(
name|User
name|user
parameter_list|)
throws|throws
name|Exception
block|{
name|boolean
name|isAdmin
init|=
name|user
operator|.
name|getRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|ADMIN_ROLE
argument_list|)
decl_stmt|;
name|Long
name|sessionId
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getUniqueId
argument_list|()
decl_stmt|;
name|String
name|mgrId
init|=
operator|(
name|String
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|TMTBL_MGR_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|TimetableManagerDAO
name|tdao
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
decl_stmt|;
name|TimetableManager
name|manager
init|=
name|tdao
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|mgrId
argument_list|)
argument_list|)
decl_stmt|;
name|Set
name|depts
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
if|if
condition|(
name|isAdmin
condition|)
block|{
name|List
name|list
init|=
name|tdao
operator|.
name|getSession
argument_list|()
operator|.
name|createCriteria
argument_list|(
name|Department
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|list
operator|!=
literal|null
condition|)
name|depts
operator|.
name|addAll
argument_list|(
name|list
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Set
name|departments
init|=
name|manager
operator|.
name|departmentsForSession
argument_list|(
name|sessionId
argument_list|)
decl_stmt|;
if|if
condition|(
name|departments
operator|!=
literal|null
condition|)
name|depts
operator|.
name|addAll
argument_list|(
name|departments
argument_list|)
expr_stmt|;
block|}
return|return
name|depts
return|;
block|}
block|}
end_class

end_unit

