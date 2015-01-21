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
name|ArrayList
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
name|hibernate
operator|.
name|HibernateException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
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
name|base
operator|.
name|BaseAcademicArea
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
name|AcademicAreaDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|AcademicArea
extends|extends
name|BaseAcademicArea
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|AcademicArea
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|AcademicArea
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** Request Attribute name for Academic Area **/
specifier|public
specifier|static
specifier|final
name|String
name|ACAD_AREA_REQUEST_ATTR
init|=
literal|"academicAreas"
decl_stmt|;
comment|/* 	 * @return all Academic Areas 	 */
specifier|public
specifier|static
name|ArrayList
name|getAll
parameter_list|()
throws|throws
name|HibernateException
block|{
return|return
operator|(
name|ArrayList
operator|)
operator|(
operator|new
name|AcademicAreaDAO
argument_list|()
operator|)
operator|.
name|findAll
argument_list|()
return|;
block|}
comment|/** 	 * Retrieves all academic areas in the database for the academic session 	 * ordered by academic area abbreviation 	 * @param sessionId academic session 	 * @return Vector of AcademicArea objects 	 */
specifier|public
specifier|static
name|List
name|getAcademicAreaList
parameter_list|(
name|Long
name|sessionId
parameter_list|)
throws|throws
name|HibernateException
block|{
name|AcademicAreaDAO
name|adao
init|=
operator|new
name|AcademicAreaDAO
argument_list|()
decl_stmt|;
name|Session
name|hibSession
init|=
name|adao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|List
name|l
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select a from AcademicArea as a where a.session.uniqueId=:sessionId "
operator|+
literal|"order by a.academicAreaAbbreviation"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
return|return
name|l
return|;
block|}
comment|/**      * Creates label of the format Abbr - Short Title       * @return      */
specifier|public
name|String
name|getLabelAbbrTitle
parameter_list|()
block|{
return|return
name|this
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|+
literal|" : "
operator|+
name|this
operator|.
name|getTitle
argument_list|()
return|;
block|}
comment|/**      * Creates label of the format Short Title - Abbr      * @return      */
specifier|public
name|String
name|getLabelTitleAbbr
parameter_list|()
block|{
return|return
name|this
operator|.
name|getTitle
argument_list|()
operator|+
literal|" : "
operator|+
name|this
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
return|;
block|}
specifier|public
name|Long
name|getSessionId
parameter_list|()
block|{
if|if
condition|(
name|getSession
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
name|getSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
block|}
specifier|public
specifier|static
name|AcademicArea
name|findByAbbv
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|abbv
parameter_list|)
block|{
return|return
operator|(
name|findByAbbv
argument_list|(
operator|new
name|AcademicAreaDAO
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|sessionId
argument_list|,
name|abbv
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|AcademicArea
name|findByAbbv
parameter_list|(
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|String
name|abbv
parameter_list|)
block|{
return|return
operator|(
name|AcademicArea
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select a from AcademicArea a where "
operator|+
literal|"a.session.uniqueId=:sessionId and "
operator|+
literal|"a.academicAreaAbbreviation=:abbv"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"abbv"
argument_list|,
name|abbv
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
name|AcademicArea
name|findByExternalId
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
name|findByExternalId
argument_list|(
operator|new
name|AcademicAreaDAO
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|sessionId
argument_list|,
name|externalId
argument_list|)
operator|)
return|;
block|}
specifier|public
specifier|static
name|AcademicArea
name|findByExternalId
parameter_list|(
name|Session
name|hibSession
parameter_list|,
name|Long
name|sessionId
parameter_list|,
name|String
name|externalId
parameter_list|)
block|{
return|return
operator|(
name|AcademicArea
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select a from AcademicArea a where "
operator|+
literal|"a.session.uniqueId=:sessionId and "
operator|+
literal|"a.externalUniqueId=:externalId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
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
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|AcademicArea
name|area
init|=
operator|new
name|AcademicArea
argument_list|()
decl_stmt|;
name|area
operator|.
name|setExternalUniqueId
argument_list|(
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|area
operator|.
name|setAcademicAreaAbbreviation
argument_list|(
name|getAcademicAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|area
operator|.
name|setTitle
argument_list|(
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|area
return|;
block|}
block|}
end_class

end_unit

