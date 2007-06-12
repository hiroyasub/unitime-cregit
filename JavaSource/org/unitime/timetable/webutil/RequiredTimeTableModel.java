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
name|webutil
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_interface
specifier|public
interface|interface
name|RequiredTimeTableModel
block|{
comment|/** Name to be printed above time preferences grid */
specifier|public
name|String
name|getName
parameter_list|()
function_decl|;
comment|/** Number of days (columns) */
specifier|public
name|int
name|getNrDays
parameter_list|()
function_decl|;
comment|/** Number of times (rows) */
specifier|public
name|int
name|getNrTimes
parameter_list|()
function_decl|;
comment|/** Start time */
specifier|public
name|String
name|getStartTime
parameter_list|(
name|int
name|time
parameter_list|)
function_decl|;
comment|/** End time */
specifier|public
name|String
name|getEndTime
parameter_list|(
name|int
name|time
parameter_list|)
function_decl|;
comment|/** Day header, e.g., MWF */
specifier|public
name|String
name|getDayHeader
parameter_list|(
name|int
name|day
parameter_list|)
function_decl|;
comment|/** Unique file name representing the model and its content */
specifier|public
name|String
name|getFileName
parameter_list|()
function_decl|;
comment|/** Set preference */
specifier|public
name|void
name|setPreference
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|time
parameter_list|,
name|String
name|pref
parameter_list|)
function_decl|;
comment|/** Get preference */
specifier|public
name|String
name|getPreference
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|time
parameter_list|)
function_decl|;
comment|/** Get text to be printed on the field */
specifier|public
name|String
name|getFieldText
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|time
parameter_list|)
function_decl|;
comment|/** Is given field editable */
specifier|public
name|boolean
name|isEditable
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|time
parameter_list|)
function_decl|;
comment|/** Get all preferences, represented as a single string */
specifier|public
name|String
name|getPreferences
parameter_list|()
function_decl|;
comment|/** Set all preferences, represented as a single string */
specifier|public
name|void
name|setPreferences
parameter_list|(
name|String
name|pref
parameter_list|)
function_decl|;
comment|/** Exact time preference */
specifier|public
name|boolean
name|isExactTime
parameter_list|()
function_decl|;
specifier|public
name|int
name|getExactDays
parameter_list|()
function_decl|;
specifier|public
name|int
name|getExactStartSlot
parameter_list|()
function_decl|;
specifier|public
name|void
name|setExactDays
parameter_list|(
name|int
name|days
parameter_list|)
function_decl|;
specifier|public
name|void
name|setExactStartSlot
parameter_list|(
name|int
name|slot
parameter_list|)
function_decl|;
comment|/** Default preference */
specifier|public
name|String
name|getDefaultPreference
parameter_list|()
function_decl|;
comment|/** Border of the field (null for default border) */
specifier|public
name|Color
name|getBorder
parameter_list|(
name|int
name|day
parameter_list|,
name|int
name|time
parameter_list|)
function_decl|;
comment|/** Preference names ('R', '-2', ...) */
specifier|public
name|String
index|[]
name|getPreferenceNames
parameter_list|()
function_decl|;
comment|/** Preference color */
specifier|public
name|Color
name|getPreferenceColor
parameter_list|(
name|String
name|pref
parameter_list|)
function_decl|;
comment|/** Preference text (to be printed in legend) */
specifier|public
name|String
name|getPreferenceText
parameter_list|(
name|String
name|pref
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|isPreferenceEnabled
parameter_list|(
name|String
name|pref
parameter_list|)
function_decl|;
comment|/** Number of selections*/
specifier|public
name|int
name|getNrSelections
parameter_list|()
function_decl|;
specifier|public
name|String
name|getSelectionName
parameter_list|(
name|int
name|idx
parameter_list|)
function_decl|;
specifier|public
name|int
index|[]
name|getSelectionLimits
parameter_list|(
name|int
name|idx
parameter_list|)
function_decl|;
specifier|public
name|int
name|getDefaultSelection
parameter_list|()
function_decl|;
specifier|public
name|void
name|setDefaultSelection
parameter_list|(
name|int
name|selection
parameter_list|)
function_decl|;
specifier|public
name|void
name|setDefaultSelection
parameter_list|(
name|String
name|selectionName
parameter_list|)
function_decl|;
specifier|public
name|String
name|getPreferenceCheck
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

