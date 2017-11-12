# -*- coding: UTF-8 -*-
"""
2）编写程序打印如下图所示的n*n方阵
1 1 1 1 1 1
1 2 2 2 2 1
1 2 3 3 2 1
1 2 3 3 2 1
1 2 2 2 2 1
1 1 1 1 1 1
"""
import math

if __name__ == '__main__':
    while 1:
        N = input("N: ")
        try:
            n = int(N)
            if n < 0: raise ValueError
        except ValueError:
            continue
        else:
            print("\n".join(
                    "".join(["{:<{align}}".format(min(i + 1, j + 1, n - i, n - j), align=2 + int(math.log10(n / 2)))
                             for i in range(n)])
                    for j in range(n)))
