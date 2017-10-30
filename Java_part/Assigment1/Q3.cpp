#include <vector>
#include <iostream>

struct Point {
    Point() : Point(0, 0) {}

    Point(int x, int y) : x(x), y(y) {}

    int x;
    int y;
};

/**
 * 给定N个顶点，形成一个凸多边形，判断任意点P在多边形的内部还是外部。
 *
 * 使用Winding Number method（卷绕数）
 * @example
 * 输入点的个数n: 3
   按顺序输入n个点的x和y坐标: 1 1 3 1 2 3
   输入测试点的x和y:0 0
   >> 在外部
 *
 * @param P 测试点
 * @param V[] 由多边形的定点有序排列的数组，大小为n+1，且有V[n] == V[0]，即首尾相接
 * @param n 数组长度
 *
 * @return 点P是否在多边形里
 */
bool Q3(Point P, Point *V, int n);

/**
 * 测试点test_point在由P0和P1点构成的直线的 左边|上|右边
 *
 * @return > 0 test_point在P0和P1构成的直线的左边
 * @return ==0 test_point在直线上
 * @return < 0 test_point在直线右侧
 * */
inline int
isLeft(Point P0, Point P1, Point test_point) {
    return ((P1.x - P0.x) * (test_point.y - P0.y)
            - (test_point.x - P0.x) * (P1.y - P0.y));
}

bool Q3(Point P, Point *V, int n) {
    int wn = 0;    // 卷绕数计数器
    // 遍历多边形的所有边
    for (int i = 0; i < n; i++) {   // 由 V[i] 和 V[i+1] 两点组成的边向量
        if (V[i].y <= P.y) {          // 起始点的 y <= P.y
            if (V[i + 1].y > P.y)      // 向上的交叉点
                if (isLeft(V[i], V[i + 1], P) > 0)  // P 在边的左侧
                    ++wn;            // 有一个有效的上交叉点
        } else {                        // 起始点的 y > P.y (不需要测试)
            if (V[i + 1].y <= P.y)     // 向下的交叉点
                if (isLeft(V[i], V[i + 1], P) < 0)  // P 在边的右侧
                    --wn;            // 有一个有效的下交叉点
        }
    }
    return wn != 0;
}

int main() {
    int n;
    std::cout << "输入点的个数n: ";
    std::cin >> n;
    std::cout << "按顺序输入n个点的x和y坐标: ";
    Point *points = new Point[n + 1];
    for (int i = 0; i < n; ++i) {
        int a, b;
        std::cin >> a >> b;
        points[i].x = a;
        points[i].y = b;
    }
    points[n] = points[0];
    std::cout << "输入测试点的x和y:";
    int x, y;
    std::cin >> x >> y;

    bool t = Q3(Point(x, y), points, n);
    std::cout << (t ? "在内部" : "在外部");
    return 0;
}
