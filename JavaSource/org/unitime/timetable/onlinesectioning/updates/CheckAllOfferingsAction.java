begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|List
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
name|resources
operator|.
name|StudentSectioningMessages
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
name|model
operator|.
name|XConfig
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
name|model
operator|.
name|XCourseRequest
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
name|model
operator|.
name|XEnrollment
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
name|model
operator|.
name|XOffering
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
name|model
operator|.
name|XRequest
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
name|model
operator|.
name|XSection
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
name|model
operator|.
name|XStudent
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
name|server
operator|.
name|CheckMaster
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
name|server
operator|.
name|CheckMaster
operator|.
name|Master
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
class|class
name|CheckAllOfferingsAction
extends|extends
name|CheckOfferingAction
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|CheckAllOfferingsAction
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
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
name|List
argument_list|<
name|Long
argument_list|>
name|offeringIds
init|=
literal|null
decl_stmt|;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|offeringIds
operator|=
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select io.uniqueId from InstructionalOffering io "
operator|+
literal|"where io.session.uniqueId = :sessionId and io.notOffered = false"
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
expr_stmt|;
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
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
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
name|helper
operator|.
name|info
argument_list|(
literal|"Checking all offerings for "
operator|+
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|Lock
name|lock
init|=
name|server
operator|.
name|lockAll
argument_list|()
decl_stmt|;
try|try
block|{
for|for
control|(
name|Long
name|offeringId
range|:
name|offeringIds
control|)
name|checkOffering
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|server
operator|.
name|getOffering
argument_list|(
name|offeringId
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
name|helper
operator|.
name|info
argument_list|(
literal|"Updating enrollment counts..."
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"Check done."
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|check
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|XStudent
name|student
parameter_list|,
name|XOffering
name|offering
parameter_list|,
name|XCourseRequest
name|request
parameter_list|)
block|{
if|if
condition|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|true
return|;
if|if
condition|(
operator|!
name|offering
operator|.
name|getOfferingId
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getOfferingId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
name|List
argument_list|<
name|XSection
argument_list|>
name|sections
init|=
name|offering
operator|.
name|getSections
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
argument_list|)
decl_stmt|;
name|XConfig
name|config
init|=
name|offering
operator|.
name|getConfig
argument_list|(
name|request
operator|.
name|getEnrollment
argument_list|()
operator|.
name|getConfigId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|==
literal|null
operator|||
name|sections
operator|.
name|size
argument_list|()
operator|!=
name|config
operator|.
name|getSubparts
argument_list|()
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|XSection
name|s1
range|:
name|sections
control|)
block|{
for|for
control|(
name|XSection
name|s2
range|:
name|sections
control|)
block|{
if|if
condition|(
name|s1
operator|.
name|getSectionId
argument_list|()
operator|<
name|s2
operator|.
name|getSectionId
argument_list|()
operator|&&
name|s1
operator|.
name|isOverlapping
argument_list|(
name|offering
operator|.
name|getDistributions
argument_list|()
argument_list|,
name|s2
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|s1
operator|.
name|getSectionId
argument_list|()
operator|.
name|equals
argument_list|(
name|s2
operator|.
name|getSectionId
argument_list|()
argument_list|)
operator|&&
name|s1
operator|.
name|getSubpartId
argument_list|()
operator|.
name|equals
argument_list|(
name|s2
operator|.
name|getSubpartId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|offering
operator|.
name|getSubpart
argument_list|(
name|s1
operator|.
name|getSubpartId
argument_list|()
argument_list|)
operator|.
name|getConfigId
argument_list|()
operator|.
name|equals
argument_list|(
name|config
operator|.
name|getConfigId
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
for|for
control|(
name|XRequest
name|r
range|:
name|student
operator|.
name|getRequests
argument_list|()
control|)
if|if
condition|(
name|r
operator|instanceof
name|XCourseRequest
operator|&&
operator|!
name|r
operator|.
name|getRequestId
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getRequestId
argument_list|()
argument_list|)
operator|&&
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|getEnrollment
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|XEnrollment
name|e
init|=
operator|(
operator|(
name|XCourseRequest
operator|)
name|r
operator|)
operator|.
name|getEnrollment
argument_list|()
decl_stmt|;
name|XOffering
name|other
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|e
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|other
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|XSection
argument_list|>
name|assignment
init|=
name|other
operator|.
name|getSections
argument_list|(
name|e
argument_list|)
decl_stmt|;
for|for
control|(
name|XSection
name|section
range|:
name|sections
control|)
if|if
condition|(
name|section
operator|.
name|isOverlapping
argument_list|(
name|offering
operator|.
name|getDistributions
argument_list|()
argument_list|,
name|assignment
argument_list|)
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|isAlternative
argument_list|()
operator|&&
operator|!
name|r
operator|.
name|isAlternative
argument_list|()
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|request
operator|.
name|isAlternative
argument_list|()
operator|==
name|r
operator|.
name|isAlternative
argument_list|()
operator|&&
name|request
operator|.
name|getPriority
argument_list|()
operator|>
name|r
operator|.
name|getPriority
argument_list|()
condition|)
return|return
literal|false
return|;
block|}
block|}
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

