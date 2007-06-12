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
name|Class_
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
name|InstrOfferingConfig
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
name|InstructionalOffering
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
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|NavigationComparator
implements|implements
name|Comparator
block|{
name|ClassComparator
name|iClassComparator
init|=
operator|new
name|ClassComparator
argument_list|(
name|ClassComparator
operator|.
name|COMPARE_BY_HIERARCHY
argument_list|)
decl_stmt|;
name|SchedulingSubpartComparator
name|iSchedulingSubpartComparator
init|=
operator|new
name|SchedulingSubpartComparator
argument_list|()
decl_stmt|;
name|InstructionalOfferingComparator
name|iInstructionalOfferingComparator
init|=
operator|new
name|InstructionalOfferingComparator
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|InstrOfferingConfigComparator
name|iInstrOfferingConfigComparator
init|=
operator|new
name|InstrOfferingConfigComparator
argument_list|(
literal|null
argument_list|)
decl_stmt|;
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
name|o1
operator|instanceof
name|Class_
condition|)
return|return
name|iClassComparator
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
return|;
if|if
condition|(
name|o1
operator|instanceof
name|SchedulingSubpart
condition|)
return|return
name|iSchedulingSubpartComparator
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
return|;
if|if
condition|(
name|o1
operator|instanceof
name|InstrOfferingConfig
condition|)
block|{
if|if
condition|(
name|iInstrOfferingConfigComparator
operator|.
name|getSubjectUID
argument_list|()
operator|==
literal|null
condition|)
block|{
name|InstrOfferingConfig
name|ioc
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|o1
decl_stmt|;
name|iInstrOfferingConfigComparator
operator|.
name|setSubjectUID
argument_list|(
name|ioc
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|iInstrOfferingConfigComparator
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
return|;
block|}
if|if
condition|(
name|o1
operator|instanceof
name|InstructionalOffering
condition|)
block|{
if|if
condition|(
name|iInstructionalOfferingComparator
operator|.
name|getSubjectUID
argument_list|()
operator|==
literal|null
condition|)
block|{
name|InstructionalOffering
name|io
init|=
operator|(
name|InstructionalOffering
operator|)
name|o1
decl_stmt|;
name|iInstructionalOfferingComparator
operator|.
name|setSubjectUID
argument_list|(
name|io
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|iInstructionalOfferingComparator
operator|.
name|compare
argument_list|(
name|o1
argument_list|,
name|o2
argument_list|)
return|;
block|}
return|return
name|o1
operator|.
name|toString
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o2
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

