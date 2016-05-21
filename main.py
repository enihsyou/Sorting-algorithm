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
        (list[int]): 随机数列表

    """
    if unique:
        return random.sample(range(number), number)
    else:
        return [random.randrange(number) for _ in range(number)]


def judge(random_list, debug=False, steps=False):
    """
    执行测试

    Args:
        random_list (list[int]): 被排序的列表
        debug (bool): 是否启用debug模式 (default: False)
        steps (bool): 是否显示每步 (default: False)
    Returns:
        (list[int[): 有序列表
    """
    for algo in algorithm.__all__:
        if debug:
            command = getattr(getattr(algorithm, algo), algo + '_debug')
            return command(random_list, False, steps)
        else:
            command = getattr(getattr(algorithm, algo), algo)
            return command(random_list)


def main():
    """
    测试用的主函数
    """
    print("1: 测试随机数列表生成器\n"
          "2: 测试所有排序算法\n"
          "3: 测试所有排序算法 (debug)\n"
          "4: 测试所有排序算法 (steps)\n")

    user_choice = input(">>> ")
    try:
        test_algo(user_choice)
    except ValueError:
        print("错误的输入")


if __name__ == '__main__':
    def test_algo(choice):
        """
        测试所有排序算法
        Args:
            choice (str[int]): 选择的第几项
        """
        while True:
            inp = input("\n数组大小: ")
            ranlist = []  # 初始化列表
            try:
                inp = int(inp)  # 转换成整数
                ranlist = generate_list(inp)
            except ValueError:  # 如果输入的不是整数 跳过
                main()
                break
            if choice == '1':
                print("待排列表: \n{}".format(ranlist))
                continue
            else:
                print("待排列表: \n{}".format(ranlist))
            if choice == '2':
                print("有序列表: \n{}".format(judge(ranlist)))
            elif choice == '3':
                # print("有序列表: \n", judge(ranlist, debug=True))
                judge(ranlist, debug=True)
            elif choice == '4':
                # print("有序列表: \n", judge(ranlist, debug=True, steps=True))
                judge(ranlist, debug=True, steps=True)


    main()
