# -*- coding: UTF-8 -*-
"""
第6次作业：
1）键盘输入一个含有括号的四则运算表达式，可能含有多余的括号。编写一个算法整理该表达式，
去掉所有多余的括号。原表达式中所有变量和运算符相对位置保持不变，并保持与原表达式等价。
例：输入表达式 应输出表达式
a+(b+c) a+b+c
(a*b)+c/d a*b+c/d
a+b/(c-d) a+b/(c-d)
"""
from random import choice, random
import string

OPERATORS = ('+', '-', '*', '/')
NUMBERS = string.ascii_letters + string.digits
P = 0.5


class Node:
    def __init__(self, value: str, left=None, right=None):
        self.value = value  # type: str
        self.left = left  # type: Node
        self.right = right  # type: Node

    def __str__(self):
        return str(self.value)

    def print(self):
        s = self.value
        if s in NUMBERS:
            print(s, end='')
        elif s in OPERATORS:
            print('(', end='')
            self.left.print(), print(s, end=''), self.right.print()
            print(')', end='')
        else:
            raise NotImplementedError(s)

    def str(self, parent: str = ""):
        s = self.value
        if s in NUMBERS:
            return parent + s
        elif s in OPERATORS:
            return "({}{}{})".format(self.left.str(parent), s, self.right.str(parent))
        else:
            raise NotImplementedError(s)


def generate_number():
    return choice(NUMBERS)


def generate_operator():
    return choice(OPERATORS)


def make_node(parent: Node, depth: int):
    if parent is None or parent.value in NUMBERS:
        return
    if depth >= 10:
        parent.left = Node(generate_number())
        parent.right = Node(generate_number())
        return
    assert parent.value in OPERATORS
    if random() < P:  # 在左边生成操作符
        parent.left = Node(generate_operator())
        make_node(parent.left, depth + 1)
    else:
        parent.left = Node(generate_number())

    if random() < P:  # 在右边生成操作符
        parent.right = Node(generate_operator())
        make_node(parent.right, depth + 1)
    else:
        parent.right = Node(generate_number())


def get_precedence(char: str) -> int:
    if char == '+' or char == '-':
        return 1
    if char == '*' or char == '/':
        return 2
    return 0


def get_lowest(dic: dict) -> int:
    if dic['+'] or dic['-']:
        return 1
    if dic['*'] or dic['/']:
        return 2

    return 0


# noinspection PyDefaultArgument
def remove_parentheses(s: str, begin: int = 0, _stack=[]):  # _stack作为函数的域变量，记录括号匹配情况
    left_side_operator = None  # 记录在当前处理字符之前遇到的那个运算符
    local_operators = {k: 0 for k in OPERATORS}  # 记录当前括号上下文中操作符的数量
    pointer = begin
    remove = []
    while pointer < len(s):
        char = s[pointer]
        pointer += 1  # 指向下一个
        if char in NUMBERS:
            pass
        elif char in string.whitespace:
            pass
        elif char in OPERATORS:
            left_side_operator = char  # 利用局部变量记录上下文中的前一个操作符
            local_operators[char] += 1
        elif char == '(':  # 遇到左括号 利用调用栈保存当前状态，进入下一个括号的范围
            _stack.append(pointer)
            inner_operators, pointer, l_begin, pp = remove_parentheses(s, pointer)
            remove += pp  # pp是临时变量 只是保存个返回结果
            """
            we know that parentheses are needed when at least one of the following conditions is true:
            
            - There is an operator with higher precedence than the lowest precedence operator 
            inside the parenthesized expression either to the left or to the right of the parenthesized expression.
            - There is a - operator to the left of the parenthesized expression, 
            and the parenthesized expression contains at least a + or -.
            - There is a / operator to the left of the parenthesized expression.
            """
            if left_side_operator is not None \
                    and (left_side_operator == '-' and inner_operators['+'] + inner_operators['-']
                         or left_side_operator == '/'
                         or get_precedence(left_side_operator) > get_lowest(inner_operators)) \
                    or (pointer < len(s) and s[pointer] in OPERATORS and get_precedence(s[pointer]) > get_lowest(
                            inner_operators)):
                pass
            else:
                remove += (l_begin - 1, pointer - 1)
        elif char == ')':  # 从当前括号环境中跳出来返回上一个括号环境，返回当前括号中的符号统计
            try:
                _stack.pop()
            except IndexError as error:
                _stack.clear()
                raise ValueError("'%s'的右括号不匹配" % s) from error
            if not any(local_operators.values()):
                remove += (begin - 1, pointer - 1)
            return local_operators, pointer, begin, remove
        else:
            raise NotImplementedError(char)
    if len(_stack) != 0 or len(remove) % 2 != 0:
        _stack.clear()
        raise ValueError("'%s'的左括号不匹配" % s)
    result = "".join([c for i, c in enumerate(s) if i not in remove])
    return result


"""测试代码"""
# remove_parentheses("(((q-o)*d)/i)")
# remove_parentheses("((x-m)-f)")
# try:
#     import binarytree
# except ImportError:
#     pass
# from time import sleep
# import sys
# my_null = None
# binarytree.customize(
#         node_init=lambda val: Node(val, my_null, my_null),
#         node_class=Node,
#         null_value=my_null,
#         value_attr='value',
#         left_attr='left',
#         right_attr='right'
# )
# while 1:
#     root = Node(generate_operator())
#     make_node(root, 0)
#     # root.print()
#     s = root.str()
#     print(s)
#     remove_parentheses(s)
#     sys.stdout.flush()
#     # my_custom_tree = root
#     # binarytree.show(my_custom_tree)
#
#     sleep(1)
if __name__ == '__main__':
    while 1:
        s = input("输入算式: ")
        try:
            r = remove_parentheses(s)
        except ValueError as e:
            print(e)
        else:
            print(r)
