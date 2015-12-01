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
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
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
name|ParseException
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
name|Date
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
name|Map
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
name|Localization
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
name|GwtConstants
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
name|StudentSectioningConstants
import|;
end_import

begin_comment
comment|/**  * To prevent concurrency issues (see bug JDK-6609686, http://bugs.sun.com/view_bug.do?bug_id=4264153) and to  * promote localization, all date and number formating needs should be routed through this class.   *    * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|Formats
block|{
specifier|private
specifier|static
name|StudentSectioningConstants
name|SCT_CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|GwtConstants
name|GWT_CONSTANTS
init|=
name|Localization
operator|.
name|create
argument_list|(
name|GwtConstants
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|ThreadLocal
argument_list|<
name|FormatBundle
argument_list|>
name|sBundle
init|=
operator|new
name|ThreadLocal
argument_list|<
name|FormatBundle
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|protected
name|FormatBundle
name|initialValue
parameter_list|()
block|{
return|return
operator|new
name|FormatBundle
argument_list|()
return|;
block|}
block|}
decl_stmt|;
specifier|public
enum|enum
name|Pattern
implements|implements
name|Serializable
block|{
name|DATE_EXAM_PERIOD
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|examPeriodDateFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_EVENT
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|eventDateFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_EVENT_SHORT
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|eventDateFormatShort
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_EVENT_LONG
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|eventDateFormatLong
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_TIME_STAMP
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|timeStampFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_TIME_STAMP_SHORT
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|timeStampFormatShort
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_REQUEST
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|SCT_CONSTANTS
operator|.
name|requestDateFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_PATTERN
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|SCT_CONSTANTS
operator|.
name|patternDateFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_DAY_OF_WEEK
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
literal|"EEE"
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_MEETING
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|meetingDateFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_SHORT
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|dateFormatShort
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|TIME_SHORT
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|timeFormatShort
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|SESSION_DATE
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|sessionDateFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|DATE_ENTRY_FORMAT
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|dateEntryFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|,
name|FILTER_DATE
argument_list|(
operator|new
name|PatternHolder
argument_list|()
block|{
specifier|public
name|String
name|getPattern
parameter_list|()
block|{
return|return
name|GWT_CONSTANTS
operator|.
name|filterDateFormat
argument_list|()
return|;
block|}
block|}
argument_list|)
block|, 		;
specifier|private
name|PatternHolder
name|iHolder
decl_stmt|;
name|Pattern
parameter_list|(
name|PatternHolder
name|holder
parameter_list|)
block|{
name|iHolder
operator|=
name|holder
expr_stmt|;
block|}
specifier|public
name|String
name|toPattern
parameter_list|()
block|{
return|return
name|iHolder
operator|.
name|getPattern
argument_list|()
return|;
block|}
specifier|protected
name|PatternHolder
name|holder
parameter_list|()
block|{
return|return
name|iHolder
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|removeFormats
parameter_list|()
block|{
name|sBundle
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|FormatBundle
name|getFormats
parameter_list|()
block|{
return|return
name|sBundle
operator|.
name|get
argument_list|()
return|;
block|}
comment|/** 	 * Use this to create a new instance that can be accessed through multiple threads. 	 * For static reference use {@link Formats#getDateFormat(Pattern)}. 	 */
specifier|public
specifier|static
name|Format
argument_list|<
name|Date
argument_list|>
name|getDateFormat
parameter_list|(
specifier|final
name|String
name|pattern
parameter_list|)
block|{
return|return
operator|new
name|Format
argument_list|<
name|Date
argument_list|>
argument_list|()
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|format
parameter_list|(
name|Date
name|t
parameter_list|)
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getDateFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|format
argument_list|(
name|t
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|parse
parameter_list|(
name|String
name|source
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getDateFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|parse
argument_list|(
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toPattern
parameter_list|()
block|{
return|return
name|pattern
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isValid
parameter_list|(
name|String
name|source
parameter_list|)
block|{
try|try
block|{
return|return
name|parse
argument_list|(
name|source
argument_list|)
operator|!=
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
return|;
block|}
comment|/** 	 * Use this to create a new instance that can be accessed through multiple threads. 	 * For static reference use {@link Formats#getDateFormat(Pattern)}. 	 */
specifier|public
specifier|static
name|Format
argument_list|<
name|Number
argument_list|>
name|getNumberFormat
parameter_list|(
specifier|final
name|String
name|pattern
parameter_list|)
block|{
return|return
operator|new
name|Format
argument_list|<
name|Number
argument_list|>
argument_list|()
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|format
parameter_list|(
name|Number
name|t
parameter_list|)
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getNumberFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|format
argument_list|(
name|t
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Number
name|parse
parameter_list|(
name|String
name|source
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getNumberFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|parse
argument_list|(
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toPattern
parameter_list|()
block|{
return|return
name|pattern
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isValid
parameter_list|(
name|String
name|source
parameter_list|)
block|{
try|try
block|{
return|return
name|parse
argument_list|(
name|source
argument_list|)
operator|!=
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
return|;
block|}
comment|/** 	 * Use this to create a new instance that can be accessed through multiple threads. 	 * For static reference use {@link Formats#getDateFormat(Pattern)}. 	 */
specifier|public
specifier|static
name|Format
argument_list|<
name|Date
argument_list|>
name|getDateFormat
parameter_list|(
specifier|final
name|Pattern
name|pattern
parameter_list|)
block|{
return|return
operator|new
name|Format
argument_list|<
name|Date
argument_list|>
argument_list|()
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|format
parameter_list|(
name|Date
name|t
parameter_list|)
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getDateFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|format
argument_list|(
name|t
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Date
name|parse
parameter_list|(
name|String
name|source
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getDateFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|parse
argument_list|(
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toPattern
parameter_list|()
block|{
return|return
name|pattern
operator|.
name|toPattern
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isValid
parameter_list|(
name|String
name|source
parameter_list|)
block|{
try|try
block|{
return|return
name|parse
argument_list|(
name|source
argument_list|)
operator|!=
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
return|;
block|}
comment|/** 	 * Use this to create a new instance that can be accessed through multiple threads. 	 * For static reference use {@link Formats#getDateFormat(Pattern)}. 	 */
specifier|public
specifier|static
name|Format
argument_list|<
name|Number
argument_list|>
name|getConcurrentNumberFormat
parameter_list|(
specifier|final
name|Pattern
name|pattern
parameter_list|)
block|{
return|return
operator|new
name|Format
argument_list|<
name|Number
argument_list|>
argument_list|()
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|format
parameter_list|(
name|Number
name|t
parameter_list|)
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getNumberFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|format
argument_list|(
name|t
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Number
name|parse
parameter_list|(
name|String
name|source
parameter_list|)
throws|throws
name|ParseException
block|{
return|return
name|getFormats
argument_list|()
operator|.
name|getNumberFormat
argument_list|(
name|pattern
argument_list|)
operator|.
name|parse
argument_list|(
name|source
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toPattern
parameter_list|()
block|{
return|return
name|pattern
operator|.
name|toPattern
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isValid
parameter_list|(
name|String
name|source
parameter_list|)
block|{
try|try
block|{
return|return
name|parse
argument_list|(
name|source
argument_list|)
operator|!=
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
return|;
block|}
specifier|public
specifier|static
class|class
name|FormatBundle
block|{
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|DateFormat
argument_list|>
name|iDateFormats
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|DateFormat
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|NumberFormat
argument_list|>
name|iNumberFormats
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|NumberFormat
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|FormatBundle
parameter_list|()
block|{
block|}
specifier|public
name|DateFormat
name|getDateFormat
parameter_list|(
name|String
name|pattern
parameter_list|)
block|{
name|DateFormat
name|df
init|=
name|iDateFormats
operator|.
name|get
argument_list|(
name|pattern
argument_list|)
decl_stmt|;
if|if
condition|(
name|df
operator|==
literal|null
condition|)
block|{
name|df
operator|=
operator|new
name|SimpleDateFormat
argument_list|(
name|pattern
argument_list|,
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
expr_stmt|;
name|iDateFormats
operator|.
name|put
argument_list|(
name|pattern
argument_list|,
name|df
argument_list|)
expr_stmt|;
block|}
return|return
name|df
return|;
block|}
specifier|public
name|DateFormat
name|getDateFormat
parameter_list|(
name|Pattern
name|pattern
parameter_list|)
block|{
return|return
name|getDateFormat
argument_list|(
name|pattern
operator|.
name|toPattern
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|NumberFormat
name|getNumberFormat
parameter_list|(
name|String
name|pattern
parameter_list|)
block|{
name|NumberFormat
name|nf
init|=
name|iNumberFormats
operator|.
name|get
argument_list|(
name|pattern
argument_list|)
decl_stmt|;
if|if
condition|(
name|nf
operator|==
literal|null
condition|)
block|{
name|nf
operator|=
operator|new
name|DecimalFormat
argument_list|(
name|pattern
argument_list|,
operator|new
name|DecimalFormatSymbols
argument_list|(
name|Localization
operator|.
name|getJavaLocale
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|iNumberFormats
operator|.
name|put
argument_list|(
name|pattern
argument_list|,
name|nf
argument_list|)
expr_stmt|;
block|}
return|return
name|nf
return|;
block|}
specifier|public
name|NumberFormat
name|getNumberFormat
parameter_list|(
name|Pattern
name|pattern
parameter_list|)
block|{
return|return
name|getNumberFormat
argument_list|(
name|pattern
operator|.
name|toPattern
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
interface|interface
name|Format
parameter_list|<
name|T
parameter_list|>
extends|extends
name|Serializable
block|{
specifier|public
name|String
name|format
parameter_list|(
name|T
name|t
parameter_list|)
function_decl|;
specifier|public
name|T
name|parse
parameter_list|(
name|String
name|source
parameter_list|)
throws|throws
name|ParseException
function_decl|;
specifier|public
name|String
name|toPattern
parameter_list|()
function_decl|;
specifier|public
name|boolean
name|isValid
parameter_list|(
name|String
name|source
parameter_list|)
function_decl|;
block|}
specifier|protected
interface|interface
name|PatternHolder
block|{
name|String
name|getPattern
parameter_list|()
function_decl|;
block|}
block|}
end_class

end_unit

