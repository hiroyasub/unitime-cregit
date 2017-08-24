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
name|defaults
package|;
end_package

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

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_enum
specifier|public
enum|enum
name|UserProperty
block|{
name|LastAcademicSession
argument_list|(
literal|"LastUsed.acadSessionId"
argument_list|,
literal|"Last used academic session id"
argument_list|)
block|,
name|CourseOfferingNoteDisplay
argument_list|(
literal|"crsOffrNoteDisplay"
argument_list|,
name|CommonValues
operator|.
name|NoteAsIcon
argument_list|,
literal|"Display an icon or shortened text when a course offering has a schedule note."
argument_list|)
block|,
name|SchedulePrintNoteDisplay
argument_list|(
literal|"printNoteDisplay"
argument_list|,
name|CommonValues
operator|.
name|NoteAsIcon
argument_list|,
literal|"Display an icon or shortened text when a class has a schedule print note."
argument_list|)
block|,
name|ManagerNoteDisplay
argument_list|(
literal|"mgrNoteDisplay"
argument_list|,
name|CommonValues
operator|.
name|NoteAsIcon
argument_list|,
literal|"Display an icon or shortened text when a class has a note to the schedule manager."
argument_list|)
block|,
name|GridOrientation
argument_list|(
literal|"timeGrid"
argument_list|,
name|CommonValues
operator|.
name|VerticalGrid
argument_list|,
literal|"Time grid display format"
argument_list|)
block|,
name|GridSize
argument_list|(
literal|"timeGridSize"
argument_list|,
literal|"Workdays x Daytime"
argument_list|,
literal|"Time grid default selection"
argument_list|)
block|,
name|NameFormat
argument_list|(
literal|"name"
argument_list|,
name|CommonValues
operator|.
name|NameLastInitial
argument_list|,
literal|"Instructor name display format"
argument_list|)
block|,
name|ClassesKeepSort
argument_list|(
literal|"keepSort"
argument_list|,
name|CommonValues
operator|.
name|No
argument_list|,
literal|"Sort classes on detail pages based on Classes page sorting options."
argument_list|)
block|,
name|ConfirmationDialogs
argument_list|(
literal|"jsConfirm"
argument_list|,
name|CommonValues
operator|.
name|Yes
argument_list|,
literal|"Display confirmation dialogs"
argument_list|)
block|,
name|InheritInstructorPrefs
argument_list|(
literal|"inheritInstrPref"
argument_list|,
name|CommonValues
operator|.
name|Never
argument_list|,
literal|"Inherit instructor preferences on a class"
argument_list|)
block|,
name|DisplayLastChanges
argument_list|(
literal|"dispLastChanges"
argument_list|,
name|CommonValues
operator|.
name|Yes
argument_list|,
literal|"Display last changes"
argument_list|)
block|,
name|DispInstructorPrefs
argument_list|(
literal|"InstructorDetail.distPref"
argument_list|,
name|CommonValues
operator|.
name|Yes
argument_list|,
literal|"Display instructor preferences"
argument_list|)
block|,
name|VariableClassLimits
argument_list|(
literal|"showVarLimits"
argument_list|,
name|CommonValues
operator|.
name|No
argument_list|,
literal|"Show the option to set variable class limits"
argument_list|)
block|,
name|ConfigAutoCalc
argument_list|(
literal|"cfgAutoCalc"
argument_list|,
name|CommonValues
operator|.
name|Yes
argument_list|,
literal|"Automatically calculate number of classes and room size when editing configuration"
argument_list|)
block|,
name|SortNames
argument_list|(
literal|"instrNameSort"
argument_list|,
name|CommonValues
operator|.
name|SortByLastName
argument_list|,
literal|"Sort instructor names"
argument_list|)
block|,
name|RoomFeaturesInOneColumn
argument_list|(
literal|"roomFeaturesInOneColumn"
argument_list|,
name|CommonValues
operator|.
name|Yes
argument_list|,
literal|"Display Room Features In One Column"
argument_list|)
block|,
name|HighlighClassPreferences
argument_list|(
literal|"highlightClassPrefs"
argument_list|,
name|CommonValues
operator|.
name|UseSystemDefault
argument_list|,
literal|"Highlight preferences that are set directly on classes"
argument_list|)
block|,
name|PrimaryCampus
argument_list|(
literal|"primaryAcademicInitiative"
argument_list|,
literal|"Primary academic initiative"
argument_list|)
block|, 	;
name|String
name|iKey
decl_stmt|,
name|iDefault
decl_stmt|,
name|iDescription
decl_stmt|;
name|UserProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|defaultValue
parameter_list|,
name|String
name|description
parameter_list|)
block|{
name|iKey
operator|=
name|key
expr_stmt|;
name|iDefault
operator|=
name|defaultValue
expr_stmt|;
name|iDescription
operator|=
name|defaultValue
expr_stmt|;
block|}
name|UserProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|description
parameter_list|)
block|{
name|this
argument_list|(
name|key
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
name|UserProperty
parameter_list|(
name|String
name|key
parameter_list|,
name|CommonValues
name|defaultValue
parameter_list|,
name|String
name|description
parameter_list|)
block|{
name|this
argument_list|(
name|key
argument_list|,
name|defaultValue
operator|.
name|value
argument_list|()
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|key
parameter_list|()
block|{
return|return
name|iKey
return|;
block|}
specifier|public
name|String
name|defaultValue
parameter_list|()
block|{
return|return
name|iDefault
return|;
block|}
specifier|public
name|String
name|description
parameter_list|()
block|{
return|return
name|iDescription
return|;
block|}
specifier|public
name|String
name|get
parameter_list|(
name|UserContext
name|user
parameter_list|)
block|{
return|return
name|user
operator|==
literal|null
condition|?
name|defaultValue
argument_list|()
else|:
name|user
operator|.
name|getProperty
argument_list|(
name|this
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

