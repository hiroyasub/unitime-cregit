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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|JspWriter
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
name|web
operator|.
name|htmlgen
operator|.
name|TableCell
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
name|web
operator|.
name|htmlgen
operator|.
name|TableStream
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
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|LearningManagementSystemInfo
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
name|comparators
operator|.
name|ClassCourseComparator
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
name|InstrOfferingConfigComparator
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
comment|/**  * @author Tomas Muller, Zuzana Mullerova, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|WebInstrOfferingConfigTableBuilder
extends|extends
name|WebInstructionalOfferingTableBuilder
block|{
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|WebInstrOfferingConfigTableBuilder
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|buttonsTable
parameter_list|(
name|InstrOfferingConfig
name|ioc
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|StringBuffer
name|btnTable
init|=
operator|new
name|StringBuffer
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<div class='unitime-MainTableHeader'><div class='unitime-HeaderPanel' style='margin-bottom: 0px;'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<div class='left'><div class='title'>"
argument_list|)
expr_stmt|;
name|String
name|configName
init|=
name|ioc
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|configName
operator|==
literal|null
operator|||
name|configName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|configName
operator|=
name|ioc
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|ioc
operator|.
name|getInstructionalMethod
argument_list|()
operator|!=
literal|null
condition|)
name|btnTable
operator|.
name|append
argument_list|(
name|MSG
operator|.
name|labelConfigurationWithInstructionalMethod
argument_list|(
name|configName
argument_list|,
name|ioc
operator|.
name|getInstructionalMethod
argument_list|()
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|btnTable
operator|.
name|append
argument_list|(
name|MSG
operator|.
name|labelConfiguration
argument_list|(
name|configName
argument_list|)
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</div></div>"
argument_list|)
expr_stmt|;
name|boolean
name|notOffered
init|=
name|ioc
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|isNotOffered
argument_list|()
operator|.
name|booleanValue
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|notOffered
condition|)
block|{
name|btnTable
operator|.
name|append
argument_list|(
literal|"<div class='right unitime-NoPrint' style='line-height: 29px; vertical-align: bottom; font-size: small;'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<table border='0' align='right' cellspacing='1' cellpadding='0'><tr>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|.
name|hasPermission
argument_list|(
name|ioc
argument_list|,
name|Right
operator|.
name|InstrOfferingConfigEdit
argument_list|)
condition|)
block|{
name|btnTable
operator|.
name|append
argument_list|(
literal|"<td>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<form method='post' action='instructionalOfferingConfigEdit.do' class='FormWithNoPadding'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<input type='hidden' name='configId' value='"
operator|+
name|ioc
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<input type='submit' name='op' value='"
operator|+
name|MSG
operator|.
name|actionEditConfiguration
argument_list|()
operator|+
literal|"' title='"
operator|+
name|MSG
operator|.
name|titleEditConfiguration
argument_list|()
operator|+
literal|"' class='gwt-Button'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</form>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|hasPermission
argument_list|(
name|ioc
argument_list|,
name|Right
operator|.
name|MultipleClassSetup
argument_list|)
condition|)
block|{
name|btnTable
operator|.
name|append
argument_list|(
literal|"<td>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<form method='post' action='instructionalOfferingModify.do' class='FormWithNoPadding'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<input type='hidden' name='uid' value='"
operator|+
name|ioc
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<input type='submit' name='op' value='"
operator|+
name|MSG
operator|.
name|actionClassSetup
argument_list|()
operator|+
literal|"' title='"
operator|+
name|MSG
operator|.
name|titleClassSetup
argument_list|()
operator|+
literal|"' class='gwt-Button'> "
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</form>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ApplicationProperty
operator|.
name|LegacyCourseAssignInstructors
operator|.
name|isTrue
argument_list|()
operator|&&
name|context
operator|.
name|hasPermission
argument_list|(
name|ioc
argument_list|,
name|Right
operator|.
name|AssignInstructors
argument_list|)
condition|)
block|{
name|btnTable
operator|.
name|append
argument_list|(
literal|"<td>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<form method='post' action='classInstructorAssignment.do' class='FormWithNoPadding'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<input type='hidden' name='uid' value='"
operator|+
name|ioc
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"'>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<input type='submit' name='op' value='"
operator|+
name|MSG
operator|.
name|actionAssignInstructors
argument_list|()
operator|+
literal|"' title='"
operator|+
name|MSG
operator|.
name|titleAssignInstructors
argument_list|()
operator|+
literal|"' class='gwt-Button'> "
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</form>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ApplicationProperty
operator|.
name|LegacyCourseAssignInstructors
operator|.
name|isFalse
argument_list|()
operator|&&
name|context
operator|.
name|hasPermission
argument_list|(
name|ioc
argument_list|,
name|Right
operator|.
name|AssignInstructors
argument_list|)
condition|)
block|{
name|btnTable
operator|.
name|append
argument_list|(
literal|"<td>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"<span id='"
operator|+
name|ioc
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"' name='UniTimeGWT:AssignInstructorsButton' style=\"display: none;\">"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
name|ioc
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</span>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</td>"
argument_list|)
expr_stmt|;
block|}
name|btnTable
operator|.
name|append
argument_list|(
literal|"</tr></table>"
argument_list|)
expr_stmt|;
name|btnTable
operator|.
name|append
argument_list|(
literal|"</div>"
argument_list|)
expr_stmt|;
block|}
name|btnTable
operator|.
name|append
argument_list|(
literal|"</div></div>"
argument_list|)
expr_stmt|;
return|return
operator|(
name|btnTable
operator|.
name|toString
argument_list|()
operator|)
return|;
block|}
specifier|public
name|void
name|htmlTableForInstructionalOfferingConfig
parameter_list|(
name|Vector
name|subpartIds
parameter_list|,
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|Long
name|instrOfferingConfigId
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|JspWriter
name|outputStream
parameter_list|)
block|{
if|if
condition|(
name|instrOfferingConfigId
operator|!=
literal|null
condition|)
block|{
name|InstrOfferingConfigDAO
name|iocDao
init|=
operator|new
name|InstrOfferingConfigDAO
argument_list|()
decl_stmt|;
name|InstrOfferingConfig
name|ioc
init|=
name|iocDao
operator|.
name|get
argument_list|(
name|instrOfferingConfigId
argument_list|)
decl_stmt|;
name|this
operator|.
name|htmlTableForInstructionalOfferingConfig
argument_list|(
name|subpartIds
argument_list|,
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
name|ioc
argument_list|,
name|context
argument_list|,
name|outputStream
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|void
name|htmlTableForInstructionalOfferingConfig
parameter_list|(
name|Vector
name|subpartIds
parameter_list|,
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|InstrOfferingConfig
name|ioc
parameter_list|,
name|SessionContext
name|context
parameter_list|,
name|JspWriter
name|outputStream
parameter_list|)
block|{
if|if
condition|(
name|CommonValues
operator|.
name|Yes
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|ClassesKeepSort
argument_list|)
argument_list|)
condition|)
block|{
name|setClassComparator
argument_list|(
operator|new
name|ClassCourseComparator
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"InstructionalOfferingList.sortBy"
argument_list|,
name|ClassCourseComparator
operator|.
name|getName
argument_list|(
name|ClassCourseComparator
operator|.
name|SortBy
operator|.
name|NAME
argument_list|)
argument_list|)
argument_list|,
name|classAssignment
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ioc
operator|!=
literal|null
condition|)
block|{
name|this
operator|.
name|setDisplayDistributionPrefs
argument_list|(
literal|false
argument_list|)
expr_stmt|;
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
try|try
block|{
if|if
condition|(
name|classAssignment
operator|instanceof
name|CachedClassAssignmentProxy
condition|)
block|{
name|Vector
name|allClasses
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|k
init|=
name|ioc
operator|.
name|getSchedulingSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
operator|!
name|hasTimetable
operator|&&
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SchedulingSubpart
name|ss
init|=
operator|(
name|SchedulingSubpart
operator|)
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|l
init|=
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|l
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|l
operator|.
name|next
argument_list|()
decl_stmt|;
name|allClasses
operator|.
name|add
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
block|}
operator|(
operator|(
name|CachedClassAssignmentProxy
operator|)
name|classAssignment
operator|)
operator|.
name|setCache
argument_list|(
name|allClasses
argument_list|)
expr_stmt|;
name|hasTimetable
operator|=
operator|!
name|classAssignment
operator|.
name|getAssignmentTable
argument_list|(
name|allClasses
argument_list|)
operator|.
name|isEmpty
argument_list|()
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|Iterator
name|k
init|=
name|ioc
operator|.
name|getSchedulingSubparts
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
operator|!
name|hasTimetable
operator|&&
name|k
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|SchedulingSubpart
name|ss
init|=
operator|(
name|SchedulingSubpart
operator|)
name|k
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|l
init|=
name|ss
operator|.
name|getClasses
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|l
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|l
operator|.
name|next
argument_list|()
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
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
name|setDisplayTimetable
argument_list|(
name|hasTimetable
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getDisplayConfigOpButtons
argument_list|()
condition|)
block|{
try|try
block|{
name|outputStream
operator|.
name|write
argument_list|(
name|this
operator|.
name|buttonsTable
argument_list|(
name|ioc
argument_list|,
name|context
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
block|}
name|ArrayList
argument_list|<
name|String
argument_list|>
name|columnList
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|LABEL
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnMinPerWk
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|StudentClassEnrollment
operator|.
name|sessionHasEnrollments
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnDemand
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnLimit
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|ioc
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|getCurrentSnapshotDate
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnSnapshotLimit
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnRoomRatio
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnManager
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnDatePattern
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnTimePattern
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnInstructor
argument_list|()
argument_list|)
expr_stmt|;
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnTimetable
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|LearningManagementSystemInfo
operator|.
name|isLmsInfoDefinedForSession
argument_list|(
name|ioc
operator|.
name|getSessionId
argument_list|()
argument_list|)
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnLms
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ApplicationProperty
operator|.
name|CoursesFundingDepartmentsEnabled
operator|.
name|isTrue
argument_list|()
condition|)
block|{
name|ss
label|:
for|for
control|(
name|SchedulingSubpart
name|ss
range|:
name|ioc
operator|.
name|getSchedulingSubparts
argument_list|()
control|)
block|{
for|for
control|(
name|Class_
name|c
range|:
name|ss
operator|.
name|getClasses
argument_list|()
control|)
if|if
condition|(
name|c
operator|.
name|getFundingDept
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|columnList
operator|.
name|add
argument_list|(
name|MSG
operator|.
name|columnFundingDepartment
argument_list|()
argument_list|)
expr_stmt|;
break|break
name|ss
break|;
block|}
block|}
block|}
name|setVisibleColumns
argument_list|(
name|columnList
argument_list|)
expr_stmt|;
name|boolean
name|hasInstructorAssignments
init|=
literal|false
decl_stmt|;
name|ss
label|:
for|for
control|(
name|SchedulingSubpart
name|ss
range|:
name|ioc
operator|.
name|getSchedulingSubparts
argument_list|()
control|)
block|{
if|if
condition|(
name|ss
operator|.
name|isInstructorAssignmentNeeded
argument_list|()
condition|)
block|{
name|hasInstructorAssignments
operator|=
literal|true
expr_stmt|;
break|break;
block|}
for|for
control|(
name|Class_
name|c
range|:
name|ss
operator|.
name|getClasses
argument_list|()
control|)
if|if
condition|(
name|c
operator|.
name|isInstructorAssignmentNeeded
argument_list|()
condition|)
block|{
name|hasInstructorAssignments
operator|=
literal|true
expr_stmt|;
break|break
name|ss
break|;
block|}
block|}
name|setShowInstructorAssignment
argument_list|(
name|hasInstructorAssignments
argument_list|)
expr_stmt|;
name|setDisplayInstructorPrefs
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ClassDurationType
name|dtype
init|=
name|ioc
operator|.
name|getEffectiveDurationType
argument_list|()
decl_stmt|;
name|TableStream
name|configTable
init|=
name|this
operator|.
name|initTable
argument_list|(
name|outputStream
argument_list|,
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|,
name|dtype
operator|==
literal|null
condition|?
name|MSG
operator|.
name|columnMinPerWk
argument_list|()
else|:
name|dtype
operator|.
name|getLabel
argument_list|()
argument_list|)
decl_stmt|;
name|this
operator|.
name|buildConfigRow
argument_list|(
name|subpartIds
argument_list|,
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
name|configTable
argument_list|,
name|ioc
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
argument_list|,
name|ioc
argument_list|,
name|context
argument_list|,
operator|!
name|getDisplayConfigOpButtons
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|configTable
operator|.
name|tableComplete
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|htmlConfigTablesForInstructionalOffering
parameter_list|(
name|SessionContext
name|context
parameter_list|,
name|ClassAssignmentProxy
name|classAssignment
parameter_list|,
name|ExamAssignmentProxy
name|examAssignment
parameter_list|,
name|Long
name|instructionalOffering
parameter_list|,
name|JspWriter
name|outputStream
parameter_list|,
name|String
name|backType
parameter_list|,
name|String
name|backId
parameter_list|)
block|{
name|setBackType
argument_list|(
name|backType
argument_list|)
expr_stmt|;
name|setBackId
argument_list|(
name|backId
argument_list|)
expr_stmt|;
if|if
condition|(
name|CommonValues
operator|.
name|Yes
operator|.
name|eq
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
name|UserProperty
operator|.
name|ClassesKeepSort
argument_list|)
argument_list|)
condition|)
block|{
name|setClassComparator
argument_list|(
operator|new
name|ClassCourseComparator
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getProperty
argument_list|(
literal|"InstructionalOfferingList.sortBy"
argument_list|,
name|ClassCourseComparator
operator|.
name|getName
argument_list|(
name|ClassCourseComparator
operator|.
name|SortBy
operator|.
name|NAME
argument_list|)
argument_list|)
argument_list|,
name|classAssignment
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|instructionalOffering
operator|!=
literal|null
condition|)
block|{
name|InstructionalOfferingDAO
name|iDao
init|=
operator|new
name|InstructionalOfferingDAO
argument_list|()
decl_stmt|;
name|InstructionalOffering
name|io
init|=
name|iDao
operator|.
name|get
argument_list|(
name|instructionalOffering
argument_list|)
decl_stmt|;
name|setUserSettings
argument_list|(
name|context
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
name|Vector
name|subpartIds
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
if|if
condition|(
name|io
operator|.
name|getInstrOfferingConfigs
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|TreeSet
name|configs
init|=
operator|new
name|TreeSet
argument_list|(
operator|new
name|InstrOfferingConfigComparator
argument_list|(
name|io
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|configs
operator|.
name|addAll
argument_list|(
name|io
operator|.
name|getInstrOfferingConfigs
argument_list|()
argument_list|)
expr_stmt|;
name|InstrOfferingConfig
name|ioc
init|=
literal|null
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|it
init|=
name|configs
operator|.
name|iterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|ioc
operator|=
operator|(
name|InstrOfferingConfig
operator|)
name|it
operator|.
name|next
argument_list|()
expr_stmt|;
if|if
condition|(
name|idx
operator|>
literal|0
operator|&&
name|getDisplayConfigOpButtons
argument_list|()
condition|)
block|{
try|try
block|{
name|outputStream
operator|.
name|println
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
block|}
name|this
operator|.
name|htmlTableForInstructionalOfferingConfig
argument_list|(
name|subpartIds
argument_list|,
name|classAssignment
argument_list|,
name|examAssignment
argument_list|,
name|ioc
argument_list|,
name|context
argument_list|,
name|outputStream
argument_list|)
expr_stmt|;
block|}
block|}
name|Navigation
operator|.
name|set
argument_list|(
name|context
argument_list|,
name|Navigation
operator|.
name|sSchedulingSubpartLevel
argument_list|,
name|subpartIds
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|TableCell
name|buildPrefGroupLabel
parameter_list|(
name|CourseOffering
name|co
parameter_list|,
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|int
name|indentSpaces
parameter_list|,
name|boolean
name|isEditable
parameter_list|,
name|String
name|prevLabel
parameter_list|,
name|String
name|icon
parameter_list|)
block|{
name|TableCell
name|cell
init|=
name|super
operator|.
name|buildPrefGroupLabel
argument_list|(
name|co
argument_list|,
name|prefGroup
argument_list|,
name|indentSpaces
argument_list|,
name|isEditable
argument_list|,
name|prevLabel
argument_list|,
name|icon
argument_list|)
decl_stmt|;
if|if
condition|(
literal|"PreferenceGroup"
operator|.
name|equals
argument_list|(
name|getBackType
argument_list|()
argument_list|)
operator|&&
name|prefGroup
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|getBackId
argument_list|()
argument_list|)
condition|)
name|cell
operator|.
name|addContent
argument_list|(
literal|"<A name=\"back\"></A>"
argument_list|)
expr_stmt|;
return|return
name|cell
return|;
block|}
annotation|@
name|Override
specifier|protected
name|TableCell
name|buildMinPerWeek
parameter_list|(
name|PreferenceGroup
name|prefGroup
parameter_list|,
name|boolean
name|isEditable
parameter_list|)
block|{
name|TableCell
name|cell
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|prefGroup
operator|instanceof
name|Class_
condition|)
block|{
name|Class_
name|aClass
init|=
operator|(
name|Class_
operator|)
name|prefGroup
decl_stmt|;
name|cell
operator|=
name|initNormalCell
argument_list|(
name|aClass
operator|.
name|getSchedulingSubpart
argument_list|()
operator|.
name|getMinutesPerWk
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setAlign
argument_list|(
literal|"right"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|prefGroup
operator|instanceof
name|SchedulingSubpart
condition|)
block|{
name|SchedulingSubpart
name|aSchedulingSubpart
init|=
operator|(
name|SchedulingSubpart
operator|)
name|prefGroup
decl_stmt|;
name|cell
operator|=
name|initNormalCell
argument_list|(
name|aSchedulingSubpart
operator|.
name|getMinutesPerWk
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
name|cell
operator|.
name|setAlign
argument_list|(
literal|"right"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|cell
operator|=
name|this
operator|.
name|initNormalCell
argument_list|(
literal|"&nbsp;"
argument_list|,
name|isEditable
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|cell
operator|)
return|;
block|}
block|}
end_class

end_unit

