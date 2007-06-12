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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
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
name|lang
operator|.
name|reflect
operator|.
name|Field
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
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
name|Connection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|DatabaseMetaData
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
name|Arrays
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
name|interceptors
operator|.
name|LobCleanUpInterceptor
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|BinaryBlobType
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
name|BinaryBlobType
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
comment|//Get the blob field we are interested in from the result set
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
return|return
name|blob
operator|.
name|getBytes
argument_list|(
literal|1
argument_list|,
operator|(
name|int
operator|)
name|blob
operator|.
name|length
argument_list|()
argument_list|)
return|;
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
name|DatabaseMetaData
name|dbMetaData
init|=
name|ps
operator|.
name|getConnection
argument_list|()
operator|.
name|getMetaData
argument_list|()
decl_stmt|;
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
name|Class
name|oracleBlobClass
init|=
name|Class
operator|.
name|forName
argument_list|(
literal|"oracle.sql.BLOB"
argument_list|)
decl_stmt|;
name|Class
name|oracleConnectionClass
init|=
name|Class
operator|.
name|forName
argument_list|(
literal|"oracle.jdbc.OracleConnection"
argument_list|)
decl_stmt|;
comment|// now get the static factory method
name|Class
index|[]
name|partypes
init|=
operator|new
name|Class
index|[
literal|3
index|]
decl_stmt|;
name|partypes
index|[
literal|0
index|]
operator|=
name|Connection
operator|.
name|class
expr_stmt|;
name|partypes
index|[
literal|1
index|]
operator|=
name|Boolean
operator|.
name|TYPE
expr_stmt|;
name|partypes
index|[
literal|2
index|]
operator|=
name|Integer
operator|.
name|TYPE
expr_stmt|;
name|Method
name|createTemporaryMethod
init|=
name|oracleBlobClass
operator|.
name|getDeclaredMethod
argument_list|(
literal|"createTemporary"
argument_list|,
name|partypes
argument_list|)
decl_stmt|;
name|Field
name|durationSessionField
init|=
name|oracleBlobClass
operator|.
name|getField
argument_list|(
literal|"DURATION_SESSION"
argument_list|)
decl_stmt|;
name|Object
index|[]
name|arglist
init|=
operator|new
name|Object
index|[
literal|3
index|]
decl_stmt|;
name|Connection
name|conn
init|=
name|dbMetaData
operator|.
name|getConnection
argument_list|()
decl_stmt|;
try|try
block|{
name|conn
operator|=
operator|(
name|Connection
operator|)
name|conn
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getConnection"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
name|conn
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|ex
parameter_list|)
block|{
block|}
comment|// Make sure connection object is right type
if|if
condition|(
operator|!
name|oracleConnectionClass
operator|.
name|isAssignableFrom
argument_list|(
name|conn
operator|.
name|getClass
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
literal|"JDBC connection object must be a oracle.jdbc.OracleConnection. "
operator|+
literal|"Connection class is "
operator|+
name|conn
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
throw|;
block|}
name|arglist
index|[
literal|0
index|]
operator|=
name|conn
expr_stmt|;
name|arglist
index|[
literal|1
index|]
operator|=
name|Boolean
operator|.
name|TRUE
expr_stmt|;
name|arglist
index|[
literal|2
index|]
operator|=
name|durationSessionField
operator|.
name|get
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//null is valid because of static field
comment|// Create our BLOB
name|Object
name|tempBlob
init|=
name|createTemporaryMethod
operator|.
name|invoke
argument_list|(
literal|null
argument_list|,
name|arglist
argument_list|)
decl_stmt|;
comment|//null is valid because of static method
comment|// get the open method
name|partypes
operator|=
operator|new
name|Class
index|[
literal|1
index|]
expr_stmt|;
name|partypes
index|[
literal|0
index|]
operator|=
name|Integer
operator|.
name|TYPE
expr_stmt|;
name|Method
name|openMethod
init|=
name|oracleBlobClass
operator|.
name|getDeclaredMethod
argument_list|(
literal|"open"
argument_list|,
name|partypes
argument_list|)
decl_stmt|;
comment|// prepare to call the method
name|Field
name|modeReadWriteField
init|=
name|oracleBlobClass
operator|.
name|getField
argument_list|(
literal|"MODE_READWRITE"
argument_list|)
decl_stmt|;
name|arglist
operator|=
operator|new
name|Object
index|[
literal|1
index|]
expr_stmt|;
name|arglist
index|[
literal|0
index|]
operator|=
name|modeReadWriteField
operator|.
name|get
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|//null is valid because of static field
comment|// call open(BLOB.MODE_READWRITE);
name|openMethod
operator|.
name|invoke
argument_list|(
name|tempBlob
argument_list|,
name|arglist
argument_list|)
expr_stmt|;
comment|// get the getCharacterOutputStream method
name|Method
name|getBinaryOutputStreamMethod
init|=
name|oracleBlobClass
operator|.
name|getDeclaredMethod
argument_list|(
literal|"getBinaryOutputStream"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
decl_stmt|;
name|OutputStream
name|out
init|=
operator|(
name|OutputStream
operator|)
name|getBinaryOutputStreamMethod
operator|.
name|invoke
argument_list|(
name|tempBlob
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
decl_stmt|;
comment|// write the bytes to the blob
name|out
operator|.
name|write
argument_list|(
operator|(
name|byte
index|[]
operator|)
name|value
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
name|Method
name|closeMethod
init|=
name|oracleBlobClass
operator|.
name|getDeclaredMethod
argument_list|(
literal|"close"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
decl_stmt|;
comment|// call the close method
name|closeMethod
operator|.
name|invoke
argument_list|(
name|tempBlob
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
expr_stmt|;
comment|// add the blob to the statement
name|ps
operator|.
name|setBlob
argument_list|(
name|index
argument_list|,
operator|(
name|Blob
operator|)
name|tempBlob
argument_list|)
expr_stmt|;
name|LobCleanUpInterceptor
operator|.
name|registerTempLobs
argument_list|(
name|tempBlob
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
comment|// could not find the class with reflection
throw|throw
operator|new
name|HibernateException
argument_list|(
literal|"Unable to find a required class, reason: "
operator|+
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
name|NoSuchMethodException
name|e
parameter_list|)
block|{
comment|// could not find the metho with reflection
throw|throw
operator|new
name|HibernateException
argument_list|(
literal|"Unable to find a required method, reason: "
operator|+
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
name|NoSuchFieldException
name|e
parameter_list|)
block|{
comment|// could not find the field with reflection
throw|throw
operator|new
name|HibernateException
argument_list|(
literal|"Unable to find a required field, reason: "
operator|+
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
name|IllegalAccessException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|HibernateException
argument_list|(
literal|"Unable to access a required method or field, reason: "
operator|+
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
name|InvocationTargetException
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
block|{
return|return
literal|null
return|;
block|}
name|byte
index|[]
name|src
init|=
operator|(
name|byte
index|[]
operator|)
name|value
decl_stmt|;
name|byte
index|[]
name|dest
init|=
operator|new
name|byte
index|[
name|src
operator|.
name|length
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|src
argument_list|,
literal|0
argument_list|,
name|dest
argument_list|,
literal|0
argument_list|,
name|src
operator|.
name|length
argument_list|)
expr_stmt|;
return|return
name|dest
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
name|byte
index|[]
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
return|return
name|Arrays
operator|.
name|equals
argument_list|(
operator|(
name|byte
index|[]
operator|)
name|x
argument_list|,
operator|(
name|byte
index|[]
operator|)
name|y
argument_list|)
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
return|return
operator|(
name|byte
index|[]
operator|)
name|value
return|;
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
return|return
name|cached
return|;
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
name|byte
index|[]
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

