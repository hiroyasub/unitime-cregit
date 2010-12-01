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
name|commons
operator|.
name|hibernate
operator|.
name|blob
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Blob
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|PreparedStatement
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|ResultSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Types
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
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|GZIPInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|GZIPOutputStream
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
name|Attribute
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
name|OutputFormat
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
name|dom4j
operator|.
name|io
operator|.
name|XMLWriter
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
name|usertype
operator|.
name|UserType
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|XmlBlobType
implements|implements
name|UserType
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
name|XmlBlobType
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|Object
name|nullSafeGet
parameter_list|(
name|ResultSet
name|rs
parameter_list|,
name|String
index|[]
name|names
parameter_list|,
name|Object
name|owner
parameter_list|)
throws|throws
name|SQLException
block|{
name|Blob
name|blob
init|=
name|rs
operator|.
name|getBlob
argument_list|(
name|names
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|blob
operator|==
literal|null
condition|)
return|return
literal|null
return|;
try|try
block|{
name|SAXReader
name|reader
init|=
operator|new
name|SAXReader
argument_list|()
decl_stmt|;
name|GZIPInputStream
name|gzipInput
init|=
operator|new
name|GZIPInputStream
argument_list|(
name|blob
operator|.
name|getBinaryStream
argument_list|()
argument_list|)
decl_stmt|;
name|Document
name|document
init|=
name|reader
operator|.
name|read
argument_list|(
name|gzipInput
argument_list|)
decl_stmt|;
name|gzipInput
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|document
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|DocumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|void
name|nullSafeSet
parameter_list|(
name|PreparedStatement
name|ps
parameter_list|,
name|Object
name|value
parameter_list|,
name|int
name|index
parameter_list|)
throws|throws
name|SQLException
throws|,
name|HibernateException
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
name|ps
operator|.
name|setNull
argument_list|(
name|index
argument_list|,
name|sqlTypes
argument_list|()
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|ByteArrayOutputStream
name|bytes
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|XMLWriter
name|writer
init|=
operator|new
name|XMLWriter
argument_list|(
operator|new
name|GZIPOutputStream
argument_list|(
name|bytes
argument_list|)
argument_list|,
name|OutputFormat
operator|.
name|createCompactFormat
argument_list|()
argument_list|)
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
operator|(
name|Document
operator|)
name|value
argument_list|)
expr_stmt|;
name|writer
operator|.
name|flush
argument_list|()
expr_stmt|;
name|writer
operator|.
name|close
argument_list|()
expr_stmt|;
name|ps
operator|.
name|setBinaryStream
argument_list|(
name|index
argument_list|,
operator|new
name|ByteArrayInputStream
argument_list|(
name|bytes
operator|.
name|toByteArray
argument_list|()
argument_list|,
literal|0
argument_list|,
name|bytes
operator|.
name|size
argument_list|()
argument_list|)
argument_list|,
name|bytes
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
specifier|public
name|Object
name|deepCopy
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
operator|(
operator|(
name|Document
operator|)
name|value
operator|)
operator|.
name|clone
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isMutable
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
specifier|public
name|int
index|[]
name|sqlTypes
parameter_list|()
block|{
return|return
operator|new
name|int
index|[]
block|{
name|Types
operator|.
name|BLOB
block|}
return|;
block|}
specifier|public
name|Class
name|returnedClass
parameter_list|()
block|{
return|return
name|Document
operator|.
name|class
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|x
parameter_list|,
name|Object
name|y
parameter_list|)
block|{
if|if
condition|(
name|x
operator|==
literal|null
condition|)
return|return
operator|(
name|y
operator|==
literal|null
operator|)
return|;
if|if
condition|(
name|y
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|x
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|y
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|x
operator|instanceof
name|Document
condition|)
block|{
name|Document
name|a
init|=
operator|(
name|Document
operator|)
name|x
decl_stmt|;
name|Document
name|b
init|=
operator|(
name|Document
operator|)
name|y
decl_stmt|;
return|return
name|equals
argument_list|(
name|a
operator|.
name|getName
argument_list|()
argument_list|,
name|b
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|equals
argument_list|(
name|a
operator|.
name|getRootElement
argument_list|()
argument_list|,
name|b
operator|.
name|getRootElement
argument_list|()
argument_list|)
return|;
block|}
if|else if
condition|(
name|x
operator|instanceof
name|Element
condition|)
block|{
name|Element
name|a
init|=
operator|(
name|Element
operator|)
name|x
decl_stmt|;
name|Element
name|b
init|=
operator|(
name|Element
operator|)
name|y
decl_stmt|;
return|return
name|equals
argument_list|(
name|a
operator|.
name|getName
argument_list|()
argument_list|,
name|b
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|equals
argument_list|(
name|a
operator|.
name|getText
argument_list|()
argument_list|,
name|b
operator|.
name|getText
argument_list|()
argument_list|)
operator|&&
name|equals
argument_list|(
name|a
operator|.
name|attributes
argument_list|()
argument_list|,
name|b
operator|.
name|attributes
argument_list|()
argument_list|)
operator|&&
name|equals
argument_list|(
name|a
operator|.
name|elements
argument_list|()
argument_list|,
name|b
operator|.
name|elements
argument_list|()
argument_list|)
return|;
block|}
if|else if
condition|(
name|x
operator|instanceof
name|Attribute
condition|)
block|{
name|Attribute
name|a
init|=
operator|(
name|Attribute
operator|)
name|x
decl_stmt|;
name|Attribute
name|b
init|=
operator|(
name|Attribute
operator|)
name|y
decl_stmt|;
return|return
name|equals
argument_list|(
name|a
operator|.
name|getName
argument_list|()
argument_list|,
name|b
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|equals
argument_list|(
name|a
operator|.
name|getValue
argument_list|()
argument_list|,
name|b
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
if|else if
condition|(
name|x
operator|instanceof
name|List
condition|)
block|{
name|List
name|a
init|=
operator|(
name|List
operator|)
name|x
decl_stmt|;
name|List
name|b
init|=
operator|(
name|List
operator|)
name|y
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|size
argument_list|()
operator|!=
name|b
operator|.
name|size
argument_list|()
condition|)
return|return
literal|false
return|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|a
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
if|if
condition|(
operator|!
name|equals
argument_list|(
name|a
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|b
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
else|else
return|return
operator|(
name|x
operator|.
name|equals
argument_list|(
name|y
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Serializable
name|disassemble
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|HibernateException
block|{
try|try
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|ByteArrayOutputStream
name|out
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
name|XMLWriter
name|writer
init|=
operator|new
name|XMLWriter
argument_list|(
operator|new
name|GZIPOutputStream
argument_list|(
name|out
argument_list|)
argument_list|,
name|OutputFormat
operator|.
name|createCompactFormat
argument_list|()
argument_list|)
decl_stmt|;
name|writer
operator|.
name|write
argument_list|(
operator|(
name|Document
operator|)
name|value
argument_list|)
expr_stmt|;
name|writer
operator|.
name|flush
argument_list|()
expr_stmt|;
name|writer
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|out
operator|.
name|toByteArray
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|Object
name|assemble
parameter_list|(
name|Serializable
name|cached
parameter_list|,
name|Object
name|owner
parameter_list|)
throws|throws
name|HibernateException
block|{
try|try
block|{
if|if
condition|(
name|cached
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|ByteArrayInputStream
name|in
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
operator|(
name|byte
index|[]
operator|)
name|cached
argument_list|)
decl_stmt|;
name|SAXReader
name|reader
init|=
operator|new
name|SAXReader
argument_list|()
decl_stmt|;
name|GZIPInputStream
name|gzipInput
init|=
operator|new
name|GZIPInputStream
argument_list|(
name|in
argument_list|)
decl_stmt|;
name|Document
name|document
init|=
name|reader
operator|.
name|read
argument_list|(
name|gzipInput
argument_list|)
decl_stmt|;
name|gzipInput
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|document
return|;
block|}
catch|catch
parameter_list|(
name|DocumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|public
name|Object
name|replace
parameter_list|(
name|Object
name|original
parameter_list|,
name|Object
name|target
parameter_list|,
name|Object
name|owner
parameter_list|)
throws|throws
name|HibernateException
block|{
return|return
name|original
return|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|(
name|Object
name|value
parameter_list|)
throws|throws
name|HibernateException
block|{
return|return
operator|(
operator|(
name|Document
operator|)
name|value
operator|)
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

