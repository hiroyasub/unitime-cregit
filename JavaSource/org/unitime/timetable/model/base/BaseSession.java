begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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

begin_comment
comment|/**  * This is an object that contains data related to the SESSIONS table.  * Do not modify this class because it will be overwritten if the configuration file  * related to this class is modified.  *  * @hibernate.class  *  table="SESSIONS"  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseSession
extends|extends
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|PreferenceGroup
implements|implements
name|Serializable
block|{
specifier|public
specifier|static
name|String
name|REF
init|=
literal|"Session"
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
name|PROP_HOLIDAYS
init|=
literal|"holidays"
decl_stmt|;
comment|// constructors
specifier|public
name|BaseSession
parameter_list|()
block|{
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|BaseSession
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
specifier|private
name|int
name|hashCode
init|=
name|Integer
operator|.
name|MIN_VALUE
decl_stmt|;
comment|// fields
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|academicInitiative
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|academicYear
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|academicTerm
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Date
name|sessionBeginDateTime
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Date
name|classesEndDateTime
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Date
name|sessionEndDateTime
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Date
name|examBeginDate
decl_stmt|;
specifier|private
name|java
operator|.
name|lang
operator|.
name|String
name|holidays
decl_stmt|;
comment|// many to one
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentStatusType
name|statusType
decl_stmt|;
specifier|private
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|defaultDatePattern
decl_stmt|;
comment|// collections
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|subjectAreas
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|buildings
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|departments
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|rooms
decl_stmt|;
specifier|private
name|java
operator|.
name|util
operator|.
name|Set
name|instructionalOfferings
decl_stmt|;
comment|/** 	 * Return the value associated with the column: ACADEMIC_INITIATIVE 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getAcademicInitiative
parameter_list|()
block|{
return|return
name|academicInitiative
return|;
block|}
comment|/** 	 * Set the value related to the column: ACADEMIC_INITIATIVE 	 * @param academicInitiative the ACADEMIC_INITIATIVE value 	 */
specifier|public
name|void
name|setAcademicInitiative
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|academicInitiative
parameter_list|)
block|{
name|this
operator|.
name|academicInitiative
operator|=
name|academicInitiative
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ACADEMIC_YEAR 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getAcademicYear
parameter_list|()
block|{
return|return
name|academicYear
return|;
block|}
comment|/** 	 * Set the value related to the column: ACADEMIC_YEAR 	 * @param academicYear the ACADEMIC_YEAR value 	 */
specifier|public
name|void
name|setAcademicYear
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|academicYear
parameter_list|)
block|{
name|this
operator|.
name|academicYear
operator|=
name|academicYear
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: ACADEMIC_TERM 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getAcademicTerm
parameter_list|()
block|{
return|return
name|academicTerm
return|;
block|}
comment|/** 	 * Set the value related to the column: ACADEMIC_TERM 	 * @param academicTerm the ACADEMIC_TERM value 	 */
specifier|public
name|void
name|setAcademicTerm
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|academicTerm
parameter_list|)
block|{
name|this
operator|.
name|academicTerm
operator|=
name|academicTerm
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SESSION_BEGIN_DATE_TIME 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Date
name|getSessionBeginDateTime
parameter_list|()
block|{
return|return
name|sessionBeginDateTime
return|;
block|}
comment|/** 	 * Set the value related to the column: SESSION_BEGIN_DATE_TIME 	 * @param sessionBeginDateTime the SESSION_BEGIN_DATE_TIME value 	 */
specifier|public
name|void
name|setSessionBeginDateTime
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|sessionBeginDateTime
parameter_list|)
block|{
name|this
operator|.
name|sessionBeginDateTime
operator|=
name|sessionBeginDateTime
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: CLASSES_END_DATE_TIME 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Date
name|getClassesEndDateTime
parameter_list|()
block|{
return|return
name|classesEndDateTime
return|;
block|}
comment|/** 	 * Set the value related to the column: CLASSES_END_DATE_TIME 	 * @param classesEndDateTime the CLASSES_END_DATE_TIME value 	 */
specifier|public
name|void
name|setClassesEndDateTime
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|classesEndDateTime
parameter_list|)
block|{
name|this
operator|.
name|classesEndDateTime
operator|=
name|classesEndDateTime
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: SESSION_END_DATE_TIME 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Date
name|getSessionEndDateTime
parameter_list|()
block|{
return|return
name|sessionEndDateTime
return|;
block|}
comment|/** 	 * Set the value related to the column: SESSION_END_DATE_TIME 	 * @param sessionEndDateTime the SESSION_END_DATE_TIME value 	 */
specifier|public
name|void
name|setSessionEndDateTime
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|sessionEndDateTime
parameter_list|)
block|{
name|this
operator|.
name|sessionEndDateTime
operator|=
name|sessionEndDateTime
expr_stmt|;
block|}
specifier|public
name|java
operator|.
name|util
operator|.
name|Date
name|getExamBeginDate
parameter_list|()
block|{
return|return
name|examBeginDate
return|;
block|}
specifier|public
name|void
name|setExamBeginDate
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Date
name|examBeginDate
parameter_list|)
block|{
name|this
operator|.
name|examBeginDate
operator|=
name|examBeginDate
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: HOLIDAYS 	 */
specifier|public
name|java
operator|.
name|lang
operator|.
name|String
name|getHolidays
parameter_list|()
block|{
return|return
name|holidays
return|;
block|}
comment|/** 	 * Set the value related to the column: HOLIDAYS 	 * @param holidays the HOLIDAYS value 	 */
specifier|public
name|void
name|setHolidays
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|String
name|holidays
parameter_list|)
block|{
name|this
operator|.
name|holidays
operator|=
name|holidays
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: STATUS_TYPE 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentStatusType
name|getStatusType
parameter_list|()
block|{
return|return
name|statusType
return|;
block|}
comment|/** 	 * Set the value related to the column: STATUS_TYPE 	 * @param statusType the STATUS_TYPE value 	 */
specifier|public
name|void
name|setStatusType
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DepartmentStatusType
name|statusType
parameter_list|)
block|{
name|this
operator|.
name|statusType
operator|=
name|statusType
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: DEF_DATEPATT_ID 	 */
specifier|public
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|getDefaultDatePattern
parameter_list|()
block|{
return|return
name|defaultDatePattern
return|;
block|}
comment|/** 	 * Set the value related to the column: DEF_DATEPATT_ID 	 * @param defaultDatePattern the DEF_DATEPATT_ID value 	 */
specifier|public
name|void
name|setDefaultDatePattern
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|DatePattern
name|defaultDatePattern
parameter_list|)
block|{
name|this
operator|.
name|defaultDatePattern
operator|=
name|defaultDatePattern
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: subjectAreas 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getSubjectAreas
parameter_list|()
block|{
return|return
name|subjectAreas
return|;
block|}
comment|/** 	 * Set the value related to the column: subjectAreas 	 * @param subjectAreas the subjectAreas value 	 */
specifier|public
name|void
name|setSubjectAreas
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|subjectAreas
parameter_list|)
block|{
name|this
operator|.
name|subjectAreas
operator|=
name|subjectAreas
expr_stmt|;
block|}
specifier|public
name|void
name|addTosubjectAreas
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|SubjectArea
name|subjectArea
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getSubjectAreas
argument_list|()
condition|)
name|setSubjectAreas
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getSubjectAreas
argument_list|()
operator|.
name|add
argument_list|(
name|subjectArea
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: buildings 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getBuildings
parameter_list|()
block|{
return|return
name|buildings
return|;
block|}
comment|/** 	 * Set the value related to the column: buildings 	 * @param buildings the buildings value 	 */
specifier|public
name|void
name|setBuildings
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|buildings
parameter_list|)
block|{
name|this
operator|.
name|buildings
operator|=
name|buildings
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: departments 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getDepartments
parameter_list|()
block|{
return|return
name|departments
return|;
block|}
comment|/** 	 * Set the value related to the column: departments 	 * @param departments the departments value 	 */
specifier|public
name|void
name|setDepartments
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|departments
parameter_list|)
block|{
name|this
operator|.
name|departments
operator|=
name|departments
expr_stmt|;
block|}
specifier|public
name|void
name|addTodepartments
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Department
name|department
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getDepartments
argument_list|()
condition|)
name|setDepartments
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getDepartments
argument_list|()
operator|.
name|add
argument_list|(
name|department
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: rooms 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getRooms
parameter_list|()
block|{
return|return
name|rooms
return|;
block|}
comment|/** 	 * Set the value related to the column: rooms 	 * @param rooms the rooms value 	 */
specifier|public
name|void
name|setRooms
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|rooms
parameter_list|)
block|{
name|this
operator|.
name|rooms
operator|=
name|rooms
expr_stmt|;
block|}
specifier|public
name|void
name|addTorooms
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Location
name|location
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getRooms
argument_list|()
condition|)
name|setRooms
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getRooms
argument_list|()
operator|.
name|add
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Return the value associated with the column: instructionalOfferings 	 */
specifier|public
name|java
operator|.
name|util
operator|.
name|Set
name|getInstructionalOfferings
parameter_list|()
block|{
return|return
name|instructionalOfferings
return|;
block|}
comment|/** 	 * Set the value related to the column: instructionalOfferings 	 * @param instructionalOfferings the instructionalOfferings value 	 */
specifier|public
name|void
name|setInstructionalOfferings
parameter_list|(
name|java
operator|.
name|util
operator|.
name|Set
name|instructionalOfferings
parameter_list|)
block|{
name|this
operator|.
name|instructionalOfferings
operator|=
name|instructionalOfferings
expr_stmt|;
block|}
specifier|public
name|void
name|addToinstructionalOfferings
parameter_list|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|InstructionalOffering
name|instructionalOffering
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|getInstructionalOfferings
argument_list|()
condition|)
name|setInstructionalOfferings
argument_list|(
operator|new
name|java
operator|.
name|util
operator|.
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
name|getInstructionalOfferings
argument_list|()
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
name|obj
parameter_list|)
block|{
if|if
condition|(
literal|null
operator|==
name|obj
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
operator|)
condition|)
return|return
literal|false
return|;
else|else
block|{
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
init|=
operator|(
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
operator|)
name|obj
decl_stmt|;
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
operator|||
literal|null
operator|==
name|session
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
literal|false
return|;
else|else
return|return
operator|(
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|)
return|;
block|}
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
if|if
condition|(
name|Integer
operator|.
name|MIN_VALUE
operator|==
name|this
operator|.
name|hashCode
condition|)
block|{
if|if
condition|(
literal|null
operator|==
name|this
operator|.
name|getUniqueId
argument_list|()
condition|)
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
else|else
block|{
name|String
name|hashStr
init|=
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|":"
operator|+
name|this
operator|.
name|getUniqueId
argument_list|()
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|this
operator|.
name|hashCode
operator|=
name|hashStr
operator|.
name|hashCode
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|this
operator|.
name|hashCode
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|super
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

