# -*- coding: utf-8 -*-
"""Count algorithms' efficiency.

File name: main
Reference:
Introduction: Use random generate_function to count efficiency of algorithms.
Date: 2016-05-20
Last modified: 2016-05-23
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


def judge(random_list, debug=False, steps=False):
    """Run the test.

    Have two ways, one is just run with some useful information printed,
    the other is debug mode, running with wraps.
    Args:
        random_list (list[int]): list to sort
        debug (bool): whether to use debug mode,
                      for showing running time (default: False)
        steps (bool): whether to show steps   (default: False)
    Returns:
        (list[int[): ordered list
    """
    for algo in algorithm.__all__:
        if debug:
            command = getattr(getattr(algorithm, algo), algo + '_debug')
            command(random_list[:], False, steps)  # False:从大到小
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
    while True:
        inp = input("数组大小: ")

        try:
            inp = int(inp)  # 转换成整数
            ranlist = generate_list(inp)
        except ValueError:  # 如果输入的不是整数 跳过
            if inp != 'q': main()
            break

        if choice == 1:
            print("待排列表: \n{}\n".format(ranlist))
            continue
        else:
            print("待排列表: \n{}\n".format(ranlist))
        if choice == 2:
            judge(ranlist)
        elif choice == 3:
            # print("有序列表: \n", judge(ranlist, debug=True))
            judge(ranlist, debug=True)
        elif choice == 4:
            # print("有序列表: \n", judge(ranlist, debug=True, steps=True))
            judge(ranlist, debug=True, steps=True)


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
          "2: 测试所有排序算法\n"
          "3: 测试所有排序算法 (debug)\n"
          "4: 测试所有排序算法 (steps)\n"
          "q: 输入'q'结束\n")

    if not user_choice:
        user_choice = input(">>> ")
    if user_choice == 'q': return None

    try:
        user_choice = int(user_choice)
        if not 0 < user_choice < 5: raise ValueError
    except ValueError:
        print("错误的输入\n")
        main()
    test_algo(user_choice)


if __name__ == "__main__":
    main()
