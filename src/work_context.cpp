#include "autoworker/work_context.hpp"

namespace autoworker
{
    std::string to_json_string(const WorkContext& ctx)
    {
        nlohmann::json j;
        j["task_id"] = ctx.task_id;
        j["payload"] = ctx.payload;
        j["metadata"] = ctx.metadata;
        return j.dump();
    }

    WorkContext from_json_string(const std::string& json)
    {
        auto j = nlohmann::json::parse(json);
        return WorkContext
        {
            .task_id = j.at("task_id"),
            .payload = j.value("payload", nlohmann::json::object()),
            .metadata = j.value("metadata", nlohmann::json::object())
        };
    }
}
