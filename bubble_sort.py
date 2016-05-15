"""
File name: bubble_sort
Reference: https://en.wikipedia.org/wiki/Bubble_sort
Introduction: 实现冒泡排序 O(n^2)
Time: 2016-05-15
By: enihsyou
"""


def bubble_sort(data, reverse = False):
    """
    Bubble sort
    输入一组数据 默认从大到小排序"""
    length = len(data)

    for i in range(length):
        for j in range(i, length):
            if data[i] < data[j]:  # 从大到小 如果反了交换
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp
            elif reverse:  # 如果数据从小到大
                tmp = data[j]
                data[j] = data[i]
                data[i] = tmp

    return data
