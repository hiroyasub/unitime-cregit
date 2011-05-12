begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|updates
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|Map
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Offering
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|reservation
operator|.
name|Reservation
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
name|SectioningException
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
name|SectioningExceptionType
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
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
operator|.
name|Lock
import|;
end_import

begin_class
specifier|public
class|class
name|ExpireReservationsAction
extends|extends
name|CheckOfferingAction
block|{
annotation|@
name|Override
specifier|public
name|Boolean
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|helper
operator|.
name|info
argument_list|(
literal|"Checking for expired reservations..."
argument_list|)
expr_stmt|;
name|Hashtable
argument_list|<
name|Offering
argument_list|,
name|List
argument_list|<
name|Reservation
argument_list|>
argument_list|>
name|reservations2expire
init|=
operator|new
name|Hashtable
argument_list|<
name|Offering
argument_list|,
name|List
argument_list|<
name|Reservation
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Reservation
name|expiredReservation
range|:
operator|(
name|List
argument_list|<
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Reservation
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from Reservation r where "
operator|+
literal|"r.instructionalOffering.session.uniqueId = :sessionId and "
operator|+
literal|"r.expirationDate is not null and r.expirationDate< current_timestamp()"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Offering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|expiredReservation
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|Reservation
name|reservation
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Reservation
name|r
range|:
name|offering
operator|.
name|getReservations
argument_list|()
control|)
if|if
condition|(
name|r
operator|.
name|getId
argument_list|()
operator|==
name|expiredReservation
operator|.
name|getUniqueId
argument_list|()
condition|)
block|{
name|reservation
operator|=
name|r
expr_stmt|;
break|break;
block|}
if|if
condition|(
name|reservation
operator|==
literal|null
condition|)
continue|continue;
comment|// already expired
name|List
argument_list|<
name|Reservation
argument_list|>
name|reservations
init|=
name|reservations2expire
operator|.
name|get
argument_list|(
name|offering
argument_list|)
decl_stmt|;
if|if
condition|(
name|reservations
operator|==
literal|null
condition|)
block|{
name|reservations
operator|=
operator|new
name|ArrayList
argument_list|<
name|Reservation
argument_list|>
argument_list|()
expr_stmt|;
name|reservations2expire
operator|.
name|put
argument_list|(
name|offering
argument_list|,
name|reservations
argument_list|)
expr_stmt|;
block|}
name|reservations
operator|.
name|add
argument_list|(
name|reservation
argument_list|)
expr_stmt|;
block|}
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Offering
argument_list|,
name|List
argument_list|<
name|Reservation
argument_list|>
argument_list|>
name|entry
range|:
name|reservations2expire
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|expireReservation
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
operator|.
name|getValue
argument_list|()
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|rollbackTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|SectioningException
condition|)
throw|throw
operator|(
name|SectioningException
operator|)
name|e
throw|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|SectioningExceptionType
operator|.
name|UNKNOWN
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|void
name|expireReservation
parameter_list|(
name|Offering
name|offering
parameter_list|,
name|List
argument_list|<
name|Reservation
argument_list|>
name|reservations
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
comment|// offering is locked -> assuming that the offering will get checked when it is unlocked
if|if
condition|(
name|server
operator|.
name|isOfferingLocked
argument_list|(
name|offering
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
return|return;
name|Lock
name|lock
init|=
name|server
operator|.
name|lockOffering
argument_list|(
name|offering
operator|.
name|getId
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
decl_stmt|;
try|try
block|{
comment|// Remove reservation from the model
for|for
control|(
name|Reservation
name|reservation
range|:
name|reservations
control|)
block|{
name|helper
operator|.
name|info
argument_list|(
literal|"Expiring reservation "
operator|+
name|reservation
operator|.
name|getId
argument_list|()
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|offering
operator|.
name|getReservations
argument_list|()
operator|.
name|remove
argument_list|(
name|reservation
argument_list|)
expr_stmt|;
name|offering
operator|.
name|clearReservationCache
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|isSectioningEnabled
argument_list|()
condition|)
block|{
comment|// Re-check offering
name|checkOffering
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|offering
argument_list|)
expr_stmt|;
comment|// Update enrollment counters
name|updateEnrollmentCounters
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|offering
argument_list|)
expr_stmt|;
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"expire-reservations"
return|;
block|}
block|}
end_class

end_unit

