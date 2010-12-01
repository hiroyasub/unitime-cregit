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
name|SchedulingSubpart
import|;
end_import

begin_comment
comment|/**  * Compares scheduling subparts by itype  *   * @author Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|SchedulingSubpartComparator
implements|implements
name|Comparator
block|{
specifier|private
name|Long
name|subjectUID
decl_stmt|;
specifier|public
name|SchedulingSubpartComparator
parameter_list|(
name|Long
name|subjectUID
parameter_list|)
block|{
name|this
operator|.
name|subjectUID
operator|=
name|subjectUID
expr_stmt|;
block|}
specifier|public
name|SchedulingSubpartComparator
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isParent
parameter_list|(
name|SchedulingSubpart
name|s1
parameter_list|,
name|SchedulingSubpart
name|s2
parameter_list|)
block|{
name|SchedulingSubpart
name|p1
init|=
name|s1
operator|.
name|getParentSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|p1
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|p1
operator|.
name|equals
argument_list|(
name|s2
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
name|isParent
argument_list|(
name|p1
argument_list|,
name|s2
argument_list|)
return|;
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
name|SchedulingSubpart
name|s1
init|=
operator|(
name|SchedulingSubpart
operator|)
name|o1
decl_stmt|;
name|SchedulingSubpart
name|s2
init|=
operator|(
name|SchedulingSubpart
operator|)
name|o2
decl_stmt|;
if|if
condition|(
operator|!
name|s1
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|equals
argument_list|(
name|s2
operator|.
name|getInstrOfferingConfig
argument_list|()
argument_list|)
condition|)
block|{
name|Comparator
name|cmp
init|=
operator|new
name|InstrOfferingConfigComparator
argument_list|(
name|subjectUID
argument_list|)
decl_stmt|;
return|return
name|cmp
operator|.
name|compare
argument_list|(
name|s1
operator|.
name|getInstrOfferingConfig
argument_list|()
argument_list|,
name|s2
operator|.
name|getInstrOfferingConfig
argument_list|()
argument_list|)
return|;
block|}
if|if
condition|(
name|isParent
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
condition|)
return|return
literal|1
return|;
if|if
condition|(
name|isParent
argument_list|(
name|s2
argument_list|,
name|s1
argument_list|)
condition|)
return|return
operator|-
literal|1
return|;
name|int
name|cmp
init|=
name|s1
operator|.
name|getItype
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getItype
argument_list|()
operator|.
name|getItype
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
name|s1
operator|.
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Long
name|getSubjectUID
parameter_list|()
block|{
return|return
name|subjectUID
return|;
block|}
specifier|public
name|void
name|setSubjectUID
parameter_list|(
name|Long
name|subjectUID
parameter_list|)
block|{
name|this
operator|.
name|subjectUID
operator|=
name|subjectUID
expr_stmt|;
block|}
block|}
end_class

end_unit

