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
name|Building
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
name|ClassDurationType
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
name|DatePattern
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
name|Department
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
name|DepartmentStatusType
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
name|SubjectArea
import|;
end_import

begin_comment
comment|/**  * Do not change this class. It has been automatically generated using ant create-model.  * @see org.unitime.commons.ant.CreateBaseModelFromXml  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSession
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
name|iAcademicInitiative
decl_stmt|;
specifier|private
name|String
name|iAcademicYear
decl_stmt|;
specifier|private
name|String
name|iAcademicTerm
decl_stmt|;
specifier|private
name|Date
name|iSessionBeginDateTime
decl_stmt|;
specifier|private
name|Date
name|iClassesEndDateTime
decl_stmt|;
specifier|private
name|Date
name|iSessionEndDateTime
decl_stmt|;
specifier|private
name|Date
name|iExamBeginDate
decl_stmt|;
specifier|private
name|Date
name|iEventBeginDate
decl_stmt|;
specifier|private
name|Date
name|iEventEndDate
decl_stmt|;
specifier|private
name|String
name|iHolidays
decl_stmt|;
specifier|private
name|Integer
name|iLastWeekToEnroll
decl_stmt|;
specifier|private
name|Integer
name|iLastWeekToChange
decl_stmt|;
specifier|private
name|Integer
name|iLastWeekToDrop
decl_stmt|;
specifier|private
name|DepartmentStatusType
name|iStatusType
decl_stmt|;
specifier|private
name|DatePattern
name|iDefaultDatePattern
decl_stmt|;
specifier|private
name|StudentSectioningStatus
name|iDefaultSectioningStatus
decl_stmt|;
specifier|private
name|ClassDurationType
name|iDefaultClassDurationType
decl_stmt|;
specifier|private
name|InstructionalMethod
name|iDefaultInstructionalMethod
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|SubjectArea
argument_list|>
name|iSubjectAreas
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Building
argument_list|>
name|iBuildings
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Department
argument_list|>
name|iDepartments
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Location
argument_list|>
name|iRooms
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|InstructionalOffering
argument_list|>
name|iInstructionalOfferings
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ACADEMIC_INITIATIVE
init|=
literal|"academicInitiative"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ACADEMIC_YEAR
init|=
literal|"academicYear"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_ACADEMIC_TERM
init|=
literal|"academicTerm"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SESSION_BEGIN_DATE_TIME
init|=
literal|"sessionBeginDateTime"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_CLASSES_END_DATE_TIME
init|=
literal|"classesEndDateTime"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_SESSION_END_DATE_TIME
init|=
literal|"sessionEndDateTime"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EXAM_BEGIN_DATE
init|=
literal|"examBeginDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EVENT_BEGIN_DATE
init|=
literal|"eventBeginDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_EVENT_END_DATE
init|=
literal|"eventEndDate"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_HOLIDAYS
init|=
literal|"holidays"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_WK_ENROLL
init|=
literal|"lastWeekToEnroll"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_WK_CHANGE
init|=
literal|"lastWeekToChange"
decl_stmt|;
specifier|public
specifier|static
name|String
name|PROP_WK_DROP
init|=
literal|"lastWeekToDrop"
decl_stmt|;
specifier|public
name|BaseSession
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
specifier|public
name|BaseSession
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
name|getAcademicInitiative
parameter_list|()
block|{
return|return
name|iAcademicInitiative
return|;
block|}
specifier|public
name|void
name|setAcademicInitiative
parameter_list|(
name|String
name|academicInitiative
parameter_list|)
block|{
name|iAcademicInitiative
operator|=
name|academicInitiative
expr_stmt|;
block|}
specifier|public
name|String
name|getAcademicYear
parameter_list|()
block|{
return|return
name|iAcademicYear
return|;
block|}
specifier|public
name|void
name|setAcademicYear
parameter_list|(
name|String
name|academicYear
parameter_list|)
block|{
name|iAcademicYear
operator|=
name|academicYear
expr_stmt|;
block|}
specifier|public
name|String
name|getAcademicTerm
parameter_list|()
block|{
return|return
name|iAcademicTerm
return|;
block|}
specifier|public
name|void
name|setAcademicTerm
parameter_list|(
name|String
name|academicTerm
parameter_list|)
block|{
name|iAcademicTerm
operator|=
name|academicTerm
expr_stmt|;
block|}
specifier|public
name|Date
name|getSessionBeginDateTime
parameter_list|()
block|{
return|return
name|iSessionBeginDateTime
return|;
block|}
specifier|public
name|void
name|setSessionBeginDateTime
parameter_list|(
name|Date
name|sessionBeginDateTime
parameter_list|)
block|{
name|iSessionBeginDateTime
operator|=
name|sessionBeginDateTime
expr_stmt|;
block|}
specifier|public
name|Date
name|getClassesEndDateTime
parameter_list|()
block|{
return|return
name|iClassesEndDateTime
return|;
block|}
specifier|public
name|void
name|setClassesEndDateTime
parameter_list|(
name|Date
name|classesEndDateTime
parameter_list|)
block|{
name|iClassesEndDateTime
operator|=
name|classesEndDateTime
expr_stmt|;
block|}
specifier|public
name|Date
name|getSessionEndDateTime
parameter_list|()
block|{
return|return
name|iSessionEndDateTime
return|;
block|}
specifier|public
name|void
name|setSessionEndDateTime
parameter_list|(
name|Date
name|sessionEndDateTime
parameter_list|)
block|{
name|iSessionEndDateTime
operator|=
name|sessionEndDateTime
expr_stmt|;
block|}
specifier|public
name|Date
name|getExamBeginDate
parameter_list|()
block|{
return|return
name|iExamBeginDate
return|;
block|}
specifier|public
name|void
name|setExamBeginDate
parameter_list|(
name|Date
name|examBeginDate
parameter_list|)
block|{
name|iExamBeginDate
operator|=
name|examBeginDate
expr_stmt|;
block|}
specifier|public
name|Date
name|getEventBeginDate
parameter_list|()
block|{
return|return
name|iEventBeginDate
return|;
block|}
specifier|public
name|void
name|setEventBeginDate
parameter_list|(
name|Date
name|eventBeginDate
parameter_list|)
block|{
name|iEventBeginDate
operator|=
name|eventBeginDate
expr_stmt|;
block|}
specifier|public
name|Date
name|getEventEndDate
parameter_list|()
block|{
return|return
name|iEventEndDate
return|;
block|}
specifier|public
name|void
name|setEventEndDate
parameter_list|(
name|Date
name|eventEndDate
parameter_list|)
block|{
name|iEventEndDate
operator|=
name|eventEndDate
expr_stmt|;
block|}
specifier|public
name|String
name|getHolidays
parameter_list|()
block|{
return|return
name|iHolidays
return|;
block|}
specifier|public
name|void
name|setHolidays
parameter_list|(
name|String
name|holidays
parameter_list|)
block|{
name|iHolidays
operator|=
name|holidays
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLastWeekToEnroll
parameter_list|()
block|{
return|return
name|iLastWeekToEnroll
return|;
block|}
specifier|public
name|void
name|setLastWeekToEnroll
parameter_list|(
name|Integer
name|lastWeekToEnroll
parameter_list|)
block|{
name|iLastWeekToEnroll
operator|=
name|lastWeekToEnroll
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLastWeekToChange
parameter_list|()
block|{
return|return
name|iLastWeekToChange
return|;
block|}
specifier|public
name|void
name|setLastWeekToChange
parameter_list|(
name|Integer
name|lastWeekToChange
parameter_list|)
block|{
name|iLastWeekToChange
operator|=
name|lastWeekToChange
expr_stmt|;
block|}
specifier|public
name|Integer
name|getLastWeekToDrop
parameter_list|()
block|{
return|return
name|iLastWeekToDrop
return|;
block|}
specifier|public
name|void
name|setLastWeekToDrop
parameter_list|(
name|Integer
name|lastWeekToDrop
parameter_list|)
block|{
name|iLastWeekToDrop
operator|=
name|lastWeekToDrop
expr_stmt|;
block|}
specifier|public
name|DepartmentStatusType
name|getStatusType
parameter_list|()
block|{
return|return
name|iStatusType
return|;
block|}
specifier|public
name|void
name|setStatusType
parameter_list|(
name|DepartmentStatusType
name|statusType
parameter_list|)
block|{
name|iStatusType
operator|=
name|statusType
expr_stmt|;
block|}
specifier|public
name|DatePattern
name|getDefaultDatePattern
parameter_list|()
block|{
return|return
name|iDefaultDatePattern
return|;
block|}
specifier|public
name|void
name|setDefaultDatePattern
parameter_list|(
name|DatePattern
name|defaultDatePattern
parameter_list|)
block|{
name|iDefaultDatePattern
operator|=
name|defaultDatePattern
expr_stmt|;
block|}
specifier|public
name|StudentSectioningStatus
name|getDefaultSectioningStatus
parameter_list|()
block|{
return|return
name|iDefaultSectioningStatus
return|;
block|}
specifier|public
name|void
name|setDefaultSectioningStatus
parameter_list|(
name|StudentSectioningStatus
name|defaultSectioningStatus
parameter_list|)
block|{
name|iDefaultSectioningStatus
operator|=
name|defaultSectioningStatus
expr_stmt|;
block|}
specifier|public
name|ClassDurationType
name|getDefaultClassDurationType
parameter_list|()
block|{
return|return
name|iDefaultClassDurationType
return|;
block|}
specifier|public
name|void
name|setDefaultClassDurationType
parameter_list|(
name|ClassDurationType
name|defaultClassDurationType
parameter_list|)
block|{
name|iDefaultClassDurationType
operator|=
name|defaultClassDurationType
expr_stmt|;
block|}
specifier|public
name|InstructionalMethod
name|getDefaultInstructionalMethod
parameter_list|()
block|{
return|return
name|iDefaultInstructionalMethod
return|;
block|}
specifier|public
name|void
name|setDefaultInstructionalMethod
parameter_list|(
name|InstructionalMethod
name|defaultInstructionalMethod
parameter_list|)
block|{
name|iDefaultInstructionalMethod
operator|=
name|defaultInstructionalMethod
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|SubjectArea
argument_list|>
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|iSubjectAreas
return|;
block|}
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|Set
argument_list|<
name|SubjectArea
argument_list|>
name|subjectAreas
parameter_list|)
block|{
name|iSubjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|public
name|void
name|addTosubjectAreas
parameter_list|(
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
if|if
condition|(
name|iSubjectAreas
operator|==
literal|null
condition|)
name|iSubjectAreas
operator|=
operator|new
name|HashSet
argument_list|<
name|SubjectArea
argument_list|>
argument_list|()
expr_stmt|;
name|iSubjectAreas
operator|.
name|add
argument_list|(
name|subjectArea
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Building
argument_list|>
name|getBuildings
parameter_list|()
block|{
return|return
name|iBuildings
return|;
block|}
specifier|public
name|void
name|setBuildings
parameter_list|(
name|Set
argument_list|<
name|Building
argument_list|>
name|buildings
parameter_list|)
block|{
name|iBuildings
operator|=
name|buildings
expr_stmt|;
block|}
specifier|public
name|void
name|addTobuildings
parameter_list|(
name|Building
name|building
parameter_list|)
block|{
if|if
condition|(
name|iBuildings
operator|==
literal|null
condition|)
name|iBuildings
operator|=
operator|new
name|HashSet
argument_list|<
name|Building
argument_list|>
argument_list|()
expr_stmt|;
name|iBuildings
operator|.
name|add
argument_list|(
name|building
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Department
argument_list|>
name|getDepartments
parameter_list|()
block|{
return|return
name|iDepartments
return|;
block|}
specifier|public
name|void
name|setDepartments
parameter_list|(
name|Set
argument_list|<
name|Department
argument_list|>
name|departments
parameter_list|)
block|{
name|iDepartments
operator|=
name|departments
expr_stmt|;
block|}
specifier|public
name|void
name|addTodepartments
parameter_list|(
name|Department
name|department
parameter_list|)
block|{
if|if
condition|(
name|iDepartments
operator|==
literal|null
condition|)
name|iDepartments
operator|=
operator|new
name|HashSet
argument_list|<
name|Department
argument_list|>
argument_list|()
expr_stmt|;
name|iDepartments
operator|.
name|add
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Set
argument_list|<
name|Location
argument_list|>
name|getRooms
parameter_list|()
block|{
return|return
name|iRooms
return|;
block|}
specifier|public
name|void
name|setRooms
parameter_list|(
name|Set
argument_list|<
name|Location
argument_list|>
name|rooms
parameter_list|)
block|{
name|iRooms
operator|=
name|rooms
expr_stmt|;
block|}
specifier|public
name|void
name|addTorooms
parameter_list|(
name|Location
name|location
parameter_list|)
block|{
if|if
condition|(
name|iRooms
operator|==
literal|null
condition|)
name|iRooms
operator|=
operator|new
name|HashSet
argument_list|<
name|Location
argument_list|>
argument_list|()
expr_stmt|;
name|iRooms
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
name|InstructionalOffering
argument_list|>
name|getInstructionalOfferings
parameter_list|()
block|{
return|return
name|iInstructionalOfferings
return|;
block|}
specifier|public
name|void
name|setInstructionalOfferings
parameter_list|(
name|Set
argument_list|<
name|InstructionalOffering
argument_list|>
name|instructionalOfferings
parameter_list|)
block|{
name|iInstructionalOfferings
operator|=
name|instructionalOfferings
expr_stmt|;
block|}
specifier|public
name|void
name|addToinstructionalOfferings
parameter_list|(
name|InstructionalOffering
name|instructionalOffering
parameter_list|)
block|{
if|if
condition|(
name|iInstructionalOfferings
operator|==
literal|null
condition|)
name|iInstructionalOfferings
operator|=
operator|new
name|HashSet
argument_list|<
name|InstructionalOffering
argument_list|>
argument_list|()
expr_stmt|;
name|iInstructionalOfferings
operator|.
name|add
argument_list|(
name|instructionalOffering
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
name|Session
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
name|Session
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
name|Session
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
literal|"Session["
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
literal|"Session["
operator|+
literal|"\n	AcademicInitiative: "
operator|+
name|getAcademicInitiative
argument_list|()
operator|+
literal|"\n	AcademicTerm: "
operator|+
name|getAcademicTerm
argument_list|()
operator|+
literal|"\n	AcademicYear: "
operator|+
name|getAcademicYear
argument_list|()
operator|+
literal|"\n	ClassesEndDateTime: "
operator|+
name|getClassesEndDateTime
argument_list|()
operator|+
literal|"\n	DefaultClassDurationType: "
operator|+
name|getDefaultClassDurationType
argument_list|()
operator|+
literal|"\n	DefaultDatePattern: "
operator|+
name|getDefaultDatePattern
argument_list|()
operator|+
literal|"\n	DefaultInstructionalMethod: "
operator|+
name|getDefaultInstructionalMethod
argument_list|()
operator|+
literal|"\n	DefaultSectioningStatus: "
operator|+
name|getDefaultSectioningStatus
argument_list|()
operator|+
literal|"\n	EventBeginDate: "
operator|+
name|getEventBeginDate
argument_list|()
operator|+
literal|"\n	EventEndDate: "
operator|+
name|getEventEndDate
argument_list|()
operator|+
literal|"\n	ExamBeginDate: "
operator|+
name|getExamBeginDate
argument_list|()
operator|+
literal|"\n	Holidays: "
operator|+
name|getHolidays
argument_list|()
operator|+
literal|"\n	LastWeekToChange: "
operator|+
name|getLastWeekToChange
argument_list|()
operator|+
literal|"\n	LastWeekToDrop: "
operator|+
name|getLastWeekToDrop
argument_list|()
operator|+
literal|"\n	LastWeekToEnroll: "
operator|+
name|getLastWeekToEnroll
argument_list|()
operator|+
literal|"\n	SessionBeginDateTime: "
operator|+
name|getSessionBeginDateTime
argument_list|()
operator|+
literal|"\n	SessionEndDateTime: "
operator|+
name|getSessionEndDateTime
argument_list|()
operator|+
literal|"\n	StatusType: "
operator|+
name|getStatusType
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

