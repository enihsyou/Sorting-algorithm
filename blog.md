# 用Python来写各种各样的排序算法


刚开始学习Python的时候，就想着如何高效地完成计算。凭借着Python易懂的语法规则和清晰的思路展示，他成了我进行算法初步试验的不二选择。
## 准备工作
首先当然是需要学会基础的Python语法啦，按照官方文档，各处教程零零散散的也有，完整课程的也有，利用起空闲时间学习。学习曲线很平缓，而且成就感很高，对初学者特别友好。

其次，我们需要一个能够编辑和运行Python文件的工具，我现在在Windows平台上用着[Python3.5][]，虽然Python2和Python3.x的语法有些许差别，但编程重要的是思想，编程语言只是我们的工具，就像中文英语一样，都能表达我们的意思。对于IDE，安装完Python后自动赠送一个IDLE，不过我喜欢用Jetbrains家的[Pycharm][]。主要因为她特别智能，能够满足我对一个IDE的几乎全部要求。

接下来我们就只需要带上脑子和一些时间就行了。

[Python3.5]: https://www.python.org/ "Python3.5"
[Pycharm]: https://www.jetbrains.com/pycharm/ "Pycharm"

## 获取知识
从[wikipedia][]上我们可以知道有好多好多有效的算法，这些都是人类的智慧结晶呐。
从文档中我们可以知道: 对算法效率的评判手段有 用[大O符号][2]标记的时间复杂度，空间复杂度; 稳定性; 数据操作频率 这几个概念。当然复杂度越低，算法的效率越高; 稳定性可以保证数据的相对顺序不被改变; 而数据操作频率 则是对要排序的数据操作了多少次。
一般而言，理想情况下排序算法的性能是 $O(n)$，当然这只是理想，平均来说最高的能达到 $O (n \log n)$ 的性能，最坏是 $O(n^2)$。对于额外空间使用率来说，最好的能实现原地排序，即不需要额外空间，而有些算法却需要大小为 $n$ 的额外空间，这对于大量的数据来说可不是个好消息。

常见的有:

* [冒泡排序][] Bubble sort
* [选择排序][] Selection sort
* [插入排序][] Insertion sort
* [堆排序][] Heap sort
* [归并排序][] Merge sort
* [快速排序][] Quick sort
* [希尔排序][] Shell sort
* [桶排序][] Bucket sort
* ···

在以后的文章中都会说明到

[wikipedia]: https://en.wikipedia.org/wiki/Sorting_algorithm "Sorting algorithm"
[2]: https://en.wikipedia.org/wiki/Big_O_notation "大O符号"
[冒泡排序]: https://zh.wikipedia.org/wiki/%E6%8F%92%E5%85%A5%E6%8E%92%E5%BA%8F "冒泡排序"
[选择排序]: https://zh.wikipedia.org/wiki/%E9%80%89%E6%8B%A9%E6%8E%92%E5%BA%8F "选择排序"
[插入排序]: https://zh.wikipedia.org/wiki/%E6%8F%92%E5%85%A5%E6%8E%92%E5%BA%8F "插入排序"
[堆排序]: https://zh.wikipedia.org/wiki/%E5%A0%86%E6%8E%92%E5%BA%8F "堆排序"
[归并排序]: https://zh.wikipedia.org/wiki/%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F "归并排序"
[快速排序]: https://zh.wikipedia.org/wiki/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F "快速排序"
[希尔排序]: https://zh.wikipedia.org/wiki/%E5%B8%8C%E5%B0%94%E6%8E%92%E5%BA%8F "希尔排序"
[桶排序]: https://zh.wikipedia.org/wiki/%E6%A1%B6%E6%8E%92%E5%BA%8F "桶排序"

## 生成随机数据
知道了基础的信息，掌握了基本技能之后就该开始真刀真枪地干了！

### 随机数列表
首先我们需要一个由随机数组成的数组列表。最简单直接地，Python官方标准库中有个叫[random][]的用来生成随机数的库，很明显就是为我们准备的。
在我们需要她的时候只需要简单地`import random`就行。现在初始阶段，只是简单地需要一些随机正整数，强大的Python提供了[列表推导式][list_comprehensions]来实现一行生成一个列表的功能。我们创建一个名为`generate_list`的函数来完成这个经常会被调用的操作。

