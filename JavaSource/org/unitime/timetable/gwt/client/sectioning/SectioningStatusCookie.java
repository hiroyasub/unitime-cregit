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
name|gwt
operator|.
name|client
operator|.
name|sectioning
package|;
end_package

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
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|user
operator|.
name|client
operator|.
name|Cookies
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|SectioningStatusCookie
block|{
specifier|private
specifier|static
name|SectioningStatusCookie
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|private
name|int
name|iOnlineTab
init|=
literal|0
decl_stmt|,
name|iBashTab
init|=
literal|0
decl_stmt|;
specifier|private
name|String
name|iOnlineQuery
init|=
literal|""
decl_stmt|,
name|iBashQuery
init|=
literal|""
decl_stmt|;
specifier|private
name|int
index|[]
name|iSortBy
init|=
operator|new
name|int
index|[]
block|{
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|,
literal|0
block|}
decl_stmt|;
specifier|private
name|int
name|iStudentTab
init|=
literal|2
decl_stmt|;
specifier|private
name|String
index|[]
name|iSortByGroup
init|=
operator|new
name|String
index|[]
block|{
literal|""
block|,
literal|""
block|}
decl_stmt|;
specifier|private
name|boolean
name|iEmailIncludeCourseRequests
init|=
literal|false
decl_stmt|,
name|iEmailIncludeClassSchedule
init|=
literal|true
decl_stmt|,
name|iEmailAdvisorRequests
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iEmailSubject
init|=
literal|""
decl_stmt|,
name|iEmailCC
init|=
literal|""
decl_stmt|;
specifier|private
name|boolean
name|iAdvisorRequestsEmailStudent
decl_stmt|;
specifier|private
name|Boolean
name|iOptionalEmailToggle
init|=
literal|null
decl_stmt|;
specifier|private
name|SectioningStatusCookie
parameter_list|()
block|{
try|try
block|{
name|String
name|cookie
init|=
name|Cookies
operator|.
name|getCookie
argument_list|(
literal|"UniTime:StudentStatus"
argument_list|)
decl_stmt|;
if|if
condition|(
name|cookie
operator|!=
literal|null
condition|)
block|{
name|String
index|[]
name|params
init|=
name|cookie
operator|.
name|split
argument_list|(
literal|"\\|"
argument_list|)
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|iOnlineTab
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iOnlineQuery
operator|=
name|params
index|[
name|idx
operator|++
index|]
expr_stmt|;
name|iBashTab
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iBashQuery
operator|=
name|params
index|[
name|idx
operator|++
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iSortBy
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|iSortBy
index|[
name|i
index|]
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iStudentTab
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iSortByGroup
index|[
literal|0
index|]
operator|=
name|params
index|[
name|idx
operator|++
index|]
expr_stmt|;
name|iSortByGroup
index|[
literal|1
index|]
operator|=
name|params
index|[
name|idx
operator|++
index|]
expr_stmt|;
name|iEmailIncludeCourseRequests
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iEmailIncludeClassSchedule
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iEmailCC
operator|=
name|params
index|[
name|idx
operator|++
index|]
expr_stmt|;
name|iEmailSubject
operator|=
name|params
index|[
name|idx
operator|++
index|]
expr_stmt|;
name|iEmailAdvisorRequests
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iAdvisorRequestsEmailStudent
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
name|iOptionalEmailToggle
operator|=
name|parseBoolean
argument_list|(
name|params
index|[
name|idx
operator|++
index|]
argument_list|)
expr_stmt|;
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
specifier|private
specifier|static
name|Boolean
name|parseBoolean
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
literal|"1"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
return|return
literal|true
return|;
if|if
condition|(
literal|"0"
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|SectioningStatusCookie
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|SectioningStatusCookie
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
specifier|private
name|void
name|save
parameter_list|()
block|{
name|String
name|cookie
init|=
name|iOnlineTab
operator|+
literal|"|"
operator|+
name|iOnlineQuery
operator|+
literal|"|"
operator|+
name|iBashTab
operator|+
literal|"|"
operator|+
name|iBashQuery
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iSortBy
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|cookie
operator|+=
literal|"|"
operator|+
name|iSortBy
index|[
name|i
index|]
expr_stmt|;
name|cookie
operator|+=
literal|"|"
operator|+
name|iStudentTab
expr_stmt|;
name|cookie
operator|+=
literal|"|"
operator|+
name|iSortByGroup
index|[
literal|0
index|]
operator|+
literal|"|"
operator|+
name|iSortByGroup
index|[
literal|1
index|]
expr_stmt|;
name|cookie
operator|+=
literal|"|"
operator|+
operator|(
name|iEmailIncludeCourseRequests
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iEmailIncludeClassSchedule
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iEmailCC
operator|==
literal|null
condition|?
literal|""
else|:
name|iEmailCC
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iEmailSubject
operator|==
literal|null
condition|?
literal|""
else|:
name|iEmailSubject
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iEmailAdvisorRequests
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iAdvisorRequestsEmailStudent
condition|?
literal|"1"
else|:
literal|"0"
operator|)
operator|+
literal|"|"
operator|+
operator|(
name|iOptionalEmailToggle
operator|==
literal|null
condition|?
literal|"N"
else|:
name|iOptionalEmailToggle
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"1"
else|:
literal|"0"
operator|)
expr_stmt|;
name|Date
name|expires
init|=
operator|new
name|Date
argument_list|(
operator|new
name|Date
argument_list|()
operator|.
name|getTime
argument_list|()
operator|+
literal|604800000l
argument_list|)
decl_stmt|;
comment|// expires in 7 days
name|Cookies
operator|.
name|setCookie
argument_list|(
literal|"UniTime:StudentStatus"
argument_list|,
name|cookie
argument_list|,
name|expires
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getQuery
parameter_list|(
name|boolean
name|online
parameter_list|)
block|{
return|return
name|online
condition|?
name|iOnlineQuery
else|:
name|iBashQuery
return|;
block|}
specifier|public
name|int
name|getTab
parameter_list|(
name|boolean
name|online
parameter_list|)
block|{
return|return
name|online
condition|?
name|iOnlineTab
else|:
name|iBashTab
return|;
block|}
specifier|public
name|void
name|setQueryTab
parameter_list|(
name|boolean
name|online
parameter_list|,
name|String
name|query
parameter_list|,
name|int
name|tab
parameter_list|)
block|{
if|if
condition|(
name|online
condition|)
block|{
name|iOnlineQuery
operator|=
name|query
expr_stmt|;
name|iOnlineTab
operator|=
name|tab
expr_stmt|;
block|}
else|else
block|{
name|iBashQuery
operator|=
name|query
expr_stmt|;
name|iBashTab
operator|=
name|tab
expr_stmt|;
block|}
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getSortBy
parameter_list|(
name|boolean
name|online
parameter_list|,
name|int
name|tab
parameter_list|)
block|{
return|return
name|iSortBy
index|[
name|online
condition|?
name|tab
else|:
literal|3
operator|+
name|tab
index|]
return|;
block|}
specifier|public
name|void
name|setSortBy
parameter_list|(
name|boolean
name|online
parameter_list|,
name|int
name|tab
parameter_list|,
name|int
name|ord
parameter_list|)
block|{
name|iSortBy
index|[
name|online
condition|?
name|tab
else|:
literal|3
operator|+
name|tab
index|]
operator|=
name|ord
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setSortBy
parameter_list|(
name|boolean
name|online
parameter_list|,
name|int
name|tab
parameter_list|,
name|int
name|ord
parameter_list|,
name|String
name|group
parameter_list|)
block|{
name|iSortBy
index|[
name|online
condition|?
name|tab
else|:
literal|3
operator|+
name|tab
index|]
operator|=
name|ord
expr_stmt|;
name|iSortByGroup
index|[
name|online
condition|?
literal|0
else|:
literal|1
index|]
operator|=
name|group
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|String
name|getSortByGroup
parameter_list|(
name|boolean
name|online
parameter_list|)
block|{
return|return
name|iSortByGroup
index|[
name|online
condition|?
literal|0
else|:
literal|1
index|]
return|;
block|}
specifier|public
name|int
name|getStudentTab
parameter_list|()
block|{
return|return
name|iStudentTab
return|;
block|}
specifier|public
name|void
name|setStudentTab
parameter_list|(
name|int
name|tab
parameter_list|)
block|{
name|iStudentTab
operator|=
name|tab
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEmailIncludeCourseRequests
parameter_list|()
block|{
return|return
name|iEmailIncludeCourseRequests
return|;
block|}
specifier|public
name|boolean
name|isEmailIncludeClassSchedule
parameter_list|()
block|{
return|return
name|iEmailIncludeClassSchedule
return|;
block|}
specifier|public
name|boolean
name|isEmailIncludeAdvisorRequests
parameter_list|()
block|{
return|return
name|iEmailAdvisorRequests
return|;
block|}
specifier|public
name|String
name|getEmailCC
parameter_list|()
block|{
return|return
name|iEmailCC
return|;
block|}
specifier|public
name|boolean
name|hasEmailCC
parameter_list|()
block|{
return|return
name|iEmailCC
operator|!=
literal|null
operator|&&
operator|!
name|iEmailCC
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getEmailSubject
parameter_list|()
block|{
return|return
name|iEmailSubject
return|;
block|}
specifier|public
name|boolean
name|hasEmailSubject
parameter_list|()
block|{
return|return
name|iEmailSubject
operator|!=
literal|null
operator|&&
operator|!
name|iEmailSubject
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|void
name|setEmailDefaults
parameter_list|(
name|boolean
name|includeCourseRequests
parameter_list|,
name|boolean
name|includeClassSchedule
parameter_list|,
name|boolean
name|includeAdvisorRequests
parameter_list|,
name|String
name|cc
parameter_list|,
name|String
name|subject
parameter_list|,
name|Boolean
name|optionalEmailToggle
parameter_list|)
block|{
name|iEmailIncludeCourseRequests
operator|=
name|includeCourseRequests
expr_stmt|;
name|iEmailIncludeClassSchedule
operator|=
name|includeClassSchedule
expr_stmt|;
name|iEmailAdvisorRequests
operator|=
name|includeAdvisorRequests
expr_stmt|;
name|iEmailCC
operator|=
name|cc
expr_stmt|;
name|iEmailSubject
operator|=
name|subject
expr_stmt|;
if|if
condition|(
name|optionalEmailToggle
operator|!=
literal|null
condition|)
name|iOptionalEmailToggle
operator|=
name|optionalEmailToggle
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setEmailCC
parameter_list|(
name|String
name|cc
parameter_list|)
block|{
name|iEmailCC
operator|=
name|cc
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isAdvisorRequestsEmailStudent
parameter_list|()
block|{
return|return
name|iAdvisorRequestsEmailStudent
return|;
block|}
specifier|public
name|void
name|setAdvisorRequestsEmailStudent
parameter_list|(
name|boolean
name|email
parameter_list|)
block|{
name|iAdvisorRequestsEmailStudent
operator|=
name|email
expr_stmt|;
name|save
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isOptionalEmailToggle
parameter_list|(
name|boolean
name|defaultValue
parameter_list|)
block|{
return|return
operator|(
name|iOptionalEmailToggle
operator|==
literal|null
condition|?
name|defaultValue
else|:
name|iOptionalEmailToggle
operator|.
name|booleanValue
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

