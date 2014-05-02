begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.4 - 3.5 (University Timetabling Application)  * Copyright (C) 2013, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|dataexchange
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
name|io
operator|.
name|StringWriter
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
name|io
operator|.
name|SAXReader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|stereotype
operator|.
name|Service
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
name|defaults
operator|.
name|ApplicationProperty
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"dataExchangeHelper"
argument_list|)
specifier|public
class|class
name|DataExchangeIntegrationHelper
block|{
specifier|public
name|Document
name|file2document
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|DocumentException
block|{
return|return
operator|new
name|SAXReader
argument_list|()
operator|.
name|read
argument_list|(
name|file
argument_list|)
return|;
block|}
specifier|public
name|String
name|exception2message
parameter_list|(
name|Exception
name|exception
parameter_list|)
throws|throws
name|IOException
block|{
name|StringWriter
name|out
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|exception
operator|.
name|printStackTrace
argument_list|(
operator|new
name|PrintWriter
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|out
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|public
name|String
name|importDocument
parameter_list|(
name|Document
name|document
parameter_list|)
throws|throws
name|Exception
block|{
specifier|final
name|StringBuffer
name|log
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"<html><header><title>XML Import Log</title></header><body>\n"
argument_list|)
decl_stmt|;
name|DataExchangeHelper
operator|.
name|LogWriter
name|logger
init|=
operator|new
name|DataExchangeHelper
operator|.
name|LogWriter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|println
parameter_list|(
name|String
name|message
parameter_list|)
block|{
name|log
operator|.
name|append
argument_list|(
name|message
operator|+
literal|"<br>\n"
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|String
name|manager
init|=
name|document
operator|.
name|getRootElement
argument_list|()
operator|.
name|attributeValue
argument_list|(
literal|"manager"
argument_list|,
name|ApplicationProperty
operator|.
name|DataExchangeXmlManager
operator|.
name|value
argument_list|()
argument_list|)
decl_stmt|;
name|DataExchangeHelper
operator|.
name|importDocument
argument_list|(
name|document
argument_list|,
name|manager
argument_list|,
name|logger
argument_list|)
expr_stmt|;
name|log
operator|.
name|append
argument_list|(
literal|"</body></html>"
argument_list|)
expr_stmt|;
return|return
name|log
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

