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
name|dataexchange
package|;
end_package

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
name|Properties
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|springframework
operator|.
name|web
operator|.
name|util
operator|.
name|HtmlUtils
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
name|Session
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
name|TimetableManager
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
name|dao
operator|.
name|_RootDAO
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer  */
end_comment

begin_class
specifier|public
class|class
name|DataExchangeHelper
block|{
specifier|protected
specifier|static
name|Log
name|sLog
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|DataExchangeHelper
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|String
name|sLogLevelDebug
init|=
literal|"DEBUG"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sLogLevelInfo
init|=
literal|"INFO"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sLogLevelWarn
init|=
literal|"WARN"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sLogLevelError
init|=
literal|"ERROR"
decl_stmt|;
specifier|public
specifier|static
name|String
name|sLogLevelFatal
init|=
literal|"FATAL"
decl_stmt|;
specifier|protected
name|LogWriter
name|iTextLog
decl_stmt|;
specifier|protected
name|org
operator|.
name|hibernate
operator|.
name|Session
name|iHibSession
init|=
literal|null
decl_stmt|;
specifier|protected
name|org
operator|.
name|hibernate
operator|.
name|Transaction
name|iTx
init|=
literal|null
decl_stmt|;
specifier|protected
name|int
name|iFlushIfNeededCounter
init|=
literal|0
decl_stmt|;
specifier|protected
specifier|static
name|int
name|sBatchSize
init|=
literal|100
decl_stmt|;
specifier|public
specifier|static
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
name|sExportRegister
decl_stmt|;
specifier|public
specifier|static
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
name|sImportRegister
decl_stmt|;
static|static
block|{
name|sExportRegister
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
argument_list|()
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"exams"
argument_list|,
name|CourseOfferingExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"offerings"
argument_list|,
name|CourseOfferingExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"timetable"
argument_list|,
name|CourseTimetableExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"curricula"
argument_list|,
name|CurriculaExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"studentEnrollments"
argument_list|,
name|StudentEnrollmentExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"students"
argument_list|,
name|StudentExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"reservations"
argument_list|,
name|ReservationExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"permissions"
argument_list|,
name|PermissionsExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"traveltimes"
argument_list|,
name|TravelTimesExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"lastLikeCourseDemand"
argument_list|,
name|LastLikeCourseDemandExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"request"
argument_list|,
name|StudentSectioningExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"roomSharing"
argument_list|,
name|RoomSharingExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sExportRegister
operator|.
name|put
argument_list|(
literal|"pointInTimeData"
argument_list|,
name|PointInTimeDataExport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Class
argument_list|>
argument_list|()
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"academicAreas"
argument_list|,
name|AcademicAreaImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"academicClassifications"
argument_list|,
name|AcademicClassificationImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"buildingsRooms"
argument_list|,
name|BuildingRoomImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"courseCatalog"
argument_list|,
name|CourseCatalogImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"offerings"
argument_list|,
name|CourseOfferingImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"courseOfferingReservations"
argument_list|,
name|CourseOfferingReservationImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"departments"
argument_list|,
name|DepartmentImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"posMajors"
argument_list|,
name|PosMajorImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"posMinors"
argument_list|,
name|PosMinorImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"session"
argument_list|,
name|SessionImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"staff"
argument_list|,
name|StaffImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"studentEnrollments"
argument_list|,
name|StudentEnrollmentImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"students"
argument_list|,
name|StudentImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"lastLikeCourseDemand"
argument_list|,
name|LastLikeCourseDemandImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"subjectAreas"
argument_list|,
name|SubjectAreaImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"request"
argument_list|,
name|StudentSectioningImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"events"
argument_list|,
name|EventImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"curricula"
argument_list|,
name|CurriculaImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"studentGroups"
argument_list|,
name|StudentGroupImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"studentAccomodations"
argument_list|,
name|StudentAccomodationImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"reservations"
argument_list|,
name|ReservationImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"permissions"
argument_list|,
name|PermissionsImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"traveltimes"
argument_list|,
name|TravelTimesImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"timetable"
argument_list|,
name|CourseTimetableImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"roomSharing"
argument_list|,
name|RoomSharingImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"scripts"
argument_list|,
name|ScriptImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"script"
argument_list|,
name|ScriptImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"reports"
argument_list|,
name|HQLImport
operator|.
name|class
argument_list|)
expr_stmt|;
name|sImportRegister
operator|.
name|put
argument_list|(
literal|"report"
argument_list|,
name|HQLImport
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
specifier|public
name|DataExchangeHelper
parameter_list|()
block|{
block|}
specifier|public
name|void
name|setLog
parameter_list|(
name|LogWriter
name|out
parameter_list|)
block|{
name|iTextLog
operator|=
name|out
expr_stmt|;
block|}
specifier|public
name|LogWriter
name|getLog
parameter_list|()
block|{
return|return
name|iTextLog
return|;
block|}
specifier|public
name|void
name|log
parameter_list|(
name|String
name|level
parameter_list|,
name|String
name|message
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
if|if
condition|(
name|iTextLog
operator|==
literal|null
condition|)
return|return;
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
block|{
name|String
name|escapedMessage
init|=
name|HtmlUtils
operator|.
name|htmlEscape
argument_list|(
name|message
argument_list|)
decl_stmt|;
if|if
condition|(
name|sLogLevelDebug
operator|.
name|equals
argument_list|(
name|level
argument_list|)
condition|)
name|iTextLog
operator|.
name|println
argument_list|(
literal|"<font color='gray'>&nbsp;&nbsp;--"
operator|+
name|escapedMessage
operator|+
literal|"</font>"
argument_list|)
expr_stmt|;
if|else if
condition|(
name|sLogLevelInfo
operator|.
name|equals
argument_list|(
name|level
argument_list|)
condition|)
name|iTextLog
operator|.
name|println
argument_list|(
name|escapedMessage
operator|+
literal|""
argument_list|)
expr_stmt|;
if|else if
condition|(
name|sLogLevelWarn
operator|.
name|equals
argument_list|(
name|level
argument_list|)
condition|)
name|iTextLog
operator|.
name|println
argument_list|(
literal|"<font color='orange'>"
operator|+
name|escapedMessage
operator|+
literal|"</font>"
argument_list|)
expr_stmt|;
if|else if
condition|(
name|sLogLevelError
operator|.
name|equals
argument_list|(
name|level
argument_list|)
condition|)
name|iTextLog
operator|.
name|println
argument_list|(
literal|"<font color='red'>"
operator|+
name|escapedMessage
operator|+
literal|"</font>"
argument_list|)
expr_stmt|;
if|else if
condition|(
name|sLogLevelFatal
operator|.
name|equals
argument_list|(
name|level
argument_list|)
condition|)
name|iTextLog
operator|.
name|println
argument_list|(
literal|"<font color='red'><b>"
operator|+
name|escapedMessage
operator|+
literal|"</b></font>"
argument_list|)
expr_stmt|;
else|else
name|iTextLog
operator|.
name|println
argument_list|(
name|escapedMessage
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|void
name|debug
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelDebug
argument_list|,
name|msg
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
name|msg
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|info
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelInfo
argument_list|,
name|msg
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
name|msg
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|warn
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelWarn
argument_list|,
name|msg
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|warn
argument_list|(
name|msg
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|error
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelError
argument_list|,
name|msg
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|error
argument_list|(
name|msg
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|fatal
parameter_list|(
name|String
name|msg
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelFatal
argument_list|,
name|msg
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|fatal
argument_list|(
name|msg
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|debug
parameter_list|(
name|String
name|msg
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelDebug
argument_list|,
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|debug
argument_list|(
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|info
parameter_list|(
name|String
name|msg
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelInfo
argument_list|,
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|info
argument_list|(
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|warn
parameter_list|(
name|String
name|msg
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelWarn
argument_list|,
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|warn
argument_list|(
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|error
parameter_list|(
name|String
name|msg
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelError
argument_list|,
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|error
argument_list|(
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|fatal
parameter_list|(
name|String
name|msg
parameter_list|,
name|Throwable
name|t
parameter_list|)
block|{
name|log
argument_list|(
name|sLogLevelFatal
argument_list|,
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
name|sLog
operator|.
name|fatal
argument_list|(
name|msg
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|org
operator|.
name|hibernate
operator|.
name|Session
name|getHibSession
parameter_list|()
block|{
return|return
name|iHibSession
return|;
block|}
specifier|public
name|boolean
name|beginTransaction
parameter_list|()
block|{
try|try
block|{
name|iHibSession
operator|=
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
expr_stmt|;
name|iTx
operator|=
name|iHibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|debug
argument_list|(
literal|"Transaction started."
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Unable to begin transaction, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|boolean
name|commitTransaction
parameter_list|()
block|{
try|try
block|{
name|iTx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|debug
argument_list|(
literal|"Transaction committed."
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Unable to commit transaction, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
finally|finally
block|{
if|if
condition|(
name|iHibSession
operator|!=
literal|null
operator|&&
name|iHibSession
operator|.
name|isOpen
argument_list|()
condition|)
name|iHibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|rollbackTransaction
parameter_list|()
block|{
try|try
block|{
name|iTx
operator|.
name|rollback
argument_list|()
expr_stmt|;
name|info
argument_list|(
literal|"Transaction rollbacked."
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Unable to rollback transaction, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
finally|finally
block|{
if|if
condition|(
name|iHibSession
operator|!=
literal|null
operator|&&
name|iHibSession
operator|.
name|isOpen
argument_list|()
condition|)
name|iHibSession
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
specifier|public
name|boolean
name|flush
parameter_list|(
name|boolean
name|commit
parameter_list|)
block|{
try|try
block|{
name|getHibSession
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
name|getHibSession
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|commit
operator|&&
name|iTx
operator|!=
literal|null
condition|)
block|{
name|iTx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|iTx
operator|=
name|getHibSession
argument_list|()
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Unable to flush current session, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|boolean
name|flushIfNeeded
parameter_list|(
name|boolean
name|commit
parameter_list|)
block|{
name|iFlushIfNeededCounter
operator|++
expr_stmt|;
if|if
condition|(
name|iFlushIfNeededCounter
operator|>=
name|sBatchSize
condition|)
block|{
name|iFlushIfNeededCounter
operator|=
literal|0
expr_stmt|;
return|return
name|flush
argument_list|(
name|commit
argument_list|)
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
name|boolean
name|flushDoNotClearSession
parameter_list|(
name|boolean
name|commit
parameter_list|)
block|{
try|try
block|{
name|getHibSession
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
if|if
condition|(
name|commit
operator|&&
name|iTx
operator|!=
literal|null
condition|)
block|{
name|iTx
operator|.
name|commit
argument_list|()
expr_stmt|;
name|iTx
operator|=
name|getHibSession
argument_list|()
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|fatal
argument_list|(
literal|"Unable to flush current session, reason: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
specifier|public
name|boolean
name|flushIfNeededDoNotClearSession
parameter_list|(
name|boolean
name|commit
parameter_list|)
block|{
name|iFlushIfNeededCounter
operator|++
expr_stmt|;
if|if
condition|(
name|iFlushIfNeededCounter
operator|>=
name|sBatchSize
condition|)
block|{
name|iFlushIfNeededCounter
operator|=
literal|0
expr_stmt|;
return|return
name|flushDoNotClearSession
argument_list|(
name|commit
argument_list|)
return|;
block|}
return|return
literal|true
return|;
block|}
specifier|public
specifier|static
name|BaseImport
name|createImportBase
parameter_list|(
name|String
name|type
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|type
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Import type not provided."
argument_list|)
throw|;
if|if
condition|(
operator|!
name|sImportRegister
operator|.
name|containsKey
argument_list|(
name|type
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Unknown import type "
operator|+
name|type
operator|+
literal|"."
argument_list|)
throw|;
return|return
operator|(
name|BaseImport
operator|)
name|sImportRegister
operator|.
name|get
argument_list|(
name|type
argument_list|)
operator|.
name|getConstructor
argument_list|()
operator|.
name|newInstance
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|BaseExport
name|createExportBase
parameter_list|(
name|String
name|type
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|type
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Export type not provided."
argument_list|)
throw|;
if|if
condition|(
operator|!
name|sExportRegister
operator|.
name|containsKey
argument_list|(
name|type
argument_list|)
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Unknown export type "
operator|+
name|type
operator|+
literal|"."
argument_list|)
throw|;
return|return
operator|(
name|BaseExport
operator|)
name|sExportRegister
operator|.
name|get
argument_list|(
name|type
argument_list|)
operator|.
name|getConstructor
argument_list|()
operator|.
name|newInstance
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|void
name|importDocument
parameter_list|(
name|Document
name|document
parameter_list|,
name|String
name|userId
parameter_list|,
name|LogWriter
name|log
parameter_list|)
throws|throws
name|Exception
block|{
name|BaseImport
name|imp
init|=
name|createImportBase
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|imp
operator|.
name|setLog
argument_list|(
name|log
argument_list|)
expr_stmt|;
if|if
condition|(
name|userId
operator|!=
literal|null
condition|)
name|imp
operator|.
name|setManager
argument_list|(
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|userId
argument_list|)
argument_list|)
expr_stmt|;
name|imp
operator|.
name|loadXml
argument_list|(
name|document
operator|.
name|getRootElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Document
name|exportDocument
parameter_list|(
name|String
name|rootName
parameter_list|,
name|Session
name|session
parameter_list|,
name|Properties
name|parameters
parameter_list|,
name|LogWriter
name|log
parameter_list|)
throws|throws
name|Exception
block|{
name|BaseExport
name|exp
init|=
name|createExportBase
argument_list|(
name|rootName
argument_list|)
decl_stmt|;
name|exp
operator|.
name|setLog
argument_list|(
name|log
argument_list|)
expr_stmt|;
return|return
name|exp
operator|.
name|saveXml
argument_list|(
name|session
argument_list|,
name|parameters
argument_list|)
return|;
block|}
specifier|public
interface|interface
name|LogWriter
block|{
specifier|public
name|void
name|println
parameter_list|(
name|String
name|message
parameter_list|)
function_decl|;
block|}
block|}
end_class

end_unit

