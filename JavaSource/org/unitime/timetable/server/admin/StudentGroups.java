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
name|admin
package|;
end_package

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
name|Hashtable
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
name|ToolBox
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|security
operator|.
name|access
operator|.
name|prepost
operator|.
name|PreAuthorize
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
name|SimpleEditInterface
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
name|SimpleEditInterface
operator|.
name|Field
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
name|SimpleEditInterface
operator|.
name|FieldType
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
name|SimpleEditInterface
operator|.
name|Flag
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
name|SimpleEditInterface
operator|.
name|PageName
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
name|SimpleEditInterface
operator|.
name|Record
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
name|ChangeLog
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
name|ChangeLog
operator|.
name|Operation
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
name|ChangeLog
operator|.
name|Source
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
name|StudentSectioningQueue
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
name|SessionDAO
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
name|StudentGroupDAO
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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"gwtAdminTable[type=group]"
argument_list|)
specifier|public
class|class
name|StudentGroups
implements|implements
name|AdminTable
block|{
specifier|protected
specifier|static
specifier|final
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
name|Override
specifier|public
name|PageName
name|name
parameter_list|()
block|{
return|return
operator|new
name|PageName
argument_list|(
name|MESSAGES
operator|.
name|pageStudentGroup
argument_list|()
argument_list|,
name|MESSAGES
operator|.
name|pageStudentGroups
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('StudentGroups')"
argument_list|)
specifier|public
name|SimpleEditInterface
name|load
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|SimpleEditInterface
name|data
init|=
operator|new
name|SimpleEditInterface
argument_list|(
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldExternalId
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|120
argument_list|,
literal|40
argument_list|,
name|Flag
operator|.
name|READ_ONLY
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldCode
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|80
argument_list|,
literal|10
argument_list|,
name|Flag
operator|.
name|UNIQUE
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldName
argument_list|()
argument_list|,
name|FieldType
operator|.
name|text
argument_list|,
literal|300
argument_list|,
literal|50
argument_list|,
name|Flag
operator|.
name|UNIQUE
argument_list|)
argument_list|,
operator|new
name|Field
argument_list|(
name|MESSAGES
operator|.
name|fieldStudents
argument_list|()
argument_list|,
name|FieldType
operator|.
name|students
argument_list|,
literal|200
argument_list|)
argument_list|)
decl_stmt|;
name|data
operator|.
name|setSortBy
argument_list|(
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
for|for
control|(
name|StudentGroup
name|group
range|:
name|StudentGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findBySession
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|Record
name|r
init|=
name|data
operator|.
name|addRecord
argument_list|(
name|group
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|0
argument_list|,
name|group
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|1
argument_list|,
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|r
operator|.
name|setField
argument_list|(
literal|2
argument_list|,
name|group
operator|.
name|getGroupName
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|students
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Student
name|student
range|:
operator|new
name|TreeSet
argument_list|<
name|Student
argument_list|>
argument_list|(
name|group
operator|.
name|getStudents
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
name|students
operator|.
name|isEmpty
argument_list|()
condition|)
name|students
operator|+=
literal|"\n"
expr_stmt|;
name|students
operator|+=
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|student
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFirstMiddle
argument_list|)
expr_stmt|;
block|}
name|r
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|students
argument_list|,
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
argument_list|)
expr_stmt|;
name|r
operator|.
name|setDeletable
argument_list|(
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
argument_list|)
expr_stmt|;
block|}
name|data
operator|.
name|setEditable
argument_list|(
name|context
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|StudentGroupEdit
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|data
return|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('StudentGroupEdit')"
argument_list|)
specifier|public
name|void
name|save
parameter_list|(
name|SimpleEditInterface
name|data
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentGroup
name|group
range|:
name|StudentGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findBySession
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|Record
name|r
init|=
name|data
operator|.
name|getRecord
argument_list|(
name|group
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|==
literal|null
condition|)
name|delete
argument_list|(
name|group
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
else|else
name|update
argument_list|(
name|group
argument_list|,
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Record
name|r
range|:
name|data
operator|.
name|getNewRecords
argument_list|()
control|)
name|save
argument_list|(
name|r
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|studentIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|StudentSectioningQueue
operator|.
name|studentChanged
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|save
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
name|StudentGroup
name|group
init|=
operator|new
name|StudentGroup
argument_list|()
decl_stmt|;
name|group
operator|.
name|setExternalUniqueId
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|.
name|setGroupAbbreviation
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|.
name|setGroupName
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|.
name|setSession
argument_list|(
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|hibSession
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|.
name|setStudents
argument_list|(
operator|new
name|HashSet
argument_list|<
name|Student
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|students
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
operator|.
name|split
argument_list|(
literal|"\\n"
argument_list|)
control|)
block|{
if|if
condition|(
name|s
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
operator|>=
literal|0
condition|)
name|s
operator|=
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|s
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|Student
name|student
init|=
name|Student
operator|.
name|findByExternalId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|s
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|group
operator|.
name|getStudents
argument_list|()
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|students
operator|.
name|isEmpty
argument_list|()
condition|)
name|students
operator|+=
literal|"\n"
expr_stmt|;
name|students
operator|+=
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|student
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFirstMiddle
argument_list|)
expr_stmt|;
name|studentIds
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|record
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|students
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
name|record
operator|.
name|setUniqueId
argument_list|(
operator|(
name|Long
operator|)
name|hibSession
operator|.
name|save
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|group
argument_list|,
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
operator|+
literal|" "
operator|+
name|group
operator|.
name|getGroupName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
name|Operation
operator|.
name|CREATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('StudentGroupEdit')"
argument_list|)
specifier|public
name|void
name|save
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|save
argument_list|(
name|record
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|studentIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|StudentSectioningQueue
operator|.
name|studentChanged
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|update
parameter_list|(
name|StudentGroup
name|group
parameter_list|,
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
if|if
condition|(
name|group
operator|==
literal|null
condition|)
return|return;
name|boolean
name|changed
init|=
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|group
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|||
operator|!
name|ToolBox
operator|.
name|equals
argument_list|(
name|group
operator|.
name|getGroupName
argument_list|()
argument_list|,
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
name|group
operator|.
name|setExternalUniqueId
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|.
name|setGroupAbbreviation
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|group
operator|.
name|setGroupName
argument_list|(
name|record
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
operator|&&
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Student
argument_list|>
name|students
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Student
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Student
name|s
range|:
name|group
operator|.
name|getStudents
argument_list|()
control|)
name|students
operator|.
name|put
argument_list|(
name|s
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|s
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|line
range|:
name|record
operator|.
name|getField
argument_list|(
literal|3
argument_list|)
operator|.
name|split
argument_list|(
literal|"\\n"
argument_list|)
control|)
block|{
name|String
name|extId
init|=
operator|(
name|line
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
operator|>=
literal|0
condition|?
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|line
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
argument_list|)
else|:
name|line
operator|)
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|extId
operator|.
name|isEmpty
argument_list|()
operator|||
name|students
operator|.
name|remove
argument_list|(
name|extId
argument_list|)
operator|!=
literal|null
condition|)
continue|continue;
name|Student
name|student
init|=
name|Student
operator|.
name|findByExternalId
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|extId
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
condition|)
block|{
name|group
operator|.
name|getStudents
argument_list|()
operator|.
name|add
argument_list|(
name|student
argument_list|)
expr_stmt|;
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|add
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
name|studentIds
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|students
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
for|for
control|(
name|Student
name|student
range|:
name|students
operator|.
name|values
argument_list|()
control|)
block|{
name|studentIds
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|remove
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
name|group
operator|.
name|getStudents
argument_list|()
operator|.
name|removeAll
argument_list|(
name|students
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
name|String
name|newStudents
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Student
name|student
range|:
operator|new
name|TreeSet
argument_list|<
name|Student
argument_list|>
argument_list|(
name|group
operator|.
name|getStudents
argument_list|()
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
name|newStudents
operator|.
name|isEmpty
argument_list|()
condition|)
name|newStudents
operator|+=
literal|"\n"
expr_stmt|;
name|newStudents
operator|+=
name|student
operator|.
name|getExternalUniqueId
argument_list|()
operator|+
literal|" "
operator|+
name|student
operator|.
name|getName
argument_list|(
name|DepartmentalInstructor
operator|.
name|sNameFormatLastFirstMiddle
argument_list|)
expr_stmt|;
block|}
name|record
operator|.
name|setField
argument_list|(
literal|3
argument_list|,
name|newStudents
argument_list|,
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
argument_list|)
expr_stmt|;
block|}
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|group
argument_list|)
expr_stmt|;
if|if
condition|(
name|changed
condition|)
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|group
argument_list|,
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
operator|+
literal|" "
operator|+
name|group
operator|.
name|getGroupName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('StudentGroupEdit')"
argument_list|)
specifier|public
name|void
name|update
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|update
argument_list|(
name|StudentGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|record
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|,
name|record
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|studentIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|StudentSectioningQueue
operator|.
name|studentChanged
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|delete
parameter_list|(
name|StudentGroup
name|group
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|,
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
if|if
condition|(
name|group
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|group
operator|.
name|getStudents
argument_list|()
operator|!=
literal|null
condition|)
for|for
control|(
name|Student
name|student
range|:
name|group
operator|.
name|getStudents
argument_list|()
control|)
block|{
name|studentIds
operator|.
name|add
argument_list|(
name|student
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|getGroups
argument_list|()
operator|.
name|remove
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|context
argument_list|,
name|group
argument_list|,
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
operator|+
literal|" "
operator|+
name|group
operator|.
name|getGroupName
argument_list|()
argument_list|,
name|Source
operator|.
name|SIMPLE_EDIT
argument_list|,
name|Operation
operator|.
name|DELETE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('StudentGroupEdit')"
argument_list|)
specifier|public
name|void
name|delete
parameter_list|(
name|Record
name|record
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|Session
name|hibSession
parameter_list|)
block|{
name|Set
argument_list|<
name|Long
argument_list|>
name|studentIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
name|delete
argument_list|(
name|StudentGroupDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|record
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|,
name|context
argument_list|,
name|hibSession
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|studentIds
operator|.
name|isEmpty
argument_list|()
condition|)
name|StudentSectioningQueue
operator|.
name|studentChanged
argument_list|(
name|hibSession
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|studentIds
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

