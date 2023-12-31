begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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
name|exam
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
name|org
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|ExamRoom
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
name|defaults
operator|.
name|ApplicationProperty
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
name|Exam
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
name|Location
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
name|LocationDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamRoomInfo
implements|implements
name|Serializable
implements|,
name|Comparable
argument_list|<
name|ExamRoomInfo
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|5882156641099610154L
decl_stmt|;
specifier|private
name|Long
name|iId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iName
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iPreference
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iCapacity
decl_stmt|,
name|iExamCapacity
init|=
literal|0
decl_stmt|;
specifier|private
name|Double
name|iX
init|=
literal|null
decl_stmt|,
name|iY
init|=
literal|null
decl_stmt|;
specifier|private
specifier|transient
name|Location
name|iLocation
init|=
literal|null
decl_stmt|;
specifier|private
specifier|transient
specifier|static
name|DistanceMetric
name|sDistanceMetric
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iHard
init|=
literal|true
decl_stmt|;
specifier|public
name|ExamRoomInfo
parameter_list|(
name|ExamRoom
name|room
parameter_list|,
name|int
name|preference
parameter_list|)
block|{
name|iId
operator|=
name|room
operator|.
name|getId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|room
operator|.
name|getName
argument_list|()
expr_stmt|;
name|iCapacity
operator|=
name|room
operator|.
name|getSize
argument_list|()
expr_stmt|;
name|iExamCapacity
operator|=
name|room
operator|.
name|getAltSize
argument_list|()
expr_stmt|;
name|iPreference
operator|=
name|preference
expr_stmt|;
name|iX
operator|=
name|room
operator|.
name|getCoordX
argument_list|()
expr_stmt|;
name|iY
operator|=
name|room
operator|.
name|getCoordY
argument_list|()
expr_stmt|;
name|iHard
operator|=
name|room
operator|.
name|isHard
argument_list|()
expr_stmt|;
block|}
specifier|public
name|ExamRoomInfo
parameter_list|(
name|Location
name|location
parameter_list|,
name|int
name|preference
parameter_list|)
block|{
name|iLocation
operator|=
name|location
expr_stmt|;
name|iId
operator|=
name|location
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iName
operator|=
name|location
operator|.
name|getLabel
argument_list|()
expr_stmt|;
name|iCapacity
operator|=
name|location
operator|.
name|getCapacity
argument_list|()
expr_stmt|;
name|iExamCapacity
operator|=
operator|(
name|location
operator|.
name|getExamCapacity
argument_list|()
operator|==
literal|null
condition|?
name|location
operator|.
name|getCapacity
argument_list|()
operator|/
literal|2
else|:
name|location
operator|.
name|getExamCapacity
argument_list|()
operator|)
expr_stmt|;
name|iPreference
operator|=
name|preference
expr_stmt|;
name|iX
operator|=
name|location
operator|.
name|getCoordinateX
argument_list|()
expr_stmt|;
name|iY
operator|=
name|location
operator|.
name|getCoordinateY
argument_list|()
expr_stmt|;
name|iHard
operator|=
operator|!
name|location
operator|.
name|isIgnoreRoomCheck
argument_list|()
expr_stmt|;
block|}
specifier|public
name|Long
name|getLocationId
parameter_list|()
block|{
return|return
name|iId
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
name|int
name|getPreference
parameter_list|()
block|{
return|return
name|iPreference
return|;
block|}
specifier|public
name|void
name|setPreference
parameter_list|(
name|int
name|preference
parameter_list|)
block|{
name|iPreference
operator|=
name|preference
expr_stmt|;
block|}
specifier|public
name|int
name|getCapacity
parameter_list|()
block|{
return|return
name|iCapacity
return|;
block|}
specifier|public
name|int
name|getExamCapacity
parameter_list|()
block|{
return|return
name|iExamCapacity
return|;
block|}
specifier|public
name|int
name|getCapacity
parameter_list|(
name|ExamInfo
name|exam
parameter_list|)
block|{
return|return
operator|(
name|exam
operator|.
name|getSeatingType
argument_list|()
operator|==
name|Exam
operator|.
name|sSeatingTypeExam
condition|?
name|getExamCapacity
argument_list|()
else|:
name|getCapacity
argument_list|()
operator|)
return|;
block|}
specifier|public
name|boolean
name|isHard
parameter_list|()
block|{
return|return
name|iHard
return|;
block|}
specifier|public
name|Location
name|getLocation
parameter_list|()
block|{
if|if
condition|(
name|iLocation
operator|==
literal|null
condition|)
name|iLocation
operator|=
operator|new
name|LocationDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getLocationId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iLocation
return|;
block|}
specifier|public
name|Location
name|getLocation
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
return|return
operator|new
name|LocationDAO
argument_list|()
operator|.
name|get
argument_list|(
name|getLocationId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"<span style='color:"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|PreferenceLevel
operator|.
name|int2prolog
argument_list|(
name|getPreference
argument_list|()
argument_list|)
argument_list|)
operator|+
literal|";' "
operator|+
literal|"onmouseover=\"showGwtRoomHint(this, '"
operator|+
name|getLocationId
argument_list|()
operator|+
literal|"', '"
operator|+
name|PreferenceLevel
operator|.
name|int2string
argument_list|(
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|"');\" onmouseout=\"hideGwtRoomHint();\">"
operator|+
name|getName
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|String
name|getNameWithHint
parameter_list|(
name|boolean
name|pref
parameter_list|)
block|{
return|return
literal|"<span"
operator|+
operator|(
name|pref
condition|?
literal|" style='color:"
operator|+
name|PreferenceLevel
operator|.
name|prolog2color
argument_list|(
name|PreferenceLevel
operator|.
name|int2prolog
argument_list|(
name|getPreference
argument_list|()
argument_list|)
argument_list|)
operator|+
literal|";'"
else|:
literal|""
operator|)
operator|+
literal|" onmouseover=\"showGwtRoomHint(this, '"
operator|+
name|getLocationId
argument_list|()
operator|+
literal|"', '"
operator|+
name|PreferenceLevel
operator|.
name|int2string
argument_list|(
name|getPreference
argument_list|()
argument_list|)
operator|+
literal|"');\" onmouseout=\"hideGwtRoomHint();\">"
operator|+
name|getName
argument_list|()
operator|+
literal|"</span>"
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|ExamRoomInfo
name|room
parameter_list|)
block|{
name|int
name|cmp
init|=
operator|-
name|Double
operator|.
name|compare
argument_list|(
name|getCapacity
argument_list|()
argument_list|,
name|room
operator|.
name|getCapacity
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
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|room
operator|.
name|getName
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
name|getLocationId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|room
operator|.
name|getLocationId
argument_list|()
argument_list|)
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
name|ExamRoomInfo
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|getLocationId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|ExamRoomInfo
operator|)
name|o
operator|)
operator|.
name|getLocationId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|getLocationId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|Double
name|getCoordX
parameter_list|()
block|{
return|return
name|iX
return|;
block|}
specifier|public
name|Double
name|getCoordY
parameter_list|()
block|{
return|return
name|iY
return|;
block|}
specifier|public
name|double
name|getDistance
parameter_list|(
name|ExamRoomInfo
name|other
parameter_list|)
block|{
if|if
condition|(
name|sDistanceMetric
operator|==
literal|null
condition|)
block|{
name|sDistanceMetric
operator|=
operator|new
name|DistanceMetric
argument_list|(
name|DistanceMetric
operator|.
name|Ellipsoid
operator|.
name|valueOf
argument_list|(
name|ApplicationProperty
operator|.
name|DistanceEllipsoid
operator|.
name|value
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|sDistanceMetric
operator|.
name|getDistanceInMeters
argument_list|(
name|getLocationId
argument_list|()
argument_list|,
name|getCoordX
argument_list|()
argument_list|,
name|getCoordY
argument_list|()
argument_list|,
name|other
operator|.
name|getLocationId
argument_list|()
argument_list|,
name|other
operator|.
name|getCoordX
argument_list|()
argument_list|,
name|other
operator|.
name|getCoordY
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

