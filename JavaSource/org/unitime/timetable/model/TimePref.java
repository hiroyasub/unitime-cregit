begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|java
operator|.
name|util
operator|.
name|Date
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
name|base
operator|.
name|BaseTimePref
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
name|webutil
operator|.
name|RequiredTimeTable
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
name|coursett
operator|.
name|model
operator|.
name|TimeLocation
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
name|TimePref
extends|extends
name|BaseTimePref
implements|implements
name|Comparable
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
name|TimePref
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|TimePref
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
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|String
name|preferenceText
parameter_list|()
block|{
return|return
name|this
operator|.
name|getTimePattern
argument_list|()
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|public
name|TimePatternModel
name|getTimePatternModel
parameter_list|()
block|{
return|return
name|getTimePatternModel
argument_list|(
literal|null
argument_list|)
return|;
block|}
specifier|public
name|TimePatternModel
name|getTimePatternModel
parameter_list|(
name|TimeLocation
name|assignment
parameter_list|)
block|{
name|TimePatternModel
name|model
init|=
operator|new
name|TimePatternModel
argument_list|(
name|getTimePattern
argument_list|()
argument_list|,
name|assignment
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|model
operator|.
name|setPreferences
argument_list|(
name|super
operator|.
name|getPreference
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|model
return|;
block|}
specifier|public
name|void
name|setTimePatternModel
parameter_list|(
name|TimePatternModel
name|model
parameter_list|)
block|{
if|if
condition|(
name|model
operator|==
literal|null
condition|)
block|{
name|setPreference
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setTimePattern
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setPreference
argument_list|(
name|model
operator|.
name|getPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|setTimePattern
argument_list|(
name|model
operator|.
name|getTimePattern
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|TimePattern
name|t1
init|=
name|getTimePattern
argument_list|()
decl_stmt|;
name|TimePattern
name|t2
init|=
operator|(
operator|(
name|TimePref
operator|)
name|o
operator|)
operator|.
name|getTimePattern
argument_list|()
decl_stmt|;
name|int
name|cmp
init|=
name|t1
operator|.
name|compareTo
argument_list|(
name|t2
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
operator|-
name|getPreference
argument_list|()
operator|.
name|compareTo
argument_list|(
operator|(
operator|(
name|TimePref
operator|)
name|o
operator|)
operator|.
name|getPreference
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
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
empty_stmt|;
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
name|void
name|setInOldFormat
parameter_list|(
name|String
name|days
parameter_list|,
name|Date
name|startTime
parameter_list|,
name|Date
name|endTime
parameter_list|,
name|PreferenceLevel
name|pref
parameter_list|)
block|{
name|TimePatternModel
name|model
init|=
name|getTimePatternModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|model
operator|==
literal|null
condition|)
return|return;
name|model
operator|.
name|setInOldFormat
argument_list|(
name|days
argument_list|,
name|startTime
argument_list|,
name|endTime
argument_list|,
name|pref
argument_list|)
expr_stmt|;
name|setPreference
argument_list|(
name|model
operator|.
name|getPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|combineWith
parameter_list|(
name|TimePref
name|other
parameter_list|,
name|boolean
name|clear
parameter_list|)
block|{
name|combineWith
argument_list|(
name|other
argument_list|,
name|clear
argument_list|,
name|TimePatternModel
operator|.
name|sMixAlgMinMax
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|combineWith
parameter_list|(
name|TimePref
name|other
parameter_list|,
name|boolean
name|clear
parameter_list|,
name|int
name|alg
parameter_list|)
block|{
name|TimePatternModel
name|model
init|=
name|getTimePatternModel
argument_list|()
decl_stmt|;
name|TimePatternModel
name|otherModel
init|=
name|other
operator|.
name|getTimePatternModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|model
operator|==
literal|null
operator|||
name|otherModel
operator|==
literal|null
condition|)
return|return;
name|model
operator|.
name|combineWith
argument_list|(
name|otherModel
argument_list|,
name|clear
argument_list|,
name|alg
argument_list|)
expr_stmt|;
name|setPreference
argument_list|(
name|model
operator|.
name|getPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|weakenHardPreferences
parameter_list|()
block|{
name|TimePatternModel
name|model
init|=
name|getTimePatternModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|model
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|model
operator|.
name|weakenHardPreferences
argument_list|()
expr_stmt|;
name|setPreference
argument_list|(
name|model
operator|.
name|getPreferences
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
specifier|public
name|RequiredTimeTable
name|getRequiredTimeTable
parameter_list|()
block|{
return|return
name|getRequiredTimeTable
argument_list|(
literal|null
argument_list|)
return|;
block|}
specifier|public
name|RequiredTimeTable
name|getRequiredTimeTable
parameter_list|(
name|TimeLocation
name|assignment
parameter_list|)
block|{
return|return
operator|new
name|RequiredTimeTable
argument_list|(
name|getTimePatternModel
argument_list|(
name|assignment
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|TimePref
name|p
init|=
operator|new
name|TimePref
argument_list|()
decl_stmt|;
name|p
operator|.
name|setPreference
argument_list|(
name|getPreference
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setTimePattern
argument_list|(
name|getTimePattern
argument_list|()
argument_list|)
expr_stmt|;
name|p
operator|.
name|setPrefLevel
argument_list|(
name|getPrefLevel
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|p
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
name|TimePref
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
name|getTimePattern
argument_list|()
argument_list|,
operator|(
operator|(
name|TimePref
operator|)
name|other
operator|)
operator|.
name|getTimePattern
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getPreference
parameter_list|()
block|{
if|if
condition|(
name|super
operator|.
name|getPreference
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
name|getTimePatternModel
argument_list|()
operator|.
name|getPreferences
argument_list|()
return|;
block|}
else|else
return|return
name|super
operator|.
name|getPreference
argument_list|()
return|;
block|}
block|}
end_class

end_unit

