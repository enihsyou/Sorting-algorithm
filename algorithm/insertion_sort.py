# -*- coding: utf-8 -*-
"""
File name: insertion_sort
Reference: https://en.wikipedia.org/wiki/Insertion_sort
Introduction: 插入排序 O(n^2)
Time: 2016-05-21
Last modified: 2016-05-21
By: enihsyou
"""
from count_time import count_time

"""
循环法
=====
"""


def insertion_sort(data, reverse=False):
    """
    Insertion sort

    采用了非递归的方式，改用循环
    Args:
        data (List[int]): 输入的数据，得是非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)

    Returns:
        data (List[int]): 有序列表
    """
    length = len(data)  # 输入元素个数

    for i in range(1, length):  # 假设第一个元素已经有序
        now_num = data[i]
        j = 0  # while循环的指针
        if reverse:
            "从大到小 如果待排元素大于有序列表中的当前元素 找到该插入的位置"
            while data[j] < now_num and j < i:
                j += 1
        else:
            "从小到大 如果待排元素大于有序列表中的当前元素 找到该插入的位置"
            while data[j] > now_num and j < i:
                j += 1
        data = data[:j] + [now_num] + data[j:i] + data[i + 1:]
    return data


@count_time
def insertion_sort_debug(data, reverse=False, print_step=False):
    """
    Insertion sort ver.debug

    采用了非递归的方式，改用循环
    Args:
        data (List[int]): 输入的数据，得是非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)
        print_step (bool): 是否打印每步过程 (default: False)

    Returns:
        data (List[int]): 有序列表
    """
    length = len(data)  # 输入元素个数

    steps = 0  # 记录操作步数
    swaps = 0  # 记录交换次数

    for i in range(1, length):  # 假设第一个元素已经有序
        now_num = data[i]
        j = 0  # while循环的指针
        if reverse:
            "从大到小 如果待排元素大于有序列表中的当前元素 找到该插入的位置"
            while data[j] < now_num and j < i:
                steps += 1
                j += 1
        else:
            "从小到大 如果待排元素大于有序列表中的当前元素 找到该插入的位置"
            while data[j] > now_num and j < i:
                steps += 1
                j += 1
        swaps += 1
        data = data[:j] + [now_num] + data[j:i] + data[i + 1:]
        if print_step: print(data)
    print("输入数据长度:", length,
          "执行步数:", steps,
          "操作次数:", swaps)
    return data


"""
递归法
=====
"""


def insertion_sort_r(data, reverse=False):
    """
    Insertion sort ver.recursive

    简单的实现，使用了递归的方式，可能无法处理特别大的列表
    Args:
        data (List[int]): 输入的数据，得是非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)

    Returns:
        List[int]: 元素插入以后的列表
    """
    length = len(data)  # 输入元素个数

    if length == 1:  # 如果只有一个元素 不必处理
        return data
    last = insertion_sort(data[1:], reverse)  # 递归处理 从第二个元素开始
    last_len = len(last)  # 递归结果的元素个数
    for i in range(last_len):  # 在递归结果(有序列表)中插入
        if data[0] > last[i]:  # 从大到小 如果待排元素大于有序列表中的当前元素 插入返回
            if reverse: continue
            return last[:i] + [data[0]] + last[i:]
        elif reverse:
            return last[:i] + [data[0]] + last[i:]
    return last + [data[0]]  # 如果首元素大于整个列表


@count_time
def insertion_sort_r_debug(data, reverse=False, print_step=False):
    """
    Insertion sort ver.recursive_debug

    简单的实现，使用了递归的方式，可能无法处理特别大的列表
    Args:
        data (List[int]): 输入的数据，得是非空列表
        reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)
        print_step (bool): 是否打印每步过程 (default: False)

    Returns:
        result (List[int]): 有序列表
    """
    length = len(data)  # 输入元素个数
    steps = 0  # 记录操作步数
    swaps = 0  # 记录交换次数

    def _insertion_sort(_data, _reverse=False, _print_step=False):
        """
        内部函数 排序的主程序
        Args:
            _data (List[int]): 输入的数据，得是非空列表
            _reverse (bool): 是否从小到大排序 (default: False，即从大到小排序)
            _print_step (bool): 是否打印每步过程 (default: False)

        Returns:
            List[int]: 元素插入以后的列表
        """
        nonlocal steps, swaps
        _length = len(_data)  # 输入元素个数
        if _length == 1:  # 如果只有一个元素 不必处理
            steps += 1
            return _data
        last = _insertion_sort(_data[1:], _reverse,
                               _print_step)  # 递归处理 从第二个元素开始
        last_len = len(last)  # 递归结果的元素个数
        for i in range(last_len):  # 在递归结果(有序列表)中插入
            steps += 1
            if _data[0] > last[i]:  # 从大到小 如果待排元素大于有序列表中的当前元素 插入返回
                if _reverse: continue
                swaps += 1
                if _print_step:
                    print(data[:length - _length] +
                          last[:i] + [_data[0]] +
                          last[i:])
                return last[:i] + [_data[0]] + last[i:]
            elif _reverse:
                swaps += 1
                if _print_step:
                    print(data[:length - _length] +
                          last[:i] + [_data[0]] +
                          last[i:])
                return last[:i] + [_data[0]] + last[i:]
        if _print_step:
            print(data[:length - _length] +
                  last + [_data[0]])
        return last + [_data[0]]  # 如果首元素大于整个列表

    result = _insertion_sort(data, reverse, print_step)  # 执行排序
    print("输入数据长度:", length,
          "执行步数:", steps,
          "操作次数:", swaps)
    return result


"""
Test Part
=========
"""
# 调用测试
# print(insertion_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(insertion_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
# insertion_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# insertion_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# insertion_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# insertion_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True, print_step=True)

if __name__ == '__main__':
    print("插入排序法::输入数组进行测试")
    while True:
        inp = input()
        print(eval(inp))