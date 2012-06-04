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
name|events
package|;
end_package

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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcResponse
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
name|command
operator|.
name|server
operator|.
name|GwtRpcHelper
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|resources
operator|.
name|GwtConstants
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
name|resources
operator|.
name|GwtMessages
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
name|EventRpcRequest
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
name|MeetingConglictInterface
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
name|model
operator|.
name|Meeting
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|EventAction
parameter_list|<
name|T
extends|extends
name|EventRpcRequest
parameter_list|<
name|R
parameter_list|>
parameter_list|,
name|R
extends|extends
name|GwtRpcResponse
parameter_list|>
implements|implements
name|GwtRpcImplementation
argument_list|<
name|T
argument_list|,
name|R
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|DateFormat
name|sMeetingDateFormat
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
annotation|@
name|Override
specifier|public
name|R
name|execute
parameter_list|(
name|T
name|request
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|)
block|{
comment|// Create event rights
name|EventRights
name|rights
init|=
name|createEventRights
argument_list|(
name|request
argument_list|,
name|helper
argument_list|)
decl_stmt|;
comment|// Check basic access
name|rights
operator|.
name|checkAccess
argument_list|()
expr_stmt|;
comment|// Execute action
return|return
name|execute
argument_list|(
name|request
argument_list|,
name|helper
argument_list|,
name|rights
argument_list|)
return|;
block|}
specifier|public
specifier|abstract
name|R
name|execute
parameter_list|(
name|T
name|request
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|,
name|EventRights
name|rights
parameter_list|)
function_decl|;
specifier|protected
name|EventRights
name|createEventRights
parameter_list|(
name|T
name|request
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|)
block|{
return|return
operator|new
name|SimpleEventRights
argument_list|(
name|helper
argument_list|,
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|String
name|toString
parameter_list|(
name|MeetingInterface
name|meeting
parameter_list|)
block|{
return|return
operator|(
name|meeting
operator|instanceof
name|MeetingConglictInterface
condition|?
operator|(
operator|(
name|MeetingConglictInterface
operator|)
name|meeting
operator|)
operator|.
name|getName
argument_list|()
operator|+
literal|" "
else|:
literal|""
operator|)
operator|+
operator|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sMeetingDateFormat
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|)
operator|+
name|meeting
operator|.
name|getAllocatedTime
argument_list|(
name|CONSTANTS
argument_list|)
operator|+
operator|(
name|meeting
operator|.
name|hasLocation
argument_list|()
condition|?
literal|" "
operator|+
name|meeting
operator|.
name|getLocationName
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
specifier|protected
specifier|static
name|String
name|toString
parameter_list|(
name|Meeting
name|meeting
parameter_list|)
block|{
return|return
operator|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|sMeetingDateFormat
operator|.
name|format
argument_list|(
name|meeting
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
operator|+
literal|" "
operator|)
operator|+
name|time2string
argument_list|(
name|meeting
operator|.
name|getStartPeriod
argument_list|()
argument_list|,
literal|0
argument_list|)
operator|+
literal|" - "
operator|+
name|time2string
argument_list|(
name|meeting
operator|.
name|getStopPeriod
argument_list|()
argument_list|,
literal|0
argument_list|)
operator|+
operator|(
name|meeting
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|" "
operator|+
name|meeting
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
specifier|protected
specifier|static
name|String
name|time2string
parameter_list|(
name|int
name|slot
parameter_list|,
name|int
name|offset
parameter_list|)
block|{
name|int
name|min
init|=
literal|5
operator|*
name|slot
operator|+
name|offset
decl_stmt|;
if|if
condition|(
name|min
operator|==
literal|0
operator|||
name|min
operator|==
literal|1440
condition|)
return|return
name|CONSTANTS
operator|.
name|timeMidnitgh
argument_list|()
return|;
if|if
condition|(
name|min
operator|==
literal|720
condition|)
return|return
name|CONSTANTS
operator|.
name|timeNoon
argument_list|()
return|;
name|int
name|h
init|=
name|min
operator|/
literal|60
decl_stmt|;
name|int
name|m
init|=
name|min
operator|%
literal|60
decl_stmt|;
if|if
condition|(
name|CONSTANTS
operator|.
name|useAmPm
argument_list|()
condition|)
block|{
return|return
operator|(
name|h
operator|>
literal|12
condition|?
name|h
operator|-
literal|12
else|:
name|h
operator|)
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
operator|+
operator|(
name|h
operator|==
literal|24
condition|?
literal|"a"
else|:
name|h
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
return|;
block|}
else|else
block|{
return|return
name|h
operator|+
literal|":"
operator|+
operator|(
name|m
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|m
return|;
block|}
block|}
block|}
end_class

end_unit

