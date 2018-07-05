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
name|form
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Properties
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
name|apache
operator|.
name|struts
operator|.
name|upload
operator|.
name|FormFile
import|;
end_import

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 01-24-2007  *   * XDoclet definition:  * @struts.form name="dataImportForm"  *  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|DataImportForm
extends|extends
name|ActionForm
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|7165669008085313647L
decl_stmt|;
specifier|private
specifier|transient
name|FormFile
name|iFile
decl_stmt|;
specifier|private
name|String
name|iOp
decl_stmt|;
specifier|private
name|String
name|iExport
decl_stmt|;
specifier|private
name|boolean
name|iEmail
init|=
literal|false
decl_stmt|;
specifier|private
name|String
name|iAddress
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
enum|enum
name|ExportType
block|{
name|COURSES
argument_list|(
literal|"offerings"
argument_list|,
literal|"Course Offerings"
argument_list|,
literal|"tmtbl.export.timetable"
argument_list|,
literal|"false"
argument_list|,
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"none"
argument_list|)
block|,
name|COURSES_WITH_TIME
argument_list|(
literal|"offerings"
argument_list|,
literal|"Course Offerings (including course timetable)"
argument_list|,
literal|"tmtbl.export.timetable"
argument_list|,
literal|"true"
argument_list|,
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"none"
argument_list|)
block|,
name|COURSES_WITH_EXAMS
argument_list|(
literal|"offerings"
argument_list|,
literal|"Course Offerings (including exams)"
argument_list|,
literal|"tmtbl.export.timetable"
argument_list|,
literal|"false"
argument_list|,
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"all"
argument_list|)
block|,
name|COURSES_ALL
argument_list|(
literal|"offerings"
argument_list|,
literal|"Course Offerings (including course timetable and exams)"
argument_list|,
literal|"tmtbl.export.timetable"
argument_list|,
literal|"true"
argument_list|,
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"all"
argument_list|)
block|,
name|TIMETABLE
argument_list|(
literal|"timetable"
argument_list|,
literal|"Course Timetable"
argument_list|)
block|,
name|EXAMS
argument_list|(
literal|"exams"
argument_list|,
literal|"Examinations"
argument_list|,
literal|"tmtbl.export.exam"
argument_list|,
literal|"true"
argument_list|,
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"all"
argument_list|)
block|,
name|EXAMS_FINAL
argument_list|(
literal|"exams"
argument_list|,
literal|"Examinations (only finals)"
argument_list|,
literal|"tmtbl.export.exam"
argument_list|,
literal|"true"
argument_list|,
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"final"
argument_list|)
block|,
name|EXAMS_MIDTERM
argument_list|(
literal|"exams"
argument_list|,
literal|"Examinations (only midterm)"
argument_list|,
literal|"tmtbl.export.exam"
argument_list|,
literal|"true"
argument_list|,
literal|"tmtbl.export.exam.type"
argument_list|,
literal|"midterm"
argument_list|)
block|,
name|CURRICULA
argument_list|(
literal|"curricula"
argument_list|,
literal|"Curricula"
argument_list|)
block|,
name|STUDENTS
argument_list|(
literal|"students"
argument_list|,
literal|"Students"
argument_list|)
block|,
name|STUDENT_ENRL
argument_list|(
literal|"studentEnrollments"
argument_list|,
literal|"Student class enrollments"
argument_list|)
block|,
name|REQUESTS
argument_list|(
literal|"request"
argument_list|,
literal|"Student course requests"
argument_list|)
block|,
name|RESERVATIONS
argument_list|(
literal|"reservations"
argument_list|,
literal|"Reservations"
argument_list|)
block|,
name|SESSION
argument_list|(
literal|"session"
argument_list|,
literal|"Academic Session"
argument_list|)
block|,
name|PERMISSIONS
argument_list|(
literal|"permissions"
argument_list|,
literal|"Permissions"
argument_list|)
block|,
name|TRAVELTIMES
argument_list|(
literal|"traveltimes"
argument_list|,
literal|"Travel Times"
argument_list|)
block|,
name|ROOM_SHARING
argument_list|(
literal|"roomSharing"
argument_list|,
literal|"Room Sharing"
argument_list|)
block|,
name|POINT_IN_TIME_DATA
argument_list|(
literal|"pointInTimeData"
argument_list|,
literal|"Point-In-Time Data"
argument_list|)
block|,
name|PREFERENCES
argument_list|(
literal|"preferences"
argument_list|,
literal|"Course Timetabling Preferences"
argument_list|)
block|,
name|SESSION_SETUP
argument_list|(
literal|"sessionSetup"
argument_list|,
literal|"Academic Session Setup"
argument_list|)
block|,
name|STUDENT_ADVISORS
argument_list|(
literal|"studentAdvisors"
argument_list|,
literal|"Student Advisors"
argument_list|)
block|,
name|STUDENT_STATUSES
argument_list|(
literal|"studentStatuses"
argument_list|,
literal|"Student Scheduling Statuses"
argument_list|)
block|;
specifier|private
name|String
name|iType
decl_stmt|,
name|iLabel
decl_stmt|;
specifier|private
name|String
index|[]
name|iOptions
decl_stmt|;
name|ExportType
parameter_list|(
name|String
name|type
parameter_list|,
name|String
name|label
parameter_list|,
name|String
modifier|...
name|options
parameter_list|)
block|{
name|iType
operator|=
name|type
expr_stmt|;
name|iLabel
operator|=
name|label
expr_stmt|;
name|iOptions
operator|=
name|options
expr_stmt|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iLabel
return|;
block|}
specifier|public
name|void
name|setOptions
parameter_list|(
name|Properties
name|config
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|iOptions
operator|.
name|length
condition|;
name|i
operator|+=
literal|2
control|)
name|config
operator|.
name|put
argument_list|(
name|iOptions
index|[
name|i
index|]
argument_list|,
name|iOptions
index|[
name|i
operator|+
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
block|}
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
literal|"Import"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|&&
operator|(
name|iFile
operator|==
literal|null
operator|||
name|iFile
operator|.
name|getFileSize
argument_list|()
operator|<=
literal|0
operator|)
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
literal|"File"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|"Export"
operator|.
name|equals
argument_list|(
name|iOp
argument_list|)
operator|&&
name|getExportType
argument_list|()
operator|==
literal|null
condition|)
block|{
name|errors
operator|.
name|add
argument_list|(
literal|"export"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"Nothing to export"
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|errors
return|;
block|}
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
name|iFile
operator|=
literal|null
expr_stmt|;
name|iExport
operator|=
literal|null
expr_stmt|;
name|iEmail
operator|=
literal|false
expr_stmt|;
name|iAddress
operator|=
literal|null
expr_stmt|;
block|}
specifier|public
name|FormFile
name|getFile
parameter_list|()
block|{
return|return
name|iFile
return|;
block|}
specifier|public
name|void
name|setFile
parameter_list|(
name|FormFile
name|file
parameter_list|)
block|{
name|iFile
operator|=
name|file
expr_stmt|;
block|}
specifier|public
name|String
name|getOp
parameter_list|()
block|{
return|return
name|iOp
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
name|iOp
operator|=
name|op
expr_stmt|;
block|}
specifier|public
name|String
name|getExport
parameter_list|()
block|{
return|return
name|iExport
return|;
block|}
specifier|public
name|void
name|setExport
parameter_list|(
name|String
name|export
parameter_list|)
block|{
name|iExport
operator|=
name|export
expr_stmt|;
block|}
specifier|public
name|boolean
name|getEmail
parameter_list|()
block|{
return|return
name|iEmail
return|;
block|}
specifier|public
name|void
name|setEmail
parameter_list|(
name|boolean
name|email
parameter_list|)
block|{
name|iEmail
operator|=
name|email
expr_stmt|;
block|}
specifier|public
name|String
name|getAddress
parameter_list|()
block|{
return|return
name|iAddress
return|;
block|}
specifier|public
name|void
name|setAddress
parameter_list|(
name|String
name|address
parameter_list|)
block|{
name|iAddress
operator|=
name|address
expr_stmt|;
block|}
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|DataImportForm
name|form
init|=
operator|new
name|DataImportForm
argument_list|()
decl_stmt|;
name|form
operator|.
name|iFile
operator|=
name|iFile
expr_stmt|;
name|form
operator|.
name|iOp
operator|=
name|iOp
expr_stmt|;
name|form
operator|.
name|iExport
operator|=
name|iExport
expr_stmt|;
name|form
operator|.
name|iEmail
operator|=
name|iEmail
expr_stmt|;
name|form
operator|.
name|iAddress
operator|=
name|iAddress
expr_stmt|;
return|return
name|form
return|;
block|}
specifier|public
name|List
argument_list|<
name|ListItem
argument_list|>
name|getExportTypes
parameter_list|()
block|{
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
name|items
init|=
operator|new
name|ArrayList
argument_list|<
name|ListItem
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|ExportType
name|t
range|:
name|ExportType
operator|.
name|values
argument_list|()
control|)
name|items
operator|.
name|add
argument_list|(
operator|new
name|ListItem
argument_list|(
name|t
operator|.
name|name
argument_list|()
argument_list|,
name|t
operator|.
name|getLabel
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|items
return|;
block|}
specifier|public
name|ExportType
name|getExportType
parameter_list|()
block|{
if|if
condition|(
name|getExport
argument_list|()
operator|==
literal|null
operator|||
name|getExport
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
return|return
literal|null
return|;
return|return
name|ExportType
operator|.
name|valueOf
argument_list|(
name|getExport
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
class|class
name|ListItem
block|{
name|String
name|iValue
decl_stmt|,
name|iText
decl_stmt|;
specifier|public
name|ListItem
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|iValue
operator|=
name|value
expr_stmt|;
name|iText
operator|=
name|text
expr_stmt|;
block|}
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|iValue
return|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|iText
return|;
block|}
block|}
block|}
end_class

end_unit

