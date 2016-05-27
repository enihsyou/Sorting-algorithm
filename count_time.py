# -*- coding: utf-8 -*-
"""Print calculate time

File name: count_time
Reference: http://www.cnblogs.com/rhcad/archive/2011/12/21/2295507.html
Introduction: Make it a decorator, print running time.
Date: 2016-05-16
Last modified: 2016-05-28
Author: enihsyou
"""
import time
from functools import wraps


def count_time(func):
    """Timer decorator.

    Args:
        func (function): function to be decorated

    Returns:
        function: decorated function
    """

    @wraps(func)
    def _deco(data, reverse=False, print_step=False, loop_times=1):
        """print running time.

        Args:
            data (List[int]): list to sort
            reverse (bool): whether to sort descending (default: False)
            print_step (bool) : whether to show sorting steps (default: False)
            loop_times (int): loop times

        Returns:
            result (list[int]): ordered list
        """
        result = []  # ordered list
        print("调用函数:", func.__name__[:-6])  # remove "_debug"
        time_log = []
        for i in range(loop_times):
            start_time = time.perf_counter()
            result = func(data[:], reverse, print_step)  # sorting
            end_time = time.perf_counter()

            step_time = end_time - start_time  # lapsed time
            time_log += [step_time]  # add to total time list
            print("{:<20} (第{}次)".format(step_time, i + 1))
        print("{:-<80}\n执行时间: {:<20} (共{}次)".format(
                "", sum(time_log), loop_times))
        print("平均时间: {:<20}\n最好: {}\n最差: {}\n{:=<80}\n".format(
                sum(time_log) / loop_times, min(time_log), max(time_log), ""))

        return result

    return _deco

