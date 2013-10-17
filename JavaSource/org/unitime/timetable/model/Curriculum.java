begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 - 3.5 (University Timetabling Application)  * Copyright (C) 2008 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|BaseCurriculum
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
name|CurriculumDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Curriculum
extends|extends
name|BaseCurriculum
implements|implements
name|Comparable
argument_list|<
name|Curriculum
argument_list|>
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
name|Curriculum
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|Curriculum
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
specifier|public
specifier|static
name|List
argument_list|<
name|Curriculum
argument_list|>
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
name|CurriculumDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from Curriculum c where c.department.session.uniqueId=:sessionId"
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
specifier|static
name|List
argument_list|<
name|Curriculum
argument_list|>
name|findByDepartment
parameter_list|(
name|Long
name|deptId
parameter_list|)
block|{
return|return
name|CurriculumDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from Curriculum c where c.department.uniqueId=:deptId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"deptId"
argument_list|,
name|deptId
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
name|int
name|compareTo
parameter_list|(
name|Curriculum
name|c
parameter_list|)
block|{
name|int
name|cmp
init|=
name|getAbbv
argument_list|()
operator|.
name|compareToIgnoreCase
argument_list|(
name|c
operator|.
name|getAbbv
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|c
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|c
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

