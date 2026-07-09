#include <gtest/gtest.h>
#include "autoworker/work_context.hpp"

using namespace autoworker;

TEST(WorkContext, serializes_all_fields_to_json)
{
    // given
    auto ctx = WorkContext
    {
        .task_id = "task-001",
        .payload = {{"query", "summarize this"}},
        .metadata = {{"source", "test"}}
    };

    // when
    auto json = to_json_string(ctx);

    // then
    EXPECT_TRUE(json.find("task-001") != std::string::npos);
    EXPECT_TRUE(json.find("query") != std::string::npos);
    EXPECT_TRUE(json.find("summarize this") != std::string::npos);
    EXPECT_TRUE(json.find("source") != std::string::npos);
}

TEST(WorkContext, round_trips_through_serialize_deserialize)
{
    // given
    auto original = WorkContext
    {
        .task_id = "task-002",
        .payload = {{"query", "analyze"}, {"limit", 10}},
        .metadata = {{"source", "unit-test"}, {"timestamp", "1999-12-31T23:59:59Z"}}
    };

    // when
    auto restored = from_json_string(to_json_string(original));

    // then
    EXPECT_EQ(restored.task_id, "task-002");
    EXPECT_EQ(restored.payload["query"], "analyze");
    EXPECT_EQ(restored.payload["limit"], 10);
    EXPECT_EQ(restored.metadata["source"], "unit-test");
    EXPECT_EQ(restored.metadata["timestamp"], "1999-12-31T23:59:59Z");
}

TEST(WorkContext, round_trips_with_empty_payload_and_metadata)
{
    // given
    auto original = WorkContext
    {
        .task_id = "task-003",
        .payload = nlohmann::json::object(),
        .metadata = nlohmann::json::object()
    };

    // when
    auto restored = from_json_string(to_json_string(original));

    // then
    EXPECT_EQ(restored.task_id, "task-003");
    EXPECT_TRUE(restored.payload.is_object());
    EXPECT_TRUE(restored.metadata.is_object());
}

TEST(WorkContext, round_trips_with_nested_payload)
{
    // given
    auto original = WorkContext
    {
        .task_id = "task-004",
        .payload = {{"request", {{"model", "gpt-4"}, {"messages", {{"role", "user"}, {"content", "hello"}}}}}},
        .metadata = {{"node_history",{"node-a","node-b"}}}
    };

    // when
    auto restored = from_json_string(to_json_string(original));

    // then
    EXPECT_EQ(restored.payload["request"]["model"], "gpt-4");
    EXPECT_EQ(restored.payload["request"]["messages"]["role"], "user");
    EXPECT_EQ(restored.metadata["node_history"][0], "node-a");
    EXPECT_EQ(restored.metadata["node_history"][1], "node-b");
}

TEST(WorkContext, from_json_preserves_field_types)
{
    // given
    auto original = WorkContext
    {
        .task_id = "task-005",
        .payload = {{"count", 42}, {"ratio", 3.14}, {"enabled", true}},
        .metadata = {}
    };

    // when
    auto restored = from_json_string(to_json_string(original));

    // then
    EXPECT_EQ(restored.payload["count"], 42);
    EXPECT_DOUBLE_EQ(restored.payload["ratio"].get<double>(), 3.14);
    EXPECT_TRUE(restored.payload["enabled"].get<bool>());
}
