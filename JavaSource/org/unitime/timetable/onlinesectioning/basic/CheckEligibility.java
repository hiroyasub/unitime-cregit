begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|basic
package|;
end_package

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
name|OnlineSectioningInterface
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
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
operator|.
name|EligibilityFlag
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
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
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
name|Student
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
name|StudentSectioningStatus
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
name|StudentDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CheckEligibility
implements|implements
name|OnlineSectioningAction
argument_list|<
name|OnlineSectioningInterface
operator|.
name|EligibilityCheck
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
name|Long
name|iStudentId
decl_stmt|;
specifier|private
name|EligibilityCheck
name|iCheck
decl_stmt|;
specifier|public
name|CheckEligibility
name|forStudent
parameter_list|(
name|Long
name|studentId
parameter_list|)
block|{
name|iStudentId
operator|=
name|studentId
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|CheckEligibility
name|withCheck
parameter_list|(
name|EligibilityCheck
name|check
parameter_list|)
block|{
name|iCheck
operator|=
name|check
expr_stmt|;
return|return
name|this
return|;
block|}
annotation|@
name|Override
specifier|public
name|EligibilityCheck
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
if|if
condition|(
name|iCheck
operator|==
literal|null
condition|)
name|iCheck
operator|=
operator|new
name|EligibilityCheck
argument_list|()
expr_stmt|;
name|Lock
name|lock
init|=
name|server
operator|.
name|readLock
argument_list|()
decl_stmt|;
try|try
block|{
comment|// Always allow for the assistant mode
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_USE_ASSISTANT
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|Student
name|student
init|=
operator|(
name|iStudentId
operator|==
literal|null
condition|?
literal|null
else|:
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|iStudentId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADMIN
argument_list|)
operator|&&
operator|!
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADVISOR
argument_list|)
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
name|MSG
operator|.
name|exceptionEnrollNotStudent
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|iCheck
return|;
block|}
name|StudentSectioningStatus
name|status
init|=
name|student
operator|.
name|getSectioningStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|null
condition|)
name|status
operator|=
name|student
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
expr_stmt|;
name|boolean
name|disabled
init|=
operator|(
name|status
operator|!=
literal|null
operator|&&
operator|!
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|enabled
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|disabled
operator|&&
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADMIN
argument_list|)
condition|)
name|disabled
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|disabled
operator|&&
name|status
operator|.
name|hasOption
argument_list|(
name|StudentSectioningStatus
operator|.
name|Option
operator|.
name|advisor
argument_list|)
operator|&&
name|iCheck
operator|.
name|hasFlag
argument_list|(
name|EligibilityFlag
operator|.
name|IS_ADVISOR
argument_list|)
condition|)
name|disabled
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|disabled
condition|)
block|{
if|if
condition|(
name|status
operator|.
name|getMessage
argument_list|()
operator|==
literal|null
condition|)
name|iCheck
operator|.
name|setMessage
argument_list|(
name|MSG
operator|.
name|exceptionEnrollmentDisabled
argument_list|()
argument_list|)
expr_stmt|;
else|else
name|iCheck
operator|.
name|setMessage
argument_list|(
name|status
operator|.
name|getMessage
argument_list|()
argument_list|)
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
if|if
condition|(
operator|!
name|disabled
condition|)
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_ENROLL
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// iCheck.setMessage(MSG.exceptionNoServerForSession());
name|iCheck
operator|.
name|setFlag
argument_list|(
name|EligibilityFlag
operator|.
name|CAN_ENROLL
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
return|return
name|iCheck
return|;
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
literal|"eligibility"
return|;
block|}
block|}
end_class

end_unit

