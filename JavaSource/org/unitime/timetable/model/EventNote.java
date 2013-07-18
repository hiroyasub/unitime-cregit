begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*   * UniTime 3.1 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2008 - 2010, UniTime LLC  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *   */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Event
operator|.
name|MultiMeeting
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
name|base
operator|.
name|BaseEventNote
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
name|Formats
import|;
end_import

begin_class
specifier|public
class|class
name|EventNote
extends|extends
name|BaseEventNote
implements|implements
name|Comparable
argument_list|<
name|EventNote
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|EventNote
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|EventNote
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeCreateEvent
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeAddMeetings
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeApproval
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeRejection
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeDeletion
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeEditEvent
init|=
literal|5
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeInquire
init|=
literal|6
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeCancel
init|=
literal|7
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sEventNoteTypeEmail
init|=
literal|8
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|sEventNoteTypeBgColor
init|=
operator|new
name|String
index|[]
block|{
literal|"transparent"
block|,
literal|"transparent"
block|,
literal|"#D7FFD7"
block|,
literal|"#FFD7D7"
block|,
literal|"transparent"
block|,
literal|"transparent"
block|,
literal|"#FFFFD7"
block|,
literal|"transparent"
block|}
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|sEventNoteTypeName
init|=
operator|new
name|String
index|[]
block|{
literal|"Create"
block|,
literal|"Update"
block|,
literal|"Approve"
block|,
literal|"Reject"
block|,
literal|"Delete"
block|,
literal|"Edit"
block|,
literal|"Inquire"
block|,
literal|"Cancel"
block|,
literal|"Email"
block|}
decl_stmt|;
specifier|public
name|int
name|compareTo
parameter_list|(
name|EventNote
name|n
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getTimeStamp
argument_list|()
operator|.
name|compareTo
argument_list|(
name|n
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|n
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setMeetingCollection
parameter_list|(
name|Collection
name|meetings
parameter_list|)
block|{
name|String
name|meetingStr
init|=
literal|""
decl_stmt|;
for|for
control|(
name|MultiMeeting
name|m
range|:
name|Event
operator|.
name|getMultiMeetings
argument_list|(
name|meetings
argument_list|)
control|)
block|{
if|if
condition|(
name|meetingStr
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|meetingStr
operator|+=
literal|"\n"
expr_stmt|;
name|meetingStr
operator|+=
name|m
operator|.
name|toShortString
argument_list|()
expr_stmt|;
block|}
name|setMeetings
argument_list|(
name|meetingStr
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getMeetingsHtml
parameter_list|()
block|{
if|if
condition|(
name|getMeetings
argument_list|()
operator|==
literal|null
operator|||
name|getMeetings
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|"<i>N/A</i>"
return|;
return|return
name|getMeetings
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
return|;
block|}
specifier|public
name|String
name|toHtmlString
parameter_list|(
name|boolean
name|includeUser
parameter_list|)
block|{
return|return
literal|"<tr style=\"background-color:"
operator|+
name|sEventNoteTypeBgColor
index|[
name|getNoteType
argument_list|()
index|]
operator|+
literal|";\" valign='top' "
operator|+
literal|"onMouseOver=\"this.style.backgroundColor='rgb(223,231,242)';\" "
operator|+
literal|"onMouseOut=\"this.style.backgroundColor='"
operator|+
name|sEventNoteTypeBgColor
index|[
name|getNoteType
argument_list|()
index|]
operator|+
literal|"';\">"
operator|+
literal|"<td>"
operator|+
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_TIME_STAMP
argument_list|)
operator|.
name|format
argument_list|(
name|getTimeStamp
argument_list|()
argument_list|)
operator|+
literal|"</td>"
operator|+
operator|(
name|includeUser
condition|?
literal|"<td>"
operator|+
operator|(
name|getUser
argument_list|()
operator|==
literal|null
operator|||
name|getUser
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"<i>N/A</i>"
else|:
name|getUser
argument_list|()
operator|)
operator|+
literal|"</td>"
else|:
literal|""
operator|)
operator|+
literal|"<td>"
operator|+
name|sEventNoteTypeName
index|[
name|getNoteType
argument_list|()
index|]
operator|+
literal|"</td>"
operator|+
literal|"<td>"
operator|+
name|getMeetingsHtml
argument_list|()
operator|+
literal|"</td>"
operator|+
literal|"<td>"
operator|+
operator|(
name|getTextNote
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getTextNote
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
operator|)
operator|+
literal|"</td>"
operator|+
literal|"</tr>"
return|;
block|}
block|}
end_class

end_unit

