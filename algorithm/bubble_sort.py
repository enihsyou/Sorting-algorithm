# -*- coding: utf-8 -*-
"""Bubble sort

File name: bubble_sort
Reference: https://en.wikipedia.org/wiki/Bubble_sort
Introduction: 冒泡排序 O(n^2)
Date: 2016-05-15
Last modified: 2016-05-25
Author: enihsyou
"""
from count_time import count_time


def bubble_sort(data, reverse=False):
    """Bubble sort

    输入一组数据 默认从大到小排序
    第一次就想出来的方式

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    for i in range(length):
        for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
            if data[i] < data[j]:  # 从大到小 如果反了交换
                if reverse: continue
                data[i], data[j] = data[j], data[i]
            elif reverse:  # 如果数据从小到大
                data[i], data[j] = data[j], data[i]

    return data


@count_time
def bubble_sort_debug(data, reverse=False, print_step=False):
    """Bubble sort ver.debug

    输入一组数据 默认从大到小排序
    会输出每次的操作和进行的步数以及函数执行时间

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)
        print_step (bool) : whether to show sorting steps (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    steps = 0  # 记录循环次数
    comps = 0  # 记录比较次数
    swaps = 0  # 记录交换次数

    for i in range(length):
        for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
            comps += 1  # 下一行有一次比较
            if data[i] < data[j]:  # 从大到小 如果反了交换
                if reverse: continue
                data[i], data[j] = data[j], data[i]
                swaps += 1

            elif reverse:  # 如果数据从小到大
                data[i], data[j] = data[j], data[i]
                swaps += 1
            if print_step: print(data)  # 打印每步操作之后结果
            steps += 1

    print("输入长度:", length,
          "循环次数:", steps,
          "比较次数:", comps,
          "操作次数:", swaps)

    return data


#############
# Test Part #
#############

# 调用测试
# print(bubble_sort([4, 7, 6, 5, 3, 2, 1]))
# print(bubble_sort([4, 7, 6, 5, 3, 2, 1], reverse=True))
# 计时测试
# bubble_sort_debug([4, 7, 6, 5, 3, 2, 1])
# bubble_sort_debug([4, 7, 6, 5, 3, 2, 1], reverse=True)
# 步骤测试
# bubble_sort_debug([4, 7, 6, 5, 3, 2, 1], print_step=True)
# bubble_sort_debug([4, 7, 6, 5, 3, 2, 1], reverse=True, print_step=True)

if __name__ == "__main__":
    print("冒泡排序法::输入数组进行测试")
    while True:
        inp = input()
        print(bubble_sort_debug(eval(inp), True))
