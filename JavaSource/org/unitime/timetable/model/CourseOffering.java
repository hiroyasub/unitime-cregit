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
name|model
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
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|LazyInitializationException
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
name|hibernate
operator|.
name|Session
import|;
end_import

begin_import
import|import
name|org
operator|.
name|hibernate
operator|.
name|engine
operator|.
name|spi
operator|.
name|SessionImplementor
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
name|interfaces
operator|.
name|ExternalInstructionalOfferingAddAction
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
name|BaseCourseOffering
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
name|CourseOfferingDAO
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
name|InstructionalOfferingDAO
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
name|_RootDAO
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
name|ComboBoxLookup
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
name|InstrOfferingPermIdGenerator
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller, Stephanie Schluttenhofer, Heston Fernandes  */
end_comment

begin_class
specifier|public
class|class
name|CourseOffering
extends|extends
name|BaseCourseOffering
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
comment|/** Request attribute name for list of course offerings */
specifier|public
specifier|static
specifier|final
name|String
name|CRS_OFFERING_LIST_ATTR_NAME
init|=
literal|"crsOfferingList"
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|CourseOffering
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|CourseOffering
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
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|String
name|getCourseName
parameter_list|()
block|{
return|return
name|getSubjectAreaAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|getCourseNbr
argument_list|()
return|;
block|}
specifier|public
name|String
name|getCourseNameWithTitle
parameter_list|()
block|{
return|return
name|getSubjectAreaAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|getCourseNbr
argument_list|()
operator|+
operator|(
name|getTitle
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getTitle
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|" - "
operator|+
name|getTitle
argument_list|()
else|:
literal|""
operator|)
return|;
block|}
specifier|public
name|String
name|getCourseNumberWithTitle
parameter_list|()
block|{
return|return
operator|(
name|getCourseNbr
argument_list|()
operator|+
operator|(
name|getTitle
argument_list|()
operator|!=
literal|null
operator|&&
operator|!
name|getTitle
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
literal|" - "
operator|+
name|getTitle
argument_list|()
else|:
literal|""
operator|)
operator|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|getSubjectAreaAbbv
argument_list|()
operator|+
literal|" "
operator|+
name|this
operator|.
name|getCourseNbr
argument_list|()
operator|+
operator|(
operator|(
name|this
operator|.
name|getTitle
argument_list|()
operator|!=
literal|null
operator|)
condition|?
literal|" - "
operator|+
name|this
operator|.
name|getTitle
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|">"
argument_list|,
literal|"&gt;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"<"
argument_list|,
literal|"&lt;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"'"
argument_list|,
literal|"&quot;"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&"
argument_list|,
literal|"&amp;"
argument_list|)
else|:
literal|""
operator|)
operator|)
return|;
block|}
comment|/** 	 * Same as isIsContol. Added so that beans in JSPs can access getter method 	 * @return true/false 	 */
specifier|public
name|Boolean
name|getIsControl
parameter_list|()
block|{
return|return
name|this
operator|.
name|isIsControl
argument_list|()
return|;
block|}
comment|/** 	 * Search for a particular course offering 	 * @param acadSessionId Academic Session 	 * @param subjAreaId Subject Area Unique Id 	 * @param courseNbr Course Number 	 * @return List object with matching course offering 	 */
specifier|public
specifier|static
name|CourseOffering
name|findBySessionSubjAreaIdCourseNbr
parameter_list|(
name|Long
name|acadSessionId
parameter_list|,
name|Long
name|subjAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from CourseOffering co "
operator|+
literal|"where co.uniqueCourseNbr.subjectArea.uniqueId = :subjArea "
operator|+
literal|"and co.uniqueCourseNbr.courseNbr = :crsNbr "
operator|+
literal|"and co.instructionalOffering.session.uniqueId = :acadSessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"crsNbr"
argument_list|,
name|courseNbr
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjArea"
argument_list|,
name|subjAreaId
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"acadSessionId"
argument_list|,
name|acadSessionId
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findBySessionSubjAreaAbbvCourseNbr
parameter_list|(
name|Long
name|acadSessionId
parameter_list|,
name|String
name|subjAreaAbbv
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"from CourseOffering co "
operator|+
literal|"where co.uniqueCourseNbr.subjectArea.subjectAreaAbbreviation = :subjArea "
operator|+
literal|"and co.uniqueCourseNbr.courseNbr = :crsNbr "
operator|+
literal|"and co.instructionalOffering.session.uniqueId = :acadSessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"crsNbr"
argument_list|,
name|courseNbr
argument_list|)
operator|.
name|setString
argument_list|(
literal|"subjArea"
argument_list|,
name|subjAreaAbbv
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"acadSessionId"
argument_list|,
name|acadSessionId
argument_list|)
operator|.
name|setMaxResults
argument_list|(
literal|1
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findBySessionSubjAreaAbbvCourseNbrTitle
parameter_list|(
name|Long
name|acadSessionId
parameter_list|,
name|String
name|subjAreaAbbv
parameter_list|,
name|String
name|courseNbr
parameter_list|,
name|String
name|title
parameter_list|)
block|{
name|InstructionalOfferingDAO
name|iDao
init|=
operator|new
name|InstructionalOfferingDAO
argument_list|()
decl_stmt|;
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|iDao
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|String
name|sql
init|=
literal|" from CourseOffering co "
operator|+
literal|" where co.subjectArea.subjectAreaAbbreviation=:subjArea"
operator|+
literal|" and co.courseNbr = :crsNbr"
operator|+
literal|" and co.title = :title"
operator|+
literal|" and co.instructionalOffering.session.uniqueId = :acadSessionId"
decl_stmt|;
name|Query
name|query
init|=
name|hibSession
operator|.
name|createQuery
argument_list|(
name|sql
argument_list|)
decl_stmt|;
name|query
operator|.
name|setString
argument_list|(
literal|"crsNbr"
argument_list|,
name|courseNbr
argument_list|)
expr_stmt|;
name|query
operator|.
name|setString
argument_list|(
literal|"subjArea"
argument_list|,
name|subjAreaAbbv
argument_list|)
expr_stmt|;
name|query
operator|.
name|setLong
argument_list|(
literal|"acadSessionId"
argument_list|,
name|acadSessionId
operator|.
name|longValue
argument_list|()
argument_list|)
expr_stmt|;
name|query
operator|.
name|setString
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
return|return
operator|(
name|CourseOffering
operator|)
name|query
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
comment|/** 	 * Add a new course offering (instructional offering) to the database 	 * @param subjAreaId Subject Area Unique Id 	 * @param courseNbr Course Number 	 * @return CourseOffering object representing thenew course offering  	 * @throws Exception 	 */
specifier|public
specifier|static
specifier|synchronized
name|CourseOffering
name|addNew
parameter_list|(
name|Long
name|subjAreaId
parameter_list|,
name|String
name|courseNbr
parameter_list|)
throws|throws
name|Exception
block|{
name|CourseOffering
name|co
init|=
literal|null
decl_stmt|;
name|InstructionalOfferingDAO
name|idao
init|=
operator|new
name|InstructionalOfferingDAO
argument_list|()
decl_stmt|;
name|Session
name|hibSession
init|=
name|idao
operator|.
name|getSession
argument_list|()
decl_stmt|;
try|try
block|{
comment|/* 		    // Get Instr Offering Perm Id 		    String permId = ""; 		    String sql = "select timetable.instr_offr_permid_seq.nextval from dual"; 		    stmt = Database.execute(sql); 		    rs = stmt.getResultSet();   		    if(rs.next()) { 		        permId = rs.getString(1); 		    } 		    else { 		        throw new Exception("Could not retrieve instr offering perm id"); 		    }             */
comment|// Add new Course Offering
name|SubjectArea
name|subjArea
init|=
operator|new
name|SubjectAreaDAO
argument_list|()
operator|.
name|get
argument_list|(
name|subjAreaId
argument_list|)
decl_stmt|;
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|model
operator|.
name|Session
name|acadSession
init|=
name|subjArea
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|CourseOfferingDAO
name|cdao
init|=
operator|new
name|CourseOfferingDAO
argument_list|()
decl_stmt|;
name|co
operator|=
operator|new
name|CourseOffering
argument_list|()
expr_stmt|;
name|co
operator|.
name|setSubjectArea
argument_list|(
name|subjArea
argument_list|)
expr_stmt|;
name|co
operator|.
name|setCourseNbr
argument_list|(
name|courseNbr
argument_list|)
expr_stmt|;
name|co
operator|.
name|setProjectedDemand
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|co
operator|.
name|setDemand
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|co
operator|.
name|setNbrExpectedStudents
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|co
operator|.
name|setIsControl
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|co
operator|.
name|setPermId
argument_list|(
name|InstrOfferingPermIdGenerator
operator|.
name|getGenerator
argument_list|()
operator|.
name|generate
argument_list|(
operator|(
name|SessionImplementor
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
argument_list|,
name|co
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|HashSet
name|s
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
name|s
operator|.
name|add
argument_list|(
name|co
argument_list|)
expr_stmt|;
comment|// Add new Instructional Offering
name|InstructionalOffering
name|io
init|=
operator|new
name|InstructionalOffering
argument_list|()
decl_stmt|;
name|io
operator|.
name|setNotOffered
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|io
operator|.
name|setSession
argument_list|(
name|acadSession
argument_list|)
expr_stmt|;
name|io
operator|.
name|generateInstrOfferingPermId
argument_list|()
expr_stmt|;
name|io
operator|.
name|setLimit
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|io
operator|.
name|setByReservationOnly
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|idao
operator|.
name|saveOrUpdate
argument_list|(
name|io
argument_list|)
expr_stmt|;
name|idao
operator|.
name|getSession
argument_list|()
operator|.
name|refresh
argument_list|(
name|io
argument_list|)
expr_stmt|;
name|co
operator|.
name|setInstructionalOffering
argument_list|(
name|io
argument_list|)
expr_stmt|;
name|io
operator|.
name|addTocourseOfferings
argument_list|(
name|co
argument_list|)
expr_stmt|;
name|cdao
operator|.
name|saveOrUpdate
argument_list|(
name|co
argument_list|)
expr_stmt|;
name|cdao
operator|.
name|getSession
argument_list|()
operator|.
name|refresh
argument_list|(
name|co
argument_list|)
expr_stmt|;
name|cdao
operator|.
name|getSession
argument_list|()
operator|.
name|refresh
argument_list|(
name|subjArea
argument_list|)
expr_stmt|;
name|String
name|className
init|=
name|ApplicationProperty
operator|.
name|ExternalActionInstructionalOfferingAdd
operator|.
name|value
argument_list|()
decl_stmt|;
if|if
condition|(
name|className
operator|!=
literal|null
operator|&&
name|className
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
name|ExternalInstructionalOfferingAddAction
name|addAction
init|=
operator|(
name|ExternalInstructionalOfferingAddAction
operator|)
operator|(
name|Class
operator|.
name|forName
argument_list|(
name|className
argument_list|)
operator|.
name|newInstance
argument_list|()
operator|)
decl_stmt|;
name|addAction
operator|.
name|performExternalInstructionalOfferingAddAction
argument_list|(
name|io
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|//Database.closeConnObjs(stmt, rs);
name|Debug
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Could not create new course offering: "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
finally|finally
block|{
comment|//if (hibSession!=null&& hibSession.isOpen()) hibSession.close();
block|}
return|return
name|co
return|;
block|}
comment|/** 	 * Get a list of all controlling courses for the academic session 	 * @param sessionId Academic Session Unique Id 	 * @return Vector containing ComboBoxLookup objects 	 * @see ComboBoxLookup 	 */
specifier|public
specifier|static
name|Vector
name|getControllingCourses
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
name|Vector
name|l
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select co.uniqueId, co.subjectAreaAbbv, co.courseNbr from CourseOffering co where co.isControl=true and co.subjectArea.session.uniqueId=:sessionId"
argument_list|)
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
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
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
name|Object
index|[]
name|o
init|=
operator|(
name|Object
index|[]
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|l
operator|.
name|addElement
argument_list|(
operator|new
name|ComboBoxLookup
argument_list|(
name|o
index|[
literal|0
index|]
operator|.
name|toString
argument_list|()
argument_list|,
name|o
index|[
literal|1
index|]
operator|+
literal|" "
operator|+
name|o
index|[
literal|2
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|l
return|;
block|}
specifier|public
name|Department
name|getDepartment
parameter_list|()
block|{
name|Department
name|dept
init|=
literal|null
decl_stmt|;
try|try
block|{
name|dept
operator|=
name|this
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
expr_stmt|;
if|if
condition|(
name|dept
operator|.
name|toString
argument_list|()
operator|==
literal|null
condition|)
block|{
block|}
block|}
catch|catch
parameter_list|(
name|LazyInitializationException
name|lie
parameter_list|)
block|{
operator|new
name|_RootDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|refresh
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|dept
operator|=
name|this
operator|.
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
expr_stmt|;
block|}
return|return
operator|(
name|dept
operator|)
return|;
block|}
specifier|public
name|List
name|getCourseOfferingDemands
parameter_list|()
block|{
if|if
condition|(
name|getPermId
argument_list|()
operator|!=
literal|null
condition|)
return|return
operator|(
operator|new
name|CourseOfferingDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select d from LastLikeCourseDemand d where d.coursePermId=:permId and d.subjectArea.session.uniqueId=:sessionId"
argument_list|)
operator|.
name|setString
argument_list|(
literal|"permId"
argument_list|,
name|getPermId
argument_list|()
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|getSubjectArea
argument_list|()
operator|.
name|getSessionId
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
else|else
return|return
operator|(
operator|new
name|CourseOfferingDAO
argument_list|()
operator|)
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select d from LastLikeCourseDemand d where d.subjectArea.uniqueId=:subjectAreaId and d.courseNbr=:courseNbr"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectAreaId"
argument_list|,
name|getSubjectArea
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|getCourseNbr
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|list
argument_list|()
return|;
block|}
comment|//TODO: to distinguish between last like semester student demands and all student demands in the future
specifier|public
name|List
name|getLastLikeSemesterCourseOfferingDemands
parameter_list|()
block|{
return|return
name|getCourseOfferingDemands
argument_list|()
return|;
block|}
comment|/**      * Clones the course Offering      * Note: It does not set the Instructional Offering      */
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|CourseOffering
name|co
init|=
operator|new
name|CourseOffering
argument_list|()
decl_stmt|;
name|co
operator|.
name|setCourseNbr
argument_list|(
name|this
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setDemand
argument_list|(
name|this
operator|.
name|getDemand
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setPermId
argument_list|(
name|this
operator|.
name|getPermId
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setNbrExpectedStudents
argument_list|(
name|this
operator|.
name|getNbrExpectedStudents
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setProjectedDemand
argument_list|(
name|this
operator|.
name|getProjectedDemand
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setSubjectArea
argument_list|(
name|this
operator|.
name|getSubjectArea
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setSubjectAreaAbbv
argument_list|(
name|this
operator|.
name|getSubjectAreaAbbv
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setTitle
argument_list|(
name|this
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setDemandOffering
argument_list|(
name|this
operator|.
name|getDemandOffering
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setDemandOfferingType
argument_list|(
name|this
operator|.
name|getDemandOfferingType
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setExternalUniqueId
argument_list|(
name|this
operator|.
name|getExternalUniqueId
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setScheduleBookNote
argument_list|(
name|this
operator|.
name|getScheduleBookNote
argument_list|()
argument_list|)
expr_stmt|;
name|co
operator|.
name|setIsControl
argument_list|(
name|this
operator|.
name|getIsControl
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|co
return|;
block|}
comment|//End
specifier|public
specifier|static
name|List
name|findAll
parameter_list|(
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where c.subjectArea.session.uniqueId=:sessionId"
argument_list|)
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
operator|.
name|list
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findBySubjectAreaCourseNbr
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|subjectAreaAbbv
parameter_list|,
name|String
name|courseNbr
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where "
operator|+
literal|"c.subjectArea.session.uniqueId=:sessionId and "
operator|+
literal|"c.subjectArea.subjectAreaAbbreviation=:subjectAreaAbbv and "
operator|+
literal|"c.courseNbr=:courseNbr"
argument_list|)
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
operator|.
name|setString
argument_list|(
literal|"subjectAreaAbbv"
argument_list|,
name|subjectAreaAbbv
argument_list|)
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findByExternalId
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|String
name|externalId
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where c.subjectArea.session.uniqueId=:sessionId and c.externalUniqueId=:externalId"
argument_list|)
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
operator|.
name|setString
argument_list|(
literal|"externalId"
argument_list|,
name|externalId
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findByUniqueId
parameter_list|(
name|Long
name|uniqueId
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|get
argument_list|(
name|uniqueId
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findBySubjectCourseNbrInstrOffUniqueId
parameter_list|(
name|String
name|subjectAreaAbbv
parameter_list|,
name|String
name|courseNbr
parameter_list|,
name|Long
name|instrOffrUniqueId
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from InstructionalOffering io inner join io.courseOfferings c where "
operator|+
literal|"io.uniqueId=:instrOffrUniqueId and "
operator|+
literal|"c.subjectArea.subjectAreaAbbreviation=:subjectAreaAbbv and "
operator|+
literal|"c.courseNbr=:courseNbr"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"instrOffrUniqueId"
argument_list|,
name|instrOffrUniqueId
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setString
argument_list|(
literal|"subjectAreaAbbv"
argument_list|,
name|subjectAreaAbbv
argument_list|)
operator|.
name|setString
argument_list|(
literal|"courseNbr"
argument_list|,
name|courseNbr
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findByName
parameter_list|(
name|String
name|name
parameter_list|,
name|Long
name|sessionId
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where c.subjectArea.session.uniqueId = :sessionId and lower(c.subjectArea.subjectAreaAbbreviation || ' ' || c.courseNbr) = :name"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessionId"
argument_list|,
name|sessionId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"name"
argument_list|,
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|CourseOffering
name|findByIdRolledForwardFrom
parameter_list|(
name|Long
name|sessionId
parameter_list|,
name|Long
name|uniqueIdRolledForwardFrom
parameter_list|)
block|{
return|return
operator|(
name|CourseOffering
operator|)
operator|new
name|CourseOfferingDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select c from CourseOffering c where c.subjectArea.session.uniqueId=:sessionId and c.uniqueIdRolledForwardFrom=:uniqueIdRolledForwardFrom"
argument_list|)
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
operator|.
name|setLong
argument_list|(
literal|"uniqueIdRolledForwardFrom"
argument_list|,
name|uniqueIdRolledForwardFrom
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|uniqueResult
argument_list|()
return|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
operator|||
operator|!
operator|(
name|o
operator|instanceof
name|CourseOffering
operator|)
condition|)
return|return
operator|-
literal|1
return|;
name|CourseOffering
name|co
init|=
operator|(
name|CourseOffering
operator|)
name|o
decl_stmt|;
name|int
name|cmp
init|=
name|getSubjectAreaAbbv
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co
operator|.
name|getSubjectAreaAbbv
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
name|cmp
operator|=
name|getCourseNbr
argument_list|()
operator|.
name|compareTo
argument_list|(
name|co
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
expr_stmt|;
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
operator|(
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|new
name|Long
argument_list|(
operator|-
literal|1
argument_list|)
else|:
name|getUniqueId
argument_list|()
operator|)
operator|.
name|compareTo
argument_list|(
name|co
operator|.
name|getUniqueId
argument_list|()
operator|==
literal|null
condition|?
operator|-
literal|1
else|:
name|co
operator|.
name|getUniqueId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|CourseCreditUnitConfig
name|getCredit
parameter_list|()
block|{
if|if
condition|(
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|==
literal|null
operator|||
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
return|return
operator|(
literal|null
operator|)
return|;
block|}
else|else
block|{
return|return
operator|(
operator|(
name|CourseCreditUnitConfig
operator|)
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|)
return|;
block|}
block|}
specifier|public
name|void
name|setCredit
parameter_list|(
name|CourseCreditUnitConfig
name|courseCreditUnitConfig
parameter_list|)
block|{
if|if
condition|(
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|==
literal|null
operator|||
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|this
operator|.
name|addTocreditConfigs
argument_list|(
name|courseCreditUnitConfig
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
operator|!
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|.
name|contains
argument_list|(
name|courseCreditUnitConfig
argument_list|)
condition|)
block|{
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|this
operator|.
name|getCreditConfigs
argument_list|()
operator|.
name|add
argument_list|(
name|courseCreditUnitConfig
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//course already contains this config so we do not need to add it again.
block|}
block|}
specifier|public
name|boolean
name|isAllowStudentScheduling
parameter_list|()
block|{
return|return
name|getSubjectArea
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|isAllowStudentScheduling
argument_list|()
return|;
block|}
specifier|public
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|getClassEnrollments
parameter_list|(
name|Student
name|s
parameter_list|)
block|{
name|List
argument_list|<
name|StudentClassEnrollment
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|StudentClassEnrollment
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|StudentClassEnrollment
name|e
range|:
name|s
operator|.
name|getClassEnrollments
argument_list|()
control|)
block|{
if|if
condition|(
name|this
operator|.
name|equals
argument_list|(
name|e
operator|.
name|getCourseOffering
argument_list|()
argument_list|)
condition|)
name|ret
operator|.
name|add
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

