# -*- coding: utf-8 -*-
"""Count algorithms' efficiency.

File name: main
Reference:
Introduction: Use random generate_function to count efficiency of algorithms.
Date: 2016-05-20
Last modified: 2016-05-25
Author: enihsyou
"""

import random

import algorithm


def generate_list(number, unique=True):
    """Make list with random integers.

    ``number`` defined how many items in the list,
    all the number in is interval [0, ``number``).
    Args:
        number (int): how many items in the list
        unique (bool): whether to ust unique number (default: True)
    Returns:
        (list[int]): random list
    Raises:
        EXCEPTION (ValueError): we need a Integer greater than 0
    """
    if unique:
        try:
            return random.sample(range(number), number)
        except ValueError as EXCEPTION:
            print("需要一个非负数！")
            raise EXCEPTION
    else:
        try:
            return [random.randrange(number) for _ in range(number)]
        except ValueError as EXCEPTION:
            print("需要一个非负数！")
            raise EXCEPTION


def judge(random_list, debug=False, steps=False, func=None, ranges=""):
    """Run the test.

    Have two ways, one is just run with some useful information printed,
    the other is debug mode, running with wraps.
    Args:
        random_list (list[int]): list to sort
        debug (bool): whether to use debug mode,
                      for showing running time (default: False)
        steps (bool): whether to show steps   (default: False)
        func (function): specific function to use
        ranges (str): whether to set function range
    Returns:
        (list[int]): ordered list
    """
    if func:  # 处理 当有函数名指定的时候
        try:
            command = getattr(getattr(algorithm, func), func + '_debug')
        except AttributeError:
            print("输入的名字不存在！")
            func2 = input("输入名称: ")
            judge(random_list, debug, steps, func2)
            return
        loop_time = _get_loop_time()
        command(random_list[:], False, steps, loop_time)  # False:从大到小

    if ranges:  # 处理 当有函数名范围指定的时候
        try:
            ranges = getattr(algorithm, ranges)
        except AttributeError:
            print("输入的名字不存在！")
            ranges = input("输入名称: ")
            judge(random_list, debug, steps, func, ranges)
            return

    else:  # 若没有指定，使用全部
        ranges = algorithm.__all__

    loop_time = 0
    if debug: loop_time = _get_loop_time()

    for algo in ranges:
        if debug:
            command = getattr(getattr(algorithm, algo), algo + '_debug')
            command(random_list[:], False, steps, loop_time)  # False:从大到小
        else:
            command = getattr(getattr(algorithm, algo), algo)
            print("调用函数: {}\n"
                  "有序列表: \n{}\n".format(command.__name__,
                                        command(random_list[:])))


def test_algo(choice):
    """Test all the algorithms.

    For choice ``1``: just print generated random integer list.
    For choice ``2``: test all algorithms with same list, ordered list printed.
    For choice ``3``: use debug mode to print calculate time.
    For choice ``4``: not only calculate running time, also show steps.

    Args:
        choice (int): number chose
    """

    def _t_size():
        user_inp = input("数组大小: ")
        try:
            user_inp = int(user_inp)  # 转换成整数
        except ValueError:  # 如果输入的不是整数 跳过
            if user_inp != 'q':
                main()
                return None
            else:
                return None
        return user_inp

    while choice is not None:
        if choice == 1:
            inp = _t_size()
            if inp is None: break
            ranlist = generate_list(inp)
            print("待排列表: \n{}\n".format(ranlist))
            continue
        else:
            print("1: 所有算法\n"
                  "2: O(n^2)\n"
                  "3: O(n*Log(n)\n"
                  "4: 手动输入一个")
            user_choice = _user_choice(range(4 + 1))

            inp = _t_size()
            if inp is None: break
            ranlist = generate_list(inp)
            if choice == 2:
                if user_choice == 1:
                    judge(ranlist[:])
                elif user_choice == 2:
                    judge(ranlist[:], ranges="n2")
                elif user_choice == 3:
                    judge(ranlist[:], ranges="nlogn")
                elif user_choice == 4:
                    algo = input("输入名称: ")
                    judge(ranlist[:], func=algo)
            if choice == 3:
                if user_choice == 1:
                    judge(ranlist[:], debug=True)
                elif user_choice == 2:
                    judge(ranlist[:], debug=True, ranges="n2")
                elif user_choice == 3:
                    judge(ranlist[:], debug=True, ranges="nlogn")
                elif user_choice == 4:
                    algo = input("输入名称: ")
                    judge(ranlist[:], debug=True, func=algo)
            if choice == 4:
                if user_choice == 1:
                    judge(ranlist[:], debug=True, steps=True, )
                elif user_choice == 2:
                    judge(ranlist[:], debug=True, steps=True, ranges="n2")
                elif user_choice == 3:
                    judge(ranlist[:], debug=True, steps=True, ranges="nlogn")
                elif user_choice == 4:
                    algo = input("输入名称: ")
                    judge(ranlist[:], debug=True, steps=True, func=algo)


def _user_choice(ranges=None):
    """Try to return chose number."""
    user_choice = input(">>> ")
    if user_choice == 'q': return None
    try:
        user_choice = int(user_choice)
        if ranges and user_choice not in ranges: raise ValueError
    except ValueError:
        if user_choice.isnumeric():
            if user_choice != 'q': return None
        print("错误的输入，应该是个正整数！")
        return _user_choice(ranges)
    return user_choice


def _get_loop_time():
    try:
        loop_time = int(input("循环次数: "))
    except ValueError:
        print("不合理的输入！")
        _get_loop_time()
        return
    return loop_time


def main(user_choice=None):
    """Main function for testing

    Args:
        user_choice (int): predefined choice number
    Returns:
        None: quit if ``q`` is typed
    Raises:
        ValueError: undefined input
    """
    print("1: 测试随机数列表生成器\n"
          "2: 测试排序算法\n"
          "3: 测试排序算法 (debug)\n"
          "4: 测试排序算法 (steps)\n"
          "q: 输入'q'结束\n")
    if not user_choice:
        user_choice = _user_choice(range(4 + 1))

    test_algo(user_choice)


if __name__ == "__main__":
    main()
