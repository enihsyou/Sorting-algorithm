
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

/*
 * 2�� ������������֮��Ϊ��ֵ���ҳ���������±ꡣ��֪һ������һ����������������֮�ͣ��ҳ����������ĳһ�Է�����������
 * ���磺a[]={1,2,3,5,8} n=10�������{2��8}
 * Ҫ���д�����㷨��ʱ�临�Ӷ�ΪO(n2)�ģ��Լ�ʱ�临�Ӷ�Ϊ O(nlgn)�ġ�
 *
 * ��������:
 * ����a��Ԫ�ظ���n���Լ�����m
 *
 * ���������
 * ������ӵ���m�����ݶ�
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
    printf("�ܹ���%d��\n", count);
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
    printf("�ܹ���%d��\n", count);
}


int main() {
//    system("chcp 65001");
    printf("���������Сn\n");
    int n;
    scanf("%d", &n);
    printf("����n������Ԫ��\n");
    int a[n];
    for (int i = 0; i < n; ++i) {
        scanf("%d", a + i);
    }
    printf("������ĺ�\n");
    int m;
    scanf("%d", &m);
    printf("ʱ�临�Ӷ�ΪO(n2)��\n");
    F1(a, n, m);

    printf("ʱ�临�Ӷ�ΪO(nlogn)��\n");
    F2(a, n, m);
    return 0;
}
