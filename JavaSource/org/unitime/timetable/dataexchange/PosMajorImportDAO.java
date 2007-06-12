begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|dom4j
operator|.
name|io
operator|.
name|SAXReader
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
name|AcademicArea
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
name|PosMajor
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
name|dao
operator|.
name|PosMajorDAO
import|;
end_import

begin_comment
comment|/**  *   * @author Timothy Almon  *  */
end_comment

begin_class
specifier|public
class|class
name|PosMajorImportDAO
extends|extends
name|PosMajorDAO
block|{
specifier|public
name|PosMajorImportDAO
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|loadFromXML
parameter_list|(
name|String
name|filename
parameter_list|)
throws|throws
name|Exception
block|{
name|FileInputStream
name|fis
init|=
literal|null
decl_stmt|;
try|try
block|{
name|fis
operator|=
operator|new
name|FileInputStream
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|loadFromStream
argument_list|(
name|fis
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|fis
operator|!=
literal|null
condition|)
name|fis
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
return|return;
block|}
specifier|public
name|void
name|loadFromStream
parameter_list|(
name|FileInputStream
name|fis
parameter_list|)
throws|throws
name|Exception
block|{
name|Document
name|document
init|=
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|fis
argument_list|)
decl_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
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
literal|"posMajors"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a PosMajor load file."
argument_list|)
throw|;
block|}
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
name|PosMajor
name|posMajor
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|externalId
operator|!=
literal|null
operator|&&
name|externalId
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|posMajor
operator|=
name|findByExternalId
argument_list|(
name|externalId
argument_list|,
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|posMajor
operator|==
literal|null
condition|)
block|{
name|posMajor
operator|=
operator|new
name|PosMajor
argument_list|()
expr_stmt|;
name|posMajor
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|posMajor
operator|.
name|setAcademicAreas
argument_list|(
operator|new
name|HashSet
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
literal|"T"
operator|.
name|equalsIgnoreCase
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"delete"
argument_list|)
argument_list|)
condition|)
block|{
name|this
operator|.
name|delete
argument_list|(
name|posMajor
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
name|posMajor
operator|.
name|setName
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
argument_list|)
expr_stmt|;
name|posMajor
operator|.
name|setCode
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"code"
argument_list|)
argument_list|)
expr_stmt|;
name|posMajor
operator|.
name|setExternalUniqueId
argument_list|(
name|externalId
argument_list|)
expr_stmt|;
name|AcademicArea
name|acadArea
init|=
name|AcademicArea
operator|.
name|findByAbbv
argument_list|(
name|session
operator|.
name|getSessionId
argument_list|()
argument_list|,
name|element
operator|.
name|attributeValue
argument_list|(
literal|"academicArea"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|acadArea
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Could not find AcademicArea: "
operator|+
name|element
operator|.
name|attributeValue
argument_list|(
literal|"academicArea"
argument_list|)
argument_list|)
throw|;
block|}
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|Iterator
name|iter
init|=
name|posMajor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|iter
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|AcademicArea
name|area
init|=
operator|(
name|AcademicArea
operator|)
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|area
operator|.
name|getAcademicAreaAbbreviation
argument_list|()
operator|.
name|equals
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"academicArea"
argument_list|)
argument_list|)
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|found
condition|)
block|{
name|posMajor
operator|.
name|getAcademicAreas
argument_list|()
operator|.
name|add
argument_list|(
name|acadArea
argument_list|)
expr_stmt|;
name|acadArea
operator|.
name|getPosMajors
argument_list|()
operator|.
name|add
argument_list|(
name|posMajor
argument_list|)
expr_stmt|;
block|}
name|saveOrUpdate
argument_list|(
name|posMajor
argument_list|)
expr_stmt|;
block|}
return|return;
block|}
specifier|private
name|PosMajor
name|findByExternalId
parameter_list|(
name|String
name|externalId
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|PosMajor
operator|)
name|this
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from PosMajor as a where a.externalUniqueId=:externalId and a.session.uniqueId=:sessionId"
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
block|}
end_class

end_unit

