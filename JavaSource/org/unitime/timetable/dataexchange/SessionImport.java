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
name|dataexchange
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|DepartmentStatusType
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

begin_comment
comment|/**  *   * @author Timothy Almon, Tomas Muller  *  */
end_comment

begin_class
specifier|public
class|class
name|SessionImport
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
literal|"session"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a Session load file."
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
name|DepartmentStatusType
name|statusType
init|=
name|DepartmentStatusType
operator|.
name|findByRef
argument_list|(
literal|"initial"
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|root
operator|.
name|elementIterator
argument_list|()
init|;
name|iter
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
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|beginDate
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"beginDate"
argument_list|)
decl_stmt|;
if|if
condition|(
name|beginDate
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Begin date not provided."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|endDate
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"endDate"
argument_list|)
decl_stmt|;
if|if
condition|(
name|endDate
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"End date not provided."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|classesEnd
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"classesEnd"
argument_list|)
decl_stmt|;
if|if
condition|(
name|classesEnd
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Classes end date not provided."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|examBegin
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"examBegin"
argument_list|,
name|classesEnd
argument_list|)
decl_stmt|;
name|String
name|eventBegin
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"eventBegin"
argument_list|,
name|beginDate
argument_list|)
decl_stmt|;
name|String
name|eventEnd
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"eventEnd"
argument_list|,
name|endDate
argument_list|)
decl_stmt|;
name|Session
name|session
init|=
operator|new
name|Session
argument_list|()
decl_stmt|;
name|session
operator|.
name|setAcademicInitiative
argument_list|(
name|campus
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAcademicYear
argument_list|(
name|year
argument_list|)
expr_stmt|;
name|session
operator|.
name|setAcademicTerm
argument_list|(
name|term
argument_list|)
expr_stmt|;
name|DateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
name|root
operator|.
name|attributeValue
argument_list|(
literal|"dateFormat"
argument_list|,
literal|"M/d/y"
argument_list|)
argument_list|)
decl_stmt|;
name|session
operator|.
name|setSessionBeginDateTime
argument_list|(
name|df
operator|.
name|parse
argument_list|(
name|beginDate
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setSessionEndDateTime
argument_list|(
name|df
operator|.
name|parse
argument_list|(
name|endDate
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setClassesEndDateTime
argument_list|(
name|df
operator|.
name|parse
argument_list|(
name|classesEnd
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setExamBeginDate
argument_list|(
name|df
operator|.
name|parse
argument_list|(
name|examBegin
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setEventBeginDate
argument_list|(
name|df
operator|.
name|parse
argument_list|(
name|eventBegin
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setEventEndDate
argument_list|(
name|df
operator|.
name|parse
argument_list|(
name|eventEnd
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setStatusType
argument_list|(
name|statusType
argument_list|)
expr_stmt|;
name|session
operator|.
name|setLastWeekToEnroll
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"lastWeekToEnroll"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setLastWeekToChange
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"lastWeekToChange"
argument_list|,
literal|"1"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|session
operator|.
name|setLastWeekToDrop
argument_list|(
name|Integer
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"lastWeekToDrop"
argument_list|,
literal|"4"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
literal|false
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

