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
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|ApplicationProperties
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
name|events
operator|.
name|EventEnrollmentsBackend
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
name|ExaminationEnrollmentsRpcRequest
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
name|client
operator|.
name|GwtRpcResponseList
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
name|GwtConstants
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
name|ClassAssignmentInterface
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
name|ClassAssignmentInterface
operator|.
name|Conflict
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
name|ClassAssignmentInterface
operator|.
name|Enrollment
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
name|Meeting
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
name|service
operator|.
name|SolverService
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|ExaminationEnrollmentsRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|ExaminationEnrollmentsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|ExaminationEnrollmentsRpcRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
argument_list|>
block|{
specifier|protected
specifier|static
name|GwtConstants
name|CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
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
name|GwtRpcResponseList
argument_list|<
name|Enrollment
argument_list|>
name|execute
parameter_list|(
name|ExaminationEnrollmentsRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
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
name|exam
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
name|exam
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|period
operator|=
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
expr_stmt|;
block|}
name|GwtRpcResponseList
argument_list|<
name|ClassAssignmentInterface
operator|.
name|Enrollment
argument_list|>
name|ret
init|=
name|EventEnrollmentsBackend
operator|.
name|convert
argument_list|(
name|exam
operator|.
name|getStudentClassEnrollments
argument_list|()
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Formats
operator|.
name|Format
argument_list|<
name|Date
argument_list|>
name|df
init|=
name|Formats
operator|.
name|getDateFormat
argument_list|(
name|Formats
operator|.
name|Pattern
operator|.
name|DATE_EXAM_PERIOD
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|List
argument_list|<
name|Meeting
argument_list|>
argument_list|>
name|conflicts
init|=
name|computeConflicts
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|period
argument_list|)
decl_stmt|;
if|if
condition|(
name|conflicts
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|enrollment
range|:
name|ret
control|)
block|{
name|List
argument_list|<
name|Meeting
argument_list|>
name|conf
init|=
name|conflicts
operator|.
name|get
argument_list|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|conf
operator|==
literal|null
condition|)
continue|continue;
name|Map
argument_list|<
name|Event
argument_list|,
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
argument_list|>
name|events
init|=
operator|new
name|HashMap
argument_list|<
name|Event
argument_list|,
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Meeting
name|m
range|:
name|conf
control|)
block|{
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
name|ms
init|=
name|events
operator|.
name|get
argument_list|(
name|m
operator|.
name|getEvent
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|ms
operator|==
literal|null
condition|)
block|{
name|ms
operator|=
operator|new
name|TreeSet
argument_list|<
name|Meeting
argument_list|>
argument_list|()
expr_stmt|;
name|events
operator|.
name|put
argument_list|(
name|m
operator|.
name|getEvent
argument_list|()
argument_list|,
name|ms
argument_list|)
expr_stmt|;
block|}
name|ms
operator|.
name|add
argument_list|(
name|m
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Event
name|confEvent
range|:
operator|new
name|TreeSet
argument_list|<
name|Event
argument_list|>
argument_list|(
name|events
operator|.
name|keySet
argument_list|()
argument_list|)
control|)
block|{
name|Conflict
name|conflict
init|=
operator|new
name|Conflict
argument_list|()
decl_stmt|;
name|conflict
operator|.
name|setName
argument_list|(
name|confEvent
operator|.
name|getEventName
argument_list|()
argument_list|)
expr_stmt|;
name|conflict
operator|.
name|setType
argument_list|(
name|confEvent
operator|.
name|getEventTypeAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|lastDate
init|=
literal|null
decl_stmt|,
name|lastTime
init|=
literal|null
decl_stmt|,
name|lastRoom
init|=
literal|null
decl_stmt|;
for|for
control|(
name|Meeting
name|m
range|:
name|events
operator|.
name|get
argument_list|(
name|confEvent
argument_list|)
control|)
block|{
name|String
name|date
init|=
name|df
operator|.
name|format
argument_list|(
name|m
operator|.
name|getMeetingDate
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastDate
operator|==
literal|null
condition|)
block|{
name|conflict
operator|.
name|setDate
argument_list|(
name|date
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|lastDate
operator|.
name|equals
argument_list|(
name|date
argument_list|)
condition|)
block|{
name|conflict
operator|.
name|setDate
argument_list|(
name|conflict
operator|.
name|getDate
argument_list|()
operator|+
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|conflict
operator|.
name|setDate
argument_list|(
name|conflict
operator|.
name|getDate
argument_list|()
operator|+
literal|"<br>"
operator|+
name|date
argument_list|)
expr_stmt|;
block|}
name|lastDate
operator|=
name|date
expr_stmt|;
name|String
name|time
init|=
name|m
operator|.
name|startTime
argument_list|()
operator|+
literal|" - "
operator|+
name|m
operator|.
name|stopTime
argument_list|()
decl_stmt|;
if|if
condition|(
name|lastTime
operator|==
literal|null
condition|)
block|{
name|conflict
operator|.
name|setTime
argument_list|(
name|time
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|lastTime
operator|.
name|equals
argument_list|(
name|time
argument_list|)
condition|)
block|{
name|conflict
operator|.
name|setTime
argument_list|(
name|conflict
operator|.
name|getTime
argument_list|()
operator|+
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|conflict
operator|.
name|setTime
argument_list|(
name|conflict
operator|.
name|getTime
argument_list|()
operator|+
literal|"<br>"
operator|+
name|time
argument_list|)
expr_stmt|;
block|}
name|lastTime
operator|=
name|time
expr_stmt|;
name|String
name|room
init|=
name|m
operator|.
name|getLocation
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|m
operator|.
name|getLocation
argument_list|()
operator|.
name|getLabel
argument_list|()
decl_stmt|;
if|if
condition|(
name|lastRoom
operator|==
literal|null
condition|)
block|{
name|conflict
operator|.
name|setRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|lastRoom
operator|.
name|equals
argument_list|(
name|room
argument_list|)
condition|)
block|{
name|conflict
operator|.
name|setRoom
argument_list|(
name|conflict
operator|.
name|getRoom
argument_list|()
operator|+
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|conflict
operator|.
name|setRoom
argument_list|(
name|conflict
operator|.
name|getRoom
argument_list|()
operator|+
literal|"<br>"
operator|+
name|room
argument_list|)
expr_stmt|;
block|}
name|lastRoom
operator|=
name|room
expr_stmt|;
block|}
name|conflict
operator|.
name|setStyle
argument_list|(
literal|"dc"
argument_list|)
expr_stmt|;
name|enrollment
operator|.
name|addConflict
argument_list|(
name|conflict
argument_list|)
expr_stmt|;
block|}
block|}
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
operator|==
literal|null
condition|)
continue|continue;
name|Conflict
name|conf
init|=
operator|new
name|Conflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setName
argument_list|(
name|other
operator|.
name|getExamName
argument_list|()
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setType
argument_list|(
literal|"Direct"
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setDate
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|other
operator|.
name|getPeriod
argument_list|()
operator|.
name|getStartDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setTime
argument_list|(
name|other
operator|.
name|getTime
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setRoom
argument_list|(
name|other
operator|.
name|getRoomsName
argument_list|(
literal|false
argument_list|,
literal|", "
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setStyle
argument_list|(
literal|"dc"
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|conflict
operator|.
name|getStudents
argument_list|()
control|)
block|{
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|enrollment
range|:
name|ret
control|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
name|studentId
condition|)
name|enrollment
operator|.
name|addConflict
argument_list|(
name|conf
argument_list|)
expr_stmt|;
block|}
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
name|Conflict
name|conf
init|=
operator|new
name|Conflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setName
argument_list|(
name|other
operator|.
name|getExamName
argument_list|()
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setType
argument_list|(
literal|"Back-To-Back"
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setDate
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|other
operator|.
name|getPeriod
argument_list|()
operator|.
name|getStartDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setTime
argument_list|(
name|other
operator|.
name|getTime
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setRoom
argument_list|(
name|other
operator|.
name|getRoomsName
argument_list|(
literal|false
argument_list|,
literal|", "
argument_list|)
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setStyle
argument_list|(
literal|"b2b"
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|conflict
operator|.
name|getStudents
argument_list|()
control|)
block|{
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|enrollment
range|:
name|ret
control|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
name|studentId
condition|)
name|enrollment
operator|.
name|addConflict
argument_list|(
name|conf
argument_list|)
expr_stmt|;
block|}
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
name|String
name|name
init|=
literal|null
decl_stmt|,
name|date
init|=
literal|null
decl_stmt|,
name|time
init|=
literal|null
decl_stmt|,
name|room
init|=
literal|null
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
if|if
condition|(
name|name
operator|==
literal|null
condition|)
block|{
name|name
operator|=
name|other
operator|.
name|getExamName
argument_list|()
expr_stmt|;
name|date
operator|=
name|df
operator|.
name|format
argument_list|(
name|other
operator|.
name|getPeriod
argument_list|()
operator|.
name|getStartDate
argument_list|()
argument_list|)
expr_stmt|;
name|time
operator|=
name|other
operator|.
name|getTime
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|room
operator|=
name|other
operator|.
name|getRoomsName
argument_list|(
literal|false
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|name
operator|+=
literal|"<br>"
operator|+
name|other
operator|.
name|getExamName
argument_list|()
expr_stmt|;
name|date
operator|+=
literal|"<br>"
operator|+
name|df
operator|.
name|format
argument_list|(
name|other
operator|.
name|getPeriod
argument_list|()
operator|.
name|getStartDate
argument_list|()
argument_list|)
expr_stmt|;
name|time
operator|+=
literal|"<br>"
operator|+
name|other
operator|.
name|getTime
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|room
operator|+=
literal|"<br>"
operator|+
name|other
operator|.
name|getRoomsName
argument_list|(
literal|false
argument_list|,
literal|", "
argument_list|)
expr_stmt|;
block|}
block|}
name|Conflict
name|conf
init|=
operator|new
name|Conflict
argument_list|()
decl_stmt|;
name|conf
operator|.
name|setName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setType
argument_list|(
literal|"&gt;2 A Day"
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setDate
argument_list|(
name|date
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setTime
argument_list|(
name|time
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setRoom
argument_list|(
name|room
argument_list|)
expr_stmt|;
name|conf
operator|.
name|setStyle
argument_list|(
literal|"m2d"
argument_list|)
expr_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|conflict
operator|.
name|getStudents
argument_list|()
control|)
block|{
for|for
control|(
name|ClassAssignmentInterface
operator|.
name|Enrollment
name|enrollment
range|:
name|ret
control|)
block|{
if|if
condition|(
name|enrollment
operator|.
name|getStudent
argument_list|()
operator|.
name|getId
argument_list|()
operator|==
name|studentId
condition|)
name|enrollment
operator|.
name|addConflict
argument_list|(
name|conf
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|private
specifier|static
name|String
name|where
parameter_list|(
name|int
name|type
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
switch|switch
condition|(
name|type
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
return|return
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerType = "
operator|+
name|type
operator|+
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerId = s"
operator|+
name|idx
operator|+
literal|".clazz.uniqueId"
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
return|return
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerType = "
operator|+
name|type
operator|+
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerId = s"
operator|+
name|idx
operator|+
literal|".clazz.schedulingSubpart.instrOfferingConfig.uniqueId"
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
return|return
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerType = "
operator|+
name|type
operator|+
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerId = s"
operator|+
name|idx
operator|+
literal|".courseOffering.uniqueId"
return|;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
return|return
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerType = "
operator|+
name|type
operator|+
literal|" and o"
operator|+
name|idx
operator|+
literal|".ownerId = s"
operator|+
name|idx
operator|+
literal|".courseOffering.instructionalOffering.uniqueId"
return|;
default|default:
return|return
literal|""
return|;
block|}
block|}
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|List
argument_list|<
name|Meeting
argument_list|>
argument_list|>
name|computeConflicts
parameter_list|(
name|Long
name|examId
parameter_list|,
name|ExamPeriod
name|period
parameter_list|)
block|{
if|if
condition|(
name|period
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Map
argument_list|<
name|Long
argument_list|,
name|List
argument_list|<
name|Meeting
argument_list|>
argument_list|>
name|conflicts
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|List
argument_list|<
name|Meeting
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|EventDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|int
name|nrTravelSlotsClassEvent
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.eventConflicts.travelTime.classEvent"
argument_list|,
literal|"6"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|nrTravelSlotsCourseEvent
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.exam.eventConflicts.travelTime.courseEvent"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
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
for|for
control|(
name|Object
index|[]
name|o
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s1.student.uniqueId, m1"
operator|+
literal|" from StudentClassEnrollment s1, ClassEvent e1 inner join e1.meetings m1, Exam e2 inner join e2.owners o2, StudentClassEnrollment s2"
operator|+
literal|" where e2.uniqueId = :examId and e1.clazz = s1.clazz and s1.student = s2.student"
operator|+
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
name|examId
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
control|)
block|{
name|Long
name|studentId
init|=
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|Meeting
name|meeting
init|=
operator|(
name|Meeting
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
name|List
argument_list|<
name|Meeting
argument_list|>
name|meetings
init|=
name|conflicts
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|meetings
operator|==
literal|null
condition|)
block|{
name|meetings
operator|=
operator|new
name|ArrayList
argument_list|<
name|Meeting
argument_list|>
argument_list|()
expr_stmt|;
name|conflicts
operator|.
name|put
argument_list|(
name|studentId
argument_list|,
name|meetings
argument_list|)
expr_stmt|;
block|}
name|meetings
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
block|}
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
for|for
control|(
name|Object
index|[]
name|o
range|:
operator|(
name|List
argument_list|<
name|Object
index|[]
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select s1.student.uniqueId, m1"
operator|+
literal|" from StudentClassEnrollment s1, CourseEvent e1 inner join e1.meetings m1 inner join e1.relatedCourses o1, Exam e2 inner join e2.owners o2, StudentClassEnrollment s2"
operator|+
literal|" where e2.uniqueId = :examId and s1.student = s2.student"
operator|+
name|where
argument_list|(
name|t1
argument_list|,
literal|1
argument_list|)
operator|+
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
name|examId
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
control|)
block|{
name|Long
name|studentId
init|=
operator|(
name|Long
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
name|Meeting
name|meeting
init|=
operator|(
name|Meeting
operator|)
name|o
index|[
literal|1
index|]
decl_stmt|;
name|List
argument_list|<
name|Meeting
argument_list|>
name|meetings
init|=
name|conflicts
operator|.
name|get
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
if|if
condition|(
name|meetings
operator|==
literal|null
condition|)
block|{
name|meetings
operator|=
operator|new
name|ArrayList
argument_list|<
name|Meeting
argument_list|>
argument_list|()
expr_stmt|;
name|conflicts
operator|.
name|put
argument_list|(
name|studentId
argument_list|,
name|meetings
argument_list|)
expr_stmt|;
block|}
name|meetings
operator|.
name|add
argument_list|(
name|meeting
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|conflicts
return|;
block|}
block|}
end_class

end_unit

