begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|interfaces
package|;
end_package

begin_interface
specifier|public
interface|interface
name|ExternalUidTranslation
block|{
specifier|public
specifier|static
enum|enum
name|Source
block|{
name|Staff
block|,
comment|// Staff/DepartmentalInstructor tables
name|Student
block|,
comment|// Student table
name|User
block|,
comment|// Authentication, TimetableManager, etc.
name|LDAP
comment|// LDAP lookup
block|}
specifier|public
name|String
name|translate
parameter_list|(
name|String
name|uid
parameter_list|,
name|Source
name|source
parameter_list|,
name|Source
name|target
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

