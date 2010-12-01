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
name|solver
operator|.
name|ui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

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
name|FileOutputStream
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
name|DocumentHelper
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
name|unitime
operator|.
name|timetable
operator|.
name|ApplicationProperties
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|TimetableInfoUtil
implements|implements
name|TimetableInfoFileProxy
block|{
specifier|private
specifier|static
name|TimetableInfoUtil
name|sInstance
init|=
operator|new
name|TimetableInfoUtil
argument_list|()
decl_stmt|;
specifier|private
name|TimetableInfoUtil
parameter_list|()
block|{
block|}
specifier|public
specifier|static
name|TimetableInfoUtil
name|getInstance
parameter_list|()
block|{
return|return
name|sInstance
return|;
block|}
specifier|public
name|void
name|saveToFile
parameter_list|(
name|String
name|name
parameter_list|,
name|TimetableInfo
name|info
parameter_list|)
throws|throws
name|Exception
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|ApplicationProperties
operator|.
name|getBlobFolder
argument_list|()
argument_list|,
name|name
argument_list|)
decl_stmt|;
name|file
operator|.
name|getParentFile
argument_list|()
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
name|FileOutputStream
name|out
init|=
literal|null
decl_stmt|;
try|try
block|{
name|out
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
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
name|Document
name|document
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|addElement
argument_list|(
name|info
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|info
operator|.
name|save
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|writer
operator|.
name|write
argument_list|(
name|document
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
name|out
operator|=
literal|null
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
if|if
condition|(
name|out
operator|!=
literal|null
condition|)
name|out
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
specifier|public
name|TimetableInfo
name|loadFromFile
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|ApplicationProperties
operator|.
name|getBlobFolder
argument_list|()
argument_list|,
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
return|return
literal|null
return|;
name|Document
name|document
init|=
literal|null
decl_stmt|;
name|GZIPInputStream
name|gzipInput
init|=
literal|null
decl_stmt|;
try|try
block|{
name|gzipInput
operator|=
operator|new
name|GZIPInputStream
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
name|document
operator|=
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|gzipInput
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|gzipInput
operator|!=
literal|null
condition|)
name|gzipInput
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|Element
name|root
init|=
name|document
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
name|String
name|infoClassName
init|=
name|root
operator|.
name|getName
argument_list|()
decl_stmt|;
name|Class
name|infoClass
init|=
name|Class
operator|.
name|forName
argument_list|(
name|infoClassName
argument_list|)
decl_stmt|;
name|TimetableInfo
name|info
init|=
operator|(
name|TimetableInfo
operator|)
name|infoClass
operator|.
name|getConstructor
argument_list|(
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|newInstance
argument_list|(
operator|new
name|Object
index|[]
block|{}
argument_list|)
decl_stmt|;
name|info
operator|.
name|load
argument_list|(
name|root
argument_list|)
expr_stmt|;
return|return
name|info
return|;
block|}
specifier|public
name|void
name|deleteFile
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|Exception
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|ApplicationProperties
operator|.
name|getBlobFolder
argument_list|()
argument_list|,
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

