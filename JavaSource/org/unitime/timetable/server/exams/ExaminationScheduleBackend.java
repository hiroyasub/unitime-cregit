begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
literal|"["
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|+
literal|"]"
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
literal|"examDetail.do?examId="
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
if|if
condition|(
name|proxy
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|x
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|ExamPeriod
name|period
init|=
name|x
operator|.
name|getAssignedPeriod
argument_list|()
decl_stmt|;
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
for|for
control|(
name|Location
name|r
range|:
name|x
operator|.
name|getAssignedRooms
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
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|location
operator|.
name|setName
argument_list|(
name|r
operator|.
name|getLabel
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
name|getEventMessage
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
else|else
block|{
name|ExamAssignment
name|assignment
init|=
name|proxy
operator|.
name|getAssignment
argument_list|(
name|x
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|assignment
operator|!=
literal|null
operator|&&
name|assignment
operator|.
name|getPeriod
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|ExamPeriod
name|period
init|=
name|assignment
operator|.
name|getPeriod
argument_list|()
decl_stmt|;
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
name|related
operator|.
name|addLocation
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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

