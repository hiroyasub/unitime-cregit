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
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|log4j
operator|.
name|Logger
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
name|onlinesectioning
operator|.
name|solver
operator|.
name|expectations
operator|.
name|OverExpectedCriterion
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
name|onlinesectioning
operator|.
name|solver
operator|.
name|expectations
operator|.
name|PercentageOverExpected
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|studentsct
operator|.
name|StudentSectioningModel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|OnlineSectioningModel
extends|extends
name|StudentSectioningModel
block|{
specifier|private
specifier|static
name|Logger
name|sLog
init|=
name|Logger
operator|.
name|getLogger
argument_list|(
name|OnlineSectioningModel
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|OverExpectedCriterion
name|iOverExpectedCriterion
decl_stmt|;
specifier|public
name|OnlineSectioningModel
parameter_list|(
name|DataProperties
name|properties
parameter_list|)
block|{
name|super
argument_list|(
name|properties
argument_list|)
expr_stmt|;
try|try
block|{
name|Class
argument_list|<
name|OverExpectedCriterion
argument_list|>
name|overExpectedCriterionClass
init|=
operator|(
name|Class
argument_list|<
name|OverExpectedCriterion
argument_list|>
operator|)
name|Class
operator|.
name|forName
argument_list|(
name|properties
operator|.
name|getProperty
argument_list|(
literal|"OverExpectedCriterion.Class"
argument_list|,
name|PercentageOverExpected
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|iOverExpectedCriterion
operator|=
name|overExpectedCriterionClass
operator|.
name|getConstructor
argument_list|(
name|DataProperties
operator|.
name|class
argument_list|)
operator|.
name|newInstance
argument_list|(
name|properties
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to create custom over-expected criterion ("
operator|+
name|e
operator|.
name|getMessage
argument_list|()
operator|+
literal|"), using default."
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|iOverExpectedCriterion
operator|=
operator|new
name|PercentageOverExpected
argument_list|(
name|properties
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|OverExpectedCriterion
name|getOverExpectedCriterion
parameter_list|()
block|{
return|return
name|iOverExpectedCriterion
return|;
block|}
specifier|public
name|void
name|setOverExpectedCriterion
parameter_list|(
name|OverExpectedCriterion
name|overExpectedCriterion
parameter_list|)
block|{
name|iOverExpectedCriterion
operator|=
name|overExpectedCriterion
expr_stmt|;
block|}
specifier|public
name|double
name|getOverExpected
parameter_list|(
name|Section
name|section
parameter_list|,
name|Request
name|request
parameter_list|)
block|{
return|return
name|getOverExpectedCriterion
argument_list|()
operator|.
name|getOverExpected
argument_list|(
name|section
argument_list|,
name|request
argument_list|)
return|;
block|}
block|}
end_class

end_unit

