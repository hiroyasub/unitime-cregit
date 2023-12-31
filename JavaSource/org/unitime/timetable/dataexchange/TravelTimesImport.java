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
name|Iterator
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
name|Location
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
name|NonUniversityLocation
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
name|Room
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
name|TravelTime
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TravelTimesImport
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
literal|"traveltimes"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a Travel Times load file."
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
name|info
argument_list|(
literal|"Deleting existing travel times..."
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"delete from TravelTime where session.uniqueId = :sessionId"
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
name|executeUpdate
argument_list|()
expr_stmt|;
name|info
argument_list|(
literal|"Importing travel times..."
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|root
operator|.
name|elementIterator
argument_list|(
literal|"from"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|fromEl
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|Location
name|from
init|=
name|findLocation
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|fromEl
argument_list|)
decl_stmt|;
if|if
condition|(
name|from
operator|==
literal|null
condition|)
continue|continue;
for|for
control|(
name|Iterator
name|j
init|=
name|fromEl
operator|.
name|elementIterator
argument_list|(
literal|"to"
argument_list|)
init|;
name|j
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|toEl
init|=
operator|(
name|Element
operator|)
name|j
operator|.
name|next
argument_list|()
decl_stmt|;
name|Location
name|to
init|=
name|findLocation
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|toEl
argument_list|)
decl_stmt|;
if|if
condition|(
name|to
operator|==
literal|null
condition|)
continue|continue;
name|TravelTime
name|time
init|=
operator|new
name|TravelTime
argument_list|()
decl_stmt|;
name|time
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|time
operator|.
name|setLocation1Id
argument_list|(
name|Math
operator|.
name|min
argument_list|(
name|from
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|to
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|time
operator|.
name|setLocation2Id
argument_list|(
name|Math
operator|.
name|max
argument_list|(
name|from
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|to
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|time
operator|.
name|setDistance
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|toEl
operator|.
name|getTextTrim
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|time
argument_list|)
expr_stmt|;
block|}
block|}
name|info
argument_list|(
literal|"All done."
argument_list|)
expr_stmt|;
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
specifier|private
name|Location
name|findLocation
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Element
name|element
parameter_list|)
block|{
if|if
condition|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Room
name|room
init|=
operator|(
name|Room
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select r from Room r where r.externalUniqueId=:externalId and r.building.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"id"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|room
operator|!=
literal|null
condition|)
return|return
name|room
return|;
block|}
if|if
condition|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"building"
argument_list|)
operator|!=
literal|null
operator|&&
name|element
operator|.
name|attributeValue
argument_list|(
literal|"roomNbr"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Room
name|room
init|=
operator|(
name|Room
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select  r from Room r where r.roomNumber=:roomNbr and r.building.abbreviation = :building and r.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"building"
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"building"
argument_list|)
argument_list|)
operator|.
name|setString
argument_list|(
literal|"roomNbr"
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"roomNbr"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|room
operator|!=
literal|null
condition|)
return|return
name|room
return|;
block|}
if|if
condition|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|Room
name|room
init|=
operator|(
name|Room
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select  r from Room r where (r.building.abbreviation || ' ' || r.roomNumber) = :name and r.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|room
operator|!=
literal|null
condition|)
return|return
name|room
return|;
name|NonUniversityLocation
name|location
init|=
operator|(
name|NonUniversityLocation
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select  r from NonUniversityLocation r where r.name = :name and r.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
decl_stmt|;
if|if
condition|(
name|location
operator|!=
literal|null
condition|)
return|return
name|location
return|;
block|}
name|warn
argument_list|(
literal|"Location "
operator|+
name|element
operator|.
name|asXML
argument_list|()
operator|+
literal|" not found."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