```python
import random
def generate_list(number):
"""生成由`number`个 范围在[0, number)的元素组成的列表"""
    return [random.randrange(number) for _ in range(number)]
```
很简单的一行就能生成我们需要的数组了，当然效率在数目小的时候还是能够接受的，有挺大的优化空间的。

[random]: https://docs.python.org/3/library/random.html "random"
[list_comprehensions]: https://docs.python.org/3.5/tutorial/datastructures.html#list-comprehensions "列表推导式"

###计时器
然后我们需要一个统计执行时间的小函数
有了随机数生成器，接下来还需要一个小秒表。在手上掐着一块表的方式肯定不现实，我们有计算机这么个强大的工具为什么不去使用呢！刚好Python官方也有一个库叫[time][]赶紧的`import time`. 这里面还有一些关于计时器如何选择的讨论，我们使用平台上精度最高的`time.perf_counter`.

同时为了将这个计时器应用到每个算法函数上:

* 可以将计时器装饰到每个算法上
* 可以在计时器函数上调用所有算法
* 可以让算法函数调用这个计时器

我认为第一个会更方便些，就是你了！

```python
import time
def count_time(func):
    """计时器装饰器"""
    def _deco(data):
        start_time = time.perf_counter()
        result = func(data)
        end_time = time.perf_counter()
        print("执行时间:", end_time - start_time)
        return result
    return _deco
```

这小家伙现在已经能够统计时间了，虽然改进的地方有很多，我们以后再来改进它。

[time]: https://docs.python.org/3/library/time.html#time.perf_counter "time"

---
准备工作完成后，接下来就是正戏了！




------------------------------------------------------------------------------

##$O(n^2)$系列
一般人第一次接触到的排序算法应该都是$O(n^2)$等级的吧。常见的有 冒泡排序，选择排序，插入排序···

###冒泡排序 Bubble sort
冒泡排序是利用两层循环 不断地将数据中比较大的数字交换到最上面来，看上去就像水中的气泡冒出来一样。

想法很简单，实现起来也是如此。循环中，看到循序不一样的就交换一次。

```python
def bubble_sort(data):
    """Bubble sort

    Args:
        data (List[int]): 待排列表

    Returns:
        List[int]: 有序列表
    """
    length = len(data)

    for i in range(length):
        for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
            if data[i] < data[j]:  # 从大到小 如果反了交换
                data[i], data[j] = data[j], data[i]

    return data
```

是不是很直接，当然效率也是很感人···
在我这个小奔腾上面进行, 进行10000个数据的排序就要10秒左右(9.5665s)。

在第13行上，`range(i + 1, length)` 循环从`i + 1`开始而不是从`0`或者`i`开始，是为了提高效率，因为我们知道第一层循环是从第一个元素开始的，开头的`i`个元素是有序的，就不需要进行大小比较了，而第`range(x, y)`的循环范围是[x, y)，第`i`个不需要重复比较，因为就是它本身。可以看到Python本身神奇的**tuple assignment**功能用在了交换数组数据上面，简单清晰。


总共需要进行$\underbrace{1+2+3+\text{...}+(n-1)}_{n-1}=\frac{1}{2} n (n-1)$
这么多次排序操作，二次函数增长可是很快的。

我们可以在每次循环结束的时候添加一个打印操作，显示当前进行的状态，比如这是10个元素`[3, 5, 4, 8, 2, 7, 6, 0, 9, 1]`的排序过程，每次交换输出一行:
```
调用函数: bubble_sort
[5, 3, 4, 8, 2, 7, 6, 0, 9, 1]
[8, 3, 4, 5, 2, 7, 6, 0, 9, 1]
[9, 3, 4, 5, 2, 7, 6, 0, 8, 1]
[9, 4, 3, 5, 2, 7, 6, 0, 8, 1]
[9, 5, 3, 4, 2, 7, 6, 0, 8, 1]
[9, 7, 3, 4, 2, 5, 6, 0, 8, 1]
[9, 8, 3, 4, 2, 5, 6, 0, 7, 1]
[9, 8, 4, 3, 2, 5, 6, 0, 7, 1]
[9, 8, 5, 3, 2, 4, 6, 0, 7, 1]
[9, 8, 6, 3, 2, 4, 5, 0, 7, 1]
[9, 8, 7, 3, 2, 4, 5, 0, 6, 1]
[9, 8, 7, 4, 2, 3, 5, 0, 6, 1]
[9, 8, 7, 5, 2, 3, 4, 0, 6, 1]
[9, 8, 7, 6, 2, 3, 4, 0, 5, 1]
[9, 8, 7, 6, 3, 2, 4, 0, 5, 1]
[9, 8, 7, 6, 4, 2, 3, 0, 5, 1]
[9, 8, 7, 6, 5, 2, 3, 0, 4, 1]
[9, 8, 7, 6, 5, 3, 2, 0, 4, 1]
[9, 8, 7, 6, 5, 4, 2, 0, 3, 1]
[9, 8, 7, 6, 5, 4, 3, 0, 2, 1]
[9, 8, 7, 6, 5, 4, 3, 2, 0, 1]
[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
输入长度: 10 循环次数: 45 比较次数: 45 操作次数: 22
```
这是从大到小排序的，我们还可以添加一个`reverse`参数来让函数返回一个从小到大排序的列表。

