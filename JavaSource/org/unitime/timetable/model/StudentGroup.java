begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|hibernate
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Order
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|criterion
operator|.
name|Restrictions
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
name|BaseStudentGroup
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
name|StudentGroupDAO
import|;
end_import

begin_class
specifier|public
class|class
name|StudentGroup
extends|extends
name|BaseStudentGroup
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
name|StudentGroup
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|StudentGroup
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
comment|/** Request attribute name for available student groups**/
specifier|public
specifier|static
name|String
name|STUGRP_ATTR_NAME
init|=
literal|"studentGroupList"
decl_stmt|;
comment|/** 	 * Retrieves all student groups in the database for the academic session 	 * ordered by column group name 	 * @param sessionId academic session 	 * @return Vector of StudentGroup objects 	 */
specifier|public
specifier|static
name|List
name|getStudentGroupList
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|StudentGroupDAO
name|sdao
init|=
operator|new
name|StudentGroupDAO
argument_list|()
decl_stmt|;
name|Session
name|hibSession
init|=
name|sdao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|List
name|l
init|=
name|hibSession
operator|.
name|createCriteria
argument_list|(
name|StudentGroup
operator|.
name|class
argument_list|)
operator|.
name|add
argument_list|(
name|Restrictions
operator|.
name|eq
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
argument_list|)
operator|.
name|addOrder
argument_list|(
name|Order
operator|.
name|asc
argument_list|(
literal|"groupName"
argument_list|)
argument_list|)
operator|.
name|list
argument_list|()
decl_stmt|;
return|return
name|l
return|;
block|}
specifier|public
specifier|static
name|StudentGroup
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
name|StudentGroup
operator|)
operator|new
name|StudentGroupDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select a from StudentGroup a where "
operator|+
literal|"a.session.uniqueId=:sessionId and "
operator|+
literal|"a.groupAbbreviation=:abbv"
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
block|}
end_class

end_unit

