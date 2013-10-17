begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2012 - 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|export
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|export
operator|.
name|Exporter
operator|.
name|Printer
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|CSVPrinter
implements|implements
name|Printer
block|{
specifier|private
name|PrintWriter
name|iOut
decl_stmt|;
specifier|private
name|String
index|[]
name|iLastLine
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iCheckLast
init|=
literal|false
decl_stmt|;
specifier|private
name|Set
argument_list|<
name|Integer
argument_list|>
name|iHiddenColumns
init|=
operator|new
name|HashSet
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|CSVPrinter
parameter_list|(
name|PrintWriter
name|writer
parameter_list|,
name|boolean
name|checkLast
parameter_list|)
block|{
name|iOut
operator|=
name|writer
expr_stmt|;
name|iCheckLast
operator|=
name|checkLast
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getContentType
parameter_list|()
block|{
return|return
literal|"text/csv"
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|hideColumn
parameter_list|(
name|int
name|col
parameter_list|)
block|{
name|iHiddenColumns
operator|.
name|add
argument_list|(
name|col
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printHeader
parameter_list|(
name|String
modifier|...
name|fields
parameter_list|)
block|{
name|printLine
argument_list|(
name|fields
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|printLine
parameter_list|(
name|String
modifier|...
name|fields
parameter_list|)
block|{
for|for
control|(
name|int
name|idx
init|=
literal|0
init|;
name|idx
operator|<
name|fields
operator|.
name|length
condition|;
name|idx
operator|++
control|)
block|{
if|if
condition|(
name|iHiddenColumns
operator|.
name|contains
argument_list|(
name|idx
argument_list|)
condition|)
continue|continue;
name|String
name|f
init|=
name|fields
index|[
name|idx
index|]
decl_stmt|;
if|if
condition|(
name|f
operator|!=
literal|null
operator|&&
operator|!
name|f
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|iCheckLast
operator|||
operator|!
name|f
operator|.
name|equals
argument_list|(
name|iLastLine
operator|==
literal|null
operator|||
name|idx
operator|>=
name|iLastLine
operator|.
name|length
condition|?
literal|null
else|:
name|iLastLine
index|[
name|idx
index|]
argument_list|)
condition|)
name|iOut
operator|.
name|print
argument_list|(
literal|"\""
operator|+
name|f
operator|.
name|replace
argument_list|(
literal|"\""
argument_list|,
literal|"\"\""
argument_list|)
operator|+
literal|"\""
argument_list|)
expr_stmt|;
block|}
name|iOut
operator|.
name|print
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
name|iOut
operator|.
name|println
argument_list|()
expr_stmt|;
name|iLastLine
operator|=
name|fields
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|flush
parameter_list|()
block|{
name|iLastLine
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|close
parameter_list|()
block|{
block|}
block|}
end_class

end_unit

