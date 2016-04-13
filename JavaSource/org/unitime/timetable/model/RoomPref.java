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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|ToolBox
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
name|BaseRoomPref
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|RoomPref
extends|extends
name|BaseRoomPref
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
name|RoomPref
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|RoomPref
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
operator|(
name|this
operator|.
name|getRoom
argument_list|()
operator|.
name|getLabel
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
name|RoomPref
name|p
init|=
operator|(
name|RoomPref
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getRoom
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getRoom
argument_list|()
operator|.
name|getLabel
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
name|RoomPref
name|pref
init|=
operator|new
name|RoomPref
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
name|setRoom
argument_list|(
name|getRoom
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
name|RoomPref
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
name|getRoom
argument_list|()
argument_list|,
operator|(
operator|(
name|RoomPref
operator|)
name|other
operator|)
operator|.
name|getRoom
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|preferenceHtml
parameter_list|(
name|String
name|nameFormat
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
name|ApplicationProperty
operator|.
name|PreferencesHighlighClassPreferences
operator|.
name|isTrue
argument_list|()
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
literal|", "
operator|+
name|MSG
operator|.
name|prefOwnerClass
argument_list|()
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
literal|", "
operator|+
name|MSG
operator|.
name|prefOwnerSchedulingSubpart
argument_list|()
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
literal|", "
operator|+
name|MSG
operator|.
name|prefOwnerInstructor
argument_list|()
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
literal|", "
operator|+
name|MSG
operator|.
name|prefOwnerExamination
argument_list|()
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
literal|", "
operator|+
name|MSG
operator|.
name|prefOwnerDepartment
argument_list|()
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
literal|", "
operator|+
name|MSG
operator|.
name|prefOwnerSession
argument_list|()
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"onmouseover=\"showGwtRoomHint(this, '"
operator|+
name|getRoom
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"', '"
operator|+
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
operator|+
literal|" "
operator|+
name|MSG
operator|.
name|prefRoom
argument_list|()
operator|+
literal|" {0} ({1}"
operator|+
name|owner
operator|+
literal|")');\" onmouseout=\"hideGwtRoomHint();\">"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|this
operator|.
name|preferenceAbbv
argument_list|()
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
name|preferenceTitle
parameter_list|()
block|{
return|return
name|MSG
operator|.
name|prefTitleRoom
argument_list|(
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
argument_list|,
name|getRoom
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Type
name|getType
parameter_list|()
block|{
return|return
name|Type
operator|.
name|ROOM
return|;
block|}
block|}
end_class

end_unit

