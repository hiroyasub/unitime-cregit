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
name|hql
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|SavedHQLInterface
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
name|SavedHQLInterface
operator|.
name|HQLOptionsInterface
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
name|SavedHQLInterface
operator|.
name|HQLOptionsRpcRequest
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
name|SavedHQL
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
name|GwtRpcImplements
argument_list|(
name|HQLOptionsRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|HQLOptionsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|HQLOptionsRpcRequest
argument_list|,
name|HQLOptionsInterface
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
specifier|private
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Override
annotation|@
name|PreAuthorize
argument_list|(
literal|"checkPermission('HQLReports')"
argument_list|)
specifier|public
name|HQLOptionsInterface
name|execute
parameter_list|(
name|HQLOptionsRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|HQLOptionsInterface
name|ret
init|=
operator|new
name|HQLOptionsInterface
argument_list|()
decl_stmt|;
for|for
control|(
name|SavedHQL
operator|.
name|Option
name|o
range|:
name|SavedHQL
operator|.
name|Option
operator|.
name|values
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|o
operator|.
name|allowSingleSelection
argument_list|()
operator|&&
operator|!
name|o
operator|.
name|allowMultiSelection
argument_list|()
condition|)
continue|continue;
name|SavedHQLInterface
operator|.
name|Option
name|option
init|=
operator|new
name|SavedHQLInterface
operator|.
name|Option
argument_list|()
decl_stmt|;
name|option
operator|.
name|setMultiSelect
argument_list|(
name|o
operator|.
name|allowMultiSelection
argument_list|()
argument_list|)
expr_stmt|;
name|option
operator|.
name|setName
argument_list|(
name|getLocalizedText
argument_list|(
name|o
argument_list|)
argument_list|)
expr_stmt|;
name|option
operator|.
name|setType
argument_list|(
name|o
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|values
init|=
name|o
operator|.
name|values
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|values
operator|==
literal|null
operator|||
name|values
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|e
range|:
name|values
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|SavedHQLInterface
operator|.
name|IdValue
name|v
init|=
operator|new
name|SavedHQLInterface
operator|.
name|IdValue
argument_list|()
decl_stmt|;
name|v
operator|.
name|setText
argument_list|(
name|e
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|v
operator|.
name|setValue
argument_list|(
name|e
operator|.
name|getKey
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|option
operator|.
name|values
argument_list|()
operator|.
name|add
argument_list|(
name|v
argument_list|)
expr_stmt|;
block|}
name|Collections
operator|.
name|sort
argument_list|(
name|option
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addOption
argument_list|(
name|option
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|SavedHQL
operator|.
name|Flag
name|f
range|:
name|SavedHQL
operator|.
name|Flag
operator|.
name|values
argument_list|()
control|)
block|{
name|SavedHQLInterface
operator|.
name|Flag
name|flag
init|=
operator|new
name|SavedHQLInterface
operator|.
name|Flag
argument_list|()
decl_stmt|;
name|flag
operator|.
name|setValue
argument_list|(
name|f
operator|.
name|flag
argument_list|()
argument_list|)
expr_stmt|;
name|flag
operator|.
name|setText
argument_list|(
name|getLocalizedDescription
argument_list|(
name|f
argument_list|)
argument_list|)
expr_stmt|;
name|flag
operator|.
name|setAppearance
argument_list|(
name|f
operator|.
name|getAppearance
argument_list|()
argument_list|)
expr_stmt|;
name|ret
operator|.
name|addFlag
argument_list|(
name|flag
argument_list|)
expr_stmt|;
block|}
name|ret
operator|.
name|setEditable
argument_list|(
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|HQLReportAdd
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|private
name|String
name|getLocalizedText
parameter_list|(
name|SavedHQL
operator|.
name|Option
name|option
parameter_list|)
block|{
switch|switch
condition|(
name|option
condition|)
block|{
case|case
name|BUILDING
case|:
return|return
name|MESSAGES
operator|.
name|optionBuilding
argument_list|()
return|;
case|case
name|BUILDINGS
case|:
return|return
name|MESSAGES
operator|.
name|optionBuildings
argument_list|()
return|;
case|case
name|DEPARTMENT
case|:
return|return
name|MESSAGES
operator|.
name|optionDepartment
argument_list|()
return|;
case|case
name|DEPARTMENTS
case|:
return|return
name|MESSAGES
operator|.
name|optionDepartments
argument_list|()
return|;
case|case
name|ROOM
case|:
return|return
name|MESSAGES
operator|.
name|optionRoom
argument_list|()
return|;
case|case
name|ROOMS
case|:
return|return
name|MESSAGES
operator|.
name|optionRooms
argument_list|()
return|;
case|case
name|SESSION
case|:
return|return
name|MESSAGES
operator|.
name|optionAcademicSession
argument_list|()
return|;
case|case
name|SUBJECT
case|:
return|return
name|MESSAGES
operator|.
name|optionSubjectArea
argument_list|()
return|;
case|case
name|SUBJECTS
case|:
return|return
name|MESSAGES
operator|.
name|optionSubjectAreas
argument_list|()
return|;
default|default:
return|return
name|option
operator|.
name|text
argument_list|()
return|;
block|}
block|}
specifier|private
name|String
name|getLocalizedDescription
parameter_list|(
name|SavedHQL
operator|.
name|Flag
name|flag
parameter_list|)
block|{
switch|switch
condition|(
name|flag
condition|)
block|{
case|case
name|APPEARANCE_COURSES
case|:
return|return
name|MESSAGES
operator|.
name|flagAppearanceCourses
argument_list|()
return|;
case|case
name|APPEARANCE_EXAMS
case|:
return|return
name|MESSAGES
operator|.
name|flagAppearanceExaminations
argument_list|()
return|;
case|case
name|APPEARANCE_EVENTS
case|:
return|return
name|MESSAGES
operator|.
name|flagAppearanceEvents
argument_list|()
return|;
case|case
name|APPEARANCE_SECTIONING
case|:
return|return
name|MESSAGES
operator|.
name|flagAppearanceStudentSectioning
argument_list|()
return|;
case|case
name|APPEARANCE_ADMINISTRATION
case|:
return|return
name|MESSAGES
operator|.
name|flagAppearanceAdministration
argument_list|()
return|;
case|case
name|ADMIN_ONLY
case|:
return|return
name|MESSAGES
operator|.
name|flagRestrictionsAdministratorOnly
argument_list|()
return|;
default|default:
return|return
name|flag
operator|.
name|description
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

