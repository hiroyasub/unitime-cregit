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
name|onlinesectioning
operator|.
name|updates
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|StudentSectioningMessages
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
name|SectioningException
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
name|StudentNote
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
name|dao
operator|.
name|StudentDAO
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
name|onlinesectioning
operator|.
name|OnlineSectioningAction
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
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
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
name|onlinesectioning
operator|.
name|OnlineSectioningLog
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
operator|.
name|Lock
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XStudent
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XStudentNote
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
name|onlinesectioning
operator|.
name|server
operator|.
name|CheckMaster
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
name|onlinesectioning
operator|.
name|server
operator|.
name|CheckMaster
operator|.
name|Master
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|CheckMaster
argument_list|(
name|Master
operator|.
name|REQUIRED
argument_list|)
specifier|public
class|class
name|ChangeStudentStatus
implements|implements
name|OnlineSectioningAction
argument_list|<
name|Boolean
argument_list|>
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
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|Collection
argument_list|<
name|Long
argument_list|>
name|iStudentIds
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iStatus
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iNote
init|=
literal|null
decl_stmt|;
specifier|public
name|ChangeStudentStatus
name|forStudents
parameter_list|(
name|Collection
argument_list|<
name|Long
argument_list|>
name|studentIds
parameter_list|)
block|{
name|iStudentIds
operator|=
name|studentIds
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ChangeStudentStatus
name|withStatus
parameter_list|(
name|String
name|status
parameter_list|)
block|{
name|iStatus
operator|=
name|status
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|ChangeStudentStatus
name|withNote
parameter_list|(
name|String
name|note
parameter_list|)
block|{
name|iNote
operator|=
name|note
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|String
name|getStatus
parameter_list|()
block|{
return|return
name|iStatus
return|;
block|}
specifier|public
name|boolean
name|hasStatus
parameter_list|()
block|{
return|return
name|iStatus
operator|!=
literal|null
operator|&&
operator|!
name|iStatus
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|changeStatus
parameter_list|()
block|{
return|return
operator|!
literal|"-"
operator|.
name|equals
argument_list|(
name|iStatus
argument_list|)
return|;
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
name|boolean
name|hasNote
parameter_list|()
block|{
return|return
name|iNote
operator|!=
literal|null
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|Long
argument_list|>
name|getStudentIds
parameter_list|()
block|{
return|return
name|iStudentIds
return|;
block|}
annotation|@
name|Override
specifier|public
name|Boolean
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|StudentSectioningStatus
name|status
init|=
operator|(
name|changeStatus
argument_list|()
operator|&&
name|hasStatus
argument_list|()
condition|?
operator|(
name|StudentSectioningStatus
operator|)
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentSectioningStatus where reference = :ref and (session is null or session = :sessionId)"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"ref"
argument_list|,
name|getStatus
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|uniqueResult
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
name|Date
name|ts
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
for|for
control|(
name|Long
name|studentId
range|:
name|getStudentIds
argument_list|()
control|)
block|{
name|Lock
name|lock
init|=
name|server
operator|.
name|lockStudent
argument_list|(
name|studentId
argument_list|,
literal|null
argument_list|,
name|name
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|studentId
argument_list|)
decl_stmt|;
name|helper
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
try|try
block|{
name|Student
name|dbStudent
init|=
name|StudentDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|studentId
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|!=
literal|null
operator|&&
name|dbStudent
operator|!=
literal|null
condition|)
block|{
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|addAction
argument_list|(
name|this
argument_list|,
name|server
operator|.
name|getAcademicSession
argument_list|()
argument_list|)
decl_stmt|;
name|action
operator|.
name|setStudent
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|status
operator|!=
literal|null
condition|)
block|{
name|action
operator|.
name|addOther
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|newBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|status
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|status
operator|.
name|getLabel
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|status
operator|.
name|getReference
argument_list|()
argument_list|)
operator|.
name|setType
argument_list|(
name|OnlineSectioningLog
operator|.
name|Entity
operator|.
name|EntityType
operator|.
name|OTHER
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|hasNote
argument_list|()
condition|)
block|{
name|action
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|getNote
argument_list|()
argument_list|)
operator|.
name|setTimeStamp
argument_list|(
name|ts
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
argument_list|)
expr_stmt|;
name|StudentNote
name|note
init|=
operator|new
name|StudentNote
argument_list|()
decl_stmt|;
name|note
operator|.
name|setStudent
argument_list|(
name|dbStudent
argument_list|)
expr_stmt|;
name|note
operator|.
name|setTextNote
argument_list|(
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
name|note
operator|.
name|setTimeStamp
argument_list|(
name|ts
argument_list|)
expr_stmt|;
name|note
operator|.
name|setUserId
argument_list|(
name|helper
operator|.
name|getUser
argument_list|()
operator|.
name|getExternalId
argument_list|()
argument_list|)
expr_stmt|;
name|dbStudent
operator|.
name|addTonotes
argument_list|(
name|note
argument_list|)
expr_stmt|;
name|student
operator|.
name|setLastNote
argument_list|(
operator|new
name|XStudentNote
argument_list|(
name|note
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|changeStatus
argument_list|()
condition|)
block|{
name|String
name|oldStatus
init|=
operator|(
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|?
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
else|:
name|dbStudent
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|!=
literal|null
condition|?
name|MSG
operator|.
name|studentStatusSessionDefault
argument_list|(
name|dbStudent
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
else|:
name|MSG
operator|.
name|studentStatusSystemDefault
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
name|action
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"old-status"
argument_list|)
operator|.
name|setValue
argument_list|(
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|student
operator|.
name|setStatus
argument_list|(
name|status
operator|==
literal|null
condition|?
literal|null
else|:
name|status
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
name|dbStudent
operator|.
name|setSectioningStatus
argument_list|(
name|status
argument_list|)
expr_stmt|;
name|String
name|newStatus
init|=
operator|(
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|?
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
else|:
name|dbStudent
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|!=
literal|null
condition|?
name|MSG
operator|.
name|studentStatusSessionDefault
argument_list|(
name|dbStudent
operator|.
name|getSession
argument_list|()
operator|.
name|getDefaultSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
else|:
name|MSG
operator|.
name|studentStatusSystemDefault
argument_list|()
operator|)
decl_stmt|;
if|if
condition|(
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|!=
literal|null
condition|)
name|action
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"new-status"
argument_list|)
operator|.
name|setValue
argument_list|(
name|dbStudent
operator|.
name|getSectioningStatus
argument_list|()
operator|.
name|getReference
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|oldStatus
operator|.
name|equals
argument_list|(
name|newStatus
argument_list|)
condition|)
name|action
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|oldStatus
argument_list|)
operator|.
name|setTimeStamp
argument_list|(
name|ts
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|action
operator|.
name|addMessage
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|newBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|oldStatus
operator|+
literal|"&rarr; "
operator|+
name|newStatus
argument_list|)
operator|.
name|setTimeStamp
argument_list|(
name|ts
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|helper
operator|.
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|dbStudent
argument_list|)
expr_stmt|;
name|server
operator|.
name|update
argument_list|(
name|student
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
name|helper
operator|.
name|commitTransaction
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|helper
operator|.
name|rollbackTransaction
argument_list|()
expr_stmt|;
if|if
condition|(
name|e
operator|instanceof
name|SectioningException
condition|)
throw|throw
operator|(
name|SectioningException
operator|)
name|e
throw|;
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionUnknown
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"status-change"
return|;
block|}
block|}
end_class

end_unit

