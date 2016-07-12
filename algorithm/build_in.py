# -*- coding: utf-8 -*-
"""
File name: build_in
Reference:
Introduction:
Date: 2016-07-13
Last modified: 2016-07-13
Author: enihsyou
"""
from count_time import count_time_debug, count_time


@count_time
def build_in(data):
    """用内建函数进行排序，对比用

    Args:
        data (List[int]): list to sort, need a not None list

    Returns:
        List[int]: ordered list
    """
    return data.sort()


@count_time_debug
def build_in_debug(data, reverse=False, print_step=False):
    """用内建函数进行排序，对比用

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)
        print_step (bool) : whether to show sorting steps (default: False)

    Returns:
        List[int]: ordered list
    """
    if reverse:
        if print_step: pass
        return data.sort(reverse=True)
    return data.sort()


#############
# Test Part #
#############
# 调用测试
# print(build_in([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(build_in([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
# build_in_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# build_in_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# build_in_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# build_in_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True, print_step=True)

if __name__ == "__main__":
    print("内建排序::输入数组进行测试")
    while True:
        inp = input()
        print(build_in_debug(eval(inp), print_step=True))
