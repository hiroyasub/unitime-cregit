begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|ui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|PreferenceLevel
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BtbInstructorConstraintInfo
implements|implements
name|TimetableInfo
implements|,
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|2L
decl_stmt|;
specifier|public
specifier|static
name|int
name|sVersion
init|=
literal|2
decl_stmt|;
comment|// to be able to do some changes in the future
specifier|public
name|int
name|iPreference
init|=
name|PreferenceLevel
operator|.
name|sIntLevelNeutral
decl_stmt|;
specifier|public
name|Long
name|iInstructorId
init|=
literal|null
decl_stmt|;
specifier|public
name|BtbInstructorConstraintInfo
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getPreference
parameter_list|()
block|{
return|return
name|iPreference
return|;
block|}
specifier|public
name|void
name|setPreference
parameter_list|(
name|int
name|preference
parameter_list|)
block|{
name|iPreference
operator|=
name|preference
expr_stmt|;
block|}
specifier|public
name|Long
name|getInstructorId
parameter_list|()
block|{
return|return
name|iInstructorId
return|;
block|}
specifier|public
name|void
name|setInstructorId
parameter_list|(
name|Long
name|instructorId
parameter_list|)
block|{
name|iInstructorId
operator|=
name|instructorId
expr_stmt|;
block|}
specifier|public
name|void
name|load
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
name|int
name|version
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|root
operator|.
name|attributeValue
argument_list|(
literal|"version"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|version
operator|==
name|sVersion
condition|)
block|{
name|iInstructorId
operator|=
name|Long
operator|.
name|valueOf
argument_list|(
name|root
operator|.
name|elementText
argument_list|(
literal|"instructor"
argument_list|)
argument_list|)
expr_stmt|;
name|iPreference
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|root
operator|.
name|elementText
argument_list|(
literal|"pref"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|save
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
name|root
operator|.
name|addAttribute
argument_list|(
literal|"version"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|sVersion
argument_list|)
argument_list|)
expr_stmt|;
name|root
operator|.
name|addElement
argument_list|(
literal|"pref"
argument_list|)
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|iPreference
argument_list|)
argument_list|)
expr_stmt|;
name|root
operator|.
name|addElement
argument_list|(
literal|"instructor"
argument_list|)
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|iInstructorId
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|saveToFile
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

