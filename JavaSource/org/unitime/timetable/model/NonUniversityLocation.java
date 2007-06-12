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
name|List
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
name|BaseNonUniversityLocation
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
name|NonUniversityLocationDAO
import|;
end_import

begin_class
specifier|public
class|class
name|NonUniversityLocation
extends|extends
name|BaseNonUniversityLocation
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
name|NonUniversityLocation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|NonUniversityLocation
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
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|NonUniversityLocation
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|capacity
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateX
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|coordinateY
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreTooFar
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Boolean
name|ignoreRoomCheck
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|capacity
argument_list|,
name|coordinateX
argument_list|,
name|coordinateY
argument_list|,
name|ignoreTooFar
argument_list|,
name|ignoreRoomCheck
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getName
argument_list|()
operator|)
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
name|Object
name|clone
parameter_list|()
block|{
name|NonUniversityLocation
name|l
init|=
operator|new
name|NonUniversityLocation
argument_list|()
decl_stmt|;
name|l
operator|.
name|setCapacity
argument_list|(
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setCoordinateX
argument_list|(
name|getCoordinateX
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setCoordinateY
argument_list|(
name|getCoordinateY
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setDisplayName
argument_list|(
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setName
argument_list|(
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|isIgnoreRoomCheck
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setIgnoreTooFar
argument_list|(
name|isIgnoreTooFar
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setPattern
argument_list|(
name|getPattern
argument_list|()
argument_list|)
expr_stmt|;
name|l
operator|.
name|setSession
argument_list|(
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|l
return|;
block|}
specifier|public
name|NonUniversityLocation
name|findSameNonUniversityLocationInSession
parameter_list|(
name|Session
name|newSession
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|newSession
operator|==
literal|null
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
name|NonUniversityLocation
name|newNonUniversityLocation
init|=
literal|null
decl_stmt|;
name|NonUniversityLocationDAO
name|nulDao
init|=
operator|new
name|NonUniversityLocationDAO
argument_list|()
decl_stmt|;
name|String
name|query
init|=
literal|"from NonUniversityLocation nul inner join RoomDept rd where nul.name = '"
operator|+
name|getName
argument_list|()
operator|+
literal|"'"
decl_stmt|;
name|query
operator|+=
literal|" and nul.session.uniqueId = "
operator|+
name|newSession
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|query
operator|+=
literal|" and rd.control = "
operator|+
literal|1
expr_stmt|;
name|query
operator|+=
literal|" and rd.department.uniqueId ="
operator|+
name|getControllingDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|List
name|l
init|=
name|nulDao
operator|.
name|getQuery
argument_list|(
name|query
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
name|l
operator|!=
literal|null
operator|&&
name|l
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|newNonUniversityLocation
operator|=
operator|(
name|NonUniversityLocation
operator|)
name|l
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|newNonUniversityLocation
operator|)
return|;
block|}
block|}
end_class

end_unit

