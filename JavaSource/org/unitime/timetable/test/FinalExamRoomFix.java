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
name|test
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
name|commons
operator|.
name|hibernate
operator|.
name|util
operator|.
name|HibernateUtil
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
name|ExamType
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
name|dao
operator|.
name|_RootDAO
import|;
end_import

begin_class
specifier|public
class|class
name|FinalExamRoomFix
block|{
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
try|try
block|{
name|HibernateUtil
operator|.
name|configureHibernate
argument_list|(
operator|new
name|Properties
argument_list|()
argument_list|)
expr_stmt|;
name|Session
name|hibSession
init|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|ExamType
name|type
init|=
name|ExamType
operator|.
name|findByReference
argument_list|(
literal|"final"
argument_list|)
decl_stmt|;
for|for
control|(
name|Location
name|location
range|:
operator|(
name|List
argument_list|<
name|Location
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct p.location from ExamLocationPref p where p.examPeriod.examType.uniqueId = :type"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"type"
argument_list|,
name|type
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
operator|!
name|location
operator|.
name|hasFinalExamsEnabled
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Fixing "
operator|+
name|location
operator|.
name|getLabel
argument_list|()
operator|+
literal|" ("
operator|+
name|location
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|location
operator|.
name|setExamEnabled
argument_list|(
name|type
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Location
name|location
range|:
operator|(
name|List
argument_list|<
name|Location
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select distinct r from Exam x inner join x.assignedRooms r where x.examType.uniqueId = :type"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"type"
argument_list|,
name|type
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
operator|!
name|location
operator|.
name|hasFinalExamsEnabled
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Fixing "
operator|+
name|location
operator|.
name|getLabel
argument_list|()
operator|+
literal|" ("
operator|+
name|location
operator|.
name|getSession
argument_list|()
operator|.
name|getLabel
argument_list|()
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|location
operator|.
name|setExamEnabled
argument_list|(
name|type
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|location
argument_list|)
expr_stmt|;
block|}
block|}
name|hibSession
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|HibernateUtil
operator|.
name|closeHibernate
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

