begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * UniTime 3.5 (University Timetabling Application)  * Copyright (C) 2014, UniTime LLC, and individual contributors  * as indicated by the @authors tag.  *   * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 3 of the License, or  * (at your option) any later version.  *   * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *   * You should have received a copy of the GNU General Public License along  * with this program.  If not, see<http://www.gnu.org/licenses/>.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|server
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|gwt
operator|.
name|client
operator|.
name|widgets
operator|.
name|CourseNumbersSuggestBox
operator|.
name|SuggestionInterface
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
name|gwt
operator|.
name|client
operator|.
name|widgets
operator|.
name|CourseNumbersSuggestBox
operator|.
name|SuggestionRpcRequest
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
name|gwt
operator|.
name|command
operator|.
name|client
operator|.
name|GwtRpcResponseList
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplementation
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
name|gwt
operator|.
name|command
operator|.
name|server
operator|.
name|GwtRpcImplements
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
name|CourseOffering
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
name|security
operator|.
name|SessionContext
import|;
end_import

begin_comment
comment|/**  * @author Tomas Muller  */
end_comment

begin_class
annotation|@
name|GwtRpcImplements
argument_list|(
name|SuggestionRpcRequest
operator|.
name|class
argument_list|)
specifier|public
class|class
name|CourseNumbersSuggestionsBackend
implements|implements
name|GwtRpcImplementation
argument_list|<
name|SuggestionRpcRequest
argument_list|,
name|GwtRpcResponseList
argument_list|<
name|SuggestionInterface
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
specifier|public
name|GwtRpcResponseList
argument_list|<
name|SuggestionInterface
argument_list|>
name|execute
parameter_list|(
name|SuggestionRpcRequest
name|request
parameter_list|,
name|SessionContext
name|context
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|params
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|p
range|:
name|request
operator|.
name|getConfiguration
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
control|)
block|{
if|if
condition|(
name|p
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
continue|continue;
name|String
name|name
init|=
name|p
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|p
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|p
operator|.
name|substring
argument_list|(
name|p
operator|.
name|indexOf
argument_list|(
literal|'='
argument_list|)
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|params
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|CourseOfferingDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|GwtRpcResponseList
argument_list|<
name|SuggestionInterface
argument_list|>
name|result
init|=
operator|new
name|GwtRpcResponseList
argument_list|<
name|SuggestionInterface
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|params
operator|.
name|containsKey
argument_list|(
literal|"subjectAbbv"
argument_list|)
condition|)
block|{
name|Long
name|sessionId
init|=
literal|null
decl_stmt|;
try|try
block|{
if|if
condition|(
name|params
operator|.
name|containsKey
argument_list|(
literal|"sessionId"
argument_list|)
condition|)
name|sessionId
operator|=
name|Long
operator|.
name|parseLong
argument_list|(
name|params
operator|.
name|get
argument_list|(
literal|"sessionId"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|sessionId
operator|==
literal|null
condition|)
name|sessionId
operator|=
operator|(
name|context
operator|.
name|isAuthenticated
argument_list|()
condition|?
name|context
operator|.
name|getUser
argument_list|()
operator|.
name|getCurrentAcademicSessionId
argument_list|()
else|:
literal|null
operator|)
expr_stmt|;
if|if
condition|(
name|sessionId
operator|!=
literal|null
condition|)
for|for
control|(
name|CourseOffering
name|co
range|:
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select co from CourseOffering co "
operator|+
literal|"where co.subjectArea.session.uniqueId = :sessionId and co.subjectArea.subjectAreaAbbreviation = :subjectAbbv "
operator|+
operator|(
literal|"include"
operator|.
name|equals
argument_list|(
name|params
operator|.
name|get
argument_list|(
literal|"notOffered"
argument_list|)
argument_list|)
condition|?
literal|""
else|:
literal|"and co.instructionalOffering.notOffered = false "
operator|)
operator|+
operator|(
literal|"exclude"
operator|.
name|equals
argument_list|(
name|params
operator|.
name|get
argument_list|(
literal|"crossListed"
argument_list|)
argument_list|)
condition|?
literal|"and co.isControl = true "
else|:
literal|""
operator|)
operator|+
literal|"and co.courseNbr like :q order by co.courseNbr"
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
literal|"subjectAbbv"
argument_list|,
name|params
operator|.
name|get
argument_list|(
literal|"subjectAbbv"
argument_list|)
argument_list|)
operator|.
name|setString
argument_list|(
literal|"q"
argument_list|,
name|request
operator|.
name|getQuery
argument_list|()
operator|+
literal|"%"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
name|request
operator|.
name|getLimit
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|SuggestionInterface
argument_list|(
name|co
operator|.
name|getCourseNumberWithTitle
argument_list|()
argument_list|,
name|co
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|params
operator|.
name|containsKey
argument_list|(
literal|"subjectId"
argument_list|)
condition|)
block|{
name|Long
name|subjectId
init|=
literal|null
decl_stmt|;
try|try
block|{
name|subjectId
operator|=
name|Long
operator|.
name|parseLong
argument_list|(
name|params
operator|.
name|get
argument_list|(
literal|"subjectId"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
block|}
if|if
condition|(
name|subjectId
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|CourseOffering
name|co
range|:
operator|(
name|List
argument_list|<
name|CourseOffering
argument_list|>
operator|)
name|hibSession
operator|.
name|createQuery
argument_list|(
literal|"select co from CourseOffering co "
operator|+
literal|"where co.subjectArea.uniqueId = :subjectId "
operator|+
operator|(
literal|"include"
operator|.
name|equals
argument_list|(
name|params
operator|.
name|get
argument_list|(
literal|"notOffered"
argument_list|)
argument_list|)
condition|?
literal|""
else|:
literal|"and co.instructionalOffering.notOffered = false "
operator|)
operator|+
operator|(
literal|"exclude"
operator|.
name|equals
argument_list|(
name|params
operator|.
name|get
argument_list|(
literal|"crossListed"
argument_list|)
argument_list|)
condition|?
literal|"and co.isControl = true "
else|:
literal|""
operator|)
operator|+
literal|"and co.courseNbr like :q order by co.courseNbr"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"subjectId"
argument_list|,
name|subjectId
argument_list|)
operator|.
name|setString
argument_list|(
literal|"q"
argument_list|,
name|request
operator|.
name|getQuery
argument_list|()
operator|+
literal|"%"
argument_list|)
operator|.
name|setCacheable
argument_list|(
literal|true
argument_list|)
operator|.
name|setMaxResults
argument_list|(
name|request
operator|.
name|getLimit
argument_list|()
argument_list|)
operator|.
name|list
argument_list|()
control|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|SuggestionInterface
argument_list|(
name|co
operator|.
name|getCourseNumberWithTitle
argument_list|()
argument_list|,
name|co
operator|.
name|getCourseNbr
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|result
return|;
comment|/*                  // Read form variables -- Classes Schedule Screen         if (map.get("session")!=null&& map.get("session") instanceof String&&             map.get("subjectArea")!=null&& map.get("subjectArea") instanceof String&&             map.get("courseNumber")!=null ) {                          StringBuffer query = new StringBuffer();             query.append("select distinct co.courseNbr ");             query.append("  from CourseOffering co ");             query.append(" where co.subjectArea.session.uniqueId = :acadSessionId ");             query.append("       and co.subjectArea.subjectAreaAbbreviation = :subjectArea");             query.append("       and co.courseNbr like :courseNbr ");             query.append(" order by co.courseNbr ");                  CourseOfferingDAO cdao = new CourseOfferingDAO();             Session hibSession = cdao.getSession();                  Query q = hibSession.createQuery(query.toString());             q.setFetchSize(5000);             q.setCacheable(true);             q.setFlushMode(FlushMode.MANUAL);             q.setLong("acadSessionId", Long.parseLong(map.get("session").toString()));             q.setString("subjectArea", map.get("subjectArea").toString());             q.setString("courseNbr", map.get("courseNumber").toString() + "%");                  result = q.list();                          if (result == null)                 result = new ArrayList();              return result;         }                  // Get Academic Session         Long acadSessionId = (sessionContext.isAuthenticated() ? sessionContext.getUser().getCurrentAcademicSessionId() : null);                  if (acadSessionId == null)         	return new ArrayList();          // Read form variables -- Instructional Offerings Screen, Reservations Screen         if(map.get("subjectAreaId")!=null&& map.get("courseNbr")!=null&& map.get("subjectAreaId").toString().length()>0&&          		!Constants.ALL_OPTION_VALUE.equals(map.get("subjectAreaId"))) {              	        StringBuffer query = new StringBuffer(); 	        query.append("select distinct co.courseNbr "); 	        query.append("  from CourseOffering co "); 	        query.append(" where co.subjectArea.session.uniqueId = :acadSessionId "); 	        query.append(" 		 and co.subjectArea.uniqueId = :subjectAreaId "); 	        query.append(" 		 and co.courseNbr like :courseNbr "); 	        //query.append(" 		 and co.isControl = true "); 	        query.append(" order by co.courseNbr "); 	 	        CourseOfferingDAO cdao = new CourseOfferingDAO(); 	        Session hibSession = cdao.getSession(); 	 	        Query q = hibSession.createQuery(query.toString()); 	        q.setFetchSize(5000); 	        q.setCacheable(true); 	        q.setFlushMode(FlushMode.MANUAL); 	        q.setLong("acadSessionId", acadSessionId); 	        q.setLong("subjectAreaId", Long.valueOf(map.get("subjectAreaId").toString())); 	        q.setString("courseNbr", map.get("courseNbr").toString() + "%"); 	 	        result = q.list();         }                  // Read form variables -- Distribution Preferences Screen         if(map.get("filterSubjectAreaId")!=null&& !Constants.BLANK_OPTION_VALUE.equals(map.get("filterSubjectAreaId"))&& !Constants.ALL_OPTION_VALUE.equals(map.get("filterSubjectAreaId"))&& map.get("filterCourseNbr")!=null ) {              	        StringBuffer query = new StringBuffer(); 	        query.append("select distinct co.courseNbr "); 	        query.append("  from CourseOffering co "); 	        query.append(" where co.subjectArea.session.uniqueId = :acadSessionId "); 	        query.append(" 		 and co.subjectArea.uniqueId = :subjectAreaId "); 	        query.append(" 		 and co.courseNbr like :courseNbr "); 	        query.append(" 		 and co.isControl = true "); 	        query.append(" 		 and co.instructionalOffering.notOffered = false "); 	        query.append(" order by co.courseNbr "); 	 	        CourseOfferingDAO cdao = new CourseOfferingDAO(); 	        Session hibSession = cdao.getSession(); 	 	        Query q = hibSession.createQuery(query.toString()); 	        q.setFetchSize(5000); 	        q.setCacheable(true); 	        q.setFlushMode(FlushMode.MANUAL); 	        q.setLong("acadSessionId", acadSessionId); 	        q.setLong("subjectAreaId", Long.valueOf(map.get("filterSubjectAreaId").toString())); 	        q.setString("courseNbr", map.get("filterCourseNbr").toString() + "%"); 	 	        result = q.list();         }                  // Read form variables -- Classes Screen         if(map.get("subjectAreaIds")!=null&& map.get("subjectAreaIds") instanceof String&& map.get("courseNbr")!=null ) {              	        StringBuffer query = new StringBuffer(); 	        query.append("select distinct co.courseNbr "); 	        query.append("  from CourseOffering co "); 	        query.append(" where co.subjectArea.session.uniqueId = :acadSessionId "); 	        query.append(" 		 and co.subjectArea.uniqueId = :subjectAreaId"); 	        query.append(" 		 and co.courseNbr like :courseNbr "); 	        if (!"include".equals(map.get("notOffered"))) 	        	query.append(" 		 and co.instructionalOffering.notOffered = false "); 	        //query.append(" 		 and co.isControl = true "); 	        query.append(" order by co.courseNbr "); 	 	        CourseOfferingDAO cdao = new CourseOfferingDAO(); 	        Session hibSession = cdao.getSession(); 	 	        Query q = hibSession.createQuery(query.toString()); 	        q.setFetchSize(5000); 	        q.setCacheable(true); 	        q.setFlushMode(FlushMode.MANUAL); 	        q.setLong("acadSessionId", acadSessionId); 	        q.setLong("subjectAreaId", Long.valueOf(map.get("subjectAreaIds").toString())); 	        q.setString("courseNbr", map.get("courseNbr").toString() + "%"); 	 	        result = q.list();         }                  if (result == null)             result = new ArrayList();          return result;         */
block|}
block|}
end_class

end_unit
