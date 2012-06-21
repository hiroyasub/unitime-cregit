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
name|util
operator|.
name|Collection
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
name|action
operator|.
name|ActionMessages
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|HibernateException
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
name|form
operator|.
name|ClassListForm
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
name|SubjectArea
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
name|SubjectAreaDAO
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
name|TimetableManagerDAO
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
name|solver
operator|.
name|WebSolver
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
name|util
operator|.
name|LookupTables
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
name|BackTracker
import|;
end_import

begin_comment
comment|/**  * @author Stephanie Schluttenhofer  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/classShowSearch"
argument_list|)
specifier|public
class|class
name|ClassShowSearchAction
extends|extends
name|Action
block|{
comment|/**  	 * Method execute 	 * @param mapping 	 * @param form 	 * @param request 	 * @param response 	 * @return ActionForward 	 * @throws HibernateException 	 */
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
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|//clear back list
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Web
operator|.
name|isLoggedIn
argument_list|(
name|httpSession
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Access Denied."
argument_list|)
throw|;
block|}
name|httpSession
operator|.
name|setAttribute
argument_list|(
literal|"callingPage"
argument_list|,
literal|"classShowSearch"
argument_list|)
expr_stmt|;
comment|// Check if subject area / course number saved to session
name|Object
name|sas
init|=
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|CRS_LST_SUBJ_AREA_IDS_ATTR_NAME
argument_list|)
decl_stmt|;
name|Object
name|cn
init|=
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|CRS_LST_CRS_NBR_ATTR_NAME
argument_list|)
decl_stmt|;
name|String
name|subjectAreaIds
init|=
literal|""
decl_stmt|;
name|String
name|courseNbr
init|=
literal|""
decl_stmt|;
if|if
condition|(
operator|(
name|sas
operator|==
literal|null
operator|||
name|sas
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
operator|&&
operator|(
name|cn
operator|==
literal|null
operator|||
name|cn
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
comment|// use session variables from io search
name|sas
operator|=
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SUBJ_AREA_ID_ATTR_NAME
argument_list|)
expr_stmt|;
name|cn
operator|=
name|httpSession
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|CRS_NBR_ATTR_NAME
argument_list|)
expr_stmt|;
block|}
name|User
name|user
init|=
name|Web
operator|.
name|getUser
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
name|LookupTables
operator|.
name|setupExternalDepts
argument_list|(
name|request
argument_list|,
operator|(
name|Long
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|SESSION_ID_ATTR_NAME
argument_list|)
argument_list|)
expr_stmt|;
name|ClassListForm
name|classListForm
init|=
operator|(
name|ClassListForm
operator|)
name|form
decl_stmt|;
name|ClassSearchAction
operator|.
name|setupBasicFormData
argument_list|(
name|classListForm
argument_list|,
name|user
argument_list|)
expr_stmt|;
name|ClassSearchAction
operator|.
name|setupGeneralFormFilters
argument_list|(
name|httpSession
argument_list|,
name|classListForm
argument_list|)
expr_stmt|;
name|ClassSearchAction
operator|.
name|setupClassListSpecificFormFilters
argument_list|(
name|httpSession
argument_list|,
name|classListForm
argument_list|)
expr_stmt|;
comment|/* 		if (request.getParameter("sortBy") != null) { 			classListForm.setDivSec(request.getParameter("divSec")==null?Boolean.FALSE:new Boolean(request.getParameter("divSec"))); 			classListForm.setLimit(request.getParameter("limit")==null?Boolean.FALSE:new Boolean(request.getParameter("limit"))); 			classListForm.setRoomLimit(request.getParameter("roomLimit")==null?Boolean.FALSE:new Boolean(request.getParameter("roomLimit")));	 			classListForm.setManager(request.getParameter("manager")==null?Boolean.FALSE:new Boolean(request.getParameter("manager"))); 			classListForm.setDatePattern(request.getParameter("datePattern")==null?Boolean.FALSE:new Boolean(request.getParameter("datePattern"))); 			classListForm.setTimePattern(request.getParameter("timePattern")==null?Boolean.FALSE:new Boolean(request.getParameter("timePattern")));	 			classListForm.setInstructor(request.getParameter("instructor")==null?Boolean.FALSE:new Boolean(request.getParameter("instructor"))); 			classListForm.setPreferences(request.getParameter("preferences")==null?Boolean.FALSE:new Boolean(request.getParameter("preferences")));	 			classListForm.setTimetable(request.getParameter("timetable")==null?Boolean.FALSE:new Boolean(request.getParameter("timetable"))); 			classListForm.setSchedulePrintNote(request.getParameter("schedulePrintNote")==null?Boolean.FALSE:new Boolean(request.getParameter("schedulePrintNote"))); 			classListForm.setSortBy((String)request.getParameter("sortBy")); 			classListForm.setFilterAssignedRoom((String)request.getParameter("filterAssignedRoom")); 			classListForm.setFilterInstructor((String)request.getParameter("filterInstructor")); 			classListForm.setFilterManager((String)request.getParameter("filterManager")); 			classListForm.setFilterIType((String)request.getParameter("filterIType")); 			classListForm.setFilterAssignedTimeMon(request.getParameter("filterAssignedTimeMon")==null?false:Boolean.getBoolean((String)request.getParameter("filterAssignedTimeMon"))); 			classListForm.setFilterAssignedTimeTue(request.getParameter("filterAssignedTimeTue")==null?false:Boolean.getBoolean((String)request.getParameter("filterAssignedTimeTue"))); 			classListForm.setFilterAssignedTimeWed(request.getParameter("filterAssignedTimeWed")==null?false:Boolean.getBoolean((String)request.getParameter("filterAssignedTimeWed"))); 			classListForm.setFilterAssignedTimeThu(request.getParameter("filterAssignedTimeThu")==null?false:Boolean.getBoolean((String)request.getParameter("filterAssignedTimeThu"))); 			classListForm.setFilterAssignedTimeFri(request.getParameter("filterAssignedTimeFri")==null?false:Boolean.getBoolean((String)request.getParameter("filterAssignedTimeFri"))); 			classListForm.setFilterAssignedTimeSat(request.getParameter("filterAssignedTimeSat")==null?false:Boolean.getBoolean((String)request.getParameter("filterAssignedTimeSat"))); 			classListForm.setFilterAssignedTimeSun(request.getParameter("filterAssignedTimeSun")==null?false:Boolean.getBoolean((String)request.getParameter("filterAssignedTimeSun"))); 			classListForm.setFilterAssignedTimeHour((String)request.getParameter("filterAssignedTimeHour")); 			classListForm.setFilterAssignedTimeMin((String)request.getParameter("filterAssignedTimeMin")); 			classListForm.setFilterAssignedTimeAmPm((String)request.getParameter("filterAssignedTimeAmPm")); 			classListForm.setFilterAssignedTimeLength((String)request.getParameter("filterAssignedTimeLength")); 			classListForm.setSortByKeepSubparts(Boolean.getBoolean((String)request.getParameter("sortByKeepSubparts"))); 		} 		*/
name|String
name|managerId
init|=
operator|(
name|String
operator|)
name|user
operator|.
name|getAttribute
argument_list|(
name|Constants
operator|.
name|TMTBL_MGR_ID_ATTR_NAME
argument_list|)
decl_stmt|;
name|TimetableManager
name|manager
init|=
operator|(
operator|new
name|TimetableManagerDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|managerId
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|manager
operator|==
literal|null
operator|||
operator|!
name|manager
operator|.
name|canSeeTimetable
argument_list|(
name|Session
operator|.
name|getCurrentAcadSession
argument_list|(
name|user
argument_list|)
argument_list|,
name|user
argument_list|)
condition|)
name|classListForm
operator|.
name|setTimetable
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setCollections
argument_list|(
name|request
argument_list|,
literal|null
argument_list|)
expr_stmt|;
if|if
condition|(
name|sas
operator|==
literal|null
operator|&&
name|classListForm
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
name|sas
operator|=
operator|(
operator|(
name|SubjectArea
operator|)
name|classListForm
operator|.
name|getSubjectAreas
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|Constants
operator|.
name|ALL_OPTION_VALUE
operator|.
name|equals
argument_list|(
name|sas
argument_list|)
condition|)
name|sas
operator|=
literal|null
expr_stmt|;
comment|// Subject Areas are saved to the session - Perform automatic search
if|if
condition|(
name|sas
operator|!=
literal|null
operator|&&
name|sas
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|subjectAreaIds
operator|=
name|sas
operator|.
name|toString
argument_list|()
expr_stmt|;
try|try
block|{
if|if
condition|(
name|cn
operator|!=
literal|null
operator|&&
name|cn
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|courseNbr
operator|=
name|cn
operator|.
name|toString
argument_list|()
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"Subject Areas: "
operator|+
name|subjectAreaIds
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"Course Number: "
operator|+
name|courseNbr
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setSubjectAreaIds
argument_list|(
name|subjectAreaIds
operator|.
name|split
argument_list|(
literal|","
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setCourseNbr
argument_list|(
name|courseNbr
argument_list|)
expr_stmt|;
name|StringBuffer
name|ids
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|names
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|StringBuffer
name|subjIds
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|classListForm
operator|.
name|setCollections
argument_list|(
name|request
argument_list|,
name|ClassSearchAction
operator|.
name|getClasses
argument_list|(
name|classListForm
argument_list|,
name|WebSolver
operator|.
name|getClassAssignmentProxy
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Collection
name|classes
init|=
name|classListForm
operator|.
name|getClasses
argument_list|()
decl_stmt|;
if|if
condition|(
name|classes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
name|errors
operator|.
name|add
argument_list|(
literal|"searchResult"
argument_list|,
operator|new
name|ActionMessage
argument_list|(
literal|"errors.generic"
argument_list|,
literal|"No records matching the search criteria were found."
argument_list|)
argument_list|)
expr_stmt|;
name|saveErrors
argument_list|(
name|request
argument_list|,
name|errors
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showClassSearch"
argument_list|)
return|;
block|}
else|else
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
name|classListForm
operator|.
name|getSubjectAreaIds
argument_list|()
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|>
literal|0
condition|)
block|{
name|names
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
name|subjIds
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
block|}
name|ids
operator|.
name|append
argument_list|(
literal|"&subjectAreaIds="
operator|+
name|classListForm
operator|.
name|getSubjectAreaIds
argument_list|()
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|subjIds
operator|.
name|append
argument_list|(
name|classListForm
operator|.
name|getSubjectAreaIds
argument_list|()
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|names
operator|.
name|append
argument_list|(
operator|(
operator|(
operator|new
name|SubjectAreaDAO
argument_list|()
operator|)
operator|.
name|get
argument_list|(
operator|new
name|Long
argument_list|(
name|classListForm
operator|.
name|getSubjectAreaIds
argument_list|()
index|[
name|i
index|]
argument_list|)
argument_list|)
operator|)
operator|.
name|getSubjectAreaAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"classSearch.do?doit=Search&loadFilter=1&"
operator|+
name|ids
operator|+
literal|"&courseNbr="
operator|+
name|classListForm
operator|.
name|getCourseNbr
argument_list|()
argument_list|,
literal|"Classes ("
operator|+
name|names
operator|+
operator|(
name|classListForm
operator|.
name|getCourseNbr
argument_list|()
operator|==
literal|null
operator|||
name|classListForm
operator|.
name|getCourseNbr
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|""
else|:
literal|" "
operator|+
name|classListForm
operator|.
name|getCourseNbr
argument_list|()
operator|)
operator|+
literal|")"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showClassList"
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|nfe
parameter_list|)
block|{
name|Debug
operator|.
name|error
argument_list|(
literal|"Subject Area Ids session attribute is corrupted. Resetting ... "
argument_list|)
expr_stmt|;
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_LST_SUBJ_AREA_IDS_ATTR_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|httpSession
operator|.
name|setAttribute
argument_list|(
name|Constants
operator|.
name|CRS_LST_CRS_NBR_ATTR_NAME
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showClassSearch"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

