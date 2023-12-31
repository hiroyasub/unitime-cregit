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
name|FileInputStream
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
name|InputStreamReader
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
name|Comparator
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
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|localization
operator|.
name|impl
operator|.
name|ExportTranslations
operator|.
name|Bundle
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|impl
operator|.
name|ExportTranslations
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|PageNames
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
name|ImportTranslations
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
name|Project
name|iProject
decl_stmt|;
specifier|private
name|File
name|iBaseDir
decl_stmt|;
specifier|private
name|File
name|iSource
decl_stmt|;
specifier|private
name|String
name|iTranslations
init|=
literal|"Documentation/Translations"
decl_stmt|;
specifier|private
name|boolean
name|iGeneratePageNames
init|=
literal|false
decl_stmt|;
specifier|private
name|boolean
name|iFixMe
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iEncoding
init|=
literal|"UTF-8"
decl_stmt|;
specifier|public
name|ImportTranslations
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
name|setEncoding
parameter_list|(
name|String
name|encoding
parameter_list|)
block|{
name|iEncoding
operator|=
name|encoding
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
specifier|public
name|void
name|setGeneratePageNames
parameter_list|(
name|boolean
name|generatePageNames
parameter_list|)
block|{
name|iGeneratePageNames
operator|=
name|generatePageNames
expr_stmt|;
block|}
specifier|public
name|void
name|setIncludeFixMe
parameter_list|(
name|boolean
name|fixMe
parameter_list|)
block|{
name|iFixMe
operator|=
name|fixMe
expr_stmt|;
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
literal|"\\,"
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
name|boolean
name|includeColon
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
literal|':'
operator|&&
name|includeColon
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"\\:"
argument_list|)
expr_stmt|;
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
literal|"Importing translations from: "
operator|+
name|translations
argument_list|)
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
name|boolean
name|constants
init|=
name|Constants
operator|.
name|class
operator|.
name|isAssignableFrom
argument_list|(
name|clazz
argument_list|)
decl_stmt|;
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
name|boolean
name|empty
init|=
literal|true
decl_stmt|;
name|File
name|input
init|=
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
decl_stmt|;
if|if
condition|(
operator|!
name|input
operator|.
name|exists
argument_list|()
condition|)
continue|continue;
name|Properties
name|translation
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|translation
operator|.
name|load
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|input
argument_list|)
argument_list|,
name|iEncoding
argument_list|)
argument_list|)
expr_stmt|;
name|File
name|output
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
name|Properties
name|old
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
if|if
condition|(
name|output
operator|.
name|exists
argument_list|()
condition|)
name|old
operator|.
name|load
argument_list|(
operator|new
name|FileReader
argument_list|(
name|output
argument_list|)
argument_list|)
expr_stmt|;
name|PrintStream
name|out
init|=
operator|new
name|PrintStream
argument_list|(
name|output
argument_list|)
decl_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# Licensed to The Apereo Foundation under one or more contributor license"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# agreements. See the NOTICE file distributed with this work for"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# additional information regarding copyright ownership."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"#"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# The Apereo Foundation licenses this file to you under the Apache License,"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# Version 2.0 (the \"License\"); you may not use this file except in"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# compliance with the License. You may obtain a copy of the License at:"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"#"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# http://www.apache.org/licenses/LICENSE-2.0"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"#"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# Unless required by applicable law or agreed to in writing, software"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# distributed under the License is distributed on an \"AS IS\" BASIS,"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"#"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# See the License for the specific language governing permissions and"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# limitations under the License."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"#"
argument_list|)
expr_stmt|;
if|if
condition|(
name|PageNames
operator|.
name|class
operator|.
name|equals
argument_list|(
name|clazz
argument_list|)
operator|&&
name|iGeneratePageNames
condition|)
block|{
name|TreeSet
argument_list|<
name|String
argument_list|>
name|names
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|o
range|:
name|translation
operator|.
name|keySet
argument_list|()
control|)
name|names
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
name|Properties
name|defaults
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|File
name|defaultFile
init|=
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
decl_stmt|;
if|if
condition|(
name|defaultFile
operator|.
name|exists
argument_list|()
condition|)
block|{
name|defaults
operator|.
name|load
argument_list|(
operator|new
name|FileReader
argument_list|(
name|defaultFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|PageNameGenerator
name|gen
init|=
operator|new
name|PageNameGenerator
argument_list|()
decl_stmt|;
name|gen
operator|.
name|setSource
argument_list|(
name|iSource
argument_list|)
expr_stmt|;
name|gen
operator|.
name|execute
argument_list|()
expr_stmt|;
name|defaults
operator|=
name|gen
operator|.
name|getProperties
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Object
name|o
range|:
name|defaults
operator|.
name|keySet
argument_list|()
control|)
name|names
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|name
range|:
name|names
control|)
block|{
name|String
name|value
init|=
name|defaults
operator|.
name|getProperty
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|translation
operator|.
name|getProperty
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|iFixMe
operator|&&
name|text
operator|==
literal|null
condition|)
continue|continue;
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|out
operator|.
name|println
argument_list|(
literal|"# Default: "
operator|+
name|unicodeEscape
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|out
operator|.
name|println
argument_list|(
literal|"# FIXME: Translate \""
operator|+
name|unicodeEscape
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
operator|+
literal|"\""
argument_list|)
expr_stmt|;
else|else
name|out
operator|.
name|println
argument_list|(
literal|"# FIXME: Translate "
operator|+
name|name
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# "
operator|+
name|name
operator|+
literal|"="
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|println
argument_list|(
name|name
operator|+
literal|"="
operator|+
name|unicodeEscape
argument_list|(
name|text
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|empty
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|TreeSet
argument_list|<
name|Method
argument_list|>
name|methods
init|=
operator|new
name|TreeSet
argument_list|<
name|Method
argument_list|>
argument_list|(
operator|new
name|Comparator
argument_list|<
name|Method
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|Method
name|m1
parameter_list|,
name|Method
name|m2
parameter_list|)
block|{
return|return
name|m1
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|m2
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
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
name|methods
operator|.
name|add
argument_list|(
name|method
argument_list|)
expr_stmt|;
for|for
control|(
name|Method
name|method
range|:
name|methods
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
name|String
name|text
init|=
name|translation
operator|.
name|getProperty
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|,
name|old
operator|.
name|getProperty
argument_list|(
name|method
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
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
name|text
operator|==
literal|null
operator|&&
operator|(
name|constants
operator|||
name|doNotTranslate
operator|||
operator|!
name|iFixMe
operator|)
condition|)
continue|continue;
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|out
operator|.
name|println
argument_list|(
literal|"# Default: "
operator|+
name|unicodeEscape
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
name|out
operator|.
name|println
argument_list|(
literal|"# FIXME: Translate \""
operator|+
name|unicodeEscape
argument_list|(
name|value
argument_list|,
literal|false
argument_list|)
operator|+
literal|"\""
argument_list|)
expr_stmt|;
else|else
name|out
operator|.
name|println
argument_list|(
literal|"# FIXME: Translate "
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
literal|"# "
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"="
argument_list|)
expr_stmt|;
block|}
else|else
block|{
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
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|empty
operator|=
literal|false
expr_stmt|;
block|}
block|}
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
if|if
condition|(
name|empty
condition|)
name|output
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|BuildException
argument_list|(
literal|"Import failed: "
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
name|ImportTranslations
name|task
init|=
operator|new
name|ImportTranslations
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

