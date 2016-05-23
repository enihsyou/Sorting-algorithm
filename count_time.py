# -*- coding: utf-8 -*-
"""Print calculate time

File name: count_time
Reference: http://www.cnblogs.com/rhcad/archive/2011/12/21/2295507.html
Introduction: Make it a decorator, print running time.
Time: 2016-05-16
Last modified: 2016-05-23
By: enihsyou
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
    def _deco(data, reverse=False, print_step=False):
        """print running time.

        Args:
            data (List[int]): list to sort
            reverse (bool): whether to sort descending (default: False)
            print_step (bool) : whether to show sorting steps (default: False)

        Returns:
            result (list[int]): ordered list
        """
        print("调用函数:", func.__name__[:-6])  # remove "_debug"

        start_time = time.perf_counter()
        result = func(data, reverse, print_step)
        end_time = time.perf_counter()

        print("执行时间:", (end_time - start_time), "\n")

        return result

    return _deco
