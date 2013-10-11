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
name|util
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
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|CallableStatement
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
name|engine
operator|.
name|spi
operator|.
name|SessionImplementor
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
name|interfaces
operator|.
name|RoomAvailabilityInterface
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
name|BlobRoomAvailabilityService
extends|extends
name|RoomAvailabilityService
block|{
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|RoomAvailabilityInterface
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|String
name|iRequestSql
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.room.availability.request"
argument_list|,
literal|"{ call room_avail_interface.request(?) }"
argument_list|)
decl_stmt|;
specifier|private
name|String
name|iResponseSql
init|=
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"tmtbl.room.availability.response"
argument_list|,
literal|"{? = call room_avail_interface.response()}"
argument_list|)
decl_stmt|;
specifier|protected
name|void
name|sendRequest
parameter_list|(
name|Document
name|request
parameter_list|)
throws|throws
name|IOException
block|{
try|try
block|{
name|StringWriter
name|writer
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
operator|(
operator|new
name|XMLWriter
argument_list|(
name|writer
argument_list|,
name|OutputFormat
operator|.
name|createPrettyPrint
argument_list|()
argument_list|)
operator|)
operator|.
name|write
argument_list|(
name|request
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
name|SessionImplementor
name|session
init|=
operator|(
name|SessionImplementor
operator|)
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Connection
name|connection
init|=
name|session
operator|.
name|getJdbcConnectionAccess
argument_list|()
operator|.
name|obtainConnection
argument_list|()
decl_stmt|;
try|try
block|{
name|CallableStatement
name|call
init|=
name|connection
operator|.
name|prepareCall
argument_list|(
name|iRequestSql
argument_list|)
decl_stmt|;
name|call
operator|.
name|setString
argument_list|(
literal|1
argument_list|,
name|writer
operator|.
name|getBuffer
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|call
operator|.
name|execute
argument_list|()
expr_stmt|;
name|call
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|session
operator|.
name|getJdbcConnectionAccess
argument_list|()
operator|.
name|releaseConnection
argument_list|(
name|connection
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to send request: "
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
finally|finally
block|{
name|_RootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|Document
name|receiveResponse
parameter_list|()
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
try|try
block|{
name|SessionImplementor
name|session
init|=
operator|(
name|SessionImplementor
operator|)
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Connection
name|connection
init|=
name|session
operator|.
name|getJdbcConnectionAccess
argument_list|()
operator|.
name|obtainConnection
argument_list|()
decl_stmt|;
name|String
name|response
init|=
literal|null
decl_stmt|;
try|try
block|{
name|CallableStatement
name|call
init|=
name|connection
operator|.
name|prepareCall
argument_list|(
name|iResponseSql
argument_list|)
decl_stmt|;
name|call
operator|.
name|registerOutParameter
argument_list|(
literal|1
argument_list|,
name|java
operator|.
name|sql
operator|.
name|Types
operator|.
name|CLOB
argument_list|)
expr_stmt|;
name|call
operator|.
name|execute
argument_list|()
expr_stmt|;
name|response
operator|=
name|call
operator|.
name|getString
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|call
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|session
operator|.
name|getJdbcConnectionAccess
argument_list|()
operator|.
name|releaseConnection
argument_list|(
name|connection
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|response
operator|==
literal|null
operator|||
name|response
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return
literal|null
return|;
name|StringReader
name|reader
init|=
operator|new
name|StringReader
argument_list|(
name|response
argument_list|)
decl_stmt|;
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
name|reader
argument_list|)
decl_stmt|;
name|reader
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
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|error
argument_list|(
literal|"Unable to receive response: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
finally|finally
block|{
name|_RootDAO
operator|.
name|closeCurrentThreadSessions
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

