begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|solver
operator|.
name|expectations
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
name|assignment
operator|.
name|Assignment
import|;
end_import

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
name|DataProperties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Enrollment
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Request
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Section
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|model
operator|.
name|Subpart
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|AvoidUnbalancedWhenNoExpectations
extends|extends
name|PercentageOverExpected
block|{
specifier|private
name|Double
name|iDisbalance
init|=
literal|0.1
decl_stmt|;
specifier|private
name|boolean
name|iBalanceUnlimited
init|=
literal|false
decl_stmt|;
specifier|public
name|AvoidUnbalancedWhenNoExpectations
parameter_list|(
name|DataProperties
name|config
parameter_list|)
block|{
name|super
argument_list|(
name|config
argument_list|)
expr_stmt|;
name|iDisbalance
operator|=
name|config
operator|.
name|getPropertyDouble
argument_list|(
literal|"OverExpected.Disbalance"
argument_list|,
name|iDisbalance
argument_list|)
expr_stmt|;
name|iBalanceUnlimited
operator|=
name|config
operator|.
name|getPropertyBoolean
argument_list|(
literal|"General.BalanceUnlimited"
argument_list|,
name|iBalanceUnlimited
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AvoidUnbalancedWhenNoExpectations
parameter_list|(
name|Double
name|percentage
parameter_list|,
name|Double
name|disbalance
parameter_list|)
block|{
name|super
argument_list|(
name|percentage
argument_list|)
expr_stmt|;
name|iDisbalance
operator|=
name|disbalance
expr_stmt|;
block|}
specifier|public
name|AvoidUnbalancedWhenNoExpectations
parameter_list|(
name|Double
name|percentage
parameter_list|)
block|{
name|this
argument_list|(
name|percentage
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|AvoidUnbalancedWhenNoExpectations
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Double
name|getDisbalance
parameter_list|()
block|{
return|return
name|iDisbalance
return|;
block|}
specifier|public
name|boolean
name|isBalanceUnlimited
parameter_list|()
block|{
return|return
name|iBalanceUnlimited
return|;
block|}
annotation|@
name|Override
specifier|public
name|double
name|getOverExpected
parameter_list|(
name|Assignment
argument_list|<
name|Request
argument_list|,
name|Enrollment
argument_list|>
name|assignment
parameter_list|,
name|Section
name|section
parameter_list|,
name|Request
name|request
parameter_list|)
block|{
name|Subpart
name|subpart
init|=
name|section
operator|.
name|getSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|hasExpectations
argument_list|(
name|subpart
argument_list|)
operator|&&
name|section
operator|.
name|getLimit
argument_list|()
operator|>
literal|0
condition|)
return|return
name|super
operator|.
name|getOverExpected
argument_list|(
name|assignment
argument_list|,
name|section
argument_list|,
name|request
argument_list|)
return|;
if|if
condition|(
name|getDisbalance
argument_list|()
operator|==
literal|null
operator|||
name|getDisbalance
argument_list|()
operator|<
literal|0.0
condition|)
return|return
literal|0.0
return|;
name|double
name|enrlConfig
init|=
name|request
operator|.
name|getWeight
argument_list|()
operator|+
name|getEnrollment
argument_list|(
name|assignment
argument_list|,
name|subpart
operator|.
name|getConfig
argument_list|()
argument_list|,
name|request
argument_list|)
decl_stmt|;
name|int
name|subparts
init|=
name|section
operator|.
name|getSubpart
argument_list|()
operator|.
name|getConfig
argument_list|()
operator|.
name|getSubparts
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
name|int
name|limit
init|=
name|getLimit
argument_list|(
name|section
argument_list|)
decl_stmt|;
name|double
name|enrl
init|=
name|request
operator|.
name|getWeight
argument_list|()
operator|+
name|getEnrollment
argument_list|(
name|assignment
argument_list|,
name|section
argument_list|,
name|request
argument_list|)
decl_stmt|;
if|if
condition|(
name|limit
operator|>
literal|0
condition|)
block|{
comment|// sections have limits -> desired size is section limit x (total enrollment / total limit)
name|double
name|desired
init|=
operator|(
name|enrlConfig
operator|/
name|getLimit
argument_list|(
name|subpart
argument_list|)
operator|)
operator|*
name|limit
decl_stmt|;
if|if
condition|(
name|enrl
operator|-
name|desired
operator|>=
name|Math
operator|.
name|max
argument_list|(
literal|1.0
argument_list|,
name|getDisbalance
argument_list|()
operator|*
name|limit
argument_list|)
condition|)
return|return
literal|1.0
operator|/
name|subparts
return|;
block|}
if|else if
condition|(
name|isBalanceUnlimited
argument_list|()
condition|)
block|{
comment|// unlimited sections -> desired size is total enrollment / number of sections
name|double
name|desired
init|=
name|enrlConfig
operator|/
name|subpart
operator|.
name|getSections
argument_list|()
operator|.
name|size
argument_list|()
decl_stmt|;
if|if
condition|(
name|enrl
operator|-
name|desired
operator|>=
name|Math
operator|.
name|max
argument_list|(
literal|1.0
argument_list|,
name|getDisbalance
argument_list|()
operator|*
name|desired
argument_list|)
condition|)
return|return
literal|1.0
operator|/
name|subparts
return|;
block|}
return|return
literal|0.0
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"bal("
operator|+
name|getPercentage
argument_list|()
operator|+
literal|","
operator|+
name|getDisbalance
argument_list|()
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

