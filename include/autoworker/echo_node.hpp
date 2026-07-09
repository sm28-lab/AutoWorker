#pragma once

#include <string>

#include "autoworker/worker_node.hpp"

namespace autoworker
{
    class EchoNode : public WorkerNode
    {
    public:
        explicit EchoNode(std::string id);

        std::string id() const override;
        std::string kind() const override;
        std::expected<WorkContext, Error> execute(const WorkContext& input) override;
    private:
        std::string id_;
    };
}
