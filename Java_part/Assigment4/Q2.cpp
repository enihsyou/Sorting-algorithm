

#include <iostream>

/*
 * 2��һ������˶�Ա���,��һ����10��,����nǹ����m���Ŀ������ж�����?
 * ��������:
 * ǹ����n������m
 *
 * �������:
 * ����ֵ0����һ��ʧ�ܵ���ϣ�����ֵ����0���������������������������
 */
unsigned long Q2(int shuts, int score);

unsigned long Q2(int shuts, int score) {
    if (shuts < 0 || score < 0) return 0;
    if (score == 0)return 1;
    unsigned long sum = 0;
    for (int i = 0; i <= 10; ++i) {
        sum += Q2(shuts - 1, score - i);
    }
    return sum;
}

int main() {
    std::cout << "����ǹ��n����Ŀ�껷��m: ";
    int n, m;
    std::cin >> n >> m;
    std::cout << "����" << Q2(n, m) << "�����";
}
