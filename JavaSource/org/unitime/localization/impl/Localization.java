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
name|localization
operator|.
name|impl
package|;
end_package

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
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|InvocationHandler
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
name|lang
operator|.
name|reflect
operator|.
name|Proxy
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormatSymbols
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|NumberFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
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
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|java
operator|.
name|util
operator|.
name|StringTokenizer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
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
name|util
operator|.
name|Formats
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|gwt
operator|.
name|i18n
operator|.
name|client
operator|.
name|Messages
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Localization
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
name|Localization
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ROOT
init|=
literal|"org.unitime.localization.messages."
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|GWTROOT
init|=
literal|"org.unitime.timetable.gwt.resources."
decl_stmt|;
specifier|private
specifier|static
name|Map
argument_list|<
name|Class
argument_list|,
name|Object
argument_list|>
name|sBundles
init|=
operator|new
name|Hashtable
argument_list|<
name|Class
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Pattern
name|sRtlLocalesRe
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"^(ar|dv|he|iw|fa|nqo|ps|sd|ug|ur|yi|.*[-_](Arab|Hebr|Thaa|Nkoo|Tfng))"
operator|+
literal|"(?!.*[-_](Latn|Cyrl)($|-|_))($|-|_)"
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|ThreadLocal
argument_list|<
name|String
argument_list|>
name|sLocale
init|=
operator|new
name|ThreadLocal
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|String
name|initialValue
parameter_list|()
block|{
return|return
name|ApplicationProperty
operator|.
name|Locale
operator|.
name|value
argument_list|()
return|;
block|}
block|}
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|ThreadLocal
argument_list|<
name|Locale
argument_list|>
name|sJavaLocale
init|=
operator|new
name|ThreadLocal
argument_list|<
name|Locale
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|Locale
name|initialValue
parameter_list|()
block|{
return|return
name|guessJavaLocale
argument_list|(
name|ApplicationProperty
operator|.
name|Locale
operator|.
name|value
argument_list|()
argument_list|)
return|;
block|}
block|}
decl_stmt|;
specifier|public
specifier|static
name|void
name|setLocale
parameter_list|(
name|String
name|locale
parameter_list|)
block|{
name|sLocale
operator|.
name|set
argument_list|(
name|locale
argument_list|)
expr_stmt|;
name|sJavaLocale
operator|.
name|set
argument_list|(
name|guessJavaLocale
argument_list|(
name|locale
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|removeLocale
parameter_list|()
block|{
name|sLocale
operator|.
name|remove
argument_list|()
expr_stmt|;
name|sJavaLocale
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|String
name|getLocale
parameter_list|()
block|{
return|return
name|sLocale
operator|.
name|get
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Locale
name|getJavaLocale
parameter_list|()
block|{
return|return
name|sJavaLocale
operator|.
name|get
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|DateFormat
name|getDateFormat
parameter_list|(
name|String
name|pattern
parameter_list|)
block|{
return|return
operator|new
name|SimpleDateFormat
argument_list|(
name|pattern
argument_list|,
name|getJavaLocale
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|NumberFormat
name|getNumberFormat
parameter_list|(
name|String
name|pattern
parameter_list|)
block|{
return|return
operator|new
name|DecimalFormat
argument_list|(
name|pattern
argument_list|,
operator|new
name|DecimalFormatSymbols
argument_list|(
name|getJavaLocale
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isRTL
parameter_list|()
block|{
name|String
name|locale
init|=
name|getLocale
argument_list|()
decl_stmt|;
return|return
name|locale
operator|!=
literal|null
operator|&&
name|sRtlLocalesRe
operator|.
name|matcher
argument_list|(
name|locale
argument_list|)
operator|.
name|find
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|String
name|getFirstLocale
parameter_list|()
block|{
name|String
name|locale
init|=
name|getLocale
argument_list|()
decl_stmt|;
if|if
condition|(
name|locale
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
operator|>=
literal|0
condition|)
name|locale
operator|=
name|locale
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|locale
operator|.
name|indexOf
argument_list|(
literal|','
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|locale
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
operator|>=
literal|0
condition|)
name|locale
operator|=
name|locale
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|locale
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|locale
operator|.
name|trim
argument_list|()
return|;
block|}
specifier|private
specifier|static
name|Locale
name|guessJavaLocale
parameter_list|(
name|String
name|locale
parameter_list|)
block|{
for|for
control|(
name|StringTokenizer
name|s
init|=
operator|new
name|StringTokenizer
argument_list|(
name|locale
argument_list|,
literal|",;"
argument_list|)
init|;
name|s
operator|.
name|hasMoreTokens
argument_list|()
condition|;
control|)
block|{
name|String
name|lang
init|=
name|s
operator|.
name|nextToken
argument_list|()
decl_stmt|;
name|String
name|cc
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|lang
operator|.
name|indexOf
argument_list|(
literal|'_'
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|cc
operator|=
name|lang
operator|.
name|substring
argument_list|(
name|lang
operator|.
name|indexOf
argument_list|(
literal|'_'
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
name|lang
operator|=
name|lang
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|lang
operator|.
name|indexOf
argument_list|(
literal|'_'
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Locale
name|loc
range|:
name|Locale
operator|.
name|getAvailableLocales
argument_list|()
control|)
if|if
condition|(
operator|(
name|lang
operator|==
literal|null
operator|||
name|lang
operator|.
name|isEmpty
argument_list|()
operator|||
name|lang
operator|.
name|equals
argument_list|(
name|loc
operator|.
name|getLanguage
argument_list|()
argument_list|)
operator|)
operator|&&
operator|(
name|cc
operator|==
literal|null
operator|||
name|cc
operator|.
name|isEmpty
argument_list|()
operator|||
name|cc
operator|.
name|equals
argument_list|(
name|loc
operator|.
name|getCountry
argument_list|()
argument_list|)
operator|)
condition|)
block|{
return|return
name|loc
return|;
block|}
block|}
return|return
name|Locale
operator|.
name|getDefault
argument_list|()
return|;
block|}
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|T
name|create
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|bundle
parameter_list|)
block|{
synchronized|synchronized
init|(
name|sBundles
init|)
block|{
name|T
name|ret
init|=
operator|(
name|T
operator|)
name|sBundles
operator|.
name|get
argument_list|(
name|bundle
argument_list|)
decl_stmt|;
if|if
condition|(
name|ret
operator|==
literal|null
condition|)
block|{
name|ret
operator|=
operator|(
name|T
operator|)
name|Proxy
operator|.
name|newProxyInstance
argument_list|(
name|Localization
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
operator|new
name|Class
index|[]
block|{
name|bundle
block|,
name|StrutsActionsRetriever
operator|.
name|class
block|}
argument_list|,
operator|new
name|Bundle
argument_list|(
name|bundle
argument_list|)
argument_list|)
expr_stmt|;
name|sBundles
operator|.
name|put
argument_list|(
name|bundle
argument_list|,
name|ret
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Bundle
implements|implements
name|InvocationHandler
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Properties
argument_list|>
name|iProperties
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Properties
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Class
argument_list|<
name|?
argument_list|>
name|iMessages
init|=
literal|null
decl_stmt|;
specifier|public
name|Bundle
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|messages
parameter_list|)
block|{
name|iMessages
operator|=
name|messages
expr_stmt|;
block|}
specifier|private
specifier|synchronized
name|String
name|getProperty
parameter_list|(
name|String
name|locale
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|Properties
name|properties
init|=
name|iProperties
operator|.
name|get
argument_list|(
name|locale
argument_list|)
decl_stmt|;
if|if
condition|(
name|properties
operator|==
literal|null
condition|)
block|{
name|properties
operator|=
operator|new
name|Properties
argument_list|()
expr_stmt|;
name|String
name|resource
init|=
name|iMessages
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
operator|+
operator|(
name|locale
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
literal|"_"
operator|+
name|locale
operator|)
operator|+
literal|".properties"
decl_stmt|;
try|try
block|{
name|InputStream
name|is
init|=
name|Localization
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|resource
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|!=
literal|null
condition|)
name|properties
operator|.
name|load
argument_list|(
name|is
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
literal|"Failed to load message bundle "
operator|+
name|iMessages
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
name|iMessages
operator|.
name|getName
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
operator|+
literal|" for "
operator|+
name|locale
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
block|}
name|iProperties
operator|.
name|put
argument_list|(
name|locale
argument_list|,
name|properties
argument_list|)
expr_stmt|;
block|}
return|return
name|properties
operator|.
name|getProperty
argument_list|(
name|name
argument_list|)
return|;
block|}
specifier|private
name|String
name|getProperty
parameter_list|(
name|String
name|name
parameter_list|)
block|{
for|for
control|(
name|String
name|locale
range|:
name|getLocale
argument_list|()
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
if|if
condition|(
name|locale
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
operator|>=
literal|0
condition|)
name|locale
operator|=
name|locale
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|locale
operator|.
name|indexOf
argument_list|(
literal|';'
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|value
init|=
name|getProperty
argument_list|(
name|locale
operator|.
name|trim
argument_list|()
argument_list|,
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
return|return
name|value
return|;
if|if
condition|(
name|locale
operator|.
name|indexOf
argument_list|(
literal|'_'
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|locale
operator|=
name|locale
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|locale
operator|.
name|indexOf
argument_list|(
literal|'_'
argument_list|)
argument_list|)
expr_stmt|;
name|value
operator|=
name|getProperty
argument_list|(
name|locale
operator|.
name|trim
argument_list|()
argument_list|,
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
return|return
name|value
return|;
block|}
block|}
return|return
name|getProperty
argument_list|(
literal|""
argument_list|,
name|name
argument_list|)
return|;
comment|// try default message bundle
block|}
specifier|private
name|String
name|fillArgumentsIn
parameter_list|(
name|String
name|value
parameter_list|,
name|Object
index|[]
name|args
parameter_list|,
name|int
name|firstIndex
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
operator|||
name|args
operator|==
literal|null
condition|)
return|return
name|value
return|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|+
name|firstIndex
operator|<
name|args
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|value
operator|=
name|value
operator|.
name|replace
argument_list|(
literal|"{"
operator|+
name|i
operator|+
literal|"}"
argument_list|,
operator|(
name|args
index|[
name|i
operator|+
name|firstIndex
index|]
operator|==
literal|null
condition|?
literal|""
else|:
name|args
index|[
name|i
operator|+
name|firstIndex
index|]
operator|.
name|toString
argument_list|()
operator|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|.
name|indexOf
argument_list|(
literal|"{"
operator|+
name|i
operator|+
literal|",number,"
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|int
name|idx
init|=
name|value
operator|.
name|indexOf
argument_list|(
literal|"{"
operator|+
name|i
operator|+
literal|",number,"
argument_list|)
decl_stmt|;
name|String
name|pattern
init|=
name|value
operator|.
name|substring
argument_list|(
name|idx
operator|+
operator|(
literal|"{"
operator|+
name|i
operator|+
literal|",number,"
operator|)
operator|.
name|length
argument_list|()
argument_list|,
name|value
operator|.
name|indexOf
argument_list|(
literal|'}'
argument_list|,
name|idx
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|number
init|=
operator|(
name|args
index|[
name|i
operator|+
name|firstIndex
index|]
operator|==
literal|null
condition|?
literal|""
else|:
name|Formats
operator|.
name|getNumberFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|format
argument_list|(
operator|(
name|Number
operator|)
name|args
index|[
name|i
operator|+
name|firstIndex
index|]
argument_list|)
operator|)
decl_stmt|;
name|value
operator|=
name|value
operator|.
name|replace
argument_list|(
literal|"{"
operator|+
name|i
operator|+
literal|",number,"
operator|+
name|pattern
operator|+
literal|"}"
argument_list|,
name|number
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|value
return|;
block|}
specifier|private
name|String
index|[]
name|string2array
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
name|value
operator|.
name|split
argument_list|(
literal|"(?<=^.*[^\\\\]),(?=.*$)"
argument_list|)
return|;
block|}
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|array2map
parameter_list|(
name|String
index|[]
name|value
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
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
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|value
operator|.
name|length
operator|-
literal|1
condition|;
name|i
operator|+=
literal|2
control|)
name|map
operator|.
name|put
argument_list|(
name|value
index|[
name|i
index|]
argument_list|,
name|value
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
return|return
name|map
return|;
block|}
specifier|private
name|Object
name|type
parameter_list|(
name|String
name|value
parameter_list|,
name|Class
name|returnType
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
return|return
name|value
return|;
if|if
condition|(
name|String
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
condition|)
return|return
name|value
return|;
if|if
condition|(
name|Boolean
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
operator|||
name|boolean
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
condition|)
return|return
literal|"true"
operator|.
name|equalsIgnoreCase
argument_list|(
name|value
argument_list|)
return|;
if|if
condition|(
name|Double
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
operator|||
name|double
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
condition|)
return|return
name|Double
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
return|;
if|if
condition|(
name|Float
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
operator|||
name|float
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
condition|)
return|return
name|Float
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
return|;
if|if
condition|(
name|Integer
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
operator|||
name|int
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
condition|)
return|return
name|Integer
operator|.
name|valueOf
argument_list|(
name|value
argument_list|)
return|;
if|if
condition|(
name|String
index|[]
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
condition|)
return|return
name|string2array
argument_list|(
name|value
argument_list|)
return|;
if|if
condition|(
name|Map
operator|.
name|class
operator|.
name|equals
argument_list|(
name|returnType
argument_list|)
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
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
name|String
name|key
range|:
name|string2array
argument_list|(
name|value
argument_list|)
control|)
block|{
name|String
name|val
init|=
name|getProperty
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|val
operator|!=
literal|null
condition|)
name|map
operator|.
name|put
argument_list|(
name|key
operator|.
name|trim
argument_list|()
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|map
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
name|array2map
argument_list|(
name|string2array
argument_list|(
name|value
argument_list|)
argument_list|)
return|;
return|return
name|map
return|;
block|}
return|return
name|value
return|;
block|}
specifier|private
name|String
index|[]
name|fixStringArray
parameter_list|(
name|String
index|[]
name|value
parameter_list|,
name|String
index|[]
name|defaults
parameter_list|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
name|value
operator|.
name|length
operator|<
name|defaults
operator|.
name|length
condition|)
block|{
name|String
index|[]
name|fixed
init|=
name|Arrays
operator|.
name|copyOf
argument_list|(
name|value
argument_list|,
name|defaults
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|value
operator|.
name|length
init|;
name|i
operator|<
name|defaults
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|fixed
index|[
name|i
index|]
operator|=
name|defaults
index|[
name|i
index|]
expr_stmt|;
return|return
name|fixed
return|;
block|}
return|return
name|value
return|;
block|}
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fixStringMap
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|value
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|defaults
parameter_list|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
operator|&&
operator|!
name|value
operator|.
name|keySet
argument_list|()
operator|.
name|equals
argument_list|(
name|defaults
operator|.
name|keySet
argument_list|()
argument_list|)
condition|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|fixed
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|(
name|value
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
name|e
range|:
name|defaults
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|value
operator|.
name|containsKey
argument_list|(
name|e
operator|.
name|getKey
argument_list|()
argument_list|)
condition|)
name|fixed
operator|.
name|put
argument_list|(
name|e
operator|.
name|getKey
argument_list|()
argument_list|,
name|e
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|fixed
return|;
block|}
return|return
name|value
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|invoke
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Method
name|method
parameter_list|,
name|Object
index|[]
name|args
parameter_list|)
throws|throws
name|Throwable
block|{
if|if
condition|(
literal|"getStrutsActions"
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|method
operator|.
name|getParameterTypes
argument_list|()
operator|.
name|length
operator|==
literal|1
condition|)
return|return
name|getStrutsActions
argument_list|(
name|proxy
argument_list|,
operator|(
name|Class
argument_list|<
name|?
extends|extends
name|LocalizedLookupDispatchAction
argument_list|>
operator|)
name|args
index|[
literal|0
index|]
argument_list|)
return|;
if|if
condition|(
literal|"translateMessage"
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
operator|&&
name|method
operator|.
name|getParameterTypes
argument_list|()
operator|.
name|length
operator|>=
literal|2
condition|)
block|{
name|String
name|value
init|=
operator|(
name|args
index|[
literal|0
index|]
operator|==
literal|null
condition|?
literal|null
else|:
name|getProperty
argument_list|(
operator|(
name|String
operator|)
name|args
index|[
literal|0
index|]
argument_list|)
operator|)
decl_stmt|;
return|return
operator|(
name|value
operator|==
literal|null
condition|?
operator|(
name|String
operator|)
name|args
index|[
literal|1
index|]
else|:
name|fillArgumentsIn
argument_list|(
name|value
argument_list|,
name|args
argument_list|,
literal|2
argument_list|)
operator|)
return|;
block|}
name|String
name|value
init|=
name|getProperty
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|Object
name|ret
init|=
name|type
argument_list|(
name|fillArgumentsIn
argument_list|(
name|value
argument_list|,
name|args
argument_list|,
literal|0
argument_list|)
argument_list|,
name|method
operator|.
name|getReturnType
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|String
index|[]
operator|.
name|class
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getReturnType
argument_list|()
argument_list|)
condition|)
block|{
name|Constants
operator|.
name|DefaultStringArrayValue
name|dsa
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultStringArrayValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|dsa
operator|!=
literal|null
condition|)
return|return
name|fixStringArray
argument_list|(
operator|(
name|String
index|[]
operator|)
name|ret
argument_list|,
name|dsa
operator|.
name|value
argument_list|()
argument_list|)
return|;
block|}
if|if
condition|(
name|Map
operator|.
name|class
operator|.
name|equals
argument_list|(
name|method
operator|.
name|getReturnType
argument_list|()
argument_list|)
condition|)
block|{
name|Constants
operator|.
name|DefaultStringMapValue
name|dsm
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultStringMapValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|dsm
operator|!=
literal|null
condition|)
return|return
name|fixStringMap
argument_list|(
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
operator|)
name|ret
argument_list|,
name|array2map
argument_list|(
name|dsm
operator|.
name|value
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
return|return
name|ret
return|;
block|}
name|Messages
operator|.
name|DefaultMessage
name|dm
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Messages
operator|.
name|DefaultMessage
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|dm
operator|!=
literal|null
condition|)
return|return
name|fillArgumentsIn
argument_list|(
name|dm
operator|.
name|value
argument_list|()
argument_list|,
name|args
argument_list|,
literal|0
argument_list|)
return|;
name|Constants
operator|.
name|DefaultBooleanValue
name|db
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultBooleanValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|db
operator|!=
literal|null
condition|)
return|return
name|db
operator|.
name|value
argument_list|()
return|;
name|Constants
operator|.
name|DefaultDoubleValue
name|dd
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultDoubleValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|dd
operator|!=
literal|null
condition|)
return|return
name|dd
operator|.
name|value
argument_list|()
return|;
name|Constants
operator|.
name|DefaultFloatValue
name|df
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultFloatValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|df
operator|!=
literal|null
condition|)
return|return
name|df
operator|.
name|value
argument_list|()
return|;
name|Constants
operator|.
name|DefaultIntValue
name|di
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultIntValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|di
operator|!=
literal|null
condition|)
return|return
name|di
operator|.
name|value
argument_list|()
return|;
name|Constants
operator|.
name|DefaultStringValue
name|ds
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultStringValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|ds
operator|!=
literal|null
condition|)
return|return
name|ds
operator|.
name|value
argument_list|()
return|;
name|Constants
operator|.
name|DefaultStringArrayValue
name|dsa
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultStringArrayValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|dsa
operator|!=
literal|null
condition|)
return|return
name|dsa
operator|.
name|value
argument_list|()
return|;
name|Constants
operator|.
name|DefaultStringMapValue
name|dsm
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DefaultStringMapValue
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|dsm
operator|!=
literal|null
condition|)
return|return
name|array2map
argument_list|(
name|dsm
operator|.
name|value
argument_list|()
argument_list|)
return|;
return|return
name|method
operator|.
name|getName
argument_list|()
return|;
block|}
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getStrutsActions
parameter_list|(
name|Object
name|proxy
parameter_list|,
name|Class
argument_list|<
name|?
extends|extends
name|LocalizedLookupDispatchAction
argument_list|>
name|apply
parameter_list|)
throws|throws
name|Throwable
block|{
name|Map
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
name|Method
name|m
range|:
name|iMessages
operator|.
name|getDeclaredMethods
argument_list|()
control|)
block|{
if|if
condition|(
name|m
operator|.
name|getParameterTypes
argument_list|()
operator|.
name|length
operator|>
literal|0
condition|)
continue|continue;
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|Messages
operator|.
name|StrutsAction
name|action
init|=
name|m
operator|.
name|getAnnotation
argument_list|(
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|Messages
operator|.
name|StrutsAction
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|action
operator|!=
literal|null
condition|)
block|{
name|Messages
operator|.
name|DefaultMessage
name|dm
init|=
name|m
operator|.
name|getAnnotation
argument_list|(
name|Messages
operator|.
name|DefaultMessage
operator|.
name|class
argument_list|)
decl_stmt|;
if|if
condition|(
name|action
operator|.
name|apply
argument_list|()
operator|==
literal|null
operator|||
name|action
operator|.
name|apply
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
block|{
try|try
block|{
if|if
condition|(
name|apply
operator|.
name|getMethod
argument_list|(
name|action
operator|.
name|value
argument_list|()
argument_list|,
operator|new
name|Class
argument_list|<
name|?
argument_list|>
index|[]
block|{
name|ActionMapping
operator|.
name|class
operator|,
name|ActionForm
operator|.
name|class
operator|,
name|HttpServletRequest
operator|.
name|class
operator|,
name|HttpServletResponse
operator|.
name|class
block|}
block_content|)
block|!= null
block_content|)
block|{
name|ret
operator|.
name|put
argument_list|(
operator|(
name|String
operator|)
name|invoke
argument_list|(
name|proxy
argument_list|,
name|m
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
argument_list|,
name|action
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|dm
operator|!=
literal|null
condition|)
name|ret
operator|.
name|put
argument_list|(
name|dm
operator|.
name|value
argument_list|()
argument_list|,
name|action
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
block|}
block|}
else|else
block|{
for|for
control|(
name|Class
argument_list|<
name|?
extends|extends
name|LocalizedLookupDispatchAction
argument_list|>
name|a
range|:
name|action
operator|.
name|apply
argument_list|()
control|)
if|if
condition|(
name|a
operator|.
name|equals
argument_list|(
name|apply
argument_list|)
condition|)
block|{
name|ret
operator|.
name|put
argument_list|(
operator|(
name|String
operator|)
name|invoke
argument_list|(
name|proxy
argument_list|,
name|m
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
argument_list|,
name|action
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|dm
operator|!=
literal|null
condition|)
name|ret
operator|.
name|put
argument_list|(
name|dm
operator|.
name|value
argument_list|()
argument_list|,
name|action
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|ret
return|;
block|}
end_class

begin_interface
unit|} 	 	public
specifier|static
interface|interface
name|StrutsActionsRetriever
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getStrutsActions
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|LocalizedLookupDispatchAction
argument_list|>
name|apply
parameter_list|)
function_decl|;
block|}
end_interface

unit|}
end_unit

