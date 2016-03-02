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
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
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
name|java
operator|.
name|io
operator|.
name|PrintStream
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
name|util
operator|.
name|ArrayList
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
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|tools
operator|.
name|ant
operator|.
name|BuildException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|tools
operator|.
name|ant
operator|.
name|Project
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
name|resources
operator|.
name|Constants
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
name|resources
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
name|ExportTranslations
block|{
specifier|private
name|List
argument_list|<
name|Bundle
argument_list|>
name|iBundles
init|=
operator|new
name|ArrayList
argument_list|<
name|Bundle
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|List
argument_list|<
name|Locale
argument_list|>
name|iLocales
init|=
operator|new
name|ArrayList
argument_list|<
name|Locale
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|File
name|iBaseDir
decl_stmt|;
specifier|private
name|String
name|iTranslations
init|=
literal|"Documentation/Translations"
decl_stmt|;
specifier|private
name|File
name|iSource
decl_stmt|;
specifier|private
name|Project
name|iProject
init|=
literal|null
decl_stmt|;
specifier|public
name|ExportTranslations
parameter_list|()
block|{
block|}
specifier|public
name|void
name|setProject
parameter_list|(
name|Project
name|project
parameter_list|)
block|{
name|iProject
operator|=
name|project
expr_stmt|;
name|iBaseDir
operator|=
name|project
operator|.
name|getBaseDir
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setSource
parameter_list|(
name|String
name|source
parameter_list|)
block|{
name|iSource
operator|=
operator|new
name|File
argument_list|(
name|source
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setBaseDir
parameter_list|(
name|String
name|baseDir
parameter_list|)
block|{
name|iBaseDir
operator|=
operator|new
name|File
argument_list|(
name|baseDir
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Bundle
name|createBundle
parameter_list|()
block|{
name|Bundle
name|bundle
init|=
operator|new
name|Bundle
argument_list|()
decl_stmt|;
name|iBundles
operator|.
name|add
argument_list|(
name|bundle
argument_list|)
expr_stmt|;
return|return
name|bundle
return|;
block|}
specifier|public
name|void
name|addBundle
parameter_list|(
name|Bundle
name|bundle
parameter_list|)
block|{
name|iBundles
operator|.
name|add
argument_list|(
name|bundle
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setBundles
parameter_list|(
name|String
name|bundles
parameter_list|)
block|{
for|for
control|(
name|String
name|name
range|:
name|bundles
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|addBundle
argument_list|(
operator|new
name|Bundle
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|Locale
name|createLocale
parameter_list|()
block|{
name|Locale
name|locale
init|=
operator|new
name|Locale
argument_list|()
decl_stmt|;
name|iLocales
operator|.
name|add
argument_list|(
name|locale
argument_list|)
expr_stmt|;
return|return
name|locale
return|;
block|}
specifier|public
name|void
name|addLocale
parameter_list|(
name|Locale
name|locale
parameter_list|)
block|{
name|iLocales
operator|.
name|add
argument_list|(
name|locale
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setLocales
parameter_list|(
name|String
name|locales
parameter_list|)
block|{
for|for
control|(
name|String
name|value
range|:
name|locales
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|addLocale
argument_list|(
operator|new
name|Locale
argument_list|(
name|value
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|setTranslations
parameter_list|(
name|String
name|translations
parameter_list|)
block|{
name|iTranslations
operator|=
name|translations
expr_stmt|;
block|}
specifier|private
specifier|static
name|String
name|array2string
parameter_list|(
name|String
index|[]
name|value
parameter_list|)
block|{
name|String
name|ret
init|=
literal|""
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|value
control|)
block|{
if|if
condition|(
operator|!
name|ret
operator|.
name|isEmpty
argument_list|()
condition|)
name|ret
operator|+=
literal|","
expr_stmt|;
name|ret
operator|+=
name|s
operator|.
name|replace
argument_list|(
literal|","
argument_list|,
literal|"\\\\,"
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
specifier|private
specifier|static
specifier|final
name|char
index|[]
name|hexChar
init|=
block|{
literal|'0'
block|,
literal|'1'
block|,
literal|'2'
block|,
literal|'3'
block|,
literal|'4'
block|,
literal|'5'
block|,
literal|'6'
block|,
literal|'7'
block|,
literal|'8'
block|,
literal|'9'
block|,
literal|'A'
block|,
literal|'B'
block|,
literal|'C'
block|,
literal|'D'
block|,
literal|'E'
block|,
literal|'F'
block|}
decl_stmt|;
specifier|private
specifier|static
name|String
name|unicodeEscape
parameter_list|(
name|String
name|s
parameter_list|,
name|String
name|locale
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
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
name|s
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|c
operator|>>
literal|7
operator|)
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"\\u"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|hexChar
index|[
operator|(
name|c
operator|>>
literal|12
operator|)
operator|&
literal|0xF
index|]
argument_list|)
expr_stmt|;
comment|// append the hex character for the left-most 4-bits
name|sb
operator|.
name|append
argument_list|(
name|hexChar
index|[
operator|(
name|c
operator|>>
literal|8
operator|)
operator|&
literal|0xF
index|]
argument_list|)
expr_stmt|;
comment|// hex for the second group of 4-bits from the left
name|sb
operator|.
name|append
argument_list|(
name|hexChar
index|[
operator|(
name|c
operator|>>
literal|4
operator|)
operator|&
literal|0xF
index|]
argument_list|)
expr_stmt|;
comment|// hex for the third group
name|sb
operator|.
name|append
argument_list|(
name|hexChar
index|[
name|c
operator|&
literal|0xF
index|]
argument_list|)
expr_stmt|;
comment|// hex for the last group, e.g., the right most 4-bits
block|}
if|else if
condition|(
name|c
operator|==
literal|'\n'
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"\\n"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|c
operator|==
literal|'\"'
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"\\\\\""
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|c
operator|==
literal|'\\'
condition|)
block|{
if|if
condition|(
name|locale
operator|!=
literal|null
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"\\\\\\\\"
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
literal|"\\\\"
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|c
operator|==
literal|' '
operator|&&
operator|(
name|i
operator|==
literal|0
operator|||
name|i
operator|+
literal|1
operator|==
name|s
operator|.
name|length
argument_list|()
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"\\\\ "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|void
name|info
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iProject
operator|!=
literal|null
condition|)
name|iProject
operator|.
name|log
argument_list|(
name|message
argument_list|)
expr_stmt|;
else|else
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"     [info] "
operator|+
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|warn
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iProject
operator|!=
literal|null
condition|)
name|iProject
operator|.
name|log
argument_list|(
name|message
argument_list|,
name|Project
operator|.
name|MSG_WARN
argument_list|)
expr_stmt|;
else|else
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  [warning] "
operator|+
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|debug
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iProject
operator|!=
literal|null
condition|)
name|iProject
operator|.
name|log
argument_list|(
name|message
argument_list|,
name|Project
operator|.
name|MSG_DEBUG
argument_list|)
expr_stmt|;
else|else
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"    [debug] "
operator|+
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|error
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|iProject
operator|!=
literal|null
condition|)
name|iProject
operator|.
name|log
argument_list|(
name|message
argument_list|,
name|Project
operator|.
name|MSG_ERR
argument_list|)
expr_stmt|;
else|else
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"    [error] "
operator|+
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|execute
parameter_list|()
throws|throws
name|BuildException
block|{
try|try
block|{
name|File
name|translations
init|=
operator|new
name|File
argument_list|(
name|iBaseDir
argument_list|,
name|iTranslations
argument_list|)
decl_stmt|;
name|info
argument_list|(
literal|"Exporting translations to: "
operator|+
name|translations
argument_list|)
expr_stmt|;
name|translations
operator|.
name|mkdirs
argument_list|()
expr_stmt|;
for|for
control|(
name|Bundle
name|bundle
range|:
name|iBundles
control|)
block|{
name|info
argument_list|(
literal|"Loading "
operator|+
name|bundle
argument_list|)
expr_stmt|;
name|Class
name|clazz
init|=
literal|null
decl_stmt|;
name|File
name|folder
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|bundle
operator|.
name|hasPackage
argument_list|()
condition|)
block|{
try|try
block|{
name|clazz
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|bundle
operator|.
name|getPackage
argument_list|()
operator|+
literal|"."
operator|+
name|bundle
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|folder
operator|=
operator|new
name|File
argument_list|(
name|iSource
argument_list|,
name|bundle
operator|.
name|getPackage
argument_list|()
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
name|File
operator|.
name|separatorChar
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
block|}
block|}
try|try
block|{
name|clazz
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|Localization
operator|.
name|ROOT
operator|+
name|bundle
argument_list|)
expr_stmt|;
name|folder
operator|=
operator|new
name|File
argument_list|(
name|iSource
argument_list|,
name|Localization
operator|.
name|ROOT
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
name|File
operator|.
name|separatorChar
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
block|}
try|try
block|{
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
block|{
name|clazz
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|Localization
operator|.
name|GWTROOT
operator|+
name|bundle
argument_list|)
expr_stmt|;
name|folder
operator|=
operator|new
name|File
argument_list|(
name|iSource
argument_list|,
name|Localization
operator|.
name|GWTROOT
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
name|File
operator|.
name|separatorChar
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|clazz
operator|==
literal|null
condition|)
block|{
name|error
argument_list|(
literal|"Bundle "
operator|+
name|bundle
operator|+
literal|" not found."
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|PrintStream
name|out
init|=
operator|new
name|PrintStream
argument_list|(
operator|new
name|File
argument_list|(
name|translations
argument_list|,
name|bundle
operator|.
name|getName
argument_list|()
operator|+
literal|".properties"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Method
name|method
range|:
name|clazz
operator|.
name|getMethods
argument_list|()
control|)
block|{
name|String
name|value
init|=
literal|null
decl_stmt|;
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
name|value
operator|=
name|dm
operator|.
name|value
argument_list|()
expr_stmt|;
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
name|value
operator|=
operator|(
name|db
operator|.
name|value
argument_list|()
condition|?
literal|"true"
else|:
literal|"false"
operator|)
expr_stmt|;
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
name|value
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|dd
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
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
name|value
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|df
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
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
name|value
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|di
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
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
name|value
operator|=
name|ds
operator|.
name|value
argument_list|()
expr_stmt|;
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
name|value
operator|=
name|array2string
argument_list|(
name|dsa
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
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
name|value
operator|=
name|array2string
argument_list|(
name|dsm
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
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
condition|)
continue|continue;
name|boolean
name|doNotTranslate
init|=
operator|(
name|method
operator|.
name|getAnnotation
argument_list|(
name|Messages
operator|.
name|DoNotTranslate
operator|.
name|class
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|method
operator|.
name|getAnnotation
argument_list|(
name|Constants
operator|.
name|DoNotTranslate
operator|.
name|class
argument_list|)
operator|!=
literal|null
operator|)
decl_stmt|;
if|if
condition|(
name|doNotTranslate
condition|)
continue|continue;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|warn
argument_list|(
literal|"Property "
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|" has no default value!"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# "
operator|+
name|method
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"="
operator|+
name|unicodeEscape
argument_list|(
name|value
operator|!=
literal|null
condition|?
name|value
else|:
literal|""
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
for|for
control|(
name|Locale
name|locale
range|:
name|iLocales
control|)
block|{
name|debug
argument_list|(
literal|"Locale "
operator|+
name|locale
argument_list|)
expr_stmt|;
name|Properties
name|properties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|InputStream
name|is
init|=
name|clazz
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
name|clazz
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
literal|"_"
operator|+
name|locale
operator|+
literal|".properties"
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|!=
literal|null
condition|)
block|{
name|properties
operator|.
name|load
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|folder
argument_list|,
name|bundle
operator|.
name|getName
argument_list|()
operator|+
literal|"_"
operator|+
name|locale
operator|.
name|getValue
argument_list|()
operator|+
literal|".properties"
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
name|FileReader
name|r
init|=
operator|new
name|FileReader
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|properties
operator|.
name|load
argument_list|(
name|r
argument_list|)
expr_stmt|;
name|r
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
name|out
operator|=
operator|new
name|PrintStream
argument_list|(
operator|new
name|File
argument_list|(
name|translations
argument_list|,
name|bundle
operator|.
name|getName
argument_list|()
operator|+
literal|"_"
operator|+
name|locale
operator|.
name|getValue
argument_list|()
operator|+
literal|".properties"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|Method
name|method
range|:
name|clazz
operator|.
name|getMethods
argument_list|()
control|)
block|{
name|String
name|text
init|=
name|properties
operator|.
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
name|text
operator|!=
literal|null
condition|)
name|out
operator|.
name|println
argument_list|(
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"="
operator|+
name|unicodeEscape
argument_list|(
name|text
argument_list|,
name|locale
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|BuildException
argument_list|(
literal|"Export failed: "
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
block|}
specifier|public
specifier|static
class|class
name|Bundle
block|{
name|String
name|iPackage
decl_stmt|,
name|iName
decl_stmt|;
specifier|public
name|Bundle
parameter_list|(
name|String
name|pck
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|iPackage
operator|=
name|pck
expr_stmt|;
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|Bundle
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Bundle
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setPackage
parameter_list|(
name|String
name|pck
parameter_list|)
block|{
name|iPackage
operator|=
name|pck
expr_stmt|;
block|}
specifier|public
name|boolean
name|hasPackage
parameter_list|()
block|{
return|return
name|iPackage
operator|!=
literal|null
operator|&&
operator|!
name|iPackage
operator|.
name|isEmpty
argument_list|()
return|;
block|}
specifier|public
name|String
name|getPackage
parameter_list|()
block|{
return|return
name|iPackage
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|iName
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|hasPackage
argument_list|()
condition|?
name|getPackage
argument_list|()
operator|+
literal|"."
else|:
literal|""
operator|)
operator|+
name|getName
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Locale
block|{
name|String
name|iValue
decl_stmt|;
specifier|public
name|Locale
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|Locale
parameter_list|()
block|{
name|this
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getValue
argument_list|()
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
try|try
block|{
name|ExportTranslations
name|task
init|=
operator|new
name|ExportTranslations
argument_list|()
decl_stmt|;
name|task
operator|.
name|setBaseDir
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"source"
argument_list|,
literal|"/Users/muller/git/unitime"
argument_list|)
argument_list|)
expr_stmt|;
name|task
operator|.
name|setSource
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"source"
argument_list|,
literal|"/Users/muller/git/unitime"
argument_list|)
operator|+
name|File
operator|.
name|separator
operator|+
literal|"JavaSource"
argument_list|)
expr_stmt|;
name|task
operator|.
name|setBundles
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"bundle"
argument_list|,
literal|"CourseMessages,ConstantsMessages,ExaminationMessages,SecurityMessages,GwtConstants,GwtAriaMessages,GwtMessages,StudentSectioningConstants,StudentSectioningMessages"
argument_list|)
argument_list|)
expr_stmt|;
name|task
operator|.
name|setLocales
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"locale"
argument_list|,
literal|"cs"
argument_list|)
argument_list|)
expr_stmt|;
name|task
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

