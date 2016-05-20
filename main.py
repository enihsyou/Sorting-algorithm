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

import algorithm


def generate_list(number, unique=True):
    """
    返回一个由随机数组成的列表

    列表元素个数由``number``指定，数值范围在[0, ``number``)。
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


def judge(random_list):
    """
    执行测试

    Args:
        random_list (list): 被排序的列表
    """
    for algo in algorithm.__all__:
        command = "algorithm.{}_debug({})".format(algo, random_list)
        return eval(command)


if __name__ == '__main__':

    def test_list():
        """
        测试随机数列表生成器
        """
        print("输入数字生成列表")
        while True:
            inp = input("数组大小: ")
            try:
                inp = int(inp)  # 转换成整数
                print(generate_list(inp))
            except ValueError:  # 如果输入的不是整数 跳过
                pass


    def test_algo():
        """
        测试所有排序算法
        """
        print("输入列表大小")
        while True:
            inp = input("\n数组大小: ")
            try:
                inp = int(inp)  # 转换成整数
                ranlist = generate_list(inp)
                print("待排列表: \n", ranlist)
                print("有序列表: \n", judge(ranlist))
            except ValueError:  # 如果输入的不是整数 跳过
                pass


    func = {1: test_list,
            2: test_algo}
    print("1: 测试随机数列表生成器\n"
          "2: 测试所有排序算法\n")
    user_choice = input()
    try:
        func[int(user_choice)]()
    except ValueError:
        print("错误的输入")
