# -*- coding: utf-8 -*-
"""Cocktail shaker sort

File name: cocktail_shaker_sort
Reference: https://en.wikipedia.org/wiki/Cocktail_shaker_sort
Introduction: 鸡尾酒排序 O(n^2)
Time: 2016-05-22
Last modified: 2016-05-23
By: enihsyou
"""
from count_time import count_time


def cocktail_shaker_sort(data, reverse=False):
    """Cocktail shaker sort

    Use ``swapped`` to end if necessary.

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
            if data[i] > data[i + 1]:  # 与后面一个元素对比
                if reverse: continue
                tmp = data[i]
                data[i] = data[i + 1]
                data[i + 1] = tmp
                swapped = True
            elif reverse:
                tmp = data[i]
                data[i] = data[i + 1]
                data[i + 1] = tmp
                swapped = True
        right -= 1

        for i in range(right, left, -1):  # 与前面一个元素对比
            if data[i - 1] > data[i]:
                if reverse: continue
                tmp = data[i]
                data[i] = data[i - 1]
                data[i - 1] = tmp
                swapped = True
            elif reverse:
                tmp = data[i]
                data[i] = data[i - 1]
                data[i - 1] = tmp
                swapped = True
        left += 1

    return data


@count_time
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
    swapped = True  # 是否一遍过去有没有改变 没有即 已有序

    steps = 0  # 记录操作步数
    swaps = 0  # 记录交换次数

    while swapped:
        swapped = False
        for i in range(left, right):
            steps += 1
            if data[i] > data[i + 1]:  # 与后面一个元素对比
                if reverse: continue
                tmp = data[i]
                data[i] = data[i + 1]
                data[i + 1] = tmp
                swapped = True
                swaps += 1
            elif reverse:
                tmp = data[i]
                data[i] = data[i + 1]
                data[i + 1] = tmp
                swapped = True
                swaps += 1
        right -= 1
        if print_step: print(data)

        for i in range(right, left, -1):  # 与前面一个元素对比
            steps += 1
            if data[i - 1] > data[i]:
                if reverse: continue
                tmp = data[i]
                data[i] = data[i - 1]
                data[i - 1] = tmp
                swapped = True
                swaps += 1
            elif reverse:
                tmp = data[i]
                data[i] = data[i - 1]
                data[i - 1] = tmp
                swapped = True
                swaps += 1
        left += 1
        if print_step: print(data)

    print("输入数据长度:", length,
          "执行步数:", steps,
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

if __name__ == '__main__':
    print("鸡尾酒排序法::输入数组进行测试")
    while True:
        inp = input()
        print(cocktail_shaker_sort_debug(eval(inp), True))
