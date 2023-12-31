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
name|model
operator|.
name|base
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
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|ClassWaitList
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
name|CourseDemand
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
name|CourseOffering
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
name|CourseRequest
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
name|CourseRequestOption
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
name|StudentSectioningPref
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseCourseRequest
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|Long
name|iUniqueId
decl_stmt|;
specifier|private
name|Integer
name|iOrder
decl_stmt|;
specifier|private
name|Boolean
name|iAllowOverlap
decl_stmt|;
specifier|private
name|Integer
name|iCredit
decl_stmt|;
specifier|private
name|Integer
name|iOverrideStatus
decl_stmt|;
specifier|private
name|String
name|iOverrideExternalId
decl_stmt|;
specifier|private
name|Date
name|iOverrideTimeStamp
decl_stmt|;
specifier|private
name|Integer
name|iOverrideIntent
decl_stmt|;
specifier|private
name|CourseDemand
name|iCourseDemand
decl_stmt|;
specifier|private
name|CourseOffering
name|iCourseOffering
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|CourseRequestOption
argument_list|>
name|iCourseRequestOptions
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ClassWaitList
argument_list|>
name|iClassWaitLists
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentSectioningPref
argument_list|>
name|iPreferences
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UNIQUEID
init|=
literal|"uniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ORD
init|=
literal|"order"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ALLOW_OVERLAP
init|=
literal|"allowOverlap"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CREDIT
init|=
literal|"credit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_REQ_STATUS
init|=
literal|"overrideStatus"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_REQ_EXTID
init|=
literal|"overrideExternalId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_REQ_TS
init|=
literal|"overrideTimeStamp"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_REQ_INTENT
init|=
literal|"overrideIntent"
decl_stmt|;
specifier|public
name|BaseCourseRequest
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseCourseRequest
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|setUniqueId
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
block|}
specifier|public
name|Long
name|getUniqueId
parameter_list|()
block|{
return|return
name|iUniqueId
return|;
block|}
specifier|public
name|void
name|setUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
name|iUniqueId
operator|=
name|uniqueId
expr_stmt|;
block|}
specifier|public
name|Integer
name|getOrder
parameter_list|()
block|{
return|return
name|iOrder
return|;
block|}
specifier|public
name|void
name|setOrder
parameter_list|(
name|Integer
name|order
parameter_list|)
block|{
name|iOrder
operator|=
name|order
expr_stmt|;
block|}
specifier|public
name|Boolean
name|isAllowOverlap
parameter_list|()
block|{
return|return
name|iAllowOverlap
return|;
block|}
specifier|public
name|Boolean
name|getAllowOverlap
parameter_list|()
block|{
return|return
name|iAllowOverlap
return|;
block|}
specifier|public
name|void
name|setAllowOverlap
parameter_list|(
name|Boolean
name|allowOverlap
parameter_list|)
block|{
name|iAllowOverlap
operator|=
name|allowOverlap
expr_stmt|;
block|}
specifier|public
name|Integer
name|getCredit
parameter_list|()
block|{
return|return
name|iCredit
return|;
block|}
specifier|public
name|void
name|setCredit
parameter_list|(
name|Integer
name|credit
parameter_list|)
block|{
name|iCredit
operator|=
name|credit
expr_stmt|;
block|}
specifier|public
name|Integer
name|getOverrideStatus
parameter_list|()
block|{
return|return
name|iOverrideStatus
return|;
block|}
specifier|public
name|void
name|setOverrideStatus
parameter_list|(
name|Integer
name|overrideStatus
parameter_list|)
block|{
name|iOverrideStatus
operator|=
name|overrideStatus
expr_stmt|;
block|}
specifier|public
name|String
name|getOverrideExternalId
parameter_list|()
block|{
return|return
name|iOverrideExternalId
return|;
block|}
specifier|public
name|void
name|setOverrideExternalId
parameter_list|(
name|String
name|overrideExternalId
parameter_list|)
block|{
name|iOverrideExternalId
operator|=
name|overrideExternalId
expr_stmt|;
block|}
specifier|public
name|Date
name|getOverrideTimeStamp
parameter_list|()
block|{
return|return
name|iOverrideTimeStamp
return|;
block|}
specifier|public
name|void
name|setOverrideTimeStamp
parameter_list|(
name|Date
name|overrideTimeStamp
parameter_list|)
block|{
name|iOverrideTimeStamp
operator|=
name|overrideTimeStamp
expr_stmt|;
block|}
specifier|public
name|Integer
name|getOverrideIntent
parameter_list|()
block|{
return|return
name|iOverrideIntent
return|;
block|}
specifier|public
name|void
name|setOverrideIntent
parameter_list|(
name|Integer
name|overrideIntent
parameter_list|)
block|{
name|iOverrideIntent
operator|=
name|overrideIntent
expr_stmt|;
block|}
specifier|public
name|CourseDemand
name|getCourseDemand
parameter_list|()
block|{
return|return
name|iCourseDemand
return|;
block|}
specifier|public
name|void
name|setCourseDemand
parameter_list|(
name|CourseDemand
name|courseDemand
parameter_list|)
block|{
name|iCourseDemand
operator|=
name|courseDemand
expr_stmt|;
block|}
specifier|public
name|CourseOffering
name|getCourseOffering
parameter_list|()
block|{
return|return
name|iCourseOffering
return|;
block|}
specifier|public
name|void
name|setCourseOffering
parameter_list|(
name|CourseOffering
name|courseOffering
parameter_list|)
block|{
name|iCourseOffering
operator|=
name|courseOffering
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|CourseRequestOption
argument_list|>
name|getCourseRequestOptions
parameter_list|()
block|{
return|return
name|iCourseRequestOptions
return|;
block|}
specifier|public
name|void
name|setCourseRequestOptions
parameter_list|(
name|Set
argument_list|<
name|CourseRequestOption
argument_list|>
name|courseRequestOptions
parameter_list|)
block|{
name|iCourseRequestOptions
operator|=
name|courseRequestOptions
expr_stmt|;
block|}
specifier|public
name|void
name|addTocourseRequestOptions
parameter_list|(
name|CourseRequestOption
name|courseRequestOption
parameter_list|)
block|{
if|if
condition|(
name|iCourseRequestOptions
operator|==
literal|null
condition|)
name|iCourseRequestOptions
operator|=
operator|new
name|HashSet
argument_list|<
name|CourseRequestOption
argument_list|>
argument_list|()
expr_stmt|;
name|iCourseRequestOptions
operator|.
name|add
argument_list|(
name|courseRequestOption
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ClassWaitList
argument_list|>
name|getClassWaitLists
parameter_list|()
block|{
return|return
name|iClassWaitLists
return|;
block|}
specifier|public
name|void
name|setClassWaitLists
parameter_list|(
name|Set
argument_list|<
name|ClassWaitList
argument_list|>
name|classWaitLists
parameter_list|)
block|{
name|iClassWaitLists
operator|=
name|classWaitLists
expr_stmt|;
block|}
specifier|public
name|void
name|addToclassWaitLists
parameter_list|(
name|ClassWaitList
name|classWaitList
parameter_list|)
block|{
if|if
condition|(
name|iClassWaitLists
operator|==
literal|null
condition|)
name|iClassWaitLists
operator|=
operator|new
name|HashSet
argument_list|<
name|ClassWaitList
argument_list|>
argument_list|()
expr_stmt|;
name|iClassWaitLists
operator|.
name|add
argument_list|(
name|classWaitList
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentSectioningPref
argument_list|>
name|getPreferences
parameter_list|()
block|{
return|return
name|iPreferences
return|;
block|}
specifier|public
name|void
name|setPreferences
parameter_list|(
name|Set
argument_list|<
name|StudentSectioningPref
argument_list|>
name|preferences
parameter_list|)
block|{
name|iPreferences
operator|=
name|preferences
expr_stmt|;
block|}
specifier|public
name|void
name|addTopreferences
parameter_list|(
name|StudentSectioningPref
name|studentSectioningPref
parameter_list|)
block|{
if|if
condition|(
name|iPreferences
operator|==
literal|null
condition|)
name|iPreferences
operator|=
operator|new
name|HashSet
argument_list|<
name|StudentSectioningPref
argument_list|>
argument_list|()
expr_stmt|;
name|iPreferences
operator|.
name|add
argument_list|(
name|studentSectioningPref
argument_list|)
expr_stmt|;
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
name|CourseRequest
operator|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
operator|||
operator|(
operator|(
name|CourseRequest
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|CourseRequest
operator|)
name|o
operator|)
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"CourseRequest["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
specifier|public
name|String
name|toDebugString
parameter_list|()
block|{
return|return
literal|"CourseRequest["
operator|+
literal|"\n	AllowOverlap: "
operator|+
name|getAllowOverlap
argument_list|()
operator|+
literal|"\n	CourseDemand: "
operator|+
name|getCourseDemand
argument_list|()
operator|+
literal|"\n	CourseOffering: "
operator|+
name|getCourseOffering
argument_list|()
operator|+
literal|"\n	Credit: "
operator|+
name|getCredit
argument_list|()
operator|+
literal|"\n	Order: "
operator|+
name|getOrder
argument_list|()
operator|+
literal|"\n	OverrideExternalId: "
operator|+
name|getOverrideExternalId
argument_list|()
operator|+
literal|"\n	OverrideIntent: "
operator|+
name|getOverrideIntent
argument_list|()
operator|+
literal|"\n	OverrideStatus: "
operator|+
name|getOverrideStatus
argument_list|()
operator|+
literal|"\n	OverrideTimeStamp: "
operator|+
name|getOverrideTimeStamp
argument_list|()
operator|+
literal|"\n	UniqueId: "
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

