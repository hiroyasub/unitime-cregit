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
name|server
operator|.
name|exams
package|;
end_package

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
name|List
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
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|util
operator|.
name|HtmlUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|Localization
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
name|gwt
operator|.
name|client
operator|.
name|sectioning
operator|.
name|ExaminationEnrollmentTable
operator|.
name|ExaminationScheduleRpcRequest
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
name|gwt
operator|.
name|client
operator|.
name|sectioning
operator|.
name|ExaminationEnrollmentTable
operator|.
name|ExaminationScheduleRpcResponse
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|gwt
operator|.
name|resources
operator|.
name|GwtMessages
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|ContactInterface
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|RelatedObjectInterface
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|ResourceInterface
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
name|gwt
operator|.
name|shared
operator|.
name|EventInterface
operator|.
name|ResourceType
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
name|DepartmentalInstructor
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
name|ExamOwner
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
name|ExamPeriod
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
name|security
operator|.
name|SessionContext
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

begin_import
import|import
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
name|ExamSolverProxy
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignment
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignmentInfo
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamRoomInfo
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignmentInfo
operator|.
name|BackToBackConflict
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignmentInfo
operator|.
name|DirectConflict
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamAssignmentInfo
operator|.
name|MoreThanTwoADayConflict
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
name|solver
operator|.
name|exam
operator|.
name|ui
operator|.
name|ExamInfo
operator|.
name|ExamSectionInfo
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
name|solver
operator|.
name|service
operator|.
name|SolverService
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|ExaminationScheduleRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ExaminationScheduleBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ExaminationScheduleRpcRequest
argument_list|,
name|ExaminationScheduleRpcResponse
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtMessages
name|MESSAGES
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
name|examinationSolverService
decl_stmt|;
annotation|@
name|Override
specifier|public
name|ExaminationScheduleRpcResponse
name|execute
parameter_list|(
name|ExaminationScheduleRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|ExamDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
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
name|request
operator|.
name|getExamId
argument_list|()
argument_list|)
decl_stmt|;
name|context
operator|.
name|checkPermission
argument_list|(
name|exam
argument_list|,
name|Right
operator|.
name|ExaminationDetail
argument_list|)
expr_stmt|;
name|ExamSolverProxy
name|proxy
init|=
name|examinationSolverService
operator|.
name|getSolver
argument_list|()
decl_stmt|;
if|if
condition|(
name|proxy
operator|!=
literal|null
operator|&&
operator|!
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|proxy
operator|.
name|getExamTypeId
argument_list|()
argument_list|)
condition|)
name|proxy
operator|=
literal|null
expr_stmt|;
name|ExaminationScheduleRpcResponse
name|response
init|=
operator|new
name|ExaminationScheduleRpcResponse
argument_list|()
decl_stmt|;
name|response
operator|.
name|setExamType
argument_list|(
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|Object
index|[]
argument_list|>
name|exams
init|=
operator|new
name|ArrayList
argument_list|<
name|Object
index|[]
argument_list|>
argument_list|()
decl_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select o, enrl.courseOffering from ExamOwner o, StudentClassEnrollment enrl inner join enrl.courseOffering co "
operator|+
literal|"where o.ownerType = :type and o.ownerId = co.uniqueId and enrl.student.uniqueId = :studentId and o.exam.examType.uniqueId = :examTypeId"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examTypeId"
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select o, enrl.courseOffering from ExamOwner o, StudentClassEnrollment enrl inner join enrl.courseOffering.instructionalOffering io "
operator|+
literal|"where o.ownerType = :type and o.ownerId = io.uniqueId and enrl.student.uniqueId = :studentId and o.exam.examType.uniqueId = :examTypeId"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examTypeId"
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select o, enrl.courseOffering from ExamOwner o, StudentClassEnrollment enrl inner join enrl.clazz.schedulingSubpart.instrOfferingConfig cfg "
operator|+
literal|"where o.ownerType = :type and o.ownerId = cfg.uniqueId and enrl.student.uniqueId = :studentId and o.exam.examType.uniqueId = :examTypeId"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examTypeId"
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
name|exams
operator|.
name|addAll
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select o, enrl.courseOffering from ExamOwner o, StudentClassEnrollment enrl inner join enrl.clazz c "
operator|+
literal|"where o.ownerType = :type and o.ownerId = c.uniqueId and enrl.student.uniqueId = :studentId and o.exam.examType.uniqueId = :examTypeId"
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"type"
argument_list|,
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examTypeId"
argument_list|,
name|exam
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
index|[]
name|o
range|:
name|exams
control|)
block|{
name|ExamOwner
name|owner
init|=
operator|(
name|ExamOwner
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
name|Exam
name|x
init|=
name|owner
operator|.
name|getExam
argument_list|()
decl_stmt|;
name|RelatedObjectInterface
name|related
init|=
operator|new
name|RelatedObjectInterface
argument_list|()
decl_stmt|;
name|related
operator|.
name|setType
argument_list|(
name|RelatedObjectInterface
operator|.
name|RelatedObjectType
operator|.
name|Examination
argument_list|)
expr_stmt|;
name|related
operator|.
name|setUniqueId
argument_list|(
name|x
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setName
argument_list|(
name|x
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
name|x
operator|.
name|generateName
argument_list|()
else|:
name|x
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addCourseId
argument_list|(
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addCourseName
argument_list|(
name|co
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addCourseTitle
argument_list|(
name|co
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setInstruction
argument_list|(
name|x
operator|.
name|getExamType
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setInstructionType
argument_list|(
name|x
operator|.
name|getExamType
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|owner
operator|.
name|getOwnerType
argument_list|()
operator|==
name|ExamOwner
operator|.
name|sOwnerTypeClass
condition|)
block|{
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|owner
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
name|related
operator|.
name|setSectionNumber
argument_list|(
name|clazz
operator|.
name|getSectionNumberString
argument_list|(
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|related
operator|.
name|setInstruction
argument_list|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getItype
argument_list|()
operator|.
name|getAbbv
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalMethod
argument_list|()
operator|!=
literal|null
condition|)
name|related
operator|.
name|setInstruction
argument_list|(
name|related
operator|.
name|getInstruction
argument_list|()
operator|+
literal|" ("
operator|+
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getClassSuffix
argument_list|()
operator|!=
literal|null
condition|)
name|related
operator|.
name|addExternalId
argument_list|(
name|clazz
operator|.
name|getClassSuffix
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|owner
operator|.
name|getOwnerType
argument_list|()
operator|==
name|ExamOwner
operator|.
name|sOwnerTypeConfig
condition|)
block|{
name|InstrOfferingConfig
name|config
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|owner
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
name|related
operator|.
name|setSectionNumber
argument_list|(
name|config
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setInstruction
argument_list|(
name|MESSAGES
operator|.
name|colConfig
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|!=
literal|null
condition|)
name|related
operator|.
name|setInstruction
argument_list|(
name|related
operator|.
name|getInstruction
argument_list|()
operator|+
literal|" ("
operator|+
name|config
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|owner
operator|.
name|getOwnerType
argument_list|()
operator|==
name|ExamOwner
operator|.
name|sOwnerTypeCourse
condition|)
block|{
name|related
operator|.
name|setInstruction
argument_list|(
name|MESSAGES
operator|.
name|colCourse
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|owner
operator|.
name|getOwnerType
argument_list|()
operator|==
name|ExamOwner
operator|.
name|sOwnerTypeOffering
condition|)
block|{
name|related
operator|.
name|setInstruction
argument_list|(
name|MESSAGES
operator|.
name|colOffering
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|!=
literal|null
operator|&&
name|context
operator|.
name|hasPermission
argument_list|(
name|x
argument_list|,
name|Right
operator|.
name|ExaminationDetail
argument_list|)
condition|)
name|related
operator|.
name|setDetailPage
argument_list|(
literal|"examDetail.action?examId="
operator|+
name|x
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|DepartmentalInstructor
name|i
range|:
name|x
operator|.
name|getInstructors
argument_list|()
control|)
block|{
name|ContactInterface
name|instructor
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|instructor
operator|.
name|setFirstName
argument_list|(
name|i
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setMiddleName
argument_list|(
name|i
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setLastName
argument_list|(
name|i
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setAcademicTitle
argument_list|(
name|i
operator|.
name|getAcademicTitle
argument_list|()
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setExternalId
argument_list|(
name|i
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|instructor
operator|.
name|setEmail
argument_list|(
name|i
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addInstructor
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
block|}
name|ExamAssignmentInfo
name|assignment
init|=
literal|null
decl_stmt|;
name|ExamPeriod
name|period
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|proxy
operator|!=
literal|null
condition|)
block|{
name|assignment
operator|=
name|proxy
operator|.
name|getAssignmentInfo
argument_list|(
name|x
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|period
operator|=
operator|(
name|assignment
operator|==
literal|null
condition|?
literal|null
else|:
name|assignment
operator|.
name|getPeriod
argument_list|()
operator|)
expr_stmt|;
block|}
else|else
block|{
name|assignment
operator|=
operator|new
name|ExamAssignmentInfo
argument_list|(
name|x
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|period
operator|=
name|x
operator|.
name|getAssignedPeriod
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|period
operator|!=
literal|null
condition|)
block|{
name|related
operator|.
name|setDate
argument_list|(
name|period
operator|.
name|getStartDateLabel
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setDayOfYear
argument_list|(
name|period
operator|.
name|getDateOffset
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setStartSlot
argument_list|(
name|period
operator|.
name|getStartSlot
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|setEndSlot
argument_list|(
name|period
operator|.
name|getEndSlot
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|printOffset
init|=
operator|(
name|x
operator|.
name|getPrintOffset
argument_list|()
operator|==
literal|null
condition|?
literal|0
else|:
name|x
operator|.
name|getPrintOffset
argument_list|()
operator|)
decl_stmt|;
name|related
operator|.
name|setTime
argument_list|(
name|period
operator|.
name|getStartTimeLabel
argument_list|(
name|printOffset
argument_list|)
operator|+
literal|" - "
operator|+
name|period
operator|.
name|getEndTimeLabel
argument_list|(
name|x
operator|.
name|getLength
argument_list|()
argument_list|,
name|printOffset
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|!=
literal|null
operator|&&
name|assignment
operator|.
name|getRooms
argument_list|()
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ExamRoomInfo
name|r
range|:
name|assignment
operator|.
name|getRooms
argument_list|()
control|)
block|{
name|ResourceInterface
name|location
init|=
operator|new
name|ResourceInterface
argument_list|()
decl_stmt|;
name|location
operator|.
name|setType
argument_list|(
name|ResourceType
operator|.
name|ROOM
argument_list|)
expr_stmt|;
name|location
operator|.
name|setId
argument_list|(
name|r
operator|.
name|getLocationId
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setName
argument_list|(
name|r
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setSize
argument_list|(
name|r
operator|.
name|getCapacity
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setRoomType
argument_list|(
name|r
operator|.
name|getLocation
argument_list|()
operator|.
name|getRoomTypeLabel
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setBreakTime
argument_list|(
name|r
operator|.
name|getLocation
argument_list|()
operator|.
name|getEffectiveBreakTime
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setMessage
argument_list|(
name|r
operator|.
name|getLocation
argument_list|()
operator|.
name|getEventMessage
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setIgnoreRoomCheck
argument_list|(
name|r
operator|.
name|getLocation
argument_list|()
operator|.
name|isIgnoreRoomCheck
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setDisplayName
argument_list|(
name|r
operator|.
name|getLocation
argument_list|()
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setPartitionParentId
argument_list|(
name|r
operator|.
name|getLocation
argument_list|()
operator|.
name|getPartitionParentId
argument_list|()
argument_list|)
expr_stmt|;
name|related
operator|.
name|addLocation
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|conflicts
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|period
operator|!=
literal|null
condition|)
block|{
name|int
name|nrTravelSlotsClassEvent
init|=
name|ApplicationProperty
operator|.
name|ExaminationTravelTimeClass
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|int
name|nrTravelSlotsCourseEvent
init|=
name|ApplicationProperty
operator|.
name|ExaminationTravelTimeCourse
operator|.
name|intValue
argument_list|()
decl_stmt|;
name|TreeSet
argument_list|<
name|Event
argument_list|>
name|events
init|=
operator|new
name|TreeSet
argument_list|<
name|Event
argument_list|>
argument_list|()
decl_stmt|;
comment|// class events
for|for
control|(
name|int
name|t2
init|=
literal|0
init|;
name|t2
operator|<
name|ExamOwner
operator|.
name|sOwnerTypes
operator|.
name|length
condition|;
name|t2
operator|++
control|)
block|{
name|events
operator|.
name|addAll
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct m1.event"
operator|+
literal|" from StudentClassEnrollment s1, ClassEvent e1 inner join e1.meetings m1, Exam e2 inner join e2.owners o2, StudentClassEnrollment s2"
operator|+
literal|" where e2.uniqueId = :examId and e1.clazz = s1.clazz and s1.student = s2.student and s1.student.uniqueId = :studentId"
operator|+
name|ExaminationEnrollmentsBackend
operator|.
name|where
argument_list|(
name|t2
argument_list|,
literal|2
argument_list|)
operator|+
literal|" and m1.meetingDate = :meetingDate and m1.startPeriod< :endSlot and :startSlot< m1.stopPeriod"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examId"
argument_list|,
name|x
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"meetingDate"
argument_list|,
name|period
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|period
operator|.
name|getStartSlot
argument_list|()
operator|-
name|nrTravelSlotsClassEvent
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|period
operator|.
name|getEndSlot
argument_list|()
operator|+
name|nrTravelSlotsClassEvent
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// course events
for|for
control|(
name|int
name|t1
init|=
literal|0
init|;
name|t1
operator|<
name|ExamOwner
operator|.
name|sOwnerTypes
operator|.
name|length
condition|;
name|t1
operator|++
control|)
block|{
for|for
control|(
name|int
name|t2
init|=
literal|0
init|;
name|t2
operator|<
name|ExamOwner
operator|.
name|sOwnerTypes
operator|.
name|length
condition|;
name|t2
operator|++
control|)
block|{
name|events
operator|.
name|addAll
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct m1.event"
operator|+
literal|" from StudentClassEnrollment s1, CourseEvent e1 inner join e1.meetings m1 inner join e1.relatedCourses o1, Exam e2 inner join e2.owners o2, StudentClassEnrollment s2"
operator|+
literal|" where e2.uniqueId = :examId and s1.student = s2.student and s1.student.uniqueId = :studentId"
operator|+
name|ExaminationEnrollmentsBackend
operator|.
name|where
argument_list|(
name|t1
argument_list|,
literal|1
argument_list|)
operator|+
name|ExaminationEnrollmentsBackend
operator|.
name|where
argument_list|(
name|t2
argument_list|,
literal|2
argument_list|)
operator|+
literal|" and m1.meetingDate = :meetingDate and m1.startPeriod< :endSlot and :startSlot< m1.stopPeriod and e1.reqAttendance = true and m1.approvalStatus = 1"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examId"
argument_list|,
name|x
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"meetingDate"
argument_list|,
name|period
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|period
operator|.
name|getStartSlot
argument_list|()
operator|-
name|nrTravelSlotsCourseEvent
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|period
operator|.
name|getEndSlot
argument_list|()
operator|+
name|nrTravelSlotsCourseEvent
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// exam events of different type
for|for
control|(
name|int
name|t1
init|=
literal|0
init|;
name|t1
operator|<
name|ExamOwner
operator|.
name|sOwnerTypes
operator|.
name|length
condition|;
name|t1
operator|++
control|)
block|{
for|for
control|(
name|int
name|t2
init|=
literal|0
init|;
name|t2
operator|<
name|ExamOwner
operator|.
name|sOwnerTypes
operator|.
name|length
condition|;
name|t2
operator|++
control|)
block|{
name|events
operator|.
name|addAll
argument_list|(
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct m1.event"
operator|+
literal|" from StudentClassEnrollment s1, ExamEvent e1 inner join e1.meetings m1 inner join e1.exam.owners o1, Exam e2 inner join e2.owners o2, StudentClassEnrollment s2"
operator|+
literal|" where e2.uniqueId = :examId and s1.student = s2.student and s1.student.uniqueId = :studentId"
operator|+
name|ExaminationEnrollmentsBackend
operator|.
name|where
argument_list|(
name|t1
argument_list|,
literal|1
argument_list|)
operator|+
name|ExaminationEnrollmentsBackend
operator|.
name|where
argument_list|(
name|t2
argument_list|,
literal|2
argument_list|)
operator|+
literal|" and m1.meetingDate = :meetingDate and m1.startPeriod< :endSlot and :startSlot< m1.stopPeriod and e1.exam.examType.uniqueId != :examTypeId and m1.approvalStatus = 1"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examId"
argument_list|,
name|x
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"studentId"
argument_list|,
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setDate
argument_list|(
literal|"meetingDate"
argument_list|,
name|period
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"startSlot"
argument_list|,
name|period
operator|.
name|getStartSlot
argument_list|()
operator|-
name|nrTravelSlotsCourseEvent
argument_list|)
operator|.
name|setInteger
argument_list|(
literal|"endSlot"
argument_list|,
name|period
operator|.
name|getEndSlot
argument_list|()
operator|+
name|nrTravelSlotsCourseEvent
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"examTypeId"
argument_list|,
name|period
operator|.
name|getExamType
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Event
name|e
range|:
name|events
control|)
name|conflicts
operator|+=
operator|(
name|conflicts
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"<br>"
operator|)
operator|+
literal|"<span class='dc' title='"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|e
operator|.
name|getEventTypeAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|e
operator|.
name|getEventName
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|e
operator|.
name|getEventName
argument_list|()
argument_list|)
operator|+
literal|"</span>"
expr_stmt|;
block|}
if|if
condition|(
name|assignment
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|DirectConflict
name|conflict
range|:
name|assignment
operator|.
name|getDirectConflicts
argument_list|()
control|)
block|{
name|ExamAssignment
name|other
init|=
name|conflict
operator|.
name|getOtherExam
argument_list|()
decl_stmt|;
if|if
condition|(
name|other
operator|!=
literal|null
operator|&&
name|conflict
operator|.
name|getStudents
argument_list|()
operator|.
name|contains
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
block|{
for|for
control|(
name|ExamSectionInfo
name|section
range|:
name|other
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|section
operator|.
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
name|conflicts
operator|+=
operator|(
name|conflicts
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"<br>"
operator|)
operator|+
literal|"<span class='dc' title='"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
literal|"Direct "
operator|+
name|other
operator|.
name|getExamName
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|+
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
operator|+
literal|"</span>"
expr_stmt|;
block|}
block|}
for|for
control|(
name|BackToBackConflict
name|conflict
range|:
name|assignment
operator|.
name|getBackToBackConflicts
argument_list|()
control|)
block|{
name|ExamAssignment
name|other
init|=
name|conflict
operator|.
name|getOtherExam
argument_list|()
decl_stmt|;
if|if
condition|(
name|other
operator|!=
literal|null
operator|&&
name|conflict
operator|.
name|getStudents
argument_list|()
operator|.
name|contains
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
block|{
for|for
control|(
name|ExamSectionInfo
name|section
range|:
name|other
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|section
operator|.
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
name|conflicts
operator|+=
operator|(
name|conflicts
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"<br>"
operator|)
operator|+
literal|"<span class='b2b' title='"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
literal|"Back-To-Back "
operator|+
name|other
operator|.
name|getExamName
argument_list|()
argument_list|)
operator|+
literal|"'>"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|section
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|+
name|section
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
operator|+
literal|"</span>"
expr_stmt|;
block|}
block|}
for|for
control|(
name|MoreThanTwoADayConflict
name|conflict
range|:
name|assignment
operator|.
name|getMoreThanTwoADaysConflicts
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|conflict
operator|.
name|getStudents
argument_list|()
operator|.
name|contains
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
continue|continue;
name|String
name|name
init|=
literal|""
decl_stmt|,
name|first
init|=
literal|""
decl_stmt|,
name|next
init|=
literal|""
decl_stmt|;
for|for
control|(
name|ExamAssignment
name|other
range|:
name|conflict
operator|.
name|getOtherExams
argument_list|()
control|)
block|{
name|name
operator|+=
operator|(
name|name
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|", "
operator|)
operator|+
name|other
operator|.
name|getExamName
argument_list|()
expr_stmt|;
for|for
control|(
name|ExamSectionInfo
name|section
range|:
name|other
operator|.
name|getSections
argument_list|()
control|)
if|if
condition|(
name|section
operator|.
name|getStudentIds
argument_list|()
operator|.
name|contains
argument_list|(
name|request
operator|.
name|getStudentId
argument_list|()
argument_list|)
condition|)
block|{
name|String
name|course
init|=
name|section
operator|.
name|getSubject
argument_list|()
operator|+
literal|" "
operator|+
name|section
operator|.
name|getCourseNbr
argument_list|()
decl_stmt|;
if|if
condition|(
name|first
operator|.
name|isEmpty
argument_list|()
operator|||
name|course
operator|.
name|compareTo
argument_list|(
name|first
argument_list|)
operator|<
literal|0
condition|)
name|first
operator|=
name|course
expr_stmt|;
if|if
condition|(
name|owner
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|course
argument_list|)
operator|<
literal|0
operator|&&
operator|(
name|next
operator|.
name|isEmpty
argument_list|()
operator|||
name|course
operator|.
name|compareTo
argument_list|(
name|next
argument_list|)
operator|<
literal|0
operator|)
condition|)
name|next
operator|=
name|course
expr_stmt|;
block|}
block|}
name|conflicts
operator|+=
operator|(
name|conflicts
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"<br>"
operator|)
operator|+
literal|"<span class='m2d' title='"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
literal|">2 A Day "
operator|+
name|name
argument_list|)
operator|+
literal|"'>"
operator|+
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|next
operator|.
name|isEmpty
argument_list|()
condition|?
name|first
else|:
name|next
argument_list|)
operator|+
literal|"</span>"
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|conflicts
operator|.
name|isEmpty
argument_list|()
condition|)
name|related
operator|.
name|setConflicts
argument_list|(
name|conflicts
argument_list|)
expr_stmt|;
name|response
operator|.
name|addExam
argument_list|(
name|related
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