比如将循环体改成这样
```python
for i in range(length):
    for j in range(i + 1, length):  # 从i + 1开始 减少循环次数
        if data[i] < data[j]:  # 从大到小 如果反了交换
            if reverse: continue
            data[i], data[j] = data[j], data[i]
        elif reverse:  # 如果数据从小到大
            data[i], data[j] = data[j], data[i]
```
巧妙地利用了`if/else`来控制了判断情况。但是效率不佳，因为每次循环都需要进行`if`的判断，消耗了时间。还不如在结束的时候`return data[::-1] if reverse else data`
，这样效率还来得高。



###鸡尾酒排序 Cocktail shaker sort
这其实是一个冒泡排序的改进版本，通过从左到右再从右到左，
```python
def cocktail_shaker_sort(data):
    """Cocktail shaker sort

    使用 ``swapped`` 在必要时候提前结束。

    Args:
        data (List[int]): 待排列表

    Returns:
        List[int]: 有序列表
    """
    length = len(data)

    left, right = 0, length - 1  # 有序序号 左边和右边

    while left < right:
        swapped = False  # 是否一遍过去有没有改变 没有即 已有序
        for i in range(left, right):
            if data[i] < data[i + 1]:  # 与后面一个元素对比
                data[i], data[i + 1] = data[i + 1], data[i]
                swapped = True
        right -= 1
        if not swapped: break
        for i in range(right, left, -1):  # 与前面一个元素对比
            if data[i - 1] < data[i]:
                data[i], data[i - 1] = data[i - 1], data[i]
                swapped = True
        left += 1
        if not swapped: break

    return data
```
和上面相同的一组数据`[3, 5, 4, 8, 2, 7, 6, 0, 9, 1]`，可以看到对列表的交换次数没有变化，但循环次数减少了一点，这是因为数据已经提前有序了，程序提前结束了。然而效率却更低了，10000个数据花了13s(12.8131s)，

大的数字一次次地向左走，小的数字一次次地向右走…
```
调用函数: cocktail_shaker_sort
[5, 3, 4, 8, 2, 7, 6, 0, 9, 1]
[5, 4, 3, 8, 2, 7, 6, 0, 9, 1]
[5, 4, 8, 3, 2, 7, 6, 0, 9, 1]
[5, 4, 8, 3, 7, 2, 6, 0, 9, 1]
[5, 4, 8, 3, 7, 6, 2, 0, 9, 1]
[5, 4, 8, 3, 7, 6, 2, 9, 0, 1]
[5, 4, 8, 3, 7, 6, 2, 9, 1, 0]
[5, 4, 8, 3, 7, 6, 9, 2, 1, 0]
[5, 4, 8, 3, 7, 9, 6, 2, 1, 0]
[5, 4, 8, 3, 9, 7, 6, 2, 1, 0]
[5, 4, 8, 9, 3, 7, 6, 2, 1, 0]
[5, 4, 9, 8, 3, 7, 6, 2, 1, 0]
[5, 9, 4, 8, 3, 7, 6, 2, 1, 0]
[9, 5, 4, 8, 3, 7, 6, 2, 1, 0]
[9, 5, 8, 4, 3, 7, 6, 2, 1, 0]
[9, 5, 8, 4, 7, 3, 6, 2, 1, 0]
[9, 5, 8, 4, 7, 6, 3, 2, 1, 0]
[9, 5, 8, 7, 4, 6, 3, 2, 1, 0]
[9, 8, 5, 7, 4, 6, 3, 2, 1, 0]
[9, 8, 7, 5, 4, 6, 3, 2, 1, 0]
[9, 8, 7, 5, 6, 4, 3, 2, 1, 0]
[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
输入长度: 10 循环次数: 42 比较次数: 42 操作次数: 22
```
可以改进一下 让它只是标记最大(最小)的数据的序号而不是直接进行交换 来提升效率。


