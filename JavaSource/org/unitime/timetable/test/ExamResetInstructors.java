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
name|test
package|;
end_package

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
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|model
operator|.
name|ClassInstructor
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
name|dao
operator|.
name|_RootDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExamResetInstructors
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ExamResetInstructors
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|void
name|doUpdate
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|examType
parameter_list|,
name|boolean
name|override
parameter_list|,
name|boolean
name|classOnly
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
for|for
control|(
name|Exam
name|exam
range|:
operator|new
name|TreeSet
argument_list|<
name|Exam
argument_list|>
argument_list|(
name|Exam
operator|.
name|findAll
argument_list|(
name|sessionId
argument_list|,
name|examType
argument_list|)
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
name|override
operator|&&
operator|!
name|exam
operator|.
name|getInstructors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|sLog
operator|.
name|info
argument_list|(
literal|"Updating "
operator|+
name|exam
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|exam
operator|.
name|getInstructors
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
name|DepartmentalInstructor
name|instructor
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|instructor
operator|.
name|getExams
argument_list|()
operator|.
name|remove
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|ExamOwner
name|owner
range|:
name|exam
operator|.
name|getOwners
argument_list|()
control|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"  owner "
operator|+
name|owner
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|Object
name|object
init|=
name|owner
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|Class_
condition|)
block|{
for|for
control|(
name|ClassInstructor
name|instructor
range|:
operator|(
operator|(
name|Class_
operator|)
name|object
operator|)
operator|.
name|getClassInstructors
argument_list|()
control|)
if|if
condition|(
name|instructor
operator|.
name|getPercentShare
argument_list|()
operator|>=
literal|100
condition|)
name|exam
operator|.
name|getInstructors
argument_list|()
operator|.
name|add
argument_list|(
name|instructor
operator|.
name|getInstructor
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|object
operator|instanceof
name|InstrOfferingConfig
operator|&&
operator|!
name|classOnly
condition|)
block|{
name|SchedulingSubpart
name|top
init|=
literal|null
decl_stmt|;
for|for
control|(
name|SchedulingSubpart
name|s
range|:
operator|(
operator|(
name|InstrOfferingConfig
operator|)
name|object
operator|)
operator|.
name|getSchedulingSubparts
argument_list|()
control|)
if|if
condition|(
name|s
operator|.
name|getParentSubpart
argument_list|()
operator|==
literal|null
operator|&&
operator|(
name|top
operator|==
literal|null
operator|||
name|s
operator|.
name|getItype
argument_list|()
operator|.
name|compareTo
argument_list|(
name|top
operator|.
name|getItype
argument_list|()
argument_list|)
operator|<
literal|0
operator|||
operator|(
name|s
operator|.
name|getItype
argument_list|()
operator|.
name|compareTo
argument_list|(
name|top
operator|.
name|getItype
argument_list|()
argument_list|)
operator|==
literal|0
operator|&&
name|s
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|<
name|top
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|)
operator|)
condition|)
name|top
operator|=
name|s
expr_stmt|;
if|if
condition|(
name|top
operator|!=
literal|null
condition|)
for|for
control|(
name|Class_
name|clazz
range|:
name|top
operator|.
name|getClasses
argument_list|()
control|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"  using "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|ClassInstructor
name|instructor
range|:
name|clazz
operator|.
name|getClassInstructors
argument_list|()
control|)
if|if
condition|(
name|instructor
operator|.
name|getPercentShare
argument_list|()
operator|>=
literal|100
condition|)
name|exam
operator|.
name|getInstructors
argument_list|()
operator|.
name|add
argument_list|(
name|instructor
operator|.
name|getInstructor
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|else if
condition|(
operator|!
name|classOnly
condition|)
block|{
for|for
control|(
name|InstrOfferingConfig
name|config
range|:
name|owner
operator|.
name|getCourse
argument_list|()
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getInstrOfferingConfigs
argument_list|()
control|)
block|{
name|SchedulingSubpart
name|top
init|=
literal|null
decl_stmt|;
for|for
control|(
name|SchedulingSubpart
name|s
range|:
name|config
operator|.
name|getSchedulingSubparts
argument_list|()
control|)
if|if
condition|(
name|s
operator|.
name|getParentSubpart
argument_list|()
operator|==
literal|null
operator|&&
operator|(
name|top
operator|==
literal|null
operator|||
name|s
operator|.
name|getItype
argument_list|()
operator|.
name|compareTo
argument_list|(
name|top
operator|.
name|getItype
argument_list|()
argument_list|)
operator|<
literal|0
operator|||
operator|(
name|s
operator|.
name|getItype
argument_list|()
operator|.
name|compareTo
argument_list|(
name|top
operator|.
name|getItype
argument_list|()
argument_list|)
operator|==
literal|0
operator|&&
name|s
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|<
name|top
operator|.
name|getClasses
argument_list|()
operator|.
name|size
argument_list|()
operator|)
operator|)
condition|)
name|top
operator|=
name|s
expr_stmt|;
if|if
condition|(
name|top
operator|!=
literal|null
condition|)
for|for
control|(
name|Class_
name|clazz
range|:
name|top
operator|.
name|getClasses
argument_list|()
control|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"  using "
operator|+
name|clazz
operator|.
name|getClassLabel
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|ClassInstructor
name|instructor
range|:
name|clazz
operator|.
name|getClassInstructors
argument_list|()
control|)
if|if
condition|(
name|instructor
operator|.
name|getPercentShare
argument_list|()
operator|>=
literal|100
condition|)
name|exam
operator|.
name|getInstructors
argument_list|()
operator|.
name|add
argument_list|(
name|instructor
operator|.
name|getInstructor
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
if|if
condition|(
name|exam
operator|.
name|getInstructors
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|sLog
operator|.
name|info
argument_list|(
literal|"    no instructors"
argument_list|)
expr_stmt|;
else|else
name|sLog
operator|.
name|info
argument_list|(
literal|"    instructors "
operator|+
operator|new
name|TreeSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
argument_list|(
name|exam
operator|.
name|getInstructors
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|DepartmentalInstructor
name|instructor
range|:
name|exam
operator|.
name|getInstructors
argument_list|()
control|)
block|{
name|instructor
operator|.
name|getExams
argument_list|()
operator|.
name|add
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|instructor
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|exam
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
name|args
index|[]
parameter_list|)
block|{
try|try
block|{
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
name|ApplicationProperties
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"initiative"
argument_list|,
literal|"PWL"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"year"
argument_list|,
literal|"2013"
argument_list|)
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"term"
argument_list|,
literal|"Fall"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Academic session not found, use properties initiative, year, and term to set academic session."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Session: "
operator|+
name|session
argument_list|)
expr_stmt|;
block|}
name|ExamType
name|examType
init|=
name|ExamType
operator|.
name|findByReference
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"type"
argument_list|,
literal|"final"
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|override
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"override"
argument_list|,
literal|"true"
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|classOnly
init|=
literal|"true"
operator|.
name|equals
argument_list|(
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"classOnly"
argument_list|,
literal|"false"
argument_list|)
argument_list|)
decl_stmt|;
name|doUpdate
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|examType
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|override
argument_list|,
name|classOnly
argument_list|,
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|)
expr_stmt|;
name|HibernateUtil
operator|.
name|closeHibernate
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

