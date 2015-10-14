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
name|api
operator|.
name|connectors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|api
operator|.
name|ApiConnector
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
name|api
operator|.
name|ApiHelper
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
name|AcademicAreaClassification
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
name|Event
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
name|PosMajor
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
name|PosMinor
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
name|dao
operator|.
name|Class_DAO
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
name|CourseOfferingDAO
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
name|EventDAO
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
name|ExamDAO
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
name|InstrOfferingConfigDAO
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
name|InstructionalOfferingDAO
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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/api/enrollments"
argument_list|)
specifier|public
class|class
name|EnrollmentsConnector
extends|extends
name|ApiConnector
block|{
annotation|@
name|Override
specifier|public
name|void
name|doGet
parameter_list|(
name|ApiHelper
name|helper
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|eventId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"eventId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|eventId
operator|!=
literal|null
condition|)
block|{
name|Event
name|event
init|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|eventId
argument_list|)
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|event
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Event with the given ID does not exist."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|event
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|ApiRetrieveEnrollments
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|convert
argument_list|(
name|event
operator|.
name|getStudentClassEnrollments
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|classId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"classId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|classId
operator|!=
literal|null
condition|)
block|{
name|Class_
name|clazz
init|=
name|Class_DAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|classId
argument_list|)
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Class with the given ID does not exist."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|clazz
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|ApiRetrieveEnrollments
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|convert
argument_list|(
name|clazz
operator|.
name|getStudentEnrollments
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|examId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"examId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|examId
operator|!=
literal|null
condition|)
block|{
name|Exam
name|exam
init|=
name|ExamDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|examId
argument_list|)
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|exam
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Examination with the given ID does not exist."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|exam
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|ApiRetrieveEnrollments
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|convert
argument_list|(
name|exam
operator|.
name|getStudentClassEnrollments
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|courseId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"courseId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|courseId
operator|!=
literal|null
condition|)
block|{
name|CourseOffering
name|course
init|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|courseId
argument_list|)
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|course
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Course with the given ID does not exist."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|ApiRetrieveEnrollments
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|convert
argument_list|(
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentClassEnrollment e where e.courseOffering.uniqueId = :courseId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"courseId"
argument_list|,
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|offeringId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"offeringId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|offeringId
operator|!=
literal|null
condition|)
block|{
name|InstructionalOffering
name|offering
init|=
name|InstructionalOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|offeringId
argument_list|)
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|offering
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Offering with the given ID does not exist."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|offering
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|ApiRetrieveEnrollments
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|convert
argument_list|(
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentClassEnrollment e where e.courseOffering.instructionalOffering.uniqueId = :offeringId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"offeringId"
argument_list|,
name|offering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|configurationId
init|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"configurationId"
argument_list|)
decl_stmt|;
if|if
condition|(
name|configurationId
operator|!=
literal|null
condition|)
block|{
name|InstrOfferingConfig
name|config
init|=
name|InstrOfferingConfigDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|parseLong
argument_list|(
name|configurationId
argument_list|)
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|config
operator|==
literal|null
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Configuration with the given ID does not exist."
argument_list|)
throw|;
name|helper
operator|.
name|getSessionContext
argument_list|()
operator|.
name|checkPermissionAnyAuthority
argument_list|(
name|config
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|Right
operator|.
name|ApiRetrieveEnrollments
argument_list|)
expr_stmt|;
name|helper
operator|.
name|setResponse
argument_list|(
name|convert
argument_list|(
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentClassEnrollment e where e.clazz.schedulingSubpart.instrOfferingConfig.uniqueId = :configId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"configId"
argument_list|,
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|List
argument_list|<
name|ClassEnrollmentInfo
argument_list|>
name|convert
parameter_list|(
name|Collection
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|enrollments
parameter_list|)
block|{
name|List
argument_list|<
name|ClassEnrollmentInfo
argument_list|>
name|converted
init|=
operator|new
name|ArrayList
argument_list|<
name|ClassEnrollmentInfo
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|enrollments
operator|!=
literal|null
condition|)
for|for
control|(
name|StudentClassEnrollment
name|enrollment
range|:
name|enrollments
control|)
name|converted
operator|.
name|add
argument_list|(
operator|new
name|ClassEnrollmentInfo
argument_list|(
name|enrollment
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|converted
return|;
block|}
specifier|static
class|class
name|ClassEnrollmentInfo
block|{
name|Long
name|iStudentId
decl_stmt|;
name|String
name|iExternalId
decl_stmt|;
name|String
name|iFirstName
decl_stmt|;
name|String
name|iMiddleName
decl_stmt|;
name|String
name|iLastName
decl_stmt|;
name|String
name|iTitle
decl_stmt|;
name|String
name|iEmail
decl_stmt|;
name|String
name|iSectioningStatus
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|iArea
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|iClassification
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|iMajor
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|iMinor
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|iGroup
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|iAccomodation
decl_stmt|;
name|Long
name|iCourseId
decl_stmt|;
name|String
name|iSubjectArea
decl_stmt|;
name|String
name|iCourseNumber
decl_stmt|;
name|String
name|iCourseTitle
decl_stmt|;
name|Long
name|iClassId
decl_stmt|;
name|String
name|iSubpart
decl_stmt|;
name|String
name|iSectionNumber
decl_stmt|;
name|String
name|iClassSuffix
decl_stmt|;
name|String
name|iClassExternalId
decl_stmt|;
name|ClassEnrollmentInfo
parameter_list|(
name|StudentClassEnrollment
name|enrollment
parameter_list|)
block|{
name|iStudentId
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iExternalId
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getExternalUniqueId
argument_list|()
expr_stmt|;
name|iFirstName
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getFirstName
argument_list|()
expr_stmt|;
name|iMiddleName
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getMiddleName
argument_list|()
expr_stmt|;
name|iLastName
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getLastName
argument_list|()
expr_stmt|;
name|iTitle
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getAcademicTitle
argument_list|()
expr_stmt|;
name|iEmail
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getEmail
argument_list|()
expr_stmt|;
if|if
condition|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
name|iSectioningStatus
operator|=
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
expr_stmt|;
for|for
control|(
name|AcademicAreaClassification
name|aac
range|:
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getAcademicAreaClassifications
argument_list|()
control|)
block|{
if|if
condition|(
name|iArea
operator|==
literal|null
condition|)
block|{
name|iArea
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iClassification
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
block|}
name|iArea
operator|.
name|add
argument_list|(
name|aac
operator|.
name|getAcademicArea
argument_list|()
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|iClassification
operator|.
name|add
argument_list|(
name|aac
operator|.
name|getAcademicClassification
argument_list|()
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|PosMajor
name|major
range|:
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getPosMajors
argument_list|()
control|)
block|{
if|if
condition|(
name|iMajor
operator|==
literal|null
condition|)
name|iMajor
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iMajor
operator|.
name|add
argument_list|(
name|major
operator|.
name|getCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|PosMinor
name|minor
range|:
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getPosMinors
argument_list|()
control|)
block|{
if|if
condition|(
name|iMinor
operator|==
literal|null
condition|)
name|iMinor
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iMinor
operator|.
name|add
argument_list|(
name|minor
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
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getGroups
argument_list|()
control|)
block|{
if|if
condition|(
name|iGroup
operator|==
literal|null
condition|)
name|iGroup
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iGroup
operator|.
name|add
argument_list|(
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StudentAccomodation
name|accomodation
range|:
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getAccomodations
argument_list|()
control|)
block|{
if|if
condition|(
name|iAccomodation
operator|==
literal|null
condition|)
name|iAccomodation
operator|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|iAccomodation
operator|.
name|add
argument_list|(
name|accomodation
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|iCourseId
operator|=
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iSubjectArea
operator|=
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getSubjectAreaAbbv
argument_list|()
expr_stmt|;
name|iCourseNumber
operator|=
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getCourseNbr
argument_list|()
expr_stmt|;
name|iCourseTitle
operator|=
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
operator|.
name|getTitle
argument_list|()
expr_stmt|;
name|iClassId
operator|=
name|enrollment
operator|.
name|getClazz
argument_list|()
operator|.
name|getUniqueId
argument_list|()
expr_stmt|;
name|iSectionNumber
operator|=
name|enrollment
operator|.
name|getClazz
argument_list|()
operator|.
name|getSectionNumberString
argument_list|()
expr_stmt|;
name|iSubpart
operator|=
name|enrollment
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
expr_stmt|;
name|iClassSuffix
operator|=
name|enrollment
operator|.
name|getClazz
argument_list|()
operator|.
name|getClassSuffix
argument_list|(
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
name|iClassExternalId
operator|=
name|enrollment
operator|.
name|getClazz
argument_list|()
operator|.
name|getExternalId
argument_list|(
name|enrollment
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"enrollments"
return|;
block|}
block|}
end_class

end_unit

