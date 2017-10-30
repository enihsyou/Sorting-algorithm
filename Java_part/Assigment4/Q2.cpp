

#include <iostream>

/*
 * 2）一个射击运动员打靶,靶一共有10环,连开n枪打中m环的可能行有多少种?
 * 输入描述:
 * 枪数：n，环数m
 *
 * 输出描述:
 * 返回值0代表一次失败的组合，返回值大于0则代表满足题设情况的组合数量。
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
    std::cout << "输入枪数n，和目标环数m: ";
    int n, m;
    std::cin >> n >> m;
    std::cout << "共有" << Q2(n, m) << "种组合";
}
