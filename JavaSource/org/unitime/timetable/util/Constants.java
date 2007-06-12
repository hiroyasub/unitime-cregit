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
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpSession
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
name|BuildingPref
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
name|DistributionPref
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
name|RoomFeaturePref
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
name|RoomPref
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
name|TimePref
import|;
end_import

begin_comment
comment|/**  * Various constants used in timetabling project.  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Constants
extends|extends
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|coursett
operator|.
name|Constants
block|{
comment|// --------------------------------------------------------- Class Properties
comment|/** Day names in 3-character format (e.g. Mon) */
specifier|public
specifier|static
name|String
name|DAY_NAME
index|[]
init|=
operator|new
name|String
index|[]
block|{
literal|"Mon"
block|,
literal|"Tue"
block|,
literal|"Wed"
block|,
literal|"Thu"
block|,
literal|"Fri"
block|,
literal|"Sat"
block|,
literal|"Sun"
block|}
decl_stmt|;
comment|/** Day names */
specifier|public
specifier|static
name|String
name|DAY_NAMES_FULL
index|[]
init|=
operator|new
name|String
index|[]
block|{
literal|"Monday"
block|,
literal|"Tuesday"
block|,
literal|"Wednesday"
block|,
literal|"Thursday"
block|,
literal|"Friday"
block|,
literal|"Saturday"
block|,
literal|"Sunday"
block|}
decl_stmt|;
specifier|public
specifier|static
name|int
name|DAY_MON
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
name|int
name|DAY_TUE
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
name|int
name|DAY_WED
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
name|int
name|DAY_THU
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
name|int
name|DAY_FRI
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
name|int
name|DAY_SAT
init|=
literal|5
decl_stmt|;
specifier|public
specifier|static
name|int
name|DAY_SUN
init|=
literal|6
decl_stmt|;
specifier|public
specifier|static
name|int
name|EVENING_SLOTS_FIRST
init|=
name|DAY_SLOTS_LAST
operator|+
literal|1
decl_stmt|;
specifier|public
specifier|static
name|int
name|EVENING_SLOTS_LAST
init|=
operator|(
literal|23
operator|*
literal|60
operator|+
literal|00
operator|)
operator|/
literal|5
operator|-
literal|1
decl_stmt|;
comment|// evening ends at 23:00
comment|/** version */
specifier|public
specifier|static
name|String
name|VERSION
init|=
literal|"3.0"
decl_stmt|;
comment|/** release date */
specifier|public
specifier|static
name|String
name|REL_DATE
init|=
literal|"@build.date@"
decl_stmt|;
comment|/** build number */
specifier|public
specifier|static
name|String
name|BLD_NUMBER
init|=
literal|"@build.number@"
decl_stmt|;
comment|/** startup date format */
specifier|public
specifier|static
name|String
name|STARTUP_DATE_FORMAT
init|=
literal|"EEE, d MMM yyyy HH:mm:ss z"
decl_stmt|;
comment|/** date format */
specifier|public
specifier|static
name|String
name|DATE_FORMAT
init|=
literal|"EEE, d MMM yyyy"
decl_stmt|;
specifier|public
specifier|static
name|int
name|TIME_PATTERN_STANDARD
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
name|int
name|TIME_PATTERN_GENERIC
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
name|int
name|TIME_PATTERN_EVENING
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
name|int
name|TIME_PATTERN_SATURDAY
init|=
literal|3
decl_stmt|;
comment|/** campus */
specifier|public
specifier|static
name|String
name|CAMPUS_WLAF
init|=
literal|"1"
decl_stmt|;
comment|/** Request Attribute names */
specifier|public
specifier|static
name|String
name|SESSION_ID_ATTR_NAME
init|=
literal|"acadSessionId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|ACAD_YRTERM_ATTR_NAME
init|=
literal|"acadYearTerm"
decl_stmt|;
specifier|public
specifier|static
name|String
name|ACAD_YRTERM_LABEL_ATTR_NAME
init|=
literal|"acadYearTermLabel"
decl_stmt|;
specifier|public
specifier|static
name|String
name|TMTBL_MGR_ID_ATTR_NAME
init|=
literal|"tmtblManagerId"
decl_stmt|;
comment|/** Request Attribute for jump-to anchor */
specifier|public
specifier|static
name|String
name|JUMP_TO_ATTR_NAME
init|=
literal|"jumpTo"
decl_stmt|;
comment|/** Session Attribute Names */
specifier|public
specifier|static
name|String
name|SUBJ_AREA_ID_ATTR_NAME
init|=
literal|"subjectAreaId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|CRS_NBR_ATTR_NAME
init|=
literal|"courseNbr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|DEPT_ID_ATTR_NAME
init|=
literal|"deptUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|DEPT_CODE_ATTR_NAME
init|=
literal|"deptCode"
decl_stmt|;
specifier|public
specifier|static
name|String
name|DEPT_CODE_ATTR_ROOM_NAME
init|=
literal|"deptCodeRoom"
decl_stmt|;
specifier|public
specifier|static
name|String
name|CRS_LST_SUBJ_AREA_IDS_ATTR_NAME
init|=
literal|"crsLstSubjectAreaIds"
decl_stmt|;
specifier|public
specifier|static
name|String
name|CRS_LST_CRS_NBR_ATTR_NAME
init|=
literal|"crsLstCrsNbr"
decl_stmt|;
specifier|public
specifier|static
name|String
name|CRS_ASGN_LST_SUBJ_AREA_IDS_ATTR_NAME
init|=
literal|"crsAsgnLstSubjectAreaIds"
decl_stmt|;
comment|/** LLR Manager Department Codes **/
specifier|public
specifier|static
name|String
index|[]
name|LLR_DEPTS
init|=
block|{
literal|"1994"
block|,
literal|"1980"
block|}
decl_stmt|;
comment|/** Blank Select Box Label/Value */
specifier|public
specifier|static
name|String
name|BLANK_OPTION_LABEL
init|=
literal|"Select ..."
decl_stmt|;
specifier|public
specifier|static
name|String
name|BLANK_OPTION_VALUE
init|=
literal|""
decl_stmt|;
specifier|public
specifier|static
name|String
name|ALL_OPTION_LABEL
init|=
literal|"All"
decl_stmt|;
specifier|public
specifier|static
name|String
name|ALL_OPTION_VALUE
init|=
literal|"All"
decl_stmt|;
comment|/** Facility group references */
specifier|public
specifier|static
name|String
name|FACILITY_GROUP_LLR
init|=
literal|"LLR"
decl_stmt|;
specifier|public
specifier|static
name|String
name|FACILITY_GROUP_LAB
init|=
literal|"LAB"
decl_stmt|;
specifier|public
specifier|static
name|String
name|FACILITY_GROUP_DEPT
init|=
literal|"DEPT"
decl_stmt|;
comment|/** Pref names */
specifier|public
specifier|static
name|String
name|PREF_CLASS_NAMES
index|[]
init|=
operator|new
name|String
index|[]
block|{
name|TimePref
operator|.
name|class
operator|.
name|getName
argument_list|()
block|,
name|RoomPref
operator|.
name|class
operator|.
name|getName
argument_list|()
block|,
name|BuildingPref
operator|.
name|class
operator|.
name|getName
argument_list|()
block|,
name|RoomFeaturePref
operator|.
name|class
operator|.
name|getName
argument_list|()
block|,
name|DistributionPref
operator|.
name|class
operator|.
name|getName
argument_list|()
block|}
decl_stmt|;
comment|/** User settings */
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_TIME_GRID_SIZE
init|=
literal|"timeGridSize"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_TIME_GRID_TEXT
init|=
literal|"text"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_TIME_GRID_ORIENTATION
init|=
literal|"timeGrid"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_TIME_GRID_ORIENTATION_VERTICAL
init|=
literal|"vertical"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_TIME_GRID_ORIENTATION_HORIZONTAL
init|=
literal|"horizontal"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_AUTOCALC
init|=
literal|"cfgAutoCalc"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_JS_DIALOGS
init|=
literal|"jsConfirm"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INHERIT_INSTRUCTOR_PREF
init|=
literal|"inheritInstrPref"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INHERIT_INSTRUCTOR_PREF_CONFIRM
init|=
literal|"ask"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INHERIT_INSTRUCTOR_PREF_YES
init|=
literal|"always"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INHERIT_INSTRUCTOR_PREF_NO
init|=
literal|"never"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INSTRUCTOR_NAME_FORMAT
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INSTRUCTOR_SORT
init|=
literal|"instrNameSort"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INSTRUCTOR_SORT_LNAME_ALWAYS
init|=
literal|"Always by Last Name"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_INSTRUCTOR_SORT_NATURAL
init|=
literal|"Natural Order (as displayed)"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_SHOW_VAR_LIMITS
init|=
literal|"showVarLimits"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_KEEP_SORT
init|=
literal|"keepSort"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_ROOMS_FEATURES_ONE_COLUMN
init|=
literal|"roomFeaturesInOneColumn"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS_DISP_LAST_CHANGES
init|=
literal|"dispLastChanges"
decl_stmt|;
comment|/** Indicates classes are managed by multiple departments */
specifier|public
specifier|static
specifier|final
name|long
name|MANAGED_BY_MULTIPLE_DEPTS
init|=
literal|0
decl_stmt|;
comment|/** Configuration Keys */
specifier|public
specifier|static
specifier|final
name|String
name|SESSION_APP_ACCESS_LEVEL
init|=
literal|"systemAccess"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CFG_APP_ACCESS_LEVEL
init|=
literal|"systemAccessLevel"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CFG_SYSTEM_MESSAGE
init|=
literal|"systemMessage"
decl_stmt|;
comment|/** Configuration Values */
specifier|public
specifier|static
specifier|final
name|String
name|APP_ACL_ALL
init|=
literal|"all"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|APP_ACL_ADMIN
init|=
literal|"admin"
decl_stmt|;
comment|/** Reservation Owner Type */
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_IO
init|=
literal|"I"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_CLASS
init|=
literal|"C"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_CONFIG
init|=
literal|"R"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_IO_LBL
init|=
literal|"Instructional Offering"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_CLASS_LBL
init|=
literal|"Class"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_CONFIG_LBL
init|=
literal|"Configuration"
decl_stmt|;
comment|//TODO Reservations - functionality to be removed later
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_COURSE
init|=
literal|"U"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_OWNER_COURSE_LBL
init|=
literal|"Course Offering"
decl_stmt|;
comment|// End Bypass
comment|/** Reservation Classification */
specifier|public
specifier|static
specifier|final
name|String
name|RESV_INDIVIDUAL
init|=
literal|"Individual"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_STU_GROUP
init|=
literal|"Student Group"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_COURSE
init|=
literal|"Course"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_ACAD_AREA
init|=
literal|"Academic Area"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_POS
init|=
literal|"Program of Study"
decl_stmt|;
comment|/** Array of Reservation Classifications */
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|RESV_CLASS_LABELS
init|=
block|{
name|RESV_ACAD_AREA
block|,
name|RESV_COURSE
block|}
decl_stmt|;
comment|//, RESV_INDIVIDUAL, RESV_POS, RESV_STU_GROUP };
comment|/** Array of Reservation Priorities */
specifier|public
specifier|static
specifier|final
name|int
index|[]
name|RESV_PRIORITIES
init|=
block|{
literal|1
block|,
literal|2
block|,
literal|3
block|,
literal|4
block|,
literal|5
block|,
literal|6
block|,
literal|7
block|,
literal|8
block|,
literal|9
block|,
literal|10
block|}
decl_stmt|;
comment|/** Default Reservation Priority */
specifier|public
specifier|static
specifier|final
name|int
name|RESV_DEFAULT_PRIORITY
init|=
literal|1
decl_stmt|;
comment|/** Reservation Types */
specifier|public
specifier|static
specifier|final
name|String
name|RESV_TYPE_PERM_REF
init|=
literal|"perm"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_TYPE_TEMP_REF
init|=
literal|"temp"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESV_TYPE_INFO_REF
init|=
literal|"info"
decl_stmt|;
comment|/** (Http)Request attributes */
specifier|public
specifier|static
specifier|final
name|String
name|REQUEST_OPEN_URL
init|=
literal|"RqOpenUrl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REQUEST_WARN
init|=
literal|"RqWarn"
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**      * Check if string is a positive integer greater or equal to than 0      * @param str String to be converted to integer      * @param defaultValue Default value returned if conversion fails      * @return integer if success, default value if fails      */
specifier|public
specifier|static
name|int
name|getPositiveInteger
parameter_list|(
name|String
name|str
parameter_list|,
name|int
name|defaultValue
parameter_list|)
block|{
try|try
block|{
name|int
name|i
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|str
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>=
literal|0
condition|)
return|return
name|i
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
name|defaultValue
return|;
block|}
return|return
name|defaultValue
return|;
block|}
comment|/**      * Check if string is a positive float greater or equal to than 0      * @param str String to be converted to float      * @param defaultValue Default value returned if conversion fails      * @return float if success, default value if fails      */
specifier|public
specifier|static
name|float
name|getPositiveFloat
parameter_list|(
name|String
name|str
parameter_list|,
name|float
name|defaultValue
parameter_list|)
block|{
try|try
block|{
name|float
name|i
init|=
name|Float
operator|.
name|parseFloat
argument_list|(
name|str
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>=
literal|0
condition|)
return|return
name|i
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
name|defaultValue
return|;
block|}
return|return
name|defaultValue
return|;
block|}
comment|/**      * Filters out possible Cross Site Scripting and SQL Injection characters by allowing only       * the following characters in the input A-Z a-z 0-9 @ . ' space _ -      * @param str Input String      * @return Filtered String      */
specifier|public
specifier|static
name|String
name|filterXSS
parameter_list|(
name|String
name|str
parameter_list|)
block|{
if|if
condition|(
name|str
operator|!=
literal|null
condition|)
name|str
operator|=
name|str
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"([^A-Za-z0-9@.' _-]+)"
argument_list|,
literal|"_"
argument_list|)
expr_stmt|;
return|return
name|str
return|;
block|}
comment|/**      * Converts an array of object to a string representation      * @param array Array of objects      * @param encloseBy Each array object will be enclosed by the parameter supplied      * @param separator Array objects will separated by this separator      * @return Array converted to a string      * @throws IllegalArgumentException      */
specifier|public
specifier|static
name|String
name|arrayToStr
parameter_list|(
name|Object
index|[]
name|array
parameter_list|,
name|String
name|encloseBy
parameter_list|,
name|String
name|separator
parameter_list|)
throws|throws
name|IllegalArgumentException
block|{
if|if
condition|(
name|array
operator|==
literal|null
operator|||
name|array
operator|.
name|length
operator|==
literal|0
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Supply a valid array"
argument_list|)
throw|;
if|if
condition|(
name|encloseBy
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"encloseBy cannot be null"
argument_list|)
throw|;
if|if
condition|(
name|separator
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"separator cannot be null"
argument_list|)
throw|;
name|StringBuffer
name|str
init|=
operator|new
name|StringBuffer
argument_list|(
literal|""
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|array
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|str
operator|.
name|append
argument_list|(
name|encloseBy
argument_list|)
expr_stmt|;
name|str
operator|.
name|append
argument_list|(
name|array
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|str
operator|.
name|append
argument_list|(
name|encloseBy
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|array
operator|.
name|length
operator|-
literal|1
condition|)
name|str
operator|.
name|append
argument_list|(
name|separator
argument_list|)
expr_stmt|;
block|}
return|return
name|str
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** 	 * Returns a string with desired character padding before the string representation of the      * object to make it the desired width. No change if it is already that long      * or longer.      *       * @param value object to display      * @param width size of field in which to display the object    	 * @param pad pad character(s); defaults to space       * @return string version of value with spaces before to make it the given width      * @throws Exception if parameters are not correct      */
specifier|public
specifier|static
name|String
name|leftPad
parameter_list|(
name|Object
name|value
parameter_list|,
name|int
name|width
parameter_list|,
name|String
name|pad
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|pad
argument_list|(
literal|1
argument_list|,
name|value
argument_list|,
name|width
argument_list|,
name|pad
argument_list|)
return|;
block|}
comment|/**    * Returns a string with desired character padding after the string representation of the    * string to make it the desired width. No change if it is already that long    * or longer.     *     * @param value object to display    * @param width size of field in which to display the object    * @param pad pad character(s); defaults to space    * @return string version of value with spaces after to make it the given width    * @throws Exception if parameters are not correct    */
specifier|public
specifier|static
name|String
name|rightPad
parameter_list|(
name|Object
name|value
parameter_list|,
name|int
name|width
parameter_list|,
name|String
name|pad
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|pad
argument_list|(
literal|2
argument_list|,
name|value
argument_list|,
name|width
argument_list|,
name|pad
argument_list|)
return|;
block|}
comment|/**      * Returns a string with desired character padding before or after the string representation of the      * string to make it the desired width. No change if it is already that long      * or longer.       *       * @param direction 1=left pad, any other integer=right pad       * @param value object to display      * @param width size of field in which to display the object      * @param pad pad character(s); defaults to space      * @return string version of value with spaces after to make it the given width      * @throws Exception if parameters are not correct    */
specifier|public
specifier|static
name|String
name|pad
parameter_list|(
name|int
name|direction
parameter_list|,
name|Object
name|value
parameter_list|,
name|int
name|width
parameter_list|,
name|String
name|pad
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|value1
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|value1
operator|=
literal|""
expr_stmt|;
else|else
name|value1
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|width
operator|<=
literal|0
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Width must be> 0"
argument_list|)
throw|;
if|if
condition|(
name|pad
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|||
name|pad
operator|==
literal|null
condition|)
name|pad
operator|=
literal|" "
expr_stmt|;
name|int
name|lnCount
init|=
name|width
operator|-
name|value1
operator|.
name|length
argument_list|()
decl_stmt|;
comment|// Left Pad
if|if
condition|(
name|direction
operator|==
literal|1
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lnCount
condition|;
name|i
operator|++
control|)
name|value1
operator|=
name|pad
operator|+
name|value1
expr_stmt|;
block|}
comment|// Default Right Pad
else|else
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|lnCount
condition|;
name|i
operator|++
control|)
name|value1
operator|=
name|value1
operator|+
name|pad
expr_stmt|;
block|}
return|return
name|value1
return|;
block|}
comment|/**   	 * Converts a string to initial case.   	 * All letters occuring after a space or period are converted to uppercase   	 * Example JOHN G DOE -> John G Doe   	 * @param str Input String    	 * @return Formatted String   	 */
specifier|public
specifier|static
name|String
name|toInitialCase
parameter_list|(
name|String
name|str
parameter_list|)
block|{
return|return
name|toInitialCase
argument_list|(
name|str
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/**   	 * Converts a string to initial case.   	 * All letters occuring after a space or period are converted to uppercase   	 * Example JOHN G DOE -> John G Doe   	 * @param str Input String   	 * @param delimiters array of delimiters which should be included with space and period    	 * @return Formatted String   	 */
specifier|public
specifier|static
name|String
name|toInitialCase
parameter_list|(
name|String
name|str
parameter_list|,
name|char
index|[]
name|delimiters
parameter_list|)
block|{
if|if
condition|(
name|str
operator|==
literal|null
operator|||
name|str
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
name|str
return|;
name|char
index|[]
name|chars
init|=
name|str
operator|.
name|toCharArray
argument_list|()
decl_stmt|;
name|boolean
name|upper
init|=
literal|true
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|chars
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|upper
operator|&&
name|Character
operator|.
name|isLetter
argument_list|(
name|chars
index|[
name|i
index|]
argument_list|)
condition|)
block|{
name|chars
index|[
name|i
index|]
operator|=
name|Character
operator|.
name|toUpperCase
argument_list|(
name|chars
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|upper
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|chars
index|[
name|i
index|]
operator|=
name|Character
operator|.
name|toLowerCase
argument_list|(
name|chars
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|Character
operator|.
name|isLetterOrDigit
argument_list|(
name|chars
index|[
name|i
index|]
argument_list|)
operator|&&
name|chars
index|[
name|i
index|]
operator|!=
literal|'\''
condition|)
block|{
name|upper
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|delimiters
operator|!=
literal|null
operator|&&
name|delimiters
operator|.
name|length
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|delimiters
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|chars
index|[
name|i
index|]
operator|==
name|delimiters
index|[
name|j
index|]
condition|)
block|{
name|upper
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
name|str
operator|=
operator|new
name|String
argument_list|(
name|chars
argument_list|)
expr_stmt|;
return|return
name|str
return|;
block|}
specifier|public
specifier|static
name|String
name|slot2str
parameter_list|(
name|int
name|slot
parameter_list|)
block|{
name|int
name|hour
init|=
operator|(
name|slot
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|/
literal|60
decl_stmt|;
name|int
name|min
init|=
operator|(
name|slot
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
operator|)
operator|%
literal|60
decl_stmt|;
return|return
operator|(
name|hour
operator|>
literal|12
condition|?
name|hour
operator|-
literal|12
else|:
name|hour
operator|)
operator|+
literal|":"
operator|+
operator|(
name|min
operator|<
literal|10
condition|?
literal|"0"
else|:
literal|""
operator|)
operator|+
name|min
operator|+
operator|(
name|hour
operator|>=
literal|12
condition|?
literal|"p"
else|:
literal|"a"
operator|)
return|;
block|}
comment|/**   	 * Test Method   	 * @param args   	 */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"JOHN G DOE -> "
operator|+
name|toInitialCase
argument_list|(
literal|"JOHN G DOE"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"DOE jOHn G. -> "
operator|+
name|toInitialCase
argument_list|(
literal|"DOE jOHn G."
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"jOhN g. dOe -> "
operator|+
name|toInitialCase
argument_list|(
literal|"jOhN g. dOe"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"To be Or NOT to BE.that is THE QUESTION. -> "
operator|+
name|toInitialCase
argument_list|(
literal|"To be Or NOT to BE.that is THE QUESTION."
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**   	 * sort a string   	 * @param data   	 * @param separator   	 * @return   	 */
specifier|public
specifier|static
name|String
name|sort
parameter_list|(
name|String
name|data
parameter_list|,
name|String
name|separator
parameter_list|)
block|{
if|if
condition|(
name|separator
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"separator cannot be null"
argument_list|)
throw|;
name|List
name|list
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|data
operator|.
name|split
argument_list|(
name|separator
argument_list|)
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|)
expr_stmt|;
return|return
name|list
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Reset Session variables for a particular user      * @param webSession      */
specifier|public
specifier|static
name|void
name|resetSessionAttributes
parameter_list|(
name|HttpSession
name|webSession
parameter_list|)
block|{
name|webSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_ID_ATTR_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|DEPT_CODE_ATTR_ROOM_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_LST_SUBJ_AREA_IDS_ATTR_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|removeAttribute
argument_list|(
name|Constants
operator|.
name|CRS_ASGN_LST_SUBJ_AREA_IDS_ATTR_NAME
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|removeAttribute
argument_list|(
literal|"SolverProxy"
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|removeAttribute
argument_list|(
literal|"ManageSolver.puid"
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|removeAttribute
argument_list|(
literal|"Solver.selectedSolutionId"
argument_list|)
expr_stmt|;
name|webSession
operator|.
name|removeAttribute
argument_list|(
literal|"LastSolutionClassAssignmentProxy"
argument_list|)
expr_stmt|;
block|}
comment|/**      * True if string can be parsed to an integer      * @param str      * @return      */
specifier|public
specifier|static
name|boolean
name|isInteger
parameter_list|(
name|String
name|str
parameter_list|)
block|{
try|try
block|{
name|int
name|i
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|str
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
comment|/** 	 * True if it can be parsed to a number 	 * @param str 	 * @return 	 */
specifier|public
specifier|static
name|boolean
name|isNumber
parameter_list|(
name|String
name|str
parameter_list|)
block|{
try|try
block|{
name|double
name|i
init|=
name|Double
operator|.
name|parseDouble
argument_list|(
name|str
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

