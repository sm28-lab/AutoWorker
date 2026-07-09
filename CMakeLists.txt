cmake_minimum_required(VERSION 3.14)
project(autoworker CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# --- Coverage build type -----------------------------------------------------

if(CMAKE_BUILD_TYPE STREQUAL "Coverage")
    set(COVERAGE_FLAGS --coverage -O0 -g)
    add_compile_options(${COVERAGE_FLAGS})
    add_link_options(${COVERAGE_FLAGS})
endif()

# --- Main library ------------------------------------------------------------

add_library(autoworker_lib INTERFACE)
target_include_directories(autoworker_lib INTERFACE include)

add_executable(autoworker src/main.cpp)
target_link_libraries(autoworker PRIVATE autoworker_lib)

# --- Tests -------------------------------------------------------------------

include(FetchContent)
FetchContent_Declare(
    googletest
    GIT_REPOSITORY https://github.com/google/googletest.git
    GIT_TAG        v1.17.0
)
FetchContent_MakeAvailable(googletest)

enable_testing()

add_executable(autoworker_tests
    tests/test_autoworker.cpp
)
target_link_libraries(autoworker_tests
    PRIVATE
        autoworker_lib
        GTest::gtest_main
)

include(GoogleTest)
gtest_discover_tests(autoworker_tests)

# --- Coverage target ---------------------------------------------------------

find_program(GCOVR_BIN gcovr)

if(GCOVR_BIN)
    add_custom_target(coverage
        COMMAND ${CMAKE_CTEST_COMMAND} --output-on-failure
        COMMAND ${GCOVR_BIN}
            --root ${CMAKE_SOURCE_DIR}
            --exclude ${CMAKE_BINARY_DIR}/_deps/
            --txt-metric=line
            --print-summary
        WORKING_DIRECTORY ${CMAKE_BINARY_DIR}
        DEPENDS autoworker_tests
        COMMENT "Running tests and generating coverage report"
    )
endif()
