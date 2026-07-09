# autoworker

## Requirements

- CMake 3.14+
- C++17 compiler
- gcovr (for coverage): `brew install gcovr`

## Build

```sh
cmake -B build -DCMAKE_BUILD_TYPE=Debug
cmake --build build
```

## Run

```sh
./build/autoworker
```

## Test

```sh
cd build && ctest --output-on-failure
```

## Coverage

```sh
cmake -B build -DCMAKE_BUILD_TYPE=Coverage
cmake --build build
cmake --build build --target coverage
```

## Convenience Commands

```sh
cmake -B build -DCMAKE_BUILD_TYPE=Coverage && cmake --build build && cmake --build build --target coverage
```

