#include <vector>
#include <iostream>

struct Point {
    Point() : Point(0, 0) {}

    Point(int x, int y) : x(x), y(y) {}

    int x;
    int y;
};

/**
 * ����N�����㣬�γ�һ��͹����Σ��ж������P�ڶ���ε��ڲ������ⲿ��
 *
 * ʹ��Winding Number method����������
 * @example
 * �����ĸ���n: 3
   ��˳������n�����x��y����: 1 1 3 1 2 3
   ������Ե��x��y:0 0
   >> ���ⲿ
 *
 * @param P ���Ե�
 * @param V[] �ɶ���εĶ����������е����飬��СΪn+1������V[n] == V[0]������β���
 * @param n ���鳤��
 *
 * @return ��P�Ƿ��ڶ������
 */
bool Q3(Point P, Point *V, int n);

/**
 * ���Ե�test_point����P0��P1�㹹�ɵ�ֱ�ߵ� ���|��|�ұ�
 *
 * @return > 0 test_point��P0��P1���ɵ�ֱ�ߵ����
 * @return ==0 test_point��ֱ����
 * @return < 0 test_point��ֱ���Ҳ�
 * */
inline int
isLeft(Point P0, Point P1, Point test_point) {
    return ((P1.x - P0.x) * (test_point.y - P0.y)
            - (test_point.x - P0.x) * (P1.y - P0.y));
}

bool Q3(Point P, Point *V, int n) {
    int wn = 0;    // ������������
    // ��������ε����б�
    for (int i = 0; i < n; i++) {   // �� V[i] �� V[i+1] ������ɵı�����
        if (V[i].y <= P.y) {          // ��ʼ��� y <= P.y
            if (V[i + 1].y > P.y)      // ���ϵĽ����
                if (isLeft(V[i], V[i + 1], P) > 0)  // P �ڱߵ����
                    ++wn;            // ��һ����Ч���Ͻ����
        } else {                        // ��ʼ��� y > P.y (����Ҫ����)
            if (V[i + 1].y <= P.y)     // ���µĽ����
                if (isLeft(V[i], V[i + 1], P) < 0)  // P �ڱߵ��Ҳ�
                    --wn;            // ��һ����Ч���½����
        }
    }
    return wn != 0;
}

int main() {
    int n;
    std::cout << "�����ĸ���n: ";
    std::cin >> n;
    std::cout << "��˳������n�����x��y����: ";
    Point *points = new Point[n + 1];
    for (int i = 0; i < n; ++i) {
        int a, b;
        std::cin >> a >> b;
        points[i].x = a;
        points[i].y = b;
    }
    points[n] = points[0];
    std::cout << "������Ե��x��y:";
    int x, y;
    std::cin >> x >> y;

    bool t = Q3(Point(x, y), points, n);
    std::cout << (t ? "���ڲ�" : "���ⲿ");
    return 0;
}
