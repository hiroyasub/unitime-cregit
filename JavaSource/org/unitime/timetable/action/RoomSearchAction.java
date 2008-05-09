begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|RoomListForm
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
name|Exam
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
name|util
operator|.
name|LookupTables
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 06-06-2006  *   * XDoclet definition:  * @struts.action path="/roomSearch" name="roomListForm" input="/admin/roomSearch.jsp" scope="request"  * @struts.action-forward name="showRoomList" path="roomListTile"  * @struts.action-forward name="roomList" path="/roomList.do"  * @struts.action-forward name="showRoomSearch" path="roomSearchTile"  */
end_comment

begin_class
specifier|public
class|class
name|RoomSearchAction
extends|extends
name|Action
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 * @throws Exception  	 */
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
name|RoomListForm
name|roomListForm
init|=
operator|(
name|RoomListForm
operator|)
name|form
decl_stmt|;
comment|//Check permissions
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
name|Long
name|sessionId
init|=
operator|(
name|Long
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SESSION_ID_ATTR_NAME
argument_list|)
decl_stmt|;
comment|// Check if dept code saved to session
name|Object
name|dc
init|=
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
decl_stmt|;
if|if
condition|(
name|dc
operator|==
literal|null
condition|)
name|dc
operator|=
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|)
expr_stmt|;
if|if
condition|(
name|dc
operator|==
literal|null
condition|)
block|{
name|dc
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"default"
argument_list|)
expr_stmt|;
if|if
condition|(
name|dc
operator|!=
literal|null
condition|)
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|,
name|dc
argument_list|)
expr_stmt|;
block|}
name|String
name|deptCode
init|=
literal|""
decl_stmt|;
comment|// Dept code is saved to the session - go to instructor list
if|if
condition|(
name|dc
operator|!=
literal|null
operator|&&
operator|!
name|Constants
operator|.
name|BLANK_OPTION_VALUE
operator|.
name|equals
argument_list|(
name|dc
argument_list|)
condition|)
block|{
name|deptCode
operator|=
name|dc
operator|.
name|toString
argument_list|()
expr_stmt|;
name|roomListForm
operator|.
name|setDeptCodeX
argument_list|(
name|deptCode
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"All"
argument_list|)
operator|&&
operator|!
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"Exam"
argument_list|)
operator|&&
operator|!
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"EExam"
argument_list|)
condition|)
block|{
if|if
condition|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
operator|.
name|getRoomsFast
argument_list|(
operator|new
name|String
index|[]
block|{
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
block|}
block_content|)
block|.size(
block_content|)
block|== 0
block_content|)
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"searchResult"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No rooms for the selected department were found."
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
end_class

begin_decl_stmt
name|int
name|examType
init|=
operator|-
literal|1
decl_stmt|;
end_decl_stmt

begin_if_stmt
if|if
condition|(
literal|"Exam"
operator|.
name|equals
argument_list|(
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
argument_list|)
condition|)
name|examType
operator|=
name|Exam
operator|.
name|sExamTypeFinal
expr_stmt|;
end_if_stmt

begin_if_stmt
if|if
condition|(
literal|"EExam"
operator|.
name|equals
argument_list|(
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
argument_list|)
condition|)
name|examType
operator|=
name|Exam
operator|.
name|sExamTypeMidterm
expr_stmt|;
end_if_stmt

begin_if_stmt
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
argument_list|)
condition|)
block|{
name|RoomListAction
operator|.
name|buildPdfWebTable
argument_list|(
name|request
argument_list|,
name|roomListForm
argument_list|,
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
name|SETTINGS_ROOMS_FEATURES_ONE_COLUMN
argument_list|)
argument_list|)
argument_list|,
name|examType
argument_list|)
expr_stmt|;
block|}
end_if_stmt

begin_return
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"roomList"
argument_list|)
return|;
end_return

begin_block
unit|} else
block|{
comment|// No session attribute found - Load dept code
name|LookupTables
operator|.
name|setupDeptsForUser
argument_list|(
name|request
argument_list|,
name|user
argument_list|,
name|sessionId
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|TimetableManager
name|owner
init|=
operator|new
name|TimetableManagerDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
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
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|owner
operator|.
name|isExternalManager
argument_list|()
condition|)
block|{
name|Set
name|depts
init|=
name|Department
operator|.
name|findAllOwned
argument_list|(
name|sessionId
argument_list|,
name|owner
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|depts
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|roomListForm
operator|.
name|setDeptCodeX
argument_list|(
operator|(
operator|(
name|Department
operator|)
name|depts
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|,
name|roomListForm
operator|.
name|getDeptCodeX
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"roomList"
argument_list|)
return|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomSearch"
argument_list|)
return|;
block|}
end_block

unit|} 	 }
end_unit

