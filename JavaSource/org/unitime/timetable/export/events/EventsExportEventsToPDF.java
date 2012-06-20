begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|export
operator|.
name|events
package|;
end_package

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
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|export
operator|.
name|ExportHelper
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
name|export
operator|.
name|PDFPrinter
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
name|gwt
operator|.
name|client
operator|.
name|events
operator|.
name|EventComparator
operator|.
name|EventMeetingSortBy
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|EventFlag
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|MeetingInterface
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|MultiMeetingInterface
import|;
end_import

begin_class
annotation|@
name|Service
argument_list|(
literal|"org.unitime.timetable.export.Exporter:events.pdf"
argument_list|)
specifier|public
class|class
name|EventsExportEventsToPDF
extends|extends
name|EventsExporter
block|{
annotation|@
name|Override
specifier|public
name|String
name|reference
parameter_list|()
block|{
return|return
literal|"events.pdf"
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|print
parameter_list|(
name|ExportHelper
name|helper
parameter_list|,
name|List
argument_list|<
name|EventInterface
argument_list|>
name|events
parameter_list|,
name|int
name|eventCookieFlags
parameter_list|,
name|EventMeetingSortBy
name|sort
parameter_list|)
throws|throws
name|IOException
block|{
name|sort
argument_list|(
name|events
argument_list|,
name|sort
argument_list|)
expr_stmt|;
name|Printer
name|printer
init|=
operator|new
name|PDFPrinter
argument_list|(
name|helper
operator|.
name|getOutputStream
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|helper
operator|.
name|setup
argument_list|(
name|printer
operator|.
name|getContentType
argument_list|()
argument_list|,
name|reference
argument_list|()
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|hideColumns
argument_list|(
name|printer
argument_list|,
name|events
argument_list|,
name|eventCookieFlags
argument_list|)
expr_stmt|;
name|print
argument_list|(
name|printer
argument_list|,
name|events
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|hideColumn
parameter_list|(
name|Printer
name|out
parameter_list|,
name|List
argument_list|<
name|EventInterface
argument_list|>
name|events
parameter_list|,
name|EventFlag
name|flag
parameter_list|)
block|{
switch|switch
condition|(
name|flag
condition|)
block|{
case|case
name|SHOW_SECTION
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|1
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_PUBLISHED_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|4
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_ALLOCATED_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|5
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_SETUP_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|6
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_TEARDOWN_TIME
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|7
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_CAPACITY
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|9
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_ENROLLMENT
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|10
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_LIMIT
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|11
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_SPONSOR
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|12
argument_list|)
expr_stmt|;
break|break;
case|case
name|SHOW_MAIN_CONTACT
case|:
name|out
operator|.
name|hideColumn
argument_list|(
literal|13
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
specifier|protected
name|void
name|print
parameter_list|(
name|Printer
name|out
parameter_list|,
name|List
argument_list|<
name|EventInterface
argument_list|>
name|events
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|printHeader
argument_list|(
comment|/*  0 */
name|MESSAGES
operator|.
name|colName
argument_list|()
argument_list|,
comment|/*  1 */
name|MESSAGES
operator|.
name|colSection
argument_list|()
argument_list|,
comment|/*  2 */
name|MESSAGES
operator|.
name|colType
argument_list|()
argument_list|,
comment|/*  3 */
name|MESSAGES
operator|.
name|colDate
argument_list|()
argument_list|,
comment|/*  4 */
name|MESSAGES
operator|.
name|colPublishedTime
argument_list|()
argument_list|,
comment|/*  5 */
name|MESSAGES
operator|.
name|colAllocatedTime
argument_list|()
argument_list|,
comment|/*  6 */
name|MESSAGES
operator|.
name|colSetupTimeShort
argument_list|()
argument_list|,
comment|/*  7 */
name|MESSAGES
operator|.
name|colTeardownTimeShort
argument_list|()
argument_list|,
comment|/*  8 */
name|MESSAGES
operator|.
name|colLocation
argument_list|()
argument_list|,
comment|/*  9 */
name|MESSAGES
operator|.
name|colCapacity
argument_list|()
argument_list|,
comment|/* 10 */
name|MESSAGES
operator|.
name|colEnrollment
argument_list|()
argument_list|,
comment|/* 11 */
name|MESSAGES
operator|.
name|colLimit
argument_list|()
argument_list|,
comment|/* 12 */
name|MESSAGES
operator|.
name|colSponsorOrInstructor
argument_list|()
argument_list|,
comment|/* 13 */
name|MESSAGES
operator|.
name|colMainContact
argument_list|()
argument_list|,
comment|/* 14 */
name|MESSAGES
operator|.
name|colApproval
argument_list|()
argument_list|)
expr_stmt|;
name|DateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|CONSTANTS
operator|.
name|eventDateFormat
argument_list|()
argument_list|,
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|DateFormat
name|dfShort
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|CONSTANTS
operator|.
name|eventDateFormatShort
argument_list|()
argument_list|,
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|DateFormat
name|dfLong
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|CONSTANTS
operator|.
name|eventDateFormatLong
argument_list|()
argument_list|,
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|EventInterface
name|event
range|:
name|events
control|)
block|{
for|for
control|(
name|MultiMeetingInterface
name|multi
range|:
name|EventInterface
operator|.
name|getMultiMeetings
argument_list|(
name|event
operator|.
name|getMeetings
argument_list|()
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
control|)
block|{
name|MeetingInterface
name|meeting
init|=
name|multi
operator|.
name|getMeetings
argument_list|()
operator|.
name|first
argument_list|()
decl_stmt|;
name|out
operator|.
name|printLine
argument_list|(
name|getName
argument_list|(
name|event
argument_list|)
argument_list|,
name|getSection
argument_list|(
name|event
argument_list|)
argument_list|,
name|event
operator|.
name|hasInstruction
argument_list|()
condition|?
name|event
operator|.
name|getInstruction
argument_list|()
else|:
name|event
operator|.
name|getType
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|,
name|meeting
operator|.
name|isArrangeHours
argument_list|()
condition|?
name|CONSTANTS
operator|.
name|arrangeHours
argument_list|()
else|:
operator|(
name|multi
operator|.
name|getDays
argument_list|(
name|CONSTANTS
argument_list|)
operator|+
literal|" "
operator|+
operator|(
name|multi
operator|.
name|getNrMeetings
argument_list|()
operator|==
literal|1
condition|?
name|multi
operator|.
name|getFirstMeetingDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|dfLong
operator|.
name|format
argument_list|(
name|multi
operator|.
name|getFirstMeetingDate
argument_list|()
argument_list|)
else|:
name|dfShort
operator|.
name|format
argument_list|(
name|multi
operator|.
name|getFirstMeetingDate
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|dfLong
operator|.
name|format
argument_list|(
name|multi
operator|.
name|getLastMeetingDate
argument_list|()
argument_list|)
operator|)
operator|)
argument_list|,
name|meeting
operator|.
name|getMeetingTime
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|,
name|meeting
operator|.
name|getAllocatedTime
argument_list|(
name|CONSTANTS
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|meeting
operator|.
name|getStartOffset
argument_list|()
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
operator|-
name|meeting
operator|.
name|getEndOffset
argument_list|()
argument_list|)
argument_list|,
name|meeting
operator|.
name|getLocationName
argument_list|()
argument_list|,
name|meeting
operator|.
name|hasLocation
argument_list|()
operator|&&
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|hasSize
argument_list|()
condition|?
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getSize
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasEnrollment
argument_list|()
condition|?
name|event
operator|.
name|getEnrollment
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasMaxCapacity
argument_list|()
condition|?
name|event
operator|.
name|getMaxCapacity
argument_list|()
operator|.
name|toString
argument_list|()
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasInstructors
argument_list|()
condition|?
name|event
operator|.
name|getInstructorNames
argument_list|(
literal|"\n"
argument_list|)
else|:
name|event
operator|.
name|hasSponsor
argument_list|()
condition|?
name|event
operator|.
name|getSponsor
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|,
name|event
operator|.
name|hasContact
argument_list|()
condition|?
name|event
operator|.
name|getContact
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|null
argument_list|,
name|multi
operator|.
name|isArrangeHours
argument_list|()
condition|?
literal|""
else|:
name|multi
operator|.
name|isApproved
argument_list|()
condition|?
name|df
operator|.
name|format
argument_list|(
name|multi
operator|.
name|getApprovalDate
argument_list|()
argument_list|)
else|:
name|MESSAGES
operator|.
name|approvalNotApproved
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

