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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|base
operator|.
name|BaseExamStatus
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
name|ExamStatusDAO
import|;
end_import

begin_class
specifier|public
class|class
name|ExamStatus
extends|extends
name|BaseExamStatus
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|ExamStatus
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|ExamStatus
name|findStatus
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|typeId
parameter_list|)
block|{
return|return
name|findStatus
argument_list|(
literal|null
argument_list|,
name|sessionId
argument_list|,
name|typeId
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|ExamStatus
name|findStatus
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|Long
name|typeId
parameter_list|)
block|{
return|return
operator|(
name|ExamStatus
operator|)
operator|(
name|hibSession
operator|!=
literal|null
condition|?
name|hibSession
else|:
name|ExamStatusDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
operator|.
name|createQuery
argument_list|(
literal|"from ExamStatus where session.uniqueId = :sessionId and type.uniqueId = :typeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"typeId"
argument_list|,
name|typeId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|ExamStatus
argument_list|>
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
name|findAll
argument_list|(
literal|null
argument_list|,
name|sessionId
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
argument_list|<
name|ExamStatus
argument_list|>
name|findAll
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|List
argument_list|<
name|ExamStatus
argument_list|>
operator|)
operator|(
name|hibSession
operator|!=
literal|null
condition|?
name|hibSession
else|:
name|ExamStatusDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|)
operator|.
name|createQuery
argument_list|(
literal|"from ExamStatus where session.uniqueId = :sessionId and type.uniqueId = :typeId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
name|DepartmentStatusType
name|effectiveStatus
parameter_list|()
block|{
return|return
name|getStatus
argument_list|()
operator|==
literal|null
condition|?
name|getSession
argument_list|()
operator|.
name|getStatusType
argument_list|()
else|:
name|getStatus
argument_list|()
return|;
block|}
block|}
end_class

end_unit
