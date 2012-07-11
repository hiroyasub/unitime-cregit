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
name|localization
operator|.
name|impl
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|unitime
operator|.
name|localization
operator|.
name|messages
operator|.
name|CourseMessages
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
name|SessionAttribute
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
name|ClassAssignmentsReportForm
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
name|Department
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
name|solver
operator|.
name|ClassAssignmentProxy
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
name|exam
operator|.
name|ExamSolverProxy
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
name|service
operator|.
name|AssignmentService
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
name|service
operator|.
name|SolverService
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
literal|"/classAssignmentsReportShowSearch"
argument_list|)
specifier|public
class|class
name|ClassAssignmentsReportShowSearchAction
extends|extends
name|Action
block|{
specifier|protected
specifier|final
specifier|static
name|CourseMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|CourseMessages
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Autowired
name|SessionContext
name|sessionContext
decl_stmt|;
annotation|@
name|Autowired
name|AssignmentService
argument_list|<
name|ClassAssignmentProxy
argument_list|>
name|classAssignmentService
decl_stmt|;
annotation|@
name|Autowired
name|SolverService
argument_list|<
name|ExamSolverProxy
argument_list|>
name|examinationSolverService
decl_stmt|;
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|ClassAssignments
argument_list|)
expr_stmt|;
name|ClassAssignmentsReportForm
name|classListForm
init|=
operator|(
name|ClassAssignmentsReportForm
operator|)
name|form
decl_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
name|Department
operator|.
name|EXTERNAL_DEPT_ATTR_NAME
argument_list|,
name|Department
operator|.
name|findAllExternal
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|ClassAssignmentsReportSearchAction
operator|.
name|setupGeneralFormFilters
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|,
name|classListForm
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"sortBy"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|classListForm
operator|.
name|setSortBy
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"sortBy"
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedRoom
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedRoom"
argument_list|)
argument_list|)
expr_stmt|;
comment|//classListForm.setFilterInstructor((String)request.getParameter("filterInstructor"));
name|classListForm
operator|.
name|setFilterManager
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterManager"
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterIType
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterIType"
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeMon
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeMon"
argument_list|)
operator|==
literal|null
condition|?
literal|false
else|:
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeMon"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeTue
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeTue"
argument_list|)
operator|==
literal|null
condition|?
literal|false
else|:
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeTue"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeWed
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeWed"
argument_list|)
operator|==
literal|null
condition|?
literal|false
else|:
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeWed"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeThu
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeThu"
argument_list|)
operator|==
literal|null
condition|?
literal|false
else|:
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeThu"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeFri
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeFri"
argument_list|)
operator|==
literal|null
condition|?
literal|false
else|:
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeFri"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeSat
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeSat"
argument_list|)
operator|==
literal|null
condition|?
literal|false
else|:
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeSat"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeSun
argument_list|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeSun"
argument_list|)
operator|==
literal|null
condition|?
literal|false
else|:
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeSun"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeHour
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeHour"
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeMin
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeMin"
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeAmPm
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeAmPm"
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setFilterAssignedTimeLength
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"filterAssignedTimeLength"
argument_list|)
argument_list|)
expr_stmt|;
name|classListForm
operator|.
name|setSortByKeepSubparts
argument_list|(
name|Boolean
operator|.
name|getBoolean
argument_list|(
operator|(
name|String
operator|)
name|request
operator|.
name|getParameter
argument_list|(
literal|"sortByKeepSubparts"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|classListForm
operator|.
name|setSubjectAreas
argument_list|(
name|SubjectArea
operator|.
name|getUserSubjectAreas
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Object
name|sas
init|=
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ClassAssignmentsSubjectAreas
argument_list|)
decl_stmt|;
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
name|String
name|subjectAreaIds
init|=
name|sas
operator|.
name|toString
argument_list|()
decl_stmt|;
try|try
block|{
name|Debug
operator|.
name|debug
argument_list|(
literal|"Subject Areas: "
operator|+
name|subjectAreaIds
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
name|setClasses
argument_list|(
name|ClassSearchAction
operator|.
name|getClasses
argument_list|(
name|classListForm
argument_list|,
name|classAssignmentService
operator|.
name|getAssignment
argument_list|()
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
literal|"showClassAssignmentsReportSearch"
argument_list|)
return|;
block|}
else|else
block|{
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
name|names
operator|.
name|append
argument_list|(
literal|","
argument_list|)
expr_stmt|;
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
literal|"classAssignmentsReportSearch.do?doit=Search&loadFilter=1"
operator|+
name|ids
argument_list|,
literal|"Class Assignments ("
operator|+
name|names
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
literal|"showClassAssignmentsReportList"
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
name|sessionContext
operator|.
name|removeAttribute
argument_list|(
name|SessionAttribute
operator|.
name|ClassAssignmentsSubjectAreas
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showClassAssignmentsReportSearch"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

