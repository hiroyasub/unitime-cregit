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
name|specreg
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
name|Calendar
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
name|Locale
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
name|timetable
operator|.
name|gwt
operator|.
name|resources
operator|.
name|StudentSectioningMessages
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
name|shared
operator|.
name|SectioningException
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
name|shared
operator|.
name|CourseRequestInterface
operator|.
name|RequestedCourse
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
name|shared
operator|.
name|SpecialRegistrationInterface
operator|.
name|VariableTitleCourseRequest
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
name|shared
operator|.
name|SpecialRegistrationInterface
operator|.
name|VariableTitleCourseResponse
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
name|dao
operator|.
name|SessionDAO
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
name|OnlineSectioningAction
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
name|OnlineSectioningServer
operator|.
name|Lock
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
name|custom
operator|.
name|CustomSpecialRegistrationHolder
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
name|custom
operator|.
name|SpecialRegistrationProvider
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
name|match
operator|.
name|AnyCourseMatcher
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
name|XConfig
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
name|XCourse
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
name|XInstructor
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
name|XOffering
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
name|XSection
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
name|XSubpart
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
name|DateUtils
import|;
end_import

begin_class
specifier|public
class|class
name|SpecialRegistrationRequestVariableTitleCourse
implements|implements
name|OnlineSectioningAction
argument_list|<
name|VariableTitleCourseResponse
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|private
name|VariableTitleCourseRequest
name|iRequest
decl_stmt|;
specifier|private
specifier|static
name|StudentSectioningMessages
name|MSG
init|=
name|Localization
operator|.
name|create
argument_list|(
name|StudentSectioningMessages
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|SpecialRegistrationRequestVariableTitleCourse
name|withRequest
parameter_list|(
name|VariableTitleCourseRequest
name|request
parameter_list|)
block|{
name|iRequest
operator|=
name|request
expr_stmt|;
return|return
name|this
return|;
block|}
specifier|public
name|VariableTitleCourseRequest
name|getRequest
parameter_list|()
block|{
return|return
name|iRequest
return|;
block|}
specifier|public
name|Long
name|getStudentId
parameter_list|()
block|{
return|return
name|iRequest
operator|.
name|getStudentId
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|VariableTitleCourseResponse
name|execute
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|)
block|{
name|Lock
name|lock
init|=
name|server
operator|.
name|lockStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|,
literal|null
argument_list|,
name|name
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|OnlineSectioningLog
operator|.
name|Action
operator|.
name|Builder
name|action
init|=
name|helper
operator|.
name|getAction
argument_list|()
decl_stmt|;
name|Session
name|session
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|server
operator|.
name|getAcademicSession
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|,
name|helper
operator|.
name|getHibSession
argument_list|()
argument_list|)
decl_stmt|;
name|XStudent
name|student
init|=
name|server
operator|.
name|getStudent
argument_list|(
name|getStudentId
argument_list|()
argument_list|)
decl_stmt|;
name|action
operator|.
name|getStudentBuilder
argument_list|()
operator|.
name|setUniqueId
argument_list|(
name|student
operator|.
name|getStudentId
argument_list|()
argument_list|)
operator|.
name|setExternalId
argument_list|(
name|student
operator|.
name|getExternalId
argument_list|()
argument_list|)
operator|.
name|setName
argument_list|(
name|student
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"course"
argument_list|)
operator|.
name|setValue
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|)
expr_stmt|;
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
operator|+
literal|": "
operator|+
name|getRequest
argument_list|()
operator|.
name|getTitle
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
expr_stmt|;
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|hasTitle
argument_list|()
condition|)
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"title"
argument_list|)
operator|.
name|setValue
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getInstructor
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"instructor"
argument_list|)
operator|.
name|setValue
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getInstructor
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
literal|"Instructor: "
operator|+
name|getRequest
argument_list|()
operator|.
name|getInstructor
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|hasNote
argument_list|()
condition|)
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"note"
argument_list|)
operator|.
name|setValue
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getNote
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"credit"
argument_list|)
operator|.
name|setValue
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getCredit
argument_list|()
operator|>
name|getRequest
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getAvailableCredits
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
condition|)
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
literal|"Credit: "
operator|+
name|getRequest
argument_list|()
operator|.
name|getCredit
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|hasGradeMode
argument_list|()
condition|)
block|{
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"gradeMode"
argument_list|)
operator|.
name|setValue
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getGradeModeCode
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|getRequest
argument_list|()
operator|.
name|getGradeModeCode
argument_list|()
operator|.
name|equals
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getDefaultGradeModeCode
argument_list|()
argument_list|)
condition|)
name|action
operator|.
name|addMessageBuilder
argument_list|()
operator|.
name|setText
argument_list|(
literal|"Grade Mode: "
operator|+
name|getRequest
argument_list|()
operator|.
name|getGradeModeLabel
argument_list|()
argument_list|)
operator|.
name|setLevel
argument_list|(
name|OnlineSectioningLog
operator|.
name|Message
operator|.
name|Level
operator|.
name|INFO
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getStartDate
argument_list|()
operator|!=
literal|null
operator|&&
name|getRequest
argument_list|()
operator|.
name|getEndDate
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|SimpleDateFormat
name|df
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyy-MM-dd"
argument_list|)
decl_stmt|;
name|helper
operator|.
name|getAction
argument_list|()
operator|.
name|addOptionBuilder
argument_list|()
operator|.
name|setKey
argument_list|(
literal|"dates"
argument_list|)
operator|.
name|setValue
argument_list|(
name|df
operator|.
name|format
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getStartDate
argument_list|()
argument_list|)
operator|+
literal|" - "
operator|+
name|df
operator|.
name|format
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getEndDate
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|getRequest
argument_list|()
operator|.
name|getInstructor
argument_list|()
operator|!=
literal|null
operator|&&
name|getRequest
argument_list|()
operator|.
name|isCheckIfExists
argument_list|()
condition|)
block|{
for|for
control|(
name|XCourseId
name|courseId
range|:
name|server
operator|.
name|findCourses
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getCourse
argument_list|()
operator|.
name|getCourseName
argument_list|()
argument_list|,
literal|null
argument_list|,
operator|new
name|AnyCourseMatcher
argument_list|()
argument_list|)
control|)
block|{
name|XCourse
name|course
init|=
operator|(
name|courseId
operator|instanceof
name|XCourse
condition|?
operator|(
name|XCourse
operator|)
name|courseId
else|:
name|server
operator|.
name|getCourse
argument_list|(
name|courseId
operator|.
name|getCourseId
argument_list|()
argument_list|)
operator|)
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|getTitle
argument_list|()
operator|!=
literal|null
operator|&&
name|course
operator|.
name|getTitle
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getTitle
argument_list|()
argument_list|)
condition|)
block|{
name|XOffering
name|offering
init|=
name|server
operator|.
name|getOffering
argument_list|(
name|courseId
operator|.
name|getOfferingId
argument_list|()
argument_list|)
decl_stmt|;
name|RequestedCourse
name|rc
init|=
operator|new
name|RequestedCourse
argument_list|(
name|courseId
operator|.
name|getCourseId
argument_list|()
argument_list|,
name|courseId
operator|.
name|getCourseName
argument_list|()
argument_list|)
decl_stmt|;
name|rc
operator|.
name|setCourseTitle
argument_list|(
name|course
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|XConfig
name|config
range|:
name|offering
operator|.
name|getConfigs
argument_list|()
control|)
block|{
for|for
control|(
name|XSubpart
name|subpart
range|:
name|config
operator|.
name|getSubparts
argument_list|()
control|)
block|{
for|for
control|(
name|XSection
name|section
range|:
name|subpart
operator|.
name|getSections
argument_list|()
control|)
block|{
for|for
control|(
name|XInstructor
name|ins
range|:
name|section
operator|.
name|getAllInstructors
argument_list|()
control|)
if|if
condition|(
name|ins
operator|.
name|getIntructorId
argument_list|()
operator|.
name|equals
argument_list|(
name|getRequest
argument_list|()
operator|.
name|getInstructor
argument_list|()
operator|.
name|getId
argument_list|()
argument_list|)
operator|&&
name|getRequest
argument_list|()
operator|.
name|getStartDate
argument_list|()
operator|.
name|equals
argument_list|(
name|getFirstDate
argument_list|(
name|section
argument_list|,
name|session
argument_list|)
argument_list|)
operator|&&
name|getRequest
argument_list|()
operator|.
name|getEndDate
argument_list|()
operator|.
name|equals
argument_list|(
name|getLastDate
argument_list|(
name|section
argument_list|,
name|session
argument_list|)
argument_list|)
condition|)
block|{
name|rc
operator|.
name|setSelectedClass
argument_list|(
name|section
operator|.
name|getSectionId
argument_list|()
argument_list|,
name|section
operator|.
name|getName
argument_list|(
name|course
operator|.
name|getCourseId
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
if|if
condition|(
name|rc
operator|.
name|hasSelectedClasses
argument_list|()
condition|)
return|return
operator|new
name|VariableTitleCourseResponse
argument_list|(
name|rc
argument_list|)
return|;
block|}
block|}
block|}
name|SpecialRegistrationProvider
name|specReg
init|=
name|CustomSpecialRegistrationHolder
operator|.
name|getProvider
argument_list|()
decl_stmt|;
if|if
condition|(
name|specReg
operator|==
literal|null
condition|)
throw|throw
operator|new
name|SectioningException
argument_list|(
name|MSG
operator|.
name|exceptionNotSupportedFeature
argument_list|()
argument_list|)
throw|;
return|return
name|specReg
operator|.
name|requestVariableTitleCourse
argument_list|(
name|server
argument_list|,
name|helper
argument_list|,
name|student
argument_list|,
name|getRequest
argument_list|()
argument_list|)
return|;
block|}
finally|finally
block|{
name|lock
operator|.
name|release
argument_list|()
expr_stmt|;
block|}
block|}
specifier|protected
name|Date
name|getFirstDate
parameter_list|(
name|XSection
name|section
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
if|if
condition|(
name|section
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|cal
operator|.
name|setLenient
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Date
name|start
init|=
name|DateUtils
operator|.
name|getDate
argument_list|(
literal|1
argument_list|,
name|session
operator|.
name|getPatternStartMonth
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|start
argument_list|)
expr_stmt|;
name|int
name|idx
init|=
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getWeeks
argument_list|()
operator|.
name|nextSetBit
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|idx
argument_list|)
expr_stmt|;
return|return
name|cal
operator|.
name|getTime
argument_list|()
return|;
block|}
specifier|protected
name|Date
name|getLastDate
parameter_list|(
name|XSection
name|section
parameter_list|,
name|Session
name|session
parameter_list|)
block|{
if|if
condition|(
name|section
operator|.
name|getTime
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
name|Calendar
name|cal
init|=
name|Calendar
operator|.
name|getInstance
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
decl_stmt|;
name|cal
operator|.
name|setLenient
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|Date
name|last
init|=
name|DateUtils
operator|.
name|getDate
argument_list|(
literal|1
argument_list|,
name|session
operator|.
name|getPatternStartMonth
argument_list|()
argument_list|,
name|session
operator|.
name|getSessionStartYear
argument_list|()
argument_list|)
decl_stmt|;
name|cal
operator|.
name|setTime
argument_list|(
name|last
argument_list|)
expr_stmt|;
name|int
name|idx
init|=
name|section
operator|.
name|getTime
argument_list|()
operator|.
name|getWeeks
argument_list|()
operator|.
name|length
argument_list|()
operator|-
literal|1
decl_stmt|;
name|cal
operator|.
name|add
argument_list|(
name|Calendar
operator|.
name|DAY_OF_YEAR
argument_list|,
name|idx
argument_list|)
expr_stmt|;
return|return
name|cal
operator|.
name|getTime
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|name
parameter_list|()
block|{
return|return
literal|"req-var-course"
return|;
block|}
block|}
end_class

end_unit

