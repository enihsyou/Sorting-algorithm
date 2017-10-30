cmake_minimum_required(VERSION 3.8)
project(Assigment4)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_EXE_LINKER_FLAGS "-static")
set(SOURCE_FILES1 Q1.cpp)
set(SOURCE_FILES2 Q2.cpp)
add_executable(Q1 ${SOURCE_FILES1})
add_executable(Q2 ${SOURCE_FILES2})