###选择排序 Selection sort
选择排序也是一种简单直接的排序方式，每一次循环从乱序部分中寻找出最大的那个数字，交换到有序部分的最后去，直到列表都有序。

```python
def selection_sort(data):
    """Selection sort

    Args:
        data (List[int]): 待排列表

    Returns:
        List[int]: 有序列表
    """
    length = len(data)

    for i in range(length):
        index = i  # 最大或最小的序号
        for j in range(i + 1, length):
            if data[j] > data[index]:  # 如果当前值大于循环中最大的记录
                index = j

        # 进行交换
        if index == i: continue  # 如果当前位正确 不必交换
        data[index], data[i] = data[i], data[index]

    return data
```

在第13行使用了一个`index`变量来记录当前的位置，用于在第19行与`i`进行比较 避免多余的交换操作。

总共的运行次数和冒泡排序一样，$\frac{1}{2} n (n-1)$次，但是由于交换次数少得多了，速度也就更快了。
10000个数据大概需要6秒(6.2190s) 很明显的提升。
```
调用函数: selection_sort
[9, 5, 4, 8, 2, 7, 6, 0, 3, 1]
[9, 8, 4, 5, 2, 7, 6, 0, 3, 1]
[9, 8, 7, 5, 2, 4, 6, 0, 3, 1]
[9, 8, 7, 6, 2, 4, 5, 0, 3, 1]
[9, 8, 7, 6, 5, 4, 2, 0, 3, 1]
[9, 8, 7, 6, 5, 4, 3, 0, 2, 1]
[9, 8, 7, 6, 5, 4, 3, 2, 0, 1]
[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
输入长度: 10 循环次数: 45 比较次数: 45 操作次数: 8
```
当然对数组的交换次数小于等于数组个数减一。

###插入排序 Insertion sort
插入排序也是一种很直接的排序方式，同时也是很低效的一种。

它的思想就是 将每一个无序部分的元素插入到有序部分中。在数组元素比较少的时候，用几行就能实现的这个算法看上去很简洁，但一旦数量上去了，每一轮插入需要比较的次数越来越多，拖慢了速度。

插入算法的实现有递归法和迭代法。然而由于递归的方式每一次插入都是一层递归，很快就会触及Python的最大递归层数限制，我们就使用迭代循环的方式。
```python
def insertion_sort(data):
    """Insertion sort

    Args:
        data (List[int]): 无序列表

    Returns:
        List[int]: 有序列表
    """
    length = len(data)  # 输入元素个数

    for i in range(1, length):  # 假设第一个元素已经有序
        now_num = data[i]  # 将要插入的元素
        j = 0  # while循环的指针，指向当前循环中比较的元素的序号

        # 从小到大 如果待排元素大于有序列表中的当前元素 找到该插入的位置
        while data[j] > now_num and j < i:
            j += 1
        if j == i: continue  # 如果当前位正确 不必交换
        data = data[:j] + [now_num] + data[j:i] + data[i + 1:]

    return data
```
在第20行，利用Python的切片功能将元素插入。
不过`list`自身有`list.insert()`的方法，也可以实现，两种方法的效率有待对比。

```
调用函数: insertion_sort
[5, 3, 4, 8, 2, 7, 6, 0, 9, 1]
[5, 4, 3, 8, 2, 7, 6, 0, 9, 1]
[8, 5, 4, 3, 2, 7, 6, 0, 9, 1]
[8, 7, 5, 4, 3, 2, 6, 0, 9, 1]
[8, 7, 6, 5, 4, 3, 2, 0, 9, 1]
[9, 8, 7, 6, 5, 4, 3, 2, 0, 1]
[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
输入长度: 10 循环次数: 23 比较次数: 32 操作次数: 7
```
对于10000个元素，随手测试一下，插入排序的速度甚至比选择排序还要快一些(5.7829s)。

-------------------------------------------------------------------------------

