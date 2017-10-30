#include <cstdio>
#include <iostream>

/**
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 *
 * For example,
 * Given input array A = [1,1,2],
 *
 * Your function should return length = 2, and A is now [1,2].
 *
 * @example
 * int a[] = {1, 1, 2};
 * std::cout << Q1(a, 3);
 */
unsigned int Q1(int *array, unsigned int length);

unsigned int Q1(int *array, unsigned int length) {
    if (length == 0) return 0;
    unsigned int i = 0;
    for (unsigned int j = 1; j < length; ++j) {
        if (array[j] != array[i]) {
            i++;
            array[i] = array[j];
        }
    }
    return i + 1;
}

int main() {
    std::cout << "����Ԫ������n:\n";
    unsigned int n;
    std::cin >> n;
    int *array = new int[n];
    std::cout << "�Կո�ָ��������Ԫ��:\n";
    for (int i = 0; i < n; ++i) {
        std::cin >> array[i];
    }
    n = Q1(array, n);
    printf("�³���: %d, Ԫ��Ϊ: \n", n);
    for (int i = 0; i < n; ++i) {
        printf("%d ", *(array + i));
    }
    return 0;
}
