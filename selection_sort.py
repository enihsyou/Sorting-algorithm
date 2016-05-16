"""
File name: selection_sort
Reference: https://en.wikipedia.org/wiki/Selection_sort
Introduction: 选择排序 O(n^2)
Time: 2016-05-15
By: enihsyou
"""
from count_time import *


def selection_sort(data, reverse = False):
    """
    Selection sort
    输入一组数据 默认从大到小排序
    第一次就想出来的方式
    """
    length = len(data)

    for i in range(length):
        for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
            if data[i] < data[j]:  # 从大到小 如果反了交换
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp
            elif reverse:  # 如果数据从小到大
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp

    return data


@count_time
def selection_sort_debug(data, reverse = False):
    """
    Selection sort ver.debug
    输入一组数据 默认从大到小排序
    会输出每次的操作和进行的步数以及函数执行时间
    """
    length = len(data)

    steps = 0  # 记录操作步数

    for i in range(length):
        for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
            if data[i] < data[j]:  # 从大到小 如果反了交换
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp
            elif reverse:  # 如果数据从小到大
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp
            print(data)  # 打印每步操作之后结果
            steps += 1
    print("输入数据长度:", length,
          "执行步数:", steps)
    return data


selection_sort_debug([9, 8, 7, 6, 1, 2, 3, 4, 5])
