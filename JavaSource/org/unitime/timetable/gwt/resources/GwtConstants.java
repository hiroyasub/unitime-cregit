begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|gwt
operator|.
name|resources
package|;
end_package

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|Constants
import|;
end_import

begin_interface
specifier|public
interface|interface
name|GwtConstants
extends|extends
name|Constants
block|{
annotation|@
name|DefaultStringValue
argument_list|(
literal|"3.4"
argument_list|)
name|String
name|version
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"&copy; 2008 - 2012 UniTime LLC"
argument_list|)
name|String
name|copyright
parameter_list|()
function_decl|;
annotation|@
name|DefaultBooleanValue
argument_list|(
literal|true
argument_list|)
name|boolean
name|useAmPm
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Mon"
block|,
literal|"Tue"
block|,
literal|"Wed"
block|,
literal|"Thu"
block|,
literal|"Fri"
block|,
literal|"Sat"
block|,
literal|"Sun"
block|}
argument_list|)
name|String
index|[]
name|days
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd/yyyy"
argument_list|)
name|String
name|eventDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"EEE, MM/dd/yyyy"
argument_list|)
name|String
name|meetingDateFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringValue
argument_list|(
literal|"MM/dd hh:mmaa"
argument_list|)
name|String
name|timeStampFormat
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|3
argument_list|)
name|int
name|eventSlotIncrement
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|90
argument_list|)
name|int
name|eventStartDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|210
argument_list|)
name|int
name|eventStopDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|12
argument_list|)
name|int
name|eventLengthDefault
parameter_list|()
function_decl|;
annotation|@
name|DefaultIntValue
argument_list|(
literal|10000
argument_list|)
name|int
name|maxMeetings
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"Monday"
block|,
literal|"Tuesday"
block|,
literal|"Wednesday"
block|,
literal|"Thursday"
block|,
literal|"Friday"
block|,
literal|"Saturday"
block|,
literal|"Sunday"
block|}
argument_list|)
name|String
index|[]
name|longDays
parameter_list|()
function_decl|;
annotation|@
name|DefaultStringArrayValue
argument_list|(
block|{
literal|"blue"
block|,
literal|"green"
block|,
literal|"orange"
block|,
literal|"yellow"
block|,
literal|"pink"
block|,
literal|"purple"
block|,
literal|"teal"
block|,
literal|"darkpurple"
block|,
literal|"steelblue"
block|,
literal|"lightblue"
block|,
literal|"lightgreen"
block|,
literal|"yellowgreen"
block|,
literal|"redorange"
block|,
literal|"lightbrown"
block|,
literal|"lightpurple"
block|,
literal|"grey"
block|,
literal|"bluegrey"
block|,
literal|"lightteal"
block|,
literal|"yellowgrey"
block|,
literal|"brown"
block|,
literal|"red"
block|}
argument_list|)
name|String
index|[]
name|meetingColors
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

