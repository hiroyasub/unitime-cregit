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
name|action
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
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletRequest
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|servlet
operator|.
name|http
operator|.
name|HttpServletResponse
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionForward
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionMapping
import|;
end_import

begin_import
import|import
name|org
operator|.
name|springframework
operator|.
name|beans
operator|.
name|factory
operator|.
name|annotation
operator|.
name|Autowired
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
name|ApplicationProperties
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
name|model
operator|.
name|ItypeDesc
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
name|security
operator|.
name|SessionContext
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
name|security
operator|.
name|rights
operator|.
name|Right
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
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|webutil
operator|.
name|PdfWebTable
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 10-03-2005  *   * XDoclet definition:  * @struts:action validate="true"  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/itypeDescList"
argument_list|)
specifier|public
class|class
name|ItypeDescListAction
extends|extends
name|Action
block|{
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
comment|// --------------------------------------------------------- Instance Variables
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 */
specifier|public
name|ActionForward
name|execute
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|ActionForm
name|form
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|HttpServletResponse
name|response
parameter_list|)
throws|throws
name|Exception
block|{
name|String
name|errM
init|=
literal|""
decl_stmt|;
comment|// Check if user is logged in
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|InstructionalTypes
argument_list|)
expr_stmt|;
comment|// Create new table
name|PdfWebTable
name|webTable
init|=
operator|new
name|PdfWebTable
argument_list|(
literal|6
argument_list|,
literal|null
argument_list|,
literal|"itypeDescList.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"IType"
block|,
literal|"Abbreviation"
block|,
literal|"Name"
block|,
literal|"Reference"
block|,
literal|"Type"
block|,
literal|"Parent"
block|,
literal|"Organized"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"left"
block|,
literal|"center"
block|}
argument_list|,
operator|new
name|boolean
index|[]
block|{
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|false
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|PdfWebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"itypeDescList.ord"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"ord"
argument_list|)
argument_list|,
literal|1
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|ItypeDesc
operator|.
name|findAll
argument_list|(
literal|false
argument_list|)
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|ItypeDesc
name|itypeDesc
init|=
operator|(
name|ItypeDesc
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// Add to web table
name|webTable
operator|.
name|addLine
argument_list|(
literal|"onclick=\"document.location='itypeDescEdit.do?op=Edit&id="
operator|+
name|itypeDesc
operator|.
name|getItype
argument_list|()
operator|+
literal|"';\""
argument_list|,
operator|new
name|String
index|[]
block|{
name|itypeDesc
operator|.
name|getItype
argument_list|()
operator|.
name|toString
argument_list|()
block|,
name|itypeDesc
operator|.
name|getAbbv
argument_list|()
block|,
name|itypeDesc
operator|.
name|getDesc
argument_list|()
block|,
operator|(
name|itypeDesc
operator|.
name|getSis_ref
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|itypeDesc
operator|.
name|getSis_ref
argument_list|()
operator|)
block|,
name|itypeDesc
operator|.
name|getBasicType
argument_list|()
block|,
operator|(
name|itypeDesc
operator|.
name|getParent
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|itypeDesc
operator|.
name|getParent
argument_list|()
operator|.
name|getDesc
argument_list|()
operator|)
block|,
operator|(
name|itypeDesc
operator|.
name|isOrganized
argument_list|()
condition|?
literal|"yes"
else|:
literal|"no"
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|itypeDesc
operator|.
name|getItype
argument_list|()
block|,
name|itypeDesc
operator|.
name|getAbbv
argument_list|()
block|,
name|itypeDesc
operator|.
name|getDesc
argument_list|()
block|,
operator|(
name|itypeDesc
operator|.
name|getSis_ref
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|itypeDesc
operator|.
name|getSis_ref
argument_list|()
operator|)
block|,
name|itypeDesc
operator|.
name|getBasic
argument_list|()
block|,
operator|(
name|itypeDesc
operator|.
name|getParent
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|itypeDesc
operator|.
name|getParent
argument_list|()
operator|.
name|getItype
argument_list|()
operator|)
block|,
operator|(
name|itypeDesc
operator|.
name|isOrganized
argument_list|()
condition|?
literal|0
else|:
literal|1
operator|)
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Export PDF"
operator|.
name|equals
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op"
argument_list|)
argument_list|)
condition|)
block|{
name|File
name|file
init|=
name|ApplicationProperties
operator|.
name|getTempFile
argument_list|(
literal|"itypes"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|webTable
operator|.
name|exportPdf
argument_list|(
name|file
argument_list|,
name|PdfWebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"itypeDescList.ord"
argument_list|)
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|REQUEST_OPEN_URL
argument_list|,
literal|"temp/"
operator|+
name|file
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|String
name|tblData
init|=
name|webTable
operator|.
name|printTable
argument_list|(
name|PdfWebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"itypeDescList.ord"
argument_list|)
argument_list|)
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"itypeDescList"
argument_list|,
name|errM
operator|+
name|tblData
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"success"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

