begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to The Apereo Foundation under one or more contributor license  * agreements. See the NOTICE file distributed with this work for  * additional information regarding copyright ownership.  *  * The Apereo Foundation licenses this file to you under the Apache License,  * Version 2.0 (the "License"); you may not use this file except in  * compliance with the License. You may obtain a copy of the License at:  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  * See the License for the specific language governing permissions and  * limitations under the License.  *  */
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|jgroups
operator|.
name|CourseSolverContainer
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
name|solver
operator|.
name|jgroups
operator|.
name|SolverServer
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
name|solver
operator|.
name|jgroups
operator|.
name|SolverServerImplementation
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
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|TimetableInfoUtil
operator|.
name|class
argument_list|)
decl_stmt|;
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
name|getLocalInstance
parameter_list|()
block|{
return|return
name|sInstance
return|;
block|}
specifier|public
specifier|static
name|TimetableInfoFileProxy
name|getInstance
parameter_list|()
block|{
comment|// Create the cluster instance
name|SolverServer
name|server
init|=
name|SolverServerImplementation
operator|.
name|getInstance
argument_list|()
decl_stmt|;
if|if
condition|(
name|server
operator|!=
literal|null
operator|&&
name|server
operator|.
name|getCourseSolverContainer
argument_list|()
operator|!=
literal|null
condition|)
return|return
operator|(
operator|(
name|CourseSolverContainer
operator|)
name|server
operator|.
name|getCourseSolverContainer
argument_list|()
operator|)
operator|.
name|getFileProxy
argument_list|()
return|;
comment|// Fall back to local instance
return|return
name|getLocalInstance
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|saveToFile
parameter_list|(
name|String
name|name
parameter_list|,
name|TimetableInfo
name|info
parameter_list|)
block|{
name|FileOutputStream
name|out
init|=
literal|null
decl_stmt|;
try|try
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
name|sLog
operator|.
name|info
argument_list|(
literal|"Saved info "
operator|+
name|name
operator|+
literal|" as "
operator|+
name|file
operator|+
literal|" ("
operator|+
name|file
operator|.
name|length
argument_list|()
operator|+
literal|" bytes)"
argument_list|)
expr_stmt|;
return|return
literal|true
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
name|warn
argument_list|(
literal|"Failed to save info "
operator|+
name|name
operator|+
literal|": "
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
literal|false
return|;
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
block|{
try|try
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
name|sLog
operator|.
name|info
argument_list|(
literal|"Loading info "
operator|+
name|name
operator|+
literal|" from "
operator|+
name|file
operator|+
literal|" ("
operator|+
name|file
operator|.
name|length
argument_list|()
operator|+
literal|" bytes)"
argument_list|)
expr_stmt|;
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
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|warn
argument_list|(
literal|"Failed to load info "
operator|+
name|name
operator|+
literal|": "
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
block|}
specifier|public
name|boolean
name|deleteFile
parameter_list|(
name|String
name|name
parameter_list|)
block|{
try|try
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
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Deleting info "
operator|+
name|name
operator|+
literal|" as "
operator|+
name|file
operator|+
literal|" ("
operator|+
name|file
operator|.
name|length
argument_list|()
operator|+
literal|" bytes)"
argument_list|)
expr_stmt|;
name|file
operator|.
name|delete
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
return|return
literal|false
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
name|warn
argument_list|(
literal|"Failed to delete info "
operator|+
name|name
operator|+
literal|": "
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
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

