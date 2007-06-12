begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
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
name|model
operator|.
name|base
operator|.
name|BaseBuildingPref
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
name|ifs
operator|.
name|util
operator|.
name|ToolBox
import|;
end_import

begin_class
specifier|public
class|class
name|BuildingPref
extends|extends
name|BaseBuildingPref
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|BuildingPref
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BuildingPref
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|BuildingPref
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceGroup
name|owner
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceLevel
name|prefLevel
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|owner
argument_list|,
name|prefLevel
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|String
name|preferenceText
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
try|try
block|{
name|BuildingPref
name|p
init|=
operator|(
name|BuildingPref
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
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
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
return|return
name|super
operator|.
name|compareTo
argument_list|(
name|o
argument_list|)
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|BuildingPref
name|pref
init|=
operator|new
name|BuildingPref
argument_list|()
decl_stmt|;
name|pref
operator|.
name|setPrefLevel
argument_list|(
name|getPrefLevel
argument_list|()
argument_list|)
expr_stmt|;
name|pref
operator|.
name|setBuilding
argument_list|(
name|getBuilding
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|pref
return|;
block|}
specifier|public
name|boolean
name|isSame
parameter_list|(
name|Preference
name|other
parameter_list|)
block|{
if|if
condition|(
name|other
operator|==
literal|null
operator|||
operator|!
operator|(
name|other
operator|instanceof
name|BuildingPref
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|ToolBox
operator|.
name|equals
argument_list|(
name|getBuilding
argument_list|()
argument_list|,
operator|(
operator|(
name|BuildingPref
operator|)
name|other
operator|)
operator|.
name|getBuilding
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|preferenceTitle
parameter_list|()
block|{
return|return
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
operator|+
literal|" Building "
operator|+
name|preferenceText
argument_list|()
return|;
block|}
block|}
end_class

end_unit

