#include <cmath>
#include <vector>
#include <iostream>
#include <iomanip>

/**
 * 打印输出逆时针螺旋矩阵，要求螺旋矩阵的阶数由用户输入。例如N=4时，输出
 * 1  12  11  10
 * 2  13  16  9
 * 3  14  15  8
 * 4   5   6  7
 *
 * @example Q2(10);
 */
std::vector<std::vector<int>> Q2(unsigned int N);

void Q2_print(std::vector<std::vector<int>> a);

std::vector<std::vector<int>> Q2(unsigned int N) {
    std::vector<std::vector<int>> a(N, std::vector<int>(N));
    if (N == 0)
        return a;
    int yoko, tate, count = 1, roll = 0;
    while (roll < N / 2) {
        yoko = tate = roll;
        while (yoko < N - roll - 1) {
            a[yoko][tate] = count++;
            yoko++;
        }
        while (tate < N - roll - 1) {
            a[yoko][tate] = count++;
            tate++;
        }
        while (yoko > roll) {
            a[yoko][tate] = count++;
            yoko--;
        }
        while (tate > roll) {
            a[yoko][tate] = count++;
            tate--;
        }
        roll++;
    }
    if (N % 2)
        a[N / 2][N / 2] = count;
    return a;
}

void Q2_print(std::vector<std::vector<int>> a) {
    int yoko, tate, N = static_cast<int>(a.size());
    int width = 1 + static_cast<int>(std::log10(N * N));
    std::cout << std::left;
    for (yoko = 0; yoko < N; ++yoko) {
        for (tate = 0; tate < N; ++tate)
            std::cout << std::setw(width) << a[yoko][tate] << " ";
        std::cout << std::endl;
    }
}

int main() {
    std::cout << "输入逆时针螺旋矩阵阶数n: ";
    int n;
    std::cin >> n;
    Q2_print(Q2(n));
    return 0;
}
