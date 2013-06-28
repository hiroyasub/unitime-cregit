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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|org
operator|.
name|hibernate
operator|.
name|Hibernate
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
name|hibernate
operator|.
name|Query
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
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseDistributionPref
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
name|DistributionPrefDAO
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
name|PreferenceGroupDAO
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

begin_class
specifier|public
class|class
name|DistributionPref
extends|extends
name|BaseDistributionPref
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/** Request Attribute name for Dist Prefs **/
specifier|public
specifier|static
specifier|final
name|String
name|DIST_PREF_REQUEST_ATTR
init|=
literal|"distPrefs"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sGroupingNone
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sGroupingProgressive
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sGroupingByTwo
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sGroupingByThree
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sGroupingByFour
init|=
literal|4
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sGroupingByFive
init|=
literal|5
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sGroupingPairWise
init|=
literal|6
decl_stmt|;
comment|//TODO put this stuff into the database (as a some kind of DistributionPreferenceGroupingType object)
specifier|public
specifier|static
name|String
index|[]
name|sGroupings
init|=
operator|new
name|String
index|[]
block|{
literal|"All Classes"
block|,
literal|"Progressive"
block|,
literal|"Groups of Two"
block|,
literal|"Groups of Three"
block|,
literal|"Groups of Four"
block|,
literal|"Groups of Five"
block|,
literal|"Pairwise"
block|}
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sGroupingsSufix
init|=
operator|new
name|String
index|[]
block|{
literal|""
block|,
literal|" Progressive"
block|,
literal|" Groups of Two"
block|,
literal|" Groups of Three"
block|,
literal|" Groups of Four"
block|,
literal|" Groups of Five"
block|,
literal|" Pairwise"
block|}
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sGroupingsSufixShort
init|=
operator|new
name|String
index|[]
block|{
literal|""
block|,
literal|" Prg"
block|,
literal|" Go2"
block|,
literal|" Go3"
block|,
literal|" Go4"
block|,
literal|" Go5"
block|,
literal|" Pair"
block|}
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sGroupingsDescription
init|=
operator|new
name|String
index|[]
block|{
comment|//All Classes
literal|"The constraint will apply to all classes in the selected distribution set. "
operator|+
literal|"For example, a Back-to-Back constraint among three classes seeks to place all three classes "
operator|+
literal|"sequentially in time such that there are no intervening class times (transition time between "
operator|+
literal|"classes is taken into account, e.g., if the first class ends at 8:20, the second has to start at 8:30)."
block|,
comment|//Progressive
literal|"The distribution constraint is created between classes in one scheduling subpart and the "
operator|+
literal|"appropriate class(es) in one or more other subparts. This structure links child and parent "
operator|+
literal|"classes together if subparts have been grouped. Otherwise the first class in one subpart is "
operator|+
literal|"linked to the the first class in the second subpart, etc."
block|,
comment|//Groups of Two
literal|"The distribution constraint is applied only on subsets containing two classes in the selected "
operator|+
literal|"distribution set.  A constraint is posted between the first two classes (in the order listed), "
operator|+
literal|"then between the second two classes, etc."
block|,
comment|//Groups of Three
literal|"The distribution constraint is applied only on subsets containing three classes in the selected "
operator|+
literal|"distribution set.  A constraint is posted between the first three classes (in the order listed), "
operator|+
literal|"then between the second three classes, etc."
block|,
comment|//Groups of Four
literal|"The distribution constraint is applied only on subsets containing four classes in the selected "
operator|+
literal|"distribution set.  A constraint is posted between the first four classes (in the order listed), "
operator|+
literal|"then between the second four classes, etc."
block|,
comment|//Groups of Five
literal|"The distribution constraint is applied only on subsets containing five classes in the selected "
operator|+
literal|"distribution set.  A constraint is posted between the first five classes (in the order listed), "
operator|+
literal|"then between the second five classes, etc."
block|,
comment|//Pairwise
literal|"The distribution constraint is created between every pair of classes in the selected distribution set. "
operator|+
literal|"Therefore, if n classes are in the set, n(n-1)/2 constraints will be posted among the classes. "
operator|+
literal|"This structure should not be used with \"required\" or \"prohibited\" preferences on sets containing "
operator|+
literal|"more than a few classes."
block|}
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|DistributionPref
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|DistributionPref
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
name|preferenceText
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
literal|"<BR>"
argument_list|,
literal|"<BR>"
argument_list|,
literal|""
argument_list|)
return|;
block|}
specifier|public
name|String
name|preferenceText
parameter_list|(
name|boolean
name|includeDistrObjects
parameter_list|,
name|boolean
name|abbv
parameter_list|,
name|String
name|objQuotationLeft
parameter_list|,
name|String
name|objSeparator
parameter_list|,
name|String
name|objQuotationRight
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
name|abbv
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|getDistributionType
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"<"
argument_list|,
literal|"&lt;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|">"
argument_list|,
literal|"&gt;"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
name|getGrouping
argument_list|()
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
name|sGroupingsSufixShort
index|[
name|getGrouping
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getGrouping
argument_list|()
operator|.
name|intValue
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|getDistributionType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
name|getGrouping
argument_list|()
argument_list|)
condition|)
name|sb
operator|.
name|append
argument_list|(
name|sGroupingsSufix
index|[
name|getGrouping
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getGrouping
argument_list|()
operator|.
name|intValue
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|includeDistrObjects
condition|)
block|{
if|if
condition|(
name|getDistributionObjects
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getDistributionObjects
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|objQuotationLeft
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|getOrderedSetOfDistributionObjects
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DistributionObject
name|distObj
init|=
operator|(
name|DistributionObject
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
empty_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|distObj
operator|.
name|preferenceText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
name|objSeparator
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|objQuotationRight
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|getOwner
argument_list|()
operator|instanceof
name|DepartmentalInstructor
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" ("
operator|+
operator|(
operator|(
name|DepartmentalInstructor
operator|)
name|getOwner
argument_list|()
operator|)
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatShort
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|abbv
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|objQuotationLeft
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
operator|(
operator|(
name|DepartmentalInstructor
operator|)
name|getOwner
argument_list|()
operator|)
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ClassInstructor
name|ci
init|=
operator|(
name|ClassInstructor
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|ci
operator|.
name|getClassInstructing
argument_list|()
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
name|objSeparator
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|objQuotationRight
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
argument_list|()
decl_stmt|;
name|String
name|color
init|=
name|getPrefLevel
argument_list|()
operator|.
name|prefcolor
argument_list|()
decl_stmt|;
if|if
condition|(
name|PreferenceLevel
operator|.
name|sNeutral
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
name|color
operator|=
literal|"gray"
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<span style='color:"
operator|+
name|color
operator|+
literal|";font-weight:bold;' onmouseover=\"showGwtHint(this, '"
operator|+
name|getPrefLevel
argument_list|()
operator|.
name|getPrefName
argument_list|()
operator|+
literal|" "
operator|+
name|preferenceText
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|,
literal|"<ul><li>"
argument_list|,
literal|"<li>"
argument_list|,
literal|"</ul>"
argument_list|)
operator|+
literal|"');\" onmouseout=\"hideGwtHint();\">"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|preferenceText
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
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
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/** 	 * @param schedulingSubpart_ 	 * @return 	 */
specifier|public
name|boolean
name|appliesTo
parameter_list|(
name|SchedulingSubpart
name|schedulingSubpart
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|getDistributionObjects
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|Iterator
name|it
init|=
name|this
operator|.
name|getDistributionObjects
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|DistributionObject
name|dObj
init|=
operator|(
name|DistributionObject
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//SchedulingSubpart check
comment|//no checking whether dObj.getPrefGroup() is SchedulingSubpart not needed since all PreferenceGroups have unique ids
if|if
condition|(
name|dObj
operator|.
name|getPrefGroup
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|schedulingSubpart
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/** 	 * @param aClass 	 * @return 	 */
specifier|public
name|boolean
name|appliesTo
parameter_list|(
name|Class_
name|aClass
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|getDistributionObjects
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
name|Iterator
name|it
init|=
literal|null
decl_stmt|;
try|try
block|{
name|it
operator|=
name|getDistributionObjects
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
name|it
operator|=
name|getDistributionObjects
argument_list|()
operator|.
name|iterator
argument_list|()
expr_stmt|;
block|}
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|DistributionObject
name|dObj
init|=
operator|(
name|DistributionObject
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//Class_ check
comment|//no checking whether dObj.getPrefGroup() is Class_ not needed since all PreferenceGroups have unique ids
if|if
condition|(
name|dObj
operator|.
name|getPrefGroup
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|aClass
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
comment|//SchedulingSubpart check
name|SchedulingSubpart
name|ss
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|Hibernate
operator|.
name|isInitialized
argument_list|(
name|dObj
operator|.
name|getPrefGroup
argument_list|()
argument_list|)
condition|)
block|{
if|if
condition|(
name|dObj
operator|.
name|getPrefGroup
argument_list|()
operator|instanceof
name|SchedulingSubpart
condition|)
block|{
name|ss
operator|=
operator|(
name|SchedulingSubpart
operator|)
name|dObj
operator|.
name|getPrefGroup
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
comment|//dObj.getPrefGroup() is a proxy -> try to load it
name|PreferenceGroup
name|pg
init|=
operator|(
operator|new
name|PreferenceGroupDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
name|dObj
operator|.
name|getPrefGroup
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|pg
operator|!=
literal|null
operator|&&
name|pg
operator|instanceof
name|SchedulingSubpart
condition|)
name|ss
operator|=
operator|(
name|SchedulingSubpart
operator|)
name|pg
expr_stmt|;
block|}
if|if
condition|(
name|ss
operator|!=
literal|null
operator|&&
name|ss
operator|.
name|getClasses
argument_list|()
operator|!=
literal|null
operator|&&
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|Iterator
name|it2
init|=
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|it2
operator|.
name|hasNext
argument_list|()
condition|;
control|)
if|if
condition|(
operator|(
operator|(
name|Class_
operator|)
name|it2
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|aClass
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
comment|// overide default
specifier|public
name|boolean
name|appliesTo
parameter_list|(
name|PreferenceGroup
name|group
parameter_list|)
block|{
if|if
condition|(
name|group
operator|instanceof
name|Class_
condition|)
return|return
name|appliesTo
argument_list|(
operator|(
name|Class_
operator|)
name|group
argument_list|)
return|;
if|if
condition|(
name|group
operator|instanceof
name|SchedulingSubpart
condition|)
return|return
name|appliesTo
argument_list|(
operator|(
name|SchedulingSubpart
operator|)
name|group
argument_list|)
return|;
return|return
literal|false
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
name|DistributionPref
name|p
init|=
operator|(
name|DistributionPref
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getDistributionType
argument_list|()
operator|.
name|getReference
argument_list|()
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getDistributionType
argument_list|()
operator|.
name|getReference
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
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|p
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|DistributionPref
name|pref
init|=
operator|new
name|DistributionPref
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
name|setDistributionObjects
argument_list|(
operator|new
name|HashSet
argument_list|<
name|DistributionObject
argument_list|>
argument_list|(
name|getDistributionObjects
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|pref
operator|.
name|setDistributionType
argument_list|(
name|getDistributionType
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
return|return
name|equals
argument_list|(
name|other
argument_list|)
return|;
block|}
comment|/** Ordered set of distribution objects */
specifier|public
name|Set
argument_list|<
name|DistributionObject
argument_list|>
name|getOrderedSetOfDistributionObjects
parameter_list|()
block|{
try|try
block|{
return|return
operator|new
name|TreeSet
argument_list|<
name|DistributionObject
argument_list|>
argument_list|(
name|getDistributionObjects
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|ObjectNotFoundException
name|ex
parameter_list|)
block|{
operator|(
operator|new
name|DistributionPrefDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|refresh
argument_list|(
name|this
argument_list|)
expr_stmt|;
return|return
operator|new
name|TreeSet
argument_list|<
name|DistributionObject
argument_list|>
argument_list|(
name|getDistributionObjects
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
name|String
name|getGroupingName
parameter_list|()
block|{
if|if
condition|(
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
name|getGrouping
argument_list|()
argument_list|)
condition|)
return|return
literal|null
return|;
return|return
name|sGroupings
index|[
name|getGrouping
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getGrouping
argument_list|()
operator|.
name|intValue
argument_list|()
index|]
return|;
block|}
specifier|public
name|String
name|getGroupingSufix
parameter_list|()
block|{
if|if
condition|(
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
operator|.
name|equals
argument_list|(
name|getGrouping
argument_list|()
argument_list|)
condition|)
return|return
literal|null
return|;
return|return
name|sGroupingsSufix
index|[
name|getGrouping
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|getGrouping
argument_list|()
operator|.
name|intValue
argument_list|()
index|]
return|;
block|}
specifier|public
specifier|static
name|String
name|getGroupingDescription
parameter_list|(
name|int
name|grouping
parameter_list|)
block|{
return|return
name|sGroupingsDescription
index|[
name|grouping
index|]
return|;
block|}
specifier|public
specifier|static
name|Collection
name|getPreferences
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|ownerId
parameter_list|,
name|boolean
name|useControllingCourseOfferingManager
parameter_list|,
name|Long
name|uniqueId
parameter_list|)
block|{
return|return
name|getPreferences
argument_list|(
name|sessionId
argument_list|,
name|ownerId
argument_list|,
name|useControllingCourseOfferingManager
argument_list|,
name|uniqueId
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Collection
name|getPreferences
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|ownerId
parameter_list|,
name|boolean
name|useControllingCourseOfferingManager
parameter_list|,
name|Long
name|uniqueId
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"select distinct dp "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" from "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" DistributionPref as dp "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" inner join dp.distributionObjects as do, "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" Class_ as c "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" inner join c.schedulingSubpart as ss inner join ss.instrOfferingConfig.instructionalOffering as io "
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
operator|||
name|ownerId
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" inner join io.courseOfferings as co "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"where "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" (c.uniqueId = do.prefGroup.uniqueId or ss.uniqueId = do.prefGroup.uniqueId) and "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" io.session.uniqueId = :sessionId "
argument_list|)
expr_stmt|;
if|if
condition|(
name|ownerId
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and ("
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"((c.managingDept is not null and c.managingDept.uniqueId = :ownerId )"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" or (c.managingDept is null "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" and co.isControl = true "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"and co.subjectArea.department.uniqueId = :ownerId))"
argument_list|)
expr_stmt|;
if|if
condition|(
name|useControllingCourseOfferingManager
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" or (co.isControl = true"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" and co.subjectArea.department.uniqueId = :ownerId)"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|")"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|uniqueId
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and (c.uniqueId = :uniqueId or ss.uniqueId = :uniqueId or io.uniqueId = :uniqueId))"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and co.subjectArea.uniqueId=:subjectAreaId "
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|!=
literal|null
operator|&&
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and co.courseNbr "
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|.
name|indexOf
argument_list|(
literal|'*'
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" like "
argument_list|)
expr_stmt|;
name|courseNbr
operator|=
name|courseNbr
operator|.
name|replace
argument_list|(
literal|'*'
argument_list|,
literal|'%'
argument_list|)
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" = "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|":courseNbr"
argument_list|)
expr_stmt|;
block|}
block|}
name|Query
name|q
init|=
operator|(
operator|new
name|DistributionPrefDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ownerId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"ownerId"
argument_list|,
name|ownerId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|uniqueId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"uniqueId"
argument_list|,
name|uniqueId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
condition|)
block|{
name|q
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|subjectAreaId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|!=
literal|null
operator|&&
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|q
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Collection
name|getInstructorPreferences
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|ownerId
parameter_list|,
name|Long
name|subjectAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"select distinct dp "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" from "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" DistributionPref as dp, "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" DepartmentalInstructor as di "
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" inner join di.classes as ci inner join ci.classInstructing.schedulingSubpart.instrOfferingConfig.instructionalOffering.courseOfferings as co "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"where "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" dp.owner = di "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" and di.department.session.uniqueId = :sessionId "
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and ci.lead = true "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" and co.isControl = true "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" and co.subjectArea.uniqueId = :subjectAreaId "
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|!=
literal|null
operator|&&
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and co.courseNbr "
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|.
name|indexOf
argument_list|(
literal|'*'
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" like "
argument_list|)
expr_stmt|;
name|courseNbr
operator|=
name|courseNbr
operator|.
name|replace
argument_list|(
literal|'*'
argument_list|,
literal|'%'
argument_list|)
operator|.
name|toUpperCase
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" = "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|":courseNbr"
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|ownerId
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" and di.department.uniqueId = :ownerId "
argument_list|)
expr_stmt|;
block|}
name|Query
name|q
init|=
operator|(
operator|new
name|DistributionPrefDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ownerId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"ownerId"
argument_list|,
name|ownerId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjectAreaId
operator|!=
literal|null
condition|)
block|{
name|q
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|subjectAreaId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|courseNbr
operator|!=
literal|null
operator|&&
name|courseNbr
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
operator|.
name|toUpperCase
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|q
operator|.
name|list
argument_list|()
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
block|{
if|if
condition|(
name|getDistributionType
argument_list|()
operator|.
name|getAllowedPref
argument_list|()
operator|.
name|indexOf
argument_list|(
name|PreferenceLevel
operator|.
name|sCharLevelStronglyPreferred
argument_list|)
operator|>=
literal|0
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
else|else
return|return
literal|false
return|;
block|}
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
block|{
if|if
condition|(
name|getDistributionType
argument_list|()
operator|.
name|getAllowedPref
argument_list|()
operator|.
name|indexOf
argument_list|(
name|PreferenceLevel
operator|.
name|sCharLevelStronglyDiscouraged
argument_list|)
operator|>=
literal|0
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
else|else
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
specifier|static
name|DistributionPref
name|findByIdRolledForwardFrom
parameter_list|(
name|Long
name|uidRolledForwardFrom
parameter_list|)
block|{
return|return
operator|(
name|DistributionPref
operator|)
operator|new
name|DistributionPrefDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select dp from DistributionPref dp where "
operator|+
literal|"dp.uniqueIdRolledForwardFrom=:uidRolledFrom"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"uidRolledFrom"
argument_list|,
name|uidRolledForwardFrom
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|preferenceText
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
literal|" "
argument_list|,
literal|", "
argument_list|,
literal|""
argument_list|)
operator|)
return|;
block|}
block|}
end_class

end_unit

