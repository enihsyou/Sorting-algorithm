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
  <td width="258" height="20" class="xl1522719" style="width: 194pt; height: 15pt;">调用函数:
  bubble_sort</td>
  <td width="259" class="xl1522719" style="width: 194pt;">调用函数: cocktail_shaker_sort</td>
  <td width="259" class="xl1522719" style="width: 194pt;">调用函数: selection_sort</td>
  <td width="259" class="xl1522719" style="width: 194pt;">调用函数: insertion_sort</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">9.519173909667678<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第1次)</td>
  <td class="xl1522719">12.353366377898574<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第1次)</td>
  <td class="xl1522719">6.022212266411543<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第1次)</td>
  <td class="xl1522719">5.3178025661597985<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第1次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">9.344330735997186<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第2次)</td>
  <td class="xl1522719">12.401928769744408<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第2次)</td>
  <td class="xl1522719">6.044589190428965<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第2次)</td>
  <td class="xl1522719">5.284994649743396<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第2次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">9.410286147252986<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第3次)</td>
  <td class="xl1522719">12.369088278788084<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第3次)</td>
  <td class="xl1522719">6.040824376822272<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第3次)</td>
  <td class="xl1522719">5.2804917773829345<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第3次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">9.420827357615796<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第4次)</td>
  <td class="xl1522719">12.397692740875343<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第4次)</td>
  <td class="xl1522719">6.049547660121718<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第4次)</td>
  <td class="xl1522719">5.267566824660605<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第4次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">9.375585337708728<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第5次)</td>
  <td class="xl1522719">12.407597632143307<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第5次)</td>
  <td class="xl1522719">6.072944658087835<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第5次)</td>
  <td class="xl1522719">5.289787123150489<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第5次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">执行时间:
  47.07020348824237<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(共5次)</td>
  <td class="xl1522719">执行时间: 61.929673799449716<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(共5次)</td>
  <td class="xl1522719">执行时间: 30.230118151872333<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(共5次)</td>
  <td class="xl1522719">执行时间: 26.440642941097224<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(共5次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">平均时间:
  9.414040697648474<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
  <td class="xl1522719">平均时间: 12.385934759889944<span style="mso-spacerun: yes;">&nbsp;&nbsp;</span></td>
  <td class="xl1522719">平均时间: 6.046023630374466<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
  <td class="xl1522719">平均时间: 5.288128588219445<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">最好: 9.344330735997186</td>
  <td class="xl1522719">最好: 12.353366377898574</td>
  <td class="xl1522719">最好: 6.022212266411543</td>
  <td class="xl1522719">最好: 5.267566824660605</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1522719" style="height: 15pt;">最差: 9.519173909667678</td>
  <td class="xl1522719">最差: 12.407597632143307</td>
  <td class="xl1522719">最差: 6.072944658087835</td>
  <td class="xl1522719">最差: 5.3178025661597985</td>
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


--------

###堆排序 Heap Sort
堆是一种数据结构，我们这里要用的是二叉堆。数据可以表现成一颗二叉树，通过对树的节点修改，完成排序操作。

一个数据为`[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]`的树长这样
![带有位置编号的二叉树](http://ww4.sinaimg.cn/large/9d340313gw1f5ce13w3maj20b30f0t8t.jpg)

从下到上从左到右观察序号，可以总结出来(当然也可以直接通过计算)

* 第$i$列有$2^{n-1}$个数字
* 父节点在$2i$的位置 或者`int((i - 1)/2)`
* 左子节点在$2i+1$
* 右子节点在$2i+2$

为了排序我们规定左(下)子节点的数据大小永远小于等于右(上)子节点，同时父节点不大于子节点。
获取排序结果的时候只要从左到右从下到上读取这棵树就行。

要进行排序 我们需要做的就是对这些节点进行旋转，术语叫做"最大堆调整"

![有顺序错误的树](http://ww3.sinaimg.cn/large/9d340313gw1f5cf99l1rfj20b30f03yo.jpg)

但是不能想当然地直接交换父节点和子节点的顺序，这在最后一块虽然是没问题的，但是如图 在中间的话，交换之后
子节点的顺序就出现了错误。

为了完成修改顺序，就需要一层一层向父节点走上去，把小的数字一层一层向上放到父节点，大数字放在子节点上
```python
data = [1, 4, 3, 9, 5, 6, 7, 8, 2, 10, 11, 12, 13]

# 创建最大堆
def max_heap(start, end):
    root = start  # 根节点位置
    end = end  # 最后的位置
    while True:
        child = 2 * root + 1  # 二叉子节点开始的位置
        if child > end: break

        # 先比较两个子节点，选择更大的一个child
        if child + 1 <= end and data[child] > data[child + 1]:
            child += 1

        if data[root] > data[child]:
            data[root], data[child] = data[child], data[root]
            root = child  # 继续向下
        else:  # 子节点小于根节点，跳出
            break

for start in range((len(data) - 2) // 2, -1, -1):
    end = len(data) - 1 # length - 1: 保证有两个位置，不越界
    max_heap(start, end)
```

for循环从最下面一层的父节点 也就是从右向左第二列开始，先交换9 2 8那组，把2和9的数据交换了位置，
接下来从右向左第三列，将4 5 2(9)的顺序摆正，完成了二叉树的排序

虽然排序完成之后的结果是我们期望的，那是因为第一个数字和最小的数字都是1

如果用我们的随机生成器生成一个丢进来，`[3, 5, 4, 8, 2, 7, 6, 0, 9, 1]`
可能会得到这样的结果`[0, 1, 4, 5, 2, 7, 6, 8, 9, 3]`
还需要进一步处理。

不过之前的操作结果很明显，最小的数字到了最上面，这时候只要将第一个数字和最后一个没有确定顺序的数字交换就行
由于还没有排序结果，就把第一个和最后一个数字交换
```python
for end in range(len(data) - 1, 0, -1):
    data[0], data[end] = data[end], data[0]
    max_heap(0, end - 1)
```
就能获得从大到小的排序结果了

    调用函数: heap_sort
    输入:
    [3, 5, 4, 8, 2, 7, 6, 0, 9, 1]
    第一部分:
    [3, 5, 4, 8, 1, 7, 6, 0, 9, 2]
    [3, 5, 4, 0, 1, 7, 6, 8, 9, 2]
    [3, 5, 4, 0, 1, 7, 6, 8, 9, 2]
    [3, 0, 4, 5, 1, 7, 6, 8, 9, 2]
    [0, 1, 4, 5, 2, 7, 6, 8, 9, 3]
    第二部分:
    [1, 2, 4, 5, 3, 7, 6, 8, 9, 0]
    [2, 3, 4, 5, 9, 7, 6, 8, 1, 0]
    [3, 5, 4, 8, 9, 7, 6, 2, 1, 0]
    [4, 5, 6, 8, 9, 7, 3, 2, 1, 0]
    [5, 7, 6, 8, 9, 4, 3, 2, 1, 0]
    [6, 7, 9, 8, 5, 4, 3, 2, 1, 0]
    [7, 8, 9, 6, 5, 4, 3, 2, 1, 0]
    [8, 9, 7, 6, 5, 4, 3, 2, 1, 0]
    [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
    0.00017581405477187906s

10个数字的排序的速度看不出什么，那么就进行100000个数据吧

    调用函数: heap_sort
    1.2504024981660005   (第1次)
    1.2336180569066364   (第2次)
    1.244385105961435    (第3次)
    1.2345189923802011   (第4次)
    1.2370223346321563   (第5次)
    --------------------------------------------------------------------------------
    执行时间: 6.199946988046429    (共5次)
    平均时间: 1.239989397609286
    最好: 1.2336180569066364
    最差: 1.2504024981660005
    ================================================================================

不必多说地相比$O(n^2)$的性能爆炸


### 归并排序 Merge Sort
程序设计的一条重要思维就是"大事化小 小事化了"
其实就是把大问题分割为一个个小的问题，处理了这些小问题就等于解决了大问题。
归并排序就是基于这一思维的，而且能够很好地用于多线程处理。

归并排序的思想也是出奇地简单:

1. 把数据一分为二
2. 把一分为二的部分继续细分
3. 如果一组只剩下一个或两个数据，则排序并返回上一层

当最后一层退出之后，得到的就是排序完成的数据了！

对方想不出该说什么，直接朝你丢出了代码
```python
def merge_sort(data):
    """Merge sort ver.recursive

    归并排序，递归调用
    Args:
        data (List[int]): list to sort, need a not None list

    Returns:
        List[int]: ordered list
    """
    length = len(data)

    def merge(_left, _right):
        if _left == _right:
            return

        # 先分治
        mid = (_left + _right) // 2  # 对半分

        # 再调用自身排序
        merge(_left, mid)
        merge(mid + 1, _right)

        # 交换顺序错了的两个元素
        if _right - _left == 1 and data[_left] < data[_right]:
            data[_left], data[_right] = data[_right], data[_left]

        temp = []  # 合并操作的临时列表
        left_side = _left  # 左半边指针
        right_side = mid + 1  # 右半边指针

        # 进行合并
        # [_left, mid] [mid + 1, _right] 分治的左右范围
        # [left_side, mid] [right_side, _right] 合并操作时的范围
        while left_side <= mid and right_side <= _right:
            if data[left_side] >= data[right_side]:  # 左边元素更大
                temp += [data[left_side]]
                left_side += 1
            else:
                temp += [data[right_side]]  # 右边元素更大
                right_side += 1
        while left_side <= mid:  # 左半边还有剩余元素
            temp += [data[left_side]]
            left_side += 1
        while right_side <= _right:  # 右半边还有剩余元素
            temp += [data[right_side]]
            right_side += 1

        data[_left:_right + 1] = temp  # 切片操作范围 [_left, _right + 1)

    merge(0, length - 1)  # 开始排序

    return data
```

主要的难点是在控制和记录每一层的左右范围，这里我使用了`left_side`和`right_side`
两个变量来记录左右范围。
程序一层一层通过调用自身，缩小范围，直到只剩下一个或者两个元素在一组里，这是我们期望的最容易排序的情况。开辟一个新空间，存放排序好的这部分元素，在覆盖回这部分的里(`left_side`和`right_side`范围)。一层一层下去上来，效率极高而且数据量大的话可以分配到多线程来处理。

数据测试同样拿`[3, 5, 4, 8, 2, 7, 6, 0, 9, 1]`进行:

    调用函数: merge_sort
    [5, 3, 4, 8, 2, 7, 6, 0, 9, 1]
    [5, 4, 3, 8, 2, 7, 6, 0, 9, 1]
    [5, 4, 3, 8, 2, 7, 6, 0, 9, 1]
    [8, 5, 4, 3, 2, 7, 6, 0, 9, 1]
    [8, 5, 4, 3, 2, 7, 6, 0, 9, 1]
    [8, 5, 4, 3, 2, 7, 6, 0, 9, 1]
    [8, 5, 4, 3, 2, 7, 6, 0, 9, 1]
    [8, 5, 4, 3, 2, 9, 7, 6, 1, 0]
    [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
    输入长度: 10 循环次数: 48 比较次数: 28 操作次数: 35

需要开辟新空间进行合并的操作导致了这种算法需要额外一倍的空间。但是速度很可观，100000元素只需要1s不到的时间！

    调用函数: merge_sort
    0.9027587511724603   (第1次)
    0.9062107599506295   (第2次)
    0.9047141160206942   (第3次)
    0.9057230343050069   (第4次)
    0.9114704325809653   (第5次)
    --------------------------------------------------------------------------------
    执行时间: 4.530877094029757    (共5次)
    平均时间: 0.9061754188059513  
    最好: 0.9027587511724603
    最差: 0.9114704325809653
    ================================================================================

如果加上多线程，性能想必就会更高了了！

###快速排序 Quick Sort
这是一种十分简单的算法，简单带来了代码的清晰与高效。通过递归调用达到$O (n \log n)$的性能。

```python
def quick_sort(data):
    """Quick sort

    递归解决列表排序，使用局部变量加快访问，列表推倒式简单方便，可能效率不如原生的。
    没有使用迭代的方式，因为测试之后发现效率更低。

    Args:
        data (List[int]): list to sort, need a not None list

    Returns:
        List[int]: ordered list
    """

    def _quick_sort(_data):
        if len(_data) <= 1: return _data
        pivot = _data[0]

        left = _quick_sort([x for x in _data[1:] if x <= pivot])
        right = _quick_sort([x for x in _data[1:] if x > pivot])
        return right + [pivot] + left

    return _quick_sort(data)
```

`pivot`就是以第一个元素取的基准，将第一个元素后面的，大于基准的放到基准左边，小于的放到右边，一层粗略的排序就完成了。继续重复操作。不过记住，这些都是从元素最少的一层层上来变大的。

简洁带来的提升十分了不得，100000元素的测试比上面两个快了一倍！

    调用函数: quick_sort
    0.43976878323830193  (第1次)
    0.4490810856157079   (第2次)
    0.4438049026021257   (第3次)
    0.43707580586288164  (第4次)
    0.4395657501470318   (第5次)
    --------------------------------------------------------------------------------
    执行时间: 2.209296327466049    (共5次)
    平均时间: 0.4418592654932098  
    最好: 0.43707580586288164
    最差: 0.4490810856157079
    ================================================================================

###总结
对比$O (n \log n)$和$O (n^2)$，很明显的不同就是前者多用递归，后者循环搞定。而递归的好处便是每次需要对比的次数虽然是指数增长的，但也是从少到多，同时数量少的次数最多。或者换种说法，把任务分成了许许多多小任务，增加了速度。

我们来看看1000000(1M)个元素的排序情况:

<table width="1028" style="width: 770pt; border-collapse: collapse; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
 <colgroup><col width="255" style="width: 191pt; mso-width-source: userset; mso-width-alt: 9325;" span="2">
 <col width="259" style="width: 194pt; mso-width-source: userset; mso-width-alt: 9472;" span="2">
 <tbody><tr height="20" style="height: 15pt;">
  <td width="255" height="20" class="xl1518870" style="width: 191pt; height: 15pt;">调用函数:
  heap_sort</td>
  <td width="255" class="xl1518870" style="width: 191pt;">调用函数: merge_sort</td>
  <td width="259" class="xl1518870" style="width: 194pt;">调用函数: quick_sort</td>
  <td width="259" class="xl1518870" style="width: 194pt;">调用函数: build_in</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">15.538451790127148<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第1次)</td>
  <td class="xl1518870">10.874872713869692<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第1次)</td>
  <td class="xl1518870">6.393853407430754<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第1次)</td>
  <td class="xl1518870">0.7502603732060575<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第1次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">15.855851413690411<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第2次)</td>
  <td class="xl1518870">10.857533241648653<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第2次)</td>
  <td class="xl1518870">6.189114391967195<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第2次)</td>
  <td class="xl1518870">0.7355045528497367<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第2次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">15.471093442522005<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第3次)</td>
  <td class="xl1518870">10.87834212548428<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第3次)</td>
  <td class="xl1518870">6.152806720529185<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第3次)</td>
  <td class="xl1518870">0.7365906683313892<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第3次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">15.276213356989288<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第4次)</td>
  <td class="xl1518870">10.89595602704847<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第4次)</td>
  <td class="xl1518870">6.206629677458523<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第4次)</td>
  <td class="xl1518870">0.7408338368256513<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第4次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">15.331094317126087<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第5次)</td>
  <td class="xl1518870">10.845189722152554<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第5次)</td>
  <td class="xl1518870">6.193406199158062<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(第5次)</td>
  <td class="xl1518870">0.7503326619110817<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(第5次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">执行时间:
  77.47270432045494<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;
  </span>(共5次)</td>
  <td class="xl1518870">执行时间: 54.35189383020365<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp; </span>(共5次)</td>
  <td class="xl1518870">执行时间: 31.135810396543718<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(共5次)</td>
  <td class="xl1518870">执行时间: 3.7135220931239163<span style="mso-spacerun: yes;">&nbsp;&nbsp; </span>(共5次)</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">平均时间:
  15.494540864090988<span style="mso-spacerun: yes;">&nbsp;&nbsp;</span></td>
  <td class="xl1518870">平均时间: 10.87037876604073<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
  <td class="xl1518870">平均时间: 6.227162079308743<span style="mso-spacerun: yes;">&nbsp;&nbsp;&nbsp;</span></td>
  <td class="xl1518870">平均时间: 0.7427044186247833<span style="mso-spacerun: yes;">&nbsp;&nbsp;</span></td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">最好: 15.276213356989288</td>
  <td class="xl1518870">最好: 10.845189722152554</td>
  <td class="xl1518870">最好: 6.152806720529185</td>
  <td class="xl1518870">最好: 0.7355045528497367</td>
 </tr>
 <tr height="20" style="height: 15pt;">
  <td height="20" class="xl1518870" style="height: 15pt;">最差: 15.855851413690411</td>
  <td class="xl1518870">最差: 10.89595602704847</td>
  <td class="xl1518870">最差: 6.393853407430754</td>
  <td class="xl1518870">最差: 0.7503326619110817</td>
 </tr>
 <!--[if supportMisalignedColumns]-->
 <tr height="0" style="display: none;">
  <td width="255" style="width: 191pt;"></td>
  <td width="255" style="width: 191pt;"></td>
  <td width="259" style="width: 194pt;"></td>
  <td width="259" style="width: 194pt;"></td>
 </tr>
 <!--[endif]-->
</tbody></table>

虽然从左到右一个比一个快了，但还是被内建函数以数量级的区分度碾压···
