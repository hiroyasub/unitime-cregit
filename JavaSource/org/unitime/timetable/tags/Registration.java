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
name|tags
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
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|jsp
operator|.
name|JspException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|jsp
operator|.
name|tagext
operator|.
name|BodyTagSupport
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
name|restlet
operator|.
name|resource
operator|.
name|ClientResource
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
name|defaults
operator|.
name|ApplicationProperty
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
name|QueryLog
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
name|rights
operator|.
name|Right
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
name|util
operator|.
name|Constants
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Registration
extends|extends
name|BodyTagSupport
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|6840487122075978529L
decl_stmt|;
specifier|private
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|Registration
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|String
name|sMessage
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|String
name|sKey
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
name|boolean
name|sRegistered
init|=
literal|false
decl_stmt|;
specifier|private
specifier|static
name|String
name|sNote
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
enum|enum
name|Method
block|{
name|hasMessage
block|,
name|message
block|,
name|status
block|}
empty_stmt|;
specifier|private
name|Method
name|iMethod
init|=
name|Method
operator|.
name|status
decl_stmt|;
specifier|public
name|String
name|getMethod
parameter_list|()
block|{
return|return
name|iMethod
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|setMethod
parameter_list|(
name|String
name|method
parameter_list|)
block|{
name|iMethod
operator|=
name|Method
operator|.
name|valueOf
argument_list|(
name|method
argument_list|)
expr_stmt|;
block|}
specifier|private
name|boolean
name|iUpdate
init|=
literal|false
decl_stmt|;
specifier|public
name|void
name|setUpdate
parameter_list|(
name|boolean
name|update
parameter_list|)
block|{
name|iUpdate
operator|=
name|update
expr_stmt|;
block|}
specifier|public
name|boolean
name|isUpdate
parameter_list|()
block|{
return|return
name|iUpdate
return|;
block|}
specifier|private
specifier|static
name|long
name|sLastRefresh
init|=
operator|-
literal|1
decl_stmt|;
specifier|private
specifier|static
enum|enum
name|Obtrusiveness
block|{
name|high
block|,
name|medium
block|,
name|low
block|,
name|none
block|}
specifier|public
name|SessionContext
name|getSessionContext
parameter_list|()
block|{
return|return
name|HttpSessionContext
operator|.
name|getSessionContext
argument_list|(
name|pageContext
operator|.
name|getServletContext
argument_list|()
argument_list|)
return|;
block|}
specifier|private
specifier|synchronized
name|void
name|init
parameter_list|()
block|{
name|sLastRefresh
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
try|try
block|{
name|File
name|regFile
init|=
operator|new
name|File
argument_list|(
name|ApplicationProperties
operator|.
name|getDataFolder
argument_list|()
argument_list|,
literal|"unitime.reg"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sKey
operator|==
literal|null
operator|&&
name|regFile
operator|.
name|exists
argument_list|()
condition|)
block|{
name|Properties
name|reg
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|FileInputStream
name|in
init|=
operator|new
name|FileInputStream
argument_list|(
name|regFile
argument_list|)
decl_stmt|;
try|try
block|{
name|reg
operator|.
name|load
argument_list|(
name|in
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|sKey
operator|=
name|reg
operator|.
name|getProperty
argument_list|(
literal|"key"
argument_list|)
expr_stmt|;
block|}
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|registration
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|sKey
operator|!=
literal|null
condition|)
name|registration
operator|.
name|put
argument_list|(
literal|"key"
argument_list|,
name|sKey
argument_list|)
expr_stmt|;
else|else
name|sLog
operator|.
name|debug
argument_list|(
literal|"No registration key found..."
argument_list|)
expr_stmt|;
name|registration
operator|.
name|put
argument_list|(
literal|"version"
argument_list|,
name|Constants
operator|.
name|getVersion
argument_list|()
argument_list|)
expr_stmt|;
name|registration
operator|.
name|put
argument_list|(
literal|"sessions"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|QueryLog
operator|.
name|getNrSessions
argument_list|(
literal|31
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|registration
operator|.
name|put
argument_list|(
literal|"users"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|QueryLog
operator|.
name|getNrActiveUsers
argument_list|(
literal|31
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|registration
operator|.
name|put
argument_list|(
literal|"url"
argument_list|,
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getScheme
argument_list|()
operator|+
literal|"://"
operator|+
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getServerName
argument_list|()
operator|+
literal|":"
operator|+
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getServerPort
argument_list|()
operator|+
operator|(
operator|(
name|HttpServletRequest
operator|)
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|)
operator|.
name|getContextPath
argument_list|()
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"Sending the following registration info: "
operator|+
name|registration
argument_list|)
expr_stmt|;
name|Document
name|input
init|=
name|DocumentHelper
operator|.
name|createDocument
argument_list|()
decl_stmt|;
name|Element
name|regEl
init|=
name|input
operator|.
name|addElement
argument_list|(
literal|"registration"
argument_list|)
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|registration
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|regEl
operator|.
name|addElement
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|setText
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|sLog
operator|.
name|debug
argument_list|(
literal|"Contacting registration service..."
argument_list|)
expr_stmt|;
name|ClientResource
name|cr
init|=
operator|new
name|ClientResource
argument_list|(
literal|"http://register.unitime.org/xml"
argument_list|)
decl_stmt|;
name|StringWriter
name|w
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
operator|new
name|XMLWriter
argument_list|(
name|w
argument_list|,
name|OutputFormat
operator|.
name|createPrettyPrint
argument_list|()
argument_list|)
operator|.
name|write
argument_list|(
name|input
argument_list|)
expr_stmt|;
name|w
operator|.
name|flush
argument_list|()
expr_stmt|;
name|w
operator|.
name|close
argument_list|()
expr_stmt|;
name|String
name|result
init|=
name|cr
operator|.
name|post
argument_list|(
name|w
operator|.
name|toString
argument_list|()
argument_list|,
name|String
operator|.
name|class
argument_list|)
decl_stmt|;
name|sLog
operator|.
name|info
argument_list|(
literal|"Registration information received."
argument_list|)
expr_stmt|;
try|try
block|{
name|cr
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
name|StringReader
name|r
init|=
operator|new
name|StringReader
argument_list|(
name|result
argument_list|)
decl_stmt|;
name|Document
name|output
init|=
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|r
argument_list|)
decl_stmt|;
name|r
operator|.
name|close
argument_list|()
expr_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Element
name|e
range|:
operator|(
name|List
argument_list|<
name|Element
argument_list|>
operator|)
name|output
operator|.
name|getRootElement
argument_list|()
operator|.
name|elements
argument_list|()
control|)
block|{
name|ret
operator|.
name|put
argument_list|(
name|e
operator|.
name|getName
argument_list|()
argument_list|,
name|e
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|newKey
init|=
name|ret
operator|.
name|get
argument_list|(
literal|"key"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|newKey
operator|.
name|equals
argument_list|(
name|sKey
argument_list|)
condition|)
block|{
name|sKey
operator|=
name|newKey
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
literal|"New registration key received..."
argument_list|)
expr_stmt|;
name|Properties
name|reg
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|reg
operator|.
name|setProperty
argument_list|(
literal|"key"
argument_list|,
name|sKey
argument_list|)
expr_stmt|;
name|FileOutputStream
name|out
init|=
operator|new
name|FileOutputStream
argument_list|(
name|regFile
argument_list|)
decl_stmt|;
try|try
block|{
name|reg
operator|.
name|store
argument_list|(
name|out
argument_list|,
literal|"UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|" registration file, please do not delete or modify."
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
name|sMessage
operator|=
name|ret
operator|.
name|get
argument_list|(
literal|"message"
argument_list|)
expr_stmt|;
name|sNote
operator|=
name|ret
operator|.
name|get
argument_list|(
literal|"note"
argument_list|)
expr_stmt|;
name|sRegistered
operator|=
literal|"1"
operator|.
name|equals
argument_list|(
name|ret
operator|.
name|get
argument_list|(
literal|"registered"
argument_list|)
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
name|error
argument_list|(
literal|"Validation failed: "
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
specifier|private
name|void
name|refresh
parameter_list|()
block|{
if|if
condition|(
literal|"1"
operator|.
name|equals
argument_list|(
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|.
name|getParameter
argument_list|(
literal|"refresh"
argument_list|)
argument_list|)
operator|||
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|sLastRefresh
operator|>
literal|60
operator|*
literal|60
operator|*
literal|1000
condition|)
name|init
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|doStartTag
parameter_list|()
throws|throws
name|JspException
block|{
if|if
condition|(
name|sLastRefresh
operator|<
literal|0
condition|)
name|init
argument_list|()
expr_stmt|;
switch|switch
condition|(
name|iMethod
condition|)
block|{
case|case
name|hasMessage
case|:
if|if
condition|(
operator|!
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Registration
argument_list|)
condition|)
return|return
name|SKIP_BODY
return|;
try|try
block|{
name|refresh
argument_list|()
expr_stmt|;
if|if
condition|(
name|Registration
operator|.
name|sNote
operator|!=
literal|null
operator|&&
operator|!
name|Registration
operator|.
name|sNote
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|EVAL_BODY_INCLUDE
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
return|return
name|SKIP_BODY
return|;
default|default:
return|return
name|EVAL_BODY_BUFFERED
return|;
block|}
block|}
specifier|public
name|int
name|doEndTag
parameter_list|()
throws|throws
name|JspException
block|{
switch|switch
condition|(
name|iMethod
condition|)
block|{
case|case
name|hasMessage
case|:
return|return
name|EVAL_PAGE
return|;
case|case
name|message
case|:
try|try
block|{
if|if
condition|(
name|Registration
operator|.
name|sNote
operator|!=
literal|null
condition|)
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|Registration
operator|.
name|sNote
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
return|return
name|EVAL_PAGE
return|;
case|case
name|status
case|:
if|if
condition|(
name|sMessage
operator|==
literal|null
condition|)
return|return
name|EVAL_PAGE
return|;
try|try
block|{
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
name|sMessage
argument_list|)
expr_stmt|;
if|if
condition|(
name|isUpdate
argument_list|()
condition|)
block|{
name|Obtrusiveness
name|obtrusiveness
init|=
name|Obtrusiveness
operator|.
name|valueOf
argument_list|(
name|ApplicationProperty
operator|.
name|RegistrationPopupObtrusiveness
operator|.
name|value
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|getSessionContext
argument_list|()
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|Registration
argument_list|)
condition|)
block|{
name|String
name|backUrl
init|=
name|URLEncoder
operator|.
name|encode
argument_list|(
operator|(
operator|(
name|HttpServletRequest
operator|)
name|pageContext
operator|.
name|getRequest
argument_list|()
operator|)
operator|.
name|getRequestURL
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"?refresh=1"
argument_list|,
literal|"ISO-8859-1"
argument_list|)
decl_stmt|;
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<br><span style=\"font-size: x-small;\">Click<a "
operator|+
literal|"onMouseOver=\"this.style.cursor='hand';this.style.cursor='pointer';\" "
operator|+
literal|"onClick=\"showGwtDialog('UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|" Registration', 'https://unitimereg.appspot.com?key="
operator|+
name|sKey
operator|+
literal|"&back="
operator|+
name|backUrl
operator|+
literal|"', '750px', '75%');\" "
operator|+
literal|"title='UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|" Registration'>here</a> to "
operator|+
operator|(
name|sRegistered
condition|?
literal|"update the current registration"
else|:
literal|"register"
operator|)
operator|+
literal|"."
operator|+
literal|"</span>"
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|obtrusiveness
condition|)
block|{
case|case
name|low
case|:
if|if
condition|(
name|sRegistered
condition|)
break|break;
case|case
name|high
case|:
case|case
name|medium
case|:
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<script>function gwtOnLoad() { gwtShowMessage(\""
operator|+
name|sMessage
operator|+
literal|"<br><span style='font-size: x-small;'>Click<a "
operator|+
literal|"onMouseOver=\\\"this.style.cursor='hand';this.style.cursor='pointer';\\\" "
operator|+
literal|"onCLick=\\\"showGwtDialog('UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|" Registration', 'https://unitimereg.appspot.com?key="
operator|+
name|sKey
operator|+
literal|"&back="
operator|+
name|backUrl
operator|+
literal|"', '750px', '75%');\\\" "
operator|+
literal|"title='UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|" Registration'>here</a> to "
operator|+
operator|(
name|sRegistered
condition|?
literal|"update the current registration"
else|:
literal|"register"
operator|)
operator|+
literal|"."
operator|+
literal|"</span>\"); }</script>"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
switch|switch
condition|(
name|obtrusiveness
condition|)
block|{
case|case
name|medium
case|:
if|if
condition|(
name|sRegistered
condition|)
break|break;
case|case
name|high
case|:
name|pageContext
operator|.
name|getOut
argument_list|()
operator|.
name|println
argument_list|(
literal|"<script>function gwtOnLoad() { gwtShowMessage(\""
operator|+
name|sMessage
operator|+
literal|"\"); }</script>"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
block|}
return|return
name|EVAL_PAGE
return|;
default|default:
return|return
name|EVAL_PAGE
return|;
block|}
block|}
block|}
end_class

end_unit

