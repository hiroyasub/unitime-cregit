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
name|export
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
name|javax
operator|.
name|servlet
operator|.
name|ServletException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServlet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
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
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|NoSuchBeanDefinitionException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|context
operator|.
name|WebApplicationContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|web
operator|.
name|context
operator|.
name|support
operator|.
name|WebApplicationContextUtils
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
name|gwt
operator|.
name|shared
operator|.
name|PageAccessException
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|context
operator|.
name|HttpSessionContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExportServlet
extends|extends
name|HttpServlet
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
name|ExportServlet
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|protected
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|getServletContext
argument_list|()
argument_list|)
return|;
block|}
specifier|protected
name|Exporter
name|getExporter
parameter_list|(
name|String
name|reference
parameter_list|)
block|{
name|WebApplicationContext
name|applicationContext
init|=
name|WebApplicationContextUtils
operator|.
name|getWebApplicationContext
argument_list|(
name|getServletContext
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
name|Exporter
operator|)
name|applicationContext
operator|.
name|getBean
argument_list|(
literal|"org.unitime.timetable.export.Exporter:"
operator|+
name|reference
argument_list|)
return|;
block|}
specifier|public
name|void
name|doGet
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|ServletException
throws|,
name|IOException
block|{
name|ExportServletHelper
name|helper
init|=
literal|null
decl_stmt|;
name|String
name|ref
init|=
literal|null
decl_stmt|;
try|try
block|{
name|helper
operator|=
operator|new
name|ExportServletHelper
argument_list|(
name|request
argument_list|,
name|response
argument_list|,
name|getSessionContext
argument_list|()
argument_list|)
expr_stmt|;
name|ref
operator|=
name|helper
operator|.
name|getParameter
argument_list|(
literal|"output"
argument_list|)
expr_stmt|;
if|if
condition|(
name|ref
operator|==
literal|null
condition|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"No exporter provided."
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"No exporter provided, please set the output parameter."
argument_list|)
expr_stmt|;
return|return;
block|}
name|getExporter
argument_list|(
name|ref
argument_list|)
operator|.
name|export
argument_list|(
name|helper
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchBeanDefinitionException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|info
argument_list|(
literal|"Exporter "
operator|+
name|ref
operator|+
literal|" not known."
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
literal|"Exporter "
operator|+
name|ref
operator|+
literal|" not known, please check the output parameter."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|info
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_BAD_REQUEST
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PageAccessException
name|e
parameter_list|)
block|{
name|sLog
operator|.
name|info
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_FORBIDDEN
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
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
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|response
operator|.
name|sendError
argument_list|(
name|HttpServletResponse
operator|.
name|SC_INTERNAL_SERVER_ERROR
argument_list|,
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
if|if
condition|(
name|helper
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|helper
operator|.
name|hasOutputStream
argument_list|()
condition|)
block|{
name|helper
operator|.
name|getOutputStream
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
name|helper
operator|.
name|getOutputStream
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|helper
operator|.
name|hasWriter
argument_list|()
condition|)
block|{
name|helper
operator|.
name|getWriter
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
name|helper
operator|.
name|getWriter
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