接下来我们可以给这几种常见的$O(n^2)$算法排个序了。
通过修改一下之前的`count_time`我们能够生成一个统计表:
```python
def count_time(func):
    """Timer decorator."""
    @wraps(func)
    def _deco(data, reverse=False, loop_times=1):
        """print running time."""
        result = []  # ordered list
        print("调用函数:", func.__name__)
        time_log = []

        for i in range(loop_times):
            if func.__name__.endswith("reverse"):
                parameter = data[:], reverse
            else:
                parameter = data[:]

            start_time = time.perf_counter()
            result = func(parameter)  # sorting
            end_time = time.perf_counter()

            step_time = end_time - start_time  # lapsed time
            time_log += [step_time]  # add to total time list
            print("{:<20} (第{}次)".format(step_time, i + 1))
        print("{:-<80}\n执行时间: {:<20} (共{}次)".format(
                "", sum(time_log), loop_times))
        print("平均时间: {:<20}\n最好: {}\n最差: {}\n{:=<80}\n".format(
                sum(time_log) / loop_times, min(time_log), max(time_log), ""))

        return result

    return _deco
```

<table width="1035" style="width: 776pt; border-collapse: collapse; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
 <colgroup><col width="258" style="width: 194pt; mso-width-source: userset; mso-width-alt: 9435;">
 <col width="259" style="width: 194pt; mso-width-source: userset; mso-width-alt: 9472;" span="3">
 <tbody><tr height="20" style="height: 15pt;">
  <td width="258" height="20" style="width: 194pt; height: 15pt;">调用函数: bubble_sort</td>
  <td width="259" style="width: 194pt;">调用函数: cocktail_shaker_sort</td>
  <td width="259" style="width: 194pt;">调用函数: selection_sort</td>
  <td width="259" style="width: 194pt;">调用函数: insertion_sort</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">9.519173909667678<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第1次)</td>
  <td>12.353366377898574<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(第1次)</td>
  <td>6.022212266411543<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第1次)</td>
  <td>5.3178025661597985<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(第1次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">9.344330735997186<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第2次)</td>
  <td>12.401928769744408<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(第2次)</td>
  <td>6.044589190428965<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第2次)</td>
  <td>5.284994649743396<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第2次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">9.410286147252986<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第3次)</td>
  <td>12.369088278788084<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(第3次)</td>
  <td>6.040824376822272<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第3次)</td>
  <td>5.2804917773829345<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(第3次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">9.420827357615796<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第4次)</td>
  <td>12.397692740875343<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(第4次)</td>
  <td>6.049547660121718<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第4次)</td>
  <td>5.267566824660605<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第4次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">9.375585337708728<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第5次)</td>
  <td>12.407597632143307<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(第5次)</td>
  <td>6.072944658087835<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第5次)</td>
  <td>5.289787123150489<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(第5次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">执行时间: 47.07020348824237<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(共5次)</td>
  <td>执行时间: 61.929673799449716<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(共5次)</td>
  <td>执行时间: 30.230118151872333<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(共5次)</td>
  <td>执行时间: 26.440642941097224<span style="mso-spacerun: yes;">&nbsp;&nbsp;
  </span>(共5次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">平均时间: 9.414040697648474<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
  <td>平均时间: 12.385934759889944<span style="mso-spacerun: yes;">&nbsp;&nbsp;</span></td>
  <td>平均时间: 6.046023630374466<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
  <td>平均时间: 5.288128588219445<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">最好: 9.344330735997186</td>
  <td>最好: 12.353366377898574</td>
  <td>最好: 6.022212266411543</td>
  <td>最好: 5.267566824660605</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" style="height: 15pt;">最差: 9.519173909667678</td>
  <td>最差: 12.407597632143307</td>
  <td>最差: 6.072944658087835</td>
  <td>最差: 5.3178025661597985</td>
 </tr>
 <!--[if supportMisalignedColumns]-->
 <tr height="0" style="display: none;">
  <td width="258" style="width: 194pt;"></td>
  <td width="259" style="width: 194pt;"></td>
  <td width="259" style="width: 194pt;"></td>
  <td width="259" style="width: 194pt;"></td>
 </tr>
 <!--[endif]-->
</tbody></table>

可以看出 插入 > 选择 > 冒泡 > 鸡尾酒

--------
接下来就是强势的 $O (n \log n)$ 组别了






