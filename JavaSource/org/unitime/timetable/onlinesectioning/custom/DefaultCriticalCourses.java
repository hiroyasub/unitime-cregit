begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|onlinesectioning
operator|.
name|custom
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|AdvisorCourseRequest
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
name|CourseDemand
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
name|onlinesectioning
operator|.
name|OnlineSectioningHelper
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
name|onlinesectioning
operator|.
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
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
name|onlinesectioning
operator|.
name|OnlineSectioningServer
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XAdvisorRequest
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XCourseId
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XStudent
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
name|onlinesectioning
operator|.
name|model
operator|.
name|XStudentId
import|;
end_import

begin_class
specifier|public
class|class
name|DefaultCriticalCourses
implements|implements
name|CriticalCoursesProvider
block|{
annotation|@
name|Override
specifier|public
name|CriticalCourses
name|getCriticalCourses
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudentId
name|studentId
parameter_list|)
block|{
return|return
name|getCriticalCourses
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|studentId
argument_list|,
name|helper
operator|.
name|getAction
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|CriticalCourses
name|getCriticalCourses
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudentId
name|studentId
parameter_list|,
name|Builder
name|action
parameter_list|)
block|{
name|XStudent
name|student
init|=
operator|(
name|studentId
operator|instanceof
name|XStudent
condition|?
operator|(
name|XStudent
operator|)
name|studentId
else|:
name|server
operator|.
name|getStudent
argument_list|(
name|studentId
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|student
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|CourseDemand
operator|.
name|Critical
name|critical
init|=
name|CourseDemand
operator|.
name|Critical
operator|.
name|fromText
argument_list|(
name|ApplicationProperty
operator|.
name|AdvisorCourseRequestsAllowCritical
operator|.
name|valueOfSession
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|CriticalCoursesImpl
name|cc
init|=
operator|new
name|CriticalCoursesImpl
argument_list|(
name|critical
argument_list|)
decl_stmt|;
if|if
condition|(
name|student
operator|.
name|hasAdvisorRequests
argument_list|()
condition|)
block|{
for|for
control|(
name|XAdvisorRequest
name|ar
range|:
name|student
operator|.
name|getAdvisorRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|ar
operator|.
name|isCritical
argument_list|()
operator|&&
operator|!
name|ar
operator|.
name|isSubstitute
argument_list|()
operator|&&
name|ar
operator|.
name|hasCourseId
argument_list|()
operator|&&
name|ar
operator|.
name|getAlternative
argument_list|()
operator|==
literal|0
condition|)
block|{
name|cc
operator|.
name|addCritical
argument_list|(
name|ar
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XAdvisorRequest
name|alt
range|:
name|student
operator|.
name|getAdvisorRequests
argument_list|()
control|)
block|{
if|if
condition|(
name|alt
operator|.
name|getPriority
argument_list|()
operator|==
name|ar
operator|.
name|getPriority
argument_list|()
operator|&&
name|alt
operator|.
name|getAlternative
argument_list|()
operator|>
literal|0
operator|&&
operator|!
name|alt
operator|.
name|isSubstitute
argument_list|()
operator|&&
name|alt
operator|.
name|hasCourseId
argument_list|()
condition|)
block|{
name|cc
operator|.
name|addCritical
argument_list|(
name|alt
operator|.
name|getCourseId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
return|return
name|cc
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|dispose
parameter_list|()
block|{
block|}
specifier|protected
specifier|static
class|class
name|CriticalCoursesImpl
implements|implements
name|CriticalCourses
implements|,
name|CriticalCoursesProvider
operator|.
name|AdvisorCriticalCourses
block|{
specifier|private
name|CourseDemand
operator|.
name|Critical
name|iCritical
init|=
literal|null
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
name|iCriticalCourses
init|=
operator|new
name|HashMap
argument_list|<
name|Long
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|CriticalCoursesImpl
parameter_list|(
name|CourseDemand
operator|.
name|Critical
name|critical
parameter_list|)
block|{
name|iCritical
operator|=
name|critical
expr_stmt|;
block|}
specifier|public
name|boolean
name|addCritical
parameter_list|(
name|XCourseId
name|course
parameter_list|)
block|{
return|return
name|iCriticalCourses
operator|.
name|put
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|course
operator|.
name|getCourseName
argument_list|()
argument_list|)
operator|!=
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|iCriticalCourses
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|isCritical
parameter_list|(
name|CourseOffering
name|course
parameter_list|)
block|{
if|if
condition|(
name|iCriticalCourses
operator|.
name|containsKey
argument_list|(
name|course
operator|.
name|getUniqueId
argument_list|()
argument_list|)
condition|)
return|return
name|iCritical
operator|.
name|ordinal
argument_list|()
return|;
return|return
name|CourseDemand
operator|.
name|Critical
operator|.
name|NORMAL
operator|.
name|ordinal
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|isCritical
parameter_list|(
name|XCourseId
name|course
parameter_list|)
block|{
if|if
condition|(
name|iCriticalCourses
operator|.
name|containsKey
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
condition|)
return|return
name|iCritical
operator|.
name|ordinal
argument_list|()
return|;
return|return
name|CourseDemand
operator|.
name|Critical
operator|.
name|NORMAL
operator|.
name|ordinal
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|courses
init|=
operator|new
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|iCriticalCourses
operator|.
name|values
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|courses
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|isCritical
parameter_list|(
name|AdvisorCourseRequest
name|request
parameter_list|)
block|{
return|return
name|request
operator|.
name|getEffectiveCritical
argument_list|()
operator|.
name|ordinal
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|isCritical
parameter_list|(
name|XAdvisorRequest
name|request
parameter_list|)
block|{
return|return
name|request
operator|.
name|getCritical
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit
