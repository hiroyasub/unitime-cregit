begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|course
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
name|java
operator|.
name|util
operator|.
name|BitSet
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
name|NaturalOrderComparator
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
name|Assignment
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
name|DatePattern
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
name|DatePatternDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ClassDateInfo
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|ClassDateInfo
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1113308106992466641L
decl_stmt|;
specifier|private
specifier|static
name|NaturalOrderComparator
name|sCmp
init|=
operator|new
name|NaturalOrderComparator
argument_list|()
decl_stmt|;
specifier|private
name|Long
name|iId
decl_stmt|,
name|iClassId
decl_stmt|;
specifier|private
name|String
name|iName
decl_stmt|;
specifier|private
name|BitSet
name|iPattern
decl_stmt|;
specifier|private
name|int
name|iPreference
decl_stmt|;
specifier|private
specifier|transient
name|DatePattern
name|iDatePattern
init|=
literal|null
decl_stmt|;
specifier|public
name|ClassDateInfo
parameter_list|(
name|Long
name|id
parameter_list|,
name|Long
name|classId
parameter_list|,
name|String
name|name
parameter_list|,
name|BitSet
name|pattern
parameter_list|,
name|int
name|preference
parameter_list|)
block|{
name|iId
operator|=
name|id
expr_stmt|;
name|iClassId
operator|=
name|classId
expr_stmt|;
name|iName
operator|=
name|name
expr_stmt|;
name|iPattern
operator|=
name|pattern
expr_stmt|;
name|iPreference
operator|=
name|preference
expr_stmt|;
block|}
specifier|public
name|ClassDateInfo
parameter_list|(
name|Assignment
name|a
parameter_list|,
name|int
name|preference
parameter_list|)
block|{
name|iId
operator|=
name|a
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iClassId
operator|=
name|a
operator|.
name|getClassId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|a
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iPattern
operator|=
name|a
operator|.
name|getDatePattern
argument_list|()
operator|.
name|getPatternBitSet
argument_list|()
expr_stmt|;
name|iPreference
operator|=
name|preference
expr_stmt|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|Long
name|getClassId
parameter_list|()
block|{
return|return
name|iClassId
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|BitSet
name|getPattern
parameter_list|()
block|{
return|return
name|iPattern
return|;
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
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|iId
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
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
name|ClassDateInfo
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|ClassDateInfo
operator|)
name|o
operator|)
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
specifier|public
name|String
name|toHtml
parameter_list|()
block|{
return|return
literal|"<span style='color:"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|getName
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|String
name|toLongHtml
parameter_list|()
block|{
return|return
literal|"<span style='color:"
operator|+
name|PreferenceLevel
operator|.
name|int2color
argument_list|(
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|getName
argument_list|()
operator|+
literal|"</span>"
operator|+
literal|"<img style=\"cursor: pointer;\" src=\"scripts/jscalendar/calendar_1.gif\" border=\"0\" "
operator|+
literal|"onclick=\"showGwtDialog('Preview of "
operator|+
name|getName
argument_list|()
operator|+
literal|"', 'user/dispDatePattern.jsp?id="
operator|+
name|getId
argument_list|()
operator|+
literal|"&class="
operator|+
name|getClassId
argument_list|()
operator|+
literal|"','840','520');\">"
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ClassDateInfo
name|other
parameter_list|)
block|{
name|int
name|cmp
init|=
name|sCmp
operator|.
name|compare
argument_list|(
name|getName
argument_list|()
argument_list|,
name|other
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|cmp
operator|==
literal|0
condition|?
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|other
operator|.
name|getId
argument_list|()
argument_list|)
else|:
name|cmp
operator|)
return|;
block|}
specifier|public
name|boolean
name|overlaps
parameter_list|(
name|ClassDateInfo
name|date
parameter_list|)
block|{
return|return
name|getPattern
argument_list|()
operator|.
name|intersects
argument_list|(
name|date
operator|.
name|getPattern
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|DatePattern
name|getDatePattern
parameter_list|()
block|{
if|if
condition|(
name|iDatePattern
operator|==
literal|null
condition|)
name|iDatePattern
operator|=
name|DatePatternDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|getId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iDatePattern
return|;
block|}
block|}
end_class

end_unit

