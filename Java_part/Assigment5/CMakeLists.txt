cmake_minimum_required(VERSION 3.8)
project(Assigment5)

set(CMAKE_C_STANDARD 99)
set(CMAKE_CXX_FLAGS "-static")
set(SOURCE_FILES Q1.c)
set(SOURCE_FILES2 Q2.c)
add_executable(Q1 ${SOURCE_FILES})
add_executable(Q2 ${SOURCE_FILES2})
