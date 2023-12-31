begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|springframework
operator|.
name|web
operator|.
name|util
operator|.
name|HtmlUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|defaults
operator|.
name|ApplicationProperty
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
name|BasePreference
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|Preference
extends|extends
name|BasePreference
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
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/** Blank Pref Value **/
specifier|public
specifier|static
specifier|final
name|String
name|BLANK_PREF_VALUE
init|=
literal|"-"
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|Type
block|{
name|TIME
argument_list|(
name|TimePref
operator|.
name|class
argument_list|)
block|,
name|ROOM
argument_list|(
name|RoomPref
operator|.
name|class
argument_list|)
block|,
name|ROOM_GROUP
argument_list|(
name|RoomGroupPref
operator|.
name|class
argument_list|)
block|,
name|ROOM_FEATURE
argument_list|(
name|RoomFeaturePref
operator|.
name|class
argument_list|)
block|,
name|BUILDING
argument_list|(
name|BuildingPref
operator|.
name|class
argument_list|)
block|,
name|DISTRIBUTION
argument_list|(
name|DistributionPref
operator|.
name|class
argument_list|)
block|,
name|PERIOD
argument_list|(
name|ExamPeriodPref
operator|.
name|class
argument_list|)
block|,
name|DATE
argument_list|(
name|DatePatternPref
operator|.
name|class
argument_list|)
block|,
name|ATTRIBUTE
argument_list|(
name|InstructorAttributePref
operator|.
name|class
argument_list|)
block|,
name|COURSE
argument_list|(
name|InstructorCoursePref
operator|.
name|class
argument_list|)
block|,
name|INSTRUCTOR
argument_list|(
name|InstructorPref
operator|.
name|class
argument_list|)
block|;
name|Class
argument_list|<
name|?
extends|extends
name|Preference
argument_list|>
name|iClazz
decl_stmt|;
name|Type
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|Preference
argument_list|>
name|clazz
parameter_list|)
block|{
name|iClazz
operator|=
name|clazz
expr_stmt|;
block|}
specifier|public
name|Class
argument_list|<
name|?
extends|extends
name|Preference
argument_list|>
name|getImplementation
parameter_list|()
block|{
return|return
name|iClazz
return|;
block|}
name|int
name|flag
parameter_list|()
block|{
return|return
literal|1
operator|<<
name|ordinal
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|in
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|flags
operator|&
name|flag
argument_list|()
operator|)
operator|!=
literal|0
return|;
block|}
specifier|public
name|int
name|set
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
else|:
name|flags
operator|+
name|flag
argument_list|()
operator|)
return|;
block|}
specifier|public
name|int
name|clear
parameter_list|(
name|int
name|flags
parameter_list|)
block|{
return|return
operator|(
name|in
argument_list|(
name|flags
argument_list|)
condition|?
name|flags
operator|-
name|flag
argument_list|()
else|:
name|flags
operator|)
return|;
block|}
specifier|public
specifier|static
name|int
name|toInt
parameter_list|(
name|Type
modifier|...
name|types
parameter_list|)
block|{
name|int
name|ret
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Type
name|t
range|:
name|types
control|)
name|ret
operator|+=
name|t
operator|.
name|flag
argument_list|()
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|Preference
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Preference
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
name|preferenceTitle
parameter_list|(
name|String
name|nameFormat
parameter_list|)
block|{
return|return
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
operator|+
literal|" "
operator|+
name|preferenceText
argument_list|()
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
literal|" "
operator|+
name|preferenceText
argument_list|()
return|;
block|}
specifier|public
name|String
name|preferenceHtml
parameter_list|(
name|String
name|nameFormat
parameter_list|)
block|{
return|return
name|preferenceHtml
argument_list|(
name|nameFormat
argument_list|,
name|ApplicationProperty
operator|.
name|PreferencesHighlighClassPreferences
operator|.
name|isTrue
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|preferenceDescription
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|preferenceHtml
parameter_list|(
name|String
name|nameFormat
parameter_list|,
name|boolean
name|highlightClassPrefs
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"<span "
argument_list|)
decl_stmt|;
name|String
name|style
init|=
literal|"font-weight:bold;"
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|getPrefId
argument_list|()
operator|.
name|intValue
argument_list|()
operator|!=
literal|4
condition|)
block|{
name|style
operator|+=
literal|"color:"
operator|+
name|this
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|prefcolor
argument_list|()
operator|+
literal|";"
expr_stmt|;
block|}
if|if
condition|(
name|this
operator|.
name|getOwner
argument_list|()
operator|!=
literal|null
operator|&&
name|this
operator|.
name|getOwner
argument_list|()
operator|instanceof
name|Class_
operator|&&
name|highlightClassPrefs
condition|)
block|{
name|style
operator|+=
literal|"background: #ffa;"
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"style='"
operator|+
name|style
operator|+
literal|"' "
argument_list|)
expr_stmt|;
name|String
name|owner
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|getOwner
argument_list|()
operator|!=
literal|null
operator|&&
name|getOwner
argument_list|()
operator|instanceof
name|Class_
condition|)
block|{
name|owner
operator|=
literal|" ("
operator|+
name|MSG
operator|.
name|prefOwnerClass
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
if|else if
condition|(
name|getOwner
argument_list|()
operator|!=
literal|null
operator|&&
name|getOwner
argument_list|()
operator|instanceof
name|SchedulingSubpart
condition|)
block|{
name|owner
operator|=
literal|" ("
operator|+
name|MSG
operator|.
name|prefOwnerSchedulingSubpart
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
if|else if
condition|(
name|getOwner
argument_list|()
operator|!=
literal|null
operator|&&
name|getOwner
argument_list|()
operator|instanceof
name|DepartmentalInstructor
condition|)
block|{
name|owner
operator|=
literal|" ("
operator|+
name|MSG
operator|.
name|prefOwnerInstructor
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
if|else if
condition|(
name|getOwner
argument_list|()
operator|!=
literal|null
operator|&&
name|getOwner
argument_list|()
operator|instanceof
name|Exam
condition|)
block|{
name|owner
operator|=
literal|" ("
operator|+
name|MSG
operator|.
name|prefOwnerExamination
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
if|else if
condition|(
name|getOwner
argument_list|()
operator|!=
literal|null
operator|&&
name|getOwner
argument_list|()
operator|instanceof
name|Department
condition|)
block|{
name|owner
operator|=
literal|" ("
operator|+
name|MSG
operator|.
name|prefOwnerDepartment
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
if|else if
condition|(
name|getOwner
argument_list|()
operator|!=
literal|null
operator|&&
name|getOwner
argument_list|()
operator|instanceof
name|Session
condition|)
block|{
name|owner
operator|=
literal|" ("
operator|+
name|MSG
operator|.
name|prefOwnerSession
argument_list|()
operator|+
literal|")"
expr_stmt|;
block|}
name|String
name|hint
init|=
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|preferenceTitle
argument_list|(
name|nameFormat
argument_list|)
operator|+
name|owner
argument_list|)
decl_stmt|;
name|String
name|description
init|=
name|preferenceDescription
argument_list|()
decl_stmt|;
if|if
condition|(
name|description
operator|!=
literal|null
operator|&&
operator|!
name|description
operator|.
name|isEmpty
argument_list|()
condition|)
name|hint
operator|+=
literal|"<br>"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|description
operator|.
name|replace
argument_list|(
literal|"\'"
argument_list|,
literal|"\\\'"
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"onmouseover=\"showGwtHint(this, '"
operator|+
name|hint
operator|+
literal|"');\" onmouseout=\"hideGwtHint();\">"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|preferenceAbbv
argument_list|(
name|nameFormat
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</span>"
argument_list|)
expr_stmt|;
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|public
name|String
name|preferenceText
parameter_list|(
name|String
name|nameFormat
parameter_list|)
block|{
return|return
name|preferenceText
argument_list|()
return|;
block|}
specifier|public
specifier|abstract
name|String
name|preferenceText
parameter_list|()
function_decl|;
specifier|public
name|String
name|preferenceAbbv
parameter_list|()
block|{
return|return
name|preferenceText
argument_list|()
return|;
block|}
specifier|public
name|String
name|preferenceAbbv
parameter_list|(
name|String
name|nameFormat
parameter_list|)
block|{
return|return
name|preferenceAbbv
argument_list|()
return|;
block|}
comment|/* (non-Javadoc) 	 * @see java.lang.Comparable#compareTo(java.lang.Object) 	 */
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|Preference
operator|)
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Object must be of type Preference"
argument_list|)
throw|;
name|Preference
name|p
init|=
operator|(
name|Preference
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getClass
argument_list|()
operator|.
name|getName
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
if|if
condition|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
name|p
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
operator|-
literal|1
return|;
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
name|Long
operator|.
name|valueOf
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|boolean
name|appliesTo
parameter_list|(
name|PreferenceGroup
name|group
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|weakenHardPreferences
parameter_list|()
block|{
if|if
condition|(
name|PreferenceLevel
operator|.
name|sRequired
operator|.
name|equals
argument_list|(
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sStronglyPreferred
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|PreferenceLevel
operator|.
name|sProhibited
operator|.
name|equals
argument_list|(
name|getPrefLevel
argument_list|()
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
name|setPrefLevel
argument_list|(
name|PreferenceLevel
operator|.
name|getPreferenceLevel
argument_list|(
name|PreferenceLevel
operator|.
name|sStronglyDiscouraged
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
specifier|public
specifier|abstract
name|Object
name|clone
parameter_list|()
function_decl|;
specifier|public
specifier|abstract
name|boolean
name|isSame
parameter_list|(
name|Preference
name|other
parameter_list|)
function_decl|;
specifier|public
specifier|abstract
name|Type
name|getType
parameter_list|()
function_decl|;
block|}
end_class

end_unit

