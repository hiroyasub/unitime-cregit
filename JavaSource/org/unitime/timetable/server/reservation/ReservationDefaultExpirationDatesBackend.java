begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
operator|.
name|reservation
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|ParseException
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
name|Calendar
import|;
end_import

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
name|gwt
operator|.
name|shared
operator|.
name|ReservationInterface
operator|.
name|DefaultExpirationDates
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
name|ReservationInterface
operator|.
name|ReservationDefaultExpirationDatesRpcRequest
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

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|ReservationDefaultExpirationDatesRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ReservationDefaultExpirationDatesBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ReservationDefaultExpirationDatesRpcRequest
argument_list|,
name|DefaultExpirationDates
argument_list|>
block|{
specifier|private
specifier|static
name|String
index|[]
name|sTypes
init|=
operator|new
name|String
index|[]
block|{
literal|"individual"
block|,
literal|"group"
block|,
literal|"curriculum"
block|,
literal|"course"
block|}
decl_stmt|;
specifier|private
specifier|static
name|SimpleDateFormat
name|sDF
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyy-MM-dd"
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|DefaultExpirationDates
name|execute
parameter_list|(
name|ReservationDefaultExpirationDatesRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|DefaultExpirationDates
name|expirations
init|=
operator|new
name|DefaultExpirationDates
argument_list|()
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|getUser
argument_list|()
operator|!=
literal|null
operator|&&
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|sTypes
control|)
block|{
name|expirations
operator|.
name|setExpirationDate
argument_list|(
name|type
argument_list|,
name|getDefaultExpirationDate
argument_list|(
name|session
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|expirations
return|;
block|}
specifier|public
name|Date
name|getDefaultExpirationDate
parameter_list|(
name|Session
name|session
parameter_list|,
name|String
name|type
parameter_list|)
block|{
name|String
name|expirationStr
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.reservations."
operator|+
name|type
operator|+
literal|".expiration_date"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.reservations.expiration_date"
argument_list|)
argument_list|)
decl_stmt|;
name|Date
name|expiration
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|expirationStr
operator|!=
literal|null
operator|&&
operator|!
name|expirationStr
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|session
operator|.
name|getSessionBeginDateTime
argument_list|()
argument_list|)
expr_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|expirationStr
argument_list|)
argument_list|)
expr_stmt|;
name|expiration
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
try|try
block|{
name|expiration
operator|=
name|sDF
operator|.
name|parse
argument_list|(
name|expirationStr
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|f
parameter_list|)
block|{
block|}
block|}
block|}
name|String
name|expInDaysStr
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.reservations."
operator|+
name|type
operator|+
literal|".expire_in_days"
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.reservations.expire_in_days"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|expInDaysStr
operator|!=
literal|null
operator|&&
operator|!
name|expInDaysStr
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
decl_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|HOUR_OF_DAY
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MINUTE
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|SECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|set
argument_list|(
name|Calendar
operator|.
name|MILLISECOND
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|Integer
operator|.
name|parseInt
argument_list|(
name|expInDaysStr
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|expiration
operator|==
literal|null
operator|||
name|cal
operator|.
name|getTime
argument_list|()
operator|.
name|after
argument_list|(
name|expiration
argument_list|)
condition|)
name|expiration
operator|=
name|cal
operator|.
name|getTime
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
block|}
return|return
name|expiration
return|;
block|}
block|}
end_class

end_unit

