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
name|dataexchange
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormatSymbols
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Element
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
name|AdvisorClassPref
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
name|AdvisorInstrMthPref
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
name|AdvisorSectioningPref
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
name|StudentClassPref
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
name|StudentInstrMthPref
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
operator|.
name|Constants
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
name|util
operator|.
name|Formats
import|;
end_import

begin_class
specifier|public
class|class
name|StudentSectioningExport
extends|extends
name|BaseExport
block|{
specifier|protected
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Number
argument_list|>
name|sTwoNumbersDF
init|=
name|Formats
operator|.
name|getNumberFormat
argument_list|(
literal|"00"
argument_list|)
decl_stmt|;
specifier|protected
specifier|static
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|sDateFormat
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
literal|"yyyy-MM-dd'T'HH:mm:ss'Z'"
argument_list|)
decl_stmt|;
specifier|protected
name|DecimalFormat
name|iCreditDF
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"0.0"
argument_list|,
operator|new
name|DecimalFormatSymbols
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|saveXml
parameter_list|(
name|Document
name|document
parameter_list|,
name|Session
name|session
parameter_list|,
name|Properties
name|parameters
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|addElement
argument_list|(
literal|"request"
argument_list|)
decl_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"campus"
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"year"
argument_list|,
name|session
operator|.
name|getAcademicYear
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"term"
argument_list|,
name|session
operator|.
name|getAcademicTerm
argument_list|()
argument_list|)
expr_stmt|;
name|document
operator|.
name|addDocType
argument_list|(
literal|"request"
argument_list|,
literal|"-//UniTime//UniTime Student Sectioning DTD/EN"
argument_list|,
literal|"http://www.unitime.org/interface/StudentSectioning.dtd"
argument_list|)
expr_stmt|;
for|for
control|(
name|Student
name|student
range|:
operator|(
name|List
argument_list|<
name|Student
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select s from Student s where s.session.uniqueId = :sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|Element
name|studentEl
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"student"
argument_list|)
decl_stmt|;
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"key"
argument_list|,
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
operator|||
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
name|student
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
else|:
name|student
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
name|studentEl
operator|.
name|addAttribute
argument_list|(
literal|"status"
argument_list|,
name|student
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
comment|// Student demographics
name|Element
name|demographicsEl
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"updateDemographics"
argument_list|)
decl_stmt|;
name|Element
name|nameEl
init|=
name|demographicsEl
operator|.
name|addElement
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|.
name|getFirstName
argument_list|()
operator|!=
literal|null
condition|)
name|nameEl
operator|.
name|addAttribute
argument_list|(
literal|"first"
argument_list|,
name|student
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getMiddleName
argument_list|()
operator|!=
literal|null
condition|)
name|nameEl
operator|.
name|addAttribute
argument_list|(
literal|"middle"
argument_list|,
name|student
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getLastName
argument_list|()
operator|!=
literal|null
condition|)
name|nameEl
operator|.
name|addAttribute
argument_list|(
literal|"last"
argument_list|,
name|student
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentAreaClassificationMajor
name|acm
range|:
name|student
operator|.
name|getAreaClasfMajors
argument_list|()
control|)
block|{
name|Element
name|acadAreaEl
init|=
name|demographicsEl
operator|.
name|addElement
argument_list|(
literal|"acadArea"
argument_list|)
decl_stmt|;
name|acadAreaEl
operator|.
name|addAttribute
argument_list|(
literal|"abbv"
argument_list|,
name|acm
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|acadAreaEl
operator|.
name|addAttribute
argument_list|(
literal|"classification"
argument_list|,
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|Element
name|majorEl
init|=
name|acadAreaEl
operator|.
name|addElement
argument_list|(
literal|"major"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|acm
operator|.
name|getMajor
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|acm
operator|.
name|getConcentration
argument_list|()
operator|!=
literal|null
condition|)
name|majorEl
operator|.
name|addAttribute
argument_list|(
literal|"concentration"
argument_list|,
name|acm
operator|.
name|getConcentration
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acm
operator|.
name|getDegree
argument_list|()
operator|!=
literal|null
condition|)
name|majorEl
operator|.
name|addAttribute
argument_list|(
literal|"degree"
argument_list|,
name|acm
operator|.
name|getDegree
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acm
operator|.
name|getProgram
argument_list|()
operator|!=
literal|null
condition|)
name|majorEl
operator|.
name|addAttribute
argument_list|(
literal|"program"
argument_list|,
name|acm
operator|.
name|getProgram
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acm
operator|.
name|getCampus
argument_list|()
operator|!=
literal|null
condition|)
name|majorEl
operator|.
name|addAttribute
argument_list|(
literal|"campus"
argument_list|,
name|acm
operator|.
name|getCampus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acm
operator|.
name|getWeight
argument_list|()
operator|!=
literal|null
operator|&&
name|acm
operator|.
name|getWeight
argument_list|()
operator|!=
literal|1.0
condition|)
name|majorEl
operator|.
name|addAttribute
argument_list|(
literal|"weight"
argument_list|,
name|acm
operator|.
name|getWeight
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StudentAreaClassificationMinor
name|acm
range|:
name|student
operator|.
name|getAreaClasfMinors
argument_list|()
control|)
block|{
name|Element
name|acadAreaEl
init|=
name|demographicsEl
operator|.
name|addElement
argument_list|(
literal|"acadArea"
argument_list|)
decl_stmt|;
name|acadAreaEl
operator|.
name|addAttribute
argument_list|(
literal|"abbv"
argument_list|,
name|acm
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|acadAreaEl
operator|.
name|addAttribute
argument_list|(
literal|"classification"
argument_list|,
name|acm
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
name|acadAreaEl
operator|.
name|addElement
argument_list|(
literal|"minor"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|acm
operator|.
name|getMinor
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StudentGroup
name|group
range|:
name|student
operator|.
name|getGroups
argument_list|()
control|)
name|demographicsEl
operator|.
name|addElement
argument_list|(
literal|"groupAffiliation"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentAccomodation
name|acc
range|:
name|student
operator|.
name|getAccomodations
argument_list|()
control|)
name|demographicsEl
operator|.
name|addElement
argument_list|(
literal|"disability"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"code"
argument_list|,
name|acc
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getMinCredit
argument_list|()
operator|!=
literal|null
condition|)
name|demographicsEl
operator|.
name|addAttribute
argument_list|(
literal|"minCredit"
argument_list|,
name|iCreditDF
operator|.
name|format
argument_list|(
name|student
operator|.
name|getMinCredit
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|student
operator|.
name|getMaxCredit
argument_list|()
operator|!=
literal|null
condition|)
name|demographicsEl
operator|.
name|addAttribute
argument_list|(
literal|"maxCredit"
argument_list|,
name|iCreditDF
operator|.
name|format
argument_list|(
name|student
operator|.
name|getMaxCredit
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Course requests
name|Element
name|requestsEl
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"updateCourseRequests"
argument_list|)
operator|.
name|addAttribute
argument_list|(
literal|"commit"
argument_list|,
literal|"true"
argument_list|)
decl_stmt|;
for|for
control|(
name|CourseDemand
name|cd
range|:
operator|new
name|TreeSet
argument_list|<
name|CourseDemand
argument_list|>
argument_list|(
name|student
operator|.
name|getCourseDemands
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Element
name|freeTimeEl
init|=
name|requestsEl
operator|.
name|addElement
argument_list|(
literal|"freeTime"
argument_list|)
decl_stmt|;
name|String
name|days
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|Constants
operator|.
name|DAY_NAMES_SHORT
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|i
index|]
operator|)
operator|!=
literal|0
condition|)
block|{
name|days
operator|+=
name|Constants
operator|.
name|DAY_NAMES_SHORT
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"days"
argument_list|,
name|days
argument_list|)
expr_stmt|;
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"startTime"
argument_list|,
name|startSlot2startTime
argument_list|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"endTime"
argument_list|,
name|startSlot2startTime
argument_list|(
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|+
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"length"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|cd
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|cd
operator|.
name|getCourseRequests
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|courseOfferingEl
init|=
literal|null
decl_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|CourseRequest
name|cr
range|:
operator|new
name|TreeSet
argument_list|<
name|CourseRequest
argument_list|>
argument_list|(
name|cd
operator|.
name|getCourseRequests
argument_list|()
argument_list|)
control|)
block|{
name|courseOfferingEl
operator|=
operator|(
name|courseOfferingEl
operator|==
literal|null
condition|?
name|requestsEl
operator|.
name|addElement
argument_list|(
literal|"courseOffering"
argument_list|)
else|:
name|courseOfferingEl
operator|.
name|addElement
argument_list|(
literal|"alternative"
argument_list|)
operator|)
expr_stmt|;
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"subjectArea"
argument_list|,
name|cr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"courseNumber"
argument_list|,
name|cr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|&&
name|cd
operator|.
name|isWaitlist
argument_list|()
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"waitlist"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|&&
name|cd
operator|.
name|getNoSub
argument_list|()
operator|!=
literal|null
operator|&&
name|cd
operator|.
name|getNoSub
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"nosub"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|&&
name|cd
operator|.
name|isAlternative
argument_list|()
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"alternative"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|&&
name|cd
operator|.
name|getCritical
argument_list|()
operator|!=
literal|null
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"critical"
argument_list|,
name|CourseDemand
operator|.
name|Critical
operator|.
name|values
argument_list|()
index|[
name|cd
operator|.
name|getCritical
argument_list|()
index|]
operator|.
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|&&
name|cd
operator|.
name|getCriticalOverride
argument_list|()
operator|!=
literal|null
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"criticalOverride"
argument_list|,
name|CourseDemand
operator|.
name|Critical
operator|.
name|values
argument_list|()
index|[
name|cd
operator|.
name|getCriticalOverride
argument_list|()
index|]
operator|.
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|cr
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
operator|&&
name|cr
operator|.
name|getCredit
argument_list|()
operator|!=
literal|0
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"credit"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|cr
operator|.
name|getCredit
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|&&
name|cd
operator|.
name|getWaitlistedTimeStamp
argument_list|()
operator|!=
literal|null
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"waitlisted"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|cd
operator|.
name|getWaitlistedTimeStamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|first
operator|&&
name|cd
operator|.
name|getTimestamp
argument_list|()
operator|!=
literal|null
condition|)
name|courseOfferingEl
operator|.
name|addAttribute
argument_list|(
literal|"requested"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|cd
operator|.
name|getTimestamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|enrollment
range|:
name|cr
operator|.
name|getClassEnrollments
argument_list|()
control|)
block|{
name|Element
name|classEl
init|=
name|courseOfferingEl
operator|.
name|addElement
argument_list|(
literal|"class"
argument_list|)
decl_stmt|;
name|Class_
name|clazz
init|=
name|enrollment
operator|.
name|getClazz
argument_list|()
decl_stmt|;
name|String
name|extId
init|=
name|clazz
operator|.
name|getExternalId
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|extId
operator|!=
literal|null
operator|&&
operator|!
name|extId
operator|.
name|isEmpty
argument_list|()
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|extId
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"suffix"
argument_list|,
name|getClassSuffix
argument_list|(
name|clazz
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|enrollment
operator|.
name|getTimestamp
argument_list|()
operator|!=
literal|null
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"enrolled"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|enrollment
operator|.
name|getTimestamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cr
operator|.
name|getPreferences
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|cr
operator|.
name|getPreferences
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|prefEl
init|=
name|courseOfferingEl
operator|.
name|addElement
argument_list|(
literal|"preferences"
argument_list|)
decl_stmt|;
for|for
control|(
name|StudentSectioningPref
name|p
range|:
name|cr
operator|.
name|getPreferences
argument_list|()
control|)
block|{
if|if
condition|(
name|p
operator|instanceof
name|StudentClassPref
condition|)
block|{
name|StudentClassPref
name|scp
init|=
operator|(
name|StudentClassPref
operator|)
name|p
decl_stmt|;
name|Element
name|classEl
init|=
name|prefEl
operator|.
name|addElement
argument_list|(
literal|"class"
argument_list|)
decl_stmt|;
name|String
name|extId
init|=
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getExternalId
argument_list|(
name|cr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|extId
operator|!=
literal|null
operator|&&
operator|!
name|extId
operator|.
name|isEmpty
argument_list|()
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|extId
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"suffix"
argument_list|,
name|getClassSuffix
argument_list|(
name|scp
operator|.
name|getClazz
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|scp
operator|.
name|isRequired
argument_list|()
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"required"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|p
operator|instanceof
name|StudentInstrMthPref
condition|)
block|{
name|StudentInstrMthPref
name|imp
init|=
operator|(
name|StudentInstrMthPref
operator|)
name|p
decl_stmt|;
name|Element
name|imEl
init|=
name|prefEl
operator|.
name|addElement
argument_list|(
literal|"instructional-method"
argument_list|)
decl_stmt|;
name|imEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|imEl
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|imp
operator|.
name|isRequired
argument_list|()
condition|)
name|imEl
operator|.
name|addAttribute
argument_list|(
literal|"required"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|first
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
comment|// Advisor recommendations
name|Element
name|recommendationsEl
init|=
name|studentEl
operator|.
name|addElement
argument_list|(
literal|"updateAdvisorRecommendations"
argument_list|)
decl_stmt|;
name|Element
name|recEl
init|=
literal|null
decl_stmt|;
for|for
control|(
name|AdvisorCourseRequest
name|acr
range|:
operator|new
name|TreeSet
argument_list|<
name|AdvisorCourseRequest
argument_list|>
argument_list|(
name|student
operator|.
name|getAdvisorCourseRequests
argument_list|()
argument_list|)
control|)
block|{
name|Element
name|acrEl
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|acr
operator|.
name|getPriority
argument_list|()
operator|==
operator|-
literal|1
condition|)
block|{
if|if
condition|(
name|acr
operator|.
name|getNotes
argument_list|()
operator|!=
literal|null
condition|)
name|recommendationsEl
operator|.
name|addAttribute
argument_list|(
literal|"notes"
argument_list|,
name|acr
operator|.
name|getNotes
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getTimestamp
argument_list|()
operator|!=
literal|null
condition|)
name|recommendationsEl
operator|.
name|addAttribute
argument_list|(
literal|"recommended"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|acr
operator|.
name|getTimestamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|else if
condition|(
name|acr
operator|.
name|getAlternative
argument_list|()
operator|==
literal|0
condition|)
block|{
name|recEl
operator|=
name|recommendationsEl
operator|.
name|addElement
argument_list|(
literal|"recommendation"
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|isSubstitute
argument_list|()
condition|)
name|recEl
operator|.
name|addAttribute
argument_list|(
literal|"substitute"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|acrEl
operator|=
name|recEl
expr_stmt|;
block|}
else|else
block|{
name|acrEl
operator|=
name|recEl
operator|.
name|addElement
argument_list|(
literal|"alternative"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|acr
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"credit"
argument_list|,
name|acr
operator|.
name|getCredit
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getWaitlist
argument_list|()
operator|!=
literal|null
condition|)
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"waitlist"
argument_list|,
name|acr
operator|.
name|getWaitlist
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getNoSub
argument_list|()
operator|!=
literal|null
condition|)
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"nosub"
argument_list|,
name|acr
operator|.
name|getNoSub
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getNotes
argument_list|()
operator|!=
literal|null
condition|)
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"notes"
argument_list|,
name|acr
operator|.
name|getNotes
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getCourse
argument_list|()
operator|!=
literal|null
condition|)
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"course"
argument_list|,
name|acr
operator|.
name|getCourse
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|Element
name|freeTimeEl
init|=
name|acrEl
operator|.
name|addElement
argument_list|(
literal|"freeTime"
argument_list|)
decl_stmt|;
name|String
name|days
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|Constants
operator|.
name|DAY_NAMES_SHORT
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getDayCode
argument_list|()
operator|&
name|Constants
operator|.
name|DAY_CODES
index|[
name|i
index|]
operator|)
operator|!=
literal|0
condition|)
block|{
name|days
operator|+=
name|Constants
operator|.
name|DAY_NAMES_SHORT
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"days"
argument_list|,
name|days
argument_list|)
expr_stmt|;
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"startTime"
argument_list|,
name|startSlot2startTime
argument_list|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"endTime"
argument_list|,
name|startSlot2startTime
argument_list|(
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getStartSlot
argument_list|()
operator|+
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|freeTimeEl
operator|.
name|addAttribute
argument_list|(
literal|"length"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|*
name|acr
operator|.
name|getFreeTime
argument_list|()
operator|.
name|getLength
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|acr
operator|.
name|getTimestamp
argument_list|()
operator|!=
literal|null
condition|)
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"recommended"
argument_list|,
name|sDateFormat
operator|.
name|format
argument_list|(
name|acr
operator|.
name|getTimestamp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"subjectArea"
argument_list|,
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"courseNumber"
argument_list|,
name|acr
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getCritical
argument_list|()
operator|!=
literal|null
condition|)
name|acrEl
operator|.
name|addAttribute
argument_list|(
literal|"critical"
argument_list|,
name|CourseDemand
operator|.
name|Critical
operator|.
name|values
argument_list|()
index|[
name|acr
operator|.
name|getCritical
argument_list|()
index|]
operator|.
name|name
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|acr
operator|.
name|getPreferences
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|acr
operator|.
name|getPreferences
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Element
name|prefEl
init|=
name|acrEl
operator|.
name|addElement
argument_list|(
literal|"preferences"
argument_list|)
decl_stmt|;
for|for
control|(
name|AdvisorSectioningPref
name|p
range|:
name|acr
operator|.
name|getPreferences
argument_list|()
control|)
block|{
if|if
condition|(
name|p
operator|instanceof
name|AdvisorClassPref
condition|)
block|{
name|AdvisorClassPref
name|scp
init|=
operator|(
name|AdvisorClassPref
operator|)
name|p
decl_stmt|;
name|Element
name|classEl
init|=
name|prefEl
operator|.
name|addElement
argument_list|(
literal|"class"
argument_list|)
decl_stmt|;
name|String
name|extId
init|=
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getExternalId
argument_list|(
name|acr
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|extId
operator|!=
literal|null
operator|&&
operator|!
name|extId
operator|.
name|isEmpty
argument_list|()
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|extId
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"type"
argument_list|,
name|scp
operator|.
name|getClazz
argument_list|()
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItypeDesc
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"suffix"
argument_list|,
name|getClassSuffix
argument_list|(
name|scp
operator|.
name|getClazz
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|scp
operator|.
name|isRequired
argument_list|()
condition|)
name|classEl
operator|.
name|addAttribute
argument_list|(
literal|"required"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|p
operator|instanceof
name|AdvisorInstrMthPref
condition|)
block|{
name|AdvisorInstrMthPref
name|imp
init|=
operator|(
name|AdvisorInstrMthPref
operator|)
name|p
decl_stmt|;
name|Element
name|imEl
init|=
name|prefEl
operator|.
name|addElement
argument_list|(
literal|"instructional-method"
argument_list|)
decl_stmt|;
name|imEl
operator|.
name|addAttribute
argument_list|(
literal|"externalId"
argument_list|,
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|imEl
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
name|imp
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|imp
operator|.
name|isRequired
argument_list|()
condition|)
name|imEl
operator|.
name|addAttribute
argument_list|(
literal|"required"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
block|}
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|rollbackTransaction
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|String
name|startSlot2startTime
parameter_list|(
name|int
name|startSlot
parameter_list|)
block|{
name|int
name|minHrs
init|=
name|startSlot
operator|*
name|Constants
operator|.
name|SLOT_LENGTH_MIN
operator|+
name|Constants
operator|.
name|FIRST_SLOT_TIME_MIN
decl_stmt|;
return|return
name|sTwoNumbersDF
operator|.
name|format
argument_list|(
name|minHrs
operator|/
literal|60
argument_list|)
operator|+
name|sTwoNumbersDF
operator|.
name|format
argument_list|(
name|minHrs
operator|%
literal|60
argument_list|)
return|;
block|}
block|}
end_class

end_unit

