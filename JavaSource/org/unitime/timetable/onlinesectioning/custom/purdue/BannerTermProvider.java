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
name|custom
operator|.
name|purdue
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
name|onlinesectioning
operator|.
name|AcademicSessionInfo
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
name|ExternalTermProvider
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BannerTermProvider
implements|implements
name|ExternalTermProvider
block|{
annotation|@
name|Override
specifier|public
name|String
name|getExternalTerm
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|)
block|{
if|if
condition|(
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"spr"
argument_list|)
condition|)
return|return
name|session
operator|.
name|getYear
argument_list|()
operator|+
literal|"20"
return|;
if|if
condition|(
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"sum"
argument_list|)
condition|)
return|return
name|session
operator|.
name|getYear
argument_list|()
operator|+
literal|"30"
return|;
if|if
condition|(
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"fal"
argument_list|)
condition|)
return|return
name|String
operator|.
name|valueOf
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|session
operator|.
name|getYear
argument_list|()
argument_list|)
operator|+
literal|1
argument_list|)
operator|+
literal|"10"
return|;
return|return
name|session
operator|.
name|getYear
argument_list|()
operator|+
name|session
operator|.
name|getTerm
argument_list|()
operator|.
name|toLowerCase
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getExternalCampus
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|)
block|{
return|return
name|session
operator|.
name|getCampus
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getExternalSubject
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|String
name|subjectArea
parameter_list|,
name|String
name|courseNumber
parameter_list|)
block|{
return|return
name|subjectArea
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getExternalCourseNumber
parameter_list|(
name|AcademicSessionInfo
name|session
parameter_list|,
name|String
name|subjectArea
parameter_list|,
name|String
name|courseNumber
parameter_list|)
block|{
return|return
name|courseNumber
operator|.
name|length
argument_list|()
operator|>
literal|5
condition|?
name|courseNumber
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|)
else|:
name|courseNumber
return|;
block|}
block|}
end_class

end_unit

