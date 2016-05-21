# -*- coding: utf-8 -*-
"""
File name: step_by_step
Reference: http://www.cnblogs.com/rhcad/archive/2011/12/21/2295507.html
Introduction: 作为装饰器 输出函数单次执行的时间
Time: 2016-05-16
By: enihsyou
"""
import time


def count_time(func):
    """
    计时器装饰器

    Args:
        func (function): 被装饰函数

    Returns:
        function: 返回
    """

    def _deco(data, reverse=False, print_step=False):
        """

        Args:
            data (List[int]): 待排列表
            reverse (bool): 是否从大到小 (default: False)
            print_step (bool) : 被装饰函数的参数 (default: False)

        Returns:
            result (list[int]): 有序列表
        """
        start_time = time.perf_counter()
        result = func(data, reverse, print_step)
        end_time = time.perf_counter()
        print("执行时间:", (end_time - start_time))
        return result

    return _deco
