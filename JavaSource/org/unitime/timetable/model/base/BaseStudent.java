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
name|Advisor
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
name|AdvisorCourseRequest
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
name|LastLikeCourseDemand
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
name|Session
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
name|Student
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
name|StudentAccomodation
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
name|StudentAreaClassificationMajor
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
name|StudentAreaClassificationMinor
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
name|StudentClassEnrollment
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
name|StudentGroup
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
name|StudentNote
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
name|StudentSectioningStatus
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
name|WaitList
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseStudent
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
name|String
name|iExternalUniqueId
decl_stmt|;
specifier|private
name|String
name|iFirstName
decl_stmt|;
specifier|private
name|String
name|iMiddleName
decl_stmt|;
specifier|private
name|String
name|iLastName
decl_stmt|;
specifier|private
name|String
name|iEmail
decl_stmt|;
specifier|private
name|Integer
name|iFreeTimeCategory
decl_stmt|;
specifier|private
name|Integer
name|iSchedulePreference
decl_stmt|;
specifier|private
name|Float
name|iMaxCredit
decl_stmt|;
specifier|private
name|Float
name|iMinCredit
decl_stmt|;
specifier|private
name|Float
name|iOverrideMaxCredit
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
name|Date
name|iScheduleEmailedDate
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|StudentSectioningStatus
name|iSectioningStatus
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentAreaClassificationMajor
argument_list|>
name|iAreaClasfMajors
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentAreaClassificationMinor
argument_list|>
name|iAreaClasfMinors
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentAccomodation
argument_list|>
name|iAccomodations
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentGroup
argument_list|>
name|iGroups
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|WaitList
argument_list|>
name|iWaitlists
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|CourseDemand
argument_list|>
name|iCourseDemands
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|iClassEnrollments
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|LastLikeCourseDemand
argument_list|>
name|iLastLikeCourseDemands
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|StudentNote
argument_list|>
name|iNotes
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Advisor
argument_list|>
name|iAdvisors
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|AdvisorCourseRequest
argument_list|>
name|iAdvisorCourseRequests
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
name|PROP_EXTERNAL_UID
init|=
literal|"externalUniqueId"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FIRST_NAME
init|=
literal|"firstName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MIDDLE_NAME
init|=
literal|"middleName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LAST_NAME
init|=
literal|"lastName"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EMAIL
init|=
literal|"email"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_FREE_TIME_CAT
init|=
literal|"freeTimeCategory"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SCHEDULE_PREFERENCE
init|=
literal|"schedulePreference"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MAX_CREDIT
init|=
literal|"maxCredit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MIN_CREDIT
init|=
literal|"minCredit"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_REQ_CREDIT
init|=
literal|"overrideMaxCredit"
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
name|PROP_SCHEDULE_EMAILED
init|=
literal|"scheduleEmailedDate"
decl_stmt|;
specifier|public
name|BaseStudent
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseStudent
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
name|String
name|getExternalUniqueId
parameter_list|()
block|{
return|return
name|iExternalUniqueId
return|;
block|}
specifier|public
name|void
name|setExternalUniqueId
parameter_list|(
name|String
name|externalUniqueId
parameter_list|)
block|{
name|iExternalUniqueId
operator|=
name|externalUniqueId
expr_stmt|;
block|}
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|iFirstName
return|;
block|}
specifier|public
name|void
name|setFirstName
parameter_list|(
name|String
name|firstName
parameter_list|)
block|{
name|iFirstName
operator|=
name|firstName
expr_stmt|;
block|}
specifier|public
name|String
name|getMiddleName
parameter_list|()
block|{
return|return
name|iMiddleName
return|;
block|}
specifier|public
name|void
name|setMiddleName
parameter_list|(
name|String
name|middleName
parameter_list|)
block|{
name|iMiddleName
operator|=
name|middleName
expr_stmt|;
block|}
specifier|public
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|iLastName
return|;
block|}
specifier|public
name|void
name|setLastName
parameter_list|(
name|String
name|lastName
parameter_list|)
block|{
name|iLastName
operator|=
name|lastName
expr_stmt|;
block|}
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|String
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|Integer
name|getFreeTimeCategory
parameter_list|()
block|{
return|return
name|iFreeTimeCategory
return|;
block|}
specifier|public
name|void
name|setFreeTimeCategory
parameter_list|(
name|Integer
name|freeTimeCategory
parameter_list|)
block|{
name|iFreeTimeCategory
operator|=
name|freeTimeCategory
expr_stmt|;
block|}
specifier|public
name|Integer
name|getSchedulePreference
parameter_list|()
block|{
return|return
name|iSchedulePreference
return|;
block|}
specifier|public
name|void
name|setSchedulePreference
parameter_list|(
name|Integer
name|schedulePreference
parameter_list|)
block|{
name|iSchedulePreference
operator|=
name|schedulePreference
expr_stmt|;
block|}
specifier|public
name|Float
name|getMaxCredit
parameter_list|()
block|{
return|return
name|iMaxCredit
return|;
block|}
specifier|public
name|void
name|setMaxCredit
parameter_list|(
name|Float
name|maxCredit
parameter_list|)
block|{
name|iMaxCredit
operator|=
name|maxCredit
expr_stmt|;
block|}
specifier|public
name|Float
name|getMinCredit
parameter_list|()
block|{
return|return
name|iMinCredit
return|;
block|}
specifier|public
name|void
name|setMinCredit
parameter_list|(
name|Float
name|minCredit
parameter_list|)
block|{
name|iMinCredit
operator|=
name|minCredit
expr_stmt|;
block|}
specifier|public
name|Float
name|getOverrideMaxCredit
parameter_list|()
block|{
return|return
name|iOverrideMaxCredit
return|;
block|}
specifier|public
name|void
name|setOverrideMaxCredit
parameter_list|(
name|Float
name|overrideMaxCredit
parameter_list|)
block|{
name|iOverrideMaxCredit
operator|=
name|overrideMaxCredit
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
name|Date
name|getScheduleEmailedDate
parameter_list|()
block|{
return|return
name|iScheduleEmailedDate
return|;
block|}
specifier|public
name|void
name|setScheduleEmailedDate
parameter_list|(
name|Date
name|scheduleEmailedDate
parameter_list|)
block|{
name|iScheduleEmailedDate
operator|=
name|scheduleEmailedDate
expr_stmt|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|iSession
return|;
block|}
specifier|public
name|void
name|setSession
parameter_list|(
name|Session
name|session
parameter_list|)
block|{
name|iSession
operator|=
name|session
expr_stmt|;
block|}
specifier|public
name|StudentSectioningStatus
name|getSectioningStatus
parameter_list|()
block|{
return|return
name|iSectioningStatus
return|;
block|}
specifier|public
name|void
name|setSectioningStatus
parameter_list|(
name|StudentSectioningStatus
name|sectioningStatus
parameter_list|)
block|{
name|iSectioningStatus
operator|=
name|sectioningStatus
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentAreaClassificationMajor
argument_list|>
name|getAreaClasfMajors
parameter_list|()
block|{
return|return
name|iAreaClasfMajors
return|;
block|}
specifier|public
name|void
name|setAreaClasfMajors
parameter_list|(
name|Set
argument_list|<
name|StudentAreaClassificationMajor
argument_list|>
name|areaClasfMajors
parameter_list|)
block|{
name|iAreaClasfMajors
operator|=
name|areaClasfMajors
expr_stmt|;
block|}
specifier|public
name|void
name|addToareaClasfMajors
parameter_list|(
name|StudentAreaClassificationMajor
name|studentAreaClassificationMajor
parameter_list|)
block|{
if|if
condition|(
name|iAreaClasfMajors
operator|==
literal|null
condition|)
name|iAreaClasfMajors
operator|=
operator|new
name|HashSet
argument_list|<
name|StudentAreaClassificationMajor
argument_list|>
argument_list|()
expr_stmt|;
name|iAreaClasfMajors
operator|.
name|add
argument_list|(
name|studentAreaClassificationMajor
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentAreaClassificationMinor
argument_list|>
name|getAreaClasfMinors
parameter_list|()
block|{
return|return
name|iAreaClasfMinors
return|;
block|}
specifier|public
name|void
name|setAreaClasfMinors
parameter_list|(
name|Set
argument_list|<
name|StudentAreaClassificationMinor
argument_list|>
name|areaClasfMinors
parameter_list|)
block|{
name|iAreaClasfMinors
operator|=
name|areaClasfMinors
expr_stmt|;
block|}
specifier|public
name|void
name|addToareaClasfMinors
parameter_list|(
name|StudentAreaClassificationMinor
name|studentAreaClassificationMinor
parameter_list|)
block|{
if|if
condition|(
name|iAreaClasfMinors
operator|==
literal|null
condition|)
name|iAreaClasfMinors
operator|=
operator|new
name|HashSet
argument_list|<
name|StudentAreaClassificationMinor
argument_list|>
argument_list|()
expr_stmt|;
name|iAreaClasfMinors
operator|.
name|add
argument_list|(
name|studentAreaClassificationMinor
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentAccomodation
argument_list|>
name|getAccomodations
parameter_list|()
block|{
return|return
name|iAccomodations
return|;
block|}
specifier|public
name|void
name|setAccomodations
parameter_list|(
name|Set
argument_list|<
name|StudentAccomodation
argument_list|>
name|accomodations
parameter_list|)
block|{
name|iAccomodations
operator|=
name|accomodations
expr_stmt|;
block|}
specifier|public
name|void
name|addToaccomodations
parameter_list|(
name|StudentAccomodation
name|studentAccomodation
parameter_list|)
block|{
if|if
condition|(
name|iAccomodations
operator|==
literal|null
condition|)
name|iAccomodations
operator|=
operator|new
name|HashSet
argument_list|<
name|StudentAccomodation
argument_list|>
argument_list|()
expr_stmt|;
name|iAccomodations
operator|.
name|add
argument_list|(
name|studentAccomodation
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentGroup
argument_list|>
name|getGroups
parameter_list|()
block|{
return|return
name|iGroups
return|;
block|}
specifier|public
name|void
name|setGroups
parameter_list|(
name|Set
argument_list|<
name|StudentGroup
argument_list|>
name|groups
parameter_list|)
block|{
name|iGroups
operator|=
name|groups
expr_stmt|;
block|}
specifier|public
name|void
name|addTogroups
parameter_list|(
name|StudentGroup
name|studentGroup
parameter_list|)
block|{
if|if
condition|(
name|iGroups
operator|==
literal|null
condition|)
name|iGroups
operator|=
operator|new
name|HashSet
argument_list|<
name|StudentGroup
argument_list|>
argument_list|()
expr_stmt|;
name|iGroups
operator|.
name|add
argument_list|(
name|studentGroup
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|WaitList
argument_list|>
name|getWaitlists
parameter_list|()
block|{
return|return
name|iWaitlists
return|;
block|}
specifier|public
name|void
name|setWaitlists
parameter_list|(
name|Set
argument_list|<
name|WaitList
argument_list|>
name|waitlists
parameter_list|)
block|{
name|iWaitlists
operator|=
name|waitlists
expr_stmt|;
block|}
specifier|public
name|void
name|addTowaitlists
parameter_list|(
name|WaitList
name|waitList
parameter_list|)
block|{
if|if
condition|(
name|iWaitlists
operator|==
literal|null
condition|)
name|iWaitlists
operator|=
operator|new
name|HashSet
argument_list|<
name|WaitList
argument_list|>
argument_list|()
expr_stmt|;
name|iWaitlists
operator|.
name|add
argument_list|(
name|waitList
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|CourseDemand
argument_list|>
name|getCourseDemands
parameter_list|()
block|{
return|return
name|iCourseDemands
return|;
block|}
specifier|public
name|void
name|setCourseDemands
parameter_list|(
name|Set
argument_list|<
name|CourseDemand
argument_list|>
name|courseDemands
parameter_list|)
block|{
name|iCourseDemands
operator|=
name|courseDemands
expr_stmt|;
block|}
specifier|public
name|void
name|addTocourseDemands
parameter_list|(
name|CourseDemand
name|courseDemand
parameter_list|)
block|{
if|if
condition|(
name|iCourseDemands
operator|==
literal|null
condition|)
name|iCourseDemands
operator|=
operator|new
name|HashSet
argument_list|<
name|CourseDemand
argument_list|>
argument_list|()
expr_stmt|;
name|iCourseDemands
operator|.
name|add
argument_list|(
name|courseDemand
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|getClassEnrollments
parameter_list|()
block|{
return|return
name|iClassEnrollments
return|;
block|}
specifier|public
name|void
name|setClassEnrollments
parameter_list|(
name|Set
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|classEnrollments
parameter_list|)
block|{
name|iClassEnrollments
operator|=
name|classEnrollments
expr_stmt|;
block|}
specifier|public
name|void
name|addToclassEnrollments
parameter_list|(
name|StudentClassEnrollment
name|studentClassEnrollment
parameter_list|)
block|{
if|if
condition|(
name|iClassEnrollments
operator|==
literal|null
condition|)
name|iClassEnrollments
operator|=
operator|new
name|HashSet
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|()
expr_stmt|;
name|iClassEnrollments
operator|.
name|add
argument_list|(
name|studentClassEnrollment
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|LastLikeCourseDemand
argument_list|>
name|getLastLikeCourseDemands
parameter_list|()
block|{
return|return
name|iLastLikeCourseDemands
return|;
block|}
specifier|public
name|void
name|setLastLikeCourseDemands
parameter_list|(
name|Set
argument_list|<
name|LastLikeCourseDemand
argument_list|>
name|lastLikeCourseDemands
parameter_list|)
block|{
name|iLastLikeCourseDemands
operator|=
name|lastLikeCourseDemands
expr_stmt|;
block|}
specifier|public
name|void
name|addTolastLikeCourseDemands
parameter_list|(
name|LastLikeCourseDemand
name|lastLikeCourseDemand
parameter_list|)
block|{
if|if
condition|(
name|iLastLikeCourseDemands
operator|==
literal|null
condition|)
name|iLastLikeCourseDemands
operator|=
operator|new
name|HashSet
argument_list|<
name|LastLikeCourseDemand
argument_list|>
argument_list|()
expr_stmt|;
name|iLastLikeCourseDemands
operator|.
name|add
argument_list|(
name|lastLikeCourseDemand
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|StudentNote
argument_list|>
name|getNotes
parameter_list|()
block|{
return|return
name|iNotes
return|;
block|}
specifier|public
name|void
name|setNotes
parameter_list|(
name|Set
argument_list|<
name|StudentNote
argument_list|>
name|notes
parameter_list|)
block|{
name|iNotes
operator|=
name|notes
expr_stmt|;
block|}
specifier|public
name|void
name|addTonotes
parameter_list|(
name|StudentNote
name|studentNote
parameter_list|)
block|{
if|if
condition|(
name|iNotes
operator|==
literal|null
condition|)
name|iNotes
operator|=
operator|new
name|HashSet
argument_list|<
name|StudentNote
argument_list|>
argument_list|()
expr_stmt|;
name|iNotes
operator|.
name|add
argument_list|(
name|studentNote
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Advisor
argument_list|>
name|getAdvisors
parameter_list|()
block|{
return|return
name|iAdvisors
return|;
block|}
specifier|public
name|void
name|setAdvisors
parameter_list|(
name|Set
argument_list|<
name|Advisor
argument_list|>
name|advisors
parameter_list|)
block|{
name|iAdvisors
operator|=
name|advisors
expr_stmt|;
block|}
specifier|public
name|void
name|addToadvisors
parameter_list|(
name|Advisor
name|advisor
parameter_list|)
block|{
if|if
condition|(
name|iAdvisors
operator|==
literal|null
condition|)
name|iAdvisors
operator|=
operator|new
name|HashSet
argument_list|<
name|Advisor
argument_list|>
argument_list|()
expr_stmt|;
name|iAdvisors
operator|.
name|add
argument_list|(
name|advisor
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|AdvisorCourseRequest
argument_list|>
name|getAdvisorCourseRequests
parameter_list|()
block|{
return|return
name|iAdvisorCourseRequests
return|;
block|}
specifier|public
name|void
name|setAdvisorCourseRequests
parameter_list|(
name|Set
argument_list|<
name|AdvisorCourseRequest
argument_list|>
name|advisorCourseRequests
parameter_list|)
block|{
name|iAdvisorCourseRequests
operator|=
name|advisorCourseRequests
expr_stmt|;
block|}
specifier|public
name|void
name|addToadvisorCourseRequests
parameter_list|(
name|AdvisorCourseRequest
name|advisorCourseRequest
parameter_list|)
block|{
if|if
condition|(
name|iAdvisorCourseRequests
operator|==
literal|null
condition|)
name|iAdvisorCourseRequests
operator|=
operator|new
name|HashSet
argument_list|<
name|AdvisorCourseRequest
argument_list|>
argument_list|()
expr_stmt|;
name|iAdvisorCourseRequests
operator|.
name|add
argument_list|(
name|advisorCourseRequest
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
name|Student
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
name|Student
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
name|Student
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
literal|"Student["
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
literal|"Student["
operator|+
literal|"\n	Email: "
operator|+
name|getEmail
argument_list|()
operator|+
literal|"\n	ExternalUniqueId: "
operator|+
name|getExternalUniqueId
argument_list|()
operator|+
literal|"\n	FirstName: "
operator|+
name|getFirstName
argument_list|()
operator|+
literal|"\n	FreeTimeCategory: "
operator|+
name|getFreeTimeCategory
argument_list|()
operator|+
literal|"\n	LastName: "
operator|+
name|getLastName
argument_list|()
operator|+
literal|"\n	MaxCredit: "
operator|+
name|getMaxCredit
argument_list|()
operator|+
literal|"\n	MiddleName: "
operator|+
name|getMiddleName
argument_list|()
operator|+
literal|"\n	MinCredit: "
operator|+
name|getMinCredit
argument_list|()
operator|+
literal|"\n	OverrideExternalId: "
operator|+
name|getOverrideExternalId
argument_list|()
operator|+
literal|"\n	OverrideMaxCredit: "
operator|+
name|getOverrideMaxCredit
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
literal|"\n	ScheduleEmailedDate: "
operator|+
name|getScheduleEmailedDate
argument_list|()
operator|+
literal|"\n	SchedulePreference: "
operator|+
name|getSchedulePreference
argument_list|()
operator|+
literal|"\n	SectioningStatus: "
operator|+
name|getSectioningStatus
argument_list|()
operator|+
literal|"\n	Session: "
operator|+
name|getSession
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

