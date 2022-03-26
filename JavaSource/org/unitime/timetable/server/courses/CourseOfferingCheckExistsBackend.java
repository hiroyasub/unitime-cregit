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
name|courses
package|;
end_package

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
name|shared
operator|.
name|CourseOfferingInterface
operator|.
name|CourseOfferingCheckExists
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
name|CourseOfferingInterface
operator|.
name|CourseOfferingCheckExistsInterface
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
name|dao
operator|.
name|SubjectAreaDAO
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

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|CourseOfferingCheckExists
operator|.
name|class
argument_list|)
specifier|public
class|class
name|CourseOfferingCheckExistsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|CourseOfferingCheckExists
argument_list|,
name|CourseOfferingCheckExistsInterface
argument_list|>
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
annotation|@
name|Override
specifier|public
name|CourseOfferingCheckExistsInterface
name|execute
parameter_list|(
name|CourseOfferingCheckExists
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|CourseOfferingCheckExistsInterface
name|response
init|=
operator|new
name|CourseOfferingCheckExistsInterface
argument_list|()
decl_stmt|;
name|Boolean
name|isEdit
init|=
name|request
operator|.
name|getIsEdit
argument_list|()
decl_stmt|;
name|SubjectArea
name|sa
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSubjectAreaId
argument_list|()
argument_list|)
decl_stmt|;
name|CourseOffering
name|co
init|=
name|CourseOffering
operator|.
name|findBySessionSubjAreaAbbvCourseNbr
argument_list|(
name|sa
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|sa
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|,
name|request
operator|.
name|getCourseNumber
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|isEdit
operator|&&
name|co
operator|!=
literal|null
condition|)
block|{
name|response
operator|.
name|setResponseText
argument_list|(
name|MSG
operator|.
name|errorCourseCannotBeCreated
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|isEdit
operator|&&
name|co
operator|!=
literal|null
operator|&&
operator|!
name|co
operator|.
name|getUniqueId
argument_list|()
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getCourseOfferingId
argument_list|()
argument_list|)
condition|)
block|{
name|response
operator|.
name|setResponseText
argument_list|(
name|MSG
operator|.
name|errorCourseCannotBeRenamed
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|response
operator|.
name|setResponseText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|response
return|;
block|}
block|}
end_class

end_unit

