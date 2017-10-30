#include <string>
#include <iostream>

/*
 * 1����дһ������������ָ֤�����ַ����Ƿ�Ϊ��ת�ַ�������true��false��
 * ���õݹ��㷨ʵ�֡�����ת�ַ�����ʽΪ"abcdedcba"��
 *
 * ��������:
 * �ַ���s��������n.
 *
 * �������:
 * ���true ����false
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
    std::cout << "��������ַ���: ";
    std::string s;
    std::cin >> s;
    std::cout << (Q1(s) ? "�Ƿ�ת�ַ�" : "���Ƿ�ת�ַ�");
    return 0;
}
