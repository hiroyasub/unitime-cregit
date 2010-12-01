begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2009 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|webutil
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
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
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|Debug
import|;
end_import

begin_comment
comment|/**  * Miscellaneous functions to try to screen out attempts at cross-site scripting  *   * @author Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|WebTextValidation
block|{
specifier|private
specifier|static
specifier|final
name|String
name|patternStr
init|=
literal|".*[|<>\"+].*"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|hexPatternStr
init|=
literal|".*%[0-9A-Fa-f][0-9A-Fa-f].*"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|phonePatternStr
init|=
literal|"^[ \t\n\f\r]*[0-9)( \t\n\f\r+][0-9)( \t\n\f\r.,-]+[ \t\n\f\r]*$"
decl_stmt|;
specifier|public
specifier|static
name|boolean
name|isTextValid
parameter_list|(
name|String
name|aText
parameter_list|,
name|boolean
name|canBeNull
parameter_list|)
block|{
if|if
condition|(
operator|!
name|canBeNull
operator|&&
operator|(
name|aText
operator|==
literal|null
operator|||
name|aText
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
if|if
condition|(
name|canBeNull
operator|&&
operator|(
name|aText
operator|==
literal|null
operator|||
name|aText
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
name|String
name|checkText
init|=
name|aText
operator|.
name|toUpperCase
argument_list|()
decl_stmt|;
try|try
block|{
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|patternStr
argument_list|,
name|Pattern
operator|.
name|DOTALL
operator|+
name|Pattern
operator|.
name|CASE_INSENSITIVE
operator|+
name|Pattern
operator|.
name|UNIX_LINES
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|checkText
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|matches
argument_list|()
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
name|Pattern
name|patternHex
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|hexPatternStr
argument_list|,
name|Pattern
operator|.
name|DOTALL
operator|+
name|Pattern
operator|.
name|CASE_INSENSITIVE
operator|+
name|Pattern
operator|.
name|UNIX_LINES
argument_list|)
decl_stmt|;
name|Matcher
name|matcherHex
init|=
name|patternHex
operator|.
name|matcher
argument_list|(
name|checkText
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcherHex
operator|.
name|matches
argument_list|()
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|info
argument_list|(
literal|"Threw exception "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|(
literal|false
operator|)
return|;
block|}
return|return
operator|(
literal|true
operator|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|containsOnlyCharactersUsedInPhoneNumbers
parameter_list|(
name|String
name|aText
parameter_list|,
name|boolean
name|canBeNull
parameter_list|)
block|{
if|if
condition|(
operator|!
name|canBeNull
operator|&&
operator|(
name|aText
operator|==
literal|null
operator|||
name|aText
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
if|if
condition|(
name|canBeNull
operator|&&
operator|(
name|aText
operator|==
literal|null
operator|||
name|aText
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
try|try
block|{
name|Pattern
name|phonePattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|phonePatternStr
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
init|=
name|phonePattern
operator|.
name|matcher
argument_list|(
name|aText
argument_list|)
decl_stmt|;
if|if
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
operator|(
literal|true
operator|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
return|return
operator|(
literal|false
operator|)
return|;
block|}
return|return
operator|(
literal|false
operator|)
return|;
block|}
specifier|public
name|WebTextValidation
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

