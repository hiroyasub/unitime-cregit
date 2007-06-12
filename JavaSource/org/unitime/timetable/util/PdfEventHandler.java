begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.0 (University Course Timetabling& Student Sectioning Application)  * Copyright (C) 2007, UniTime.org, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|com
operator|.
name|lowagie
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
name|lowagie
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
name|lowagie
operator|.
name|text
operator|.
name|pdf
operator|.
name|BaseFont
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|pdf
operator|.
name|PdfContentByte
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|pdf
operator|.
name|PdfPageEventHelper
import|;
end_import

begin_import
import|import
name|com
operator|.
name|lowagie
operator|.
name|text
operator|.
name|pdf
operator|.
name|PdfWriter
import|;
end_import

begin_class
specifier|public
class|class
name|PdfEventHandler
extends|extends
name|PdfPageEventHelper
block|{
specifier|private
name|BaseFont
name|baseFont
decl_stmt|;
specifier|private
name|int
name|fontSize
decl_stmt|;
specifier|private
name|Date
name|dateTime
init|=
literal|null
decl_stmt|;
specifier|private
name|SimpleDateFormat
name|dateFormat
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy hh:mmaa"
argument_list|)
decl_stmt|;
comment|/**      * Constructor for PdfEventHandler      *       */
specifier|public
name|PdfEventHandler
parameter_list|()
throws|throws
name|DocumentException
throws|,
name|IOException
block|{
name|super
argument_list|()
expr_stmt|;
name|setBaseFont
argument_list|(
name|BaseFont
operator|.
name|createFont
argument_list|(
name|BaseFont
operator|.
name|HELVETICA
argument_list|,
name|BaseFont
operator|.
name|CP1252
argument_list|,
name|BaseFont
operator|.
name|NOT_EMBEDDED
argument_list|)
argument_list|)
expr_stmt|;
name|setFontSize
argument_list|(
literal|12
argument_list|)
expr_stmt|;
return|return;
block|}
comment|/**      * Initialize Pdf footer      * @param document      * @param outputStream      * @return PdfWriter      */
specifier|public
specifier|static
name|PdfWriter
name|initFooter
parameter_list|(
name|Document
name|document
parameter_list|,
name|FileOutputStream
name|outputStream
parameter_list|)
throws|throws
name|DocumentException
throws|,
name|IOException
block|{
name|PdfWriter
name|iWriter
init|=
name|PdfWriter
operator|.
name|getInstance
argument_list|(
name|document
argument_list|,
name|outputStream
argument_list|)
decl_stmt|;
name|iWriter
operator|.
name|setPageEvent
argument_list|(
operator|new
name|PdfEventHandler
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|iWriter
return|;
block|}
comment|/**      * Print footer string on each page      * @param writer      * @param document      */
specifier|public
name|void
name|onEndPage
parameter_list|(
name|PdfWriter
name|writer
parameter_list|,
name|Document
name|document
parameter_list|)
block|{
if|if
condition|(
name|getDateTime
argument_list|()
operator|==
literal|null
condition|)
block|{
name|setDateTime
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|PdfContentByte
name|cb
init|=
name|writer
operator|.
name|getDirectContent
argument_list|()
decl_stmt|;
name|cb
operator|.
name|beginText
argument_list|()
expr_stmt|;
name|cb
operator|.
name|setFontAndSize
argument_list|(
name|getBaseFont
argument_list|()
argument_list|,
name|getFontSize
argument_list|()
argument_list|)
expr_stmt|;
name|cb
operator|.
name|showTextAligned
argument_list|(
name|PdfContentByte
operator|.
name|ALIGN_LEFT
argument_list|,
name|getDateFormat
argument_list|()
operator|.
name|format
argument_list|(
name|getDateTime
argument_list|()
argument_list|)
argument_list|,
name|document
operator|.
name|left
argument_list|()
argument_list|,
literal|20
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cb
operator|.
name|showTextAligned
argument_list|(
name|PdfContentByte
operator|.
name|ALIGN_RIGHT
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|document
operator|.
name|getPageNumber
argument_list|()
argument_list|)
argument_list|,
name|document
operator|.
name|right
argument_list|()
argument_list|,
literal|20
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|cb
operator|.
name|endText
argument_list|()
expr_stmt|;
return|return;
block|}
specifier|private
name|BaseFont
name|getBaseFont
parameter_list|()
block|{
return|return
name|baseFont
return|;
block|}
specifier|private
name|void
name|setBaseFont
parameter_list|(
name|BaseFont
name|baseFont
parameter_list|)
block|{
name|this
operator|.
name|baseFont
operator|=
name|baseFont
expr_stmt|;
block|}
specifier|private
name|int
name|getFontSize
parameter_list|()
block|{
return|return
name|fontSize
return|;
block|}
specifier|private
name|void
name|setFontSize
parameter_list|(
name|int
name|fontSize
parameter_list|)
block|{
name|this
operator|.
name|fontSize
operator|=
name|fontSize
expr_stmt|;
block|}
specifier|private
name|Date
name|getDateTime
parameter_list|()
block|{
return|return
name|dateTime
return|;
block|}
specifier|private
name|void
name|setDateTime
parameter_list|(
name|Date
name|dateTime
parameter_list|)
block|{
name|this
operator|.
name|dateTime
operator|=
name|dateTime
expr_stmt|;
block|}
specifier|private
name|SimpleDateFormat
name|getDateFormat
parameter_list|()
block|{
return|return
name|dateFormat
return|;
block|}
block|}
end_class

end_unit

