begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
package|;
end_package

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
name|CourseOffering
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
name|custom
operator|.
name|CourseDetailsProvider
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CourseInfo
implements|implements
name|Comparable
argument_list|<
name|CourseInfo
argument_list|>
block|{
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|String
name|iSubjectArea
decl_stmt|;
specifier|private
name|String
name|iDepartment
decl_stmt|;
specifier|private
name|String
name|iCourseNbr
decl_stmt|;
specifier|private
name|String
name|iTitle
decl_stmt|;
specifier|private
name|String
name|iCourseNameLowerCase
decl_stmt|;
specifier|private
name|String
name|iTitleLowerCase
decl_stmt|;
specifier|private
name|String
name|iNote
decl_stmt|;
specifier|private
name|String
name|iDetails
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iHasUniqueName
init|=
literal|true
decl_stmt|;
specifier|private
name|String
name|iConsent
init|=
literal|null
decl_stmt|,
name|iConsentAbbv
init|=
literal|null
decl_stmt|;
specifier|private
name|Integer
name|iWkEnroll
init|=
literal|null
decl_stmt|,
name|iWkChange
init|=
literal|null
decl_stmt|,
name|iWkDrop
init|=
literal|null
decl_stmt|;
specifier|public
name|CourseInfo
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
throws|throws
name|SectioningException
block|{
name|iUniqueId
operator|=
name|course
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iSubjectArea
operator|=
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
expr_stmt|;
name|iDepartment
operator|=
operator|(
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|==
literal|null
condition|?
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
else|:
name|course
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|getDeptCode
argument_list|()
operator|)
expr_stmt|;
name|iCourseNbr
operator|=
name|course
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|iTitle
operator|=
operator|(
name|course
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|course
operator|.
name|getTitle
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
expr_stmt|;
name|iNote
operator|=
name|course
operator|.
name|getScheduleBookNote
argument_list|()
expr_stmt|;
if|if
condition|(
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getConsentType
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|iConsent
operator|=
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getConsentType
argument_list|()
operator|.
name|getLabel
argument_list|()
expr_stmt|;
name|iConsentAbbv
operator|=
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getConsentType
argument_list|()
operator|.
name|getAbbv
argument_list|()
expr_stmt|;
block|}
name|iCourseNameLowerCase
operator|=
operator|(
name|iSubjectArea
operator|+
literal|" "
operator|+
name|iCourseNbr
operator|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|iTitleLowerCase
operator|=
operator|(
name|iTitle
operator|==
literal|null
condition|?
literal|null
else|:
name|iTitle
operator|.
name|toLowerCase
argument_list|()
operator|)
expr_stmt|;
name|iWkEnroll
operator|=
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getLastWeekToEnroll
argument_list|()
expr_stmt|;
name|iWkChange
operator|=
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getLastWeekToChange
argument_list|()
expr_stmt|;
name|iWkDrop
operator|=
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getLastWeekToDrop
argument_list|()
expr_stmt|;
block|}
specifier|public
name|CourseInfo
parameter_list|(
name|CourseOffering
name|course
parameter_list|,
name|String
name|courseNbr
parameter_list|)
throws|throws
name|SectioningException
block|{
name|this
argument_list|(
name|course
argument_list|)
expr_stmt|;
name|iCourseNbr
operator|=
name|courseNbr
expr_stmt|;
name|iCourseNameLowerCase
operator|=
operator|(
name|iSubjectArea
operator|+
literal|" "
operator|+
name|iCourseNbr
operator|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|String
name|getSubjectArea
parameter_list|()
block|{
return|return
name|iSubjectArea
return|;
block|}
specifier|public
name|String
name|getCourseNbr
parameter_list|()
block|{
return|return
name|iCourseNbr
return|;
block|}
specifier|public
name|String
name|getDepartment
parameter_list|()
block|{
return|return
name|iDepartment
return|;
block|}
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|iTitle
return|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|boolean
name|hasUniqueName
parameter_list|()
block|{
return|return
name|iHasUniqueName
return|;
block|}
specifier|public
name|void
name|setHasUniqueName
parameter_list|(
name|boolean
name|hasUniqueName
parameter_list|)
block|{
name|iHasUniqueName
operator|=
name|hasUniqueName
expr_stmt|;
block|}
specifier|public
name|String
name|getConsent
parameter_list|()
block|{
return|return
name|iConsent
return|;
block|}
specifier|public
name|String
name|getConsentAbbv
parameter_list|()
block|{
return|return
name|iConsentAbbv
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|CourseInfo
name|c
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getSubjectArea
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|c
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
name|getCourseNbr
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|c
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
name|cmp
operator|=
operator|(
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|getTitle
argument_list|()
operator|)
operator|.
name|compareToIgnoreCase
argument_list|(
name|c
operator|.
name|getTitle
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|c
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|matchCourseName
parameter_list|(
name|String
name|queryInLowerCase
parameter_list|)
block|{
if|if
condition|(
name|iCourseNameLowerCase
operator|.
name|startsWith
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|iTitleLowerCase
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|(
name|iCourseNameLowerCase
operator|+
literal|" "
operator|+
name|iTitleLowerCase
operator|)
operator|.
name|startsWith
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
operator|(
name|iCourseNameLowerCase
operator|+
literal|" - "
operator|+
name|iTitleLowerCase
operator|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|boolean
name|matchTitle
parameter_list|(
name|String
name|queryInLowerCase
parameter_list|)
block|{
if|if
condition|(
name|iTitleLowerCase
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|matchCourseName
argument_list|(
name|queryInLowerCase
argument_list|)
operator|&&
name|iTitleLowerCase
operator|.
name|contains
argument_list|(
name|queryInLowerCase
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
specifier|public
name|String
name|getDetails
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|CourseDetailsProvider
name|provider
parameter_list|)
throws|throws
name|SectioningException
block|{
if|if
condition|(
name|iDetails
operator|==
literal|null
condition|)
name|iDetails
operator|=
name|provider
operator|.
name|getDetails
argument_list|(
name|session
argument_list|,
name|iSubjectArea
argument_list|,
name|iCourseNbr
argument_list|)
expr_stmt|;
return|return
name|iDetails
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|getSubjectArea
argument_list|()
operator|+
literal|" "
operator|+
name|getCourseNbr
argument_list|()
operator|)
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
specifier|public
name|Integer
name|getLastWeekToEnroll
parameter_list|()
block|{
return|return
name|iWkEnroll
return|;
block|}
specifier|public
name|Integer
name|getLastWeekToChange
parameter_list|()
block|{
return|return
name|iWkChange
return|;
block|}
specifier|public
name|Integer
name|getLastWeekToDrop
parameter_list|()
block|{
return|return
name|iWkDrop
return|;
block|}
block|}
end_class

end_unit

