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
name|gwt
operator|.
name|client
operator|.
name|sectioning
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
name|user
operator|.
name|client
operator|.
name|Cookies
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SectioningCookie
block|{
specifier|private
name|boolean
name|iCourseDetails
init|=
literal|false
decl_stmt|,
name|iShowClassNumbers
init|=
literal|false
decl_stmt|;
specifier|private
name|int
name|iRelatedSortBy
init|=
literal|0
decl_stmt|;
specifier|private
name|EnrollmentFilter
name|iEnrollmentFilter
init|=
name|EnrollmentFilter
operator|.
name|ALL
decl_stmt|;
specifier|private
specifier|static
name|SectioningCookie
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|EnrollmentFilter
block|{
name|ALL
block|,
name|ENROLLED
block|,
name|NOT_ENROLLED
block|,
name|WAIT_LISTED
block|}
specifier|private
name|SectioningCookie
parameter_list|()
block|{
try|try
block|{
name|String
name|cookie
init|=
name|Cookies
operator|.
name|getCookie
argument_list|(
literal|"UniTime:Sectioning"
argument_list|)
decl_stmt|;
if|if
condition|(
name|cookie
operator|!=
literal|null
operator|&&
name|cookie
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
index|[]
name|values
init|=
name|cookie
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
name|iCourseDetails
operator|=
literal|"T"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|iShowClassNumbers
operator|=
literal|"T"
operator|.
name|equals
argument_list|(
name|values
operator|.
name|length
operator|>=
literal|2
condition|?
name|values
index|[
literal|1
index|]
else|:
literal|"F"
argument_list|)
expr_stmt|;
name|iRelatedSortBy
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|values
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|iEnrollmentFilter
operator|=
name|EnrollmentFilter
operator|.
name|values
argument_list|()
index|[
name|Integer
operator|.
name|parseInt
argument_list|(
name|values
index|[
literal|3
index|]
argument_list|)
index|]
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
specifier|private
name|void
name|save
parameter_list|()
block|{
name|String
name|cookie
init|=
operator|(
name|iCourseDetails
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iShowClassNumbers
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
name|iRelatedSortBy
operator|+
literal|":"
operator|+
name|iEnrollmentFilter
operator|.
name|ordinal
argument_list|()
decl_stmt|;
name|Cookies
operator|.
name|setCookie
argument_list|(
literal|"UniTime:Sectioning"
argument_list|,
name|cookie
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|SectioningCookie
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|SectioningCookie
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|boolean
name|getEnrollmentCoursesDetails
parameter_list|()
block|{
return|return
name|iCourseDetails
return|;
block|}
specifier|public
name|void
name|setEnrollmentCoursesDetails
parameter_list|(
name|boolean
name|details
parameter_list|)
block|{
name|iCourseDetails
operator|=
name|details
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|getShowClassNumbers
parameter_list|()
block|{
return|return
name|iShowClassNumbers
return|;
block|}
specifier|public
name|void
name|setShowClassNumbers
parameter_list|(
name|boolean
name|showClassNumbers
parameter_list|)
block|{
name|iShowClassNumbers
operator|=
name|showClassNumbers
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getRelatedSortBy
parameter_list|()
block|{
return|return
name|iRelatedSortBy
return|;
block|}
specifier|public
name|void
name|setRelatedSortBy
parameter_list|(
name|int
name|sort
parameter_list|)
block|{
name|iRelatedSortBy
operator|=
name|sort
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|EnrollmentFilter
name|getEnrollmentFilter
parameter_list|()
block|{
return|return
name|iEnrollmentFilter
return|;
block|}
specifier|public
name|void
name|setEnrollmentFilter
parameter_list|(
name|EnrollmentFilter
name|enrollmentFilter
parameter_list|)
block|{
name|iEnrollmentFilter
operator|=
name|enrollmentFilter
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

