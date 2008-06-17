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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|DocumentException
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
name|TimetableManager
import|;
end_import

begin_comment
comment|/**  *   * @author Tomas Muller  *  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|BaseImport
extends|extends
name|DataExchangeHelper
block|{
specifier|protected
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|BaseImport
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|TimetableManager
name|iManager
init|=
literal|null
decl_stmt|;
specifier|public
name|BaseImport
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|loadXml
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|Exception
block|{
name|debug
argument_list|(
literal|"Loading "
operator|+
name|fileName
argument_list|)
expr_stmt|;
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
name|fileName
argument_list|)
expr_stmt|;
name|loadXml
argument_list|(
name|fis
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Unable to read file "
operator|+
name|fileName
operator|+
literal|", reason:"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
throw|throw
name|e
throw|;
block|}
finally|finally
block|{
if|if
condition|(
name|fis
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|fis
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
block|}
block|}
block|}
specifier|public
name|void
name|loadXml
parameter_list|(
name|InputStream
name|inputStream
parameter_list|)
throws|throws
name|Exception
block|{
try|try
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
name|inputStream
argument_list|)
decl_stmt|;
name|loadXml
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|DocumentException
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Unable to parse given XML, reason:"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|abstract
name|void
name|loadXml
parameter_list|(
name|Element
name|rootElement
parameter_list|)
throws|throws
name|Exception
function_decl|;
specifier|protected
name|String
name|getRequiredStringAttribute
parameter_list|(
name|Element
name|element
parameter_list|,
name|String
name|attributeName
parameter_list|,
name|String
name|elementName
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|attributeValue
init|=
name|element
operator|.
name|attributeValue
argument_list|(
name|attributeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|attributeValue
operator|==
literal|null
operator|||
name|attributeValue
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"For element '"
operator|+
name|elementName
operator|+
literal|"' a '"
operator|+
name|attributeName
operator|+
literal|"' is required"
argument_list|)
throw|;
block|}
else|else
block|{
name|attributeValue
operator|=
name|attributeValue
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|'\u0096'
argument_list|,
literal|' '
argument_list|)
operator|.
name|replace
argument_list|(
literal|'\u0097'
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|attributeValue
operator|)
return|;
block|}
specifier|protected
name|String
name|getOptionalStringAttribute
parameter_list|(
name|Element
name|element
parameter_list|,
name|String
name|attributeName
parameter_list|)
block|{
name|String
name|attributeValue
init|=
name|element
operator|.
name|attributeValue
argument_list|(
name|attributeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|attributeValue
operator|==
literal|null
operator|||
name|attributeValue
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|attributeValue
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|attributeValue
operator|=
name|attributeValue
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|'\u0096'
argument_list|,
literal|' '
argument_list|)
operator|.
name|replace
argument_list|(
literal|'\u0097'
argument_list|,
literal|' '
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|attributeValue
operator|)
return|;
block|}
specifier|protected
name|Integer
name|getRequiredIntegerAttribute
parameter_list|(
name|Element
name|element
parameter_list|,
name|String
name|attributeName
parameter_list|,
name|String
name|elementName
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|attributeStr
init|=
name|getRequiredStringAttribute
argument_list|(
name|element
argument_list|,
name|attributeName
argument_list|,
name|elementName
argument_list|)
decl_stmt|;
return|return
operator|(
operator|new
name|Integer
argument_list|(
name|attributeStr
argument_list|)
operator|)
return|;
block|}
specifier|protected
name|Integer
name|getOptionalIntegerAttribute
parameter_list|(
name|Element
name|element
parameter_list|,
name|String
name|attributeName
parameter_list|)
block|{
name|String
name|attributeStr
init|=
name|getOptionalStringAttribute
argument_list|(
name|element
argument_list|,
name|attributeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|attributeStr
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
operator|new
name|Integer
argument_list|(
name|attributeStr
argument_list|)
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
specifier|protected
name|Boolean
name|getRequiredBooleanAttribute
parameter_list|(
name|Element
name|element
parameter_list|,
name|String
name|attributeName
parameter_list|,
name|String
name|elementName
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|attributeStr
init|=
name|getRequiredStringAttribute
argument_list|(
name|element
argument_list|,
name|attributeName
argument_list|,
name|elementName
argument_list|)
decl_stmt|;
return|return
operator|(
operator|new
name|Boolean
argument_list|(
name|attributeStr
argument_list|)
operator|)
return|;
block|}
specifier|protected
name|Boolean
name|getOptionalBooleanAttribute
parameter_list|(
name|Element
name|element
parameter_list|,
name|String
name|attributeName
parameter_list|)
block|{
name|String
name|attributeStr
init|=
name|getOptionalStringAttribute
argument_list|(
name|element
argument_list|,
name|attributeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|attributeStr
operator|!=
literal|null
condition|)
block|{
return|return
operator|(
operator|new
name|Boolean
argument_list|(
name|attributeStr
argument_list|)
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
specifier|protected
name|TimetableManager
name|getManager
parameter_list|()
block|{
if|if
condition|(
name|iManager
operator|==
literal|null
condition|)
name|iManager
operator|=
name|findDefaultManager
argument_list|()
expr_stmt|;
return|return
name|iManager
return|;
block|}
specifier|public
name|void
name|setManager
parameter_list|(
name|TimetableManager
name|manager
parameter_list|)
block|{
name|iManager
operator|=
name|manager
expr_stmt|;
block|}
specifier|protected
name|TimetableManager
name|findDefaultManager
parameter_list|()
block|{
return|return
operator|(
operator|(
name|TimetableManager
operator|)
name|getHibSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from TimetableManager as m where m.uniqueId = (select min(tm.uniqueId) from TimetableManager as tm inner join tm.managerRoles as mr inner join mr.role as r where r.reference = 'Administrator')"
argument_list|)
operator|.
name|uniqueResult
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

