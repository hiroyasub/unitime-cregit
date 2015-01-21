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
name|ExamConflict
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
name|ExamType
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
name|Session
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseExam
extends|extends
name|PreferenceGroup
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
name|String
name|iName
decl_stmt|;
specifier|private
name|String
name|iNote
decl_stmt|;
specifier|private
name|Integer
name|iLength
decl_stmt|;
specifier|private
name|Integer
name|iExamSize
decl_stmt|;
specifier|private
name|Integer
name|iPrintOffset
decl_stmt|;
specifier|private
name|Integer
name|iMaxNbrRooms
decl_stmt|;
specifier|private
name|Integer
name|iSeatingType
decl_stmt|;
specifier|private
name|String
name|iAssignedPreference
decl_stmt|;
specifier|private
name|Integer
name|iAvgPeriod
decl_stmt|;
specifier|private
name|Long
name|iUniqueIdRolledForwardFrom
decl_stmt|;
specifier|private
name|Session
name|iSession
decl_stmt|;
specifier|private
name|ExamPeriod
name|iAssignedPeriod
decl_stmt|;
specifier|private
name|ExamType
name|iExamType
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ExamOwner
argument_list|>
name|iOwners
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Location
argument_list|>
name|iAssignedRooms
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|iInstructors
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|ExamConflict
argument_list|>
name|iConflicts
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_NOTE
init|=
literal|"note"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_LENGTH
init|=
literal|"length"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXAM_SIZE
init|=
literal|"examSize"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_PRINT_OFFSET
init|=
literal|"printOffset"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_MAX_NBR_ROOMS
init|=
literal|"maxNbrRooms"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SEATING_TYPE
init|=
literal|"seatingType"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ASSIGNED_PREF
init|=
literal|"assignedPreference"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_AVG_PERIOD
init|=
literal|"avgPeriod"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_UID_ROLLED_FWD_FROM
init|=
literal|"uniqueIdRolledForwardFrom"
decl_stmt|;
specifier|public
name|BaseExam
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseExam
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
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getNote
parameter_list|()
block|{
return|return
name|iNote
return|;
block|}
specifier|public
name|void
name|setNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLength
parameter_list|()
block|{
return|return
name|iLength
return|;
block|}
specifier|public
name|void
name|setLength
parameter_list|(
name|Integer
name|length
parameter_list|)
block|{
name|iLength
operator|=
name|length
expr_stmt|;
block|}
specifier|public
name|Integer
name|getExamSize
parameter_list|()
block|{
return|return
name|iExamSize
return|;
block|}
specifier|public
name|void
name|setExamSize
parameter_list|(
name|Integer
name|examSize
parameter_list|)
block|{
name|iExamSize
operator|=
name|examSize
expr_stmt|;
block|}
specifier|public
name|Integer
name|getPrintOffset
parameter_list|()
block|{
return|return
name|iPrintOffset
return|;
block|}
specifier|public
name|void
name|setPrintOffset
parameter_list|(
name|Integer
name|printOffset
parameter_list|)
block|{
name|iPrintOffset
operator|=
name|printOffset
expr_stmt|;
block|}
specifier|public
name|Integer
name|getMaxNbrRooms
parameter_list|()
block|{
return|return
name|iMaxNbrRooms
return|;
block|}
specifier|public
name|void
name|setMaxNbrRooms
parameter_list|(
name|Integer
name|maxNbrRooms
parameter_list|)
block|{
name|iMaxNbrRooms
operator|=
name|maxNbrRooms
expr_stmt|;
block|}
specifier|public
name|Integer
name|getSeatingType
parameter_list|()
block|{
return|return
name|iSeatingType
return|;
block|}
specifier|public
name|void
name|setSeatingType
parameter_list|(
name|Integer
name|seatingType
parameter_list|)
block|{
name|iSeatingType
operator|=
name|seatingType
expr_stmt|;
block|}
specifier|public
name|String
name|getAssignedPreference
parameter_list|()
block|{
return|return
name|iAssignedPreference
return|;
block|}
specifier|public
name|void
name|setAssignedPreference
parameter_list|(
name|String
name|assignedPreference
parameter_list|)
block|{
name|iAssignedPreference
operator|=
name|assignedPreference
expr_stmt|;
block|}
specifier|public
name|Integer
name|getAvgPeriod
parameter_list|()
block|{
return|return
name|iAvgPeriod
return|;
block|}
specifier|public
name|void
name|setAvgPeriod
parameter_list|(
name|Integer
name|avgPeriod
parameter_list|)
block|{
name|iAvgPeriod
operator|=
name|avgPeriod
expr_stmt|;
block|}
specifier|public
name|Long
name|getUniqueIdRolledForwardFrom
parameter_list|()
block|{
return|return
name|iUniqueIdRolledForwardFrom
return|;
block|}
specifier|public
name|void
name|setUniqueIdRolledForwardFrom
parameter_list|(
name|Long
name|uniqueIdRolledForwardFrom
parameter_list|)
block|{
name|iUniqueIdRolledForwardFrom
operator|=
name|uniqueIdRolledForwardFrom
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
name|ExamPeriod
name|getAssignedPeriod
parameter_list|()
block|{
return|return
name|iAssignedPeriod
return|;
block|}
specifier|public
name|void
name|setAssignedPeriod
parameter_list|(
name|ExamPeriod
name|assignedPeriod
parameter_list|)
block|{
name|iAssignedPeriod
operator|=
name|assignedPeriod
expr_stmt|;
block|}
specifier|public
name|ExamType
name|getExamType
parameter_list|()
block|{
return|return
name|iExamType
return|;
block|}
specifier|public
name|void
name|setExamType
parameter_list|(
name|ExamType
name|examType
parameter_list|)
block|{
name|iExamType
operator|=
name|examType
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ExamOwner
argument_list|>
name|getOwners
parameter_list|()
block|{
return|return
name|iOwners
return|;
block|}
specifier|public
name|void
name|setOwners
parameter_list|(
name|Set
argument_list|<
name|ExamOwner
argument_list|>
name|owners
parameter_list|)
block|{
name|iOwners
operator|=
name|owners
expr_stmt|;
block|}
specifier|public
name|void
name|addToowners
parameter_list|(
name|ExamOwner
name|examOwner
parameter_list|)
block|{
if|if
condition|(
name|iOwners
operator|==
literal|null
condition|)
name|iOwners
operator|=
operator|new
name|HashSet
argument_list|<
name|ExamOwner
argument_list|>
argument_list|()
expr_stmt|;
name|iOwners
operator|.
name|add
argument_list|(
name|examOwner
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Location
argument_list|>
name|getAssignedRooms
parameter_list|()
block|{
return|return
name|iAssignedRooms
return|;
block|}
specifier|public
name|void
name|setAssignedRooms
parameter_list|(
name|Set
argument_list|<
name|Location
argument_list|>
name|assignedRooms
parameter_list|)
block|{
name|iAssignedRooms
operator|=
name|assignedRooms
expr_stmt|;
block|}
specifier|public
name|void
name|addToassignedRooms
parameter_list|(
name|Location
name|location
parameter_list|)
block|{
if|if
condition|(
name|iAssignedRooms
operator|==
literal|null
condition|)
name|iAssignedRooms
operator|=
operator|new
name|HashSet
argument_list|<
name|Location
argument_list|>
argument_list|()
expr_stmt|;
name|iAssignedRooms
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|getInstructors
parameter_list|()
block|{
return|return
name|iInstructors
return|;
block|}
specifier|public
name|void
name|setInstructors
parameter_list|(
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
parameter_list|)
block|{
name|iInstructors
operator|=
name|instructors
expr_stmt|;
block|}
specifier|public
name|void
name|addToinstructors
parameter_list|(
name|DepartmentalInstructor
name|departmentalInstructor
parameter_list|)
block|{
if|if
condition|(
name|iInstructors
operator|==
literal|null
condition|)
name|iInstructors
operator|=
operator|new
name|HashSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
argument_list|()
expr_stmt|;
name|iInstructors
operator|.
name|add
argument_list|(
name|departmentalInstructor
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|ExamConflict
argument_list|>
name|getConflicts
parameter_list|()
block|{
return|return
name|iConflicts
return|;
block|}
specifier|public
name|void
name|setConflicts
parameter_list|(
name|Set
argument_list|<
name|ExamConflict
argument_list|>
name|conflicts
parameter_list|)
block|{
name|iConflicts
operator|=
name|conflicts
expr_stmt|;
block|}
specifier|public
name|void
name|addToconflicts
parameter_list|(
name|ExamConflict
name|examConflict
parameter_list|)
block|{
if|if
condition|(
name|iConflicts
operator|==
literal|null
condition|)
name|iConflicts
operator|=
operator|new
name|HashSet
argument_list|<
name|ExamConflict
argument_list|>
argument_list|()
expr_stmt|;
name|iConflicts
operator|.
name|add
argument_list|(
name|examConflict
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
name|Exam
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
name|Exam
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
name|Exam
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
literal|"Exam["
operator|+
name|getUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|getName
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
literal|"Exam["
operator|+
literal|"\n	AssignedPeriod: "
operator|+
name|getAssignedPeriod
argument_list|()
operator|+
literal|"\n	AssignedPreference: "
operator|+
name|getAssignedPreference
argument_list|()
operator|+
literal|"\n	AvgPeriod: "
operator|+
name|getAvgPeriod
argument_list|()
operator|+
literal|"\n	ExamSize: "
operator|+
name|getExamSize
argument_list|()
operator|+
literal|"\n	ExamType: "
operator|+
name|getExamType
argument_list|()
operator|+
literal|"\n	Length: "
operator|+
name|getLength
argument_list|()
operator|+
literal|"\n	MaxNbrRooms: "
operator|+
name|getMaxNbrRooms
argument_list|()
operator|+
literal|"\n	Name: "
operator|+
name|getName
argument_list|()
operator|+
literal|"\n	Note: "
operator|+
name|getNote
argument_list|()
operator|+
literal|"\n	PrintOffset: "
operator|+
name|getPrintOffset
argument_list|()
operator|+
literal|"\n	SeatingType: "
operator|+
name|getSeatingType
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
literal|"\n	UniqueIdRolledForwardFrom: "
operator|+
name|getUniqueIdRolledForwardFrom
argument_list|()
operator|+
literal|"]"
return|;
block|}
block|}
end_class

end_unit

