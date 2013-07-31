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
comment|/** Blank Pref Value **/
specifier|public
specifier|static
specifier|final
name|String
name|BLANK_PREF_VALUE
init|=
literal|"-"
decl_stmt|;
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
parameter_list|()
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
name|sb
operator|.
name|append
argument_list|(
literal|"style='color:"
operator|+
name|this
operator|.
name|getPrefLevel
argument_list|()
operator|.
name|prefcolor
argument_list|()
operator|+
literal|";font-weight:bold;' "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"style='font-weight:bold;' "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"onmouseover=\"showGwtHint(this, '"
operator|+
name|preferenceTitle
argument_list|()
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
operator|new
name|Long
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
block|}
end_class

end_unit

