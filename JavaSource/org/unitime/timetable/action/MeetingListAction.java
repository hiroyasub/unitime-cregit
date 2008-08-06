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
name|MeetingListForm
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
name|pdf
operator|.
name|PdfEventTableBuilder
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|MeetingListAction
extends|extends
name|Action
block|{
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
name|MeetingListForm
name|myForm
init|=
operator|(
name|MeetingListForm
operator|)
name|form
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
name|TimetableManager
name|manager
init|=
operator|(
name|user
operator|==
literal|null
condition|?
literal|null
else|:
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|user
operator|==
literal|null
operator|||
operator|!
name|TimetableManager
operator|.
name|canSeeEvents
argument_list|(
name|user
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
name|String
name|op
init|=
operator|(
name|myForm
operator|.
name|getOp
argument_list|()
operator|!=
literal|null
condition|?
name|myForm
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
operator|)
decl_stmt|;
if|if
condition|(
literal|"Search"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|ActionMessages
name|errors
init|=
name|myForm
operator|.
name|validate
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|errors
operator|.
name|isEmpty
argument_list|()
condition|)
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
else|else
name|myForm
operator|.
name|save
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
name|myForm
operator|.
name|load
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"Add Event"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"addEvent"
argument_list|)
return|;
block|}
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|File
name|pdfFile
init|=
operator|new
name|PdfEventTableBuilder
argument_list|()
operator|.
name|pdfTableForMeetings
argument_list|(
name|myForm
argument_list|)
decl_stmt|;
if|if
condition|(
name|pdfFile
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_OPEN_URL
argument_list|,
literal|"temp/"
operator|+
name|pdfFile
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"hash"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"meetingList.do"
argument_list|,
literal|"Meetings"
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
literal|"show"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

