#include <string>
#include <iostream>

/*
 * 1）编写一个方法用于验证指定的字符串是否为反转字符，返回true和false。
 * 请用递归算法实现。（反转字符串样式为"abcdedcba"）
 *
 * 输入描述:
 * 字符串s，及长度n.
 *
 * 输出描述:
 * 输出true 或者false
 */
bool Q1(const std::string &s);

bool __Q1(const std::string &s, unsigned long a, unsigned long b) {
    if (a >= b)return true;
    return s.at(a) == s.at(b) && __Q1(s, a + 1, b - 1);
}


bool Q1(const std::string &s) {
    return __Q1(s, 0, s.length() - 1);
}


int main() {
    std::cout << "输入测试字符串: ";
    std::string s;
    std::cin >> s;
    std::cout << (Q1(s) ? "是反转字符" : "不是反转字符");
    return 0;
}
