#pragma once

#include <string>
#include <nlohmann/json.hpp>

namespace autoworker
{
    struct WorkContext
    {
        std::string task_id;
        nlohmann::json payload;
        nlohmann::json metadata;
    };

    std::string to_json_string(const WorkContext& ctx);

    WorkContext from_json_string(const std::string& json);
}
