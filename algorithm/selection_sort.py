# -*- coding: utf-8 -*-
"""
File name: selection_sort
Reference: https://en.wikipedia.org/wiki/Selection_sort
Introduction: 选择排序 O(n^2)
Time: 2016-05-21
Last modified: 2016-05-22
By: enihsyou
"""
from count_time import count_time


def selection_sort(data, reverse=False):
    """
    Selection sort

    看上去很简单
    Args:
        data (List[int]): 输入的数据，非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)

    Returns:
        data (List[int]): 有序列表
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

        "进行交换"
        if index == i: continue  # 如果当前位正确 不必交换
        tmp = data[index]
        data[index] = data[i]
        data[i] = tmp

    return data


@count_time
def selection_sort_debug(data, reverse=False, print_step=False):
    """
    Selection sort ver.debug

    看上去很简单
    Args:
        data (List[int]): 输入的数据，非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)
        print_step (bool): 是否打印每步过程 (default: False)

    Returns:
        data (List[int]): 有序列表
    """
    length = len(data)

    steps = 0  # 记录操作步数
    swaps = 0  # 记录交换次数

    for i in range(length):
        index = i  # 最大或最小的序号
        for j in range(i + 1, length):
            steps += 1
            if data[j] > data[index]:  # 如果当前值大于循环中最大的记录
                if reverse: continue
                index = j
            elif reverse:
                index = j

        "进行交换"
        if index == i: continue  # 如果当前位正确 不必交换
        tmp = data[index]
        data[index] = data[i]
        data[i] = tmp
        swaps += 1
        if print_step: print(data)

    print("输入数据长度:", length,
          "执行步数:", steps,
          "操作次数:", swaps)

    return data


# 调用测试
# print(selection_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(selection_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# selection_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True, print_step=True)

if __name__ == '__main__':
    print("选择排序法::输入数组进行测试")
    while True:
        inp = input()
        print(selection_sort_debug(eval(inp), True))
