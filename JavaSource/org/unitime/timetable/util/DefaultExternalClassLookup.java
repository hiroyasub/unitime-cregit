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
name|util
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
name|interfaces
operator|.
name|ExternalClassLookupInterface
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
name|Class_
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
name|dao
operator|.
name|CourseOfferingDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|DefaultExternalClassLookup
implements|implements
name|ExternalClassLookupInterface
block|{
annotation|@
name|Override
specifier|public
name|CourseOffering
name|findCourseByExternalId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|externalId
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct co from Class_ c inner join c.schedulingSubpart.instrOfferingConfig.instructionalOffering io inner join io.courseOfferings co "
operator|+
literal|"where io.session.uniqueId = :sessionId and c.externalUniqueId = :externalId and co.isControl = true"
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
name|externalId
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
return|;
block|}
annotation|@
name|Override
specifier|public
name|List
argument_list|<
name|Class_
argument_list|>
name|findClassesByExternalId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|externalId
parameter_list|)
block|{
return|return
operator|(
name|List
argument_list|<
name|Class_
argument_list|>
operator|)
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from Class_ c inner join c.schedulingSubpart.instrOfferingConfig.instructionalOffering io "
operator|+
literal|"where io.session.uniqueId = :sessionId and c.externalUniqueId = :externalId"
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
name|externalId
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
block|}
end_class

end_unit
