# -*- coding: utf-8 -*-
"""Cocktail shaker sort

File name: cocktail_shaker_sort
Reference: https://en.wikipedia.org/wiki/Cocktail_shaker_sort
Introduction: 鸡尾酒排序 O(n^2)
Date: 2016-05-22
Last modified: 2016-06-01
Author: enihsyou
"""
from count_time import count_time_debug, count_time


@count_time
def cocktail_shaker_sort(data):
    """Cocktail shaker sort

    Use ``swapped`` to end if necessary.

    Args:
        data (List[int]): list to sort, need a not None list

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    left, right = 0, length - 1  # 有序序号 左边和右边

    while left < right:
        swapped = False  # 是否一遍过去有没有改变 没有即 已有序
        for i in range(left, right):
            if data[i] < data[i + 1]:  # 与后面一个元素对比
                data[i], data[i + 1] = data[i + 1], data[i]
                swapped = True
        right -= 1
        if not swapped: break
        for i in range(right, left, -1):  # 与前面一个元素对比
            if data[i - 1] < data[i]:
                data[i], data[i - 1] = data[i - 1], data[i]
                swapped = True
        left += 1
        if not swapped: break

    return data


@count_time
def cocktail_shaker_sort_reverse(data, reverse=False):
    """Cocktail shaker sort ver.reverse

    Use ``swapped`` to end if necessary.
    支持从小到大排序，可以用[::-1]提升效率

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    left, right = 0, length - 1  # 有序序号 左边和右边
    swapped = True  # 是否一遍过去有没有改变 没有即 已有序

    while swapped:
        swapped = False
        for i in range(left, right):
            if data[i] < data[i + 1]:  # 与后面一个元素对比
                if reverse: continue
                data[i], data[i + 1] = data[i + 1], data[i]
                swapped = True
            elif reverse:
                data[i], data[i + 1] = data[i + 1], data[i]
                swapped = True
        right -= 1

        for i in range(right, left, -1):  # 与前面一个元素对比
            if data[i - 1] < data[i]:
                if reverse: continue
                data[i], data[i - 1] = data[i - 1], data[i]
                swapped = True
            elif reverse:
                data[i], data[i - 1] = data[i - 1], data[i]
                swapped = True
        left += 1

    return data


@count_time_debug
def cocktail_shaker_sort_debug(data, reverse=False, print_step=False):
    """Cocktail shaker sort ver.debug

    Use ``swapped`` to end if necessary.

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)
        print_step (bool) : whether to show sorting steps (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    left, right = 0, length - 1  # 有序序号 左边和右边
    swapped = True  # 是否一遍过去有没有改变 没有便是有序

    steps = 0  # 记录循环次数
    comps = 0  # 记录比较次数
    swaps = 0  # 记录交换次数

    while swapped:
        swapped = False
        for i in range(left, right):
            steps += 1
            comps += 1
            if data[i] < data[i + 1]:  # 与后面一个元素对比
                if reverse: continue
                data[i], data[i + 1] = data[i + 1], data[i]
                swapped = True
                swaps += 1
                if print_step: print(data)
            elif reverse:
                data[i], data[i + 1] = data[i + 1], data[i]
                swapped = True
                swaps += 1
                if print_step: print(data)

        right -= 1
        if not swapped: break

        for i in range(right, left, -1):  # 与前面一个元素对比
            steps += 1

            comps += 1
            if data[i - 1] < data[i]:
                if reverse: continue
                data[i], data[i - 1] = data[i - 1], data[i]
                swapped = True
                swaps += 1
                if print_step: print(data)
            elif reverse:
                data[i], data[i - 1] = data[i - 1], data[i]
                swapped = True
                swaps += 1
                if print_step: print(data)

        left += 1

    print("输入长度:", length,
          "循环次数:", steps,
          "比较次数:", comps,
          "操作次数:", swaps)

    return data


#############
# Test Part #
#############

# 调用测试
# print(cocktail_shaker_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(cocktail_shaker_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
# cocktail_shaker_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# cocktail_shaker_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# cocktail_shaker_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# cocktail_shaker_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True,
#                            print_step=True)

if __name__ == "__main__":
    print("鸡尾酒排序法::输入数组进行测试")
    while True:
        inp = input()
        print(cocktail_shaker_sort_debug(eval(inp), print_step=True))
