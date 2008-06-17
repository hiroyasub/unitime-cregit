begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.1 (University Timetabling Application)  * Copyright (C) 2008, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|BaseHistory
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
name|HistoryDAO
import|;
end_import

begin_class
specifier|public
class|class
name|History
extends|extends
name|BaseHistory
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
name|History
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|History
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
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|History
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|oldValue
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|newValue
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Long
name|sessionId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|,
name|sessionId
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
comment|/** 	 * Retrieves all history data for the academic session 	 * @param sessionId academic session 	 * @param aClass history class 	 * @return List of aClassHistory objects 	 */
specifier|public
specifier|static
name|List
name|getHistoryList
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Class
name|aClass
parameter_list|)
throws|throws
name|HibernateException
block|{
name|HistoryDAO
name|adao
init|=
operator|new
name|HistoryDAO
argument_list|()
decl_stmt|;
name|Session
name|hSession
init|=
name|adao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|List
name|aaList
init|=
name|hSession
operator|.
name|createCriteria
argument_list|(
name|aClass
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
name|list
argument_list|()
decl_stmt|;
return|return
name|aaList
return|;
block|}
block|}
end_class

end_unit

