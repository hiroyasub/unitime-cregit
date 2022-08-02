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
name|action
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|convention
operator|.
name|annotation
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|convention
operator|.
name|annotation
operator|.
name|Result
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|tiles
operator|.
name|annotation
operator|.
name|TilesDefinition
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts2
operator|.
name|tiles
operator|.
name|annotation
operator|.
name|TilesPutAttribute
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
name|SessionAttribute
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
name|dao
operator|.
name|DepartmentDAO
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
comment|/**   * @author Tomas Muller, Zuzana Mullerova  */
end_comment

begin_class
annotation|@
name|Action
argument_list|(
name|value
operator|=
literal|"instructorAdd"
argument_list|,
name|results
operator|=
block|{
annotation|@
name|Result
argument_list|(
name|name
operator|=
literal|"showAdd"
argument_list|,
name|type
operator|=
literal|"tiles"
argument_list|,
name|location
operator|=
literal|"instructorAdd.tiles"
argument_list|)
block|,
block|}
argument_list|)
annotation|@
name|TilesDefinition
argument_list|(
name|name
operator|=
literal|"instructorAdd.tiles"
argument_list|,
name|extend
operator|=
literal|"baseLayout"
argument_list|,
name|putAttributes
operator|=
block|{
annotation|@
name|TilesPutAttribute
argument_list|(
name|name
operator|=
literal|"title"
argument_list|,
name|value
operator|=
literal|"Add Instructor"
argument_list|)
block|,
annotation|@
name|TilesPutAttribute
argument_list|(
name|name
operator|=
literal|"body"
argument_list|,
name|value
operator|=
literal|"/user/instructorAdd.jsp"
argument_list|)
block|}
argument_list|)
specifier|public
class|class
name|InstructorAddAction
extends|extends
name|InstructorAction
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3203081761214701242L
decl_stmt|;
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
name|String
name|execute
parameter_list|()
throws|throws
name|Exception
block|{
name|super
operator|.
name|execute
argument_list|()
expr_stmt|;
name|form
operator|.
name|setMatchFound
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|// Cancel adding an instructor - Go back to Instructors screen
if|if
condition|(
name|MSG
operator|.
name|actionBackToInstructors
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructorSearch.action"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|//get department
if|if
condition|(
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|String
name|deptId
init|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentId
argument_list|)
decl_stmt|;
name|Department
name|d
init|=
operator|new
name|DepartmentDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|deptId
argument_list|)
argument_list|)
decl_stmt|;
name|form
operator|.
name|setDeptName
argument_list|(
name|d
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|form
operator|.
name|setDeptCode
argument_list|(
name|d
operator|.
name|getDeptCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|form
operator|.
name|getDeptCode
argument_list|()
argument_list|,
literal|"Department"
argument_list|,
name|Right
operator|.
name|InstructorAdd
argument_list|)
expr_stmt|;
comment|//update - Update the instructor and go back to Instructor List Screen
if|if
condition|(
name|MSG
operator|.
name|actionSaveInstructor
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|form
operator|.
name|validate
argument_list|(
name|this
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|hasFieldErrors
argument_list|()
operator|&&
name|isDeptInstructorUnique
argument_list|()
condition|)
block|{
name|doUpdate
argument_list|()
expr_stmt|;
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"instructorSearch.action"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|hasFieldErrors
argument_list|()
condition|)
block|{
name|addFieldError
argument_list|(
literal|"uniqueId"
argument_list|,
name|MSG
operator|.
name|errorInstructorIdAlreadyExistsInList
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|"showAdd"
return|;
block|}
block|}
comment|// lookup
if|if
condition|(
name|MSG
operator|.
name|actionLookupInstructor
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|form
operator|.
name|validate
argument_list|(
name|this
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|hasFieldErrors
argument_list|()
condition|)
block|{
name|findMatchingInstructor
argument_list|()
expr_stmt|;
if|if
condition|(
name|form
operator|.
name|getMatchFound
argument_list|()
operator|==
literal|null
operator|||
operator|!
name|form
operator|.
name|getMatchFound
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|addFieldError
argument_list|(
literal|"lookup"
argument_list|,
name|MSG
operator|.
name|errorNoMatchingRecordsFound
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
literal|"showAdd"
return|;
block|}
comment|// search select
if|if
condition|(
name|MSG
operator|.
name|actionSelectInstructor
argument_list|()
operator|.
name|equals
argument_list|(
name|op
argument_list|)
condition|)
block|{
name|String
name|select
init|=
name|form
operator|.
name|getSearchSelect
argument_list|()
decl_stmt|;
if|if
condition|(
name|select
operator|!=
literal|null
operator|&&
name|select
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
if|if
condition|(
name|select
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"i2a2"
argument_list|)
condition|)
block|{
name|fillI2A2Info
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|fillStaffInfo
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|addFieldError
argument_list|(
literal|"lookup"
argument_list|,
name|MSG
operator|.
name|errorNoInstructorSelectedFromList
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|"showAdd"
return|;
block|}
return|return
literal|"showAdd"
return|;
block|}
block|}
end_class

end_unit

