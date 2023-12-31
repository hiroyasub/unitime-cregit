begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|unitime
operator|.
name|commons
package|;
end_package

begin_comment
comment|/* NaturalOrderComparator.java -- Perform 'natural order' comparisons of strings in Java. Copyright (C) 2003 by Pierre-Luc Paour<natorder@paour.com>  Based on the C version by Martin Pool, of which this is more or less a straight conversion. Copyright (C) 2000 by Martin Pool<mbp@humbug.org.au>  This software is provided 'as-is', without any express or implied warranty.  In no event will the authors be held liable for any damages arising from the use of this software.  Permission is granted to anyone to use this software for any purpose, including commercial applications, and to alter it and redistribute it freely, subject to the following restrictions:  1. The origin of this software must not be misrepresented; you must not claim that you wrote the original software. If you use this software in a product, an acknowledgment in the product documentation would be appreciated but is not required. 2. Altered source versions must be plainly marked as such, and must not be misrepresented as being the original software. 3. This notice may not be removed or altered from any source distribution. */
end_comment

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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

begin_class
specifier|public
class|class
name|NaturalOrderComparator
implements|implements
name|Comparator
argument_list|<
name|String
argument_list|>
block|{
specifier|private
specifier|static
name|NaturalOrderComparator
name|sInstance
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|NaturalOrderComparator
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|sInstance
operator|==
literal|null
condition|)
name|sInstance
operator|=
operator|new
name|NaturalOrderComparator
argument_list|()
expr_stmt|;
return|return
name|sInstance
return|;
block|}
name|int
name|compareRight
parameter_list|(
name|String
name|a
parameter_list|,
name|String
name|b
parameter_list|)
block|{
name|int
name|bias
init|=
literal|0
decl_stmt|;
name|int
name|ia
init|=
literal|0
decl_stmt|;
name|int
name|ib
init|=
literal|0
decl_stmt|;
comment|// The longest run of digits wins.  That aside, the greatest
comment|// value wins, but we can't know that it will until we've scanned
comment|// both numbers to know that they have the same magnitude, so we
comment|// remember it in BIAS.
for|for
control|(
init|;
condition|;
name|ia
operator|++
operator|,
name|ib
operator|++
control|)
block|{
name|char
name|ca
init|=
name|charAt
argument_list|(
name|a
argument_list|,
name|ia
argument_list|)
decl_stmt|;
name|char
name|cb
init|=
name|charAt
argument_list|(
name|b
argument_list|,
name|ib
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|Character
operator|.
name|isDigit
argument_list|(
name|ca
argument_list|)
operator|&&
operator|!
name|Character
operator|.
name|isDigit
argument_list|(
name|cb
argument_list|)
condition|)
block|{
return|return
name|bias
return|;
block|}
if|else if
condition|(
operator|!
name|Character
operator|.
name|isDigit
argument_list|(
name|ca
argument_list|)
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
if|else if
condition|(
operator|!
name|Character
operator|.
name|isDigit
argument_list|(
name|cb
argument_list|)
condition|)
block|{
return|return
operator|+
literal|1
return|;
block|}
if|else if
condition|(
name|ca
operator|<
name|cb
condition|)
block|{
if|if
condition|(
name|bias
operator|==
literal|0
condition|)
block|{
name|bias
operator|=
operator|-
literal|1
expr_stmt|;
block|}
block|}
if|else if
condition|(
name|ca
operator|>
name|cb
condition|)
block|{
if|if
condition|(
name|bias
operator|==
literal|0
condition|)
name|bias
operator|=
operator|+
literal|1
expr_stmt|;
block|}
if|else if
condition|(
name|ca
operator|==
literal|0
operator|&&
name|cb
operator|==
literal|0
condition|)
block|{
return|return
name|bias
return|;
block|}
block|}
block|}
specifier|public
name|int
name|compare
parameter_list|(
name|String
name|a
parameter_list|,
name|String
name|b
parameter_list|)
block|{
name|int
name|ia
init|=
literal|0
decl_stmt|,
name|ib
init|=
literal|0
decl_stmt|;
name|int
name|nza
init|=
literal|0
decl_stmt|,
name|nzb
init|=
literal|0
decl_stmt|;
name|char
name|ca
decl_stmt|,
name|cb
decl_stmt|;
name|int
name|result
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
comment|// only count the number of zeroes leading the last number compared
name|nza
operator|=
name|nzb
operator|=
literal|0
expr_stmt|;
name|ca
operator|=
name|charAt
argument_list|(
name|a
argument_list|,
name|ia
argument_list|)
expr_stmt|;
name|cb
operator|=
name|charAt
argument_list|(
name|b
argument_list|,
name|ib
argument_list|)
expr_stmt|;
comment|// skip over leading spaces or zeros
while|while
condition|(
name|Character
operator|.
name|isSpaceChar
argument_list|(
name|ca
argument_list|)
operator|||
name|ca
operator|==
literal|'0'
condition|)
block|{
if|if
condition|(
name|ca
operator|==
literal|'0'
condition|)
block|{
name|nza
operator|++
expr_stmt|;
block|}
else|else
block|{
comment|// only count consecutive zeroes
name|nza
operator|=
literal|0
expr_stmt|;
block|}
name|ca
operator|=
name|charAt
argument_list|(
name|a
argument_list|,
operator|++
name|ia
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|Character
operator|.
name|isSpaceChar
argument_list|(
name|cb
argument_list|)
operator|||
name|cb
operator|==
literal|'0'
condition|)
block|{
if|if
condition|(
name|cb
operator|==
literal|'0'
condition|)
block|{
name|nzb
operator|++
expr_stmt|;
block|}
else|else
block|{
comment|// only count consecutive zeroes
name|nzb
operator|=
literal|0
expr_stmt|;
block|}
name|cb
operator|=
name|charAt
argument_list|(
name|b
argument_list|,
operator|++
name|ib
argument_list|)
expr_stmt|;
block|}
comment|// process run of digits
if|if
condition|(
name|Character
operator|.
name|isDigit
argument_list|(
name|ca
argument_list|)
operator|&&
name|Character
operator|.
name|isDigit
argument_list|(
name|cb
argument_list|)
condition|)
block|{
if|if
condition|(
operator|(
name|result
operator|=
name|compareRight
argument_list|(
name|a
operator|.
name|substring
argument_list|(
name|ia
argument_list|)
argument_list|,
name|b
operator|.
name|substring
argument_list|(
name|ib
argument_list|)
argument_list|)
operator|)
operator|!=
literal|0
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
if|if
condition|(
name|ca
operator|==
literal|0
operator|&&
name|cb
operator|==
literal|0
condition|)
block|{
comment|// The strings compare the same.  Perhaps the caller
comment|// will want to call strcmp to break the tie.
return|return
name|nza
operator|-
name|nzb
return|;
block|}
if|if
condition|(
name|ca
operator|<
name|cb
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
if|else if
condition|(
name|ca
operator|>
name|cb
condition|)
block|{
return|return
operator|+
literal|1
return|;
block|}
operator|++
name|ia
expr_stmt|;
operator|++
name|ib
expr_stmt|;
block|}
block|}
specifier|static
name|char
name|charAt
parameter_list|(
name|String
name|s
parameter_list|,
name|int
name|i
parameter_list|)
block|{
if|if
condition|(
name|i
operator|>=
name|s
operator|.
name|length
argument_list|()
condition|)
block|{
return|return
literal|0
return|;
block|}
else|else
block|{
return|return
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
return|;
block|}
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|String
index|[]
name|strings
init|=
operator|new
name|String
index|[]
block|{
literal|"1-2"
block|,
literal|"1-02"
block|,
literal|"1-20"
block|,
literal|"10-20"
block|,
literal|"fred"
block|,
literal|"jane"
block|,
literal|"pic01"
block|,
literal|"pic2"
block|,
literal|"pic02"
block|,
literal|"pic02a"
block|,
literal|"pic3"
block|,
literal|"pic4"
block|,
literal|"pic 4 else"
block|,
literal|"pic 5"
block|,
literal|"pic05"
block|,
literal|"pic 5"
block|,
literal|"pic 5 something"
block|,
literal|"pic 6"
block|,
literal|"pic   7"
block|,
literal|"pic100"
block|,
literal|"pic100a"
block|,
literal|"pic120"
block|,
literal|"pic121"
block|,
literal|"pic02000"
block|,
literal|"tom"
block|,
literal|"x2-g8"
block|,
literal|"x2-y7"
block|,
literal|"x2-y08"
block|,
literal|"x8-y8"
block|}
decl_stmt|;
name|List
name|orig
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|strings
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Original: "
operator|+
name|orig
argument_list|)
expr_stmt|;
name|List
name|scrambled
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|strings
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|shuffle
argument_list|(
name|scrambled
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Scrambled: "
operator|+
name|scrambled
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|scrambled
argument_list|,
operator|new
name|NaturalOrderComparator
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Sorted: "
operator|+
name|scrambled
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

