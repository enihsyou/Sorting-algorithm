# -*- coding: utf-8 -*-
"""
生成随机列表，测试排序算法效率

File name: main
Reference: 
Introduction: 使用随机函数生成随机列表，调用排序算法多次测试 统计时间效率
Time: 2016-05-20
Last modified: 2016-05-20
By: enihsyou
"""

import random


def generate_list(number, unique=True):
    """
    返回一个由随机数组成的列表

    列表元素个数由``number``指定，数值范围在[0, ``numebr``)。
    Args:
        number (int): 列表元素个数
        unique (bool): 数字是否唯一 (default: True)
    Returns:
        (list): 随机数列表

    """
    if unique:
        return random.sample(range(number), number)
    else:
        return [random.randrange(number) for _ in range(number)]


"""
Test Part
=========
"""
# print(generate_list(10))
# print(generate_list(10))
# print(generate_list(10))
# print(generate_list(10))
# print(generate_list(10, False))
# print(generate_list(10, False))
# print(generate_list(10, False))
# print(generate_list(10, False))

if __name__ == '__main__':
    print("输入数字生成列表")
    while True:
        inp = input()
        try:
            inp = int(inp)  # 转换成整数
            print(generate_list(inp))
        except ValueError:  # 如果输入的不是整数 跳过
            pass
