# -*- coding: utf-8 -*-
"""
File name: merge_sort
Reference: https://en.wikipedia.org/wiki/Merge_sort
Introduction: 归并排序 O(n*Log(n))
Date: 2016-05-24
Last modified: 2016-06-01
Author: enihsyou
"""
from count_time import count_time_debug, count_time


@count_time
def merge_sort(data):
    """Merge sort ver.recursive

    归并排序，递归调用
    Args:
        data (List[int]): list to sort, need a not None list

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    def _merge(_left, _right):
        if _left == _right:
            return

        # 先分治
        mid = (_left + _right) // 2  # 对半分

        _merge(_left, mid)
        _merge(mid + 1, _right)

        # 交换顺序错了的两个元素
        if _right - _left == 1 and data[_left] < data[_right]:
            data[_left], data[_right] = data[_right], data[_left]

        temp = []  # 合并操作的临时列表
        left_side = _left  # 左半边指针
        right_side = mid + 1  # 右半边指针

        # 进行合并
        # [_left, mid] [mid + 1, _right] 分治的左右范围
        # [left_side, mid] [right_side, _right] 合并操作时的范围
        while left_side <= mid and right_side <= _right:
            if data[left_side] >= data[right_side]:  # 左边元素更大
                temp += [data[left_side]]
                left_side += 1
            else:
                temp += [data[right_side]]  # 右边元素更大
                right_side += 1
        while left_side <= mid:  # 左半边还有剩余元素
            temp += [data[left_side]]
            left_side += 1
        while right_side <= _right:  # 右半边还有剩余元素
            temp += [data[right_side]]
            right_side += 1

        data[_left:_right + 1] = temp  # 切片操作范围 [_left, _right + 1)

    _merge(0, length - 1)  # 开始排序

    return data

@count_time
def merge_sort(data, reverse=False):
    """Merge sort ver.recursive_reverse

    归并排序，递归调用
    支持从小到大排序，可以用[::-1]提升效率

    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    def _merge(_left, _right):
        if _left == _right:
            return

        # 先分治
        mid = (_left + _right) // 2  # 对半分

        _merge(_left, mid)
        _merge(mid + 1, _right)

        # 交换顺序错了的两个元素
        if _right - _left == 1:
            if reverse:
                if data[_left] > data[_right]:
                    data[_left], data[_right] = data[_right], data[_left]
            else:
                if data[_left] < data[_right]:
                    data[_left], data[_right] = data[_right], data[_left]

        temp = []  # 合并操作的临时列表
        left_side = _left  # 左半边指针
        right_side = mid + 1  # 右半边指针

        # 进行合并
        # [_left, mid] [mid + 1, _right] 分治的左右范围
        # [left_side, mid] [right_side, _right] 合并操作时的范围
        while left_side <= mid and right_side <= _right:
            if reverse:
                if data[left_side] <= data[right_side]:  # 左边元素更大
                    temp += [data[left_side]]
                    left_side += 1
                else:
                    temp += [data[right_side]]  # 右边元素更大
                    right_side += 1
            else:
                if data[left_side] >= data[right_side]:  # 左边元素更大
                    temp += [data[left_side]]
                    left_side += 1
                else:
                    temp += [data[right_side]]  # 右边元素更大
                    right_side += 1
        while left_side <= mid:  # 左半边还有剩余元素
            temp += [data[left_side]]
            left_side += 1
        while right_side <= _right:  # 右半边还有剩余元素
            temp += [data[right_side]]
            right_side += 1

        data[_left:_right + 1] = temp  # 切片操作范围 [_left, _right + 1)

    _merge(0, length - 1)  # 开始排序

    return data


