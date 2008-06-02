begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
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
name|Set
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
name|BaseClassEvent
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
name|RelatedCourseInfoDAO
import|;
end_import

begin_class
specifier|public
class|class
name|ClassEvent
extends|extends
name|BaseClassEvent
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/*[CONSTRUCTOR MARKER BEGIN]*/
specifier|public
name|ClassEvent
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Constructor for primary key 	 */
specifier|public
name|ClassEvent
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
comment|/** 	 * Constructor for required fields 	 */
specifier|public
name|ClassEvent
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Long
name|uniqueId
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|minCapacity
parameter_list|,
name|java
operator|.
name|lang
operator|.
name|Integer
name|maxCapacity
parameter_list|)
block|{
name|super
argument_list|(
name|uniqueId
argument_list|,
name|minCapacity
argument_list|,
name|maxCapacity
argument_list|)
expr_stmt|;
block|}
comment|/*[CONSTRUCTOR MARKER END]*/
specifier|public
name|Set
argument_list|<
name|Student
argument_list|>
name|getStudents
parameter_list|()
block|{
name|HashSet
argument_list|<
name|Student
argument_list|>
name|students
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
name|getClazz
argument_list|()
operator|.
name|getStudentEnrollments
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
name|students
operator|.
name|add
argument_list|(
operator|(
operator|(
name|StudentClassEnrollment
operator|)
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|getStudent
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|students
return|;
block|}
specifier|public
name|List
name|getStudentIds
parameter_list|()
block|{
return|return
operator|new
name|RelatedCourseInfoDAO
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|createQuery
argument_list|(
literal|"select distinct e.student.uniqueId from StudentClassEnrollment e where e.clazz.uniqueId = :classId"
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"classId"
argument_list|,
name|getClazz
argument_list|()
operator|.
name|getUniqueId
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
specifier|public
name|Set
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|getInstructors
parameter_list|()
block|{
name|HashSet
argument_list|<
name|DepartmentalInstructor
argument_list|>
name|instructors
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
name|getClazz
argument_list|()
operator|.
name|getClassInstructors
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
name|ClassInstructor
name|ci
init|=
operator|(
name|ClassInstructor
operator|)
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|ci
operator|.
name|isLead
argument_list|()
condition|)
name|instructors
operator|.
name|add
argument_list|(
name|ci
operator|.
name|getInstructor
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|instructors
return|;
block|}
specifier|public
name|int
name|getEventType
parameter_list|()
block|{
return|return
name|sEventTypeClass
return|;
block|}
specifier|public
name|Session
name|getSession
parameter_list|()
block|{
return|return
name|getClazz
argument_list|()
operator|.
name|getSession
argument_list|()
return|;
block|}
block|}
end_class

end_unit

