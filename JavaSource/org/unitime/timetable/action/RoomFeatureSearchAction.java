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
name|io
operator|.
name|OutputStream
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
name|RoomFeatureListForm
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
name|ExportUtils
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

begin_comment
comment|/**   * MyEclipse Struts  * Creation date: 06-27-2006  *   * XDoclet definition:  * @struts.action path="/roomFeatureSearch" name="roomFeatureListForm" input="/admin/roomFeatureSearch.jsp" scope="request"  * @struts.action-forward name="roomFeatureList" path="/roomFeatureList.do"  * @struts.action-forward name="showRoomFeatureSearch" path="roomFeatureSearchTile"  * @struts.action-forward name="showRoomFeatureList" path="roomFeatureListTile"  *  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/roomFeatureSearch"
argument_list|)
specifier|public
class|class
name|RoomFeatureSearchAction
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
name|RoomFeatureListForm
name|roomFeatureListForm
init|=
operator|(
name|RoomFeatureListForm
operator|)
name|form
decl_stmt|;
comment|//Check permissions
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|RoomFeatures
argument_list|)
expr_stmt|;
name|String
name|deptCode
init|=
name|roomFeatureListForm
operator|.
name|getDeptCodeX
argument_list|()
decl_stmt|;
if|if
condition|(
name|deptCode
operator|==
literal|null
condition|)
block|{
name|deptCode
operator|=
operator|(
name|String
operator|)
name|sessionContext
operator|.
name|getAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentCodeRoom
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|deptCode
operator|==
literal|null
condition|)
block|{
name|deptCode
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"default"
argument_list|)
expr_stmt|;
if|if
condition|(
name|deptCode
operator|!=
literal|null
condition|)
name|sessionContext
operator|.
name|setAttribute
argument_list|(
name|SessionAttribute
operator|.
name|DepartmentCodeRoom
argument_list|,
name|deptCode
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|deptCode
operator|!=
literal|null
operator|&&
operator|!
name|deptCode
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
literal|"All"
operator|.
name|equals
argument_list|(
name|deptCode
argument_list|)
operator|||
name|deptCode
operator|.
name|matches
argument_list|(
literal|"Exam[0-9]*"
argument_list|)
operator|||
name|sessionContext
operator|.
name|hasPermission
argument_list|(
name|deptCode
argument_list|,
literal|"Department"
argument_list|,
name|Right
operator|.
name|RoomFeatures
argument_list|)
operator|)
condition|)
block|{
name|roomFeatureListForm
operator|.
name|setDeptCodeX
argument_list|(
name|deptCode
argument_list|)
expr_stmt|;
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
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|RoomFeaturesExportPdf
argument_list|)
expr_stmt|;
name|OutputStream
name|out
init|=
name|ExportUtils
operator|.
name|getPdfOutputStream
argument_list|(
name|response
argument_list|,
literal|"roomFeatures"
argument_list|)
decl_stmt|;
name|RoomFeatureListAction
operator|.
name|printPdfFeatureTable
argument_list|(
name|out
argument_list|,
name|sessionContext
argument_list|,
name|roomFeatureListForm
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
literal|null
return|;
block|}
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"roomFeatureList"
argument_list|)
return|;
block|}
else|else
block|{
if|if
condition|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Department"
argument_list|)
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|roomFeatureListForm
operator|.
name|setDeptCodeX
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAuthority
argument_list|()
operator|.
name|getQualifiers
argument_list|(
literal|"Department"
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getQualifierReference
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"roomFeatureList"
argument_list|)
return|;
block|}
name|LookupTables
operator|.
name|setupDepartments
argument_list|(
name|request
argument_list|,
name|sessionContext
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupExamTypes
argument_list|(
name|request
argument_list|,
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showRoomFeatureSearch"
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

