# -*- coding: utf-8 -*-
"""
File name: quick_sort
Reference: https://en.wikipedia.org/wiki/Quicksort
Introduction: Quick sort, first pivot. O(n*Log(n))
Date: 2016-05-27
Last modified: 2016-05-27
Author: enihsyou
"""
from count_time import count_time


def quick_sort(data, reverse=False):
    """Quick sort

    递归解决列表排序，使用局部变量加快访问，列表推倒式简单方便，可能效率不如原生的。
    空间复杂度能有O(n)。
    没有使用迭代的方式，因为测试之后发现效率更低。

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)

    Returns:
        List[int]: ordered list
    """

    def _quick_sort(_data):
        if len(_data) <= 1: return _data
        pivot = _data[0]

        left = _quick_sort([x for x in _data[1:] if x <= pivot])
        right = _quick_sort([x for x in _data[1:] if x > pivot])
        if reverse:
            return left + [pivot] + right
        else:
            return right + [pivot] + left

    return _quick_sort(data)


@count_time
def quick_sort_debug(data, reverse=False, print_step=False):
    """Quick sort ver.debug

    递归解决列表排序，使用局部变量加快访问，列表推倒式简单方便，可能效率不如原生的。
    空间复杂度能有O(n)。
    没有使用迭代的方式，因为测试之后发现效率更低。

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)
        print_step (bool) : whether to show sorting steps (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)
    steps = 0  # 记录操作步数
    comps = 0  # 记录比较次数
    swaps = 0  # 记录交换次数

    def _quick_sort(_data):
        nonlocal steps, comps, swaps
        steps += 1
        if len(_data) <= 1: return _data
        pivot = _data[0]
        left = _quick_sort([x for x in _data[1:] if x <= pivot])
        right = _quick_sort([x for x in _data[1:] if x > pivot])
        comps += 2 * (len(_data) - 1)
        swaps += 2
        if reverse:
            if print_step: print(left + [pivot] + right)
            return left + [pivot] + right
        else:
            if print_step: print(right + [pivot] + left)
            return right + [pivot] + left

    _quick_sort(data)
    print("输入长度:", length,
          "循环次数:", steps,
          "比较次数:", comps,
          "操作次数:", swaps)
    return data


#############
# Test Part #
#############
# 调用测试
# print(quick_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(quick_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
# quick_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# quick_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# quick_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# quick_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True, print_step=True)

if __name__ == "__main__":
    print("快速排序法::输入数组进行测试")
    while True:
        inp = input()
        print(quick_sort_debug(eval(inp), print_step=True))
