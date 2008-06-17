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
name|BaseExamConflict
import|;
end_import

begin_class
specifier|public
class|class
name|ExamConflict
extends|extends
name|BaseExamConflict
implements|implements
name|Comparable
argument_list|<
name|ExamConflict
argument_list|>
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
name|ExamConflict
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|ExamConflict
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
name|ExamConflict
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
name|conflictType
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|conflictType
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
specifier|final
name|int
name|sConflictTypeDirect
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sConflictTypeMoreThanTwoADay
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sConflictTypeBackToBackDist
init|=
literal|2
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sConflictTypeBackToBack
init|=
literal|3
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|sConflictTypes
init|=
operator|new
name|String
index|[]
block|{
literal|"Distance"
block|,
literal|">2 A Day"
block|,
literal|"Distance Back-To-Back"
block|,
literal|"Back-To-Back"
block|}
decl_stmt|;
specifier|public
name|boolean
name|isDirectConflict
parameter_list|()
block|{
return|return
name|sConflictTypeDirect
operator|==
name|getConflictType
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isMoreThanTwoADayConflict
parameter_list|()
block|{
return|return
name|sConflictTypeMoreThanTwoADay
operator|==
name|getConflictType
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isBackToBackConflict
parameter_list|()
block|{
return|return
name|sConflictTypeBackToBack
operator|==
name|getConflictType
argument_list|()
operator|||
name|sConflictTypeBackToBackDist
operator|==
name|getConflictType
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isDistanceBackToBackConflict
parameter_list|()
block|{
return|return
name|sConflictTypeBackToBackDist
operator|==
name|getConflictType
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExamConflict
name|conflict
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getConflictType
argument_list|()
operator|.
name|compareTo
argument_list|(
name|conflict
operator|.
name|getConflictType
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
name|cmp
operator|=
name|getNrStudents
argument_list|()
operator|.
name|compareTo
argument_list|(
name|conflict
operator|.
name|getNrStudents
argument_list|()
argument_list|)
expr_stmt|;
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
name|conflict
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