@count_time_debug
def merge_sort_debug(data, reverse=False, print_step=False):
    """Merge sort ver.recursive_debug

    归并排序，递归调用，可再优化一下
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

    def _merge(_left, _right):
        nonlocal steps, swaps, comps

        if _left == _right:
            steps += 1
            return

        # 先分治
        mid = (_left + _right) // 2  # 对半分

        _merge(_left, mid)
        _merge(mid + 1, _right)

        # 交换顺序错了的两个元素
        if _right - _left == 1:
            steps += 1
            if reverse:
                comps += 1
                if data[_left] > data[_right]:
                    swaps += 1
                    data[_left], data[_right] = data[_right], data[_left]
            else:
                comps += 1
                if data[_left] < data[_right]:
                    swaps += 1
                    data[_left], data[_right] = data[_right], data[_left]

        temp = []  # 合并操作的临时列表
        left_side = _left  # 左半边指针
        right_side = mid + 1  # 右半边指针

        # 进行合并
        # [_left, mid] [mid + 1, _right] 分治的左右范围
        # [left_side, mid] [right_side, _right] 合并操作时的范围
        while left_side <= mid and right_side <= _right:
            steps += 1
            swaps += 1
            if reverse:
                comps += 1
                if data[left_side] <= data[right_side]:  # 左边元素更大
                    temp += [data[left_side]]
                    left_side += 1
                else:
                    temp += [data[right_side]]  # 右边元素更大
                    right_side += 1
            else:
                comps += 1
                if data[left_side] >= data[right_side]:  # 左边元素更大
                    temp += [data[left_side]]
                    left_side += 1
                else:
                    temp += [data[right_side]]  # 右边元素更大
                    right_side += 1
        while left_side <= mid:  # 左半边还有剩余元素
            steps += 1
            swaps += 1
            temp += [data[left_side]]
            left_side += 1
        while right_side <= _right:  # 右半边还有剩余元素
            steps += 1
            swaps += 1
            temp += [data[right_side]]
            right_side += 1

        data[_left:_right + 1] = temp  # 切片操作范围 [_left, _right + 1)
        if print_step: print(data)

    _merge(0, length - 1)  # 开始排序

    print("输入长度:", length,
          "循环次数:", steps,
          "比较次数:", comps,
          "操作次数:", swaps)

    return data


def merge_sort_i(data, reverse=False):
    """Merge sort ver.iterative

    归并排序，循环方式
    Args:
        data (List[int]): list to sort, need a not None list
        reverse (bool): whether to sort descending (default: False)

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    step = 1
    while step < length:
        start = 0
        while start < length:
            low = start  # 范围开头
            mid = min(start + step, length)  # 中部位置
            hig = min(start + step * 2, length)  # 范围结束

            i, j = low, mid  # 前后两组的两个指针

            tmp = []
            while i < mid and j < hig:
                if reverse:  # 小数字放前面
                    if data[i] > data[j]:
                        tmp, j = tmp + [data[j]], j + 1
                    else:
                        tmp, i = tmp + [data[i]], i + 1
                else:  # 大数字放前面
                    if data[i] > data[j]:
                        tmp, i = tmp + [data[i]], i + 1
                    else:
                        tmp, j = tmp + [data[j]], j + 1
            # 处理剩余数字
            while i < mid:
                tmp += [data[i]]
                i += 1
            while j < hig:
                tmp += [data[j]]
                j += 1

            data[low:hig] = tmp
            start += 2 * step  # 下一组
        step *= 2

    return data


@count_time_debug
def merge_sort_i_debug(data, reverse=False, print_step=False):
    """Merge sort ver.iterative_debug

    归并排序，可再优化一下。
    最内层循环，细分为 最多两个元素一组，对该组进行大小对比交换。
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

    step = 1
    while step < length:
        start = 0
        while start < length:
            low = start  # 范围开头
            mid = min(start + step, length)  # 中部位置
            hig = min(start + step * 2, length)  # 范围结束

            i, j = low, mid  # 前后两组的两个指针

            tmp = []
            while i < mid and j < hig:
                steps += 1
                if reverse:  # 小数字放前面
                    comps += 1
                    if data[i] > data[j]:
                        tmp, j = tmp + [data[j]], j + 1
                    else:
                        tmp, i = tmp + [data[i]], i + 1
                else:  # 大数字放前面
                    comps += 1
                    if data[i] > data[j]:
                        tmp, i = tmp + [data[i]], i + 1
                    else:
                        tmp, j = tmp + [data[j]], j + 1
            # 处理剩余数字
            while i < mid:
                steps += 1
                swaps += 1
                tmp += [data[i]]
                i += 1
            while j < hig:
                steps += 1
                swaps += 1
                tmp += [data[j]]
                j += 1

            data[low:hig] = tmp
            if print_step: print(data)
            swaps += hig - low
            start += 2 * step  # 下一组
        step *= 2

    print("输入长度:", length,
          "循环次数:", steps,
          "比较次数:", comps,
          "操作次数:", swaps)
    return data


#############
# Test Part #
#############
# 调用测试
# print(merge_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1]))
# print(merge_sort([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True))
# 计时测试
# merge_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# merge_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# merge_sort_i_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1])
# merge_sort_i_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True)
# 步骤测试
# merge_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], print_step=True)
# merge_sort_debug([3, 5, 4, 8, 2, 7, 6, 0, 9, 1], True, print_step=True)

if __name__ == "__main__":
    print("归并排序法::输入数组进行测试")
    while True:
        inp = input()
        print(merge_sort_debug(eval(inp), print_step=True))
