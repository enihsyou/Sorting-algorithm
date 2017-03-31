# -*- coding: utf-8 -*-
"""Selection sort

File name: selection_sort
Reference: https://en.wikipedia.org/wiki/Selection_sort
Introduction: 选择排序 O(n^2)
Date: 2016-05-21
Last modified: 2016-06-01
Author: enihsyou
"""
from count_time import count_time_debug, count_time


@count_time
def selection_sort(data):
    """Selection sort

    看上去很简单

    Args:
        data (List[int]): list to sort, need a not None list

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    for i in range(length):
        index = i  # 最大或最小的序号
        for j in range(i + 1, length):
            if data[j] > data[index]:  # 如果当前值大于循环中最大的记录
                index = j

        # 进行交换
        if index == i: continue  # 如果当前位正确 不必交换
        data[index], data[i] = data[i], data[index]

    return data


@count_time
def selection_sort_reverse(data, reverse=False):
    """Selection sort

    看上去很简单。支持从小到大排序，可以用[::-1]提升效率

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    for i in range(length):
        index = i  # 最大或最小的序号
        for j in range(i + 1, length):
            if data[j] > data[index]:  # 如果当前值大于循环中最大的记录
                if reverse: continue
                index = j
            elif reverse:
                index = j

        # 进行交换
        if index == i: continue  # 如果当前位正确 不必交换
        data[index], data[i] = data[i], data[index]

    return data


@count_time_debug
def selection_sort_debug(data, reverse=False, print_step=False):
    """Selection sort ver.debug

    看上去很简单

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

    for i in range(length):
        index = i  # 最大或最小的序号
        for j in range(i + 1, length):
            steps += 1
            comps += 1
            if data[j] > data[index]:  # 如果当前值大于循环中最大的记录
                if reverse: continue
                index = j
            elif reverse:
                index = j

        # 进行交换
        if index == i: continue  # 如果当前位正确 不必交换
        data[index], data[i] = data[i], data[index]
        swaps += 1
        if print_step: print(data)

    print("输入长度:", length,
          "循环次数:", steps,
          "比较次数:", comps,
          "操作次数:", swaps)

    return data


#############
# Test Part #
#############

# 调用测试
# print(selection_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(selection_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True, print_step=True)

if __name__ == "__main__":
    print("选择排序法::输入数组进行测试")
    while True:
        inp = input()
        print(selection_sort_debug(eval(inp), print_step=True))
