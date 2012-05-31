begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 (University Timetabling Application)  * Copyright (C) 2012, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|events
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
name|List
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
name|User
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
name|GwtRpcHelper
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
name|server
operator|.
name|LookupServlet
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
name|EventInterface
operator|.
name|EventType
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
name|PersonInterface
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
name|EventInterface
operator|.
name|ContactInterface
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
name|EventInterface
operator|.
name|EventPropertiesRpcRequest
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
name|EventInterface
operator|.
name|EventPropertiesRpcResponse
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
name|EventInterface
operator|.
name|SponsoringOrganizationInterface
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
name|EventContact
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
name|SponsoringOrganization
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
name|StandardEventNote
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
name|StandardEventNoteDAO
import|;
end_import

begin_class
specifier|public
class|class
name|EventPropertiesBackend
extends|extends
name|EventAction
argument_list|<
name|EventPropertiesRpcRequest
argument_list|,
name|EventPropertiesRpcResponse
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|EventPropertiesRpcResponse
name|execute
parameter_list|(
name|EventPropertiesRpcRequest
name|request
parameter_list|,
name|GwtRpcHelper
name|helper
parameter_list|,
name|EventRights
name|rights
parameter_list|)
block|{
name|EventPropertiesRpcResponse
name|response
init|=
operator|new
name|EventPropertiesRpcResponse
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|request
operator|.
name|getSessionId
argument_list|()
argument_list|)
decl_stmt|;
name|response
operator|.
name|setCanLookupPeople
argument_list|(
name|rights
operator|.
name|canSeeSchedule
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanLookupContacts
argument_list|(
name|rights
operator|.
name|canLookupContacts
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanAddEvent
argument_list|(
name|rights
operator|.
name|canAddEvent
argument_list|(
name|EventType
operator|.
name|Special
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|response
operator|.
name|setCanAddCourseEvent
argument_list|(
name|rights
operator|.
name|canAddEvent
argument_list|(
name|EventType
operator|.
name|Course
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|setupSponsoringOrganizations
argument_list|(
name|session
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|setupMainContact
argument_list|(
name|session
argument_list|,
name|response
argument_list|,
name|helper
operator|.
name|getUser
argument_list|()
argument_list|)
expr_stmt|;
name|setupStandardNotes
argument_list|(
name|session
argument_list|,
name|response
argument_list|)
expr_stmt|;
return|return
name|response
return|;
block|}
specifier|public
name|void
name|setupSponsoringOrganizations
parameter_list|(
name|Session
name|session
parameter_list|,
name|EventPropertiesRpcResponse
name|response
parameter_list|)
block|{
for|for
control|(
name|SponsoringOrganization
name|s
range|:
name|SponsoringOrganization
operator|.
name|findAll
argument_list|()
control|)
block|{
name|SponsoringOrganizationInterface
name|sponsor
init|=
operator|new
name|SponsoringOrganizationInterface
argument_list|()
decl_stmt|;
name|sponsor
operator|.
name|setUniqueId
argument_list|(
name|s
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|sponsor
operator|.
name|setName
argument_list|(
name|s
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|sponsor
operator|.
name|setEmail
argument_list|(
name|s
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|addSponsoringOrganization
argument_list|(
name|sponsor
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setupMainContact
parameter_list|(
name|Session
name|session
parameter_list|,
name|EventPropertiesRpcResponse
name|response
parameter_list|,
name|User
name|user
parameter_list|)
block|{
if|if
condition|(
name|user
operator|==
literal|null
condition|)
return|return;
name|EventContact
name|contact
init|=
name|EventContact
operator|.
name|findByExternalUniqueId
argument_list|(
name|user
operator|.
name|getId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|contact
operator|!=
literal|null
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|contact
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|contact
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|contact
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|contact
operator|.
name|getEmailAddress
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setPhone
argument_list|(
name|contact
operator|.
name|getPhone
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|contact
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setMainContact
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|PersonInterface
argument_list|>
name|people
init|=
operator|new
name|LookupServlet
argument_list|()
operator|.
name|lookupPeople
argument_list|(
name|user
operator|.
name|getName
argument_list|()
argument_list|,
literal|"mustHaveExternalId"
operator|+
operator|(
name|session
operator|==
literal|null
condition|?
literal|""
else|:
literal|",session="
operator|+
name|session
operator|.
name|getUniqueId
argument_list|()
operator|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|people
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|PersonInterface
name|person
range|:
name|people
control|)
block|{
if|if
condition|(
name|user
operator|.
name|getId
argument_list|()
operator|.
name|equals
argument_list|(
name|person
operator|.
name|getId
argument_list|()
argument_list|)
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|person
operator|.
name|getFirstName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setMiddleName
argument_list|(
name|person
operator|.
name|getMiddleName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|person
operator|.
name|getLastName
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setEmail
argument_list|(
name|person
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setPhone
argument_list|(
name|person
operator|.
name|getPhone
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|setExternalId
argument_list|(
name|person
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|setMainContact
argument_list|(
name|c
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|response
operator|.
name|hasMainContact
argument_list|()
condition|)
block|{
name|ContactInterface
name|c
init|=
operator|new
name|ContactInterface
argument_list|()
decl_stmt|;
name|String
name|name
index|[]
init|=
name|user
operator|.
name|getName
argument_list|()
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|length
operator|==
literal|1
condition|)
block|{
name|c
operator|.
name|setLastName
argument_list|(
name|name
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|name
operator|.
name|length
operator|==
literal|2
condition|)
block|{
name|c
operator|.
name|setFirstName
argument_list|(
name|name
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|name
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|c
operator|.
name|setFirstName
argument_list|(
name|name
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|String
name|mName
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|name
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|++
control|)
name|mName
operator|+=
operator|(
name|mName
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|" "
operator|)
operator|+
name|name
index|[
name|i
index|]
expr_stmt|;
name|c
operator|.
name|setFirstName
argument_list|(
name|mName
argument_list|)
expr_stmt|;
name|c
operator|.
name|setLastName
argument_list|(
name|name
index|[
name|name
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
name|c
operator|.
name|setExternalId
argument_list|(
name|user
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|setupStandardNotes
parameter_list|(
name|Session
name|session
parameter_list|,
name|EventPropertiesRpcResponse
name|response
parameter_list|)
block|{
for|for
control|(
name|StandardEventNote
name|note
range|:
name|StandardEventNoteDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|findAll
argument_list|()
control|)
name|response
operator|.
name|addStandardNote
argument_list|(
name|note
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|response
operator|.
name|hasStandardNotes
argument_list|()
condition|)
name|Collections
operator|.
name|sort
argument_list|(
name|response
operator|.
name|getStandardNotes
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

