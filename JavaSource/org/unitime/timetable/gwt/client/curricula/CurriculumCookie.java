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
name|gwt
operator|.
name|client
operator|.
name|curricula
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
name|CurriculumCookie
block|{
specifier|private
name|CourseCurriculaTable
operator|.
name|Type
name|iType
init|=
name|CourseCurriculaTable
operator|.
name|Type
operator|.
name|EXP
decl_stmt|;
specifier|private
name|CurriculaCourses
operator|.
name|Mode
name|iMode
init|=
name|CurriculaCourses
operator|.
name|Mode
operator|.
name|NONE
decl_stmt|;
specifier|private
name|boolean
name|iPercent
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iRulesPercent
init|=
literal|true
decl_stmt|;
specifier|private
name|boolean
name|iRulesShowLastLike
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iCourseDetails
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iShowLast
init|=
literal|true
decl_stmt|,
name|iShowProjected
init|=
literal|true
decl_stmt|,
name|iShowExpected
init|=
literal|true
decl_stmt|,
name|iShowEnrolled
init|=
literal|true
decl_stmt|,
name|iShowRequested
init|=
literal|false
decl_stmt|;
specifier|private
name|CurriculaTable
operator|.
name|DisplayMode
name|iCurMode
init|=
operator|new
name|CurriculaTable
operator|.
name|DisplayMode
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|changed
parameter_list|()
block|{
name|save
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
specifier|private
specifier|static
name|CurriculumCookie
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|private
name|CurriculumCookie
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
literal|"UniTime:Curriculum"
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
name|iType
operator|=
name|CourseCurriculaTable
operator|.
name|Type
operator|.
name|valueOf
argument_list|(
name|values
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|iMode
operator|=
name|CurriculaCourses
operator|.
name|Mode
operator|.
name|valueOf
argument_list|(
name|values
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|iPercent
operator|=
literal|"T"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
name|iRulesPercent
operator|=
literal|"T"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|3
index|]
argument_list|)
expr_stmt|;
name|iRulesShowLastLike
operator|=
literal|"T"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|4
index|]
argument_list|)
expr_stmt|;
name|iCourseDetails
operator|=
literal|"T"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|5
index|]
argument_list|)
expr_stmt|;
name|iCurMode
operator|.
name|fromString
argument_list|(
name|values
index|[
literal|6
index|]
argument_list|)
expr_stmt|;
name|iShowLast
operator|=
operator|!
literal|"F"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|7
index|]
argument_list|)
expr_stmt|;
name|iShowProjected
operator|=
operator|!
literal|"F"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|8
index|]
argument_list|)
expr_stmt|;
name|iShowExpected
operator|=
operator|!
literal|"F"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|9
index|]
argument_list|)
expr_stmt|;
name|iShowEnrolled
operator|=
operator|!
literal|"F"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|10
index|]
argument_list|)
expr_stmt|;
name|iShowRequested
operator|=
literal|"T"
operator|.
name|equals
argument_list|(
name|values
index|[
literal|11
index|]
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
name|iType
operator|==
literal|null
condition|?
literal|""
else|:
name|iType
operator|.
name|name
argument_list|()
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iMode
operator|==
literal|null
condition|?
literal|""
else|:
name|iMode
operator|.
name|name
argument_list|()
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iPercent
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iRulesPercent
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iRulesShowLastLike
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
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
name|iCurMode
operator|.
name|toString
argument_list|()
operator|+
literal|":"
operator|+
operator|(
name|iShowLast
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iShowProjected
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iShowExpected
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iShowEnrolled
condition|?
literal|"T"
else|:
literal|"F"
operator|)
operator|+
literal|":"
operator|+
operator|(
name|iShowRequested
condition|?
literal|"T"
else|:
literal|"F"
operator|)
decl_stmt|;
name|Cookies
operator|.
name|setCookie
argument_list|(
literal|"UniTime:Curriculum"
argument_list|,
name|cookie
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|CurriculumCookie
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
name|CurriculumCookie
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|public
name|CourseCurriculaTable
operator|.
name|Type
name|getCourseCurriculaTableType
parameter_list|()
block|{
return|return
operator|(
name|iType
operator|==
literal|null
condition|?
name|CourseCurriculaTable
operator|.
name|Type
operator|.
name|EXP
else|:
name|iType
operator|)
return|;
block|}
specifier|public
name|void
name|setCourseCurriculaTableType
parameter_list|(
name|CourseCurriculaTable
operator|.
name|Type
name|type
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|CurriculaCourses
operator|.
name|Mode
name|getCurriculaCoursesMode
parameter_list|()
block|{
return|return
operator|(
name|iMode
operator|==
literal|null
condition|?
name|CurriculaCourses
operator|.
name|Mode
operator|.
name|NONE
else|:
name|iMode
operator|)
return|;
block|}
specifier|public
name|void
name|setCurriculaCoursesMode
parameter_list|(
name|CurriculaCourses
operator|.
name|Mode
name|mode
parameter_list|)
block|{
name|iMode
operator|=
name|mode
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCurriculaCoursesPercent
parameter_list|()
block|{
return|return
name|iPercent
return|;
block|}
specifier|public
name|void
name|setCurriculaCoursesPercent
parameter_list|(
name|boolean
name|percent
parameter_list|)
block|{
name|iPercent
operator|=
name|percent
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCurriculumProjectionRulesPercent
parameter_list|()
block|{
return|return
name|iRulesPercent
return|;
block|}
specifier|public
name|void
name|setCurriculumProjectionRulesPercent
parameter_list|(
name|boolean
name|percent
parameter_list|)
block|{
name|iRulesPercent
operator|=
name|percent
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCurriculumProjectionRulesShowLastLike
parameter_list|()
block|{
return|return
name|iRulesShowLastLike
return|;
block|}
specifier|public
name|void
name|setCurriculumProjectionRulesShowLastLike
parameter_list|(
name|boolean
name|showLastLike
parameter_list|)
block|{
name|iRulesShowLastLike
operator|=
name|showLastLike
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|getCurriculaCoursesDetails
parameter_list|()
block|{
return|return
name|iCourseDetails
return|;
block|}
specifier|public
name|void
name|setCurriculaCoursesDetails
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
name|CurriculaTable
operator|.
name|DisplayMode
name|getCurriculaDisplayMode
parameter_list|()
block|{
return|return
name|iCurMode
return|;
block|}
specifier|public
name|boolean
name|isShowLast
parameter_list|()
block|{
return|return
name|iShowLast
return|;
block|}
specifier|public
name|void
name|setShowLast
parameter_list|(
name|boolean
name|show
parameter_list|)
block|{
name|iShowLast
operator|=
name|show
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowProjected
parameter_list|()
block|{
return|return
name|iShowProjected
return|;
block|}
specifier|public
name|void
name|setShowProjected
parameter_list|(
name|boolean
name|show
parameter_list|)
block|{
name|iShowProjected
operator|=
name|show
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowEnrolled
parameter_list|()
block|{
return|return
name|iShowEnrolled
return|;
block|}
specifier|public
name|void
name|setShowEnrolled
parameter_list|(
name|boolean
name|show
parameter_list|)
block|{
name|iShowEnrolled
operator|=
name|show
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowExpected
parameter_list|()
block|{
return|return
name|iShowExpected
return|;
block|}
specifier|public
name|void
name|setShowExpected
parameter_list|(
name|boolean
name|show
parameter_list|)
block|{
name|iShowExpected
operator|=
name|show
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isShowRequested
parameter_list|()
block|{
return|return
name|iShowRequested
return|;
block|}
specifier|public
name|void
name|setShowRequested
parameter_list|(
name|boolean
name|show
parameter_list|)
block|{
name|iShowRequested
operator|=
name|show
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAllHidden
parameter_list|()
block|{
return|return
operator|!
name|iShowLast
operator|&&
operator|!
name|iShowProjected
operator|&&
operator|!
name|iShowExpected
operator|&&
operator|!
name|iShowEnrolled
operator|&&
operator|!
name|iShowRequested
return|;
block|}
block|}
end_class

end_unit

