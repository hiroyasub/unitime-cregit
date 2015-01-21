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
name|text
operator|.
name|DecimalFormat
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
name|timetable
operator|.
name|defaults
operator|.
name|ApplicationProperty
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
name|Building
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
name|cpsolver
operator|.
name|ifs
operator|.
name|util
operator|.
name|DistanceMetric
import|;
end_import

begin_comment
comment|/**  * MyEclipse Struts * Creation date: 02-18-2005 *  * XDoclet definition: * @struts:action path="/BuildingList" name="buildingListForm" input="/admin/buildingList.jsp" scope="request" validate="true" */
end_comment

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|Service
argument_list|(
literal|"/buildingList"
argument_list|)
specifier|public
class|class
name|BuildingListAction
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
name|Exception
block|{
name|sessionContext
operator|.
name|checkPermission
argument_list|(
name|Right
operator|.
name|BuildingList
argument_list|)
expr_stmt|;
name|DistanceMetric
operator|.
name|Ellipsoid
name|ellipsoid
init|=
name|DistanceMetric
operator|.
name|Ellipsoid
operator|.
name|valueOf
argument_list|(
name|ApplicationProperty
operator|.
name|DistanceEllipsoid
operator|.
name|value
argument_list|()
argument_list|)
decl_stmt|;
name|WebTable
name|webTable
init|=
operator|new
name|WebTable
argument_list|(
literal|5
argument_list|,
literal|null
argument_list|,
literal|"buildingList.do?ord=%%"
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Abbreviation"
block|,
literal|"Name"
block|,
literal|"External ID"
block|,
name|ellipsoid
operator|.
name|getFirstCoordinateName
argument_list|()
block|,
name|ellipsoid
operator|.
name|getSecondCoordinateName
argument_list|()
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
literal|"right"
block|,
literal|"right"
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
block|}
argument_list|)
decl_stmt|;
name|WebTable
operator|.
name|setOrder
argument_list|(
name|sessionContext
argument_list|,
literal|"BuildingList.ord"
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
name|DecimalFormat
name|df5
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"####0.######"
argument_list|)
decl_stmt|;
for|for
control|(
name|Building
name|b
range|:
name|Building
operator|.
name|findAll
argument_list|(
name|sessionContext
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
argument_list|)
control|)
block|{
name|webTable
operator|.
name|addLine
argument_list|(
literal|"onClick=\"document.location='buildingEdit.do?op=Edit&id="
operator|+
name|b
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
argument_list|,
operator|new
name|String
index|[]
block|{
name|b
operator|.
name|getAbbreviation
argument_list|()
block|,
name|b
operator|.
name|getName
argument_list|()
block|,
name|b
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
condition|?
literal|"<i>N/A</i>"
else|:
name|b
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
operator|(
name|b
operator|.
name|getCoordinateX
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|df5
operator|.
name|format
argument_list|(
name|b
operator|.
name|getCoordinateX
argument_list|()
argument_list|)
operator|)
block|,
operator|(
name|b
operator|.
name|getCoordinateY
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|df5
operator|.
name|format
argument_list|(
name|b
operator|.
name|getCoordinateY
argument_list|()
argument_list|)
operator|)
block|}
argument_list|,
operator|new
name|Comparable
index|[]
block|{
name|b
operator|.
name|getAbbreviation
argument_list|()
block|,
name|b
operator|.
name|getName
argument_list|()
block|,
name|b
operator|.
name|getExternalUniqueId
argument_list|()
operator|==
literal|null
condition|?
literal|""
else|:
name|b
operator|.
name|getExternalUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
block|,
name|b
operator|.
name|getCoordinateX
argument_list|()
block|,
name|b
operator|.
name|getCoordinateY
argument_list|()
block|}
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
literal|"BuildingList.ord"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showBuildingList"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

