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
name|Collections
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
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|java
operator|.
name|util
operator|.
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|dom4j
operator|.
name|Document
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
name|TravelTimesExport
extends|extends
name|BaseExport
block|{
annotation|@
name|Override
specifier|public
name|void
name|saveXml
parameter_list|(
name|Document
name|document
parameter_list|,
name|Session
name|session
parameter_list|,
name|Properties
name|parameters
parameter_list|)
throws|throws
name|Exception
block|{
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|addElement
argument_list|(
literal|"traveltimes"
argument_list|)
decl_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"campus"
argument_list|,
name|session
operator|.
name|getAcademicInitiative
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"year"
argument_list|,
name|session
operator|.
name|getAcademicYear
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"term"
argument_list|,
name|session
operator|.
name|getAcademicTerm
argument_list|()
argument_list|)
expr_stmt|;
name|root
operator|.
name|addAttribute
argument_list|(
literal|"created"
argument_list|,
operator|new
name|Date
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|document
operator|.
name|addDocType
argument_list|(
literal|"traveltimes"
argument_list|,
literal|"-//UniTime//UniTime Travel Times DTD/EN"
argument_list|,
literal|"http://www.unitime.org/interface/TravelTimes.dtd"
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Long
argument_list|,
name|Map
argument_list|<
name|Long
argument_list|,
name|Integer
argument_list|>
argument_list|>
name|matrix
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Map
argument_list|<
name|Long
argument_list|,
name|Integer
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|TravelTime
name|travel
range|:
operator|(
name|List
argument_list|<
name|TravelTime
argument_list|>
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from TravelTime where session.uniqueId = :sessionId"
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
name|Map
argument_list|<
name|Long
argument_list|,
name|Integer
argument_list|>
name|m
init|=
name|matrix
operator|.
name|get
argument_list|(
name|travel
operator|.
name|getLocation1Id
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|==
literal|null
condition|)
block|{
name|m
operator|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|Integer
argument_list|>
argument_list|()
expr_stmt|;
name|matrix
operator|.
name|put
argument_list|(
name|travel
operator|.
name|getLocation1Id
argument_list|()
argument_list|,
name|m
argument_list|)
expr_stmt|;
block|}
name|m
operator|.
name|put
argument_list|(
name|travel
operator|.
name|getLocation2Id
argument_list|()
argument_list|,
name|travel
operator|.
name|getDistance
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|Location
argument_list|>
name|locations
init|=
name|Location
operator|.
name|findAll
argument_list|(
name|session
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|locations
argument_list|)
expr_stmt|;
for|for
control|(
name|Location
name|from
range|:
name|locations
control|)
block|{
name|Map
argument_list|<
name|Long
argument_list|,
name|Integer
argument_list|>
name|m
init|=
name|matrix
operator|.
name|get
argument_list|(
name|from
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|==
literal|null
operator|||
name|m
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|Element
name|fromEl
init|=
name|root
operator|.
name|addElement
argument_list|(
literal|"from"
argument_list|)
decl_stmt|;
name|fillLocationData
argument_list|(
name|from
argument_list|,
name|fromEl
argument_list|)
expr_stmt|;
for|for
control|(
name|Location
name|to
range|:
name|locations
control|)
block|{
name|Integer
name|distance
init|=
name|m
operator|.
name|get
argument_list|(
name|to
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|distance
operator|==
literal|null
condition|)
continue|continue;
name|Element
name|toEl
init|=
name|fromEl
operator|.
name|addElement
argument_list|(
literal|"to"
argument_list|)
decl_stmt|;
name|fillLocationData
argument_list|(
name|to
argument_list|,
name|toEl
argument_list|)
expr_stmt|;
name|toEl
operator|.
name|setText
argument_list|(
name|distance
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
block|}
block|}
specifier|private
name|void
name|fillLocationData
parameter_list|(
name|Location
name|location
parameter_list|,
name|Element
name|element
parameter_list|)
block|{
if|if
condition|(
name|location
operator|instanceof
name|Room
condition|)
block|{
name|Room
name|room
init|=
operator|(
name|Room
operator|)
name|location
decl_stmt|;
name|element
operator|.
name|addAttribute
argument_list|(
literal|"building"
argument_list|,
name|room
operator|.
name|getBuilding
argument_list|()
operator|.
name|getAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|element
operator|.
name|addAttribute
argument_list|(
literal|"roomNbr"
argument_list|,
name|room
operator|.
name|getRoomNumber
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|element
operator|.
name|addAttribute
argument_list|(
literal|"name"
argument_list|,
name|location
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|location
operator|.
name|getExternalUniqueId
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|location
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
name|element
operator|.
name|addAttribute
argument_list|(
literal|"id"
argument_list|,
name|location
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

