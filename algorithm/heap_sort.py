# -*- coding: utf-8 -*-
"""Heap sort

File name: heap_sort
Reference: https://en.wikipedia.org/wiki/Heapsort
Introduction: 堆排序 O(n*Log(n))
Date: 2016-05-22
Last modified: 2016-05-24
Author: enihsyou
"""
from count_time import count_time


def heap_sort(data, reverse=False):
    """Heap sort

    堆排序
    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    def _build_max_heap(_start, _end):
        """把``start``到``end``之间的数据 变得符合堆结构

        将堆的末节点进行调整，使子节点永远不大于父节点。
        把``reverse``判断放在外面能加快一点。
        Args:
            _start (int): 指定要变换的开始位置
            _end (int): 指定要变换的终点位置

        Returns:
            None: 只改变``data``数组
        """
        root = _start  # 根节点位置

        while True:
            child = 2 * root + 1  # 二叉子节点开始的位置
            if child > _end: break

            # 先比较两个子节点，选择更大的一个child
            if child + 1 <= _end:
                if reverse:  # descending
                    if data[child] < data[child + 1]:
                        child += 1
                else:  # ascending
                    if data[child] > data[child + 1]:
                        child += 1
            # 如果子节点大于根节点，交换
            if reverse:  # descending
                if data[root] < data[child]:
                    data[root], data[child] = data[child], data[root]
                    root = child  # 继续向下
                else:  # 子节点小于根节点，跳出
                    break
            else:  # ascending
                if data[root] > data[child]:
                    data[root], data[child] = data[child], data[root]
                    root = child  # 继续向下
                else:  # 子节点小于根节点，跳出
                    break

    # 创建最大堆
    # (length - 2) // 2: 最后一个父节点，从右向左循环调整成堆结构
    for start in range((length - 2) // 2, -1, -1):
        _build_max_heap(start, length - 1)  # length - 1: 保证有两个位置，不越界

    # 堆排序
    for end in range(length - 1, 0, -1):
        data[0], data[end] = data[end], data[0]
        _build_max_heap(0, end - 1)

    return data


@count_time
def heap_sort_debug(data, reverse=False, print_step=False):
    """Heap sort ver.debug

    堆排序
    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)
        print_step (bool) : whether to show sorting steps (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    steps = 0  # 记录操作步数
    swaps = 0  # 记录交换次数

    def _build_max_heap(_start, _end):
        """把``start``到``end``之间的数据 变得符合堆结构

        将堆的末节点进行调整，使子节点永远不大于父节点。
        把``reverse``判断放在外面能加快一点。
        Args:
            _start (int): 指定要变换的开始位置
            _end (int): 指定要变换的终点位置

        Returns:
            None: 只改变``data``数组
        """
        nonlocal steps, swaps
        root = _start  # 根节点位置

        while True:
            steps += 1
            child = 2 * root + 1  # 二叉子节点开始的位置
            if child > _end: break

            # 先比较两个子节点，选择更大的一个child
            if child + 1 <= _end:
                if reverse:  # descending
                    if data[child] < data[child + 1]:
                        child += 1
                else:  # ascending
                    if data[child] > data[child + 1]:
                        child += 1
            # 如果子节点大于根节点，交换
            if reverse:  # descending
                if data[root] < data[child]:
                    swaps += 1
                    data[root], data[child] = data[child], data[root]
                    root = child  # 继续向下
                else:  # 子节点小于根节点，跳出
                    break
            else:  # ascending
                if data[root] > data[child]:
                    swaps += 1
                    data[root], data[child] = data[child], data[root]
                    root = child  # 继续向下
                else:  # 子节点小于根节点，跳出
                    break

    # 创建最大堆
    # (length - 2) // 2: 最后一个父节点，从右向左循环调整成堆结构
    for start in range((length - 2) // 2, -1, -1):
        _build_max_heap(start, length - 1)  # length - 1: 保证有两个位置，不越界

    # 堆排序
    for end in range(length - 1, 0, -1):
        swaps += 1
        data[0], data[end] = data[end], data[0]
        _build_max_heap(0, end - 1)

    print("输入数据长度:", length,
          "执行步数:", steps,
          "操作次数:", swaps)

    return data


#############
# Test Part #
#############
# 调用测试
# print(heap_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(heap_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
heap_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# heap_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# heap_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# heap_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True, print_step=True)

if __name__ == "__main__":
    print("堆排序法::输入数组进行测试")
    while True:
        inp = input()
        print(heap_sort_debug(eval(inp), True))
