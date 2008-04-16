begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|solver
operator|.
name|exam
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
name|Set
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|cpsolver
operator|.
name|exam
operator|.
name|model
operator|.
name|ExamPeriod
import|;
end_import

begin_class
specifier|public
class|class
name|ExamResourceUnavailability
block|{
specifier|protected
name|ExamPeriod
name|iPeriod
decl_stmt|;
specifier|protected
name|Long
name|iId
decl_stmt|;
specifier|protected
name|String
name|iType
decl_stmt|;
specifier|protected
name|String
name|iName
decl_stmt|;
specifier|protected
name|String
name|iDate
decl_stmt|;
specifier|protected
name|String
name|iTime
decl_stmt|;
specifier|protected
name|String
name|iRoom
decl_stmt|;
specifier|protected
name|int
name|iSize
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|Long
argument_list|>
name|iStudentIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|protected
name|Set
argument_list|<
name|Long
argument_list|>
name|iInstructorIds
init|=
operator|new
name|HashSet
argument_list|<
name|Long
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
name|ExamResourceUnavailability
parameter_list|(
name|ExamPeriod
name|period
parameter_list|,
name|Long
name|id
parameter_list|,
name|String
name|type
parameter_list|,
name|String
name|name
parameter_list|,
name|String
name|date
parameter_list|,
name|String
name|time
parameter_list|,
name|String
name|room
parameter_list|,
name|int
name|size
parameter_list|)
block|{
name|iPeriod
operator|=
name|period
expr_stmt|;
name|iId
operator|=
name|id
expr_stmt|;
name|iType
operator|=
name|type
expr_stmt|;
name|iName
operator|=
name|name
expr_stmt|;
name|iDate
operator|=
name|date
expr_stmt|;
name|iTime
operator|=
name|time
expr_stmt|;
name|iRoom
operator|=
name|room
expr_stmt|;
name|iSize
operator|=
name|size
expr_stmt|;
block|}
specifier|public
name|ExamPeriod
name|getPeriod
parameter_list|()
block|{
return|return
name|iPeriod
return|;
block|}
specifier|public
name|Long
name|getId
parameter_list|()
block|{
return|return
name|iId
return|;
block|}
specifier|public
name|String
name|getType
parameter_list|()
block|{
return|return
name|iType
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|iName
return|;
block|}
specifier|public
name|String
name|getDate
parameter_list|()
block|{
return|return
name|iDate
return|;
block|}
specifier|public
name|String
name|getTime
parameter_list|()
block|{
return|return
name|iTime
return|;
block|}
specifier|public
name|String
name|getRoom
parameter_list|()
block|{
return|return
name|iRoom
return|;
block|}
specifier|public
name|int
name|getSize
parameter_list|()
block|{
return|return
name|iSize
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getStudentIds
parameter_list|()
block|{
return|return
name|iStudentIds
return|;
block|}
specifier|public
name|Set
argument_list|<
name|Long
argument_list|>
name|getInstructorIds
parameter_list|()
block|{
return|return
name|iInstructorIds
return|;
block|}
specifier|protected
name|void
name|addRoom
parameter_list|(
name|String
name|room
parameter_list|)
block|{
name|iRoom
operator|+=
operator|(
name|iRoom
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|?
literal|", "
else|:
literal|""
operator|)
operator|+
name|room
expr_stmt|;
block|}
block|}
end_class

end_unit

