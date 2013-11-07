begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|org
operator|.
name|joda
operator|.
name|time
operator|.
name|DateTimeZone
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
name|widgets
operator|.
name|ServerDateTimeFormat
operator|.
name|ServerTimeZoneRequest
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
name|widgets
operator|.
name|ServerDateTimeFormat
operator|.
name|ServerTimeZoneResponse
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
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|dao
operator|.
name|SessionDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|ServerTimeZoneRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ServerTimeZoneBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ServerTimeZoneRequest
argument_list|,
name|ServerTimeZoneResponse
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|ServerTimeZoneResponse
name|execute
parameter_list|(
name|ServerTimeZoneRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|Date
name|first
init|=
literal|null
decl_stmt|,
name|last
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Session
name|session
range|:
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
block|{
if|if
condition|(
name|first
operator|==
literal|null
operator|||
name|first
operator|.
name|after
argument_list|(
name|session
operator|.
name|getEventBeginDate
argument_list|()
argument_list|)
condition|)
name|first
operator|=
name|session
operator|.
name|getEventBeginDate
argument_list|()
expr_stmt|;
if|if
condition|(
name|last
operator|==
literal|null
operator|||
name|last
operator|.
name|before
argument_list|(
name|session
operator|.
name|getEventEndDate
argument_list|()
argument_list|)
condition|)
name|last
operator|=
name|session
operator|.
name|getEventEndDate
argument_list|()
expr_stmt|;
block|}
name|DateTimeZone
name|zone
init|=
name|DateTimeZone
operator|.
name|getDefault
argument_list|()
decl_stmt|;
name|int
name|offsetInMinutes
init|=
name|zone
operator|.
name|getOffset
argument_list|(
name|first
operator|.
name|getTime
argument_list|()
argument_list|)
operator|/
literal|60000
decl_stmt|;
name|ServerTimeZoneResponse
name|ret
init|=
operator|new
name|ServerTimeZoneResponse
argument_list|()
decl_stmt|;
name|ret
operator|.
name|setId
argument_list|(
name|zone
operator|.
name|getID
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addName
argument_list|(
name|zone
operator|.
name|getName
argument_list|(
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|ret
operator|.
name|setTimeZoneOffsetInMinutes
argument_list|(
name|offsetInMinutes
argument_list|)
expr_stmt|;
name|long
name|time
init|=
name|first
operator|.
name|getTime
argument_list|()
decl_stmt|;
name|long
name|transition
decl_stmt|;
while|while
condition|(
name|time
operator|!=
operator|(
name|transition
operator|=
name|zone
operator|.
name|nextTransition
argument_list|(
name|time
argument_list|)
operator|)
operator|&&
name|time
operator|<
name|last
operator|.
name|getTime
argument_list|()
condition|)
block|{
name|int
name|adjustment
init|=
operator|(
name|zone
operator|.
name|getOffset
argument_list|(
name|transition
argument_list|)
operator|/
literal|60000
operator|)
operator|-
name|offsetInMinutes
decl_stmt|;
name|ret
operator|.
name|addTransition
argument_list|(
operator|(
name|int
operator|)
operator|(
name|transition
operator|/
literal|3600000
operator|)
argument_list|,
name|adjustment
argument_list|)
expr_stmt|;
name|time
operator|=
name|transition
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

