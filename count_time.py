"""
File name: step_by_step
Reference: http://www.cnblogs.com/rhcad/archive/2011/12/21/2295507.html
Introduction: 作为装饰器 输出函数单次执行的时间
Time: 2016-05-16
By: enihsyou
"""
import time


def count_time(func):
    def _deco(data, reverse = False):
        start_time = time.perf_counter()
        func(data, reverse)
        end_time = time.perf_counter()
        print("执行时间:", (end_time - start_time))

    return _deco
