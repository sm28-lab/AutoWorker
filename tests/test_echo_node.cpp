#include <gtest/gtest.h>
#include <memory>

#include "autoworker/echo_node.hpp"

using namespace autoworker;

TEST(EchoNode, returns_constructed_id)
{
    // given
    EchoNode node{"echo-1"};

    // when
    auto id = node.id();

    // then
    EXPECT_EQ(id, "echo-1");
}

TEST(EchoNode, returns_echo_kind)
{
    // given
    EchoNode node{"echo-2"};

    // when
    auto kind = node.kind();

    // then
    EXPECT_EQ(kind, "echo");
}

TEST(EchoNode, preserves_work_context_through_execute)
{
    // given
    EchoNode node{"echo-3"};
    auto input = WorkContext
    {
        .task_id = "task-100",
        .payload = {{"query", "hello world"}, {"count", 42}},
        .metadata = nlohmann::json::object()
    };

    // when
    auto output = node.execute(input);

    // then
    ASSERT_TRUE(output.has_value());
    EXPECT_EQ(output->task_id, "task-100");
    EXPECT_EQ(output->payload["query"], "hello world");
    EXPECT_EQ(output->payload["count"], 42);
}

