# -*- coding: utf-8 -*-
"""Print calculate time

File name: count_time
Reference: http://www.cnblogs.com/rhcad/archive/2011/12/21/2295507.html
Introduction: Make it a decorator, print running time.
Date: 2016-05-16
Last modified: 2016-06-01
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
    def _deco(data, reverse=False, loop_times=1):
        """print running time.

        Args:
            data (List[int]): list to sort
            reverse (bool): whether to sort descending (default: False)
            loop_times (int): loop times

        Returns:
            result (list[int]): ordered list
        """
        result = []  # ordered list
        print("调用函数:", func.__name__)
        time_log = []

        for i in range(loop_times):
            if func.__name__.endswith("reverse"):
                parameter = data[:], reverse
            else:
                parameter = data[:]

            start_time = time.perf_counter()
            result = func(parameter)  # sorting
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


def count_time_debug(func):
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
