begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|timetable
operator|.
name|security
operator|.
name|authority
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
name|security
operator|.
name|rights
operator|.
name|Right
import|;
end_import

begin_class
specifier|public
class|class
name|NoRoleAuthority
extends|extends
name|AbstractAuthority
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TYPE
init|=
literal|"NoRole"
decl_stmt|;
specifier|public
name|NoRoleAuthority
parameter_list|()
block|{
name|super
argument_list|(
literal|0l
argument_list|,
name|TYPE
argument_list|,
literal|"No Role"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasRight
parameter_list|(
name|Right
name|right
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

