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
name|dataexchange
package|;
end_package

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
name|Iterator
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
name|dom4j
operator|.
name|Element
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
name|StudentGroupType
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|StudentGroupImport
extends|extends
name|BaseImport
block|{
specifier|public
name|void
name|loadXml
parameter_list|(
name|Element
name|root
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"studentGroups"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not student groups load file."
argument_list|)
throw|;
block|}
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|String
name|campus
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"campus"
argument_list|)
decl_stmt|;
name|String
name|year
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"year"
argument_list|)
decl_stmt|;
name|String
name|term
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"term"
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
name|Session
operator|.
name|getSessionUsingInitiativeYearTerm
argument_list|(
name|campus
argument_list|,
name|year
argument_list|,
name|term
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"No session found for the given campus, year, and term."
argument_list|)
throw|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
name|id2group
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
name|code2group
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|StudentGroup
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentGroup
name|group
range|:
operator|(
name|List
argument_list|<
name|StudentGroup
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from StudentGroup where session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
if|if
condition|(
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
condition|)
name|id2group
operator|.
name|put
argument_list|(
name|group
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|,
name|group
argument_list|)
expr_stmt|;
name|code2group
operator|.
name|put
argument_list|(
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
argument_list|,
name|group
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|it
init|=
name|root
operator|.
name|elementIterator
argument_list|()
init|;
name|it
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|element
init|=
operator|(
name|Element
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|externalId
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
decl_stmt|;
name|String
name|code
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"code"
argument_list|)
decl_stmt|;
name|String
name|name
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
name|String
name|size
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"size"
argument_list|)
decl_stmt|;
name|String
name|type
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"type"
argument_list|)
decl_stmt|;
name|StudentGroup
name|group
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|externalId
operator|!=
literal|null
condition|)
name|group
operator|=
name|id2group
operator|.
name|remove
argument_list|(
name|externalId
argument_list|)
expr_stmt|;
if|if
condition|(
name|group
operator|==
literal|null
condition|)
name|group
operator|=
name|code2group
operator|.
name|get
argument_list|(
name|code
argument_list|)
expr_stmt|;
if|if
condition|(
name|group
operator|==
literal|null
condition|)
block|{
name|group
operator|=
operator|new
name|StudentGroup
argument_list|()
expr_stmt|;
name|group
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|info
argument_list|(
literal|"Group "
operator|+
name|code
operator|+
operator|(
name|externalId
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|externalId
operator|+
literal|")"
operator|)
operator|+
literal|" created."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|info
argument_list|(
literal|"Group "
operator|+
name|code
operator|+
operator|(
name|externalId
operator|==
literal|null
condition|?
literal|""
else|:
literal|" ("
operator|+
name|externalId
operator|+
literal|")"
operator|)
operator|+
literal|" updated."
argument_list|)
expr_stmt|;
block|}
name|group
operator|.
name|setExternalUniqueId
argument_list|(
name|externalId
argument_list|)
expr_stmt|;
name|group
operator|.
name|setGroupAbbreviation
argument_list|(
name|code
argument_list|)
expr_stmt|;
name|group
operator|.
name|setGroupName
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|group
operator|.
name|setType
argument_list|(
name|type
operator|==
literal|null
condition|?
literal|null
else|:
name|StudentGroupType
operator|.
name|findByReference
argument_list|(
name|type
argument_list|,
name|getHibSession
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|group
operator|.
name|setExpectedSize
argument_list|(
name|size
operator|==
literal|null
operator|||
name|size
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
name|Integer
operator|.
name|valueOf
argument_list|(
name|size
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|group
operator|.
name|setExpectedSize
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|StudentGroup
name|group
range|:
name|id2group
operator|.
name|values
argument_list|()
control|)
block|{
name|info
argument_list|(
literal|"Group "
operator|+
name|group
operator|.
name|getGroupAbbreviation
argument_list|()
operator|+
literal|" ("
operator|+
name|group
operator|.
name|getExternalUniqueId
argument_list|()
operator|+
literal|") deleted."
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|group
argument_list|)
expr_stmt|;
block|}
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
name|fatal
argument_list|(
literal|"Exception: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|rollbackTransaction
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
block|}
block|}
end_class

end_unit

