
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

/*
 * 2） 数组中两个数之和为定值，找出这对数的下标。已知一个数是一个数组中两个数据之和，找出这个数组中某一对符合条件的数
 * 例如：a[]={1,2,3,5,8} n=10，则输出{2，8}
 * 要求编写两种算法：时间复杂度为O(n2)的，以及时间复杂度为 O(nlgn)的。
 *
 * 输入描述:
 * 数组a及元素个数n，以及和数m
 *
 * 输出描述：
 * 所有相加等于m的数据对
 * */

void F1(int *a, int n, int m) {
    int count = 0;
    bool checked = false;

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            if (a[i] + a[j] == m && i != j) {
                if (a[i] > a[j]) {
                    continue;
                } else if (a[i] == a[j]) {
                    if (checked)
                        continue;
                    checked = true;
                }
                printf("%d + %d == %d\n", a[i], a[j], m);
                count++;
                break;
            }
        }
    }
    printf("总共有%d对\n", count);
}

int compare(const void *e, const void *b) {
    return *(int *) e - *(int *) b;
}

void F2(int *a, int n, int m) {
    int count = 0;
    qsort(a, n, sizeof(int), compare);
    int first, last, middle, search, last_number = a[0];
    bool checked = false;
    for (int i = 0; i < n; ++i) {
        first = i;
        last = n - 1;
        middle = (first + last) / 2;
        search = m - a[i];
        if (a[i] >= m) break;
        if (a[i] == last_number) {
            if (m % 2 == 0 && a[i] == m / 2) {
                if (checked)
                    continue;
                checked = true;
            }
        }
        last_number = a[i];

        while (first <= last) {
            if (a[middle] < search)
                first = middle + 1;
            else if (a[middle] == search) {
                printf("%d + %d == %d\n", a[i], a[middle], m);
                count++;
                break;
            } else
                last = middle - 1;

            middle = (first + last) / 2;
        }
//        if (first > last)
//            continue;
    }
    printf("总共有%d对\n", count);
}


int main() {
//    system("chcp 65001");
    printf("输入数组大小n\n");
    int n;
    scanf("%d", &n);
    printf("输入n个数组元素\n");
    int a[n];
    for (int i = 0; i < n; ++i) {
        scanf("%d", a + i);
    }
    printf("输入求的和\n");
    int m;
    scanf("%d", &m);
    printf("时间复杂度为O(n2)的\n");
    F1(a, n, m);

    printf("时间复杂度为O(nlogn)的\n");
    F2(a, n, m);
    return 0;
}
