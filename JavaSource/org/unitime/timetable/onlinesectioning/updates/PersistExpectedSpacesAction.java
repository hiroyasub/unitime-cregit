begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|text
operator|.
name|DecimalFormat
import|;
end_import

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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Config
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
name|model
operator|.
name|Section
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
name|Subpart
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
name|model
operator|.
name|Class_
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
name|SectioningInfo
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
name|OnlineSectioningAction
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
name|PersistExpectedSpacesAction
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Boolean
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
specifier|private
name|Collection
argument_list|<
name|Long
argument_list|>
name|iOfferingIds
decl_stmt|;
specifier|private
specifier|static
name|DecimalFormat
name|sDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"+0.000;-0.000"
argument_list|)
decl_stmt|;
specifier|public
name|PersistExpectedSpacesAction
parameter_list|(
name|Long
modifier|...
name|offeringIds
parameter_list|)
block|{
name|iOfferingIds
operator|=
operator|new
name|ArrayList
argument_list|<
name|Long
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|Long
name|offeringId
range|:
name|offeringIds
control|)
name|iOfferingIds
operator|.
name|add
argument_list|(
name|offeringId
argument_list|)
expr_stmt|;
block|}
specifier|public
name|PersistExpectedSpacesAction
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|offeringIds
parameter_list|)
block|{
name|iOfferingIds
operator|=
name|offeringIds
expr_stmt|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getOfferingIds
parameter_list|()
block|{
return|return
name|iOfferingIds
return|;
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
for|for
control|(
name|Long
name|offeringId
range|:
name|getOfferingIds
argument_list|()
control|)
block|{
try|try
block|{
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|persistExpectedSpaces
argument_list|(
name|offeringId
argument_list|,
literal|true
argument_list|,
name|server
argument_list|,
name|helper
argument_list|)
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
name|helper
operator|.
name|error
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
expr_stmt|;
block|}
block|}
return|return
literal|true
return|;
block|}
specifier|public
specifier|static
name|void
name|persistExpectedSpaces
parameter_list|(
name|Long
name|offeringId
parameter_list|,
name|boolean
name|needLock
parameter_list|,
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|Section
argument_list|>
name|sections
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Section
argument_list|>
argument_list|()
decl_stmt|;
name|Lock
name|lock
init|=
operator|(
name|needLock
condition|?
name|server
operator|.
name|lockOffering
argument_list|(
name|offeringId
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
else|:
literal|null
operator|)
decl_stmt|;
try|try
block|{
name|Offering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|offeringId
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
condition|)
return|return;
name|helper
operator|.
name|info
argument_list|(
literal|"Persisting expected spaces for "
operator|+
name|offering
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Config
name|config
range|:
name|offering
operator|.
name|getConfigs
argument_list|()
control|)
for|for
control|(
name|Subpart
name|subpart
range|:
name|config
operator|.
name|getSubparts
argument_list|()
control|)
for|for
control|(
name|Section
name|section
range|:
name|subpart
operator|.
name|getSections
argument_list|()
control|)
name|sections
operator|.
name|put
argument_list|(
name|section
operator|.
name|getId
argument_list|()
argument_list|,
name|section
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|lock
operator|!=
literal|null
condition|)
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|SectioningInfo
name|info
range|:
operator|(
name|List
argument_list|<
name|SectioningInfo
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select i from SectioningInfo i where i.clazz.schedulingSubpart.instrOfferingConfig.instructionalOffering = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Section
name|section
init|=
name|sections
operator|.
name|remove
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|==
literal|null
condition|)
continue|continue;
if|if
condition|(
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
operator|==
name|section
operator|.
name|getSpaceExpected
argument_list|()
operator|&&
name|info
operator|.
name|getNbrHoldingStudents
argument_list|()
operator|==
name|section
operator|.
name|getSpaceHeld
argument_list|()
condition|)
continue|continue;
name|helper
operator|.
name|debug
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": expected "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|section
operator|.
name|getSpaceExpected
argument_list|()
operator|-
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
argument_list|)
operator|+
literal|", held "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|section
operator|.
name|getSpaceHeld
argument_list|()
operator|-
name|info
operator|.
name|getNbrHoldingStudents
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
operator|&&
name|section
operator|.
name|getLimit
argument_list|()
operator|>=
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
operator|&&
name|section
operator|.
name|getLimit
argument_list|()
operator|<
name|section
operator|.
name|getSpaceExpected
argument_list|()
condition|)
name|helper
operator|.
name|info
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": become over-expected"
argument_list|)
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
operator|&&
name|section
operator|.
name|getLimit
argument_list|()
operator|<
name|info
operator|.
name|getNbrExpectedStudents
argument_list|()
operator|&&
name|section
operator|.
name|getLimit
argument_list|()
operator|>=
name|section
operator|.
name|getSpaceExpected
argument_list|()
condition|)
name|helper
operator|.
name|info
argument_list|(
name|info
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": no longer over-expected"
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrExpectedStudents
argument_list|(
name|section
operator|.
name|getSpaceExpected
argument_list|()
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrHoldingStudents
argument_list|(
name|section
operator|.
name|getSpaceHeld
argument_list|()
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|info
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|sections
operator|.
name|isEmpty
argument_list|()
condition|)
for|for
control|(
name|Class_
name|clazz
range|:
operator|(
name|List
argument_list|<
name|Class_
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from Class_ c where c.schedulingSubpart.instrOfferingConfig.instructionalOffering = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offeringId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Section
name|section
init|=
name|sections
operator|.
name|remove
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|section
operator|==
literal|null
condition|)
continue|continue;
name|SectioningInfo
name|info
init|=
operator|new
name|SectioningInfo
argument_list|()
decl_stmt|;
name|helper
operator|.
name|debug
argument_list|(
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": expected "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|section
operator|.
name|getSpaceExpected
argument_list|()
argument_list|)
operator|+
literal|", held "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|section
operator|.
name|getSpaceHeld
argument_list|()
argument_list|)
operator|+
literal|" (new)"
argument_list|)
expr_stmt|;
if|if
condition|(
name|section
operator|.
name|getLimit
argument_list|()
operator|>=
literal|0
operator|&&
name|section
operator|.
name|getLimit
argument_list|()
operator|<
name|section
operator|.
name|getSpaceExpected
argument_list|()
condition|)
name|helper
operator|.
name|info
argument_list|(
name|clazz
operator|.
name|getClassLabel
argument_list|(
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|+
literal|": become over-expected"
argument_list|)
expr_stmt|;
name|info
operator|.
name|setClazz
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrExpectedStudents
argument_list|(
name|section
operator|.
name|getSpaceExpected
argument_list|()
argument_list|)
expr_stmt|;
name|info
operator|.
name|setNbrHoldingStudents
argument_list|(
name|section
operator|.
name|getSpaceHeld
argument_list|()
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|info
argument_list|)
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
literal|"persist-expectations"
return|;
block|}
block|}
end_class

end_unit

