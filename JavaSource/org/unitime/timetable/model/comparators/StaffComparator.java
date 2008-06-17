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
operator|.
name|comparators
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|Staff
import|;
end_import

begin_comment
comment|/**  * Compares Staff based on specified criteria  * Defaults to compare by name  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|StaffComparator
implements|implements
name|Comparator
block|{
specifier|public
specifier|static
specifier|final
name|short
name|COMPARE_BY_NAME
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|short
name|COMPARE_BY_POSITION
init|=
literal|2
decl_stmt|;
specifier|private
name|short
name|compareBy
decl_stmt|;
specifier|public
name|StaffComparator
parameter_list|()
block|{
name|compareBy
operator|=
name|COMPARE_BY_NAME
expr_stmt|;
block|}
specifier|public
name|StaffComparator
parameter_list|(
name|short
name|compareBy
parameter_list|)
block|{
if|if
condition|(
name|compareBy
operator|!=
name|COMPARE_BY_NAME
operator|&&
name|compareBy
operator|!=
name|COMPARE_BY_POSITION
condition|)
block|{
name|this
operator|.
name|compareBy
operator|=
name|COMPARE_BY_NAME
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|compareBy
operator|=
name|compareBy
expr_stmt|;
block|}
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o1
operator|instanceof
name|Staff
operator|)
operator|||
name|o1
operator|==
literal|null
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"o1 Class must be of type Staff and cannot be null"
argument_list|)
throw|;
if|if
condition|(
operator|!
operator|(
name|o2
operator|instanceof
name|Staff
operator|)
operator|||
name|o2
operator|==
literal|null
condition|)
throw|throw
operator|new
name|ClassCastException
argument_list|(
literal|"o2 Class must be of type Staff and cannot be null"
argument_list|)
throw|;
name|Staff
name|s1
init|=
operator|(
name|Staff
operator|)
name|o1
decl_stmt|;
name|Staff
name|s2
init|=
operator|(
name|Staff
operator|)
name|o2
decl_stmt|;
if|if
condition|(
name|compareBy
operator|==
name|COMPARE_BY_POSITION
condition|)
block|{
name|Integer
name|l1
init|=
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|s1
operator|.
name|getPositionCode
argument_list|()
operator|!=
literal|null
condition|)
name|l1
operator|=
name|s1
operator|.
name|getPositionCode
argument_list|()
operator|.
name|getPositionType
argument_list|()
operator|.
name|getSortOrder
argument_list|()
expr_stmt|;
name|Integer
name|l2
init|=
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|s2
operator|.
name|getPositionCode
argument_list|()
operator|!=
literal|null
condition|)
name|l2
operator|=
name|s2
operator|.
name|getPositionCode
argument_list|()
operator|.
name|getPositionType
argument_list|()
operator|.
name|getSortOrder
argument_list|()
expr_stmt|;
name|int
name|ret
init|=
name|l1
operator|.
name|compareTo
argument_list|(
name|l2
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|!=
literal|0
condition|)
return|return
name|ret
return|;
block|}
return|return
name|s1
operator|.
name|nameLastNameFirst
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|s2
operator|.
name|nameLastNameFirst
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

