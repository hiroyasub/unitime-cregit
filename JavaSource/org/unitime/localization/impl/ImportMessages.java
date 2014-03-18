begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.3 - 3.5 (University Timetabling Application)  * Copyright (C) 2011 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
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
name|PrintStream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|CSVFile
import|;
end_import

begin_import
import|import
name|org
operator|.
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|CSVFile
operator|.
name|CSVLine
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, Zuzana Mullerova  */
end_comment

begin_class
specifier|public
class|class
name|ImportMessages
block|{
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
name|PrintStream
name|out
init|=
operator|new
name|PrintStream
argument_list|(
name|System
operator|.
name|out
argument_list|,
literal|true
argument_list|,
literal|"UTF-8"
argument_list|)
decl_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# UniTime 3.4 (University Timetabling Application)"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# as indicated by the @authors tag."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# "
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# This program is free software; you can redistribute it and/or modify"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# it under the terms of the GNU General Public License as published by"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# the Free Software Foundation; either version 3 of the License, or"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# (at your option) any later version."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# "
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# This program is distributed in the hope that it will be useful,"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# but WITHOUT ANY WARRANTY; without even the implied warranty of"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# GNU General Public License for more details."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# "
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# You should have received a copy of the GNU General Public License along"
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# with this program.  If not, see<http://www.gnu.org/licenses/>."
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# "
argument_list|)
expr_stmt|;
name|CSVFile
name|csv
init|=
operator|new
name|CSVFile
argument_list|(
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"file"
argument_list|,
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
operator|+
literal|"/Downloads/UniTime Localization Czech.csv"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|CSVLine
name|line
range|:
name|csv
operator|.
name|getLines
argument_list|()
control|)
block|{
if|if
condition|(
name|line
operator|.
name|getFields
argument_list|()
operator|.
name|size
argument_list|()
operator|>=
literal|2
condition|)
block|{
name|out
operator|.
name|println
argument_list|()
expr_stmt|;
if|if
condition|(
name|line
operator|.
name|getFields
argument_list|()
operator|.
name|size
argument_list|()
operator|>=
literal|3
operator|&&
operator|(
operator|!
name|line
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|||
name|line
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|out
operator|.
name|println
argument_list|(
literal|"# Default: "
operator|+
name|unicodeEscape
argument_list|(
name|line
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
name|line
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
operator|.
name|toString
argument_list|()
operator|+
literal|"="
operator|+
name|unicodeEscape
argument_list|(
name|line
operator|.
name|getField
argument_list|(
literal|2
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|replace
argument_list|(
literal|":"
argument_list|,
literal|"\\:"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|out
operator|.
name|println
argument_list|(
literal|"# Default: "
operator|+
name|unicodeEscape
argument_list|(
name|line
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# FIXME: Translate \""
operator|+
name|unicodeEscape
argument_list|(
name|line
operator|.
name|getField
argument_list|(
literal|1
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
operator|+
literal|"\""
argument_list|)
expr_stmt|;
name|out
operator|.
name|println
argument_list|(
literal|"# "
operator|+
name|line
operator|.
name|getField
argument_list|(
literal|0
argument_list|)
operator|.
name|toString
argument_list|()
operator|+
literal|"="
argument_list|)
expr_stmt|;
block|}
block|}
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

