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
name|form
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
name|org
operator|.
name|apache
operator|.
name|struts
operator|.
name|action
operator|.
name|ActionErrors
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
name|ActionMapping
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
name|ActionMessage
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
name|Element
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
name|unitime
operator|.
name|timetable
operator|.
name|dataexchange
operator|.
name|AcadAreaReservationImportDAO
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
name|dataexchange
operator|.
name|AcademicAreaImportDAO
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
name|dataexchange
operator|.
name|AcademicClassificationImportDAO
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
name|dataexchange
operator|.
name|BuildingRoomImport
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
name|dataexchange
operator|.
name|CourseCatalogImportDAO
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
name|dataexchange
operator|.
name|DepartmentImportDAO
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
name|dataexchange
operator|.
name|LastLikeCourseDemandImport
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
name|dataexchange
operator|.
name|PosMajorImportDAO
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
name|dataexchange
operator|.
name|PosMinorImportDAO
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
name|dataexchange
operator|.
name|SessionImportDAO
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
name|dataexchange
operator|.
name|StaffImportDAO
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
name|dataexchange
operator|.
name|StudentImportDAO
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
name|dataexchange
operator|.
name|SubjectAreaImportDAO
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 01-24-2007  *   * XDoclet definition:  * @struts.form name="dataImportForm"  */
end_comment

begin_class
specifier|public
class|class
name|DataImportForm
extends|extends
name|ActionForm
block|{
comment|// --------------------------------------------------------- Instance Variables
comment|/** fileName property */
specifier|private
name|String
name|fileName
decl_stmt|;
specifier|private
name|String
name|op
decl_stmt|;
comment|// --------------------------------------------------------- Methods
comment|/**  	 * Method validate 	 * @param mapping 	 * @param request 	 * @return ActionErrors 	 */
specifier|public
name|ActionErrors
name|validate
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|ActionErrors
name|errors
init|=
operator|new
name|ActionErrors
argument_list|()
decl_stmt|;
if|if
condition|(
name|fileName
operator|==
literal|null
operator|||
name|fileName
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"name"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.required"
argument_list|,
literal|"File Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"notFound"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.fileNotFound"
argument_list|,
name|fileName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|errors
return|;
block|}
comment|/**  	 * Method reset 	 * @param mapping 	 * @param request 	 */
specifier|public
name|void
name|reset
parameter_list|(
name|ActionMapping
name|mapping
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|)
block|{
name|this
operator|.
name|fileName
operator|=
literal|null
expr_stmt|;
block|}
comment|/**  	 * Returns the fileName. 	 * @return String 	 */
specifier|public
name|String
name|getFileName
parameter_list|()
block|{
return|return
name|fileName
return|;
block|}
comment|/**  	 * Set the fileName. 	 * @param fileName The fileName to set 	 */
specifier|public
name|void
name|setFileName
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|this
operator|.
name|fileName
operator|=
name|fileName
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|op
return|;
block|}
specifier|public
name|void
name|setOp
parameter_list|(
name|String
name|op
parameter_list|)
block|{
name|this
operator|.
name|op
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|void
name|doImport
parameter_list|()
throws|throws
name|Exception
block|{
name|Document
name|document
init|=
operator|(
operator|new
name|SAXReader
argument_list|()
operator|)
operator|.
name|read
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
name|Element
name|root
init|=
name|document
operator|.
name|getRootElement
argument_list|()
decl_stmt|;
if|if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"academicAreas"
argument_list|)
condition|)
block|{
operator|new
name|AcademicAreaImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"subjectAreas"
argument_list|)
condition|)
block|{
operator|new
name|SubjectAreaImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"academicClassifications"
argument_list|)
condition|)
block|{
operator|new
name|AcademicClassificationImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"departments"
argument_list|)
condition|)
block|{
operator|new
name|DepartmentImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"posMajors"
argument_list|)
condition|)
block|{
operator|new
name|PosMajorImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"posMinors"
argument_list|)
condition|)
block|{
operator|new
name|PosMinorImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"students"
argument_list|)
condition|)
block|{
operator|new
name|StudentImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"staff"
argument_list|)
condition|)
block|{
operator|new
name|StaffImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"lastLikeCourseDemand"
argument_list|)
condition|)
block|{
operator|new
name|LastLikeCourseDemandImport
argument_list|()
operator|.
name|loadXml
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"academicAreaReservations"
argument_list|)
condition|)
block|{
operator|new
name|AcadAreaReservationImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"session"
argument_list|)
condition|)
block|{
operator|new
name|SessionImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"courseCatalog"
argument_list|)
condition|)
block|{
operator|new
name|CourseCatalogImportDAO
argument_list|()
operator|.
name|loadFromXML
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|root
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"buildingsRooms"
argument_list|)
condition|)
block|{
operator|new
name|BuildingRoomImport
argument_list|()
operator|.
name|loadXml
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|Exception
argument_list|(
name|root
operator|.
name|getName
argument_list|()
operator|+
literal|" is an unknown data type."
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

