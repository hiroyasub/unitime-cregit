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
name|commons
package|;
end_package

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
name|util
operator|.
name|Locale
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

begin_comment
comment|/**  * This class provides logging of debug informations.  *  * Debug files are writen in directory provided via key DEBUG_DIR in configuration.  * Maximum number of debug files is limited via key DEBUG_MAXFILES in configuration.  * If the number of limited debug files exceeds, oldest debug file is deleted.  *  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Debug
block|{
comment|// Number format for logging (allocated memory)
specifier|private
specifier|static
name|NumberFormat
name|sNumberFormat
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"###,#00.00"
argument_list|,
operator|new
name|DecimalFormatSymbols
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
comment|/** Prints an error to log. 	 * @param e an error 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|error
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|getSource
argument_list|(
name|e
argument_list|)
argument_list|)
operator|.
name|error
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|/** Prints an fatal error message to log. 	 * @param message a fatal error message 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|fatal
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|fatal
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/** Prints an error message to log. 	 * @param message an error message 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|error
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|error
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/** Prints an error message to log. 	 * @param message an error message 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|error
parameter_list|(
name|String
name|message
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|error
argument_list|(
name|message
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
comment|/** Prints a warning message to log. 	 * @param message a warning message 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|warning
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|warn
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/** Prints a message to log. 	 * @param message a message 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|info
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|info
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/** Prints an debug message to log. 	 * @param message debug message 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|debug
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|debug
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/** Prints a message to log. 	 * @param message a message 	 */
specifier|public
specifier|static
specifier|synchronized
name|void
name|log
parameter_list|(
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|message
operator|.
name|startsWith
argument_list|(
literal|"ERROR:"
argument_list|)
condition|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|error
argument_list|(
name|message
operator|.
name|substring
argument_list|(
literal|"ERROR:"
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|else if
condition|(
name|message
operator|.
name|startsWith
argument_list|(
literal|"WARNING:"
argument_list|)
condition|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|warn
argument_list|(
name|message
operator|.
name|substring
argument_list|(
literal|"WARNING:"
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|else if
condition|(
name|message
operator|.
name|startsWith
argument_list|(
literal|"INFO:"
argument_list|)
condition|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|info
argument_list|(
name|message
operator|.
name|substring
argument_list|(
literal|"INFO:"
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|()
argument_list|)
operator|.
name|debug
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|synchronized
name|void
name|log
parameter_list|(
name|int
name|depth
parameter_list|,
name|String
name|message
parameter_list|)
block|{
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|message
operator|.
name|startsWith
argument_list|(
literal|"ERROR:"
argument_list|)
condition|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|(
literal|4
operator|+
name|depth
argument_list|)
argument_list|)
operator|.
name|error
argument_list|(
name|message
operator|.
name|substring
argument_list|(
literal|"ERROR:"
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|else if
condition|(
name|message
operator|.
name|startsWith
argument_list|(
literal|"WARNING:"
argument_list|)
condition|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|(
literal|4
operator|+
name|depth
argument_list|)
argument_list|)
operator|.
name|warn
argument_list|(
name|message
operator|.
name|substring
argument_list|(
literal|"WARNING:"
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
if|else if
condition|(
name|message
operator|.
name|startsWith
argument_list|(
literal|"INFO:"
argument_list|)
condition|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|(
literal|4
operator|+
name|depth
argument_list|)
argument_list|)
operator|.
name|info
argument_list|(
name|message
operator|.
name|substring
argument_list|(
literal|"INFO:"
operator|.
name|length
argument_list|()
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ToolBox
operator|.
name|getCaller
argument_list|(
literal|4
operator|+
name|depth
argument_list|)
argument_list|)
operator|.
name|debug
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
comment|/** Return source from a class. 	 * @param source a class 	 * @return class name (without package) 	 */
specifier|public
specifier|static
specifier|synchronized
name|String
name|getSource
parameter_list|(
name|Class
name|source
parameter_list|)
block|{
name|String
name|name
init|=
name|source
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|!=
literal|null
operator|&&
name|name
operator|.
name|indexOf
argument_list|(
literal|'.'
argument_list|)
operator|>
literal|0
condition|)
block|{
return|return
name|name
operator|.
name|substring
argument_list|(
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
operator|+
literal|1
argument_list|)
return|;
block|}
return|return
name|name
return|;
block|}
comment|/** Return source from an object. 	 * @param source am object 	 * @return class name (without package) 	 */
specifier|public
specifier|static
specifier|synchronized
name|String
name|getSource
parameter_list|(
name|Object
name|source
parameter_list|)
block|{
if|if
condition|(
name|source
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
else|else
block|{
return|return
name|getSource
argument_list|(
name|source
operator|.
name|getClass
argument_list|()
argument_list|)
return|;
block|}
block|}
comment|/** Returns amount of allocated memory.      * @return amount of allocated memory to be written in the log      */
specifier|public
specifier|static
specifier|synchronized
name|String
name|getMem
parameter_list|()
block|{
return|return
name|sNumberFormat
operator|.
name|format
argument_list|(
operator|(
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|totalMemory
argument_list|()
operator|-
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|freeMemory
argument_list|()
operator|)
operator|/
literal|1048576.0
argument_list|)
operator|+
literal|"M "
return|;
block|}
block|}
end_class

end_unit

