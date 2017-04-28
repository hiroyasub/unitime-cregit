begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|util
package|;
end_package

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
name|timetable
operator|.
name|model
operator|.
name|CurriculumClassification
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
name|CurriculumCourse
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

begin_class
specifier|public
class|class
name|PopulateProjectedDemandSnapshotData
block|{
specifier|public
name|PopulateProjectedDemandSnapshotData
parameter_list|()
block|{
block|}
specifier|public
name|Date
name|populateProjectedDemandDataFor
parameter_list|(
name|Session
name|acadSession
parameter_list|)
block|{
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
init|=
name|SessionDAO
operator|.
name|getInstance
argument_list|()
operator|.
name|getSession
argument_list|()
decl_stmt|;
name|Transaction
name|trans
init|=
literal|null
decl_stmt|;
name|Date
name|snapshotDate
init|=
literal|null
decl_stmt|;
try|try
block|{
name|trans
operator|=
name|hibSession
operator|.
name|beginTransaction
argument_list|()
expr_stmt|;
name|snapshotDate
operator|=
name|populateProjectedDemandDataFor
argument_list|(
name|acadSession
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|trans
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
name|trans
operator|.
name|rollback
argument_list|()
expr_stmt|;
block|}
return|return
operator|(
name|snapshotDate
operator|)
return|;
block|}
specifier|public
name|Date
name|populateProjectedDemandDataFor
parameter_list|(
name|Session
name|acadSession
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|Date
name|snapshotDate
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|updateCourseOfferingData
argument_list|(
name|acadSession
argument_list|,
name|snapshotDate
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|updateInstructionalOfferingData
argument_list|(
name|acadSession
argument_list|,
name|snapshotDate
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|updateClassData
argument_list|(
name|acadSession
argument_list|,
name|snapshotDate
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|updateCurriculumProjectionRuleData
argument_list|(
name|acadSession
argument_list|,
name|snapshotDate
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|updateCurriculumClassificationData
argument_list|(
name|acadSession
argument_list|,
name|snapshotDate
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
name|updateCurriculumCourseData
argument_list|(
name|acadSession
argument_list|,
name|snapshotDate
argument_list|,
name|hibSession
argument_list|)
expr_stmt|;
return|return
operator|(
name|snapshotDate
operator|)
return|;
block|}
specifier|private
name|void
name|updateCourseOfferingData
parameter_list|(
name|Session
name|acadSession
parameter_list|,
name|Date
name|snapshotDate
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|StringBuilder
name|courseOfferingUpdateSb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|courseOfferingUpdateSb
operator|.
name|append
argument_list|(
literal|"update CourseOffering as co"
argument_list|)
operator|.
name|append
argument_list|(
literal|" set co.snapshotProjectedDemand = co.projectedDemand,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" co.snapshotProjectedDemandDate = :snapshotDate"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where co.instructionalOffering.uniqueId in "
argument_list|)
operator|.
name|append
argument_list|(
literal|" ( select io.uniqueId from InstructionalOffering io where io.session.uniqueId = :sessId ) "
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|createQuery
argument_list|(
name|courseOfferingUpdateSb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"snapshotDate"
argument_list|,
name|snapshotDate
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|acadSession
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|updateInstructionalOfferingData
parameter_list|(
name|Session
name|acadSession
parameter_list|,
name|Date
name|snapshotDate
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|StringBuilder
name|instructionalOfferingUpdateSb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|instructionalOfferingUpdateSb
operator|.
name|append
argument_list|(
literal|"update InstructionalOffering as io"
argument_list|)
operator|.
name|append
argument_list|(
literal|" set io.snapshotLimit = ( select sum(ioc.limit) from InstrOfferingConfig ioc where ioc.instructionalOffering.uniqueId = io.uniqueId ),"
argument_list|)
operator|.
name|append
argument_list|(
literal|" io.snapshotLimitDate = :snapshotDate"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where io.session.uniqueId = :sessId "
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|createQuery
argument_list|(
name|instructionalOfferingUpdateSb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"snapshotDate"
argument_list|,
name|snapshotDate
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|acadSession
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|updateClassData
parameter_list|(
name|Session
name|acadSession
parameter_list|,
name|Date
name|snapshotDate
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|StringBuilder
name|classUpdateSb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|classUpdateSb
operator|.
name|append
argument_list|(
literal|"update Class_ as c"
argument_list|)
operator|.
name|append
argument_list|(
literal|" set c.snapshotLimit = c.expectedCapacity,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" c.snapshotLimitDate = :snapshotDate"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where c.schedulingSubpart.uniqueId in "
argument_list|)
operator|.
name|append
argument_list|(
literal|" ( select ss.uniqueId from SchedulingSubpart as ss where "
argument_list|)
operator|.
name|append
argument_list|(
literal|"  ss.instrOfferingConfig.instructionalOffering.session.uniqueId = :sessId ) "
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|createQuery
argument_list|(
name|classUpdateSb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"snapshotDate"
argument_list|,
name|snapshotDate
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|acadSession
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|updateCurriculumProjectionRuleData
parameter_list|(
name|Session
name|acadSession
parameter_list|,
name|Date
name|snapshotDate
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|StringBuilder
name|curriculumProjectionRuleUpdateSb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|curriculumProjectionRuleUpdateSb
operator|.
name|append
argument_list|(
literal|"update CurriculumProjectionRule as cpr"
argument_list|)
operator|.
name|append
argument_list|(
literal|" set cpr.snapshotProjection = cpr.projection,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" cpr.snapshotProjectedDate = :snapshotDate"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where cpr.academicArea.uniqueId in "
argument_list|)
operator|.
name|append
argument_list|(
literal|" ( select aa.uniqueId from AcademicArea aa where aa.session.uniqueId = :sessId ) "
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|createQuery
argument_list|(
name|curriculumProjectionRuleUpdateSb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"snapshotDate"
argument_list|,
name|snapshotDate
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|acadSession
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|updateCurriculumClassificationData
parameter_list|(
name|Session
name|acadSession
parameter_list|,
name|Date
name|snapshotDate
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|StringBuilder
name|curriculumClassificationUpdateSb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|curriculumClassificationUpdateSb
operator|.
name|append
argument_list|(
literal|"update CurriculumClassification as cc"
argument_list|)
operator|.
name|append
argument_list|(
literal|" set cc.snapshotNrStudents = cc.nrStudents,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" cc.snapshotNrStudentsDate = :snapshotDate"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where cc.curriculum.uniqueId in "
argument_list|)
operator|.
name|append
argument_list|(
literal|" ( select c.uniqueId from Curriculum c where c.academicArea.session.uniqueId = :sessId ) "
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|createQuery
argument_list|(
name|curriculumClassificationUpdateSb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"snapshotDate"
argument_list|,
name|snapshotDate
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|acadSession
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|updateCurriculumCourseData
parameter_list|(
name|Session
name|acadSession
parameter_list|,
name|Date
name|snapshotDate
parameter_list|,
name|org
operator|.
name|hibernate
operator|.
name|Session
name|hibSession
parameter_list|)
block|{
name|StringBuilder
name|curriculumClassificationUpdateSb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|curriculumClassificationUpdateSb
operator|.
name|append
argument_list|(
literal|"update CurriculumCourse as ccrs"
argument_list|)
operator|.
name|append
argument_list|(
literal|" set ccrs.snapshotPercShare = ccrs.percShare,"
argument_list|)
operator|.
name|append
argument_list|(
literal|" ccrs.snapshotPercShareDate = :snapshotDate"
argument_list|)
operator|.
name|append
argument_list|(
literal|" where ccrs.classification.uniqueId in "
argument_list|)
operator|.
name|append
argument_list|(
literal|" ( select cc.uniqueId from CurriculumClassification cc where cc.curriculum.academicArea.session.uniqueId = :sessId ) "
argument_list|)
expr_stmt|;
name|hibSession
operator|.
name|createQuery
argument_list|(
name|curriculumClassificationUpdateSb
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|setTimestamp
argument_list|(
literal|"snapshotDate"
argument_list|,
name|snapshotDate
argument_list|)
operator|.
name|setLong
argument_list|(
literal|"sessId"
argument_list|,
name|acadSession
operator|.
name|getUniqueId
argument_list|()
operator|.
name|longValue
argument_list|()
argument_list|)
operator|.
name|executeUpdate
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

