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
name|ApplicationProperties
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
name|ChangeLog
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
name|PositionCodeType
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
name|Staff
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
name|PositionCodeTypeDAO
import|;
end_import

begin_comment
comment|/**  *   * @author Timothy Almon  *  */
end_comment

begin_class
specifier|public
class|class
name|StaffImport
extends|extends
name|BaseImport
block|{
name|boolean
name|trimLeadingZerosFromExternalId
init|=
literal|false
decl_stmt|;
specifier|public
name|StaffImport
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
name|loadXml
argument_list|(
name|root
argument_list|)
expr_stmt|;
block|}
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
name|String
name|trimLeadingZeros
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.data.exchange.trim.externalId"
argument_list|,
literal|"false"
argument_list|)
decl_stmt|;
if|if
condition|(
name|trimLeadingZeros
operator|.
name|equals
argument_list|(
literal|"true"
argument_list|)
condition|)
block|{
name|trimLeadingZerosFromExternalId
operator|=
literal|true
expr_stmt|;
block|}
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
literal|"staff"
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Given XML file is not a Staff load file."
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
name|String
name|created
init|=
name|root
operator|.
name|attributeValue
argument_list|(
literal|"created"
argument_list|)
decl_stmt|;
name|String
name|elementName
init|=
literal|"staffMember"
decl_stmt|;
try|try
block|{
name|beginTransaction
argument_list|()
expr_stmt|;
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
operator|!=
literal|null
operator|&&
name|created
operator|!=
literal|null
condition|)
block|{
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|getHibSession
argument_list|()
argument_list|,
name|getManager
argument_list|()
argument_list|,
name|session
argument_list|,
name|session
argument_list|,
name|created
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|DATA_IMPORT_STAFF
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|UPDATE
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
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
name|getRequiredStringAttribute
argument_list|(
name|element
argument_list|,
literal|"externalId"
argument_list|,
name|elementName
argument_list|)
decl_stmt|;
name|String
name|dept
init|=
name|getOptionalStringAttribute
argument_list|(
name|element
argument_list|,
literal|"department"
argument_list|)
decl_stmt|;
name|Staff
name|staff
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
if|if
condition|(
name|trimLeadingZerosFromExternalId
condition|)
block|{
try|try
block|{
name|Integer
name|num
init|=
operator|new
name|Integer
argument_list|(
name|externalId
argument_list|)
decl_stmt|;
name|externalId
operator|=
name|num
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// do nothing
block|}
block|}
name|staff
operator|=
name|findByExternalId
argument_list|(
name|externalId
argument_list|,
name|dept
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|staff
operator|==
literal|null
condition|)
block|{
name|staff
operator|=
operator|new
name|Staff
argument_list|()
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
name|getHibSession
argument_list|()
operator|.
name|delete
argument_list|(
name|staff
argument_list|)
expr_stmt|;
continue|continue;
block|}
block|}
name|staff
operator|.
name|setFirstName
argument_list|(
name|getOptionalStringAttribute
argument_list|(
name|element
argument_list|,
literal|"firstName"
argument_list|)
argument_list|)
expr_stmt|;
name|staff
operator|.
name|setMiddleName
argument_list|(
name|getOptionalStringAttribute
argument_list|(
name|element
argument_list|,
literal|"middleName"
argument_list|)
argument_list|)
expr_stmt|;
name|staff
operator|.
name|setLastName
argument_list|(
name|getRequiredStringAttribute
argument_list|(
name|element
argument_list|,
literal|"lastName"
argument_list|,
name|elementName
argument_list|)
argument_list|)
expr_stmt|;
name|PositionCodeType
name|posCodeType
init|=
literal|null
decl_stmt|;
name|String
name|positionCode
init|=
name|getOptionalStringAttribute
argument_list|(
name|element
argument_list|,
literal|"positionCode"
argument_list|)
decl_stmt|;
if|if
condition|(
name|positionCode
operator|!=
literal|null
condition|)
block|{
name|posCodeType
operator|=
operator|new
name|PositionCodeTypeDAO
argument_list|()
operator|.
name|get
argument_list|(
name|positionCode
argument_list|)
expr_stmt|;
block|}
name|staff
operator|.
name|setPositionCode
argument_list|(
name|posCodeType
argument_list|)
expr_stmt|;
name|staff
operator|.
name|setExternalUniqueId
argument_list|(
name|externalId
argument_list|)
expr_stmt|;
if|if
condition|(
name|dept
operator|!=
literal|null
condition|)
name|staff
operator|.
name|setDept
argument_list|(
name|dept
argument_list|)
expr_stmt|;
name|String
name|email
init|=
name|getOptionalStringAttribute
argument_list|(
name|element
argument_list|,
literal|"email"
argument_list|)
decl_stmt|;
if|if
condition|(
name|email
operator|!=
literal|null
condition|)
name|staff
operator|.
name|setEmail
argument_list|(
name|email
argument_list|)
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|staff
argument_list|)
expr_stmt|;
name|flushIfNeeded
argument_list|(
literal|true
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
return|return;
block|}
specifier|private
name|Staff
name|findByExternalId
parameter_list|(
name|String
name|externalId
parameter_list|,
name|String
name|deptCode
parameter_list|)
block|{
if|if
condition|(
name|deptCode
operator|!=
literal|null
condition|)
return|return
operator|(
name|Staff
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from Staff as a where a.externalUniqueId=:externalId and a.dept=:deptCode"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|externalId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"deptCode"
argument_list|,
name|deptCode
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
else|else
return|return
operator|(
name|Staff
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct a from Staff as a where a.externalUniqueId=:externalId"
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

