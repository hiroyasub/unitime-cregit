begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|DistanceMetric
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
name|BaseTravelTime
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
name|TravelTimeDAO
import|;
end_import

begin_class
specifier|public
class|class
name|TravelTime
extends|extends
name|BaseTravelTime
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5810111960278939304L
decl_stmt|;
specifier|public
name|TravelTime
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|populateTravelTimes
parameter_list|(
name|DistanceMetric
name|metric
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
for|for
control|(
name|TravelTime
name|time
range|:
operator|(
name|List
argument_list|<
name|TravelTime
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from TravelTime where session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
name|metric
operator|.
name|addTravelTime
argument_list|(
name|time
operator|.
name|getLocation1Id
argument_list|()
argument_list|,
name|time
operator|.
name|getLocation2Id
argument_list|()
argument_list|,
name|time
operator|.
name|getDistance
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|populateTravelTimes
parameter_list|(
name|DistanceMetric
name|metric
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|TravelTimeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
name|populateTravelTimes
argument_list|(
name|metric
argument_list|,
name|sessionId
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|populateTravelTimes
parameter_list|(
name|DistanceMetric
name|metric
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
for|for
control|(
name|TravelTime
name|time
range|:
operator|(
name|List
argument_list|<
name|TravelTime
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"from TravelTime"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
control|)
name|metric
operator|.
name|addTravelTime
argument_list|(
name|time
operator|.
name|getLocation1Id
argument_list|()
argument_list|,
name|time
operator|.
name|getLocation2Id
argument_list|()
argument_list|,
name|time
operator|.
name|getDistance
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|populateTravelTimes
parameter_list|(
name|DistanceMetric
name|metric
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|TravelTimeDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|createNewSession
argument_list|()
decl_stmt|;
name|populateTravelTimes
argument_list|(
name|metric
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

