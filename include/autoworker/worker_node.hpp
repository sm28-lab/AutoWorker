#pragma once

#include <expected>
#include <string>

#include "autoworker/work_context.hpp"

namespace autoworker
{
    class WorkerNode
    {
    public:
        struct Error
        {
            std::string message;
        };

        virtual ~WorkerNode() = default;
        virtual std::string id() const = 0;
        virtual std::string kind() const = 0;
        virtual std::expected<WorkContext, Error> execute(const WorkContext& input) = 0;
    };
}
