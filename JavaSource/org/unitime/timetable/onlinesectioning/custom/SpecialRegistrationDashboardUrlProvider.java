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
name|model
operator|.
name|Student
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
name|XStudent
import|;
end_import

begin_interface
specifier|public
interface|interface
name|SpecialRegistrationDashboardUrlProvider
block|{
specifier|public
name|String
name|getDashboardUrl
parameter_list|(
name|OnlineSectioningServer
name|server
parameter_list|,
name|OnlineSectioningHelper
name|helper
parameter_list|,
name|XStudent
name|student
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
specifier|public
name|String
name|getDashboardUrl
parameter_list|(
name|Student
name|student
parameter_list|)
throws|throws
name|SectioningException
function_decl|;
block|}
end_interface

end_unit
