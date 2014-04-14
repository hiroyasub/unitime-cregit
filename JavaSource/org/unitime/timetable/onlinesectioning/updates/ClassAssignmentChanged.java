begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|dao
operator|.
name|Class_DAO
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
name|OnlineSectioningLog
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
name|XSubpart
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
name|ClassAssignmentChanged
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
name|iClassIds
init|=
literal|null
decl_stmt|;
specifier|public
name|ClassAssignmentChanged
name|forClasses
parameter_list|(
name|Long
modifier|...
name|classIds
parameter_list|)
block|{
name|iClassIds
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
name|classId
range|:
name|classIds
control|)
name|iClassIds
operator|.
name|add
argument_list|(
name|classId
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ClassAssignmentChanged
name|forClasses
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|classIds
parameter_list|)
block|{
name|iClassIds
operator|=
name|classIds
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getClassIds
parameter_list|()
block|{
return|return
name|iClassIds
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
name|helper
operator|.
name|info
argument_list|(
name|getClassIds
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|" class assignments changed."
argument_list|)
expr_stmt|;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|previous
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|PREVIOUS
argument_list|)
decl_stmt|;
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|Builder
name|stored
init|=
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|newBuilder
argument_list|()
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Enrollment
operator|.
name|EnrollmentType
operator|.
name|STORED
argument_list|)
decl_stmt|;
for|for
control|(
name|Long
name|classId
range|:
name|getClassIds
argument_list|()
control|)
block|{
name|Class_
name|clazz
init|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|classId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Class "
operator|+
name|classId
operator|+
literal|" wos deleted -- unsupported operation (use reload offering instead)."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|Lock
name|lock
init|=
name|server
operator|.
name|lockOffering
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
operator|(
name|List
argument_list|<
name|Long
argument_list|>
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select e.student.uniqueId from StudentClassEnrollment e where "
operator|+
literal|"e.clazz.uniqueId = :classId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"classId"
argument_list|,
name|classId
argument_list|)
operator|.
name|list
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
try|try
block|{
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|XSection
name|oldSection
init|=
operator|(
name|offering
operator|==
literal|null
condition|?
literal|null
else|:
name|offering
operator|.
name|getSection
argument_list|(
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|oldSection
operator|==
literal|null
condition|)
block|{
name|helper
operator|.
name|warn
argument_list|(
literal|"Class "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|()
operator|+
literal|" was added -- unsupported operation (use reload offering instead)."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|previous
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|oldSection
argument_list|)
argument_list|)
expr_stmt|;
name|helper
operator|.
name|info
argument_list|(
literal|"Reloading "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
name|XSection
name|newSection
init|=
operator|new
name|XSection
argument_list|(
name|clazz
argument_list|,
name|helper
argument_list|)
decl_stmt|;
name|XSubpart
name|subpart
init|=
name|offering
operator|.
name|getSubpart
argument_list|(
name|newSection
operator|.
name|getSubpartId
argument_list|()
argument_list|)
decl_stmt|;
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|remove
argument_list|(
name|oldSection
argument_list|)
expr_stmt|;
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|add
argument_list|(
name|newSection
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|subpart
operator|.
name|getSections
argument_list|()
argument_list|)
expr_stmt|;
name|server
operator|.
name|update
argument_list|(
name|offering
argument_list|)
expr_stmt|;
name|stored
operator|.
name|addSection
argument_list|(
name|OnlineSectioningHelper
operator|.
name|toProto
argument_list|(
name|newSection
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
block|}
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addEnrollment
argument_list|(
name|previous
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addEnrollment
argument_list|(
name|stored
argument_list|)
expr_stmt|;
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
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
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"class-reassigned"
return|;
block|}
block|}
end_class

end_unit

