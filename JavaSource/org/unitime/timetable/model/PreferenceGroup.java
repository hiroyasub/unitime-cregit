begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|ObjectNotFoundException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
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
name|BasePreferenceGroup
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
name|_RootDAO
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

begin_class
specifier|public
specifier|abstract
class|class
name|PreferenceGroup
extends|extends
name|BasePreferenceGroup
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
name|PreferenceGroup
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|PreferenceGroup
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
comment|/* getTimePreferences()      * @return ArrayList of TimePrefs      */
specifier|public
name|Set
name|getTimePreferences
parameter_list|()
block|{
return|return
name|getPreferences
argument_list|(
name|TimePref
operator|.
name|class
argument_list|)
return|;
block|}
specifier|public
name|Set
name|getTimePatterns
parameter_list|()
block|{
name|Set
name|timePrefs
init|=
name|getTimePreferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|timePrefs
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|timePrefs
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimePref
name|tp
init|=
operator|(
name|TimePref
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|tp
operator|.
name|getTimePattern
argument_list|()
operator|!=
literal|null
condition|)
name|ret
operator|.
name|add
argument_list|(
name|tp
operator|.
name|getTimePattern
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
comment|/* getEffectiveTimePreferences()      * @return ArrayList of TimePrefs      */
specifier|public
name|Set
name|getEffectiveTimePreferences
parameter_list|()
block|{
return|return
name|effectivePreferences
argument_list|(
name|TimePref
operator|.
name|class
argument_list|)
return|;
block|}
specifier|public
name|Set
name|effectiveTimePatterns
parameter_list|()
block|{
name|Set
name|timePrefs
init|=
name|getEffectiveTimePreferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|timePrefs
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|timePrefs
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimePref
name|tp
init|=
operator|(
name|TimePref
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|tp
operator|.
name|getTimePattern
argument_list|()
operator|!=
literal|null
condition|)
name|ret
operator|.
name|add
argument_list|(
name|tp
operator|.
name|getTimePattern
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
comment|/* getRoomPreferences()      * @return ArrayList of RoomPrefs      */
specifier|public
name|Set
name|getRoomPreferences
parameter_list|()
block|{
return|return
name|getPreferences
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getEffectiveRoomPreferences()      * @return ArrayList of RoomPrefs      */
specifier|public
name|Set
name|getEffectiveRoomPreferences
parameter_list|()
block|{
return|return
name|effectivePreferences
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getBuildingPreferences()      * @return ArrayList of BuildingPrefs      */
specifier|public
name|Set
name|getBuildingPreferences
parameter_list|()
block|{
return|return
name|getPreferences
argument_list|(
name|BuildingPref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getEffectiveBuildingPreferences()      * @return ArrayList of BuildingPrefs      */
specifier|public
name|Set
name|getEffectiveBuildingPreferences
parameter_list|()
block|{
return|return
name|effectivePreferences
argument_list|(
name|BuildingPref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getRoomFeaturePreferences()      * @return ArrayList of RoomFeaturePrefs      */
specifier|public
name|Set
name|getRoomFeaturePreferences
parameter_list|()
block|{
return|return
name|getPreferences
argument_list|(
name|RoomFeaturePref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getRoomGroupPreferences()      * @return ArrayList of RoomGroupPrefs      */
specifier|public
name|Set
name|getRoomGroupPreferences
parameter_list|()
block|{
return|return
name|getPreferences
argument_list|(
name|RoomGroupPref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getEffectiveRoomFeaturePreferences()      * @return ArrayList of RoomFeaturePrefs      */
specifier|public
name|Set
name|getEffectiveRoomFeaturePreferences
parameter_list|()
block|{
return|return
name|effectivePreferences
argument_list|(
name|RoomFeaturePref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getDistributionPreferences()      * @return ArrayList of DistributionPrefs      */
specifier|public
name|Set
name|getDistributionPreferences
parameter_list|()
block|{
return|return
name|getPreferences
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getEffectiveistributionPreferences()      * @return ArrayList of DistributionPrefs      */
specifier|public
name|Set
name|getEffectiveDistributionPreferences
parameter_list|()
block|{
return|return
name|effectivePreferences
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
return|;
block|}
comment|/* getPrefHtmlForPrefType()      * @parameter prefName - the Name of the Class of the Preference Type you want to see html for      * @return - a String of HTML to display the Preference      */
specifier|private
name|String
name|htmlForPrefs
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|Set
name|prefList
parameter_list|,
name|boolean
name|timeVertical
parameter_list|,
name|boolean
name|gridAsText
parameter_list|,
name|String
name|timeGridSize
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefList
operator|!=
literal|null
operator|&&
operator|!
name|prefList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|prefList
operator|.
name|toArray
argument_list|()
index|[
literal|0
index|]
operator|instanceof
name|TimePref
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|htmlForTimePrefs
argument_list|(
name|assignment
argument_list|,
name|prefList
argument_list|,
name|timeVertical
argument_list|,
name|gridAsText
argument_list|,
name|timeGridSize
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Iterator
name|it
init|=
name|prefList
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Preference
name|aPref
init|=
literal|null
decl_stmt|;
name|boolean
name|notFirst
init|=
literal|false
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
if|if
condition|(
name|notFirst
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<BR>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|notFirst
operator|=
literal|true
expr_stmt|;
block|}
name|aPref
operator|=
operator|(
name|Preference
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|aPref
operator|.
name|preferenceHtml
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|private
name|String
name|htmlForPrefs
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|Set
name|prefList
parameter_list|)
block|{
return|return
operator|(
name|htmlForTimePrefs
argument_list|(
name|assignment
argument_list|,
name|prefList
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
operator|)
return|;
block|}
specifier|private
name|String
name|htmlForTimePrefs
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|Set
name|timePrefList
parameter_list|,
name|boolean
name|timeVertical
parameter_list|,
name|boolean
name|gridAsText
parameter_list|,
name|String
name|timeGridSize
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|timePrefList
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|TimePref
name|tp
init|=
operator|(
name|TimePref
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|RequiredTimeTable
name|rtt
init|=
name|tp
operator|.
name|getRequiredTimeTable
argument_list|(
name|assignment
argument_list|)
decl_stmt|;
if|if
condition|(
name|gridAsText
condition|)
block|{
name|String
name|title
init|=
name|tp
operator|.
name|getTimePattern
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
name|title
operator|+=
literal|", assigned "
operator|+
name|assignment
operator|.
name|getPlacement
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<span title='"
operator|+
name|title
operator|+
literal|"'>"
operator|+
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|", "
argument_list|,
literal|"<br>"
argument_list|)
operator|+
literal|"</span>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|setDefaultSelection
argument_list|(
name|timeGridSize
argument_list|)
expr_stmt|;
name|File
name|imageFileName
init|=
literal|null
decl_stmt|;
try|try
block|{
name|imageFileName
operator|=
name|rtt
operator|.
name|createImage
argument_list|(
name|timeVertical
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|String
name|title
init|=
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
name|title
operator|+=
literal|", assigned "
operator|+
name|assignment
operator|.
name|getPlacement
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
if|if
condition|(
name|imageFileName
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"<img border='0' src='temp/"
operator|+
operator|(
name|imageFileName
operator|.
name|getName
argument_list|()
operator|)
operator|+
literal|"' title='"
operator|+
name|title
operator|+
literal|"'>&nbsp;"
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
literal|"<span title='"
operator|+
name|title
operator|+
literal|"'>"
operator|+
name|rtt
operator|.
name|getModel
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"</span>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
specifier|abstract
name|String
name|htmlLabel
parameter_list|()
function_decl|;
specifier|public
name|String
name|effectiveTimePatternHtml
parameter_list|()
block|{
return|return
operator|(
operator|new
name|String
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|getPrefHtmlForPrefType
parameter_list|(
name|Class
name|type
parameter_list|)
block|{
return|return
operator|(
name|htmlForPrefs
argument_list|(
literal|null
argument_list|,
name|getPreferences
argument_list|(
name|type
argument_list|)
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|getEffectivePrefHtmlForPrefType
parameter_list|(
name|Class
name|type
parameter_list|)
block|{
return|return
operator|(
name|htmlForPrefs
argument_list|(
literal|null
argument_list|,
name|effectivePreferences
argument_list|(
name|type
argument_list|)
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|getEffectivePrefHtmlForPrefType
parameter_list|(
name|Class
name|type
parameter_list|,
name|boolean
name|timeVertical
parameter_list|,
name|boolean
name|gridAsText
parameter_list|,
name|String
name|timeGridSize
parameter_list|)
block|{
return|return
operator|(
name|htmlForPrefs
argument_list|(
literal|null
argument_list|,
name|effectivePreferences
argument_list|(
name|type
argument_list|)
argument_list|,
name|timeVertical
argument_list|,
name|gridAsText
argument_list|,
name|timeGridSize
argument_list|)
operator|)
return|;
block|}
specifier|public
name|String
name|getEffectivePrefHtmlForPrefType
parameter_list|(
name|Assignment
name|assignment
parameter_list|,
name|Class
name|type
parameter_list|,
name|boolean
name|timeVertical
parameter_list|,
name|boolean
name|gridAsText
parameter_list|,
name|String
name|timeGridSize
parameter_list|)
block|{
return|return
operator|(
name|htmlForPrefs
argument_list|(
name|assignment
argument_list|,
name|effectivePreferences
argument_list|(
name|type
argument_list|)
argument_list|,
name|timeVertical
argument_list|,
name|gridAsText
argument_list|,
name|timeGridSize
argument_list|)
operator|)
return|;
block|}
comment|/*     public Set effectivePreferences(){     	return(this.getPreferences());     }     */
specifier|private
name|HashMap
name|timePrefHash
parameter_list|(
name|Collection
name|timePrefList
parameter_list|)
block|{
name|HashMap
name|hm
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|timePrefList
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|TimePref
name|t
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|t
operator|=
operator|(
name|TimePref
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|hm
operator|.
name|containsKey
argument_list|(
name|t
operator|.
name|getTimePattern
argument_list|()
argument_list|)
condition|)
block|{
operator|(
operator|(
name|ArrayList
operator|)
name|hm
operator|.
name|get
argument_list|(
name|t
operator|.
name|getTimePattern
argument_list|()
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ArrayList
name|a
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|a
operator|.
name|add
argument_list|(
name|t
argument_list|)
expr_stmt|;
name|hm
operator|.
name|put
argument_list|(
name|t
operator|.
name|getTimePattern
argument_list|()
argument_list|,
name|a
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|(
name|hm
operator|)
return|;
block|}
specifier|protected
specifier|abstract
name|boolean
name|canUserEdit
parameter_list|(
name|User
name|user
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|boolean
name|canUserView
parameter_list|(
name|User
name|user
parameter_list|)
function_decl|;
specifier|public
name|boolean
name|isEditableBy
parameter_list|(
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
operator|(
name|this
operator|.
name|canUserEdit
argument_list|(
name|user
argument_list|)
operator|)
return|;
block|}
specifier|public
name|boolean
name|isViewableBy
parameter_list|(
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
if|if
condition|(
name|user
operator|.
name|isAdmin
argument_list|()
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
if|if
condition|(
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|VIEW_ALL_ROLE
argument_list|)
operator|||
name|user
operator|.
name|getCurrentRole
argument_list|()
operator|.
name|equals
argument_list|(
name|Roles
operator|.
name|EXAM_MGR_ROLE
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|this
operator|.
name|canUserEdit
argument_list|(
name|user
argument_list|)
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
return|return
operator|(
name|this
operator|.
name|canUserView
argument_list|(
name|user
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Class
name|getInstanceOf
parameter_list|()
block|{
return|return
name|PreferenceGroup
operator|.
name|class
return|;
block|}
specifier|public
name|Set
name|getPreferences
parameter_list|(
name|Class
name|type
parameter_list|)
block|{
return|return
name|getPreferences
argument_list|(
name|type
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
name|Set
name|getPreferences
parameter_list|(
name|Class
name|type
parameter_list|,
name|PreferenceGroup
name|appliesTo
parameter_list|)
block|{
name|Set
name|ret
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
name|Iterator
name|i
init|=
literal|null
decl_stmt|;
try|try
block|{
name|i
operator|=
name|getPreferences
argument_list|()
operator|.
name|iterator
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ObjectNotFoundException
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
literal|"Exception "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|" seen for "
operator|+
name|this
argument_list|)
expr_stmt|;
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|refresh
argument_list|(
name|this
argument_list|)
expr_stmt|;
if|if
condition|(
name|getPreferences
argument_list|()
operator|!=
literal|null
condition|)
name|i
operator|=
name|getPreferences
argument_list|()
operator|.
name|iterator
argument_list|()
expr_stmt|;
else|else
name|i
operator|=
literal|null
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|i
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|==
literal|null
condition|)
block|{
return|return
operator|(
name|ret
operator|)
return|;
block|}
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Preference
name|preference
init|=
operator|(
name|Preference
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|appliesTo
operator|!=
literal|null
operator|&&
operator|!
name|preference
operator|.
name|appliesTo
argument_list|(
name|appliesTo
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|type
operator|.
name|isInstance
argument_list|(
name|preference
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|preference
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|public
name|Set
name|effectivePreferences
parameter_list|(
name|Class
name|type
parameter_list|,
name|Vector
name|leadInstructors
parameter_list|)
block|{
return|return
name|effectivePreferences
argument_list|(
name|type
argument_list|)
return|;
block|}
specifier|public
name|Set
name|effectivePreferences
parameter_list|(
name|Class
name|type
parameter_list|)
block|{
return|return
name|getPreferences
argument_list|(
name|type
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
name|DatePattern
name|effectiveDatePattern
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|boolean
name|canUseHardTimePreferences
parameter_list|(
name|User
name|user
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|canUseHardRoomPreferences
parameter_list|(
name|User
name|user
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
specifier|public
name|Set
name|getAvailableRooms
parameter_list|()
block|{
return|return
operator|new
name|TreeSet
argument_list|()
return|;
block|}
specifier|public
name|Set
name|getAvailableBuildings
parameter_list|()
block|{
name|TreeSet
name|bldgs
init|=
operator|new
name|TreeSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|getAvailableRooms
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Location
name|location
init|=
operator|(
name|Location
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
name|bldgs
operator|.
name|add
argument_list|(
operator|(
operator|(
name|Room
operator|)
name|location
operator|)
operator|.
name|getBuilding
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|bldgs
return|;
block|}
specifier|public
name|Set
name|getAvailableRoomFeatures
parameter_list|()
block|{
return|return
operator|new
name|TreeSet
argument_list|(
name|RoomFeature
operator|.
name|getAllGlobalRoomFeatures
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Set
name|getAvailableRoomGroups
parameter_list|()
block|{
return|return
operator|new
name|TreeSet
argument_list|(
name|RoomGroup
operator|.
name|getAllGlobalRoomGroups
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Set
name|getExamPeriodPreferences
parameter_list|()
block|{
return|return
name|getPreferences
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
return|;
block|}
specifier|public
name|Set
name|getEffectiveExamPeriodPreferences
parameter_list|()
block|{
return|return
name|effectivePreferences
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
return|;
block|}
block|}
end_class

end_unit

