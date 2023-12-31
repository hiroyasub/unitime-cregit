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
name|action
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
name|web
operator|.
name|WebTable
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
name|WebTable
operator|.
name|WebTableLine
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
name|DistributionType
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
name|PreferenceLevel
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 02-18-2005  *   * XDoclet definition:  * @struts:action path="/distributionTypeList" name="distributionTypeListForm" input="/admin/distributionTypeList.jsp" scope="request" validate="true"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/distributionTypeList"
argument_list|)
specifier|public
class|class
name|DistributionTypeListAction
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
name|HibernateException
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|DistributionTypes
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|DistributionType
argument_list|>
name|distTypes
init|=
operator|new
name|ArrayList
argument_list|<
name|DistributionType
argument_list|>
argument_list|()
decl_stmt|;
name|distTypes
operator|.
name|addAll
argument_list|(
name|DistributionType
operator|.
name|findAll
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|distTypes
operator|.
name|addAll
argument_list|(
name|DistributionType
operator|.
name|findAll
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|11
argument_list|,
literal|"Distribution Types"
argument_list|,
literal|"distributionTypeList.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Id"
block|,
literal|"Reference"
block|,
literal|"Abbreviation"
block|,
literal|"Name"
block|,
literal|"Type"
block|,
literal|"Visible"
block|,
literal|"Allow Instructor Preference"
block|,
literal|"Sequencing Required"
block|,
literal|"Allow Preferences"
block|,
literal|"Departments"
block|,
literal|"Description"
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
literal|"center"
block|,
literal|"center"
block|,
literal|"center"
block|,
literal|"center"
block|,
literal|"left"
block|,
literal|"left"
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
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|,
literal|true
block|}
argument_list|)
decl_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"DistributionTypeList.ord"
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
name|boolean
name|edit
init|=
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|Right
operator|.
name|DistributionTypeEdit
argument_list|)
decl_stmt|;
for|for
control|(
name|DistributionType
name|d
range|:
name|distTypes
control|)
block|{
name|String
name|allowPref
init|=
literal|null
decl_stmt|;
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|d
operator|.
name|getAllowedPref
argument_list|()
argument_list|)
condition|)
block|{
name|allowPref
operator|=
literal|"<i>None</i>"
expr_stmt|;
block|}
if|else if
condition|(
literal|"P43210R"
operator|.
name|equals
argument_list|(
name|d
operator|.
name|getAllowedPref
argument_list|()
argument_list|)
condition|)
block|{
name|allowPref
operator|=
literal|"<i>All</i>"
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|PreferenceLevel
name|p
range|:
name|PreferenceLevel
operator|.
name|getPreferenceLevelList
argument_list|()
control|)
block|{
if|if
condition|(
name|d
operator|.
name|getAllowedPref
argument_list|()
operator|.
name|indexOf
argument_list|(
name|PreferenceLevel
operator|.
name|prolog2char
argument_list|(
name|p
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
argument_list|)
operator|<
literal|0
condition|)
continue|continue;
if|if
condition|(
name|allowPref
operator|==
literal|null
condition|)
name|allowPref
operator|=
literal|""
expr_stmt|;
else|else
name|allowPref
operator|+=
literal|"<br>"
expr_stmt|;
if|if
condition|(
name|PreferenceLevel
operator|.
name|sNeutral
operator|.
name|equals
argument_list|(
name|p
operator|.
name|getPrefProlog
argument_list|()
argument_list|)
condition|)
name|allowPref
operator|+=
name|p
operator|.
name|getPrefName
argument_list|()
expr_stmt|;
else|else
name|allowPref
operator|+=
literal|"<span style='color:"
operator|+
name|p
operator|.
name|prefcolor
argument_list|()
operator|+
literal|";'>"
operator|+
name|p
operator|.
name|getPrefName
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"&nbsp;"
argument_list|)
operator|+
literal|"</span>"
expr_stmt|;
block|}
block|}
name|String
name|deptStr
init|=
literal|""
decl_stmt|;
name|String
name|deptCmp
init|=
literal|""
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|d
operator|.
name|getDepartments
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
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
name|Department
name|x
init|=
operator|(
name|Department
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|deptStr
operator|+=
name|x
operator|.
name|getManagingDeptAbbv
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
name|deptCmp
operator|+=
name|x
operator|.
name|getDeptCode
argument_list|()
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|deptStr
operator|+=
literal|", "
expr_stmt|;
name|deptCmp
operator|+=
literal|","
expr_stmt|;
block|}
block|}
name|WebTableLine
name|line
init|=
name|webTable
operator|.
name|addLine
argument_list|(
name|edit
condition|?
literal|"onClick=\"document.location='distributionTypeEdit.do?id="
operator|+
name|d
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
else|:
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|d
operator|.
name|getRequirementId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
name|d
operator|.
name|getReference
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
name|d
operator|.
name|getLabel
argument_list|()
block|,
name|d
operator|.
name|isExamPref
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"Examination"
else|:
literal|"Course"
block|,
name|d
operator|.
name|isVisible
argument_list|()
condition|?
literal|"Yes"
else|:
literal|"No"
block|,
name|d
operator|.
name|isExamPref
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"N/A"
else|:
name|d
operator|.
name|isInstructorPref
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|"Yes"
else|:
literal|"No"
block|,
name|d
operator|.
name|isSequencingRequired
argument_list|()
condition|?
literal|"Yes"
else|:
literal|"No"
block|,
name|allowPref
block|,
operator|(
name|deptStr
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|?
literal|"<i>All</i>"
else|:
name|deptStr
operator|)
block|,
name|d
operator|.
name|getDescr
argument_list|()
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|d
operator|.
name|getRequirementId
argument_list|()
block|,
name|d
operator|.
name|getReference
argument_list|()
block|,
name|d
operator|.
name|getAbbreviation
argument_list|()
block|,
name|d
operator|.
name|getLabel
argument_list|()
block|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|d
operator|.
name|isExamPref
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
literal|0
argument_list|)
block|,
name|d
operator|.
name|isVisible
argument_list|()
block|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|d
operator|.
name|isInstructorPref
argument_list|()
operator|.
name|booleanValue
argument_list|()
condition|?
literal|1
else|:
literal|0
argument_list|)
block|,
name|Integer
operator|.
name|valueOf
argument_list|(
name|d
operator|.
name|isSequencingRequired
argument_list|()
condition|?
literal|1
else|:
literal|0
argument_list|)
block|,
literal|null
block|,
name|deptCmp
block|,
name|d
operator|.
name|getDescr
argument_list|()
block|}
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|d
operator|.
name|isVisible
argument_list|()
condition|)
name|line
operator|.
name|setStyle
argument_list|(
literal|"color:gray;"
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"table"
argument_list|,
name|webTable
operator|.
name|printTable
argument_list|(
name|WebTable
operator|.
name|getOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"DistributionTypeList.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showDistributionTypeList"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

