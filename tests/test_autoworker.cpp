#include <gtest/gtest.h>
#include "autoworker/autoworker.hpp"

TEST(Greet, returns_expected_string)
{
    EXPECT_EQ(autoworker::greet(), "autoworker");
}
