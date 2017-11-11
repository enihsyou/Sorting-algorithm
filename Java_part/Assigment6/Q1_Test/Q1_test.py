# -*- codine: UTF-8 -*-
from random import choice, random
from time import sleep

import binarytree
import string

import sys

OPERATORS = ('+', '-', '*', '/')
NUMBERS = string.ascii_lowercase
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


def get_precedence(char:str)->int:
    if char =='+' or char == '-':
        return 1
    if char =='*' or char == '/':
        return 2
    return 0


def get_lowest(dic: dict) -> int:
    if dic['+'] or dic['-']:
        return 1
    if dic['*'] or dic['/']:
        return 2

    return 0


def remove_parentheses(s: str, begin: int = 0, _stack=[]):
    left_side_operator = None  # 记录在当前处理字符之前遇到的那个运算符
    local_operators = {k: 0 for k in OPERATORS}  # 记录当前括号上下文中操作符的数量
    pointer = begin
    remove = []
    while pointer < len(s):
        char = s[pointer]
        pointer += 1  # 指向下一个
        if char in NUMBERS:
            pass
        elif char in OPERATORS:
            left_side_operator = char  # 利用局部变量记录上下文中的前一个操作符
            local_operators[char] += 1
        elif char == '(':  # 遇到左括号 利用调用栈保存当前状态，进入下一个括号的范围
            _stack.append(pointer)
            inner_operators, pointer, l_begin, pp = remove_parentheses(s, pointer)
            remove += pp
            """
            we know that parentheses are needed when at least one of the following conditions is true:
            
            - There is an operator with higher precedence than the lowest precedence operator 
            inside the parenthesized expression either to the left or to the right of the parenthesized expression.
            - There is a - operator to the left of the parenthesized expression, 
            and the parenthesized expression contains at least a + or -.
            - There is a / operator to the left of the parenthesized expression
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
            except IndexError as e:
                raise ValueError("括号不匹配") from e
            return local_operators, pointer, begin, remove
        else:
            raise NotImplementedError(char)
    for i, c in enumerate(s):
        if i not in remove:
            print(c, end='')
    print(flush=True)


remove_parentheses("(((q-o)*d)/i)")
remove_parentheses("((x-m)-f)")

my_null = None
binarytree.customize(
        node_init=lambda val: Node(val, my_null, my_null),
        node_class=Node,
        null_value=my_null,
        value_attr='value',
        left_attr='left',
        right_attr='right'
)
while 1:
    root = Node(generate_operator())
    make_node(root, 0)
    # root.print()
    s = root.str()
    print(s)
    remove_parentheses(s)
    sys.stdout.flush()
    # my_custom_tree = root
    # binarytree.show(my_custom_tree)

    sleep(1)