# def just_count_time(func):
#     """Timer decorator.
#
#     Args:
#         func (function): function to be decorated
#
#     Returns:
#         function: decorated function
#     """
#
#     @wraps(func)
#     def _deco(number, unique=True, loop_times=1):
#         """print running time.
#
#         Args:
#             data (List[int]): list to sort
#             reverse (bool): whether to sort descending (default: False)
#             print_step (bool) : whether to show sorting steps (default: False)
#             loop_times (int): loop times
#
#         Returns:
#             result (list[int]): ordered list
#         """
#         result = []  # ordered list
#         print("调用函数:", func.__name__)
#         time_log = []
#         for i in range(loop_times):
#             start_time = time.perf_counter()
#             result = func(number, unique)  # sorting
#             end_time = time.perf_counter()
#
#             step_time = end_time - start_time  # lapsed time
#             time_log += [step_time]  # add to total time list
#             print("{:<20} (第{}次)".format(step_time, i + 1))
#         print("{:-<80}\n执行时间: {:<20} (共{}次)".format(
#                 "", sum(time_log), loop_times))
#         print("平均时间: {:<20}\n最好: {}\n最差: {}\n{:=<80}\n".format(
#                 sum(time_log) / loop_times, min(time_log), max(time_log), ""))
#
#         return result
#
#     return _deco
#
#
# @just_count_time
# def generate_list1(number, unique=True):
#     """Make list with random integers.
#
#     ``number`` defined how many items in the list,
#     all the number in is interval [0, ``number``).
#     Args:
#         number (int): how many items in the list
#         unique (bool): whether to ust unique number (default: True)
#     Returns:
#         (list[int]): random list
#     Raises:
#         EXCEPTION (ValueError): we need a Integer greater than 0
#     """
#     import random
#     if unique:
#         try:
#             return random.sample(range(number), number)
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#     else:
#         try:
#             return [random.randrange(number) for _ in range(number)]
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#
#
# # generate_list1(1000000, loop_times=5)
# """
# 调用函数: generate_list1
# 1.5168029072592681   (第1次)
# 1.5088587249237064   (第2次)
# 1.5020243095638528   (第3次)
# 1.5048855181879963   (第4次)
# 1.50468828577816     (第5次)
# --------------------------------------------------------------------------------
# 执行时间: 7.537259745712984    (共5次)
# 平均时间: 1.5074519491425968
# 最好: 1.5020243095638528
# 最差: 1.5168029072592681
# ================================================================================
# """
#
#
# @just_count_time
# def generate_list2(number, unique=True):
#     """Make list with random integers.
#
#     ``number`` defined how many items in the list,
#     all the number in is interval [0, ``number``).
#     Args:
#         number (int): how many items in the list
#         unique (bool): whether to ust unique number (default: True)
#     Returns:
#         (list[int]): random list
#     Raises:
#         EXCEPTION (ValueError): we need a Integer greater than 0
#     """
#     import random
#     if unique:
#         try:
#             a = [i for i in range(number)]
#             random.shuffle(a)
#             return a
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#     else:
#         try:
#             return [random.randrange(number) for _ in range(number)]
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#
#
# # generate_list2(1000000, loop_times=5)
# """
# 调用函数: generate_list2
# 1.4005741158789493   (第1次)
# 1.3958093021408642   (第2次)
# 1.4132255486474627   (第3次)
# 1.4051207245301338   (第4次)
# 1.4120912391773173   (第5次)
# --------------------------------------------------------------------------------
# 执行时间: 7.026820930374727    (共5次)
# 平均时间: 1.4053641860749455
# 最好: 1.3958093021408642
# 最差: 1.4132255486474627
# ================================================================================
# """
#
#
# @just_count_time
# def generate_list3(number, unique=True):
#     """Make list with random integers.
#
#     ``number`` defined how many items in the list,
#     all the number in is interval [0, ``number``).
#     Args:
#         number (int): how many items in the list
#         unique (bool): whether to ust unique number (default: True)
#     Returns:
#         (list[int]): random list
#     Raises:
#         EXCEPTION (ValueError): we need a Integer greater than 0
#     """
#     import random
#     if unique:
#         try:
#             a = []
#             n = number
#             while n > 0:
#                 a.append(random.randrange(number))
#                 n -= 1
#             return a
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#     else:
#         try:
#             return [random.randrange(number) for _ in range(number)]
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#
#
# # generate_list3(1000000, loop_times=5)
# """
# 调用函数: generate_list3
# 1.7184208377379673   (第1次)
# 1.6908765731181148   (第2次)
# 1.6940131039071207   (第3次)
# 1.7556120875801149   (第4次)
# 1.7644482780316562   (第5次)
# --------------------------------------------------------------------------------
# 执行时间: 8.623370880374974    (共5次)
# 平均时间: 1.7246741760749948
# 最好: 1.6908765731181148
# 最差: 1.7644482780316562
# ================================================================================
# """
#
#
# @just_count_time
# def generate_list4(number, unique=True):
#     """Make list with random integers.
#
#     ``number`` defined how many items in the list,
#     all the number in is interval [0, ``number``).
#     Args:
#         number (int): how many items in the list
#         unique (bool): whether to ust unique number (default: True)
#     Returns:
#         (list[int]): random list
#     Raises:
#         EXCEPTION (ValueError): we need a Integer greater than 0
#     """
#     from random import sample
#     if unique:
#         try:
#             return sample(range(number), number)
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#     else:
#         try:
#             return [random.randrange(number) for _ in range(number)]
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#
#
# # generate_list4(1000000, loop_times=5)
# """
# 调用函数: generate_list4
# 1.4234383499233159   (第1次)
# 1.4633435534229862   (第2次)
# 1.4624809963003305   (第3次)
# 1.463333736425212    (第4次)
# 1.4714059859590156   (第5次)
# --------------------------------------------------------------------------------
# 执行时间: 7.28400262203086     (共5次)
# 平均时间: 1.456800524406172
# 最好: 1.4234383499233159
# 最差: 1.4714059859590156
# ================================================================================
# """
#
#
# @just_count_time
# def generate_list5(number, unique=True):
#     """Make list with random integers.
#
#     ``number`` defined how many items in the list,
#     all the number in is interval [0, ``number``).
#     Args:
#         number (int): how many items in the list
#         unique (bool): whether to ust unique number (default: True)
#     Returns:
#         (list[int]): random list
#     Raises:
#         EXCEPTION (ValueError): we need a Integer greater than 0
#     """
#     from numpy.random import random_sample
#     from numpy import int32
#     if unique:
#         try:
#             a = number * random_sample(number)
#             return a.astype(int).tolist()
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#     else:
#         try:
#             return [random.randrange(number) for _ in range(number)]
#         except ValueError as EXCEPTION:
#             print("需要一个非负数！")
#             raise EXCEPTION
#
#
# # print(generate_list5(10, loop_times=5))
# # generate_list5(1000000, loop_times=5)
# """
# 调用函数: generate_list5
# 0.040782932346159845 (第1次)
# 0.05334913572490274  (第2次)
# 0.05220902530152685  (第3次)
# 0.05079046912308771  (第4次)
# 0.05114700463317673  (第5次)
# --------------------------------------------------------------------------------
# 执行时间: 0.24827856712885388  (共5次)
# 平均时间: 0.04965571342577078
# 最好: 0.040782932346159845
# 最差: 0.05334913572490274
# ================================================================================
# """
#
#
# @just_count_time
# def generate_list6(number, unique=True):
#     """Make list with random integers.
#
#     ``number`` defined how many items in the list,
#     all the number in is interval [0, ``number``).
#     Args:
#         number (int): how many items in the list
#         unique (bool): whether to ust unique number (default: True)
#     Returns:
#         (list[int]): random list
#     Raises:
#         EXCEPTION (ValueError): we need a Integer greater than 0
#     """
#     from numpy.random import randint
#
#     a = randint(0, number, number)
#     return a.tolist()
#
#
#
#
# print(generate_list6(10, loop_times=5))
# generate_list6(1000000, loop_times=5)
# """
# 0.02946839618368674  (第1次)
# 0.0408239852459448   (第2次)
# 0.040305469272573835 (第3次)
# 0.04012876331262985  (第4次)
# 0.03979409293394809  (第5次)
# --------------------------------------------------------------------------------
# 执行时间: 0.1905207069487833   (共5次)
# 平均时间: 0.038104141389756666
# 最好: 0.02946839618368674
# 最差: 0.0408239852459448
# ================================================================================
# """
