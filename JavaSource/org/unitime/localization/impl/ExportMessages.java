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
name|Method
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
operator|.
name|DefaultMessage
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|ExportMessages
block|{
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
name|Class
name|clazz
init|=
name|Class
operator|.
name|forName
argument_list|(
name|Localization
operator|.
name|ROOT
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"bundle"
argument_list|,
literal|"CourseMessages"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|locale
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"locale"
argument_list|,
literal|"cs"
argument_list|)
decl_stmt|;
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
name|properties
operator|.
name|load
argument_list|(
name|is
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\"Key\",\"Default\",\"Value\""
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
name|dm
operator|!=
literal|null
condition|)
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
name|dm
operator|.
name|value
argument_list|()
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
argument_list|)
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
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
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
argument_list|)
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
name|dd
operator|.
name|value
argument_list|()
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
name|df
operator|.
name|value
argument_list|()
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
name|di
operator|.
name|value
argument_list|()
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
name|ds
operator|.
name|value
argument_list|()
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
argument_list|)
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
name|array2string
argument_list|(
name|dsa
operator|.
name|value
argument_list|()
argument_list|)
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"\""
operator|+
name|method
operator|.
name|getName
argument_list|()
operator|+
literal|"\",\""
operator|+
name|array2string
argument_list|(
name|dsm
operator|.
name|value
argument_list|()
argument_list|)
operator|+
literal|"\",\""
operator|+
operator|(
name|text
operator|==
literal|null
condition|?
literal|""
else|:
name|text
operator|)
operator|+
literal|"\""
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

