# -*- coding: utf-8 -*-
"""
File name: bubble_sort
Reference: https://en.wikipedia.org/wiki/Bubble_sort
Introduction: 冒泡排序 O(n^2)
Time: 2016-05-15
Last modified: 2016-05-21
By: enihsyou
"""
from count_time import count_time


def bubble_sort(data, reverse=False):
    """
    Bubble sort

    输入一组数据 默认从大到小排序
    第一次就想出来的方式
    Args:
        data (list[int]): 输入的数据，得是非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)

    Returns:
        data (list[int]): 排序好的数据
    """
    length = len(data)

    for i in range(length):
        for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
            if data[i] < data[j]:  # 从大到小 如果反了交换
                if reverse: continue
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp
            elif reverse:  # 如果数据从小到大
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp

    return data


@count_time
def bubble_sort_debug(data, reverse=False, print_step=False):
    """
    Bubble sort ver.debug

    输入一组数据 默认从大到小排序
    会输出每次的操作和进行的步数以及函数执行时间
    Args:
        data (list[int]): 输入的数据，得是非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)
        print_step (bool): 是否打印每步过程 (default: False)
    Returns:
        data (list[int]): 排序好的数据
    """
    length = len(data)

    steps = 0  # 记录操作步数
    swaps = 0  # 记录交换次数

    for i in range(length):
        for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
            if data[i] < data[j]:  # 从大到小 如果反了交换
                if reverse: continue
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp
                swaps += 1

            elif reverse:  # 如果数据从小到大
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp
                swaps += 1
            if print_step: print(data)  # 打印每步操作之后结果
            steps += 1
    print("输入数据长度:", length,
          "执行步数:", steps,
          "交换次数:", swaps)
    return data


"""
Test Part
=========
"""
# bubble_sort_debug([4, 7, 6, 5, 3, 2, 1], reverse=True)
# bubble_sort_debug([4, 7, 6, 5, 3, 2, 1])
# print(bubble_sort([4, 7, 6, 5, 3, 2, 1], reverse=True))
# print(bubble_sort([4, 7, 6, 5, 3, 2, 1]))

if __name__ == '__main__':
    print("冒泡排序法::输入数组进行测试")
    while True:
        inp = input()
        print(eval(inp))
