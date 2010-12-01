begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.2 (University Timetabling Application)  * Copyright (C) 2008 - 2010, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|reports
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
name|FileOutputStream
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
name|PrintWriter
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|com
operator|.
name|itextpdf
operator|.
name|text
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|com
operator|.
name|itextpdf
operator|.
name|text
operator|.
name|DocumentException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|itextpdf
operator|.
name|text
operator|.
name|FontFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|itextpdf
operator|.
name|text
operator|.
name|PageSize
import|;
end_import

begin_import
import|import
name|com
operator|.
name|itextpdf
operator|.
name|text
operator|.
name|Paragraph
import|;
end_import

begin_import
import|import
name|com
operator|.
name|itextpdf
operator|.
name|text
operator|.
name|pdf
operator|.
name|PdfWriter
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
specifier|public
class|class
name|PdfLegacyReport
block|{
specifier|protected
name|int
name|iNrChars
init|=
literal|133
decl_stmt|;
specifier|protected
name|int
name|iNrLines
init|=
literal|50
decl_stmt|;
specifier|private
name|FileOutputStream
name|iOut
init|=
literal|null
decl_stmt|;
specifier|private
name|Document
name|iDoc
init|=
literal|null
decl_stmt|;
specifier|private
name|StringBuffer
name|iBuffer
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
specifier|private
name|PrintWriter
name|iPrint
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iTitle
decl_stmt|,
name|iTitle2
decl_stmt|,
name|iSubject
decl_stmt|;
specifier|private
name|String
name|iSession
decl_stmt|;
specifier|private
name|int
name|iPageNo
init|=
literal|0
decl_stmt|;
specifier|private
name|int
name|iLineNo
init|=
literal|0
decl_stmt|;
specifier|private
name|String
name|iPageId
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iCont
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iHeader
index|[]
init|=
literal|null
decl_stmt|;
specifier|private
name|String
name|iFooter
init|=
literal|null
decl_stmt|;
specifier|private
name|boolean
name|iEmpty
init|=
literal|true
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeNormal
init|=
literal|0
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeLedger
init|=
literal|1
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|int
name|sModeText
init|=
literal|2
decl_stmt|;
specifier|public
name|PdfLegacyReport
parameter_list|(
name|int
name|mode
parameter_list|,
name|File
name|file
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|title2
parameter_list|,
name|String
name|subject
parameter_list|,
name|String
name|session
parameter_list|)
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
name|iTitle
operator|=
name|title
expr_stmt|;
name|iTitle2
operator|=
name|title2
expr_stmt|;
name|iSubject
operator|=
name|subject
expr_stmt|;
name|iSession
operator|=
name|session
expr_stmt|;
if|if
condition|(
name|file
operator|!=
literal|null
condition|)
name|open
argument_list|(
name|file
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|open
parameter_list|(
name|File
name|file
parameter_list|,
name|int
name|mode
parameter_list|)
throws|throws
name|DocumentException
throws|,
name|IOException
block|{
if|if
condition|(
name|file
operator|==
literal|null
condition|)
return|return;
name|iOut
operator|=
operator|new
name|FileOutputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
name|mode
operator|==
name|sModeText
condition|)
block|{
name|iPrint
operator|=
operator|new
name|PrintWriter
argument_list|(
name|iOut
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iNrLines
operator|=
operator|(
name|mode
operator|==
name|sModeLedger
condition|?
literal|116
else|:
literal|50
operator|)
expr_stmt|;
name|iDoc
operator|=
operator|new
name|Document
argument_list|(
name|mode
operator|==
name|sModeLedger
condition|?
name|PageSize
operator|.
name|LEDGER
operator|.
name|rotate
argument_list|()
else|:
name|PageSize
operator|.
name|LETTER
operator|.
name|rotate
argument_list|()
argument_list|)
expr_stmt|;
name|PdfWriter
operator|.
name|getInstance
argument_list|(
name|iDoc
argument_list|,
name|iOut
argument_list|)
expr_stmt|;
name|iDoc
operator|.
name|addTitle
argument_list|(
name|iTitle
argument_list|)
expr_stmt|;
name|iDoc
operator|.
name|addAuthor
argument_list|(
literal|"UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|"."
operator|+
name|Constants
operator|.
name|BLD_NUMBER
operator|.
name|replaceAll
argument_list|(
literal|"@build.number@"
argument_list|,
literal|"?"
argument_list|)
operator|+
literal|", www.unitime.org"
argument_list|)
expr_stmt|;
name|iDoc
operator|.
name|addSubject
argument_list|(
name|iSubject
argument_list|)
expr_stmt|;
name|iDoc
operator|.
name|addCreator
argument_list|(
literal|"UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|"."
operator|+
name|Constants
operator|.
name|BLD_NUMBER
operator|.
name|replaceAll
argument_list|(
literal|"@build.number@"
argument_list|,
literal|"?"
argument_list|)
operator|+
literal|", www.unitime.org"
argument_list|)
expr_stmt|;
name|iDoc
operator|.
name|open
argument_list|()
expr_stmt|;
block|}
name|iEmpty
operator|=
literal|true
expr_stmt|;
name|iPageNo
operator|=
literal|0
expr_stmt|;
name|iLineNo
operator|=
literal|0
expr_stmt|;
block|}
specifier|protected
name|void
name|setPageName
parameter_list|(
name|String
name|pageName
parameter_list|)
block|{
name|iPageId
operator|=
name|pageName
expr_stmt|;
block|}
specifier|protected
name|void
name|setCont
parameter_list|(
name|String
name|cont
parameter_list|)
block|{
name|iCont
operator|=
name|cont
expr_stmt|;
block|}
specifier|protected
name|void
name|setHeader
parameter_list|(
name|String
index|[]
name|header
parameter_list|)
block|{
name|iHeader
operator|=
name|header
expr_stmt|;
block|}
specifier|protected
name|String
index|[]
name|getHeader
parameter_list|()
block|{
return|return
name|iHeader
return|;
block|}
specifier|protected
name|void
name|setFooter
parameter_list|(
name|String
name|footer
parameter_list|)
block|{
name|iFooter
operator|=
name|footer
expr_stmt|;
block|}
specifier|protected
name|void
name|out
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|DocumentException
block|{
if|if
condition|(
name|iBuffer
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|iBuffer
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|iBuffer
operator|.
name|append
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|static
name|String
name|rep
parameter_list|(
name|char
name|ch
parameter_list|,
name|int
name|cnt
parameter_list|)
block|{
name|String
name|ret
init|=
literal|""
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
name|cnt
condition|;
name|i
operator|++
control|)
name|ret
operator|+=
name|ch
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|protected
name|void
name|outln
parameter_list|(
name|char
name|ch
parameter_list|)
throws|throws
name|DocumentException
block|{
name|out
argument_list|(
name|rep
argument_list|(
name|ch
argument_list|,
name|iNrChars
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|String
name|lpad
parameter_list|(
name|String
name|s
parameter_list|,
name|char
name|ch
parameter_list|,
name|int
name|len
parameter_list|)
block|{
while|while
condition|(
name|s
operator|.
name|length
argument_list|()
operator|<
name|len
condition|)
name|s
operator|=
name|ch
operator|+
name|s
expr_stmt|;
return|return
name|s
return|;
block|}
specifier|protected
name|String
name|lpad
parameter_list|(
name|String
name|s
parameter_list|,
name|int
name|len
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
name|s
operator|=
literal|""
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|length
argument_list|()
operator|>
name|len
condition|)
return|return
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|len
argument_list|)
return|;
return|return
name|lpad
argument_list|(
name|s
argument_list|,
literal|' '
argument_list|,
name|len
argument_list|)
return|;
block|}
specifier|protected
name|String
name|rpad
parameter_list|(
name|String
name|s
parameter_list|,
name|char
name|ch
parameter_list|,
name|int
name|len
parameter_list|)
block|{
while|while
condition|(
name|s
operator|.
name|length
argument_list|()
operator|<
name|len
condition|)
name|s
operator|=
name|s
operator|+
name|ch
expr_stmt|;
return|return
name|s
return|;
block|}
specifier|protected
name|String
name|rpad
parameter_list|(
name|String
name|s
parameter_list|,
name|int
name|len
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
name|s
operator|=
literal|""
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|length
argument_list|()
operator|>
name|len
condition|)
return|return
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|len
argument_list|)
return|;
return|return
name|rpad
argument_list|(
name|s
argument_list|,
literal|' '
argument_list|,
name|len
argument_list|)
return|;
block|}
specifier|protected
name|String
name|mpad
parameter_list|(
name|String
name|s
parameter_list|,
name|char
name|ch
parameter_list|,
name|int
name|len
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
name|s
operator|=
literal|""
expr_stmt|;
if|if
condition|(
name|s
operator|.
name|length
argument_list|()
operator|>
name|len
condition|)
return|return
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|len
argument_list|)
return|;
while|while
condition|(
name|s
operator|.
name|length
argument_list|()
operator|<
name|len
condition|)
if|if
condition|(
name|s
operator|.
name|length
argument_list|()
operator|%
literal|2
operator|==
literal|0
condition|)
name|s
operator|=
name|s
operator|+
name|ch
expr_stmt|;
else|else
name|s
operator|=
name|ch
operator|+
name|s
expr_stmt|;
return|return
name|s
return|;
block|}
specifier|protected
name|String
name|mpad
parameter_list|(
name|String
name|s
parameter_list|,
name|int
name|len
parameter_list|)
block|{
return|return
name|mpad
argument_list|(
name|s
argument_list|,
literal|' '
argument_list|,
name|len
argument_list|)
return|;
block|}
specifier|protected
name|String
name|mpad
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|,
name|char
name|ch
parameter_list|,
name|int
name|len
parameter_list|)
block|{
name|String
name|m
init|=
literal|""
decl_stmt|;
while|while
condition|(
operator|(
name|s1
operator|+
name|m
operator|+
name|s2
operator|)
operator|.
name|length
argument_list|()
operator|<
name|len
condition|)
name|m
operator|+=
name|ch
expr_stmt|;
return|return
name|s1
operator|+
name|m
operator|+
name|s2
return|;
block|}
specifier|protected
name|String
name|render
parameter_list|(
name|String
name|line
parameter_list|,
name|String
name|s
parameter_list|,
name|int
name|idx
parameter_list|)
block|{
name|String
name|a
init|=
operator|(
name|line
operator|.
name|length
argument_list|()
operator|<=
name|idx
condition|?
name|rpad
argument_list|(
name|line
argument_list|,
literal|' '
argument_list|,
name|idx
argument_list|)
else|:
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|idx
argument_list|)
operator|)
decl_stmt|;
name|String
name|b
init|=
operator|(
name|line
operator|.
name|length
argument_list|()
operator|<=
name|idx
operator|+
name|s
operator|.
name|length
argument_list|()
condition|?
literal|""
else|:
name|line
operator|.
name|substring
argument_list|(
name|idx
operator|+
name|s
operator|.
name|length
argument_list|()
argument_list|)
operator|)
decl_stmt|;
return|return
name|a
operator|+
name|s
operator|+
name|b
return|;
block|}
specifier|protected
name|String
name|renderMiddle
parameter_list|(
name|String
name|line
parameter_list|,
name|String
name|s
parameter_list|)
block|{
return|return
name|render
argument_list|(
name|line
argument_list|,
name|s
argument_list|,
operator|(
name|iNrChars
operator|-
name|s
operator|.
name|length
argument_list|()
operator|)
operator|/
literal|2
argument_list|)
return|;
block|}
specifier|protected
name|String
name|renderEnd
parameter_list|(
name|String
name|line
parameter_list|,
name|String
name|s
parameter_list|)
block|{
return|return
name|render
argument_list|(
name|line
argument_list|,
name|s
argument_list|,
name|iNrChars
operator|-
name|s
operator|.
name|length
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|printHeader
parameter_list|()
throws|throws
name|DocumentException
block|{
name|out
argument_list|(
name|renderEnd
argument_list|(
name|renderMiddle
argument_list|(
literal|"UniTime "
operator|+
name|Constants
operator|.
name|VERSION
operator|+
literal|"."
operator|+
name|Constants
operator|.
name|BLD_NUMBER
operator|.
name|replaceAll
argument_list|(
literal|"@build.number@"
argument_list|,
literal|"?"
argument_list|)
argument_list|,
name|iTitle
argument_list|)
argument_list|,
name|iTitle2
argument_list|)
argument_list|)
expr_stmt|;
name|out
argument_list|(
name|mpad
argument_list|(
operator|new
name|SimpleDateFormat
argument_list|(
literal|"EEE MMM dd, yyyy"
argument_list|)
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
argument_list|,
name|iSession
argument_list|,
literal|' '
argument_list|,
name|iNrChars
argument_list|)
argument_list|)
expr_stmt|;
name|outln
argument_list|(
literal|'='
argument_list|)
expr_stmt|;
name|iLineNo
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|iCont
operator|!=
literal|null
operator|&&
name|iCont
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|println
argument_list|(
literal|"("
operator|+
name|iCont
operator|+
literal|" Continued)"
argument_list|)
expr_stmt|;
if|if
condition|(
name|iHeader
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
name|iHeader
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|println
argument_list|(
name|iHeader
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|headerPrinted
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|headerPrinted
parameter_list|()
block|{
block|}
empty_stmt|;
specifier|protected
name|void
name|printFooter
parameter_list|()
throws|throws
name|DocumentException
block|{
name|iEmpty
operator|=
literal|false
expr_stmt|;
name|out
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|out
argument_list|(
name|renderEnd
argument_list|(
name|renderMiddle
argument_list|(
operator|(
name|iFooter
operator|==
literal|null
condition|?
literal|""
else|:
name|iFooter
operator|)
argument_list|,
literal|"Page "
operator|+
operator|(
name|iPageNo
operator|+
literal|1
operator|)
argument_list|)
argument_list|,
operator|(
name|iPageId
operator|==
literal|null
operator|||
name|iPageId
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
name|iPageId
operator|)
operator|+
literal|"  "
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iPrint
operator|!=
literal|null
condition|)
block|{
name|iPrint
operator|.
name|print
argument_list|(
name|iBuffer
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//FIXME: For some reason when a line starts with space, the line is shifted by one space in the resulting PDF (when using iText 5.0.2)
name|Paragraph
name|p
init|=
operator|new
name|Paragraph
argument_list|(
name|iBuffer
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n "
argument_list|,
literal|"\n  "
argument_list|)
argument_list|,
name|FontFactory
operator|.
name|getFont
argument_list|(
name|FontFactory
operator|.
name|COURIER
argument_list|,
literal|9
argument_list|)
argument_list|)
decl_stmt|;
name|p
operator|.
name|setLeading
argument_list|(
literal|9.5f
argument_list|)
expr_stmt|;
comment|//was 13.5f
name|iDoc
operator|.
name|add
argument_list|(
name|p
argument_list|)
expr_stmt|;
block|}
name|iBuffer
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
name|iPageNo
operator|++
expr_stmt|;
block|}
specifier|public
name|void
name|lastPage
parameter_list|()
throws|throws
name|DocumentException
block|{
while|while
condition|(
name|iLineNo
operator|<
name|iNrLines
condition|)
block|{
name|out
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iLineNo
operator|++
expr_stmt|;
block|}
name|printFooter
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|void
name|newPage
parameter_list|()
throws|throws
name|DocumentException
block|{
while|while
condition|(
name|iLineNo
operator|<
name|iNrLines
condition|)
block|{
name|out
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|iLineNo
operator|++
expr_stmt|;
block|}
name|printFooter
argument_list|()
expr_stmt|;
if|if
condition|(
name|iPrint
operator|!=
literal|null
condition|)
block|{
name|iPrint
operator|.
name|print
argument_list|(
literal|"\f\n"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|iDoc
operator|.
name|newPage
argument_list|()
expr_stmt|;
block|}
name|printHeader
argument_list|()
expr_stmt|;
block|}
specifier|public
name|int
name|getLineNumber
parameter_list|()
block|{
return|return
name|iLineNo
return|;
block|}
specifier|protected
name|void
name|println
parameter_list|(
name|String
name|text
parameter_list|)
throws|throws
name|DocumentException
block|{
name|out
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|iLineNo
operator|++
expr_stmt|;
if|if
condition|(
name|iLineNo
operator|>=
name|iNrLines
condition|)
name|newPage
argument_list|()
expr_stmt|;
block|}
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|iEmpty
return|;
block|}
specifier|public
name|void
name|close
parameter_list|()
throws|throws
name|IOException
throws|,
name|DocumentException
block|{
if|if
condition|(
name|isEmpty
argument_list|()
condition|)
block|{
name|println
argument_list|(
literal|"Nothing to report."
argument_list|)
expr_stmt|;
name|lastPage
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|iPrint
operator|!=
literal|null
condition|)
block|{
name|iPrint
operator|.
name|flush
argument_list|()
expr_stmt|;
name|iPrint
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|iDoc
operator|.
name|close
argument_list|()
expr_stmt|;
name|iOut
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

