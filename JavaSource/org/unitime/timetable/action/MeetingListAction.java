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
name|io
operator|.
name|File
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
name|timetable
operator|.
name|form
operator|.
name|EventListForm
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
name|ComboBoxLookup
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
name|CalendarEventTableBuilder
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
name|CsvEventTableBuilder
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
annotation|@
name|Service
argument_list|(
literal|"/meetingList"
argument_list|)
specifier|public
class|class
name|MeetingListAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
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
name|sessionContext
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|Right
operator|.
name|Events
argument_list|)
expr_stmt|;
name|Vector
argument_list|<
name|ComboBoxLookup
argument_list|>
name|modes
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|modes
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"My Events"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|EventListForm
operator|.
name|sModeMyEvents
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|!=
literal|null
operator|&&
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|hasRight
argument_list|(
name|Right
operator|.
name|EventMeetingApprove
argument_list|)
condition|)
name|modes
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"Events Awaiting My Approval"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|EventListForm
operator|.
name|sModeEvents4Approval
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|modes
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"All Events"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|EventListForm
operator|.
name|sModeAllEvents
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|HasRole
argument_list|)
condition|)
block|{
name|modes
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"All Approved Events"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|EventListForm
operator|.
name|sModeAllApprovedEvents
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|modes
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"All Events Awaiting Approval"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|EventListForm
operator|.
name|sModeAllEventsWaitingApproval
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|modes
operator|.
name|add
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
literal|"All Conflicting Events"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|EventListForm
operator|.
name|sModeAllConflictingEvents
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"modes"
argument_list|,
name|modes
argument_list|)
expr_stmt|;
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
operator|!
operator|(
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
operator|||
literal|"Add Event"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"iCalendar"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|||
literal|"Export CSV"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
operator|)
condition|)
block|{
name|op
operator|=
literal|null
expr_stmt|;
block|}
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
operator|||
literal|"Export CSV"
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
name|sessionContext
argument_list|)
expr_stmt|;
block|}
else|else
name|myForm
operator|.
name|load
argument_list|(
name|sessionContext
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
name|sessionContext
argument_list|,
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
literal|"Export CSV"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|File
name|csvFile
init|=
operator|new
name|CsvEventTableBuilder
argument_list|()
operator|.
name|csvTableForMeetings
argument_list|(
name|sessionContext
argument_list|,
name|myForm
argument_list|)
decl_stmt|;
if|if
condition|(
name|csvFile
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
name|csvFile
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"iCalendar"
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|String
name|url
init|=
operator|new
name|CalendarEventTableBuilder
argument_list|()
operator|.
name|calendarUrlForMeetings
argument_list|(
name|sessionContext
argument_list|,
name|myForm
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
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
name|url
argument_list|)
expr_stmt|;
comment|/*             File pdfFile = new CalendarEventTableBuilder().calendarTableForMeetings(myForm);             if (pdfFile!=null) request.setAttribute(Constants.REQUEST_OPEN_URL, "temp/"+pdfFile.getName());             */
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

