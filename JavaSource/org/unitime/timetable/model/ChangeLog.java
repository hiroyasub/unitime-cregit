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
name|model
package|;
end_package

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
name|Collection
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
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|javax
operator|.
name|servlet
operator|.
name|ServletRequest
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
name|HttpSession
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
name|Globals
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
name|util
operator|.
name|MessageResources
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|Query
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

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|commons
operator|.
name|User
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
name|web
operator|.
name|Web
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
name|base
operator|.
name|BaseChangeLog
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
name|ChangeLogDAO
import|;
end_import

begin_class
specifier|public
class|class
name|ChangeLog
extends|extends
name|BaseChangeLog
implements|implements
name|Comparable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|ChangeLog
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|ChangeLog
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|ChangeLog
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|session
parameter_list|,
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|TimetableManager
name|manager
parameter_list|,
name|java
operator|.
name|util
operator|.
name|Date
name|timeStamp
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|objectType
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|objectTitle
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Long
name|objectUniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|sourceString
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|String
name|operationString
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|session
argument_list|,
name|manager
argument_list|,
name|timeStamp
argument_list|,
name|objectType
argument_list|,
name|objectTitle
argument_list|,
name|objectUniqueId
argument_list|,
name|sourceString
argument_list|,
name|operationString
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
specifier|static
enum|enum
name|Operation
block|{
name|CREATE
block|,
name|UPDATE
block|,
name|DELETE
block|,
name|CLEAR_PREF
block|,
name|CLEAR_ALL_PREF
block|}
specifier|public
specifier|static
enum|enum
name|Source
block|{
name|CLASS_EDIT
block|,
name|SCHEDULING_SUBPART_EDIT
block|,
name|INSTR_CFG_EDIT
block|,
name|CROSS_LIST
block|,
name|MAKE_OFFERED
block|,
name|MAKE_NOT_OFFERED
block|,
name|RESERVATION
block|,
name|COURSE_OFFERING_EDIT
block|,
name|CLASS_SETUP
block|,
name|CLASS_INSTR_ASSIGN
block|,
name|DIST_PREF_EDIT
block|,
name|DESIGNATOR_EDIT
block|,
name|INSTRUCTOR_EDIT
block|,
name|INSTRUCTOR_PREF_EDIT
block|,
name|INSTRUCTOR_MANAGE
block|,
name|ROOM_DEPT_EDIT
block|,
name|ROOM_FEATURE_EDIT
block|,
name|ROOM_GROUP_EDIT
block|,
name|ROOM_EDIT
block|,
name|ROOM_PREF_EDIT
block|,
name|DEPARTMENT_EDIT
block|,
name|SESSION_EDIT
block|,
name|SOLVER_GROUP_EDIT
block|,
name|TIME_PATTERN_EDIT
block|,
name|DATE_PATTERN_EDIT
block|,
name|DIST_TYPE_EDIT
block|,
name|MANAGER_EDIT
block|,
name|SUBJECT_AREA_EDIT
block|,
name|BUILDING_EDIT
block|,
name|EXAM_PERIOD_EDIT
block|,
name|ROOM_EXAM_PERIOD_REF_EDIT
block|,
name|EXAM_EDIT
block|,
name|DATA_IMPORT
block|}
specifier|public
specifier|static
name|SimpleDateFormat
name|sDF
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy hh:mmaa"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|SimpleDateFormat
name|sDFdate
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"MM/dd/yy"
argument_list|)
decl_stmt|;
specifier|public
name|Operation
name|getOperation
parameter_list|()
block|{
return|return
name|Operation
operator|.
name|valueOf
argument_list|(
name|getOperationString
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setOperation
parameter_list|(
name|Operation
name|operation
parameter_list|)
block|{
name|setOperationString
argument_list|(
name|operation
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Source
name|getSource
parameter_list|()
block|{
return|return
name|Source
operator|.
name|valueOf
argument_list|(
name|getSourceString
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|void
name|setSource
parameter_list|(
name|Source
name|source
parameter_list|)
block|{
name|setSourceString
argument_list|(
name|source
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|addChange
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|Object
name|object
parameter_list|,
name|Source
name|source
parameter_list|,
name|Operation
name|operation
parameter_list|,
name|SubjectArea
name|subjArea
parameter_list|,
name|Department
name|dept
parameter_list|)
block|{
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|object
argument_list|,
literal|null
argument_list|,
name|source
argument_list|,
name|operation
argument_list|,
name|subjArea
argument_list|,
name|dept
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|addChange
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|HttpServletRequest
name|request
parameter_list|,
name|Object
name|object
parameter_list|,
name|String
name|objectTitle
parameter_list|,
name|Source
name|source
parameter_list|,
name|Operation
name|operation
parameter_list|,
name|SubjectArea
name|subjArea
parameter_list|,
name|Department
name|dept
parameter_list|)
block|{
try|try
block|{
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|String
name|userId
init|=
operator|(
name|String
operator|)
name|httpSession
operator|.
name|getAttribute
argument_list|(
literal|"authUserExtId"
argument_list|)
decl_stmt|;
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|httpSession
argument_list|)
decl_stmt|;
if|if
condition|(
name|userId
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|user
operator|!=
literal|null
condition|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"No authenticated user defined, using "
operator|+
name|user
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|userId
operator|=
name|user
operator|.
name|getId
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|userId
operator|==
literal|null
condition|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"Unable to add change log -- no user."
argument_list|)
expr_stmt|;
return|return;
block|}
name|Session
name|session
init|=
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
decl_stmt|;
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"Unable to add change log -- no academic session."
argument_list|)
expr_stmt|;
return|return;
block|}
name|TimetableManager
name|manager
init|=
name|TimetableManager
operator|.
name|findByExternalId
argument_list|(
name|userId
argument_list|)
decl_stmt|;
if|if
condition|(
name|manager
operator|==
literal|null
condition|)
name|manager
operator|=
name|TimetableManager
operator|.
name|getManager
argument_list|(
name|user
argument_list|)
expr_stmt|;
if|if
condition|(
name|manager
operator|==
literal|null
condition|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"Unable to add change log -- no timetabling manager."
argument_list|)
expr_stmt|;
return|return;
block|}
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|manager
argument_list|,
name|session
argument_list|,
name|object
argument_list|,
name|objectTitle
argument_list|,
name|source
argument_list|,
name|operation
argument_list|,
name|subjArea
argument_list|,
name|dept
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|addChange
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|TimetableManager
name|manager
parameter_list|,
name|Session
name|session
parameter_list|,
name|Object
name|object
parameter_list|,
name|Source
name|source
parameter_list|,
name|Operation
name|operation
parameter_list|,
name|SubjectArea
name|subjArea
parameter_list|,
name|Department
name|dept
parameter_list|)
block|{
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|manager
argument_list|,
name|session
argument_list|,
name|object
argument_list|,
literal|null
argument_list|,
name|source
argument_list|,
name|operation
argument_list|,
name|subjArea
argument_list|,
name|dept
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|addChange
parameter_list|(
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|,
name|TimetableManager
name|manager
parameter_list|,
name|Session
name|session
parameter_list|,
name|Object
name|object
parameter_list|,
name|String
name|objectTitle
parameter_list|,
name|Source
name|source
parameter_list|,
name|Operation
name|operation
parameter_list|,
name|SubjectArea
name|subjArea
parameter_list|,
name|Department
name|dept
parameter_list|)
block|{
try|try
block|{
if|if
condition|(
name|session
operator|==
literal|null
condition|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"Unable to add change log -- no academic session."
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|manager
operator|==
literal|null
condition|)
block|{
name|Debug
operator|.
name|warning
argument_list|(
literal|"Unable to add change log -- no timetabling manager."
argument_list|)
expr_stmt|;
return|return;
block|}
name|Number
name|objectUniqueId
init|=
operator|(
name|Number
operator|)
name|object
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getUniqueId"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
name|object
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
decl_stmt|;
name|String
name|objectType
init|=
name|object
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|objectType
operator|.
name|indexOf
argument_list|(
literal|"$$"
argument_list|)
operator|>=
literal|0
condition|)
name|objectType
operator|=
name|objectType
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|objectType
operator|.
name|indexOf
argument_list|(
literal|"$$"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|objectTitle
operator|==
literal|null
operator|||
name|objectTitle
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
try|try
block|{
name|objectTitle
operator|=
operator|(
name|String
operator|)
name|object
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getTitle"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
name|object
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|objectTitle
operator|==
literal|null
operator|||
name|objectTitle
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|objectTitle
operator|=
name|object
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|CourseOffering
condition|)
name|objectTitle
operator|=
operator|(
operator|(
name|CourseOffering
operator|)
name|object
operator|)
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
block|}
name|ChangeLog
name|chl
init|=
operator|new
name|ChangeLog
argument_list|()
decl_stmt|;
name|chl
operator|.
name|setSession
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setManager
argument_list|(
name|manager
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setTimeStamp
argument_list|(
operator|new
name|Date
argument_list|()
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setObjectType
argument_list|(
name|objectType
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setObjectUniqueId
argument_list|(
operator|new
name|Long
argument_list|(
name|objectUniqueId
operator|.
name|longValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setObjectTitle
argument_list|(
name|objectTitle
operator|==
literal|null
operator|||
name|objectTitle
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"N/A"
else|:
name|objectTitle
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setSubjectArea
argument_list|(
name|subjArea
argument_list|)
expr_stmt|;
if|if
condition|(
name|dept
operator|==
literal|null
operator|&&
name|subjArea
operator|!=
literal|null
condition|)
name|dept
operator|=
name|subjArea
operator|.
name|getDepartment
argument_list|()
expr_stmt|;
name|chl
operator|.
name|setDepartment
argument_list|(
name|dept
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setSource
argument_list|(
name|source
argument_list|)
expr_stmt|;
name|chl
operator|.
name|setOperation
argument_list|(
name|operation
argument_list|)
expr_stmt|;
if|if
condition|(
name|hibSession
operator|!=
literal|null
condition|)
name|hibSession
operator|.
name|saveOrUpdate
argument_list|(
name|chl
argument_list|)
expr_stmt|;
else|else
operator|new
name|ChangeLogDAO
argument_list|()
operator|.
name|saveOrUpdate
argument_list|(
name|chl
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|String
name|getMessage
parameter_list|(
name|ServletRequest
name|request
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|MessageResources
name|rsc
init|=
operator|(
name|MessageResources
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
name|Globals
operator|.
name|MESSAGES_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|rsc
operator|==
literal|null
condition|)
return|return
name|message
return|;
name|String
name|ret
init|=
name|rsc
operator|.
name|getMessage
argument_list|(
name|message
argument_list|)
decl_stmt|;
return|return
operator|(
name|ret
operator|==
literal|null
condition|?
name|message
else|:
name|ret
operator|)
return|;
block|}
specifier|public
name|String
name|getOperationTitle
parameter_list|(
name|ServletRequest
name|request
parameter_list|)
block|{
return|return
name|getMessage
argument_list|(
name|request
argument_list|,
literal|"changelog.operation."
operator|+
name|getOperationString
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getSourceTitle
parameter_list|(
name|ServletRequest
name|request
parameter_list|)
block|{
return|return
name|getMessage
argument_list|(
name|request
argument_list|,
literal|"changelog.source."
operator|+
name|getSourceString
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getLabel
parameter_list|(
name|ServletRequest
name|request
parameter_list|)
block|{
return|return
literal|"Last "
operator|+
name|getOperationTitle
argument_list|(
name|request
argument_list|)
operator|+
literal|" of "
operator|+
name|getObjectTitle
argument_list|()
operator|+
literal|" was made by "
operator|+
name|getManager
argument_list|()
operator|.
name|getShortName
argument_list|()
operator|+
literal|" at "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|getTimeStamp
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|String
name|getShortLabel
parameter_list|(
name|ServletRequest
name|request
parameter_list|)
block|{
return|return
literal|"Last "
operator|+
name|getOperationTitle
argument_list|(
name|request
argument_list|)
operator|+
literal|" was made by "
operator|+
name|getManager
argument_list|()
operator|.
name|getShortName
argument_list|()
operator|+
literal|" at "
operator|+
name|sDF
operator|.
name|format
argument_list|(
name|getTimeStamp
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|obj
operator|==
literal|null
operator|||
operator|!
operator|(
name|obj
operator|instanceof
name|ChangeLog
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|ChangeLog
name|chl
init|=
operator|(
name|ChangeLog
operator|)
name|obj
decl_stmt|;
name|int
name|cmp
init|=
name|getTimeStamp
argument_list|()
operator|.
name|compareTo
argument_list|(
name|chl
operator|.
name|getTimeStamp
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|cmp
operator|!=
literal|0
condition|)
return|return
name|cmp
return|;
return|return
name|getUniqueId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|chl
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|ChangeLog
name|findLastChange
parameter_list|(
name|Object
name|object
parameter_list|)
block|{
return|return
name|findLastChange
argument_list|(
name|object
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|ChangeLog
name|findLastChange
parameter_list|(
name|Object
name|object
parameter_list|,
name|Source
name|source
parameter_list|)
block|{
try|try
block|{
name|Number
name|objectUniqueId
init|=
operator|(
name|Number
operator|)
name|object
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getUniqueId"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
name|object
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
decl_stmt|;
name|String
name|objectType
init|=
name|object
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
return|return
name|findLastChange
argument_list|(
name|objectType
argument_list|,
name|objectUniqueId
argument_list|,
name|source
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|ChangeLog
name|findLastChange
parameter_list|(
name|String
name|objectType
parameter_list|,
name|Number
name|objectUniqueId
parameter_list|)
block|{
return|return
name|findLastChange
argument_list|(
name|objectType
argument_list|,
name|objectUniqueId
argument_list|,
literal|null
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|ChangeLog
name|findLastChange
parameter_list|(
name|String
name|objectType
parameter_list|,
name|Number
name|objectUniqueId
parameter_list|,
name|Source
name|source
parameter_list|)
block|{
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|ChangeLogDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select ch from ChangeLog ch "
operator|+
literal|"where ch.objectUniqueId=:objectUniqueId and ch.objectType=:objectType "
operator|+
operator|(
name|source
operator|==
literal|null
condition|?
literal|""
else|:
literal|"and ch.sourceString=:source "
operator|)
operator|+
literal|"order by ch.timeStamp desc"
argument_list|)
decl_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"objectUniqueId"
argument_list|,
name|objectUniqueId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setString
argument_list|(
literal|"objectType"
argument_list|,
name|objectType
argument_list|)
expr_stmt|;
if|if
condition|(
name|source
operator|!=
literal|null
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"source"
argument_list|,
name|source
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|List
name|logs
init|=
name|q
operator|.
name|list
argument_list|()
decl_stmt|;
return|return
operator|(
name|logs
operator|.
name|isEmpty
argument_list|()
condition|?
literal|null
else|:
operator|(
name|ChangeLog
operator|)
name|logs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|ChangeLog
name|findLastChange
parameter_list|(
name|String
name|objectType
parameter_list|,
name|Collection
name|objectUniqueIds
parameter_list|,
name|Source
name|source
parameter_list|)
block|{
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|ChangeLogDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|ChangeLog
name|changeLog
init|=
literal|null
decl_stmt|;
name|StringBuffer
name|ids
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|idx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|objectUniqueIds
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
name|idx
operator|++
control|)
block|{
name|ids
operator|.
name|append
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|idx
operator|++
expr_stmt|;
if|if
condition|(
name|idx
operator|==
literal|100
condition|)
block|{
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select ch from ChangeLog ch "
operator|+
literal|"where ch.objectUniqueId in ("
operator|+
name|ids
operator|+
literal|") and ch.objectType=:objectType "
operator|+
operator|(
name|source
operator|==
literal|null
condition|?
literal|""
else|:
literal|"and ch.sourceString=:source "
operator|)
operator|+
literal|"order by ch.timeStamp desc"
argument_list|)
decl_stmt|;
name|q
operator|.
name|setString
argument_list|(
literal|"objectType"
argument_list|,
name|objectType
argument_list|)
expr_stmt|;
if|if
condition|(
name|source
operator|!=
literal|null
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"source"
argument_list|,
name|source
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|List
name|logs
init|=
name|q
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|logs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ChangeLog
name|cl
init|=
operator|(
name|ChangeLog
operator|)
name|logs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|changeLog
operator|==
literal|null
operator|||
name|changeLog
operator|.
name|compareTo
argument_list|(
name|cl
argument_list|)
operator|>
literal|0
condition|)
name|changeLog
operator|=
name|cl
expr_stmt|;
block|}
name|ids
operator|=
operator|new
name|StringBuffer
argument_list|()
expr_stmt|;
name|idx
operator|=
literal|0
expr_stmt|;
block|}
if|else if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|ids
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|idx
operator|>
literal|0
condition|)
block|{
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select ch from ChangeLog ch "
operator|+
literal|"where ch.objectUniqueId in ("
operator|+
name|ids
operator|+
literal|") and ch.objectType=:objectType "
operator|+
operator|(
name|source
operator|==
literal|null
condition|?
literal|""
else|:
literal|"and ch.sourceString=:source "
operator|)
operator|+
literal|"order by ch.timeStamp desc"
argument_list|)
decl_stmt|;
name|q
operator|.
name|setString
argument_list|(
literal|"objectType"
argument_list|,
name|objectType
argument_list|)
expr_stmt|;
if|if
condition|(
name|source
operator|!=
literal|null
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"source"
argument_list|,
name|source
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|List
name|logs
init|=
name|q
operator|.
name|list
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|logs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ChangeLog
name|cl
init|=
operator|(
name|ChangeLog
operator|)
name|logs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|changeLog
operator|==
literal|null
operator|||
name|changeLog
operator|.
name|compareTo
argument_list|(
name|cl
argument_list|)
operator|>
literal|0
condition|)
name|changeLog
operator|=
name|cl
expr_stmt|;
block|}
block|}
return|return
name|changeLog
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|List
name|findLastNChanges
parameter_list|(
name|Object
name|object
parameter_list|,
name|int
name|n
parameter_list|)
block|{
return|return
name|findLastNChanges
argument_list|(
name|object
argument_list|,
literal|null
argument_list|,
name|n
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|List
name|findLastNChanges
parameter_list|(
name|Object
name|object
parameter_list|,
name|Source
name|source
parameter_list|,
name|int
name|n
parameter_list|)
block|{
try|try
block|{
name|Number
name|objectUniqueId
init|=
operator|(
name|Number
operator|)
name|object
operator|.
name|getClass
argument_list|()
operator|.
name|getMethod
argument_list|(
literal|"getUniqueId"
argument_list|,
operator|new
name|Class
index|[]
block|{}
argument_list|)
operator|.
name|invoke
argument_list|(
name|object
argument_list|,
operator|new
name|Object
index|[]
block|{}
argument_list|)
decl_stmt|;
name|String
name|objectType
init|=
name|object
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|ChangeLogDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select ch from ChangeLog ch "
operator|+
literal|"where ch.objectUniqueId=:objectUniqueId and ch.objectType=:objectType "
operator|+
operator|(
name|source
operator|==
literal|null
condition|?
literal|""
else|:
literal|"and ch.sourceString=:source "
operator|)
operator|+
literal|"order by ch.timeStamp desc"
argument_list|)
decl_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"objectUniqueId"
argument_list|,
name|objectUniqueId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setString
argument_list|(
literal|"objectType"
argument_list|,
name|objectType
argument_list|)
expr_stmt|;
if|if
condition|(
name|source
operator|!=
literal|null
condition|)
name|q
operator|.
name|setString
argument_list|(
literal|"source"
argument_list|,
name|source
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setMaxResults
argument_list|(
name|n
argument_list|)
expr_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|q
operator|.
name|list
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|List
name|findLastNChanges
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|managerId
parameter_list|,
name|Long
name|subjAreaId
parameter_list|,
name|Long
name|departmentId
parameter_list|,
name|int
name|n
parameter_list|)
block|{
try|try
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|ChangeLogDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Query
name|q
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select ch from ChangeLog ch where "
operator|+
literal|"ch.session.uniqueId=:sessionId "
operator|+
operator|(
name|managerId
operator|==
literal|null
condition|?
literal|""
else|:
literal|"and ch.manager.uniqueId=:managerId "
operator|)
operator|+
operator|(
name|subjAreaId
operator|==
literal|null
condition|?
literal|""
else|:
literal|"and ch.subjectArea.uniqueId=:subjAreaId "
operator|)
operator|+
operator|(
name|departmentId
operator|==
literal|null
condition|?
literal|""
else|:
literal|"and ch.department.uniqueId=:departmentId "
operator|)
operator|+
literal|"order by ch.timeStamp desc"
argument_list|)
decl_stmt|;
name|q
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|managerId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"managerId"
argument_list|,
name|managerId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|subjAreaId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"subjAreaId"
argument_list|,
name|subjAreaId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|departmentId
operator|!=
literal|null
condition|)
name|q
operator|.
name|setLong
argument_list|(
literal|"departmentId"
argument_list|,
name|departmentId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
name|q
operator|.
name|setMaxResults
argument_list|(
name|n
argument_list|)
expr_stmt|;
name|q
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|q
operator|.
name|list
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

