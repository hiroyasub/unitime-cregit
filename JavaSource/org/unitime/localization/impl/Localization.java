begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 (University Timetabling Application)  * Copyright (C) 2011, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|Messages
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
parameter_list|<
name|T
parameter_list|>
name|T
name|create
parameter_list|(
name|Class
argument_list|<
name|?
extends|extends
name|Messages
argument_list|>
name|bundle
parameter_list|)
block|{
return|return
name|create
argument_list|(
name|bundle
argument_list|,
name|ApplicationProperties
operator|.
name|getProperty
argument_list|(
literal|"unitime.locale"
argument_list|,
literal|"en"
argument_list|)
argument_list|)
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
name|?
extends|extends
name|Messages
argument_list|>
name|bundle
parameter_list|,
name|String
name|locale
parameter_list|)
block|{
return|return
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
argument_list|,
name|locale
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
class|class
name|Bundle
implements|implements
name|InvocationHandler
block|{
name|List
argument_list|<
name|Properties
argument_list|>
name|iProperties
init|=
operator|new
name|ArrayList
argument_list|<
name|Properties
argument_list|>
argument_list|()
decl_stmt|;
name|Class
argument_list|<
name|?
extends|extends
name|Messages
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
extends|extends
name|Messages
argument_list|>
name|messages
parameter_list|,
name|String
name|locale
parameter_list|)
block|{
name|iMessages
operator|=
name|messages
expr_stmt|;
for|for
control|(
name|String
name|loc
range|:
name|locale
operator|.
name|split
argument_list|(
literal|","
argument_list|)
control|)
block|{
name|String
name|resource
init|=
name|messages
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
name|loc
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
literal|"Failed to load bundle "
operator|+
name|messages
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
name|messages
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
name|loc
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
block|}
name|String
name|resource
init|=
name|messages
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
literal|"Failed to load default bundle "
operator|+
name|messages
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
name|messages
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
literal|0
condition|)
return|return
name|getStrutsActions
argument_list|(
name|proxy
argument_list|)
return|;
for|for
control|(
name|Properties
name|p
range|:
name|iProperties
control|)
block|{
name|String
name|val
init|=
name|p
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
name|val
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|args
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|args
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|val
operator|=
name|val
operator|.
name|replaceAll
argument_list|(
literal|"\\{"
operator|+
operator|(
literal|1
operator|+
name|i
operator|)
operator|+
literal|"\\}"
argument_list|,
operator|(
name|args
index|[
name|i
index|]
operator|==
literal|null
condition|?
literal|""
else|:
name|args
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
operator|)
argument_list|)
expr_stmt|;
return|return
name|val
return|;
block|}
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
block|{
name|String
name|val
init|=
name|dm
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|args
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|args
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|val
operator|=
name|val
operator|.
name|replaceAll
argument_list|(
literal|"\\{"
operator|+
operator|(
literal|1
operator|+
name|i
operator|)
operator|+
literal|"\\}"
argument_list|,
operator|(
name|args
index|[
name|i
index|]
operator|==
literal|null
condition|?
literal|""
else|:
name|args
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
operator|)
argument_list|)
expr_stmt|;
return|return
name|val
return|;
block|}
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
name|Messages
operator|.
name|StrutsAction
name|action
init|=
name|m
operator|.
name|getAnnotation
argument_list|(
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
block|}
return|return
name|ret
return|;
block|}
name|void
name|load
parameter_list|(
name|InputStream
name|in
parameter_list|)
throws|throws
name|IOException
block|{
name|Properties
name|p
init|=
operator|new
name|Properties
argument_list|()
decl_stmt|;
name|p
operator|.
name|load
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|iProperties
operator|.
name|add
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
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
parameter_list|()
function_decl|;
block|}
block|}
end_class

end_unit

