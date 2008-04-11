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
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|base
operator|.
name|BaseItypeDesc
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
name|ItypeDescDAO
import|;
end_import

begin_class
specifier|public
class|class
name|ItypeDesc
extends|extends
name|BaseItypeDesc
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
specifier|public
specifier|static
name|String
index|[]
name|sBasicTypes
init|=
operator|new
name|String
index|[]
block|{
literal|"Extended"
block|,
literal|"Basic"
block|}
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|ItypeDesc
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|ItypeDesc
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Integer
name|itype
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|organized
parameter_list|)
block|{
name|super
argument_list|(
name|itype
argument_list|,
name|organized
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** Request attribute name for available itypes **/
specifier|public
specifier|static
name|String
name|ITYPE_ATTR_NAME
init|=
literal|"itypesList"
decl_stmt|;
comment|/**      * @return Returns the itypes.      */
specifier|public
specifier|static
name|TreeSet
name|findAll
parameter_list|(
name|boolean
name|basic
parameter_list|)
block|{
return|return
operator|new
name|TreeSet
argument_list|(
operator|new
name|ItypeDescDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select i from ItypeDesc i"
operator|+
operator|(
name|basic
condition|?
literal|" where i.basic=1"
else|:
literal|""
operator|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getBasicType
parameter_list|()
block|{
if|if
condition|(
name|getBasic
argument_list|()
operator|>=
literal|0
operator|&&
name|getBasic
argument_list|()
operator|<
name|sBasicTypes
operator|.
name|length
condition|)
return|return
name|sBasicTypes
index|[
name|getBasic
argument_list|()
index|]
return|;
return|return
literal|"Unknown"
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
name|ItypeDesc
operator|)
condition|)
return|return
operator|-
literal|1
return|;
return|return
name|getItype
argument_list|()
operator|.
name|compareTo
argument_list|(
operator|(
operator|(
name|ItypeDesc
operator|)
name|o
operator|)
operator|.
name|getItype
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

