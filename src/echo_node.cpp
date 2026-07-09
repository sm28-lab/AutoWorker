#include "autoworker/echo_node.hpp"

namespace autoworker
{
    EchoNode::EchoNode(std::string id) : id_(std::move(id)) {}

    std::string EchoNode::id() const
    {
        return id_;
    }

    std::string EchoNode::kind() const
    {
        return "echo";
    }

    std::expected<WorkContext, WorkerNode::Error> EchoNode::execute(const WorkContext& input)
    {
        WorkContext output = input;
        return output;
    }
}
