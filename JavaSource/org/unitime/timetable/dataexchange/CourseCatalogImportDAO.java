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
name|CourseCatalog
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
name|CourseSubpartCredit
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
name|CourseCatalogDAO
import|;
end_import

begin_comment
comment|/**  *   * @author Timothy Almon  *  */
end_comment

begin_class
specifier|public
class|class
name|CourseCatalogImportDAO
extends|extends
name|CourseCatalogDAO
block|{
specifier|private
specifier|static
specifier|final
name|int
name|MIN_CREDIT
init|=
literal|0
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|int
name|MAX_CREDIT
init|=
literal|16
decl_stmt|;
specifier|public
name|CourseCatalogImportDAO
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
literal|"courseCatalog"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a Course Catalog load file."
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
name|CourseCatalog
name|catalog
init|=
operator|new
name|CourseCatalog
argument_list|()
decl_stmt|;
name|catalog
operator|.
name|setApprovalType
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setCourseNumber
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"courseNumber"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setDesignatorRequired
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"designatorRequired"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setExternalUniqueId
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"externalId"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setFractionalCreditAllowed
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"fractionalCreditAllowed"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setPermanentId
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"permanentId"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setPreviousCourseNumber
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"previousCourseNumber"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setPreviousSubject
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"previousSubject"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setSubject
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"subject"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setTitle
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Element
name|credit
init|=
name|element
operator|.
name|element
argument_list|(
literal|"courseCredit"
argument_list|)
decl_stmt|;
if|if
condition|(
name|credit
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Course credit is required."
argument_list|)
throw|;
block|}
name|catalog
operator|.
name|setCreditFormat
argument_list|(
name|credit
operator|.
name|attributeValue
argument_list|(
literal|"creditFormat"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setCreditType
argument_list|(
name|credit
operator|.
name|attributeValue
argument_list|(
literal|"creditType"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|setCreditUnitType
argument_list|(
name|credit
operator|.
name|attributeValue
argument_list|(
literal|"creditUnitType"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|minCredit
init|=
name|credit
operator|.
name|attributeValue
argument_list|(
literal|"fixedCredit"
argument_list|)
decl_stmt|;
if|if
condition|(
name|minCredit
operator|!=
literal|null
condition|)
block|{
name|catalog
operator|.
name|setFixedMinimumCredit
argument_list|(
name|Float
operator|.
name|parseFloat
argument_list|(
name|minCredit
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|minCredit
operator|=
name|credit
operator|.
name|attributeValue
argument_list|(
literal|"minimumCredit"
argument_list|)
expr_stmt|;
if|if
condition|(
name|minCredit
operator|!=
literal|null
condition|)
block|{
name|catalog
operator|.
name|setFixedMinimumCredit
argument_list|(
name|Float
operator|.
name|parseFloat
argument_list|(
name|minCredit
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|catalog
operator|.
name|setFixedMinimumCredit
argument_list|(
operator|new
name|Float
argument_list|(
name|MIN_CREDIT
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|maxCredit
init|=
name|credit
operator|.
name|attributeValue
argument_list|(
literal|"maximumCredit"
argument_list|)
decl_stmt|;
if|if
condition|(
name|maxCredit
operator|!=
literal|null
condition|)
block|{
name|catalog
operator|.
name|setMaximumCredit
argument_list|(
name|Float
operator|.
name|parseFloat
argument_list|(
name|maxCredit
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|catalog
operator|.
name|setMaximumCredit
argument_list|(
operator|new
name|Float
argument_list|(
name|MAX_CREDIT
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|saveOrUpdate
argument_list|(
name|catalog
argument_list|)
expr_stmt|;
comment|// to set the uniqueId
name|loadCredits
argument_list|(
name|element
argument_list|,
name|catalog
argument_list|)
expr_stmt|;
name|saveOrUpdate
argument_list|(
name|catalog
argument_list|)
expr_stmt|;
comment|// to save the subparts
block|}
return|return;
block|}
specifier|private
name|void
name|loadCredits
parameter_list|(
name|Element
name|course
parameter_list|,
name|CourseCatalog
name|catalog
parameter_list|)
throws|throws
name|Exception
block|{
for|for
control|(
name|Iterator
name|it
init|=
name|course
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
if|if
condition|(
name|element
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
literal|"courseCredit"
argument_list|)
condition|)
continue|continue;
name|CourseSubpartCredit
name|credit
init|=
operator|new
name|CourseSubpartCredit
argument_list|()
decl_stmt|;
name|credit
operator|.
name|setCourseCatalog
argument_list|(
name|catalog
argument_list|)
expr_stmt|;
name|credit
operator|.
name|setCreditFormat
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"creditFormat"
argument_list|)
argument_list|)
expr_stmt|;
name|credit
operator|.
name|setCreditType
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"creditType"
argument_list|)
argument_list|)
expr_stmt|;
name|credit
operator|.
name|setCreditUnitType
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"creditUnitType"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|minCredit
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"fixedCredit"
argument_list|)
decl_stmt|;
if|if
condition|(
name|minCredit
operator|!=
literal|null
condition|)
block|{
name|credit
operator|.
name|setFixedMinimumCredit
argument_list|(
name|Float
operator|.
name|parseFloat
argument_list|(
name|minCredit
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|minCredit
operator|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"minimumCredit"
argument_list|)
expr_stmt|;
if|if
condition|(
name|minCredit
operator|!=
literal|null
condition|)
block|{
name|credit
operator|.
name|setFixedMinimumCredit
argument_list|(
name|Float
operator|.
name|parseFloat
argument_list|(
name|minCredit
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|credit
operator|.
name|setFixedMinimumCredit
argument_list|(
operator|new
name|Float
argument_list|(
name|MIN_CREDIT
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|maxCredit
init|=
name|element
operator|.
name|attributeValue
argument_list|(
literal|"maximumCredit"
argument_list|)
decl_stmt|;
if|if
condition|(
name|maxCredit
operator|!=
literal|null
condition|)
block|{
name|credit
operator|.
name|setMaximumCredit
argument_list|(
name|Float
operator|.
name|parseFloat
argument_list|(
name|maxCredit
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|credit
operator|.
name|setMaximumCredit
argument_list|(
operator|new
name|Float
argument_list|(
name|MAX_CREDIT
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|credit
operator|.
name|setFractionalCreditAllowed
argument_list|(
name|Boolean
operator|.
name|valueOf
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"fractionalCreditAllowed"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|credit
operator|.
name|setSubpartId
argument_list|(
name|element
operator|.
name|attributeValue
argument_list|(
literal|"subpartId"
argument_list|)
argument_list|)
expr_stmt|;
name|catalog
operator|.
name|addTosubparts
argument_list|(
name|credit
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

