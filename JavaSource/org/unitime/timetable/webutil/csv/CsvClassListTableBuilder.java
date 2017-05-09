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
name|webutil
operator|.
name|csv
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|CSVFile
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
name|CSVFile
operator|.
name|CSVField
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
name|CommonValues
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
name|UserProperty
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
name|form
operator|.
name|ClassListForm
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
name|InstructionalMethod
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
name|PreferenceGroup
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
name|SchedulingSubpart
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
name|SubjectArea
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
name|comparators
operator|.
name|SchedulingSubpartComparator
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
name|UserContext
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
name|CachedClassAssignmentProxy
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
name|ClassAssignmentProxy
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
name|ExamAssignmentProxy
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CsvClassListTableBuilder
extends|extends
name|CsvInstructionalOfferingTableBuilder
block|{
specifier|public
name|CsvClassListTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|String
name|additionalNote
parameter_list|()
block|{
return|return
operator|(
operator|new
name|String
argument_list|()
operator|)
return|;
block|}
specifier|protected
name|String
name|labelForTable
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|subjectArea
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" - "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|subjectArea
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|additionalNote
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
name|sb
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|csvTableForClasses
parameter_list|(
name|PrintWriter
name|out
parameter_list|,
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|ClassListForm
name|form
parameter_list|,
name|SessionContext
name|context
parameter_list|)
throws|throws
name|Exception
block|{
name|setVisibleColumns
argument_list|(
name|form
argument_list|)
expr_stmt|;
name|TreeSet
name|classes
init|=
operator|(
name|TreeSet
operator|)
name|form
operator|.
name|getClasses
argument_list|()
decl_stmt|;
if|if
condition|(
name|isShowTimetable
argument_list|()
condition|)
block|{
name|boolean
name|hasTimetable
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|ClassAssignments
argument_list|)
operator|&&
name|classAssignment
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|classAssignment
operator|instanceof
name|CachedClassAssignmentProxy
condition|)
block|{
operator|(
operator|(
name|CachedClassAssignmentProxy
operator|)
name|classAssignment
operator|)
operator|.
name|setCache
argument_list|(
name|classes
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|classes
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|o
index|[
literal|0
index|]
decl_stmt|;
if|if
condition|(
name|classAssignment
operator|.
name|getAssignment
argument_list|(
name|clazz
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|hasTimetable
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
name|setDisplayTimetable
argument_list|(
name|hasTimetable
argument_list|)
expr_stmt|;
block|}
name|setUserSettings
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|isShowExam
argument_list|()
condition|)
name|setShowExamTimetable
argument_list|(
name|examAssignment
operator|!=
literal|null
operator|||
name|Exam
operator|.
name|hasTimetable
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iFile
operator|=
operator|new
name|CSVFile
argument_list|()
expr_stmt|;
name|csvBuildTableHeader
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|ct
init|=
literal|0
decl_stmt|;
name|Iterator
name|it
init|=
name|classes
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
name|prevLabel
init|=
literal|null
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|Class_
name|c
init|=
operator|(
name|Class_
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
name|csvBuildClassRow
argument_list|(
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
operator|++
name|ct
argument_list|,
name|co
argument_list|,
name|c
argument_list|,
literal|""
argument_list|,
name|context
argument_list|,
name|prevLabel
argument_list|)
expr_stmt|;
name|prevLabel
operator|=
name|c
operator|.
name|getClassLabel
argument_list|(
name|co
argument_list|)
expr_stmt|;
block|}
name|save
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|CSVField
name|csvBuildPrefGroupLabel
parameter_list|(
name|CourseOffering
name|co
parameter_list|,
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|String
name|indentSpaces
parameter_list|,
name|boolean
name|isEditable
parameter_list|,
name|String
name|prevLabel
parameter_list|)
block|{
if|if
condition|(
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
name|String
name|label
init|=
name|prefGroup
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Class_
name|aClass
init|=
operator|(
name|Class_
operator|)
name|prefGroup
decl_stmt|;
name|label
operator|=
name|aClass
operator|.
name|getClassLabel
argument_list|(
name|co
argument_list|)
expr_stmt|;
if|if
condition|(
name|prevLabel
operator|!=
literal|null
operator|&&
name|label
operator|.
name|equals
argument_list|(
name|prevLabel
argument_list|)
condition|)
block|{
name|label
operator|=
literal|""
expr_stmt|;
block|}
name|CSVField
name|cell
init|=
name|createCell
argument_list|()
decl_stmt|;
name|addText
argument_list|(
name|cell
argument_list|,
name|indentSpaces
operator|+
name|label
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|InstructionalMethod
name|im
init|=
name|aClass
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalMethod
argument_list|()
decl_stmt|;
if|if
condition|(
name|aClass
operator|.
name|isCancelled
argument_list|()
condition|)
block|{
if|if
condition|(
name|im
operator|!=
literal|null
condition|)
name|addText
argument_list|(
name|cell
argument_list|,
literal|" ("
operator|+
name|MSG
operator|.
name|statusCancelled
argument_list|()
operator|+
literal|", "
operator|+
name|im
operator|.
name|getReference
argument_list|()
operator|+
literal|")"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
else|else
name|addText
argument_list|(
name|cell
argument_list|,
literal|" ("
operator|+
name|MSG
operator|.
name|statusCancelled
argument_list|()
operator|+
literal|")"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|im
operator|!=
literal|null
condition|)
name|addText
argument_list|(
name|cell
argument_list|,
literal|" ("
operator|+
name|im
operator|.
name|getReference
argument_list|()
operator|+
literal|")"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
return|return
name|cell
return|;
block|}
else|else
return|return
name|super
operator|.
name|csvBuildPrefGroupLabel
argument_list|(
name|co
argument_list|,
name|prefGroup
argument_list|,
name|indentSpaces
argument_list|,
name|isEditable
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|protected
name|TreeSet
name|getExams
parameter_list|(
name|Class_
name|clazz
parameter_list|)
block|{
comment|//exams directly attached to the given class
name|TreeSet
name|ret
init|=
operator|new
name|TreeSet
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeClass
argument_list|,
name|clazz
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
comment|//check whether the given class is of the first subpart of the config
name|SchedulingSubpart
name|subpart
init|=
name|clazz
operator|.
name|getSchedulingSubpart
argument_list|()
decl_stmt|;
if|if
condition|(
name|subpart
operator|.
name|getParentSubpart
argument_list|()
operator|!=
literal|null
condition|)
return|return
name|ret
return|;
name|InstrOfferingConfig
name|config
init|=
name|subpart
operator|.
name|getInstrOfferingConfig
argument_list|()
decl_stmt|;
name|SchedulingSubpartComparator
name|cmp
init|=
operator|new
name|SchedulingSubpartComparator
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|config
operator|.
name|getSchedulingSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SchedulingSubpart
name|s
init|=
operator|(
name|SchedulingSubpart
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|cmp
operator|.
name|compare
argument_list|(
name|s
argument_list|,
name|subpart
argument_list|)
operator|<
literal|0
condition|)
return|return
name|ret
return|;
block|}
name|InstructionalOffering
name|offering
init|=
name|config
operator|.
name|getInstructionalOffering
argument_list|()
decl_stmt|;
comment|//check passed -- add config/offering/course exams to the class exams
name|ret
operator|.
name|addAll
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeConfig
argument_list|,
name|config
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addAll
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeOffering
argument_list|,
name|offering
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|offering
operator|.
name|getCourseOfferings
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ret
operator|.
name|addAll
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|ExamOwner
operator|.
name|sOwnerTypeCourse
argument_list|,
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|protected
name|CSVField
name|csvBuildNote
parameter_list|(
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|boolean
name|isEditable
parameter_list|,
name|UserContext
name|user
parameter_list|)
block|{
name|CSVField
name|cell
init|=
name|createCell
argument_list|()
decl_stmt|;
if|if
condition|(
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
name|Class_
name|c
init|=
operator|(
name|Class_
operator|)
name|prefGroup
decl_stmt|;
name|String
name|offeringNote
init|=
name|c
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getInstrOfferingConfig
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getNotes
argument_list|()
decl_stmt|;
name|String
name|classNote
init|=
name|c
operator|.
name|getNotes
argument_list|()
decl_stmt|;
name|String
name|note
init|=
operator|(
name|offeringNote
operator|==
literal|null
operator|||
name|offeringNote
operator|.
name|isEmpty
argument_list|()
condition|?
name|classNote
else|:
name|offeringNote
operator|+
operator|(
name|classNote
operator|==
literal|null
operator|||
name|classNote
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"\n"
operator|+
name|classNote
operator|)
operator|)
decl_stmt|;
if|if
condition|(
name|note
operator|!=
literal|null
operator|&&
operator|!
name|note
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|note
operator|.
name|length
argument_list|()
operator|<=
literal|30
operator|||
name|user
operator|==
literal|null
operator|||
name|CommonValues
operator|.
name|NoteAsFullText
operator|.
name|eq
argument_list|(
name|user
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|ManagerNoteDisplay
argument_list|)
argument_list|)
condition|)
block|{
name|addText
argument_list|(
name|cell
argument_list|,
name|note
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|classNote
operator|!=
literal|null
operator|&&
operator|!
name|classNote
operator|.
name|isEmpty
argument_list|()
condition|)
name|note
operator|=
name|classNote
expr_stmt|;
name|addText
argument_list|(
name|cell
argument_list|,
operator|(
name|note
operator|.
name|length
argument_list|()
operator|<=
literal|30
condition|?
name|note
else|:
name|note
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|30
argument_list|)
operator|+
literal|"..."
operator|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|cell
return|;
block|}
block|}
end_class

end_unit

