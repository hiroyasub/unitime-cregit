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
name|FileNotFoundException
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
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|SAXReader
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
name|GwtMessages
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

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|EntityResolver
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|InputSource
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
operator|.
name|DefaultMessage
import|;
end_import

begin_class
specifier|public
class|class
name|PageNameGenerator
block|{
specifier|private
name|TreeSet
argument_list|<
name|String
argument_list|>
name|iPageNames
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|File
name|iSource
decl_stmt|;
specifier|public
name|PageNameGenerator
parameter_list|()
block|{
block|}
specifier|public
name|void
name|setSource
parameter_list|(
name|File
name|source
parameter_list|)
block|{
name|iSource
operator|=
name|source
expr_stmt|;
block|}
specifier|public
name|void
name|checkPageNamecClass
parameter_list|()
block|{
for|for
control|(
name|Method
name|method
range|:
name|PageNames
operator|.
name|class
operator|.
name|getMethods
argument_list|()
control|)
block|{
name|DefaultMessage
name|dm
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
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
if|if
condition|(
name|iPageNames
operator|.
name|add
argument_list|(
name|dm
operator|.
name|value
argument_list|()
argument_list|)
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"[names] "
operator|+
name|dm
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|checkMenuXML
parameter_list|()
throws|throws
name|DocumentException
throws|,
name|IOException
block|{
name|SAXReader
name|sax
init|=
operator|new
name|SAXReader
argument_list|()
decl_stmt|;
name|Document
name|document
init|=
literal|null
decl_stmt|;
name|InputStream
name|is
init|=
name|PageNameGenerator
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"menu.xml"
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|!=
literal|null
condition|)
block|{
name|sax
operator|.
name|setEntityResolver
argument_list|(
operator|new
name|EntityResolver
argument_list|()
block|{
specifier|public
name|InputSource
name|resolveEntity
parameter_list|(
name|String
name|publicId
parameter_list|,
name|String
name|systemId
parameter_list|)
block|{
if|if
condition|(
name|publicId
operator|.
name|equals
argument_list|(
literal|"-//UniTime//UniTime Menu DTD/EN"
argument_list|)
condition|)
block|{
return|return
operator|new
name|InputSource
argument_list|(
name|PageNameGenerator
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"menu.dtd"
argument_list|)
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|document
operator|=
name|sax
operator|.
name|read
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|iSource
operator|!=
literal|null
condition|)
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|iSource
argument_list|,
literal|"menu.xml"
argument_list|)
decl_stmt|;
name|sax
operator|.
name|setEntityResolver
argument_list|(
operator|new
name|EntityResolver
argument_list|()
block|{
specifier|public
name|InputSource
name|resolveEntity
parameter_list|(
name|String
name|publicId
parameter_list|,
name|String
name|systemId
parameter_list|)
block|{
if|if
condition|(
name|publicId
operator|.
name|equals
argument_list|(
literal|"-//UniTime//UniTime Menu DTD/EN"
argument_list|)
condition|)
block|{
try|try
block|{
return|return
operator|new
name|InputSource
argument_list|(
operator|new
name|FileInputStream
argument_list|(
operator|new
name|File
argument_list|(
name|iSource
argument_list|,
literal|"menu.dtd"
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" [menu] Using "
operator|+
operator|new
name|File
argument_list|(
name|iSource
argument_list|,
literal|"menu.xml"
argument_list|)
argument_list|)
expr_stmt|;
name|document
operator|=
name|sax
operator|.
name|read
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|document
operator|!=
literal|null
condition|)
name|parseMenu
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|parseMenu
parameter_list|(
name|Element
name|element
parameter_list|)
block|{
for|for
control|(
name|Iterator
name|i
init|=
name|element
operator|.
name|elementIterator
argument_list|(
literal|"item"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|item
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|item
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|!=
literal|null
operator|&&
operator|!
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|iPageNames
operator|.
name|add
argument_list|(
name|name
argument_list|)
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" [menu] "
operator|+
name|name
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|element
operator|.
name|elementIterator
argument_list|(
literal|"menu"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|menu
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|menu
operator|.
name|attributeValue
argument_list|(
literal|"name"
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|!=
literal|null
operator|&&
operator|!
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|iPageNames
operator|.
name|add
argument_list|(
name|name
argument_list|)
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" [menu] "
operator|+
name|name
argument_list|)
expr_stmt|;
block|}
name|parseMenu
argument_list|(
name|menu
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|checkLocale
parameter_list|(
name|String
name|locale
parameter_list|)
throws|throws
name|IOException
block|{
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
name|PageNameGenerator
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResourceAsStream
argument_list|(
literal|"org/unitime/localization/messages/PageNames_"
operator|+
name|locale
operator|+
literal|".properties"
argument_list|)
decl_stmt|;
if|if
condition|(
name|is
operator|==
literal|null
operator|&&
name|iSource
operator|!=
literal|null
condition|)
block|{
name|is
operator|=
operator|new
name|FileInputStream
argument_list|(
operator|new
name|File
argument_list|(
name|iSource
argument_list|,
literal|"org"
operator|+
name|File
operator|.
name|separator
operator|+
literal|"unitime"
operator|+
name|File
operator|.
name|separator
operator|+
literal|"localization"
operator|+
name|File
operator|.
name|separator
operator|+
literal|"messages"
operator|+
name|File
operator|.
name|separator
operator|+
literal|"PageNames_"
operator|+
name|locale
operator|+
literal|".properties"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|is
operator|==
literal|null
condition|)
return|return;
try|try
block|{
name|properties
operator|.
name|load
argument_list|(
name|is
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|is
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
for|for
control|(
name|Object
name|o
range|:
name|properties
operator|.
name|keySet
argument_list|()
control|)
block|{
name|String
name|name
init|=
name|property2name
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
decl_stmt|;
if|if
condition|(
name|iPageNames
operator|.
name|add
argument_list|(
name|name
argument_list|)
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"   ["
operator|+
name|locale
operator|+
literal|"] "
operator|+
name|name
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|checkGwtMessages
parameter_list|()
block|{
for|for
control|(
name|Method
name|method
range|:
name|GwtMessages
operator|.
name|class
operator|.
name|getMethods
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|method
operator|.
name|getName
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"page"
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
operator|!
name|doNotTranslate
condition|)
continue|continue;
name|DefaultMessage
name|dm
init|=
name|method
operator|.
name|getAnnotation
argument_list|(
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
operator|&&
operator|!
name|dm
operator|.
name|value
argument_list|()
operator|.
name|contains
argument_list|(
literal|"{0}"
argument_list|)
condition|)
if|if
condition|(
name|iPageNames
operator|.
name|add
argument_list|(
name|dm
operator|.
name|value
argument_list|()
argument_list|)
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"  [gwt] "
operator|+
name|dm
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|checkOnlineHelp
parameter_list|()
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
literal|"https://sites.google.com/feeds/content/unitime.org/help45?kind=webpage"
argument_list|)
decl_stmt|;
while|while
condition|(
name|url
operator|!=
literal|null
condition|)
block|{
name|Element
name|feed
init|=
name|readHelpContentFeed
argument_list|(
name|url
argument_list|)
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
name|url
operator|=
literal|null
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|feed
operator|.
name|elementIterator
argument_list|(
literal|"entry"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|entry
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|name
init|=
name|entry
operator|.
name|element
argument_list|(
literal|"pageName"
argument_list|)
operator|.
name|getTextTrim
argument_list|()
decl_stmt|;
name|String
name|title
init|=
name|entry
operator|.
name|element
argument_list|(
literal|"title"
argument_list|)
operator|.
name|getTextTrim
argument_list|()
decl_stmt|;
if|if
condition|(
name|name2property
argument_list|(
name|title
argument_list|)
operator|.
name|equals
argument_list|(
name|name
argument_list|)
operator|&&
name|iPageNames
operator|.
name|add
argument_list|(
name|title
argument_list|)
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" [help] "
operator|+
name|title
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|feed
operator|.
name|elementIterator
argument_list|(
literal|"link"
argument_list|)
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|Element
name|link
init|=
operator|(
name|Element
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
literal|"next"
operator|.
name|equals
argument_list|(
name|link
operator|.
name|attributeValue
argument_list|(
literal|"rel"
argument_list|)
argument_list|)
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|" [next] "
operator|+
name|link
operator|.
name|attributeValue
argument_list|(
literal|"href"
argument_list|)
argument_list|)
expr_stmt|;
name|url
operator|=
operator|new
name|URL
argument_list|(
name|link
operator|.
name|attributeValue
argument_list|(
literal|"href"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|protected
name|Document
name|readHelpContentFeed
parameter_list|(
name|URL
name|url
parameter_list|)
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
name|InputStream
name|in
init|=
name|url
operator|.
name|openStream
argument_list|()
decl_stmt|;
try|try
block|{
return|return
operator|new
name|SAXReader
argument_list|()
operator|.
name|read
argument_list|(
name|in
argument_list|)
return|;
block|}
finally|finally
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|String
name|name2property
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|name
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'_'
argument_list|)
operator|.
name|replace
argument_list|(
literal|"("
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|")"
argument_list|,
literal|""
argument_list|)
operator|.
name|replace
argument_list|(
literal|':'
argument_list|,
literal|'_'
argument_list|)
return|;
block|}
specifier|public
name|String
name|property2name
parameter_list|(
name|String
name|property
parameter_list|)
block|{
return|return
name|property
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|'_'
argument_list|,
literal|' '
argument_list|)
operator|.
name|replace
argument_list|(
literal|'_'
argument_list|,
literal|':'
argument_list|)
return|;
block|}
specifier|public
name|Properties
name|getProperties
parameter_list|()
block|{
name|Properties
name|properties
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|name
range|:
name|iPageNames
control|)
block|{
name|properties
operator|.
name|put
argument_list|(
name|name2property
argument_list|(
name|name
argument_list|)
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
return|return
name|properties
return|;
block|}
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|execute
parameter_list|()
throws|throws
name|Exception
block|{
name|checkPageNamecClass
argument_list|()
expr_stmt|;
name|checkMenuXML
argument_list|()
expr_stmt|;
name|checkGwtMessages
argument_list|()
expr_stmt|;
name|checkOnlineHelp
argument_list|()
expr_stmt|;
name|checkLocale
argument_list|(
literal|"cs"
argument_list|)
expr_stmt|;
return|return
name|iPageNames
return|;
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
name|PageNameGenerator
name|task
init|=
operator|new
name|PageNameGenerator
argument_list|()
decl_stmt|;
name|task
operator|.
name|execute
argument_list|()
expr_stmt|;
name|task
operator|.
name|getProperties
argument_list|()
operator|.
name|store
argument_list|(
name|System
operator|.
name|out
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
block|}
block|}
end_class

end_unit

