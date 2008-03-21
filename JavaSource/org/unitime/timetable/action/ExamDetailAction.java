begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|TreeSet
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
name|ActionMessages
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
name|Transaction
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
name|form
operator|.
name|ExamEditForm
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
name|ChangeLog
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
name|Class_
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
name|DepartmentalInstructor
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
name|DistributionPref
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
name|Exam
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
name|ExamOwner
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
name|InstrOfferingConfig
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
name|InstructionalOffering
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
name|ExamDAO
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
name|solver
operator|.
name|exam
operator|.
name|ExamAssignmentProxy
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
name|ui
operator|.
name|ExamAssignmentInfo
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
name|ExamDistributionPrefsTableBuilder
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
name|Navigation
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
name|RequiredTimeTable
import|;
end_import

begin_class
specifier|public
class|class
name|ExamDetailAction
extends|extends
name|PreferencesAction
block|{
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
name|ExamEditForm
name|frm
init|=
operator|(
name|ExamEditForm
operator|)
name|form
decl_stmt|;
try|try
block|{
comment|// Set common lookup tables
name|super
operator|.
name|execute
argument_list|(
name|mapping
argument_list|,
name|form
argument_list|,
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
name|HttpSession
name|httpSession
init|=
name|request
operator|.
name|getSession
argument_list|()
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
name|Long
name|sessionId
init|=
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
decl_stmt|;
name|MessageResources
name|rsc
init|=
name|getResources
argument_list|(
name|request
argument_list|)
decl_stmt|;
name|ActionMessages
name|errors
init|=
operator|new
name|ActionMessages
argument_list|()
decl_stmt|;
comment|//Read parameters
name|String
name|examId
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"examId"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"examId"
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
name|examId
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"examId"
argument_list|)
expr_stmt|;
if|if
condition|(
name|examId
operator|==
literal|null
operator|&&
name|request
operator|.
name|getAttribute
argument_list|(
literal|"examId"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getAttribute
argument_list|(
literal|"examId"
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|!=
literal|0
condition|)
name|examId
operator|=
name|request
operator|.
name|getAttribute
argument_list|(
literal|"examId"
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
name|String
name|op
init|=
name|frm
operator|.
name|getOp
argument_list|()
decl_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|op
operator|=
name|request
operator|.
name|getParameter
argument_list|(
literal|"op2"
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getAttribute
argument_list|(
literal|"fromChildScreen"
argument_list|)
operator|!=
literal|null
operator|&&
name|request
operator|.
name|getAttribute
argument_list|(
literal|"fromChildScreen"
argument_list|)
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"true"
argument_list|)
condition|)
block|{
name|op
operator|=
literal|""
expr_stmt|;
name|frm
operator|.
name|setOp
argument_list|(
name|op
argument_list|)
expr_stmt|;
block|}
comment|// Read exam id from form
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.editExam"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addDistPref"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.nextExam"
argument_list|)
argument_list|)
operator|||
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.previousExam"
argument_list|)
argument_list|)
condition|)
block|{
name|examId
operator|=
name|frm
operator|.
name|getExamId
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|frm
operator|.
name|reset
argument_list|(
name|mapping
argument_list|,
name|request
argument_list|)
expr_stmt|;
block|}
comment|//Check op exists
if|if
condition|(
name|op
operator|==
literal|null
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Null Operation not supported."
argument_list|)
throw|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"op: "
operator|+
name|op
argument_list|)
expr_stmt|;
name|Debug
operator|.
name|debug
argument_list|(
literal|"exam: "
operator|+
name|examId
argument_list|)
expr_stmt|;
comment|//Check exam exists
if|if
condition|(
name|examId
operator|==
literal|null
operator|||
name|examId
operator|.
name|trim
argument_list|()
operator|==
literal|""
condition|)
throw|throw
operator|new
name|Exception
argument_list|(
literal|"Exam Info not supplied."
argument_list|)
throw|;
name|Exam
name|exam
init|=
operator|new
name|ExamDAO
argument_list|()
operator|.
name|get
argument_list|(
name|Long
operator|.
name|valueOf
argument_list|(
name|examId
argument_list|)
argument_list|)
decl_stmt|;
comment|//After delete -> one more back
if|if
condition|(
name|exam
operator|==
literal|null
operator|&&
name|BackTracker
operator|.
name|hasBack
argument_list|(
name|request
argument_list|,
literal|1
argument_list|)
condition|)
block|{
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backType"
argument_list|)
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"backType"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"backType"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
literal|"backId"
argument_list|,
name|request
operator|.
name|getParameter
argument_list|(
literal|"backId"
argument_list|)
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|doBack
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
comment|//Edit Information - Redirect to info edit screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.editExam"
argument_list|)
argument_list|)
operator|&&
name|examId
operator|!=
literal|null
operator|&&
name|examId
operator|.
name|trim
argument_list|()
operator|!=
literal|""
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"examEdit.do?examId="
operator|+
name|examId
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.nextExam"
argument_list|)
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"examDetail.do?examId="
operator|+
name|frm
operator|.
name|getNextId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.previousExam"
argument_list|)
argument_list|)
condition|)
block|{
name|response
operator|.
name|sendRedirect
argument_list|(
name|response
operator|.
name|encodeURL
argument_list|(
literal|"examDetail.do?examId="
operator|+
name|frm
operator|.
name|getPreviousId
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.deleteExam"
argument_list|)
argument_list|)
condition|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
operator|new
name|ExamDAO
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|tx
init|=
literal|null
decl_stmt|;
try|try
block|{
name|tx
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|ChangeLog
operator|.
name|addChange
argument_list|(
name|hibSession
argument_list|,
name|request
argument_list|,
name|exam
argument_list|,
name|ChangeLog
operator|.
name|Source
operator|.
name|EXAM_EDIT
argument_list|,
name|ChangeLog
operator|.
name|Operation
operator|.
name|DELETE
argument_list|,
name|exam
operator|.
name|firstSubjectArea
argument_list|()
argument_list|,
name|exam
operator|.
name|firstDepartment
argument_list|()
argument_list|)
expr_stmt|;
name|exam
operator|.
name|deleteDependentObjects
argument_list|(
name|hibSession
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|delete
argument_list|(
name|exam
argument_list|)
expr_stmt|;
name|tx
operator|.
name|commit
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
if|if
condition|(
name|tx
operator|!=
literal|null
condition|)
name|tx
operator|.
name|rollback
argument_list|()
expr_stmt|;
throw|throw
name|e
throw|;
block|}
if|if
condition|(
name|BackTracker
operator|.
name|hasBack
argument_list|(
name|request
argument_list|,
literal|1
argument_list|)
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"backType"
argument_list|,
literal|"Exam"
argument_list|)
expr_stmt|;
name|request
operator|.
name|setAttribute
argument_list|(
literal|"backId"
argument_list|,
literal|"-1"
argument_list|)
expr_stmt|;
name|BackTracker
operator|.
name|doBack
argument_list|(
name|request
argument_list|,
name|response
argument_list|)
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
literal|"showList"
argument_list|)
return|;
block|}
comment|// Add Distribution Preference - Redirect to dist prefs screen
if|if
condition|(
name|op
operator|.
name|equals
argument_list|(
name|rsc
operator|.
name|getMessage
argument_list|(
literal|"button.addDistPref"
argument_list|)
argument_list|)
condition|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
literal|"examId"
argument_list|,
name|examId
argument_list|)
expr_stmt|;
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"addDistributionPrefs"
argument_list|)
return|;
block|}
comment|// Load form attributes that are constant
name|doLoad
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|exam
argument_list|)
expr_stmt|;
comment|// Display distribution Prefs
name|ExamDistributionPrefsTableBuilder
name|tbl
init|=
operator|new
name|ExamDistributionPrefsTableBuilder
argument_list|()
decl_stmt|;
name|String
name|html
init|=
name|tbl
operator|.
name|getDistPrefsTable
argument_list|(
name|request
argument_list|,
name|exam
argument_list|)
decl_stmt|;
if|if
condition|(
name|html
operator|!=
literal|null
condition|)
name|request
operator|.
name|setAttribute
argument_list|(
name|DistributionPref
operator|.
name|DIST_PREF_REQUEST_ATTR
argument_list|,
name|html
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|exam
operator|.
name|getOwners
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|WebTable
name|table
init|=
operator|new
name|WebTable
argument_list|(
literal|5
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"Object"
block|,
literal|"Type"
block|,
literal|"Manager"
block|,
literal|"Students"
block|,
literal|"Assignment"
block|}
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"left"
block|,
literal|"center"
block|,
literal|"left"
block|,
literal|"center"
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
block|}
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
operator|new
name|TreeSet
argument_list|(
name|exam
operator|.
name|getOwners
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
name|ExamOwner
name|owner
init|=
operator|(
name|ExamOwner
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|onclick
init|=
literal|null
decl_stmt|,
name|name
init|=
literal|null
decl_stmt|,
name|type
init|=
literal|null
decl_stmt|,
name|students
init|=
name|String
operator|.
name|valueOf
argument_list|(
name|owner
operator|.
name|countStudents
argument_list|()
argument_list|)
decl_stmt|,
name|manager
init|=
literal|null
decl_stmt|,
name|assignment
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|owner
operator|.
name|getOwnerType
argument_list|()
condition|)
block|{
case|case
name|ExamOwner
operator|.
name|sOwnerTypeClass
case|:
name|Class_
name|clazz
init|=
operator|(
name|Class_
operator|)
name|owner
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|clazz
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='classDetail.do?cid="
operator|+
name|clazz
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
name|name
operator|=
name|clazz
operator|.
name|getClassLabel
argument_list|()
expr_stmt|;
name|type
operator|=
literal|"Class"
expr_stmt|;
name|manager
operator|=
name|clazz
operator|.
name|getManagingDept
argument_list|()
operator|.
name|getShortLabel
argument_list|()
expr_stmt|;
if|if
condition|(
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
operator|!=
literal|null
condition|)
name|assignment
operator|=
name|clazz
operator|.
name|getCommittedAssignment
argument_list|()
operator|.
name|getPlacement
argument_list|()
operator|.
name|getLongName
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeConfig
case|:
name|InstrOfferingConfig
name|config
init|=
operator|(
name|InstrOfferingConfig
operator|)
name|owner
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|config
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='instructionalOfferingDetail.do?io="
operator|+
name|config
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
empty_stmt|;
name|name
operator|=
name|config
operator|.
name|getCourseName
argument_list|()
operator|+
literal|" ["
operator|+
name|config
operator|.
name|getName
argument_list|()
operator|+
literal|"]"
expr_stmt|;
name|type
operator|=
literal|"Configuration"
expr_stmt|;
name|manager
operator|=
name|config
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|getShortLabel
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeOffering
case|:
name|InstructionalOffering
name|offering
init|=
operator|(
name|InstructionalOffering
operator|)
name|owner
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|offering
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='instructionalOfferingDetail.do?io="
operator|+
name|offering
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
empty_stmt|;
name|name
operator|=
name|offering
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
name|type
operator|=
literal|"Offering"
expr_stmt|;
name|manager
operator|=
name|offering
operator|.
name|getControllingCourseOffering
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|getShortLabel
argument_list|()
expr_stmt|;
break|break;
case|case
name|ExamOwner
operator|.
name|sOwnerTypeCourse
case|:
name|CourseOffering
name|course
init|=
operator|(
name|CourseOffering
operator|)
name|owner
operator|.
name|getOwnerObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|course
operator|.
name|isViewableBy
argument_list|(
name|user
argument_list|)
condition|)
name|onclick
operator|=
literal|"onClick=\"document.location='instructionalOfferingDetail.do?io="
operator|+
name|course
operator|.
name|getInstructionalOffering
argument_list|()
operator|.
name|getUniqueId
argument_list|()
operator|+
literal|"';\""
expr_stmt|;
empty_stmt|;
name|name
operator|=
name|course
operator|.
name|getCourseName
argument_list|()
expr_stmt|;
name|type
operator|=
literal|"Course"
expr_stmt|;
name|manager
operator|=
name|course
operator|.
name|getDepartment
argument_list|()
operator|.
name|getShortLabel
argument_list|()
expr_stmt|;
break|break;
block|}
name|table
operator|.
name|addLine
argument_list|(
name|onclick
argument_list|,
operator|new
name|String
index|[]
block|{
name|name
block|,
name|type
block|,
name|manager
block|,
name|students
block|,
name|assignment
block|}
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ExamDetail.table"
argument_list|,
name|table
operator|.
name|printTable
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|ExamAssignmentInfo
name|ea
init|=
literal|null
decl_stmt|;
name|ExamAssignmentProxy
name|examAssignment
init|=
name|WebSolver
operator|.
name|getExamSolver
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|examAssignment
operator|!=
literal|null
condition|)
block|{
name|ea
operator|=
name|examAssignment
operator|.
name|getAssignmentInfo
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|else if
condition|(
name|exam
operator|.
name|getAssignedPeriod
argument_list|()
operator|!=
literal|null
condition|)
name|ea
operator|=
operator|new
name|ExamAssignmentInfo
argument_list|(
name|exam
argument_list|)
expr_stmt|;
if|if
condition|(
name|ea
operator|!=
literal|null
condition|)
block|{
name|String
name|assignment
init|=
literal|"<tr><td>Examination Period:</td><td>"
operator|+
name|ea
operator|.
name|getPeriodNameWithPref
argument_list|()
operator|+
literal|"</td></tr>"
decl_stmt|;
if|if
condition|(
operator|!
name|ea
operator|.
name|getRooms
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|assignment
operator|+=
literal|"<tr><td>Room"
operator|+
operator|(
name|ea
operator|.
name|getRooms
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|?
literal|"s"
else|:
literal|""
operator|)
operator|+
literal|":</td><td>"
expr_stmt|;
name|assignment
operator|+=
name|ea
operator|.
name|getRoomsNameWithPref
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
name|assignment
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
if|if
condition|(
name|ea
operator|.
name|getNrDistributionConflicts
argument_list|()
operator|>
literal|0
condition|)
block|{
name|assignment
operator|+=
literal|"<tr><td>Violated Distributions:</td><td>"
expr_stmt|;
name|assignment
operator|+=
name|ea
operator|.
name|getDistributionConflictTable
argument_list|()
expr_stmt|;
name|assignment
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
if|if
condition|(
name|ea
operator|.
name|getHasConflicts
argument_list|()
condition|)
block|{
name|assignment
operator|+=
literal|"<tr><td>Student Conflicts</td><td>"
expr_stmt|;
name|assignment
operator|+=
name|ea
operator|.
name|getConflictTable
argument_list|()
expr_stmt|;
name|assignment
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
if|if
condition|(
name|ea
operator|.
name|getHasInstructorConflicts
argument_list|()
condition|)
block|{
name|assignment
operator|+=
literal|"<tr><td>Instructor Conflicts</td><td>"
expr_stmt|;
name|assignment
operator|+=
name|ea
operator|.
name|getInstructorConflictTable
argument_list|()
expr_stmt|;
name|assignment
operator|+=
literal|"</td></tr>"
expr_stmt|;
block|}
name|request
operator|.
name|setAttribute
argument_list|(
literal|"ExamDetail.assignment"
argument_list|,
name|assignment
argument_list|)
expr_stmt|;
block|}
name|BackTracker
operator|.
name|markForBack
argument_list|(
name|request
argument_list|,
literal|"examDetail.do?examId="
operator|+
name|examId
argument_list|,
literal|"Exam ("
operator|+
operator|(
name|frm
operator|.
name|getName
argument_list|()
operator|==
literal|null
condition|?
name|frm
operator|.
name|getLabel
argument_list|()
operator|.
name|trim
argument_list|()
else|:
name|frm
operator|.
name|getName
argument_list|()
operator|.
name|trim
argument_list|()
operator|)
operator|+
literal|")"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// Initialize Preferences for initial load
name|frm
operator|.
name|setAvailableTimePatterns
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|initPrefs
argument_list|(
name|user
argument_list|,
name|frm
argument_list|,
name|exam
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|boolean
name|timeVertical
init|=
name|RequiredTimeTable
operator|.
name|getTimeGridVertical
argument_list|(
name|Web
operator|.
name|getUser
argument_list|(
name|httpSession
argument_list|)
argument_list|)
decl_stmt|;
name|generateExamPeriodGrid
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|exam
argument_list|,
literal|"init"
argument_list|,
name|timeVertical
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// Process Preferences Action
name|processPrefAction
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|errors
argument_list|)
expr_stmt|;
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|frm
argument_list|,
name|exam
argument_list|)
expr_stmt|;
name|LookupTables
operator|.
name|setupRooms
argument_list|(
name|request
argument_list|,
name|exam
argument_list|)
expr_stmt|;
comment|// Room Prefs
name|LookupTables
operator|.
name|setupBldgs
argument_list|(
name|request
argument_list|,
name|exam
argument_list|)
expr_stmt|;
comment|// Building Prefs
name|LookupTables
operator|.
name|setupRoomFeatures
argument_list|(
name|request
argument_list|,
name|exam
argument_list|)
expr_stmt|;
comment|// Preference Levels
name|LookupTables
operator|.
name|setupRoomGroups
argument_list|(
name|request
argument_list|,
name|exam
argument_list|)
expr_stmt|;
comment|// Room Groups
return|return
name|mapping
operator|.
name|findForward
argument_list|(
literal|"showExamDetail"
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
throw|throw
name|e
throw|;
block|}
block|}
specifier|protected
name|void
name|doLoad
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ExamEditForm
name|frm
parameter_list|,
name|Exam
name|exam
parameter_list|)
block|{
name|frm
operator|.
name|setExamId
argument_list|(
name|exam
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setLabel
argument_list|(
name|exam
operator|.
name|getLabel
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setName
argument_list|(
name|exam
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setNote
argument_list|(
name|exam
operator|.
name|getNote
argument_list|()
operator|==
literal|null
condition|?
literal|null
else|:
name|exam
operator|.
name|getNote
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"<br>"
argument_list|)
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setLength
argument_list|(
name|exam
operator|.
name|getLength
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setSeatingType
argument_list|(
name|Exam
operator|.
name|sSeatingTypes
index|[
name|exam
operator|.
name|getSeatingType
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setMaxNbrRooms
argument_list|(
name|exam
operator|.
name|getMaxNbrRooms
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setExamType
argument_list|(
name|exam
operator|.
name|getExamType
argument_list|()
argument_list|)
expr_stmt|;
name|TreeSet
name|instructors
init|=
operator|new
name|TreeSet
argument_list|(
name|exam
operator|.
name|getInstructors
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|instructors
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
name|DepartmentalInstructor
name|instr
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|frm
operator|.
name|getInstructors
argument_list|()
operator|.
name|add
argument_list|(
name|instr
operator|.
name|getUniqueId
argument_list|()
operator|.
name|toString
argument_list|()
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
name|frm
operator|.
name|setEditable
argument_list|(
name|exam
operator|.
name|isEditableBy
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
name|Long
name|nextId
init|=
name|Navigation
operator|.
name|getNext
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Navigation
operator|.
name|sInstructionalOfferingLevel
argument_list|,
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|Long
name|prevId
init|=
name|Navigation
operator|.
name|getPrevious
argument_list|(
name|request
operator|.
name|getSession
argument_list|()
argument_list|,
name|Navigation
operator|.
name|sInstructionalOfferingLevel
argument_list|,
name|exam
operator|.
name|getUniqueId
argument_list|()
argument_list|)
decl_stmt|;
name|frm
operator|.
name|setPreviousId
argument_list|(
name|prevId
operator|==
literal|null
condition|?
literal|null
else|:
name|prevId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|frm
operator|.
name|setNextId
argument_list|(
name|nextId
operator|==
literal|null
condition|?
literal|null
else|:
name|nextId
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|setupInstructors
parameter_list|(
name|HttpServletRequest
name|request
parameter_list|,
name|ExamEditForm
name|frm
parameter_list|,
name|Exam
name|exam
parameter_list|)
throws|throws
name|Exception
block|{
name|List
name|instructors
init|=
name|frm
operator|.
name|getInstructors
argument_list|()
decl_stmt|;
if|if
condition|(
name|instructors
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
return|return;
name|HashSet
name|deptIds
init|=
operator|new
name|HashSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|i
init|=
name|exam
operator|.
name|getInstructors
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
name|DepartmentalInstructor
name|instr
init|=
operator|(
name|DepartmentalInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|deptIds
operator|.
name|add
argument_list|(
name|instr
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Iterator
name|i
init|=
name|exam
operator|.
name|getOwners
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
name|ExamOwner
name|own
init|=
operator|(
name|ExamOwner
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|deptIds
operator|.
name|add
argument_list|(
name|own
operator|.
name|getCourse
argument_list|()
operator|.
name|getDepartment
argument_list|()
operator|.
name|getUniqueId
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Long
index|[]
name|deptsIdsArray
init|=
operator|new
name|Long
index|[
name|deptIds
operator|.
name|size
argument_list|()
index|]
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
name|deptIds
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
name|deptsIdsArray
index|[
name|idx
operator|++
index|]
operator|=
operator|(
name|Long
operator|)
name|i
operator|.
name|next
argument_list|()
expr_stmt|;
name|LookupTables
operator|.
name|setupInstructors
argument_list|(
name|request
argument_list|,
name|deptsIdsArray
argument_list|)
expr_stmt|;
name|Vector
name|deptInstrList
init|=
operator|(
name|Vector
operator|)
name|request
operator|.
name|getAttribute
argument_list|(
name|DepartmentalInstructor
operator|.
name|INSTR_LIST_ATTR_NAME
argument_list|)
decl_stmt|;
comment|// For each instructor set the instructor list
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|instructors
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|request
operator|.
name|setAttribute
argument_list|(
name|DepartmentalInstructor
operator|.
name|INSTR_LIST_ATTR_NAME
operator|+
name|i
argument_list|,
name|deptInstrList
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

